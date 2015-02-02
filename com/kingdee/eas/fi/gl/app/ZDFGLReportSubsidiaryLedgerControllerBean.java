package com.kingdee.eas.fi.gl.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.autoupdate.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.IPermissionServiceProvider;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.PermissionServiceProviderFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.IExchangeRate;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitGroupInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodTypeInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.IAsstAccount;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.ReportCurrencyConvertMode;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.cas.FeeTypeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.IVoucherFacade;
import com.kingdee.eas.fi.gl.ReportConditionBase;
import com.kingdee.eas.fi.gl.ZDFReportConditionSubsidiaryLedger;
import com.kingdee.eas.fi.gl.ReportConditionSubsidiaryLedgerAssist;
import com.kingdee.eas.fi.gl.ReportException;
import com.kingdee.eas.fi.gl.ReportResultInfo;
import com.kingdee.eas.fi.gl.ReportTableHeadInfo;
import com.kingdee.eas.fi.gl.ReportTableHeadItemInfo;
import com.kingdee.eas.fi.gl.VoucherFacadeFactory;
import com.kingdee.eas.fi.gl.common.AsstactTypeEntity;
import com.kingdee.eas.fi.gl.common.RptServerUtil;
import com.kingdee.eas.fi.gl.common.SQLUtil;
import com.kingdee.eas.fi.gl.common.SqlParameter2;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.report.RptException;
import com.kingdee.eas.framework.report.util.DBUtilx;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.JdbcRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ZDFGLReportSubsidiaryLedgerControllerBean extends
		AbstractZDFGLReportSubsidiaryLedgerControllerBean {
	private static final long serialVersionUID = -374532530308766616L;
	private static final String HAS_KSQL_SEQ = "USE_KSQL_SEQ";
	private static final String COUNT = "COUNT";
	private boolean isShowQuantity = false;

	private boolean isTransAccountDefaultMu = true;

	private boolean isShowUnit = true;

	int k = -1;

	boolean isSameCreditUnitGroup = true;

	boolean isSameDebitUnitGroup = true;

	StringBuffer strSelfOrder = new StringBuffer();

	Map initParam = new HashMap();

	protected boolean getPar(ReportPartner partner) {
		if (this.initParam.get("PARAM_ASSISTUNIT_FILTER") != null) {
			return ((Boolean) this.initParam.get("PARAM_ASSISTUNIT_FILTER"))
					.booleanValue();
		}

		boolean re = true;
		Context ctx = partner.getContext();
		String curComId = (String) ctx.get("CurCompanyId");
		try {
			Object[] ps = VoucherFacadeFactory.getLocalInstance(ctx)
					.fetchInitData(curComId);
			re = ((Boolean) ps[28]).booleanValue();
		} catch (Exception err) {
			re = false;
			err.printStackTrace();
		}
		this.initParam.put("PARAM_ASSISTUNIT_FILTER", new Boolean(re));
		return re;
	}

	private boolean getIsShowQuantity(ReportPartner partner)
			throws EASBizException, BOSException {
		if (this.initParam.get("PARAM_ISSHOWQUANTITY") != null) {
			return ((Boolean) this.initParam.get("PARAM_ISSHOWQUANTITY"))
					.booleanValue();
		}

		boolean isQtyAsst = partner.getParmQtyAsst();

		if ((partner.getAccountViewInfo().isIsQty()) && (!isQtyAsst))
			this.isShowQuantity = true;
		else if ((partner.getAccountViewInfo().isIsQty()) && (isQtyAsst)) {
			if (partner.getAccountViewInfo().getCAA() != null) {
				IAsstAccount iasstAccount = AsstAccountFactory
						.getLocalInstance(partner.getContext());
				AsstAccountInfo actaccount = iasstAccount
						.getAsstAccountInfo(new ObjectUuidPK(partner
								.getAccountViewInfo().getCAA().getId()
								.toString()));

				if (actaccount.isIsQty())
					this.isShowQuantity = true;
			} else {
				this.isShowQuantity = true;
			}
		}

		this.initParam.put("PARAM_ISSHOWQUANTITY", new Boolean(
				this.isShowQuantity));
		return this.isShowQuantity;
	}

	protected String getSqlForDataFromAndWhere(ReportPartner partner)
			throws BOSException, EASBizException {
		return getSqlForDataFrom(partner);
	}

	protected boolean isShowLocalCurrencyColumns(ReportPartner partner)
			throws BOSException, EASBizException {
		return (!partner.isParmForeignCurrencySu())
				&& (partner.getCurrencyType() == 0)
				&& (!partner.isBaseCurrency());
	}

	protected boolean isShowReportingCurrencyColumns(ReportPartner partner)
			throws BOSException, EASBizException {
		return (!partner.isParmForeignCurrencySu())
				&& (partner.isParmUseReportCurrency())
				&& (partner.getCurrencyType() == 0)
				&& (!partner.isReportingCurrency());
	}

	protected String getSqlForDataSelect(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		boolean showLocalCurrencyColumns = isShowLocalCurrencyColumns(partner);
		boolean showReportingCurrencyColumns = isShowReportingCurrencyColumns(partner);
		boolean optionShowBusinessDate = condition.getOptionShowBusinessDate();
		boolean optionOtherAccount = condition.getOptionOtherAccount();

		StringBuffer sqlSelect = new StringBuffer();

		sqlSelect.append("FNumber, FName, ");

		sqlSelect.append("FDate,");

		if (optionShowBusinessDate) {
			sqlSelect.append("FBizDate,");
		}
		sqlSelect
				.append("FVoucherNumber,FVoucherTypeNumber ,FVoucherTypeName ,FCREATETIME ,FSeq,FDescription,\r\n");

		if (optionOtherAccount) {
			sqlSelect.append("accountName,\r\n");
			sqlSelect.append("accountLongNumber,\r\n");
			sqlSelect.append("currencyID,\r\n");
		}

		if ((optionOtherAccount) || (showLocalCurrencyColumns)) {
			sqlSelect.append("FLocalRate,\r\n");
		}

		if (showReportingCurrencyColumns) {
			sqlSelect.append("FRptRate,\r\n");
		}

		if (this.isShowQuantity) {
			sqlSelect
					.append(" FDebitUnitId,FDebitQty,FDebitQtyDefault,FDebitPrice,\r\n");
			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (getPar(partner))) {
				sqlSelect.append(" FDebitAssistUnit,\r\n");
				if (ZDFReportConditionSubsidiaryLedger.class.equals(partner
						.getFixCondition().getClass()))
					sqlSelect
							.append(" (case when FLineType=3 or FLineType=4 then null else FDebitAssistPrice end) FDebitAssistPrice,\r\n");
				else {
					sqlSelect.append("FDebitAssistPrice,\r\n");
				}
				sqlSelect.append(" FDebitAssistQty,\r\n");
			}
		}
		sqlSelect.append("\tFDebitFor,");

		if ((optionOtherAccount) || (showLocalCurrencyColumns)) {
			sqlSelect.append("FDebitLocal,");
		}

		if (showReportingCurrencyColumns) {
			sqlSelect.append("FDebitRpt,");
		}

		if (this.isShowQuantity) {
			sqlSelect
					.append("\tFCreditUnitId,FCreditQty,FCreditQtyDefault,FCreditPrice,\r\n");
			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (getPar(partner))) {
				sqlSelect.append(" FCreditAssistUnit,\r\n");
				if (ZDFReportConditionSubsidiaryLedger.class.equals(partner
						.getFixCondition().getClass()))
					sqlSelect
							.append(" (case when FLineType=3 or FLineType=4 then null else FCreditAssistPrice end) FCreditAssistPrice,\r\n");
				else {
					sqlSelect.append("FCreditAssistPrice,\r\n");
				}

				sqlSelect.append(" FCreditAssistQty,\r\n");
			}
		}
		sqlSelect.append("\tFCreditFor,");

		if ((optionOtherAccount) || (showLocalCurrencyColumns)) {
			sqlSelect.append("FCreditLocal,");
		}

		if (showReportingCurrencyColumns) {
			sqlSelect.append("FCreditRpt,");
		}

		sqlSelect.append("FBalanceUnitId,");
		sqlSelect.append("\tFLineType,FVoucherID, \r\n");

		if (condition.getOptionShowAccountCusAttribute()) {
			sqlSelect.append(" \tFSettlementType,FSettlementCode, \r\n");
			sqlSelect.append("\tFBizNumber,\r\n");
			sqlSelect.append("\tFTicketNumber,FInvoiceNumber,\r\n");
			sqlSelect.append("\tFFeeType,FHandler,\r\n");
		}
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sqlSelect.append(" FCurrencyName ,\r\n");
			sqlSelect.append(" fcurrencynumber ,\r\n");
			sqlSelect.append(" fcurrencyid ,\r\n");
		}
		sqlSelect.append("\tFPeriodYear,FPeriodNumber ");
		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sqlSelect.append("    ,FAccountOrAssist,");
			sqlSelect.append("     FAsstAccountPerId,");
			sqlSelect.append("     FNumberGroupAsst,");
			sqlSelect.append("    FAsstAccountName,");
			sqlSelect.append("    FdivisionBalance");
		}

		return sqlSelect.toString();
	}

	protected String getSqlForDataFrom(ReportPartner partner)
			throws BOSException {
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append("FROM ");
		sqlFrom.append(partner.getTempTableName());
		sqlFrom.append(" T \r\n");
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency())
			sqlFrom
					.append(" inner join t_bd_currency cc on cc.fid = T.fcurrencyid ");
		try {
			sqlFrom.append(getSqlForDataWhere(partner));
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		return sqlFrom.toString();
	}

	protected String getSqlForDataWhere(ReportPartner partner)
			throws BOSException, EASBizException {
		StringBuffer sqlWhere = new StringBuffer();
		ZDFReportConditionSubsidiaryLedger reportcondition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		if ((reportcondition.getOptionOnlyAsst())
				|| (reportcondition.getAssisthgId() != null)) {
			sqlWhere
					.append("where  FdivisionBalance  is null or FdivisionBalance <> 2 ");
		} else {
			sqlWhere.append("");
		}
		return sqlWhere.toString();
	}

	protected String getSqlForDataOrder(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		StringBuffer order = new StringBuffer("ORDER BY ");

		if ((((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency())
				&& (!((ZDFReportConditionSubsidiaryLedger) partner
						.getFixCondition()).getOptionOtherAccount())) {
			order.append(" cc.fnumber,");
		}
		order = order.append("FPeriodYear,FPeriodNumber, ");

		if (this.strSelfOrder.length() > 0) {
			String selfOrder = this.strSelfOrder.toString().toLowerCase();
			while (selfOrder.indexOf("  ") != -1) {
				selfOrder = selfOrder.replaceAll("  ", " ");
			}

			selfOrder = selfOrder + ",";

			if (selfOrder.indexOf("fdate") == -1) {
				if (condition.getOptionDailyTotal()) {
					order.append("FDate,");
				}
				order.append("FLineType,").append(selfOrder);
				order
						.append(selfOrder.indexOf("fvouchertypenumber") == -1 ? "FVoucherTypeNumber,"
								: "");
				order
						.append(selfOrder.indexOf("fvouchernumber") == -1 ? "FVoucherNumber,"
								: "");
				order.append("t.fcreatetime,");
			} else {
				if (condition.getOptionDailyTotal()) {
					order.append("FDate,");
					selfOrder = selfOrder.replaceAll("fdate asc,", "");
					selfOrder = selfOrder.replaceAll("fdate desc,", "");
				}

				order.append("FLineType,").append(selfOrder);
				order
						.append(selfOrder.indexOf("fvouchertypenumber") == -1 ? "FVoucherTypeNumber,"
								: "");
				order
						.append(selfOrder.indexOf("fvouchernumber") == -1 ? "FVoucherNumber,"
								: "");
				order.append("t.fcreatetime,");
			}
		} else {
			order
					.append("FDate,FLineType,t.fcreatetime,FVoucherTypeNumber,FVoucherNumber,");
		}

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			order.append(" FAccountOrAssist, ");
		}

		order.append(" FSeq ");
		if (condition.getOptionOtherAccount()) {
			order.append(", FVoucherID ");
		}
		if ((((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency())
				&& (((ZDFReportConditionSubsidiaryLedger) partner
						.getFixCondition()).getOptionOtherAccount())) {
			order.append(", cc.fnumber");
		}
		return order.toString();
	}

	protected void doSomethingBeforeQuery(ReportPartner partner)
			throws BOSException, EASBizException {
		super.doSomethingBeforeQuery(partner);

		boolean isShowAccountCusAttribute = ((ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition()).getOptionShowAccountCusAttribute();
		HashMap table = new HashMap();
		table.put("VOUCHER", "TV");
		table.put("ENTRIES", "TVE");
		table.put("VOUCHERTYPE", "TVT");
		table.put("PERIOD", "TP");
		table.put("ACCOUNTVIEW", "AV");

		HashMap tableValue = RptServerUtil.getRptSelfCongdition(partner
				.getContext(), partner.getCondition(), table, true,
				isShowAccountCusAttribute);
		if ((tableValue != null) && (tableValue.get("orderCondition") != null))
			this.strSelfOrder = new StringBuffer(tableValue.get(
					"orderCondition").toString());
	}

	protected void doSomethingAfterInsertData(ReportPartner partner)
			throws BOSException, EASBizException {
		if (partner.isNewQuery())
			addTableHeadInfo(partner);
	}

	protected ReportTableHeadInfo getDefalutTableHeadInfo(ReportPartner partner)
			throws EASBizException {
		ReportTableHeadInfo head = new ReportTableHeadInfo();

		String[] columnNames = { "FDate", "FVoucherNumber", "FDescription",
				"FDebit", "FCredit", "FDC", "FBalance", "FBizNumber",
				"FFeeType", "FHandler", "FInvoiceNumber", "FSettlementCode",
				"FSettlementType", "FTicketNumber" };
		head.setColumnNames(columnNames);

		String[][] titles = { { "Date_jz", "VoucherNumber", "Description",
				"jind", "Credit", "Balance", "Balance", "AccountCusAttribute",
				"AccountCusAttribute", "AccountCusAttribute",
				"AccountCusAttribute", "AccountCusAttribute",
				"AccountCusAttribute", "AccountCusAttribute" } };

		head.setTitles(titles);
		head.setHeadMerge(true);

		int[] widths = { 80, 80, 140, 70, 70, 40, 110, 110, 100, 100, 100, 100,
				100, 100 };
		head.setWidths(widths);

		int[] alignments = { 0, 0, 0, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2 };
		head.setAlignments(alignments);

		if (!head.check()) {
			throw new ReportException(ReportException.TABLE_HEAD_ERROR);
		}
		return head;
	}

	protected void addTableHeadInfo(ReportPartner partner)
			throws EASBizException, BOSException {
		boolean isQtyAsst = partner.getParmQtyAsst();
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		this.isShowQuantity = getIsShowQuantity(partner);

		ArrayList itemList = new ArrayList();

		ReportTableHeadItemInfo item = new ReportTableHeadItemInfo();
		item.setColumnName("FDate");
		item.setWidth(80);
		item.setAlignment(0);
		item.setFreeze(false);
		item.setMerge(false);
		item.setVisible(true);
		item.setTitles(new String[] { "Date_jz" });

		itemList.add(item);

		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionShowBusinessDate()) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FBizDate");
			item.setWidth(80);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "BizDate" });

			itemList.add(item);
		}

		item = new ReportTableHeadItemInfo();
		item.setColumnName("FVoucherNumber");
		item.setWidth(80);
		item.setAlignment(0);
		item.setFreeze(false);
		item.setMerge(false);
		item.setVisible(false);
		item.setTitles(new String[] { "VoucherNumber" });

		itemList.add(item);

		item = new ReportTableHeadItemInfo();
		item.setColumnName("FDescription");
		item.setWidth(140);
		item.setAlignment(0);
		item.setFreeze(false);
		item.setMerge(false);
		item.setVisible(true);
		item.setTitles(new String[] { "Description" });

		itemList.add(item);

		if (condition.getOptionOtherAccount()) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("accountName");
			item.setWidth(200);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "otherAccount" });
			itemList.add(item);
		}
		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			this.isShowUnit = true;
			StringBuffer sqlForAsstCount = new StringBuffer();
			sqlForAsstCount.append("SELECT MAX(FCount) FROM  ").append(
					partner.getTempTableName());
			RptRowSet rs;
			try {
				rs = DBUtilx.executeQuery(sqlForAsstCount.toString(), null,
						partner.getContext());
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			int k = 1;
			if (rs.next()) {
				if (!rs.isNull(0))
					k = rs.getInt(0);
				else {
					k = 0;
				}
			}

			for (int h = 0; h < k; h++) {
				item = new ReportTableHeadItemInfo();
				item.setColumnName("FAsstAccountName" + h);
				item.setWidth(180);
				item.setAlignment(0);
				item.setFreeze(false);
				item.setMerge(false);
				item.setVisible(true);
				item.setTitles(new String[] { "AssistActName" });
				itemList.add(item);
			}

		}

		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FCurrencyName");
			item.setWidth(80);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "CurrencyType" });

			itemList.add(item);
		}

		if (isShowLocalCurrencyColumns(partner)) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FBaseRate");
			item.setWidth(70);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			String[] titles = new String[1];
			titles[0] = (isShowReportingCurrencyColumns(partner) ? "BaseRate"
					: "Rate");
			item.setTitles(titles);

			item.setNumberStyleType(1);
			item.setNumberScale(2);
			if (!condition.isAllCurrency()) {
				item.setNumberScale(partner.getScaleRateLocal());
			}
			item.setNeedKilobit(true);

			itemList.add(item);
		}

		if (isShowReportingCurrencyColumns(partner)) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FRptRate");
			item.setWidth(70);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			String[] titles = new String[1];
			titles[0] = (isShowLocalCurrencyColumns(partner) ? "RptRate"
					: "Rate");
			item.setTitles(titles);

			item.setNumberStyleType(1);
			item.setNumberScale(2);
			if (!condition.isAllCurrency()) {
				item.setNumberScale(partner.getScaleRateRpt());
			}
			item.setNeedKilobit(true);

			itemList.add(item);
		}

		ArrayList debitList = new ArrayList(5);

		if (this.isShowQuantity) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitUnit");
			item.setWidth(40);
			item.setAlignment(1);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "Debit", "Unit" });

			debitList.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitQuantity");
			item.setWidth(80);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "Debit", "Quantity" });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getParmQtyScale());
			item.setNeedKilobit(true);

			debitList.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitPrice");
			item.setWidth(80);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "Debit", "Price" });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getParmPriceScale());
			item.setNeedKilobit(true);

			debitList.add(item);
		}

		item = new ReportTableHeadItemInfo();
		item.setColumnName("FDebitFor");
		item.setWidth(110);
		item.setAlignment(2);
		item.setFreeze(false);
		item.setMerge(false);
		item.setVisible(true);
		item.setTitles(new String[] { "Debit" });

		item.setNumberStyleType(1);
		item.setNumberScale(getScaleFor(partner, null));
		item.setNeedKilobit(true);

		debitList.add(item);

		if (isShowLocalCurrencyColumns(partner)) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitLocal");
			item.setWidth(110);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "Debit", "LocalCurrency" });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getScaleLocal());
			item.setNeedKilobit(true);

			debitList.add(item);
		}

		if (isShowReportingCurrencyColumns(partner)) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitRpt");
			item.setWidth(110);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] { "Debit", "ReportingCurrency" });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getScaleRpt());
			item.setNeedKilobit(true);

			debitList.add(item);
		}

		if (debitList.size() != 1) {
			int forIndex = this.isShowQuantity ? 3 : 0;
			item = (ReportTableHeadItemInfo) debitList.get(forIndex);
			if (forIndex == debitList.size() - 1) {
				item.setTitles(new String[] { "Debit", "Amount" });
			} else {
				item.setTitles(new String[] { "Debit", "SourceCurrency" });
			}

		}

		itemList.addAll(debitList);

		if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
				&& (this.isShowQuantity) && (getPar(partner))) {
			ArrayList debitListx = new ArrayList(5);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitAssistUnit");
			item.setWidth(80);
			item.setTitleNeedResource(new boolean[] { true, false });
			item.setAlignment(1);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] {
					"Debit",
					ResourceBase.getString(
							"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
							"537_GLReportSubsidiaryLedgerControllerBean",
							partner.getContext().getLocale()) });

			debitListx.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitAssistPrice");
			item.setTitleNeedResource(new boolean[] { true, false });
			item.setWidth(80);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] {
					"Debit",
					ResourceBase.getString(
							"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
							"236_VoucherEditBaseUI", partner.getContext()
									.getLocale()) });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getParmQtyScale());
			item.setNeedKilobit(true);

			debitListx.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FDebitAssistQty");
			item.setTitleNeedResource(new boolean[] { true, false });
			item.setWidth(80);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] {
					"Debit",
					ResourceBase.getString(
							"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
							"237_VoucherEditBaseUI", partner.getContext()
									.getLocale()) });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getParmPriceScale());
			item.setNeedKilobit(true);

			debitListx.add(item);

			itemList.addAll(debitListx);
		}

		for (int i = 0; i < debitList.size(); i++) {
			ReportTableHeadItemInfo debitItem = (ReportTableHeadItemInfo) debitList
					.get(i);
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FCredit"
					+ debitItem.getColumnName().substring(6));
			item.setWidth(debitItem.getWidth());
			item.setAlignment(debitItem.getAlignment());
			item.setFreeze(debitItem.isFreeze());
			item.setMerge(debitItem.isMerge());
			item.setVisible(false);
			item.setNeedKilobit(debitItem.isNeedKilobit());
			item.setNumberScale(debitItem.getNumberScale());
			item.setNumberStyleType(debitItem.getNumberStyleType());
			String[] titles = new String[debitItem.getTitles().length];
			titles[0] = "Credit";
			for (int j = 1; j < debitItem.getTitles().length; j++) {
				titles[j] = debitItem.getTitles()[j];
			}
			item.setTitles(titles);

			itemList.add(item);
		}

		if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
				&& (this.isShowQuantity) && (getPar(partner))) {
			ArrayList debitListx = new ArrayList(5);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FCreditAssistUnit");
			item.setWidth(80);
			item.setTitleNeedResource(new boolean[] { true, false });
			item.setAlignment(1);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] {
					"Credit",
					ResourceBase.getString(
							"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
							"537_GLReportSubsidiaryLedgerControllerBean",
							partner.getContext().getLocale()) });

			debitListx.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FCreditAssistPrice");
			item.setTitleNeedResource(new boolean[] { true, false });
			item.setWidth(80);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] {
					"Credit",
					ResourceBase.getString(
							"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
							"236_VoucherEditBaseUI", partner.getContext()
									.getLocale()) });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getParmQtyScale());
			item.setNeedKilobit(true);

			debitListx.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FCreditAssistQty");
			item.setTitleNeedResource(new boolean[] { true, false });
			item.setWidth(80);
			item.setAlignment(2);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(true);
			item.setTitles(new String[] {
					"Credit",
					ResourceBase.getString(
							"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
							"237_VoucherEditBaseUI", partner.getContext()
									.getLocale()) });

			item.setNumberStyleType(1);
			item.setNumberScale(partner.getParmPriceScale());
			item.setNeedKilobit(true);

			debitListx.add(item);

			itemList.addAll(debitListx);
		}

		for (int i = 0; i < debitList.size(); i++) {
			if (((!this.isShowQuantity) && (i == 0))
					|| ((this.isShowQuantity) && (i == 3))) {
				item = new ReportTableHeadItemInfo();
				item.setColumnName("FDC");
				item.setWidth(40);
				item.setAlignment(1);
				item.setFreeze(false);
				item.setMerge(false);
				item.setVisible(false);
				item.setTitles(new String[] { "Balance", "DC" });

				itemList.add(item);
			}

			ReportTableHeadItemInfo debitItem = (ReportTableHeadItemInfo) debitList
					.get(i);
			item = new ReportTableHeadItemInfo();
			item.setColumnName("FBalance"
					+ debitItem.getColumnName().substring(6));
			item.setWidth(debitItem.getWidth());
			item.setAlignment(debitItem.getAlignment());
			item.setFreeze(debitItem.isFreeze());
			item.setMerge(debitItem.isMerge());
			item.setVisible(false);// debitItem.isVisible()
			item.setNeedKilobit(debitItem.isNeedKilobit());
			item.setNumberScale(debitItem.getNumberScale());
			item.setNumberStyleType(debitItem.getNumberStyleType());
			String[] titles = new String[2];
			titles[0] = "Balance";
			if (debitList.size() == 1)
				titles[1] = "Amount";
			else {
				for (int j = 1; j < debitItem.getTitles().length; j++) {
					titles[j] = debitItem.getTitles()[j];
				}
			}

			item.setTitles(titles);

			itemList.add(item);
		}

		if (condition.getOptionShowAccountCusAttribute()) {
			ArrayList acclist = new ArrayList();
			String companyid = partner.getCompanyId();
			String id = partner.getAccountViewInfo().getId().toString();
			StringBuffer sqln = new StringBuffer();
			sqln.append(" select fnumber from t_bd_accountview where fid ='");
			sqln.append(id).append("'");
			RptRowSet rs = null;
			try {
				rs = DBUtilx.executeQuery(sqln.toString(), null, partner
						.getContext());
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			String fnumber = null;
			while (rs.next()) {
				fnumber = rs.getString("fnumber");
			}
			String sqlids = " select fid  from t_bd_accountview where fnumber like '"
					+ fnumber
					+ "%'"
					+ " and FCompanyID='"
					+ companyid
					+ "'"
					+ " and FAccountTableID='"
					+ partner.getAccountTableId()
					+ "'";
			try {
				rs = DBUtilx.executeQuery(sqlids, null, partner.getContext());
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			StringBuffer ids = new StringBuffer();
			while (rs.next()) {
				ids.append("'").append(rs.getString("fid")).append("',");
			}

			StringBuffer sql = new StringBuffer();
			sql
					.append(" select FmappingFieldName from t_gl_assistcustomizedtype ");
			sql.append(" where Fid in (");
			sql
					.append(" select TGA.FCUSTOMIZEDTYPEID from t_gl_assistCustomizedProperty TGA ");
			sql
					.append(" inner join t_bd_accountview TA on TGA.FACCOUNTID = TA.FID ");
			sql.append(" where TA.FID in(");
			sql.append(ids.substring(0, ids.length() - 1).toString()).append(
					"))");
			try {
				rs = DBUtilx.executeQuery(sql.toString(), null, partner
						.getContext());
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			ArrayList mFieldNamelist = new ArrayList();
			while (rs.next()) {
				mFieldNamelist.add(rs.getString("FmappingFieldName"));
			}

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FBizNumber");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FBizNumberID")) {
				item.setVisible(true);
			}
			item.setTitles(new String[] { "AccountCusAttribute", "BizNumber" });
			acclist.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FFeeType");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FFeeType")) {
				item.setVisible(true);
			}

			item.setTitles(new String[] { "AccountCusAttribute", "FeeType" });
			acclist.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FHandler");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FHandlerID")) {
				item.setVisible(true);
			}

			item.setTitles(new String[] { "AccountCusAttribute", "Handler" });
			acclist.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FInvoiceNumber");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FInvoiceNumber")) {
				item.setVisible(true);
			}

			item.setTitles(new String[] { "AccountCusAttribute",
					"InvoiceNumber" });
			acclist.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FSettlementCode");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FSettlementCode")) {
				item.setVisible(true);
			}
			item.setTitles(new String[] { "AccountCusAttribute",
					"SettlementCode" });
			acclist.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FSettlementType");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FSettlementTypeID")) {
				item.setVisible(true);
			}
			item.setTitles(new String[] { "AccountCusAttribute",
					"SettlementType" });
			acclist.add(item);

			item = new ReportTableHeadItemInfo();
			item.setColumnName("FTicketNumber");
			item.setWidth(100);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			if (mFieldNamelist.contains("FTicketNumber")) {
				item.setVisible(true);
			}
			item
					.setTitles(new String[] { "AccountCusAttribute",
							"TicketNumber" });
			acclist.add(item);

			itemList.addAll(acclist);
		}

		ReportTableHeadItemInfo FNumber = new ReportTableHeadItemInfo();
		FNumber.setColumnName("FNumber");
		FNumber.setWidth(-1);
		FNumber.setAlignment(0);
		FNumber.setFreeze(false);
		FNumber.setMerge(false);
		FNumber.setVisible(false);
		FNumber.setTitles(new String[] { "FNumber" });
		itemList.add(FNumber);

		ReportTableHeadItemInfo itemFName = new ReportTableHeadItemInfo();
		itemFName.setColumnName("FName");
		itemFName.setWidth(-1);
		itemFName.setAlignment(0);
		itemFName.setFreeze(false);
		itemFName.setMerge(false);
		itemFName.setVisible(false);
		itemFName.setTitles(new String[] { "FName" });
		itemList.add(itemFName);

		if (condition.getOptionOtherAccount()) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("currencyID");
			item.setWidth(-1);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			item.setTitles(new String[] { "otherAccount" });
			itemList.add(item);
		}
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			item = new ReportTableHeadItemInfo();
			item.setColumnName("fcurrencyid");
			item.setWidth(0);
			item.setAlignment(0);
			item.setFreeze(false);
			item.setMerge(false);
			item.setVisible(false);
			item.setTitles(new String[] { "fcurrencyid" });

			itemList.add(item);
		}

		if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
				&& (this.isShowQuantity) && (getPar(partner))) {
			ArrayList t = new ArrayList();
			String headStr = itemList.toString();
			String[] headStrs = headStr.split(",");
			int fDebitPriceIndex = 0;
			int fCreditPriceIndex = 0;

			for (int i = 0; i < headStrs.length; i++) {
				if ("FDebitPrice".equals(headStrs[i].toString().trim())) {
					fDebitPriceIndex = i;
				}
				if ("FCreditPrice".equals(headStrs[i].toString().trim())) {
					fCreditPriceIndex = i;
				}
			}

			for (int i = 0; i <= fDebitPriceIndex; i++) {
				t.add(itemList.get(i));
			}

			for (int i = fDebitPriceIndex + 1; i < fCreditPriceIndex; i++) {
				if ("FDebitAssistUnit,FDebitAssistPrice,FDebitAssistQty"
						.indexOf(itemList.get(i).toString().trim()) >= 0) {
					t.add(itemList.get(i));
				}
			}

			for (int i = fDebitPriceIndex + 1; i <= fCreditPriceIndex; i++) {
				if ("FDebitAssistUnit,FDebitAssistPrice,FDebitAssistQty"
						.indexOf(itemList.get(i).toString().trim()) < 0) {
					t.add(itemList.get(i));
				}
			}

			for (int i = fCreditPriceIndex + 1; i < itemList.size(); i++) {
				if ("FCreditAssistUnit,FCreditAssistPrice,FCreditAssistQty"
						.indexOf(itemList.get(i).toString().trim()) >= 0) {
					t.add(itemList.get(i));
				}
			}

			for (int i = fCreditPriceIndex + 1; i < itemList.size(); i++) {
				if ("FCreditAssistUnit,FCreditAssistPrice,FCreditAssistQty"
						.indexOf(itemList.get(i).toString().trim()) < 0) {
					t.add(itemList.get(i));
				}
			}
			itemList = t;
		}

		ReportTableHeadItemInfo[] items = new ReportTableHeadItemInfo[itemList
				.size()];
		for (int i = 0; i < itemList.size(); i++) {
			items[i] = ((ReportTableHeadItemInfo) itemList.get(i));
		}

		ReportTableHeadInfo head = new ReportTableHeadInfo(items);
		head.setHeadMerge(true);

		if (!head.check()) {
			throw new ReportException(ReportException.TABLE_HEAD_ERROR);
		}

		partner.setTableHeadInfo(head);
	}

	protected Collection getDataFromResult(ReportPartner partner, ResultSet rs)
			throws BOSException, SQLException, EASBizException {
		if (partner.getStartIndex() + partner.getLineCount() == 0) {
			return new ArrayList();
		}

		ArrayList startBalaceList = new ArrayList();
		ArrayList arrayList = new ArrayList(partner.getLineCount());
		this.isTransAccountDefaultMu = partner.getParmUseMuAccountDefalut();

		BigDecimal[] balances = null;

		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		boolean isQueryLocalCuryCol = isShowLocalCurrencyColumns(partner);
		boolean isQueryRptCuryCol = isShowReportingCurrencyColumns(partner);
		ArrayList list = new ArrayList();
		JdbcRowSet rs2 = new JdbcRowSet();
		rs2.populate(rs);
		IMeasureUnit iMeasureUnit = MeasureUnitFactory.getLocalInstance(partner
				.getContext());
		BigDecimal coefficient = new BigDecimal("1.0000");
		MeasureUnitInfo accMU = partner.getAccountViewInfo().getMeasureUnitID();
		if (((this.isTransAccountDefaultMu) || ((partner
				.isRptTransferMeasureUnit()) && (partner.getAccountViewInfo()
				.getCAA() == null)))
				&& (accMU != null)) {
			accMU = iMeasureUnit.getMeasureUnitInfo(new ObjectUuidPK(accMU
					.getId()));
			if (accMU != null)
				coefficient = accMU.getCoefficient();
		}
		MeasureUnitGroupInfo groupInfo = partner.getAccountViewInfo()
				.getMeasureUnitGroupID();
		if (this.isShowQuantity) {
			if (groupInfo != null) {
				list.add(groupInfo.getId().toString());
			}
			while (rs2.next()) {
				String unitDebitid = rs2.getString("FDebitUnitId");
				if (unitDebitid != null) {
					String unitGroupid = iMeasureUnit.getMeasureUnitInfo(
							new ObjectUuidPK(unitDebitid))
							.getMeasureUnitGroup().getId().toString();
					if (!list.contains(unitGroupid)) {
						list.add(unitGroupid);
					}
					if (list.size() > 1) {
						this.isSameDebitUnitGroup = false;
						break;
					}
				}
			}
		}
		list.clear();
		if (this.isShowQuantity) {
			if (groupInfo != null) {
				list.add(groupInfo.getId().toString());
			}
			rs2.beforeFirst();
			while (rs2.next()) {
				String unitCreditid = rs2.getString("FCreditUnitId");
				if (unitCreditid != null) {
					String unitGroupid = iMeasureUnit.getMeasureUnitInfo(
							new ObjectUuidPK(unitCreditid))
							.getMeasureUnitGroup().getId().toString();
					if (!list.contains(unitGroupid)) {
						list.add(unitGroupid);
					}
					if (list.size() > 1) {
						this.isSameCreditUnitGroup = false;
						break;
					}
				}
			}

		}

		BigDecimal rptRate = new BigDecimal("1");

		CurrencyInfo rptCurrency = partner.getCompany().getReportCurrency();
		CurrencyInfo baseCurrency = partner.getCompany().getBaseCurrency();
		if ((isQueryRptCuryCol) && (rptCurrency != null)
				&& (baseCurrency != null)) {
			ExchangeTableInfo exchangeTableInfo = partner.getCompany()
					.getBaseExchangeTable();
			IExchangeRate iExchangeRate = ExchangeRateFactory
					.getLocalInstance(partner.getContext());
			ExchangeRateInfo exchangeRateInfo = iExchangeRate.getExchangeRate(
					new ObjectUuidPK(exchangeTableInfo.getId()),
					new ObjectUuidPK(baseCurrency.getId()), new ObjectUuidPK(
							rptCurrency.getId()), partner.getCurrentPeriod()
							.getEndDate());

			if (exchangeRateInfo != null) {
				rptRate = exchangeRateInfo.getConvertRate();
			}
		}
		rs2.beforeFirst();
		int index = 0;
		String lastVoucherID = null;
		ArrayList debitDataList = null;
		ArrayList creditDataList = null;
		ArrayList dcDataList = null;
		boolean isChangeCur = false;
		String curId = null;
		int assiId = 0;
		while (rs2.next()) {
			Map asssiHeIdMap = new HashMap();
			if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
					.isAllCurrency()) {
				if ((curId != null)
						&& (!curId.equals(rs2.getString("FCurrencyid")))) {
					isChangeCur = true;
				}
				curId = rs2.getString("fcurrencyid");
			}

			if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
					.getOptionOnlyAsst()) {
				assiId = rs2.getObject("FdivisionBalance") == null ? 0 : rs2
						.getInt("FdivisionBalance");
			}

			if (balances == null) {
				if (partner.getStartIndex() == 0) {
					balances = new BigDecimal[4];
				} else if (assiId == 2) {
					balances = new BigDecimal[4];
				} else if ((partner.getFixCondition() instanceof ReportConditionSubsidiaryLedgerAssist))
					balances = (BigDecimal[]) partner.getContext().get(
							"balances_Assist");
				else {
					balances = (BigDecimal[]) partner.getContext().get(
							"balances");
				}

			}

			if (((index == 0) && (partner.getStartIndex() == 0))
					|| (isChangeCur)) {
				isChangeCur = false;
				if (this.isShowQuantity) {
					balances[0] = rs2.getBigDecimal("FDebitQtyDefault");
					balances[0] = (balances[0] == null ? ZERO : balances[0]);
				}
				balances[1] = rs2.getBigDecimal("FDebitFor");
				balances[1] = (balances[1] == null ? ZERO : balances[1]);

				if (isQueryLocalCuryCol) {
					balances[2] = rs2.getBigDecimal("FDebitLocal");
					balances[2] = (balances[1] == null ? ZERO : balances[2]);
				}
				if (isQueryRptCuryCol) {
					balances[3] = rs2.getBigDecimal("FDebitRpt");
					balances[3] = (balances[3] == null ? ZERO : balances[3]);
				}
			}
			if (partner.getStartIndex() > index)
				;
			String voucherID = rs2.getString("FVoucherID");

			if (condition.getOptionOtherAccount()) {
				if (((voucherID != null) && (!voucherID
						.equalsIgnoreCase(lastVoucherID)))
						|| ((voucherID == null) && (lastVoucherID != null))) {
					ArrayList resultData = new ArrayList();
					divideVoucherEntry(resultData, partner, debitDataList,
							creditDataList, dcDataList, balances, rptRate,
							isQueryLocalCuryCol, isQueryRptCuryCol, coefficient);
					if (resultData.size() > 0) {
						arrayList.addAll(resultData);
					}
					debitDataList = new ArrayList();
					creditDataList = new ArrayList();
					dcDataList = new ArrayList();
					lastVoucherID = voucherID;
				}
				if (voucherID != null) {
					BigDecimal debitFor = rs2.getBigDecimal("FDebitFor");
					BigDecimal creditFor = rs2.getBigDecimal("FCreditFor");
					BigDecimal debitLocal = ZERO;
					BigDecimal creditLocal = ZERO;
					BigDecimal debitRpt = ZERO;
					BigDecimal creditRpt = ZERO;
					debitLocal = rs2.getBigDecimal("FDebitLocal");
					creditLocal = rs2.getBigDecimal("FCreditLocal");
					if (isQueryRptCuryCol) {
						debitRpt = rs2.getBigDecimal("FDebitRpt");
						creditRpt = rs2.getBigDecimal("FCreditRpt");
					}

					if (((debitFor != null) && (debitFor.doubleValue() > 0.0D))
							|| ((debitLocal != null) && (debitLocal
									.doubleValue() > 0.0D))
							|| ((debitRpt != null) && (debitRpt.doubleValue() > 0.0D))
							|| ((creditFor != null) && (creditFor.doubleValue() < 0.0D))
							|| ((creditLocal != null) && (creditLocal
									.doubleValue() < 0.0D))
							|| ((creditRpt != null) && (creditRpt.doubleValue() < 0.0D))) {
						debitDataList.add(transfer(partner, rs2,
								isQueryRptCuryCol));
					} else if (((debitFor != null) && (debitFor.doubleValue() < 0.0D))
							|| ((debitLocal != null) && (debitLocal
									.doubleValue() < 0.0D))
							|| ((debitRpt != null) && (debitRpt.doubleValue() < 0.0D))
							|| ((creditFor != null) && (creditFor.doubleValue() > 0.0D))
							|| ((creditLocal != null) && (creditLocal
									.doubleValue() > 0.0D))
							|| ((creditRpt != null) && (creditRpt.doubleValue() > 0.0D))) {
						creditDataList.add(transfer(partner, rs2,
								isQueryRptCuryCol));
					}
					dcDataList.add(transfer(partner, rs2, isQueryRptCuryCol));
				} else if (assiId == 2) {
					Object[] obj = getLineObjects(partner, rs2, balances,
							(index == 0) && (partner.getStartIndex() == 0),
							isQueryLocalCuryCol, isQueryRptCuryCol, coefficient);
					asssiHeIdMap.put(rs2.getString("Fasstaccountperid"), obj);
					startBalaceList.add(asssiHeIdMap);
				} else {
					arrayList
							.add(getLineObjects(partner, rs2, balances,
									(index == 0)
											&& (partner.getStartIndex() == 0),
									isQueryLocalCuryCol, isQueryRptCuryCol,
									coefficient));
				}

			} else if (assiId == 2) {
				Object[] obj = getLineObjects(partner, rs2, balances,
						(index == 0) && (partner.getStartIndex() == 0),
						isQueryLocalCuryCol, isQueryRptCuryCol, coefficient);
				asssiHeIdMap.put(rs2.getString("Fasstaccountperid"), obj);
				startBalaceList.add(asssiHeIdMap);
			} else {
				arrayList.add(getLineObjects(partner, rs2, balances,
						(index == 0) && (partner.getStartIndex() == 0),
						isQueryLocalCuryCol, isQueryRptCuryCol, coefficient));
			}

			if (assiId == 2)
				index = 0;
			else {
				index++;
			}
		}
		if (assiId != 2) {
			if ((partner.getFixCondition() instanceof ReportConditionSubsidiaryLedgerAssist)) {
				partner.getContext().put("balances_Assist", balances);
			} else
				partner.getContext().put("balances", balances);

		}

		if (assiId == 2) {
			return startBalaceList;
		}
		return arrayList;
	}

	private HashMap transfer(ReportPartner partner, JdbcRowSet rs,
			boolean isShowRptCuryCol) throws SQLException, EASBizException,
			BOSException {
		if (rs == null)
			return null;
		HashMap map = new HashMap();
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		map.put("FNumber", rs.getString("FNumber"));
		map.put("FName", rs.getString("FName"));
		map.put("FDate", rs.getDate("FDate"));

		if (condition.getOptionShowBusinessDate()) {
			map.put("FBizDate", rs.getDate("FBizDate"));
		}
		map.put("FVoucherNumber", rs.getString("FVoucherTypeName") + " - "
				+ rs.getString("FVoucherNumber"));
		map.put("FLineType", rs.getObject("FLineType"));
		map.put("FDescription", rs.getString("FDescription"));
		map.put("accountLongNumber", rs.getString("accountLongNumber"));
		map.put("accountName", rs.getString("accountName"));
		map.put("currencyID", rs.getString("currencyID"));
		if (partner.getFixCondition().isAllCurrency()) {
			map.put("FCurrencyName", rs.getString("FCurrencyName"));
			map.put("fcurrencyid", rs.getString("FCurrencyID"));
		}
		map.put("FLocalRate", rs.getBigDecimal("FLocalRate"));

		if (isShowRptCuryCol) {
			map.put("FRptRate", rs.getBigDecimal("FRptRate"));
		}

		if (this.isShowQuantity) {
			map.put("FDebitQtyDefault", rs.getBigDecimal("FDebitQtyDefault"));
			map.put("FDebitUnitId", rs.getString("FDebitUnitId"));

			map.put("FDebitQty", rs.getBigDecimal("FDebitQty"));

			map.put("FDebitPrice", rs.getBigDecimal("FDebitPrice"));
		}

		BigDecimal debitFor = rs.getBigDecimal("FDebitFor");
		map.put("FDebitFor", debitFor);

		map.put("FDebitLocal", rs.getBigDecimal("FDebitLocal"));

		if (isShowRptCuryCol) {
			map.put("FDebitRpt", rs.getBigDecimal("FDebitRpt"));
		}

		if (this.isShowQuantity) {
			map.put("FCreditQtyDefault", rs.getBigDecimal("FCreditQtyDefault"));

			map.put("FCreditUnitId", rs.getString("FCreditUnitId"));

			map.put("FCreditQty", rs.getBigDecimal("FCreditQty"));

			map.put("FCreditPrice", rs.getBigDecimal("FCreditPrice"));
		}
		map.put("FCreditFor", rs.getBigDecimal("FCreditFor"));

		map.put("FCreditLocal", rs.getBigDecimal("FCreditLocal"));

		if (isShowRptCuryCol) {
			map.put("FCreditRpt", rs.getBigDecimal("FCreditRpt"));
		}
		map.put("FVoucherID", rs.getString("FVoucherID"));
		map.put("FentryID", rs.getString("FSeq"));
		return map;
	}

	private void divideVoucherEntry(Collection resultData,
			ReportPartner partner, ArrayList debitDataList,
			ArrayList creditDataList, ArrayList dcDataList,
			BigDecimal[] balances, BigDecimal rptRate,
			boolean isShowLocalCuryCol, boolean isShowRptCuryCol,
			BigDecimal coefficient) throws EASBizException, BOSException,
			SQLException {
		if ((debitDataList == null) || (creditDataList == null)
				|| (debitDataList.size() == 0) || (creditDataList.size() == 0))
			return;
		int debitCount = 0;
		int creditCount = 0;

		BigDecimal totalDebitQty = new BigDecimal("0");
		BigDecimal totalCreditQty = new BigDecimal("0");
		BigDecimal totalDebitQtyDefault = new BigDecimal("0");
		BigDecimal totalCreditQtyDefault = new BigDecimal("0");

		BigDecimal totalDebitFor = new BigDecimal("0");
		BigDecimal totalCreditFor = new BigDecimal("0");

		BigDecimal totalDebitRpt = new BigDecimal("0");
		BigDecimal totalCreditRpt = new BigDecimal("0");

		BigDecimal creditPrice = null;
		BigDecimal debitPrice = null;

		HashMap debitDataList0 = new HashMap();
		HashMap creditDataList0 = new HashMap();

		HashMap paramKeyMap = new HashMap();
		IParamControl iParamControl = ParamControlFactory
				.getLocalInstance(partner.getContext());
		paramKeyMap.put("GL_002", partner.getCompany().getId().toString());
		String qtyScale = (String) iParamControl.getParamHashMap(paramKeyMap)
				.get("GL_002");

		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		String accountLongNumber = partner.getAccountViewInfo().getLongNumber();

		for (int i = 0; i < debitDataList.size(); i++) {
			HashMap debitData = (HashMap) debitDataList.get(i);
			BigDecimal debit_debit_Local = (BigDecimal) debitData
					.get("FDebitLocal");
			String debitAccountNumber = (String) debitData
					.get("accountLongNumber");
			if ((accountLongNumber.equals(debitAccountNumber))
					|| (debitAccountNumber.startsWith(accountLongNumber + "!"))) {
				if (debit_debit_Local.doubleValue() > 0.0D) {
					BigDecimal FDebitFor = (BigDecimal) debitData
							.get("FDebitFor");
					totalDebitFor = totalDebitFor.add(FDebitFor);
					if (isShowRptCuryCol) {
						BigDecimal FDebitRpt = (BigDecimal) debitData
								.get("FDebitRpt");
						totalDebitRpt = totalDebitRpt.add(FDebitRpt);
					}
				} else {
					BigDecimal FCreditFor = (BigDecimal) debitData
							.get("FCreditFor");
					totalCreditFor = totalCreditFor.add(FCreditFor);
					if (isShowRptCuryCol) {
						BigDecimal FCreditRpt = (BigDecimal) debitData
								.get("FCreditRpt");
						totalCreditRpt = totalCreditRpt.add(FCreditRpt);
					}
				}
			}
		}
		for (int i = 0; i < creditDataList.size(); i++) {
			HashMap creditData = (HashMap) creditDataList.get(i);
			BigDecimal credit_credit_Local = (BigDecimal) creditData
					.get("FCreditLocal");
			String creditAccountNumber = (String) creditData
					.get("accountLongNumber");
			if ((accountLongNumber.equals(creditAccountNumber))
					|| (creditAccountNumber.startsWith(accountLongNumber + "!"))) {
				if (credit_credit_Local.doubleValue() > 0.0D) {
					BigDecimal FCreditFor = (BigDecimal) creditData
							.get("FCreditFor");
					totalCreditFor = totalCreditFor.add(FCreditFor);
					if (isShowRptCuryCol) {
						BigDecimal FCreditRpt = (BigDecimal) creditData
								.get("FCreditRpt");
						totalCreditRpt = totalCreditRpt.add(FCreditRpt);
					}
				} else {
					BigDecimal FDebitFor = (BigDecimal) creditData
							.get("FDebitFor");
					totalDebitFor = totalDebitFor.add(FDebitFor);
					if (isShowRptCuryCol) {
						BigDecimal FDebitRpt = (BigDecimal) creditData
								.get("FDebitRpt");
						totalDebitRpt = totalDebitRpt.add(FDebitRpt);
					}
				}
			}

		}

		if (this.isShowQuantity) {
			for (int i = 0; i < debitDataList.size(); i++) {
				HashMap debitData = (HashMap) debitDataList.get(i);
				BigDecimal debit_debit_Local = (BigDecimal) debitData
						.get("FDebitLocal");
				String debitAccountNumber = (String) debitData
						.get("accountLongNumber");
				if ((accountLongNumber.equals(debitAccountNumber))
						|| (debitAccountNumber.startsWith(accountLongNumber
								+ "!"))) {
					if (debit_debit_Local.doubleValue() > 0.0D) {
						BigDecimal FDebitQty = (BigDecimal) debitData
								.get("FDebitQty");
						BigDecimal FDebitQtyDefault = (BigDecimal) debitData
								.get("FDebitQtyDefault");
						totalDebitQty = totalDebitQty.add(FDebitQty);
						totalDebitQtyDefault = totalDebitQtyDefault
								.add(FDebitQtyDefault);
					} else {
						BigDecimal FCreditQty = (BigDecimal) debitData
								.get("FCreditQty");
						BigDecimal FCreditQtyDefault = (BigDecimal) debitData
								.get("FCreditQtyDefault");
						totalCreditQty = totalCreditQty.add(FCreditQty);
						totalCreditQtyDefault = totalCreditQtyDefault
								.add(FCreditQtyDefault);
					}
				}
			}
			for (int i = 0; i < creditDataList.size(); i++) {
				HashMap creditData = (HashMap) creditDataList.get(i);
				BigDecimal credit_credit_Local = (BigDecimal) creditData
						.get("FCreditLocal");
				String creditAccountNumber = (String) creditData
						.get("accountLongNumber");
				if ((accountLongNumber.equals(creditAccountNumber))
						|| (creditAccountNumber.startsWith(accountLongNumber
								+ "!"))) {
					if (credit_credit_Local.doubleValue() > 0.0D) {
						BigDecimal FCreditQty = (BigDecimal) creditData
								.get("FCreditQty");
						BigDecimal FCreditQtyDefault = (BigDecimal) creditData
								.get("FCreditQtyDefault");
						totalCreditQty = totalCreditQty.add(FCreditQty);
						totalCreditQtyDefault = totalCreditQtyDefault
								.add(FCreditQtyDefault);
					} else {
						BigDecimal FDebitQty = (BigDecimal) creditData
								.get("FDebitQty");
						BigDecimal FDebitQtyDefault = (BigDecimal) creditData
								.get("FDebitQtyDefault");
						totalDebitQty = totalDebitQty.add(FDebitQty);
						totalDebitQtyDefault = totalDebitQtyDefault
								.add(FDebitQtyDefault);
					}
				}
			}
		}

		Map splitedDataEntryMap = new HashMap();
		Map splitedDataEntrySefMap = new HashMap();
		do {
			if ((debitDataList.size() == 0) || (creditDataList.size() == 0)) {
				return;
			}
			HashMap debitData = (HashMap) debitDataList.get(0);
			BigDecimal debit_debit_Local = (BigDecimal) debitData
					.get("FDebitLocal");
			BigDecimal debit_credit_Local = (BigDecimal) debitData
					.get("FCreditLocal");
			BigDecimal debitLocal = debit_debit_Local.add(debit_credit_Local
					.negate());
			String debitAccountNumber = (String) debitData
					.get("accountLongNumber");

			HashMap creditData = (HashMap) creditDataList.get(0);
			BigDecimal credit_debit_Local = (BigDecimal) creditData
					.get("FDebitLocal");
			BigDecimal credit_credit_Local = (BigDecimal) creditData
					.get("FCreditLocal");
			BigDecimal creditLocal = credit_credit_Local.add(credit_debit_Local
					.negate());
			String creditAccountNumber = (String) creditData
					.get("accountLongNumber");

			if ((debitLocal == null) || (creditLocal == null)) {
				return;
			}
			int scale = 2;
			String currencyID = null;
			CurrencyInfo currencyInfo = null;
			if (!condition.isAllCurrency()) {
				scale = partner.getScaleFor();
			} else {
				String accountcode = condition.getAccountCodeStart();
				if (debitAccountNumber.endsWith("!" + accountcode))
					currencyID = debitData.get("currencyID").toString();
				else if (creditAccountNumber.endsWith("!" + accountcode)) {
					currencyID = creditData.get("currencyID").toString();
				}
				if (currencyID != null)
					scale = getCurrency(partner, currencyID).getPrecision();
				else {
					scale = Integer.parseInt(qtyScale);
				}
			}

			int rptScale = partner.getScaleRpt();
			BigDecimal rate = new BigDecimal("1.00");
			if (partner.getCurrencyType() == 0) {
				if ((accountLongNumber.equals(debitAccountNumber))
						|| (debitAccountNumber.startsWith(accountLongNumber
								+ "!")))
					rate = (BigDecimal) debitData.get("FLocalRate");
				else if ((accountLongNumber.equals(creditAccountNumber))
						|| (creditAccountNumber.startsWith(accountLongNumber
								+ "!")))
					rate = (BigDecimal) creditData.get("FLocalRate");
			} else {
				rptRate = rate;
			}
			BigDecimal debitFor = debitLocal.divide(rate, scale, 4);
			BigDecimal creditFor = creditLocal.divide(rate, scale, 4);
			BigDecimal debitRpt = debitLocal.divide(rptRate, rptScale, 4);
			BigDecimal creditRpt = creditLocal.divide(rptRate, rptScale, 4);
			if (debitLocal.compareTo(creditLocal) == 0) {
				if (((accountLongNumber.equals(debitAccountNumber)) || (debitAccountNumber
						.startsWith(accountLongNumber + "!")))
						&& ((partner.getCurrencyType() != 0)
								|| ((!partner.getFixCondition().isAllCurrency()) && (partner
										.getCurrency().getId().toString()
										.equalsIgnoreCase((String) debitData
												.get("currencyID"))))
								|| ((debitData.get("currencyID") != null) && (debitData
										.get("currencyID").equals(creditData
										.get("currencyID")))) || (partner
								.getFixCondition().isAllCurrency()))) {
					HashMap tmpHap = (HashMap) debitData.clone();
					tmpHap.put("accountName", creditData.get("accountName"));
					if (this.isShowQuantity) {
						debitPrice = (BigDecimal) debitData.get("FDebitPrice");
						creditPrice = (BigDecimal) debitData
								.get("FCreditPrice");
						if (debit_debit_Local.doubleValue() > 0.0D) {
							if ((debitPrice != null)
									&& (debitPrice.doubleValue() != 0.0D)) {
								if (debitData.get("FDebitUnitId") != null) {
									tmpHap.put("FDebitQty", creditLocal.divide(
											debitPrice, scale, 4));
									tmpHap.put("FDebitQtyDefault", creditLocal
											.divide(debitPrice, scale, 4)
											.multiply(coefficient));
								} else {
									debitPrice = debitPrice
											.multiply(coefficient);
									tmpHap.put("FDebitPrice", debitPrice);
									tmpHap.put("FDebitQty", creditLocal.divide(
											debitPrice, scale, 4));
									tmpHap.put("FDebitQtyDefault", creditLocal
											.divide(debitPrice, scale, 4)
											.multiply(coefficient));
								}
							} else {
								tmpHap.put("FDebitQty", GlUtils.zero);
								tmpHap.put("FDebitQtyDefault", GlUtils.zero);
							}
						} else if (debit_debit_Local.doubleValue() <= 0.0D) {
							if ((creditPrice != null)
									&& (creditPrice.doubleValue() != 0.0D)) {
								if (debitData.get("FCreditUnitId") != null) {
									tmpHap.put("FCreditQty", creditLocal
											.negate().divide(creditPrice,
													scale, 4));
									tmpHap.put("FCreditQtyDefault", creditLocal
											.negate().divide(creditPrice,
													scale, 4).multiply(
													coefficient));
								} else {
									creditPrice = creditPrice
											.multiply(coefficient);
									tmpHap.put("FCreditPrice", creditPrice);
									tmpHap.put("FCreditQty", creditLocal
											.negate().divide(creditPrice,
													scale, 4));
									tmpHap.put("FCreditQtyDefault", creditLocal
											.negate().divide(creditPrice,
													scale, 4).multiply(
													coefficient));
								}
							} else {
								tmpHap.put("FCreditQty", GlUtils.zero);
								tmpHap.put("FCreditQtyDefault", GlUtils.zero);
							}
						}
					}
					if ((creditDataList0 != null)
							&& (creditDataList0.size() != 0)) {
						tmpHap.put("oppFentryID", debitData.get("FentryID")
								.toString());
						creditDataList0.put(String.valueOf(creditCount++),
								tmpHap);
					} else {
						Integer debitDataEntrySEQ = new Integer(debitData.get(
								"FentryID").toString());
						if (splitedDataEntryMap.get(debitDataEntrySEQ) != null) {
							splitedDataEntrySefMap = (Map) splitedDataEntryMap
									.get(debitDataEntrySEQ);
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1), tmpHap);
						} else {
							splitedDataEntrySefMap = new HashMap();
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1), tmpHap);
							splitedDataEntryMap.put(debitDataEntrySEQ,
									splitedDataEntrySefMap);
						}
					}

				}

				if (((accountLongNumber.equals(creditAccountNumber)) || (creditAccountNumber
						.startsWith(accountLongNumber + "!")))
						&& ((partner.getCurrencyType() != 0)
								|| ((!partner.getFixCondition().isAllCurrency()) && (partner
										.getCurrency().getId().toString()
										.equalsIgnoreCase((String) creditData
												.get("currencyID"))))
								|| ((debitData.get("currencyID") != null) && (debitData
										.get("currencyID").equals(creditData
										.get("currencyID")))) || (partner
								.getFixCondition().isAllCurrency()))) {
					HashMap tmpHap = (HashMap) creditData.clone();
					tmpHap.put("accountName", debitData.get("accountName"));
					if (this.isShowQuantity) {
						creditPrice = (BigDecimal) creditData
								.get("FCreditPrice");
						debitPrice = (BigDecimal) creditData.get("FDebitPrice");
						if (credit_credit_Local.doubleValue() > 0.0D) {
							if ((creditPrice != null)
									&& (creditPrice.doubleValue() != 0.0D)) {
								if (creditData.get("FCreditUnitId") != null) {
									tmpHap.put("FCreditQty", debitLocal.divide(
											creditPrice, scale, 4));
									tmpHap.put("FCreditQtyDefault", debitLocal
											.divide(creditPrice, scale, 4)
											.multiply(coefficient));
								} else {
									creditPrice = creditPrice
											.multiply(coefficient);
									tmpHap.put("FCreditPrice", creditPrice);
									tmpHap.put("FCreditQty", debitLocal.divide(
											creditPrice, scale, 4));
									tmpHap.put("FCreditQtyDefault", debitLocal
											.divide(creditPrice, scale, 4)
											.multiply(coefficient));
								}
							} else {
								tmpHap.put("FCreditQty", GlUtils.zero);
								tmpHap.put("FCreditQtyDefault", GlUtils.zero);
							}
						} else if ((debitPrice != null)
								&& (debitPrice.doubleValue() != 0.0D)) {
							if (creditData.get("FDebitUnitId") != null) {
								tmpHap.put("FDebitQty", debitLocal.negate()
										.divide(debitPrice, scale, 4));
								tmpHap.put("FDebitQtyDefault", debitLocal
										.negate().divide(debitPrice, scale, 4)
										.multiply(coefficient));
							} else {
								debitPrice = debitPrice.multiply(coefficient);
								tmpHap.put("FDebitPrice", debitPrice);
								tmpHap.put("FDebitQty", debitLocal.negate()
										.divide(debitPrice, scale, 4));
								tmpHap.put("FDebitQtyDefault", debitLocal
										.negate().divide(debitPrice, scale, 4)
										.multiply(coefficient));
							}
						} else {
							tmpHap.put("FDebitQty", GlUtils.zero);
							tmpHap.put("FDebitQtyDefault", GlUtils.zero);
						}
					}

					if ((debitDataList0 != null)
							&& (debitDataList0.size() != 0)) {
						tmpHap.put("oppFentryID", creditData.get("FentryID")
								.toString());
						debitDataList0
								.put(String.valueOf(debitCount++), tmpHap);
					} else {
						Integer creditDataEntrySEQ = new Integer(creditData
								.get("FentryID").toString());
						if (splitedDataEntryMap.get(creditDataEntrySEQ) != null) {
							splitedDataEntrySefMap = (Map) splitedDataEntryMap
									.get(creditDataEntrySEQ);
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1), tmpHap);
						} else {
							splitedDataEntrySefMap = new HashMap();
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1), tmpHap);
							splitedDataEntryMap.put(creditDataEntrySEQ,
									splitedDataEntrySefMap);
						}
					}

				}

				debitDataList.remove(0);
				creditDataList.remove(0);
			} else if (debitLocal.compareTo(creditLocal) > 0) {
				debitLocal = debitLocal.subtract(creditLocal);
				debitFor = debitLocal.divide(rate, scale, 4);
				debitRpt = debitLocal.divide(rptRate, rptScale, 4);
				if (debit_debit_Local.doubleValue() > 0.0D) {
					debitData.put("FDebitFor", debitFor);
					debitData.put("FDebitRpt", debitRpt);
					debitData.put("FDebitLocal", debitLocal);
				} else {
					debitData.put("FCreditFor", debitFor.negate());
					debitData.put("FCreditRpt", debitRpt.negate());
					debitData.put("FCreditLocal", debitLocal.negate());
				}

				if ((accountLongNumber.equals(debitAccountNumber))
						|| (debitAccountNumber.startsWith(accountLongNumber
								+ "!"))) {
					HashMap splitedData = (HashMap) debitData.clone();
					if (this.isShowQuantity) {
						debitPrice = (BigDecimal) debitData.get("FDebitPrice");
						creditPrice = (BigDecimal) debitData
								.get("FCreditPrice");
					}
					if (debit_debit_Local.doubleValue() > 0.0D) {
						splitedData.put("FDebitFor", creditFor);
						splitedData.put("FDebitRpt", creditRpt);
						splitedData.put("FDebitLocal", creditLocal);
						if (this.isShowQuantity)
							if ((debitPrice != null)
									&& (debitPrice.doubleValue() != 0.0D)) {
								if (debitData.get("FDebitUnitId") != null) {
									splitedData.put("FDebitQty", creditLocal
											.divide(debitPrice, scale, 4));
									splitedData.put("FDebitQtyDefault",
											creditLocal.divide(debitPrice,
													scale, 4).multiply(
													coefficient));
								} else {
									debitPrice = debitPrice
											.multiply(coefficient);
									splitedData.put("FDebitPrice", debitPrice);
									splitedData.put("FDebitQty", creditLocal
											.divide(debitPrice, scale, 4));
									splitedData.put("FDebitQtyDefault",
											creditLocal.divide(debitPrice,
													scale, 4).multiply(
													coefficient));
								}
							} else {
								splitedData.put("FDebitQty", GlUtils.zero);
								splitedData.put("FDebitQtyDefault",
										GlUtils.zero);
							}
					} else {
						splitedData.put("FCreditFor", creditFor.negate());
						splitedData.put("FCreditRpt", creditRpt.negate());
						splitedData.put("FCreditLocal", creditLocal.negate());
						if (this.isShowQuantity) {
							if ((creditPrice != null)
									&& (creditPrice.doubleValue() != 0.0D)) {
								if (debitData.get("FCreditUnitId") != null) {
									splitedData.put("FCreditQty", creditLocal
											.negate().divide(creditPrice,
													scale, 4));
									splitedData.put("FCreditQtyDefault",
											creditLocal.negate().divide(
													creditPrice, scale, 4)
													.multiply(coefficient));
								} else {
									creditPrice = creditPrice
											.multiply(coefficient);
									splitedData
											.put("FCreditPrice", creditPrice);
									splitedData.put("FCreditQty", creditLocal
											.negate().divide(creditPrice,
													scale, 4));
									splitedData.put("FCreditQtyDefault",
											creditLocal.negate().divide(
													creditPrice, scale, 4)
													.multiply(coefficient));
								}
							} else {
								splitedData.put("FCreditQty", GlUtils.zero);
								splitedData.put("FCreditQtyDefault",
										GlUtils.zero);
							}
						}
					}
					if ((partner.getCurrencyType() != 0)
							|| ((!partner.getFixCondition().isAllCurrency()) && (partner
									.getCurrency().getId().toString()
									.equalsIgnoreCase((String) splitedData
											.get("currencyID"))))
							|| ((splitedData.get("currencyID") != null) && (splitedData
									.get("currencyID").equals(creditData
									.get("currencyID"))))
							|| (partner.getFixCondition().isAllCurrency())) {
						splitedData.put("accountName", creditData
								.get("accountName"));
						Integer debitDataEntrySEQ = new Integer(debitData.get(
								"FentryID").toString());
						if (splitedDataEntryMap.get(debitDataEntrySEQ) != null) {
							splitedDataEntrySefMap = (Map) splitedDataEntryMap
									.get(debitDataEntrySEQ);
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1),
									splitedData);
						} else {
							splitedDataEntrySefMap = new HashMap();
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1),
									splitedData);
							splitedDataEntryMap.put(debitDataEntrySEQ,
									splitedDataEntrySefMap);
						}
					}

				}

				if ((accountLongNumber.equals(creditAccountNumber))
						|| (creditAccountNumber.startsWith(accountLongNumber
								+ "!"))) {
					HashMap splitedData = (HashMap) creditData.clone();
					if (this.isShowQuantity) {
						creditPrice = (BigDecimal) creditData
								.get("FCreditPrice");
						debitPrice = (BigDecimal) creditData.get("FDebitPrice");
					}
					if (credit_credit_Local.doubleValue() > 0.0D) {
						if (this.isShowQuantity) {
							if ((creditPrice != null)
									&& (creditPrice.doubleValue() != 0.0D)) {
								if (creditData.get("FCreditUnitId") != null) {
									splitedData.put("FCreditQty", creditLocal
											.divide(creditPrice, scale, 4));
									splitedData.put("FCreditQtyDefault",
											creditLocal.divide(creditPrice,
													scale, 4).multiply(
													coefficient));
								} else {
									creditPrice = creditPrice
											.multiply(coefficient);
									splitedData
											.put("FCreditPrice", creditPrice);
									splitedData.put("FCreditQty", creditLocal
											.divide(creditPrice, scale, 4));
									splitedData.put("FCreditQtyDefault",
											creditLocal.divide(creditPrice,
													scale, 4).multiply(
													coefficient));
								}
							} else {
								splitedData.put("FCreditQty", GlUtils.zero);
								splitedData.put("FCreditQtyDefault",
										GlUtils.zero);
							}
						}
					} else if (this.isShowQuantity) {
						if ((debitPrice != null)
								&& (debitPrice.doubleValue() != 0.0D)) {
							if (creditData.get("FDebitUnitId") != null) {
								splitedData.put("FDebitQty", creditLocal
										.negate().divide(debitPrice, scale, 4));
								splitedData.put("FDebitQtyDefault", creditLocal
										.negate().divide(debitPrice, scale, 4)
										.multiply(coefficient));
							} else {
								debitPrice = debitPrice.multiply(coefficient);
								splitedData.put("FDebitPrice", debitPrice);
								splitedData.put("FDebitQty", creditLocal
										.negate().divide(debitPrice, scale, 4));
								splitedData.put("FDebitQtyDefault", creditLocal
										.negate().divide(debitPrice, scale, 4)
										.multiply(coefficient));
							}
						} else {
							splitedData.put("FDebitQty", GlUtils.zero);
							splitedData.put("FDebitQtyDefault", GlUtils.zero);
						}
					}

					if ((partner.getCurrencyType() != 0)
							|| ((!partner.getFixCondition().isAllCurrency()) && (partner
									.getCurrency().getId().toString()
									.equalsIgnoreCase((String) splitedData
											.get("currencyID"))))
							|| ((debitData.get("currencyID") != null) && (debitData
									.get("currencyID").equals(splitedData
									.get("currencyID"))))
							|| (partner.getFixCondition().isAllCurrency())) {
						splitedData.put("accountName", debitData
								.get("accountName"));
						splitedData.put("oppFentryID", creditData.get(
								"FentryID").toString());
						debitDataList0.put(String.valueOf(debitCount++),
								splitedData);
					}
				}
				creditDataList.remove(0);
			} else {
				creditLocal = creditLocal.subtract(debitLocal);
				creditFor = creditLocal.divide(rate, scale, 4);
				creditRpt = creditLocal.divide(rptRate, rptScale, 4);
				if (credit_credit_Local.doubleValue() > 0.0D) {
					creditData.put("FCreditFor", creditFor);
					creditData.put("FCreditRpt", creditRpt);
					creditData.put("FCreditLocal", creditLocal);
				} else {
					creditData.put("FDebitFor", creditFor.negate());
					creditData.put("FDebitRpt", creditRpt.negate());
					creditData.put("FDebitLocal", creditLocal.negate());
				}

				if ((accountLongNumber.equals(creditAccountNumber))
						|| (creditAccountNumber.startsWith(accountLongNumber
								+ "!"))) {
					HashMap splitedData = (HashMap) creditData.clone();
					if (this.isShowQuantity) {
						creditPrice = (BigDecimal) creditData
								.get("FCreditPrice");
						debitPrice = (BigDecimal) creditData.get("FDebitPrice");
					}
					if (credit_credit_Local.doubleValue() > 0.0D) {
						splitedData.put("FCreditFor", debitFor);
						splitedData.put("FCreditRpt", debitRpt);
						splitedData.put("FCreditLocal", debitLocal);
						if (this.isShowQuantity)
							if ((creditPrice != null)
									&& (creditPrice.doubleValue() != 0.0D)) {
								if (creditData.get("FCreditUnitId") != null) {
									splitedData.put("FCreditQty", debitLocal
											.divide(creditPrice, scale, 4));
									splitedData.put("FCreditQtyDefault",
											debitLocal.divide(creditPrice,
													scale, 4).multiply(
													coefficient));
								} else {
									creditPrice = creditPrice
											.multiply(coefficient);
									splitedData
											.put("FCreditPrice", creditPrice);
									splitedData.put("FCreditQty", debitLocal
											.divide(creditPrice, scale, 4));
									splitedData.put("FCreditQtyDefault",
											debitLocal.divide(creditPrice,
													scale, 4).multiply(
													coefficient));
								}
							} else {
								splitedData.put("FCreditQty", GlUtils.zero);
								splitedData.put("FCreditQtyDefault",
										GlUtils.zero);
							}
					} else {
						splitedData.put("FDebitFor", debitFor.negate());
						splitedData.put("FDebitRpt", debitRpt.negate());
						splitedData.put("FDebitLocal", debitLocal.negate());
						if (this.isShowQuantity) {
							if ((debitPrice != null)
									&& (debitPrice.doubleValue() != 0.0D)) {
								if (creditData.get("FDebitUnitId") != null) {
									splitedData.put("FDebitQty", debitLocal
											.negate().divide(debitPrice, scale,
													4));
									splitedData.put("FDebitQtyDefault",
											debitLocal.negate().divide(
													debitPrice, scale, 4)
													.multiply(coefficient));
								} else {
									debitPrice = debitPrice
											.multiply(coefficient);
									splitedData.put("FDebitPrice", debitPrice);
									splitedData.put("FDebitQty", debitLocal
											.negate().divide(debitPrice, scale,
													4));
									splitedData.put("FDebitQtyDefault",
											debitLocal.negate().divide(
													debitPrice, scale, 4)
													.multiply(coefficient));
								}
							} else {
								splitedData.put("FDebitQty", GlUtils.zero);
								splitedData.put("FDebitQtyDefault",
										GlUtils.zero);
							}
						}
					}
					if ((partner.getCurrencyType() != 0)
							|| ((!partner.getFixCondition().isAllCurrency()) && (partner
									.getCurrency().getId().toString()
									.equalsIgnoreCase((String) splitedData
											.get("currencyID"))))
							|| ((debitData.get("currencyID") != null) && (debitData
									.get("currencyID").equals(splitedData
									.get("currencyID"))))
							|| (partner.getFixCondition().isAllCurrency())) {
						splitedData.put("accountName", debitData
								.get("accountName"));
						Integer creditDataEntrySEQ = new Integer(creditData
								.get("FentryID").toString());
						if (splitedDataEntryMap.get(creditDataEntrySEQ) != null) {
							splitedDataEntrySefMap = (Map) splitedDataEntryMap
									.get(creditDataEntrySEQ);
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1),
									splitedData);
						} else {
							splitedDataEntrySefMap = new HashMap();
							splitedDataEntrySefMap.put(new Integer(
									splitedDataEntrySefMap.size() + 1),
									splitedData);
							splitedDataEntryMap.put(creditDataEntrySEQ,
									splitedDataEntrySefMap);
						}
					}
				}

				if ((accountLongNumber.equals(debitAccountNumber))
						|| (debitAccountNumber.startsWith(accountLongNumber
								+ "!"))) {
					HashMap splitedData = (HashMap) debitData.clone();
					if (this.isShowQuantity) {
						debitPrice = (BigDecimal) debitData.get("FDebitPrice");
						creditPrice = (BigDecimal) debitData
								.get("FCreditPrice");
					}
					if (debit_debit_Local.doubleValue() > 0.0D) {
						if (this.isShowQuantity) {
							if ((debitPrice != null)
									&& (debitPrice.doubleValue() != 0.0D)) {
								if (debitData.get("FDebitUnitId") != null) {
									splitedData.put("FDebitQty", debitLocal
											.divide(debitPrice, scale, 4));
									splitedData.put("FDebitQtyDefault",
											debitLocal.divide(debitPrice,
													scale, 4).multiply(
													coefficient));
								} else {
									debitPrice = debitPrice
											.multiply(coefficient);
									splitedData.put("FDebitPrice", debitPrice);
									splitedData.put("FDebitQty", debitLocal
											.divide(debitPrice, scale, 4));
									splitedData.put("FDebitQtyDefault",
											debitLocal.divide(debitPrice,
													scale, 4).multiply(
													coefficient));
								}
							} else {
								splitedData.put("FDebitQty", GlUtils.zero);
								splitedData.put("FDebitQtyDefault",
										GlUtils.zero);
							}
						}
					} else if (this.isShowQuantity) {
						if ((creditPrice != null)
								&& (creditPrice.doubleValue() != 0.0D)) {
							if (debitData.get("FCreditUnitId") != null) {
								splitedData
										.put("FCreditQty", debitLocal.negate()
												.divide(creditPrice, scale, 4));
								splitedData.put("FCreditQtyDefault", debitLocal
										.negate().divide(creditPrice, scale, 4)
										.multiply(coefficient));
							} else {
								creditPrice = creditPrice.multiply(coefficient);
								splitedData.put("FCreditPrice", creditPrice);
								splitedData
										.put("FCreditQty", debitLocal.negate()
												.divide(creditPrice, scale, 4));
								splitedData.put("FCreditQtyDefault", debitLocal
										.negate().divide(creditPrice, scale, 4)
										.multiply(coefficient));
							}
						} else {
							splitedData.put("FCreditQty", GlUtils.zero);
							splitedData.put("FCreditQtyDefault", GlUtils.zero);
						}
					}

					if ((partner.getCurrencyType() != 0)
							|| ((!partner.getFixCondition().isAllCurrency()) && (partner
									.getCurrency().getId().toString()
									.equalsIgnoreCase((String) splitedData
											.get("currencyID"))))
							|| ((splitedData.get("currencyID") != null) && (splitedData
									.get("currencyID").equals(creditData
									.get("currencyID"))))
							|| (partner.getFixCondition().isAllCurrency())) {
						splitedData.put("accountName", creditData
								.get("accountName"));
						splitedData.put("oppFentryID", debitData
								.get("FentryID").toString());
						creditDataList0.put(String.valueOf(creditCount++),
								splitedData);
					}
				}
				debitDataList.remove(0);
			}
		} while ((debitDataList != null) && (creditDataList != null)
				&& (debitDataList.size() > 0) && (creditDataList.size() > 0));

		if ((creditDataList0 != null) && (creditDataList0.size() != 0)) {
			for (int i = 0; i < creditCount; i++) {
				Map value = (HashMap) creditDataList0.get(String.valueOf(i));
				Integer debitDataEntrySEQ = new Integer(value
						.get("oppFentryID").toString());
				if (splitedDataEntryMap.get(debitDataEntrySEQ) != null) {
					splitedDataEntrySefMap = (Map) splitedDataEntryMap
							.get(debitDataEntrySEQ);
					splitedDataEntrySefMap.put(new Integer(
							splitedDataEntrySefMap.size() + 1), value);
				} else {
					splitedDataEntrySefMap = new HashMap();
					splitedDataEntrySefMap.put(new Integer(
							splitedDataEntrySefMap.size() + 1), value);
					splitedDataEntryMap.put(debitDataEntrySEQ,
							splitedDataEntrySefMap);
				}
			}
		}

		if ((debitDataList0 != null) && (debitDataList0.size() != 0)) {
			for (int i = 0; i < debitCount; i++) {
				Map value = (HashMap) debitDataList0.get(String.valueOf(i));
				Integer creditDataEntrySEQ = new Integer(value.get(
						"oppFentryID").toString());
				if (splitedDataEntryMap.get(creditDataEntrySEQ) != null) {
					splitedDataEntrySefMap = (Map) splitedDataEntryMap
							.get(creditDataEntrySEQ);
					splitedDataEntrySefMap.put(new Integer(
							splitedDataEntrySefMap.size() + 1), value);
				} else {
					splitedDataEntrySefMap = new HashMap();
					splitedDataEntrySefMap.put(new Integer(
							splitedDataEntrySefMap.size() + 1), value);
					splitedDataEntryMap.put(creditDataEntrySEQ,
							splitedDataEntrySefMap);
				}
			}

		}

		List splitedDataEntryList = new ArrayList(splitedDataEntryMap.keySet());
		Collections.sort(splitedDataEntryList, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Integer) o1).intValue() - ((Integer) o2).intValue();
			}
		});
		BigDecimal sumCQty = new BigDecimal("0");
		BigDecimal sumDQty = new BigDecimal("0");
		BigDecimal sumCQtyDefault = new BigDecimal("0");
		BigDecimal sumDQtyDefault = new BigDecimal("0");

		BigDecimal sumCFor = new BigDecimal("0");
		BigDecimal sumDFor = new BigDecimal("0");
		BigDecimal sumCRpt = new BigDecimal("0");
		BigDecimal sumDRpt = new BigDecimal("0");
		for (int i = 0; i < splitedDataEntryList.size(); i++) {
			Map splitedDataEntry = (Map) splitedDataEntryMap
					.get(splitedDataEntryList.get(i));

			List splitedDataEntrySefList = new ArrayList(splitedDataEntry
					.keySet());

			Collections.sort(splitedDataEntrySefList, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Integer) o1).intValue()
							- ((Integer) o2).intValue();
				}
			});
			for (int j = 0; j < splitedDataEntrySefList.size(); j++) {
				HashMap splitedDataEntrySef = (HashMap) splitedDataEntry
						.get(splitedDataEntrySefList.get(j));
				if ((i == splitedDataEntryList.size() - 1)
						&& (j == splitedDataEntrySefList.size() - 1)) {
					Object cFor = splitedDataEntrySef.get("FCreditFor");
					Object dFor = splitedDataEntrySef.get("FDebitFor");
					if (cFor != null) {
						splitedDataEntrySef.put("FCreditFor", totalCreditFor
								.subtract(sumCFor));
					}
					if (dFor != null) {
						splitedDataEntrySef.put("FDebitFor", totalDebitFor
								.subtract(sumDFor));
					}

					if (isShowRptCuryCol) {
						Object cRpt = splitedDataEntrySef.get("FCreditRpt");
						Object dRpt = splitedDataEntrySef.get("FDebitRpt");
						if (cRpt != null) {
							splitedDataEntrySef.put("FCreditRpt",
									totalCreditRpt.subtract(sumCRpt));
						}
						if (dRpt != null)
							splitedDataEntrySef.put("FDebitRpt", totalDebitRpt
									.subtract(sumDRpt));
					}
				} else {
					Object cFor = splitedDataEntrySef.get("FCreditFor");
					Object dFor = splitedDataEntrySef.get("FDebitFor");
					sumCFor = sumCFor.add(cFor == null ? new BigDecimal("0")
							: (BigDecimal) cFor);
					sumDFor = sumDFor.add(dFor == null ? new BigDecimal("0")
							: (BigDecimal) dFor);

					if (isShowRptCuryCol) {
						Object cRpt = splitedDataEntrySef.get("FCreditRpt");
						Object dRpt = splitedDataEntrySef.get("FDebitRpt");
						sumCRpt = sumCRpt
								.add(cRpt == null ? new BigDecimal("0")
										: (BigDecimal) cRpt);
						sumDRpt = sumDRpt
								.add(dRpt == null ? new BigDecimal("0")
										: (BigDecimal) dRpt);
					}
				}
				if (this.isShowQuantity) {
					if ((i == splitedDataEntryList.size() - 1)
							&& (j == splitedDataEntrySefList.size() - 1)) {
						Object cQty = splitedDataEntrySef.get("FCreditQty");
						Object dQty = splitedDataEntrySef.get("FDebitQty");
						Object cQtyDefault = splitedDataEntrySef
								.get("FCreditQtyDefault");
						Object dQtyDefault = splitedDataEntrySef
								.get("FDebitQtyDefault");
						if (cQty != null) {
							splitedDataEntrySef.put("FCreditQty",
									totalCreditQty.subtract(sumCQty));
						}
						if (dQty != null) {
							splitedDataEntrySef.put("FDebitQty", totalDebitQty
									.subtract(sumDQty));
						}
						if (cQtyDefault != null) {
							splitedDataEntrySef.put("FCreditQtyDefault",
									totalCreditQtyDefault
											.subtract(sumCQtyDefault));
						}
						if (dQtyDefault != null)
							splitedDataEntrySef.put("FDebitQtyDefault",
									totalDebitQtyDefault
											.subtract(sumDQtyDefault));
					} else {
						Object cQty = splitedDataEntrySef.get("FCreditQty");
						Object dQty = splitedDataEntrySef.get("FDebitQty");
						Object cQtyDefault = splitedDataEntrySef
								.get("FCreditQtyDefault");
						Object dQtyDefault = splitedDataEntrySef
								.get("FDebitQtyDefault");
						sumCQty = sumCQty
								.add(cQty == null ? new BigDecimal("0")
										: (BigDecimal) cQty);
						sumDQty = sumDQty
								.add(dQty == null ? new BigDecimal("0")
										: (BigDecimal) dQty);
						sumCQtyDefault = sumCQtyDefault
								.add(cQtyDefault == null ? new BigDecimal("0")
										: (BigDecimal) cQtyDefault);
						sumDQtyDefault = sumDQtyDefault
								.add(dQtyDefault == null ? new BigDecimal("0")
										: (BigDecimal) dQtyDefault);
					}
				}
				resultData.add(getLineObjects(partner, splitedDataEntrySef,
						balances, isShowLocalCuryCol, isShowRptCuryCol,
						coefficient));
			}
		}
	}

	public int[] getDCQtyIndexs(ReportPartner partner,
			boolean isShowLocalCuryCol, boolean isShowRptCuryCol) {
		int[] qtyIndexs = new int[2];
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		int index = 0;
		index++;

		if (condition.getOptionShowBusinessDate()) {
			index++;
		}
		index += 4;
		if (condition.isAllCurrency()) {
			index++;
		}
		if (isShowLocalCuryCol) {
			index++;
		}
		if (isShowRptCuryCol) {
			index++;
		}
		index++;

		qtyIndexs[0] = (index++);
		index += 2;
		if (isShowLocalCuryCol) {
			index++;
		}
		if (isShowRptCuryCol) {
			index++;
		}
		index++;

		qtyIndexs[1] = index;
		return qtyIndexs;
	}

	public CurrencyInfo getCurrency(ReportPartner partner, String currencyId)
			throws BOSException, EASBizException {
		CurrencyInfo currencyInfo = null;
		if (currencyInfo == null) {
			if (currencyId == null)
				return null;
			CurrencyCollection cc = partner.getAllCurrency();
			int i = 0;
			for (int n = cc.size(); i < n; i++) {
				if (currencyId.equals(cc.get(i).getId().toString())) {
					currencyInfo = cc.get(i);
					break;
				}
			}
		}
		return currencyInfo;
	}

	protected void addBalance(BigDecimal[] debits, BigDecimal[] credits,
			BigDecimal[] balances) {
		if (balances == null)
			balances = new BigDecimal[4];
		for (int i = 0; i < balances.length; i++) {
			if (debits[i] != null) {
				if (balances[i] == null) {
					balances[i] = ZERO;
				}
				balances[i] = balances[i].add(debits[i]);
			}
			if (credits[i] != null) {
				if (balances[i] == null) {
					balances[i] = ZERO;
				}
				balances[i] = balances[i].subtract(credits[i]);
			}
		}
	}

	protected int getColumnCount(ReportPartner partner,
			boolean isShowLocalCuryCol, boolean isShowRptCuryCol)
			throws EASBizException, BOSException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		Integer columnCountObject = (Integer) partner
				.getVariable("ColumnCount");
		if (columnCountObject == null) {
			int columnCount = 9;

			if (condition.getOptionShowBusinessDate()) {
				columnCount++;
			}
			if (condition.getOptionOtherAccount()) {
				columnCount += 2;
			}
			if (isShowLocalCuryCol) {
				columnCount += 4;
			}
			if (isShowRptCuryCol) {
				columnCount += 4;
			}
			if (this.isShowQuantity) {
				columnCount += 9;
			}
			if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
					.isAllCurrency()) {
				columnCount += 2;
			}

			columnCount += 2;

			if ((condition.getOptionOnlyAsst())
					|| (condition.getAssisthgId() != null)) {
				columnCount += 2;
			}

			if (condition.getOptionShowAccountCusAttribute()) {
				columnCount += 7;
			}
			columnCountObject = new Integer(columnCount);
		}
		return columnCountObject.intValue();
	}

	protected Object[] getLineObjects(ReportPartner partner, HashMap rs,
			BigDecimal[] balances, boolean isShowLocalCuryCol,
			boolean isShowRptCuryCol, BigDecimal coefficient)
			throws SQLException, BOSException, EASBizException {
		int index = 0;
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		boolean isRptTransferMeasureUnit = partner.isRptTransferMeasureUnit();
		Object[] row = new Object[getColumnCount(partner, isShowLocalCuryCol,
				isShowRptCuryCol)];

		row[(index++)] = rs.get("FDate");

		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionShowBusinessDate()) {
			row[(index++)] = rs.get("FBizDate");
		}
		row[(index++)] = rs.get("FVoucherNumber");
		int lineType = Integer.parseInt(rs.get("FLineType").toString());
		row[(index++)] = (lineType == 0 ? rs.get("FDescription")
				: getDescription(partner, lineType));
		row[(index++)] = rs.get("accountName");

		if (partner.getFixCondition().isAllCurrency()) {
			row[(index++)] = rs.get("FCurrencyName");
		}
		BigDecimal[] debits = new BigDecimal[4];
		BigDecimal[] credits = new BigDecimal[4];
		if (isShowLocalCuryCol) {
			row[(index++)] = GlUtils.getScaleBigDecimal((BigDecimal) rs
					.get("FLocalRate"), partner.getScaleRateLocal(partner
					.getFixCondition().isAllCurrency() ? null : rs
					.get("fcurrencyid") != null ? rs.get("fcurrencyid")
					.toString() : null));
		}
		if (isShowRptCuryCol) {
			row[(index++)] = GlUtils.getScaleBigDecimal((BigDecimal) rs
					.get("FRptRate"), partner.getScaleRateRpt(partner
					.getFixCondition().isAllCurrency() ? null : rs
					.get("fcurrencyid") != null ? rs.get("fcurrencyid")
					.toString() : null));
		}

		debits[1] = ((BigDecimal) rs.get("FDebitFor"));
		if (this.isShowQuantity) {
			debits[0] = ((BigDecimal) rs.get("FDebitQtyDefault"));

			MeasureUnitInfo measureUnitInfo = null;
			if (lineType == 0) {
				if ((partner.getParmQtyAsst())
						&& (partner.getAccountViewInfo().getCAA() != null)
						&& (!condition.getOptionOnlyAsst())
						&& (this.isTransAccountDefaultMu)
						&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
						&& (this.isShowUnit)) {
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				} else
					measureUnitInfo = partner
							.getMeasureUnitInfoForAsst((String) rs
									.get("FDebitUnitId"));
			} else {
				if ((this.isSameCreditUnitGroup) && (this.isSameDebitUnitGroup)
						&& (this.isShowUnit)) {
					if ((isRptTransferMeasureUnit)
							&& (partner.getAccountViewInfo().getCAA() == null))
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else {
						measureUnitInfo = partner
								.getBaseMeasureUnitInfoForAccount();
					}
				}
				if ((this.isTransAccountDefaultMu)
						&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
						&& (this.isShowUnit)) {
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				}
			}
			row[index] = (measureUnitInfo == null ? null : measureUnitInfo
					.getName());
			index++;

			BigDecimal qty = null;
			if (lineType == 0) {
				if ((partner.getParmQtyAsst())
						&& (partner.getAccountViewInfo().getCAA() != null)
						&& (!condition.getOptionOnlyAsst())
						&& (this.isTransAccountDefaultMu)
						&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
						&& (this.isShowUnit)) {
					qty = (BigDecimal) rs.get("FDebitQtyDefault");
					if (qty != null)
						qty = qty.divide(coefficient, 4, 4);
				} else {
					qty = (BigDecimal) rs.get("FDebitQty");
				}
			} else {
				qty = debits[0];

				if (qty != null) {
					qty = qty.divide(coefficient, 4, 4);
				}
			}
			row[(index++)] = getBigDecimalForScale(qty, partner
					.getParmQtyScale());

			if (lineType == 0) {
				BigDecimal price = (BigDecimal) rs.get("FDebitPrice");

				if ((price == null) || (partner.getCurrencyType() == 1)
						|| (partner.getCurrencyType() == 2)) {
					index++;
				} else {
					if ((partner.getParmQtyAsst())
							&& (partner.getAccountViewInfo().getCAA() != null)
							&& (!condition.getOptionOnlyAsst())
							&& (this.isTransAccountDefaultMu)
							&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
							&& (this.isShowUnit) && (qty != null)
							&& (qty.compareTo(GlUtils.zero) != 0)) {
						price = ((BigDecimal) rs.get("FDebitFor")).divide(qty,
								partner.getParmPriceScale(), 4);
					}
					row[(index++)] = getBigDecimalForScale(price.abs(), partner
							.getParmPriceScale());
				}
			} else if ((debits[1] == null) || (qty == null)
					|| (debits[1].compareTo(ZERO) == 0)
					|| (debits[0].compareTo(ZERO) == 0) || (qty == null)
					|| (qty.compareTo(GlUtils.zero) == 0)) {
				row[(index++)] = null;
			} else {
				row[(index++)] = debits[1].divide(qty,
						partner.getParmPriceScale(), 4).abs();
			}

		}

		row[(index++)] = getBigDecimalForScale(debits[1], getScaleFor(partner,
				rs.get("currencyID") != null ? rs.get("currencyID").toString()
						: null));

		if (isShowLocalCuryCol) {
			debits[2] = ((BigDecimal) rs.get("FDebitLocal"));
			row[(index++)] = getBigDecimalForScale(debits[2], partner
					.getScaleLocal());
		}

		if (isShowRptCuryCol) {
			debits[3] = ((BigDecimal) rs.get("FDebitRpt"));
			row[(index++)] = getBigDecimalForScale(debits[3], partner
					.getScaleRpt());
		}

		credits[1] = ((BigDecimal) rs.get("FCreditFor"));
		if (this.isShowQuantity) {
			credits[0] = ((BigDecimal) rs.get("FCreditQtyDefault"));

			MeasureUnitInfo measureUnitInfo = null;
			if (lineType == 0) {
				if ((partner.getParmQtyAsst())
						&& (partner.getAccountViewInfo().getCAA() != null)
						&& (!condition.getOptionOnlyAsst())
						&& (this.isTransAccountDefaultMu)
						&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
						&& (this.isShowUnit)) {
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				} else
					measureUnitInfo = partner
							.getMeasureUnitInfoForAsst((String) rs
									.get("FCreditUnitId"));
			} else {
				if ((this.isSameCreditUnitGroup) && (this.isSameDebitUnitGroup)
						&& (this.isShowUnit)) {
					if ((isRptTransferMeasureUnit)
							&& (partner.getAccountViewInfo().getCAA() == null))
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else {
						measureUnitInfo = partner
								.getBaseMeasureUnitInfoForAccount();
					}
				}
				if ((this.isTransAccountDefaultMu)
						&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
						&& (this.isShowUnit)) {
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				}
			}
			row[index] = (measureUnitInfo == null ? null : measureUnitInfo
					.getName());
			index++;

			BigDecimal qty = null;
			if (lineType == 0) {
				if ((partner.getParmQtyAsst())
						&& (partner.getAccountViewInfo().getCAA() != null)
						&& (!condition.getOptionOnlyAsst())
						&& (this.isTransAccountDefaultMu)
						&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
						&& (this.isShowUnit)) {
					qty = (BigDecimal) rs.get("FCreditQtyDefault");
					if (qty != null)
						qty = qty.divide(coefficient, 4, 4);
				} else {
					qty = (BigDecimal) rs.get("FCreditQty");
				}
			} else {
				qty = debits[0];

				if (qty != null) {
					qty = qty.divide(coefficient, 4, 4);
				}
			}
			row[(index++)] = getBigDecimalForScale(qty, partner
					.getParmQtyScale());

			if (lineType == 0) {
				BigDecimal price = (BigDecimal) rs.get("FCreditPrice");

				if ((price == null) || (partner.getCurrencyType() == 1)
						|| (partner.getCurrencyType() == 2)) {
					index++;
				} else {
					if ((partner.getParmQtyAsst())
							&& (partner.getAccountViewInfo().getCAA() != null)
							&& (!condition.getOptionOnlyAsst())
							&& (this.isTransAccountDefaultMu)
							&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
							&& (this.isShowUnit) && (qty != null)
							&& (qty.compareTo(GlUtils.zero) != 0)) {
						price = ((BigDecimal) rs.get("FCreditFor")).divide(qty,
								partner.getParmPriceScale(), 4);
					}
					row[(index++)] = getBigDecimalForScale(price.abs(), partner
							.getParmPriceScale());
				}
			} else if ((credits[1] == null) || (qty == null)
					|| (credits[1].compareTo(ZERO) == 0)
					|| (credits[0].compareTo(ZERO) == 0) || (qty == null)
					|| (qty.compareTo(GlUtils.zero) == 0)) {
				row[(index++)] = null;
			} else {
				row[(index++)] = credits[1].divide(qty,
						partner.getParmPriceScale(), 4).abs();
			}

		}

		credits[1] = ((BigDecimal) rs.get("FCreditFor"));
		row[(index++)] = getBigDecimalForScale(credits[1], getScaleFor(partner,
				rs.get("currencyID") != null ? rs.get("currencyID").toString()
						: null));

		if (isShowLocalCuryCol) {
			credits[2] = ((BigDecimal) rs.get("FCreditLocal"));
			row[(index++)] = getBigDecimalForScale(credits[2], partner
					.getScaleLocal());
		}

		if (isShowRptCuryCol) {
			credits[3] = ((BigDecimal) rs.get("FCreditRpt"));
			row[(index++)] = getBigDecimalForScale(credits[3], partner
					.getScaleRpt());
		}

		if (lineType == 0) {
			addBalance(debits, credits, balances);
		}
		if (this.isShowQuantity) {
			MeasureUnitInfo measureUnitInfo = null;
			if ((this.isSameCreditUnitGroup) && (this.isSameDebitUnitGroup)
					&& (this.isShowUnit)) {
				if ((partner.getAccountViewInfo().getCAA() == null)
						&& (partner.isRptTransferMeasureUnit()))
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				else {
					measureUnitInfo = partner
							.getMeasureUnitInfoForAccount((String) rs
									.get("FBalanceUnitId"));
				}
			}

			if (lineType != 0) {
				if ((isRptTransferMeasureUnit)
						&& (partner.getAccountViewInfo().getCAA() == null))
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				else {
					measureUnitInfo = partner
							.getBaseMeasureUnitInfoForAccount();
				}
			}
			if ((this.isTransAccountDefaultMu)
					&& (partner.getDefaultMeasureUnitInfoForAccount() != null)
					&& (this.isShowUnit)) {
				measureUnitInfo = partner.getDefaultMeasureUnitInfoForAccount();
			}

			row[index] = (measureUnitInfo == null ? null : measureUnitInfo
					.getName());
			index++;

			BigDecimal qty = balances[0];

			if (qty != null) {
				qty = qty.divide(coefficient, 4, 4);
			}
			row[(index++)] = getBigDecimalForScale(qty, partner
					.getParmQtyScale());

			if ((balances[0].compareTo(ZERO) == 0) || (qty == null)
					|| (qty.compareTo(GlUtils.zero) == 0))
				index++;
			else {
				try {
					BigDecimal rowendqty = balances[1].divide(qty,
							partner.getParmPriceScale(), 4).abs();
					row[(index++)] = rowendqty;
				} catch (ArithmeticException e) {
					index++;
				}
			}
		}

		row[(index++)] = partner.getDCString(balances[1]);

		row[(index++)] = getBigDecimalForScale(partner
				.getBalanceForDC(balances[1]), getScaleFor(partner, rs
				.get("currencyID") != null ? rs.get("currencyID").toString()
				: null));

		if ((balances[1] != null)
				&& (!partner.getBalanceForDC(balances[1]).equals(balances[1]))) {
			if ((this.isShowQuantity) && (row[(index - 4)] != null)) {
				row[(index - 4)] = ((BigDecimal) row[(index - 4)]).negate();
			}
		}

		if (isShowLocalCuryCol) {
			row[(index++)] = getBigDecimalForScale(partner
					.getBalanceForDC(balances[2]), partner.getScaleLocal());
		}

		if (isShowRptCuryCol) {
			row[(index++)] = getBigDecimalForScale(partner
					.getBalanceForDC(balances[3]), partner.getScaleRpt());
		}

		row[(index++)] = rs.get("FNumber");
		row[(index++)] = rs.get("FName");

		row[(index++)] = rs.get("currencyID");
		if (partner.getFixCondition().isAllCurrency()) {
			row[(index++)] = rs.get("fcurrencyid");
		}
		row[(index++)] = (lineType == -1 ? ReportResultInfo.LINE_DATE_BEGIN
				: lineType == -2 ? ReportResultInfo.LINE_PERIOD_BEGIN
						: lineType == -3 ? ReportResultInfo.LINE_YEAR_BEGIN
								: new Integer(lineType));

		row[(index++)] = rs.get("FVoucherID");
		return row;
	}

	protected Object[] getLineObjects(ReportPartner partner, ResultSet rs,
			BigDecimal[] balances, boolean isFirstLine,
			boolean isShowLocalCuryCol, boolean isShowRptCuryCol,
			BigDecimal coefficient) throws SQLException, EASBizException,
			BOSException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		int assiId = 0;
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionOnlyAsst())
			assiId = rs.getInt("FdivisionBalance");
		if ((condition.getOptionOnlyAsst() || condition.getAssisthgId() != null)
				&& k == -1 || assiId == 2) {
			StringBuffer sqlForAsstCount = new StringBuffer();
			sqlForAsstCount.append("SELECT MAX(FCount) FROM  ").append(
					partner.getTempTableName());
			RptRowSet rsCount;
			try {
				rsCount = DBUtilx.executeQuery(sqlForAsstCount.toString(),
						null, partner.getContext());
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			if (rsCount.next())
				if (rsCount.getObject(0) != null)
					k = rsCount.getInt(0);
				else
					k = 0;
		}
		int index = 0;
		Object row[] = new Object[getColumnCount(partner, isShowLocalCuryCol,
				isShowRptCuryCol)];
		if ((condition.getOptionOnlyAsst() || condition.getAssisthgId() != null)
				&& isShowQuantity && getPar(partner))
			row = new Object[getColumnCount(partner, isShowLocalCuryCol,
					isShowRptCuryCol)
					+ k + 6];
		else if (condition.getOptionOnlyAsst()
				|| condition.getAssisthgId() != null)
			row = new Object[getColumnCount(partner, isShowLocalCuryCol,
					isShowRptCuryCol)
					+ k];
		int accountOrAssist = 0;
		if (condition.getOptionOnlyAsst() || condition.getAssisthgId() != null)
			accountOrAssist = rs.getInt("FAccountOrAssist");
		row[index++] = rs.getDate("FDate");
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionShowBusinessDate())
			row[index++] = rs.getDate("FBizDate");
		row[index++] = rs.getString("FVoucherTypeName") != null ? ((Object) ((new StringBuilder())
				.append(rs.getString("FVoucherTypeName")).append(" - ").append(
						rs.getString("FVoucherNumber")).toString()))
				: null;
		int lineType = rs.getInt("FLineType");
		row[index++] = lineType != 0 ? ((Object) (getDescription(partner,
				lineType))) : ((Object) (rs.getString("FDescription")));
		if (condition.getOptionOtherAccount())
			row[index++] = rs.getString("accountName");
		if (condition.getOptionOnlyAsst() || condition.getAssisthgId() != null) {
			for (int i = 0; i < k; i++)
				if (accountOrAssist == 2) {
					String asstName = rs.getString("FAsstAccountName");
					if (asstName != null){
						if(asstName.lastIndexOf(";")>-1){
							asstName = asstName.substring(0, asstName.trim()
									.length() - 1);
						}else{
							asstName = asstName;
						}
					}
					String strAsstName[] = StringUtil.split(asstName, ";");
					if (strAsstName != null && strAsstName.length > i)
						row[index++] = strAsstName[i];
					else
						row[index++] = "";
				} else {
					row[index++] = "";
				}

		}
		if (condition.isAllCurrency())
			row[index++] = rs.getString("FCurrencyName");
		boolean isRptTransferMeasureUnit = partner.isRptTransferMeasureUnit();
		if (isFirstLine || lineType == -2 || lineType == -3) {
			if (isShowLocalCuryCol)
				index += 3;
			if (isShowRptCuryCol)
				index += 3;
			if (isShowQuantity)
				index += 6;
			if ((condition.getOptionOnlyAsst() || condition.getAssisthgId() != null)
					&& isShowQuantity && getPar(partner))
				index += 6;
			index += 2;
			if (isShowQuantity) {
				MeasureUnitInfo measureUnitInfo = null;
				if (isSameCreditUnitGroup && isSameDebitUnitGroup && isShowUnit)
					if (isRptTransferMeasureUnit
							&& partner.getAccountViewInfo().getCAA() == null)
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else
						measureUnitInfo = partner
								.getBaseMeasureUnitInfoForAccount();
				if (isTransAccountDefaultMu
						&& partner.getDefaultMeasureUnitInfoForAccount() != null
						&& isShowUnit)
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				row[index] = measureUnitInfo != null ? ((Object) (measureUnitInfo
						.getName()))
						: null;
				index++;
				BigDecimal endQty = getBigDecimalForScale(balances[0], partner
						.getParmQtyScale());
				if (endQty != null)
					endQty = endQty.divide(coefficient, partner
							.getParmQtyScale(), 4);
				row[index++] = endQty;
				if (balances[0].compareTo(ZERO) == 0 || endQty == null
						|| endQty.compareTo(GlUtils.zero) == 0)
					index++;
				else
					row[index++] = balances[1].divide(endQty,
							partner.getParmPriceScale(), 4).abs();
			}
			row[index++] = partner.getDCString(balances[1]);
			row[index++] = getBigDecimalForScale(partner
					.getBalanceForDC(balances[1]), getScaleFor(partner,
					condition.isAllCurrency() ? rs.getString("fcurrencyid")
							: null));
			if (balances[1] != null
					&& !partner.getBalanceForDC(balances[1])
							.equals(balances[1]) && isShowQuantity
					&& row[index - 4] != null)
				row[index - 4] = ((BigDecimal) row[index - 4]).negate();
			if (isShowLocalCuryCol) {
				balances[2] = rs.getBigDecimal("FDebitLocal");
				row[index++] = getBigDecimalForScale(partner
						.getBalanceForDC(balances[2]), partner.getScaleLocal());
			}
			if (isShowRptCuryCol) {
				balances[3] = rs.getBigDecimal("FDebitRpt");
				row[index++] = getBigDecimalForScale(partner
						.getBalanceForDC(balances[3]), partner.getScaleRpt());
			}
		} else {
			BigDecimal debits[] = new BigDecimal[4];
			BigDecimal credits[] = new BigDecimal[4];
			if (isShowLocalCuryCol)
				row[index++] = GlUtils.getScaleBigDecimal(rs
						.getBigDecimal("FLocalRate"), partner
						.getScaleRateLocal(condition.isAllCurrency() ? rs
								.getString("fcurrencyid") : null));
			if (isShowRptCuryCol)
				row[index++] = GlUtils.getScaleBigDecimal(rs
						.getBigDecimal("FRptRate"), partner
						.getScaleRateRpt(condition.isAllCurrency() ? rs
								.getString("fcurrencyid") : null));
			debits[1] = rs.getBigDecimal("FDebitFor");
			if (isShowQuantity) {
				debits[0] = rs.getBigDecimal("FDebitQtyDefault");
				MeasureUnitInfo measureUnitInfo = null;
				if (lineType == 0) {
					if (partner.getParmQtyAsst()
							&& partner.getAccountViewInfo().getCAA() != null
							&& !condition.getOptionOnlyAsst()
							&& isTransAccountDefaultMu
							&& partner.getDefaultMeasureUnitInfoForAccount() != null
							&& isShowUnit)
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else
						measureUnitInfo = partner.getMeasureUnitInfoForAsst(rs
								.getString("FDebitUnitId"));
				} else {
					if (isSameCreditUnitGroup && isSameDebitUnitGroup
							&& isShowUnit)
						if (isRptTransferMeasureUnit
								&& partner.getAccountViewInfo().getCAA() == null)
							measureUnitInfo = partner
									.getDefaultMeasureUnitInfoForAccount();
						else
							measureUnitInfo = partner
									.getBaseMeasureUnitInfoForAccount();
					if (isTransAccountDefaultMu
							&& partner.getDefaultMeasureUnitInfoForAccount() != null
							&& isShowUnit)
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
				}
				row[index] = measureUnitInfo != null ? ((Object) (measureUnitInfo
						.getName()))
						: null;
				index++;
				BigDecimal qty = null;
				if (lineType == 0) {
					if (partner.getParmQtyAsst()
							&& partner.getAccountViewInfo().getCAA() != null
							&& !condition.getOptionOnlyAsst()
							&& isTransAccountDefaultMu
							&& partner.getDefaultMeasureUnitInfoForAccount() != null
							&& isShowUnit) {
						qty = rs.getBigDecimal("FDebitQtyDefault");
						if (qty != null)
							qty = qty.divide(coefficient, partner
									.getParmQtyScale(), 4);
					} else {
						qty = rs.getBigDecimal("FDebitQty");
					}
				} else {
					qty = debits[0];
					if (qty != null)
						qty = qty.divide(coefficient,
								partner.getParmQtyScale(), 4);
				}
				row[index++] = getBigDecimalForScale(qty, partner
						.getParmQtyScale());
				if (lineType == 0) {
					BigDecimal price = rs.getBigDecimal("FDebitPrice");
					if (price == null || partner.getCurrencyType() == 1
							|| partner.getCurrencyType() == 2) {
						index++;
					} else {
						if (partner.getParmQtyAsst()
								&& partner.getAccountViewInfo().getCAA() != null
								&& !condition.getOptionOnlyAsst()
								&& isTransAccountDefaultMu
								&& partner
										.getDefaultMeasureUnitInfoForAccount() != null
								&& isShowUnit && qty != null
								&& qty.compareTo(GlUtils.zero) != 0)
							price = rs.getBigDecimal("FDebitFor").divide(qty,
									partner.getParmPriceScale(), 4);
						row[index++] = getBigDecimalForScale(price.abs(),
								partner.getParmPriceScale());
					}
				} else if (debits[1] == null || debits[0] == null
						|| debits[1].compareTo(ZERO) == 0
						|| debits[0].compareTo(ZERO) == 0 || qty == null
						|| qty.compareTo(GlUtils.zero) == 0)
					row[index++] = null;
				else
					row[index++] = debits[1].divide(qty,
							partner.getParmPriceScale(), 4).abs();
				if ((condition.getOptionOnlyAsst() || condition.getAssisthgId() != null)
						&& isShowQuantity && getPar(partner)) {
					row[index++] = rs.getString("FDebitAssistUnit");
					row[index++] = rs.getBigDecimal("FDebitAssistPrice");
					row[index++] = rs.getBigDecimal("FDebitAssistQty");
				}
			}
			row[index++] = getBigDecimalForScale(debits[1], getScaleFor(
					partner, condition.isAllCurrency() ? rs
							.getString("fcurrencyid") : null));
			if (isShowLocalCuryCol) {
				debits[2] = rs.getBigDecimal("FDebitLocal");
				row[index++] = getBigDecimalForScale(debits[2], partner
						.getScaleLocal());
			}
			if (isShowRptCuryCol) {
				debits[3] = rs.getBigDecimal("FDebitRpt");
				row[index++] = getBigDecimalForScale(debits[3], partner
						.getScaleRpt());
			}
			credits[1] = rs.getBigDecimal("FCreditFor");
			if (isShowQuantity) {
				credits[0] = rs.getBigDecimal("FCreditQtyDefault");
				MeasureUnitInfo measureUnitInfo = null;
				if (lineType == 0) {
					if (partner.getParmQtyAsst()
							&& partner.getAccountViewInfo().getCAA() != null
							&& !condition.getOptionOnlyAsst()
							&& isTransAccountDefaultMu
							&& partner.getDefaultMeasureUnitInfoForAccount() != null
							&& isShowUnit)
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else
						measureUnitInfo = partner.getMeasureUnitInfoForAsst(rs
								.getString("FCreditUnitId"));
				} else {
					if (isSameCreditUnitGroup && isSameDebitUnitGroup
							&& isShowUnit)
						if (isRptTransferMeasureUnit
								&& partner.getAccountViewInfo().getCAA() == null)
							measureUnitInfo = partner
									.getDefaultMeasureUnitInfoForAccount();
						else
							measureUnitInfo = partner
									.getBaseMeasureUnitInfoForAccount();
					if (isTransAccountDefaultMu
							&& partner.getDefaultMeasureUnitInfoForAccount() != null
							&& isShowUnit)
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
				}
				row[index] = measureUnitInfo != null ? ((Object) (measureUnitInfo
						.getName()))
						: null;
				index++;
				BigDecimal qty = null;
				if (lineType == 0) {
					if (partner.getParmQtyAsst()
							&& partner.getAccountViewInfo().getCAA() != null
							&& !condition.getOptionOnlyAsst()
							&& isTransAccountDefaultMu
							&& partner.getDefaultMeasureUnitInfoForAccount() != null
							&& isShowUnit) {
						qty = rs.getBigDecimal("FCreditQtyDefault");
						if (qty != null)
							qty = qty.divide(coefficient, partner
									.getParmQtyScale(), 4);
					} else {
						qty = rs.getBigDecimal("FCreditQty");
					}
				} else {
					qty = credits[0];
					if (qty != null)
						qty = qty.divide(coefficient,
								partner.getParmQtyScale(), 4);
				}
				row[index++] = getBigDecimalForScale(qty, partner
						.getParmQtyScale());
				if (lineType == 0) {
					BigDecimal price = rs.getBigDecimal("FCreditPrice");
					if (price == null || partner.getCurrencyType() == 1
							|| partner.getCurrencyType() == 2) {
						index++;
					} else {
						if (partner.getParmQtyAsst()
								&& partner.getAccountViewInfo().getCAA() != null
								&& !condition.getOptionOnlyAsst()
								&& isTransAccountDefaultMu
								&& partner
										.getDefaultMeasureUnitInfoForAccount() != null
								&& isShowUnit && qty != null
								&& qty.compareTo(GlUtils.zero) != 0)
							price = rs.getBigDecimal("FCreditFor").divide(qty,
									partner.getParmPriceScale(), 4);
						row[index++] = getBigDecimalForScale(price.abs(),
								partner.getParmPriceScale());
					}
				} else if (credits[1] == null || credits[0] == null
						|| credits[1].compareTo(ZERO) == 0
						|| credits[0].compareTo(ZERO) == 0 || qty == null
						|| qty.compareTo(GlUtils.zero) == 0)
					row[index++] = null;
				else
					row[index++] = credits[1].divide(qty,
							partner.getParmPriceScale(), 4).abs();
				if ((condition.getOptionOnlyAsst() || condition.getAssisthgId() != null)
						&& getPar(partner)) {
					row[index++] = rs.getString("FCreditAssistUnit");
					row[index++] = rs.getBigDecimal("FCreditAssistPrice");
					row[index++] = rs.getBigDecimal("FCreditAssistQty");
				}
			}
			credits[1] = rs.getBigDecimal("FCreditFor");
			row[index++] = getBigDecimalForScale(credits[1], getScaleFor(
					partner, condition.isAllCurrency() ? rs
							.getString("fcurrencyid") : null));
			if (isShowLocalCuryCol) {
				credits[2] = rs.getBigDecimal("FCreditLocal");
				row[index++] = getBigDecimalForScale(credits[2], partner
						.getScaleLocal());
			}
			if (isShowRptCuryCol) {
				credits[3] = rs.getBigDecimal("FCreditRpt");
				row[index++] = getBigDecimalForScale(credits[3], partner
						.getScaleRpt());
			}
			if (lineType == 0)
				addBalance(debits, credits, balances);
			if (isShowQuantity) {
				MeasureUnitInfo measureUnitInfo = null;
				if (isSameCreditUnitGroup && isSameDebitUnitGroup && isShowUnit)
					if (partner.getAccountViewInfo().getCAA() == null
							&& partner.isRptTransferMeasureUnit())
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else
						measureUnitInfo = partner
								.getMeasureUnitInfoForAccount(rs
										.getString("FBalanceUnitId"));
				if (lineType != 0)
					if (isRptTransferMeasureUnit
							&& partner.getAccountViewInfo().getCAA() == null)
						measureUnitInfo = partner
								.getDefaultMeasureUnitInfoForAccount();
					else
						measureUnitInfo = partner
								.getBaseMeasureUnitInfoForAccount();
				if (isTransAccountDefaultMu
						&& partner.getDefaultMeasureUnitInfoForAccount() != null
						&& isShowUnit)
					measureUnitInfo = partner
							.getDefaultMeasureUnitInfoForAccount();
				row[index] = measureUnitInfo != null ? ((Object) (measureUnitInfo
						.getName()))
						: null;
				index++;
				BigDecimal qty = balances[0];
				if (qty != null)
					qty = qty.divide(coefficient, partner.getParmQtyScale(), 4);
				row[index++] = getBigDecimalForScale(qty, partner
						.getParmQtyScale());
				if (balances[0].compareTo(ZERO) == 0 || qty == null
						|| qty.compareTo(GlUtils.zero) == 0)
					index++;
				else
					try {
						BigDecimal rowendqty = balances[1].divide(qty,
								partner.getParmPriceScale(), 4).abs();
						row[index++] = rowendqty;
					} catch (ArithmeticException e) {
						index++;
					}
			}
			row[index++] = partner.getDCString(balances[1]);
			row[index++] = getBigDecimalForScale(partner
					.getBalanceForDC(balances[1]), getScaleFor(partner,
					condition.isAllCurrency() ? rs.getString("fcurrencyid")
							: null));
			if (balances[1] != null
					&& !partner.getBalanceForDC(balances[1])
							.equals(balances[1]) && isShowQuantity
					&& row[index - 4] != null)
				row[index - 4] = ((BigDecimal) row[index - 4]).negate();
			if (isShowLocalCuryCol)
				row[index++] = getBigDecimalForScale(partner
						.getBalanceForDC(balances[2]), partner.getScaleLocal());
			if (isShowRptCuryCol)
				row[index++] = getBigDecimalForScale(partner
						.getBalanceForDC(balances[3]), partner.getScaleRpt());
		}
		row[index++] = rs.getString("FNumber");
		row[index++] = rs.getString("FName");
		if (condition.getOptionOtherAccount())
			row[index++] = rs.getString("currencyID");
		if (condition.isAllCurrency())
			row[index++] = rs.getString("fcurrencyid");
		if (condition.getOptionOnlyAsst() || condition.getAssisthgId() != null) {
			String numberGroupAsst = rs.getString("FNumberGroupAsst");
			row[index++] = numberGroupAsst != null ? ((Object) (numberGroupAsst))
					: null;
			String asstId = rs.getString("FAsstAccountPerId");
			row[index++] = asstId != null ? ((Object) (asstId)) : null;
		}
		row[index++] = lineType != -3 ? ((Object) (lineType != -2 ? ((Object) (lineType != -1 ? ((Object) (new Integer(
				lineType)))
				: ((Object) (ReportResultInfo.LINE_DATE_BEGIN))))
				: ((Object) (ReportResultInfo.LINE_PERIOD_BEGIN))))
				: ((Object) (ReportResultInfo.LINE_YEAR_BEGIN));
		row[index++] = rs.getString("FVoucherID");
		return row;
	}

	protected Object[] getLineObjects(ReportPartner partner, ResultSet rs)
			throws SQLException, EASBizException, BOSException {
		return null;
	}

	protected String getDescription(ReportPartner partner, int lineType) {
		String description = null;

		switch (lineType) {
		case -3:
			description = partner.TITLE_YEAR_START;

			break;
		case -2:
			description = partner.TITLE_PERIOD_START;

			break;
		case 1:
			description = partner.TITLE_DAY_SUM;

			break;
		case 2:
			description = partner.TITLE_DAY_SUM;

			break;
		case 3:
			description = partner.TITLE_PERIOD_SUM;

			break;
		case 4:
			description = partner.TITLE_YEAR_SUM;

			break;
		case -1:
		case 0:
		default:
			description = "UnKnow Line Type";
		}

		return description;
	}

	protected CurrencyCollection getAllCurrency(ReportPartner partner)
			throws BOSException, EASBizException {
		ICurrency currency = CurrencyFactory.getLocalInstance(partner
				.getContext());

		CurrencyCollection currencyCollection = null;

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("precision"));
		sic.add(new SelectorItemInfo("isoCode"));
		sic.add(new SelectorItemInfo("sign"));
		sic.add(new SelectorItemInfo("BaseUnit"));

		if (partner.isParmForeignCurrencySu()) {
			currencyCollection = currency.getCurrencyCollection(view, true);
		} else {
			if (!partner.isParmUseReportCurrency()) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								CurrencyInfo.GENERAL_REPORT_CURRENCY_ID,
								CompareType.NOTEQUALS));
				view.setFilter(filter);
			}

			currencyCollection = currency.getCurrencyCollection(view, false);
		}

		return currencyCollection;
	}

	protected CurrencyInfo getDefaultCurrency(ReportPartner partner)
			throws BOSException, EASBizException {
		return partner.getCompany().getBaseCurrency();
	}

	protected String getTempTableStruct(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		boolean isQtyAsst = partner.getParmQtyAsst();
		this.isShowQuantity = getIsShowQuantity(partner);

		StringBuffer sql = new StringBuffer();
		sql.append("( \r\n");
		if ("USE_KSQL_SEQ".equals(partner.getVariable("USE_KSQL_SEQ"))) {
			sql.append("\tKSQL_SEQ INT IDENTITY (1, 1), \r\n");
		}
		sql.append("\tFDate DateTime, \r\n");
		sql.append("\tFBizDate DateTime, \r\n");
		sql.append("\tFVoucherNumber NVarchar(640), \r\n");
		sql.append("\tFVoucherTypeNumber NVarchar(40), \r\n");
		sql.append("\tFVoucherTypeName NVarchar(40), \r\n");
		sql.append("\tFCREATETIME DateTime, \r\n");
		sql.append("    FSeq Int,\r\n");
		sql.append("\tFDescription NVarchar(320), \r\n");
		if (condition.getOptionOtherAccount()) {
			sql.append("\taccountName NVarchar(640), \r\n");
			sql.append("\taccountLongNumber NVarchar(400), \r\n");
			sql.append("\tcurrencyID varChar(44),\r\n");
		}
		sql.append("\tFSettlementType NVarchar(320), \r\n");
		sql.append("\tFSettlementCode NVarchar(320), \r\n");
		sql.append("\tFBizNumber NVarchar(320), \r\n");
		sql.append("\tFTicketNumber NVARCHAR(320), \r\n");
		sql.append("\tFInvoiceNumber NVARCHAR(320) , \r\n");
		sql.append("\tFFeeType NVARCHAR(320) , \r\n");
		sql.append("\tFHandler NVARCHAR(320) , \r\n");
		sql.append("\tFLocalRate Decimal(28,10), \r\n");
		sql.append("\tFRptRate Decimal(28,10), \r\n");
		if (this.isShowQuantity) {
			sql.append(" FDebitUnitId varChar(44),\r\n");
			sql.append(" FCreditUnitId varChar(44),\r\n");
			sql.append(" FDebitQty Decimal(28,10), \r\n");
			sql.append(" FCreditQty Decimal(28,10), \r\n");
			sql.append(" FDebitQtyDefault Decimal(28,10), \r\n");
			sql.append(" FCreditQtyDefault Decimal(28,10), \r\n");
			sql.append(" FDebitPrice Decimal(28,10), \r\n");
			sql.append(" FCreditPrice Decimal(28,10), \r\n");
			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql.append(" FDebitAssistUnit varChar(44),\r\n");
				sql.append(" FCreditAssistUnit varChar(44),\r\n");
				sql.append(" FDebitAssistPrice Decimal(28,10), \r\n");
				sql.append(" FCreditAssistPrice Decimal(28,10), \r\n");
				sql.append(" FDebitAssistQty Decimal(28,10), \r\n");
				sql.append(" FCreditAssistQty Decimal(28,10), \r\n");
			}
		}

		sql.append("\tFDebitFor Decimal(28,10), \r\n");
		sql.append("\tFCreditFor Decimal(28,10), \r\n");
		sql.append("\tFDebitLocal Decimal(28,10), \r\n");
		sql.append("\tFCreditLocal Decimal(28,10), \r\n");
		sql.append("\tFDebitRpt Decimal(28,10), \r\n");
		sql.append("\tFCreditRpt Decimal(28,10), \r\n");
		sql.append("\tFBalanceUnitId varChar(44), \r\n");
		sql.append("\tFLineType Int, \r\n");
		sql.append("\tFVoucherID varChar(44), \r\n");
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append("    FCurrencyid varChar(44), \r\n");
		}
		sql.append("\tFPeriodYear Int, \r\n");

		sql.append("\tFNumber NVarchar(640),  \r\n ");
		sql.append("\tFName NVarchar(640),  \r\n ");

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql.append("  FPeriodNumber Int,  \r\n ");
			sql.append("  FAccountOrAssist Int,  \r\n");
			sql.append("  FAsstAccountPerId  varChar(44)\r\n");

			sql.append(" \t,FdivisionBalance Int  \r\n");

			sql.append("   ,FNumberGroupAsst NVARCHAR(500), \r\n");
			sql.append("  FCount Int, \r\n ");
			sql.append("  FAsstAccountName NVARCHAR(800) )\r\n");
		} else {
			sql.append("\tFPeriodNumber Int  \r\n )");
		}

		return sql.toString();
	}

	private String getTempTableColumns(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		boolean isQtyAsst = partner.getParmQtyAsst();
		this.isShowQuantity = getIsShowQuantity(partner);

		StringBuffer sql = new StringBuffer();
		sql.append("(");
		if ("USE_KSQL_SEQ".equals(partner.getVariable("USE_KSQL_SEQ"))) {
			sql.append("KSQL_SEQ, ");
		}
		sql.append("FDate, ");
		sql.append("FBizDate, ");
		sql.append("FVoucherNumber, ");
		sql.append("FVoucherTypeNumber, ");
		sql.append("FVoucherTypeName, ");
		sql.append("FCREATETIME, ");
		sql.append("FSeq, ");
		sql.append("FDescription, ");
		if (condition.getOptionOtherAccount()) {
			sql.append("accountName, ");
			sql.append("accountLongNumber, ");
			sql.append("currencyID, ");
		}
		sql.append("FSettlementType, ");
		sql.append("FSettlementCode, ");
		sql.append("FBizNumber, ");
		sql.append("FTicketNumber, ");
		sql.append("FInvoiceNumber, ");
		sql.append("FFeeType, ");
		sql.append("FHandler, ");
		sql.append("FLocalRate, ");
		sql.append("FRptRate, ");
		if (this.isShowQuantity) {
			sql.append("FDebitUnitId, ");
			sql.append("FCreditUnitId, ");
			sql.append("FDebitQty, ");
			sql.append("FCreditQty, ");
			sql.append("FDebitQtyDefault, ");
			sql.append("FCreditQtyDefault, ");
			sql.append("FDebitPrice, ");
			sql.append("FCreditPrice, ");

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql.append(" FDebitAssistUnit,\r\n");
				sql.append(" FCreditAssistUnit,\r\n");
				sql.append(" FDebitAssistPrice, \r\n");
				sql.append(" FCreditAssistPrice, \r\n");
				sql.append(" FDebitAssistQty, \r\n");
				sql.append(" FCreditAssistQty, \r\n");
			}
		}
		sql.append("FDebitFor, ");
		sql.append("FCreditFor, ");
		sql.append("FDebitLocal, ");
		sql.append("FCreditLocal, ");
		sql.append("FDebitRpt, ");
		sql.append("FCreditRpt, ");
		sql.append("FBalanceUnitId, ");
		sql.append("FLineType, ");
		sql.append("FVoucherID, ");
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append("FCurrencyid, ");
		}
		sql.append("FPeriodYear, ");

		sql.append("FNumber, ");
		sql.append("FName, ");

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql.append("FPeriodNumber, ");
			sql.append("FAccountOrAssist, ");
			sql.append("  FAsstAccountPerId ");

			sql.append(" \t,FdivisionBalance  \r\n");

			sql.append("   ,FNumberGroupAsst, \r\n");
			sql.append("FCount, ");
			sql.append("FAsstAccountName )");
		} else {
			sql.append("FPeriodNumber )");
		}

		return sql.toString();
	}

	protected void insertDataIntoTempTable(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		partner.setTempTableName(partner.createTempTableAsynchronism("GLSL",
				getTempTableStruct(partner)));
		boolean isShowAsst = (condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null);

		HashMap map = null;
		if (isShowAsst)
			map = getStartAsstBalance(partner);
		else {
			map = getStartBalance(partner);
		}
		insertStartBalance(map, partner);

		if ((isShowAsst) || (condition.getOptionShowAccountCusAttribute())) {
			insertVoucherOrAsst(partner);
		} else if (condition.getOptionOtherAccount())
			insertVoucherEntry(partner);
		else {
			insertSelfVoucherEntry(partner);
		}

		// add by yangyang 20141127
		if (condition.isOptionShowHisData()) {
			insertHisData(partner);
		}

		if (condition.getOptionDailyTotal()) {
			insertDailySum(partner);
		}

		insertPeriodSum(partner);

		insertYearSum(map, partner);

		addSeqColToTable(partner);
	}

	protected void addSeqColToTable(ReportPartner partner)
			throws EASBizException, BOSException {
		partner.setVariable("USE_KSQL_SEQ", "USE_KSQL_SEQ");
		String tableStruct = getTempTableStruct(partner);
		String tableColumns = getTempTableColumns(partner);
		partner.setVariable("USE_KSQL_SEQ", null);
		String oldTempTable = partner.getTempTableName();
		String newTempTable = partner.createTempTableAsynchronism("GLSL",
				tableStruct);
		StringBuffer sql = new StringBuffer("INSERT INTO ")
				.append(newTempTable).append(" \r\n").append(tableColumns)
				.append(" \r\n").append(" (SELECT t.* FROM ").append(
						oldTempTable).append(" T ");

		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql
					.append(" inner join t_bd_currency cc on cc.fid = t.fcurrencyid \r\n");
		}
		sql.append(getSqlForDataOrder(partner)).append(")");
		try {
			partner.executeSql(sql.toString(), null);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		partner.setTempTableName(newTempTable);
		partner.dropTempTableAsynchronism(oldTempTable);
	}

	protected boolean insertDailySum(ReportPartner partner)
			throws BOSException, EASBizException {
		boolean isQtyAsst = partner.getParmQtyAsst();
		this.isShowQuantity = getIsShowQuantity(partner);

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");

		sql.append("\t(FDate, FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt ,\r\n");
		if (this.isShowQuantity) {
			sql.append("FDebitQtyDefault,FCreditQtyDefault, \r\n");
		}
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append("FCurrencyid,\r\n");
		}
		sql.append(" FLineType,FPeriodYear, FPeriodNumber) \r\n");

		sql.append("SELECT FDate, \r\n");
		sql
				.append("\tSUM(FDebitFor) FDebitFor,SUM(FCreditFor) FCreditFor, \r\n");
		sql
				.append("\tSUM(FDebitLocal) FDebitLocal, SUM(FCreditLocal) FCreditLocal, \r\n");
		sql
				.append("\tSUM(FDebitRpt) FDebitRpt, SUM(FCreditRpt) FCreditRpt, \r\n");
		if (this.isShowQuantity) {
			sql.append("SUM(FDebitQtyDefault) FDebitQtyDefault, \r\n");
			sql.append("SUM(FCreditQtyDefault) FCreditQtyDefault, \r\n");
		}
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append("FCurrencyid, \r\n");
		}
		sql.append("\t1 FLineType,FPeriodYear, FPeriodNumber \r\n");

		sql.append("FROM ");
		sql.append(partner.getTempTableName());
		sql.append(" WHERE FLineType = 0 \r\n");

		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionOtherAccount()) {
			sql.append(" and accountLongNumber like '").append(
					partner.getAccountViewInfo().getLongNumber()).append(
					"%' \r\n");
		}

		sql.append("GROUP BY FDate,FPeriodYear, FPeriodNumber \r\n");
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append(",FCurrencyid \r\n");
		}
		return partner.executeSql(sql.toString(), null);
	}

	protected boolean insertPeriodSum(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");

		sql.append("\t(FDate, FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt ,\r\n");
		if (this.isShowQuantity) {
			sql.append("FDebitQtyDefault,FCreditQtyDefault, \r\n");

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql.append(" FCreditAssistPrice, \r\n");
				sql.append(" FCreditAssistQty, \r\n");
			}

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql.append(" FDebitAssistPrice, \r\n");
				sql.append(" FDebitAssistQty, \r\n");
			}
		}
		if (condition.isAllCurrency()) {
			sql.append("FCurrencyid,\r\n");
		}
		sql.append("\tFLineType,FPeriodYear, FPeriodNumber) \r\n");
		sql.append("SELECT TBP.FEndDate FDate, \r\n");
		if (condition.isAllCurrency()) {
			sql
					.append("\tSUM(case when cc.fid is null then 0.0 else FDebitFor end) FDebitFor,   SUM(case when cc.fid is null then 0.0 else FCreditFor end) FCreditFor, \r\n");

			sql
					.append("\tSUM(case when cc.fid is null then 0.0 else FDebitLocal end) FDebitLocal,    SUM(case when cc.fid is null then 0.0 else FCreditLocal end) FCreditLocal, \r\n");

			sql
					.append("\tSUM(case when cc.fid is null then 0.0 else FDebitRpt end) FDebitRpt, \tSUM(case when cc.fid is null then 0.0 else FCreditRpt end) FCreditRpt, \r\n");

			if (this.isShowQuantity) {
				sql
						.append("SUM(case when cc.fid is null then 0.0 else FDebitQtyDefault end) FDebitQtyDefault, \r\n");
				sql
						.append("SUM(case when cc.fid is null then 0.0 else FCreditQtyDefault end) FCreditQtyDefault, \r\n");

				if (((condition.getOptionOnlyAsst()) || (condition
						.getAssisthgId() != null))
						&& (this.isShowQuantity) && (getPar(partner))) {
					sql
							.append(" (case when SUM(case when cc.fid is null then 0.0 else FCreditAssistQty end)=0 then null else SUM(case when cc.fid is null then 0.0 else FCreditFor end)/SUM(case when cc.fid is null then 0.0 else FCreditAssistQty end) end) FCreditAssistPrice, \r\n");
					sql
							.append(" SUM(case when cc.fid is null then 0.0 else FCreditAssistQty end) FCreditAssistQty, \r\n");
				}

				if (((condition.getOptionOnlyAsst()) || (condition
						.getAssisthgId() != null))
						&& (this.isShowQuantity) && (getPar(partner))) {
					sql
							.append(" (case when SUM(case when cc.fid is null then 0.0 else FDebitAssistQty end)=0 then null else SUM(case when cc.fid is null then 0.0 else FDebitFor end)/SUM(case when cc.fid is null then 0.0 else FDebitAssistQty end) end) FDebitAssistPrice, \r\n");
					sql
							.append(" SUM(case when cc.fid is null then 0.0 else FDebitAssistQty end) FDebitAssistQty, \r\n");
				}
			}
		} else {
			sql
					.append("\tSUM(FDebitFor) FDebitFor,SUM(FCreditFor) FCreditFor, \r\n");
			sql
					.append("\tSUM(FDebitLocal) FDebitLocal, SUM(FCreditLocal) FCreditLocal, \r\n");
			sql
					.append("\tSUM(FDebitRpt) FDebitRpt, SUM(FCreditRpt) FCreditRpt, \r\n");
			if (this.isShowQuantity) {
				sql.append("SUM(FDebitQtyDefault) FDebitQtyDefault, \r\n");
				sql.append("SUM(FCreditQtyDefault) FCreditQtyDefault, \r\n");

				if (((condition.getOptionOnlyAsst()) || (condition
						.getAssisthgId() != null))
						&& (this.isShowQuantity) && (getPar(partner))) {
					sql
							.append(" (case when SUM(isnull(FCreditAssistQty,0))=0 then null else SUM(FCreditFor)/SUM(isnull(FCreditAssistQty,0)) end) FCreditAssistPrice, \r\n");
					sql.append(" SUM(FCreditAssistQty) FCreditAssistQty, \r\n");
				}

				if (((condition.getOptionOnlyAsst()) || (condition
						.getAssisthgId() != null))
						&& (this.isShowQuantity) && (getPar(partner))) {
					sql
							.append(" (case when SUM(isnull(FDebitAssistQty,0))=0 then null else SUM(FDebitFor)/SUM(isnull(FDebitAssistQty,0)) end) FDebitAssistPrice, \r\n");
					sql.append(" SUM(FDebitAssistQty) FDebitAssistQty, \r\n");
				}
			}
		}

		if (condition.isAllCurrency()) {
			sql.append("TP.FCurrencyid,\r\n");
		}
		sql.append("\t3 FLineType,TBP.FPeriodYear, TBP.FPeriodNumber \r\n");
		sql.append("FROM T_BD_PERIOD TBP \r\n");
		sql.append("\tLEFT OUTER JOIN  ");
		sql.append(partner.getTempTableName());
		sql.append(" TP ON 1 = 1 \r\n");
		if ((!condition.getOptionNoDisplayZeroTotal())
				&& (condition.isAllCurrency())) {
			sql
					.append(" left join t_bd_currency cc on cc.fid = tp.fcurrencyid ");
		}
		sql.append(" AND TP.FPeriodYear = TBP.FPeriodYear \r\n");

		sql.append("\t\tAND TP.FPeriodNumber = TBP.FPeriodNumber \r\n");
		sql.append("\t\tAND TP.FLineType = 0 \r\n");
		if ((condition.getOptionNoDisplayZeroTotal())
				&& (condition.isAllCurrency())) {
			sql
					.append(" left join t_bd_currency cc on cc.fid = tp.fcurrencyid ");
		}
		sql.append("WHERE TBP.FTypeID = ? \r\n");
		int[] periodRange = {
				condition.getPeriodYearStart() * 100
						+ condition.getPeriodNumberStart(),
				condition.getPeriodYearEnd() * 100
						+ condition.getPeriodNumberEnd() };
		sql.append(" AND ");
		sql.append(SQLUtil.getPeriodCondition("TBP.FNumber", periodRange));
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionOtherAccount()) {
			sql.append(" AND (TP.accountLongNumber = '").append(
					partner.getAccountViewInfo().getLongNumber());
			sql.append("' OR TP.accountLongNumber like '").append(
					partner.getAccountViewInfo().getLongNumber()).append(
					"!%' OR  TP.accountLongNumber IS null) \r\n");
			if (condition.isAllCurrency()) {
				sql
						.append(" AND TP.fcurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TP.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC' ");
			} else {
				String currencyid = partner.getCurrency().getId().toString();
				if ((!"11111111-1111-1111-1111-111111111111DEB58FDC"
						.equals(currencyid))
						&& (!"22222222-2222-2222-2222-222222222222DEB58FDC"
								.equals(currencyid)))
					sql.append(" AND (TP.currencyID = '").append(currencyid)
							.append("' OR TP.currencyID IS NULL) ");
			}
		}
		if (condition.getOptionNoDisplayZeroTotal()) {
			sql.append(" and (TP.FDebitFor<>0 or TP.FCreditFor<>0)");
		}
		sql
				.append(" GROUP BY TBP.FPeriodYear,TBP.FPeriodNumber,TBP.FEndDate \r\n");
		if (condition.isAllCurrency()) {
			sql.append(",TP.FCurrencyid\r\n");
		}

		if (condition.getOptionNoDisplayZeroTotal()) {
			sql.append("  Having SUM(FDebitFor)<>0 or SUM(FCreditFor)<>0");
		}

		SqlParameter2 parameter = new SqlParameter2();
		parameter.addValueId(partner.getCompany().getAccountPeriodType());

		return partner.executeSql(sql.toString(), parameter);
	}

	protected boolean insertYearSum(Map startBalance, ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");
		sql.append("\t(FDate, FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt ,\r\n");
		if (this.isShowQuantity) {
			sql.append("FDebitQtyDefault,FCreditQtyDefault, \r\n");

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql.append(" FDebitAssistPrice, \r\n");
				sql.append(" FDebitAssistQty, \r\n");
			}

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql.append(" FCreditAssistPrice, \r\n");
				sql.append(" FCreditAssistQty, \r\n");
			}
		}
		if (condition.isAllCurrency()) {
			sql.append("FCurrencyid,\r\n");
		}
		sql.append("\tFLineType,FPeriodYear, FPeriodNumber) \r\n");

		sql.append("SELECT MAX(TP1.FDate) FDate, \r\n");
		sql
				.append("\tSUM(ISNULL(TP2.FDebitFor,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FDebitFor, \r\n");
		sql
				.append("\tSUM(ISNULL(TP2.FCreditFor,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FCreditFor, \r\n");
		sql
				.append("\tSUM(ISNULL(TP2.FDebitLocal,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FDebitLocal, \r\n");
		sql
				.append("\tSUM(ISNULL(TP2.FCreditLocal,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FCreditLocal, \r\n");
		sql
				.append("\tSUM(ISNULL(TP2.FDebitRpt,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FDebitRpt, \r\n");
		sql
				.append("\tSUM(ISNULL(TP2.FCreditRpt,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FCreditRpt, \r\n");
		if (this.isShowQuantity) {
			sql
					.append("\tSUM(ISNULL(TP2.FDebitQtyDefault,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FDebitQtyDefault, \r\n");
			sql
					.append("\tSUM(ISNULL(TP2.FCreditQtyDefault,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FCreditQtyDefault, \r\n");

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql
						.append(" SUM(ISNULL(TP2.FDebitAssistPrice,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FDebitAssistPrice, \r\n");
				sql
						.append(" SUM(ISNULL(TP2.FDebitAssistQty,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FDebitAssistQty, \r\n");
			}

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				sql
						.append(" SUM(ISNULL(TP2.FCreditAssistPrice,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FCreditAssistPrice, \r\n");
				sql
						.append(" SUM(ISNULL(TP2.FCreditAssistQty,0))+CASE WHEN TP1.FPeriodYear=? THEN ? ELSE 0 END FCreditAssistQty, \r\n");
			}
		}

		if (condition.isAllCurrency()) {
			sql.append("TP1.FCurrencyid,\r\n");
		}
		sql.append("\t4 FLineType,TP1.FPeriodYear, TP1.FPeriodNumber \r\n");
		sql.append("FROM ");
		sql.append(partner.getTempTableName());
		sql.append(" TP1 \r\n");
		sql.append("\tINNER JOIN ");
		sql.append(partner.getTempTableName());
		sql.append(" TP2  \r\n");
		sql.append("\tON TP1.FPeriodYear = TP2.FPeriodYear \r\n");
		sql.append("\tAND TP1.FLineType = TP2.FLineType \r\n");
		if (condition.isAllCurrency()) {
			sql.append("\tand TP1.FCurrencyid = TP2.FCurrencyid \r\n");
		}
		sql.append("WHERE TP1.FLineType = 3 AND \r\n");
		sql.append("\tTP1.FPeriodNumber >= TP2.FPeriodNumber \r\n");
		if (condition.getOptionNoDisplayZeroTotal()) {
			sql.append(" and (TP2.FDebitFor<>0 or TP2.FCreditFor<>0)");
		}

		int sqlLegth = sql.length();
		if (condition.isAllCurrency()) {
			ArrayList hasInsertCur = new ArrayList();
			for (int i = 0; i < startBalance.size(); i++) {
				HashMap curStartBalance = (HashMap) startBalance
						.get(new Integer(i));
				if (hasInsertCur.contains(curStartBalance.get("FCurrencyid"))) {
					continue;
				}
				hasInsertCur.add(curStartBalance.get("FCurrencyid"));

				sql.setLength(sqlLegth);
				sql.append(" and TP1.fcurrencyid = ? \r\n");
				sql.append("GROUP BY TP1.FPeriodYear,TP1.FPeriodNumber \r\n");
				if (condition.isAllCurrency()) {
					sql.append(",TP1.FCurrencyid\r\n");
				}

				SqlParameter2 parameter = new SqlParameter2();

				Integer periodYear = (Integer) curStartBalance
						.get("FPeriodYear");

				parameter.addValue(periodYear);
				parameter.addValue(curStartBalance.get("FYearDebitFor"));
				parameter.addValue(periodYear);
				parameter.addValue(curStartBalance.get("FYearCreditFor"));
				parameter.addValue(periodYear);
				parameter.addValue(curStartBalance.get("FYearDebitLocal"));
				parameter.addValue(periodYear);
				parameter.addValue(curStartBalance.get("FYearCreditLocal"));
				parameter.addValue(periodYear);
				parameter.addValue(curStartBalance.get("FYearDebitRpt"));
				parameter.addValue(periodYear);
				parameter.addValue(curStartBalance.get("FYearCreditRpt"));
				if (this.isShowQuantity) {
					parameter.addValue(periodYear);
					parameter.addValue(((BigDecimal) curStartBalance
							.get("FYearDebitQty")).setScale(6));

					parameter.addValue(periodYear);

					parameter.addValue(((BigDecimal) curStartBalance
							.get("FYearCreditQty")).setScale(6));

					if (((condition.getOptionOnlyAsst()) || (condition
							.getAssisthgId() != null))
							&& (this.isShowQuantity) && (getPar(partner))) {
						parameter.addValue(periodYear);
						if (curStartBalance.get("FDebitAssistPrice") == null)
							parameter
									.addValue(new BigDecimal(0.0D).setScale(6));
						else {
							parameter.addValue(((BigDecimal) curStartBalance
									.get("FDebitAssistPrice")).setScale(6));
						}

						parameter.addValue(periodYear);
						if (curStartBalance.get("FDebitAssistQty") == null)
							parameter
									.addValue(new BigDecimal(0.0D).setScale(6));
						else {
							parameter.addValue(((BigDecimal) curStartBalance
									.get("FDebitAssistQty")).setScale(6));
						}

					}

					if (((condition.getOptionOnlyAsst()) || (condition
							.getAssisthgId() != null))
							&& (this.isShowQuantity) && (getPar(partner))) {
						parameter.addValue(periodYear);
						if (curStartBalance.get("FCreditAssistPrice") == null)
							parameter
									.addValue(new BigDecimal(0.0D).setScale(6));
						else {
							parameter.addValue(((BigDecimal) curStartBalance
									.get("FCreditAssistPrice")).setScale(6));
						}

						parameter.addValue(periodYear);
						if (curStartBalance.get("FCreditAssistQty") == null)
							parameter
									.addValue(new BigDecimal(0.0D).setScale(6));
						else {
							parameter.addValue(((BigDecimal) curStartBalance
									.get("FCreditAssistQty")).setScale(6));
						}
					}
				}
				parameter.addValue(curStartBalance.get("FCurrencyid"));

				String sql1 = parameter.getSqlWithParameters(sql.toString());
				try {
					DbUtil.execute(partner.getContext(), sql1);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity)) {
				setAssCou(partner);
			}
			return true;
		}
		sql.append("GROUP BY TP1.FPeriodYear,TP1.FPeriodNumber \r\n");

		SqlParameter2 parameter = new SqlParameter2();

		Integer periodYear = (Integer) startBalance.get("FPeriodYear");

		parameter.addValue(periodYear);
		parameter.addValue(startBalance.get("FYearDebitFor"));
		parameter.addValue(periodYear);
		parameter.addValue(startBalance.get("FYearCreditFor"));
		parameter.addValue(periodYear);
		parameter.addValue(startBalance.get("FYearDebitLocal"));
		parameter.addValue(periodYear);
		parameter.addValue(startBalance.get("FYearCreditLocal"));
		parameter.addValue(periodYear);
		parameter.addValue(startBalance.get("FYearDebitRpt"));
		parameter.addValue(periodYear);
		parameter.addValue(startBalance.get("FYearCreditRpt"));
		if (this.isShowQuantity) {
			parameter.addValue(periodYear);
			parameter.addValue(((BigDecimal) startBalance.get("FYearDebitQty"))
					.setScale(6));

			parameter.addValue(periodYear);

			parameter
					.addValue(((BigDecimal) startBalance.get("FYearCreditQty"))
							.setScale(6));

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				parameter.addValue(periodYear);
				if (startBalance.get("FDebitAssistPrice") == null)
					parameter.addValue(new BigDecimal(0.0D).setScale(6));
				else {
					parameter.addValue(((BigDecimal) startBalance
							.get("FDebitAssistPrice")).setScale(6));
				}

				parameter.addValue(periodYear);
				if (startBalance.get("FDebitAssistQty") == null)
					parameter.addValue(new BigDecimal(0.0D).setScale(6));
				else {
					parameter.addValue(((BigDecimal) startBalance
							.get("FDebitAssistQty")).setScale(6));
				}

			}

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (this.isShowQuantity) && (getPar(partner))) {
				parameter.addValue(periodYear);
				if (startBalance.get("FCreditAssistPrice") == null)
					parameter.addValue(new BigDecimal(0.0D).setScale(6));
				else {
					parameter.addValue(((BigDecimal) startBalance
							.get("FCreditAssistPrice")).setScale(6));
				}

				parameter.addValue(periodYear);
				if (startBalance.get("FCreditAssistQty") == null)
					parameter.addValue(new BigDecimal(0.0D).setScale(6));
				else {
					parameter.addValue(((BigDecimal) startBalance
							.get("FCreditAssistQty")).setScale(6));
				}

			}

		}

		String sql1 = parameter.getSqlWithParameters(sql.toString());
		try {
			DbUtil.execute(partner.getContext(), sql1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
				&& (this.isShowQuantity)) {
			setAssCou(partner);
		}
		return true;
	}

	protected void setAssCou(ReportPartner partner) throws EASBizException,
			BOSException {
		String sql = "select FPeriodNumber ,count(FDebitAssistUnit) co from( select FPeriodNumber,FDebitAssistUnit from "
				+ partner.getTempTableName()
				+ " \r\n"
				+ " where FPeriodNumber in \r\n"
				+ " (select FPeriodNumber from "
				+ partner.getTempTableName()
				+ "  where Flinetype=3 group by FPeriodNumber) \r\n"
				+ " and FDebitAssistUnit is not null group by FDebitAssistUnit,FPeriodNumber \r\n"
				+ " ) group by FPeriodNumber";

		String ids = "";
		try {
			IRowSet rs = DbUtil.executeQuery(partner.getContext(), sql);

			while (rs.next()) {
				int cou = rs.getInt("co");
				if (cou != 1) {
					ids = ids + rs.getString("FPeriodNumber") + "','";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 3);
			String sql2 = "update "
					+ partner.getTempTableName()
					+ " set FDebitAssistQty=null,FDebitAssistPrice=null where FPeriodNumber in('"
					+ ids + "') and (Flinetype=3 or Flinetype=4)";
			partner.executeSql(sql2, null);
		}

		sql = "select FPeriodNumber ,count(FCreditAssistUnit) co from( select FPeriodNumber,FCreditAssistUnit from "
				+ partner.getTempTableName()
				+ " \r\n"
				+ " where FPeriodNumber in \r\n"
				+ " (select FPeriodNumber from "
				+ partner.getTempTableName()
				+ "  where Flinetype=3 group by FPeriodNumber) \r\n"
				+ " and FCreditAssistUnit is not null group by FCreditAssistUnit,FPeriodNumber \r\n"
				+ " ) group by FPeriodNumber";

		ids = "";
		try {
			IRowSet rs = DbUtil.executeQuery(partner.getContext(), sql);

			while (rs.next()) {
				int cou = rs.getInt("co");
				if (cou != 1) {
					ids = ids + rs.getString("FPeriodNumber") + "','";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 3);
			String sql2 = "update "
					+ partner.getTempTableName()
					+ " set FCreditAssistPrice=null,FCreditAssistQty=null where FPeriodNumber in('"
					+ ids + "') and (Flinetype=3 or Flinetype=4)";
			partner.executeSql(sql2, null);
		}
	}

	protected boolean insertYearStart(String tempTableName,
			ReportPartner partner) throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(tempTableName);
		sql.append(" \r\n");
		sql.append("\t(FDate, FLineType,FPeriodYear, FPeriodNumber) \r\n");

		sql
				.append("SELECT TP.FBeginDate,-3 FLineType,TP.FPeriodYear,TP.FPeriodNumber \r\n");
		sql.append("FROM ");
		sql.append(tempTableName);
		sql.append(" TPT \r\n");
		sql.append("\tINNER JOIN T_BD_Period TP \r\n");
		sql.append("\tON TPT.FPeriodYear = TP.FPeriodYear \r\n");
		sql.append("\tAND TPT.FPeriodNumber = TP.FPeriodNumber \r\n");
		sql.append("WHERE TPT.FLineType = 3 AND \r\n");
		sql.append("\tTPT.FPeriodNumber = 1 AND \r\n");
		sql.append("\tTP.FTypeID = ? AND  \r\n");
		sql.append("\tTP.FPeriodNumber = 1  \r\n");

		SqlParameter2 parameter = new SqlParameter2();
		parameter.addValueId(partner.getCompany().getAccountPeriodType());

		return partner.executeSql(sql.toString(), parameter);
	}

	protected boolean insertSelfVoucherEntry(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");
		sql
				.append("\t(FDate, FBizDate,FVoucherNumber,FVoucherTypeNumber ,FVoucherTypeName ,FCREATETIME ,FSeq, FDescription,  \r\n");
		if (condition.getOptionOtherAccount()) {
			sql.append(" accountName, \r\n");
			sql.append(" otheraccountLongNumber, \r\n");
			sql.append(" currencyID,\r\n");
		}
		sql.append("\t FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FLocalRate,FRptRate, \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt, \r\n");
		if (this.isShowQuantity) {
			sql.append(" FDebitUnitId,FCreditUnitId, \r\n");
			sql.append(" FDebitQty,FCreditQty,\r\n");
			sql.append(" FDebitQtyDefault,FCreditQtyDefault, \r\n");
			sql.append(" FDebitPrice,FCreditPrice, \r\n");
		}

		sql.append("\tFBalanceUnitId,FLineType,FVoucherID,   \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    FCurrencyid , \r\n");
		}
		sql.append("FPeriodYear, FPeriodNumber) \r\n");
		Map editParame = condition.getExpandInfo();
		boolean isChkSum = (editParame.get("isChkSum") != null)
				&& ((editParame.get("isChkSum") instanceof Boolean))
				&& (((Boolean) editParame.get("isChkSum")).booleanValue())
				&& (!condition.getOptionOnlyAsst());

		boolean isRdoIsAccountAndDesc = (editParame
				.get("isRdoIsAccountAndDesc") != null)
				&& ((editParame.get("isRdoIsAccountAndDesc") instanceof Boolean))
				&& (((Boolean) editParame.get("isRdoIsAccountAndDesc"))
						.booleanValue());

		boolean isChkIsZeroNoDisplay = (editParame.get("isChkIsZeroNoDisplay") != null)
				&& ((editParame.get("isChkIsZeroNoDisplay") instanceof Boolean))
				&& (((Boolean) editParame.get("isChkIsZeroNoDisplay"))
						.booleanValue());

		int spnAccountLevel = 1;
		if ((editParame.get("spnAccountLevel") != null)
				&& ((editParame.get("spnAccountLevel") instanceof Integer))) {
			spnAccountLevel = ((Integer) editParame.get("spnAccountLevel"))
					.intValue();
		}
		if (isChkSum) {
			if (isChkIsZeroNoDisplay) {
				sql.append("SELECT * from ( \r\n");
			}
			sql
					.append("SELECT min(FBookedDate) FBookedDate, min(FBizDate) FBizDate, min(FNumber_tv) FNumber_tv, min(fnumber_tvt) fnumber_tvt, \r\n");
			sql
					.append("min(FName_tvt) FName_tvt, min(FCREATETIME_tv) FCREATETIME_tv, min(FSeq) FSeq, ");
			if (isRdoIsAccountAndDesc) {
				sql.append("FDescription_tve, \r\n");
			} else {
				sql
						.append("substring(min( TOCHAR (FSeq) || FDescription_tve), len(min(FSeq)) + 1, len(min( TOCHAR (FSeq) || FDescription_tve)) - 1) FDescription_tve,\r\n");
			}

			sql
					.append("sum(FDebitFor) FDebitFor, sum(FCreditFor) FCreditFor, min(FLocalRate) FLocalRate, min(FRptRate) FRptRate, sum(FDebitLocal) FDebitLocal, \r\n");
			sql
					.append("sum(FCreditLocal) FCreditLocal, sum(FDebitRpt) FDebitRpt, sum(FCreditRpt) FCreditRpt,  \r\n");
			if (this.isShowQuantity) {
				sql
						.append(" min(FDebitUnitId) FDebitUnitId,min(FCreditUnitId) FCreditUnitId, \r\n");
				sql
						.append(" min(FDebitQty) FDebitQty,min(FCreditQty) FCreditQty,\r\n");
				sql
						.append(" min(FDebitQtyDefault) FDebitQtyDefault,min(FCreditQtyDefault) FCreditQtyDefault, \r\n");
				sql
						.append(" min(FDebitPrice) FDebitPrice,min(FCreditPrice) FCreditPrice, \r\n");
			}
			sql
					.append("min(FBalanceUnitId) FBalanceUnitId,min(FLineType) FLineType,FVoucherID,  \r\n");
			if (condition.isAllCurrency()) {
				sql.append(" min(FCurrencyid) FCurrencyid, \r\n");
			}
			sql
					.append("min(FPeriodYear) FPeriodYear,min(FPeriodNumber) FPeriodNumber \r\n");
			sql.append("from ( \r\n");
		}
		sql
				.append("SELECT TV.FBookedDate,TV.FBizDate,TV.FNumber FNumber_tv,TVT.fnumber fnumber_tvt,TVT.FName");
		sql.append(partner.getFieldNameExtend());
		sql
				.append(" FName_tvt,TV.FCREATETIME FCREATETIME_tv,TVE.FSeq,TVE.FDescription FDescription_tve, \r\n");

		if (partner.getCurrencyType() == 1)
			addDebitCreditFieldSQL(sql, "TVE.FLocalAmount", "0", "For");
		else if (partner.getCurrencyType() == 2)
			addDebitCreditFieldSQL(sql, "TVE.FReportingAmount", "0", "For");
		else {
			addDebitCreditFieldSQL(sql, "TVE.FOriginalAmount", "0", "For");
		}

		sql.append(" TVE.FLocalExchangeRate FLocalRate,\r\n");
		sql.append(" TVE.FReportingExchangeRate FRptRate,\r\n");

		addDebitCreditFieldSQL(sql, "TVE.FLocalAmount", "0", "Local");
		addDebitCreditFieldSQL(sql, "TVE.FReportingAmount", "0", "Rpt");

		if (this.isShowQuantity) {
			addDebitCreditFieldSQL(sql, "TVE.FMeasureUnitID", "NULL", "UnitId");
			addDebitCreditFieldSQL(sql, "TVE.FQuantity", "0", "Qty");
			addDebitCreditFieldSQL(sql, "TVE.FStandardQuantity", "0",
					"QtyDefault");
			addDebitCreditFieldSQL(sql, "TVE.FPrice", "NULL", "Price");
		}

		sql
				.append("CASE WHEN MUG.FDefaultunitID IS NULL THEN AV.FMeasureUnitID ELSE MUG.FDefaultunitID END FBalanceUnitId, \r\n");
		sql.append("\t0 FLineType,TV.FID FVoucherID, \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    TVE.FCurrencyid , \r\n");
		}
		sql
				.append("\tTP.FPeriodYear  FPeriodYear,TP.FPeriodNumber FPeriodNumber \r\n");

		if (isChkSum) {
			int accountLevel = partner.getAccountViewInfo().getLevel();
			sql.append(", (CASE WHEN av.flevel > ");
			sql.append(accountLevel > spnAccountLevel ? accountLevel
					: spnAccountLevel);
			sql.append(" THEN '~~~~~~~~~~' ELSE AV.FID END) accountid \r\n");

			sql.append(", (CASE WHEN av.flevel >= ");
			sql.append(accountLevel > spnAccountLevel ? accountLevel
					: spnAccountLevel);
			sql
					.append(" THEN '~~~~~~~~~~' ELSE TVE.fid END) voucherentryid \r\n");
		}
		sql.append("FROM T_GL_Voucher TV \r\n");
		sql.append("\tINNER JOIN T_BD_VoucherTypes TVT \r\n ");
		sql.append("\tON TV.FVoucherTypeID = TVT.FID \r\n");
		sql.append("\tINNER JOIN T_GL_VoucherEntry TVE \r\n");
		sql.append("\tON TV.FID = TVE.FBillID \r\n");
		sql.append("\tINNER JOIN T_BD_Period TP \r\n");
		sql.append("\tON TV.FPeriodID = TP.FID \r\n");
		sql.append("\tINNER JOIN T_BD_AccountView AV \r\n");
		sql
				.append("\tON TVE.FAccountID =AV.FID AND AV.FCompanyID = TV.FCompanyID \r\n");

		sql
				.append("\tLEFT JOIN t_bd_measureunitgroup MUG ON AV.FMeasureUnitGroupID=MUG.fid \r\n");
		HashMap table = new HashMap();
		table.put("VOUCHER", "TV");
		table.put("ENTRIES", "TVE");
		table.put("VOUCHERTYPE", "TVT");
		table.put("PERIOD", "TP");
		table.put("ACCOUNTVIEW", "AV");
		HashMap tableValue = RptServerUtil.getRptSelfCongdition(partner
				.getContext(), partner.getCondition(), table, true, false);
		if ((tableValue != null) && (tableValue.get("join") != null)) {
			sql.append(tableValue.get("join").toString());
		}

		sql.append(" WHERE TV.FCompanyID = ? AND \r\n");
		sql.append("  AV.FCompanyID = ? AND \r\n");
		sql.append("    (TV.FBizStatus =   ");
		sql.append(5);
		sql.append("  \r\n");

		if (condition.getOptionPosting() == true) {
			sql.append("    OR TV.FBizStatus =   ");
			sql.append(1);
			sql.append(" \r\n");
			sql.append("    OR TV.FBizStatus =   ");
			sql.append(3);
			sql.append("  \r\n");
		}
		sql.append(") AND \r\n");
		if (condition.getOptionNotIncludePLVoucher()) {
			sql.append(" TV.FSourceType != ").append(1);
			sql.append(" AND \r\n");
		}
		if (partner.getAccountViewInfo().isIsLeaf()) {
			sql.append("\tTVE.FAccountID = '");
			sql.append(partner.getAccountViewInfo().getId().toString());
			sql.append("' AND \r\n");
		} else {
			sql.append("\tTVE.FAccountID IN (");
			sql
					.append(getChildAccountIds(partner, partner
							.getAccountViewInfo()));
			sql.append(") AND \r\n");
		}

		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			sql.append("\tTVE.FCurrencyID = ? AND \r\n");
		} else if (condition.isAllCurrency()) {
			sql
					.append("\tTVE.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TVE.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC'  and\t\t\r\n");
		}

		sql.append("\tTP.FNumber>=? AND \r\n");
		sql.append("\tTP.FNumber<=? AND \r\n");
		sql.append("\tTP.FTypeID = ? \r\n");

		if ((tableValue != null) && (tableValue.get("cond") != null)) {
			sql.append("  and " + tableValue.get("cond").toString());
		}
		Context ctx = partner.getContext();
		BOSUuid userId = ContextUtil.getCurrentUserInfo(ctx).getId();
		BOSUuid cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId();
		String rule = PermissionServiceProviderFactory
				.getLocalInstance(ctx)
				.getPermissionRule(new ObjectUuidPK(userId),
						new ObjectUuidPK(cuId), "bd_assistant_voucherType_view");
		if (!StringUtils.isEmpty(rule)) {
			String p = PermissionFactory.getLocalInstance(ctx)
					.getQueryPermissionSQL(new ObjectUuidPK(userId),
							new ObjectUuidPK(cuId),
							"bd_assistant_voucherType_view");
			sql.append("    and TVT.FID IN \n(" + p + ")");
		}

		if (isChkSum) {
			sql.append(") tmp \r\n");
			sql.append("inner join \r\n");
			sql.append("(select fid from T_BD_AccountView \r\n");
			sql.append("union all \r\n");
			sql
					.append("select top 1 '~~~~~~~~~~' fid from T_BD_AccountView \r\n");
			sql.append(") account on tmp.accountid = account.fid \r\n");
			sql.append("group by account.fid,FVoucherID,voucherentryid \r\n");
			if (isRdoIsAccountAndDesc) {
				sql.append(",FDescription_tve \r\n");
			}

			if (isChkIsZeroNoDisplay) {
				sql.append(") outtmp where  \r\n");
				sql
						.append("not (FDebitFor = 0 and FCreditFor = 0 and FDebitLocal = 0 and  \r\n");
				sql
						.append("FCreditLocal = 0 and FDebitRpt = 0 and FCreditRpt = 0) \r\n");
			}

		}

		SqlParameter2 parameter = new SqlParameter2();

		parameter.addValueChar(partner.getCompanyId());
		parameter.addValueChar(partner.getCompanyId());
		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			parameter.addValueChar(condition.getCurrencyID());
		}
		parameter.addValue(new Integer(condition.getPeriodYearStart() * 100
				+ condition.getPeriodNumberStart()));
		parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
				+ condition.getPeriodNumberEnd()));
		parameter.addValueId(partner.getCompany().getAccountPeriodType());

		partner.executeSql(sql.toString(), parameter);
		return true;
	}

	protected boolean insertVoucherEntry(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");
		sql
				.append("\t(FDate, FBizDate,FVoucherNumber,FVoucherTypeNumber ,FVoucherTypeName ,FCREATETIME ,FSeq, FDescription,  \r\n");
		sql.append(" \t accountName, \r\n");
		sql.append(" \t accountLongNumber, \r\n");
		sql.append("\t currencyID,\r\n");
		sql.append("\t FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FLocalRate,FRptRate, \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt, \r\n");
		if (this.isShowQuantity) {
			sql.append(" FDebitUnitId,FCreditUnitId, \r\n");
			sql.append(" FDebitQty,FCreditQty,\r\n");
			sql.append(" FDebitQtyDefault,FCreditQtyDefault, \r\n");
			sql.append(" FDebitPrice,FCreditPrice, \r\n");
		}
		sql.append("\tFBalanceUnitId,FLineType,FVoucherID,   \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    FCurrencyid , \r\n");
		}
		sql.append("FPeriodYear, FPeriodNumber) \r\n");
		sql
				.append("SELECT TV.FBookedDate FDate,TV.FBizDate FBizDate,TV.fnumber,tvt.fnumber,TVT.FName");
		sql.append(partner.getFieldNameExtend());
		sql
				.append(" ,TV.FCREATETIME,TVE.FSeq,TVE.FDescription FDescription, \r\n");
		if (partner.isParmAccountShowLongName())
			sql.append(" AV.FNumber ||' - '|| AV.FLongName").append(
					partner.getFieldNameExtend());
		else {
			sql.append(" AV.FNumber ||' - '|| AV.FName").append(
					partner.getFieldNameExtend());
		}
		sql.append(" AS accountName, \r\n");
		sql.append("AV.FLongNumber AS accountLongNumber, \r\n");
		sql.append(" TVE.FCurrencyID currencyID,\r\n");
		if (partner.getCurrencyType() == 1)
			addDebitCreditFieldSQL(sql, "TVE.FLocalAmount", "0", "For");
		else if (partner.getCurrencyType() == 2)
			addDebitCreditFieldSQL(sql, "TVE.FReportingAmount", "0", "For");
		else {
			addDebitCreditFieldSQL(sql, "TVE.FOriginalAmount", "0", "For");
		}
		sql.append(" TVE.FLocalExchangeRate FLocalRate,\r\n");
		sql.append(" TVE.FReportingExchangeRate FRptRate,\r\n");
		addDebitCreditFieldSQL(sql, "TVE.FLocalAmount", "0", "Local");
		addDebitCreditFieldSQL(sql, "TVE.FReportingAmount", "0", "Rpt");
		if (this.isShowQuantity) {
			addDebitCreditFieldSQL(sql, "TVE.FMeasureUnitID", "NULL", "UnitId");
			addDebitCreditFieldSQL(sql, "TVE.FQuantity", "0", "Qty");
			addDebitCreditFieldSQL(sql, "TVE.FStandardQuantity", "0",
					"QtyDefault");
			addDebitCreditFieldSQL(sql, "TVE.FPrice", "NULL", "Price");
		}

		sql
				.append("CASE WHEN MUG.FDefaultunitID IS NULL THEN AV.FMeasureUnitID ELSE MUG.FDefaultunitID END FBalanceUnitId, \r\n");
		sql.append(" 0 FLineType,TV.FID FVoucherID, \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    TVE.FCurrencyid , \r\n");
		}
		sql
				.append(" TP.FPeriodYear  FPeriodYear,TP.FPeriodNumber FPeriodNumber \r\n");
		sql.append(" FROM T_GL_Voucher TV \r\n");
		sql
				.append(" INNER JOIN T_BD_VoucherTypes TVT ON TV.FVoucherTypeID = TVT.FID \r\n");
		sql
				.append(" INNER JOIN T_GL_VoucherEntry TVE ON TV.FID = TVE.FBillID \r\n");
		sql.append(" INNER JOIN T_BD_Period TP ON TV.FPeriodID = TP.FID \r\n");
		sql
				.append(" INNER JOIN T_BD_AccountView AV ON TVE.FAccountID =AV.FID AND AV.FCompanyID = TV.FCompanyID \r\n");
		sql
				.append(" LEFT OUTER JOIN t_bd_measureunitgroup MUG ON AV.FMeasureUnitGroupID=MUG.fid \r\n");
		HashMap table = new HashMap();
		table.put("VOUCHER", "TV");
		table.put("ENTRIES", "TVE");
		table.put("VOUCHERTYPE", "TVT");
		table.put("PERIOD", "TP");
		table.put("ACCOUNTVIEW", "AV");

		HashMap tableValue = RptServerUtil.getRptSelfCongdition(partner
				.getContext(), partner.getCondition(), table, true, false);
		if ((tableValue != null) && (tableValue.get("join") != null)) {
			sql.append(tableValue.get("join").toString());
		}

		sql.append(" WHERE TV.FCompanyID = ? \r\n");
		sql.append(" AND AV.FCompanyID = ? \r\n");
		sql.append(" AND (TV.FBizStatus = ").append(5).append("  \r\n");

		if (condition.getOptionPosting() == true) {
			sql.append(" OR TV.FBizStatus = ").append(1).append(" \r\n");
			sql.append(" OR TV.FBizStatus = ").append(3).append(" \r\n");
		}
		sql.append(") \r\n");
		if (condition.getOptionNotIncludePLVoucher()) {
			sql.append(" AND TV.FSourceType != ").append(1);
		}

		sql.append(" AND EXISTS \r\n").append(
				"( SELECT 'LV' FROM T_GL_VoucherEntry VE \r\n").append(
				" WHERE VE.FBillID = TV.FID \r\n");

		if (partner.getAccountViewInfo().isIsLeaf()) {
			sql.append(" AND VE.FAccountID = '").append(
					partner.getAccountViewInfo().getId().toString()).append(
					"' \r\n");
		} else {
			sql.append(" AND VE.FAccountID IN (").append(
					getChildAccountIds(partner, partner.getAccountViewInfo()))
					.append(") \r\n");
		}
		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			sql.append("\tand VE.FCurrencyID = ?  \r\n");
		} else if (condition.isAllCurrency()) {
			sql
					.append("\tand VE.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and VE.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC'  \t\t\r\n");
		}
		sql.append(")");
		sql.append(" AND TP.FNumber>=? \r\n");
		sql.append(" AND TP.FNumber<=? \r\n");
		sql.append(" AND TP.FTypeID = ? \r\n");
		sql.append(" AND AV.FCompanyID = ? \r\n");
		if ((tableValue != null) && (tableValue.get("cond") != null)) {
			sql.append("  AND " + tableValue.get("cond").toString());
		}
		Context ctx = partner.getContext();
		BOSUuid userId = ContextUtil.getCurrentUserInfo(ctx).getId();
		BOSUuid cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId();
		String rule = PermissionServiceProviderFactory
				.getLocalInstance(ctx)
				.getPermissionRule(new ObjectUuidPK(userId),
						new ObjectUuidPK(cuId), "bd_assistant_voucherType_view");
		if (!StringUtils.isEmpty(rule)) {
			String p = PermissionFactory.getLocalInstance(ctx)
					.getQueryPermissionSQL(new ObjectUuidPK(userId),
							new ObjectUuidPK(cuId),
							"bd_assistant_voucherType_view");
			sql.append("    and TVT.FID IN \n(" + p + ")");
		}

		SqlParameter2 parameter = new SqlParameter2();

		parameter.addValueChar(partner.getCompanyId());
		parameter.addValueChar(partner.getCompanyId());
		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			parameter.addValueChar(condition.getCurrencyID());
		}
		parameter.addValue(new Integer(condition.getPeriodYearStart() * 100
				+ condition.getPeriodNumberStart()));
		parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
				+ condition.getPeriodNumberEnd()));
		parameter.addValueId(partner.getCompany().getAccountPeriodType());
		parameter.addValueChar(partner.getCompanyId());

		return partner.executeSql(sql.toString(), parameter);
	}

	protected boolean insertVoucherOrAsst(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");
		sql
				.append("\t(FDate, FBizDate,FVoucherNumber,FVoucherTypeNumber ,FVoucherTypeName ,FCREATETIME ,FSeq, FDescription,  \r\n");

		if (condition.getOptionShowAccountCusAttribute()) {
			sql.append(" \tFSettlementType,FSettlementCode, \r\n");
			sql.append("\tFBizNumber, \r\n");
			sql.append("\tFTicketNumber,FInvoiceNumber,\r\n");
			sql.append("\tFFeeType,FHandler,\r\n");
		}

		sql.append("\t FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FLocalRate,FRptRate, \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt, \r\n");

		if (this.isShowQuantity) {
			sql.append(" FDebitUnitId,FCreditUnitId, \r\n");
			sql.append(" FDebitQty,FCreditQty,\r\n");
			sql.append(" FDebitQtyDefault,FCreditQtyDefault, \r\n");
			sql.append(" FDebitPrice,FCreditPrice, \r\n");

			if (getPar(partner)) {
				sql.append(" FDebitAssistUnit,FCreditAssistUnit,\r\n");
				sql.append(" FDebitAssistPrice,FCreditAssistPrice, \r\n");
				sql.append(" FDebitAssistQty,FCreditAssistQty, \r\n");
			}
		}

		sql.append("\tFBalanceUnitId,FLineType,FVoucherID,   \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    FCurrencyid , \r\n");
		}
		sql.append("FPeriodYear, FPeriodNumber \r\n");

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql.append(" , FAccountOrAssist, \r\n");
			sql.append("  FCount, \r\n");
			sql.append(" FAsstAccountPerId, \r\n");
			sql.append("FNumberGroupAsst, \r\n");
			sql.append("  FAsstAccountName \r\n");
		}

		Map editParame = condition.getExpandInfo();

		boolean isChkSum = false;

		boolean isRdoIsAccountAndDesc = (editParame
				.get("isRdoIsAccountAndDesc") != null)
				&& ((editParame.get("isRdoIsAccountAndDesc") instanceof Boolean))
				&& (((Boolean) editParame.get("isRdoIsAccountAndDesc"))
						.booleanValue());

		boolean isChkIsZeroNoDisplay = (editParame.get("isChkIsZeroNoDisplay") != null)
				&& ((editParame.get("isChkIsZeroNoDisplay") instanceof Boolean))
				&& (((Boolean) editParame.get("isChkIsZeroNoDisplay"))
						.booleanValue());

		if (isChkSum) {
			if (isChkIsZeroNoDisplay)
				sql.append(") SELECT * from ( \r\n");
			else {
				sql.append(")");
			}
			sql
					.append(" SELECT min(FBookedDate), min(FBizDate), min(FNumber_tv), min(fnumber_tvt), \r\n");
			sql.append("min(FName_tvt), min(FCREATETIME_tv), min(FSeq), ");
			if (isRdoIsAccountAndDesc)
				sql.append("FDescription_tve, \r\n");
			else {
				sql.append("min(FDescription_tve), \r\n");
			}
			if (condition.getOptionShowAccountCusAttribute()) {
				sql
						.append("min(FSettlementType), min(FSettlementCode), min(FBizNumber), \r\n");
				sql.append("min(FTicketNumber), min(FInvoiceNumber),\r\n");
				sql.append("min(FFeeType), min(FHandler),\r\n");
			}
			sql
					.append("sum(FDebitFor) FDebitFor, sum(FCreditFor) FCreditFor, min(FLocalRate), min(FRptRate), sum(FDebitLocal) FDebitLocal, \r\n");
			sql
					.append("sum(FCreditLocal) FCreditLocal, sum(FDebitRpt) FDebitRpt, sum(FCreditRpt) FCreditRpt,  \r\n");
			if (this.isShowQuantity) {
				sql.append(" min(FDebitUnitId),min(FCreditUnitId), \r\n");
				sql.append(" min(FDebitQty),min(FCreditQty),\r\n");
				sql
						.append(" min(FDebitQtyDefault),min(FCreditQtyDefault), \r\n");
				sql.append(" min(FDebitPrice),min(FCreditPrice), \r\n");
			}
			sql.append("min(FBalanceUnitId), min(FLineType),FVoucherID,  \r\n");
			if (condition.isAllCurrency()) {
				sql.append(" min(FCurrencyid) , \r\n");
			}
			sql.append("min(FPeriodYear),min(FPeriodNumber) \r\n");
			sql
					.append(",min(FAccountOrAssist), min(FCount), min(FAsstAccountName) \r\n");
			sql.append("from ( \r\n");
		} else {
			sql.append(") ");
		}

		sql
				.append("SELECT TV.FBookedDate, TV.FBizDate,TV.fnumber FNumber_tv,tvt.fnumber fnumber_tvt,TVT.FName");
		sql.append(partner.getFieldNameExtend());
		sql
				.append(" FName_tvt,TV.FCREATETIME FCREATETIME_tv,TVE.FSeq,TVE.FDescription FDescription_tve, \r\n");

		if (condition.getOptionShowAccountCusAttribute()) {
			sql.append(" TST.FName");
			sql.append(partner.getFieldNameExtend());
			sql.append(" FSettlementType,");
			sql.append(" TVAR.FSettlementCode,");
			sql.append(" TVAR.FBizNumber, ");
			sql.append(" TVAR.FTicketNumber,TVAR.FInvoiceNumber,");
			sql.append(" (case when TVAR.FFeeType=1 then '"
					+ FeeTypeEnum.getEnum(1).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=2 then '"
					+ FeeTypeEnum.getEnum(2).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=3 then '"
					+ FeeTypeEnum.getEnum(3).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=4 then '"
					+ FeeTypeEnum.getEnum(4).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=5 then '"
					+ FeeTypeEnum.getEnum(5).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=6 then '"
					+ FeeTypeEnum.getEnum(6).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=7 then '"
					+ FeeTypeEnum.getEnum(7).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=8 then '"
					+ FeeTypeEnum.getEnum(8).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=9 then '"
					+ FeeTypeEnum.getEnum(9).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=10 then '"
					+ FeeTypeEnum.getEnum(10).getAlias() + "'");
			sql.append("       else '' end)as FFeeType,");
			sql.append(" THdl.FName");
			sql.append(partner.getFieldNameExtend());
			sql.append(" FHandler, ");
		}

		if (partner.getCurrencyType() == 1)
			addDebitCreditFieldSQL(sql, "TVE.FLocalAmount", "0", "For");
		else if (partner.getCurrencyType() == 2)
			addDebitCreditFieldSQL(sql, "TVE.FReportingAmount", "0", "For");
		else {
			addDebitCreditFieldSQL(sql, "TVE.FOriginalAmount", "0", "For");
		}

		sql.append(" TVE.FLocalExchangeRate FLocalRate,\r\n");
		sql.append(" TVE.FReportingExchangeRate FRptRate,\r\n");

		addDebitCreditFieldSQL(sql, "TVE.FLocalAmount", "0", "Local");
		addDebitCreditFieldSQL(sql, "TVE.FReportingAmount", "0", "Rpt");
		if (this.isShowQuantity) {
			addDebitCreditFieldSQL(sql, "TVE.FMeasureUnitID", "NULL", "UnitId");
			addDebitCreditFieldSQL(sql, "TVE.FQuantity", "0", "Qty");
			addDebitCreditFieldSQL(sql, "TVE.FStandardQuantity", "0",
					"QtyDefault");
			addDebitCreditFieldSQL(sql, "TVE.FPrice", "NULL", "Price");

			if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
					&& (getPar(partner))) {
				addDebitCreditFieldSQL(sql, "MUI.FName_l2", "NULL",
						"AssistUnit");
				addDebitCreditFieldSQL(sql, "TVAR.FAssistPrice", "0",
						"AssistPrice");
				addDebitCreditFieldSQL(sql, "TVAR.FAssistQty", "0", "AssistQty");
			}

		}

		sql
				.append("CASE WHEN MUG.FDefaultunitID IS NULL THEN AV.FMeasureUnitID ELSE MUG.FDefaultunitID END FBalanceUnitId, \r\n");
		sql.append("\t0 FLineType,TV.FID FVoucherID, \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    TVE.FCurrencyID FCurrencyID, \r\n");
		}
		sql
				.append("\tTP.FPeriodYear  FPeriodYear,TP.FPeriodNumber FPeriodNumber \r\n");
		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql
					.append("  \t,1 FAccountOrAssist, 0 FCount,null FAsstAccountPerId,null FNumberGroupAsst, null FAsstAccountName  \r\n");
		}

		if (isChkSum) {
			sql.append(" ,AV.FID accountid \r\n");
		}
		sql.append("    FROM T_GL_Voucher TV \r\n");
		sql.append("\tINNER JOIN T_BD_VoucherTypes TVT \r\n ");
		sql.append("\tON TV.FVoucherTypeID = TVT.FID \r\n");
		sql.append("\tINNER JOIN T_GL_VoucherEntry TVE \r\n");
		sql.append("\tON TV.FID = TVE.FBillID \r\n");
		sql.append("\tINNER JOIN T_BD_Period TP \r\n");
		sql.append("\tON TV.FPeriodID = TP.FID \r\n");
		sql.append("\tINNER JOIN T_BD_AccountView AV \r\n");
		sql
				.append("\tON TVE.FAccountID =AV.FID  AND AV.FCompanyID = TV.FCompanyID and AV.fcaa is  null and AV.FhasUserProperty=0 \r\n");

		sql
				.append("\tLEFT JOIN t_bd_measureunitgroup MUG ON AV.FMeasureUnitGroupID=MUG.fid \r\n");
		HashMap table = new HashMap();
		table.put("VOUCHER", "TV");
		table.put("ENTRIES", "TVE");
		table.put("VOUCHERTYPE", "TVT");
		table.put("PERIOD", "TP");
		table.put("ACCOUNTVIEW", "AV");
		if (condition.getOptionShowAccountCusAttribute()) {
			table.put("SETTLEMENTTYPE", "TST");
		}

		boolean isIncludeAssist = false;

		sql.append(" LEFT OUTER JOIN T_GL_VoucherAssistRecord TVAR \r\n");
		if ((this.isShowQuantity) && (getPar(partner))) {
			sql
					.append("\tLEFT JOIN T_BD_MeasureUnit MUI ON TVAR.FAssistUnit=MUI.fid \r\n");
		}
		sql.append(" ON TVE.FID = TVAR.FentryID \r\n");
		sql.append(" LEFT OUTER JOIN T_BD_SettlementType TST \r\n");
		sql.append(" ON TST.FID = TVAR.FSettlementTypeID \r\n");

		if ((partner.getAccountViewInfo().isHasUserProperty())
				|| (condition.getOptionShowAccountCusAttribute())) {
			sql.append(" LEFT OUTER JOIN T_PM_User THdl \r\n");
			sql.append(" ON THdl.FID = TVAR.FHandlerID \r\n");
		}
		table.put("ASSISTRECORDS", "TVAR");
		isIncludeAssist = true;

		HashMap tableValue = RptServerUtil.getRptSelfCongdition(partner
				.getContext(), partner.getCondition(), table, isIncludeAssist,
				false);
		if ((tableValue != null) && (tableValue.get("join") != null)) {
			sql.append(tableValue.get("join").toString());
		}

		sql.append(" WHERE TV.FCompanyID = ? \r\n");
		sql.append(" AND AV.FCompanyID = ? AND \r\n");
		sql.append("    (TV.FBizStatus =   ");
		sql.append(5);
		sql.append("  \r\n");

		if (condition.getOptionPosting() == true) {
			sql.append("    OR TV.FBizStatus =   ");
			sql.append(1);
			sql.append(" \r\n");
			sql.append("    OR TV.FBizStatus =   ");
			sql.append(3);
			sql.append("  \r\n");
		}
		sql.append(") AND \r\n");
		if (condition.getOptionNotIncludePLVoucher()) {
			sql.append(" TV.FSourceType != ").append(1);
			sql.append(" AND \r\n");
		}
		if (partner.getAccountViewInfo().isIsLeaf()) {
			sql.append("\tTVE.FAccountID = '");
			sql.append(partner.getAccountViewInfo().getId().toString());
			sql.append("' AND \r\n");
		} else {
			sql.append("\tTVE.FAccountID IN (");
			sql
					.append(getChildAccountIds(partner, partner
							.getAccountViewInfo()));
			sql.append(") AND \r\n");
		}

		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			sql.append("\tTVE.FCurrencyID = ? AND \r\n");
		} else if (condition.isAllCurrency()) {
			sql
					.append("\tTVE.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TVE.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC'  and\t\t\r\n");
		}
		sql.append("\tTP.FNumber>=? AND \r\n");
		sql.append("\tTP.FNumber<=? AND \r\n");
		sql.append("\tTP.FTypeID = ? \r\n");

		boolean isAsstFilter = false;
		List asstTable = condition.getTableData();
		int i = 0;
		for (int size = asstTable.size(); i < size; i++) {
			AsstactTypeEntity at = (AsstactTypeEntity) asstTable.get(i);

			if (at.isSelected()) {
				isAsstFilter = true;
				break;
			}
		}
		if (!isAsstFilter) {
			sql.append("\tand AV.FCAA is null \r\n");
		} else {
			sql.append("\tand AV.FCAA is not null \r\n");
		}

		if ((tableValue != null) && (tableValue.get("cond") != null)) {
			sql.append("  and " + tableValue.get("cond").toString());
		}
		Context ctx = partner.getContext();
		BOSUuid userId = ContextUtil.getCurrentUserInfo(ctx).getId();
		BOSUuid cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId();
		String rule = PermissionServiceProviderFactory
				.getLocalInstance(ctx)
				.getPermissionRule(new ObjectUuidPK(userId),
						new ObjectUuidPK(cuId), "bd_assistant_voucherType_view");
		String p = null;
		if (!StringUtils.isEmpty(rule)) {
			p = PermissionFactory.getLocalInstance(ctx).getQueryPermissionSQL(
					new ObjectUuidPK(userId), new ObjectUuidPK(cuId),
					"bd_assistant_voucherType_view");
			sql.append("    and TVT.FID IN \n(" + p + ")");
		}

		SqlParameter2 parameter = new SqlParameter2();

		parameter.addValueChar(partner.getCompanyId());
		parameter.addValueChar(partner.getCompanyId());
		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			parameter.addValueChar(condition.getCurrencyID());
		}
		parameter.addValue(new Integer(condition.getPeriodYearStart() * 100
				+ condition.getPeriodNumberStart()));
		parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
				+ condition.getPeriodNumberEnd()));
		parameter.addValueId(partner.getCompany().getAccountPeriodType());

		sql.append(" UNION ALL \r\n");

		sql
				.append("SELECT TV.FBookedDate,TVAR.FBizDate,TV.fnumber,TVT.fnumber,TVT.FName");
		sql.append(partner.getFieldNameExtend());
		sql.append(",TV.FCREATETIME, \r\n");

		sql.append(" TVE.FSeq * 1000 + TVAR.FSeq FSeq,\r\n");

		sql
				.append("(CASE WHEN TVAR.FDescription is not null THEN TVAR.FDescription ELSE TVE.FDescription END ) FDescription, \r\n");

		if (condition.getOptionShowAccountCusAttribute()) {
			sql.append(" TST.FName");
			sql.append(partner.getFieldNameExtend());
			sql.append(" FSettlementType,");
			sql.append(" TVAR.FSettlementCode,");
			sql.append(" TVAR.FBizNumber, ");
			sql.append(" TVAR.FTicketNumber,TVAR.FInvoiceNumber,");
			sql.append(" (case when TVAR.FFeeType=1 then '"
					+ FeeTypeEnum.getEnum(1).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=2 then '"
					+ FeeTypeEnum.getEnum(2).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=3 then '"
					+ FeeTypeEnum.getEnum(3).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=4 then '"
					+ FeeTypeEnum.getEnum(4).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=5 then '"
					+ FeeTypeEnum.getEnum(5).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=6 then '"
					+ FeeTypeEnum.getEnum(6).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=7 then '"
					+ FeeTypeEnum.getEnum(7).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=8 then '"
					+ FeeTypeEnum.getEnum(8).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=9 then '"
					+ FeeTypeEnum.getEnum(9).getAlias() + "'");
			sql.append("       when TVAR.FFeeType=10 then '"
					+ FeeTypeEnum.getEnum(10).getAlias() + "'");
			sql.append("       else '' end)as FFeeType,");
			sql.append(" THdl.FName");
			sql.append(partner.getFieldNameExtend());
			sql.append(" FHandler, ");
		}

		if (partner.getCurrencyType() == 1)
			addDebitCreditFieldSQL(sql, "TVAR.FLocalAmount", "0", "For");
		else if (partner.getCurrencyType() == 2)
			addDebitCreditFieldSQL(sql, "TVAR.FReportingAmount", "0", "For");
		else {
			addDebitCreditFieldSQL(sql, "TVAR.FOriginalAmount", "0", "For");
		}

		sql.append(" TVE.FLocalExchangeRate FLocalRate,\r\n");
		sql.append(" TVE.FReportingExchangeRate FRptRate,\r\n");

		addDebitCreditFieldSQL(sql, "TVAR.FLocalAmount", "0", "Local");
		addDebitCreditFieldSQL(sql, "TVAR.FReportingAmount", "0", "Rpt");

		if (this.isShowQuantity) {
			boolean isQtyAsst = partner.getParmQtyAsst();
			if ((!isQtyAsst)
					|| ((isQtyAsst) && (partner.getAccountViewInfo().isIsQty())
							&& (partner.getAccountViewInfo().getCAA() != null) && (!partner
							.getAccountViewInfo().getCAA().isIsQty()))) {
				addDebitCreditFieldSQL(sql, "TVAR.FMeasureUnitID", "NULL",
						"UnitId");
				addDebitCreditFieldSQL(sql, "TVAR.FQuantity", "0", "Qty");
				addDebitCreditFieldSQL(sql, "TVAR.FStandardQuantity", "0",
						"QtyDefault");
				addDebitCreditFieldSQL(sql, "TVE.FPrice", "NULL", "Price");

				if ((this.isShowQuantity) && (getPar(partner))) {
					addDebitCreditFieldSQL(sql, "MUI.FName_l2", "NULL",
							"AssistUnit");
					addDebitCreditFieldSQL(sql, "TVAR.FAssistPrice", "0",
							"AssistPrice");
					addDebitCreditFieldSQL(sql, "TVAR.FAssistQty", "0",
							"AssistQty");
				}

			} else {
				addDebitCreditFieldSQL(sql, "TVAR.FMeasureUnitID", "NULL",
						"UnitId");
				addDebitCreditFieldSQL(sql, "TVAR.FQuantity", "0", "Qty");
				addDebitCreditFieldSQL(sql, "TVAR.FStandardQuantity", "0",
						"QtyDefault");
				addDebitCreditFieldSQL(sql, "TVAR.FPrice", "NULL", "Price");

				if (((condition.getOptionOnlyAsst()) || (condition
						.getAssisthgId() != null))
						&& (this.isShowQuantity) && (getPar(partner))) {
					addDebitCreditFieldSQL(sql, "MUI.FName_l2", "NULL",
							"AssistUnit");
					addDebitCreditFieldSQL(sql, "TVAR.FAssistPrice", "0",
							"AssistPrice");
					addDebitCreditFieldSQL(sql, "TVAR.FAssistQty", "0",
							"AssistQty");
				}

			}

		}

		if (partner.getAccountViewInfo().isIsQty())
			sql
					.append("(CASE WHEN MUG.fdefaultunitid IS NOT NULL THEN MUG.fdefaultunitid ELSE AV.FMeasureUnitID END ) FBalanceUnitId, \r\n");
		else {
			sql
					.append("(CASE WHEN MUG.fdefaultunitid IS NOT NULL THEN MUG.fdefaultunitid ELSE TVAR.FMeasureUnitID END ) FBalanceUnitId, \r\n");
		}

		sql.append("\t0 FLineType,TV.FID FVoucherID, \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    TVE.FCurrencyid FCurrencyid, \r\n");
		}
		sql
				.append("\tTP.FPeriodYear  FPeriodYear,TP.FPeriodNumber FPeriodNumber \r\n");

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql.append(" ,2 FAccountOrAssist, \t\r\n");
			sql.append(" HG.FCount FCount,  \t\r\n");
			sql.append(" HG.FID   FAsstAccountPerId, \r\n");

			sql.append(" Replace(");
			sql.append("HG.FNumberGroup_");
			sql.append(partner.getContext().getLocale());
			sql.append(", '_!', ' ')");
			sql.append("FNumberGroupAsst, \r\n");

			boolean parmAssitemShowNumber = partner.isParmAssitemShowNumber();

			boolean showLongNameFromGeneral = partner
					.isRptShowLongNameFromGeneral();

			if (parmAssitemShowNumber) {
				if (showLongNameFromGeneral) {
					sql.append(" Replace(");
					sql.append(" HG.FDisplayNameGroup_");
					sql.append(partner.getContext().getLocale());
					sql.append(", '_!', ' ')");
				} else {
					sql.append(" Replace(");
					sql.append(" HG.FNumberGroup_");
					sql.append(partner.getContext().getLocale());
					sql.append(", '_!', ' ')");
				}
			} else if (showLongNameFromGeneral) {
				sql.append(" Replace(");
				sql.append("    HG.FDisplayNameGroup_");
				sql.append(partner.getContext().getLocale());
				sql.append(", '_!', ' ')");
			} else {
				sql.append(" Replace(");
				sql.append("    HG.flongnamegroup_");
				sql.append(partner.getContext().getLocale());
				sql.append(", '_!', ' ')");
			}

			sql.append(" FAsstAccountName  \t\r\n");
		}

		if (isChkSum) {
			sql.append(" ,AV.FID accountid \r\n");
		}

		sql.append("FROM T_GL_Voucher TV \r\n");
		sql.append("\tINNER JOIN T_BD_VoucherTypes TVT \r\n ");
		sql.append("\tON TV.FVoucherTypeID = TVT.FID \r\n");
		sql.append("\tINNER JOIN T_GL_VoucherEntry TVE \r\n");
		sql.append("\tON TV.FID = TVE.FBillID \r\n");
		sql.append("\tINNER JOIN T_BD_Period TP \r\n");
		sql.append("\tON TV.FPeriodID = TP.FID \r\n");
		sql.append("\tINNER JOIN T_GL_VoucherAssistRecord TVAR  \r\n");
		sql.append("\tON TVE.FID = TVAR.FentryID \r\n");
		sql.append("\tLEFT JOIN T_BD_AssistantHG HG \r\n");
		sql.append("\tON TVAR.FAssGrpID = HG.FID \r\n");
		if ((partner.getAccountViewInfo().isAC())
				|| (partner.getAccountViewInfo().isIsBank())
				|| (partner.getAccountViewInfo().isHasUserProperty())
				|| (condition.getOptionShowAccountCusAttribute())) {
			sql.append("\tLEFT OUTER JOIN T_BD_SettlementType TST  \r\n");
			sql.append("\tON TST.FID = TVAR.FSettlementTypeID \r\n");
			sql.append(" LEFT OUTER JOIN T_PM_User THdl \r\n");
			sql.append(" ON THdl.FID = TVAR.FHandlerID \r\n");
		}

		sql.append("\tINNER JOIN T_BD_AccountView AV \r\n");
		sql
				.append("\tON TVE.FAccountID =AV.FID AND AV.FCompanyID = TV.FCompanyID \r\n");
		sql.append("\tLEFT JOIN t_bd_measureunitgroup MUG \r\n");
		sql.append("\tON AV.FMeasureUnitGroupID=MUG.fid \r\n");

		if (((condition.getOptionOnlyAsst()) || (condition.getAssisthgId() != null))
				&& (this.isShowQuantity) && (getPar(partner))) {
			sql
					.append("\tLEFT JOIN T_BD_MeasureUnit MUI ON TVAR.FAssistUnit=MUI.fid \r\n");
		}

		table.clear();
		table.put("VOUCHER", "TV");
		table.put("ENTRIES", "TVE");
		table.put("VOUCHERTYPE", "TVT");
		table.put("PERIOD", "TP");
		table.put("ACCOUNTVIEW", "AV");
		table.put("ASSISTRECORDS", "TVAR");
		if (condition.getOptionShowAccountCusAttribute()) {
			table.put("SETTLEMENTTYPE", "TST");
		}

		tableValue = RptServerUtil.getRptSelfCongdition(partner.getContext(),
				partner.getCondition(), table, true, condition
						.getOptionShowAccountCusAttribute());
		if ((tableValue != null) && (tableValue.get("join") != null)) {
			sql.append(tableValue.get("join").toString());
		}

		sql.append(" WHERE TV.FCompanyID = ? \r\n");
		sql.append(" AND TV.FCompanyID = ? AND \r\n");
		if (condition.getOptionPosting()) {
			sql.append("\t(TV.FBizStatus = ");
			sql.append(1);
			sql.append(" OR TV.FBizStatus = ");
			sql.append(3);
			sql.append(" OR TV.FBizStatus = ");
			sql.append(5);
			sql.append(" ) AND \r\n");
		} else {
			sql.append("\tTV.FBizStatus = ");
			sql.append(5);
			sql.append(" AND \r\n");
		}
		if (condition.getOptionNotIncludePLVoucher()) {
			sql.append(" TV.FSourceType != ").append(1);
			sql.append(" AND \r\n");
		}
		if (partner.getAccountViewInfo().isIsLeaf()) {
			sql.append("\tTVE.FAccountID = '");
			sql.append(partner.getAccountViewInfo().getId().toString());
			sql.append("' AND \r\n");
		} else {
			sql.append("\tTVE.FAccountID IN (");
			sql
					.append(getChildAccountIds(partner, partner
							.getAccountViewInfo()));
			sql.append(") AND \r\n");
		}

		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			sql.append("\tTVE.FCurrencyID = ? AND \r\n");
		} else if (condition.isAllCurrency()) {
			sql
					.append("\tTVE.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TVE.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC'  and\t\t\r\n");
		}

		sql.append(" TP.FNumber>=? AND \r\n");
		sql.append(" TP.FNumber<=? AND \r\n");
		sql.append(" TP.FTypeID =? \r\n");

		if ((tableValue != null) && (tableValue.get("cond") != null)) {
			sql.append("  and " + tableValue.get("cond").toString());
		}
		if (!StringUtils.isEmpty(rule)) {
			sql.append("    and TVT.FID IN \n(" + p + ")");
		}

		parameter.addValueChar(partner.getCompanyId());
		parameter.addValueChar(partner.getCompanyId());
		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			parameter.addValueChar(condition.getCurrencyID());
		}
		parameter.addValue(new Integer(condition.getPeriodYearStart() * 100
				+ condition.getPeriodNumberStart()));
		parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
				+ condition.getPeriodNumberEnd()));
		parameter.addValueId(partner.getCompany().getAccountPeriodType());

		SqlParams AsstWhereSp = new SqlParams();
		sql.append(" and ");
		try {
			sql.append(RptServerUtil.GetAsstSqlWithoutPermissionFilter(ctx,
					condition.getTableData(), condition.getOptionAsstGroup(),
					true, "TVAR.FAssGrpID", AsstWhereSp));
		} catch (ParseException e) {
			throw new BOSException(e);
		}

		Map actTypes = new HashMap();
		boolean needHandlePerm = false;
		List tblData = condition.getTableData();
		i = 0;
		for (int size = tblData.size(); i < size; i++) {
			AsstactTypeEntity at = (AsstactTypeEntity) tblData.get(i);
			actTypes.put(at.getId().toString(), at);
			if (at.isEnabledPermissionLimit()) {
				needHandlePerm = true;
			}

		}

		if (needHandlePerm) {
			Map acct2ActType = new HashMap();
			Set intersectActType = new HashSet();
			StringBuffer sqlAsstPerm = new StringBuffer();
			sqlAsstPerm.append("select a.fid accountid,d.fid acttypeid \r\n");
			sqlAsstPerm.append("from t_bd_accountview a \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstAccount b on a.fcaa=b.fid \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstActGroupDetail c on c.FAsstAccountID=b.fid \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstActType d on d.fid=c.FAsstActTypeID \r\n");
			sqlAsstPerm
					.append("where a.fcompanyid=? and a.faccounttableid=? and a.fisleaf=1 and a.flongnumber like ? \r\n");
			IRowSet rsActType = DbUtil
					.executeQuery(partner.getContext(), sqlAsstPerm.toString(),
							new Object[] {
									partner.getCompanyId(),
									partner.getAccountTableId(),
									partner.getAccountViewInfo()
											.getLongNumber()
											+ "%" });
			try {
				while (rsActType.next()) {
					List list = (List) acct2ActType.get(rsActType
							.getString("accountid"));
					if (list == null) {
						list = new ArrayList();
						acct2ActType
								.put(rsActType.getString("accountid"), list);
					}
					list.add(rsActType.getString("acttypeid"));
					intersectActType.add(rsActType.getString("acttypeid"));
				}
			} catch (SQLException sqle) {
				throw new SQLDataException(sqle);
			}

			Map asst2PermSql = new HashMap();
			IPermissionServiceProvider provider = PermissionServiceProviderFactory
					.getLocalInstance(ctx);
			IPermission permission = PermissionFactory.getLocalInstance(ctx);
			Iterator it = intersectActType.iterator();
			while (it.hasNext()) {
				String actTypeId = (String) it.next();
				AsstactTypeEntity at = (AsstactTypeEntity) actTypes
						.get(actTypeId);
				if (at != null) {
					if (at.isEnabledPermissionLimit()) {
						String asstRule = provider.getPermissionRule(
								new ObjectUuidPK(userId),
								new ObjectUuidPK(cuId), at.getPermissionItem());
						if (!StringUtils.isEmpty(asstRule)) {
							String permSql = permission.getQueryPermissionSQL(
									new ObjectUuidPK(userId), new ObjectUuidPK(
											cuId), at.getPermissionItem());
							asst2PermSql.put(at.getId().toString(),
									new Object[] { at, permSql });
						}
					}
				}

			}

			if (asst2PermSql.size() > 0) {
				StringBuffer collectAsstPermSql = new StringBuffer();
				StringBuffer acctAsstPermSql = new StringBuffer();
				Iterator itKey = acct2ActType.keySet().iterator();
				while (itKey.hasNext()) {
					String accountId = (String) itKey.next();
					List list = (List) acct2ActType.get(accountId);
					acctAsstPermSql.setLength(0);
					i = 0;
					for (int size = list.size(); i < size; i++) {
						Object[] obj = (Object[]) asst2PermSql.get(list.get(i));
						if (obj != null) {
							if (acctAsstPermSql.length() > 0) {
								acctAsstPermSql.append(" and ");
							}
							acctAsstPermSql.append("HG.").append(
									((AsstactTypeEntity) obj[0]).getHgField())
									.append(" in (").append(obj[1]).append(")");
						}

					}

					if (acctAsstPermSql.length() > 0) {
						acctAsstPermSql.append(" and TVE.FAccountID='").append(
								accountId).append("'");
						if (collectAsstPermSql.length() > 0) {
							collectAsstPermSql.append(" or ");
						}
						collectAsstPermSql.append("(").append(acctAsstPermSql)
								.append(")");
					}
				}
				if (collectAsstPermSql.length() > 0) {
					sql.append(" and (").append(collectAsstPermSql).append(")");
				}

			}

		}

		if (isChkSum) {
			sql.append(") tmp \r\n");
			sql.append("group by accountid,FVoucherID \r\n");
			if (isRdoIsAccountAndDesc) {
				sql.append(",FDescription_tve \r\n");
			}

			if (isChkIsZeroNoDisplay) {
				sql.append(") where  \r\n");
				sql
						.append("not (FDebitFor = 0 and FCreditFor = 0 and FDebitLocal = 0 and  \r\n");
				sql
						.append("FCreditLocal = 0 and FDebitRpt = 0 and FCreditRpt = 0) \r\n");
			}

		}

		Object[] Asst = AsstWhereSp.getParams();
		for (i = 0; i < Asst.length; i++) {
			parameter.addValueChar(Asst[i].toString());
		}

		return partner.executeSql(parameter
				.getSqlWithParameters(sql.toString()), null);
	}

	protected boolean insertHisData(ReportPartner partner) throws BOSException,
			EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		Context ctx = partner.getContext();
		BOSUuid userId = ContextUtil.getCurrentUserInfo(ctx).getId();
		BOSUuid cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId();
		
		String costorgunitid=condition.getStrCostUnitID();
		String companyUnitId = condition.getStrCompanyUnitID();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");
		sql
				.append("\t(FDate, FBizDate,FVoucherNumber,FVoucherTypeNumber ,FVoucherTypeName ,FCREATETIME ,FSeq, FDescription,  \r\n");

		sql.append("\t FDebitFor, FCreditFor,  \r\n");
		sql.append("\t FLocalRate,FRptRate, \r\n");
		sql.append("\t FDebitLocal,FCreditLocal, \r\n");
		sql.append("\t FDebitRpt,FCreditRpt, \r\n");

		sql.append("\tFBalanceUnitId,FLineType,FVoucherID,   \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    FCurrencyid , \r\n");
		}
		sql.append("FPeriodYear, FPeriodNumber \r\n");

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql.append(" , FAccountOrAssist, \r\n");
			sql.append("  FCount, \r\n");
			sql.append(" FAsstAccountPerId, \r\n");
			sql.append("FNumberGroupAsst, \r\n");
			sql.append("  FAsstAccountName \r\n");
		}

		Map editParame = condition.getExpandInfo();

		sql.append(") ");

		SqlParameter2 parameter = new SqlParameter2();

		parameter.addValueChar(companyUnitId);
		
