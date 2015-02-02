package com.kingdee.eas.scm.common.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.util.ParamHelper;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetListener;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDList;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.resource.KDResourceManager;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.access.MetaDataAccessFactory;
import com.kingdee.bos.metadata.access.query.QuerySelect;
import com.kingdee.bos.metadata.access.query.SimplePropertyUnit;
import com.kingdee.bos.metadata.access.query.SimplePropertyUnitCollection;
import com.kingdee.bos.metadata.access.query.SimpleQuery;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.query.util.ConstDataType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.myeas.F7MatchEnum;
import com.kingdee.eas.base.ui.IMainUIContainer;
import com.kingdee.eas.base.uiframe.client.NewMainFrame;
import com.kingdee.eas.base.uiframe.client.SystemEntry;
import com.kingdee.eas.basedata.framework.IBaseDataParser;
import com.kingdee.eas.basedata.master.cssp.CSOptionInfo;
import com.kingdee.eas.basedata.master.cssp.CSType;
import com.kingdee.eas.basedata.master.cssp.UserParamaterInfo;
import com.kingdee.eas.basedata.master.cssp.client.CSUtils;
import com.kingdee.eas.basedata.master.cssp.client.F7CSSPTreeListUI;
import com.kingdee.eas.basedata.master.cssp.client.F7CustomerTreeDetailListUI;
import com.kingdee.eas.basedata.master.cssp.client.F7SupplierTreeDetailListUI;
import com.kingdee.eas.basedata.master.material.MaterialSelectEnum;
import com.kingdee.eas.basedata.master.material.MaterialUtil;
import com.kingdee.eas.basedata.master.material.client.F7MaterialTreeListUI;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.client.f7.IF7Provider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

public class GeneralF7TreeListUI extends AbstractGeneralF7TreeListUI
{
  private static final Logger logger = CoreUIObject.getLogger(GeneralF7TreeListUI.class);
  private Component adaptee;
  public static final int IDYES = 1;
  public static final int IDNO = 2;
  private int retVal;
  private String[] filterDisplayName;
  private String[] filterName;
  private static final int SELECTED = 1;
  private String value;
  private String queryInfoName;
  private MetaDataPK queryInfo;
  private String allFilterFieldName = null;
  private String queryProperty;
  private String compareType;
  private boolean isQuickQuery = false;
  private static final int OBJECT_LISTUI_WIDTH = 800;
  private static final int OBJECT_LISTUI_HEIGHT = 500;
  private static final String DEFAULT_F7_FIELD = "defaultF7Field";
  private boolean isRequeryQuery = false;

  protected String F7matchType = F7MatchEnum.ALLMATCH.getValue();
  private KDPanel simpleQuickFilterPanel;
  protected KDTextField txtQuickQueryInput;
  private ArrayList quickQueryContent;
  private QuickQueryHandler quickQueryHandler = null;

  private KDBizPromptBox promptBox = null;
  private KDPanel quickQueryPopup;
  private Popup popup;
  private KDList quickQueryConditionList;
  private CentralizedHandler centralizedHandler;
  private FilterFieldItem[] filterFields;
  private FilterInfo quickQueryFilter = new FilterInfo();

  private int myCommonLimit = 100;
  protected KDWorkButton btnAddToMyCommon;
  protected KDWorkButton btnDelete;
  protected KDWorkButton btnMaintainMyCommon;
  private static final String TABLE_COLUMN_NAME = "name";
  private static final String TABLE_COLUMN_NUMBER = "number";
  private static final String TABLE_COLUMN_ID = "id";
  private static final int COLUMN_WIDTH = 100;
  private KDTable myCommonUseTable;
  private static final int BUTTON_WIDTH = 73;
  private static final int BUTTON_HEIGHT = 21;
  private static final int SPAN = 10;
  protected KDTabbedPane kdTMain;
  private String[] myCommonUseCache = null;

  private ArrayList myCommonUseContent = new ArrayList();
  private KDLabel statusLable;
  private boolean isNeedUpdateMyCommonUse = true;

  private int returnValueType = 0;
  protected SelectorItemCollection selectorCollection;
  private TabbedPaneMainSwitchListener tabbedPaneMainSwitchListener = null;

  private boolean isCapitalSensitiveQuery = false;
  protected Image icon_off;
  protected Image icon_disable;
  protected static final Color BGCOLOR = new Color(7645403);

  public boolean isDefaultFilterFieldsEnabled = true;
  private static final String numberAndName = "number+name";
  private String oldQuickQueryText;

  public GeneralF7TreeListUI()
    throws Exception
  {
    this.txtInput.setMaxLength(60);

    initCapitalSensitiveQuery();
  }

  private void initCapitalSensitiveQuery()
  {
    String capitalSensitiveQuery = ParamHelper.getCapitalSensitiveQuery();
    if ((capitalSensitiveQuery != null) && (capitalSensitiveQuery.equalsIgnoreCase("true")))
      this.isCapitalSensitiveQuery = true;
  }

  public Object getData()
  {
    Object data = null;
    Component selectedComponent = this.kdTMain.getSelectedComponent();
    if (selectedComponent == null) {
      return data;
    }
    if (selectedComponent.equals(this.adaptee))
    {
      if ((this.adaptee instanceof IF7Provider))
        return ((IF7Provider)this.adaptee).getData();
      if ((this.adaptee instanceof KDPromptSelector)) {
        return ((KDPromptSelector)this.adaptee).getData();
      }
      return null;
    }
    if (selectedComponent.equals(this.myCommonUseTable))
    {
      data = buildSelectedMyCommonUseData();
    }

    return data;
  }

  private Object buildSelectedMyCommonUseData()
  {
    ArrayList datas = new ArrayList();
    Object obj = KDPromptBox.DEFAULTVALUE;

    ArrayList queryValus = getSelectedValues();
    if (queryValus.size() > 0)
    {
      StringBuffer selectedValue = new StringBuffer();
      Iterator it = queryValus.iterator();
      while (it.hasNext()) {
        selectedValue.append(";").append(it.next().toString());
      }
      obj = this.promptBox.stringToValueForHistoryOrMyCommonUse(selectedValue.toString());
      if (KDPromptBox.DEFAULTVALUE.equals(obj))
      {
        int result = MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "19_GeneralF7TreeListUI"));
        if (result == 0)
        {
          deleteMyCommonUse();
        }
      }

