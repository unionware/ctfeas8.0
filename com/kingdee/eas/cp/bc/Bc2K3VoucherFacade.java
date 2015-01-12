package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.app.*;

public class Bc2K3VoucherFacade extends AbstractBizCtrl implements IBc2K3VoucherFacade
{
    public Bc2K3VoucherFacade()
    {
        super();
        registerInterface(IBc2K3VoucherFacade.class, this);
    }
    public Bc2K3VoucherFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBc2K3VoucherFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("12FE6D2D");
    }
    private Bc2K3VoucherFacadeController getController() throws BOSException
    {
        return (Bc2K3VoucherFacadeController)getBizController();
    }
    /**
     *���ñ�������ƾ֤-User defined method
     *@param billID billID
     */
    public void crVouchByBizAccount(String billID) throws BOSException, EASBizException
    {
        try {
            getController().crVouchByBizAccount(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������ƾ֤-User defined method
     *@param billID billID
     */
    public void crVouchByDailyLoan(String billID) throws BOSException, EASBizException
    {
        try {
            getController().crVouchByDailyLoan(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ӿ�����ƾ֤-User defined method
     *@param billID billID
     */
    public void crVouchByEvectionLoan(String billID) throws BOSException, EASBizException
    {
        try {
            getController().crVouchByEvectionLoan(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ñ�������ƾ֤-User defined method
     *@param billID billID
     */
    public void crVouchByTraveAccount(String billID) throws BOSException, EASBizException
    {
        try {
            getController().crVouchByTraveAccount(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��̨���г���-User defined method
     */
    public void backRun() throws BOSException, EASBizException
    {
        try {
            getController().backRun(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}