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

public class OtherExpenseBillEntry extends OtherExpenseEntryCoreBase implements IOtherExpenseBillEntry
{
    public OtherExpenseBillEntry()
    {
        super();
        registerInterface(IOtherExpenseBillEntry.class, this);
    }
    public OtherExpenseBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherExpenseBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F5C4E8C5");
    }
    private OtherExpenseBillEntryController getController() throws BOSException
    {
        return (OtherExpenseBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OtherExpenseBillEntryInfo getOtherExpenseBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherExpenseBillEntryInfo(getContext(), pk);
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
    public OtherExpenseBillEntryInfo getOtherExpenseBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherExpenseBillEntryInfo(getContext(), pk, selector);
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
    public OtherExpenseBillEntryInfo getOtherExpenseBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherExpenseBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherExpenseBillEntryCollection getOtherExpenseBillEntryCollection() throws BOSException
    {
        try {
            return getController().getOtherExpenseBillEntryCollection(getContext());
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
    public OtherExpenseBillEntryCollection getOtherExpenseBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherExpenseBillEntryCollection(getContext(), view);
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
    public OtherExpenseBillEntryCollection getOtherExpenseBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherExpenseBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}