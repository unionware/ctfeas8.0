 package com.kingdee.eas.ma.budget.client;
 
 import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.budget.AssignSolutionCollection;
import com.kingdee.eas.ma.budget.AssignSolutionFactory;
import com.kingdee.eas.ma.budget.AssignSolutionInfo;
import com.kingdee.eas.ma.budget.AssignSolutionTypeEnum;
import com.kingdee.eas.ma.budget.BgCollectException;
import com.kingdee.eas.ma.budget.BgCollectStateEnum;
import com.kingdee.eas.ma.budget.BgDisCountFormFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.eas.ma.budget.BgFormCollection;
import com.kingdee.eas.ma.budget.BgFormException;
import com.kingdee.eas.ma.budget.BgFormFacadeFactory;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormHelper;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgFormStateEnum;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.BgSchemeCollection;
import com.kingdee.eas.ma.budget.BgSchemeFactory;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.eas.ma.budget.IBgDisCountForm;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.IBgScheme;
import com.kingdee.eas.ma.budget.IRefDisCountBgForm;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormFactory;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.BgPermissionHelper;
import com.kingdee.eas.ma.nbudget.client.BgParamCHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
 
 
 public class BgDisCountFormAddNewUI
   extends AbstractBgDisCountFormAddNewUI
   implements AssignSolutionInterface
 {
   private static final Logger logger = CoreUIObject.getLogger(BgDisCountFormAddNewUI.class);
   
   private static final String STRRESOURCE = "com.kingdee.eas.ma.budget.client.BgCollectResource";
   protected boolean isCancel = true;
   protected boolean isAddNew = true;
   
   protected Map orgUnitMap = null;
   protected Vector orgUnits = null;
   
   private Map returnData = null;
   
   protected IBgDisCountForm ibgCollectForm = null;
   
   public BgDisCountFormAddNewUI()
     throws Exception
   {}
   
   public void onLoad() throws Exception
   {
     this.orgUnitMap = new HashMap();
     
     String rootUnitID = getCurrentCostCenterOrgUnitInfo().getId().toString();
     
 
 
     TreeModel treeModel = BgNFSHelper.getFullTreeModel(rootUnitID);
     
 
 
     DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeModel.getRoot();
     root.setChecked(true);
     root.setCheckBoxEnabled(false);
     this.tblTree.setModel(treeModel);
     this.tblTree.setShowCheckBox(true);
     
     super.onLoad();
     
     initSystem();
     
 
     initBgSchema();
     
     if (getOprtState() == OprtState.VIEW) {
       this.actionConfirm.setEnabled(false);
     } else {
       this.actionConfirm.setEnabled(true);
     }
     
 
     this.prmtBgForm.getEntityViewInfo().getFilter().setMaskString("(" + this.prmtBgForm.getEntityViewInfo().getFilter().getMaskString() + ") and #" + this.prmtBgForm.getEntityViewInfo().getFilter().getFilterItems().size());
     this.prmtBgForm.getEntityViewInfo().getFilter().getFilterItems().add(new FilterItemInfo("bgTemplate.formType", Integer.valueOf(4), CompareType.NOTEQUALS));
   }
   
   private IBgForm getBgFormInterface() throws BOSException
   {
     return BgFormFactory.getRemoteInstance();
   }
   
 
 
 
   private void initSystem()
     throws Exception
   {
     this.prmtBgForm.addDataChangeListener(new DataChangeListener()
     {
       public void dataChanged(DataChangeEvent eventObj) {
          checkDataValid(eventObj.getNewValue(), eventObj.getOldValue());
       }
     });
   }
   
   protected void initWorkButton() {
     super.initWorkButton();
     
     this.btnAddComment.setEnabled(false);
     this.btnAddComment.setVisible(false);
     this.btnViewComment.setEnabled(false);
     this.btnViewComment.setVisible(false);
     
     this.menuBar.setVisible(false);
     this.toolBar.setVisible(false);
     this.statusBar.setVisible(false);
     
     this.actionQuit.setEnabled(true);
   }
   
 
 
 
 
   protected void initBgSchema()
     throws Exception
   {
     FullOrgUnitInfo ouInfo = getCurrentFullOrgUnitInfoUsedInBg();
     if (ouInfo == null) {
       throw new BgCollectException(BgCollectException.CURRENTHASNOCOSTCENTER);
     }
     IBgScheme bgSchema = BgSchemeFactory.getRemoteInstance();
     EntityViewInfo view = new EntityViewInfo();
     FilterInfo filter = new FilterInfo();
     filter.getFilterItems().add(new FilterItemInfo("orgUnit", ouInfo.getId().toString()));
     if (BgParamCHelper.isFodian()) {
       filter.getFilterItems().add(new FilterItemInfo("period ", new Integer(4), CompareType.NOTEQUALS));
     } else {
       filter.getFilterItems().add(new FilterItemInfo("period ", new Integer(-1), CompareType.NOTEQUALS));
     }
     filter.getFilterItems().add(new FilterItemInfo("isFiling", Integer.valueOf(1), CompareType.NOTEQUALS));
     filter.setMaskString("#0 and #1 and #2");
     if (BgSHelper.isBgPermissionUsed()) {
       Set orgSet = new HashSet();
       orgSet.add(ouInfo.getId().toString());
       String user = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
       filter = BgPermissionHelper.addBgSchemePermission(user, orgSet, filter);
     }
     view.setFilter(filter);
     view.getSelector().add("id");
     view.getSelector().add("number");
     view.getSelector().add("name");
     
     BgSchemeCollection bgSchemeCols = bgSchema.getBgSchemeCollection(view);
     if ((bgSchemeCols == null) || (bgSchemeCols.isEmpty())) {
       EntityViewInfo viewForm = new EntityViewInfo();
       FilterInfo filterForm = new FilterInfo();
       filterForm.getFilterItems().add(new FilterItemInfo("id", "0000000000000000000000000000"));
       viewForm.setFilter(filterForm);
       this.prmtBgForm.setEntityViewInfo(viewForm);
       return;
     }
     String[] num = new String[1];
     num[0] = "number";
     BgHelper.sortCollection(bgSchemeCols, num);
     this.comBgScheme.addItems(bgSchemeCols.toArray());
   }
   
 
 
 
   protected void initCostCenter(BOSUuid bgFormId)
     throws Exception
   {
     Map filterMap = null;
     List state = new ArrayList();
     state.add(BgFormStateEnum.Certificate);
     try
     {
       filterMap = getBgFormInterface().getFormFilterInfo(bgFormId, state);
       filterMap = getBgFormInterface().getFormFilterInfo(filterMap);
       String _bgFormId;
       String orgUnitId; Iterator iter; if ((filterMap != null) && (!filterMap.isEmpty())) {
         _bgFormId = null;orgUnitId = null;
         
         this.orgUnitMap.clear();
         for (iter = filterMap.keySet().iterator(); iter.hasNext();) {
           _bgFormId = (String)iter.next();
           orgUnitId = (String)filterMap.get(_bgFormId);
           if (!this.orgUnitMap.containsKey(orgUnitId)) {
             this.orgUnitMap.put(orgUnitId, null);
           }
         }
       }
       DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot();
       traceNode(root);
       traceNode(root, this.orgUnitMap);
       this.tblTree.repaint();
     } catch (BOSException ex) {
       throw ex;
     } catch (EASBizException ex) {
       throw ex;
     } finally {
       BgNSHelper.objClear(filterMap);
     }
   }
   
   protected void traceNode(DefaultKingdeeTreeNode root, Map orgUnitMap) {
     if ((root == null) || (orgUnitMap == null) || (orgUnitMap.isEmpty())) {
       return;
     }
     OrgStructureInfo osi = null;
     FullOrgUnitInfo orgUnitInfo = null;
     
     for (int i = 0; i < root.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
       osi = (OrgStructureInfo)node.getUserObject();
       orgUnitInfo = osi.getUnit();
       
       if (orgUnitMap.containsKey(orgUnitInfo.getId().toString())) {
         node.setChecked(true);
       }
       traceNode(node, orgUnitMap);
     }
   }
   
   protected void traceNode(DefaultKingdeeTreeNode root) {
     if (root == null)
       return;
     for (int i = 0; i < root.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
       node.setChecked(false);
       traceNode(node);
     }
   }
   
   protected void checkTraceNode(DefaultKingdeeTreeNode root) {
     if (root == null) {
       return;
     }
     OrgStructureInfo osi = null;OrgStructureInfo posi = null;
     FullOrgUnitInfo orgUnitInfo = null;FullOrgUnitInfo pOrgUnitInfo = null;
     
     for (int i = 0; i < root.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
       if (node.isChecked()) {
         osi = (OrgStructureInfo)node.getUserObject();
         orgUnitInfo = osi.getUnit();
         
         if (!root.isChecked()) {
           posi = (OrgStructureInfo)root.getUserObject();
           pOrgUnitInfo = posi.getUnit();
           
           BgCollectException e = new BgCollectException(BgCollectException.NOSELECTPARENTNODE, new String[] { orgUnitInfo.getName(), pOrgUnitInfo.getName() });
           handleException(e);
           SysUtil.abort();
         }
       }

       checkTraceNode(node);
     }
   }
   
   protected void traceNode(DefaultKingdeeTreeNode root, List orgUnitList) {
     if (root == null) {
       return;
     }
     OrgStructureInfo osi = null;
     FullOrgUnitInfo orgUnitInfo = null;
     
     for (int i = 0; i < root.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
       if (node.isChecked()) {
         osi = (OrgStructureInfo)node.getUserObject();
         orgUnitInfo = osi.getUnit();
         orgUnitList.add(orgUnitInfo.getId().toString());
       }
       traceNode(node, orgUnitList);
     }
   }
   
 
   protected void prmtBgForm_dataChanged(DataChangeEvent e)
     throws Exception
   {
     super.prmtBgForm_dataChanged(e);
     
     if ((this.isAddNew) && (this.prmtBgForm.getData() != null)) {
       BgFormInfo bgFormInfo = (BgFormInfo)this.prmtBgForm.getData();
       initCostCenter(bgFormInfo.getId());
     }
   }
   
 
   protected void checkDataValid(Object newValue, Object oldValue)
   {
     if (newValue != null) {
       BgFormInfo bgFormInfo = (BgFormInfo)newValue;
       if ((oldValue != null) && (bgFormInfo.getId().equals(((BgFormInfo)oldValue).getId()))) {
         return;
       }
       try
       {
         IBgDisCountForm bgCollectImpl = BgDisCountFormFactory.getRemoteInstance();
         boolean hasBgDataFormula = bgCollectImpl.hasBgDataFomular(bgFormInfo.getId());
         if (!hasBgDataFormula) {
           MsgBox.showError(EASResource.getString("com.kingdee.eas.ma.budget.BudgetResource", "BGFORMSPECIFIEDHASNOBGFORMULA"));
           this.prmtBgForm.setData(oldValue);
           SysUtil.abort();
         }
       /*  try {
           if (BgFormFacadeFactory.getRemoteInstance().checkFormIsAdjust(bgFormInfo.getId()) != null) {
             MsgBox.showError(EASResource.getString("com.kingdee.eas.ma.budget.BudgetResource", "BgFormInAdjustNoCollect"));
             this.prmtBgForm.setData(oldValue);
             SysUtil.abort();
           }
         } catch (BgFormException e1) {
           MsgBox.showError(EASResource.getString("com.kingdee.eas.ma.budget.BudgetResource", "BgFormInAdjustNoCollect"));
           this.prmtBgForm.setData(oldValue);
           SysUtil.abort();
         }*/
       }
       catch (Exception e) {
         handleException(e);
       }
     }
   }
   
   private void loadCostCenterStatus()
   {
     if ((!getOprtState().equals(OprtState.ADDNEW)) && 
       (this.editData != null) && (this.editData.getRefBgForms() != null)) {
       RefDisCountBgFormCollection children = this.editData.getRefBgForms();
       
       Map orgUnitMap = new HashMap();
       int i = 0; for (int count = children.size(); i < count; i++) {
         orgUnitMap.put(children.get(i).getBgForm().getOrgUnit().getId().toString(), null);
       }
       traceNode((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot(), orgUnitMap);
       
       BgNSHelper.objClear(orgUnitMap);
     }
   }
   
 
 
 
 
   public void loadFields()
   {
     super.loadFields();
     
     if (this.editData.getBgForm() != null) {
       BgSchemeInfo bgSchemaInfo = this.editData.getBgForm().getBgScheme();
       BgFormHelper.setSelectObject(this.comBgScheme, bgSchemaInfo);
       
       this.prmtBgForm.setData(this.editData.getBgForm());
       loadCostCenterStatus();
     }
   }
   
   protected void beforeStoreFields(ActionEvent ex)
   {
     try {
       super.beforeStoreFields(ex);
     }
     catch (Exception e1) {}
     try
     {
       this.editData.setBgForm((BgFormInfo)this.prmtBgForm.getData());
       if (this.editData.getBgForm() == null) {
         BgCollectException e = new BgCollectException(BgCollectException.HASNOBGFORMINBGCOLLECTFORM);
         handleException(e);
         SysUtil.abort();
       }
       
       checkTraceNode((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot());
       
       this.editData.getRefBgForms().clear();
       List ouIdList = new ArrayList();
       
       DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot();
       traceNode(root, ouIdList);
       
       IRefDisCountBgForm iRefBgForm = RefDisCountBgFormFactory.getRemoteInstance();
       RefDisCountBgFormCollection refBgFormCols = iRefBgForm.loadRefBgFormCollectionByBgForm(this.editData.getBgForm(), ouIdList);
       int i = 0; for (int count = refBgFormCols.size(); i < count; i++) {
         RefDisCountBgFormInfo child = refBgFormCols.get(i);
         child.setBgCollect(this.editData);
       }
       this.editData.getRefBgForms().addCollection(refBgFormCols);
       HashMap otherMap = new HashMap();
       otherMap.put("orgList", ouIdList);
       this.editData.setOrgList(BgHelper.storeHashMapToByte(otherMap));
       BgNSHelper.objClear(ouIdList);
     } catch (Exception e) {
       handleException(e);
       SysUtil.abort();
     }
     
     if (OprtState.ADDNEW.equals(getOprtState())) {
       setOprtState(OprtState.EDIT);
     }
   }
   
   protected IBgDisCountForm getInterface() throws BOSException {
     if (this.ibgCollectForm == null)
       this.ibgCollectForm = BgDisCountFormFactory.getRemoteInstance();
     return this.ibgCollectForm;
   }
   
   public void actionSubmit_actionPerformed(ActionEvent e)
     throws Exception
   {
     this.chkMenuItemSubmitAndAddNew.setVisible(false);
     DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot();
     checkSelectCostCenter(root);
     boolean isDebug = false;
     beforeStoreFields(null);
     if (isDebug) {
       super.actionSubmit_actionPerformed(e);
     } else {
       this.isAddNew = false;
       
 
 
 
 
 
 
 
 
 
 
 
       super.actionSubmit_actionPerformed(e);
       
 
       this.isCancel = false;
       
       this.returnData = new HashMap();
       this.returnData.put("getData", getData());
       this.returnData.put("isCancel", new Boolean(isCancel()));
       this.returnData.put("setUITitle", ((BgSchemeInfo)this.comBgScheme.getSelectedItem()).getName() + " " + ((BgFormInfo)this.prmtBgForm.getData()).getName());
       
       getInterface().addNewData(getColIdsMap(this.editData));
       
       BgFormHelper.refreshBgFormList(this);
     }
   }
   
   private Map getColIdsMap(BgDisCountFormInfo editData) {
     Map decIds = new HashMap();
     Map refIds = new HashMap();
     decIds.put("colId", editData.getId().toString());
     decIds.put("mainFormId", editData.getBgForm().getId().toString());
     RefDisCountBgFormCollection coll = editData.getRefBgForms();
     for (int i = 0; i < coll.size(); i++) {
       refIds.put(coll.get(i).getBgForm().getId().toString(), null);
     }
     decIds.put("refIds", refIds);
     return decIds;
   }
   
   protected IObjectValue createNewData()
   {
     BgDisCountFormInfo bgCollectFormInfo = null;
     try {
       bgCollectFormInfo = new BgDisCountFormInfo();
     } catch (Exception e) {
       handleException(e);
     }
     bgCollectFormInfo.setState(BgCollectStateEnum.NEWCOLLECTED);
     bgCollectFormInfo.setOrgUnit(getCurrentFullOrgUnitInfoUsedInBg());
     return bgCollectFormInfo;
   }
   
   protected ICoreBase getBizInterface() throws Exception {
     return BgDisCountFormFactory.getRemoteInstance();
   }
   
   protected void comBgScheme_itemStateChanged(ItemEvent e)
     throws Exception
   {
     if (this.comBgScheme.getSelectedItem() != null) {
       BgSchemeInfo bgSchemaInfo = (BgSchemeInfo)this.comBgScheme.getSelectedItem();
       if (this.prmtBgForm.getData() != null) {
         BgFormInfo bgFormInfo = (BgFormInfo)this.prmtBgForm.getData();
         if (!bgFormInfo.getBgScheme().getId().toString().equals(bgSchemaInfo.getId().toString())) {
           this.prmtBgForm.setData(null);
         }
       }
       
       SelectorItemCollection sic = new SelectorItemCollection();
       sic.add(new SelectorItemInfo("id"));
       sic.add(new SelectorItemInfo("name"));
       sic.add(new SelectorItemInfo("number"));
       sic.add(new SelectorItemInfo("lastUpdateTime"));
       sic.add(new SelectorItemInfo("bgPeriod.id"));
       sic.add(new SelectorItemInfo("bgPeriod.number"));
       sic.add(new SelectorItemInfo("bgPeriod.name"));
       sic.add(new SelectorItemInfo("orgUnit.id"));
       sic.add(new SelectorItemInfo("orgUnit.number"));
       sic.add(new SelectorItemInfo("orgUnit.name"));
       sic.add(new SelectorItemInfo("bgScheme.id"));
       sic.add(new SelectorItemInfo("bgScheme.number"));
       sic.add(new SelectorItemInfo("bgScheme.name"));
       sic.add(new SelectorItemInfo("bgTemplate.id"));
       sic.add(new SelectorItemInfo("bgTemplate.number"));
       sic.add(new SelectorItemInfo("bgTemplate.name"));
       sic.add(new SelectorItemInfo("bgTemplate.formType"));
       sic.add(new SelectorItemInfo("currency.id"));
       sic.add(new SelectorItemInfo("state"));
       EntityViewInfo view = new EntityViewInfo();
       FilterInfo filter = new FilterInfo();
       filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", getCurrentFullOrgUnitInfoUsedInBg().getId().toString()));
       filter.getFilterItems().add(new FilterItemInfo("bgScheme.id", bgSchemaInfo.getId().toString()));
       filter.getFilterItems().add(new FilterItemInfo("state", BgFormStateEnum.CERTIFICATE_VALUE));
       filter.getFilterItems().add(new FilterItemInfo("bgTemplate.formType", Integer.valueOf(4), CompareType.NOTEQUALS));
       filter.setMaskString("#0 and #1 and #2 and #3");
       view.getSelector().addObjectCollection(sic);
       if (BgSHelper.isBgPermissionUsed()) {
         String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
         Set orgSet = new HashSet();
         orgSet.add(getCurrentFullOrgUnitInfoUsedInBg().getId().toString());
         filter = BgPermissionHelper.addBgFormPermission(userId, orgSet, filter);
       }
       
 
       Set formType = new HashSet();
       formType.add(new Integer(2));
       formType.add(new Integer(3));
       filter.getFilterItems().add(new FilterItemInfo("bgTemplate.formType", formType, CompareType.NOTINCLUDE));
       filter.setMaskString(filter.getMaskString() + " and #" + (filter.getFilterItems().size() - 1));
       filter.getFilterItems().add(new FilterItemInfo("id", "select fadjustId from t_bg_bgform where fstate = 4", CompareType.NOTINNER));
       if (!StringUtils.isEmpty(filter.getMaskString())) {
         filter.setMaskString(filter.getMaskString() + " and #" + (filter.getFilterItems().size() - 1));
       }
       view.setFilter(filter);
       this.prmtBgForm.setEntityViewInfo(view);
       this.prmtBgForm.setRefresh(true);
       
       DefaultKingdeeTreeNode node = null;
       DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot();
       for (int i = 0; i < root.getChildCount(); i++) {
         node = (DefaultKingdeeTreeNode)root.getChildAt(i);
         node.setChecked(false);
         traceNode(node);
       }
       this.tblTree.repaint();
     }
   }
   
   protected FullOrgUnitInfo getCurrentFullOrgUnitInfoUsedInBg()
   {
     CostCenterOrgUnitInfo costCenterInfo = getCurrentCostCenterOrgUnitInfo();
     if (costCenterInfo != null) {
       FullOrgUnitInfo ouInfo = new FullOrgUnitInfo();
       ouInfo.setId(BOSUuid.read(costCenterInfo.getId().toString()));
       return ouInfo;
     }
     
     return null;
   }
   
   protected CostCenterOrgUnitInfo getCurrentCostCenterOrgUnitInfo()
   {
     return SysContext.getSysContext().getCurrentCostUnit();
   }
   
 
 
   public SelectorItemCollection getSelectors()
   {
     SelectorItemCollection sic = super.getSelectors();
     sic.add("id");
     sic.add("number");
     sic.add("name");
     sic.add("state");
     sic.add("otherCont");
     sic.add("orgList");
     sic.add("orgUnit.id");
     sic.add("orgUnit.number");
     sic.add("orgUnit.name");
     sic.add("bgForm.id");
     sic.add("bgForm.number");
     sic.add("bgForm.name");
     sic.add("bgForm.bgScheme.id");
     sic.add("bgForm.bgScheme.name");
     sic.add("bgForm.bgScheme.number");
     sic.add("bgForm.orgUnit.id");
     sic.add("bgForm.orgUnit.name");
     sic.add("bgForm.orgUnit.number");
     sic.add("bgForm.orgUnit.longnumber");
     sic.add("bgForm.bgPeriod.name");
     sic.add("refBgForms.id");
     sic.add("refBgForms.comment");
     sic.add("refBgForms.adjustMapData");
     sic.add("refBgForms.bgForm");
     sic.add("refBgForms.bgForm.id");
     sic.add("refBgForms.bgForm.number");
     sic.add("refBgForms.bgForm.name");
     sic.add("refBgForms.bgForm.orgUnit.id");
     sic.add("refBgForms.bgForm.orgUnit.name");
     sic.add("refBgForms.bgForm.orgUnit.number");
     sic.add("refBgForms.bgForm.orgUnit.longnumber");
     sic.add("refBgForms.bgForm.bgScheme.id");
     sic.add("refBgForms.bgForm.bgScheme.name");
     sic.add("refBgForms.bgForm.bgScheme.number");
     return sic;
   }
   
   public boolean isModify() {
     return false;
   }
   
   public boolean isCancel() {
     return this.isCancel;
   }
   
   public void setCancel(boolean isCancel) {
     this.isCancel = isCancel;
   }
   
   public BgDisCountFormInfo getData() {
     return this.editData;
   }
   
   protected void btnAddComment_actionPerformed(ActionEvent e) throws Exception {
     UIContext uiContext = new UIContext(this);
     uiContext.put("BGFORM", this.editData.getRefBgForms().get(0).getBgForm());
     IUIWindow uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(BgCollectFormCommentCommitDlgUI.class.getName(), uiContext);
     
 
     uiWindow.show();
   }
   
   protected void btnViewComment_actionPerformed(ActionEvent e) throws Exception
   {
     UIContext uiContext = new UIContext(this);
     uiContext.put("BGFORM", this.editData.getRefBgForms().get(0).getBgForm());
     IUIWindow uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(BgCollectFormCommentViewDlgUI.class.getName(), uiContext);
     
 
     uiWindow.show();
   }
   
   protected void verifyInput(ActionEvent e) throws Exception {
     super.verifyInput(e);
   }
   
   private void checkSelectCostCenter(DefaultKingdeeTreeNode nood) {
     for (int i = 0; i < nood.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)nood.getChildAt(i);
       OrgStructureInfo osi = (OrgStructureInfo)node.getUserObject();
       FullOrgUnitInfo orgUnitInfo = osi.getUnit();
       
       if ((node.isChecked()) && (!this.orgUnitMap.containsKey(orgUnitInfo.getId().toString()))) {
         MsgBox.showWarning(this,"选择的组织没有已认可的预算表，不能保存！");
         SysUtil.abort();
       }
       checkSelectCostCenter(node);
     }
   }
   
   protected void disposeUIWindow() {
     BgNSHelper.objClear(this.orgUnitMap);
     super.disposeUIWindow();
   }
   
 
 
   public void clear()
     throws Exception
   {
     this.prmtBgForm.setData(null);
     this.comBgScheme.setSelectedIndex(-1);
     DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot();
     disSelectedTree(root);
     root.setChecked(true);
     this.tblTree.repaint();
   }
   
   private void disSelectedTree(DefaultKingdeeTreeNode node) {
     if (node == null)
       return;
     for (int i = 0; i < node.getChildCount(); i++) {
       DefaultKingdeeTreeNode subNode = (DefaultKingdeeTreeNode)node.getChildAt(i);
       node.setChecked(false);
       disSelectedTree(subNode);
     }
   }
   
   public Object saveSolution() throws Exception
   {
     if (this.prmtBgForm.getData() == null) {
       BgCollectException e = new BgCollectException(BgCollectException.HASNOBGFORMINBGCOLLECTFORM);
       handleException(e);
       SysUtil.abort();
     }
     setOrgs();
     Map saveData = new HashMap();
     saveData.put("bgSchemeID", ((BgSchemeInfo)this.comBgScheme.getSelectedItem()).getId().toString());
     
     saveData.put("bgFormID", ((BgFormInfo)this.prmtBgForm.getData()).getId().toString());
     saveData.put("orgUnits", this.orgUnits);
     return saveData;
   }
   
   public void reloadSolution(Object o) throws Exception
   {
     try {
       if ((o instanceof Map)) {
         Map loadData = (HashMap)o;
         if ((loadData != null) && (loadData.size() > 0) && 
           (!loadDataToUI(loadData))) {
           MsgBox.showError(this, EASResource.getString("com.kingdee.eas.ma.budget.BudgetResource", "errorScheme"));
         }
       }
     }
     catch (Exception e) {
       MsgBox.showError(this, EASResource.getString("com.kingdee.eas.ma.budget.BudgetResource", "errorScheme"));
     }
   }
   
   private boolean loadDataToUI(Map loadData) throws Exception
   {
     this.comBgScheme.setSelectedItem(null);
     this.prmtBgForm.setValue(null);
     inputOrgUnitTraceNode((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot(), null);
     this.tblTree.requestFocus();
     if ((loadData.get("bgSchemeID") == null) || (loadData.get("bgFormID") == null) || (loadData.get("orgUnits") == null) || (((Vector)loadData.get("orgUnits")).size() < 2))
     {
       return false;
     }
     
     for (int i = 0; i < this.comBgScheme.getItemCount(); i++) {
       if (loadData.get("bgSchemeID").equals(((BgSchemeInfo)this.comBgScheme.getItemAt(i)).getId().toString()))
       {
         this.comBgScheme.setSelectedIndex(i);
       }
     }
     if (this.comBgScheme.getSelectedItem() == null) {
       return false;
     }
     comBgScheme_itemStateChanged(null);
     if (!inputBgForm((String)loadData.get("bgFormID"))) {
       return false;
     }
     inputOrgUnit((Vector)loadData.get("orgUnits"));
     
     return true;
   }
   
 
   private void inputOrgUnit(Vector orgUnit)
   {
     ((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot()).setChecked(true);
     inputOrgUnitTraceNode((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot(), orgUnit);
     this.tblTree.requestFocus();
   }
   
 
   private void inputOrgUnitTraceNode(DefaultKingdeeTreeNode root, Vector orgUnit)
   {
     if ((root == null) || (orgUnit == null) || (orgUnit.isEmpty()))
       return;
     Map orgIds = new HashMap();
     for (int i = 0; i < orgUnit.size(); i++) {
       orgIds.put(((FullOrgUnitInfo)orgUnit.get(i)).getId().toString(), null);
     }
     OrgStructureInfo osi = null;
     FullOrgUnitInfo orgUnitInfo = null;
     
     for (int i = 0; i < root.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
       
       osi = (OrgStructureInfo)node.getUserObject();
       orgUnitInfo = osi.getUnit();
       
       if ((orgUnit != null) && (orgIds.containsKey(orgUnitInfo.getId().toString()))) {
         node.setChecked(true);
       } else {
         node.setChecked(false);
       }
       inputOrgUnitTraceNode(node, orgUnit);
     }
   }
   
   private boolean inputBgForm(String bgFormID) throws Exception
   {
     EntityViewInfo view = new EntityViewInfo();
     view = this.prmtBgForm.getEntityViewInfo();
     FilterInfo filter = new FilterInfo();
     filter = view.getFilter();
     filter.appendFilterItem("id", bgFormID);
     String maskstring = filter.getMaskString();
     filter.setMaskString(maskstring + " and #" + (filter.getFilterItems().size() - 1));
     view.setFilter(filter);
     BgFormCollection bgFormCol = getBgFormInterface().getBgFormCollection(view);
     if (bgFormCol.size() == 1) {
       this.prmtBgForm.setValue(bgFormCol.get(0));
       prmtBgForm_dataChanged(null);
       return true;
     }
     return false;
   }
   
 
   public void clearSolutionList()
     throws Exception
   {}
   
 
   public AssignSolutionCollection solutionList(AssignSolutionTypeEnum typeEnum)
     throws Exception
   {
     StringBuffer oql = new StringBuffer("where solutionType=" + typeEnum.getValue());
     String objId = null;String operateType = null;
     Map uicontext = getUIContext();
     if (uicontext.get("ID") != null) {
       objId = (String)uicontext.get("ID");
     }
     if (uicontext.get("operateType") != null) {
       operateType = (String)uicontext.get("operateType");
     }
     if (typeEnum.getValue() == 0) {
       String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
       oql.append(" and cuid='" + cuid + "'");
     }
     else if (typeEnum.getValue() == 2) {
       String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
       oql.append(" and orgId='" + orgId + "'");
     }
     else if (typeEnum.getValue() == 1) {
       String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
       oql.append(" and userId='" + userId + "' ");
     }
     if (objId != null) {
       oql.append(" and relativeObjId='" + objId + "' ");
     }
     if (operateType != null) {
       oql.append(" and oprateObjType='" + operateType + "' ");
     }
     
     if (uicontext.get("selfPanelUiName") != null) {
       oql.append(" and uiName='" + (String)uicontext.get("selfPanelUiName") + "' ");
     }
     
     oql.append(" order by isDefaultsolution desc");
     
     AssignSolutionCollection asSolutionCol = AssignSolutionFactory.getRemoteInstance().getAssignSolutionCollection(oql.toString());
     return asSolutionCol;
   }
   
   public boolean conferm(Object invokeUi) throws Exception
   {
     ActionEvent e = new ActionEvent(this.btnConfirm, 1001, "");
     actionSubmit_actionPerformed(e);
     return true;
   }
   
   public boolean cancel() throws Exception
   {
     return true;
   }
   
   public Map getReturnData()
     throws Exception
   {
     return this.returnData;
   }
   
 
 
   public void setConfermLabel(String confermLabel) {}
   
 
 
   public AssignSolutionInfo getDefaultSoltuion()
     throws Exception
   {
     return null;
   }
   
   public void setOrgs() {
     this.orgUnits = new Vector();
     try {
       this.orgUnits.add(((OrgStructureInfo)((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot()).getUserObject()).getUnit());
       orgs((DefaultKingdeeTreeNode)this.tblTree.getModel().getRoot());
     } catch (Exception e) {
       MsgBox.showError(this, EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "errorData"));
       
       SysUtil.abort();
     }
   }
   
   public Vector getOrgs() {
     return this.orgUnits;
   }
   
   protected void orgs(DefaultKingdeeTreeNode root) throws Exception {
     if (root == null) {
       return;
     }
     OrgStructureInfo osi = null;
     FullOrgUnitInfo orgUnitInfo = null;
     for (int i = 0; i < root.getChildCount(); i++) {
       DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
       if (node.isChecked()) {
         osi = (OrgStructureInfo)node.getUserObject();
         orgUnitInfo = osi.getUnit();
         this.orgUnits.add(orgUnitInfo);
       }
       orgs(node);
     }
   }
   
 
   public String getNewConfermLabel()
   {
     return null;
   }
   
   private boolean addBgFormToChildren(String orgId) throws Exception {
     BgFormInfo bgFormInfo = null;
     if (this.prmtBgForm.getData() != null) {
       bgFormInfo = (BgFormInfo)this.prmtBgForm.getData();
     }
     try {
       bgFormInfo = getBgFormInterface().getBgFormInfo(new ObjectUuidPK(bgFormInfo.getId().toString()), getFormSelectors());
     } catch (Exception e1) {
       throw e1;
     }
     try
     {
       IBgDisCountForm iBgCollForm = BgDisCountFormFactory.getRemoteInstance();
       return iBgCollForm.addBgFormToChildren(orgId, bgFormInfo);
     } catch (Exception e) {
       throw e;
     }
   }
   
   private SelectorItemCollection getFormSelectors() {
     SelectorItemCollection sic = new SelectorItemCollection();
     sic.add(new SelectorItemInfo("id"));
     sic.add(new SelectorItemInfo("name"));
     sic.add(new SelectorItemInfo("number"));
     sic.add(new SelectorItemInfo("lastUpdateTime"));
     sic.add(new SelectorItemInfo("bgPeriod.*"));
     sic.add(new SelectorItemInfo("orgUnit.*"));
     sic.add(new SelectorItemInfo("bgScheme.*"));
     sic.add(new SelectorItemInfo("bgTemplate.*"));
     sic.add(new SelectorItemInfo("currency.*"));
     sic.add(new SelectorItemInfo("state"));
     return sic;
   }
 }
 