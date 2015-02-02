/* 
 * @(#) BgCollectFormListUI.java 
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.ma.budget.client;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTRange;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.eas.base.message.client.MessageCenterUI;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUI;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gr.cslrpt.client.ShowEditorPanelUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.budget.BgCollectStateEnum;
import com.kingdee.eas.ma.budget.BgDisCountFormFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.eas.ma.budget.BgExamineFacadeFactory;
import com.kingdee.eas.ma.budget.BgExamineHelper;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormHelper;
import com.kingdee.eas.ma.budget.BgPeriodEnum;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.IBgDisCountForm;
import com.kingdee.eas.ma.budget.IBgExamineFacade;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.BgPermissionHelper;
import com.kingdee.eas.ma.nbudget.client.BgParamCHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:预算汇编序时簿
 * @author hua_yang  date:2008-10-20 <p>
 * @version EAS5.1
 */
public class BgDisCountFormListUI extends AbstractBgDisCountFormListUI{
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = -1904999952857569242L;
	
	private static final Logger logger = CoreUIObject.getLogger(BgDisCountFormListUI.class);
    private static final String SOURCESTR = "com.kingdee.eas.ma.budget.client.BgCollectResource";
    
    protected LongTimeDialog dialog = null;
    protected IBgForm ibgForm = null;
    protected IBgExamineFacade ibgExamineFacade = null;
    
    protected BgDisCountFormInfo bgDisCountInfo = null;
    
    private String xNum = null;
    
    public BgDisCountFormListUI() throws Exception {
		super();
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	tblMain.setRefresh(false);
    	tblMain.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
    	int schemaIndex = tblMain.getColumnIndex("bgScheme.name");
    	for(int i = 0, count = tblMain.getColumnCount(); i < count; i++){
    		if(i == schemaIndex || i == 0){
    			tblMain.getColumn(i).setMergeable(true);
    		}
    		else{
    			tblMain.getColumn(i).setMergeable(false);
    		}
    	}
    	tblMain.setRefresh(true);
    	//tblMain.reLayoutAndPaint();
     }
    
    protected void initWorkButton() {
        super.initWorkButton();
        
        btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        
        menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        
        btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        
        menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        
        btnViewFlow.setEnabled(true);
        btnViewFlow.setVisible(true);
        itemViewFlow.setEnabled(true);
        itemViewFlow.setVisible(true);
        btnViewFlow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
		itemViewFlow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
    }
    
