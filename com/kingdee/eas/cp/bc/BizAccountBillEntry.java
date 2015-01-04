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

public class BizAccountBillEntry extends OtherExpenseEntryCoreBase implements IBizAccountBillEntry
{
    public BizAccountBillEntry()
    {
        super();
        registerInterface(IBizAccountBillEntry.class, this);
    }
    public BizAccountBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBizAccountBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F2062F13");
    }
    private BizAccountBillEntryController getController() throws BOSException
    {
        return (BizAccountBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public BizAccountBillEntryInfo getBizAccountBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BizAccountBillEntryInfo getBizAccountBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillEntryInfo(getContext(), pk);
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
    public BizAccountBillEntryInfo getBizAccountBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BizAccountBillEntryCollection getBizAccountBillEntryCollection() throws BOSException
    {
        try {
            return getController().getBizAccountBillEntryCollection(getContext());
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
    public BizAccountBillEntryCollection getBizAccountBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBizAccountBillEntryCollection(getContext(), view);
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
    public BizAccountBillEntryCollection getBizAccountBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBizAccountBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}