package com.kingdee.eas.basedata.assistant;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.basedata.assistant.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ManagerList extends CoreBillEntryBase implements IManagerList
{
    public ManagerList()
    {
        super();
        registerInterface(IManagerList.class, this);
    }
    public ManagerList(Context ctx)
    {
        super(ctx);
        registerInterface(IManagerList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C9EDDE40");
    }
    private ManagerListController getController() throws BOSException
    {
        return (ManagerListController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ManagerListInfo getManagerListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getManagerListInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ManagerListInfo getManagerListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getManagerListInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ManagerListInfo getManagerListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getManagerListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ManagerListCollection getManagerListCollection() throws BOSException
    {
        try {
            return getController().getManagerListCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public ManagerListCollection getManagerListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getManagerListCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ManagerListCollection getManagerListCollection(String oql) throws BOSException
    {
        try {
            return getController().getManagerListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}