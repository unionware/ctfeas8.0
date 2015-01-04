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

public class TravelAccountBillAccountEntry extends CollectionAccountCoreBase implements ITravelAccountBillAccountEntry
{
    public TravelAccountBillAccountEntry()
    {
        super();
        registerInterface(ITravelAccountBillAccountEntry.class, this);
    }
    public TravelAccountBillAccountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITravelAccountBillAccountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0BA02118");
    }
    private TravelAccountBillAccountEntryController getController() throws BOSException
    {
        return (TravelAccountBillAccountEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TravelAccountBillAccountEntryInfo getTravelAccountBillAccountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillAccountEntryInfo(getContext(), pk);
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
    public TravelAccountBillAccountEntryInfo getTravelAccountBillAccountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillAccountEntryInfo(getContext(), pk, selector);
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
    public TravelAccountBillAccountEntryInfo getTravelAccountBillAccountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillAccountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TravelAccountBillAccountEntryCollection getTravelAccountBillAccountEntryCollection() throws BOSException
    {
        try {
            return getController().getTravelAccountBillAccountEntryCollection(getContext());
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
    public TravelAccountBillAccountEntryCollection getTravelAccountBillAccountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTravelAccountBillAccountEntryCollection(getContext(), view);
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
    public TravelAccountBillAccountEntryCollection getTravelAccountBillAccountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTravelAccountBillAccountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}