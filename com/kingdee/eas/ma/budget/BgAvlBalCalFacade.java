package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.eas.ma.budget.app.*;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class BgAvlBalCalFacade extends AbstractBizCtrl implements IBgAvlBalCalFacade
{
    public BgAvlBalCalFacade()
    {
        super();
        registerInterface(IBgAvlBalCalFacade.class, this);
    }
    public BgAvlBalCalFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBgAvlBalCalFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5DCD038C");
    }
    private BgAvlBalCalFacadeController getController() throws BOSException
    {
        return (BgAvlBalCalFacadeController)getBizController();
    }
    /**
     *计算预算可用金额-User defined method
     *@param param 查询预算可用金额参数集
     *@return
     */
    public BigDecimal calAvlBalCalAmt(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().calAvlBalCalAmt(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *计算预算实际发生数-User defined method
     *@param param 查询预算实际发生数参数集
     *@return
     */
    public BigDecimal calActualAmt(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().calActualAmt(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}