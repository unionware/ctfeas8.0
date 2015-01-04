package com.kingdee.eas.basedata.org;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.basedata.org.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.IDataBase;

public class Position extends DataBase implements IPosition
{
    public Position()
    {
        super();
        registerInterface(IPosition.class, this);
    }
    public Position(Context ctx)
    {
        super(ctx);
        registerInterface(IPosition.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("74AE612E");
    }
    private PositionController getController() throws BOSException
    {
        return (PositionController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PositionInfo getPositionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionInfo(getContext(), pk);
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
    public PositionInfo getPositionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPositionInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PositionCollection getPositionCollection() throws BOSException
    {
        try {
            return getController().getPositionCollection(getContext());
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
    public PositionCollection getPositionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPositionCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取该职位下所有人-User defined method
     *@param positionId 职位ID
     *@return
     */
    public PersonCollection getAllPersons(BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getAllPersons(getContext(), positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取主要职位是该职位的人的集合-User defined method
     *@param positionId 职位ID
     *@return
     */
    public PersonCollection getPrimaryPersons(BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getPrimaryPersons(getContext(), positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取该职位在所有层级关系中的上级职位集合-User defined method
     *@param positionId 职位ID
     *@return
     */
    public PositionCollection getAllParent(BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getAllParent(getContext(), positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取默认层级关系中的上级职位-User defined method
     *@param hierarchyId 层次关系Id
     *@param positionId 职位ID
     *@return
     */
    public PositionInfo getParent(BOSUuid hierarchyId, BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getParent(getContext(), hierarchyId, positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取默认层级关系中的上级职位-User defined method
     *@param positionId 职位ID
     *@return
     */
    public PositionInfo getParent(BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getParent(getContext(), positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取该职位在所有层级关系中的下级职位集合-User defined method
     *@param positionId 职位ID
     *@return
     */
    public PositionCollection getAllChildren(BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getAllChildren(getContext(), positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取默认层级关系中的下级职位集合-User defined method
     *@param hierarchyId 层次关系ID
     *@param positionId 职位ID
     *@return
     */
    public PositionCollection getChildren(BOSUuid hierarchyId, BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getChildren(getContext(), hierarchyId, positionId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取默认层级关系中的下级职位集合-User defined method
     *@param positionId 职位ID
     *@return
     */
    public PositionCollection getChildren(BOSUuid positionId) throws BOSException, EASBizException
    {
        try {
            return getController().getChildren(getContext(), positionId);
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
    public PositionCollection getPositionCollection(String oql) throws BOSException
    {
        try {
            return getController().getPositionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *对同一组织下的职位进行排序-User defined method
     *@param ids 要进行排序的职位ID列表
     *@param orders 序号列表
     */
    public void sort(String[] ids, int[] orders) throws BOSException, EASBizException
    {
        try {
            getController().sort(getContext(), ids, orders);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}