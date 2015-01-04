package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;
import org.tempuri.Service1Locator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.K3ConstantConfigInfo;
import com.kingdee.eas.cp.bc.app.dbutil.K3DefaultConstant;
import com.kingdee.eas.cp.bc.app.dbutil.K3WebAccConfigInfos;

public class K3ConstantConfigControllerBean extends AbstractK3ConstantConfigControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.K3ConstantConfigControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	IObjectPK pk =  super._save(ctx, model);
    	K3WebAccConfigInfos.inited(ctx);
    	return pk;
    	
    }
    
    
}