//		parameter.addValueChar(partner.getCompanyId());
		// by tgw 重复出现
//		parameter.addValueChar(partner.getCompanyId());
		
		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			parameter.addValueChar(condition.getCurrencyID());
		}
		// parameter.addValue(new Integer(condition.getPeriodYearStart() * 100
		// + condition.getPeriodNumberStart()));
		// parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
		// + condition.getPeriodNumberEnd()));
		String datestartStr = null;
		String dateendStr = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CompanyOrgUnitInfo orgunitinfo = CompanyOrgUnitFactory
				.getLocalInstance(ctx).getCompanyOrgUnitInfo(
						new ObjectUuidPK(companyUnitId));
		// 开始时间
		PeriodInfo startperiodinfo = PeriodUtils.getPeriodInfo(ctx, condition
				.getPeriodYearStart(), condition.getPeriodNumberStart(),
				orgunitinfo);
		datestartStr = sdf.format(startperiodinfo.getBeginDate());
		// parameter.addValueChar(datestartStr);
		// 结束时间
		PeriodInfo endperiodinfo = PeriodUtils.getPeriodInfo(ctx, condition
				.getPeriodYearEnd(), condition.getPeriodNumberEnd(),
				orgunitinfo);
		dateendStr = sdf.format(endperiodinfo.getEndDate());
		// parameter.addValueChar(dateendStr);

		// parameter.addValueId(partner.getCompany().getAccountPeriodType());

		sql.append(" \r\n");

		sql
				.append("select CTB.CFDateOfEntry as bookDate,CTB.FBizDate,CTB.FNUMBER,'','',CTB.FCREATETIME,");
		// sql.append(partner.getFieldNameExtend());
		sql.append("1,CTB.CFContents as descriptions, \r\n");

		if (partner.getCurrencyType() == 1) {
			sql
					.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=1 THEN CTB.CFCURRENCYAMOUNT ELSE 0 END) FDebitFor ,\r\n");
			sql
					.append("(CASE WHEN CTB.CFBORROWINGDIRECTION=0 THEN CTB.CFCURRENCYAMOUNT ELSE 0 END) FCreditFor , \r\n");
		} else if (partner.getCurrencyType() == 2) {
			sql
					.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=1 THEN CTB.CFREPORTINGCurrency ELSE 0 END) FDebitFor ,\r\n");
			sql
					.append("(CASE WHEN CTB.CFBORROWINGDIRECTION=0 THEN CTB.CFREPORTINGCurrency ELSE 0 END) FCreditFor , \r\n");
		} else {
			sql
					.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=1 THEN CTB.CFOriginalCurrency ELSE 0 END) FDebitFor ,\r\n");
			sql
					.append("(CASE WHEN CTB.CFBORROWINGDIRECTION=0 THEN CTB.CFOriginalCurrency ELSE 0 END) FCreditFor , \r\n");
		}

		sql.append(" 1 FLocalRate,\r\n");
		sql.append(" 1 FRptRate,\r\n");

		sql
				.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=1 THEN CTB.CFCURRENCYAMOUNT ELSE 0 END) FDebitLocal ,\r\n");
		sql
				.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=0 THEN CTB.CFCURRENCYAMOUNT ELSE 0 END) FCreditLocal ,\r\n");
		sql
				.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=1 THEN CTB.CFREPORTINGCurrency ELSE 0 END) FDebitRpt ,\r\n");
		sql
				.append(" (CASE WHEN CTB.CFBORROWINGDIRECTION=0 THEN CTB.CFREPORTINGCurrency ELSE 0 END) FCreditRpt ,\r\n");

		if (partner.getAccountViewInfo().isIsQty())
			sql.append("'gw5fUwEOEADgAArZwKgSOFuCXFc=' FBalanceUnitId, \r\n");
		else {
			sql.append("'gw5fUwEOEADgAArZwKgSOFuCXFc=' FBalanceUnitId, \r\n");
		}

		sql.append("\t0 FLineType,CTB.FID FVoucherID, \r\n");
		if (condition.isAllCurrency()) {
			sql.append("    CTB.CFFcurrencyID FCurrencyid, \r\n");
		}
		sql
				.append("\t YEAR(CTB.CFDateOfEntry)  FPeriodYear,MONTH(CTB.CFDateOfEntry) FPeriodNumber \r\n");

		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			sql.append(" ,2 FAccountOrAssist, \t\r\n");
			sql.append(" HG.FCount FCount,  \t\r\n");
			sql.append(" HG.FID   FAsstAccountPerId, \r\n");

			sql.append(" Replace(");
			sql.append("HG.FNumberGroup_");
			sql.append(partner.getContext().getLocale());
			sql.append(", '_!', ' ')");
			sql.append("FNumberGroupAsst, \r\n");

			boolean parmAssitemShowNumber = partner.isParmAssitemShowNumber();

			boolean showLongNameFromGeneral = partner
					.isRptShowLongNameFromGeneral();

			if (parmAssitemShowNumber) {
				if (showLongNameFromGeneral) {
					sql.append(" Replace(");
					sql.append(" HG.FDisplayNameGroup_");
					sql.append(partner.getContext().getLocale());
					sql.append(", '_!', ' ')");
				} else {
					sql.append(" Replace(");
					sql.append(" HG.FNumberGroup_");
					sql.append(partner.getContext().getLocale());
					sql.append(", '_!', ' ')");
				}
			} else if (showLongNameFromGeneral) {
				sql.append(" Replace(");
				sql.append("    HG.FDisplayNameGroup_");
				sql.append(partner.getContext().getLocale());
				sql.append(", '_!', ' ')");
			} else {
				sql.append(" Replace(");
				sql.append("    HG.flongnamegroup_");
				sql.append(partner.getContext().getLocale());
				sql.append(", '_!', ' ')");
			}

			sql.append(" FAsstAccountName  \t\r\n");
		}

		sql.append("FROM CT_CR_BeginHiDetailBill CTB \r\n");
		sql.append("\tLEFT JOIN T_BD_ImpAssistantHG HG \r\n");
		sql.append("\tON CTB.CFAssistGrpID = HG.FID \r\n");
		if ((partner.getAccountViewInfo().isAC())
				|| (partner.getAccountViewInfo().isIsBank())
				|| (partner.getAccountViewInfo().isHasUserProperty())
				|| (condition.getOptionShowAccountCusAttribute())) {
			sql.append(" LEFT OUTER JOIN T_PM_User THdl \r\n");
			sql.append(" ON THdl.FID = CTB.FHandlerID \r\n");
		}

		sql.append("\tLEFT JOIN T_BD_AccountView AV \r\n");
		sql
				.append("\tON CTB.CFFAccountIDID =AV.FID AND AV.FCompanyID = CTB.CFCompanyOrgID \r\n");

		sql.append(" WHERE CTB.CFCompanyOrgID in (?) \r\n");
		// by tgw 重复出现
