package com.kingdee.eas.cp.bc.app.dbutil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.cp.bc.K3DBConfigFactory;
import com.kingdee.eas.cp.bc.K3DBConfigInfo;

class ConnectionPoolUtil {
	private static boolean inited=false;
	private static boolean isCHanged =false;
	
	public static void setDbConfigChanged(){
		isCHanged = true;
	}
	
	public static void initConnectonPoll(Context ctx) throws BOSException{
		if(!inited){
			System.out.print("");
			K3DBConfigInfo dbInfo = K3DBConfigFactory.getLocalInstance(ctx).getK3DBConfigCollection().get(0);
			String msurl = "jdbc:sqlserver://"+dbInfo.getIp() + ":" + dbInfo.getPort()+";databaseName="+dbInfo.getDbname(); //123为数据库名
			SimpleConnetionPool.setUrl(msurl);
			SimpleConnetionPool.setUser(dbInfo.getUsername());
			SimpleConnetionPool.setPassword(dbInfo.getPwd());
			inited = true;
			isCHanged =false;
		}else{
			if(isCHanged){
				if(SimpleConnetionPool.getUsedConnectionCount()>0){
					throw new BOSException("当前存在正在使用的连接,无法重新初始化数据库连接池");
				}else{
					SimpleConnetionPool.close();
					if(SimpleConnetionPool.getConnectionCount()>0){
						throw new BOSException("当前数据库连接池存在链接,无法重新初始化数据库连接池");
					}
					inited = false;
					initConnectonPoll(ctx);
				}
			}
		}
	}
	
	public static void closeConn(Statement stat){
		closeConn(null,stat);
	}
	
	public static void closeConn(Connection conn){
		closeConn(conn,null);
	}
	
	public static void closeConn(Connection conn,Statement stat){
		try{
			if(stat!=null){
				stat.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			if(conn!=null){
				conn.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
