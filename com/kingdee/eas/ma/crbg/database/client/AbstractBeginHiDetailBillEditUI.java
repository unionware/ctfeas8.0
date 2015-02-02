/**
 * output package name
 */
package com.kingdee.eas.ma.crbg.database.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractBeginHiDetailBillEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBeginHiDetailBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompanyOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateOfEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFCAA;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFAccountID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContents;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOriginalCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrencyAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReportingCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBorrowingDirection;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contassistGrp;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompanyOrg;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateOfEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFcurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFCAA;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFAccountID;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContents;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOriginalCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCurrencyAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReportingCurrency;
    protected com.kingdee.bos.ctrl.swing.KDComboBox BorrowingDirection;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtassistGrp;
    protected com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBeginHiDetailBillEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractBeginHiDetailBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompanyOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateOfEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFCAA = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFAccountID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContents = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOriginalCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrencyAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReportingCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBorrowingDirection = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contassistGrp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCompanyOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkDateOfEntry = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtFcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFCAA = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFAccountID = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtContents = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOriginalCurrency = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCurrencyAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtReportingCurrency = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.BorrowingDirection = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtassistGrp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contCompanyOrg.setName("contCompanyOrg");
        this.contDateOfEntry.setName("contDateOfEntry");
        this.contFcurrency.setName("contFcurrency");
        this.contFCAA.setName("contFCAA");
        this.contFAccountID.setName("contFAccountID");
        this.contContents.setName("contContents");
        this.contOriginalCurrency.setName("contOriginalCurrency");
        this.contCurrencyAmount.setName("contCurrencyAmount");
        this.contReportingCurrency.setName("contReportingCurrency");
        this.contBorrowingDirection.setName("contBorrowingDirection");
        this.contassistGrp.setName("contassistGrp");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtCompanyOrg.setName("prmtCompanyOrg");
        this.pkDateOfEntry.setName("pkDateOfEntry");
        this.prmtFcurrency.setName("prmtFcurrency");
        this.prmtFCAA.setName("prmtFCAA");
        this.prmtFAccountID.setName("prmtFAccountID");
        this.txtContents.setName("txtContents");
        this.txtOriginalCurrency.setName("txtOriginalCurrency");
        this.txtCurrencyAmount.setName("txtCurrencyAmount");
        this.txtReportingCurrency.setName("txtReportingCurrency");
        this.BorrowingDirection.setName("BorrowingDirection");
        this.prmtassistGrp.setName("prmtassistGrp");
        // CoreUI		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.menuItemCreateTo.setVisible(true);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemCopyLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contCompanyOrg		
        this.contCompanyOrg.setBoundLabelText(resHelper.getString("contCompanyOrg.boundLabelText"));		
        this.contCompanyOrg.setBoundLabelLength(100);		
        this.contCompanyOrg.setBoundLabelUnderline(true);		
        this.contCompanyOrg.setVisible(true);
        // contDateOfEntry		
        this.contDateOfEntry.setBoundLabelText(resHelper.getString("contDateOfEntry.boundLabelText"));		
        this.contDateOfEntry.setBoundLabelLength(100);		
        this.contDateOfEntry.setBoundLabelUnderline(true);		
        this.contDateOfEntry.setVisible(true);
        // contFcurrency		
        this.contFcurrency.setBoundLabelText(resHelper.getString("contFcurrency.boundLabelText"));		
        this.contFcurrency.setBoundLabelLength(100);		
        this.contFcurrency.setBoundLabelUnderline(true);		
        this.contFcurrency.setVisible(true);
        // contFCAA		
        this.contFCAA.setBoundLabelText(resHelper.getString("contFCAA.boundLabelText"));		
        this.contFCAA.setBoundLabelLength(100);		
        this.contFCAA.setBoundLabelUnderline(true);		
        this.contFCAA.setVisible(true);
        // contFAccountID		
        this.contFAccountID.setBoundLabelText(resHelper.getString("contFAccountID.boundLabelText"));		
        this.contFAccountID.setBoundLabelLength(100);		
        this.contFAccountID.setBoundLabelUnderline(true);		
        this.contFAccountID.setVisible(true);
        // contContents		
        this.contContents.setBoundLabelText(resHelper.getString("contContents.boundLabelText"));		
        this.contContents.setBoundLabelLength(100);		
        this.contContents.setBoundLabelUnderline(true);		
        this.contContents.setVisible(true);
        // contOriginalCurrency		
        this.contOriginalCurrency.setBoundLabelText(resHelper.getString("contOriginalCurrency.boundLabelText"));		
        this.contOriginalCurrency.setBoundLabelLength(100);		
        this.contOriginalCurrency.setBoundLabelUnderline(true);		
        this.contOriginalCurrency.setVisible(true);
        // contCurrencyAmount		
        this.contCurrencyAmount.setBoundLabelText(resHelper.getString("contCurrencyAmount.boundLabelText"));		
        this.contCurrencyAmount.setBoundLabelLength(100);		
        this.contCurrencyAmount.setBoundLabelUnderline(true);		
        this.contCurrencyAmount.setVisible(true);
        // contReportingCurrency		
        this.contReportingCurrency.setBoundLabelText(resHelper.getString("contReportingCurrency.boundLabelText"));		
        this.contReportingCurrency.setBoundLabelLength(100);		
        this.contReportingCurrency.setBoundLabelUnderline(true);		
        this.contReportingCurrency.setVisible(true);
        // contBorrowingDirection		
        this.contBorrowingDirection.setBoundLabelText(resHelper.getString("contBorrowingDirection.boundLabelText"));		
        this.contBorrowingDirection.setBoundLabelLength(100);		
        this.contBorrowingDirection.setBoundLabelUnderline(true);		
        this.contBorrowingDirection.setVisible(true);
        // contassistGrp		
        this.contassistGrp.setBoundLabelText(resHelper.getString("contassistGrp.boundLabelText"));		
        this.contassistGrp.setBoundLabelLength(100);		
        this.contassistGrp.setBoundLabelUnderline(true);		
        this.contassistGrp.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtCompanyOrg		
        this.prmtCompanyOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtCompanyOrg.setVisible(true);		
        this.prmtCompanyOrg.setEditable(true);		
        this.prmtCompanyOrg.setDisplayFormat("$number$");		
        this.prmtCompanyOrg.setEditFormat("$number$");		
        this.prmtCompanyOrg.setCommitFormat("$number$");		
        this.prmtCompanyOrg.setRequired(false);
        // pkDateOfEntry		
        this.pkDateOfEntry.setVisible(true);		
        this.pkDateOfEntry.setRequired(false);
        // prmtFcurrency		
        this.prmtFcurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtFcurrency.setVisible(true);		
        this.prmtFcurrency.setEditable(true);		
        this.prmtFcurrency.setDisplayFormat("$number$");		
        this.prmtFcurrency.setEditFormat("$number$");		
        this.prmtFcurrency.setCommitFormat("$number$");		
        this.prmtFcurrency.setRequired(false);
        // prmtFCAA		
        this.prmtFCAA.setQueryInfo("com.kingdee.eas.basedata.master.auxacct.app.AsstAccountQuery");		
        this.prmtFCAA.setVisible(true);		
        this.prmtFCAA.setEditable(true);		
        this.prmtFCAA.setDisplayFormat("$number$");		
        this.prmtFCAA.setEditFormat("$number$");		
        this.prmtFCAA.setCommitFormat("$number$");		
        this.prmtFCAA.setRequired(false);
        // prmtFAccountID		
        this.prmtFAccountID.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtFAccountID.setVisible(true);		
        this.prmtFAccountID.setEditable(true);		
        this.prmtFAccountID.setDisplayFormat("$number$");		
        this.prmtFAccountID.setEditFormat("$number$");		
        this.prmtFAccountID.setCommitFormat("$number$");		
        this.prmtFAccountID.setRequired(false);
        // txtContents		
        this.txtContents.setVisible(true);		
        this.txtContents.setHorizontalAlignment(2);		
        this.txtContents.setMaxLength(255);		
        this.txtContents.setRequired(false);
        // txtOriginalCurrency		
        this.txtOriginalCurrency.setVisible(true);		
        this.txtOriginalCurrency.setHorizontalAlignment(2);		
        this.txtOriginalCurrency.setDataType(1);		
        this.txtOriginalCurrency.setSupportedEmpty(true);		
        this.txtOriginalCurrency.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtOriginalCurrency.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtOriginalCurrency.setPrecision(2);		
        this.txtOriginalCurrency.setRequired(false);
        // txtCurrencyAmount		
        this.txtCurrencyAmount.setVisible(true);		
        this.txtCurrencyAmount.setHorizontalAlignment(2);		
        this.txtCurrencyAmount.setDataType(1);		
        this.txtCurrencyAmount.setSupportedEmpty(true);		
        this.txtCurrencyAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtCurrencyAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtCurrencyAmount.setPrecision(2);		
        this.txtCurrencyAmount.setRequired(false);
        // txtReportingCurrency		
        this.txtReportingCurrency.setVisible(true);		
        this.txtReportingCurrency.setHorizontalAlignment(2);		
        this.txtReportingCurrency.setDataType(1);		
        this.txtReportingCurrency.setSupportedEmpty(true);		
        this.txtReportingCurrency.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtReportingCurrency.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtReportingCurrency.setPrecision(2);		
        this.txtReportingCurrency.setRequired(false);
        // BorrowingDirection		
        this.BorrowingDirection.setVisible(true);		
        this.BorrowingDirection.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.gl.EntryDC").toArray());		
        this.BorrowingDirection.setRequired(false);
        // prmtassistGrp		
        this.prmtassistGrp.setVisible(true);		
        this.prmtassistGrp.setEditable(true);		
        this.prmtassistGrp.setDisplayFormat("$numberGroup$");		
        this.prmtassistGrp.setEditFormat("$number$");		
        this.prmtassistGrp.setCommitFormat("$number$");		
        this.prmtassistGrp.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtCompanyOrg,pkDateOfEntry,prmtFcurrency,prmtFCAA,prmtFAccountID,txtContents,txtOriginalCurrency,txtCurrencyAmount,txtReportingCurrency,BorrowingDirection,prmtassistGrp}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(440, 524, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(730, 524, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(440, 555, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(730, 555, 270, 19));
        this.add(contLastUpdateTime, null);
        contNumber.setBounds(new Rectangle(11, 560, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(367, 8, 270, 19));
        this.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(11, 600, 270, 19));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(15, 528, 270, 19));
        this.add(contAuditor, null);
        contCompanyOrg.setBounds(new Rectangle(8, 8, 270, 19));
        this.add(contCompanyOrg, null);
        contDateOfEntry.setBounds(new Rectangle(727, 10, 270, 19));
        this.add(contDateOfEntry, null);
        contFcurrency.setBounds(new Rectangle(8, 39, 270, 19));
        this.add(contFcurrency, null);
        contFCAA.setBounds(new Rectangle(367, 42, 270, 19));
        this.add(contFCAA, null);
        contFAccountID.setBounds(new Rectangle(727, 43, 270, 19));
        this.add(contFAccountID, null);
        contContents.setBounds(new Rectangle(8, 70, 270, 19));
        this.add(contContents, null);
        contOriginalCurrency.setBounds(new Rectangle(10, 354, 270, 19));
        this.add(contOriginalCurrency, null);
        contCurrencyAmount.setBounds(new Rectangle(369, 349, 270, 19));
        this.add(contCurrencyAmount, null);
        contReportingCurrency.setBounds(new Rectangle(731, 346, 270, 19));
        this.add(contReportingCurrency, null);
        contBorrowingDirection.setBounds(new Rectangle(7, 386, 270, 19));
        this.add(contBorrowingDirection, null);
        contassistGrp.setBounds(new Rectangle(43, 111, 270, 19));
        this.add(contassistGrp, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCompanyOrg
        contCompanyOrg.setBoundEditor(prmtCompanyOrg);
        //contDateOfEntry
        contDateOfEntry.setBoundEditor(pkDateOfEntry);
        //contFcurrency
        contFcurrency.setBoundEditor(prmtFcurrency);
        //contFCAA
        contFCAA.setBoundEditor(prmtFCAA);
        //contFAccountID
        contFAccountID.setBoundEditor(prmtFAccountID);
        //contContents
        contContents.setBoundEditor(txtContents);
        //contOriginalCurrency
        contOriginalCurrency.setBoundEditor(txtOriginalCurrency);
        //contCurrencyAmount
        contCurrencyAmount.setBoundEditor(txtCurrencyAmount);
        //contReportingCurrency
        contReportingCurrency.setBoundEditor(txtReportingCurrency);
        //contBorrowingDirection
        contBorrowingDirection.setBoundEditor(BorrowingDirection);
        //contassistGrp
        contassistGrp.setBoundEditor(prmtassistGrp);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("CompanyOrg", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCompanyOrg, "data");
		dataBinder.registerBinding("DateOfEntry", java.util.Date.class, this.pkDateOfEntry, "value");
		dataBinder.registerBinding("Fcurrency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtFcurrency, "data");
		dataBinder.registerBinding("FCAA", com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo.class, this.prmtFCAA, "data");
		dataBinder.registerBinding("FAccountID", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtFAccountID, "data");
		dataBinder.registerBinding("Contents", String.class, this.txtContents, "text");
		dataBinder.registerBinding("OriginalCurrency", java.math.BigDecimal.class, this.txtOriginalCurrency, "value");
		dataBinder.registerBinding("CurrencyAmount", java.math.BigDecimal.class, this.txtCurrencyAmount, "value");
		dataBinder.registerBinding("ReportingCurrency", java.math.BigDecimal.class, this.txtReportingCurrency, "value");
		dataBinder.registerBinding("BorrowingDirection", com.kingdee.eas.fi.gl.EntryDC.class, this.BorrowingDirection, "selectedItem");
		dataBinder.registerBinding("assistGrp", com.kingdee.eas.basedata.master.auxacct.AssistantHGInfo.class, this.prmtassistGrp, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.crbg.database.app.BeginHiDetailBillEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtCompanyOrg.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CompanyOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DateOfEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Fcurrency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FCAA", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FAccountID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Contents", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OriginalCurrency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CurrencyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ReportingCurrency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BorrowingDirection", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assistGrp", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CompanyOrg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CompanyOrg.id"));
        	sic.add(new SelectorItemInfo("CompanyOrg.number"));
        	sic.add(new SelectorItemInfo("CompanyOrg.name"));
		}
        sic.add(new SelectorItemInfo("DateOfEntry"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Fcurrency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Fcurrency.id"));
        	sic.add(new SelectorItemInfo("Fcurrency.number"));
        	sic.add(new SelectorItemInfo("Fcurrency.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FCAA.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("FCAA.id"));
        	sic.add(new SelectorItemInfo("FCAA.number"));
        	sic.add(new SelectorItemInfo("FCAA.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FAccountID.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("FAccountID.id"));
        	sic.add(new SelectorItemInfo("FAccountID.number"));
        	sic.add(new SelectorItemInfo("FAccountID.name"));
		}
        sic.add(new SelectorItemInfo("Contents"));
        sic.add(new SelectorItemInfo("OriginalCurrency"));
        sic.add(new SelectorItemInfo("CurrencyAmount"));
        sic.add(new SelectorItemInfo("ReportingCurrency"));
        sic.add(new SelectorItemInfo("BorrowingDirection"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("assistGrp.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("assistGrp.id"));
        	sic.add(new SelectorItemInfo("assistGrp.numberGroup"));
        	sic.add(new SelectorItemInfo("assistGrp.number"));
		}
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.crbg.database.client", "BeginHiDetailBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.ma.crbg.database.client.BeginHiDetailBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.ma.crbg.database.BeginHiDetailBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo objectValue = new com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/ma/crbg/database/BeginHiDetailBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.ma.crbg.database.app.BeginHiDetailBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("BorrowingDirection",new Integer(1));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}