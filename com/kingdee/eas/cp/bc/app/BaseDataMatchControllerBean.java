package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BaseDataEnum;
import com.kingdee.eas.cp.bc.BaseDataMatchEntryCollection;
import com.kingdee.eas.cp.bc.BaseDataMatchEntryFactory;
import com.kingdee.eas.framework.Result;

public class BaseDataMatchControllerBean extends AbstractBaseDataMatchControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.BaseDataMatchControllerBean");

    @Override
    protected Result _save(Context ctx, IObjectCollection colls)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._save(ctx, colls);
    }
    
    @Override
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._submit(ctx, model);
    }
    @Override
    protected IObjectPK _addnew(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._addnew(ctx, model);
    }
    
    protected String _getK3NumByEasNum(Context ctx, BaseDataEnum types,
    		String num) throws BOSException, EASBizException {
    	if(num==null || types==null){
    		return null;
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent.types",types.getValue()));
    	filter.getFilterItems().add(new FilterItemInfo("easNum",num));
    	BaseDataMatchEntryCollection coll  =  BaseDataMatchEntryFactory.getLocalInstance(ctx).getBaseDataMatchEntryCollection(view);
    	if(coll.size()>0){
    		return coll.get(0).getK3Num();
    	}
    	
    	return null;
    }

}