package com.kingdee.eas.ma.crbg.database.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceStateManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo;

public class BeginHiDetailBillControllerBean extends
		AbstractBeginHiDetailBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.ma.crbg.database.app.BeginHiDetailBillControllerBean");

	@Override
	public void getThread(Context ctx,int beginkey,int endkey) throws BOSException {
		// TODO Auto-generated method stub
		BeginHidetailImpThread thread = new BeginHidetailImpThread(ctx,beginkey,endkey);
		thread.run();
		super.getThread(ctx,beginkey,endkey);
	}

	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BeginHiDetailBillInfo coreBaseInfo = (BeginHiDetailBillInfo) model;
		ServiceStateManager.getInstance().enableNextCallServices();
		if ((coreBaseInfo.getId() != null)
				&& (_exists(ctx, new ObjectUuidPK(coreBaseInfo.getId())))) {
			ObjectUuidPK id = new ObjectUuidPK(coreBaseInfo.getId());
			super.update(ctx, id, coreBaseInfo);
			return id;
		}
		 IObjectPK retValue = super.addnew(ctx, coreBaseInfo);
		 return retValue;
	}
}