package com.kingdee.eas.basedata.master.cssp.client;

import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.EditUI;


public class ZDFSupplierCompanyEditUIHelper extends SupplierCompanyEditUIHelper {

	public ZDFSupplierCompanyEditUIHelper(ICSSPAttributeUIHelper baseUIHelper) {
		super(baseUIHelper);
	}
	
	
	@Override
	public EditUI createEditUI() throws Exception {
		super.createEditUI();
		this.currentUI = new SupplierCompanyUICTEx();
		return currentUI;
	}

}
