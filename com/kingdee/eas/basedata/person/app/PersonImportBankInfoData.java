package com.kingdee.eas.basedata.person.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.master.material.app.DataImportTools;
import com.kingdee.eas.basedata.person.BankInfoFactory;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.basedata.person.IBankInfo;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fm.be.BEBankCollection;
import com.kingdee.eas.fm.be.BEBankFactory;
import com.kingdee.eas.fm.be.BEBankInfo;
import com.kingdee.eas.fm.be.IBEBank;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.util.StringUtils;

public class PersonImportBankInfoData extends AbstractDataTransmission{


	@Override
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		try {
			return BankInfoFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("",e);
		}
	}
 
	public BankInfoInfo transmit(Hashtable hsData, Context ctx)throws TaskExternalException {
		
		PersonInfo personInfo = null;
		FilterInfo filter = null;
		BankInfoInfo info =new BankInfoInfo();
		IBankInfo iBankInfo =(IBankInfo) getController(ctx);
		
		String str="";
		
		//职员编码
		str=getDataString(hsData, "FNumber");
		if (StringUtils.isEmpty(str)) {
			throw new TaskExternalException("员工编码不能为空！");
		}else {
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",str));
			try {
				personInfo = getPersonInfoByNumber(ctx, str);
				
				if (personInfo == null){
					throw new TaskExternalException("不存在相应编码的员工信息！");
				}
			} catch (Exception e) {
				throw new TaskExternalException(e.getMessage());
			}
			
			info.setPerson(personInfo);
		}
		
		//开户银行编码
		str=getDataString(hsData, "FBankInfo_bankName");
		if (!StringUtils.isEmpty(str)) {
			try {
				BEBankInfo bankInfo = getDBBankInfoByName(ctx, str);
				if(bankInfo!=null){
					info.setBankName(bankInfo.getName());
				}else{
					info.setBankName(str);
				}
			} catch (BOSException e) {
				throw new TaskExternalException(e.getMessage());
			}
			
		}
			
		//银行账户
		str=getDataString(hsData, "FBankInfo_bandAcctNumber");
		if (StringUtils.isEmpty(str)) {
			throw new TaskExternalException("银行账号不能为空！");
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("bandAcctNumber",str));
		filter.getFilterItems().add(new FilterItemInfo("person.id",personInfo.getId().toString()));
		filter.setMaskString("(#0 and #1)");
		try {
			if(iBankInfo.exists(filter)){
				throw new TaskExternalException("员工编码："+personInfo.getNumber()+"的银行账号"+str+"重复！");
			}
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage());
		} 
		info.setBandAcctNumber(str);
		
		//银行地址
		str=getDataString(hsData, "FBankInfo_bandAddr");
		info.setBandAddr(str);
		
		//用途
		str=getDataString(hsData, "FBankInfo_usage");
		info.setUsage(str);
		
		//省
		str=getDataString(hsData, "FBankInfo_province");
		info.setProvince(str);
		
		//市
		str=getDataString(hsData, "FBankInfo_city");
		if (StringUtils.isEmpty(str)) {
			throw new TaskExternalException("市不能为空！");
		}
		info.setCity(str);
		
		return info;
	}
	
	//导入按钮事件
	public void submit(BankInfoInfo bankInfo, Context ctx)
			throws TaskExternalException {
		try {
			getController(ctx).addnew(bankInfo);
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
	
	private String getDataString(Hashtable hsData,String fieldName){
		return DataImportTools.getDataString(hsData, fieldName);
	}
	
	PersonInfo getPersonInfoByNumber(Context ctx, String strNumber)throws BOSException{
	     IPerson iPerson = PersonFactory.getLocalInstance(ctx);
	     PersonCollection pInfos = iPerson.getPersonCollection("where number = '" + strNumber.trim() + "'");
	     
	     if (pInfos.size() > 0)
	     {
	       return pInfos.get(0);
	     }
	     return null;
	}
	
	BEBankInfo getDBBankInfoByName(Context ctx, String strName)throws BOSException{
	     IBEBank iBEBank = BEBankFactory.getLocalInstance(ctx);
	     BEBankCollection beBanks = iBEBank.getBEBankCollection("where name = '" + strName.trim() + "'");
	     
	     if (beBanks.size() > 0)
	     {
	       return beBanks.get(0);
	     }
	     return null;
	}
	
}
