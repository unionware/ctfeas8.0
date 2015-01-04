package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;
import com.kingdee.bos.*;
import com.kingdee.eas.basedata.org.IPosition;
import com.kingdee.eas.basedata.org.PositionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.IsRefException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;

public class PositionLevelControllerBean extends AbstractPositionLevelControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.PositionLevelControllerBean");
    
    @Override
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	IPosition ip = PositionFactory.getLocalInstance(ctx);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("positionLevel",pk.toString()));
    	if(ip.exists(filter)){
    		throw new IsRefException(IsRefException.EXISTREF);
    	}
    	super._delete(ctx, pk);
    }
}