      return obj;
    }
    return obj;
  }

  private SimpleQuery getQuery(String fullName)
    throws BOSException
  {
    QuerySelect select = new QuerySelect(fullName);
    select.mainEntitySelect();
    select.propertyUnitSelect().selectSelector(new String[] { "displayName", "returnType" }, SysContext.getSysContext().getOriginLocale()).extendPropertySelect().select(new String[] { "isVisibleForKDTable", "ColWidthInKDTable", "displayFormat", "isCommonQueryFilter" });

    select.extendPropertySelect().select(new String[] { "F7WindowTitle", "allFilterFieldName" });

    return MetaDataAccessFactory.getRemoteInstance().select(select);
  }

  public ArrayList getValueCollection(String queryField, ArrayList queryValues, SelectorItemCollection sic)
  {
    ArrayList resultArray = new ArrayList();
    try {
      IObjectCollection coll = null;

      EntityViewInfo evInfo = new EntityViewInfo();
      FilterInfo fInfo;
      if ((this.promptBox != null) && (this.promptBox.getEntityViewInfo() != null) && (this.promptBox.getEntityViewInfo().getFilter() != null))
      {
        fInfo = (FilterInfo)this.promptBox.getEntityViewInfo().getFilter().clone();
      }
      else fInfo = new FilterInfo();

      Set queryValueSet = new HashSet();
      queryValueSet.addAll(queryValues);
      fInfo.getFilterItems().add(new FilterItemInfo(queryField, queryValueSet, CompareType.INCLUDE));
      evInfo.setFilter(fInfo);
      evInfo.getSelector().addObjectCollection(sic);
      BOSObjectType type = getQuery(this.queryInfo.getFullName()).mainEntity().getType();

      coll = DynamicObjectFactory.getRemoteInstance().getCollection(type, evInfo);
      ArrayList result = new ArrayList();
      int i = 0; for (int count = coll.size(); i < count; i++) {
        IObjectValue ov = coll.getObject(i);
        result.add(ov);
      }
      resultArray.addAll(result);
      queryValues.clear();
      queryValues.addAll(result);
      return resultArray;
    } catch (BOSException e) {
      ExceptionHandler.handle(e);
    }return null;
  }

  public int getReturnValueType()
  {
    return this.returnValueType;
  }

  public void setReturnValueType(int returnValueType)
  {
    this.returnValueType = returnValueType;
  }

  public SelectorItemCollection getSelectorCollection()
  {
    return this.selectorCollection;
  }

  public void setSelectorCollection(SelectorItemCollection selectorCollection)
  {
    this.selectorCollection = selectorCollection;
  }

  protected ArrayList getSelectedValues()
  {
    ArrayList selectList = null;
    selectList = new ArrayList();
    List selectKeyIdFields = null;

    String keyFieldName = "number";
    selectList = ListUiHelper.getSelectedIdValues(this.myCommonUseTable, keyFieldName, selectList, selectKeyIdFields);

    return selectList;
  }

  /** @deprecated */
  public Object getData_BAK()
  {
    if ((this.adaptee instanceof IF7Provider))
      return ((IF7Provider)this.adaptee).getData();
    if ((this.adaptee instanceof KDPromptSelector)) {
      return ((KDPromptSelector)this.adaptee).getData();
    }
    return null;
  }

  public void loadFields()
  {
    super.loadFields();
  }

  public void storeFields()
  {
    super.storeFields();
  }

  protected void btnOK_actionPerformed(ActionEvent e)
    throws Exception
  {
    Component selectedComponent = this.kdTMain.getSelectedComponent();
    if (selectedComponent == null) {
      return;
    }
    int selectRow = -1;
    if (selectedComponent.equals(this.adaptee))
      selectRow = ((ListUI)this.adaptee).getMainTable().getSelectManager().getActiveRowIndex();
    else if (selectedComponent.equals(this.myCommonUseTable)) {
      selectRow = this.myCommonUseTable.getSelectManager().getActiveRowIndex();
    }

    if (selectRow < 0) {
      return;
    }

    this.retVal = 1;
    this.uiWindow.close();
  }

  protected void btnCancel_actionPerformed(ActionEvent e)
    throws Exception
  {
    this.retVal = 2;
    this.uiWindow.close();
  }

  public void clickCancelBtn() throws Exception {
    btnCancel_actionPerformed(new ActionEvent(this, 0, null));
  }

  public void clickOKBtn()
    throws Exception
  {
    btnOK_actionPerformed(new ActionEvent(this, 0, null));
  }

  public void onLoad()
    throws Exception
  {
    this.F7matchType = ParamHelper.getUserMatchType(null);

    this.adaptee = ((Component)getUIContext().get("InnerWindow"));
    this.filterDisplayName = ((String[])getUIContext().get("FilterDisplayName"));
    this.filterName = ((String[])getUIContext().get("FilterName"));
    this.value = ((String)getUIContext().get("value"));
    if ((getUIContext().get("promptBox") != null) && ((getUIContext().get("promptBox") instanceof KDBizPromptBox)))
    {
      this.promptBox = ((KDBizPromptBox)getUIContext().get("promptBox"));
    }
    if ((this.promptBox != null) && (this.promptBox.getCommitParser() != null)) {
      logger.info(new StringBuilder().append("CommitFormat:").append(this.promptBox.getCommitParser().toString()).toString());
      logger.info(new StringBuilder().append("Parser:").append(this.promptBox.getCommitParser().getClass().getName()).toString());
    }

    if (getUIContext().get("queryProperty") != null) {
      this.queryProperty = getUIContext().get("queryProperty").toString();
    }
    if (getUIContext().get("compareType") != null) {
      this.compareType = getUIContext().get("compareType").toString();
    }

    if ((this.adaptee instanceof F7MaterialTreeListUI))
    {
      Map map = new HashMap();
      map.put("FilterDisplayName", this.filterDisplayName);
      map.put("FilterName", this.filterName);
      Map rsMap = ((F7MaterialTreeListUI)this.adaptee).reSetfilterName(map);
      if ((rsMap != null) && (rsMap.get("FilterDisplayName") != null) && (rsMap.get("FilterName") != null)) {
        this.filterDisplayName = ((String[])rsMap.get("FilterDisplayName"));
        this.filterName = ((String[])rsMap.get("FilterName"));
      }

      this.isRequeryQuery = ((F7MaterialTreeListUI)this.adaptee).isRequeryQuery();
    }

    addAssisFunction();
    try {
      initInterface();
      initData();
      initComboCondition();

      appendFilterInfo();

      initF7UI();

      ((ListUI)this.adaptee).initUIContentLayout();
      ((ListUI)this.adaptee).onLoad();
      setUITitle(((ListUI)this.adaptee).getUITitle());
      if (this.isRequeryQuery) {
        this.btnQuery.setVisible(true);
        this.btnQuery.setEnabled(true);
      }

      if (this.tabbedPaneMainSwitchListener == null) {
        this.tabbedPaneMainSwitchListener = new TabbedPaneMainSwitchListener();
      }
      this.adaptee.addKeyListener(this.tabbedPaneMainSwitchListener);
    }
    catch (Exception er) {
      ExceptionHandler.handle(er);
      SysUtil.abort();
    }
  }

  protected void appendFilterInfo() {
    if ((this.promptBox.getCommitParser() instanceof IBaseDataParser)) {
      IBaseDataParser parser = (IBaseDataParser)this.promptBox.getCommitParser();

      if ((this.adaptee instanceof IF7Provider)) {
        FilterInfo customerFilterInfo = null;
        FilterInfo quickFilterInfo = null;
        if (getUIContext().get("CustomerFilterInfo") == null) {
          customerFilterInfo = null;
        } else {
          FilterInfo tempFilterInfo = (FilterInfo)getUIContext().get("CustomerFilterInfo");

          customerFilterInfo = (FilterInfo)tempFilterInfo.clone();
        }
        if (this.promptBox.getEntityViewInfo() != null)
        {
          if ((parser.isCommitByEnter()) && (this.promptBox.getEntityViewInfo() != null) && (this.value != null) && (!this.value.trim().equals("")))
          {
            this.txtInput.setText(this.value);
            quickFilterInfo = parser.getQuickFilter();

            setQuickQuery(true);
          }

        }

        ((IF7Provider)this.adaptee).setFilterInfo(customerFilterInfo, quickFilterInfo);
      }

      parser.setCommitByEnter(false);
      parser.setQuickFilter(null);
    }
    else
    {
      setFilterInfo();
    }
  }

  private void initF7UI()
    throws BOSException
  {
    this.queryInfoName = ((String)getUIContext().get("QueryName"));
    this.queryInfo = MetaDataPK.create(this.queryInfoName);

    initToolBar();

    initSimpleQuickQueryPanel();

    initKDTabMain();

    initResultStatusBar();
  }

  private void initResultStatusBar()
  {
    this.statusLable = new KDLabel();
    this.statusLable.setForeground(Color.BLUE);
    this.statusLable.setText(EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "20_GeneralF7TreeListUI"));
    this.statusLable.setVisible(false);
    this.statusLable.setBounds(new Rectangle(10, 479, 200, 21));
    add(this.statusLable, new KDLayout.Constraints(10, 479, 200, 21, 14));
  }

  private void initToolBar()
  {
    if ((this.promptBox == null) || (!this.promptBox.isMyCommonUseEnabled())) {
      return;
    }

    this.btnAddToMyCommon = new KDWorkButton();
    this.btnAddToMyCommon.setName("btnAddToMyCommon");
    this.btnDelete = new KDWorkButton();
    this.btnDelete.setName("btnDelete");

    this.btnDelete.setVisible(false);
    this.btnDelete.setEnabled(false);

    this.btnAddToMyCommon.setText(EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "21_GeneralF7TreeListUI"));
    this.btnDelete.setText(EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "22_GeneralF7TreeListUI"));

    this.btnAddToMyCommon.setIcon(EASResource.getIcon("imgTbtn_new"));
    this.btnDelete.setIcon(EASResource.getIcon("imgTbtn_delete"));

    this.toolBar.add(this.btnAddToMyCommon);
    this.toolBar.add(this.btnDelete);

    this.btnAddToMyCommon.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ((ListUI)GeneralF7TreeListUI.this.adaptee).checkSelected();

        List rows = GeneralF7TreeListUI.this.getSelectedRows();
        boolean isAddSuccessed = true;
        String id = null;
        String name = null;
        String number = null;
        for (int i = 0; i < rows.size(); i++) {
          IRow row = (IRow)rows.get(i);
          id = " ";
          name = " ";
          number = " ";

          if ((row.getCell("id") != null) && (row.getCell("id").getValue() != null))
          {
            id = row.getCell("id").getValue().toString();
          }
          if ((row.getCell("name") != null) && (row.getCell("name").getValue() != null))
          {
            name = row.getCell("name").getValue().toString();
          }
          if ((row.getCell("number") != null) && (row.getCell("number").getValue() != null))
          {
            number = row.getCell("number").getValue().toString();
          }

          if (StringUtils.isEmpty(number))
          {
            String resultMessage = GeneralF7TreeListUI.this.getMLS("4_KDCommonPromptDialog", "4_KDCommonPromptDialog");
            GeneralF7TreeListUI.ShowResultThread showResultThread = new ShowResultThread( resultMessage);
            showResultThread.start();
            return;
          }

          isAddSuccessed = GeneralF7TreeListUI.this.addToMyCommon(id, number, name);
          if (!isAddSuccessed)
          {
            break;
          }
        }
        GeneralF7TreeListUI.this.isNeedUpdateMyCommonUse = true;

        String resultMessage = EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "20_GeneralF7TreeListUI");
        GeneralF7TreeListUI.ShowResultThread showResultThread = new ShowResultThread(  resultMessage);
        showResultThread.start();
      }
    });
    this.btnDelete.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        GeneralF7TreeListUI.this.deleteMyCommonUse();
      }
    });
  }

  protected List getSelectedRows()
  {
    List rows = new ArrayList();
    KDTSelectBlock selectBlock = null;
    KDTable table = ((ListUI)this.adaptee).getMainTable();
    int size = table.getSelectManager().size();

    for (int i = 0; i < size; i++) {
      selectBlock = table.getSelectManager().get(i);
      for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
        IRow row = table.getRow(j);
        rows.add(row);
      }
    }
    return rows;
  }

  private boolean addToMyCommon(String id, String number, String name)
  {
    if ((StringUtils.isEmpty(number)) && (StringUtils.isEmpty(name))) {
      return true;
    }
    String editValue = null;

    editValue = new StringBuilder().append(id).append("\t").append(number).append("\t").append(name).toString();
    String fileName = null;
    String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
    if ((this.promptBox != null) && (!StringUtils.isEmpty(this.promptBox.getRecordName())))
      fileName = this.promptBox.getRecordName();
    else {
      fileName = new StringBuilder().append(userID).append(this.promptBox.getName()).toString();
    }
    fileName = fileName.replaceAll("\\\\|/|\\:|\\*|\"|>|<|\\|\\.| |-|'|=|(|)|", "");

    if (fileName.length() > 200) {
      fileName = fileName.substring(0, 200);
    }
    fileName = new StringBuilder().append(fileName).append("MyCommonUse").toString();

    File f = getMemorizeFile(fileName);
    String[] assistorCache = null;

    if (!f.exists())
    {
      try
      {
        f.createNewFile();
        FileOutputStream fom = new FileOutputStream(f);
        fom.write(editValue.getBytes("UTF-8"));
        fom.write(10);
        fom.close();

        return true;
      }
      catch (IOException e)
      {
        logger.error(e.getMessage(), e);
      }
    }
    else {
      try {
        FileInputStream fim = new FileInputStream(f);
        byte[] bytes = new byte[(int)f.length()];
        fim.read(bytes);
        fim.close();
        String str = new String(bytes, "UTF-8");
        assistorCache = str.split("\n");
        if (assistorCache == null) {
          assistorCache = new String[0];
        }

        for (int i = 0; i < assistorCache.length; i++) {
          if (assistorCache[i].equals(editValue)) {
            return true;
          }

        }

        if (assistorCache.length >= this.myCommonLimit)
        {
          MsgBox.showWarning(this, getMLS("10_KDCommonPromptDialog", "10_KDCommonPromptDialog"));

          return false;
        }

        String[] tempAssistorInformation = new String[assistorCache.length + 1];

        int insertIndex = -1;
        for (int i = 0; i < assistorCache.length; i++) {
          String assistorCacheTemp = assistorCache[i];

          String assistorCacheNumber = null;
          if (assistorCacheTemp.indexOf("\t") >= 0)
            assistorCacheNumber = assistorCacheTemp.substring(0, assistorCacheTemp.indexOf("\t"));
          else {
            assistorCacheNumber = assistorCacheTemp;
          }
          if (compareString(number, assistorCacheNumber)) {
            tempAssistorInformation[i] = assistorCache[i];
          } else {
            tempAssistorInformation[i] = editValue;
            insertIndex = i;
            break;
          }
        }

        if (insertIndex == -1)
        {
          tempAssistorInformation[assistorCache.length] = editValue;
        }
        else for (int i = insertIndex; i < assistorCache.length; i++) {
            tempAssistorInformation[(i + 1)] = assistorCache[i];
          }


        assistorCache = tempAssistorInformation;

        str = "";
        for (int i = 0; i < assistorCache.length; i++)
        {
          str = new StringBuilder().append(str).append(assistorCache[i]).append("\n").toString();
        }

        FileOutputStream fom = new FileOutputStream(f);
        fom.write(str.getBytes("UTF-8"));
        fom.write(10);
        fom.close();
      }
      catch (Exception e)
      {
        logger.error(e.getMessage(), e);
      }
    }

    return true;
  }

  private void deleteMyCommonUse()
  {
    checkMyCommonUseSelected();

    KDTSelectManager sm = this.myCommonUseTable.getSelectManager();
    int size = sm.size();

    if (size <= 0) {
      return;
    }

    for (int i = 0; i < size; i++) {
      IBlock sb = sm.get(i);

      int top = 0;
      int bottom = 0;

      if (sb.getMode() == 8)
      {
        bottom = this.myCommonUseTable.getRowCount3();
      } else {
        top = sb.getTop() >= 0 ? sb.getTop() : 0;
        bottom = sb.getBottom() >= 0 ? sb.getBottom() : 0;
      }
      for (int j = top; j <= bottom; j++) {
        IRow row = this.myCommonUseTable.getRow(j);
        if ((row != null) && (row.getCell("number") != null))
        {
          Object number = row.getCell("number").getValue();

          if (number != null)
          {
            deleteFromMyCommonUseCache(number.toString());
          }
        }
      }
    }

    saveMyCommonUse();

    fillMyCommonUseTable();

    String resultMessage = EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "23_GeneralF7TreeListUI");
    ShowResultThread showResultThread = new ShowResultThread(resultMessage);
    showResultThread.start();
  }

  public void checkMyCommonUseSelected()
  {
    if ((this.myCommonUseTable.getRowCount() == 0) || (this.myCommonUseTable.getSelectManager().size() == 0))
    {
      MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
      SysUtil.abort();
    }
  }

  private void deleteFromMyCommonUseCache(String number)
  {
    if ((this.myCommonUseCache == null) || (this.myCommonUseCache.length <= 0)) {
      return;
    }

    for (int i = 0; i < this.myCommonUseCache.length; i++) {
      if (this.myCommonUseCache[i].toUpperCase().indexOf(number.toUpperCase()) >= 0) {
        this.myCommonUseCache[i] = "";
        break;
      }
    }

    Iterator it = this.myCommonUseContent.iterator();
    while (it.hasNext()) {
      Object tempContent = it.next();
      if (tempContent.toString().toUpperCase().indexOf(number.toUpperCase()) >= 0) {
        this.myCommonUseContent.remove(tempContent);
        break;
      }
    }
  }

  private void saveMyCommonUse()
  {
    String fileName = null;
    String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();

    if ((this.promptBox != null) && (!StringUtils.isEmpty(this.promptBox.getRecordName())))
    {
      fileName = this.promptBox.getRecordName();
    }
    else {
      fileName = new StringBuilder().append(userID).append(this.queryInfoName).toString();
    }
    fileName = fileName.replaceAll("\\\\|/|\\:|\\*|\"|>|<|\\|\\.| |-|'|=|(|)|", "");

    if (fileName.length() > 200) {
      fileName = fileName.substring(0, 200);
    }
    fileName = new StringBuilder().append(fileName).append("MyCommonUse").toString();

    File f = getMemorizeFile(fileName);
    try
    {
      String str = "";
      for (int i = 0; i < this.myCommonUseCache.length; i++) {
        if ((this.myCommonUseCache[i] != null) && (this.myCommonUseCache[i].trim().length() > 0))
        {
          str = new StringBuilder().append(str).append(this.myCommonUseCache[i]).append("\n").toString();
        }
      }
      FileOutputStream fom = new FileOutputStream(f);
      fom.write(str.getBytes("UTF-8"));
      fom.write(10);
      fom.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private File getMemorizeFile(String fileName)
  {
    String fileSepa = System.getProperty("file.separator");
    File f7cacheDir = new File(new StringBuilder().append(System.getProperty("user.dir")).append(fileSepa).append("cache").append(fileSepa).append("F7Cache").toString());

    if (!f7cacheDir.exists()) {
      f7cacheDir.mkdir();
    }
    File f = new File(new StringBuilder().append(System.getProperty("user.dir")).append(fileSepa).append("cache").append(fileSepa).append("F7Cache").append(fileSepa).append(fileName == null ? "PublicCookie" : fileName).toString());

    return f;
  }

  private void initSimpleQuickQueryPanel()
    throws BOSException
  {
    remove(this.conditionPanel);

    this.simpleQuickFilterPanel = new KDPanel();
    this.simpleQuickFilterPanel.setName("simpleQuickFilterPanel");
    this.simpleQuickFilterPanel.setLayout(null);

    this.simpleQuickFilterPanel.setBounds(new Rectangle(0, 0, 676, 39));
    add(this.simpleQuickFilterPanel, new KDLayout.Constraints(0, 0, 676, 39, 5));

    addQuickQuery();
  }

  private void addQuickQuery()
    throws BOSException
  {
    this.txtQuickQueryInput = new KDTextField();
    this.txtQuickQueryInput.setName("txtQuickQueryInput");
    this.txtQuickQueryInput.setText(EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "24_GeneralF7TreeListUI"));
    this.txtQuickQueryInput.setMaxLength(60);

    if (!StringUtils.isEmpty(this.txtInput.getText())) {
      this.txtQuickQueryInput.setText(this.txtInput.getText());
    }
    this.txtQuickQueryInput.setBounds(new Rectangle(10, 10, 200, 19));
    this.simpleQuickFilterPanel.add(this.txtQuickQueryInput, new KDLayout.Constraints(10, 10, 200, 19, 384));

    this.icon_off = KDResourceManager.getImageOfRapid("prompt_off.gif");
    this.icon_disable = KDResourceManager.getImageOfRapid("prompt_disable.gif");

    KDWorkButton button = new KDWorkButton();
    button.setName("simpleQuickQuery");

    button.setBounds(new Rectangle(210, 10, 19, 19));
    button.setIcon(new ImageIcon(this.icon_off));

    button.setBackground(BGCOLOR);
    button.setDisabledIcon(new ImageIcon(this.icon_disable));

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GeneralF7TreeListUI.this.doQuickQuery();
      }
    });
    this.simpleQuickFilterPanel.add(button, new KDLayout.Constraints(210, 10, 19, 19, 384));

    this.quickQueryPopup = new KDPanel();

    this.quickQueryPopup.setMinimumSize(new Dimension(130, 100));
    this.quickQueryPopup.setLayout(new BorderLayout());
    this.quickQueryConditionList = new KDList();

    this.quickQueryPopup.add(this.quickQueryConditionList, "Center");

    this.quickQueryConditionList.setFocusable(false);

    this.quickQueryHandler = new QuickQueryHandler();
    this.txtQuickQueryInput.addKeyListener(this.quickQueryHandler);
    this.txtQuickQueryInput.addMouseListener(this.quickQueryHandler);
    this.txtQuickQueryInput.addFocusListener(this.quickQueryHandler);

    this.centralizedHandler = new CentralizedHandler();
    this.quickQueryConditionList.addMouseListener(this.centralizedHandler);
    this.quickQueryConditionList.addMouseMotionListener(this.centralizedHandler);

    CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this.txtQuickQueryInput, KeyStroke.getKeyStroke(10, 0));

    this.filterFields = getFilterFields();
  }

  private FilterFieldItem[] getFilterFields()
    throws BOSException
  {
    SimplePropertyUnitCollection si = getQuery(this.queryInfo.getFullName()).propertyUnits().getSelectors();

    ArrayList al = new ArrayList();
    int sizeOfSI = si.size();
    for (int i = 0; i < sizeOfSI; i++)
    {
      SimplePropertyUnit qfi = si.get(i);
      String theExtendPropertyValue = qfi.getExtendedProperty("isCommonQueryFilter");
      if ((theExtendPropertyValue != null) && (theExtendPropertyValue.equals("true")))
      {
        ConstDataType cdt = null;
        try
        {
          cdt = qfi.getReturnType();
        }
        catch (Exception e) {
          cdt = ConstDataType.STRING;
        }
        if ((!cdt.equals(ConstDataType.DATE)) && (!cdt.equals(ConstDataType.BOOLEAN)) && (!cdt.equals(ConstDataType.TIME)) && (!cdt.equals(ConstDataType.TIMESTAMP)) && (!qfi.isReturnTypeEnum()))
        {
          al.add(new FilterFieldItem(qfi.getName(), qfi.getDisplayName(), cdt));
        }
      }
    }

    int sizeOfAL = al.size();
    FilterFieldItem[] ffis = new FilterFieldItem[sizeOfAL];
    for (int i = 0; i < sizeOfAL; i++)
    {
      ffis[i] = ((FilterFieldItem)al.get(i));
    }

    if ((ffis.length == 0) && (this.isDefaultFilterFieldsEnabled)) {
      ffis = new FilterFieldItem[2];
      ffis[0] = new FilterFieldItem("number", getMLS("number", "Number"), ConstDataType.STRING);
      ffis[1] = new FilterFieldItem("name", getMLS("name", "Name"), ConstDataType.STRING);
    }
    ffis = addNumberNameCompFilter(ffis);

    this.allFilterFieldName = getQuery(this.queryInfo.getFullName()).getExtendedProperty("allFilterFieldName");

    if (this.allFilterFieldName != null) {
      String allFilterFieldDesc = getMLS("numbernamehelpcode", "number+name+helpcode");

      FilterFieldItem ffisall = new FilterFieldItem(this.allFilterFieldName, allFilterFieldDesc, ConstDataType.STRING);

      FilterFieldItem[] rtFf = new FilterFieldItem[ffis.length + 1];
      rtFf[0] = ffisall;
      System.arraycopy(ffis, 0, rtFf, 1, ffis.length);
      ffis = rtFf;
    }

    return ffis;
  }

  public void setIsDefaultFilterFieldsEnabled(boolean enabled)
  {
    this.isDefaultFilterFieldsEnabled = enabled;
  }

  private String getMLS(String key, String defaultValue)
  {
    return LanguageManager.getLangMessage(key, "com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog", SysContext.getSysContext().getOriginLocale(), defaultValue);
  }

  private Locale shortToLong(Locale local) {
    if ("l2".equals(local.toString().toLowerCase()))
      return Locale.SIMPLIFIED_CHINESE;
    if ("l3".equals(local.toString().toLowerCase()))
      return Locale.TRADITIONAL_CHINESE;
    if ("l1".equals(local.toString().toLowerCase())) {
      return Locale.ENGLISH;
    }
    logger.info("KDCommonPromptDialog.shortToLong ,illegal Locale!");
    return local;
  }

  private FilterFieldItem[] addNumberNameCompFilter(FilterFieldItem[] ff)
  {
    if (ff != null)
    {
      int count = 0;
      for (int i = 0; i < ff.length; i++)
      {
        if ((ff[i] != null) && ("number".equals(ff[i].getName())))
        {
          count++;
        } else if ((ff[i] != null) && ("name".equals(ff[i].getName())))
        {
          count++;
        }
      }
      if (count == 2)
      {
        FilterFieldItem ffis = new FilterFieldItem("number+name", new StringBuilder().append(getMLS("number", "Number")).append("+").append(getMLS("name", "Name")).toString(), ConstDataType.STRING);
        FilterFieldItem[] rtFf = new FilterFieldItem[ff.length + 1];
        rtFf[0] = ffis;
        System.arraycopy(ff, 0, rtFf, 1, ff.length);
        ff = rtFf;
      }
    }

    return ff;
  }

  private void initKDTabMain()
  {
    this.kdTMain = new KDTabbedPane();

    MouseListener[] mlArray = (MouseListener[])this.kdTMain.getListeners(MouseListener.class);
    if ((mlArray != null) && (mlArray.length > 0)) {
      this.kdTMain.removeMouseListener(mlArray[0]);
    }
    this.kdTMain.setName("kdTMain");
    this.kdTMain.setTabPlacement(3);

    this.kdTMain.setForcedHide(true);

    this.myCommonUseTable = new KDTable();

    this.kdTMain.add(this.adaptee, EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "25_GeneralF7TreeListUI"), 0);
    if ((this.promptBox != null) && (this.promptBox.isMyCommonUseEnabled())) {
      this.kdTMain.add(this.myCommonUseTable, EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "26_GeneralF7TreeListUI"), 1);
    }

    this.kdTMain.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        GeneralF7TreeListUI.this.kdTMain_stateChanged(e);
      }
    });
    this.treePanel.add(this.kdTMain, "Center");

    this.kdTMain.getInputMap(1).put(KeyStroke.getKeyStroke("ctrl Q"), "DataCenterFunction");
    this.kdTMain.getActionMap().put("DataCenterFunction", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        GeneralF7TreeListUI.this.txtQuickQueryInput.requestFocus(true);
      }
    });
    if (this.tabbedPaneMainSwitchListener == null) {
      this.tabbedPaneMainSwitchListener = new TabbedPaneMainSwitchListener();
    }
    this.kdTMain.addKeyListener(this.tabbedPaneMainSwitchListener);

    initMyCommonUseTable();

    initMyCommonUseControls();
  }

  private void initMyCommonUseTable()
  {
    String[] bindContents = new String[4];

    IColumn col = this.myCommonUseTable.addColumn();
    col.setKey("id");
    bindContents[0] = "id";
    col.setWidth(100);
    col.getStyleAttributes().setHided(true);

    col = this.myCommonUseTable.addColumn();
    col.setKey("number");
    bindContents[1] = "number";
    col.setWidth(100);

    col = this.myCommonUseTable.addColumn();
    col.setKey("name");
    bindContents[2] = "name";
    col.setWidth(100);

    this.myCommonUseTable.putBindContents("query", bindContents);

    this.myCommonUseTable.setFormatXml("");
    this.myCommonUseTable.checkParsed(true);

    IRow row = this.myCommonUseTable.addHeadRow();

    row.getCell(1).setValue(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.F8ColumnNumber"));
    row.getCell(2).setValue(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.F8ColumnName"));

    int x = -82;
    int y = -29;
    int width = 73;
    int height = 21;

    height = y - 20;
    width = -19;
    x = 10;
    y = 10;
    KDLayout.Constraints cons = new KDLayout.Constraints();
    cons.originalBounds = new Rectangle(x, y, width, height);
    cons.anchor = 15;

    this.myCommonUseTable.putClientProperty("KDLayoutConstraints", cons);

    if (this.tabbedPaneMainSwitchListener == null) {
      this.tabbedPaneMainSwitchListener = new TabbedPaneMainSwitchListener();
    }
    this.myCommonUseTable.addKeyListener(this.tabbedPaneMainSwitchListener);
  }

  private void initMyCommonUseControls()
  {
    KDTableHelper.releaseEnterAndTab(this.myCommonUseTable);
    KDTableHelper.releaseEsc(this.myCommonUseTable);

    CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this.myCommonUseTable, KeyStroke.getKeyStroke(10, 0));

    this.myCommonUseTable.getSelectManager().setSelectMode(10);

    this.myCommonUseTable.getStyleAttributes().setLocked(true);

    this.myCommonUseTable.addKDTMouseListener(new KDTMouseListener() {
      public void tableClicked(KDTMouseEvent e) {
        if ((e.getClickCount() == 2) && (e.getType() == 1))
          try {
            GeneralF7TreeListUI.this.btnOK_actionPerformed(null);
          }
          catch (Exception e1) {
            e1.printStackTrace();
          }
      }
    });
    this.myCommonUseTable.addRequestRowSetListener(new RequestRowSetListener()
    {
      public void doRequestRowSet(RequestRowSetEvent e)
      {
      }
    });
    this.myCommonUseTable.addKeyListener(new KeyListener()
    {
      public void keyPressed(KeyEvent e)
      {
        if (e.getKeyCode() == 127)
          GeneralF7TreeListUI.this.deleteMyCommonUse();
        else if (e.getKeyCode() == 10)
          try {
            GeneralF7TreeListUI.this.btnOK_actionPerformed(null);
          }
          catch (Exception e1) {
            e1.printStackTrace();
          }
      }

      public void keyReleased(KeyEvent arg0)
      {
      }

      public void keyTyped(KeyEvent arg0)
      {
      }
    });
  }

  private void kdTMain_stateChanged(ChangeEvent e)
  {
    Component selectedComponent = this.kdTMain.getSelectedComponent();
    if (selectedComponent == null) {
      return;
    }
    if (selectedComponent.equals(this.adaptee)) {
      if (this.isRequeryQuery) {
        this.btnQuery.setVisible(true);
        this.btnQuery.setEnabled(true);
      }
      this.btnRefresh.setVisible(true);
      this.btnRefresh.setEnabled(true);
      this.btnMaintenance.setVisible(true);
      this.btnMaintenance.setEnabled(true);

      this.btnAddToMyCommon.setVisible(true);
      this.btnAddToMyCommon.setEnabled(true);
      this.btnDelete.setVisible(false);
      this.btnDelete.setEnabled(false);
    }
    else if (selectedComponent.equals(this.myCommonUseTable)) {
      this.btnQuery.setVisible(false);
      this.btnQuery.setEnabled(false);
      this.btnRefresh.setVisible(false);
      this.btnRefresh.setEnabled(false);
      this.btnMaintenance.setVisible(false);
      this.btnMaintenance.setEnabled(false);

      this.btnAddToMyCommon.setVisible(false);
      this.btnAddToMyCommon.setEnabled(false);
      this.btnDelete.setVisible(true);
      this.btnDelete.setEnabled(true);

      if (this.isNeedUpdateMyCommonUse)
      {
        getMyCommonUseContent();

        fillMyCommonUseTable();

        this.isNeedUpdateMyCommonUse = false;
      }

      this.myCommonUseContent.clear();
      for (int i = 0; (this.myCommonUseCache != null) && (i < this.myCommonUseCache.length); i++) {
        if ((EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "24_GeneralF7TreeListUI").equals(this.txtQuickQueryInput.getText())) || (this.myCommonUseCache[i].toUpperCase().indexOf(this.txtQuickQueryInput.getText().toUpperCase()) >= 0))
        {
          this.myCommonUseContent.add(this.myCommonUseCache[i]);
        }

      }

      fillMyCommonUseTable();

      if (this.popup != null)
        this.popup.hide();
    }
  }

  private boolean getMyCommonUseContent()
  {
    try
    {
      String fileName = null;
      String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();

      if ((this.promptBox != null) && (!StringUtils.isEmpty(this.promptBox.getRecordName())))
      {
        fileName = this.promptBox.getRecordName();
      }
      else {
        fileName = new StringBuilder().append(userID).append(this.queryInfoName).toString();
      }
      fileName = fileName.replaceAll("\\\\|/|\\:|\\*|\"|>|<|\\|\\.| |-|'|=|(|)|", "");

      if (fileName.length() > 200) {
        fileName = fileName.substring(0, 200);
      }
      fileName = new StringBuilder().append(fileName).append("MyCommonUse").toString();

      File f = getMemorizeFile(fileName);

      if (!f.exists()) {
        return false;
      }

      FileInputStream fim = new FileInputStream(f);
      byte[] bytes = new byte[(int)f.length()];
      fim.read(bytes);
      fim.close();
      String str = new String(bytes, "UTF-8");

      this.myCommonUseCache = str.split("\n");

      this.myCommonUseContent.clear();
      for (int i = 0; i < this.myCommonUseCache.length; i++) {
        this.myCommonUseContent.add(this.myCommonUseCache[i]);
      }

      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

  private void initComboCondition()
    throws BOSException, EASBizException
  {
    if ((this.adaptee instanceof F7CustomerTreeDetailListUI)) {
      CSOptionInfo cSOptionInfo = CSUtils.getCSOptionInfo(CSType.CUSTOMER);
      if (cSOptionInfo != null) {
        if (cSOptionInfo.isCsNumber()) {
          this.comboCondition.setSelectedIndex(0);
        }
        else if (cSOptionInfo.isCsName()) {
          this.comboCondition.setSelectedIndex(1);
        }
        else if (cSOptionInfo.isCsAssistNumber()) {
          this.comboCondition.setSelectedIndex(3);
        }
        else
        {
          this.comboCondition.setSelectedIndex(2);
        }
      }
    }
    if ((this.adaptee instanceof F7SupplierTreeDetailListUI)) {
      CSOptionInfo cSOptionInfo = CSUtils.getCSOptionInfo(CSType.SUPPLIER);
      if (cSOptionInfo != null) {
        if (cSOptionInfo.isCsNumber()) {
          this.comboCondition.setSelectedIndex(0);
        }
        else if (cSOptionInfo.isCsName()) {
          this.comboCondition.setSelectedIndex(1);
        }
        else if (cSOptionInfo.isCsAssistNumber()) {
          this.comboCondition.setSelectedIndex(2);
        }
      }
    }

    if ((this.adaptee instanceof F7MaterialTreeListUI)) {
      UserParamaterInfo paramInfo = MaterialUtil.getUserParamaterInfo("defaultF7Field");

      this.comboCondition.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.master.material.MaterialSelectEnum").toArray());
      if (paramInfo != null) {
        if ("number".equals(paramInfo.getValue())) {
          this.comboCondition.setSelectedIndex(0);
        }
        if ("name".equals(paramInfo.getValue())) {
          this.comboCondition.setSelectedIndex(1);
        }
        if ("model".equals(paramInfo.getValue())) {
          this.comboCondition.setSelectedIndex(2);
        }
        if ("helpCode".equals(paramInfo.getValue())) {
          this.comboCondition.setSelectedIndex(3);
        }
        if ("baseUnit".equals(paramInfo.getValue())) {
          this.comboCondition.setSelectedIndex(4);
        }
        if ("nameAndModel".equals(paramInfo.getValue()))
        {
          this.comboCondition.setSelectedItem(MaterialSelectEnum.NAMEANDMODEL);
        }
        if ("numberAndNameAndHelpCode".equals(paramInfo.getValue())) {
          this.comboCondition.setSelectedItem(MaterialSelectEnum.NUMBERANDNAMEANDHELPCODE);
        }
        if ("numberAndName".equals(paramInfo.getValue()))
        {
          this.comboCondition.setSelectedItem(MaterialSelectEnum.NUMBERANDNAME);
        }
      }
    }
  }

  public int getRetVal()
  {
    return this.retVal;
  }

  private void initInterface()
  {
    this.btnRefresh.setEnabled(true);
    this.btnQuery.setEnabled(true);
    this.btnMaintenance.setEnabled(true);
    this.btnQuickQuery.setEnabled(true);
    this.btnQuery.setVisible(false);
    this.kDSelect.setEnabled(true);
    this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
    this.btnQuery.setIcon(EASResource.getIcon("imgTbtn_filter"));
    this.btnMaintenance.setIcon(EASResource.getIcon("imgTbtn_maintenance"));
    this.btnQuickQuery.setIcon(EASResource.getIcon("imgTbtn_filter"));
    this.txtInput.setText(null);
    this.kDSelect.setToolTipText(null);
    this.btnQuickQuery.setToolTipText(null);

    adjustFocusPolicy();

    this.txtInput.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try {
          GeneralF7TreeListUI.this.actionQuickQuery_actionPerformed(e);
        }
        catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    if (((getUIContext().get("Owner") instanceof Component)) && 
      ((getUIContext().get("Owner") instanceof CoreUIObject)))
      if (((((CoreUIObject)getUIContext().get("Owner")).getOprtState() != null) && (((CoreUIObject)getUIContext().get("Owner")).getOprtState().equals(OprtState.VIEW))) || ((((getUIContext().get("Owner") instanceof CustomerQueryPanel)) || (((Boolean)getUIContext().get("isShowUnUsed")).booleanValue())) && (getUIContext().get("QueryName").toString().toLowerCase().indexOf(".master.cssp.") >= 0)))
      {
        this.chkIncludeUnUsed.setVisible(true);
        this.chkIncludeUnUsed.setEnabled(true);
        this.chkIncludeUnUsed.setSelected(true);
      } else {
        this.chkIncludeUnUsed.setSelected(false);
      }
  }

  private void adjustFocusPolicy()
  {
    Set forwardKeySet = new HashSet();
    forwardKeySet.add(KeyStroke.getKeyStroke(9, 0));
    this.txtInput.setFocusTraversalKeys(0, forwardKeySet);
  }

  public void actionIncludeUnUsed_actionPerformed(ActionEvent e)
    throws Exception
  {
    ((ListUI)this.adaptee).refreshList();
  }

  private void initData()
  {
    if (this.filterDisplayName != null) {
      this.comboCondition.removeAllItems();
      this.comboCondition.addItems(this.filterDisplayName);
    } else {
      this.filterName = new String[] { "number", "name" };
    }
  }

  private void setFilterInfo()
  {
    FilterInfo customerFilterInfo = null;
    if (getUIContext().get("CustomerFilterInfo") == null) {
      customerFilterInfo = null;
    } else {
      customerFilterInfo = new FilterInfo();
      FilterInfo tempFilterInfo = (FilterInfo)getUIContext().get("CustomerFilterInfo");

      int i = 0; for (int c = tempFilterInfo.getFilterItems().size(); i < c; i++) {
        customerFilterInfo.getFilterItems().add(tempFilterInfo.getFilterItems().get(i));
      }

      if ((tempFilterInfo.getMaskString() != null) && (!tempFilterInfo.getMaskString().equals("")))
      {
        customerFilterInfo.setMaskString(tempFilterInfo.getMaskString());
      }

    }

    FilterInfo quickFilterInfo = null;

    if ((this.value != null) && (!this.value.trim().equals(""))) {
      this.txtInput.setText(this.value);
    }
    boolean capitalSensitiveQuery = Boolean.parseBoolean(ParamHelper.getCapitalSensitiveQuery());

    if ((!this.txtInput.getText().trim().equals("")) && (this.kDSelect.isSelected())) {
      String compareValue = this.txtInput.getText().trim();
      if ((compareValue.indexOf("%") == -1) && (compareValue.indexOf("_") == -1)) {
        compareValue = ParamHelper.getMatchString(compareValue);
      }
      boolean isTextIndexUsed = isTextIndexUsed(this.adaptee, compareValue);
      quickFilterInfo = new FilterInfo();
      if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NAMEANDMODEL)))
      {
        FilterItemInfo filterItem1 = null;
        FilterItemInfo filterItem2 = null;
        if (capitalSensitiveQuery)
        {
          filterItem1 = new FilterItemInfo("name", compareValue, CompareType.LIKE);

          filterItem2 = new FilterItemInfo("model", compareValue, CompareType.LIKE);
        }
        else {
          filterItem1 = generateFilterItem("name", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem2 = generateFilterItem("model", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAMEANDHELPCODE)))
      {
        FilterItemInfo filterItem1 = null;
        FilterItemInfo filterItem2 = null;
        FilterItemInfo filterItem3 = null;
        if (capitalSensitiveQuery)
        {
          filterItem1 = new FilterItemInfo("number", compareValue, CompareType.LIKE);

          filterItem2 = new FilterItemInfo("name", compareValue, CompareType.LIKE);

          filterItem3 = new FilterItemInfo("helpCode", compareValue, CompareType.LIKE);
        }
        else {
          filterItem1 = generateFilterItem("number", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem2 = generateFilterItem("name", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem3 = generateFilterItem("helpCode", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }
        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.getFilterItems().add(filterItem3);
        quickFilterInfo.setMaskString("#0 or #1 or #2");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAME)))
      {
        FilterItemInfo filterItem1 = null;
        FilterItemInfo filterItem2 = null;
        if (capitalSensitiveQuery)
        {
          filterItem1 = new FilterItemInfo("number", compareValue, CompareType.LIKE);

          filterItem2 = new FilterItemInfo("name", compareValue, CompareType.LIKE);
        }
        else {
          filterItem1 = generateFilterItem("number", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem2 = generateFilterItem("name", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }
        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      }
      else {
        FilterItemInfo filterItem = null;
        if ((capitalSensitiveQuery) || (isTextIndexUsed)) {
          filterItem = new FilterItemInfo(this.filterName[this.comboCondition.getSelectedIndex()], compareValue, CompareType.LIKE);
        }
        else
        {
          filterItem = generateFilterItem(this.filterName[this.comboCondition.getSelectedIndex()], "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }
        quickFilterInfo.getFilterItems().add(filterItem);
      }

    }

    if ((!this.txtInput.getText().trim().equals("")) && (!this.kDSelect.isSelected())) {
      quickFilterInfo = new FilterInfo();
      if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NAMEANDMODEL)))
      {
        FilterItemInfo filterItem1 = new FilterItemInfo("name", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem2 = new FilterItemInfo("model", this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAMEANDHELPCODE)))
      {
        quickFilterInfo = new FilterInfo();

        FilterItemInfo filterItem1 = new FilterItemInfo("number", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem2 = new FilterItemInfo("name", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem3 = new FilterItemInfo("helpCode", this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.getFilterItems().add(filterItem3);
        quickFilterInfo.setMaskString("#0 or #1 or #2");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAME)))
      {
        quickFilterInfo = new FilterInfo();

        FilterItemInfo filterItem1 = new FilterItemInfo("number", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem2 = new FilterItemInfo("name", this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      }
      else
      {
        FilterItemInfo filterItem = new FilterItemInfo(this.filterName[this.comboCondition.getSelectedIndex()], this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem);
      }

    }

    if ((this.value != null) && (!"".equals(this.value)))
      setQuickQuery(true);
    this.value = "";
    if ((this.adaptee instanceof IF7Provider))
      ((IF7Provider)this.adaptee).setFilterInfo(customerFilterInfo, quickFilterInfo);
  }

  public FilterInfo getQuickFilter()
  {
    return this.quickQueryFilter;
  }

  /** @deprecated */
  public FilterInfo getQuickFilter_BAK()
  {
    FilterInfo quickFilterInfo = null;

    if ((this.value != null) && (!this.value.trim().equals(""))) {
      this.txtInput.setText(this.value);
    }
    boolean capitalSensitiveQuery = Boolean.parseBoolean(ParamHelper.getCapitalSensitiveQuery());

    if ((!this.txtInput.getText().trim().equals("")) && (this.kDSelect.isSelected())) {
      String compareValue = this.txtInput.getText().trim();
      if ((compareValue.indexOf("%") == -1) && (compareValue.indexOf("_") == -1)) {
        compareValue = ParamHelper.getMatchString(compareValue);
      }
      boolean isTextIndexUsed = isTextIndexUsed(this.adaptee, compareValue);
      quickFilterInfo = new FilterInfo();
      if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NAMEANDMODEL)))
      {
        FilterItemInfo filterItem1 = null;
        FilterItemInfo filterItem2 = null;
        if (capitalSensitiveQuery)
        {
          filterItem1 = new FilterItemInfo("name", compareValue, CompareType.LIKE);

          filterItem2 = new FilterItemInfo("model", compareValue, CompareType.LIKE);
        }
        else {
          filterItem1 = generateFilterItem("name", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem2 = generateFilterItem("model", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAMEANDHELPCODE)))
      {
        FilterItemInfo filterItem1 = null;
        FilterItemInfo filterItem2 = null;
        FilterItemInfo filterItem3 = null;
        if (capitalSensitiveQuery)
        {
          filterItem1 = new FilterItemInfo("number", compareValue, CompareType.LIKE);

          filterItem2 = new FilterItemInfo("name", compareValue, CompareType.LIKE);

          filterItem3 = new FilterItemInfo("helpCode", compareValue, CompareType.LIKE);
        }
        else {
          filterItem1 = generateFilterItem("number", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem2 = generateFilterItem("name", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem3 = generateFilterItem("helpCode", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }
        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.getFilterItems().add(filterItem3);
        quickFilterInfo.setMaskString("#0 or #1 or #2");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAME)))
      {
        FilterItemInfo filterItem1 = null;
        FilterItemInfo filterItem2 = null;
        if (capitalSensitiveQuery)
        {
          filterItem1 = new FilterItemInfo("number", compareValue, CompareType.LIKE);

          filterItem2 = new FilterItemInfo("name", compareValue, CompareType.LIKE);
        }
        else {
          filterItem1 = generateFilterItem("number", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
          filterItem2 = generateFilterItem("name", "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }
        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      }
      else {
        FilterItemInfo filterItem = null;
        if ((capitalSensitiveQuery) || (isTextIndexUsed)) {
          filterItem = new FilterItemInfo(this.filterName[this.comboCondition.getSelectedIndex()], compareValue, CompareType.LIKE);
        }
        else
        {
          filterItem = generateFilterItem(this.filterName[this.comboCondition.getSelectedIndex()], "upper(&key)", "like", new StringBuilder().append("'").append(compareValue.toUpperCase()).append("'").toString());
        }
        quickFilterInfo.getFilterItems().add(filterItem);
      }

    }

    if ((!this.txtInput.getText().trim().equals("")) && (!this.kDSelect.isSelected())) {
      quickFilterInfo = new FilterInfo();
      if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NAMEANDMODEL)))
      {
        FilterItemInfo filterItem1 = new FilterItemInfo("name", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem2 = new FilterItemInfo("model", this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAMEANDHELPCODE)))
      {
        quickFilterInfo = new FilterInfo();

        FilterItemInfo filterItem1 = new FilterItemInfo("number", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem2 = new FilterItemInfo("name", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem3 = new FilterItemInfo("helpCode", this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.getFilterItems().add(filterItem3);
        quickFilterInfo.setMaskString("#0 or #1 or #2");
      } else if (this.comboCondition.getShowSelectedItem().toString().trim().equalsIgnoreCase(String.valueOf(MaterialSelectEnum.NUMBERANDNAME)))
      {
        quickFilterInfo = new FilterInfo();

        FilterItemInfo filterItem1 = new FilterItemInfo("number", this.txtInput.getText().trim(), CompareType.EQUALS);

        FilterItemInfo filterItem2 = new FilterItemInfo("name", this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem1);
        quickFilterInfo.getFilterItems().add(filterItem2);
        quickFilterInfo.setMaskString("#0 or #1");
      }
      else
      {
        FilterItemInfo filterItem = new FilterItemInfo(this.filterName[this.comboCondition.getSelectedIndex()], this.txtInput.getText().trim(), CompareType.EQUALS);

        quickFilterInfo.getFilterItems().add(filterItem);
      }
    }

    if ((this.value != null) && (!"".equals(this.value)))
      setQuickQuery(true);
    this.value = "";
    return quickFilterInfo;
  }

  public void actionMaintenance_actionPerformed(ActionEvent e)
    throws Exception
  {
    IUIFactory uiFactory = null;
    try
    {
      UIContext uic = new UIContext(this);
      uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UINewFrameFactory");
      IUIWindow ui;
      if ((this.adaptee instanceof F7CustomerTreeDetailListUI)) {
        ui = uiFactory.create("com.kingdee.eas.basedata.master.cssp.client.CustomerInfoListUI", uic);
      }
      else
      {
        if ((this.adaptee instanceof F7SupplierTreeDetailListUI)) {
          ui = uiFactory.create("com.kingdee.eas.basedata.master.cssp.client.SupplierListUI", uic);
        }
        else
        {
          ui = uiFactory.create(((ListUI)this.adaptee).getClass().getSuperclass().getName(), uic);
        }
      }
      ((JPanel)((JDialog)ui).getContentPane()).setPreferredSize(new Dimension(800, 500));
      ui.show();
    } catch (BOSException ex) {
      handUIException(ex);
    }
  }

  public void actionQuery_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.isRequeryQuery)
      ((ListUI)this.adaptee).actionQuery_actionPerformed(e);
  }

  public void actionQuickQuery_actionPerformed(ActionEvent e)
    throws Exception
  {
    setFilterInfo();
    this.isQuickQuery = true;
    try
    {
      Method m = this.adaptee.getClass().getMethod("execQuery", new Class[0]);
      m.invoke(this.adaptee, new Object[0]);
    } catch (NoSuchMethodException ee) {
      if (((ListUI)this.adaptee).getMainTable() != null)
        ((ListUI)this.adaptee).getMainTable().removeRows();
    }
  }

  public void actionRefresh_actionPerformed(ActionEvent e)
    throws Exception
  {
    doQuickQuery();
  }

  public void setSearchValue(String value)
  {
  }

  protected boolean checkBeforeWindowClosing()
  {
    if (this.retVal != 1) {
      this.retVal = 2;
    }
    return super.checkBeforeWindowClosing();
  }

  public void setSearchProperty(String property, String compareType) {
    this.queryProperty = property;
    this.compareType = compareType;
  }

  public boolean getChkIncludeUnUsedValue() {
    return (this.chkIncludeUnUsed.isSelected()) && (this.chkIncludeUnUsed.isVisible());
  }

  public boolean isQuickQuery() {
    if (this.value.equals("")) {
      return !this.txtInput.getText().trim().equals("");
    }
    return true;
  }

  public boolean getQuickQuery()
  {
    return this.isQuickQuery;
  }

  public void setQuickQuery(boolean isQuickQuery) {
    this.isQuickQuery = isQuickQuery;
  }

  public void setFocus()
  {
    this.txtQuickQueryInput.requestFocus(true);
  }

  private void addAssisFunction() {
    getInputMap(1).put(KeyStroke.getKeyStroke("shift ctrl I"), "DataCenterFunction");
    getActionMap().put("DataCenterFunction", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        try {
          ListUI listui = (ListUI)GeneralF7TreeListUI.this.adaptee;
          StringBuffer msg = new StringBuffer();
          msg.append("queryInfo:").append(listui.getMainQueryPK());
          if ((listui instanceof F7MaterialTreeListUI)) {
            F7MaterialTreeListUI ui = (F7MaterialTreeListUI)listui;
            msg.append("\n\n\n").append(ui.getQueryExecutor().getSQL());
          } else if ((listui instanceof F7CSSPTreeListUI)) {
            F7CSSPTreeListUI ui = (F7CSSPTreeListUI)listui;
            msg.append("\n\n\n").append(ui.getQueryExecutor().getSQL());
          }
          MsgBox.showDetailAndOK(null, EASResource.getString("com.kingdee.eas.scm.common.COMMONAutoGenerateResource", "10_GeneralF7TreeListUI"), msg.toString(), 1);
        } catch (Exception ex) {
        }
      }
    });
  }

  public String getSelectItem() {
    return this.comboCondition.getSelectedItem().toString();
  }

  protected FilterItemInfo generateFilterItem(String key, String functionExp, String compareType, String value) {
    StringBuffer sb = new StringBuffer(100);
    sb.append(functionExp.replace("&key", key));
    sb.append(compareType);
    sb.append(" ").append(value);
    return new FilterItemInfo(sb.toString());
  }

  protected boolean isTextIndexUsed(Component adaptee, String compareValue) {
    if (!(adaptee instanceof F7MaterialTreeListUI))
      return false;
    if ((Boolean.parseBoolean(ParamHelper.getFullTextIndexEnabled())) && (isItemSupportTextIndex()) && (compareValue.indexOf("%") == 0))
    {
      return true;
    }return false;
  }

  public boolean isItemSupportTextIndex()
  {
    String filterItem = getSelectItem();
    if ((EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "27_GeneralF7TreeListUI").equals(filterItem)) || (EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "28_GeneralF7TreeListUI").equals(filterItem)))
      return true;
    return false;
  }

  private void gennerateQuickQueryCondition()
  {
    this.quickQueryConditionList.removeAllElements();
    String inputText = this.txtQuickQueryInput.getText();
    if ((inputText == null) || (inputText.trim().length() <= 0))
    {
      return;
    }

    if ((this.filterFields == null) || (this.filterFields.length <= 0)) {
      return;
    }
    this.quickQueryContent = new ArrayList();

    String s = EASResource.getString("com.kingdee.eas.basedata.framework.DataBaseDResources", "INCLUDE");
    MessageFormat mf = new MessageFormat(s);
    for (int i = 0; i < this.filterFields.length; i++) {
      FilterFieldItem fiterFieldItem = this.filterFields[i];

      this.quickQueryContent.add(new StringBuilder().append(mf.format(new String[] { fiterFieldItem.toString() })).append(" ").append(inputText).append("  ").toString());
    }

    this.quickQueryConditionList.setListData(this.quickQueryContent.toArray());
    this.quickQueryConditionList.setSelectedIndex(0);
  }

  private void showQuickQueryPanle()
  {
    if (this.popup != null) {
      this.popup.hide();
    }
    String inputText = this.txtQuickQueryInput.getText();
    if ((inputText == null) || (inputText.trim().length() <= 0))
    {
      return;
    }
    Point parentLocationOnScreen = this.txtQuickQueryInput.getLocationOnScreen();
    Point p = new Point(parentLocationOnScreen.x, parentLocationOnScreen.y + this.txtQuickQueryInput.getHeight());

    this.popup = PopupFactory.getSharedInstance().getPopup(this.txtQuickQueryInput, this.quickQueryPopup, p.x, p.y);
    this.popup.show();
  }

  private void doQuickQuery()
  {
    String inputText = this.txtQuickQueryInput.getText();

    this.oldQuickQueryText = inputText;

    IMainUIContainer ui = SystemEntry.instance.getCurrenContainer();
    NewMainFrame mainFrame = null;

    if ((ui != null) && ((ui instanceof NewMainFrame))) {
      mainFrame = (NewMainFrame)ui;
      if (mainFrame != null) {
        mainFrame.setCursor(Cursor.getPredefinedCursor(3));
      }
    }

    try
    {
      if (!StringUtils.isEmpty(inputText))
      {
        String conditionValue = new StringBuilder().append("%").append(inputText.trim()).append("%").toString();
        this.quickQueryFilter = new FilterInfo();
        String maskString = "#0";

        FilterFieldItem filterFieldItem = this.filterFields[0];
        if ((this.quickQueryConditionList != null) && (this.quickQueryConditionList.getSelectedValue() != null)) {
          filterFieldItem = this.filterFields[this.quickQueryConditionList.getSelectedIndex()];
        }

        String[] fieldArray = filterFieldItem.getName().split("[+]");
        for (int j = 0; j < fieldArray.length; j++) {
          if (this.isCapitalSensitiveQuery) {
            this.quickQueryFilter.getFilterItems().add(new FilterItemInfo(fieldArray[j], conditionValue, CompareType.LIKE));
          }
          else
          {
            FilterItemInfo item = null;
            String oql = new StringBuilder().append("upper(").append(fieldArray[j]).append(") like '").append(conditionValue.toUpperCase()).append("'").toString();

            item = new FilterItemInfo(oql);
            this.quickQueryFilter.getFilterItems().add(item);
          }
          if (j > 0) {
            maskString = new StringBuilder().append(maskString).append(" or #").append(j).toString();
          }

        }

        this.quickQueryFilter.setMaskString(maskString);
      } else {
        this.quickQueryFilter = new FilterInfo();
      }

      if (this.popup != null) {
        this.popup.hide();
      }

      FilterInfo customerFilterInfo = null;
      if (getUIContext().get("CustomerFilterInfo") == null) {
        customerFilterInfo = null;
      } else {
        customerFilterInfo = new FilterInfo();
        FilterInfo tempFilterInfo = (FilterInfo)getUIContext().get("CustomerFilterInfo");

        int i = 0; for (int c = tempFilterInfo.getFilterItems().size(); i < c; i++) {
          customerFilterInfo.getFilterItems().add(tempFilterInfo.getFilterItems().get(i));
        }

        if ((tempFilterInfo.getMaskString() != null) && (!tempFilterInfo.getMaskString().equals("")))
        {
          customerFilterInfo.setMaskString(tempFilterInfo.getMaskString());
        }

      }

      if ((this.adaptee instanceof IF7Provider)) {
        ((IF7Provider)this.adaptee).setFilterInfo(customerFilterInfo, this.quickQueryFilter);
      }

      this.isQuickQuery = true;
      try
      {
        Method m = this.adaptee.getClass().getMethod("execQuery", new Class[0]);
        m.invoke(this.adaptee, new Object[0]);
      } catch (Exception e) {
        ExceptionHandler.handle(e);
        if (((ListUI)this.adaptee).getMainTable() != null) {
          ((ListUI)this.adaptee).getMainTable().removeRows();
        }

      }

      ((ListUI)this.adaptee).getMainTable().requestFocusInWindow();

      int rowCount = ((ListUI)this.adaptee).getMainTable().getRowCount();
      if (rowCount > 0)
        ((ListUI)this.adaptee).getMainTable().getSelectManager().select(0, -1);
    }
    finally
    {
      if (mainFrame != null)
        mainFrame.setCursor(Cursor.getDefaultCursor());
    }
  }

  private void fillMyCommonUseTable()
  {
    this.myCommonUseTable.removeRows();
    if ((this.myCommonUseContent == null) || (this.myCommonUseContent.size() == 0)) {
      return;
    }
    Iterator it = this.myCommonUseContent.iterator();
    String content = null;
    String id = null;
    String number = null;
    String name = null;
    IRow row = null;
    String[] contentArray = null;
    while ((it != null) && (it.hasNext())) {
      content = (String)it.next();
      if ((content != null) && (content.trim().length() > 0))
      {
        if (content.indexOf("\t") >= 0)
        {
          contentArray = content.split("\t");
          if (contentArray.length == 3) {
            id = contentArray[0];
            number = contentArray[1];
            name = contentArray[2];
          } else if (contentArray.length == 2) {
            id = contentArray[0];
            number = contentArray[1];
            name = "";
          } else {
            id = contentArray[0];
            number = "";
            name = "";
          }
        } else {
          number = content;
        }

        row = this.myCommonUseTable.addRow();
        row.getCell("id").setValue(id);
        row.getCell("number").setValue(number);
        row.getCell("name").setValue(name);
      }
    }
    if (this.myCommonUseTable.getRow(0) != null)
    {
      int columnCount = this.myCommonUseTable.getColumnCount();
      for (int i = 0; i < columnCount; i++) {
        KDTableHelper.autoFitColumnWidth(this.myCommonUseTable, i);
        int columnWidth = this.myCommonUseTable.getColumn(i).getWidth();
        if (columnWidth < 100)
          this.myCommonUseTable.getColumn(i).setWidth(100);
      }
    }
  }

  private boolean compareString(String srcStr, String desStr)
  {
    if ((StringUtils.isEmpty(srcStr)) && (StringUtils.isEmpty(desStr))) {
      return false;
    }

    if ((StringUtils.isEmpty(srcStr)) && (!StringUtils.isEmpty(desStr))) {
      return false;
    }

    if ((!StringUtils.isEmpty(srcStr)) && (StringUtils.isEmpty(desStr))) {
      return true;
    }

    if (StringUtils.equals(srcStr, desStr)) {
      return false;
    }

    int srcStrLength = srcStr.length();
    int desStrLength = desStr.length();

    if (srcStrLength > desStrLength) {
      return true;
    }

    if (srcStrLength < desStrLength) {
      return false;
    }

    char[] srcStrCharArray = srcStr.toCharArray();
    char[] desStrCharArray = desStr.toCharArray();
    for (int i = 0; i < srcStr.length(); i++) {
      if (srcStrCharArray[i] > desStrCharArray[i])
        return true;
      if (srcStrCharArray[i] < desStrCharArray[i]) {
        return false;
      }
    }

    return false;
  }

  private class TabbedPaneMainSwitchListener
    implements KeyListener
  {
    private TabbedPaneMainSwitchListener()
    {
    }

    public void keyPressed(KeyEvent e)
    {
      if ((e.isAltDown()) && ((e.getKeyCode() == 37) || (e.getKeyCode() == 39)))
      {
        Component selectedComponent = GeneralF7TreeListUI.this.kdTMain.getSelectedComponent();
        if (selectedComponent == null) {
          return;
        }
        if (selectedComponent.equals(GeneralF7TreeListUI.this.adaptee)) {
          Component[] components = GeneralF7TreeListUI.this.kdTMain.getComponents();
          for (int i = 0; i < components.length; i++) {
            if (components[i].equals(GeneralF7TreeListUI.this.myCommonUseTable))
              GeneralF7TreeListUI.this.kdTMain.setSelectedComponent(GeneralF7TreeListUI.this.myCommonUseTable);
          }
        }
        else if (selectedComponent.equals(GeneralF7TreeListUI.this.myCommonUseTable)) {
          Component[] components = GeneralF7TreeListUI.this.kdTMain.getComponents();
          for (int i = 0; i < components.length; i++)
            if (components[i].equals(GeneralF7TreeListUI.this.adaptee))
              GeneralF7TreeListUI.this.kdTMain.setSelectedComponent(GeneralF7TreeListUI.this.adaptee);
        }
      }
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyTyped(KeyEvent e)
    {
    }
  }

  private class CentralizedHandler extends MouseAdapter
    implements MouseMotionListener
  {
    private CentralizedHandler()
    {
    }

    public void mouseClicked(MouseEvent e)
    {
      GeneralF7TreeListUI.this.doQuickQuery();
    }

    public void mouseDragged(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
      Point location = e.getPoint();
      Rectangle r = new Rectangle();
      GeneralF7TreeListUI.this.quickQueryConditionList.computeVisibleRect(r);
      if (r.contains(location))
        GeneralF7TreeListUI.this.quickQueryConditionList.setSelectedIndex(GeneralF7TreeListUI.this.quickQueryConditionList.locationToIndex(location));
    }
  }

  private class QuickQueryHandler extends MouseAdapter
    implements KeyListener, FocusListener
  {
    private QuickQueryHandler()
    {
    }

    public void mouseClicked(MouseEvent e)
    {
      String inputText = GeneralF7TreeListUI.this.txtQuickQueryInput.getText();
      if ((inputText != null) && (EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "24_GeneralF7TreeListUI").equals(inputText.trim()))) {
        GeneralF7TreeListUI.this.txtQuickQueryInput.setText("");
      }

      Component selectedComponent = GeneralF7TreeListUI.this.kdTMain.getSelectedComponent();
      if (selectedComponent == null) {
        return;
      }
      if (selectedComponent.equals(GeneralF7TreeListUI.this.adaptee))
      {
        GeneralF7TreeListUI.this.gennerateQuickQueryCondition();

        GeneralF7TreeListUI.this.showQuickQueryPanle();
      }
      else if (!selectedComponent.equals(GeneralF7TreeListUI.this.myCommonUseTable));
    }

    public void keyPressed(KeyEvent e)
    {
      if (e.getKeyCode() == 27)
      {
        if (GeneralF7TreeListUI.this.popup != null) {
          GeneralF7TreeListUI.this.popup.hide();
        }
      }
      else if (e.getKeyCode() == 40) {
        int selectedIndex = -1;
        int itemCount = GeneralF7TreeListUI.this.quickQueryConditionList.getElementCount();

        selectedIndex = GeneralF7TreeListUI.this.quickQueryConditionList.getSelectedIndex();
        if (selectedIndex < itemCount - 1)
        {
          selectedIndex++;
        }
        else selectedIndex = 0;

        GeneralF7TreeListUI.this.quickQueryConditionList.setSelectedIndex(selectedIndex);
        GeneralF7TreeListUI.this.quickQueryConditionList.ensureIndexIsVisible(selectedIndex);
      }
      else if (e.getKeyCode() == 38) {
        int selectedIndex = -1;

        selectedIndex = GeneralF7TreeListUI.this.quickQueryConditionList.getSelectedIndex();
        int itemCount = GeneralF7TreeListUI.this.quickQueryConditionList.getElementCount();
        if (selectedIndex > 0)
        {
          selectedIndex--;
        }
        else selectedIndex = itemCount - 1;

        GeneralF7TreeListUI.this.quickQueryConditionList.setSelectedIndex(selectedIndex);
        GeneralF7TreeListUI.this.quickQueryConditionList.ensureIndexIsVisible(selectedIndex);
      }
      else if (e.getKeyCode() == 10)
      {
        GeneralF7TreeListUI.this.isQuickQuery = true;

        Component selectedComponent = GeneralF7TreeListUI.this.kdTMain.getSelectedComponent();
        if (selectedComponent == null) {
          return;
        }
        if (selectedComponent.equals(GeneralF7TreeListUI.this.adaptee)) {
          GeneralF7TreeListUI.this.doQuickQuery();
        } else if ((selectedComponent.equals(GeneralF7TreeListUI.this.myCommonUseTable)) && 
          (GeneralF7TreeListUI.this.myCommonUseTable.getRow(0) != null)) {
          GeneralF7TreeListUI.this.myCommonUseTable.getSelectManager().select(0, -1);
          GeneralF7TreeListUI.this.myCommonUseTable.requestFocus(true);
        }
      }
    }

    public void keyReleased(KeyEvent e)
    {
      if (e.getKeyCode() == 27) {
        return;
      }

      Component selectedComponent = GeneralF7TreeListUI.this.kdTMain.getSelectedComponent();
      if (selectedComponent == null) {
        return;
      }
      int keyCode = e.getKeyCode();
      if (selectedComponent.equals(GeneralF7TreeListUI.this.adaptee)) {
        if (((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90)) || ((keyCode >= 96) && (keyCode <= 105)) || (keyCode == 8) || (keyCode == 127) || (keyCode == 32))
        {
          GeneralF7TreeListUI.this.gennerateQuickQueryCondition();

          GeneralF7TreeListUI.this.showQuickQueryPanle();
        }
      } else if ((selectedComponent.equals(GeneralF7TreeListUI.this.myCommonUseTable)) && (
        ((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90)) || ((keyCode >= 96) && (keyCode <= 105)) || (keyCode == 8) || (keyCode == 127) || (keyCode == 32)))
      {
        GeneralF7TreeListUI.this.myCommonUseContent.clear();
        for (int i = 0; (GeneralF7TreeListUI.this.myCommonUseCache != null) && (i < GeneralF7TreeListUI.this.myCommonUseCache.length); i++) {
          if (GeneralF7TreeListUI.this.myCommonUseCache[i].toUpperCase().indexOf(GeneralF7TreeListUI.this.txtQuickQueryInput.getText().toUpperCase()) >= 0) {
            GeneralF7TreeListUI.this.myCommonUseContent.add(GeneralF7TreeListUI.this.myCommonUseCache[i]);
          }

        }

        GeneralF7TreeListUI.this.fillMyCommonUseTable();
      }
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void focusGained(FocusEvent e)
    {
      String inputText = GeneralF7TreeListUI.this.txtQuickQueryInput.getText();
      if ((inputText != null) && (EASResource.getString("com.kingdee.eas.basedata.framework.FRAMEWORKAutoGenerateResource", "24_GeneralF7TreeListUI").equals(inputText.trim()))) {
        GeneralF7TreeListUI.this.txtQuickQueryInput.setText("");
      }

      Component selectedComponent = GeneralF7TreeListUI.this.kdTMain.getSelectedComponent();
      if (selectedComponent == null) {
        return;
      }
      if (selectedComponent.equals(GeneralF7TreeListUI.this.adaptee))
      {
        GeneralF7TreeListUI.this.gennerateQuickQueryCondition();

        GeneralF7TreeListUI.this.showQuickQueryPanle();
      } else if (!selectedComponent.equals(GeneralF7TreeListUI.this.myCommonUseTable));
    }

    public void focusLost(FocusEvent e)
    {
      if (GeneralF7TreeListUI.this.popup != null)
        GeneralF7TreeListUI.this.popup.hide();
    }
  }

  private class FilterFieldItem
  {
    private String ffiName;
    private String ffiDisplayName;
    private ConstDataType fficdt;

    FilterFieldItem(String name, String displayName, ConstDataType cdt)
    {
      this.ffiName = name;
      this.ffiDisplayName = displayName;
      this.fficdt = cdt;
    }

    public String toString() {
      return this.ffiDisplayName;
    }

    public String getName() {
      return this.ffiName;
    }

    public ConstDataType getType() {
      return this.fficdt;
    }
  }

  class ShowResultThread extends Thread
  {
    public ShowResultThread(String resultMessage)
    {
      GeneralF7TreeListUI.this.statusLable.setText(resultMessage);
    }

    public void run() {
      for (int i = 0; i < 3; i++) {
        GeneralF7TreeListUI.this.statusLable.setVisible(true);
        try {
          sleep(1000L);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        GeneralF7TreeListUI.this.statusLable.setVisible(false);
        try {
          sleep(500L);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}