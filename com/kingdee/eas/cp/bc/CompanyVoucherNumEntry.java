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

public class CompanyVoucherNumEntry extends CoreBillEntryBase implements ICompanyVoucherNumEntry
{
    public CompanyVoucherNumEntry()
    {
        super();
        registerInterface(ICompanyVoucherNumEntry.class, this);
    }
    public CompanyVoucherNumEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ICompanyVoucherNumEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5AD708FB");
    }
    private CompanyVoucherNumEntryController getController() throws BOSException
    {
        return (CompanyVoucherNumEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompanyVoucherNumEntryInfo getCompanyVoucherNumEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompanyVoucherNumEntryInfo(getContext(), pk);
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
    public CompanyVoucherNumEntryInfo getCompanyVoucherNumEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompanyVoucherNumEntryInfo(getContext(), pk, selector);
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
    public CompanyVoucherNumEntryInfo getCompanyVoucherNumEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompanyVoucherNumEntryInfo(getContext(), oql);
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
    public CompanyVoucherNumEntryCollection getCompanyVoucherNumEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompanyVoucherNumEntryCollection(getContext(), oql);
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
    public CompanyVoucherNumEntryCollection getCompanyVoucherNumEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompanyVoucherNumEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompanyVoucherNumEntryCollection getCompanyVoucherNumEntryCollection() throws BOSException
    {
        try {
            return getController().getCompanyVoucherNumEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}