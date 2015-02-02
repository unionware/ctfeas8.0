/*
 * @(#)com.kingdee.eas.cp.base.web.utils.CommonUtil.java
 *
 * ���������������޹�˾��Ȩ����.
 */
package com.kingdee.eas.cp.bc.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.person.PersonFacadeFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillCollection;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionReqBillCollection;
import com.kingdee.eas.cp.bc.EvectionReqBillFactory;
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillCollection;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillInfo;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * ������<��������>
 * 
 * @author xianglu date: 2013-4-15
 *         <p>
 * @version EAS6.0SP
 * @see <�����>
 */
public class CommonDataUtil {

	static Map map = null;

	//��ȡ����Դ�����б�
	public static List getRecordsOfCheckSourceBill(Context ctx,
			BizCollBillTypeEnum billtype, Map map) throws BOSException,
			ParserException {
		List listModel = new ArrayList();
		Set set=null;
		if(map.get("relationIds")!=null){
			set=BizCollUtil.getBillIdByRelationId(ctx, billtype, (String)map.get("relationIds"));
			if(set!=null && set.size()>0){
				map.put("billIds", set);
			}else{
				return listModel;
			}
		}
		map.put("isCheckSource", true);
		AbstractObjectCollection con=null;
		if (billtype.equals(BizCollBillTypeEnum.DAILY_LOAN)) {
			con = DailyLoanBillEntryFactory
			.getLocalInstance(ctx).getDailyLoanBillEntryCollection(
					getLoanBillEntityView(ctx, false, map));
			
		} else if (billtype.equals(BizCollBillTypeEnum.EVECTION_LOAN)) {
			con = EvectionLoanBillEntryFactory
			.getLocalInstance(ctx).getEvectionLoanBillEntryCollection(
					getLoanBillEntityView(ctx, true, map));
			
		} else if (billtype.equals(BizCollBillTypeEnum.OTHER_EXPENSE)) {
			con = OtherExpenseBillEntryFactory
			.getLocalInstance(ctx).getOtherExpenseBillEntryCollection(
					getReqBillEntityView(ctx, false, map));
		} 
		for(int i=0,size=con.size();i<size;i++){
			contructCheckModel((CoreBillEntryBaseInfo)con.getObject(i),listModel);
		}
		return listModel;

	}
	//��ȡ���������б�
	public static List getRecordsOfCreateToBills(Context ctx,
			BizCollBillTypeEnum billtype, Map map) throws BOSException,
			ParserException {
		List listModel = new ArrayList();
		if (billtype.equals(BizCollBillTypeEnum.DAILY_LOAN)) {
			getReqBillRecords(ctx, false, listModel, map);
		} else if (billtype.equals(BizCollBillTypeEnum.EVECTION_LOAN)) {
			getReqBillRecords(ctx, true, listModel, map);
		} else if (billtype.equals(BizCollBillTypeEnum.BIZ_ACCOUNT)) {
			getReqBillRecords(ctx, false, listModel, map);
			getLoanBillRecords(ctx, false, listModel, map);
		} else if (billtype.equals(BizCollBillTypeEnum.TRAVEL_ACCOUNT)) {
			getReqBillRecords(ctx, true, listModel, map);
			getLoanBillRecords(ctx, true, listModel, map);
		}
		return listModel;

	}

