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

public class DailyLoanBillEntry extends OtherExpenseEntryCoreBase implements IDailyLoanBillEntry
{
    public DailyLoanBillEntry()
    {
        super();
        registerInterface(IDailyLoanBillEntry.class, this);
    }
    public DailyLoanBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDailyLoanBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F4884CE0");
    }
    private DailyLoanBillEntryController getController() throws BOSException
    {
        return (DailyLoanBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DailyLoanBillEntryInfo getDailyLoanBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDailyLoanBillEntryInfo(getContext(), pk);
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
    public DailyLoanBillEntryInfo getDailyLoanBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDailyLoanBillEntryInfo(getContext(), pk, selector);
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
    public DailyLoanBillEntryInfo getDailyLoanBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDailyLoanBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DailyLoanBillEntryCollection getDailyLoanBillEntryCollection() throws BOSException
    {
        try {
            return getController().getDailyLoanBillEntryCollection(getContext());
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
    public DailyLoanBillEntryCollection getDailyLoanBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDailyLoanBillEntryCollection(getContext(), view);
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
    public DailyLoanBillEntryCollection getDailyLoanBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDailyLoanBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}