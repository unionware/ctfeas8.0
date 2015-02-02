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
     *��ȡ������ܵ�Ԥ���ĵ�����ID-User defined method
     *@param bgFormId Ԥ���ID
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
     *���ز�����ܵ�Ԥ�����Ϣ-User defined method
     *@param bgFormId �����ܵ�Ԥ���ID
     *@param orgUnitIds �����ҵ���֯
     *@param state ������ܵ�Ԥ���״̬
     *@param isFund �Ƿ����ʽ�ƻ�
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
     *��������Ŀ��ʽ�Ƿ��ظ�-User defined method
     *@param bgFormId Ԥ�������
     *@param formulaMap ��Ŀ��ʽMAP
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
     *Ϊ����RPC���ý��ܶ�ε�RPC����ʹ�ø÷���������-User defined method
     *@param id ����id
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