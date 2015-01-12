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

public class BaseDataMatchEntry extends CoreBillEntryBase implements IBaseDataMatchEntry
{
    public BaseDataMatchEntry()
    {
        super();
        registerInterface(IBaseDataMatchEntry.class, this);
    }
    public BaseDataMatchEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBaseDataMatchEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C4F7A306");
    }
    private BaseDataMatchEntryController getController() throws BOSException
    {
        return (BaseDataMatchEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BaseDataMatchEntryInfo getBaseDataMatchEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseDataMatchEntryInfo(getContext(), pk);
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
    public BaseDataMatchEntryInfo getBaseDataMatchEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseDataMatchEntryInfo(getContext(), pk, selector);
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
    public BaseDataMatchEntryInfo getBaseDataMatchEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseDataMatchEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BaseDataMatchEntryCollection getBaseDataMatchEntryCollection() throws BOSException
    {
        try {
            return getController().getBaseDataMatchEntryCollection(getContext());
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
    public BaseDataMatchEntryCollection getBaseDataMatchEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBaseDataMatchEntryCollection(getContext(), view);
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
    public BaseDataMatchEntryCollection getBaseDataMatchEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBaseDataMatchEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}