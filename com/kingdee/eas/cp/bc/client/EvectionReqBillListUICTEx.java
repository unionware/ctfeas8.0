package com.kingdee.eas.cp.bc.client;

public class EvectionReqBillListUICTEx extends EvectionReqBillListUI {

	public EvectionReqBillListUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		if (!this.isShowEntry) {
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.project.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.costedDept.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.expenseType.name"));
		}
	}

}
