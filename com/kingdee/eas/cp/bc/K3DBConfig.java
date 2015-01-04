package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class K3DBConfig extends DataBase implements IK3DBConfig
{
    public K3DBConfig()
    {
        super();
        registerInterface(IK3DBConfig.class, this);
    }
    public K3DBConfig(Context ctx)
    {
        super(ctx);
        registerInterface(IK3DBConfig.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FC100566");
    }
    private K3DBConfigController getController() throws BOSException
    {
        return (K3DBConfigController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public K3DBConfigInfo getK3DBConfigInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getK3DBConfigInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public K3DBConfigInfo getK3DBConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getK3DBConfigInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public K3DBConfigInfo getK3DBConfigInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getK3DBConfigInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public K3DBConfigCollection getK3DBConfigCollection() throws BOSException
    {
        try {
            return getController().getK3DBConfigCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public K3DBConfigCollection getK3DBConfigCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getK3DBConfigCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public K3DBConfigCollection getK3DBConfigCollection(String oql) throws BOSException
    {
        try {
            return getController().getK3DBConfigCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}