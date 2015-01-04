package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.cp.bc.K3DBConfigInfo;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.K3DBConfigCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface K3DBConfigController extends DataBaseController
{
    public K3DBConfigInfo getK3DBConfigInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public K3DBConfigInfo getK3DBConfigInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public K3DBConfigInfo getK3DBConfigInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public K3DBConfigCollection getK3DBConfigCollection(Context ctx) throws BOSException, RemoteException;
    public K3DBConfigCollection getK3DBConfigCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public K3DBConfigCollection getK3DBConfigCollection(Context ctx, String oql) throws BOSException, RemoteException;
}