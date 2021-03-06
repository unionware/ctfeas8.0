package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillException;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryFactory;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryInfo;
import com.kingdee.eas.cp.bc.ExcessSetCollection;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillInfo;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.scm.sm.pur.PurContractEntryFactory;
import com.kingdee.eas.scm.sm.pur.PurContractEntryInfo;
import com.kingdee.eas.scm.sm.pur.PurContractFactory;
import com.kingdee.eas.scm.sm.pur.PurContractInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class EvectionLoanBillControllerBeanEx extends
		EvectionLoanBillControllerBean {
	@Override
	protected IObjectPK _save(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		EvectionLoanBillInfo loanBillInfo = (EvectionLoanBillInfo) arg1;
//		if(loanBillInfo.getIsFirstCreateFrom() == 0 && loanBillInfo.getSourceBillId() != null
//				&& (!"".equals(loanBillInfo.getSourceBillId()))){
//			loanBillInfo.setName("");
//			loanBillInfo.setPayerName(null);
//			loanBillInfo.setPayerBankStr(null);
//			loanBillInfo.setPayerAccount(null);
//			loanBillInfo.setIsFirstCreateFrom(1);
//		}
		//checkDailyAmount(arg0, loanBillInfo);
		
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("company.id",loanBillInfo.getCompany().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("description","基本户"));
		AccountBankCollection coll =  AccountBankFactory.getLocalInstance(arg0).getAccountBankCollection(view);
	/*	if(coll==null || coll.size()<1){
			throw new EASBizException(new NumericExceptionSubItem("fffff","无法根据费用支付公司找到银行帐号("+loanBillInfo.getCompany().getName()+",基本户"+")!"));
		}
		if(coll.size()>1){
			throw new EASBizException(new NumericExceptionSubItem("fffff","根据费用支付公司找到多个银行帐号,无法确定使用哪一个("+loanBillInfo.getCompany().getName()+",基本户"+"!"));
		}*/
		
		loanBillInfo.setPayCompany(coll.get(0));
		
		return super._save(arg0, arg1);
	}
	@Override
	protected IObjectPK _submit(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
		EvectionLoanBillInfo loanBillInfo = (EvectionLoanBillInfo) arg1;
		//checkDailyAmount(arg0, loanBillInfo);
		String sql = "";
		if(loanBillInfo.getId()!=null){
			sql = "update T_BC_EvectionLoanBillEntry SET CFLastSubmitAmt = FAmount where FBillID='" + loanBillInfo.getId().toString() + "'";
			DbUtil.execute(arg0, sql);
		}
		
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("company.id",loanBillInfo.getCompany().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("description","基本户"));
		AccountBankCollection coll =  AccountBankFactory.getLocalInstance(arg0).getAccountBankCollection(view);
		/*if(coll==null || coll.size()<1){
			throw new EASBizException(new NumericExceptionSubItem("fffff","无法根据费用支付公司找到银行帐号("+loanBillInfo.getCompany().getName()+",基本户"+")!"));
		}
		if(coll.size()>1){
			throw new EASBizException(new NumericExceptionSubItem("fffff","根据费用支付公司找到多个银行帐号,无法确定使用哪一个("+loanBillInfo.getCompany().getName()+",基本户"+"!"));
		}*/
		
		loanBillInfo.setPayCompany(coll.get(0));
		return super._submit(arg0, arg1);
		
	}
	

	private void checkDailyAmount(Context arg0 ,EvectionLoanBillInfo loanBillInfo )throws BOSException, EASBizException{
		EvectionLoanBillEntryCollection lbeCol = new EvectionLoanBillEntryCollection();
		EvectionLoanBillEntryInfo dleInfo = new EvectionLoanBillEntryInfo();
		PurContractEntryInfo pureInfo = new PurContractEntryInfo();
		PurContractInfo purInfo = new PurContractInfo();
		OtherExpenseBillInfo oeInfo  = new OtherExpenseBillInfo();
		EvectionReqBillEntryInfo oeeInfo = new EvectionReqBillEntryInfo();
		String projectid = "",expenseTypeid="",costcenterid = "";
		
		DateFormat df = new SimpleDateFormat("yyyy");
		int year;
		if(loanBillInfo.getBizReqDate()!=null){
			year = Integer.parseInt(df.format(loanBillInfo.getBizReqDate()));
		}else{
			year = Integer.parseInt(df.format(new Date()));
		}
		/*/可用预算
		CostCenterOrgUnitInfo ccInfo = new CostCenterOrgUnitInfo();
		ProjectInfo pjInfo = new ProjectInfo();
		ExpenseTypeInfo etInfo = new ExpenseTypeInfo();
		BigDecimal bugetAmt = new BigDecimal(0); /*/
		
		BigDecimal laondAmt = new BigDecimal(0),conAmt = new BigDecimal(0),lastSubAmt = new BigDecimal(0),
					loandedAmt =  new BigDecimal(0) ,reimburseAmount = new BigDecimal(0),
					exRate = new BigDecimal(0);
		if (loanBillInfo.getSourceBillId() != null
				&& BOSUuid.read(loanBillInfo.getSourceBillId()).getType() != null) {
			if ("78B50853".equalsIgnoreCase(BOSUuid.read(loanBillInfo.getSourceBillId()).getType().toString())) 
			{// 采购订单
				purInfo = PurContractFactory.getLocalInstance(arg0).getPurContractInfo(new ObjectUuidPK(
						loanBillInfo.getSourceBillId().toString()));
				lbeCol = loanBillInfo.getEntries();
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
//						每一行不能超出可用金额，有缺陷：不能拆分原单分录
						dleInfo = lbeCol.get(i);
						laondAmt = dleInfo.getAmount().setScale(2);
						if(dleInfo.getSourceBillEntryID()!=null &&"E8D24BDF".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//采购订单分录
							pureInfo = PurContractEntryFactory.getLocalInstance(arg0).getPurContractEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(pureInfo != null)
							{
//								获取超额比例
								if (oeeInfo.getExpenseType() != null) {
									expenseTypeid = oeeInfo.getExpenseType().getId().toString();
								}
								if (oeeInfo.getProject() != null) {
									projectid = oeeInfo.getProject().getId().toString();
								}
								if (oeeInfo.getCostedDept() != null) {
									costcenterid = oeeInfo.getCostedDept().getId().toString();
								}
								if( projectid == null || projectid.equals("")){
									exRate = getAboveQuota(arg0,year,"",expenseTypeid,costcenterid);
								}else{
									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid,costcenterid);
								}
//								没有找到3个唯一，对应项目与类别
								if(exRate==null  || exRate.equals(BigDecimal.ZERO)){
									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid);
								}else{
//									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid);
								}
//								项目与类别不存在，找同一费用类别，项目为空的
								if(exRate.equals(BigDecimal.ZERO)){
									exRate = getAboveQuota(arg0,year,1,"",expenseTypeid,costcenterid);
								}
//								exRate = exRate.divide(new BigDecimal(100)).setScale(2);
//								exRate = exRate.add(new BigDecimal(1));
								conAmt = oeeInfo.getAmount().setScale(2);  //本位币申请金额
								loandedAmt = oeeInfo.getAmountUsed().setScale(2);//本位币已用金额
								reimburseAmount = oeeInfo.getAmountBalance().setScale(2);//本位币可用金额
								if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).setScale(2)
										.add(new BigDecimal(1)))).subtract(loandedAmt).setScale(2)) > 0)
								{
									 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
												"", "分录" + (i+1) + "申请金额本位币(" + laondAmt.toString() + ")已经超过费用申请单本位币申请金额(" +
														conAmt.toString() +")允许的超额比例(" + exRate.setScale(2).toString()  + "%)");
									 throw new DailyLoanBillException(LoanBiggerAmount);
								}
							}	
						}
					}
				}
			}
			if ("DE853384".equalsIgnoreCase(BOSUuid.read(loanBillInfo.getSourceBillId()).getType().toString()))
			{// 出差申请单
//				oeInfo = OtherExpenseBillFactory.getLocalInstance(arg0).getOtherExpenseBillInfo(new ObjectUuidPK(
//						loanBillInfo.getSourceBillId().toString()));
				
//				exRate = purInfo.getExchangeRate();////超额比例
				lbeCol = loanBillInfo.getEntries();
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
//						每一行不能超出可用金额，有缺陷：不能拆分原单分录
						dleInfo = lbeCol.get(i);
						laondAmt = dleInfo.getAmount().setScale(2);
//						if(dleInfo.getLastSubmitAmt()!=null){
//							lastSubAmt = dleInfo.getLastSubmitAmt().setScale(2);
//						}
						laondAmt  = dleInfo.getAmount();
						try {
							lastSubAmt = getLastSubAmount(arg0,dleInfo.getId().toString());
						} catch (SQLException e) {
							e.printStackTrace();
						}
						if(dleInfo.getSourceBillEntryID()!=null && "5902774E".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//申请单分录
							oeeInfo = EvectionReqBillEntryFactory.getLocalInstance(arg0).getEvectionReqBillEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(oeeInfo != null)
							{
//								获取超额比例
								if (oeeInfo.getExpenseType()!= null) {
									expenseTypeid = oeeInfo.getExpenseType().getId().toString();
								}
								if (oeeInfo.getProject() != null) {
									projectid = oeeInfo.getProject().getId().toString();
								}else{
									projectid = null;
								}
								if (oeeInfo.getCostedDept() != null) {
									costcenterid = oeeInfo.getCostedDept().getId().toString();
								}
								if( projectid == null || projectid.equals("")){
									exRate = getAboveQuota(arg0,year,"",expenseTypeid,costcenterid);
								}else{
									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid,costcenterid);
								}
//								没有找到3个唯一，对应项目与类别
								if(exRate==null  || exRate.equals(BigDecimal.ZERO)){
									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid);
								}else{
//									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid);
								}
//								项目与类别不存在，找同一费用类别，项目为空的
								if(exRate.equals(BigDecimal.ZERO)){
									exRate = getAboveQuota(arg0,year,1,"",expenseTypeid,costcenterid);
								}
//								exRate = exRate.divide(new BigDecimal(100)).setScale(2);
//								exRate = exRate.add(new BigDecimal(1));
								/*conAmt = oeeInfo.getAmount().setScale(2);  //本位币申请金额*/
								//需求要求改成本位币核对金额   xulisha  2014-12-30 
								conAmt = oeeInfo.getAmountApproved().setScale(2,BigDecimal.ROUND_HALF_UP);  //本位币核定金额
								loandedAmt = oeeInfo.getAmountUsed().setScale(2,BigDecimal.ROUND_HALF_UP);//本位币已用金额
								reimburseAmount = oeeInfo.getAmountBalance().setScale(2,BigDecimal.ROUND_HALF_UP);//本位币可用金额
								/*//xulisha  重复提交时，逻辑有问题，导致未超额也提交不成功
								if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).setScale(2).add(new BigDecimal(1)))).subtract(loandedAmt).setScale(2)) > 0)
								{
									 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
												"", "分录" + (i+1) + "申请金额本位币(" + laondAmt.toString() + ")已经超过费用申请单本位币申请金额(" +
														conAmt.toString() +")允许的超额比例(" + exRate.setScale(2).toString()  + "%)");
									 throw new DailyLoanBillException(LoanBiggerAmount);
								}*/
								if(lastSubAmt!=null){
									if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).add(new BigDecimal(1)))).subtract(loandedAmt).add(lastSubAmt).setScale(2,BigDecimal.ROUND_HALF_UP)) > 0)
									{
										 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
													"", "分录" + (i+1) + "申请金额本位币(" + laondAmt.toString() + ")已经超过费用申请单本位币申请金额(" +
															conAmt.toString() +")允许的超额比例(" + exRate.setScale(2,BigDecimal.ROUND_HALF_UP).toString()  + "%)");
										 throw new DailyLoanBillException(LoanBiggerAmount);
									}
								}else{
									if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)))).subtract(loandedAmt).setScale(2,BigDecimal.ROUND_HALF_UP)) > 0)
									{
										 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
													"", "分录" + (i+1) + "申请金额本位币(" + laondAmt.toString() + ")已经超过费用申请单本位币申请金额(" +
															conAmt.toString() +")允许的超额比例(" + exRate.setScale(2,BigDecimal.ROUND_HALF_UP).toString()  + "%)");
										 throw new DailyLoanBillException(LoanBiggerAmount);
									}
								}
							}
							dleInfo.setLastSubmitAmt(dleInfo.getAmount());
						}
