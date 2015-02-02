package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;

public class EvectionReqBillListUICTEx extends EvectionReqBillListUI {

	public EvectionReqBillListUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		if (!this.isShowEntry) {
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("project.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("costedDept1.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("person.name"));
		}
	}
	
	@Override
	public SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic =   super.getBOTPSelectors(); 
        sic.add(new SelectorItemInfo("entries.person.id"));
		sic.add(new SelectorItemInfo("entries.person.name"));
		sic.add(new SelectorItemInfo("entries.person.number"));
		
		sic.add(new SelectorItemInfo("entries.project.id"));
		sic.add(new SelectorItemInfo("entries.project.name"));
		sic.add(new SelectorItemInfo("entries.project.number"));
		
		return sic;
	}
}
