package com.kingdee.eas.ma.crbg.database;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.ma.crbg.database.app.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class BeginHiDetailBill extends CoreBillBase implements IBeginHiDetailBill
{
    public BeginHiDetailBill()
    {
        super();
        registerInterface(IBeginHiDetailBill.class, this);
    }
    public BeginHiDetailBill(Context ctx)
    {
        super(ctx);
        registerInterface(IBeginHiDetailBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8A5194A7");
    }
    private BeginHiDetailBillController getController() throws BOSException
    {
        return (BeginHiDetailBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection() throws BOSException
    {
        try {
            return getController().getBeginHiDetailBillCollection(getContext());
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
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBeginHiDetailBillCollection(getContext(), view);
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
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getBeginHiDetailBillCollection(getContext(), oql);
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
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBeginHiDetailBillInfo(getContext(), pk);
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
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBeginHiDetailBillInfo(getContext(), pk, selector);
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
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBeginHiDetailBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取核算项目-User defined method
     *@param ID id
     *@return
     */
    public IRowSet getProject(String ID) throws BOSException
    {
        try {
            return getController().getProject(getContext(), ID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行线程方法-User defined method
     *@param beginkey beginkey
     *@param endkey endkey
     */
    public void getThread(int beginkey, int endkey) throws BOSException
    {
        try {
            getController().getThread(getContext(), beginkey, endkey);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}