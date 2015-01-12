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

public class ReturnBillEntry extends CoreBillEntryBase implements IReturnBillEntry
{
    public ReturnBillEntry()
    {
        super();
        registerInterface(IReturnBillEntry.class, this);
    }
    public ReturnBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IReturnBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E04EB53D");
    }
    private ReturnBillEntryController getController() throws BOSException
    {
        return (ReturnBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ReturnBillEntryInfo getReturnBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReturnBillEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public ReturnBillEntryInfo getReturnBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReturnBillEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public ReturnBillEntryInfo getReturnBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReturnBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ReturnBillEntryCollection getReturnBillEntryCollection() throws BOSException
    {
        try {
            return getController().getReturnBillEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public ReturnBillEntryCollection getReturnBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReturnBillEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public ReturnBillEntryCollection getReturnBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getReturnBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}