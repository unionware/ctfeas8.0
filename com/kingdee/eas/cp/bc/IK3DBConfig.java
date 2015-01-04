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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IK3DBConfig extends IDataBase
{
    public K3DBConfigInfo getK3DBConfigInfo(IObjectPK pk) throws BOSException, EASBizException;
    public K3DBConfigInfo getK3DBConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public K3DBConfigInfo getK3DBConfigInfo(String oql) throws BOSException, EASBizException;
    public K3DBConfigCollection getK3DBConfigCollection() throws BOSException;
    public K3DBConfigCollection getK3DBConfigCollection(EntityViewInfo view) throws BOSException;
    public K3DBConfigCollection getK3DBConfigCollection(String oql) throws BOSException;
}