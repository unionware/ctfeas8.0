/**
 * 
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.EASResource;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.IBizAccountBill;
import com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade;

/**
 * @author lisha
 *
 */
public class BizExpenseAccountListUICTEx extends BizExpenseAccountListUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5259971677439047313L;

	/**
	 * @throws Exception
	 */
	public BizExpenseAccountListUICTEx() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		if (!this.isShowEntry) {
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.project.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.isNoInvoice"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.noInvoiceAmt"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.invoiceAmt"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.noTaxAmt"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.taxRate"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.tax"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.noTaxAmtLocal"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.taxLocal"));
		}
	}

	
	@Override
	public void actionAntiAudit_actionPerformed(ActionEvent arg0)
			throws Exception {
	    	checkMulSelected(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "OPERATE_ANTIAUDIT")));
	    	if (getSelectedKeyValue() == null) {
		       return;
		     }
		     ICreateK3PayNoticeBillFacade iBiz = CreateK3PayNoticeBillFacadeFactory.getRemoteInstance();
		     BOSUuid id = BOSUuid.read(getSelectedKeyValue());
		     
		super.actionAntiAudit_actionPerformed(arg0);
	}
}
