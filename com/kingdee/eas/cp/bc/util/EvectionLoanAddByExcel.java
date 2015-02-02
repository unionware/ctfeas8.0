/*
 * @(#)com.kingdee.eas.cp.bc.util.EvectionLoanAddByExcel.java
 *
 * 金蝶国际软件集团有限公司版权所有.
 */
package com.kingdee.eas.cp.bc.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.IUser;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ISettlementType;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.basedata.org.IPositionMember;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.BankInfoCollection;
import com.kingdee.eas.basedata.person.BankInfoFactory;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.basedata.person.IBankInfo;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.IPersonFacade;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFacadeFactory;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.IEvectionLoanBill;
import com.kingdee.eas.cp.bc.IExpenseType;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.ReceiveObjectEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.VehicleEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 描述: 出差借款单引入引出
 * @author dawei_li   date:2006-06-15 <p>
 * @version EASV6.0sp 
 */


public class EvectionLoanAddByExcel extends AbstractDataTransmission {

	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.cp.bc.util.EvectionLoanAddByExcel");
	
	// String 型
	String[] STRING_FIELDS = new String[] { "FTel", "FCause", "FName" };

	String[] STRING_OBJS = new String[] { "tel", "cause", "name" };
	
	 //日期类型的字段
	String[] DATE_FIELDS = new String[] { "FForeseeDate","FBizReqDate"};

	String[] DATE_OBJS = new String[] { "foreseeDate","bizReqDate"};
	//分录entry
	
    //BigDecimal 型数据?
	String[] BIGDECIMAL_FIELDS_EN = new String[] {"FTaxiExpense","FBusExpense","FHotelExpense","FOtherExpense" };

	String[] BIGDECIMAL_OBJ_EN = new String[] {"taxiExpense","bussesExpense","hotelExpense","otherExpense" };
	
    //日期类型的字段
	String[] DATE_FIELDS_EN = new String[] { "FStartDate","FEndDate" };

	String[] DATE_OBJS_EN = new String[] { "startDate","endDate" };
	
    //String 型
	String[] STRING_FIELDS_EN = new String[] { "FFrom","FTo" };

		String[] STRING_OBJS_EN = new String[] { "from","to" };

	private  IObjectPK TEMPPK = null;

	private  String TEMPNUMBER = null;

	private final static String RES = "com.kingdee.eas.cp.bc.ImportDataResource";
	
	//added by dasong_ji
	//query path
	private final String solution = "com.kingdee.eas.cp.bc.app.EvectionLoanForPrintQuery";
	
	private Set idsEverImported = new HashSet();
	
	/**
	 * 通过编码得到id
	 */
	private IObjectPK getInfoPKbyNumber(Context ctx,String number) throws TaskExternalException{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//	    filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.
//                 objectBase_CU, ContextUtil.getCurrentCtrlUnit(ctx).getId().toString(), CompareType.EQUALS));
	    filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.dataBase_Number,number,CompareType.EQUALS));
