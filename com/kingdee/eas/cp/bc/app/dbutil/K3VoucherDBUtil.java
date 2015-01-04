package com.kingdee.eas.cp.bc.app.dbutil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.util.app.DbUtil;

public class K3VoucherDBUtil {
	
	
	public static void setDbConfigChanged(){
		ConnectionPoolUtil.setDbConfigChanged();
	}
	
	
	public static void insertVoucher(Context ctx,List<Map<String,Object>> rowList,List<Map<String,Object>> headrowList,BOSObjectType  bosType) throws  SQLException, BOSException{
		ConnectionPoolUtil.initConnectonPoll(ctx);
		Connection conn =  SimpleConnetionPool.getConnection();
		
		PreparedStatement stat =null;
		try {
			String sql = getInsertRowSqlStr();
			stat = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			executeData(rowList, stat, sql);
			stat.clearBatch();
			
			ConnectionPoolUtil.closeConn(stat);
			
			sql = getInsertHeadSqlStr();
			stat = conn.prepareStatement(sql);
			
			executeData(headrowList, stat, sql);
			Set<String> billSet = getBillNum(rowList);
			String updateEasSql = getUpdateLocalStatusSqlStr(getEntityMainTable(ctx, bosType), "CFk3VoucherStatus", billSet);
			DbUtil.execute(ctx, updateEasSql);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(SQLException e2){
				e.printStackTrace();
			}
			throw e;
			
		} catch (BOSException e) {
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(SQLException e2){
				e.printStackTrace();
			}
			throw e;
		}finally{  
			ConnectionPoolUtil.closeConn(conn, stat);
        }  
	}
	
	 public static  String getEntityMainTable(Context ctx, BOSObjectType  bosType){
	    	IMetaDataLoader imeataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			IMetaDataPK pk = imeataLoader.getEntityObjectPK(bosType);
			EntityObjectInfo entityObjectInfo = imeataLoader.getEntity(pk);
			DataTableInfo tableInfo = entityObjectInfo.getTable();
			String tableName = tableInfo.getName();
			return tableName;
	    }
	 
	
	private static Set<String> getBillNum(List<Map<String,Object>> rowList){
		LinkedHashMap<String,Object> vals = null;
		Set<String> set = new HashSet<String>();
		for(int i=0;i<rowList.size();i++){
			vals = (LinkedHashMap<String,Object>)rowList.get(i);
			String billNum = (String)vals.get("FTMQNo");
			set.add(billNum);
		}
		
		return set;
	}
	
	
	
	private static void executeData(List rowList,PreparedStatement stat,String sql) throws SQLException{
		if(sql==null){
			return ;
		}
		int paramSize = stringNumbers(sql, "?", 0);
		String key = null;
		if(rowList==null || rowList.size()==0){
			return ;
		}
		for(int i=0;i<rowList.size();i++){
			LinkedHashMap<String,Object> vals = (LinkedHashMap<String,Object>)rowList.get(i);
			if(vals.size()!=paramSize){
				throw new SQLException("参数数量不匹配 sql"+sql+"   要求参数长度"+paramSize+"  参数长度"+vals.size());
			}
			Iterator<String>  it = vals.keySet().iterator();
			int j=1;
			while(it.hasNext()){
				key = it.next();
				insertVal(stat, vals.get(key), j);
				j++;
			}
			stat.addBatch();
		}
		stat.executeBatch();
	}
	
	private static void executeHead(List rowList,PreparedStatement stat,String sql){
		
	}
	
	
	
	
	
	private static void insertVal(PreparedStatement stat,Object val,int index) throws SQLException{
		if(val==null){
			stat.setString(index, (String)val);
		}else if(val instanceof String){
			stat.setString(index, (String)val);
		}else if(val instanceof Date){
			stat.setDate(index, new java.sql.Date(  ((Date)val).getTime() ) );
		}else if(val instanceof Integer){
			stat.setInt(index, (Integer)val);
		}else if(val instanceof BigDecimal){
			stat.setBigDecimal(index, (BigDecimal)val);
		}else if(val instanceof Boolean){
			stat.setBoolean(index, (Boolean)val);
		}
	} 
	
	private static int stringNumbers(String str,String partt,int count)  
    {  
        if (str.indexOf(partt)==-1)  
        {  
            return count;  
        }  
        else if(str.indexOf(partt) != -1)  
        {  
        	count++;  
        	return  stringNumbers(str.substring(str.indexOf(partt)+1),partt,count);  
        }  
        return 0;  
    }  
	
	private static String getUpdateLocalStatusSqlStr(String table,String colName,Set<String> numSet){
		StringBuffer sql = new StringBuffer();
		sql.append(" update    ").append(table).append("  set "+colName+"=1 \n");
		sql.append("where Fnumber in("+getBillNumStr(numSet)+")");
		return sql.toString();
	}
	
	
	public static String getBillNumStr(Set<String> numSet){
		if(numSet==null || numSet.size()==0){
			return "-1";
		}
		Iterator<String> it = numSet.iterator(); 
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			sb.append("'").append(it.next()).append("',");
		}
		sb.setLength(sb.length()-1);
		
		return sb.toString();
	}
	
	
	private static String getInsertRowSqlStr(){
		StringBuffer sql = new StringBuffer();
		//,FBigArea,Farea,FSmallArea
		//,?,?,?
		sql.append(" insert into t_Syn_VoucherDr(FTypeID,Findex,FTMQNo,FCompanyNum,ftransDate,fattchment,FGroupID,fAcctNum,FSupplyNum,FSupplyName");
		sql.append(",FEmpNum,FEmpName,FSpecialNum,FDeptNum,FCyNum,FAmt,FTaxAmt,fexp,FIsSyn,FVouNum) \n ");
		sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		return sql.toString();
	}
	
	private static String getInsertHeadSqlStr(){
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into t_Syn_VoucherCr(FTMQNo,FAcctNum,FAmt,fexp,FDeptNum,FEmpNum,FSupplyNum,FSpecialNum) \n ");
		sql.append("values(?,?,?,?,?,?,?,?)");
		return sql.toString();
	}
	
	
}