/*预算控制暂停
						
						////判断预算是否够用
						ccInfo = dleInfo.getCostDept();
						pjInfo = dleInfo.getProject();
						etInfo = dleInfo.getExpenseType();
						////////可用预算
						boolean isDo = false;
						for(int j = 0 ; j < i ; j ++)
						{
							dleInfo = lbeCol.get(j);
							if(ccInfo.getId().toString().equals(((CostCenterOrgUnitInfo)dleInfo.getCostDept()).getId().toString())
									&& pjInfo.getId().toString().equals(((ProjectInfo)dleInfo.getProject()).getId().toString())
									&& etInfo.getId().toString().equals(((ExpenseTypeInfo)dleInfo.getExpenseType()).getId().toString())
									){
									isDo = true;
									break;
							}
						}
						if(!isDo){
							for(int j = i + 1 ; j < lbeCol.size(); j++)
							{
								dleInfo = lbeCol.get(j);
								if(ccInfo.getId().toString().equals(((CostCenterOrgUnitInfo)dleInfo.getCostDept()).getId().toString())
										&& pjInfo.getId().toString().equals(((ProjectInfo)dleInfo.getProject()).getId().toString())
										&& etInfo.getId().toString().equals(((ExpenseTypeInfo)dleInfo.getExpenseType()).getId().toString())
										){
									laondAmt = laondAmt.add(dleInfo.getAmount());
								}
							}
							if(bugetAmt.compareTo(laondAmt) < 0 ){
								NumericExceptionSubItem NoBugetAmt = new NumericExceptionSubItem(
										"", "部门[" + ccInfo.getName() + "],项目[" + pjInfo.getName() + "],类型[" + etInfo.getName() + "]"+"预算不足" );
							 throw new DailyLoanBillException(NoBugetAmt);
							}
						}
*/
					}
				}
			}
		}
	}
	

	/**
	 * 获取当前合同对应的年份的超额设置
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

		if (isExistProject(excesssetInfos, projectid)) {
			if (excesssetInfos != null && excesssetInfos.size() > 0) {
				for (int i = 0; i < excesssetInfos.size(); i++) {
					setprojectid = excesssetInfos.get(i).getProject().getId()
							.toString();
					setexpenseTypeid = excesssetInfos.get(i).getExpenseType()
							.getId().toString();

					for (int j = 0; j < excesssetInfos.get(i).getEntry().size(); i++) {
						setcostcenterid = excesssetInfos.get(i).getEntry().get(
								j).getCostCenter().getId().toString();
						if (projectid.equalsIgnoreCase(setprojectid)
								&& expenseTypeid
										.equalsIgnoreCase(setexpenseTypeid)
								&& costcenterid
										.equalsIgnoreCase(setcostcenterid)) {
							AboveRate = excesssetInfos.get(i).getEntry().get(j)
									.getRate();
							break;
						} else {
							AboveRate = excesssetInfos.get(i).getRate();
							break;
						}
					}
				}
			}
		} else {
			if (excesssetInfos != null && excesssetInfos.size() > 0) {
				for (int i = 0; i < excesssetInfos.size(); i++) {
					setexpenseTypeid = excesssetInfos.get(i).getExpenseType()
							.getId().toString();

					for (int j = 0; j < excesssetInfos.get(i).getEntry().size(); i++) {
						setcostcenterid = excesssetInfos.get(i).getEntry().get(
								j).getCostCenter().getId().toString();
						if (expenseTypeid.equalsIgnoreCase(setexpenseTypeid)
								&& costcenterid
										.equalsIgnoreCase(setcostcenterid)) {
							AboveRate = excesssetInfos.get(i).getEntry().get(j)
									.getRate();
							break;
						} else {
							AboveRate = excesssetInfos.get(i).getRate();
							break;
						}
					}
				}
			}
		}
		return AboveRate.divide(new BigDecimal("100")).setScale(2);
	}
	
	/**
	 * 获取超额比例
	 * 项目+费用类型
	 * @return 表头比例
	 * @throws BOSException 
	 * @throws BOSException
	 */
	
	private BigDecimal getAboveQuota(Context ctx, int year,String projectid,
			String expenseTypeid) throws BOSException{
		String sql = "";
		sql = "";
		sql = sql + "SELECT T1.CFRate AS FHeadRate FROM CT_BC_ExcessSet T1" + "\n";
		sql = sql + "WHERE CFISENABLE = 1 AND CFYEAR = "  + ((Integer)year).toString() + "\n";
		sql = sql + " AND CFExpenseTypeID = '" + expenseTypeid + "' \n";
		sql = sql + " AND ISNULL(CFProjectID,'') = '" + projectid + "' \n";
		IRowSet rs = DbUtil.executeQuery(ctx, sql);
		try {
			if(rs.next()){
				return rs.getBigDecimal("FHeadRate");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 获取超额比例;获取项目为空的所有组织比例
	 * 项目+费用类型+成本中心
	 * @return 表头比例
	 * @throws BOSException 
	 * @throws BOSException
	 * @throws  
	 */
	
	private BigDecimal getAboveQuota(Context ctx, int year,int iType,String projectid,
			String expenseTypeid,String costcenterid) throws BOSException{
		String sql = "";
		sql = "";
		sql = sql + "SELECT T1.CFRate AS FHeadRate,T2.CFRate AS FEntryRate FROM CT_BC_ExcessSet T1" + "\n";
		sql = sql + "INNER JOIN CT_BC_ExcessSetEntry T2 ON T1.FID = T2.CFParentID" + "\n";
		sql = sql + "WHERE CFISENABLE = 1 AND CFYEAR = "  + ((Integer)year).toString() + "\n";
		sql = sql + " AND CFCostCenterID = '" + costcenterid + "' \n";
		sql = sql + " AND CFExpenseTypeID = '" + expenseTypeid + "' \n";
		sql = sql + " AND ISNULL(CFProjectID,'') = '' \n";
		IRowSet rs = DbUtil.executeQuery(ctx, sql);
		try {
			if(rs.next()){
				return rs.getBigDecimal("FEntryRate");
			}else{
//				没有针对组织+类型的
				sql = "";
				sql = sql + "SELECT T1.CFRate AS FHeadRate FROM CT_BC_ExcessSet T1" + "\n";
				sql = sql + "WHERE CFISENABLE = 1 AND CFYEAR = "  + ((Integer)year).toString() + "\n";
				sql = sql + " AND CFExpenseTypeID = '" + expenseTypeid + "' \n";
				sql = sql + " AND ISNULL(CFProjectID,'') = '' \n";
				rs = DbUtil.executeQuery(ctx, sql);
				if(rs.next()){
					return rs.getBigDecimal("FHeadRate");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}
	
	
	/**
	 * 获取超额比例
	 * 项目+费用类型+成本中心
	 * @return 表头比例
	 * @throws BOSException 
	 * @throws BOSException
	 * @throws  
	 */
	
	private BigDecimal getAboveQuota(Context ctx, int year,String projectid,
			String expenseTypeid,String costcenterid) throws BOSException{
		String sql = "";
		sql = "";
		sql = sql + "SELECT T1.CFRate AS FHeadRate,T2.CFRate AS FEntryRate FROM CT_BC_ExcessSet T1" + "\n";
		sql = sql + "INNER JOIN CT_BC_ExcessSetEntry T2 ON T1.FID = T2.CFParentID" + "\n";
		sql = sql + "WHERE CFISENABLE = 1 AND CFYEAR = "  + ((Integer)year).toString() + "\n";
		sql = sql + " AND CFCostCenterID = '" + costcenterid + "' \n";
		sql = sql + " AND CFExpenseTypeID = '" + expenseTypeid + "' \n";
		sql = sql + " AND ISNULL(CFProjectID,'') = '" + projectid + "' \n";
		IRowSet rs = DbUtil.executeQuery(ctx, sql);
		try {
			if(rs.next()){
				return rs.getBigDecimal("FEntryRate");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
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
				String setProjectid = excesssetInfos.get(i).getProject()
						.getId().toString();
				if (projectid.equalsIgnoreCase(setProjectid)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	protected void _setPassState(Context ctx, BOSUuid id) throws BOSException,
			EASBizException {
		super._setPassState(ctx, id);
		CreateK3PayNoticeBillFacadeFactory.getLocalInstance(ctx).createByEvectionLoanBill(id);
	}
	
	
	private BigDecimal getLastSubAmount(Context arg0,String sid) throws BOSException, SQLException{
		String sql = "";
		sql = "SELECT CFLastSubmitAmt FROM  T_BC_DailyLoanBillEntry where FID='" + sid + "'";
		IRowSet rs = null;
		rs = DbUtil.executeQuery(arg0, sql);
		if(rs.next()){
			return rs.getBigDecimal("CFLastSubmitAmt");
		}
		return BigDecimal.ZERO;
	}
}
