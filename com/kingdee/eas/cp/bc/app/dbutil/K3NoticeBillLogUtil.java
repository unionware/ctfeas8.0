package com.kingdee.eas.cp.bc.app.dbutil;

import java.sql.SQLException;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ZDFDateHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class K3NoticeBillLogUtil {
	public static void insertSuccLog(Context ctx,String id ,String billNum) throws BOSException{
		DbUtil.execute(ctx, "insert into t_succNoticeBillLog values('"+id+"','"+billNum+"',to_date('"+ZDFDateHelper.FORMAT_TIME.format(new Date())+"'))");
	}
	
	public static void insertErrorLog(Context ctx,String id ,String billNum,String desc) throws BOSException{
		if(desc!=null && desc.length()>255){
			desc = desc.substring(0,254);
		}
		DbUtil.execute(ctx, "insert into t_errorNoticeBillLog values('"+id+"','"+billNum+"',to_date('"+ZDFDateHelper.FORMAT_TIME.format(new Date())+"'),'"+desc+"')");
	}
	
	public static boolean hasSuccCreateK3NoticeBill(Context ctx,String id) throws BOSException, SQLException{
		IRowSet  set = DbUtil.executeQuery(ctx, "select count(1) nums from  t_succNoticeBillLog where FID ='"+id+"'");
		int count =-1;
		while(set.next()){
			count = set.getInt("nums");
			break;
		}
		if(count>0){
			return true;
		}
		return false;
	}
	
	public static void checkSuccCreateK3NoticeBill(Context ctx,String id ) throws BOSException, SQLException, EASBizException{
		boolean hasSucc = hasSuccCreateK3NoticeBill(ctx, id);
		if(hasSucc){
			throw new EASBizException(new NumericExceptionSubItem("fffff","单据已生成K3付款通知单"));
		}
	}
	
}