    /**
     * 描述：修改前检测状态
     * @author:longl
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
        //先检查是否还存在
        checkSelected();
        String strID = getSelectedKeyValue();
        if (strID != null && strID.trim().length() > 0) {
//            if (BgFormHelper.checkInProInst(strID)) {
//                MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE,
//                        "BGCOLLFORMINWFNOCHANGE"));
//                SysUtil.abort();
//            }

            //为了保证最新数据，应重新取数。
            IBgDisCountForm bgDisCount = BgDisCountFormFactory.getRemoteInstance();
            ObjectUuidPK pk = new ObjectUuidPK(strID);
            //EntityViewInfo evi = new EntityViewInfo();
            SelectorItemCollection sc = new SelectorItemCollection();
            sc.add(new SelectorItemInfo("id"));
            sc.add(new SelectorItemInfo("state"));
            BgDisCountFormInfo aBgDisCountFormInfo = bgDisCount.getBgDisCountFormInfo(pk, sc);
            if (BgCollectStateEnum.SIGNED.equals(aBgDisCountFormInfo.getState()) ) {//已审核
                String strMsg = EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE,"BGCOLLECTFORMNOCHANGE");
                MsgBox.showInfo(this, strMsg);
                return;
            }else   if (BgCollectStateEnum.SIGNED.equals(aBgDisCountFormInfo.getState()) ) {
                MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE,
                "BGCOLLFORMINWFNOCHANGE"));
                SysUtil.abort();
            }
        }

        super.actionEdit_actionPerformed(e);
    }
    
    public void actionViewFlow_actionPerformed(ActionEvent e) throws Exception {
    	super.actionViewFlow_actionPerformed(e);
		checkSelected();
		BgClientHelper.viewFlow((Component) this, getSelectedKeyValue());
    }

    /**
     * 描述：删除前检测状态
     * @author:longl
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
        checkSelected();
        
        String strID = getSelectedKeyValue();
        if (strID != null && strID.trim().length() > 0) {
            if (BgFormHelper.checkInProInst(strID)) {
                MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOREMOVE"));
                SysUtil.abort();
            }

            //为了保证最新数据，应重新取数。
            IBgDisCountForm bgDisCount = BgDisCountFormFactory.getRemoteInstance();
            ObjectUuidPK pk = new ObjectUuidPK(strID);
            SelectorItemCollection sc = new SelectorItemCollection();
            sc.add(new SelectorItemInfo("id"));
            sc.add(new SelectorItemInfo("state"));
            BgDisCountFormInfo aBgDisCountFormInfo = bgDisCount.getBgDisCountFormInfo(pk, sc);
            if (BgCollectStateEnum.SIGNED.equals(aBgDisCountFormInfo.getState()) ) {//已审核
                String strMsg = EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE,"BGCOLLECTFORMNODELETE");
                MsgBox.showInfo(this, strMsg);
                return;
            }
            else if (BgCollectStateEnum.AUDIT.equals(aBgDisCountFormInfo.getState())) {
            	MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOREMOVE"));
            	SysUtil.abort();
            }
        }

        super.actionRemove_actionPerformed(e);
        
        BgFormHelper.refreshBgFormList(this);
    }

    protected String getEditUIName() {
        return BgDisCountFormEditUI.class.getName();
    }

//	protected String getEditUIModal() {
//		return UIFactoryName.MODEL;
//	}
    
    protected ICoreBase getBizInterface() throws Exception {
    	return BgDisCountFormFactory.getRemoteInstance();
    }
    
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentCostUnit().getId().toString(), CompareType.EQUALS));
        //add by wangxinsheng  add月度的过滤
        if(BgParamCHelper.isFodian()) {
        	filter.getFilterItems().add(new FilterItemInfo("BGSCHEME.PERIOD",new Integer(BgPeriodEnum.MONTH_VALUE),CompareType.NOTEQUALS));
        } else {
        	filter.getFilterItems().add(new FilterItemInfo("BGSCHEME.PERIOD",new Integer(-1),CompareType.NOTEQUALS));
        }
		filter.setMaskString("#0 and #1");
        if(BgSHelper.isBgPermissionUsed()){
        	Set org = new HashSet();
        	org.add(SysContext.getSysContext().getCurrentCostUnit().getId().toString());
			String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();			
			filter = BgPermissionHelper.addBgFormPermissionForDec(userId, org, filter);
		}
        return filter;
    }
    

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	
        String bgDisCountId = getSelectedKeyValue();
        if (bgDisCountId != null && bgDisCountId.trim().length() > 0) {
            
            if (BgFormHelper.checkInProInst(bgDisCountId)) {
                MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOAUDIT"));
                SysUtil.abort();
            }
            
            //为了保证最新数据，应重新取数。
            IBgDisCountForm bgDisCount = BgDisCountFormFactory.getRemoteInstance();
            ObjectUuidPK pk = new ObjectUuidPK(bgDisCountId);
            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add("id");
            sic.add("state");
            sic.add("bgForm.id");
            sic.add("refBgForms.id");
            sic.add("refBgForms.bgForm.id");
            bgDisCountInfo = bgDisCount.getBgDisCountFormInfo(pk, sic);
            if (BgCollectStateEnum.SIGNED.equals(bgDisCountInfo.getState()) ) {//已审核
                String strMsg = EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLECTFORMNOAUDIT");
                MsgBox.showInfo(this, strMsg);
                return;
            }
            else  if (BgCollectStateEnum.AUDIT.equals(bgDisCountInfo.getState())) {
                MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOAUDIT"));
                SysUtil.abort();
            }
            
            int re = MsgBox.showConfirm2(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLECTFORMAUDITINFO"));
            if (re == MsgBox.NO || re == MsgBox.CANCEL)
                return;
            

			if (BgParamCHelper.isCompulsionReportCheck()) {
				checkReport(bgDisCountInfo);
			}
			
            
            if (getExamineInterface().canExamine( getBgExmCheckReportId(bgDisCountInfo) )) {
            	xNum = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.returnExamineX(getBgExmCheckReportId(bgDisCountInfo) , this);
    			Window win = SwingUtilities.getWindowAncestor(this);
    			if (win instanceof Frame) {    				
    				dialog = new LongTimeDialog((Frame) win);
    			} else if (win instanceof Dialog) {
    				dialog = new LongTimeDialog((Dialog) win);
    			} else {
    				dialog = null;
    			}
    			
    			if (dialog != null) {
    		        dialog.setLongTimeTask(new ILongTimeTask() {
    		        	public Object exec() throws Exception {
    		    			Object[] result = getExamineInterface().examines( getBgExmCheckReportId(bgDisCountInfo), xNum);
    		    			Map bgExamineCheckMap = new HashMap();
    		    			bgExamineCheckMap.put("BgExamineChecked", result[0]);
    		    			bgExamineCheckMap.put("BgExamineCheckResult", result[1]);
    		    			return bgExamineCheckMap;
    		            }
    		        	
    		            public void afterExec(Object result) throws Exception {
    		            	if (result != null && result instanceof HashMap) {
    		           			openBgExamineViewUI(result, null);
    		           			Map bgExamineCheckMap = (Map) result;
    		           			Boolean bgExCheck = (Boolean) bgExamineCheckMap.get("BgExamineChecked");
    		            		if (bgExCheck.booleanValue()) {
        		            		BgDisCountFormFactory.getRemoteInstance().audit( bgDisCountInfo.getId() );
    							} else {
        		        			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "cantAudit"));
    							}
    		            	} else {
    		        			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "error"));
    		        		}
    		            }
    		        });
    		        dialog.show();
    			}
    		} else {
    			BgDisCountFormFactory.getRemoteInstance().audit(BOSUuid.read(getSelectedKeyValue()));
    		}
            BgFormHelper.refreshBgFormList(this);
        }
    }
    
    protected void checkReport(BgDisCountFormInfo bgCollInfo) throws Exception {
    	boolean isExit = false;
		List check = getFormInterface().reportCheck( bgCollInfo.getId(), getRefBgFormIds(bgCollInfo), getBgExmCheckReportId(bgCollInfo), BgDisCountFormInfo.class.getName() );
//		if (check.get(0).equals(Boolean.FALSE)) {
//			isExit = true;
//			UIContext uiCtx = new UIContext(this);
//			uiCtx.put("Title", EASResource.getString(BgFormHelper.STRRESOURCE, "ReportCheckError"));
//			uiCtx.put("ShowOpen", Boolean.FALSE);
//        	SimpleNotePad.showMessageByModel(uiCtx, check.get(3).toString());
//		}
		if (check.get(0).equals(Boolean.FALSE)) {
			isExit = true;
			UIContext uiContext = new UIContext(this);
			uiContext.put("String",check.get(3).toString());
			uiContext.put("id",this.getUIContext().get("ID"));
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
			.create(ShowEditorPanelUI.class.getName(),uiContext, null);
			uiWindow.show();
		}
		BgNSHelper.objAllClear(check);
		
		/* 如果要求强制通过报表检查 */
		if (isExit)
			SysUtil.abort();
    }
    
    protected BOSUuid[] getRefBgFormIds(BgDisCountFormInfo bgCollInfo) {
    	BOSUuid[] refBgFormIds = null;
		refBgFormIds = new BOSUuid[1 + bgCollInfo.getRefBgForms().size()];
		
		refBgFormIds[0] = bgCollInfo.getId();
    	for(int i=0, n=bgCollInfo.getRefBgForms().size(); i<n; i++) {
    		refBgFormIds[i+1] = bgCollInfo.getRefBgForms().get(i).getId();
    	}
    	return refBgFormIds;
    }
    
    protected BOSUuid[] getBgExmCheckReportId(BgDisCountFormInfo bgCollInfo) {
    	BOSUuid[] bgFormIds = null;
    	RefDisCountBgFormInfo refBgFormInfo = null;

		bgFormIds = new BOSUuid[1 + bgCollInfo.getRefBgForms().size()];
    	bgFormIds[0] = bgCollInfo.getBgForm().getId();
    	for(int i=0, n=bgCollInfo.getRefBgForms().size(); i<n; i++) {
    		refBgFormInfo = bgCollInfo.getRefBgForms().get(i);
    		bgFormIds[i+1] = refBgFormInfo.getBgForm().getId();
    	}
    	return bgFormIds;
    }
    
    protected void openBgExamineViewUI(Object obj, Boolean isModel) throws Exception {

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgExamineHelper.EXAMINE_DATA, obj);
        IUIWindow uiWindow = UIFactory.createUIFactory(openBgExamineViewType(isModel))
        								.create(BgExamineViewUI.class.getName(),
        										uiContext,
        										null,
        										OprtState.VIEW);
        uiWindow.show();
	}
    
    protected String openBgExamineViewType(Boolean isModel) {
		if (isModel != null && isModel.booleanValue())
			return UIFactoryName.MODEL;
		
		if (getUIContext().get(UIContext.OWNER) != null && 
				(getUIContext().get(UIContext.OWNER) instanceof MultiApproveUI || 
				getUIContext().get(UIContext.OWNER) instanceof MessageCenterUI)) {
			return UIFactoryName.MODEL;
		} else {
			return UIFactoryName.NEWWIN;
		}
	}
    
    protected IBgForm getFormInterface() throws BOSException {
    	if (ibgForm == null)
    		ibgForm = BgFormFactory.getRemoteInstance();
    	return ibgForm;
    }
    
    protected IBgExamineFacade getExamineInterface() throws Exception {
    	if (ibgExamineFacade == null)
    		ibgExamineFacade = BgExamineFacadeFactory.getRemoteInstance();
    	return ibgExamineFacade;
    }
    
