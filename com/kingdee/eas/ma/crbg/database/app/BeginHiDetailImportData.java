package com.kingdee.eas.ma.crbg.database.app;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.data.framework.bos.BosExecutor.BosIdsResultSet;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGFactory;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.IAssistantHG;
import com.kingdee.eas.basedata.master.auxacct.IImpAssistantHG;
import com.kingdee.eas.basedata.master.auxacct.ImpAssistantHGFactory;
import com.kingdee.eas.basedata.master.auxacct.ImpAssistantHGInfo;
import com.kingdee.eas.basedata.master.material.app.DataImportTools;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.fi.gl.EntryDC;
import com.kingdee.eas.fi.gl.InitImportBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillFactory;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
public class BeginHiDetailImportData extends InitImportBase {
	private static final Logger logger = Logger.getLogger(BeginHiDetailImportData.class);
	
	
	private Map unitMap = new HashMap();
	private Map currMap = new HashMap();
	private Map FCAAMap = new HashMap();
	private Map AccountMap = new HashMap();
	private Map proMap = new HashMap();
	private Map proGroupMap = new HashMap();
	private Map infoMap = new HashMap();
	private  int Fnoi=0;
	private int dd = 0;
	private Set keySets = new HashSet();
	private String actualAssistantHGInfoID;
	public BeginHiDetailImportData() {
		actualAssistantHGInfoID = null;
	}
	
	

