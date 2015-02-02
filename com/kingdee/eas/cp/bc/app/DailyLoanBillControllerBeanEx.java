package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
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
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizAccountBillLoanCheckEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillReqCheckEntryInfo;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillException;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillReqCheckEntryInfo;
import com.kingdee.eas.cp.bc.ExcessSetCollection;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillInfo;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.scm.sm.pur.PurContractEntryFactory;
import com.kingdee.eas.scm.sm.pur.PurContractEntryInfo;
import com.kingdee.eas.scm.sm.pur.PurContractException;
import com.kingdee.eas.scm.sm.pur.PurContractFactory;
import com.kingdee.eas.scm.sm.pur.PurContractInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class DailyLoanBillControllerBeanEx extends DailyLoanBillControllerBean {

//	@Override
	public static final NumericExceptionSubItem CONTRACT_BUDGET_MSG = new NumericExceptionSubItem(
				"", "借款金额不能超出合同可用金额");
	
	Map<String, BigDecimal> oldReqMap = null;
	
	
	@Override
	protected IObjectPK _save(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		DailyLoanBillInfo loanBillInfo = (DailyLoanBillInfo) arg1;
		
		checkEntry(arg0, arg1, 0);
//		if(loanBillInfo.getFirstCreateFrom() == 0 && loanBillInfo.getSourceBillId() != null
//				&& (!"".equals(loanBillInfo.getSourceBillId()))){
////			loanBillInfo.setName("");
//			loanBillInfo.setPayerName(null);
//			loanBillInfo.setPayerBankStr(null);
//			loanBillInfo.setPayerAccount(null);
//			loanBillInfo.setFirstCreateFrom(1);
//		}
		
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
		
		return super._save(arg0, arg1);
	}
	@Override
	protected IObjectPK _submit(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
		DailyLoanBillInfo loanBillInfo = (DailyLoanBillInfo) arg1;
		checkEntry(arg0, arg1, 1);
		String sql = "";
		if(loanBillInfo.getId()!=null){
			sql = "update T_BC_DailyLoanBillEntry SET CFLastSubmitAmt = FAmount where FBillID='" + loanBillInfo.getId().toString() + "'";
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
		}
		*/
		loanBillInfo.setPayCompany(coll.get(0));
		
		return super._submit(arg0, arg1);
	}
	
	protected void initOldReqMap(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		oldReqMap = new HashMap<String, BigDecimal>();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		sic.add("ReqCheckEntries.*");
		DailyLoanBillInfo bill = getDailyLoanBillInfo(ctx, new ObjectUuidPK(((DailyLoanBillInfo)model).getId().toString()), sic);
		DailyLoanBillReqCheckEntryInfo reqInfo = null;
		OtherExpenseBillEntryInfo otherEntry = null;
		String key = null;
		BigDecimal amt = BigDecimal.ZERO;
		
		if(bill!=null && bill.getState()!=StateEnum.DRAFT){
			for(int i=0;i<bill.getReqCheckEntries().size();i++){
				reqInfo = bill.getReqCheckEntries().get(i);
				otherEntry = OtherExpenseBillEntryFactory.getLocalInstance(ctx).getOtherExpenseBillEntryInfo(new ObjectUuidPK(reqInfo.getSourceBillEntryID()));
				
				amt = reqInfo.getCheckAmount();
				
				//封装KEY
				if(otherEntry.getProject()==null || otherEntry.getProject().getId()==null){
					key = reqInfo.getSourceBillCostCenterId();
				}else{
					key = otherEntry.getProject().getId()+reqInfo.getSourceBillCostCenterId();
				}

				//设置 项目+成本中心 相同的  可用金额
				if(oldReqMap.get(key)!=null){
					amt = amt.add(oldReqMap.get(key));
				}
				oldReqMap.put(key, amt);
			}
		}
	}
	
	protected void checkEntry(Context ctx, IObjectValue model, int flag)
			throws BOSException, EASBizException {
		DailyLoanBillInfo info = (DailyLoanBillInfo)model;
		
		if(flag==1 && info.getId()!=null){
			initOldReqMap(ctx, model);
		}
	
		DailyLoanBillEntryInfo entry = null;
		OtherExpenseBillEntryInfo otherEntry = null;
		BigDecimal aboveQuota = BigDecimal.ONE;
		BigDecimal aboveQuota1 = BigDecimal.ZERO;
		DateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(info.getBizReqDate()));
		ExcessSetCollection excesssetinfos = ExcessSetFactory.getLocalInstance(ctx).getExcessSetInfos(year);//获取符合年份的超额比例集合
		
		DailyLoanBillReqCheckEntryInfo reqInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		
		Map<String, BigDecimal> reqMap = new HashMap<String, BigDecimal>();
		String key = null;
		ProjectInfo project = null;
		String expTypeID = null;
		String costOrgID = null;
		BigDecimal amt = BigDecimal.ZERO;
		//费用申请单处理
		for(int i=0; i<info.getReqCheckEntries().size();i++){
			aboveQuota = BigDecimal.ONE;
			reqInfo = info.getReqCheckEntries().get(i);
			if(reqInfo.getSourceBillEntryID()!=null && reqInfo.getSourceBillID()!=null && "E76173AD".equalsIgnoreCase(BOSUuid.read(reqInfo.getSourceBillID()).getType().toString())){
				otherEntry = OtherExpenseBillEntryFactory.getLocalInstance(ctx).getOtherExpenseBillEntryInfo(new ObjectUuidPK(reqInfo.getSourceBillEntryID()),selector);
				
				project = otherEntry.getProject();
				expTypeID = otherEntry.getExpenseType().getId().toString();
				costOrgID = otherEntry.getCostedDept().getId().toString();
				amt = otherEntry.getAmountApproved();
				
				/*aboveQuota1 = getAboveQuota(ctx, project==null?null:project.getId().toString(), expTypeID, costOrgID, excesssetinfos);
				aboveQuota = aboveQuota.add(aboveQuota1.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP, 2));*/
				//获取符合条件的超额比例值
				aboveQuota1 = ExcessSetFactory.getLocalInstance(ctx).getAboveQuota(project==null?null:project.getId().toString(), expTypeID, costOrgID, excesssetinfos);
				aboveQuota = aboveQuota.add(aboveQuota1.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP, 2));
				
				amt = otherEntry.getAmountApproved().multiply(aboveQuota).subtract(otherEntry.getAmountUsed());
				
				//封装KEY
				if(project==null || project.getId()==null){
					key = costOrgID;
				}else{
					key = project.getId()+costOrgID;
				}

				//设置 项目+成本中心 相同的  可用金额
				if(reqMap.get(key)!=null){
					amt = amt.add(reqMap.get(key));
				}
				reqMap.put(key, amt);
			}
		}
		
		String str = "";
		
		Map<String, BigDecimal> accMap = new HashMap<String, BigDecimal>();
		Map<String, String> strMap = new HashMap<String, String>();
		for (int i = 0; i < info.getEntries().size(); i++) {
			str = "";
			entry = info.getEntries().get(i);
			aboveQuota = BigDecimal.ONE;
			project = entry.getProject();
			expTypeID = entry.getExpenseType().getId().toString();
			costOrgID = entry.getCostDept().getId().toString();
			amt = entry.getAmount();

			//封装KEY
			if(project==null || project.getId()==null){
				key = costOrgID;
			}else{
				key = project.getId()+costOrgID;
				str = "项目（"+project.getName()+"）、";
			}
			str = str + "费用归属部门（"+entry.getCostDept().getName()+"）";

			//设置 项目+成本中心 相同的  本次申请金额
			if(accMap.get(key)!=null){
				amt = amt.add(accMap.get(key));
			}
			accMap.put(key, amt);
			
			strMap.put(key, str);
			
			if(flag==1){
				info.getEntries().get(i).setLastSubmitAmt(entry.getAmount());
			}
		}
		
		if(info.getReqCheckEntries().size()>0){
			
			if(oldReqMap!=null && oldReqMap.size()>0){
				for(Iterator<String> it=reqMap.keySet().iterator();it.hasNext();){
					key = it.next();
					amt = reqMap.get(key);
					if(oldReqMap.get(key)!=null){
						amt = amt.add(oldReqMap.get(key));
					}
					reqMap.put(key, amt);
				}
			}
			
			//设置 项目+成本中心 相同的  可用金额
			for(Iterator<String> it=accMap.keySet().iterator();it.hasNext();){
				key = it.next();
				if(reqMap.get(key)!=null){
					//校验是否超出了费用申请额范围
					//校验【费用报销单】费用清单分录中的“本位币申请金额”-【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”
					//必须小于等于关联【费用申请单】费用清单分录中的“本位币申请金额” * （1+超额比例）-“本位币已用金额”-已提交反写可用金额
					if(accMap.get(key).compareTo(reqMap.get(key))>0){
						 throw new ExpAccException(new NumericExceptionSubItem("1111",strMap.get(key)+"的本位币申请金额（"+accMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"）已超过可申请报销金额（"+reqMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"）！"));
					}
				}else if(accMap.get(key)!=null && accMap.get(key).compareTo(BigDecimal.ZERO)>0){
					throw new ExpAccException(new NumericExceptionSubItem("1111",strMap.get(key)+"的本位币申请金额（"+accMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"）已超过可申请报销金额（0）！"));
				}
			}
		}
	}
	
	private BigDecimal getLastSubAmount(Context arg0,String sid) throws BOSException, SQLException{
		String sql = "";
		sql = "SELECT CFLastSubmitAmt FROM  T_BC_DailyLoanBillEntry where FID='" + sid + "'";
		IRowSet rs = null;
		rs = DbUtil.executeQuery(arg0, sql);
		BigDecimal amt = BigDecimal.ZERO;
		if(rs.next()){
			amt = rs.getBigDecimal("CFLastSubmitAmt");
		}
		if(amt==null){
			amt = BigDecimal.ZERO;
		}
		return amt;
	}
	
	
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._save(ctx, pk, model);
	}
	
	private void writeBackAmount(Context arg0,DailyLoanBillInfo loanBillInfo) throws EASBizException, BOSException{
		/*DailyLoanBillEntryCollection lbeCol = new DailyLoanBillEntryCollection();
		DailyLoanBillEntryInfo dleInfo = new DailyLoanBillEntryInfo();
		PurContractEntryInfo pureInfo = new PurContractEntryInfo();
		OtherExpenseBillInfo oeInfo  = new OtherExpenseBillInfo();
		OtherExpenseBillEntryInfo oeeInfo = new OtherExpenseBillEntryInfo();
		String sql = "";
		
		BigDecimal laondAmt = new BigDecimal(0),laondAmtl = new BigDecimal(0),usedAmt = new BigDecimal(0),loandedAmt =  new BigDecimal(0) ,lastSubAmt = new BigDecimal(0);

		
		if (loanBillInfo.getSourceBillId() != null
				&& BOSUuid.read(loanBillInfo.getSourceBillId()).getType() != null) {
			lbeCol = loanBillInfo.getEntries();
			if ("78B50853".equalsIgnoreCase(BOSUuid.read(loanBillInfo.getSourceBillId()).getType().toString())) 
			{// 采购合同
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
						dleInfo = lbeCol.get(i);
						lastSubAmt = dleInfo.getLastSubmitAmt();////
						laondAmt = laondAmtl = dleInfo.getAmount();
						laondAmt = laondAmt.subtract(lastSubAmt);///减去上次提交
						if(dleInfo.getSourceBillEntryID()!=null &&"E8D24BDF".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//采购合同分录
							pureInfo = PurContractEntryFactory.getLocalInstance(arg0).getPurContractEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(pureInfo != null)
							{
								loandedAmt = pureInfo.getLoandAmount();//已借款金额
								usedAmt = pureInfo.getUsedAmount();
								loandedAmt = loandedAmt.add(laondAmt);
								usedAmt = usedAmt.add(laondAmt);
								
								sql = "UPDATE T_SM_PurContractEntry SET CFLoandAmount = " + loandedAmt
										+ ",CFUsedAmount = " + usedAmt.toString() + " where fid ='" + pureInfo.getId() + "'";
								DbUtil.execute(arg0, sql);
//								采购订单上游单据
								if(pureInfo.getSourceBillEntryId()!=null &&"E8D24BDF".equalsIgnoreCase(BOSUuid.read(pureInfo.getSourceBillEntryId()).getType().toString())){
									rewriteOthExpense(arg0,pureInfo.getSourceBillEntryId().toString(),pureInfo.getSourceBillId(),laondAmt);
								}//oeeInfo
							}//pureInfo	
						}//dleInfo
//						更新最新提交金额
						sql = "UPDATE T_BC_DailyLoanBillEntry SET CFLoandAmount = " + laondAmtl + "' where fid = '" + dleInfo.getId().toString() + "'";
						DbUtil.execute(arg0, sql);
					}
				}
			}*/
			/*-----标准流程不反写
			if ("E76173AD".equalsIgnoreCase(BOSUuid.read(loanBillInfo.getSourceBillId()).getType().toString()))
			{//费用申请
				oeInfo = OtherExpenseBillFactory.getLocalInstance(arg0).getOtherExpenseBillInfo(new ObjectUuidPK(
						loanBillInfo.getSourceBillId().toString()));
				
				lbeCol = loanBillInfo.getEntries();
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
						dleInfo = lbeCol.get(i);
						laondAmt = dleInfo.getAmount();
						if(dleInfo.getSourceBillEntryID()!=null && "F5C4E8C5".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//申请单分录
							oeeInfo = OtherExpenseBillEntryFactory.getLocalInstance(arg0).getOtherExpenseBillEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(oeeInfo != null)
							{
//								反写费用申请
								rewriteOthExpense(arg0,dleInfo.getSourceBillEntryID().toString(),dleInfo.getSourceBillID(),laondAmt);
							}	
						}
					}
				}
			}
			*/
		//}
	}
	
	
	
