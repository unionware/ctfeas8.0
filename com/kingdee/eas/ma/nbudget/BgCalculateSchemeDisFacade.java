package com.kingdee.eas.ma.nbudget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.ma.nbudget.app.*;

public class BgCalculateSchemeDisFacade extends AbstractBizCtrl implements IBgCalculateSchemeDisFacade
{
    public BgCalculateSchemeDisFacade()
    {
        super();
        registerInterface(IBgCalculateSchemeDisFacade.class, this);
    }
    public BgCalculateSchemeDisFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBgCalculateSchemeDisFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AF6307DF");
    }
    private BgCalculateSchemeDisFacadeController getController() throws BOSException
    {
        return (BgCalculateSchemeDisFacadeController)getBizController();
    }
    /**
     *����-User defined method
     *@param param ����
     *@return
     */
    public Object assign(Object param) throws BOSException, EASBizException
    {
        try {
            return getController().assign(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ؼ���-User defined method
     *@param param ����
     *@return
     */
    public Object reCalculate(Object param) throws BOSException, EASBizException
    {
        try {
            return getController().reCalculate(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *checkHasAssigned-User defined method
     *@param param param
     *@return
     */
    public boolean checkHasAssigned(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().checkHasAssigned(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ִ��-User defined method
     *@param params ����
     *@return
     */
    public Object execute(Object params) throws BOSException, EASBizException
    {
        try {
            return getController().execute(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ִ��-User defined method
     *@param schemeID ����ID
     *@return
     */
    public boolean antiExecute(Object schemeID) throws BOSException, EASBizException
    {
        try {
            return getController().antiExecute(getContext(), schemeID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���㷽��(��EAS��̨�������)-User defined method
     */
    public void reCalculateByTransaction() throws BOSException, EASBizException
    {
        try {
            getController().reCalculateByTransaction(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}