//    public SelectorItemCollection getSelectors(){
//        SelectorItemCollection sic = super.getSelectors();
//        sic.add("bgForm");
//        sic.add("refBgForms");
//        return sic;
//    }

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		 String bgDisCountId = getSelectedKeyValue();
	        if (bgDisCountId != null && bgDisCountId.trim().length() > 0) {
	        	if (BgFormHelper.checkInProInst(bgDisCountId)) {
	        		MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOSUBMIT"));
	                SysUtil.abort();
	            }
	            
	            //为了保证最新数据，应重新取数。
	            IBgDisCountForm bgDisCount = BgDisCountFormFactory.getRemoteInstance();
	            ObjectUuidPK pk = new ObjectUuidPK(bgDisCountId);
	            SelectorItemCollection sic = new SelectorItemCollection();
	            sic.add("id");
	            sic.add("state");
	            sic.add("bgForm.id");
	            sic.add("bgForm.number");
	            sic.add("refBgForms.id");
	            sic.add("refBgForms.bgForm.id");
	            sic.add("refBgForms.bgForm.number");
	            bgDisCountInfo = bgDisCount.getBgDisCountFormInfo(pk, sic);
	            if (BgCollectStateEnum.SIGNED.equals(bgDisCountInfo.getState()) ) {//已审核
	                String strMsg = EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLECTFORMNOSUBMIT");
	                MsgBox.showInfo(this, strMsg);
	                return;
	            }
	            else  if (BgCollectStateEnum.AUDIT.equals(bgDisCountInfo.getState())) {
	                MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOSUBMIT"));
	                SysUtil.abort();
	            }
	            
	            int re = MsgBox.showConfirm2(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLECTFORMSUBMITINFO"));
	            if (re == MsgBox.NO || re == MsgBox.CANCEL)
	                return;
	            

				if (BgParamCHelper.isCompulsionReportCheck()) {
					checkReport(bgDisCountInfo);
				}
				
				if (getExamineInterface().canExamine( getBgExmCheckReportId(bgDisCountInfo) )) {
					xNum = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.returnExamineX(getBgExmCheckReportId(bgDisCountInfo), this);
					Window win = SwingUtilities.getWindowAncestor(this);
					if (win instanceof Frame) {
						dialog = new LongTimeDialog((Frame) win);
					} else if (win instanceof Dialog) {
						dialog = new LongTimeDialog((Dialog) win);
					} else {
						dialog = null;
					}
					
					if (dialog != null) {
				        dialog.setLongTimeTask(new ILongTimeTask() {
				        	public Object exec() throws Exception {
				    			Object[] result = getExamineInterface().examines( getBgExmCheckReportId(bgDisCountInfo), xNum);
				    			Map bgExamineCheckMap = new HashMap();
				    			bgExamineCheckMap.put("BgExamineChecked", result[0]);
				    			bgExamineCheckMap.put("BgExamineCheckResult", result[1]);
				    			return bgExamineCheckMap;
				            }
				        	
				            public void afterExec(Object result) throws Exception {
				            	if (result != null && result instanceof HashMap) {
				           			openBgExamineViewUI(result, null);
				           			Map bgExamineCheckMap = (Map) result;
				           			Boolean bgExCheck = (Boolean) bgExamineCheckMap.get("BgExamineChecked");
									if (bgExCheck.booleanValue()) {
					           	        BgDisCountFormFactory.getRemoteInstance().auditWF(bgDisCountInfo.getId());
					           	        MsgBox.showInfo(getMessage("isSubmit"));
									} else {
										MsgBox.showWarning(getMessage("submitWorkDefeat"));
									}
				            	} else {
				        			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "error"));
				        		}
				            }
				        });
				        dialog.show();
					}
				} else {
			        BgDisCountFormFactory.getRemoteInstance().auditWF(bgDisCountInfo.getId());
			        MsgBox.showInfo(this, getMessage("isSubmit"));
				}
				
	        }
	}
    
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		int rIndex=tblMain.getSelectManager().getActiveRowIndex();
		
		/*
		 * modify by bowei_duan at 2012-3-26
		 * maybe custom select two or more
		 * first is edited.but second is not edit.
		 * so we need deal 
		 * if select more rowindex set first row ! 
		 */
		KDTRange range = tblMain.getSelectManager().toRange();
		if(range == null)
			return ;
		
		KDTSelectBlock select = (KDTSelectBlock)range.get(KDTRange.ROW_DIRECT);
		if(select == null)
			return ;
		rIndex = select.getBeginRow() ;
		
		boolean isEnable=false;
		if (rIndex>=0&&tblMain.getCell(rIndex, "state")!=null && tblMain.getCell(rIndex, "state").getValue() instanceof BizEnumValueDTO){
			BizEnumValueDTO dto=(BizEnumValueDTO)tblMain.getCell(rIndex, "state").getValue();
			if (!dto.getValue().equals(BgCollectStateEnum.SIGNED_VALUE+"")){
				isEnable=true;
			}
		}
		actionEdit.setEnabled(isEnable);
		actionRemove.setEnabled(isEnable);
		actionAudit.setEnabled(isEnable);
		actionSubmit.setEnabled(isEnable);
	}
	
	protected String getMessage(String key) {
    	return EASResource.getString("com.kingdee.eas.ma.budget.client.BgCollectResource", key);
    }
	
	protected String[] getLocateNames()
    {
        String[] locateNames = new String[2];
        locateNames[0] = "bgForm.number";
        locateNames[1] = "bgForm.name";
        return locateNames;
    }
    
}



