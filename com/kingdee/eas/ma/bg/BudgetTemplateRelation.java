package com.kingdee.eas.ma.bg;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.ma.bg.app.*;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class BudgetTemplateRelation extends DataBase implements IBudgetTemplateRelation
{
    public BudgetTemplateRelation()
    {
        super();
        registerInterface(IBudgetTemplateRelation.class, this);
    }
    public BudgetTemplateRelation(Context ctx)
    {
        super(ctx);
        registerInterface(IBudgetTemplateRelation.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1C455C3C");
    }
    private BudgetTemplateRelationController getController() throws BOSException
    {
        return (BudgetTemplateRelationController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBudgetTemplateRelationInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBudgetTemplateRelationInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBudgetTemplateRelationInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection() throws BOSException
    {
        try {
            return getController().getBudgetTemplateRelationCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBudgetTemplateRelationCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(String oql) throws BOSException
    {
        try {
            return getController().getBudgetTemplateRelationCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}