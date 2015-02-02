package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade;
import com.kingdee.eas.util.client.EASResource;

public class TravelExpenseAccountListUICTEx extends TravelExpenseAccountListUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7060982365808746371L;

	public TravelExpenseAccountListUICTEx() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		if (!this.isShowEntry) {
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.project"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("person.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.noTaxAmt"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.taxRate"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.tax"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.noTaxAmtLocal"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.taxLocal"));
		}
	}
	
	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {
	    checkMulSelected(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "OPERATE_ANTIAUDIT")));
	     if (getSelectedKeyValue() == null) {
	       return;
	     }
	     ICreateK3PayNoticeBillFacade iBiz = CreateK3PayNoticeBillFacadeFactory.getRemoteInstance();
	     BOSUuid id = BOSUuid.read(getSelectedKeyValue());
	     
	     iBiz.checkBillHasCreate(id);
	     
		super.actionAntiAudit_actionPerformed(e);
	}
}
