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

public class LinkOthExpense extends CoreBillEntryBase implements ILinkOthExpense
{
    public LinkOthExpense()
    {
        super();
        registerInterface(ILinkOthExpense.class, this);
    }
    public LinkOthExpense(Context ctx)
    {
        super(ctx);
        registerInterface(ILinkOthExpense.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3F83AF2D");
    }
    private LinkOthExpenseController getController() throws BOSException
    {
        return (LinkOthExpenseController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public LinkOthExpenseInfo getLinkOthExpenseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkOthExpenseInfo(getContext(), pk);
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
    public LinkOthExpenseInfo getLinkOthExpenseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkOthExpenseInfo(getContext(), pk, selector);
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
    public LinkOthExpenseInfo getLinkOthExpenseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkOthExpenseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public LinkOthExpenseCollection getLinkOthExpenseCollection() throws BOSException
    {
        try {
            return getController().getLinkOthExpenseCollection(getContext());
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
    public LinkOthExpenseCollection getLinkOthExpenseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLinkOthExpenseCollection(getContext(), view);
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
    public LinkOthExpenseCollection getLinkOthExpenseCollection(String oql) throws BOSException
    {
        try {
            return getController().getLinkOthExpenseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}