//		sql.append(" AND CTB.CFCompanyOrgID = ? AND \r\n"); 
		sql.append(" AND ");

		if (partner.getAccountViewInfo().isIsLeaf()) {
			sql.append("\t CTB.CFFAccountIDID = '");
			sql.append(partner.getAccountViewInfo().getId().toString());
			sql.append("' AND \r\n");
		} else {
			sql.append("\t CTB.CFFAccountIDID IN (");
			sql
					.append(getChildAccountIds(partner, partner
							.getAccountViewInfo(),companyUnitId));
			sql.append(") AND \r\n");
		}

		if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency())) {
			sql.append("\t CTB.CFFcurrencyID = ? AND \r\n");
		} else if (condition.isAllCurrency()) {
			sql
					.append("\t CTB.CFFcurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and CTB.CFFcurrencyID <> '22222222-2222-2222-2222-222222222222DEB58FDC'  and\t\t\r\n");
		}

		sql.append(" CTB.CFDateOfEntry>={ts'" + datestartStr + "'} AND \r\n");
		sql.append(" CTB.CFDateOfEntry<={ts'" + dateendStr + "'} \r\n");
//		sql.append("AND (");
//		if(costorgunitid!=null){
//			sql.append(" HG.FCostOrgID='"+ costorgunitid+"' \r\n");
//		}
		
		// parameter.addValueChar(partner.getCompanyId());
		// parameter.addValueChar(partner.getCompanyId());
		// if ((partner.getCurrencyType() == 0) && (!condition.isAllCurrency()))
		// {
		// parameter.addValueChar(condition.getCurrencyID());
		// }
		// String datestartStr = null;
		// String dateendStr = null;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// CompanyOrgUnitInfo
		// orgunitinfo=CompanyOrgUnitFactory.getLocalInstance(ctx)
		// .getCompanyOrgUnitInfo(new
		// ObjectUuidPK(partner.getCompanyId().toString()));
		// //开始时间
		// PeriodInfo startperiodinfo=PeriodUtils.getPeriodInfo(ctx,condition.
		// getPeriodYearStart(), condition.getPeriodNumberStart(),orgunitinfo );
		// datestartStr = sdf.format(startperiodinfo.getBeginDate());
		// parameter.addValueChar(datestartStr);
		// //结束时间
		// PeriodInfo
		//endperiodinfo=PeriodUtils.getPeriodInfo(ctx,condition.getPeriodYearEnd
		// (), condition.getPeriodNumberEnd(),orgunitinfo );
		// dateendStr= sdf.format(endperiodinfo.getEndDate());
		// parameter.addValueChar(dateendStr);

		// parameter.addValue(new Integer(condition.getPeriodYearStart() * 100
		// + condition.getPeriodNumberStart()));
		// parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
		// + condition.getPeriodNumberEnd()));
		// parameter.addValueId(partner.getCompany().getAccountPeriodType());

		SqlParams AsstWhereSp = new SqlParams();
		// sql.append(" and ");
		try {
			// tgw condition.getOptionAsstGroup()-false;
			String shg=RptServerUtil.GetAsstSqlWithoutPermissionFilter(ctx,
					condition.getTableData(), condition.getOptionAsstGroup(),
					true, "CTB.CFAssistGrpID", AsstWhereSp).replace(
					"t_bd_assistanthg", "T_BD_ImpAssistantHG");
			if(shg.equalsIgnoreCase("1=1")){
				
			}else{
				sql.append(" or \r\n");
				sql.append(shg);
			}
		} catch (ParseException e) {
			throw new BOSException(e);
		}

		Map actTypes = new HashMap();
		boolean needHandlePerm = false;
		List tblData = condition.getTableData();
		int i = 0;
		for (int size = tblData.size(); i < size; i++) {
			AsstactTypeEntity at = (AsstactTypeEntity) tblData.get(i);
			actTypes.put(at.getId().toString(), at);
			if (at.isEnabledPermissionLimit()) {
				needHandlePerm = true;
			}

		}

		if (needHandlePerm) {
			Map acct2ActType = new HashMap();
			Set intersectActType = new HashSet();
			StringBuffer sqlAsstPerm = new StringBuffer();
			sqlAsstPerm.append("select a.fid accountid,d.fid acttypeid \r\n");
			sqlAsstPerm.append("from t_bd_accountview a \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstAccount b on a.fcaa=b.fid \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstActGroupDetail c on c.FAsstAccountID=b.fid \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstActType d on d.fid=c.FAsstActTypeID \r\n");
			sqlAsstPerm
					.append("where a.fcompanyid=? and a.faccounttableid=? and a.fisleaf=1 and a.flongnumber like ? \r\n");
			IRowSet rsActType = DbUtil
					.executeQuery(partner.getContext(), sqlAsstPerm.toString(),
							new Object[] {
									partner.getCompanyId(),
									partner.getAccountTableId(),
									partner.getAccountViewInfo()
											.getLongNumber()
											+ "%" });
			try {
				while (rsActType.next()) {
					List list = (List) acct2ActType.get(rsActType
							.getString("accountid"));
					if (list == null) {
						list = new ArrayList();
						acct2ActType
								.put(rsActType.getString("accountid"), list);
					}
					list.add(rsActType.getString("acttypeid"));
					intersectActType.add(rsActType.getString("acttypeid"));
				}
			} catch (SQLException sqle) {
				throw new SQLDataException(sqle);
			}

			Map asst2PermSql = new HashMap();
			IPermissionServiceProvider provider = PermissionServiceProviderFactory
					.getLocalInstance(ctx);
			IPermission permission = PermissionFactory.getLocalInstance(ctx);
			Iterator it = intersectActType.iterator();
			while (it.hasNext()) {
				String actTypeId = (String) it.next();
				AsstactTypeEntity at = (AsstactTypeEntity) actTypes
						.get(actTypeId);
				if (at.isEnabledPermissionLimit()) {
					String asstRule = provider.getPermissionRule(
							new ObjectUuidPK(userId), new ObjectUuidPK(cuId),
							at.getPermissionItem());
					if (!StringUtils.isEmpty(asstRule)) {
						String permSql = permission.getQueryPermissionSQL(
								new ObjectUuidPK(userId),
								new ObjectUuidPK(cuId), at.getPermissionItem());
						asst2PermSql.put(at.getId().toString(), new Object[] {
								at, permSql });
					}
				}

			}

			if (asst2PermSql.size() > 0) {
				StringBuffer collectAsstPermSql = new StringBuffer();
				StringBuffer acctAsstPermSql = new StringBuffer();
				Iterator itKey = acct2ActType.keySet().iterator();
				while (itKey.hasNext()) {
					String accountId = (String) itKey.next();
					List list = (List) acct2ActType.get(accountId);
					acctAsstPermSql.setLength(0);
					i = 0;
					for (int size = list.size(); i < size; i++) {
						Object[] obj = (Object[]) asst2PermSql.get(list.get(i));
						if (obj != null) {
							if (acctAsstPermSql.length() > 0) {
								acctAsstPermSql.append(" and ");
							}
							acctAsstPermSql.append("HG.").append(
									((AsstactTypeEntity) obj[0]).getHgField())
									.append(" in (").append(obj[1]).append(")");
						}

					}

					if (acctAsstPermSql.length() > 0) {
						acctAsstPermSql.append(" and CTB.CFFAccountIDID='")
								.append(accountId).append("'");
						if (collectAsstPermSql.length() > 0) {
							collectAsstPermSql.append(" or ");
						}
						collectAsstPermSql.append("(").append(acctAsstPermSql)
								.append(")");
					}
				}
				if (collectAsstPermSql.length() > 0) {
					sql.append(" and (").append(collectAsstPermSql).append(")");
				}
				
			}

		}
