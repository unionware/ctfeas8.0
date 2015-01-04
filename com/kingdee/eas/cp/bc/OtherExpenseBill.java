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

public class OtherExpenseBill extends ExpenseReqBill implements IOtherExpenseBill
{
    public OtherExpenseBill()
    {
        super();
        registerInterface(IOtherExpenseBill.class, this);
    }
    public OtherExpenseBill(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherExpenseBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E76173AD");
    }
    private OtherExpenseBillController getController() throws BOSException
    {
        return (OtherExpenseBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OtherExpenseBillInfo getOtherExpenseBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherExpenseBillInfo(getContext(), pk);
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
    public OtherExpenseBillInfo getOtherExpenseBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherExpenseBillInfo(getContext(), pk, selector);
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
    public OtherExpenseBillInfo getOtherExpenseBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherExpenseBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherExpenseBillCollection getOtherExpenseBillCollection() throws BOSException
    {
        try {
            return getController().getOtherExpenseBillCollection(getContext());
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
    public OtherExpenseBillCollection getOtherExpenseBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherExpenseBillCollection(getContext(), view);
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
    public OtherExpenseBillCollection getOtherExpenseBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherExpenseBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}