package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IBaseDataMatch extends ICoreBillBase
{
    public BaseDataMatchCollection getBaseDataMatchCollection() throws BOSException;
    public BaseDataMatchCollection getBaseDataMatchCollection(EntityViewInfo view) throws BOSException;
    public BaseDataMatchCollection getBaseDataMatchCollection(String oql) throws BOSException;
    public BaseDataMatchInfo getBaseDataMatchInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BaseDataMatchInfo getBaseDataMatchInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BaseDataMatchInfo getBaseDataMatchInfo(String oql) throws BOSException, EASBizException;
    public String getK3NumByEasNum(BaseDataEnum types, String num) throws BOSException, EASBizException;
}