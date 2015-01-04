package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryFactory;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryInfo;
import com.kingdee.eas.cp.bc.ExcessSetCollection;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class TravelAccountBillControllerBeanEx extends
		TravelAccountBillControllerBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5304037025822979132L;

	public TravelAccountBillControllerBeanEx() {
	}
	
	@Override
	protected void checkBill(Context ctx, IObjectValue info)
			throws BOSException, EASBizException {
		super.checkBill(ctx, info);
		
		//checkEntry(ctx,info);
	}
	
	protected void checkEntry(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		TravelAccountBillInfo info = (TravelAccountBillInfo)model;
		TravelAccountBillEntryInfo entry = null;
		TravelAccountBillLoanCheckEntryInfo loan = null;
		EvectionReqBillEntryInfo otherEntry = null;
		BigDecimal aboveQuota = BigDecimal.ONE;
		BigDecimal aboveQuota1 = BigDecimal.ZERO;
		DateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(info.getBizDate()));
		ExcessSetCollection excesssetinfos = getExcessSetInfos(ctx, year);
		
		BigDecimal checkAmount = null;
		BigDecimal subAmt = null;
		BigDecimal loanAmt = null;
		for (int i = 0; i < info.getEntries().size(); i++) {
			entry = info.getEntries().get(i);
			aboveQuota = BigDecimal.ONE;
			
			if(entry.getSourceBillEntryID()!=null){
				ProjectInfo project = entry.getProject();
				String expTypeID = entry.getExpenseType().getId().toString();
				String costOrgID = entry.getCostCenter().getId().toString();
				BigDecimal amt = entry.getAmount();
				
				ProjectInfo project1 = null;
				String expType1 = null;
				String costOrg1 = null;
				
				checkAmount = null;
				subAmt = amt;
				loanAmt = BigDecimal.ZERO;
				
				for(int j=0;j<info.getLoanCheckEntries().size();j++){
					loan = info.getLoanCheckEntries().get(j);
					project1 = loan.getProject();
					expType1 = loan.getSourceBillExpenseTypeId();
					costOrg1 = loan.getSourceBillCostCenterId();
					checkAmount = loan.getCheckAmount();
					if(project1==project && expType1==expTypeID && costOrgID==costOrg1){
						subAmt = subAmt.subtract(checkAmount);
						
						loanAmt = loanAmt.add(checkAmount);
					}
				}
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				if ("DE853384".equalsIgnoreCase(BOSUuid.read(entry.getSourceBillID()).getType().toString())) {// 出差申请单分录
					otherEntry = EvectionReqBillEntryFactory.getLocalInstance(ctx).getEvectionReqBillEntryInfo(new ObjectUuidPK(entry.getSourceBillEntryID()),selector);

					aboveQuota1 = getAboveQuota(ctx, project==null?null:project.getId().toString(), expTypeID, costOrgID, excesssetinfos);
					aboveQuota = aboveQuota.add(aboveQuota1.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP, 2));

					//校验是否超出了费用申请额范围
					if(subAmt.compareTo(otherEntry.getAmount().multiply(aboveQuota).subtract(otherEntry.getAmountUsed()))>0){
						 throw new ExpAccException(new NumericExceptionSubItem("1111","分录"+entry.getSeq()+"申请金额本位币（扣减冲借款）（"+subAmt+"）已经超过出差申请单本位币申请金额（"+otherEntry.getAmount().multiply(aboveQuota).subtract(otherEntry.getAmountUsed())+"）允许的超额比例（"+aboveQuota1+"%）！"));
					}

					/*//校验是否超出了预算额范围
					if(otherEntry.getBudgetAmount().compareTo(otherEntry.getAmount().subtract(otherEntry.getAmountUsed()).subtract(entry.getAmount()).subtract(loanAmt))>0){
						 throw new ExpAccException(new NumericExceptionSubItem("1111","本次申请金额超出预算额范围"));
					}*/
				}
			}
		}
	}

	/**
	 * 获取超额比例
	 * 
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getAboveQuota(Context ctx, String projectid,
			String expenseTypeid, String costcenterid,
			ExcessSetCollection excesssetInfos) throws BOSException {
		BigDecimal AboveRate = new BigDecimal("0.00");
		String setprojectid = null;
		String setexpenseTypeid = null;
		String setcostcenterid = null;
		boolean flag = false;

		if (projectid!=null && isExistProject(excesssetInfos, projectid)) {
			if (excesssetInfos != null && excesssetInfos.size() > 0) {
				for (int i = 0; i < excesssetInfos.size(); i++) {
					flag = false;
					if(excesssetInfos.get(i).getProject()!=null){
						setprojectid = excesssetInfos.get(i).getProject().getId()
								.toString();
						setexpenseTypeid = excesssetInfos.get(i).getExpenseType()
								.getId().toString();

						if (projectid.equalsIgnoreCase(setprojectid) && expenseTypeid.equalsIgnoreCase(setexpenseTypeid)) {
							for (int j = 0; j < excesssetInfos.get(i).getEntry().size(); i++) {
								setcostcenterid = excesssetInfos.get(i).getEntry().get(
										j).getCostCenter().getId().toString();
								if (costcenterid.equalsIgnoreCase(setcostcenterid)) {
									AboveRate = excesssetInfos.get(i).getEntry().get(j).getRate();
									flag = true;
									break;
								}
							}
							if(!flag){
								AboveRate = excesssetInfos.get(i).getRate();
								break;
							}
						}
					}
				}
			}
		} else {
			if (excesssetInfos != null && excesssetInfos.size() > 0) {
				for (int i = 0; i < excesssetInfos.size(); i++) {
					flag = false;
					setexpenseTypeid = excesssetInfos.get(i).getExpenseType().getId().toString();
					if (excesssetInfos.get(i).getProject()==null && expenseTypeid.equalsIgnoreCase(setexpenseTypeid)) {
						for (int j = 0; j < excesssetInfos.get(i).getEntry().size(); i++) {
							setcostcenterid = excesssetInfos.get(i).getEntry().get(j).getCostCenter().getId().toString();
							if (costcenterid.equalsIgnoreCase(setcostcenterid)) {
								AboveRate = excesssetInfos.get(i).getEntry().get(j).getRate();
								flag = true;
								break;
							}
						}
						if(!flag){
							AboveRate = excesssetInfos.get(i).getRate();
							break;
						}
					}
				}
			}
		}
		return AboveRate;
	}

	/**
	 * 判断当前项目是否存在于超额比例设置中
	 * 
	 * @param excesssetInfos
	 * @param projectid
	 * @return
	 */
	private boolean isExistProject(ExcessSetCollection excesssetInfos,
			String projectid) {
		if (excesssetInfos != null && excesssetInfos.size() > 0) {
			for (int i = 0; i < excesssetInfos.size(); i++) {
				if(excesssetInfos.get(i).getProject()!=null){
					String setProjectid = excesssetInfos.get(i).getProject()
							.getId().toString();
					if (setProjectid.equalsIgnoreCase(projectid)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前报销对应的年份的超额设置
	 * 
	 * @param ctx
	 * @param year
	 * @return ExcessSetCollection
	 * @throws BOSException
	 */
	private ExcessSetCollection getExcessSetInfos(Context ctx, int year)
			throws BOSException {
		// 
		EntityViewInfo viewInfo = new EntityViewInfo();
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add(new SelectorItemInfo("id"));
        selector.add(new SelectorItemInfo("*"));
        selector.add(new SelectorItemInfo("entry.id"));
        selector.add(new SelectorItemInfo("entry.*"));
        selector.add(new SelectorItemInfo("entry.costCenter.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("year", year, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnable", 1, CompareType.EQUALS));
		filter.setMaskString("#0 AND #1");
		viewInfo.setFilter(filter);
		viewInfo.setSelector(selector);
		ExcessSetCollection excesssetInfos = ExcessSetFactory.getLocalInstance(
				ctx).getExcessSetCollection(viewInfo);

		return excesssetInfos;
	}

}
