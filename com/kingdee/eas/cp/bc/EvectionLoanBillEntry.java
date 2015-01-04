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

public class EvectionLoanBillEntry extends EvectionExpBillEntryBase implements IEvectionLoanBillEntry
{
    public EvectionLoanBillEntry()
    {
        super();
        registerInterface(IEvectionLoanBillEntry.class, this);
    }
    public EvectionLoanBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IEvectionLoanBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9DB7201A");
    }
    private EvectionLoanBillEntryController getController() throws BOSException
    {
        return (EvectionLoanBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public EvectionLoanBillEntryInfo getEvectionLoanBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionLoanBillEntryInfo(getContext(), pk);
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
    public EvectionLoanBillEntryInfo getEvectionLoanBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionLoanBillEntryInfo(getContext(), pk, selector);
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
    public EvectionLoanBillEntryInfo getEvectionLoanBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionLoanBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public EvectionLoanBillEntryCollection getEvectionLoanBillEntryCollection() throws BOSException
    {
        try {
            return getController().getEvectionLoanBillEntryCollection(getContext());
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
    public EvectionLoanBillEntryCollection getEvectionLoanBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvectionLoanBillEntryCollection(getContext(), view);
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
    public EvectionLoanBillEntryCollection getEvectionLoanBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvectionLoanBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}