//	反写费用申请
	private void rewriteOthExpense(Context ctx,String entryId ,String billId,BigDecimal loandedAmt) throws EASBizException, BOSException{

		OtherExpenseBillInfo oeInfo  = new OtherExpenseBillInfo();
		OtherExpenseBillEntryInfo oeeInfo = new OtherExpenseBillEntryInfo();
		
		String sql = "";
		
		BigDecimal laondAmt = new BigDecimal(0),usedAmt = new BigDecimal(0),appAmt = new BigDecimal(0),
				   reimburseAmount = new BigDecimal(0),rate = new BigDecimal(0);
		
		oeInfo = OtherExpenseBillFactory.getLocalInstance(ctx).getOtherExpenseBillInfo(new ObjectUuidPK(billId));
		rate = oeInfo.getApplyAmount().divide(oeInfo.getAmount());//汇率
		oeeInfo = OtherExpenseBillEntryFactory.getLocalInstance(ctx).getOtherExpenseBillEntryInfo(new ObjectUuidPK(entryId));
		
		usedAmt = oeeInfo.getAmountUsed();
		usedAmt = usedAmt.add(loandedAmt);//已用金额
		reimburseAmount = oeeInfo.getAmountBalance();//可用金额
		appAmt = oeeInfo.getAmount();//申请金额
		
		reimburseAmount = appAmt.subtract(usedAmt);
		usedAmt = usedAmt.add(laondAmt);
		sql = "UPDATE T_BC_OtherExpenseBillEntry SET FAmountUsed = " + usedAmt ;
		DbUtil.execute(ctx, sql);
		sql = "UPDATE T_BC_OtherExpenseBillEntry SET FAmountBalance " + reimburseAmount + ","
			+ " FAmountBalanceOri = " + reimburseAmount.divide(rate) + ",\n" + 
			  " FAmountUsedOri =  " + usedAmt.divide(rate) + ",\n" + 
			  " ";
		DbUtil.execute(ctx, sql);
//		表头
		usedAmt = oeInfo.getAmountUsed();
		usedAmt = usedAmt.subtract(loandedAmt);//已用金额
		appAmt = oeInfo.getAmount();//申请金额
		reimburseAmount = oeInfo.getAmountBalance();
		reimburseAmount = appAmt.subtract(usedAmt);
		sql = "UPDATE T_BC_OtherExpenseBill SET FAmountUsed = " + usedAmt + ",FAmountBalance = " + reimburseAmount ;
		DbUtil.execute(ctx, sql);
	}
	
	@Override
	protected void _delete(Context arg0, IObjectPK arg1) throws BOSException,
			EASBizException {
		super._delete(arg0, arg1);
	}

	@Override
	protected boolean _abandonFromContract(Context ctx, BOSUuid billId)
			throws BOSException {
		//TODO  检查业务变更是否还是用此功能
		/*try {
			String sql = "";
			DailyLoanBillInfo  dlbInfo = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(billId));
			DailyLoanBillEntryCollection lbeCol = new DailyLoanBillEntryCollection();
			DailyLoanBillEntryInfo dleInfo = new DailyLoanBillEntryInfo();
			PurContractEntryInfo pureInfo = new PurContractEntryInfo();
			BigDecimal laonAmt = new BigDecimal(0),usedAmt = new BigDecimal(0),loanedAmt =  new BigDecimal(0) ;
			lbeCol = dlbInfo.getEntries();
			if(lbeCol.size() > 0 ){
				for(int i = 0 ; i < lbeCol.size(); i++){
					dleInfo = lbeCol.get(i);
					if(dleInfo != null){
						laonAmt = dleInfo.getAmount();
						if(dleInfo.getSourceBillEntryID()!=null 
								&& "E8D24BDF".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString())){
							pureInfo = PurContractEntryFactory.getLocalInstance(ctx).
											getPurContractEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							usedAmt = pureInfo.getUsedAmount();
							loanedAmt = pureInfo.getLoandAmount();
							loanedAmt = loanedAmt.add(loanedAmt);
							usedAmt = usedAmt.add(laonAmt);
							sql = "UPDATE T_SM_PurContractEntry SET CFLoandAmount = " + loanedAmt
									+ ",CFUsedAmount = " + usedAmt.toString() + " where fid ='" + pureInfo.getId() + "'";
							DbUtil.execute(ctx, sql);
//							采购订单上游单据
							if(pureInfo.getSourceBillEntryId()!=null &&"E8D24BDF".equalsIgnoreCase(BOSUuid.read(pureInfo.getSourceBillEntryId()).getType().toString())){
								laonAmt = BigDecimal.ZERO.subtract(laonAmt);
								rewriteOthExpense(ctx,pureInfo.getSourceBillEntryId().toString(),pureInfo.getSourceBillId(),laonAmt);
							}//oeeInfo
						}
					}
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}*/
		return super._abandonFromContract(ctx, billId);
	}
	
		protected void _setPassState(Context ctx, BOSUuid id) throws BOSException,
				EASBizException {
			super._setPassState(ctx, id);
			CreateK3PayNoticeBillFacadeFactory.getLocalInstance(ctx).createByDailyLoanBill(id);
		}
}
