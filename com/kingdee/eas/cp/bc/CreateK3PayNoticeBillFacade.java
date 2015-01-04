package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.cp.bc.app.*;

public class CreateK3PayNoticeBillFacade extends AbstractBizCtrl implements ICreateK3PayNoticeBillFacade
{
    public CreateK3PayNoticeBillFacade()
    {
        super();
        registerInterface(ICreateK3PayNoticeBillFacade.class, this);
    }
    public CreateK3PayNoticeBillFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICreateK3PayNoticeBillFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8E09203F");
    }
    private CreateK3PayNoticeBillFacadeController getController() throws BOSException
    {
        return (CreateK3PayNoticeBillFacadeController)getBizController();
    }
    /**
     *借款单生成通知单-User defined method
     *@param billid 借款单
     */
    public void createByDailyLoanBill(BOSUuid billid) throws BOSException, EASBizException
    {
        try {
            getController().createByDailyLoanBill(getContext(), billid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *费用报销单生成通知单-User defined method
     *@param billId 费用报销单
     */
    public void createByBizAccountBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().createByBizAccountBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *出差借款单生成通知单-User defined method
     *@param billId 出差借款单
     */
    public void createByEvectionLoanBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().createByEvectionLoanBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *差旅费用报销单-User defined method
     *@param billId 差旅费用报销单
     */
    public void createByTraveAccountBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().createByTraveAccountBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步K3付款状态-User defined method
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