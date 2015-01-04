package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class BizAccountBillAccountEntry extends CollectionAccountCoreBase implements IBizAccountBillAccountEntry
{
    public BizAccountBillAccountEntry()
    {
        super();
        registerInterface(IBizAccountBillAccountEntry.class, this);
    }
    public BizAccountBillAccountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBizAccountBillAccountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("88A4EDD5");
    }
    private BizAccountBillAccountEntryController getController() throws BOSException
    {
        return (BizAccountBillAccountEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BizAccountBillAccountEntryInfo getBizAccountBillAccountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillAccountEntryInfo(getContext(), pk);
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
    public BizAccountBillAccountEntryInfo getBizAccountBillAccountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillAccountEntryInfo(getContext(), pk, selector);
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
    public BizAccountBillAccountEntryInfo getBizAccountBillAccountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillAccountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BizAccountBillAccountEntryCollection getBizAccountBillAccountEntryCollection() throws BOSException
    {
        try {
            return getController().getBizAccountBillAccountEntryCollection(getContext());
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
    public BizAccountBillAccountEntryCollection getBizAccountBillAccountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBizAccountBillAccountEntryCollection(getContext(), view);
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
    public BizAccountBillAccountEntryCollection getBizAccountBillAccountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBizAccountBillAccountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}