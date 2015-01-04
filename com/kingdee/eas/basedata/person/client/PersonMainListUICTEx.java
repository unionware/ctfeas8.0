package com.kingdee.eas.basedata.person.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.eas.framework.FrameWorkException;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;

public class PersonMainListUICTEx extends PersonMainListUI {

	public PersonMainListUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		btnImportBankInfo.setEnabled(true);
	}
	
	@Override
	public void actionImportBankInfo_actionPerformed(ActionEvent e)
			throws Exception {
		DatataskCaller task = new DatataskCaller(); //实例化导入的窗口
    	task.setParentComponent(this); 
		ArrayList param = getImportBankInfoParam(); //获取导入的路径
		if((param != null)){
			task.invoke(param, DatataskMode.ImpMode,false);  //设置导入的模式
			execQuery();
		}
	}
	
	 /**
     * 获取导入路径及参数设置方法
     * @return
     */
	private ArrayList getImportBankInfoParam() {
    	
    	DatataskParameter p = new DatataskParameter();
    	p.solutionName = "eas.hr.personBankInfo"; //实例类的路径
    	p.alias = "员工银行信息"; //实例类别 名
    	ArrayList list = new ArrayList();
    	list.add(p);
		return list;
	}

}
