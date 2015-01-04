package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ExcessSetEntry extends CoreBillEntryBase implements IExcessSetEntry
{
    public ExcessSetEntry()
    {
        super();
        registerInterface(IExcessSetEntry.class, this);
    }
    public ExcessSetEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IExcessSetEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2C024106");
    }
    private ExcessSetEntryController getController() throws BOSException
    {
        return (ExcessSetEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ExcessSetEntryInfo getExcessSetEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetEntryInfo(getContext(), pk);
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
    public ExcessSetEntryInfo getExcessSetEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetEntryInfo(getContext(), pk, selector);
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
    public ExcessSetEntryInfo getExcessSetEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetEntryInfo(getContext(), oql);
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
    public ExcessSetEntryCollection getExcessSetEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getExcessSetEntryCollection(getContext(), oql);
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
    public ExcessSetEntryCollection getExcessSetEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getExcessSetEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ExcessSetEntryCollection getExcessSetEntryCollection() throws BOSException
    {
        try {
            return getController().getExcessSetEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}