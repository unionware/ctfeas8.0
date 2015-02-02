package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
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
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.util.NumericExceptionSubItem;

public class BizAccountBillControllerBeanEx extends
		BizAccountBillControllerBean {
	
	Map<String, BigDecimal> oldReqMap = null;

	public BizAccountBillControllerBeanEx() {
	}
	
	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		
		initOldReqMap(ctx, model); //�ȴ�����ϴη�д���
		
		IObjectPK pk =  super._submit(ctx, model);
		
		afterSubmit(ctx, model);
		
		return pk;
	}
	
	@Override
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		
		initOldReqMap(ctx, model); //�ȴ�����ϴη�д���
		
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
						entryInfo.getSourceBillID()).getType().toString())) {// �ɹ���ͬ
					//��д�ɹ���ͬ��¼�����ѹ������ñ���������λ�ң���=ԭ���ѹ������ñ���������λ�ң���-�����ñ������������嵥��¼�еġ���λ�������
					//����λ�����ý�=ԭ����λ�����ý�-�����ñ������������嵥��¼�еġ���λ������� +�������¼�еĶ�Ӧ����Ŀ+��������+���ù������š��ġ����γ�����
					sSQL.append(" Update T_SM_PurContractEntry SET CFLoandAmount=isnull(CFContractAmount,0)-isnull("+entryInfo.getSubmitAmt()+",0),").append("\r\n");
					sSQL.append(" CFUsedAmount=isnull(CFUsedAmount,0)-isnull("+entryInfo.getSubmitUseAmt()+",0) ").append("\r\n");
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				
				//����λ�����ý�=ԭ����λ�����ý�-�����ñ������������嵥��¼�еġ���λ������� +�������¼�еĶ�Ӧ����Ŀ+��������+���ù������š��ġ����γ�����
				//����λ�ҿ�����=����λ�������-����λ�����ý�����ԭ�����ý�=����λ�����ý�/���ʡ���ԭ�ҿ�����=����λ�ҿ�����/����
				sSQL = new StringBuffer();
				sSQL.append(" Update T_BC_OtherExpenseBillEntry SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0), ").append("\r\n");
				sSQL.append(" FAmountUsedOri=(isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0))*isnull(FExchangeRate,0), ").append("\r\n");
				sSQL.append(" FAmountBalanceOri=(isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0))*isnull(FExchangeRate,0) ").append("\r\n");

				if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �������뵥��¼
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");
					
					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �ɹ���ͬ
					sSQL.append(" WHERE FID=select FsourceBillEntryId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				sSQL = new StringBuffer();
				
				//�������뵥��ͷ�ġ����ý�������������Ҫ���¸��ݷ�¼���ܣ��ȼ�ȥ�ϴ��ύ��д���Ľ��ڼ��ϱ�����Ҫ��д�Ľ�
				sSQL.append(" Update T_BC_OtherExpenseBill SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0) ").append("\r\n");
				
				if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �������뵥��¼
					sSQL.append(" WHERE FID='" + entryInfo.getSourceBillID() + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �ɹ���ͬ
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
						entryInfo.getSourceBillID()).getType().toString())) {// �ɹ���ͬ
					//��д�ɹ���ͬ��¼�����ѹ������ñ���������λ�ң���=ԭ���ѹ������ñ���������λ�ң���+�����ñ������������嵥��¼�еġ���λ�������
					//��λ�����ý�=ԭ����λ�����ý� +(�����ñ������������嵥��¼�еġ���λ������� -�������¼�еĶ�Ӧ����Ŀ+��������+���ù������š��ġ����γ�����)��
					//���ȼ�ȥ�ϴ��ύ��д���Ľ��ڼ��ϱ�����Ҫ��д�Ľ�
					sSQL.append(" Update T_SM_PurContractEntry SET CFLoandAmount=isnull(CFContractAmount,0)-isnull("+entryInfo.getSubmitAmt()+",0)+(" + entryInfo.getAmount() + "),").append("\r\n");
					sSQL.append(" CFUsedAmount=isnull(CFUsedAmount,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+(" + useAmt + ") ").append("\r\n");
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				
				//��д�������뵥��¼������λ�����ý�=ԭ����λ�����ý�+(�����ñ������������嵥��¼�еġ���λ������� -�������¼�еĶ�Ӧ����Ŀ+��������+���ù������š��ġ����γ����� )��
				//����λ�ҿ�����=����λ�������-����λ�����ý�����ԭ�����ý�=����λ�����ý�/���ʡ���ԭ�ҿ�����=����λ�ҿ�����/���ʣ�
				//���ȼ�ȥ�ϴ��ύ��д���Ľ��ڼ��ϱ�����Ҫ��д�Ľ�
				sSQL = new StringBuffer();
				sSQL.append(" Update T_BC_OtherExpenseBillEntry SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "), ").append("\r\n");
				sSQL.append(" FAmountUsedOri=(isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "))*isnull(FExchangeRate,0), ").append("\r\n");
				sSQL.append(" FAmountBalanceOri=(isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "))*isnull(FExchangeRate,0) ").append("\r\n");

				//Դ���Ƿ������뵥�Ļ�������Ҫ����д���ñ�׼��д����
				/*if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �������뵥��¼
					sSQL.append(" WHERE FID='" + sEntryID + "'").append("\r\n");
					
					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else */if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �ɹ���ͬ
					sSQL.append(" WHERE FID=select FsourceBillEntryId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				sSQL = new StringBuffer();
				
				//�������뵥��ͷ�ġ����ý�������������Ҫ���¸��ݷ�¼���ܣ��ȼ�ȥ�ϴ��ύ��д���Ľ��ڼ��ϱ�����Ҫ��д�Ľ�
				sSQL.append(" Update T_BC_OtherExpenseBill SET FAmountUsed=isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + "),").append("\r\n");
				sSQL.append(" FAmountBalance=isnull(FAmount,0)-isnull(FAmountUsed,0)-isnull("+entryInfo.getSubmitUseAmt()+",0)+("+ useAmt + ") ").append("\r\n");
				
				//Դ���Ƿ������뵥�Ļ�������Ҫ����д���ñ�׼��д����
				/*if ("E76173AD".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �������뵥��¼
					sSQL.append(" WHERE FID='" + entryInfo.getSourceBillID() + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}else */if("78B50853".equalsIgnoreCase(BOSUuid.read(entryInfo.getSourceBillID()).getType().toString())) {// �ɹ���ͬ
					sSQL.append(" WHERE FID=select FsourceBillId from T_SM_PurContractEntry where FID='" + sEntryID + "'").append("\r\n");

					if (sSQL.length() > 0) {
						DbUtil.execute(ctx, sSQL.toString());
					}
				}
				
				sSQL = new StringBuffer();
				//���±����ύ���
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
	
	protected void initOldReqMap(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		oldReqMap = new HashMap<String, BigDecimal>();
		
		if(model==null || ((BizAccountBillInfo)model).getId()==null){
			return;
		}
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		sic.add("ReqCheckEntries.*");
		BizAccountBillInfo bill = getBizAccountBillInfo(ctx, new ObjectUuidPK(((BizAccountBillInfo)model).getId().toString()), sic);
		BizAccountBillReqCheckEntryInfo reqInfo = null;
		OtherExpenseBillEntryInfo otherEntry = null;
		String key = null;
		BigDecimal amt = BigDecimal.ZERO;
		
		if(bill!=null && bill.getState()!=StateEnum.DRAFT){
			for(int i=0;i<bill.getReqCheckEntries().size();i++){
				reqInfo = bill.getReqCheckEntries().get(i);
				otherEntry = OtherExpenseBillEntryFactory.getLocalInstance(ctx).getOtherExpenseBillEntryInfo(new ObjectUuidPK(reqInfo.getSourceBillEntryID()));
				
				amt = reqInfo.getCheckAmount();
				
				//��װKEY
				if(otherEntry.getProject()==null || otherEntry.getProject().getId()==null){
					key = reqInfo.getSourceBillCostCenterId();
				}else{
					key = otherEntry.getProject().getId()+reqInfo.getSourceBillCostCenterId();
				}

				//���� ��Ŀ+�ɱ����� ��ͬ��  ���ý��
				if(oldReqMap.get(key)!=null){
					amt = amt.add(oldReqMap.get(key));
				}
				oldReqMap.put(key, amt);
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
		ExcessSetCollection excesssetinfos = ExcessSetFactory.getLocalInstance(ctx).getExcessSetInfos(year);//��ȡ������ݵĳ����������
		
		BizAccountBillReqCheckEntryInfo reqInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		
		Map<String, BigDecimal> reqMap = new HashMap<String, BigDecimal>();
		String key = null;
		ProjectInfo project = null;
		String expTypeID = null;
		String costOrgID = null;
		BigDecimal amt = BigDecimal.ZERO;
		//�������뵥����
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
				//��ȡ���������ĳ������ֵ
				aboveQuota1 = ExcessSetFactory.getLocalInstance(ctx).getAboveQuota(project==null?null:project.getId().toString(), expTypeID, costOrgID, excesssetinfos);
				aboveQuota = aboveQuota.add(aboveQuota1.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP, 2));
				
				amt = otherEntry.getAmountApproved().multiply(aboveQuota).subtract(otherEntry.getAmountUsed());
				
				//��װKEY
				if(project==null || project.getId()==null){
					key = costOrgID;
				}else{
					key = project.getId()+costOrgID;
				}

				//���� ��Ŀ+�ɱ����� ��ͬ��  ���ý��
				if(reqMap.get(key)!=null){
					amt = amt.add(reqMap.get(key));
				}
				reqMap.put(key, amt);
			}
		}
		//������
		for(int j=0;j<info.getLoanCheckEntries().size();j++){
			loan = info.getLoanCheckEntries().get(j);
			project = loan.getProject();
			expTypeID = loan.getSourceBillExpenseTypeId();
			costOrgID = loan.getSourceBillCostCenterId();
			amt = loan.getCheckAmount();

			//��װKEY
			if(project==null || project.getId()==null){
				key = costOrgID;
			}else{
				key = project.getId()+costOrgID;
			}

			//���� ��Ŀ+�ɱ����� ��ͬ��  ���ý��
			if(reqMap.get(key)!=null){
				amt = amt.add(reqMap.get(key));
			}
			reqMap.put(key, amt);
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
			costOrgID = entry.getCostCenter().getId().toString();
			amt = entry.getAmount();

			//��װKEY
			if(project==null || project.getId()==null){
				key = costOrgID;
			}else{
				key = project.getId()+costOrgID;
				str = "��Ŀ��"+project.getName()+"����";
			}
			str = str + "���ù������ţ�"+entry.getCostCenter().getName()+"��";

			//���� ��Ŀ+�ɱ����� ��ͬ��  ����������
			if(accMap.get(key)!=null){
				amt = amt.add(accMap.get(key));
			}
			accMap.put(key, amt);
			
			strMap.put(key, str);
		}
		
		if(info.getReqCheckEntries().size()>0){
			/*//���� ��Ŀ+�ɱ����� ��ͬ��  ���ý��
			if(accMap.size()>0){
				//У���Ƿ񳬳��˷�������Χ
				//У�顾���ñ������������嵥��¼�еġ���λ�������-�������¼�еĶ�Ӧ����Ŀ+��������+���ù������š��ġ����γ�����
				//����С�ڵ��ڹ������������뵥�������嵥��¼�еġ���λ������� * ��1+���������-����λ�����ý�-���ύ��д���ý��
				if(amt.compareTo(reqMap.get(key))>0){
					 throw new ExpAccException(new NumericExceptionSubItem("1111",str+"�ı�λ�������"+amt+"���ѳ��������뱨����"+reqMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"����"));
				}
			}else if(amt!=null && amt.compareTo(BigDecimal.ZERO)>0){
				throw new ExpAccException(new NumericExceptionSubItem("1111",str+"�ı�λ�������"+amt+"���ѳ��������뱨����0����"));
			}*/
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
			
			//���� ��Ŀ+�ɱ����� ��ͬ��  ���ý��
			for(Iterator<String> it=accMap.keySet().iterator();it.hasNext();){
				key = it.next();
				if(reqMap.get(key)!=null){
					//У���Ƿ񳬳��˷�������Χ
					//У�顾���ñ������������嵥��¼�еġ���λ�������-�������¼�еĶ�Ӧ����Ŀ+��������+���ù������š��ġ����γ�����
					//����С�ڵ��ڹ������������뵥�������嵥��¼�еġ���λ������� * ��1+���������-����λ�����ý�-���ύ��д���ý��
					if(accMap.get(key).compareTo(reqMap.get(key))>0){
						 throw new ExpAccException(new NumericExceptionSubItem("1111",strMap.get(key)+"�ı�λ�������"+accMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"���ѳ��������뱨����"+reqMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"����"));
					}
				}else if(accMap.get(key)!=null && accMap.get(key).compareTo(BigDecimal.ZERO)>0){
					throw new ExpAccException(new NumericExceptionSubItem("1111",strMap.get(key)+"�ı�λ�������"+accMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP)+"���ѳ��������뱨����0����"));
				}
			}
		}
	}
	
	protected void _antiAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._antiAudit(ctx, billId);
	}
	
	
	
}
