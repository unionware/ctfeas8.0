package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;

public class ExpenseAccountFacadeControllerBeanEx  extends ExpenseAccountFacadeControllerBean{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 6304629066202686395L;
	private static Logger logger = Logger.getLogger("com.kingdee.eas.cp.bc.app.ExpenseAccountFacadeControllerBeanEx");

	@Override
	protected void _setPassStateEvent(Context ctx, BOSUuid billId,
			BOSUuid auditorId) throws BOSException, EASBizException {
		super._setPassStateEvent(ctx, billId, auditorId);
		if("C57003BC".equals(billId.getType().toString())){
			CreateK3PayNoticeBillFacadeFactory.getLocalInstance(ctx).createByTraveAccountBill(billId);
		}else if("4A44F49F".equals(billId.getType().toString())){
			CreateK3PayNoticeBillFacadeFactory.getLocalInstance(ctx).createByBizAccountBill(billId);
		}else{
			logger.info(billId.toString()+"$$$$$$$$$$$$$$$$$$$$$");
		}
		
	}
	
}
