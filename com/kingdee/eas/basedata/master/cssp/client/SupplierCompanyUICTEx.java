package com.kingdee.eas.basedata.master.cssp.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.util.client.MsgBox;

public class SupplierCompanyUICTEx extends SupplierCompanyUI implements ICSSPAssistUI {

	public SupplierCompanyUICTEx() throws Exception {
		super();
	}

	private String TBLBANK_CITY = "city";

	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(tblBank.getRowCount()>0){
			for (int i = 0; i < tblBank.getRowCount(); i++) {
				if(tblBank.getCell(i, TBLBANK_CITY).getValue()==null){
					 tblBank.getEditManager().editCellAt(i, tblBank.getColumnIndex(TBLBANK_CITY));
	                 MsgBox.showInfo(this, "市不能为空！");
	                 abort();
				}
			}
		}
	}
}
