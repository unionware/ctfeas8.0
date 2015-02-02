package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.ma.budget.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class BgAdjustBillEntry extends BillEntryBase implements IBgAdjustBillEntry
{
    public BgAdjustBillEntry()
    {
        super();
        registerInterface(IBgAdjustBillEntry.class, this);
    }
    public BgAdjustBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBgAdjustBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2C0AAC76");
    }
    private BgAdjustBillEntryController getController() throws BOSException
    {
        return (BgAdjustBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBgAdjustBillEntryInfo(getContext(), pk);
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
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBgAdjustBillEntryInfo(getContext(), pk, selector);
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
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBgAdjustBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection() throws BOSException
    {
        try {
            return getController().getBgAdjustBillEntryCollection(getContext());
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
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBgAdjustBillEntryCollection(getContext(), view);
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
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBgAdjustBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}