//		sql.append(")");
		Object[] Asst = AsstWhereSp.getParams();
		for (i = 0; i < Asst.length; i++) {
			parameter.addValueChar(Asst[i].toString());
		}

		return partner.executeSql(parameter
				.getSqlWithParameters(sql.toString()), null);
	}

	private Object getChildAccountIds(ReportPartner partner,
			AccountViewInfo accountViewInfo, String companyUnitId) throws BOSException,
			EASBizException {
		if (accountViewInfo == null) {
			throw new ReportException(ReportException.ACCOUNT_EMPTY);
		}

		StringBuffer sb = new StringBuffer();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT TA.FID \r\n ");
		sql.append("FROM T_BD_AccountView---\r\n TA \r\n ");
		sql.append("WHERE TA.FLongNumber like ? \r\n ");
		sql.append("\tAND TA.FAccountTableID = ? \r\n ");
		sql.append("\tAND TA.FCompanyID = ? ");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = partner.getConnection().prepareStatement(sql.toString());
			stmt.setFetchSize(100);
			SqlParameter2 parameter = new SqlParameter2();
			parameter.addValueChar(accountViewInfo.getLongNumber() + "!%");
			parameter.addValueChar(partner.getAccountTableId());
			parameter.addValueChar(companyUnitId);
			parameter.putToStatement(stmt);

			rs = stmt.executeQuery();

			int index = 0;

			while (rs.next()) {
				sb.append(index > 0 ? ",'" : "'");
				sb.append(rs.getString("FID"));
				sb.append(index % 5 == 0 ? "'\r\n" : "'");

				index++;
			}
		} catch (SQLException sqle) {
			sqle.setNextException(new SQLException("The query sql is:"
					+ sql.toString()));
			throw new SQLDataException(sqle);
		} finally {
			SQLUtils.cleanup(rs, stmt);
		}

		return sb.length() > 0 ? sb.toString() : null;
	}

	protected void addDebitCreditFieldSQL(StringBuffer sql, String fieldName,
			String elseValue, String asFieldName) {
		sql.append("\t(CASE WHEN TVE.FEntryDC=");
		sql.append(1);
		sql.append(" THEN ");
		sql.append(fieldName);
		sql.append(" ELSE ");
		sql.append(elseValue);
		sql.append(" END) FDebit");
		sql.append(asFieldName);
		sql.append(" ,\r\n");
		sql.append("\t(CASE WHEN TVE.FEntryDC=");
		sql.append(0);
		sql.append(" THEN ");
		sql.append(fieldName);
		sql.append(" ELSE ");
		sql.append(elseValue);
		sql.append(" END) FCredit");
		sql.append(asFieldName);
		sql.append(" ,\r\n");
	}

	protected String getChildAccountIds(ReportPartner partner,
			AccountViewInfo accountViewInfo) throws BOSException,
			EASBizException {
		if (accountViewInfo == null) {
			throw new ReportException(ReportException.ACCOUNT_EMPTY);
		}

		StringBuffer sb = new StringBuffer();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT TA.FID \r\n ");
		sql.append("FROM T_BD_AccountView---\r\n TA \r\n ");
		sql.append("WHERE TA.FLongNumber like ? \r\n ");
		sql.append("\tAND TA.FAccountTableID = ? \r\n ");
		sql.append("\tAND TA.FCompanyID = ? ");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = partner.getConnection().prepareStatement(sql.toString());
			stmt.setFetchSize(100);
			SqlParameter2 parameter = new SqlParameter2();
			parameter.addValueChar(accountViewInfo.getLongNumber() + "!%");
			parameter.addValueChar(partner.getAccountTableId());
			parameter.addValueChar(partner.getCompanyId());
			parameter.putToStatement(stmt);

			rs = stmt.executeQuery();

			int index = 0;

			while (rs.next()) {
				sb.append(index > 0 ? ",'" : "'");
				sb.append(rs.getString("FID"));
				sb.append(index % 5 == 0 ? "'\r\n" : "'");

				index++;
			}
		} catch (SQLException sqle) {
			sqle.setNextException(new SQLException("The query sql is:"
					+ sql.toString()));
			throw new SQLDataException(sqle);
		} finally {
			SQLUtils.cleanup(rs, stmt);
		}

		return sb.length() > 0 ? sb.toString() : null;
	}

	protected boolean insertStartBalance(Map startBalance, ReportPartner partner)
			throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT ");
		sql.append(partner.getTempTableName());
		sql.append(" \r\n");

		sql
				.append("\t(FNumber,FName,FDate,FDebitFor,FDebitLocal,FDebitRpt, \r\n");

		if (this.isShowQuantity) {
			sql.append(" FDebitQtyDefault , \r\n");
		}
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append(" FCurrencyid,\r\n");
		}
		sql.append("\t FLineType,FPeriodYear,FPeriodNumber) \r\n");

		sql.append("VALUES (?,?,#,?,?,?,");

		if (this.isShowQuantity) {
			sql.append("?,");
		}
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append(" ?,");
		}
		sql.append("?,?,?) \r\n");

		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			ArrayList hasInsertCur = new ArrayList();
			for (int i = 0; i < startBalance.size(); i++) {
				HashMap curStartBalance = (HashMap) startBalance
						.get(new Integer(i));
				if ((curStartBalance.get("FCurrencyid") == null)
						|| ("".equals(curStartBalance.get("FCurrencyid")))) {
					continue;
				}
				if (hasInsertCur.contains(curStartBalance.get("FCurrencyid"))) {
					continue;
				}
				hasInsertCur.add(curStartBalance.get("FCurrencyid"));

				int periodNumberStart = ((ZDFReportConditionSubsidiaryLedger) partner
						.getFixCondition()).getPeriodNumberStart();
				int periodYearStart = ((ZDFReportConditionSubsidiaryLedger) partner
						.getFixCondition()).getPeriodYearStart();
				Integer periodNumber = (Integer) curStartBalance
						.get("FPeriodNumber");
				SqlParameter2 parameter = new SqlParameter2();
				parameter.addValue(startBalance.get("FNumber") == null ? ""
						: startBalance.get("FNumber"));
				parameter.addValue(startBalance.get("FName") == null ? ""
						: startBalance.get("FName"));

				if (periodNumberStart < periodNumber.intValue()) {
					parameter.addValue(partner.getPeriodTypeID());
					parameter.addValue(periodYearStart * 100
							+ periodNumberStart);
					parameter.addValue(ZERO);
					parameter.addValue(ZERO);
					parameter.addValue(ZERO);
					if (this.isShowQuantity) {
						parameter.addValue(ZERO);
					}
					if (((ZDFReportConditionSubsidiaryLedger) partner
							.getFixCondition()).isAllCurrency()) {
						parameter.addValue(curStartBalance.get("FCurrencyid"));
					}
					parameter.addValue(new Integer(
							periodNumber.intValue() == 1 ? -3 : -2));

					parameter.addValue(periodNumberStart);
					parameter.addValue(periodYearStart);

					partner
							.executeSql(
									sql
											.toString()
											.replaceFirst("#",
													"(select fbegindate from t_bd_period where ftypeid = ? and fnumber = ?)"),
									parameter);
				} else {
					parameter.addValue(curStartBalance.get("FBeginDate"));
					parameter.addValue(curStartBalance.get("FBeginBalanceFor"));
					parameter.addValue(curStartBalance
							.get("FBeginBalanceLocal"));
					parameter.addValue(curStartBalance.get("FBeginBalanceRpt"));
					if (this.isShowQuantity) {
						parameter.addValue(curStartBalance.get("FBeginQty"));
					}
					if (((ZDFReportConditionSubsidiaryLedger) partner
							.getFixCondition()).isAllCurrency()) {
						parameter.addValue(curStartBalance.get("FCurrencyid"));
					}
					parameter.addValue(new Integer(
							periodNumber.intValue() == 1 ? -3 : -2));

					parameter.addValue(curStartBalance.get("FPeriodYear"));
					parameter.addValue(curStartBalance.get("FPeriodNumber"));

					partner.executeSql(sql.toString().replaceFirst("#", "?"),
							parameter);
				}
			}
		} else {
			SqlParameter2 parameter = new SqlParameter2();
			parameter.addValue(startBalance.get("FNumber") == null ? ""
					: startBalance.get("FNumber"));
			parameter.addValue(startBalance.get("FName") == null ? ""
					: startBalance.get("FName"));
			parameter.addValue(startBalance.get("FBeginDate"));
			parameter.addValue(startBalance.get("FBeginBalanceFor"));
			parameter.addValue(startBalance.get("FBeginBalanceLocal"));
			parameter.addValue(startBalance.get("FBeginBalanceRpt"));
			if (this.isShowQuantity) {
				parameter.addValue(startBalance.get("FBeginQty"));
			}
			if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
					.isAllCurrency()) {
				parameter.addValue(startBalance.get("FCurrencyid"));
			}
			Integer periodNumber = (Integer) startBalance.get("FPeriodNumber");
			parameter.addValue(new Integer(periodNumber.intValue() == 1 ? -3
					: -2));

			parameter.addValue(startBalance.get("FPeriodYear"));
			parameter.addValue(startBalance.get("FPeriodNumber"));

			partner
					.executeSql(sql.toString().replaceFirst("#", "?"),
							parameter);
		}
		return true;
	}

	protected HashMap getStartBalance(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT \r\n");
		sql.append("\tTP.FBeginDate,TP.FPeriodYear,TP.FPeriodNumber,\r\n");
		if (condition.isAllCurrency()) {
			sql.append("    TB.FCurrencyid , \r\n");
		}
		sql
				.append(" sum(TB.FBeginBalanceFor) FBeginBalanceFor,sum(FBeginBalanceLocal) FBeginBalanceLocal,sum(FBeginBalanceRpt) FBeginBalanceRpt,\r\n");
		sql.append(" sum(FBeginQty) FBeginQty,\r\n");
		sql.append(" sum(TB.FYearDebitFor - TB.FDebitFor) FYearDebitFor,\r\n");
		sql
				.append(" sum(TB.FYearDebitLocal - TB.FDebitLocal) FYearDebitLocal,\r\n");
		sql.append(" sum(TB.FYearDebitRpt - TB.FDebitRpt) FYearDebitRpt,\r\n");
		sql.append(" sum(TB.FYearDebitQty - TB.FDebitQty) FYearDebitQty,\r\n");
		sql
				.append(" sum(TB.FYearCreditFor - TB.FCreditFor) FYearCreditFor, \r\n");
		sql
				.append(" sum(TB.FYearCreditLocal - TB.FCreditLocal) FYearCreditLocal, \r\n");
		sql
				.append(" sum(TB.FYearCreditRpt - TB.FCreditRpt) FYearCreditRpt, \r\n");
		sql
				.append(" sum(TB.FYearCreditQty - TB.FCreditQty) FYearCreditQty \r\n");
		sql.append(" from  ").append(
				getGeneralLedgerTableName(partner) + " TB \r\n");

		sql
				.append(" RIGHT OUTER JOIN T_BD_Period TP ON TB.FPeriodID = TP.FID \r\n");
		sql.append(" AND TB.FBalType = ? \r\n");
		sql.append(" AND TB.FOrgUnitID = ?  \r\n");
		sql.append(" AND TB.FAccountID = ?  \r\n");
		if (!condition.isAllCurrency()) {
			sql.append(" AND TB.FCurrencyID = ?  \r\n");
		}
		sql.append("WHERE TP.FTypeID = ? AND \r\n");
		int[] periodRange = {
				condition.getPeriodYearStart() * 100
						+ condition.getPeriodNumberStart(),
				condition.getPeriodYearEnd() * 100
						+ condition.getPeriodNumberEnd() };
		sql.append(SQLUtil.getPeriodCondition("TP.FNumber", periodRange));
		if (condition.isAllCurrency()) {
			sql
					.append(" AND TB.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TB.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC'  \t\t\r\n");
		}
		sql
				.append("\r\n group by TP.FBeginDate,TP.FPeriodYear,TP.FPeriodNumber \r\n");
		if (condition.isAllCurrency()) {
			sql.append(", TB.FCurrencyid  \r\n");
		}
		sql.append(" order by fbegindate");

		List params = new ArrayList();
		params.add(new Integer(condition.getOptionPosting() ? 1 : 5));
		params.add(partner.getCompanyId());
		params.add(condition.getAccountId());
		if (!condition.isAllCurrency()) {
			params.add(condition.getCurrencyID());
		}
		params.add(partner.getCompany().getAccountPeriodType().getId()
				.toString());

		HashMap map = new HashMap();
		HashMap curMap = new HashMap();
		try {
			IRowSet rs = DbUtil.executeQuery(partner.getContext(), sql
					.toString(), params.toArray());
			int ci = 0;
			if (rs.next()) {
				do {
					map.put("FBeginDate", rs.getTimestamp("FBeginDate"));
					map.put("FPeriodYear",
							new Integer(rs.getInt("FPeriodYear")));
					map.put("FPeriodNumber", new Integer(rs
							.getInt("FPeriodNumber")));
					if (condition.isAllCurrency()) {
						map.put("FCurrencyid", rs.getString("FCurrencyid"));
					}
					BigDecimal val = rs.getBigDecimal("FBeginBalanceFor");
					map.put("FBeginBalanceFor", val == null ? ZERO : val);
					val = rs.getBigDecimal("FBeginBalanceLocal");
					map.put("FBeginBalanceLocal", val == null ? ZERO : val);
					val = rs.getBigDecimal("FBeginBalanceRpt");
					map.put("FBeginBalanceRpt", val == null ? ZERO : val);
					val = rs.getBigDecimal("FBeginQty");
					map.put("FBeginQty", val == null ? ZERO : val
							.setScale(6, 4));

					val = rs.getBigDecimal("FYearDebitFor");
					map.put("FYearDebitFor", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearCreditFor");
					map.put("FYearCreditFor", val == null ? ZERO : val);

					val = rs.getBigDecimal("FYearDebitLocal");
					map.put("FYearDebitLocal", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearCreditLocal");
					map.put("FYearCreditLocal", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearDebitRpt");
					map.put("FYearDebitRpt", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearCreditRpt");
					map.put("FYearCreditRpt", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearDebitQty");
					map.put("FYearDebitQty", val == null ? ZERO : val.setScale(
							6, 4));
					val = rs.getBigDecimal("FYearCreditQty");
					map.put("FYearCreditQty", val == null ? ZERO : val
							.setScale(6, 4));

					map.put("FDebitAssistPrice", ZERO);
					map.put("FDebitAssistQty", ZERO);
					map.put("FCreditAssistPrice", ZERO);
					map.put("FCreditAssistQty", ZERO);

					if (condition.isAllCurrency()) {
						curMap.put(new Integer(ci++), map);
						map = new HashMap();
					}
					if (!rs.next())
						break;
				} while (condition.isAllCurrency());
			}

		} catch (SQLException sqle) {
			sqle.setNextException(new SQLException("The query sql is:"
					+ sql.toString()));
			throw new SQLDataException(sqle);
		}

		if (condition.isAllCurrency()) {
			return curMap;
		}
		return map;
	}

	protected void doSomethingBeforeReturn(ReportPartner partner,
			ReportResultInfo result) throws BOSException, EASBizException {
		super.doSomethingBeforeReturn(partner, result);

		if (partner.getStartIndex() == 0) {
			result.getExtendData().put("Account", partner.getAccountViewInfo());
			result.getExtendData().put("AccountShowLongName",
					Boolean.valueOf(partner.isParmAccountShowLongName()));
		}
	}

	protected String getAuthoritySql(ReportPartner partner, String authorityName)
			throws BOSException, EASBizException {
		return null;
	}

	protected String getReportName() {
		return "SubsidiaryLedger";
	}

	protected String getAuthorityName() {
		return "SubsidiaryLedger";
	}

	protected String getQueryName() {
		return "SubsidiaryLedger";
	}

	// protected boolean isSupportVirtual() {
	// return true;
	// }

	protected String getGeneralLedgerTableName(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		boolean isAfterCurrPeriod = false;
		if (partner.getCurrentPeriod() != null)
			isAfterCurrPeriod = partner.getCurrentPeriod().getNumber() < condition
					.getPeriodYearStart()
					* 100 + condition.getPeriodNumberStart();
		return isAfterCurrPeriod ? "V_GL_AccountBalance"
				: "T_GL_AccountBalance";
	}

	protected int getScaleFor(ReportPartner partner, String curId)
			throws BOSException, EASBizException {
		if (partner.getFixCondition().isAllCurrency()) {
			if (curId != null) {
				return partner.getAmountScale(curId);
			}
			return 2;
		}

		return partner.getScaleFor();
	}

	protected Collection executeSqlForData(Context ctx, ReportPartner partner)
			throws BOSException, EASBizException {
		String sqlFrom = getSqlForDataFrom(partner);
		if (sqlFrom == null) {
			return new ArrayList();
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select ");

		String sqlSelect = getSqlForDataSelect(partner);
		if ((sqlSelect == null) || (sqlSelect.length() == 0)) {
			throw new ReportException(ReportException.SQL_SELECT_EMPTY);
		}
		sql.append(sqlSelect);
		sql.append(" from (select T.* \r\n");
		if (((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.isAllCurrency()) {
			sql.append(", cc.fname").append(partner.getFieldNameExtend())
					.append(" FCurrencyName ,\r\n");
			sql.append(" cc.fnumber fcurrencynumber \r\n");
		}
		sql.append(sqlFrom);
		sql.append("\r\n");

		sql.append(") t_out where KSQL_SEQ >= ? and KSQL_SEQ <= ? ");

		sql.append(" order by KSQL_SEQ ");
		SqlParameter2 parameters = new SqlParameter2();
		parameters.addValue(partner.getStartIndex() + 1);
		parameters.addValue(partner.getStartIndex()
				+ ((Integer) partner.getVariable("COUNT")).intValue());
		setParameters(partner, parameters);

		Collection cols = getDataFormSQL(ctx, partner, sql.toString());
		return cols;
	}

	protected void doSomethingBeforeExecuteSql(ReportPartner partner)
			throws BOSException, EASBizException {
		partner.setVariable("COUNT", new Integer(partner.getLineCount()));

		boolean isQtyAsst = partner.getParmQtyAsst();
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();

		this.isShowQuantity = getIsShowQuantity(partner);
		if ((condition.getOptionOnlyAsst())
				|| (condition.getAssisthgId() != null)) {
			this.isShowUnit = true;
		}
	}

	public boolean isShowQuantity() {
		return this.isShowQuantity;
	}

	public void setShowQuantity(boolean isShowQuantity) {
		this.isShowQuantity = isShowQuantity;
	}

	protected HashMap getStartAsstBalance(ReportPartner partner)
			throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		Context ctx = partner.getContext();

		String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
		String cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
		StringBuffer sqlSubQuery = new StringBuffer();
		List sqlParams = new ArrayList();

		sqlSubQuery.append("SELECT \r\n");
		sqlSubQuery
				.append("\tTP.FBeginDate,TP.FPeriodYear,TP.FPeriodNumber,\r\n");
		if (condition.isAllCurrency()) {
			sqlSubQuery.append("    TB_FCurrencyid , \r\n");
		}
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FBeginBalanceFor else TBA_FBeginBalanceFor end) FBeginBalanceFor,\r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FBeginBalanceLocal else TBA_FBeginBalanceLocal end) FBeginBalanceLocal, \r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FBeginBalanceRpt else TBA_FBeginBalanceRpt end) FBeginBalanceRpt, \r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FBeginQty else TBA_FBeginQty end) FBeginQty,\r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearDebitFor - TB_FDebitFor else TBA_FYearDebitFor - TBA_FDebitFor end) FYearDebitFor,\r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearDebitLocal - TB_FDebitLocal else TBA_FYearDebitLocal - TBA_FDebitLocal end) FYearDebitLocal,\r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearDebitRpt - TB_FDebitRpt else TBA_FYearDebitRpt - TBA_FDebitRpt end) FYearDebitRpt,\r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearDebitQty - TB_FDebitQty else TBA_FYearDebitQty - TBA_FDebitQty end) FYearDebitQty,\r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearCreditFor - TB_FCreditFor else TBA_FYearCreditFor - TBA_FCreditFor end) FYearCreditFor, \r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearCreditLocal - TB_FCreditLocal else TBA_FYearCreditLocal - TBA_FCreditLocal end) FYearCreditLocal, \r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearCreditRpt - TB_FCreditRpt else TBA_FYearCreditRpt - TBA_FCreditRpt end) FYearCreditRpt, \r\n");
		sqlSubQuery
				.append(" sum(case when TBA_FBALTYPE IS NULL then TB_FYearCreditQty - TB_FCreditQty else TBA_FYearCreditQty - TBA_FCreditQty end) FYearCreditQty \r\n");

		sqlSubQuery
				.append(" from T_BD_Period  TP left outer join (select tb.FPeriodID,  \r\n");
		sqlSubQuery
				.append(" TBA.FBALTYPE TBA_FBALTYPE,TB.FBeginBalanceFor TB_FBeginBalanceFor,TBA.FBeginBalanceFor TBA_FBeginBalanceFor,  \r\n");
		sqlSubQuery
				.append(" TB.FBeginBalanceLocal TB_FBeginBalanceLocal,TBA.FBeginBalanceLocal TBA_FBeginBalanceLocal, \r\n");
		sqlSubQuery
				.append(" TB.FBeginBalanceRpt TB_FBeginBalanceRpt,TBA.FBeginBalanceRpt TBA_FBeginBalanceRpt, \r\n");
		sqlSubQuery
				.append(" TB.FBeginQty TB_FBeginQty,TBA.FBeginQty TBA_FBeginQty, \r\n");
		sqlSubQuery
				.append(" TB.FYearDebitFor TB_FYearDebitFor, TB.FDebitFor TB_FDebitFor,TBA.FYearDebitFor TBA_FYearDebitFor, \r\n");
		sqlSubQuery
				.append(" TBA.FDebitFor TBA_FDebitFor,TB.FYearDebitLocal TB_FYearDebitLocal, TB.FDebitLocal TB_FDebitLocal, \r\n");
		sqlSubQuery
				.append(" TBA.FYearDebitLocal TBA_FYearDebitLocal, TBA.FDebitLocal TBA_FDebitLocal, \r\n");
		sqlSubQuery
				.append(" TB.FYearDebitRpt TB_FYearDebitRpt, TB.FDebitRpt TB_FDebitRpt,TBA.FYearDebitRpt TBA_FYearDebitRpt,  \r\n");
		sqlSubQuery
				.append(" TBA.FDebitRpt TBA_FDebitRpt,TB.FYearDebitQty TB_FYearDebitQty, TB.FDebitQty TB_FDebitQty, \r\n");
		sqlSubQuery
				.append(" TBA.FYearDebitQty TBA_FYearDebitQty, TBA.FDebitQty TBA_FDebitQty,TB.FYearCreditFor TB_FYearCreditFor, TB.FCreditFor TB_FCreditFor, \r\n");
		sqlSubQuery
				.append(" TBA.FYearCreditFor TBA_FYearCreditFor, TBA.FCreditFor TBA_FCreditFor, \r\n");
		sqlSubQuery
				.append(" TB.FYearCreditLocal TB_FYearCreditLocal, TB.FCreditLocal TB_FCreditLocal, \r\n");
		sqlSubQuery
				.append(" TBA.FYearCreditLocal TBA_FYearCreditLocal, TBA.FCreditLocal TBA_FCreditLocal, \r\n");
		sqlSubQuery
				.append(" TB.FYearCreditRpt TB_FYearCreditRpt, TB.FCreditRpt TB_FCreditRpt, \r\n");
		sqlSubQuery
				.append(" TBA.FYearCreditRpt TBA_FYearCreditRpt, TBA.FCreditRpt TBA_FCreditRpt, \r\n");
		sqlSubQuery
				.append(" TB.FYearCreditQty TB_FYearCreditQty, TB.FCreditQty TB_FCreditQty, \r\n");
		sqlSubQuery
				.append(" TBA.FYearCreditQty TBA_FYearCreditQty, TBA.FCreditQty TBA_FCreditQty \r\n");
		if (condition.isAllCurrency()) {
			sqlSubQuery.append("    ,TB.FCurrencyid TB_FCurrencyid \r\n");
		}
		sqlSubQuery.append(" from  ").append(
				getGeneralLedgerTableName(partner) + " TB \r\n");

		boolean isAfterCurrPeriod = false;
		if(partner.getCurrentPeriod()!=null)
		    isAfterCurrPeriod = partner.getCurrentPeriod().getNumber() < condition
				.getPeriodYearStart()
				* 100 + condition.getPeriodNumberStart();
		sqlSubQuery.append(" left join ")
				.append(
						isAfterCurrPeriod ? "V_GL_AssistBalance"
								: "T_GL_AssistBalance").append(" TBA \r\n");
		sqlSubQuery
				.append(" on TB.FBALTYPE=TBA.FBALTYPE AND TB.FORGUNITID=TBA.FORGUNITID AND TB.FACCOUNTID=TBA.FACCOUNTID ");
		sqlSubQuery
				.append(" AND TB.FCURRENCYID=TBA.FCURRENCYID AND TB.FPERIODID=TBA.FPERIODID \r\n");
		sqlSubQuery
				.append(" left join T_BD_AssistantHG HG ON TBA.FAssistGrpID = HG.FID \r\n");
		sqlSubQuery
				.append(" inner join t_bd_accountview ta on tb.faccountid=ta.fid \r\n");

		sqlSubQuery.append("WHERE 1 = 1 \r\n");

		if (condition.isAllCurrency()) {
			sqlSubQuery
					.append(" AND TB.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TB.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC'  \t\t\r\n");
		}

		SqlParams AsstWhereSp = new SqlParams();
		sqlSubQuery.append(" and ");
		try {
			sqlSubQuery.append(RptServerUtil.GetAsstSqlWithoutPermissionFilter(
					ctx, condition.getTableData(), condition
							.getOptionAsstGroup(), true, "TBA.FAssistGrpID",
					AsstWhereSp));
		} catch (ParseException e) {
			throw new BOSException(e);
		}

		Object[] Asst = AsstWhereSp.getParams();
		for (int i = 0; i < Asst.length; i++) {
			sqlParams.add(Asst[i].toString());
		}

		Map actTypes = new HashMap();
		boolean needHandlePerm = false;
		List tblData = condition.getTableData();
		int i = 0;
		for (int size = tblData.size(); i < size; i++) {
			AsstactTypeEntity at = (AsstactTypeEntity) tblData.get(i);
			actTypes.put(at.getId().toString(), at);
			if (at.isEnabledPermissionLimit()) {
				needHandlePerm = true;
			}

		}

		if (needHandlePerm) {
			Map acct2ActType = new HashMap();
			Set intersectActType = new HashSet();
			StringBuffer sqlAsstPerm = new StringBuffer();
			sqlAsstPerm.append("select a.fid accountid,d.fid acttypeid \r\n");
			sqlAsstPerm.append("from t_bd_accountview a \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstAccount b on a.fcaa=b.fid \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstActGroupDetail c on c.FAsstAccountID=b.fid \r\n");
			sqlAsstPerm
					.append("inner join T_BD_AsstActType d on d.fid=c.FAsstActTypeID \r\n");
			sqlAsstPerm
					.append("where a.fcompanyid=? and a.faccounttableid=? and a.fisleaf=1 and a.flongnumber like ? \r\n");
			IRowSet rsActType = DbUtil
					.executeQuery(partner.getContext(), sqlAsstPerm.toString(),
							new Object[] {
									partner.getCompanyId(),
									partner.getAccountTableId(),
									partner.getAccountViewInfo()
											.getLongNumber()
											+ "%" });
			try {
				while (rsActType.next()) {
					List list = (List) acct2ActType.get(rsActType
							.getString("accountid"));
					if (list == null) {
						list = new ArrayList();
						acct2ActType
								.put(rsActType.getString("accountid"), list);
					}
					list.add(rsActType.getString("acttypeid"));
					intersectActType.add(rsActType.getString("acttypeid"));
				}
			} catch (SQLException sqle) {
				throw new SQLDataException(sqle);
			}

			Map asst2PermSql = new HashMap();
			IPermissionServiceProvider provider = PermissionServiceProviderFactory
					.getLocalInstance(ctx);
			IPermission permission = PermissionFactory.getLocalInstance(ctx);
			Iterator it = intersectActType.iterator();
			while (it.hasNext()) {
				String actTypeId = (String) it.next();
				AsstactTypeEntity at = (AsstactTypeEntity) actTypes
						.get(actTypeId);
				if (at != null) {
					if (at.isEnabledPermissionLimit()) {
						String asstRule = provider.getPermissionRule(
								new ObjectUuidPK(userId),
								new ObjectUuidPK(cuId), at.getPermissionItem());
						if (!StringUtils.isEmpty(asstRule)) {
							String permSql = permission.getQueryPermissionSQL(
									new ObjectUuidPK(userId), new ObjectUuidPK(
											cuId), at.getPermissionItem());
							asst2PermSql.put(at.getId().toString(),
									new Object[] { at, permSql });
						}
					}
				}

			}

			if (asst2PermSql.size() > 0) {
				StringBuffer collectAsstPermSql = new StringBuffer();
				StringBuffer acctAsstPermSql = new StringBuffer();
				Iterator itKey = acct2ActType.keySet().iterator();
				while (itKey.hasNext()) {
					String accountId = (String) itKey.next();
					List list = (List) acct2ActType.get(accountId);
					acctAsstPermSql.setLength(0);
					i = 0;
					for (int size = list.size(); i < size; i++) {
						Object[] obj = (Object[]) asst2PermSql.get(list.get(i));
						if (obj != null) {
							if (acctAsstPermSql.length() > 0) {
								acctAsstPermSql.append(" and ");
							}
							acctAsstPermSql.append("HG.").append(
									((AsstactTypeEntity) obj[0]).getHgField())
									.append(" in (").append(obj[1]).append(")");
						}

					}

					if (acctAsstPermSql.length() > 0) {
						acctAsstPermSql.append(" and TB.FAccountID='").append(
								accountId).append("'");
						if (collectAsstPermSql.length() > 0) {
							collectAsstPermSql.append(" or ");
						}
						collectAsstPermSql.append("(").append(acctAsstPermSql)
								.append(")");
					}
				}
				if (collectAsstPermSql.length() > 0) {
					sqlSubQuery.append(" and (").append(collectAsstPermSql)
							.append(")");
				}
			}
		}

		sqlSubQuery.append(" AND TB.FBalType = ? \r\n");
		sqlSubQuery.append(" AND TB.FOrgUnitID = ?  \r\n");
		if (partner.getAccountViewInfo().isIsLeaf())
			sqlSubQuery.append(" AND TB.FAccountID = ?  \r\n");
		else {
			sqlSubQuery.append("\tAND TA.fisleaf = 1 and TA.FNumber like ? ");
		}
		if (!condition.isAllCurrency()) {
			sqlSubQuery.append(" AND TB.FCurrencyID = ?  \r\n");
		}

		sqlSubQuery
				.append("\r\n ) tmp ON Tmp.FPeriodID = TP.FID where TP.FTypeID = ? and \r\n");
		int[] periodRange = {
				condition.getPeriodYearStart() * 100
						+ condition.getPeriodNumberStart(),
				condition.getPeriodYearEnd() * 100
						+ condition.getPeriodNumberEnd() };
		sqlSubQuery.append(SQLUtil
				.getPeriodCondition("TP.FNumber", periodRange));
		sqlSubQuery
				.append("\r\n group by FBeginDate,TP.FPeriodYear,TP.FPeriodNumber \r\n");
		if (condition.isAllCurrency()) {
			sqlSubQuery.append(", TB_FCurrencyid  \r\n");
		}
		sqlSubQuery.append(" order by fbegindate");
		sqlParams.add(new Integer(condition.getOptionPosting() ? 1 : 5));
		sqlParams.add(partner.getCompanyId());
		if (partner.getAccountViewInfo().isIsLeaf())
			sqlParams.add(condition.getAccountId());
		else {
			sqlParams.add(condition.getAccountCodeStart() + "%");
		}
		if (!condition.isAllCurrency()) {
			sqlParams.add(condition.getCurrencyID());
		}
		sqlParams.add(partner.getCompany().getAccountPeriodType().getId()
				.toString());

		HashMap map = new HashMap();
		HashMap curMap = new HashMap();
		try {
			IRowSet rs = DbUtil.executeQuery(partner.getContext(), sqlSubQuery
					.toString(), sqlParams.toArray());
			int ci = 0;
			if (rs.next()) {
				do {
					map.put("FBeginDate", rs.getTimestamp("FBeginDate"));
					map.put("FPeriodYear",
							new Integer(rs.getInt("FPeriodYear")));
					map.put("FPeriodNumber", new Integer(rs
							.getInt("FPeriodNumber")));
					if (condition.isAllCurrency()) {
						map.put("FCurrencyid", rs.getString("TB_FCurrencyid"));
					}
					BigDecimal val = rs.getBigDecimal("FBeginBalanceFor");
					map.put("FBeginBalanceFor", val == null ? ZERO : val);
					val = rs.getBigDecimal("FBeginBalanceLocal");
					map.put("FBeginBalanceLocal", val == null ? ZERO : val);
					val = rs.getBigDecimal("FBeginBalanceRpt");
					map.put("FBeginBalanceRpt", val == null ? ZERO : val);
					val = rs.getBigDecimal("FBeginQty");
					map.put("FBeginQty", val == null ? ZERO : val
							.setScale(6, 4));

					val = rs.getBigDecimal("FYearDebitFor");
					map.put("FYearDebitFor", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearCreditFor");
					map.put("FYearCreditFor", val == null ? ZERO : val);

					val = rs.getBigDecimal("FYearDebitLocal");
					map.put("FYearDebitLocal", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearCreditLocal");
					map.put("FYearCreditLocal", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearDebitRpt");
					map.put("FYearDebitRpt", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearCreditRpt");
					map.put("FYearCreditRpt", val == null ? ZERO : val);
					val = rs.getBigDecimal("FYearDebitQty");
					map.put("FYearDebitQty", val == null ? ZERO : val.setScale(
							6, 4));
					val = rs.getBigDecimal("FYearCreditQty");
					map.put("FYearCreditQty", val == null ? ZERO : val
							.setScale(6, 4));

					map.put("FDebitAssistPrice", ZERO);
					map.put("FDebitAssistQty", ZERO);
					map.put("FCreditAssistPrice", ZERO);
					map.put("FCreditAssistQty", ZERO);

					if (condition.isAllCurrency()) {
						curMap.put(new Integer(ci++), map);
						map = new HashMap();
					}
					if (!rs.next())
						break;
				} while (condition.isAllCurrency());
			} else {
				String sql = "select FBeginDate from t_bd_period where FTypeID=? and fperiodyear=? and fperiodnumber=?";
				rs = DbUtil
						.executeQuery(partner.getContext(), sql, new Object[] {
								partner.getCompany().getAccountPeriodType()
										.getId().toString(),
								new Integer(condition.getPeriodYearStart()),
								new Integer(condition.getPeriodNumberStart()) });

				rs.next();
				map.put("FBeginDate", rs.getTimestamp("FBeginDate"));
				map.put("FPeriodYear", new Integer(condition
						.getPeriodYearStart()));
				map.put("FPeriodNumber", new Integer(condition
						.getPeriodNumberStart()));
				map.put("FBeginBalanceFor", ZERO);
				map.put("FBeginBalanceLocal", ZERO);
				map.put("FBeginBalanceRpt", ZERO);
				map.put("FBeginQty", ZERO);

				map.put("FYearDebitFor", ZERO);
				map.put("FYearCreditFor", ZERO);

				map.put("FYearDebitLocal", ZERO);
				map.put("FYearCreditLocal", ZERO);
				map.put("FYearDebitRpt", ZERO);
				map.put("FYearCreditRpt", ZERO);
				map.put("FYearDebitQty", ZERO);
				map.put("FYearCreditQty", ZERO);

				map.put("FDebitAssistPrice", ZERO);
				map.put("FDebitAssistQty", ZERO);
				map.put("FCreditAssistPrice", ZERO);
				map.put("FCreditAssistQty", ZERO);
			}
		} catch (SQLException sqle) {
			sqle.setNextException(new SQLException("The query sql is:"
					+ sqlSubQuery.toString()));
			throw new SQLDataException(sqle);
		}

		if (condition.isAllCurrency()) {
			return curMap;
		}
		return map;
	}

}