	private static FilterInfo getPersonAndOrgFilter(Context ctx, Map map)
			throws BOSException {
		boolean isDepartmentCtrl = map.get("CP014") == null ? false
				: (new Boolean(map.get("CP014").toString())).booleanValue();
		FilterInfo tempFilter = new FilterInfo();
		if(map.get("applier")!=null){
			tempFilter.getFilterItems().add(
					new FilterItemInfo("applier.id", map.get("applier").toString(),
							CompareType.EQUALS));
		}else{
			tempFilter.getFilterItems().add(
					new FilterItemInfo("biller.id", ctx.getCaller().toString(),
							CompareType.EQUALS));
		}
		
		if (isDepartmentCtrl) {
			UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
			if (userInfo != null && userInfo.getPerson() != null) {
				AdminOrgUnitCollection adminOrgUnitCollection;
				try {
					adminOrgUnitCollection = PersonFacadeFactory
							.getLocalInstance(ctx).getAdminOrgUnitByPerson(
									userInfo.getPerson().getId());
					if (adminOrgUnitCollection != null
							&& adminOrgUnitCollection.size() > 0) {
						String orgNumber = parseLongNumber(adminOrgUnitCollection
								.get(0).getLongNumber());
						if (orgNumber != null) {
							tempFilter.getFilterItems().add(
									new FilterItemInfo("orgUnit.number",
											orgNumber, CompareType.EQUALS));
							tempFilter.setMaskString("#0 or #1");
						}
					}
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tempFilter;
	}

	private static FilterInfo getBillIdSetFilter(Context ctx,
			BOSObjectType bosType, FilterInfo filter,Map map) throws BOSException {
		FilterInfo tempFilter = null;
		IDynamicObject iDynamicObject = null;
		iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));
		IObjectPK[] idArray = iDynamicObject.getPKList(bosType, filter, sorter);
		Set idSet = new HashSet();
		for (int i = 0; i < idArray.length; i++) {
			idSet.add(idArray[i].toString());
		}
		if (BizCollUtil.evectionReqBosType.equals(bosType)) {
			try {
				idSet = BizCollUtil.getForBillIdSet(ctx,idSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(BizCollUtil.isLoanBill(bosType)){
			idSet=CommonUtilFacadeFactory.getLocalInstance(ctx).getCheckDestBillDoneForBillIdSet(idSet);
		}
		if (idSet.size() > 0) {
			String key="id";
			tempFilter = new FilterInfo();
			if(map.get("isCheckSource")!=null){
				key="bill.id";
				tempFilter.getFilterItems().add(
						new FilterItemInfo("amountBalance", new BigDecimal("0.00"),CompareType.GREATER));
				if(map.get("billNumber")!=null){
					tempFilter.getFilterItems().add(
							new FilterItemInfo("bill.number", "%"+map.get("billNumber")+"%",CompareType.LIKE));
				}
			}
			
			tempFilter.getFilterItems().add(
					new FilterItemInfo(key, idSet, CompareType.INCLUDE));
			if(map.get("selectedIds")!=null && ((Set)map.get("selectedIds")).size() > 0){
				tempFilter.getFilterItems().add(
						new FilterItemInfo("id", (Set)map.get("selectedIds"), CompareType.NOTINCLUDE));
			}
			if(map.get("billIds")!=null){
				tempFilter.getFilterItems().add(
						new FilterItemInfo(key, (Set)map.get("billIds"), CompareType.INCLUDE));
			}
		}
		return tempFilter;
	}

	private static FilterInfo getAmountBalanceFilter() throws BOSException {
		FilterInfo tempFilter = new FilterInfo();
		tempFilter.getFilterItems().add(
				new FilterItemInfo("amountBalance", new BigDecimal("0.00"),
						CompareType.GREATER));
		
		return tempFilter;
	}

	private static EntityViewInfo getLoanBillEntityView(Context ctx,
			boolean isEvection, Map map) throws BOSException, ParserException {
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		if(map.get("isCheckSource")!=null){
			entityViewInfo.setSelector(getCheckSelectors());
		}else{
			entityViewInfo.setSelector(getCreateSelectors());
		}
		FilterInfo filter = new FilterInfo();
		if(map.get("billid")!=null){
			filter.getFilterItems().add(
					new FilterItemInfo("amountBalance", new BigDecimal("0.00"),CompareType.GREATER));
			filter.getFilterItems().add(
					new FilterItemInfo("bill.id", map.get("billid")));
			entityViewInfo.setFilter(filter);
		}else{
			BOSObjectType bosType = null;
			boolean isNeedRelaFi = map.get("CP002") == null ? false : (new Boolean(
					map.get("CP002").toString())).booleanValue();
			boolean isNeedRelaCas = map.get("CP008") == null ? false : (new Boolean(
					map.get("CP008").toString())).booleanValue();
			FilterInfo tempFilter = new FilterInfo();
			filter = getPersonAndOrgFilter(ctx, map);

			if (isEvection) {
				bosType = new EvectionLoanBillInfo().getBOSType();
			} else {
				bosType = new DailyLoanBillInfo().getBOSType();
			}

			filter.mergeFilter(getAmountBalanceFilter(), "and");
			if (isNeedRelaFi ||isNeedRelaCas) {
				tempFilter.getFilterItems().add(
						new FilterItemInfo("loanState", "N", CompareType.EQUALS));
				tempFilter.getFilterItems().add(
						new FilterItemInfo("state", Integer
								.valueOf(StateEnum.ALREADYPAYMENT_VALUE)));
			} else {
				tempFilter.getFilterItems().add(
						new FilterItemInfo("state", Integer
								.valueOf(StateEnum.CHECKED_VALUE)));
			}
			filter.mergeFilter(tempFilter, "and");
			FilterInfo entryfilter = getBillIdSetFilter(ctx, bosType, filter,map);
			if (entryfilter != null && entryfilter.getFilterItems().size() > 0) {
				
				entityViewInfo.setFilter(entryfilter);
			} else {
				if(map.get("isCheckSource")!=null){
					entityViewInfo.setFilter("bill.state=-1");
				}else{
					entityViewInfo.setFilter("state=-1");
				}
				
			}
		}
		
		entityViewInfo.setSorter(getSorter(map.get("isCheckSource")));
		return entityViewInfo;
	}

	private static EntityViewInfo getReqBillEntityView(Context ctx,
			boolean isEvection, Map map) throws BOSException, ParserException {
		BOSObjectType bosType = null;
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		if(map.get("isCheckSource")!=null){
			entityViewInfo.setSelector(getCheckSelectors());
		}else{
			entityViewInfo.setSelector(getCreateSelectors());
		}
		FilterInfo filter = new FilterInfo();
		if(map.get("billid")!=null){
			filter.getFilterItems().add(
					new FilterItemInfo("amountBalance", new BigDecimal("0.00"),CompareType.GREATER));
			filter.getFilterItems().add(
					new FilterItemInfo("bill.id", map.get("billid")));
			entityViewInfo.setFilter(filter);
		}else{
			FilterInfo tempFilter = new FilterInfo();
			// ��ѯ״̬Ϊ���ͨ��ĵ���
			filter.getFilterItems().add(
					new FilterItemInfo("state", Integer
							.valueOf(StateEnum.CHECKED_VALUE)));
			filter.mergeFilter(getPersonAndOrgFilter(ctx, map), "and");

			if (isEvection) {
				bosType = new EvectionReqBillInfo().getBOSType();
				tempFilter.getFilterItems().add(
						new FilterItemInfo("amount", new BigDecimal("0.00"),
								CompareType.GREATER));
				filter.mergeFilter(tempFilter, "and");
			} else {
				bosType = new OtherExpenseBillInfo().getBOSType();
				filter.mergeFilter(getAmountBalanceFilter(), "and");
			}
			FilterInfo entryfilter = getBillIdSetFilter(ctx, bosType, filter,map);
			if (entryfilter != null && entryfilter.getFilterItems().size() > 0) {
				entityViewInfo.setFilter(entryfilter);
			} else {
				if(map.get("isCheckSource")!=null){
					entityViewInfo.setFilter("bill.state=-1");
				}else{
					entityViewInfo.setFilter("state=-1");
				}
				
			}
		}
		entityViewInfo.setSorter(getSorter(map.get("isCheckSource")));
		return entityViewInfo;
	}

	public static SorterItemCollection getSorter(Object isCheckSource) {
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item =null;
		if(isCheckSource!=null){
			item = new SorterItemInfo("bill.bizreqdate");
		}else{
			item = new SorterItemInfo("bizreqdate");
		}
		item.setSortType(SortType.DESCEND);
		sorter.add(item);
		if(isCheckSource!=null){
			item = new SorterItemInfo("bill.id");
			sorter.add(item);
		}
		
		return sorter;
	}

	public static SelectorItemCollection getCreateSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("bizReqDate"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("amountApproved"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("cause"));
		sic.add(new SelectorItemInfo("amountBalance"));
		sic.add(new SelectorItemInfo("operationType.name"));
		
		return sic;
	}
	public static SelectorItemCollection getCheckSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("bill.id"));
		sic.add(new SelectorItemInfo("bill.createTime"));
		sic.add(new SelectorItemInfo("bill.number"));
		sic.add(new SelectorItemInfo("bill.amountApproved"));
		sic.add(new SelectorItemInfo("bill.cause"));
		sic.add(new SelectorItemInfo("bill.operationType.name"));
		sic.add(new SelectorItemInfo("bill.sourcebillid"));
		sic.add(new SelectorItemInfo("bill.billTypeCode"));
		sic.add(new SelectorItemInfo("expenseType.name"));
		sic.add(new SelectorItemInfo("amountBalance"));
		sic.add(new SelectorItemInfo("amountBalanceOri"));
		
		sic.add(new SelectorItemInfo("exchangeRate"));

		sic.add(new SelectorItemInfo("exchangeRatePrecision"));
		sic.add(new SelectorItemInfo("convertMode"));
		sic.add(new SelectorItemInfo("amountUsed"));
		sic.add(new SelectorItemInfo("currencyType.id"));
		sic.add(new SelectorItemInfo("currencyType.name"));
		sic.add(new SelectorItemInfo("expenseType.id"));
		sic.add(new SelectorItemInfo("bill.costedDept.id"));
		sic.add(new SelectorItemInfo("bill.costedDept.name"));
		
		sic.add(new SelectorItemInfo("costDept.id"));
		sic.add(new SelectorItemInfo("costDept.name"));
		
		sic.add(new SelectorItemInfo("costedDept.id"));
		sic.add(new SelectorItemInfo("costedDept.name"));

		return sic;
	}

	private static List getReqBillRecords(Context ctx, boolean isEvection,
			List listModel, Map map) throws BOSException, ParserException {
		if (isEvection) {
			EvectionReqBillCollection con = EvectionReqBillFactory
					.getLocalInstance(ctx).getEvectionReqBillCollection(
							getReqBillEntityView(ctx, isEvection, map));
			for (int i = 0, size = con.size(); i < size; i++) {
				EvectionReqBillInfo info = con.get(i);
				contructModel(info, listModel, BizCollBillTypeEnum.EVECTION_REQ);
			}
		} else {
			OtherExpenseBillCollection con = OtherExpenseBillFactory
					.getLocalInstance(ctx).getOtherExpenseBillCollection(
							getReqBillEntityView(ctx, isEvection, map));
			for (int i = 0, size = con.size(); i < size; i++) {
				OtherExpenseBillInfo info = con.get(i);
				contructModel(info, listModel,
						BizCollBillTypeEnum.OTHER_EXPENSE);
			}
		}
		return listModel;
	}

	private static List getLoanBillRecords(Context ctx, boolean isEvection,
			List listModel, Map map) throws BOSException, ParserException {
		PersonInfo personInfo = ContextUtil.getCurrentUserInfo(ctx).getPerson();
		if (isEvection) {
			EvectionLoanBillCollection con = EvectionLoanBillFactory
					.getLocalInstance(ctx).getEvectionLoanBillCollection(
							getLoanBillEntityView(ctx, isEvection, map));
			for (int i = 0, size = con.size(); i < size; i++) {
				EvectionLoanBillInfo info = con.get(i);
				contructModel(info, listModel,
						BizCollBillTypeEnum.EVECTION_LOAN);
			}
		} else {
			DailyLoanBillCollection con = DailyLoanBillFactory
					.getLocalInstance(ctx).getDailyLoanBillCollection(
							getLoanBillEntityView(ctx, isEvection, map));
			for (int i = 0, size = con.size(); i < size; i++) {
				DailyLoanBillInfo info = con.get(i);
				contructModel(info, listModel, BizCollBillTypeEnum.DAILY_LOAN);
			}
		}
		return listModel;
	}

	private static void contructModel(BizCollBillBaseInfo info, List listModel,
			BizCollBillTypeEnum billtype) {
		if (info != null) {
			map = new HashMap();
			map.put("id", info.getId().toString());
			map.put("bizReqDate", info.getBizReqDate());
			map.put("number", info.getNumber());
			map.put("name", info.getName());
			map.put("amount", info.getAmount());
			map.put("amountApproved", info.getAmountApproved());
			map.put("cause", info.getCause());
			map.put("amountBalance", info.getAmountBalance());
			if(info.getOperationType()!=null){
				map.put("operationType", info.getOperationType().getName());
			}else{
				map.put("operationType", "");
			}
			map.put("billtype", billtype);
			listModel.add(map);
		}
	}
	private static void contructCheckModel(CoreBillEntryBaseInfo info, List listModel) {
		if (info != null) {
			map = new HashMap();
			map.put("id", info.getObjectValue("bill").get("id").toString());
			map.put("bizReqDate", info.getObjectValue("bill").getTimestamp("createTime"));
			map.put("number", info.getObjectValue("bill").get("number"));
			map.put("amountBalanceOri", info.getBigDecimal("amountBalanceOri"));
			map.put("exchangeRate", info.getBigDecimal("exchangeRate"));
			map.put("convertMode", info.get("convertMode"));
			map.put("exchangeRatePrecision", info.get("exchangeRatePrecision"));
			map.put("currencyTypeId", info.getObjectValue("currencyType").get("id"));
			map.put("currencyTypeName", info.getObjectValue("currencyType").get("name"));



			map.put("cause", info.getObjectValue("bill").get("cause"));
			if(info.getObjectValue("bill").getObjectValue("operationType")!=null){
				map.put("operationType", info.getObjectValue("bill").getObjectValue("operationType").get("name"));
			}else{
				map.put("operationType", "");
			}
			if (info.getObjectValue("bill").get("billTypeCode") != null) {
				map.put("billTypeCode", info.getObjectValue("bill").get("billTypeCode"));
			} else {
				map.put("billTypeCode", "");
			}
			
			map.put("amountBalance", info.getBigDecimal("amountBalance"));
			map.put("amountUsed", info.getBigDecimal("amountUsed"));
			if(info.getObjectValue("expenseType")!=null){
				map.put("sourceBillExpenseTypeId", info.getObjectValue("expenseType").get("id"));

				map.put("expenseType", info.getObjectValue("expenseType").get("name"));
			}else{
				map.put("expenseType", "");
				map.put("sourceBillExpenseTypeId", "");
			}
			
			IObjectValue  costOrg = info.getObjectValue("costedDept");
			if(costOrg==null){
				costOrg = info.getObjectValue("costDept");
			}
			if(costOrg!=null){
				map.put("sourceBillCostCenterId", costOrg.get("id"));

				map.put("sourceBillCostCenterName", costOrg.get("name"));
			}else{
				map.put("sourceBillCostCenterId", "");
				map.put("sourceBillCostCenterName", "");

			}
			map.put("entryid", info.get("id"));
			if(info.getObjectValue("bill").get("sourcebillid")!=null){
				map.put("hasSourceBill", "true");
			}else{
				map.put("hasSourceBill", "false");
			}
			listModel.add(map);
		}
	}
	public static String parseLongNumber(String longNumber) {
		String[] numbers = StringUtils.split(longNumber, "!");
		if (numbers.length != 0) {
			return numbers[numbers.length - 1];
		} else {
			return null;
		}

	}
}