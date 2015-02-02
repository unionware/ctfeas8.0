package com.kingdee.eas.cp.bc.app.dbutil;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.cp.bc.K3ConstantConfigCollection;
import com.kingdee.eas.cp.bc.K3ConstantConfigFactory;
import com.kingdee.eas.cp.bc.K3ConstantConfigInfo;

public class K3WebAccConfigInfos {

	public static final String NUM_K3_WEBSERVICE="K3_WEBSERVICE";
	public static final String NUM_CHARGE_NAME="P_CHARGE_NAME";
	public static final String NUM_BIZACCOUNT_PAYMODE="ACCOUNT_PAYMODE";
	
	public static final String NUM_ENABLE_CALL_K3WEBSER="ENABLE_K3_WEBSER";
	public static final String DEBIT_ACOUNT_PERSONAL="DEBIT_ACOUNT_PERSONAL";
	public static final String DEBIT_ACOUNT_SUPPLIER="DEBIT_ACOUNT_SUPPLIER";
	
	public static final String PROXY_IS_ENABLED="PROXY_IS_ENABLED";
	public static final String PROXY_HOST="PROXY_HOST";
	public static final String PROXY_PORT="PROXY_PORT";
	
	private static final String IS_INITED="ISINITED";
	
	private static boolean isInited =false;
	private static  String NUM_K3_WEBSERVICE_VAL="";
	private static  String NUM_CHARGE_NAME_VAL="";
	private static  String NUM_BIZACCOUNT_PAYMODE_VAL="";
	private static  String NUM_ENABLE_CALL_K3WEBSER_VAL="";
	private static  String DEBIT_ACOUNT_PERSONAL_VAL="1221.03";
	private static  String DEBIT_ACOUNT_SUPPLIER_VAL="1221.77.05";
	private static Map<String,Map<String,String>> aisValMap = new HashMap<String, Map<String,String>>();
	public static String getK3WebSer(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, NUM_K3_WEBSERVICE);
		//return getByAIS(ctx.getAIS()).get(NUM_K3_WEBSERVICE);
	}

	
	public static String getAcountPayModeName(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, NUM_BIZACCOUNT_PAYMODE);
		//return getByAIS(ctx.getAIS()).get(NUM_BIZACCOUNT_PAYMODE);
	}
	
	public static String getChargeName(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, NUM_CHARGE_NAME);
		//return getByAIS(ctx.getAIS()).get(NUM_CHARGE_NAME);	
	}
	
	private static String getValByNum(Context ctx,String number) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number",number));
		K3ConstantConfigCollection coll =  K3ConstantConfigFactory.getLocalInstance(ctx).getK3ConstantConfigCollection(view);
		if(coll!=null && coll.size()>0){
			return coll.get(0).getVal();
		}else{
			return null;
		}
	}
	public static boolean IsEnableCallK3WebSer(Context ctx) throws BOSException{
		init(ctx);
		String  NUM_ENABLE_CALL_K3WEBSER_VAL = getValByNum(ctx, NUM_ENABLE_CALL_K3WEBSER);
		//String  NUM_ENABLE_CALL_K3WEBSER_VAL =  getByAIS(ctx.getAIS()).get(NUM_ENABLE_CALL_K3WEBSER);	
		if("1".equals(NUM_ENABLE_CALL_K3WEBSER_VAL) || "true".equalsIgnoreCase(NUM_ENABLE_CALL_K3WEBSER_VAL)|| "ÊÇ".equals(NUM_ENABLE_CALL_K3WEBSER_VAL)){
			return true;
		}
		return false;
	}
	
	public static String getDebitPersonAccount(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, DEBIT_ACOUNT_PERSONAL);
		//return getByAIS(ctx.getAIS()).get(DEBIT_ACOUNT_PERSONAL);	
	}
	
	public static String getDebitSupplierAccount(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, DEBIT_ACOUNT_SUPPLIER);
		//return getByAIS(ctx.getAIS()).get(DEBIT_ACOUNT_SUPPLIER);	
	}
	
	
	public static boolean isEnableProxy(Context ctx) throws BOSException{
		init(ctx);
		String  isEnableProxyStr = getValByNum(ctx, PROXY_IS_ENABLED);
		if("1".equals(isEnableProxyStr) || "true".equalsIgnoreCase(isEnableProxyStr)|| "ÊÇ".equals(isEnableProxyStr)){
			return true;
		}
		return false;
		//return getByAIS(ctx.getAIS()).get(PROXY_IS_ENABLED);	
	}
	
	
	public static String getProxyHost(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, PROXY_HOST);
		//return getByAIS(ctx.getAIS()).get(NUM_CHARGE_NAME);	
	}
	public static String getProxyPort(Context ctx) throws BOSException{
		init(ctx);
		return getValByNum(ctx, PROXY_PORT);
		//return getByAIS(ctx.getAIS()).get(NUM_CHARGE_NAME);	
	}
	
	public static void inited(Context ctx) throws BOSException{
			String ais = ctx.getAIS();
			K3ConstantConfigCollection coll =  K3ConstantConfigFactory.getLocalInstance(ctx).getK3ConstantConfigCollection();
			K3ConstantConfigInfo info = null;
			for(int i=0;i<coll.size();i++){
				info = coll.get(i);
				if(info.getNumber()!=null){
				//if(NUM_K3_WEBSERVICE.equals(info.getNumber())){
					setVal(ais, info.getNumber(), info.getVal());
					/*NUM_K3_WEBSERVICE_VAL = info.getVal();
				}else if(NUM_CHARGE_NAME.equals(info.getNumber())){
					NUM_CHARGE_NAME_VAL = info.getVal();
				}else if(NUM_BIZACCOUNT_PAYMODE.equals(info.getNumber())){
					NUM_BIZACCOUNT_PAYMODE_VAL = info.getVal();
				}else if(NUM_ENABLE_CALL_K3WEBSER.equals(info.getNumber())){
					NUM_ENABLE_CALL_K3WEBSER_VAL = info.getVal();*/
				}
			}
			setVal(ais, IS_INITED, "1");
			//isInited =true;
		
	}
	
	
	private static Map<String,String> getByAIS(String ais){
		return aisValMap.get(ais);
	}
	
	private static boolean  isInited(Context ctx){
		Map<String,String> map = getByAIS(ctx.getAIS());
		if(map==null){
			return false;
		}
		String tmpInited = map.get(IS_INITED);
		if(tmpInited!=null && "1".equals(tmpInited))
		{
				return true;
		}
		return false; 
	}
	
	private static void init(Context ctx) throws BOSException{
		if(!isInited(ctx)){
			inited(ctx);
		}
	}
	
	private static void setVal(String ais,String key ,String value){
		Map<String,String> map = aisValMap.get(ais);
		if(map==null){
			map = new HashMap<String,String>();
		}
		map.put(key, value);
		aisValMap.put(ais, map);
		
	}
	
	
	
}