	@Override
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		// TODO Auto-generated method stub
		try {
			return BeginHiDetailBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			throw new TaskExternalException(getType(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)
	 */
	@Override
	/*
	 * 导入校验和存放数据
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		String str = null;
		String Fno = "";
		BeginHiDetailBillInfo info = new BeginHiDetailBillInfo();
		
		Fno = getDataString(hsData, "FEntrys_FNo");
		int Num;
		DateFormat dfe = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
		if((Fno!=null)&&(Fno.trim().length())!=0){
			Num = Integer.parseInt(Fno);
			if(keySets.contains(Num)){
				throw new TaskExternalException("“历史明细账引入表_序号”重复！");
			}
			keySets.add(Num);
			//assMap.put(Fnoi, Num);
			//Fnoi++;
			
		}else{
			throw new TaskExternalException("“历史明细账引入表_序号”为空！");
		}
		/*Set<String> mapSet =  assMap.keySet();
		Iterator<String> itor =  mapSet.iterator();
		for(int i = 1;i<mapSet.size();i++){
			if(assMap.get(i).equals(assMap.get(i-1))){
				throw new TaskExternalException("“历史明细账引入表_序号”重复！");
			}
		}*/
		
		str = getDataString(hsData,"FEntrys$FOrgUnitID_number");//财务组织
		if ((str != null) && ((str.trim().length()) != 0)) {
			CompanyOrgUnitCollection orgCollection = null;
			if(!unitMap.containsKey(str)){
			try{
				EntityViewInfo classViewInfo = new EntityViewInfo();
				FilterItemInfo filterItem = new FilterItemInfo("number", str, CompareType.EQUALS);
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(filterItem);
				classViewInfo.setFilter(filterInfo);
				orgCollection=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(classViewInfo);
			}catch(BOSException e){
				logger.error(e);
			}
			if(orgCollection!=null&&(orgCollection.size()>0)){
				
					info.setCompanyOrg(orgCollection.get(0));
					unitMap.put(str,orgCollection.get(0) );
				}else{
					throw new TaskExternalException("“历史明细账引入表_财务组织”没有相对应的数据！");
				}
			}else{
				info.setCompanyOrg((CompanyOrgUnitInfo) unitMap.get(str));
			}
		}else{
			throw new TaskExternalException("“历史明细账引入表_财务组织”为空！");
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		str = getDataString(hsData,"FEntrys_BusinessDate");
		Date o = null;
		if ((str != null) && ((str.trim().length()) != 0)) {
			try {
				o = df.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(o!=null){
				info.setBizDate(o);
			}else{
				throw new TaskExternalException("“历史明细账引入表_业务日期”格式应为yy-mm-dd！");
			}
		}else{
			throw new TaskExternalException("“历史明细账引入表_业务日期”为空！");
		}
		str = getDataString(hsData, "FEntrys_DateOfEntry");
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date dateofentry = null;
		if ((str != null) && ((str.trim().length()) != 0)) {
			try {
				dateofentry = date.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dateofentry!=null){
				info.setDateOfEntry(dateofentry);
			}else{
				throw new TaskExternalException("“历史明细账引入表_记账日期”格式应为yy-mm-dd！");
			}
		}else{
			throw new TaskExternalException("“历史明细账引入表_记账日期”为空！");
		}
		str = getDataString(hsData, "FEntrys$Fcurrency_number");
		if(((str!=null))&&((str.trim().length()))!=0){
			CurrencyCollection currencycoll = null;
			if(!currMap.containsKey(str)){
			try{
				EntityViewInfo classViewInfo = new EntityViewInfo();
				FilterItemInfo filterItem = new FilterItemInfo("number", str, CompareType.EQUALS);
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(filterItem);
				classViewInfo.setFilter(filterInfo);
				currencycoll = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(classViewInfo);
			}catch(BOSException e){
				logger.error(e);
			}
			if(currencycoll!=null&&(currencycoll.size()>0)){
				info.setFcurrency(currencycoll.get(0));
				currMap.put(str, currencycoll.get(0));
			}else{
				throw new TaskExternalException("币别没有相对应 的数据！");
			}
			}else{
				info.setFcurrency((CurrencyInfo) currMap.get(str));
			}
			
		}else{
			throw new TaskExternalException("币别 为空！");
		}
		str = getDataString(hsData, "FEntrys$FCAA_number");
		if((str!=null)&&(str.trim().length())!=0){
			AsstAccountCollection asstcoll = null;
			if(!FCAAMap.containsKey(str)){
			try{
				EntityViewInfo classViewInfo = new EntityViewInfo();
				FilterItemInfo filterItem = new FilterItemInfo("number", str, CompareType.EQUALS);
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(filterItem);
				classViewInfo.setFilter(filterInfo);
				asstcoll = AsstAccountFactory.getLocalInstance(ctx).getAsstAccountCollection(classViewInfo);
			}catch(BOSException e){
				logger.error(e);
			}
				if(asstcoll!=null&&asstcoll.size()>0){
					info.setFCAA(asstcoll.get(0));
					FCAAMap.put(str, asstcoll.get(0));
				}else{
					throw new TaskExternalException("辅助账类型没有相对应 的数据！");
				}
			}else{
				info.setFCAA((AsstAccountInfo) FCAAMap.get(str));
			}
		}
		str = getDataString(hsData, "FEntrys$FAccountID_number");
		if((str!=null)&&(str.trim().length())!=0){
			AccountViewCollection viewcoll = null;
			if(!AccountMap.containsKey(str)){
			try{
				String companyNum = getDataString(hsData,"FEntrys$FOrgUnitID_number");
				CompanyOrgUnitInfo companyInfo = (CompanyOrgUnitInfo) unitMap.get(companyNum);
				
				String caaNum = getDataString(hsData, "FEntrys$FCAA_number");
				AsstAccountInfo assAccInfo = (AsstAccountInfo) FCAAMap.get(caaNum);
				
				EntityViewInfo classViewInfo = new EntityViewInfo();
				
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("name"));
				sic.add(new SelectorItemInfo("number"));
				sic.add(new SelectorItemInfo("companyID.*"));
				sic.add(new SelectorItemInfo("CAA.*"));
				
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("number", str, CompareType.EQUALS));//编码
				filterInfo.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString(), CompareType.EQUALS));//财务组织
				filterInfo.getFilterItems().add(new FilterItemInfo("CAA.id", assAccInfo.getId().toString(), CompareType.EQUALS));//辅助账类型
				filterInfo.setMaskString("#0 and #1 and #2");
				
				classViewInfo.setSelector(sic);
				classViewInfo.setFilter(filterInfo);
				viewcoll = AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(classViewInfo);
			}catch(BOSException e){
				logger.error(e);
			}
			if(viewcoll!=null&&viewcoll.size()!=0){
				info.setFAccountID(viewcoll.get(0));
				AccountMap.put(str, viewcoll.get(0));
			}else{
				throw new TaskExternalException("科目没有相对应 的数据！");
			}
			}else{
				info.setFAccountID((AccountViewInfo) AccountMap.get(str));
			}
		}else{
			throw new TaskExternalException("科目为空！");
		}
		str = getDataString(hsData, "FEntrys_Abstract");
		if((str!=null)&&(str.trim().length())!=0){
			info.setContents(str);
		}
		try{
			str = getDataString(hsData, "FEntrys$FCAA_number");
			String FID = info.getFCAA().getId().toString();
			String mapKey = "";
			if(str!=null&&str.trim().length()>0){
					String sqltoacctaccount = "select c.Fname_" + ctx.getLocale() + "  Fname ,a.FNumber fcaaNumber  from T_BD_AsstAccount a  inner join T_BD_AsstActGroupDetail b on a.fid=b.fasstaccountid  inner join  t_bd_asstacttype c on b.fasstacttypeid=c.fid where a.FID=? order by c.fnumber";	
					IRowSet rsToGetAcctname = DbUtil.executeQuery(ctx, sqltoacctaccount, new Object[] { FID});
		         if (rsToGetAcctname != null) {
		           int siz = rsToGetAcctname.size();
		           String[] asstactTypeName = new String[siz];
		           String[] asstactTypeNumber = new String[siz];
		           String[] asstactType = new String[siz];
		           for (int iop = 0; iop < siz; ++iop) {
		             rsToGetAcctname.next();
		             asstactType[iop] = rsToGetAcctname.getString("Fname");
		           }
		           for (int i = 0; i < siz; ++i) {
		             int ii = 0; for (int counts = 8; ii < counts; ++ii) {
		               String Fitem = ((DataToken)hsData.get("FEntrys$AuxActType" + String.valueOf(ii + 1)+"_number")).data.toString();
		               if ((!(Fitem.equals(asstactType[i]))) || 
		                 (((DataToken)hsData.get("FEntrys_FNumber" + String.valueOf(ii + 1))).data == null) || (((DataToken)hsData.get("FEntrys_FName" + String.valueOf(ii + 1))).data == null)) continue;
		               asstactTypeNumber[i] = ((DataToken)hsData.get("FEntrys_FNumber" + String.valueOf(ii + 1))).data.toString();
		               asstactTypeName[i] = ((DataToken)hsData.get("FEntrys_FName" + String.valueOf(ii + 1))).data.toString();
		              mapKey = hsData.get("FEntrys$AuxActType" + String.valueOf(ii + 1)+"_number").toString()+hsData.get("FEntrys_FName" + String.valueOf(ii + 1))+asstactType[i];
		              proMap.put(mapKey, null) ;
		              break;
		             }
		             if (asstactTypeNumber[i] == null)
		               throw new Exception(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "1016_InitImportBase", ctx.getLocale()) + (i + 1) + ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "1017_InitImportBase", ctx.getLocale()));
		           }
		           if(!proMap.containsKey(mapKey)||proMap.get(mapKey)==null){
		           AssistantHGInfo assistantHGInfo = new AssistantHGInfo();
		           AsstActTypeCollection arrayAsstActTypeInfo = new AsstActTypeCollection();
		           int count = asstactTypeName.length;
		           for (int i = 0; i < count; ++i) {
		        	   constructAssistInfo(ctx, assistantHGInfo, asstactType[i], asstactTypeNumber[i], asstactTypeName[i], arrayAsstActTypeInfo);
							}
		 			IAssistantHG igCtrl = AssistantHGFactory.getLocalInstance(ctx);
		            AssistantHGInfo actualAssistantHGInfo = igCtrl.getAssistantHG(assistantHGInfo, FID, arrayAsstActTypeInfo);

		            IImpAssistantHG igImpCtrl = ImpAssistantHGFactory.getLocalInstance(ctx);
		            ImpAssistantHGInfo impAHGInfo = new ImpAssistantHGInfo();
		            impAHGInfo.setCount(actualAssistantHGInfo.getCount());
		            impAHGInfo.setNumberGroup(actualAssistantHGInfo.getNumberGroup());
		            impAHGInfo.setLongNameGroup(actualAssistantHGInfo.getLongNameGroup());
		            impAHGInfo.setDisplayNameGroup(actualAssistantHGInfo.getDisplayNameGroup());
		            impAHGInfo.setAsstAccount(actualAssistantHGInfo.getAsstAccount());
		            impAHGInfo.setMaterial(actualAssistantHGInfo.getMaterial());
		            impAHGInfo.setCustomer(actualAssistantHGInfo.getCustomer());
		            impAHGInfo.setProvider(actualAssistantHGInfo.getProvider());
		            impAHGInfo.setProject(actualAssistantHGInfo.getProject());
		            impAHGInfo.setBankAccount(actualAssistantHGInfo.getBankAccount());
		            impAHGInfo.setIndustry(actualAssistantHGInfo.getIndustry());
		            impAHGInfo.setRegion(actualAssistantHGInfo.getRegion());
		            impAHGInfo.setCashFlowItem(actualAssistantHGInfo.getCashFlowItem());
		            impAHGInfo.setProvince(actualAssistantHGInfo.getProvince());
		            impAHGInfo.setCity(actualAssistantHGInfo.getCity());
		            impAHGInfo.setAccountCussent(actualAssistantHGInfo.getAccountCussent());
		            impAHGInfo.setCostObject(actualAssistantHGInfo.getCostObject());
		            impAHGInfo.setAdminOrg(actualAssistantHGInfo.getAdminOrg());
		            impAHGInfo.setCompanyOrg(actualAssistantHGInfo.getCompanyOrg());
		            impAHGInfo.setSaleOrg(actualAssistantHGInfo.getSaleOrg());
		            impAHGInfo.setPurchaseOrg(actualAssistantHGInfo.getPurchaseOrg());
		            impAHGInfo.setStoreageOrg(actualAssistantHGInfo.getStoreageOrg());
		            impAHGInfo.setCostOrg(actualAssistantHGInfo.getCostOrg());
		            impAHGInfo.setProfitOrg(actualAssistantHGInfo.getProfitOrg());
		            impAHGInfo.setPerson(actualAssistantHGInfo.getPerson());
		            impAHGInfo.setCU(actualAssistantHGInfo.getCU());
		            impAHGInfo.setCostItem(actualAssistantHGInfo.getCostItem());
		            impAHGInfo.setNtType(actualAssistantHGInfo.getNtType());
		            impAHGInfo.setFpItem(actualAssistantHGInfo.getFpItem());
		            impAHGInfo.setInnerAccount(actualAssistantHGInfo.getInnerAccount());
		            impAHGInfo.setFeeType(actualAssistantHGInfo.getFeeType());
		            impAHGInfo.setCreator(actualAssistantHGInfo.getCreator());
		            impAHGInfo.setCreateTime(actualAssistantHGInfo.getCreateTime());
		            impAHGInfo.setLastUpdateUser(actualAssistantHGInfo.getLastUpdateUser());
		            impAHGInfo.setLastUpdateTime(actualAssistantHGInfo.getLastUpdateTime());
		            
		            IObjectPK pk = igImpCtrl.save(impAHGInfo);
		            impAHGInfo = igImpCtrl.getImpAssistantHGInfo(new ObjectUuidPK(pk.toString()));
		            
		            StringBuffer sf = new StringBuffer();
		            sf.append("delete from T_BD_AssistantHG where fid = '"+actualAssistantHGInfo.getId().toString()+"'");
		            DbUtil.execute(ctx, sf.toString());
		            
		            this.actualAssistantHGInfoID = impAHGInfo.getId().toString();
		            info.setAssistGrp(impAHGInfo);    
		            proMap.put(mapKey, impAHGInfo);
		         } 
		         }else{
		        	 info.setAssistGrp((ImpAssistantHGInfo) proMap.get(mapKey));
		         }
			}else{
				throw new TaskExternalException("“历史明细账引入表_辅助账类型”为空，核算项目不能导入！");
			}
			
		str = getDataString(hsData, "FEntrys_OriginalCurrency");
		if((str!=null)&&(str.trim().length())!=0){
//			Matcher isNum = pattern.matcher(str);
//			if(isNum.matches()){
				try{
					BigDecimal value = new BigDecimal(str);
					info.setOriginalCurrency(value);
					
				}catch(Exception e){
					logger.error(e);
					throw new TaskExternalException("“历史明细账引入表_原币金额”格式应为数字！");
				}
			
		}else{
			throw new TaskExternalException("“历史明细账引入表_原币金额”为空！");
		}
		str = getDataString(hsData, "FEntrys_CurrencyAmount");
		if((str!=null)&&(str.trim().length())!=0){
			//Matcher isNum = pattern.matcher(str);
			//if(isNum.matches()){
			try{
			BigDecimal value = new BigDecimal(str);
			info.setCurrencyAmount(value);
			}catch(Exception e){
				logger.error(e);
				throw new TaskExternalException("“历史明细账引入表_本位币金额”格式应为数字！");
			}
			
		}else{
			throw new TaskExternalException("“历史明细账引入表_本位币金额”为空！");
		}
		str = getDataString(hsData, "FEntrys_ReportingCurrency");
		if((str!=null)&&(str.trim().length())!=0){
//			Matcher isNum = pattern.matcher(str);
//			if(isNum.matches()){
			try{
				BigDecimal value = new BigDecimal(str);
				
					info.setReportingCurrency(value);
				
			}catch(Exception e){
				logger.error(e);
				throw new TaskExternalException("“历史明细账引入表_报告币金额”格式应为数字！");
			}
		}else{
			throw new TaskExternalException("“历史明细账引入表_报告币金额”为空！");
		}
		str = getDataString(hsData, "FEntrys_BorrowingDirection");
		
		if((str!=null)&&(str.trim().length())!=0){
			if(str.trim().equals("借")){
				info.setBorrowingDirection(EntryDC.DEBIT);
			}else if(str.trim().equals("贷")){
				info.setBorrowingDirection(EntryDC.CREDIT);
			}
				else{
				throw new TaskExternalException("“历史明细账引入表_借贷方向”只能为“借、贷”！");
			}
		}else{
			throw new TaskExternalException("“历史明细账引入表_借贷方向”为空！");
		}
		
		
		}catch (Exception e) {
			       logger.error(e);
			       throw new TaskExternalException(e.getMessage(), e);
			     }
		return info;
	}
	
	private String getDataString(Hashtable hsData, String fieldName) {
	     return DataImportTools.getDataString(hsData, fieldName);
	}
	private String appendErrMsg(String message, String strTemp) {
	if (message == null)
	       message = strTemp;
	else {
	       message = message + ";" + strTemp;
	     }
	     return message;
	}



	@Override
	protected String getType() {
		// TODO Auto-generated method stub
		return "BeginHiDetailBill";
	}

	@Override
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		
//		infoMap.put(infoKey, coreBaseInfo);
//		if(infoMap.size()>=5000){
//			Set<String> mapSet =  infoMap.keySet();
//			Iterator<String> itor =  mapSet.iterator();
//			for(int i = 1;i<mapSet.size();i++){
//				super.submit((CoreBaseInfo) infoMap.get(infoKey), ctx);
//			}
//		}else {
			super.submit(coreBaseInfo, ctx);
//		}
	}

	// @Override
	// public Map exportTransmit(IRowSet rs, Context ctx)
	// throws TaskExternalException {
	// // TODO Auto-generated method stub
	// return super.exportTransmit(rs, ctx);
	// }

	// @Override
	// public String getExportQueryInfo(Context ctx) {
	// // TODO Auto-generated method stub
	// return super.getExportQueryInfo(ctx);
	// }
	
}
