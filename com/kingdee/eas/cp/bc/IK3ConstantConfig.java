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

public interface IK3ConstantConfig extends IDataBase
{
    public K3ConstantConfigInfo getK3ConstantConfigInfo(IObjectPK pk) throws BOSException, EASBizException;
    public K3ConstantConfigInfo getK3ConstantConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public K3ConstantConfigInfo getK3ConstantConfigInfo(String oql) throws BOSException, EASBizException;
    public K3ConstantConfigCollection getK3ConstantConfigCollection() throws BOSException;
    public K3ConstantConfigCollection getK3ConstantConfigCollection(EntityViewInfo view) throws BOSException;
    public K3ConstantConfigCollection getK3ConstantConfigCollection(String oql) throws BOSException;
}