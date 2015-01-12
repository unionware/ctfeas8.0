package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillException;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.ExcessSetCollection;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
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
				"", "�����ܳ�����ͬ���ý��");
	
	
	@Override
	protected IObjectPK _save(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		DailyLoanBillInfo loanBillInfo = (DailyLoanBillInfo) arg1;
		checkDailyAmount(arg0, loanBillInfo, 0);
//		if(loanBillInfo.getFirstCreateFrom() == 0 && loanBillInfo.getSourceBillId() != null
//				&& (!"".equals(loanBillInfo.getSourceBillId()))){
////			loanBillInfo.setName("");
//			loanBillInfo.setPayerName(null);
//			loanBillInfo.setPayerBankStr(null);
//			loanBillInfo.setPayerAccount(null);
//			loanBillInfo.setFirstCreateFrom(1);
//		}
		return super._save(arg0, arg1);
	}
	@Override
	protected IObjectPK _submit(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
		DailyLoanBillInfo loanBillInfo = (DailyLoanBillInfo) arg1;
		checkDailyAmount(arg0, loanBillInfo, 1);
		String sql = "";
		if(loanBillInfo.getId()!=null){
			sql = "update T_BC_DailyLoanBillEntry SET CFLastSubmitAmt = FAmount where FBillID='" + loanBillInfo.getId().toString() + "'";
			DbUtil.execute(arg0, sql);
		}
		
		return super._submit(arg0, arg1);
		
		
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
			{// �ɹ���ͬ
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
						dleInfo = lbeCol.get(i);
						lastSubAmt = dleInfo.getLastSubmitAmt();////
						laondAmt = laondAmtl = dleInfo.getAmount();
						laondAmt = laondAmt.subtract(lastSubAmt);///��ȥ�ϴ��ύ
						if(dleInfo.getSourceBillEntryID()!=null &&"E8D24BDF".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//�ɹ���ͬ��¼
							pureInfo = PurContractEntryFactory.getLocalInstance(arg0).getPurContractEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(pureInfo != null)
							{
								loandedAmt = pureInfo.getLoandAmount();//�ѽ����
								usedAmt = pureInfo.getUsedAmount();
								loandedAmt = loandedAmt.add(laondAmt);
								usedAmt = usedAmt.add(laondAmt);
								
								sql = "UPDATE T_SM_PurContractEntry SET CFLoandAmount = " + loandedAmt
										+ ",CFUsedAmount = " + usedAmt.toString() + " where fid ='" + pureInfo.getId() + "'";
								DbUtil.execute(arg0, sql);
//								�ɹ��������ε���
								if(pureInfo.getSourceBillEntryId()!=null &&"E8D24BDF".equalsIgnoreCase(BOSUuid.read(pureInfo.getSourceBillEntryId()).getType().toString())){
									rewriteOthExpense(arg0,pureInfo.getSourceBillEntryId().toString(),pureInfo.getSourceBillId(),laondAmt);
								}//oeeInfo
							}//pureInfo	
						}//dleInfo
//						���������ύ���
						sql = "UPDATE T_BC_DailyLoanBillEntry SET CFLoandAmount = " + laondAmtl + "' where fid = '" + dleInfo.getId().toString() + "'";
						DbUtil.execute(arg0, sql);
					}
				}
			}*/
			/*-----��׼���̲���д
			if ("E76173AD".equalsIgnoreCase(BOSUuid.read(loanBillInfo.getSourceBillId()).getType().toString()))
			{//��������
				oeInfo = OtherExpenseBillFactory.getLocalInstance(arg0).getOtherExpenseBillInfo(new ObjectUuidPK(
						loanBillInfo.getSourceBillId().toString()));
				
				lbeCol = loanBillInfo.getEntries();
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
						dleInfo = lbeCol.get(i);
						laondAmt = dleInfo.getAmount();
						if(dleInfo.getSourceBillEntryID()!=null && "F5C4E8C5".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//���뵥��¼
							oeeInfo = OtherExpenseBillEntryFactory.getLocalInstance(arg0).getOtherExpenseBillEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(oeeInfo != null)
							{
//								��д��������
								rewriteOthExpense(arg0,dleInfo.getSourceBillEntryID().toString(),dleInfo.getSourceBillID(),laondAmt);
							}	
						}
					}
				}
			}
			*/
		//}
	}
	
	
	private void checkDailyAmount(Context arg0 ,DailyLoanBillInfo loanBillInfo,int type )throws BOSException, EASBizException{
		DailyLoanBillEntryCollection lbeCol = new DailyLoanBillEntryCollection();
		DailyLoanBillEntryInfo dleInfo = new DailyLoanBillEntryInfo();
		PurContractEntryInfo pureInfo = new PurContractEntryInfo();
	
		OtherExpenseBillEntryInfo oeeInfo = new OtherExpenseBillEntryInfo();
		String projectid = "",expenseTypeid="",costcenterid = "";
		
		DateFormat df = new SimpleDateFormat("yyyy");
		int year;
		if(loanBillInfo.getBizReqDate()!=null){
			year = Integer.parseInt(df.format(loanBillInfo.getBizReqDate()));
		}else{
			year = Integer.parseInt(df.format(new Date()));
		}
		
		
		
		/*/����Ԥ��
		CostCenterOrgUnitInfo ccInfo = new CostCenterOrgUnitInfo();
		ProjectInfo pjInfo = new ProjectInfo();
		ExpenseTypeInfo etInfo = new ExpenseTypeInfo();
		BigDecimal bugetAmt = new BigDecimal(0); /*/
		
		BigDecimal laondAmt = new BigDecimal(0),conAmt = new BigDecimal(0),lastAmt =  new BigDecimal(0), 
					loandedAmt =  new BigDecimal(0) ,reimburseAmount = new BigDecimal(0),
					exRate = new BigDecimal(0);
		if (loanBillInfo.getSourceBillId() != null
				&& BOSUuid.read(loanBillInfo.getSourceBillId()).getType() != null) {
			if ("E76173AD".equalsIgnoreCase(BOSUuid.read(loanBillInfo.getSourceBillId()).getType().toString()))
			{// �������뵥
//				oeInfo = OtherExpenseBillFactory.getLocalInstance(arg0).getOtherExpenseBillInfo(new ObjectUuidPK(
//						loanBillInfo.getSourceBillId().toString()));
				
//				exRate = purInfo.getExchangeRate();////�������
				lbeCol = loanBillInfo.getEntries();
				if(lbeCol.size()>0){
					for(int i = 0 ; i < lbeCol.size() ;i ++){
//						ÿһ�в��ܳ������ý���ȱ�ݣ����ܲ��ԭ����¼
						dleInfo = lbeCol.get(i);
						laondAmt = dleInfo.getAmount().setScale(2);
//						if(dleInfo.getLastSubmitAmt()!=null){
//							lastAmt = dleInfo.getLastSubmitAmt().setScale(2);
//						}
						try {
							lastAmt = getLastSubAmount(arg0,dleInfo.getId().toString());
						} catch (SQLException e) {
							e.printStackTrace();
						}
//						laondAmt = laondAmt.subtract(lastAmt).setScale(2);
						if(dleInfo.getSourceBillEntryID()!=null && "F5C4E8C5".equalsIgnoreCase(BOSUuid.read(dleInfo.getSourceBillEntryID()).getType().toString()))
						{
							//���뵥��¼
							oeeInfo = OtherExpenseBillEntryFactory.getLocalInstance(arg0).getOtherExpenseBillEntryInfo(new ObjectUuidPK(dleInfo.getSourceBillEntryID()));
							if(oeeInfo != null)
							{
//								��ȡ�������
								if (oeeInfo.getExpenseType() != null) {
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
//								û���ҵ�3��Ψһ����Ӧ��Ŀ�����
								if(exRate==null  || exRate.equals(BigDecimal.ZERO)){
									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid);
								}else{
//									exRate = getAboveQuota(arg0,year,projectid,expenseTypeid);
								}
//								��Ŀ����𲻴��ڣ���ͬһ���������ĿΪ�յ�
								if(exRate.equals(BigDecimal.ZERO)){
									exRate = getAboveQuota(arg0,year,1,"",expenseTypeid,costcenterid);
								}
//								exRate = exRate.divide(new BigDecimal(100)).setScale(2);
//								exRate = exRate.add(new BigDecimal(1));
//								conAmt = oeeInfo.getAmount().setScale(2);  //��λ��������
								//����Ҫ��ĳɱ�λ�Һ˶Խ��   xulisha  2014-12-30 
								conAmt = oeeInfo.getAmountApproved().setScale(2,BigDecimal.ROUND_HALF_UP);  //��λ�Һ˶����
								loandedAmt = oeeInfo.getAmountUsed().setScale(2);//��λ�����ý��
//								loandedAmt.subtract(lastAmt).setScale(2);
								reimburseAmount = oeeInfo.getAmountBalance().setScale(2);//��λ�ҿ��ý��
								/*//xulisha  �ظ��ύʱ���߼������⣬����δ����Ҳ�ύ���ɹ�
								if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).setScale(2)
										.add(new BigDecimal(1)))).subtract(loandedAmt).setScale(2)) > 0)
								{
									 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
												"", "��¼" + (i+1) + "�����λ��(" + laondAmt.toString() + ")�Ѿ������������뵥��λ��������(" +
														conAmt.toString() +")����ĳ������(" + exRate.setScale(2).toString()  + "%)");
									 throw new DailyLoanBillException(LoanBiggerAmount);
								}*/
								if(lastAmt!=null){
									if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).add(new BigDecimal(1))).subtract(loandedAmt).add(lastAmt).setScale(2,BigDecimal.ROUND_HALF_UP))) > 0)
									{
										 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
													"", "��¼" + (i+1) + "�����λ��(" + laondAmt.toString() + ")�Ѿ������������뵥��λ��������(" +
															conAmt.toString() +")����ĳ������(" + exRate.setScale(2,BigDecimal.ROUND_HALF_UP).toString()  + "%)");
										 throw new DailyLoanBillException(LoanBiggerAmount);
									}
								}else{
									if(laondAmt.compareTo((conAmt.multiply(exRate.divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)))).subtract(loandedAmt).setScale(2,BigDecimal.ROUND_HALF_UP)) > 0)
									{
										 NumericExceptionSubItem LoanBiggerAmount = new NumericExceptionSubItem(
													"", "��¼" + (i+1) + "�����λ��(" + laondAmt.toString() + ")�Ѿ������������뵥��λ��������(" +
															conAmt.toString() +")����ĳ������(" + exRate.setScale(2,BigDecimal.ROUND_HALF_UP).toString()  + "%)");
										 throw new DailyLoanBillException(LoanBiggerAmount);
									}
								}
							}
							if(type==1){
								dleInfo.setLastSubmitAmt(dleInfo.getAmount());
							}
						}
