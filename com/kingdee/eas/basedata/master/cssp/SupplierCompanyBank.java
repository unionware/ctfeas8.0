package com.kingdee.eas.basedata.master.cssp;

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
import com.kingdee.eas.basedata.master.cssp.app.*;

public class SupplierCompanyBank extends CoreBillEntryBase implements ISupplierCompanyBank
{
    public SupplierCompanyBank()
    {
        super();
        registerInterface(ISupplierCompanyBank.class, this);
    }
    public SupplierCompanyBank(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierCompanyBank.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E62C04BD");
    }
    private SupplierCompanyBankController getController() throws BOSException
    {
        return (SupplierCompanyBankController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierCompanyBankInfo getSupplierCompanyBankInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierCompanyBankInfo(getContext(), pk);
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
    public SupplierCompanyBankInfo getSupplierCompanyBankInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierCompanyBankInfo(getContext(), pk, selector);
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
    public SupplierCompanyBankInfo getSupplierCompanyBankInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierCompanyBankInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierCompanyBankCollection getSupplierCompanyBankCollection() throws BOSException
    {
        try {
            return getController().getSupplierCompanyBankCollection(getContext());
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
    public SupplierCompanyBankCollection getSupplierCompanyBankCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierCompanyBankCollection(getContext(), view);
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
    public SupplierCompanyBankCollection getSupplierCompanyBankCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierCompanyBankCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}