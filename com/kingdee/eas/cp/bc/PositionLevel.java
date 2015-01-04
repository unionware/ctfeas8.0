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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class PositionLevel extends DataBase implements IPositionLevel
{
    public PositionLevel()
    {
        super();
        registerInterface(IPositionLevel.class, this);
    }
    public PositionLevel(Context ctx)
    {
        super(ctx);
        registerInterface(IPositionLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("028A903D");
    }
    private PositionLevelController getController() throws BOSException
    {
        return (PositionLevelController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PositionLevelInfo getPositionLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionLevelInfo(getContext(), pk);
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
    public PositionLevelInfo getPositionLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionLevelInfo(getContext(), pk, selector);
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
    public PositionLevelInfo getPositionLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionLevelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PositionLevelCollection getPositionLevelCollection() throws BOSException
    {
        try {
            return getController().getPositionLevelCollection(getContext());
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
    public PositionLevelCollection getPositionLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPositionLevelCollection(getContext(), view);
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
    public PositionLevelCollection getPositionLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getPositionLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}