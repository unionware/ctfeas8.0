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

public class K3ConstantConfig extends DataBase implements IK3ConstantConfig
{
    public K3ConstantConfig()
    {
        super();
        registerInterface(IK3ConstantConfig.class, this);
    }
    public K3ConstantConfig(Context ctx)
    {
        super(ctx);
        registerInterface(IK3ConstantConfig.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F0A59BAC");
    }
    private K3ConstantConfigController getController() throws BOSException
    {
        return (K3ConstantConfigController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public K3ConstantConfigInfo getK3ConstantConfigInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getK3ConstantConfigInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public K3ConstantConfigInfo getK3ConstantConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getK3ConstantConfigInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public K3ConstantConfigInfo getK3ConstantConfigInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getK3ConstantConfigInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public K3ConstantConfigCollection getK3ConstantConfigCollection() throws BOSException
    {
        try {
            return getController().getK3ConstantConfigCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public K3ConstantConfigCollection getK3ConstantConfigCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getK3ConstantConfigCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public K3ConstantConfigCollection getK3ConstantConfigCollection(String oql) throws BOSException
    {
        try {
            return getController().getK3ConstantConfigCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}