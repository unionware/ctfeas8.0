package com.kingdee.eas.cp.bc.app;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.tempuri.Service1Locator;
import org.tempuri.Service1Soap;
import org.tempuri.Service1SoapProxy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.person.BankInfoCollection;
import com.kingdee.eas.basedata.person.BankInfoFactory;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizAccountBillAccountEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillEntryCollection;
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.CollectionAccountCoreBaseInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.ReceiveObjectEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillFactory;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.cp.bc.ZDFDateHelper;
import com.kingdee.eas.cp.bc.app.dbutil.K3NoticeBillLogUtil;
import com.kingdee.eas.cp.bc.app.dbutil.K3StatusReWriteUtil;
import com.kingdee.eas.cp.bc.app.dbutil.K3VoucherDBUtil;
import com.kingdee.eas.cp.bc.app.dbutil.K3VoucherEasNumInfo;
import com.kingdee.eas.cp.bc.app.dbutil.K3WebAccConfigInfos;
import com.kingdee.util.StringUtils;

public class CreateK3PayNoticeBillFacadeControllerBean extends AbstractCreateK3PayNoticeBillFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.CreateK3PayNoticeBillFacadeControllerBean");

   
    protected void _backRun(Context ctx) throws BOSException, EASBizException {
    	updateBillNoticeStatus(ctx);
    }
    
    
    
    
    
    
    protected void _createByBizAccountBill(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	if(K3WebAccConfigInfos.IsEnableCallK3WebSer(ctx)){
		    	BizAccountBillInfo info = BizAccountBillFactory.getLocalInstance(ctx).getBizAccountBillInfo(new ObjectUuidPK(billId),getBillSelector(billId.getType()));
		    	if(K3WebAccConfigInfos.getAcountPayModeName(ctx).equals(info.getPayMode().getName())){
		    		logger.info("���ñ���֧����ʽΪ"+info.getPayMode().getName()+"�˳�");
		    		return ;
		    	}
		    	if(beforeCreateCheck(info)){
		    		logger.info("���ñ��� ��λ�ҽ��Ϊ0 �˳�");
		    		return ;
		    	}
		    	doCreateXmlAndCall(ctx, info,"���ñ���");
    	}
    }
   
    
    @Override
    protected void _createByDailyLoanBill(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	if(K3WebAccConfigInfos.IsEnableCallK3WebSer(ctx)){
	    	BizCollBillBaseInfo info = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(billId),getBillSelector(billId.getType()));
	    	doCreateXmlAndCall(ctx, info,"��");
    	}
    }
    
    
    @Override
    protected void _createByEvectionLoanBill(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	if(K3WebAccConfigInfos.IsEnableCallK3WebSer(ctx)){
    		BizCollBillBaseInfo info = EvectionLoanBillFactory.getLocalInstance(ctx).getEvectionLoanBillInfo(new ObjectUuidPK(billId),getBillSelector(billId.getType()));
    		doCreateXmlAndCall(ctx, info,"�����");
    	}
    }
    
    
    @Override
    protected void _createByTraveAccountBill(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	if(K3WebAccConfigInfos.IsEnableCallK3WebSer(ctx)){
	    	BizCollBillBaseInfo info = TravelAccountBillFactory.getLocalInstance(ctx).getTravelAccountBillInfo(new ObjectUuidPK(billId),getBillSelector(billId.getType()));
	    	if(beforeCreateCheck(info)){
	    		logger.info("���ñ��� ��λ�ҽ��Ϊ0 �˳�");
	    		return ;
	    	}
	    	doCreateXmlAndCall(ctx, info,"���ñ�����");
    	}
    }
    
    
    private String callK3WebSer(Context ctx,String xml) throws BOSException{
    	Service1Locator.address =   K3WebAccConfigInfos.getK3WebSer(ctx);
    	Service1Soap proxy = new Service1SoapProxy();
    	try {
			String rev = proxy.billData(xml);
			return rev;
		} catch (RemoteException e) {
			logger.info("����K3�ӿ�ʧ�� ��������" +xml);
			throw  new BOSException("����K3�ӿ�ʧ��",e);
		}
    }
    
    
    private String createK3Xml(Context ctx,BizCollBillBaseInfo info) throws BOSException, EASBizException{
    	Document doc = DocumentHelper.createDocument();
    	Element root =   doc.addElement("Kdata");
    	Element list = root.addElement("head");
    	//IBaseDataMatch iBaseDataMatch=  BaseDataMatchFactory.getLocalInstance(ctx);
    	
    	Element e = null;
    	 e = list.addElement("billno"); 		e.setText((String)info.get("number"));
    	 e = list.addElement("date");   		e.setText(ZDFDateHelper.formatDate2(info.getBizReqDate()));  
    	
    	 e = list.addElement("collectnum");  //default null
    	 
    	// e = list.addElement("ispeople");  
    	/* e = list.addElement("collectname");
    	 e = list.addElement("collectbank");
    	 e = list.addElement("collectbankno");*/
    	// e = list.addElement("settletype");		e.setText(info.getPayMode().getNumber());  //���㷽ʽ����
    	 initBillCityInfo(ctx, info);
    	 putDiffInfo(info, list);
    	 
    	 
    	/* e = list.addElement("economize");  	//?
    	 e = list.addElement("city");  			//?
*/    	
    	 
    	 //���λ����
    	 AccountBankInfo accBank = ((AccountBankInfo)info.get("payCompany"));
    	
    	 e = list.addElement("paybank");  		// ���λ������ �ɿ�
    	 e = list.addElement("payname");	
    	 if(accBank!=null){
    		 e.setText(accBank.getSimpleCode()==null?"":accBank.getSimpleCode()); //e.setText(iBaseDataMatch.getK3NumByEasNum(BaseDataEnum.ORGUNIT, info.getCompany().getNumber()));
    	 }
    	 e = list.addElement("paynum");   	  //���������˺�
    	 if(accBank!=null){
    			e.setText(accBank.getBankAccountNumber());
    	 }else{
    		 logger.warn("���˾ Ϊ�գ�����");
    	 }
    	 
    	 e = list.addElement("outground");  	//default null
    	 
    	 
    	 e = list.addElement("paybiller");		e.setText(info.getApplier().getNumber()); //����������
    	
    	 e = list.addElement("transtype");  
    	 if(info.getExpenseType()!=null){
    		 e.setText(info.getExpenseType().getName()); //ҵ���������
    	 }
    	
    	 //e = list.addElement("amtfor");			e.setText("");  //ԭ�� TODO
    	// e = list.addElement("amt");			e.setText(info.getAmount()+"");  //���ҽ��
    	 e = list.addElement("curry");			e.setText(info.getCurrencyType().getIsoCode()); //�ұ� 
    	// e = list.addElement("rate");			e.setText(""); //˰�� TODO 
    	 e = list.addElement("settledate");  	e.setText(ZDFDateHelper.formatDate2(  info.getBizReqDate()));  //��������
    	 e = list.addElement("reasons"); 		e.setText(info.getCause()==null?"":info.getCause());  //����
    	 e = list.addElement("note"); 			e.setText(info.getDescription()==null?"":info.getDescription());  //��ע
    	 
    	 e = list.addElement("feedept");  		e.setText(info.getCostedDept().getName());  //����֧����������
    	 e = list.addElement("questdeptname");  e.setText(info.getOrgUnit().getName());  //���벿������
    	 e = list.addElement("questdeptnum");   e.setText(info.getOrgUnit().getNumber());  //���벿�Ŵ���
    	 e = list.addElement("biller");  		e.setText(info.getCreator().getName());  //�Ƶ���
    	
    	 e = list.addElement("checkername"); 
    	 if(info.getAuditor()!=null){
    		 e.setText(info.getAuditor().getName()); //�����
    	 }

    	 OutputFormat format = OutputFormat.createPrettyPrint(); 
         format.setEncoding("GB2312"); 
         StringWriter sw = new StringWriter(); 
         XMLWriter xw = new XMLWriter(sw, format); 
         try { 
                 xw.write(doc); 
                 xw.flush(); 
                 xw.close(); 
         } catch (IOException e2) { 
                 logger.error("��ʽ��XML�ĵ������쳣�����飡", e2); 
                 throw new BOSException(e2);
         }   
         return   sw.toString();
    }
    
    private void initBillCityInfo(Context ctx,BizCollBillBaseInfo info) throws EASBizException, BOSException{
    	int billType = getBillType(info);
    	if(billType<0){
    		throw new BOSException("�������ʹ���");
    	}
    	
    	
    	String payerAccount = null;
    	String payerName = null;
    	//BankInfoInfo bankInfoinfo =null;
    	/*String payerNum =null;
    	String city ="";
    	String province ="";*/
    	if(billType==1 || billType==2){
    		payerName = info.getString("payerName");
    		payerAccount = info.getString("payerAccount");
    		ReceiveObjectEnum  reenum  =null;
    		if(billType==1){
    			reenum = ((DailyLoanBillInfo)info).getReceiveObject();
    		}else{
    			reenum = ((EvectionLoanBillInfo)info).getReceiveObject();
    		}
    		
    		putTempInfo2Obj(info, reenum, ctx, payerAccount, payerName);
    		/*if(ReceiveObjectEnum.supplier.equals(reenum)){
    			SupplierCompanyBankInfo bankInfoinfo = getSupplierBankInfo(ctx, payerAccount, payerName);
    			if(bankInfoinfo!=null){
    				city = bankInfoinfo.getCity(); province = bankInfoinfo.getProvince();
    				city =	city ==null?"":city;	province = province==null?"":province;
    			}
    		}else{
    			BankInfoInfo bankInfoinfo = getPersonBankInfo(ctx, payerAccount, payerName);
    			if(bankInfoinfo!=null){
    				city = bankInfoinfo.getCity(); province = bankInfoinfo.getProvince();
    				city =	city ==null?"":city;	province = province==null?"":province;
    			}
    		}
    		
    		info.put("bak_city", city);
    		info.put("bak_province", province);*/
    	}else if(billType==3) {
    		BizAccountBillAccountEntryInfo  entry = ((BizAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry!=null){
    			payerName = entry.getPayerName();
    			payerAccount = entry.getPayerAccount();
    		}
    		
    		ReceiveObjectEnum  reenum =  entry.getReceiveObject();
    		
    		putTempInfo2Obj(entry, reenum, ctx, payerAccount, payerName);
    		
    	}else if(billType==4) {
    		TravelAccountBillAccountEntryInfo  entry = ((TravelAccountBillInfo)info).getCollectionEntries().get(0);
    		if(entry!=null){
    			payerName = entry.getPayerName();
    			payerAccount = entry.getPayerAccount();
    		}
    		ReceiveObjectEnum  reenum =  entry.getReceiveObject();
    		
    		putTempInfo2Obj(entry, reenum, ctx, payerAccount, payerName);
    	}
    	
    	
    	
    	
    	/* e = list.addElement("economize");  	//?
    	 e = list.addElement("city");  			//?
*/    }
    
    
    private void putTempInfo2Obj(IObjectValue obj,ReceiveObjectEnum reenum,Context ctx,String payerAccount,String payerName)
    throws EASBizException, BOSException{
    	String city ="";
    	String province ="";
    	if(ReceiveObjectEnum.supplier.equals(reenum)){
			SupplierCompanyBankInfo bankInfoinfo = getSupplierBankInfo(ctx, payerAccount, payerName);
			if(bankInfoinfo!=null){
				city = bankInfoinfo.getCity(); province = bankInfoinfo.getProvince();
				city =	city ==null?"":city;	province = province==null?"":province;
			}
		}else{
			BankInfoInfo bankInfoinfo = getPersonBankInfo(ctx, payerAccount, payerName);
			if(bankInfoinfo!=null){
				city = bankInfoinfo.getCity(); province = bankInfoinfo.getProvince();
				city =	city ==null?"":city;	province = province==null?"":province;
			}
		}
    	
    	obj.put("bak_city", city);
    	obj.put("bak_province", province);
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
	private BankInfoInfo getPersonBankInfo(Context ctx,String payerAccount,String payerName) throws BOSException, EASBizException{
		if(payerAccount==null ||  payerName==null){
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter= new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("parent.*"));
		filter.getFilterItems().add(new FilterItemInfo("bandAcctNumber",payerAccount));
		filter.getFilterItems().add(new FilterItemInfo("person.number",payerName));
		BankInfoCollection coll =  BankInfoFactory.getLocalInstance(ctx).getBankInfoCollection(view); 
		if(coll.size()==1){
				return coll.get(0);
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
	 * �����տ������ƺ��տ����˺Ż�ȡ��Ӧ��������Ϣ
	 * @param ctx
	 * @param payerAccount
	 * @param payerName
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private SupplierCompanyBankInfo getSupplierBankInfo(Context ctx,String payerAccount,String payerName) throws BOSException, EASBizException{
		if(payerAccount==null ||  payerName==null){
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter= new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("supplierCompanyInfo.id"));
		view.getSelector().add(new SelectorItemInfo("supplierCompanyInfo.supplier.name"));
		filter.getFilterItems().add(new FilterItemInfo("bankAccount",payerAccount));
		filter.getFilterItems().add(new FilterItemInfo("supplierCompanyInfo.supplier.number",payerName));
		SupplierCompanyBankCollection coll =  SupplierCompanyBankFactory.getLocalInstance(ctx).getSupplierCompanyBankCollection(view); 
		if(coll.size()==1){
				return coll.get(0);
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
    
    
    private void doCreateXmlAndCall(Context ctx,BizCollBillBaseInfo info,String billName) throws BOSException, EASBizException  {
    	String revxml = null;
    	 try {
			revxml = createK3Xml(ctx, info);
		} catch (EASBizException e) {
			e.printStackTrace();
			K3NoticeBillLogUtil.insertErrorLog(ctx, info.getId().toString(), info.getNumber(),billName+"����xml����"+e.getMessage());
			throw e;
		} catch (BOSException e) {
			e.printStackTrace();
			K3NoticeBillLogUtil.insertErrorLog(ctx, info.getId().toString(), info.getNumber(),billName+"����xml����"+e.getMessage());
			throw e;
		}
    		
    	String rev;
		try {
			rev = callK3WebSer(ctx,revxml);
		} catch (BOSException e) {
			e.printStackTrace();
			K3NoticeBillLogUtil.insertErrorLog(ctx, info.getId().toString(), info.getNumber(),billName+"����K3�ӿڳ���"+e.getMessage());
			throw e;
		}
    	logger.info("����K3�ӿ�"+billName+"������Ϣ" +rev);
    	insertLog(info, rev, ctx);
    }
    
    
    private void insertLog(BizCollBillBaseInfo info,String rev,Context ctx) throws BOSException{
    	if(StringUtils.isEmpty(rev)){
    		K3NoticeBillLogUtil.insertSuccLog(ctx, info.getId().toString(), info.getNumber());
    	}else{
    		K3NoticeBillLogUtil.insertErrorLog(ctx, info.getId().toString(), info.getNumber(),rev);
    	}
    }
    
    private boolean beforeCreateCheck(BizCollBillBaseInfo info){
    	if(info.getAmount()==null || BigDecimal.ZERO.compareTo(info.getAmount())==0){
    		return true;
    	}
    	return false;
    }
    
    //��������
    private int getBillType(IObjectValue bill){
    	if(bill==null){
    		return -1;
    	}
    	
    	 if(bill instanceof DailyLoanBillInfo){
    		return 1;
    	}else if(bill instanceof EvectionLoanBillInfo){
    		return 2;
    	}else if(bill instanceof BizAccountBillInfo){
        	return 3;
    	}else if(bill instanceof TravelAccountBillInfo){
    		return 4;
    	}
    	return -1;
    }
    
    //����������ݲ����ֶ�
    private void  putDiffInfo(BizCollBillBaseInfo info,Element list) throws BOSException{
    	int billType = getBillType(info);
    	if(billType<0){
    		throw new BOSException("�������ʹ���");
    	}
    	Element e = list.addElement("billtype"); e.setText(billType+"");
    	
    	 switch (billType){
    	 case 1:updateReceiveInfoXml((DailyLoanBillInfo)info, list) ; break; 
    	 case 2:updateReceiveInfoXml((EvectionLoanBillInfo)info, list); break; 
    	 case 3:updateReceiveInfoXml((BizAccountBillInfo)info, list); break; 
    	 case 4:updateReceiveInfoXml((TravelAccountBillInfo)info, list); break; 
    	 default :logger.error("bill type error"); break; 
    	 }
    }
    
    //���ñ���
    private void  updateReceiveInfoXml(BizAccountBillInfo info,Element list){
    	BizAccountBillAccountEntryInfo  entry = info.getCollectionEntries().get(0);
    	ReceiveObjectEnum  reenum =  entry.getReceiveObject();
    	
    	putIsPeople(reenum, list);
    	
    	putLoanInfoDiff(entry, list);
    	 
    	
    	 
    	Element  e = list.addElement("settletype"); e.setText(info.getPayMode().getName());
    	 
    	BizAccountBillEntryCollection  coll = info.getEntries();
    	BizAccountBillEntryInfo entryInfo = null;
    	BigDecimal taxRate = BigDecimal.ZERO;
    	if( coll.size()>0 ){
    		entryInfo = coll.get(0);
    		taxRate = entryInfo.getExchangeRate();
    	}
    	
    	e = list.addElement("amt");			e.setText(info.getAmountEncashed()+"");  //�����ȸ�
    	e = list.addElement("rate");			e.setText(taxRate+""); //˰�� TODO 
    	e = list.addElement("amtfor");			e.setText(info.getAmountEncashed().divide(taxRate)+"");  //ԭ��  
    	 
    	
    }
    
    //��
	private void  updateReceiveInfoXml(DailyLoanBillInfo info,Element list) throws BOSException{
		ReceiveObjectEnum  reenum =  info.getReceiveObject();
		if(reenum==null){
			throw new BOSException("�տ�������");
		}
		putIsPeople(reenum, list);
    	
    	putLoanInfoDiff(info, list);
    	 
    	Element e = list.addElement("settletype"); e.setText(info.getPayMode().getName());
    	
    	DailyLoanBillEntryCollection  coll = info.getEntries();
    	DailyLoanBillEntryInfo entryInfo = null;
    	BigDecimal taxRate = BigDecimal.ZERO;
    	if( coll.size()>0 ){
    		entryInfo = coll.get(0);
    		taxRate = entryInfo.getExchangeRate();
    	}
    	
    	e = list.addElement("amt");			e.setText(info.getAmountApproved()+"");  //�����ȸ�
    	e = list.addElement("rate");			e.setText(taxRate+""); //˰�� TODO 
    	e = list.addElement("amtfor");			e.setText(info.getAmountApproved().divide(taxRate)+"");  //ԭ��  
    	 
    	
	}
	
	//������
	private void  updateReceiveInfoXml(EvectionLoanBillInfo info,Element list) throws BOSException{
		ReceiveObjectEnum  reenum =  info.getReceiveObject();
		if(reenum==null){
			throw new BOSException("�տ�������");
		}
		
		putIsPeople(reenum, list);
    	putLoanInfoDiff(info, list);
    	 
    	Element   e = list.addElement("settletype"); e.setText(info.getPayMode().getName());
    	
    	EvectionLoanBillEntryCollection  coll = info.getEntries();
    	EvectionLoanBillEntryInfo entryInfo = null;
    	BigDecimal taxRate = BigDecimal.ZERO;
    	if( coll.size()>0 ){
    		entryInfo = coll.get(0);
    		taxRate = entryInfo.getExchangeRate();
    	}
    	
    	e = list.addElement("amt");			e.setText(info.getAmountApproved()+"");  //�����ȸ�
    	e = list.addElement("rate");			e.setText(taxRate+""); //˰�� TODO 
    	e = list.addElement("amtfor");			e.setText(info.getAmountApproved().divide(taxRate)+"");  //ԭ��  
    	 
    	
	}
	
	//���ñ���
	private void  updateReceiveInfoXml(TravelAccountBillInfo info,Element list){
		
		TravelAccountBillAccountEntryInfo  entry = info.getCollectionEntries().get(0);
    	ReceiveObjectEnum  reenum =  entry.getReceiveObject();

    	putIsPeople(reenum, list);
    	
    	putLoanInfoDiff(entry, list);
		
    	Element e = list.addElement("settletype"); e.setText(info.getPayMode().getName());
    	
    	
    	TravelAccountBillEntryCollection  coll = info.getEntries();
    	TravelAccountBillEntryInfo entryInfo = null;
    	BigDecimal taxRate = BigDecimal.ZERO;
    	if( coll.size()>0 ){
    		entryInfo = coll.get(0);
    		taxRate = entryInfo.getExchangeRate();
    	}
    	
    	e = list.addElement("amt");			e.setText(info.getAmountEncashed()+"");  //�����ȸ�
    	e = list.addElement("rate");			e.setText(taxRate+""); //˰�� TODO 
    	e = list.addElement("amtfor");			e.setText(info.getAmountEncashed().divide(taxRate)+"");  //ԭ��  
    	 
	}
	
	//���� �տ���Ϣ
	private void putLoanInfoDiff(CollectionAccountCoreBaseInfo entry,Element list){
		 Element e =null;
		 e = list.addElement("collectname");   e.setText(entry.getPayerName()==null?"":entry.getPayerName());
    	 e = list.addElement("collectbank");   e.setText(entry.getPayerBank()==null?"":entry.getPayerBank());
    	 e = list.addElement("collectbankno"); e.setText(entry.getPayerAccount()==null?"":entry.getPayerAccount());
    	 String city= (String)entry.get("bak_city");
    	 String province= (String)entry.get("bak_province");
    	 e = list.addElement("economize");  e.setText(city==null?"":city);
    	 e = list.addElement("city");  		 e.setText(province==null?"":province);
	}
	
	//��� �տ���Ϣ
	private void putLoanInfoDiff(BizCollBillBaseInfo info,Element list){
		 Element e =null;
		 e = list.addElement("collectname");   e.setText(info.getPayerName()==null?"":info.getPayerName());
		 e = list.addElement("collectbank");   e.setText(info.getPayerBank()==null?"":info.getPayerBank());
		 e = list.addElement("collectbankno"); e.setText(info.getPayerAccount()==null?"":info.getPayerAccount());
		 
		 String city= (String)info.get("bak_city");
    	 String province= (String)info.get("bak_province");
    	 e = list.addElement("economize");  e.setText(city==null?"":city);
    	 e = list.addElement("city");  		 e.setText(province==null?"":province);
    	 
	}
 
	//�տ����
	private void putIsPeople(ReceiveObjectEnum reenum,Element list ){
		Element e = list.addElement("ispeople");
		if(ReceiveObjectEnum.personal.equals(reenum)){
    		e.setText("1");
    	}else {
    		e.setText("0");
    	} 
	}
	
	
	
	
	/**
	 * ���¸���״̬
	 * @param ctx
	 * @throws BOSException
	 */
	private void updateBillNoticeStatus(Context ctx) throws BOSException{
    	//���ñ���
    	try {
			updateBillNoticeStatusByType(ctx, new BizAccountBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("���ñ������¸���״̬����");
		}
    	//���ñ���
    	try {
			updateBillNoticeStatusByType(ctx, new TravelAccountBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("���ñ������¸���״̬����");
		}
    	//���
    	try {
			updateBillNoticeStatusByType(ctx, new DailyLoanBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("��� ���¸���״̬����");
		}
    	//������
    	try {
			updateBillNoticeStatusByType(ctx, new EvectionLoanBillInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("��������¸���״̬����");
		}
    }
    
    
    
    /**
     * ����һ�൥�ݸ���״̬
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws SQLException
     */
    private void updateBillNoticeStatusByType(Context ctx,BizCollBillBaseInfo info) throws BOSException, SQLException{
   	 	EntityViewInfo  view = getBillFilter(null);
	   	 IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
	   	 AbstractObjectCollection  coll  = (AbstractObjectCollection)iDynamicObject.getCollection(info.getBOSType(), view);
	    	
	   	 if(coll.size()==0){
	   		 return ;
	   	 }
	   	 Set<String> revSet = K3StatusReWriteUtil.getBillNum(coll);
   	 
   	 	Set<String>  noticeNumSet = K3StatusReWriteUtil.getNoticeBillStatus(ctx, revSet); //��ȡ�����ɵ�֪ͨ����
   	 	if(noticeNumSet==null || noticeNumSet.size()==0){
   	 		return ;
   	 	}
		 String tableName  = K3VoucherDBUtil.getEntityMainTable(ctx, info.getBOSType());
		 K3StatusReWriteUtil.updateNoticeTableStatus(ctx, tableName, "CFIsK3Paid", noticeNumSet);
   }
    
    /**
     * ���ݲ�ѯ����
     * @param bosType  unused
     * @return
     */
    private EntityViewInfo getBillFilter(BOSObjectType  bosType){
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.getSelector().add(new SelectorItemInfo("id"));
    	view.getSelector().add(new SelectorItemInfo("number"));
    	view.getSelector().add(new SelectorItemInfo("name"));
    	filter.getFilterItems().add(new FilterItemInfo("state",StateEnum.CHECKED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isK3Paid",Boolean.FALSE));
		//filter.setMaskString("#0 and #1 ");
    	return view;
    }
    
    
	private void createLog(String logStr){
		return ;
	}
	
	
	 private SelectorItemCollection getBillSelector(BOSObjectType  bosType){
	    	SelectorItemCollection sic = new SelectorItemCollection();
	    	sic.add("*");
	    	
	    	sic.add(new SelectorItemInfo("auditor.id"));
	    	sic.add(new SelectorItemInfo("auditor.name"));
	    	sic.add(new SelectorItemInfo("auditor.number"));
	    	
	    	sic.add("company.id");
	    	sic.add("company.number");
	    	
	    	sic.add("payCompany.id");
	    	sic.add("payCompany.number");
	    	sic.add("payCompany.simpleCode");
	    	sic.add("payCompany.bankAccountNumber");
	    	
	    	
	    	sic.add("applier.id");
	    	sic.add("applier.number");
	    	
	    	sic.add("expenseType.id");
	    	sic.add("expenseType.name");
	    	
	    	sic.add("currencyType.id");
	    	sic.add("currencyType.number");
	    	sic.add("currencyType.isoCode");
	    	
	    	sic.add("costedDept.id");
	    	sic.add("costedDept.name");
	    	
	    	sic.add("orgUnit.id");
	    	sic.add("orgUnit.name");
	    	sic.add("orgUnit.number");
	    	
	    	sic.add("creator.id");
	    	sic.add("creator.name");
	    	
	    	
	    	sic.add("payMode.id");
	    	sic.add("payMode.number");
	    	sic.add("payMode.name");
	    	
	    	sic.add("entries.exchangeRate");
	    	
	    	if(bosType!=null && ("4A44F49F".equals(bosType.toString()) || "C57003BC".equals(bosType.toString())) ){
	    	//if(new BizAccountBillInfo().getBOSType().equals(bosType) || new TravelAccountBillInfo().getBOSType().equals(bosType)){
	    		sic.add("collectionEntries.*");
	    		//sic.add("collectionEntries.accountOpenArea");
	    	}else {
	    		sic.add("payerBankStr.id");
	    		sic.add("payerBankStr.BeProvince");
	    		sic.add("payerBankStr.BeCity");
	    	}
	    	 
	    	return sic;
	    }
	
	
	
}