//	    filter.setMaskString("#0 and #1");
	    viewInfo.setFilter(filter);
	    viewInfo.getSelector().add(new SelectorItemInfo(IFWEntityStruct.coreBase_ID));
	    try{
	    	CoreBaseCollection col = this.getController(ctx).getCollection(viewInfo);
	    	if(col.size() > 0){
	    		return new ObjectUuidPK(col.get(0).getId());
	    	}
	    }catch(BOSException bex){
	    	throw new TaskExternalException(bex.getMessage(), bex.getCause());
	    }
	    
		return null;
	}
	/**
	 * 描述：
	 * 
	 * @author:windy
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)
	 */
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return EvectionLoanBillFactory.getLocalInstance(ctx);
		} catch (BOSException bex) {
			throw new TaskExternalException(bex.getMessage(), bex.getCause());
		}
	}

	/**
	 * 
	 * 描述：
	 * 
	 * @author:windy
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo,
	 *      com.kingdee.bos.Context)
	 */
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		ICoreBase ipurReturns = getController(ctx);

		try {
			if (coreBaseInfo.getId() == null
					|| !ipurReturns.exists(new ObjectUuidPK(coreBaseInfo
							.getId()))) {
				TEMPPK = ipurReturns.addnew(coreBaseInfo);
			} else {
				//BizAccountBillInfo info = (BizAccountBillInfo) coreBaseInfo;
				ipurReturns.update(new ObjectUuidPK(coreBaseInfo.getId()),
						coreBaseInfo);
			}
			idsEverImported.add(TEMPPK);
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}

	/**
	 * 描述：
	 * 
	 * @author:david
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable,
	 *      com.kingdee.bos.Context)
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {

		EvectionLoanBillInfo evetionLoanInfo = null;
		Object data = null;

		Object numberdata = ((DataToken) hsData.get("FNumber")).data;
		String str = null;
		if (numberdata != null) {
			str = numberdata.toString();
			if (str != null && str.trim().length() > 0) {
//				FixExpenseAccountBillInfo tempInfo = null;
//				tempInfo = checkExist(ctx,str);
				if(str.equals(TEMPNUMBER) || (TEMPPK = this.getInfoPKbyNumber(ctx,str)) != null){
					IEvectionLoanBill iFixBill;
					try {
						iFixBill = EvectionLoanBillFactory
								.getLocalInstance(ctx);
						EvectionLoanBillInfo travelExpenseInfo = iFixBill
								.getEvectionLoanBillInfo(TEMPPK);
						if (travelExpenseInfo != null) {
							evetionLoanInfo = travelExpenseInfo;
						}
						// fixExpenseAccInfo.setId(fixExpenseInfo.getId());
						TEMPNUMBER = str;
					} catch (BOSException e) {
						// TODO 自动生成 catch 块
						throw new TaskExternalException(e.getMessage(),e.getCause());
					} catch (EASBizException e) {
						// TODO 自动生成 catch 块
						throw new TaskExternalException(e.getMessage(),e.getCause());
					}
//					if (tempInfo != null) {//同一张单据的不同分录
//					fixExpenseAccInfo = tempInfo;
				} else {//新的单据
					evetionLoanInfo = new EvectionLoanBillInfo();
					evetionLoanInfo.setNumber(str);
					TEMPNUMBER = str;			
					 
				}
				//数据库中不存在或覆盖更新时重新设置值对象属性值
				if(TEMPPK == null || this.isSltImportUpdate()){
					// 处理String字段
					for (int i = 0; i < STRING_FIELDS.length; i++) {
						Object o = ((DataToken) hsData.get(STRING_FIELDS[i])).data;
						if (o != null) {
							String value = o.toString();
							evetionLoanInfo.setString(STRING_OBJS[i], value);
						}
					}
	                   //处理日期字段
					DateFormat df = MultilingualUtil.getDateFormat(ctx);
					for (int i = 0; i < DATE_FIELDS.length; i++) {
						Object o = ((DataToken) hsData.get(DATE_FIELDS[i])).data;
						if (o != null && o.toString().length() > 0) {
							try {
								o = df.parse(o.toString());
							} catch (ParseException pex) {
								logger.error(pex.getMessage());
								throw new TaskExternalException(
										DATE_FIELDS[i]
												+ResourceBase.getString(RES,"DATAERROE",ctx.getOriginLocale()));
							}
							if (o != null && o instanceof Date) {
								Date value = (Date) o;
								if (value != null) {
									evetionLoanInfo.setDate(DATE_OBJS[i], value);
								}
							}
						}else{
							if(i == 1){
								evetionLoanInfo.setDate(DATE_OBJS[i], new Date());
							}	
						}
					}
					// FPrior
					data = ((DataToken) hsData.get("FPrior")).data;
					String value = null;
					if (data != null) {

						value = data.toString();
						if (value != null) {

							evetionLoanInfo.setPrior(getPriorValue(value));
						}
					}

					//收款对象-tgw
					data = ((DataToken) hsData.get("FReceiveObject")).data;
				    value = null;
					if (data != null) {
						value = data.toString();
						if (value != null) {
							if(ReceiveObjectEnum.personal.getAlias().equals(value)){
								evetionLoanInfo.setReceiveObject(ReceiveObjectEnum.personal);
							}else if(ReceiveObjectEnum.supplier.getAlias().equals(value)){
								evetionLoanInfo.setReceiveObject(ReceiveObjectEnum.supplier);
							}else{
								throw new TaskExternalException("收款对象输入有误！");
							}
						}
					}
					
					//收款人
					data = ((DataToken) hsData.get("FPayerName")).data;
					if (data != null) {
						str = data.toString();
						if (str != null && str.trim().length() > 0) {
							try{
								if(ReceiveObjectEnum.PERSONAL_VALUE.equals(evetionLoanInfo.getReceiveObject().getValue())){
									IPerson iPerson = PersonFactory.getLocalInstance(ctx);
									EntityViewInfo viewInfo = new EntityViewInfo();
									FilterInfo filter = new FilterInfo();
									filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
									filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
									filter.setMaskString("#0 or #1");
									viewInfo.setFilter(filter);
									viewInfo.getSelector().add(new SelectorItemInfo("id"));
									PersonCollection personCollection = iPerson.getPersonCollection(viewInfo);
									if (personCollection != null && personCollection.size() > 0) {
										PersonInfo pInfo = personCollection.get(0);
										if (pInfo != null) {
											evetionLoanInfo.setPeyerID(pInfo.getId().toString());
										}
									} else {
										throw new TaskExternalException("收款人输入有误！");
									}
									
								}else{
									ISupplier iSupplier = SupplierFactory.getLocalInstance(ctx);
									EntityViewInfo viewInfo = new EntityViewInfo();
									FilterInfo filter = new FilterInfo();
									filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
									filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
									filter.setMaskString("#0 or #1");
									viewInfo.setFilter(filter);
									viewInfo.getSelector().add(new SelectorItemInfo("id"));
									SupplierCollection supplierCollection = iSupplier.getSupplierCollection(viewInfo);
									if (supplierCollection != null && supplierCollection.size() > 0) {
										SupplierInfo sInfo = supplierCollection.get(0);
										if (sInfo != null) {
											evetionLoanInfo.setPeyerID(sInfo.getId().toString());
										}
									} else {
										throw new TaskExternalException("收款人输入有误输入有误！");
									}
								}
							}catch(Exception e){
								throw new TaskExternalException(e.getMessage(),e.getCause());
							}
						}
					}else{
						throw new TaskExternalException("收款人不能为空！");
					}
					
					//银行账号
					data = ((DataToken) hsData.get("FPayerAccount")).data;
					if (data != null) {
						str = data.toString();
						if (str != null && str.trim().length() > 0) {
							try{
								if(ReceiveObjectEnum.PERSONAL_VALUE.equals(evetionLoanInfo.getReceiveObject().getValue())){
									IBankInfo iBank = BankInfoFactory.getLocalInstance(ctx);
									EntityViewInfo viewInfo = new EntityViewInfo();
									FilterInfo filter = new FilterInfo();
									filter.getFilterItems().add(new FilterItemInfo("bandAcctNumber", str,CompareType.EQUALS));
									filter.getFilterItems().add(new FilterItemInfo("person.id", evetionLoanInfo.getPeyerID(),CompareType.EQUALS));
									filter.setMaskString("#0 and #1");
									viewInfo.setFilter(filter);
									BankInfoCollection bankInfoCollection = iBank.getBankInfoCollection(viewInfo);
									if (bankInfoCollection != null && bankInfoCollection.size() > 0) {
										BankInfoInfo bInfo = bankInfoCollection.get(0);
										if (bInfo != null) {
											evetionLoanInfo.setPayerAccount(bInfo.getBandAcctNumber());
											evetionLoanInfo.setPayerBank(bInfo.getBankName());
										}
									} else {
										throw new TaskExternalException("银行账号输入有误！");
									}
									
								}else{
									ISupplierCompanyBank iSupplierBank = SupplierCompanyBankFactory.getLocalInstance(ctx);
									EntityViewInfo viewInfo = new EntityViewInfo();
									FilterInfo filter = new FilterInfo();
									filter.getFilterItems().add(new FilterItemInfo("bankAccount", str,CompareType.EQUALS));
									
									SupplierCompanyInfoInfo sInfo = getSupplierById(ctx,evetionLoanInfo.getPeyerID());
									filter.getFilterItems().add(new FilterItemInfo("supplierCompanyInfo.id", sInfo.getIdentity().toString(),CompareType.EQUALS));
									filter.setMaskString("#0 and #1");
									viewInfo.setFilter(filter);
									SupplierCompanyBankCollection sBankCollection = iSupplierBank.getSupplierCompanyBankCollection(viewInfo);
									if (sBankCollection != null && sBankCollection.size() > 0) {
										SupplierCompanyBankInfo sbInfo = sBankCollection.get(0);
										if (sbInfo != null) {
											evetionLoanInfo.setPayerAccount(sbInfo.getBankAccount());
											evetionLoanInfo.setPayerBank(sbInfo.getBank());
										}
									} else {
										throw new TaskExternalException("银行账号输入有误！");
									}
								}
							}catch(Exception e){
								throw new TaskExternalException(e.getMessage(),e.getCause());
							}
						}
					}else{
						throw new TaskExternalException("银行账号不能为空！");
					}
					
					// 处理ID型字段
					try {
						IUser iuser = UserFactory.getLocalInstance(ctx);
						UserInfo userInfo = null;
						userInfo = iuser.getUserInfo(ctx.getCaller());
						evetionLoanInfo.setBiller(userInfo);

						// FApplierID
						PersonInfo personInfo = null;
						Object tempData = ((DataToken) hsData.get("FApplier")).data;

						if (tempData == null) {
							throw new TaskExternalException(ResourceBase.getString(RES,"USER_NAME_NOT_NULL",ctx.getOriginLocale()));
						}

						str = tempData.toString();
						if (str != null && str.trim().length() > 0) {
							IPerson iPerson = PersonFactory.getLocalInstance(ctx);
							EntityViewInfo viewInfo = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
							filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
							filter.setMaskString("#0 or #1");
							viewInfo.setFilter(filter);
							viewInfo.getSelector().add(new SelectorItemInfo("id"));
							PersonCollection perCollection = iPerson.getPersonCollection(viewInfo);							
							if (perCollection != null && perCollection.size() > 0) {
								if (perCollection.size() > 1)
									throw new TaskExternalException(ResourceBase.getString(RES,"EXIST_SAME_USERNAME",ctx.getOriginLocale()));

								personInfo = perCollection.get(0);
								if (personInfo != null) {
									evetionLoanInfo.setApplier(personInfo);

									IPersonFacade ipf = PersonFacadeFactory
											.getLocalInstance(ctx);
									AdminOrgUnitCollection aouc = ipf
											.getAdminOrgUnitByPerson(personInfo.getId());

									AdminOrgUnitInfo aouInfo = null;
									if (aouc != null && aouc.size() > 0)
										aouInfo = aouc.get(0);
									evetionLoanInfo.setOrgUnit(aouInfo);

									IPositionMember iPoMe = PositionMemberFactory
											.getLocalInstance(ctx);
									PositionMemberCollection poCol = iPoMe
											.getPositionsByPerson(personInfo.getId());
									if (poCol != null && poCol.size() > 0) {
										evetionLoanInfo.setPosition(poCol.get(0)
												.getPosition());
									}

								}
							} else {
								throw new TaskExternalException(ResourceBase.getString(RES,"NOT_EXIST_USERNAME",ctx.getOriginLocale()));
							}
						}

						// FCostedDeptID
						data = ((DataToken) hsData.get("FCostedDept")).data;
						if (data != null) {
							str = data.toString();
							if (str != null && str.trim().length() > 0) {
								ICostCenterOrgUnit iCost = CostCenterOrgUnitFactory.getLocalInstance(ctx);
								EntityViewInfo viewInfo = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
								filter.setMaskString("#0 or #1");
								viewInfo.setFilter(filter);
								viewInfo.getSelector().add(new SelectorItemInfo("id"));
								CostCenterOrgUnitCollection costCollection = iCost.getCostCenterOrgUnitCollection(viewInfo);
								if (costCollection != null && costCollection.size() > 0) {
									CostCenterOrgUnitInfo info = costCollection.get(0);
									if (personInfo != null) {
										evetionLoanInfo.setCostedDept(info);
									}
								} else {
									throw new TaskExternalException(ResourceBase.getString(RES,"COSTDEPT_ERROR",ctx.getOriginLocale()));
								}
							}

						}
						//业务类别
/*						data = ((DataToken) hsData.get("FOperationType")).data;
						if (data != null) {
							str = data.toString();
							if (str != null && str.trim().length() > 0) {
								try{
									IOperationType iOperationType = OperationTypeFactory.getLocalInstance(ctx);
									EntityViewInfo viewInfo = new EntityViewInfo();
									FilterInfo filter = new FilterInfo();
									filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
									filter.getFilterItems().add(new FilterItemInfo("name", str + "%",CompareType.LIKE));
									filter.setMaskString("#0 or #1");
									viewInfo.setFilter(filter);
									viewInfo.getSelector().add(new SelectorItemInfo("id"));
									OperationTypeCollection operationTypeCollection = iOperationType.getOperationTypeCollection(viewInfo);								
									if (operationTypeCollection != null && operationTypeCollection.size() > 0) {
										OperationTypeInfo opTypeInfo = operationTypeCollection.get(0);
										if (opTypeInfo != null) {
											evetionLoanInfo.setOperationType(opTypeInfo);
										}
									}
								}catch(Exception e){
									throw new TaskExternalException(e.getMessage(),e.getCause());
								}

							}
						}*/
						// FCompany
						data = ((DataToken) hsData.get("FCompany")).data;
						if (data != null) {
							str = data.toString();
							if (str != null && str.trim().length() > 0) {
								ICompanyOrgUnit icompany = CompanyOrgUnitFactory.getLocalInstance(ctx);
								EntityViewInfo viewInfo = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
								filter.setMaskString("#0 or #1");
								viewInfo.setFilter(filter);
								viewInfo.getSelector().add(new SelectorItemInfo("id"));
								CompanyOrgUnitCollection collection = icompany.getCompanyOrgUnitCollection(viewInfo);
								if (collection != null && collection.size() > 0) {
									CompanyOrgUnitInfo info = collection.get(0);
									if (info != null) {
										evetionLoanInfo.setCompany(info);
									}
								}
							}
						}
						
						// FApplierCompany
						data = ((DataToken) hsData.get("FApplierCompany")).data;
						if (data != null) {
							str = data.toString();
							if (str != null && str.trim().length() > 0) {
								ICompanyOrgUnit icompany = CompanyOrgUnitFactory
										.getLocalInstance(ctx);
								EntityViewInfo viewInfo = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(
										new FilterItemInfo("number", str,
												CompareType.EQUALS));
								filter.getFilterItems().add(
										new FilterItemInfo("name",str,CompareType.EQUALS));
								filter.setMaskString("#0 or #1");
								viewInfo.setFilter(filter);
								viewInfo.getSelector().add(
										new SelectorItemInfo("id"));
								CompanyOrgUnitCollection collection = icompany
										.getCompanyOrgUnitCollection(viewInfo);
								if (collection != null && collection.size() > 0) {
									CompanyOrgUnitInfo info = collection.get(0);
									if (info != null) {
										evetionLoanInfo
												.setApplierCompany(info);
									}
								}
							}else{
								throw new TaskExternalException(
									ResourceBase.getString(RES,
											"FApplierCompany_NOT_NULL", ctx
													.getOriginLocale()));																	
							}
						}
						
						// FPayMent
						data = ((DataToken) hsData.get("FPayMode")).data;
						if (data != null) {
							str = data.toString();
							if (str != null && str.trim().length() > 0) {
								ISettlementType iSet = SettlementTypeFactory.getLocalInstance(ctx);
								EntityViewInfo viewInfo = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("name",str,CompareType.EQUALS));
								filter.setMaskString("#0 or #1");
								viewInfo.setFilter(filter);
								viewInfo.getSelector().add(new SelectorItemInfo("id"));
								SettlementTypeCollection collection = iSet.getSettlementTypeCollection(viewInfo);
								if (collection != null && collection.size() > 0) {
									SettlementTypeInfo info = collection.get(0);
									if (info != null) {
										evetionLoanInfo.setPayMode(info);
									}
								} else {
									throw new TaskExternalException(ResourceBase.getString(RES,"PAYMODE_ERROR",ctx.getOriginLocale()));
								}
							}
						}

						// FCurrencyID
						data = ((DataToken) hsData.get("FCurrency")).data;
						if (data != null) {
							str = data.toString();
							if (str != null && str.trim().length() > 0) {
								ICurrency icurreny = CurrencyFactory.getLocalInstance(ctx);
								EntityViewInfo viewInfo = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
								filter.setMaskString("#0 or #1");
								viewInfo.setFilter(filter);
								viewInfo.getSelector().add(new SelectorItemInfo("id"));
								CurrencyCollection collection = icurreny.getCurrencyCollection(viewInfo);
								if (collection != null && collection.size() > 0) {
									CurrencyInfo info = collection.get(0);
									if (info != null) {
										evetionLoanInfo.setCurrencyType(info);
									}
								}
							}
						}

						// data = ((DataToken) hsData.get("FEntry")).data;
					} catch (EASBizException e) {
						// TODO 自动生成 catch 块?
						throw new TaskExternalException(e.getMessage(),e.getCause());
					} catch (BOSException e) {
						// TODO 自动生成 catch 块?
						throw new TaskExternalException(e.getMessage(),e.getCause());
					}

					/*data = ((DataToken) hsData.get("FEntryNumber")).data;
					if (data != null) {
						str = data.toString();
						if (str != null) {
							bizExpenseAccInfo.setEntryNumber(Integer.parseInt(str));
						} else {
							bizExpenseAccInfo.setEntryNumber(0);
						}
					} else {
						bizExpenseAccInfo.setEntryNumber(0);
					}*/

					evetionLoanInfo.setBillTypeCode(BizCollBillTypeEnum.ALL);
					evetionLoanInfo.setState(StateEnum.DRAFT);
					//evetionLoanInfo.setBizReqDate((new Date()));
					evetionLoanInfo.setBillDate(new Date());
				}

			} else {
				throw new TaskExternalException("number  not found!!!!");
			}

		} else {
			throw new TaskExternalException("number  not found!!!!");
		}

		//如果选择覆盖更新 
		if(this.isSltImportUpdate() && TEMPPK != null && !idsEverImported.contains(TEMPPK)){
			evetionLoanInfo.getEntries().clear();
			evetionLoanInfo.setAmount(null);
			evetionLoanInfo.setAmountApproved(null);
		}

		// 处理分录?
		EvectionLoanBillEntryInfo entryInfo = null;
		boolean isUpdateEntry = false;

		data = ((DataToken) hsData.get("FSeq")).data;
		if (data != null && data.toString().trim().length() > 0) {
			int seq = Integer.parseInt(data.toString());
			EvectionLoanBillEntryCollection entryCollection = evetionLoanInfo
					.getEntries();
			for (int i = 0, num = entryCollection.size(); i < num; i++) {
				EvectionLoanBillEntryInfo tempentryInfo = entryCollection
						.get(i);
				if (tempentryInfo.getSeq() == seq) {
					//导入单据存分录序号重复给予提示信息
					throw new TaskExternalException(ResourceBase.getString(RES,"Repetition_Coding",ctx.getOriginLocale()));					
				}
			}
			if (!isUpdateEntry) {
				entryInfo = new EvectionLoanBillEntryInfo();
				entryInfo.setBill(evetionLoanInfo);
				entryInfo.setSeq(seq);
				entryCollection.add(entryInfo);
			}
		} else {
			EvectionLoanBillEntryCollection entryCollection = evetionLoanInfo
					.getEntries();
			int seq = entryCollection.size() + 1;
			entryInfo = new EvectionLoanBillEntryInfo();
			entryInfo.setBill(evetionLoanInfo);
			entryInfo.setSeq(seq);
			entryCollection.add(entryInfo);
		}

		handleEntry(evetionLoanInfo, entryInfo, hsData, ctx);

		return evetionLoanInfo;
	}
    
	private SupplierCompanyInfoInfo getSupplierById(Context ctx, String peyerID) throws BOSException {
		SupplierCompanyInfoInfo info = null;
		ISupplierCompanyInfo iSCInfo = SupplierCompanyInfoFactory.getLocalInstance(ctx);
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplier.id", peyerID,CompareType.EQUALS));
		viewInfo.setFilter(filter);
		viewInfo.getSelector().add(new SelectorItemInfo("id"));
		viewInfo.getSelector().add(new SelectorItemInfo("id"));
		SupplierCompanyInfoCollection scInfoCollection = iSCInfo.getSupplierCompanyInfoCollection(viewInfo);
		if (scInfoCollection != null && scInfoCollection.size() > 0) {
			info = scInfoCollection.get(0);
		}
		return info;
	}
	private void handleEntry(EvectionLoanBillInfo info,
			EvectionLoanBillEntryInfo entryInfo, Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// BigDecimal 型数据?
		BigDecimal entryAmount=new BigDecimal("0");
		for (int i = 0; i < BIGDECIMAL_FIELDS_EN.length; i++) {
			try {
				Object o = ((DataToken) hsData.get(BIGDECIMAL_FIELDS_EN[i])).data;
				if (o != null && o.toString().trim().length() > 0) {
					BigDecimal value = new BigDecimal(o.toString());
					if (value != null) {
						entryInfo.setBigDecimal(BIGDECIMAL_OBJ_EN[i], value);
						//分录表
						entryAmount=entryAmount.add(value);
						entryInfo.setBigDecimal("amount", entryAmount);
						//主表?
						BigDecimal tempValue = info.getAmount();
						if (tempValue != null) {
							value = value.add(tempValue);
						}
						info.setAmount(value);
						//info.setAmountApproved(value);
					}
				}
			} catch (NumberFormatException nex) {
				logger.error(nex.getMessage());
				throw new TaskExternalException(BIGDECIMAL_OBJ_EN[i]
						+ ResourceBase.getString(RES,"NUMBERFORMET",ctx.getOriginLocale()));
			}

		}
		
		try {
			Object o = ((DataToken) hsData.get("FEntryAmountApproved")).data;
			if (o != null && o.toString().trim().length() > 0) {
				BigDecimal value = new BigDecimal(o.toString());
				if (value != null) {
					entryInfo.setBigDecimal("amountApproved", value);
					BigDecimal tempValue = info.getAmountApproved();
					if (tempValue != null) {
						value = value.add(tempValue);
					}
					info.setAmountApproved(value);
						// info.setAmountApproved(value);
					}
				}
			} catch (NumberFormatException nex) {
				logger.error(nex.getMessage());
				throw new TaskExternalException("amountApproved" + ResourceBase.getString(RES, "NUMBERFORMET", ctx
				                                                		  .getOriginLocale()));
		}
			
		// 处理日期字段
		DateFormat df = MultilingualUtil.getDateFormat(ctx);
		for (int i = 0; i < DATE_FIELDS_EN.length; i++) {
			Object o = ((DataToken) hsData.get(DATE_FIELDS_EN[i])).data;
			if (o != null && o.toString().length() > 0) {
				try {
					o = df.parse(o.toString());
				} catch (ParseException pex) {
					logger.error(pex.getMessage());
					throw new TaskExternalException(
							DATE_FIELDS_EN[i]
									+ ResourceBase.getString(RES,"DATAERROE",ctx.getOriginLocale()));
				}
				if (o != null && o instanceof Date) {
					Date value = (Date) o;
					if (value != null) {
						entryInfo.setDate(DATE_OBJS_EN[i], value);
					}
				}
			}
		}

		// 处理String字段
		for (int i = 0; i < STRING_FIELDS_EN.length; i++) {
			Object o = ((DataToken) hsData.get(STRING_FIELDS_EN[i])).data;
			if (o != null) {
				String value = o.toString();
				entryInfo.setString(STRING_OBJS_EN[i], value);
			}
		}
		
		Object o = ((DataToken) hsData.get("FVehicle")).data;
		String value = null;
		if (o != null) {

			value = o.toString();
			if (value != null) {

				entryInfo.setVehicle(getVehicleValue(value));
			}
		}
		
		// 费用类别??
		Object data = ((DataToken) hsData.get("FTypeName")).data;
		String str = null;
		if (data != null) {
			str = data.toString();
			if (str != null && str.trim().length() > 0) {
				try{
					IExpenseType iExpenseType = ExpenseTypeFactory
					.getLocalInstance(ctx);
					EntityViewInfo viewInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
					filter.setMaskString("#0 or #1");
					viewInfo.setFilter(filter);
					viewInfo.getSelector().add(new SelectorItemInfo("id"));
					ExpenseTypeCollection expenseTypeCollection = iExpenseType
							.getExpenseTypeCollection(viewInfo);								
					if (expenseTypeCollection != null && expenseTypeCollection.size() > 0) {
						ExpenseTypeInfo exTypeInfo = expenseTypeCollection.get(0);
						if (info != null) {
							entryInfo.setExpenseType(exTypeInfo);
						}
					}/* else {
						throw new TaskExternalException(EASResource.getString(RES,"COSTDEPT_ERROR"));
					}*/
				}catch(Exception e){
					throw new TaskExternalException(e.getMessage(),e.getCause());
				}

			}
		}
		
		// 项目-tgw
		data = ((DataToken) hsData.get("FEntries$project_number")).data;
		str = null;
		if (data != null) {
			str = data.toString();
			if (str != null && str.trim().length() > 0) {
				try{
					IProject iProject = ProjectFactory.getLocalInstance(ctx);
					EntityViewInfo viewInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
					filter.setMaskString("#0 or #1");
					viewInfo.setFilter(filter);
					viewInfo.getSelector().add(new SelectorItemInfo("id"));
					ProjectCollection projectCollection = iProject.getProjectCollection(viewInfo);					
					if (projectCollection != null && projectCollection.size() > 0) {
						ProjectInfo pInfo = projectCollection.get(0);
						if (info != null) {
							entryInfo.setProject(pInfo);
						}
					}else{
						throw new TaskExternalException("分录项目输入有误！");
					}
				}catch(Exception e){
					throw new TaskExternalException(e.getMessage(),e.getCause());
				}
			}
		}
		
		//费用归属部门-tgw
		data = ((DataToken) hsData.get("FEntries$costedDept_number")).data;
		if (data != null) {
			str = data.toString();
			if (str != null && str.trim().length() > 0) {
				try{
					ICostCenterOrgUnit iCost = CostCenterOrgUnitFactory.getLocalInstance(ctx);
					EntityViewInfo viewInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
					filter.setMaskString("#0 or #1");
					viewInfo.setFilter(filter);
					viewInfo.getSelector().add(new SelectorItemInfo("id"));
					CostCenterOrgUnitCollection costCollection = iCost.getCostCenterOrgUnitCollection(viewInfo);
					if (costCollection != null && costCollection.size() > 0) {
						CostCenterOrgUnitInfo cInfo = costCollection.get(0);
						if (info != null) {
							entryInfo.setCostedDept(cInfo);
						}
					} else {
						throw new TaskExternalException("分录费用归属部门输入有误！");
					}
				}catch(Exception e){
					throw new TaskExternalException(e.getMessage(),e.getCause());
				}
			}
		}else{
			throw new TaskExternalException("分录费用归属部门不能为空！");
		}
		
		//职员-tgw
		data = ((DataToken) hsData.get("FEntries$person_number")).data;
		if (data != null) {
			str = data.toString();
			if (str != null && str.trim().length() > 0) {
				try{
					IPerson iPerson = PersonFactory.getLocalInstance(ctx);
					EntityViewInfo viewInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number", str,CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("name", str,CompareType.EQUALS));
					filter.setMaskString("#0 or #1");
					viewInfo.setFilter(filter);
					viewInfo.getSelector().add(new SelectorItemInfo("id"));
					PersonCollection personCollection = iPerson.getPersonCollection(viewInfo);
					if (personCollection != null && personCollection.size() > 0) {
						PersonInfo pInfo = personCollection.get(0);
						if (info != null) {
							entryInfo.setPerson(pInfo);
						}
					} else {
						throw new TaskExternalException("分录职员输入有误！");
					}
				}catch(Exception e){
					throw new TaskExternalException(e.getMessage(),e.getCause());
				}
			}
		}else{
			throw new TaskExternalException("分录职员不能为空！");
		}
		
		BizCollUtil.initInportEntryData(entryInfo, hsData, ctx);
	}
	/*
	 * modified by dasong_ji （非 Javadoc）
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#exportTransmit(com.kingdee.jdbc.rowset.IRowSet, com.kingdee.bos.Context)
	 */
	public Hashtable exportTransmit(IRowSet rs, Context ctx) throws TaskExternalException {
		
		Hashtable result = new Hashtable();
		DateFormat df = null;
		try {
			df = MultilingualUtil.getDateFormat(ctx);
		} catch (Exception e) {
			logger.error(e);
		}
		try {	
			if(!StringUtils.isEmpty(rs.getString("number"))){
				result.put("FNumber",rs.getString("number"));
			}
			if(!StringUtils.isEmpty(rs.getString("name"))){
				result.put("FName",rs.getString("name"));
			}
			if(rs.getBigDecimal("amountApproved") != null){
				result.put("FAmountApproved",rs.getBigDecimal("amountApproved"));
			}
			if(rs.getBigDecimal("amount") != null){
				result.put("FAmount",rs.getBigDecimal("amount"));
			}
			if(!StringUtils.isEmpty(rs.getString("currencyType.name"))){
				result.put("FCurrency",rs.getString("currencyType.name"));
			}
			if(!StringUtils.isEmpty(rs.getString("applier.name"))){
				result.put("FApplier",rs.getString("applier.name"));
			}
			if(!StringUtils.isEmpty(rs.getString("orgUnit.name"))){
				result.put("FOrgUnit",rs.getString("orgUnit.name"));
			}
			if(!StringUtils.isEmpty(rs.getString("applierCompanyName"))){
				result.put("FApplierCompany",rs.getString("applierCompanyName"));
			}
			if(rs.getDate("bizReqDate") != null){
				Date date = (Date)rs.getDate("bizReqDate");
				result.put("FBizReqDate", df.format(date));
			}
			if(!StringUtils.isEmpty(rs.getString("expenseType.typeName"))){
				result.put("FTypeName",rs.getString("expenseType.typeName"));
			}
			if(!StringUtils.isEmpty(rs.getString("company.name"))){
				result.put("FCompany",rs.getString("company.name"));
			}
			if(!StringUtils.isEmpty(rs.getString("costedDept.name"))){
				result.put("FCostedDept",rs.getString("costedDept.name"));
			}
			
			result.put("FPrior", this.getPriorValue(String.valueOf(rs.getInt("prior"))).getAlias(ctx.getOriginLocale()));
			
			if(!StringUtils.isEmpty(rs.getString("tel"))){
				result.put("FTel",rs.getString("tel"));
			}
			if(rs.getDate("foreseeDate") != null){
				Date date = (Date)rs.getDate("foreseeDate");
				result.put("FForeseeDate", df.format(date));
			}
			if(!StringUtils.isEmpty(rs.getString("cause"))){
				result.put("FCause",rs.getString("cause"));
			}
			if(!StringUtils.isEmpty(rs.getString("payMode.name"))){
				result.put("FPayMode",rs.getString("payMode.name"));
			}
			result.put("FSeq",new Integer(rs.getInt("entries.seq")).toString());

			if(rs.getString("entries.startDate") != null){
				Date date = (Date)rs.getDate("entries.startDate");
				result.put("FStartDate", df.format(date));
			}
			if(rs.getString("entries.endDate") != null){
				Date date = (Date)rs.getDate("entries.endDate");
				result.put("FEndDate", df.format(date));
			}
			if(!StringUtils.isEmpty(rs.getString("entries.from"))){
				result.put("FFrom",rs.getString("entries.from"));
			}
			if(!StringUtils.isEmpty(rs.getString("entries.to"))){
				result.put("FTo",rs.getString("entries.to"));
			}
			if(!StringUtils.isEmpty(rs.getString("entries.vehicle"))){
				result.put("FVehicle",rs.getString("entries.vehicle"));
			}
			if(rs.getBigDecimal("entries.taxiExpense") != null){
				result.put("FTaxiExpense",rs.getBigDecimal("entries.taxiExpense"));
			}
			
			if(rs.getBigDecimal("entries.bussesExpense") != null){
				result.put("FBusExpense",rs.getBigDecimal("entries.bussesExpense"));
			}
			
			if(rs.getBigDecimal("entries.hotelExpense") != null){
				result.put("FHotelExpense",rs.getBigDecimal("entries.hotelExpense"));
			}
			
			if(rs.getBigDecimal("entries.otherExpense") != null){
				result.put("FOtherExpense",rs.getBigDecimal("entries.otherExpense"));
			}
			
			if (rs.getBigDecimal("entries.amount") != null) {
				result.put("FEntryAmount", rs.getBigDecimal("entries.amount"));
			}
			if (rs.getBigDecimal("entries.amountApproved") != null) {
				result.put("FEntryAmountApproved", rs
						.getBigDecimal("entries.amountApproved"));
			}
//			if(StringUtils.isEmpty(rs.getString(""))){
//				result.put("FCreatorNumber",rs.getString(""));
//			}
			if(!StringUtils.isEmpty(rs.getString("createTime"))){
				Date date = (Date)rs.getDate("createTime");
				result.put("FCreateTime", df.format(date));
			}			
			BizCollUtil.initExportEntryData(result,rs);					
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new TaskExternalException("Database access error occurs, can't read field values!", e);
		}
		
		return result;
	}
	
	
	
	public FilterInfo getExportFilterForQuery(Context ctx) {
		return null;
	}
	
	public String getExportQueryInfo(Context ctx) {
		return solution;
	}

	

	private PriorEnum getPriorValue(String value) {

		PriorEnum tSaved = PriorEnum.LOW;
		if (value != null) {
			value = value.trim();
			List EnumList = PriorEnum.getEnumList();
			for (int i = 0, num = EnumList.size(); i < num; i++) {
				PriorEnum priorEnum = (PriorEnum) EnumList.get(i);
				String enumValue = String.valueOf(priorEnum.getValue());
				String enumName = priorEnum.getName();
				String enumAlias = priorEnum.getAlias();
				if (enumValue.equalsIgnoreCase(value)
						|| enumName.equalsIgnoreCase(value)
						|| enumAlias.equalsIgnoreCase(value)) {
					tSaved = priorEnum;
					break;
				}
			}
		}
		return tSaved;

	}
	private VehicleEnum getVehicleValue(String value) {

		VehicleEnum tSaved = VehicleEnum.BUSSES;
		if (value != null) {
			value = value.trim();
			List EnumList = VehicleEnum.getEnumList();
			for (int i = 0, num = EnumList.size(); i < num; i++) {
				VehicleEnum veEnum = (VehicleEnum) EnumList.get(i);
				String enumValue = String.valueOf(veEnum.getValue());
				String enumName = veEnum.getName();
				String enumAlias = veEnum.getAlias();
				if (enumValue.equalsIgnoreCase(value)
						|| enumName.equalsIgnoreCase(value)
						|| enumAlias.equalsIgnoreCase(value)) {
					tSaved = veEnum;
					break;
				}
			}
		}
		return tSaved;

	}
}

	
	