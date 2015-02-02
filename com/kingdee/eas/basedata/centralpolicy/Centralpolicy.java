package com.kingdee.eas.basedata.centralpolicy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.basedata.centralpolicy.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class Centralpolicy extends DataBase implements ICentralpolicy
{
    public Centralpolicy()
    {
        super();
        registerInterface(ICentralpolicy.class, this);
    }
    public Centralpolicy(Context ctx)
    {
        super(ctx);
        registerInterface(ICentralpolicy.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("452858C5");
    }
    private CentralpolicyController getController() throws BOSException
    {
        return (CentralpolicyController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public CentralpolicyInfo getCentralpolicyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCentralpolicyInfo(getContext(), pk);
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
    public CentralpolicyInfo getCentralpolicyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCentralpolicyInfo(getContext(), pk, selector);
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
    public CentralpolicyInfo getCentralpolicyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCentralpolicyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CentralpolicyCollection getCentralpolicyCollection() throws BOSException
    {
        try {
            return getController().getCentralpolicyCollection(getContext());
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
    public CentralpolicyCollection getCentralpolicyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCentralpolicyCollection(getContext(), view);
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
    public CentralpolicyCollection getCentralpolicyCollection(String oql) throws BOSException
    {
        try {
            return getController().getCentralpolicyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}