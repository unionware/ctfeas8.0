package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.person.BankInfoCollection;
import com.kingdee.eas.basedata.person.BankInfoFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BaseDataEnum;
import com.kingdee.eas.cp.bc.BaseDataMatchFactory;
import com.kingdee.eas.cp.bc.BizAccountBillAccountEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillCollection;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizAccountBillLoanCheckEntryCollection;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillCollection;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeSubjectMappingCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeSubjectMappingFactory;
import com.kingdee.eas.cp.bc.IBaseDataMatch;
import com.kingdee.eas.cp.bc.ReceiveObjectEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillCollection;
import com.kingdee.eas.cp.bc.TravelAccountBillFactory;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.cp.bc.app.dbutil.K3StatusReWriteUtil;
import com.kingdee.eas.cp.bc.app.dbutil.K3VoucherDBUtil;
import com.kingdee.eas.cp.bc.app.dbutil.K3VoucherEasNumInfo;
import com.kingdee.eas.cp.bc.app.dbutil.K3WebAccConfigInfos;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;

public class Bc2K3VoucherFacadeControllerBean extends AbstractBc2K3VoucherFacadeControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9061785138787205545L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.Bc2K3VoucherFacadeControllerBean");
    
    /**
     * ��̨��������K3ƾ֤�м������
     */
    protected void _backRun(Context ctx)throws BOSException, EASBizException {
    	//���ñ���
    	
		updateVoucherStatus(ctx);
    	try {
			runAccountBills(ctx);
		} catch (EASBizException e) {
			e.printStackTrace();
			throw e;
		} catch (BOSException e) {
			e.printStackTrace();
			throw e;
		}
    	
    	try {
			runTravelAccountBills(ctx);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	try {
			runDailyLoanBills(ctx);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	try {
			runEvectionLoanBills(ctx);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
    private void updateVoucherStatus(Context ctx) throws BOSException{
    	//���ñ���
    	try {
			updateVoucherStatusByType(ctx, new BizAccountBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("���ñ�������ƾ֤��Ϣ����");
		}
    	//���ñ���
    	try {
			updateVoucherStatusByType(ctx, new TravelAccountBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("���ñ�������ƾ֤��Ϣ����");
		}
    	//���
    	try {
			updateVoucherStatusByType(ctx, new DailyLoanBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("��� ����ƾ֤��Ϣ����");
		}
    	//������
    	try {
			updateVoucherStatusByType(ctx, new EvectionLoanBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("���������ƾ֤��Ϣ����");
		}
    }
    
    
    private void updateVoucherStatusByType(Context ctx,BizCollBillBaseInfo info) throws BOSException, SQLException{
    	 EntityViewInfo  view = getBillFilter(info.getBOSType(),1);
    	 IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
    	 AbstractObjectCollection  coll  = (AbstractObjectCollection)iDynamicObject.getCollection(info.getBOSType(), view);
     	
    	 if(coll.size()==0){
    		 return ;
    	 }
    	 Set<String> revSet = K3StatusReWriteUtil.getBillNum(coll);
    	
    	 List<K3VoucherEasNumInfo>  list = K3StatusReWriteUtil.getVoucherInfo(ctx, revSet);
     	 String tableName  = K3VoucherDBUtil.getEntityMainTable(ctx, info.getBOSType());
     	 K3StatusReWriteUtil.updateEasBillByVoucherInfo(ctx,tableName,"CFVoucherNumber","CFK3VoucherStatus", list);
    }
    
    
    /**
     * ����������
     * @param ctx
     * @throws BOSException
     * @throws EASBizException
     */
    private void runAccountBills(Context ctx) throws BOSException, EASBizException{
    	AbstractObjectCollection coll = getBizAccountColl(ctx);
    	
    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> headList = new ArrayList<Map<String,Object>>();
    	IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	BizCollBillBaseInfo info =null;
    	
    	for(int i=0;i<coll.size();i++){
    		info = (BizCollBillBaseInfo)coll.getObject(i);
    		try{
    			addAccountABillRows2(ctx, info, iBaseDataMatch, rowList, headList);
    		}catch(Exception e){
    			//check 
    		}
    	}
    	
    	try {
			K3VoucherDBUtil.insertVoucher(ctx, rowList,headList,new BizAccountBillInfo().getBOSType());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
    }
    
    /**
     * ����������
     * @param ctx
     * @throws BOSException
     * @throws EASBizException
     */
    private void runTravelAccountBills(Context ctx) throws BOSException, EASBizException{
    	AbstractObjectCollection coll = getTravelAccountColl(ctx);
    	
    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> headList = new ArrayList<Map<String,Object>>();
    	IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	BizCollBillBaseInfo info =null;
    	for(int i=0;i<coll.size();i++){
    		info = (BizCollBillBaseInfo)coll.getObject(i);
    		addAccountABillRows2(ctx, info, iBaseDataMatch, rowList, headList);
    	}
    	if(coll.size()>0){
	    	try {
				K3VoucherDBUtil.insertVoucher(ctx, rowList,headList,new TravelAccountBillInfo().getBOSType());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
    	}
		
    }
    
    
    
    /**
     * �������
     * @param ctx
     * @throws BOSException
     * @throws EASBizException
     */
    private void runDailyLoanBills(Context ctx) throws BOSException, EASBizException{
    	AbstractObjectCollection coll = getDailyLoanColl(ctx);
    	
    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
    	IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	BizCollBillBaseInfo info =null;
    	for(int i=0;i<coll.size();i++){
    		info = (BizCollBillBaseInfo)coll.getObject(i);
    		addLoanABillRows2(ctx, info, iBaseDataMatch, rowList);
    	}
    	if(coll.size()>0){
	    	try {
				K3VoucherDBUtil.insertVoucher(ctx, rowList,null,new DailyLoanBillInfo().getBOSType());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
    	}
		
    }
    
    
    /**
     * ����������
     * @param ctx
     * @throws BOSException
     * @throws EASBizException
     */
    private void runEvectionLoanBills(Context ctx) throws BOSException, EASBizException{
    	AbstractObjectCollection coll = getEvectionLoanColl(ctx);
    	
    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
    	IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	BizCollBillBaseInfo info =null;
    	for(int i=0;i<coll.size();i++){
    		info = (BizCollBillBaseInfo)coll.getObject(i);
    		addLoanABillRows2(ctx, info, iBaseDataMatch, rowList);
    	}
    	
    	if(coll.size()>0){
	    	try {
				K3VoucherDBUtil.insertVoucher(ctx, rowList,null,new EvectionLoanBillInfo().getBOSType());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
    	}
		
    }
    
    
    private AbstractObjectCollection getBizAccountColl(Context ctx) throws BOSException{
    	BizAccountBillInfo info = new BizAccountBillInfo();
    	BizAccountBillCollection coll = BizAccountBillFactory.getLocalInstance(ctx).getBizAccountBillCollection(getBillFilter(info.getBOSType()));
    	return coll;
    }
    
    private AbstractObjectCollection getDailyLoanColl(Context ctx) throws BOSException{
    	DailyLoanBillInfo info = new DailyLoanBillInfo();
    	DailyLoanBillCollection coll = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillCollection(getBillFilter(info.getBOSType()));
    	return coll;
    }
    
    
    private AbstractObjectCollection getTravelAccountColl(Context ctx) throws BOSException{
    	TravelAccountBillInfo info = new TravelAccountBillInfo();
    	TravelAccountBillCollection coll = TravelAccountBillFactory.getLocalInstance(ctx).getTravelAccountBillCollection(getBillFilter(info.getBOSType()));
    	
    	return coll;
    }
    
    private AbstractObjectCollection getEvectionLoanColl(Context ctx) throws BOSException{
    	EvectionLoanBillInfo info = new EvectionLoanBillInfo();
    	EvectionLoanBillCollection coll = EvectionLoanBillFactory.getLocalInstance(ctx).getEvectionLoanBillCollection(getBillFilter(info.getBOSType()));
    	return coll;
    }
    
    
    
    /**
     * ����һ����������
     * @param ctx
     * @param info
     * @param iBaseDataMatch
     * @param rowList
     * @param headList
     * @throws EASBizException
     * @throws BOSException
     */
    private void addAccountABillRows2(Context ctx,BizCollBillBaseInfo info,IBaseDataMatch iBaseDataMatch,List<Map<String,Object>> rowList,List<Map<String,Object>> headList) throws EASBizException, BOSException{
    	String orgCode = null;
    	AbstractObjectCollection  entrys = (AbstractObjectCollection)info.get("entries");
    	int rowType =getRowType(ctx,info);   
    	int typeId=getTypeId(ctx, info, rowType); ///
    	ReceiveObjectEnum  reenum =  getReceivObj(info);
		if(reenum==null){
			throw new BOSException("�տ�������");
		}
    	orgCode = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.ORGUNIT, info.getApplierCompany().getNumber()); //check this again  ����֯��Ӧ��K3��֯��������
    	Map<String,Object> headRow = new LinkedHashMap<String,Object>();
    	orgCode = orgCode==null?"":orgCode;
    	//����
    	if(rowType==1 && typeId==0){ // ��Ʊ���<=�������
    		//no debit 
    	}else{
    		addDebitObjByRowType(ctx, rowType, reenum, headRow, info, iBaseDataMatch);
    		headList.add(headRow);
    	}
    	
    	CoreBillEntryBaseInfo entry =null;
    	BigDecimal taxTotalAmt = getLocalAllTaxAmt(ctx, info); //�ܱ���
    	BigDecimal encashedAmt = getTotalAmtEncashed(ctx, info);  //�ָ�
    	
    	String payerName = getPayerName(ctx, info,reenum);
    	String payerNum = getPayerNum(ctx, info,reenum);
    	for(int entryIndex=0;entryIndex<entrys.size();entryIndex++){
    		Map<String,Object> row = new LinkedHashMap<String,Object>();
    		entry = (CoreBillEntryBaseInfo)entrys.getObject(entryIndex);
    		/*// 
    		 * if(rowType==1 && entryIndex>0 && invoiceAmt.compareTo(apprAmt)!=0){ //�޳��,��Ʊ���!=�������
    			break;
    		}*/
    		addEntrySameObjByRowType2(ctx, rowType,typeId, reenum, row, orgCode, info, iBaseDataMatch, entry,payerName);
    		addSuppValByReceive(ctx, info, rowType, typeId, reenum, row, payerNum, payerName);
    		rowList.add(row);
    		
    		//Map<String,Object> newRow = addEntryDiffObjByRowType2(ctx, rowType, row, info, iBaseDataMatch, entry,invoiceAmt,apprAmt);
    		/*if(newRow!=null){
				rowList.add(newRow);
			}*/
    		if(typeId==0 &&  entryIndex==entrys.size()-1  && taxTotalAmt.compareTo(encashedAmt)<0){
    			Map<String,Object> newRow = createOtherRow(ctx, info, iBaseDataMatch, reenum, taxTotalAmt,
						encashedAmt, payerName, entrys.size(), row);
    			rowList.add(newRow);
    		}
    	}
    	
    }

	private Map<String,Object> createOtherRow(Context ctx, BizCollBillBaseInfo info,
			IBaseDataMatch iBaseDataMatch, ReceiveObjectEnum reenum,
			BigDecimal taxTotalAmt, BigDecimal encashedAmt, String suppName,
			int entryIndex, Map<String, Object> row) throws BOSException,
			EASBizException {
		Map<String,Object> newRow = copyRow(row);
		newRow.put("Findex", entryIndex+1);
		if(ReceiveObjectEnum.personal.equals(reenum)){
			newRow.put("fAcctNum",K3WebAccConfigInfos.getDebitPersonAccount(ctx)); 
		}else{
			newRow.put("fAcctNum",K3WebAccConfigInfos.getDebitSupplierAccount(ctx)); 
		}
		
		newRow.put("FEmpNum",info.getApplier().getNumber() ); 
		newRow.put("FEmpName",info.getApplier().getName());
		
		newRow.put("FSpecialNum", "03.01");
		String reqDept = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.DEPARTMENT, info.getOrgUnit().getNumber());
		newRow.put("FDeptNum", reqDept==null?"":reqDept);
		newRow.put("FAmt", encashedAmt.subtract(taxTotalAmt));
		newRow.put("FTaxAmt", BigDecimal.ZERO);
		newRow.put("fexp",info.getNumber()+suppName);   // ����+�տλ+""
		
		return newRow;
	}
    
    
    /**
     *  ��¼��ͬ�ֶ�
     * @author michael
     * @date: 2015-1-8
     * @param ctx
     * @param rowType  ������
     * @param typeId   ����
     * @param reenum   �տ��������
     * @param row		��
     * @param orgCode  ��֯
     * @param info		����
     * @param iBaseDataMatch  ���չ�ϵ
     * @param entry				��ǰ��¼
     * @param payerName			�տ��������
     * @throws BOSException
     * @throws EASBizException 
     * @exception 
     * @description:
     */
    private void addEntrySameObjByRowType2(Context ctx,int  rowType,int typeId,ReceiveObjectEnum  reenum ,Map<String,Object> row,
    		String orgCode,BizCollBillBaseInfo info,IBaseDataMatch iBaseDataMatch,CoreBillEntryBaseInfo entry,String payerName) throws BOSException, EASBizException
    {
    	row.put("FTypeID",Integer.valueOf(typeId));  
		row.put("Findex",entry.getInt("seq"));
		row.put("FTMQNo",info.getNumber());
		
		row.put("FCompanyNum",orgCode);
		row.put("ftransDate",info.getBizReqDate());
		row.put("fattchment",0);
		
		CompanyVoucherNumEntryInfo cVoucherEntry = (CompanyVoucherNumEntryInfo) info.get("voucherNum");
		if(cVoucherEntry!=null && cVoucherEntry.getVoucherNumber()!=null){
			row.put("FGroupID", cVoucherEntry.getVoucherNumber());
		}else{
			row.put("FGroupID", "");
		}
		//row.put("fAcctNum", "");  //ָ��λ��
		String k3AcctNum = getAcctByExpenseType(ctx, info, iBaseDataMatch, entry);
		row.put("fAcctNum",k3AcctNum==null?"":k3AcctNum); 
		
		//String payerName = payerName;
		//����  ռλ
		row.put("FSupplyNum","");
		row.put("FSupplyName","");  //�տ�������
		
		/*
		if(ReceiveObjectEnum.personal.equals(reenum)){
			//row.put("fAcctNum",K3WebAccConfigInfos.getDebitPersonAccount(ctx));
    		row.put("FSupplyNum","");
    		row.put("FSupplyName","");  //�տ�������
    		String easPersonNum = info.getApplier().getNumber(); //getPersonNum(ctx, info.getPayerAccount(), info.getPayerName());
    		if(easPersonNum==null){
    			row.put("FEmpNum","" );	
    		}else{
    			//String k3PersonNum = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.PERSON, easPersonNum);
	    		if(k3PersonNum==null){
	    			k3PersonNum = ""; //k3PersonNum = easPersonNum;
	    			logger.warn("+++++++K3PERSON is null easPersonNum is  "+easPersonNum);
	    		}
    			row.put("FEmpNum",easPersonNum );
    		}
    		//suppName =info.getApplier().getName();
    		//row.put("FEmpName",suppName);
    		
    	}else {
    		//row.put("fAcctNum",K3WebAccConfigInfos.getDebitSupplierAccount(ctx));
    		//suppName = getSupplierNum(ctx, info.getPayerAccount(), info.getPayerName());
    		row.put("FSupplyNum",info.getPayerName()==null?"":"SUP."+info.getPayerName());  
    		row.put("FSupplyName",suppName==null?"":suppName);  //�տ�������
    		//row.put("FEmpNum",""); 
    		//row.put("FEmpName","");
    	} */
		//TODO  
		PersonInfo ps = (PersonInfo)entry.getObjectValue("person");
		row.put("FEmpNum",ps==null?"":ps.getNumber()); 
		row.put("FEmpName",ps==null?"":ps.getName());
		
		/*if(ReceiveObjectEnum.supplier.equals(reenum)){
			//payerName = payerName;
		} else{
			payerName = null;//TODO  
		}*/
		
		ProjectInfo project =(ProjectInfo )entry.getObjectValue("project");
		if(project!=null){
			String k3FSpecialNum = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.PROJECT, project.getNumber());
			row.put("FSpecialNum",k3FSpecialNum==null?"":k3FSpecialNum);
		}else{
			row.put("FSpecialNum","03.01");
		}
		
		CostCenterOrgUnitInfo org=  (CostCenterOrgUnitInfo)entry.getObjectValue("costCenter");
		String reqDept = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.DEPARTMENT, org==null?"":org.getNumber());
		row.put("FDeptNum",reqDept==null?"":reqDept);
		
		row.put("FCyNum",info.getCurrencyType().getIsoCode());  //((CurrencyInfo )entry.getObjectValue("currencyType"))
		BigDecimal taxLocal = entry.getBigDecimal("taxLocal")==null?BigDecimal.ZERO:entry.getBigDecimal("taxLocal");
		BigDecimal noTaxLocal = entry.getBigDecimal("noTaxAmtLocal")==null?BigDecimal.ZERO:entry.getBigDecimal("noTaxAmtLocal");
		//row.put("FAmt",null);  //ָ��λ��
		row.put("FAmt",noTaxLocal==null?"":noTaxLocal); 
		row.put("FTaxAmt",taxLocal==null?BigDecimal.ZERO:taxLocal); 
		String purPose = entry.getString("purpose");
		row.put("fexp",info.getNumber()+payerName+(purPose==null?"":purPose));   // ����+�տλ+�����嵥�����;
		
		/*row.put("FBigArea",null);
		row.put("Farea",null);
		row.put("FSmallArea",null);*/
		
		row.put("FIsSyn",Boolean.FALSE);
		row.put("FVouNum",""); 
    }
    
    
    private void addSuppValByReceive(Context ctx,BizCollBillBaseInfo info,int  rowType,int typeId,
    		ReceiveObjectEnum  reenum ,Map<String,Object> row,String suppNum,String suppName) throws EASBizException, BOSException{
    	if(ReceiveObjectEnum.personal.equals(reenum)){
    		row.put("FSupplyNum","");
    		row.put("FSupplyName","");  //�տ�������
    	}else {
    		row.put("FSupplyNum","SUP."+suppNum);  
    		row.put("FSupplyName",suppName);  //�տ�������
    	} 
    }
    
    private String getPayerName(Context ctx,BizCollBillBaseInfo info,ReceiveObjectEnum  reenum) throws EASBizException, BOSException{
    	if(info instanceof BizAccountBillInfo){
    		BizAccountBillAccountEntryInfo  entry = ((BizAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry==null){
    			return null;
    		}
    		return entry.getPayerName();
    	}else if(info instanceof TravelAccountBillInfo){
    		TravelAccountBillAccountEntryInfo  entry = ((TravelAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry==null){
    			return null;
    		}
    		return entry.getPayerName();
    	}
    	
    	return null;
    }
    private String getPayerNum(Context ctx,BizCollBillBaseInfo info,ReceiveObjectEnum  reenum) throws EASBizException, BOSException{
    	String payAcct = null;
    	String payNum = null;
    	if(info instanceof BizAccountBillInfo){
    		BizAccountBillAccountEntryInfo  entry = ((BizAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry==null){
    			return null;
    		}
    		payNum =  entry.getPayerName();
    		payAcct =  entry.getPayerAccount();
    	}else if(info instanceof TravelAccountBillInfo){
    		TravelAccountBillAccountEntryInfo  entry = ((TravelAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry==null){
    			return null;
    		}
    		payNum =  entry.getPayerName();
    		payAcct =  entry.getPayerAccount();
    	}
    	String suppNum = getSupplierNum(ctx, payAcct,payNum,"name");
    	return suppNum;
    }
    
    
    /**
     * �������ݷ�¼��ͬ�ֶ�
     * @param ctx
     * @param rowType
     * @param row
     * @param info
     * @param iBaseDataMatch
     * @param entry
     * @return
     * @throws BOSException
     * @throws EASBizException
     * @deprecated
     */
    private Map<String,Object> addEntryDiffObjByRowType2(Context ctx,int  rowType,Map<String,Object> row,
    		BizCollBillBaseInfo info,IBaseDataMatch iBaseDataMatch,CoreBillEntryBaseInfo entry,BigDecimal invoiceTotalAmt,BigDecimal apprTotalAmt)
	throws BOSException, EASBizException{
    	//String k3AcctNum = getAcctByExpenseType(ctx, info, iBaseDataMatch, entry);
    	
    	if(rowType==-1){
    		
    	}
    	Map<String,Object> newRow  = null;
		/*if(rowType==-1 || rowType==0){ //�˿� ���г��
			row.put("fAcctNum",k3AcctNum); 
			if(rowType==0){
				BigDecimal tmpinvoice = entry.getBigDecimal("invoiceAmt");
				tmpinvoice = tmpinvoice==null?BigDecimal.ZERO:tmpinvoice;
				BigDecimal tmpTaxAmt = entry.getBigDecimal("tax")==null?BigDecimal.ZERO:entry.getBigDecimal("tax");
				row.put("FAmt",tmpinvoice.subtract(tmpTaxAmt)); 
			}else{
				row.put("FAmt",entry.getBigDecimal("AmountApproved")==null?BigDecimal.ZERO:entry.getBigDecimal("AmountApproved"));
			}
		}else{//�޳��
			
			if(invoiceTotalAmt.compareTo(apprTotalAmt)>0){ //�ܷ�Ʊ���ܱ���
				row.put("fAcctNum",k3AcctNum); //�ܵ�
				row.put("FAmt",invoiceTotalAmt); //�ܵ�
			}else if(invoiceTotalAmt.compareTo(apprTotalAmt)<0){ //�ܷ�Ʊ���ܱ���
				row.put("fAcctNum",k3AcctNum); //�ܵ�
				row.put("FAmt",invoiceTotalAmt); //�ܵ�
				//������
				newRow = copyRow(row);
				newRow.put("Findex", "2");
				newRow.put("FAmt",apprTotalAmt.subtract(invoiceTotalAmt));  //���α������-��Ʊ���
				newRow.put("fAcctNum",K3WebAccConfigInfos.getDebitSupplierAccount(ctx)); 
			}else{
				BigDecimal tmpAppr = entry.getBigDecimal("AmountApproved"); //�б���
				row.put("fAcctNum",k3AcctNum);
				row.put("FAmt",tmpAppr);  
			}
			
		}*/
		
		return newRow;
    	
    }

	private String getAcctByExpenseType(Context ctx, BizCollBillBaseInfo info,
			IBaseDataMatch iBaseDataMatch, CoreBillEntryBaseInfo entry)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.getSelector().add(new SelectorItemInfo("accountId.id"));
    	view.getSelector().add(new SelectorItemInfo("accountId.number"));
    	ExpenseTypeInfo expenseType = (ExpenseTypeInfo)entry.getObjectValue("expenseType"); //��������
    	String expenseTypeID = expenseType.getId().toString(); //����ID
		String comId = info.getCompany().getId().toString();  //��˾ID
    	filter.getFilterItems().add(new FilterItemInfo("company.id",comId));
    	filter.getFilterItems().add(new FilterItemInfo("expenseTypeId.id",expenseTypeID));
    	
    	ExpenseTypeSubjectMappingCollection coll = 
    		ExpenseTypeSubjectMappingFactory.getLocalInstance(ctx).getExpenseTypeSubjectMappingCollection(view);
    	String k3AcctNum = "";
    	if(coll.size()>0){
    		String acctNum = coll.get(0).getAccountId().getNumber();
    		k3AcctNum = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.ACCOUNT, acctNum);
    		k3AcctNum = k3AcctNum==null?"":k3AcctNum;
    	}
    	return k3AcctNum;
	}
    
    /**
     * �и���
     * @param row
     * @return
     */
    private Map<String,Object> copyRow(Map<String,Object> row){
    	Map<String,Object> newRow = new LinkedHashMap<String, Object>();
    	Iterator<String>  it = row.keySet().iterator();
    	String key = null;
    	while(it.hasNext()){
    		key = it.next();
    		newRow.put(key, row.get(key));
    	}
    	return newRow;
    }
    
    
    /**
     * Ϊ�������������
     * @param ctx
     * @param rowType
     * @param reenum
     * @param headRow
     * @param info
     * @param iBaseDataMatch
     * @throws BOSException
     * @throws EASBizException
     */
    private void addDebitObjByRowType(Context ctx,int  rowType,ReceiveObjectEnum  reenum ,Map<String,Object> headRow,BizCollBillBaseInfo info,IBaseDataMatch iBaseDataMatch)
	throws BOSException, EASBizException{
    	String acctNum = null;   //��Ŀ����
    	BigDecimal amt = null;  //���
    	String res = null;   //ժҪ
    	String specialNum = null;  //ר�����
    	//AbstractObjectCollection loanColl  = (AbstractObjectCollection)info.get("LoanCheckEntries");
    	//AbstractObjectCollection    entryColl  = (AbstractObjectCollection)info.get("entries");
    	if(rowType==-1){
    		acctNum ="1221.01";
    		amt = info.getAmountApproved();
    		res= info.getCause();
    		specialNum ="";
    	}else if(rowType==0 || rowType==1 ){
    		if(ReceiveObjectEnum.personal.equals(reenum)){
    			acctNum =K3WebAccConfigInfos.getDebitPersonAccount(ctx);
    		}else if(ReceiveObjectEnum.supplier.equals(reenum)){
    			acctNum =K3WebAccConfigInfos.getDebitSupplierAccount(ctx);
    		}
    		//amt = getLoanCheckAmount(loanColl); //������� �������
    		amt = getLocalAllTaxAmt(ctx, info).subtract(getTotalAmtEncashed(ctx, info));//getInvoiceDeApprAmount(entryColl);
    		//IObjectValue obj =  loanColl.size()==0? null:loanColl.getObject(0);
    		/*res= loanColl.size()==0?"":obj.getString("sourceBillCause");*/
    		/*ProjectInfo prj = obj==null?null :((ProjectInfo)obj.getObjectValue("project"));
    		if(prj!=null){
    			specialNum =loanColl.size()==0?"":iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.PROJECT, prj.getNumber()) ;	
    			specialNum = specialNum==null?"":specialNum;
    		}else{
    			specialNum = "03.01";
    		}*/
    		res ="";
    		specialNum = "03.01";
    		
    	}/*else if(rowType==1){
    		//acctNum =K3WebAccConfigInfos.getDebitSupplierAccount(ctx);
    		if(ReceiveObjectEnum.personal.equals(reenum)){
    			acctNum =K3WebAccConfigInfos.getDebitPersonAccount(ctx);
    		}else if(ReceiveObjectEnum.supplier.equals(reenum)){
    			acctNum =K3WebAccConfigInfos.getDebitSupplierAccount(ctx);
    		}
    		
    		amt = getLocalAllTaxAmt(ctx, info).subtract(getTotalAmtEncashed(ctx, info));//getInvoiceDeApprAmount(entryColl);
    		res= info.getCause();
    		specialNum ="";
    	}*/
    	res = res==null?"":res;
    	
    	String empNum = "";
    	String suppNum = "";
    	if(ReceiveObjectEnum.personal.equals(reenum)){
    		empNum = info.getApplier().getNumber(); //iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.PERSON, getPersonNum(ctx, info.getPayerAccount(), info.getPayerName()) );
    		empNum = empNum==null?"":empNum;
    	}else if(ReceiveObjectEnum.supplier.equals(reenum)){
    		
			//suppNum = info.getPayerName() ;// getSupplierNum(ctx, info.getPayerAccount(), info.getPayerName()); // info.getPayerAccount()==null?"":info.getPayerAccount();
			//suppNum = suppNum==null?"":"SUP."+suppNum;
			suppNum= getPayerNum(ctx, info, reenum);
    	}
    	
    	headRow.put("FTMQNo",info.getNumber());
    	headRow.put("FAcctNum",acctNum); 
    	headRow.put("Famt",amt); 
    	headRow.put("Fexp",res);
    	String dept = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.DEPARTMENT, info.getOrgUnit().getNumber());
    	headRow.put("FDeptNum",dept==null?"":dept);
    	
    	headRow.put("FEmpNum",empNum);
    	headRow.put("FSupplyNum",suppNum);
    	
    	headRow.put("FSpecialNum",specialNum);
    	
    }
    
    /**
     * ������ ȡ�������
     * @param loanColl
     * @return
     */
    private BigDecimal getLoanCheckAmount(AbstractObjectCollection loanColl){
    	BigDecimal amount = BigDecimal.ZERO;
    	if(loanColl!=null ){
    		BigDecimal tmp  =null;
    		for(int i=0;i<loanColl.size();i++){
    			tmp = loanColl.getObject(i).getBigDecimal("checkAmount");
    			if(tmp!=null){
    				amount = amount.add(tmp);
    			}
    		}
    	}
    	return amount;
    }
    
    /**
     * ������ȡ��Ʊ���-�������
     * @param entryColl
     * @return
     */
    private BigDecimal getInvoiceDeApprAmount(AbstractObjectCollection entryColl){
    	BigDecimal amount = BigDecimal.ZERO;
    	if(entryColl!=null ){
    		BigDecimal tmpinvoice  =null;
    		BigDecimal tmpAppr  =null;
    		for(int i=0;i<entryColl.size();i++){
    			tmpAppr = ((CoreBillEntryBaseInfo)entryColl.getObject(i)).getBigDecimal("AmountApproved");
    			tmpinvoice = entryColl.getObject(i).getBigDecimal("invoiceAmt");
    			tmpAppr = tmpAppr ==null?BigDecimal.ZERO:tmpAppr;
    			tmpinvoice = tmpinvoice==null?BigDecimal.ZERO:tmpinvoice;
    			
    			amount = amount.add(tmpinvoice.subtract(tmpAppr));
    		}
    	}
    	return amount;
    }
    
    
    private EntityViewInfo getBillFilter(BOSObjectType  bosType,int filterType){
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	filter.getFilterItems().add(new FilterItemInfo("state",StateEnum.CHECKED_VALUE));
    	
    	if(filterType==0){  //0  null  δ����ƾ֤
    		filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus",Integer.valueOf(0)));
    		filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus is null "));
    		filter.setMaskString("#0 and (#1 or #2)");
    	}else if(filterType==1){  //1 , 3 δ��ȡK3ƾ֤��K3ƾ֤���ɴ���
    		filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus",Integer.valueOf(1)));
    		filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus",Integer.valueOf(3)));
    		filter.setMaskString("#0 and (#1 or #2)");
    	}else if(filterType==2){ //2 ���ɳɹ���
    		
    	}
    	return view;
    }
    /**
     *  ��ѯ���ݹ�������
     * @param bosType
     * @return
     */
    private EntityViewInfo getBillFilter(BOSObjectType  bosType){
    	return getBillFilter(bosType, 0);
    }
    
    /**
     * ��ȡ����������һ�ִ���ʽ
     * @param ctx
     * @param info
     * @return  -1 �ʿ�  0 �г��  1 �޳��
     * @throws BOSException 
     */
    private int getRowType(Context ctx,BizCollBillBaseInfo info) throws BOSException{
    	BizAccountBillLoanCheckEntryCollection coll = (BizAccountBillLoanCheckEntryCollection)info.get("LoanCheckEntries");
    	//�ʿ۵Ĺ̶� 
    	if(K3WebAccConfigInfos.getAcountPayModeName(ctx).equals(((SettlementTypeInfo )info.get("payMode")).getName())){
    		return -1;
    	}else if(coll!=null && coll.size()>0){ //�г��
    		return 0;
    	}else{ //�޳��
    		return 1;
    	}
    }
    
    /**
     * ��ȡ��������TypeID
     * @param ctx
     * @param info
     * @param rowType
     * @return
     * @throws BOSException
     */
    private int getTypeId(Context ctx,BizCollBillBaseInfo info,int rowType) throws BOSException{
    	AbstractObjectCollection  coll = (AbstractObjectCollection)info.get("entries");
    	if(rowType==-1){
    		return 1;
    	}else if(rowType==0 || rowType==1){
    		if(coll!=null && coll.size()>0){

    			BigDecimal taxTotalAmt = getLocalAllTaxAmt(ctx, info); //����
        		BigDecimal apprTotalAmt = getTotalAmtEncashed(ctx, info);//�ָ�
        		
        		if(apprTotalAmt.compareTo(taxTotalAmt)==0){//�������==��Ʊ���
        			return 0;  //1 
        		}else if(taxTotalAmt.compareTo(apprTotalAmt)>0){//�������>�ָ�
        			return 2;
        		}else{//�������ָ�
        			return 0;  
        		}
        		
        		/*if(apprTotalAmt.compareTo(taxTotalAmt)==0){//�������==��Ʊ���
        			return rowType==0? 1: 0;  //rowType ==0  ==>1 rowType==1 ==>0
        		}else if(apprTotalAmt.compareTo(taxTotalAmt)>0){//�������>��Ʊ���
        			return rowType==0? 2: 0;
        		}else{//��������Ʊ���
        			return rowType==0? 1: 2;  
        		}*/
        	}
    	}else{
    		 throw new BOSException("����������ʹ��󣡣�����");
    	}
    	return -1;    	
    }
    
    /**
     * ��ȡ�ܵ� ����˰���+˰��
     * @author michael
     * @date: 2015-1-8
     * @param ctx
     * @param info
     * @return 
     * @exception 
     * @description:
     */
    private BigDecimal getLocalAllTaxAmt(Context ctx,BizCollBillBaseInfo info){
    	AbstractObjectCollection  coll = (AbstractObjectCollection)info.get("entries");
    	if(coll!=null && coll.size()>0){
    		BigDecimal taxAmt = null;
    		BigDecimal noTaxAmt = null;
    		BigDecimal taxTotalAmt = BigDecimal.ZERO;
    		for(int i=0;i<coll.size();i++){  //noTaxAmtLocal taxLocal
    			taxAmt = (BigDecimal)coll.getObject(i).getBigDecimal("taxLocal");
    			noTaxAmt = (BigDecimal)coll.getObject(i).getBigDecimal("noTaxAmtLocal");
    			taxAmt = taxAmt ==null?BigDecimal.ZERO:taxAmt;
    			noTaxAmt = noTaxAmt ==null?BigDecimal.ZERO:noTaxAmt;
    			
    			taxTotalAmt = taxTotalAmt.add(taxAmt).add(noTaxAmt);
    		}
    		return taxTotalAmt;
    	}
    	return BigDecimal.ZERO;
    }
    
    /**
     * ���� �ָ����
     * @author michael
     * @date: 2015-1-8
     * @param ctx
     * @param info
     * @return 
     * @exception 
     * @description:
     */
    private BigDecimal getTotalAmtEncashed(Context ctx,BizCollBillBaseInfo info){
    	BigDecimal payAmt = info.getBigDecimal("amountEncashed");
    	return payAmt==null?BigDecimal.ZERO:payAmt;
    	/*AbstractObjectCollection  coll = (AbstractObjectCollection)info.get("entries");
    	if(coll!=null && coll.size()>0){
			BigDecimal apprAmt = null;
			BigDecimal apprTotalAmt = BigDecimal.ZERO;
			for(int i=0;i<coll.size();i++){
				apprAmt = (BigDecimal)coll.getObject(i).getBigDecimal("amountApproved");
				apprAmt = apprAmt ==null?BigDecimal.ZERO:apprAmt;
				
				apprTotalAmt =	apprTotalAmt.add(apprAmt);
			}
			return apprTotalAmt;
    	}
    	return BigDecimal.ZERO;*/
    }
    
    /**
     * ��ȡ�����տ��������
     * @param info
     * @return
     */
    private ReceiveObjectEnum getReceivObj(BizCollBillBaseInfo info){
    	if(info instanceof BizAccountBillInfo){
    		BizAccountBillAccountEntryInfo  entry = ((BizAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry==null){
    			return null;
    		}
    		return entry.getReceiveObject();
    	}else if(info instanceof TravelAccountBillInfo){
    		TravelAccountBillAccountEntryInfo  entry = ((TravelAccountBillInfo)info).getCollectionEntries().get(0);
    		return  entry.getReceiveObject();
    	}else if(info instanceof DailyLoanBillInfo){
    		return ((DailyLoanBillInfo)info).getReceiveObject();
    	}else if(info instanceof EvectionLoanBillInfo){
    		return ((EvectionLoanBillInfo)info).getReceiveObject();
    	}
    	
    	return null;
    }
    
    
    /**
     * ����һ����������
     * @param ctx
     * @param info
     * @param iBaseDataMatch
     * @param rowList
     * @throws EASBizException
     * @throws BOSException
     */
    private void addLoanABillRows2(Context ctx,BizCollBillBaseInfo info,IBaseDataMatch iBaseDataMatch,List<Map<String,Object>> rowList) throws EASBizException, BOSException{
    	String orgCode = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.ORGUNIT, info.getApplierCompany().getNumber()); //check this again  ����֯��Ӧ��K3��֯��������
    	orgCode = orgCode==null?"":orgCode;
    	AbstractObjectCollection  entrys = (AbstractObjectCollection)info.get("entries");
    	CoreBillEntryBaseInfo entry = null;
    	for(int i=0;i<entrys.size();i++){
    		Map<String,Object> row = new LinkedHashMap<String,Object>();
    		entry = (CoreBillEntryBaseInfo)entrys.getObject(i);
    		row.put("FTypeID",Integer.valueOf(0));
    		row.put("Findex",entry.getSeq());
    		row.put("FTMQNo",info.getNumber());
    		
    		row.put("FCompanyNum",orgCode);
    		row.put("ftransDate",info.getBizReqDate());
    		row.put("fattchment",0);
    		
    		CompanyVoucherNumEntryInfo cVoucherEntry = (CompanyVoucherNumEntryInfo) info.get("voucherNum");
    		if(cVoucherEntry!=null){
    			row.put("FGroupID", cVoucherEntry.getVoucherNumber()==null?"":cVoucherEntry.getVoucherNumber());
    		}else{
    			row.put("FGroupID", "");
    		}
    		
    		ReceiveObjectEnum  reenum =  getReceivObj(info);
    		if(reenum==null){
    			throw new BOSException("�տ�������");
    		}
    		addByReceiveObj(ctx, reenum, row, info, iBaseDataMatch);
    		ProjectInfo prj = (ProjectInfo)entry.getObjectValue("project");
    		if(prj!=null){
    			String k3FSpecialNum =  iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.PROJECT, prj.getNumber());
    			row.put("FSpecialNum",k3FSpecialNum==null?"":k3FSpecialNum);
    		}else{
    			row.put("FSpecialNum","");
    		}
    		
    		String reqDept = iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.DEPARTMENT, info.getOrgUnit().getNumber());
    		row.put("FDeptNum",reqDept==null?"":reqDept);
//    currencyType  amountApprovedOri  purpose
    		CurrencyInfo currency = (CurrencyInfo)entry.get("currencyType");
    		row.put("FCyNum",currency==null?"":currency.getIsoCode());
    		row.put("FAmt",entry.getBigDecimal("amountApprovedOri"));  //ԭ�Һ˶�
    		row.put("FTaxAmt",0);  //����˰��
    		String purpose = entry.getString("purpose");
    		purpose = purpose==null?"":purpose;
    		if(ReceiveObjectEnum.supplier.equals(reenum)){
    			row.put("fexp",info.getNumber()+row.get("FSupplyName")+purpose);  // ����+�տλ+�����嵥�����;
    		}else{
    			row.put("fexp",info.getNumber()+row.get("FEmpName")+purpose);  // ����+�տλ+�����嵥�����;
    		}
    		
    		/*row.put("FBigArea","");
    		row.put("Farea","");
    		row.put("FSmallArea","");*/
    		
    		row.put("FIsSyn",Boolean.FALSE);
    		row.put("FVouNum",""); 
    		
    		rowList.add(row);
    	}
    	
    }
    
	/**
	 * (����)�� �տ����
	 * @param ctx
	 * @param reenum
	 * @param row
	 * @param info
	 * @param iBaseDataMatch
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void  addByReceiveObj(Context ctx,ReceiveObjectEnum  reenum,Map<String,Object> row,BizCollBillBaseInfo info,IBaseDataMatch iBaseDataMatch)
	throws BOSException, EASBizException{
		if(ReceiveObjectEnum.personal.equals(reenum)){
			
			row.put("fAcctNum",K3WebAccConfigInfos.getDebitPersonAccount(ctx));
    		row.put("FSupplyNum","");
    		row.put("FSupplyName","");  //�տ�������
    		 // �տ��˴��밴ְԱ��Ӧ��ϵȡK3ְԱ���룬���δ�ҵ�ֱ��ȡEAS��ְԱ����

    		/*String easPersonNum = info.getApplier().getNumber();//getPersonNum(ctx, info.getPayerAccount(), info.getPayerName());
    		if(easPersonNum==null){
    			row.put("FEmpNum", "");
    			logger.warn("+++++++easPersonNum is  null  account="+info.getPayerAccount()+"  PayerName="+info.getPayerName());
    		}else{
	    		row.put("FEmpNum", easPersonNum);
    		}*/
    		
    		row.put("FEmpNum",info.getApplier().getNumber());
    		row.put("FEmpName",info.getApplier().getName());
    		
    	}else {
    		String suppName = getSupplierNum(ctx, info.getPayerAccount(), info.getPayerName(),"number"); 
    		row.put("fAcctNum",K3WebAccConfigInfos.getDebitSupplierAccount(ctx));
    		row.put("FSupplyNum",info.getPayerName()==null?"":"SUP."+info.getPayerName());  //ȡ�տ��˴��� 
    		row.put("FSupplyName",suppName==null?"":suppName);  //�տ�������
    		
    		/*String easPersonNum = info.getApplier().getNumber();//getPersonNum(ctx, info.getPayerAccount(), info.getPayerName());
    		if(easPersonNum==null){
    			row.put("FEmpNum", "");
    			logger.warn("+++++++easPersonNum is  null  account="+info.getPayerAccount()+"  PayerName="+info.getPayerName());
    		}else{
	    		row.put("FEmpNum", easPersonNum);
    		}*/
    		
    		row.put("FEmpNum",info.getApplier().getNumber());
    		row.put("FEmpName",info.getApplier().getName());
    		
    		/*row.put("FEmpNum","");
    		row.put("FEmpName","");*/
    		
    	} 
	}
	
	
	/**
	 * �����տ������ƺ��տ����˺Ż�ȡְԱ����
	 * @param ctx
	 * @param payerAccount
	 * @param payerName
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private String getPersonNum1(Context ctx,String payerAccount,String payerName) throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter= new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("bandAcctNumber",payerAccount));
		filter.getFilterItems().add(new FilterItemInfo("person.name",payerName));
		BankInfoCollection coll =  BankInfoFactory.getLocalInstance(ctx).getBankInfoCollection(view); 
		if(coll.size()==1){
				return coll.get(0).getPerson().getNumber();
		}else{
			if(coll.size()>1){
				logger.warn("ͬһ�����˻����տ��˴��ڶ��");
				 //throw new EASBizException(new NumericExceptionSubItem("ffff","ͬһ�����˻����տ��˴��ڶ��"));
			}else{
				logger.warn("���������˻����տ���û���ҵ�ƥ���ְԱ");
			}
			 //throw new EASBizException(new NumericExceptionSubItem("ffff","���������˻����տ���û���ҵ�ƥ���ְԱ"));
		}
		return null;
	}
	
	/**
	 * �����տ������ƺ��տ����˺Ż�ȡ��Ӧ��������Ϣ
	 * @param ctx
	 * @param payerAccount
	 * @param payerName
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private String getSupplierNum(Context ctx,String payerAccount,String payerName,String byProperty ) throws BOSException, EASBizException{
		if(payerAccount==null ||  payerName==null){
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter= new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo(byProperty,payerName));
		SupplierCollection suppColl =  SupplierFactory.getLocalInstance(ctx).getSupplierCollection(view);
		if(suppColl.size()>0){
			if("name".equals(byProperty)){
				return suppColl.get(0).getNumber();
			}else{
				return suppColl.get(0).getName();
			}
		}
		filter.getFilterItems().clear();
		
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("supplierCompanyInfo.id"));
		view.getSelector().add(new SelectorItemInfo("supplierCompanyInfo.supplier."+byProperty));
		
		filter.getFilterItems().add(new FilterItemInfo("bankAccount",payerAccount));
		filter.getFilterItems().add(new FilterItemInfo("supplierCompanyInfo.supplier."+byProperty,payerName));
		
		SupplierCompanyBankCollection coll =  SupplierCompanyBankFactory.getLocalInstance(ctx).getSupplierCompanyBankCollection(view); 
		if(coll.size()==1){
			if("name".equals(byProperty)){
				return coll.get(0).getSupplierCompanyInfo().getSupplier().getNumber();
			}else{
				return coll.get(0).getSupplierCompanyInfo().getSupplier().getName();
			}
		}else{
			if(coll.size()>1){
				logger.warn("ͬһ�����˻����տ��˴��ڶ��������Ϣ");
				// throw new EASBizException(new NumericExceptionSubItem("ffff","ͬһ�����˻����տ��˴��ڶ��������Ϣ"));
			}else{
				logger.warn("���������˻����տ���û���ҵ�ƥ���������Ϣ");
			// throw new EASBizException(new NumericExceptionSubItem("ffff","���������˻����տ���û���ҵ�ƥ���������Ϣ"));
				
			}
			
			return null;
		}
	}
	
	
	  /**
	   * ���ñ���
	   */
    protected void _crVouchByBizAccount(Context ctx, String billID)
	throws BOSException, EASBizException {
    	
    	try {
			updateVoucherStatusByType(ctx, new BizAccountBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("���ñ�������ƾ֤��Ϣ����");
		}
		
    	//BOSUuid id = BOSUuid.read(billID);
    	if(billID==null){
    		throw new BOSException("bill num type error");
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.setSelector(getBillSelector(new BizAccountBillInfo().getBOSType()));
    	filter.getFilterItems().add(new FilterItemInfo("number",billID));
    /*	filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus",Integer.valueOf(0)));
		filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus is null "));
		filter.setMaskString("#0 and (#1 or #2)");*/
    	//filter.getFilterItems().add(new FilterItemInfo("k3VoucherStatus",Integer.valueOf(i),CompareType.NOTEQUALS));
    	BizAccountBillCollection coll  = BizAccountBillFactory.getLocalInstance(ctx).getBizAccountBillCollection(view);
    	if(coll.size()==0){
    		return ;
    	}
    	BizAccountBillInfo info = coll.get(0);
		
		List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> headList = new ArrayList<Map<String,Object>>();
		IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
		
		addAccountABillRows2(ctx, info, iBaseDataMatch, rowList, headList);
		
		try {
			K3VoucherDBUtil.insertVoucher(ctx, rowList,headList,new BizAccountBillInfo().getBOSType());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
    }
    
    /**
     * ���÷��ñ���
     */
    protected void _crVouchByTraveAccount(Context ctx, String billID)
	throws BOSException, EASBizException {
    	
    	if(billID==null){
    		throw new BOSException("bill num type error");
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.setSelector(getBillSelector(new TravelAccountBillInfo().getBOSType()));
    	filter.getFilterItems().add(new FilterItemInfo("number",billID));
    	TravelAccountBillCollection coll  = TravelAccountBillFactory.getLocalInstance(ctx).getTravelAccountBillCollection(view);
    	if(coll.size()==0){
    		return ;
    	}
    	TravelAccountBillInfo info = coll.get(0);
    	
    	/*
    	BOSUuid id = BOSUuid.read(billID);
    	if(id==null){
    		throw new BOSException("billId type error");
    	}*/
    	//TravelAccountBillInfo info = TravelAccountBillFactory.getLocalInstance(ctx).getTravelAccountBillInfo(new ObjectUuidPK(id),getBillSelector(id.getType()));
		
    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> headList = new ArrayList<Map<String,Object>>();
		IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
		addAccountABillRows2(ctx, info, iBaseDataMatch, rowList, headList);
		try {
			K3VoucherDBUtil.insertVoucher(ctx, rowList,headList,new TravelAccountBillInfo().getBOSType());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
    
    /**
     * ���
     */
    protected void _crVouchByDailyLoan(Context ctx, String billID)
	throws BOSException, EASBizException {
    	/*BOSUuid id = BOSUuid.read(billID);
    	if(id==null){
    		throw new BOSException("billId type error");
    	}*/
    	
    	//DailyLoanBillInfo info = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(id),getBillSelector(id.getType()));
    	
    	if(billID==null){
    		throw new BOSException("bill num type error");
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.setSelector(getBillSelector(new DailyLoanBillInfo().getBOSType()));
    	filter.getFilterItems().add(new FilterItemInfo("number",billID));
    	DailyLoanBillCollection coll  = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillCollection(view);
    	if(coll.size()==0){
    		return ;
    	}
    	DailyLoanBillInfo info = coll.get(0);
    	
    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
    	IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	
    	addLoanABillRows2(ctx, info, iBaseDataMatch, rowList);
    	
    	try {
			K3VoucherDBUtil.insertVoucher(ctx, rowList,null,info.getBOSType());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
    	
    }
    /**
     * ������
     */
    protected void _crVouchByEvectionLoan(Context ctx, String billID)
    		throws BOSException, EASBizException {
    	/*BOSUuid id = BOSUuid.read(billID);
    	if(id==null){
    		throw new BOSException("billId type error");
    	}*/
    	//EvectionLoanBillInfo info = EvectionLoanBillFactory.getLocalInstance(ctx).getEvectionLoanBillInfo(new ObjectUuidPK(id),getBillSelector(id.getType()));
    	
    	if(billID==null){
    		throw new BOSException("bill num type error");
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.setSelector(getBillSelector(new EvectionLoanBillInfo().getBOSType()));
    	filter.getFilterItems().add(new FilterItemInfo("number",billID));
    	EvectionLoanBillCollection coll  = EvectionLoanBillFactory.getLocalInstance(ctx).getEvectionLoanBillCollection(view);
    	if(coll.size()==0){
    		return ;
    	}
    	EvectionLoanBillInfo info = coll.get(0);

    	List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
    	IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	addLoanABillRows2(ctx, info, iBaseDataMatch, rowList);
    	try {
			K3VoucherDBUtil.insertVoucher(ctx, rowList,null,new EvectionLoanBillInfo().getBOSType());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
    	
    }
    
    /**
     * ���ݲ�ѯ�ֶ�
     * @param bosType
     * @return
     */
    private SelectorItemCollection getBillSelector(BOSObjectType  bosType){
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	
    	
    /*	sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.name"));
    	sic.add(new SelectorItemInfo("entries.expenseType.id"));
		sic.add(new SelectorItemInfo("entries.expenseType.name"));
    	sic.add(new SelectorItemInfo("entries.expenseType.number"));
    	sic.add(new SelectorItemInfo("entries.amountApproved"));
    	sic.add(new SelectorItemInfo("entries.operationType.id"));
		sic.add(new SelectorItemInfo("entries.operationType.name"));
    	sic.add(new SelectorItemInfo("entries.operationType.number"));
		 
    	sic.add(new SelectorItemInfo("entries.costCenter.id"));
		sic.add(new SelectorItemInfo("entries.costCenter.name"));
    	sic.add(new SelectorItemInfo("entries.costCenter.number"));
		 
		 
    	sic.add(new SelectorItemInfo("entries.company.id"));
		sic.add(new SelectorItemInfo("entries.company.name"));
    	sic.add(new SelectorItemInfo("entries.company.number"));
		 
    	sic.add(new SelectorItemInfo("entries.sourceBillEntryID"));
    	sic.add(new SelectorItemInfo("entries.convertMode"));
    	sic.add(new SelectorItemInfo("entries.exchangeRatePrecision"));
	 
    	sic.add(new SelectorItemInfo("entries.project.id"));
		sic.add(new SelectorItemInfo("entries.project.name"));
    	sic.add(new SelectorItemInfo("entries.project.number"));
		 
    	sic.add(new SelectorItemInfo("entries.isNoInvoice"));
    	sic.add(new SelectorItemInfo("entries.noInvoiceAmt"));
    	sic.add(new SelectorItemInfo("entries.noTaxAmt"));
    	sic.add(new SelectorItemInfo("entries.tax"));
    	sic.add(new SelectorItemInfo("entries.noTaxAmtLocal"));
    	sic.add(new SelectorItemInfo("entries.taxLocal"));
    	sic.add(new SelectorItemInfo("entries.invoiceAmt"));
    	sic.add(new SelectorItemInfo("entries.taxRate"));
    	sic.add(new SelectorItemInfo("entries.submitAmt"));
    	sic.add(new SelectorItemInfo("entries.submitUseAmt"));*/
    	
		sic.add("entries.currencyType.id");
		sic.add("entries.currencyType.number");
		sic.add("entries.currencyType.isoCode");
		
		sic.add("entries.project.id");
		sic.add("entries.project.number");
		
		sic.add("applierCompany.id");
    	sic.add("applierCompany.number");
		
    	sic.add("company.id");
    	sic.add("company.number");
    	
    	sic.add(new SelectorItemInfo("entries.costCenter.id"));
		sic.add(new SelectorItemInfo("entries.costCenter.name"));
    	sic.add(new SelectorItemInfo("entries.costCenter.number"));
    	
    	sic.add(new SelectorItemInfo("entries.person.id"));
    	sic.add(new SelectorItemInfo("entries.person.name"));
    	sic.add(new SelectorItemInfo("entries.person.number"));
    	
    	sic.add("payCompany.id");
    	sic.add("payCompany.acctNumber");
    	
    	sic.add("applier.id");
    	sic.add("applier.number");
    	sic.add("applier.name");
    	
    	sic.add("expenseType.id");
    	sic.add("expenseType.name");
    	
    	sic.add("voucherNum.id");
    	sic.add("voucherNum.voucherNumber");
    	
    	
    	sic.add("currencyType.id");
    	sic.add("currencyType.isoCode");
    	sic.add("currencyType.number");
    	
    	sic.add("costedDept.id");
    	sic.add("costedDept.name");
    	
    	sic.add("orgUnit.id");
    	sic.add("orgUnit.name");
    	sic.add("orgUnit.number");
    	
    	sic.add("creator.id");
    	sic.add("creator.name");
    	
    	sic.add("auditor.id");
    	sic.add("auditor.name");
    	
    	
    	sic.add("payMode.id");
    	sic.add("payMode.number");
    	sic.add("payMode.name");
    	
    	if(bosType!=null && ("4A44F49F".equals(bosType.toString()) || "C57003BC".equals(bosType.toString())) ){
    		sic.add("collectionEntries.id");
    		sic.add("collectionEntries.bill.id");
    		sic.add("collectionEntries.receiveObject");
    		sic.add("collectionEntries.payerName");
    		sic.add("collectionEntries.payerAccount");
    		//sic.add("LoanCheckEntries.*"); //checkAmount,sourceBillCause ,project.*
    		sic.add("LoanCheckEntries.checkAmount"); 
    		sic.add("LoanCheckEntries.sourceBillCause"); 
    		sic.add("LoanCheckEntries.project.id"); 
    		sic.add("LoanCheckEntries.project.number");
    	}else {
    		sic.add("payerBankStr.id");
    		sic.add("payerBankStr.BeProvince");
    		sic.add("payerBankStr.BeCity");
    	}
    	
    	sic.add("entries.*");
    	 
    	return sic;
    }
}