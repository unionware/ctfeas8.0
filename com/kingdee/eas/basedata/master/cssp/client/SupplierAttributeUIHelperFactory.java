package com.kingdee.eas.basedata.master.cssp.client;

import com.kingdee.bos.BOSException;

public class SupplierAttributeUIHelperFactory {
	public SupplierAttributeUIHelperFactory() {
	}

	private static ICSSPAttributeUIHelper createUIHelper(String className,
			ICSSPAttributeUIHelper uiHelper) throws BOSException {
		try {
			Class clz = Class.forName(className);
			java.lang.reflect.Constructor c = clz
					.getConstructor(new Class[] { ICSSPAttributeUIHelper.class });
			return (ICSSPAttributeUIHelper) c
					.newInstance(new Object[] { uiHelper });
		} catch (Exception e) {
			throw new BOSException(e);
		}
	}

	public static ICSSPAttributeUIHelper createBaseUIHelper()
			throws BOSException {
		try {
			String className = "com.kingdee.eas.basedata.master.cssp.client.SupplierBaseEditUIHelper";
			Class clz = Class.forName(className);
			return (ICSSPAttributeUIHelper) clz.newInstance();
		} catch (Exception e) {
			throw new BOSException(e);
		}
	}

	public static ICSSPAttributeUIHelper createCompanyUIHelper(
			ICSSPAttributeUIHelper uiHelper) throws BOSException {
		return createUIHelper(
				"com.kingdee.eas.basedata.master.cssp.client.ZDFSupplierCompanyEditUIHelper",
				uiHelper);
	}

	public static ICSSPAttributeUIHelper createPurchaseUIHelper(
			ICSSPAttributeUIHelper uiHelper) throws BOSException {
		return createUIHelper(
				"com.kingdee.eas.basedata.master.cssp.client.SupplierPurchaseEditUIHelper",
				uiHelper);
	}
}
