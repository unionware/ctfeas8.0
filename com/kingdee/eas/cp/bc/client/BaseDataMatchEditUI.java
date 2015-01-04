/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.cp.bc.BaseDataEnum;
import com.kingdee.eas.cp.bc.BaseDataMatchCollection;
import com.kingdee.eas.cp.bc.BaseDataMatchEntryInfo;
import com.kingdee.eas.cp.bc.BaseDataMatchFactory;
import com.kingdee.eas.cp.bc.Bc2K3VoucherFacadeFactory;
import com.kingdee.eas.cp.bc.BizAccountBillCollection;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BaseDataMatchEditUI extends AbstractBaseDataMatchEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BaseDataMatchEditUI.class);
    private Map<BaseDataEnum,String> entryEASTitleNameMap;
    private Map<BaseDataEnum,String> entryK3TitleNameMap;
    
    private BaseDataEnum types;
    /**
     * output class constructor
     */
    public BaseDataMatchEditUI() throws Exception
    {
        super();
        initEntryTitle();
        
    }
    
    
    private void initEntryTitle(){
    	entryEASTitleNameMap = new HashMap<BaseDataEnum,String>();
    	entryK3TitleNameMap = new HashMap<BaseDataEnum,String>();
    	
    	entryEASTitleNameMap.put(BaseDataEnum.ACCOUNT, "科目编码");
    	entryEASTitleNameMap.put(BaseDataEnum.ORGUNIT, "财务组织编码");
    	entryEASTitleNameMap.put(BaseDataEnum.DEPARTMENT, "成本中心编码");
    	entryEASTitleNameMap.put(BaseDataEnum.PERSON, "职员编码");
    	entryEASTitleNameMap.put(BaseDataEnum.PROJECT, "项目编码");
    	
    	
    	entryK3TitleNameMap.put(BaseDataEnum.ACCOUNT, "科目编码");
    	entryK3TitleNameMap.put(BaseDataEnum.ORGUNIT, "组织机构编码");
    	entryK3TitleNameMap.put(BaseDataEnum.DEPARTMENT, "部门编码");
    	entryK3TitleNameMap.put(BaseDataEnum.PERSON, "职员编码");
    	entryK3TitleNameMap.put(BaseDataEnum.PROJECT, "项目编码");
    }
    
    private void setEntryTitle(BaseDataEnum bdEnum){
    	if(bdEnum==null){
    		return ;
    	}
    	kdtEntrys.getHeadRow(0).getCell("easNum").setValue("EAS"+entryEASTitleNameMap.get(bdEnum));
    	kdtEntrys.getHeadRow(0).getCell("k3Num").setValue("K3"+entryK3TitleNameMap.get(bdEnum));
    }
    
    
    private void initContextId() throws BOSException{
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo(); 
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("types",types.getValue()));
    	
    	BaseDataMatchCollection coll =   BaseDataMatchFactory.getRemoteInstance().getBaseDataMatchCollection(view);
    	if(coll==null){
    		throw new BOSException("未初始化基础资料匹配信息");
    	}
    	this.getUIContext().put("ID", coll.get(0).getId().toString());
    }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	
    	initContextId();
    	super.onLoad();
    	this.actionAuditResult.setVisible(false);
    	actionAttachment.setVisible(false);
    	setEntryTitle(types);
    	
    }
    
    
    protected void initTypes(BaseDataEnum types){
    	this.types =types; 
    }
    
    
    
    
    
    @Override
    protected void beforeStoreFields(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.beforeStoreFields(e);
    }
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	
    	super.verifyInput(e);
    	ZDFClientVerifyHelper.verifyEmpty(this,kdtEntrys);
    	ZDFClientVerifyHelper.verifyInput(this, kdtEntrys, "easNum");
    	ZDFClientVerifyHelper.verifyInput(this, kdtEntrys, "k3Num");
    	 checkDumpNum("easNum");
    	 checkDumpNum("k3Num");
    	 
    	 
    		
    }
    private void  checkDumpNum(String colName){
    	Set<String> numSet = new HashSet<String>();
    	int count = kdtEntrys.getRowCount();
    	int colIndex = kdtEntrys.getColumnIndex(colName);
    	String temp =null;
    	for(int i=0;i<count;i++){
    		temp = (String)kdtEntrys.getCell(i, colName).getValue();
    		if(numSet.contains(temp)){
    			  kdtEntrys.getEditManager().editCellAt(i, colIndex);

    		      String headValue = (String)kdtEntrys.getHeadRow(0).getCell(colIndex).getValue();
    		      String msg = headValue + "'"+temp+"' 重复" ;
    		      msg = msg.replaceAll("#", " " + headValue + " ");
    		      MsgBox.showWarning(this, msg);
    		      SysUtil.abort();
    		}else{
    			numSet.add(temp);
    		}
    	}
    }
    
    
    @Override
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionAddLine_actionPerformed(e);
    }
    @Override
    public void actionRemoveLine_actionPerformed(ActionEvent arg0)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionRemoveLine_actionPerformed(arg0);
    }
    

    @Override
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionSave_actionPerformed(e);
    }
    
    @Override
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionSubmit_actionPerformed(e);
    }
    
    @Override
    public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionEdit_actionPerformed(arg0);
    }
    
    @Override
    protected void initWorkButton() {
    	super.initWorkButton();
    	actionAddNew.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionWorkFlowG.setVisible(false);
    	
    	actionSubmit.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionRemove.setVisible(false);
    	
    	
    	actionPrint.setVisible(false);
    	actionPrintPreview.setVisible(false);
    	
    	this.actionFirst.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	actionNextPerson.setVisible(false);
    	actionMultiapprove.setVisible(false);
    	
    	this.menuBiz.setVisible(false);
    	this.menuEdit.setVisible(false);
    	this.menuView.setVisible(false);
    	this.menuWorkflow.setVisible(false);
    	this.menuSubmitOption.setVisible(false);
    	
    	this.actionImportData.setEnabled(true);
    	
    }
    
    
    public void actionImportData_actionPerformed(ActionEvent e)
    		throws Exception {
    	/*BizAccountBillCollection coll =   BizAccountBillFactory.getRemoteInstance().getBizAccountBillCollection();
    	for(int i=0;i<coll.size();i++){
    		if(StateEnum.CHECKED.equals(coll.get(i).getState())){
    			//CreateK3PayNoticeBillFacadeFactory.getRemoteInstance().createByBizAccountBill(coll.get(0).getId());
    			Bc2K3VoucherFacadeFactory.getRemoteInstance().crVouchByBizAccount(BOSUuid.read("GizFL2WeRXiy8gMenXKPVEpE9J8="));
    			break;
    		}
    	}*/
    	//CreateK3PayNoticeBillFacadeFactory.getRemoteInstance().createByDailyLoanBill(BOSUuid.read("Al0fboWXS066FwC/5HDktIEQqrI="));
    	
    	
    	//Bc2K3VoucherFacadeFactory.getRemoteInstance().crVouchByBizAccount("BXD-Dec 24, 2014 2:34-000090");
    	//Bc2K3VoucherFacadeFactory.getRemoteInstance().crVouchByBizAccount(BOSUuid.read("GizFL2WeRXiy8gMenXKPVEpE9J8="));
    	/*DailyLoanBillCollection coll =   DailyLoanBillFactory.getRemoteInstance().getDailyLoanBillCollection();
    	for(int i=0;i<coll.size();i++){
    		if(StateEnum.CHECKED.equals(coll.get(i).getState())){
    			CreateK3PayNoticeBillFacadeFactory.getRemoteInstance().createByDailyLoanBill(coll.get(0).getId());
    			break;
    		}
    	}
    	*/
    	//Person[] ps = CostProjectMgrPersonFacadeFactory.getRemoteInstance().getPersonObjsByOrg(coll.get(0));
    	
    	
    	checkModified();
    	
    	DatataskCaller task = new DatataskCaller();
    	     task.setParentComponent(this);
    	    task.setMainOrgContext(getMainOrgContext());
    	   if (getImportParam() != null)
        {
    	     task.invoke(getImportParam(), 0);
    	 }
    	   
    	   inOnload();
    	   
    }
    protected ArrayList getImportParam()
    {
    	DatataskParameter param = new DatataskParameter();
    	    String solutionName = null;
    	    
    	    if(BaseDataEnum.ACCOUNT.equals(types)){
    	    	solutionName = "eas.60fi.70cpbc.BaseDataAccountImport";
    	    }else if(BaseDataEnum.ORGUNIT.equals(types)){
    	    	solutionName = "eas.60fi.70cpbc.BaseDataOrgUnitImport";
    	    }else if(BaseDataEnum.DEPARTMENT.equals(types)){
    	    	solutionName = "eas.60fi.70cpbc.BaseDataDepartmentImport";
    	    }else if(BaseDataEnum.PROJECT.equals(types)){
    	    	solutionName = "eas.60fi.70cpbc.BaseDataProjectImport";
    	    }else if(BaseDataEnum.PERSON.equals(types)){
    	    	solutionName = "eas.60fi.70cpbc.BaseDataPersonImport";
    	    }
    	    
    	    param.solutionName = solutionName;
    	   ArrayList paramList = new ArrayList();
    	    paramList.add(param);
    	   return paramList;
    }
    
    
    private String getBillIDByType(BaseDataEnum bdEnum){
    	return null;
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.BaseDataMatchFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
        return new BaseDataMatchEntryInfo();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.BaseDataMatchInfo objectValue = new com.kingdee.eas.cp.bc.BaseDataMatchInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}