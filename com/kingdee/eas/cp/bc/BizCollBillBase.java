package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public abstract class BizCollBillBase extends BizCollCoreBillBase implements IBizCollBillBase
{
    public BizCollBillBase()
    {
        super();
        registerInterface(IBizCollBillBase.class, this);
    }
    public BizCollBillBase(Context ctx)
    {
        super(ctx);
        registerInterface(IBizCollBillBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1C791319");
    }
    private BizCollBillBaseController getController() throws BOSException
    {
        return (BizCollBillBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BizCollBillBaseInfo getBizCollBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBizCollBillBaseInfo(getContext(), pk);
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
    public BizCollBillBaseInfo getBizCollBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBizCollBillBaseInfo(getContext(), pk, selector);
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
    public BizCollBillBaseInfo getBizCollBillBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBizCollBillBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BizCollBillBaseCollection getBizCollBillBaseCollection() throws BOSException
    {
        try {
            return getController().getBizCollBillBaseCollection(getContext());
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
    public BizCollBillBaseCollection getBizCollBillBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBizCollBillBaseCollection(getContext(), view);
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
    public BizCollBillBaseCollection getBizCollBillBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getBizCollBillBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param billId 单据id
     */
    public void antiAudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *废弃-User defined method
     *@param billId 单据Id
     */
    public void abandon(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().abandon(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}