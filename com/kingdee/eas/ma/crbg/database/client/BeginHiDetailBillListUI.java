/**
 * output package name
 */
package com.kingdee.eas.ma.crbg.database.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillFactory;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BeginHiDetailBillListUI extends AbstractBeginHiDetailBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BeginHiDetailBillListUI.class);
    private int beginkey = 1;
	 private int endkey = 5;
    /**
     * output class constructor
     */
    public BeginHiDetailBillListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.btnAddNew.setVisible(false);
    	btnAttachment.setVisible(false);
    	actionImportData.setVisible(false);
    	this.ActionImportProsess.setEnabled(true);
    	this.kdbtnImpotyProsess.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
    }
    
    @Override
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	
    }
    @Override
    protected void tblMain_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionImportData.setEnabled(true);
    	this.actionImportData.setVisible(true);
    	this.actionExport.setVisible(true);
    	this.actionExport.setEnabled(true);
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	
        super.storeFields();
    }
   

    @Override
    public void actionImportData_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionImportData_actionPerformed(e);
    	
    }
    
    //这边执行一个新的线程调用后台导入代码
    @Override
    public void actionImportProsess_actionPerformed(ActionEvent e)
    		throws Exception {
    	
    	
    	/*while(isrun()){
    	ImportThread thread = new ImportThread(beginkey,endkey);
    	thread.run();
		beginkey = beginkey+endkey;
		endkey = endkey+5;
    	}
    	super.actionImportProsess_actionPerformed(e);*/
    	
    	DatataskCaller task = new DatataskCaller(); //实例化导入的窗口
    	task.setParentComponent(this); 
    	task.setMainOrgContext(getMainOrgContext());
		ArrayList param = getImportParam(); //获取导入的路径
		if((param != null)){
			task.invoke(param, DatataskMode.ImpMode,false);  //设置导入的模式
			execQuery();
		}
    }
     
    private boolean isrun() throws SQLException{
    	String sql = "select count(*) count from CTF_IMP_BeginHiDetailBill where fisfinish = '0'";
    	boolean isrun = false;
    	try {
			IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
			
			if(rs!=null){
				rs.next();
				if(rs.getInt("count")>0){
					isrun =true;
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}
    	return isrun;
    }
    
    @Override
    protected void afterTableFillData(KDTDataRequestEvent e) {
    	// TODO Auto-generated method stub
    	super.afterTableFillData(e);
//    		try {
//				fillTable();
//			} catch (BOSException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
    }
    @Override
    protected ArrayList getImportParam() {
    	 DatataskParameter param = new DatataskParameter();
		param.solutionName = "eas.00StrategyManagement.BeginOfEndImport";
		param.alias = "期初历史明细账引入";
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;

    }
//    private void fillTable() throws BOSException{
//    	IRowSet rs = null;
//    	String ID = "";
//    	String Number = "";
//    	String Name = "";
//    	Map nameMap = new HashMap();
//    	Map CacheMap = new HashMap();
//    	for(int i = 0;i<this.tblMain.getRowCount();i++){
//    		ID = this.tblMain.getCell(i, "id").getValue().toString();
//    		try {
//				rs = getProject(ID);
//				if(rs!=null){
//					rs.next();
//					if(nameMap.size()<1500){
//						nameMap.put(ID, rs.getString("FName"));
//						this.tblMain.getCell(i, "accountingGroup").setValue(nameMap.get(ID));
//					}
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
//    }
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.ma.crbg.database.BeginHiDetailBillFactory.getRemoteInstance();
    }
    
//    private IRowSet getProject(String ID) throws SQLException, BOSException{
//    	String sql = "";
//    	String proSql = "";
//    	IRowSet rsAssisID = null;
//    	IRowSet ProGroup = null;
//    	if(ID!=null){
//    		sql="select CFAssistGrpID from CT_CR_BeginHiDetailBill where FID = '"+ID+"'";
//    	}
//    	rsAssisID = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
//		String AssistantID = "";
//		if(rsAssisID!=null){
//			rsAssisID.next();
//			AssistantID = rsAssisID.getString("CFAssistGrpID");
//			proSql = "select FDisplayNameGroup_l2 as FName from T_BD_AssistantHG where FID = '"+AssistantID+"'";
//			ProGroup = SQLExecutorFactory.getRemoteInstance(proSql).executeSQL();
//		}
//		
//    	return ProGroup;
//    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo objectValue = new com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo();
		
        return objectValue;
    }

}