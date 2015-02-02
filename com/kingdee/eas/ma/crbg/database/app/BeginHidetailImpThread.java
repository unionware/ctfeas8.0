package com.kingdee.eas.ma.crbg.database.app;

import java.sql.SQLException;

import com.kingdee.bos.Context;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;

public class BeginHidetailImpThread extends Thread {
	private Context ctx;
	private int beginkey;
	private int endkey;
	 public BeginHidetailImpThread(Context ctx,int beginkey,int endkey){
		 this.ctx = ctx;
		 this.beginkey = beginkey;
		 this.endkey = endkey;
	 }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BeginHiDetailImportDataProsess beginimort = new BeginHiDetailImportDataProsess();
		try {
			beginimort.transmit(ctx,beginkey,endkey);
		} catch (TaskExternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.run();
	}
	
}
