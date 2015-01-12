package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.cp.bc.app.*;

public class CostProjectMgrPersonFacade extends AbstractBizCtrl implements ICostProjectMgrPersonFacade
{
    public CostProjectMgrPersonFacade()
    {
        super();
        registerInterface(ICostProjectMgrPersonFacade.class, this);
    }
    public CostProjectMgrPersonFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostProjectMgrPersonFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E2EC4939");
    }
    private CostProjectMgrPersonFacadeController getController() throws BOSException
    {
        return (CostProjectMgrPersonFacadeController)getBizController();
    }
    /**
     *���ݵ��ݻ�ȡ������-�������ο�������ʹ��-User defined method
     *@param billId billId
     *@return
     */
    public Person[] getPersonObjsByBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonObjsByBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݵ��ݻ�ȡ������-�������ο�������ʹ��-User defined method
     *@param billId billId
     *@return
     */
    public String[] getPersonIdsByBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonIdsByBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݵ����Ϸ��ò��Ż�ȡ������-User defined method
     *@param billId ���õ���Id
     *@return
     */
    public Person[] getPersonObjsByOrg(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonObjsByOrg(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}