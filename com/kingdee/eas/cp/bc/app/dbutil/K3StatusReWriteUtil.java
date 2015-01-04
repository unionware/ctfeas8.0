package com.kingdee.eas.cp.bc.app.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.event.util.DBUtils;

public class K3StatusReWriteUtil {
	private static final String k3SUBMIT_STATUS="TRANSACTION_SUCCESS";  //K3 通知单生成成功状态
	private static final int SYS_K3_VOUCHER_UNVOUCHER =0;
	private static final int SYS_K3_VOUCHER_UNSYN =1;
	private static final int SYS_K3_VOUCHER_SYNSUCC =2;
	private static final int SYS_K3_VOUCHER_SYNVOERR =3;
	/**
	 * 获取通知单生成成功单据
	 * @param ctx
	 * @param billNumSet  需查询的单据
	 * @return	
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Set<String> getNoticeBillStatus(Context ctx,Set<String> billNumSet) throws BOSException, SQLException{
		if(billNumSet==null || billNumSet.size()==0){
			return null;
		}
		ConnectionPoolUtil.initConnectonPoll(ctx);
		Connection conn =  SimpleConnetionPool.getConnection();
		Statement stat =null;
		String sql = getQueryNoticeStatusRowSqlStr(billNumSet);
		Set<String> revSet = new HashSet<String>();
		try {
			stat = conn.createStatement();
			ResultSet rowSet = stat.executeQuery(sql);
			while(rowSet.next()){
				revSet.add(rowSet.getString("FNumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{  
			ConnectionPoolUtil.closeConn(conn, stat);
        }  
		
		return revSet;
	}
	
	/**
	 * 更新费用类单据的状态
	 * @param ctx
	 * @param tableName
	 * @param numSet
	 * @throws SQLException
	 */
	public static void updateNoticeTableStatus(Context ctx,String tableName,String tableColName,Set<String> numSet) throws SQLException{
		if(numSet==null || numSet.size()==0){
			return ;
		}
		Connection conn = DBUtils.getConnection(ctx);
		PreparedStatement stat =null;
		try {
			String sql = getUpdateNoticeStatusSqlStr(tableName,tableColName);
			stat = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			stat.clearBatch();
			Iterator<String> it = numSet.iterator();
			String num = null;
			while(it.hasNext()){
				num = it.next();
				stat.setString(1, num);
				stat.addBatch();
			}
			stat.executeBatch();
			conn.commit();
		} catch (SQLException e) {
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
	
	
	
	
	/**
	 * 获取凭证状态信息
	 * @param ctx
	 * @param billNumSet
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List<K3VoucherEasNumInfo> getVoucherInfo(Context ctx,Set<String> billNumSet) throws BOSException, SQLException{
		if(billNumSet==null || billNumSet.size()==0){
			return null;
		}
		ConnectionPoolUtil.initConnectonPoll(ctx);
		Connection conn =  SimpleConnetionPool.getConnection();
		Statement stat =null;
		String sql = getCheckVoucherNumSqlStr(billNumSet);
		//Set<String> revSet = new HashSet<String>();
		List<K3VoucherEasNumInfo> reWriteList = new ArrayList<K3VoucherEasNumInfo>();
		try {
			stat = conn.createStatement();
			ResultSet rowSet = stat.executeQuery(sql);
			
			while(rowSet.next()){
				K3VoucherEasNumInfo info = new K3VoucherEasNumInfo();
				info.setEasNum(rowSet.getString("FTMQNo"));
				info.setSynStatus(rowSet.getInt("FIsSyn"));
				info.setVoucherNum(rowSet.getString("FVouNum"));

				reWriteList.add(info);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{  
			ConnectionPoolUtil.closeConn(conn, stat);
        }  
		
		return reWriteList;
	}
	
	
	/**
	 * 更新EAS单据的凭证信息
	 * @param ctx
	 * @param tableName
	 * @param voucherColName
	 * @param synColName
	 * @param reWriteList
	 * @throws SQLException
	 */
	public static void updateEasBillByVoucherInfo(Context ctx,String tableName,String voucherColName,String synColName,
			List<K3VoucherEasNumInfo> reWriteList) throws SQLException{
		if(reWriteList==null || reWriteList.size()==0){
			return ;
		}
		Connection conn = DBUtils.getConnection(ctx);
		PreparedStatement stat =null;
		try {
			String sql = getUpdateBillByVouherSqlStr(tableName,voucherColName,synColName);
			stat = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			K3VoucherEasNumInfo info =null;
			for(int i=0;i<reWriteList.size();i++){
				info = reWriteList.get(i);
				stat.setString(1, info.getVoucherNum());
				stat.setInt(2, info.getSynStatus());
				stat.setString(3, info.getEasNum());
				stat.addBatch();
			}
			 
			stat.executeBatch();
			conn.commit();
		} catch (SQLException e) {
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
	
	/**
	 * 更新EAS付款状态SQL
	 * @param tableName
	 * @param tableColName
	 * @return
	 */
	private static String getUpdateNoticeStatusSqlStr(String tableName,String tableColName){
		StringBuffer sql = new StringBuffer();
		sql.append("update  ").append(tableName).append("  set "+tableColName+"=1 where FNumber=?");
		return sql.toString();
	}
	
	/**
	 * 更新EAS凭证信息SQL
	 * @param tableName
	 * @param voucherColName
	 * @param synColName
	 * @return
	 */
	private static String getUpdateBillByVouherSqlStr(String tableName,String voucherColName,String synColName){
		StringBuffer sql = new StringBuffer();
		sql.append("update  ").append(tableName).append("  set "+voucherColName+"=?,"+synColName+"=?  where FNumber=? ");
		return sql.toString();
	}
	
	/**
	 * 查K3通知单SQL
	 * @param numSet
	 * @return
	 */
	private static String getQueryNoticeStatusRowSqlStr(Set<String> numSet){
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.FNumber  from t_scNotifyBill t1 \n");
		sql.append(" inner join t_BK_Payment t2 on t1.FPaysettID=t2.FSourceID \n");
		sql.append(" where t2.FSubmitState ='"+k3SUBMIT_STATUS+"' and t1.FNumber in ("+getBillNumStr(numSet)+")\n");
		return sql.toString();
	}
	
	/**
	 * 查K3凭证状态
	 * @param numSet
	 * @return
	 */
	private static String getCheckVoucherNumSqlStr(Set<String> numSet){
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT t1.FTMQNo,case when t1.FIsSyn=1 then "+SYS_K3_VOUCHER_SYNSUCC+" else "+SYS_K3_VOUCHER_SYNVOERR+" ");
		sql.append("  end FIsSyn,FVouNum  from t_Syn_VoucherDr t1   \n");
		sql.append("  where 1=1   and t1.FTMQNo in ("+getBillNumStr(numSet)+") \n");
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
	
	public static Set<String> getBillNum(AbstractObjectCollection coll){
	    	Set<String> revSet = new HashSet<String>();
	    	for(int i=0;i<coll.size();i++){
	    		revSet.add(coll.getObject(i).getString("number"));
	    	}
	    	return revSet;
	    }
}
