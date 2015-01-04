package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class PositionLevelTree extends TreeBase implements IPositionLevelTree
{
    public PositionLevelTree()
    {
        super();
        registerInterface(IPositionLevelTree.class, this);
    }
    public PositionLevelTree(Context ctx)
    {
        super(ctx);
        registerInterface(IPositionLevelTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9E46117B");
    }
    private PositionLevelTreeController getController() throws BOSException
    {
        return (PositionLevelTreeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PositionLevelTreeInfo getPositionLevelTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionLevelTreeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public PositionLevelTreeInfo getPositionLevelTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionLevelTreeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public PositionLevelTreeInfo getPositionLevelTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionLevelTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PositionLevelTreeCollection getPositionLevelTreeCollection() throws BOSException
    {
        try {
            return getController().getPositionLevelTreeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public PositionLevelTreeCollection getPositionLevelTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPositionLevelTreeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public PositionLevelTreeCollection getPositionLevelTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPositionLevelTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}