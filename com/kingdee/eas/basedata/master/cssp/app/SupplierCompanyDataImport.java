package com.kingdee.eas.basedata.master.cssp.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.base.core.util.EqualsUtil;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.*;
import com.kingdee.eas.basedata.master.material.MaterialBaseException;
import com.kingdee.eas.basedata.master.material.PerminsionUtil;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.*;

public class SupplierCompanyDataImport extends AbstractDataTransmission {

	String BOOLEAN_FIELDS[] = { "FIsFreezePayment" };
	String BOOLEAN_OBJS[] = { "isFreezePayment" };
	String STRING_FIELDS[] = { "FContactPerson", "FContactPersonPost",
			"FPhone", "FEMail", "FPostalcode", "FMobile", "FFax" };
	String STRING_OBJS[] = { "contactPerson", "contactPersonPost", "phone",
			"email", "postalcode", "mobile", "fax" };
	private Set PerminsionCheckedCuIds;
	SupplierCompanyInfoInfo oldBaseInfo;
	
	public SupplierCompanyDataImport() {
		PerminsionCheckedCuIds = new HashSet();
	}

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return SupplierCompanyInfoFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}

	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		ICoreBase iSupplierCompanyInfo = getController(ctx);
		oldBaseInfo = (SupplierCompanyInfoInfo) coreBaseInfo;
		coreBaseInfo.put("isImport", "true");
		try {
			if (coreBaseInfo.getId() == null
					|| !iSupplierCompanyInfo.exists(new ObjectUuidPK(
							coreBaseInfo.getId())))
				iSupplierCompanyInfo.submit(coreBaseInfo);
			else
				iSupplierCompanyInfo.update(new ObjectUuidPK(coreBaseInfo
						.getId()), coreBaseInfo);
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		SupplierCompanyInfoInfo supplierCompanyInfo = new SupplierCompanyInfoInfo();
		String str = null;
		Object data = null;
		CompanyOrgUnitInfo curCompany = null;
		try {
			data = ((DataToken) hsData.get("FComOrgID")).data;
			if (data != null) {
				str = data.toString();
				curCompany = DataImportUtils.getCompanyOrgUnitInfoFromNumber(
						ctx, str);
				if (curCompany == null)
					throw new TaskExternalException(
							DataImportUtils
									.getResource(
											"com.kingdee.eas.basedata.master.cssp.CSSPResource",
											"getCompanyOrgUnitFailed", ctx));
				String userId = ContextUtil.getCurrentUserInfo(ctx).getId()
						.toString();
				if (!PerminsionUtil.isUserBizOrgRange(ctx, userId, curCompany
						.getId().toString()))
					throw new TaskExternalException((new MaterialBaseException(
							MaterialBaseException.OUT_BIZ_ORG,
							new Object[] { curCompany.getName() }))
							.getMessage());
				String CuId = curCompany.getCU().getId().toString();
				if (!PerminsionCheckedCuIds.contains(CuId)) {
					try {
						PerminsionUtil.checkCUPerminsion(ctx, CuId, ContextUtil
								.getCurrentUserInfo(ctx).getId().toString(),
								"Suppliercompanyinfo_Import");
					} catch (EASBizException ee) {
						throw new TaskExternalException(ee.getMessage(), ee);
					}
					PerminsionCheckedCuIds.add(CuId);
				}
				supplierCompanyInfo.setCompanyOrgUnit(curCompany);
				supplierCompanyInfo.setCU(curCompany.getCU());
			} else {
				throw new TaskExternalException(DataImportUtils.getResource(
						"com.kingdee.eas.basedata.master.cssp.CSSPResource",
						"getCompanyOrgUnitFailed", ctx));
			}
			Object numberdata = ((DataToken) hsData.get("FNumber")).data;
			if (numberdata != null) {
				str = numberdata.toString();
				if (str != null && str.trim().length() > 0) {
					if (super.isSltImportUpdate()) {
						SupplierInfo supplierInfo = DataImportUtils
								.getSupplierInfoFromNumber(ctx, curCompany
										.getCU(), str);
						if (supplierInfo != null) {
							supplierCompanyInfo.setSupplier(supplierInfo);
							ISupplier iSupplier = SupplierFactory
									.getLocalInstance(ctx);
							SupplierCompanyInfoInfo oldSupplierCompanyInfo = iSupplier
									.getCompanyInfo(
											new ObjectUuidPK(
													supplierCompanyInfo
															.getSupplier()
															.getId()),
											new ObjectUuidPK(curCompany.getId()));
							if (oldSupplierCompanyInfo != null)
								supplierCompanyInfo = oldSupplierCompanyInfo;
						} else {
							throw new TaskExternalException(
									DataImportUtils
											.getResource(
													"com.kingdee.eas.basedata.master.cssp.CSSPResource",
													"supplierNotExist", ctx));
						}
					} else {
						SupplierInfo supplierInfo = DataImportUtils
								.getSupplierInfoFromNumber(ctx, curCompany
										.getCU(), str);
						if (supplierInfo != null)
							supplierCompanyInfo.setSupplier(supplierInfo);
						else
							throw new TaskExternalException(
									DataImportUtils
											.getResource(
													"com.kingdee.eas.basedata.master.cssp.CSSPResource",
													"supplierNotExist", ctx));
					}
				} else {
					throw new TaskExternalException(
							DataImportUtils
									.getResource(
											"com.kingdee.eas.basedata.master.cssp.CSSPResource",
											"supplierNotExist", ctx));
				}
			}
			supplierCompanyInfo.setEffectedStatus(EffectedStatusEnum.EFFECTED);
			DataImportUtils.setBooleanData(hsData, BOOLEAN_FIELDS,
					supplierCompanyInfo, BOOLEAN_OBJS);
			if (hsData.get("FPostalcode") != null
					&& ((DataToken) hsData.get("FPostalcode")).data.toString()
							.length() > 10)
				throw new TaskExternalException(DataImportUtils.getResource(
						"com.kingdee.eas.basedata.master.cssp.CSSPResource",
						"postZipCode", ctx));
			DataImportUtils.setStringData(hsData, STRING_FIELDS,
					supplierCompanyInfo, STRING_OBJS);
			if (hsData.get("FPaymentTypeID") != null) {
				data = ((DataToken) hsData.get("FPaymentTypeID")).data;
				str = data.toString();
				com.kingdee.eas.basedata.assistant.PaymentTypeInfo info = DataImportUtils
						.getPaymentTypeInfoFromNumber(ctx, str);
				if (info != null)
					supplierCompanyInfo.setPaymentType(info);
			}
			if (hsData.get("FSettlementTypeID") != null) {
				data = ((DataToken) hsData.get("FSettlementTypeID")).data;
				str = data.toString();
				com.kingdee.eas.basedata.assistant.SettlementTypeInfo info = DataImportUtils
						.getSettlementTypeInfoFromNumber(ctx, str);
				if (info != null)
					supplierCompanyInfo.setSettlementType(info);
			}
			if (hsData.get("FSettlementCurrencyID") != null) {
				data = ((DataToken) hsData.get("FSettlementCurrencyID")).data;
				str = data.toString();
				CurrencyInfo info = DataImportUtils.getCurrencyInfoFromNumber(
						ctx, str);
				if (info != null)
					supplierCompanyInfo.setSettlementCurrency(info);
				else
					throw new TaskExternalException(
							DataImportUtils
									.getResource(
											"com.kingdee.eas.basedata.master.cssp.CSSPResource",
											"SupplierCurrency", ctx));
			}
			if (hsData.get("FPayConditionID") != null) {
				data = ((DataToken) hsData.get("FPayConditionID")).data;
				str = data.toString();
				com.kingdee.eas.basedata.assistant.PayConditionInfo info = DataImportUtils
						.getPayConditionInfoByNumber(ctx, str);
				if (info != null)
					supplierCompanyInfo.setPayCondition(info);
			}
			if (hsData.get("FAccountClassID") != null) {
				data = ((DataToken) hsData.get("FAccountClassID")).data;
				str = data.toString();
				com.kingdee.eas.basedata.assistant.KAClassficationInfo info = DataImportUtils
						.getKAClassficationInfoFromNumber(ctx, str, curCompany,
								"supplier");
				if (info != null)
					supplierCompanyInfo.setAccountingClassification(info);
			}
			if (hsData.get("FFreezeOrgUnitID") != null) {
				data = ((DataToken) hsData.get("FFreezeOrgUnitID")).data;
				str = data.toString();
				com.kingdee.eas.basedata.org.FullOrgUnitInfo info = DataImportUtils
						.getFullOrgUnitInfoFromNumber(ctx, str);
				if (info != null)
					supplierCompanyInfo.setFreezeOrgUnit(info);
			}
			data = ((DataToken) hsData.get("FStatus")).data;
			if (data != null) {
				String value = data.toString();
				supplierCompanyInfo.setUsingStatus(DataImportUtils
						.getCSSPAsstStatusValue(value));
			}
			String number = (String) getData(hsData, "FBankAccount");
			if (number != null && number.trim().length() > 0) {
				SupplierCompanyBankInfo bankInfo = new SupplierCompanyBankInfo();
				bankInfo.setSupplierCompanyInfo(supplierCompanyInfo);
				bankInfo.setBankAccount(number);
				bankInfo.setBankAddress((String) getData(hsData, "FBankAddress"));
				bankInfo.setBank((String) getData(hsData, "FBankName"));
				bankInfo.setProvince((String) getData(hsData, "FProvince"));
				bankInfo.setCity((String) getData(hsData, "FCity"));
				if (oldBaseInfo != null
						&& EqualsUtil.equals(oldBaseInfo.getSupplier(),
								supplierCompanyInfo.getSupplier())
						&& EqualsUtil.equals(oldBaseInfo.getCompanyOrgUnit(),
								supplierCompanyInfo.getCompanyOrgUnit()))
					supplierCompanyInfo = oldBaseInfo;
				addBank(supplierCompanyInfo, bankInfo);
			}
		} catch (BOSException ex) {
			throw new TaskExternalException(ex.getMessage(), ex);
		} catch (EASBizException ex) {
			throw new TaskExternalException(ex.getMessage(), ex);
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex);
		}
		return supplierCompanyInfo;
	}

	private void addBank(SupplierCompanyInfoInfo supplierCompanyInfo,
			SupplierCompanyBankInfo bankInfo) {
		SupplierCompanyBankCollection banks = supplierCompanyInfo
				.getSupplierBank();
		int exsistPos = -1;
		for (int i = 0; i < banks.size(); i++) {
			SupplierCompanyBankInfo tempBank = banks.get(i);
			if (tempBank.getBankAccount() != null
					&& bankInfo.getBankAccount() != null
					&& tempBank.getBankAccount().equals(
							bankInfo.getBankAccount()))
				exsistPos = i;
		}

		if (exsistPos >= 0)
			banks.set(exsistPos, bankInfo);
		else
			banks.add(bankInfo);
	}

	public Object getData(Hashtable hsData, String fieldName) {
		Object data = null;
		if (hsData.get(fieldName) != null)
			data = ((DataToken) hsData.get(fieldName)).data;
		return data;
	}

	public Hashtable exportTransmit(IRowSet rs, Context ctx)
			throws TaskExternalException {
		Hashtable result = new Hashtable();
		try {
			result.put("FNumber", DataImportUtils.transformValue(rs
					.getString("number")));
			result.put("FComOrgID", DataImportUtils.transformValue(rs
					.getString("orgUnit.number")));
			result.put("FIsFreezePayment", DataImportUtils
					.transformValue(Boolean.valueOf(rs
							.getBoolean("supplierCompany.isPayment"))));
			result.put("FPaymentTypeID", DataImportUtils.transformValue(rs
					.getString("paymentType.number")));
			result.put("FSettlementTypeID", DataImportUtils.transformValue(rs
					.getString("settlementType.number")));
			result.put("FPayConditionID", DataImportUtils.transformValue(rs
					.getString("payCondition.number")));
			result.put("FBankName", DataImportUtils.transformValue(rs
					.getString("SupplierC.bank")));
			result.put("FBankAccount", DataImportUtils.transformValue(rs
					.getString("SupplierC.bankAccount")));
			result.put("FBankAddress", DataImportUtils.transformValue(rs
					.getString("SupplierC.bankAddress")));
			result.put("FBankAddress", DataImportUtils.transformValue(rs
					.getString("SupplierC.province")));
			result.put("FBankAddress", DataImportUtils.transformValue(rs
					.getString("SupplierC.city")));
			result.put("FSettlementCurrencyID", DataImportUtils
					.transformValue(rs.getString("currency.number")));
			result.put("FAccountClassID", DataImportUtils.transformValue(rs
					.getString("kaClassfication.number")));
			result.put("FContactPerson", DataImportUtils.transformValue(rs
					.getString("supplierCompany.contactPerson")));
			result.put("FContactPersonPost", DataImportUtils.transformValue(rs
					.getString("supplierCompany.contactPerPost")));
			result.put("FPhone", DataImportUtils.transformValue(rs
					.getString("supplierCompany.phone")));
			result.put("FFax", DataImportUtils.transformValue(rs
					.getString("supplierCompany.fax")));
			result.put("FEMail", DataImportUtils.transformValue(rs
					.getString("supplierCompany.email")));
			result.put("FPostalcode", DataImportUtils.transformValue(rs
					.getString("supplierCompany.postalcode")));
			result.put("FMobile", DataImportUtils.transformValue(rs
					.getString("supplierCompany.mobile")));
			result.put("FCreatorNumber", DataImportUtils.transformValue(rs
					.getString("creator.number")));
			result.put("FCreateTime", DataImportUtils.getDateStrByLocale(ctx,
					rs.getDate("supplierCompany.createTime")));
			result.put("FStatus", DataImportUtils.transformValue(rs
					.getString("supplierCompany.usingStatus")));
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
		return result;
	}

	public FilterInfo getExportFilterForQuery(Context ctx) {
		FilterInfo filterInfo = null;
		ObjectUuidPK pk = null;
		try {
			ISupplier iSupplier = SupplierFactory.getLocalInstance(ctx);
			pk = new ObjectUuidPK(ContextUtil.getCurrentCtrlUnit(ctx).getId());
			filterInfo = iSupplier.getDatabaseDFilter(pk, "id", "adminCU.id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filterInfo;
	}

	public String getExportQueryInfo(Context ctx) {
		return "com.kingdee.eas.basedata.master.cssp.app.SupplierExportCompanyQuery";
	}
	
}