/*Ԥ�������ͣ
						
						////�ж�Ԥ���Ƿ���
						ccInfo = dleInfo.getCostDept();
						pjInfo = dleInfo.getProject();
						etInfo = dleInfo.getExpenseType();
						////////����Ԥ��
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
										"", "����[" + ccInfo.getName() + "],��Ŀ[" + pjInfo.getName() + "],����[" + etInfo.getName() + "]"+"Ԥ�㲻��" );
							 throw new DailyLoanBillException(NoBugetAmt);
							}
						}
*/
					}
				}
			}
		}
	}
	
	
	
//	��д��������
	private void rewriteOthExpense(Context ctx,String entryId ,String billId,BigDecimal loandedAmt) throws EASBizException, BOSException{

		OtherExpenseBillInfo oeInfo  = new OtherExpenseBillInfo();
		OtherExpenseBillEntryInfo oeeInfo = new OtherExpenseBillEntryInfo();
		
		String sql = "";
		
		BigDecimal laondAmt = new BigDecimal(0),usedAmt = new BigDecimal(0),appAmt = new BigDecimal(0),
				   reimburseAmount = new BigDecimal(0),rate = new BigDecimal(0);
		
		oeInfo = OtherExpenseBillFactory.getLocalInstance(ctx).getOtherExpenseBillInfo(new ObjectUuidPK(billId));
		rate = oeInfo.getApplyAmount().divide(oeInfo.getAmount());//����
		oeeInfo = OtherExpenseBillEntryFactory.getLocalInstance(ctx).getOtherExpenseBillEntryInfo(new ObjectUuidPK(entryId));
		
		usedAmt = oeeInfo.getAmountUsed();
		usedAmt = usedAmt.add(loandedAmt);//���ý��
		reimburseAmount = oeeInfo.getAmountBalance();//���ý��
		appAmt = oeeInfo.getAmount();//������
		
		reimburseAmount = appAmt.subtract(usedAmt);
		usedAmt = usedAmt.add(laondAmt);
		sql = "UPDATE T_BC_OtherExpenseBillEntry SET FAmountUsed = " + usedAmt ;
		DbUtil.execute(ctx, sql);
		sql = "UPDATE T_BC_OtherExpenseBillEntry SET FAmountBalance " + reimburseAmount + ","
			+ " FAmountBalanceOri = " + reimburseAmount.divide(rate) + ",\n" + 
			  " FAmountUsedOri =  " + usedAmt.divide(rate) + ",\n" + 
			  " ";
		DbUtil.execute(ctx, sql);
//		��ͷ
		usedAmt = oeInfo.getAmountUsed();
		usedAmt = usedAmt.subtract(loandedAmt);//���ý��
		appAmt = oeInfo.getAmount();//������
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
		//TODO  ���ҵ�����Ƿ����ô˹���
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
//							�ɹ��������ε���
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

	/**
	 * ��ȡ��ǰ��ͬ��Ӧ����ݵ�,�������ͳ�������
	 * 
	 * @param ctx
	 * @param year
	 * @return ExcessSetCollection
	 * @throws BOSException
	 */
	private ExcessSetCollection getExcessSetInfos(Context ctx, int year, String expTypeid)
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
		filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnable", 1, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("expenseType", expTypeid, CompareType.EQUALS));
		filter.setMaskString("#0 AND #1 and #3");
		viewInfo.setFilter(filter);
		viewInfo.setSelector(selector);
		ExcessSetCollection excesssetInfos = ExcessSetFactory.getLocalInstance(
				ctx).getExcessSetCollection(viewInfo);

		return excesssetInfos;
	}
	
	/**
	 * ��ȡ�������
	 * ��Ŀ+��������
	 * @return ��ͷ����
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
	 * ��ȡ�������;��ȡ��ĿΪ�յ�������֯����
	 * ��Ŀ+��������+�ɱ�����
	 * @return ��ͷ����
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
//				û�������֯+���͵�
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
	 * ��ȡ�������
	 * ��Ŀ+��������+�ɱ�����
	 * @return ��ͷ����
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
	 * ��ȡ�������
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
					if(excesssetInfos.get(i).getProject()!= null){
					setprojectid = excesssetInfos.get(i).getProject().getId()
							.toString();
					}
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
	 * �жϵ�ǰ��Ŀ�Ƿ�����ڳ������������
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
			CreateK3PayNoticeBillFacadeFactory.getLocalInstance(ctx).createByDailyLoanBill(id);
		}
}
