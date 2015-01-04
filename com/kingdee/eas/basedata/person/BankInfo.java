package com.kingdee.eas.basedata.person;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.person.app.*;

public class BankInfo extends CoreBillEntryBase implements IBankInfo
{
    public BankInfo()
    {
        super();
        registerInterface(IBankInfo.class, this);
    }
    public BankInfo(Context ctx)
    {
        super(ctx);
        registerInterface(IBankInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("79C8FDA2");
    }
    private BankInfoController getController() throws BOSException
    {
        return (BankInfoController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public BankInfoCollection getBankInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getBankInfoCollection(getContext(), oql);
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
    public BankInfoCollection getBankInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBankInfoCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BankInfoCollection getBankInfoCollection() throws BOSException
    {
        try {
            return getController().getBankInfoCollection(getContext());
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
    public BankInfoInfo getBankInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBankInfoInfo(getContext(), oql);
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
    public BankInfoInfo getBankInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBankInfoInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BankInfoInfo getBankInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBankInfoInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}