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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class CompanyVoucherNum extends DataBase implements ICompanyVoucherNum
{
    public CompanyVoucherNum()
    {
        super();
        registerInterface(ICompanyVoucherNum.class, this);
    }
    public CompanyVoucherNum(Context ctx)
    {
        super(ctx);
        registerInterface(ICompanyVoucherNum.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CF7D89B7");
    }
    private CompanyVoucherNumController getController() throws BOSException
    {
        return (CompanyVoucherNumController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompanyVoucherNumInfo(getContext(), pk);
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
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompanyVoucherNumInfo(getContext(), pk, selector);
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
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompanyVoucherNumInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection() throws BOSException
    {
        try {
            return getController().getCompanyVoucherNumCollection(getContext());
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
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompanyVoucherNumCollection(getContext(), view);
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
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompanyVoucherNumCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}