package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
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
import com.kingdee.eas.base.core.util.DbUtil;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizAccountBillLoanCheckEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillReqCheckEntryInfo;
import com.kingdee.eas.cp.bc.ExcessSetCollection;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class BizAccountBillControllerBeanEx extends
		BizAccountBillControllerBean {

	public BizAccountBillControllerBeanEx() {
	}
	
	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk =  super._submit(ctx, model);
		
		afterSubmit(ctx, model);
		
		return pk;
	}
	
	@Override
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._submit(ctx, pk, model);
		
		afterSubmit(ctx, model);
	}
	
	@Override
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("entries.*");
		selector.add("LoanCheckEntries.*");
		BizAccountBillInfo info = BizAccountBillFactory.getLocalInstance(ctx).getBizAccountBillInfo(pk, selector);
		
		super._delete(ctx, pk);
		
//		afterDelete(ctx, info);
	}
	
	@Override
	protected void _handleAfterTransform(Context ctx, String action,
			IObjectCollection collection) throws BOSException, EASBizException {
		super._handleAfterTransform(ctx, action, collection);
		
		for(int i=0;i<collection.size();i++){
			clearCollectionEntries(ctx, collection.getObject(i));
		}
	}
	
	@Override
	protected void _handleAfterTransform(Context ctx, String action,
			IObjectValue objectValue) throws BOSException, EASBizException {
		super._handleAfterTransform(ctx, action, objectValue);
		
		clearCollectionEntries(ctx, objectValue);
	}
	
	private void clearCollectionEntries(Context ctx, IObjectValue objectValue){
		if ((objectValue instanceof ExpenseAccountBillInfo)) {
			AbstractObjectCollection collectionEntries = (AbstractObjectCollection)objectValue.get("collectionEntries");
			if (collectionEntries != null) {
				collectionEntries.clear();
			}
		}
	}
	
	protected void afterDelete(Context ctx, IObjectValue model) throws BOSException{
		BizAccountBillInfo info = (BizAccountBillInfo) model;
		BizAccountBillEntryInfo entryInfo = null;
		BizAccountBillLoanCheckEntryInfo loanInfo = null;
		BigDecimal useAmt = null;
		String sEntryID = null;
		StringBuffer sSQL = new StringBuffer();
		for(int i=0;i<info.getEntries().size();i++){
			entryInfo = info.getEntries().get(i);
			useAmt=entryInfo.getAmount();
			for(int j=0;j<info.getLoanCheckEntries().size();j++){
				loanInfo = info.getLoanCheckEntries().get(j);
				if(entryInfo.getProject()==loanInfo.getProject() && (entryInfo.getExpenseType().getId().toString()==loanInfo.getSourceBillExpenseTypeId() || entryInfo.getExpenseType().getId().toString().equals(loanInfo.getSourceBillExpenseTypeId())) && (entryInfo.getCostCenter().getId().toString()==loanInfo.getSourceBillCostCenterId() || entryInfo.getCostCenter().getId().toString().equals(loanInfo.getSourceBillCostCenterId()))){
					useAmt=useAmt.subtract(loanInfo.getCheckAmount());
				}
			}
			
			sEntryID = entryInfo.getSourceBillEntryID();
			
			if(entryInfo.getSourceBillID()!=null && entryInfo.getSourceBillEntryID()!=null){
				if ("78B50853".equalsIgnoreCase(BOSUuid.read(
						entryInfo.getSourceBillID()).getType().toString())) {// 采购合同
					//反写采购合同分录：“已关联费用报销单金额（本位币）”=原“已关联费用报销单金额（本位币）”-【费用报销单】费用清单分录中的“本位币申请金额”
					//“本位币已用金额”=原“本位币已用金额”-【费用报销单】费用清单分录中的“本位币申请金额” +【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”
					sSQL.append(" Update T_SM_PurContractEntry SET CFLoandAmount=isnull(CFContractAmount,0)-isnull("+entryInfo.getSubmitAmt()+",0),").append("\r\n");
					sSQL.append(" CFUsedAmount=isnull(CFUsedAmount,0)-isnull("+entryInfo.getSubmitUseAmt()+",0) ").append("\r\n");
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				
				//“本位币已用金额”=原“本位币已用金额”-【费用报销单】费用清单分录中的“本位币申请金额” +【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”
				//“本位币可用余额”=“本位币申请金额”-“本位币已用金额”、“原币已用金额”=“本位币已用金额”/汇率、“原币可用余额”=“本位币可用余额”/汇率
				sSQL = new StringBuffer();
				sSQL.append(" Update T_BC_OtherExpenseBillEntry SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0), ").append("\r\n");
				sSQL.append(" FAmountUsedOri=(isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0))*isnull(FExchangeRate,0), ").append("\r\n");
				sSQL.append(" FAmountBalanceOri=(isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0))*isnull(FExchangeRate,0) ").append("\r\n");

				if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 费用申请单分录
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");
					
					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 采购合同
					sSQL.append(" WHERE FID=select FsourceBillEntryId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				sSQL = new StringBuffer();
				
				//费用申请单表头的“已用金额”、“可用余额”需要重新根据分录汇总（先减去上次提交反写过的金额，在加上本次需要反写的金额）
				sSQL.append(" Update T_BC_OtherExpenseBill SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0) ").append("\r\n");
				
				if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 费用申请单分录
					sSQL.append(" WHERE FID='" + entryInfo.getSourceBillID() + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 采购合同
					sSQL.append(" WHERE FID=select FsourceBillId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}				
			}
		}
	}
	
	protected void afterSubmit(Context ctx, IObjectValue model) throws BOSException{
		BizAccountBillInfo info = (BizAccountBillInfo) model;
		BizAccountBillEntryInfo entryInfo = null;
		BizAccountBillLoanCheckEntryInfo loanInfo = null;
		BigDecimal useAmt = null;
		String sEntryID = null;
		StringBuffer sSQL = new StringBuffer();
		for(int i=0;i<info.getEntries().size();i++){
			entryInfo = info.getEntries().get(i);
			useAmt=entryInfo.getAmount();
			for(int j=0;j<info.getLoanCheckEntries().size();j++){
				loanInfo = info.getLoanCheckEntries().get(j);
				if(entryInfo.getProject()==loanInfo.getProject() && (entryInfo.getExpenseType().getId().toString()==loanInfo.getSourceBillExpenseTypeId() || entryInfo.getExpenseType().getId().toString().equals(loanInfo.getSourceBillExpenseTypeId())) && (entryInfo.getCostCenter().getId().toString()==loanInfo.getSourceBillCostCenterId() || entryInfo.getCostCenter().getId().toString().equals(loanInfo.getSourceBillCostCenterId()))){
					useAmt=useAmt.subtract(loanInfo.getCheckAmount());
				}
			}
			
			sEntryID = entryInfo.getSourceBillEntryID();
			
			if(entryInfo.getSourceBillID()!=null && entryInfo.getSourceBillEntryID()!=null){
				if ("78B50853".equalsIgnoreCase(BOSUuid.read(
						entryInfo.getSourceBillID()).getType().toString())) {// 采购合同
					//反写采购合同分录：“已关联费用报销单金额（本位币）”=原“已关联费用报销单金额（本位币）”+【费用报销单】费用清单分录中的“本位币申请金额”
					//本位币已用金额”=原“本位币已用金额” +(【费用报销单】费用清单分录中的“本位币申请金额” -【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”)；
					//（先减去上次提交反写过的金额，在加上本次需要反写的金额）
					sSQL.append(" Update T_SM_PurContractEntry SET CFLoandAmount=isnull(CFContractAmount,0)-isnull("+entryInfo.getSubmitAmt()+",0)+(" + entryInfo.getAmount() + "),").append("\r\n");
					sSQL.append(" CFUsedAmount=isnull(CFUsedAmount,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+(" + useAmt + ") ").append("\r\n");
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				
				//反写费用申请单分录：“本位币已用金额”=原“本位币已用金额”+(【费用报销单】费用清单分录中的“本位币申请金额” -【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额” )；
				//“本位币可用余额”=“本位币申请金额”-“本位币已用金额”、“原币已用金额”=“本位币已用金额”/汇率、“原币可用余额”=“本位币可用余额”/汇率；
				//（先减去上次提交反写过的金额，在加上本次需要反写的金额）
				sSQL = new StringBuffer();
				sSQL.append(" Update T_BC_OtherExpenseBillEntry SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "), ").append("\r\n");
				sSQL.append(" FAmountUsedOri=(isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "))*isnull(FExchangeRate,0), ").append("\r\n");
				sSQL.append(" FAmountBalanceOri=(isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "))*isnull(FExchangeRate,0) ").append("\r\n");

				//源单是费用申请单的话，不需要处理反写，用标准反写功能
				/*if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 费用申请单分录
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");
					
					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else */if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 采购合同
					sSQL.append(" WHERE FID=select FsourceBillEntryId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				sSQL = new StringBuffer();
				
				//费用申请单表头的“已用金额”、“可用余额”需要重新根据分录汇总（先减去上次提交反写过的金额，在加上本次需要反写的金额）
				sSQL.append(" Update T_BC_OtherExpenseBill SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + ") ").append("\r\n");
				
				//源单是费用申请单的话，不需要处理反写，用标准反写功能
				/*if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 费用申请单分录
					sSQL.append(" WHERE FID='" + entryInfo.getSourceBillID() + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else */if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// 采购合同
					sSQL.append(" WHERE FID=select FsourceBillId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				
				sSQL = new StringBuffer();
				//更新本次提交金额
				sSQL.append(" Update T_BC_BizAccountBillEntry SET CFSubmitAmt="+ entryInfo.getAmount() + ",").append("\r\n");
				sSQL.append(" CFSubmitUseAmt="+ useAmt + " ").append("\r\n");
				sSQL.append(" WHERE FID='" + entryInfo.getId() + "'").append("\r\n");
				
				if (sSQL.length() > 0) {
					DbUtil.execute(ctx, sSQL.toString());
				}
				
			}
		}
	}
	
	@Override
	protected void checkBill(Context ctx, IObjectValue info)
			throws BOSException, EASBizException {
		super.checkBill(ctx, info);
		
		checkEntry(ctx,info);
	}
	
	protected void checkEntry1(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BizAccountBillInfo info = (BizAccountBillInfo)model;
		BizAccountBillEntryInfo entry = null;
		BizAccountBillLoanCheckEntryInfo loan = null;
		/*PurContractEntryInfo purEntry = null;*/
		OtherExpenseBillEntryInfo otherEntry = null;
		BigDecimal aboveQuota = BigDecimal.ONE;
		BigDecimal aboveQuota1 = BigDecimal.ZERO;
		DateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(info.getBizDate()));
		ExcessSetCollection excesssetinfos = getExcessSetInfos(ctx, year);
		
		BigDecimal checkAmount = null;
		BigDecimal subAmt = null;
		BigDecimal loanAmt = null;
		
		BizAccountBillReqCheckEntryInfo reqInfo = null;
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
				
				aboveQuota1 = getAboveQuota(ctx, project==null?null:project.getId().toString(), expTypeID, costOrgID, excesssetinfos);
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
		//借款单处理
		for(int j=0;j<info.getLoanCheckEntries().size();j++){
			loan = info.getLoanCheckEntries().get(j);
			project = loan.getProject();
			expTypeID = loan.getSourceBillExpenseTypeId();
			costOrgID = loan.getSourceBillCostCenterId();
			amt = loan.getCheckAmount();

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
		
		for (int i = 0; i < info.getEntries().size(); i++) {
			entry = info.getEntries().get(i);
			aboveQuota = BigDecimal.ONE;
			project = entry.getProject();
			expTypeID = entry.getExpenseType().getId().toString();
			costOrgID = entry.getCostCenter().getId().toString();
			amt = entry.getAmount();
			
			if(entry.getSubmitUseAmt()!=null){
				amt = amt.subtract(entry.getSubmitUseAmt());
			}

			//封装KEY
			if(project==null || project.getId()==null){
				key = costOrgID;
			}else{
				key = project.getId()+costOrgID;
			}

			/*//设置 项目+成本中心 相同的  可用金额
			if(reqMap.get(key)!=null){
				amt = amt.add(reqMap.get(key));
			}
			reqMap.put(key, amt);*/
			
			//设置 项目+成本中心 相同的  可用金额
			if(reqMap.get(key)!=null){
				//校验是否超出了费用申请额范围
				//校验【费用报销单】费用清单分录中的“本位币申请金额”-【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”
				//必须小于等于关联【费用申请单】费用清单分录中的“本位币申请金额” * （1+超额比例）-“本位币已用金额”-已提交反写可用金额
				if(amt.compareTo(reqMap.get(key))>0){
					 throw new ExpAccException(new NumericExceptionSubItem("1111","项目："+project.getName()+"，费用归属部门："+entry.getCostCenter().getName()+"申请金额本位币（扣减冲借款）已经超过费用申请单本位币申请金额！"));
				}
			}
		}
	}
	
	protected void checkEntry(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BizAccountBillInfo info = (BizAccountBillInfo)model;
		BizAccountBillEntryInfo entry = null;
		BizAccountBillLoanCheckEntryInfo loan = null;
		/*PurContractEntryInfo purEntry = null;*/
		OtherExpenseBillEntryInfo otherEntry = null;
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
			
			if(entry.getSourceBillEntryID()!=null && entry.getSourceBillID()!=null && "E76173AD".equalsIgnoreCase(BOSUuid.read(entry.getSourceBillID()).getType().toString())){

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				otherEntry = OtherExpenseBillEntryFactory.getLocalInstance(ctx).getOtherExpenseBillEntryInfo(new ObjectUuidPK(entry.getSourceBillEntryID()),selector);
				
				ProjectInfo project = otherEntry.getProject();
				String expTypeID = otherEntry.getExpenseType().getId().toString();
				String costOrgID = otherEntry.getCostedDept().getId().toString();
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
				/*if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entry.getSourceBillID()).getType().toString())) {// 费用申请单分录*/					
				
				aboveQuota1 = getAboveQuota(ctx, project==null?null:project.getId().toString(), expTypeID, costOrgID, excesssetinfos);
				aboveQuota = aboveQuota.add(aboveQuota1.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP, 2));

				//校验是否超出了费用申请额范围
				//校验【费用报销单】费用清单分录中的“本位币申请金额”-【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”
				//必须小于等于关联【费用申请单】费用清单分录中的“本位币申请金额” * （1+超额比例）-“本位币已用金额”-已提交反写可用金额
				if(entry.getSubmitUseAmt()!=null){
					if(subAmt.compareTo(otherEntry.getAmountApproved().multiply(aboveQuota).subtract(otherEntry.getAmountUsed().subtract(entry.getSubmitUseAmt())))>0){
//							 throw new ExpAccException(new NumericExceptionSubItem("1111",project.getName()+"项目，"+entry.getExpenseType().getName()+"费用类型，"+entry.getCostCenter().getName()+"费用归属部门，本次申请金额（扣减冲借款）超出费用申请单的可申请金额("+otherEntry.getAmount().multiply(aboveQuota).subtract(otherEntry.getAmountUsed().subtract(entry.getSubmitUseAmt()))+")"));
						 throw new ExpAccException(new NumericExceptionSubItem("1111","分录"+entry.getSeq()+"申请金额本位币（扣减冲借款）（"+subAmt+"）已经超过费用申请单本位币申请金额（"+otherEntry.getAmount().multiply(aboveQuota).subtract(otherEntry.getAmountUsed().subtract(entry.getSubmitUseAmt()))+"）允许的超额比例（"+aboveQuota1+"%）！"));
					}

					//校验是否超出了预算额范围
					//校验（关联的【费用申请单】的费用清单分录中的 “本位币申请金额”-【费用申请单】的费用清单分录中的“本位币已用金额”-已提交反写可用金额-
					//（【费用报销单】费用清单分录中的“本位币申请金额”-【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”））
					//的绝对值必须小于等于该“项目+费用类型+费用归属部门”对应的预算可用金额
					/*if(otherEntry.getBudgetAmount().compareTo(otherEntry.getAmount().subtract(otherEntry.getAmountUsed().subtract(entry.getSubmitUseAmt())).subtract(entry.getAmount()).subtract(loanAmt))>0){
						 throw new ExpAccException(new NumericExceptionSubItem("1111","本次申请金额超出预算额范围"));
					}*/
				}else{
					if(subAmt.compareTo(otherEntry.getAmountApproved().multiply(aboveQuota).subtract(otherEntry.getAmountUsed()))>0){
//							 throw new ExpAccException(new NumericExceptionSubItem("1111",project.getName()+"，"+entry.getExpenseType().getName()+"，"+entry.getCostCenter().getName()+"，本次申请金额（扣减冲借款）超出费用申请单的可申请金额("+otherEntry.getAmount().multiply(aboveQuota).subtract(otherEntry.getAmountUsed())+")"));
						 throw new ExpAccException(new NumericExceptionSubItem("1111","分录"+entry.getSeq()+"申请金额本位币（扣减冲借款）（"+subAmt+"）已经超过费用申请单本位币申请金额（"+otherEntry.getAmount().multiply(aboveQuota).subtract(otherEntry.getAmountUsed())+"）允许的超额比例（"+aboveQuota1+"%）！"));
					}

					//校验是否超出了预算额范围
					//校验（关联的【费用申请单】的费用清单分录中的 “本位币申请金额”-【费用申请单】的费用清单分录中的“本位币已用金额”-
					//（【费用报销单】费用清单分录中的“本位币申请金额”-【冲借款】分录中的对应“项目+费用类型+费用归属部门”的“本次冲销金额”））
					//的绝对值必须小于等于该“项目+费用类型+费用归属部门”对应的预算可用金额
					/*if(otherEntry.getBudgetAmount().compareTo(otherEntry.getAmount().subtract(otherEntry.getAmountUsed()).subtract(entry.getAmount()).subtract(loanAmt))>0){
						 throw new ExpAccException(new NumericExceptionSubItem("1111","本次申请金额超出预算额范围"));
					}*/
				}
				/*}*/
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
	
	
	protected void _antiAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._antiAudit(ctx, billId);
	}
	
	
	
}
