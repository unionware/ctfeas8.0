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
		DatataskCaller task = new DatataskCaller(); //ʵ��������Ĵ���
    	task.setParentComponent(this); 
		ArrayList param = getImportBankInfoParam(); //��ȡ�����·��
		if((param != null)){
			task.invoke(param, DatataskMode.ImpMode,false);  //���õ����ģʽ
			execQuery();
		}
	}
	
	 /**
     * ��ȡ����·�����������÷���
     * @return
     */
	private ArrayList getImportBankInfoParam() {
    	
    	DatataskParameter p = new DatataskParameter();
    	p.solutionName = "eas.hr.personBankInfo"; //ʵ�����·��
    	p.alias = "Ա��������Ϣ"; //ʵ����� ��
    	ArrayList list = new ArrayList();
    	list.add(p);
		return list;
	}

}
