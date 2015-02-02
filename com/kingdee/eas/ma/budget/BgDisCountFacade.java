package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.ma.budget.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.Set;

public class BgDisCountFacade extends AbstractBizCtrl implements IBgDisCountFacade
{
    public BgDisCountFacade()
    {
        super();
        registerInterface(IBgDisCountFacade.class, this);
    }
    public BgDisCountFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBgDisCountFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F92EF741");
    }
    private BgDisCountFacadeController getController() throws BOSException
    {
        return (BgDisCountFacadeController)getBizController();
    }
    /**
     *获取参与汇总的预算表的调整表ID-User defined method
     *@param bgFormId 预算表ID
     *@return
     */
    public BOSUuid getCollectFormId(BOSUuid bgFormId) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectFormId(getContext(), bgFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *返回参与汇总的预算表信息-User defined method
     *@param bgFormId 被汇总的预算表ID
     *@param orgUnitIds 参与汇兑的组织
     *@param state 参与汇总的预算表状态
     *@param isFund 是否是资金计划
     *@return
     */
    public Set getCollectFormInfo(BOSUuid bgFormId, Set orgUnitIds, Set state, boolean isFund) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectFormInfo(getContext(), bgFormId, orgUnitIds, state, isFund);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *检查汇编的项目公式是否重复-User defined method
     *@param bgFormId 预算表主键
     *@param formulaMap 项目公式MAP
     */
    public void checkFormulaIsEctype(BOSUuid bgFormId, Map formulaMap) throws BOSException, EASBizException
    {
        try {
            getController().checkFormulaIsEctype(getContext(), bgFormId, formulaMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *为降低RPC调用将很多次的RPC调用使用该方法来处理-User defined method
     *@param id 主键id
     *@return
     */
    public Map loadAllRelateInfo(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            return getController().loadAllRelateInfo(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}