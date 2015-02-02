package com.kingdee.eas.framework.client.service;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.base.license.LicenseException;
import com.kingdee.eas.base.license.LicenseUserInfo;
import com.kingdee.eas.base.license.client.LicenseController;
import com.kingdee.eas.base.license.client.monitor.LicenseClientUtil;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class LicenseService
{
  private Logger logger = Logger.getLogger(LicenseService.class);
  private boolean hasLicence = false;
  private IUIObject ui;
  public static String MODULENAME = "moduleName";
  public static String USERDEF = ".UserDef";

  private static List licenseRecordList = new ArrayList();

  public LicenseService(IUIObject ui) {
    this.ui = ui;
  }

  public void checkLicence()
    throws Exception
  {
    LicenseUserInfo user = (LicenseUserInfo)SysContext.getSysContext().getProperty("License.UserInfo");
    LicenseController lc = FrameWorkClientUtils.getLicenseController();

    String className = getUIClassName(this.ui);

    if (!needCheck(lc, className)) {
      return;
    }

    String moduleName = (String)this.ui.getUIContext().get(MODULENAME);
    if (!StringUtils.isEmpty(moduleName)) {
      userDefRequestLicense(lc, className, moduleName);
      return;
    }
    int licenselcFlag = lc.requestLicense(user, className);

    switch (licenselcFlag)
    {
    case 1:
      this.hasLicence = true;
      if (!licenseRecordList.contains(className))
        licenseRecordList.add(className); break;
    case 4:
      MsgBox.showDetailAndOK((CoreUI)this.ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_Invalid"), constructMessage(lc, className), 0);

      abort();
      break;
    case 2:
      MsgBox.showDetailAndOK((CoreUI)this.ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_NO_SURPLUS"), constructMessage(lc, className), 0);

      abort();
    case 3:
    }
  }

  public void releaseLicense()
    throws Exception
  {
    if (this.hasLicence) {
      String sessionId = SysContext.getSysContext().getSessionID();

      LicenseController lc = FrameWorkClientUtils.getLicenseController();
      try
      {
        String className = getUIClassName(this.ui);

        if (!needCheck(lc, className)) {
          return;
        }
        String moduleName = (String)this.ui.getUIContext().get(MODULENAME);
        if (!StringUtils.isEmpty(moduleName)) {
          userDefReleaseLicense(lc, className, moduleName);
          return;
        }
        lc.releaseLicense(sessionId, className);
        licenseRecordList.remove(className);
      }
      catch (LicenseException lex)
      {
      }

      this.hasLicence = false;
    }
  }

  public void userDefReleaseLicense(LicenseController lc, String className, String moduleName) throws Exception {
    if (this.hasLicence) {
      String sessionId = SysContext.getSysContext().getSessionID();
      try
      {
        lc.releaseLicenseByModuleAndUIName(sessionId, moduleName, className);
        String userDef = moduleName + USERDEF;
        licenseRecordList.remove(userDef);
      }
      catch (LicenseException lex)
      {
      }

      this.hasLicence = false;
    }
  }

  public void userDefRequestLicense(LicenseController lc, String className, String moduleName) throws Exception
  {
    LicenseUserInfo user = (LicenseUserInfo)SysContext.getSysContext().getProperty("License.UserInfo");

    int licenselcFlag = lc.reqeustLicenseByModuleAndUIName(user, moduleName, className);

    switch (licenselcFlag)
    {
    case 1:
      this.hasLicence = true;
      String userDef = moduleName + USERDEF;
      licenseRecordList.add(userDef);

      break;
    case 4:
      MsgBox.showDetailAndOK((CoreUI)this.ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_Invalid"), constructMessageByModuleName(lc, moduleName), 0);

      abort();
      break;
    case 2:
      MsgBox.showDetailAndOK((CoreUI)this.ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_License_NO_SURPLUS"), constructMessageByModuleName(lc, moduleName), 0);

      abort();
    case 3:
    }
  }

  private String getUIClassName(IUIObject uiObject)
  {
    String className = null;
    if (uiObject.getMetaDataPK() != null)
      className = uiObject.getMetaDataPK().getFullName();
    else {
      className = uiObject.getClass().getName();
    }
    return className;
  }

  private String constructMessageByModuleName(LicenseController lc, String moduleName) throws LicenseException {
    if ((lc == null) || (moduleName == null)) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ClassName"));
    buffer.append(this.ui.getUITitle() + "[" + getUIClassName(this.ui) + "]");
    buffer.append("\n");
    buffer.append(constructLicenseMessageByModuleName(lc, moduleName));
    return buffer.toString();
  }

  private boolean needCheck(LicenseController lc, String fullClassName)
    throws LicenseException
  {/*
    String check = (String)this.ui.getUIContext().get("checkLicense");
    if ((!StringUtils.isEmpty(check)) && (check.trim().equalsIgnoreCase("true"))) {
      this.logger.info("license main menu,need check£º" + fullClassName);
      return true;
    }

    this.logger.info("coreui license unlimited £º" + fullClassName);*/
    return false;
  }

  private String constructMessage(LicenseController lc, String className)
    throws LicenseException
  {
    if ((lc == null) || (className == null)) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ClassName"));
    buffer.append(this.ui.getUITitle() + "[" + getUIClassName(this.ui) + "]");
    buffer.append("\n");
    buffer.append(constructLicenseMessage(lc, className));
    return buffer.toString();
  }

  private final String getPackageNameForClass(Class cls)
  {
    String packageName = getUIClassName(this.ui);
    return packageName.substring(0, packageName.lastIndexOf(".") + 1);
  }

  private void abort()
  {
    ((CoreUI)this.ui).setCursorOfDefault();
    SysUtil.abort();
  }

  public static final List getLicenseRecordList()
  {
    return licenseRecordList;
  }

  public static final String constructLicenseMessage(LicenseController lc, String className)
    throws LicenseException
  {
    if ((lc == null) || (className == null)) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ModuleName"));
    String moduleName = lc.getModuleByPackage(className);
    if ((moduleName == null) || (moduleName.trim().length() == 0))
      buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));
    else {
      buffer.append(LicenseClientUtil.getModularAliasNameByModularName(moduleName) + "[" + moduleName + "]");
    }
    buffer.append("\n");
    buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_SubSystem"));
    String subSysName = LicenseClientUtil.getSubSystemNameByModularName(moduleName);
    if ((subSysName == null) || (subSysName.trim().length() == 0))
      buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));
    else {
      buffer.append(LicenseClientUtil.getModularAliasNameByModularName(subSysName) + "[" + subSysName + "]");
    }
    buffer.append("\n");
    return buffer.toString();
  }

  public static final String constructLicenseMessageByModuleName(LicenseController lc, String moduleName) throws LicenseException {
    if ((lc == null) || (moduleName == null)) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ModuleName"));
    buffer.append(LicenseClientUtil.getModularAliasNameByModularName(moduleName) + "[" + moduleName + "]");
    buffer.append("\n");
    buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_SubSystem"));
    String subSysName = LicenseClientUtil.getSubSystemNameByModularName(moduleName);
    if ((subSysName == null) || (subSysName.trim().length() == 0))
      buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));
    else {
      buffer.append(LicenseClientUtil.getModularAliasNameByModularName(subSysName) + "[" + subSysName + "]");
    }
    buffer.append("\n");
    return buffer.toString();
  }
}