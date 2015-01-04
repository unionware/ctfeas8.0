package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.app.dbutil.K3VoucherDBUtil;

public class K3DBConfigControllerBean extends AbstractK3DBConfigControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.K3DBConfigControllerBean");
    
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK  pk = super._save(ctx, model);
		K3VoucherDBUtil.setDbConfigChanged();
		return pk;
	}
    
	
    
}