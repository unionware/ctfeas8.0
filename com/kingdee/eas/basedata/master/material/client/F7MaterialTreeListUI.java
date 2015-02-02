package com.kingdee.eas.basedata.master.material.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.extendcontrols.util.ParamHelper;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.permission.IPermissionServiceProvider;
import com.kingdee.eas.base.permission.PermissionServiceProviderFactory;
import com.kingdee.eas.basedata.framework.util.BDUtil;
import com.kingdee.eas.basedata.framework.util.EntityControlTypeUtil;
import com.kingdee.eas.basedata.master.cssp.MainDataQueryConfig;
import com.kingdee.eas.basedata.master.cssp.UserParamaterFactory;
import com.kingdee.eas.basedata.master.cssp.UserParamaterInfo;
import com.kingdee.eas.basedata.master.material.IMaterial;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardTypeEnum;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.MaterialUtil;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.f7.IF7Provider;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.query.QuickFilteringSupportItems;
import com.kingdee.eas.scm.common.client.GeneralF7TreeListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

public class F7MaterialTreeListUI extends MaterialListUI
  implements IF7Provider, TreeSelectionListener
{
  private FilterInfo quickFilterInfo;
  private IMetaDataPK queryPK;
  private String treeBaseBOSType;
  private String propName;
  private FilterInfo defaultFilterInfo;
  private boolean isMultiSelect = false;

  private String filterCUId = null;
  private String queryName;
  private OrgUnitCollection bizOrgCollection;
  private DefaultKingdeeTreeNode currentNode = null;
  private UserParamaterInfo paramIsF7ShowTree;
  private Context SCMBillMainOrgContext = null;

  protected List idListCache = null;

  protected int rowCount = 0;

  protected boolean isReQuery = true;

  protected boolean hasDealDefFilter = false;

  protected boolean isF7ShowTree = false;

  private static final Logger logger = Logger.getLogger(F7MaterialTreeListUI.class);
  private static final String TABLE_COLUMN_NAME = "name";
  private static final String TABLE_COLUMN_NUMBER = "number";
  private int myCommonLimit = 100;

  FilterInfo curPermissonFilter = null;
  private IQueryExecutor queryExecutor;
  IRowSet rowSet = null;

  public FilterInfo getQuickFilterInfo()
  {
    return this.quickFilterInfo;
  }

  public void setSCMBillMainOrgContext(Context SCMBillMainOrgContext)
  {
    this.SCMBillMainOrgContext = SCMBillMainOrgContext;
  }

  public F7MaterialTreeListUI()
    throws Exception
  {
  }

  public Object getData()
  {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("*"));
    sic.add(new SelectorItemInfo("baseUnit.id"));
    sic.add(new SelectorItemInfo("baseUnit.name"));
    sic.add(new SelectorItemInfo("baseUnit.number"));
    sic.add(new SelectorItemInfo("assistUnit.id"));
    sic.add(new SelectorItemInfo("assistUnit.name"));
    sic.add(new SelectorItemInfo("assistUnit.number"));
    sic.add(new SelectorItemInfo("CU.id"));
    sic.add(new SelectorItemInfo("CU.number"));
    sic.add(new SelectorItemInfo("CU.name"));
    sic.addObjectCollection(MaterialSelectorFactory.getSelectors());
    if (this.isMultiSelect)
    {
      Object[] keyValues = getSelectedIdValues().toArray();

      if (keyValues != null)
      {
        if (keyValues.length <= 0) return null;

        try
        {
          ICoreBase iBiz = getBizInterface();

          ObjectUuidPK pk = null;

          MaterialInfo[] materialInfo = new MaterialInfo[keyValues.length];

          int i = 0; for (int c = keyValues.length; i < c; i++)
          {
            pk = new ObjectUuidPK(BOSUuid.read(keyValues[i].toString()));

            materialInfo[i] = ((MaterialInfo)iBiz.getValue(pk, sic));
          }

          return materialInfo;
        }
        catch (Exception er)
        {
          super.handUIException(er);
        }

      }

    }
    else
    {
      String keyValue = getSelectedKeyValue();

      if (keyValue != null)
      {
        try
        {
          ICoreBase iBiz = getBizInterface();

          ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));

          return iBiz.getValue(pk, sic);
        }
        catch (Exception er)
        {
          super.handUIException(er);
        }

      }

    }

    return null;
  }

  protected void tblMain_tableClicked(KDTMouseEvent e)
    throws Exception
  {
    if ((isOrderForClickTableHead()) && (e.getClickCount() != 2))
    {
      super.tblMain_tableClicked(e);
    }
    if (e.getClickCount() == 2) {
      Container c = getParent();
      while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class))) {
        c = c.getParent();
      }

      if (getData() != null)
        ((GeneralF7TreeListUI)c).clickOKBtn();
    }
  }

  public void onLoad() throws Exception {
    this.pnlMain.remove(this.pnlMain.getComponent(0));

    this.chkIsShowTree.setVisible(true);

    this.kDLabelContainer2.setVisible(false);
    this.kDLabelContainer3.setVisible(false);

    initParamIsF7ShowTree();

    setIsNeedDefaultFilter(false);

    this.actionQuery.setVisible(false);
    rewriteOnload_MaterialListUI();
    this.actionQuery.setVisible(true);

    this.treeView.setShowControlPanel(false);
    this.treeMain.setVisibleRowCount(10);
    this.treeMain.addTreeSelectionListener(this);

    this.tblMain.setRefresh(true);
    this.tblMain.reLayoutAndPaint();
    addKDTableLisener();
    setComboStandard();

    this.tblMain.getInputMap().put(KeyStroke.getKeyStroke(10, 0), "View_1");
    this.tblMain.getActionMap().put("View_1", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        Container c = F7MaterialTreeListUI.this.getParent();
        while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class)))
          c = c.getParent();
        try
        {
          if (c != null)
            ((GeneralF7TreeListUI)c).clickOKBtn();
        }
        catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    this.tblMain.getInputMap().put(KeyStroke.getKeyStroke(27, 0), "esc2");
    this.tblMain.getActionMap().put("esc2", new AbstractAction()
    {
      public void actionPerformed(ActionEvent e) {
        Container c = F7MaterialTreeListUI.this.getParent();
        while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class)))
          c = c.getParent();
        try
        {
          if (c != null)
            ((GeneralF7TreeListUI)c).clickCancelBtn();
        }
        catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    if (!isShowTree()) {
      this.chkIsShowTree.setSelected(false);
      this.kDLabelContainer1.setVisible(false);
    } else {
      this.chkIsShowTree.setSelected(true);
      this.kDLabelContainer1.setVisible(true);
    }
  }

  private ItemListener[] removeItemListeners(AbstractButton abstractButton) { ItemListener[] itemListeners = abstractButton.getItemListeners();
    for (int i = 0; i < itemListeners.length; i++) {
      abstractButton.removeItemListener(itemListeners[i]);
    }
    return itemListeners; }

  private void addItemListeners(AbstractButton abstractButton, ItemListener[] itemListeners) {
    for (int i = 0; i < itemListeners.length; i++)
      abstractButton.addItemListener(itemListeners[i]);
  }

  public void onShow()
    throws Exception
  {
    super.onShow();
    Container c = getParentUI();
    if ((c instanceof GeneralF7TreeListUI)) {
      GeneralF7TreeListUI parentUI = (GeneralF7TreeListUI)c;
      parentUI.setFocus();
      if (this.tblMain.getRow(0) != null)
        this.tblMain.requestFocus();
    }
  }

  protected Container getParentUI()
  {
    Container c = getParent();
    while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class))) {
      c = c.getParent();
    }
    return c;
  }

  private void setComboStandard()
  {
    this.kDLabelContainer1.setBounds(new Rectangle(10, 10, 100, 19));
    this.kDLabelContainer1.setBoundLabelLength(60);
  }

  private void initParamIsF7ShowTree()
  {
    try
    {
      this.paramIsF7ShowTree = MaterialUtil.getUserParamaterInfo("isF7ShowTree", SysContext.getSysContext().getCurrentUserInfo().getId().toString());
      if ((this.paramIsF7ShowTree != null) && (this.paramIsF7ShowTree.getValue() != null) && (!"".equals(this.paramIsF7ShowTree.getValue()))) {
        this.isF7ShowTree = Boolean.parseBoolean(this.paramIsF7ShowTree.getValue());
      } else {
        this.paramIsF7ShowTree = new UserParamaterInfo();
        this.paramIsF7ShowTree.setNumber("isF7ShowTree");
        this.paramIsF7ShowTree.setUserID(SysContext.getSysContext().getCurrentUserInfo().getId());
        this.paramIsF7ShowTree.setValue("false");
      }
    } catch (EASBizException e) {
      handUIException(e);
    }
    catch (BOSException e) {
      handUIException(e);
    }
  }

  public void initUIContentLayout() {
    int y = 36;
    int height = 579;

    setBounds(new Rectangle(10, 10, 1176, 624));
    setLayout(new KDLayout());
    putClientProperty("OriginalBounds", new Rectangle(10, 10, 1176, 624));
    this.btnCancel.setBounds(new Rectangle(896, 594, 79, 21));
    add(this.btnCancel, new KDLayout.Constraints(896, 594, 79, 21, 15));
    this.pnlMain.setBounds(new Rectangle(10, y, 996, height));
    add(this.pnlMain, new KDLayout.Constraints(10, y, 996, height, 15));
    this.btnConfirm.setBounds(new Rectangle(793, 594, 79, 21));
    add(this.btnConfirm, new KDLayout.Constraints(793, 594, 79, 21, 15));

    this.kDLabelContainer1.setBoundLabelLength(80);
    this.kDLabelContainer1.setBounds(new Rectangle(10, 10, 190, 19));
    add(this.kDLabelContainer1, new KDLayout.Constraints(10, 10, 210, 19, 0));

    this.pnlMain.add(this.pnlTable, "right");

    this.treeView.setTree(this.treeMain);

    this.pnlTable.setLayout(new BorderLayout(0, 0));
    this.pnlTable.add(this.tblMain, "Center");
    if (isShowTree()) {
      this.pnlMain.add(this.treeView, "left");
      this.pnlMain.setDividerLocation(210);
      this.pnlMain.setDividerSize(5);
      this.kDLabelContainer1.setVisible(true);

      this.chkIsShowTree.setBounds(new Rectangle(205, 10, 140, 19));
      add(this.chkIsShowTree, new KDLayout.Constraints(225, 10, 180, 19, 4));

      this.pnlTable.remove(this.chkIncludeChild);
      this.chkIncludeChild.setVisible(true);
      this.chkIncludeChild.setBounds(new Rectangle(300, 10, 140, 19));
      add(this.chkIncludeChild, new KDLayout.Constraints(405, 10, 200, 19, 4));
    } else {
      this.pnlMain.remove(this.treeView);
      this.pnlMain.setDividerLocation(0);
      this.pnlMain.setDividerSize(0);
      this.kDLabelContainer1.setVisible(false);
      this.chkIsShowTree.setBounds(new Rectangle(10, 10, 140, 19));
      add(this.chkIsShowTree, new KDLayout.Constraints(10, 10, 180, 19, 4));

      this.pnlTable.remove(this.chkIncludeChild);
      this.chkIncludeChild.setVisible(false);
    }

    this.kDLabelContainer1.setBoundEditor(this.comboMaterialGroupStandard);
  }

  private void addKDTableLisener()
  {
    this.tblMain.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
          Container c = F7MaterialTreeListUI.this.getParent();
          while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class)))
            c = c.getParent();
          try
          {
            if (c != null)
              ((GeneralF7TreeListUI)c).clickOKBtn();
          }
          catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });
  }

  public boolean isRequeryQuery()
  {
    return true;
  }

  public Map reSetfilterName(Map map)
  {
    Map rsMap = null;
    String[] filterDisplayName = null;
    String[] filterName = null;
    if ((map != null) && (map.get("FilterDisplayName") != null) && (map.get("FilterName") != null)) {
      rsMap = new HashMap();
      filterDisplayName = (String[])map.get("FilterDisplayName");
      filterName = (String[])map.get("FilterName");
      String[] newFilterDisplayName = new String[filterDisplayName.length];
      String[] newFilterName = new String[filterName.length];
      if (filterName != null) {
        int k = 0;
        for (int i = 0; i < filterName.length; i++) {
          if ("number".equals(filterName[i])) {
            newFilterName[k] = filterName[i];
            newFilterDisplayName[k] = filterDisplayName[i];
            k++;
          } else if ("name".equals(filterName[i])) {
            newFilterName[k] = filterName[i];
            newFilterDisplayName[k] = filterDisplayName[i];
            k++;
          } else if ("model".equals(filterName[i])) {
            newFilterName[k] = filterName[i];
            newFilterDisplayName[k] = filterDisplayName[i];
            k++;
          } else if ("helpCode".equals(filterName[i])) {
            newFilterName[k] = filterName[i];
            newFilterDisplayName[k] = filterDisplayName[i];
            k++;
          } else if ("baseUnit.name".equals(filterName[i])) {
            newFilterName[k] = filterName[i];
            newFilterDisplayName[k] = filterDisplayName[i];
            k++;
          }
        }
      }
      rsMap.put("FilterDisplayName", newFilterDisplayName);
      rsMap.put("FilterName", newFilterName);
    }
    return rsMap;
  }

  public void actionQuery_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.queryPK != null) {
      this.mainQueryPK = this.queryPK;
    }

    IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory.getRemoteInstance();

    String queryName = getQueryInfo(this.mainQueryPK).getFullName();
    if (!isPerformDefaultQuery(iQuery, queryName))
    {
      if (!isFirstDefaultQuery()) {
        if (this.dialog == null) {
          this.dialog = initCommonQueryDialog();
          this.dialog.setMaxReturnCountVisible(true);
          this.dialog.setUiObject(null);
          this.dialog.setShowToolbar(false);
          this.dialog.setShowSorter(false);
          this.dialog.setOwner(getParentUI());
        }

        if (this.dialog.show()) {
          this.tHelper.setDialog(this.dialog);
          setDefaultEntityViewInfo(this.dialog.getEntityViewInfoResult());

          this.mainQuery = getEntityViewInfo((EntityViewInfo)this.dialog.getEntityViewInfoResult().clone());

          if (this.isMergeCommonQuery) {
            buildTreeFilter();
          }

          if ((this.mainQuery.getSorter() != null) && (this.mainQuery.getSorter().size() > 0))
          {
            setIsIgnoreOrder(true);
          }
          else setIsIgnoreOrder(false);

          setLocatePre(true);
          execQuery();
        } else {
          this.tHelper.setDialog(this.dialog);
          SysUtil.abort();
        }
      }
    }
  }

  protected void tblMain_doRequestRowSet(RequestRowSetEvent e)
  {
    if ((isFirstOnload()) && (!this.chkIncludeChild.isSelected()) && ((getOuterUI() == null) || (!getOuterUI().getQuickQuery())))
    {
      return;
    }
    if (this.mainQuery != null)
    {
      try
      {
        if (this.defaultFilterInfo != null) {
          if (this.queryPK != null)
          {
            this.mainQueryPK = this.queryPK;
            this.mainQuery = getDefaultQuery();
            tblMain_doRequestRowSetForNoQueryPK(e);
          }
          else
          {
            this.mainQuery = getDefaultQuery();
            tblMain_doRequestRowSetForNoQueryPK(e);
          }

        }
        else if (this.quickFilterInfo != null) {
          if (this.queryPK != null) {
            this.mainQueryPK = this.queryPK;
            this.mainQuery = getDefaultQuery();
            tblMain_doRequestRowSetForNoQueryPK(e);
          }
          else
          {
            this.mainQuery = getDefaultQuery();
            tblMain_doRequestRowSetForNoQueryPK(e);
          }

        }
        else
        {
          EntityViewInfo query = getDefaultQuery();
          query.getSorter().addObjectCollection(this.mainQuery.getSorter());
          if (this.queryPK != null) {
            this.mainQueryPK = this.queryPK;
            this.mainQuery = query;
            tblMain_doRequestRowSetForNoQueryPK(e);
          }
          else
          {
            this.mainQuery = query;
            tblMain_doRequestRowSetForNoQueryPK(e);
          }

        }

      }
      catch (Exception ee)
      {
        if ((ee.getMessage() != null) && (ee.getMessage().indexOf("DRG-50901") > -1))
        {
          logger.error(ee);
          SysUtil.abort();
        } else {
          handUIException(ee);
        }
      }
    }
  }

  public void setQuickFilterInfo(FilterInfo quickFilterInfo)
  {
    this.quickFilterInfo = quickFilterInfo;
  }

  public void setTreeBaseBOSType(String type)
  {
    this.treeBaseBOSType = type;
  }

  protected ITreeBase getTreeInterface()
    throws Exception
  {
    if (this.treeBaseBOSType != null) {
      return (ITreeBase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType(this.treeBaseBOSType), ITreeBase.class);
    }

    return super.getTreeInterface();
  }

  public EntityViewInfo getMainQuery()
  {
    EntityViewInfo evi = new EntityViewInfo();
    try
    {
      FilterInfo tmpFilterInfo = (FilterInfo)this.quickFilterInfo.clone();
      if ((this.bizOrgCollection != null) && (this.bizOrgCollection.size() > 0))
        tmpFilterInfo.mergeFilter(getDFiltersInfo(), "and");
      else {
        tmpFilterInfo.mergeFilter(getDFilterInfo(), "and");
      }
      tmpFilterInfo.mergeFilter(this.mainQuery.getFilter(), "and");
      evi.setFilter(tmpFilterInfo);
      evi.getSorter().addObjectCollection(this.mainQuery.getSorter());
    } catch (Exception e) {
      super.handUIException(e);
    }
    return evi;
  }

  public EntityViewInfo getDefaultQuery()
  {
    EntityViewInfo evi = new EntityViewInfo();
    try
    {
      FilterInfo tmpFilterInfo;
      if (this.defaultFilterInfo != null)
      {
        tmpFilterInfo = (FilterInfo)this.defaultFilterInfo.clone();
      }
      else
      {
        tmpFilterInfo = new FilterInfo();
      }

      if (this.quickFilterInfo != null) {
        if (tmpFilterInfo.getFilterItems().size() != 0)
          tmpFilterInfo.mergeFilter(this.quickFilterInfo, "and");
        else {
          tmpFilterInfo = (FilterInfo)this.quickFilterInfo.clone();
        }

      }

      if ((this.dialog != null) && (this.dialog.getEntityViewInfoResult() != null) && (this.dialog.getEntityViewInfoResult().getFilter() != null)) {
        tmpFilterInfo.mergeFilter(this.dialog.getEntityViewInfoResult().getFilter(), "and");
      }

      handleFilterInfoWithBrowseGroup(tmpFilterInfo);

      evi.setFilter(tmpFilterInfo);
      evi.getSorter().clear();
      evi.getSorter().addObjectCollection(this.mainQuery.getSorter());
    } catch (Exception e) {
      super.handUIException(e);
    }
    return evi;
  }

  private FilterInfo getMaterialStandardFilterInfo() throws Exception {
    FilterInfo filterInfo = new FilterInfo();
    if (this.materialGroupStandardID != null) {
      filterInfo.getFilterItems().add(new FilterItemInfo("materialGroupStandard.id", this.materialGroupStandardID, CompareType.EQUALS));
    }

    return filterInfo;
  }

  public MaterialGroupStandardInfo getCurGroupStandard()
  {
    return (MaterialGroupStandardInfo)this.comboMaterialGroupStandard.getSelectedItem();
  }

  protected void handleFilterInfoWithBrowseGroup(FilterInfo tmpFilterInfo)
    throws Exception
  {
    Container c = getParent();
    while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class))) {
      c = c.getParent();
    }
    FilterInfo browseGroupFilter = new FilterInfo();
    if ((this.currentNode == null) || (!(this.currentNode.getUserObject() instanceof MaterialGroupInfo)))
    {
      if ((!this.chkIncludeChild.isSelected()) && (!((GeneralF7TreeListUI)c).getQuickQuery()))
      {
        browseGroupFilter.getFilterItems().add(new FilterItemInfo("id is null"));
      }
      else if (MaterialGroupStandardTypeEnum.BasicStandard == getCurGroupStandard().getStandardType()) {
        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroup.deletedStatus", Integer.valueOf(1)));

        if (MainDataQueryConfig.isNeedGroupStandardFilter(this.queryPK))
          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroupStandard.id", getCurGroupStandard().getId().toString()));
      }
      else
      {
        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroupStandard.id", getCurGroupStandard().getId().toString()));

        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroup.deletedStatus", Integer.valueOf(1)));
      }
    }
    else
    {
      MaterialGroupInfo curGroupInfo = (MaterialGroupInfo)this.currentNode.getUserObject();

      if ((curGroupInfo.getLongNumber() == null) || ("".equals(curGroupInfo.getLongNumber())))
      {
        throw new BOSException("data error,group's long number should not be null or empty!");
      }

      if (MaterialGroupStandardTypeEnum.BasicStandard == getCurGroupStandard().getStandardType())
      {
        String mask = "";
        if (MainDataQueryConfig.isNeedGroupStandardFilter(this.queryPK)) {
          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroupStandard.id", getCurGroupStandard().getId().toString()));

          mask = "#0";
        }
        if ((this.chkIncludeChild.isEnabled()) && (this.chkIncludeChild.isSelected())) {
          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroup.longNumber", curGroupInfo.getLongNumber() + "!%", CompareType.LIKE));

          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroup.longNumber", curGroupInfo.getLongNumber()));

          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroup.deletedStatus", Integer.valueOf(1)));

          if (!mask.equals(""))
            mask = "#0 and (#1 or #2) and #3";
          else {
            mask = "(#0 or #1) and #2";
          }
          browseGroupFilter.setMaskString(mask);
        }
        else {
          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroup.longNumber", curGroupInfo.getLongNumber()));

          browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroup.deletedStatus", Integer.valueOf(1)));

          if (!mask.equals(""))
            mask = "#0 and #1 and #2";
          else {
            mask = "#0 and #1";
          }
          browseGroupFilter.setMaskString(mask);
        }

      }
      else if ((this.chkIncludeChild.isEnabled()) && (this.chkIncludeChild.isSelected())) {
        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroup.longNumber", curGroupInfo.getLongNumber() + "!%", CompareType.LIKE));

        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroup.longNumber", curGroupInfo.getLongNumber()));

        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroup.deletedStatus", Integer.valueOf(1)));

        browseGroupFilter.setMaskString("(#0 or #1) and #2");
      } else {
        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroup.longNumber", curGroupInfo.getLongNumber()));

        browseGroupFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroup.deletedStatus", Integer.valueOf(1)));

        browseGroupFilter.setMaskString("#0 and #1");
      }

      FilterInfo stdFilter = new FilterInfo();
      stdFilter.getFilterItems().add(new FilterItemInfo("materialGroupDetails.materialGroupStandard.id", getCurGroupStandard().getId().toString()));
      browseGroupFilter.mergeFilter(stdFilter, "and");
    }
    tmpFilterInfo.mergeFilter(browseGroupFilter, "and");
  }

  public void setQueryPK(IMetaDataPK pk)
  {
    this.queryPK = pk;
  }

  protected boolean isLocateTree()
  {
    return true;
  }

  protected IObjectPK getSelectedTreeKeyValue()
  {
    int propColumnNo = -1;

    if (this.propName != null)
    {
      for (int i = 0; i < this.tblMain.getColumnCount(); i++) {
        if (this.propName.equals(this.tblMain.getColumn(i).getFieldName())) {
          propColumnNo = i;
          break;
        }
      }
      if (propColumnNo != -1)
      {
        KDTSelectBlock sb = this.tblMain.getSelectManager().get();
        int top = sb.getTop();
        ICell cell = this.tblMain.getCell(top, propColumnNo);
        String id = (String)cell.getValue();

        if ((id == null) || (id.equals(""))) {
          return null;
        }
        BOSUuid uuid = BOSUuid.read(id);
        return new ObjectUuidPK(uuid);
      }

      return null;
    }
    return super.getSelectedTreeKeyValue();
  }

  protected void chkIncludeChild_itemStateChanged(ItemEvent e)
    throws Exception
  {
    Container c = getParent();
    while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class))) {
      c = c.getParent();
    }
    if ((c instanceof GeneralF7TreeListUI)) {
      ((GeneralF7TreeListUI)c).setQuickQuery(false);
    }
    this.tblMain.setScrollStateVertical(2);
    super.chkIncludeChild_itemStateChanged(e);
    this.tblMain.setScrollStateVertical(0);
  }

  public void setAssociatePropertyName(String propName)
  {
    this.propName = propName;
  }

  protected String getQueryFieldName()
  {
    return super.getQueryFieldName();
  }

  protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
  }

  protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    this.tblMain.setScrollStateVertical(2);
    super.treeMain_valueChanged(e);
    this.tblMain.setScrollStateVertical(0);

    KDTreeNode treeNode = (KDTreeNode)this.treeMain.getLastSelectedPathComponent();
    if (treeNode != null);
    Container c = getParent();
    while ((c != null) && (!c.getClass().equals(GeneralF7TreeListUI.class))) {
      c = c.getParent();
    }
    if ((!isFirstOnload()) && 
      ((c instanceof GeneralF7TreeListUI)))
      ((GeneralF7TreeListUI)c).setQuickQuery(false);
  }

  public void setFilterInfo(FilterInfo defaultFilterInfo, FilterInfo quickFilterInfo)
  {
    this.defaultFilterInfo = defaultFilterInfo;

    this.quickFilterInfo = quickFilterInfo;
  }
  public FilterInfo getDefaultFilterInfo() {
    return this.defaultFilterInfo;
  }

  public void setMultiSelect(boolean isMulti)
  {
    this.isMultiSelect = isMulti;
  }

  private FilterInfo getDFilterInfo()
    throws Exception
  {
    IMaterial iMaterial = MaterialFactory.getRemoteInstance();
    ObjectUuidPK pk;
    if (this.filterCUId != null)
    {
      pk = new ObjectUuidPK(BOSUuid.read(this.filterCUId));
    }
    else
    {
      pk = new ObjectUuidPK(SysContext.getSysContext().getCurrentCtrlUnit().getId());
    }

    return iMaterial.getDatabaseDFilter(pk, "id", "adminCU.id");
  }

  public void setFilterCUID(String cuId)
  {
    this.filterCUId = cuId;
    if (this.filterCUId != null) {
      CtrlUnitInfo curCU = null;
      try {
        curCU = CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(cuId));
      } catch (EASBizException e) {
        handUIException(e);
      } catch (BOSException e) {
        handUIException(e);
      }
      setCurrentCtrlUnit(curCU);
    } else {
      setCurrentCtrlUnit(SysContext.getSysContext().getCurrentCtrlUnit());
    }
  }

  private FilterInfo getDFiltersInfo()
    throws Exception
  {
    return FilterInfoProducerFactory.getMultiOUs4DataFilterInfoProducer(this.queryName, this.bizOrgCollection).getFilterInfo();
  }

  public void setFilterCU(String queryName, OrgUnitCollection bizOrgCollection) {
    this.queryName = queryName;
    this.bizOrgCollection = bizOrgCollection;
  }

  protected FilterInfo getDefaultFilterForTree() {
    FilterInfo filterInfo = new FilterInfo();
    if (!isShowTree()) {
      filterInfo.getFilterItems().add(new FilterItemInfo("id is null"));
    }
    if (this.filterCUId == null) {
      this.filterCUId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    }

    filterInfo.getFilterItems().add(new FilterItemInfo("groupStandard", this.materialGroupStandardID, CompareType.EQUALS));
    try {
      FilterInfo filterInfoCU = null;

      if (getCurrentCtrlUnit() != null) {
        filterInfoCU = EntityControlTypeUtil.getFilterInfoForControlTypeS4New(getCurrentCtrlUnit().getId().toString(), getCurrentCtrlUnit().getLongNumber());
      }

      filterInfo.mergeFilter(filterInfoCU, "and");

      filterInfo.mergeFilter(getDataPermissonFilter(this.filterCUId), "and");

      FilterInfo statusFilter = new FilterInfo();
      statusFilter.getFilterItems().add(new FilterItemInfo("deletedStatus", new Integer(1), CompareType.EQUALS));

      filterInfo.mergeFilter(statusFilter, "and");
    }
    catch (BOSException e) {
      e.printStackTrace();
    } catch (EASBizException e) {
      e.printStackTrace();
    } catch (ParserException e) {
      e.printStackTrace();
    }
    return filterInfo;
  }

  private FilterInfo getDataPermissonFilter(String filterCUId)
    throws BOSException, EASBizException, ParserException
  {
    if (this.curPermissonFilter != null) {
      return this.curPermissonFilter;
    }
    FilterInfo tmpFilterInfo = null;
    IObjectPK userId = null;
    IObjectPK orgId = null;
    String rule = null;
    IPermissionServiceProvider provider = null;

    userId = new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId());

    orgId = new ObjectStringPK(filterCUId);
    provider = PermissionServiceProviderFactory.getRemoteInstance();
    if (provider.isEnableDataPermission()) {
      rule = provider.getPermissionRule(userId, orgId, "bd_materialGroup_view");

      if ((rule != null) && 
        (rule.toLowerCase().indexOf("where") != -1)) {
        rule = rule.substring(rule.toLowerCase().indexOf("where") + 5, rule.length());

        tmpFilterInfo = new FilterInfo(rule.trim());
      }
    }

    if (tmpFilterInfo == null)
      tmpFilterInfo = new FilterInfo();
    this.curPermissonFilter = tmpFilterInfo;
    return tmpFilterInfo;
  }

  protected FilterInfo getInitFilterInfo()
  {
    FilterInfo filterInfo = null;
    try {
      if ((this.bizOrgCollection != null) && (this.bizOrgCollection.size() > 0))
        filterInfo = getDFiltersInfo();
      else
        filterInfo = getDFilterInfo();
    }
    catch (Exception e) {
      handUIException(e);
    }
    if (filterInfo == null) {
      filterInfo = new FilterInfo();
    }
    return filterInfo;
  }

  protected FilterInfo getDefaultFilterForQuery()
  {
    FilterInfo filterInfo = new FilterInfo();
    FilterInfo tempFilterInfo = new FilterInfo();
    if ((getSelectedTreeNode() != null) && (getSelectedTreeNode().getUserObject() == getRootObject())) {
      if (this.chkIncludeChild.isSelected()) {
        tempFilterInfo.getFilterItems().add(new FilterItemInfo("materialGroupStandard.id", this.materialGroupStandardID, CompareType.EQUALS));
        tempFilterInfo.getFilterItems().add(new FilterItemInfo("materialGroupStandard.id", "null", CompareType.EQUALS));
        tempFilterInfo.setMaskString("#0 or #1");
      } else {
        tempFilterInfo.getFilterItems().add(new FilterItemInfo("materialGroupStandard.id", "null", CompareType.EQUALS));
      }
    }
    else tempFilterInfo.getFilterItems().add(new FilterItemInfo("materialGroupStandard.id", this.materialGroupStandardID, CompareType.EQUALS));
    try
    {
      filterInfo.mergeFilter(tempFilterInfo, "and");
    } catch (BOSException e) {
      handleException(e);
    }
    return filterInfo;
  }

  protected ICoreBase getBizInterface() throws Exception
  {
    if (this.SCMBillMainOrgContext != null)
    {
      return MaterialFactory.getRemoteInstanceWithObjectContext(this.SCMBillMainOrgContext);
    }

    return MaterialFactory.getRemoteInstanceWithObjectContext(getMainOrgContext());
  }

  protected String getSelectDetailTreeName()
  {
    return super.getSelectDetailTreeName();
  }

  protected void initKeyStroke()
  {
  }

  protected Context getMainOrgContext()
  {
    return this.SCMBillMainOrgContext;
  }

  public void valueChanged(TreeSelectionEvent arg0)
  {
    this.currentNode = ((DefaultKingdeeTreeNode)arg0.getPath().getLastPathComponent());
  }

  protected void initFocusPolicy()
  {
  }

  public boolean isOptimizeQuery()
  {
    return false;
  }

  public boolean isIgnoreRowCount() {
    return true;
  }

  public IQueryExecutor getQueryExecutor()
  {
    return this.queryExecutor;
  }

  protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
  {
    if ((viewInfo.getSorter() == null) || (viewInfo.getSorter().size() == 0)) {
      viewInfo.setSorter((SorterItemCollection)this.defSort.clone());
    }
    this.queryExecutor = super.getQueryExecutor(queryPK, viewInfo);

    if ((viewInfo.getSorter() == null) || (viewInfo.getSorter().size() == 0))
      this.queryExecutor.option().isIgnoreOrder = false;
    else {
      this.queryExecutor.option().isIgnoreOrder = true;
    }
    this.queryExecutor.option().isAutoIgnoreDuplicatedRow = (!MainDataQueryConfig.canIgnoreDistinct(viewInfo.getFilter(), this.mainQueryPK));

    if (isSuportTextIndex())
    {
      BDUtil.replaceFilterItem(this.queryExecutor.getObjectView().getFilter(), "number,name", "number4idx,name4idx");
      this.queryExecutor.option().prosAutoUseTextIndex = getProsAutoUseTextIndex();
    }

    return this.queryExecutor;
  }
  public GeneralF7TreeListUI getOuterUI() {
    return (GeneralF7TreeListUI)getParentUI();
  }

  protected boolean isF7()
  {
    return true;
  }

  protected int getTreeInitialLevel()
  {
    return 1;
  }

  protected void checkQueryPK()
  {
    setHasQyeryPK(true);
  }

  public void setRowSet(IRowSet rowSet)
  {
    this.rowSet = rowSet;
  }

  public int getMaxReturnRowCount()
  {
    return 1000;
  }

  protected IRowSet doRealModeExcuteQuery(IQueryExecutor exec, int start, int length) throws BOSException {
    if (start >= getMaxReturnRowCount() - length) {
      length = getMaxReturnRowCount() - start;
      this.tblMain.setRowCount(getMaxReturnRowCount());
    }

    return exec.executeQuery(start, length);
  }

  private String generateSqlSegment(List idListCache, int start, int length)
  {
    StringBuffer result = new StringBuffer(500);

    return result.toString();
  }

  public void execQuery()
  {
    this.isReQuery = true;
    super.execQuery();
  }

  protected void rewriteOnload_MaterialListUI()
    throws Exception
  {
    initDefSortItem();
    if (getUIContext().get("isF7") == null) {
      this.btnConfirm.setVisible(false);
      this.btnCancel.setVisible(false);
    }
    this.btnCoefficient.setVisible(false);

    this.kDSeparator3.setVisible(false);
    this.separatorView2.setVisible(false);

    loadParams();

    beforeLoadInitData();
    initCurrentHelper();
    getCurrentHelper().setListUI(this);
    rewriteOnload_DataBaseDTreeDetailListUI();

    this.tblMain.getColumn("number").setWidth(100);
    this.tblMain.getColumn("name").setWidth(200);

    IColumn colModel = this.tblMain.getColumn("model");
    if (colModel != null) {
      colModel.setWidth(150);
    }
    int columnIndex = this.tblMain.getColumnIndex("name");
    this.tblMain.getViewManager().setFreezeView(0, columnIndex + 1);

    this.tblMain.getGroupManager().setGroup(false);
    setNumberFormat();
  }

  protected void rewriteOnload_DataBaseDTreeDetailListUI()
    throws Exception
  {
    String[] mergeColumnKeys = getMergeColumnKeys(this.tblMain);

    setMergeColumn(this.tblMain, mergeColumnKeys);
    onload_TreeDetailListUI();
    addSrcFunction();
  }

  protected void setNumberFormat()
  {
  }

  protected String getProsAutoUseTextIndex()
  {
    return "number4idx,name4idx";
  }
  protected boolean isSuportTextIndex() {
    if ((Boolean.parseBoolean(ParamHelper.getFullTextIndexEnabled())) && (isItemSupportTextIndex()))
    {
      return true;
    }return false;
  }

  protected boolean isShowTree()
  {
    return this.isF7ShowTree;
  }

  public boolean isItemSupportTextIndex()
  {
    if ((getParentUI() != null) && ((getParentUI() instanceof GeneralF7TreeListUI))) {
      String filterItem = ((GeneralF7TreeListUI)getParentUI()).getSelectItem();
      if ((EASResource.getString("com.kingdee.eas.basedata.master.material.MATERIALAutoGenerateResource", "14_F7MaterialTreeListUI").equals(filterItem)) || (EASResource.getString("com.kingdee.eas.basedata.master.material.MATERIALAutoGenerateResource", "15_F7MaterialTreeListUI").equals(filterItem)))
        return true;
    }
    return false;
  }

  public void chkIsShowTree_itemStateChanged(ItemEvent e) {
    if (this.chkIsShowTree.isSelected()) {
      this.isF7ShowTree = true;
      this.paramIsF7ShowTree.setValue("true");
      try
      {
        TreeSelectionListener[] treeSelectionListeners = removeAllTreeSelectionListener();
        super.initTree();
        this.treeMain.setSelectionRow(0);

        addAllTreeSelectionListener(treeSelectionListeners);
      }
      catch (Exception e1)
      {
        logger.error(e1.getMessage(), e1);
        handleException(e1);
      }
    } else {
      this.isF7ShowTree = false;
      this.paramIsF7ShowTree.setValue("false");
    }
    initUIContentLayout();
    try {
      if (!isFirstOnload())
        UserParamaterFactory.getRemoteInstance().save(this.paramIsF7ShowTree);
    }
    catch (Exception e1) {
      logger.error(e1.getMessage(), e1);
      handleException(e1);
    }
  }

  private TreeSelectionListener[] removeAllTreeSelectionListener() { TreeSelectionListener[] listeners = this.treeMain.getTreeSelectionListeners();
    for (int i = 0; i < listeners.length; i++) {
      this.treeMain.removeTreeSelectionListener(listeners[i]);
    }
    return listeners;
  }

  private void addAllTreeSelectionListener(TreeSelectionListener[] listeners)
  {
    for (int i = listeners.length - 1; i >= 0; i--)
      this.treeMain.addTreeSelectionListener(listeners[i]);
  }

  protected QuickFilteringSupportItems getQuickFilteringSupport()
  {
    return new QuickFilteringSupportItems().supportNoRowsTips(false);
  }

  protected void adjustComponent()
  {
  }
}