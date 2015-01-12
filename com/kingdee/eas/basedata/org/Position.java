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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *��ȡ��ְλ��������-User defined method
     *@param positionId ְλID
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
     *��ȡ��Ҫְλ�Ǹ�ְλ���˵ļ���-User defined method
     *@param positionId ְλID
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
     *��ȡ��ְλ�����в㼶��ϵ�е��ϼ�ְλ����-User defined method
     *@param positionId ְλID
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
     *��ȡĬ�ϲ㼶��ϵ�е��ϼ�ְλ-User defined method
     *@param hierarchyId ��ι�ϵId
     *@param positionId ְλID
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
     *��ȡĬ�ϲ㼶��ϵ�е��ϼ�ְλ-User defined method
     *@param positionId ְλID
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
     *��ȡ��ְλ�����в㼶��ϵ�е��¼�ְλ����-User defined method
     *@param positionId ְλID
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
     *��ȡĬ�ϲ㼶��ϵ�е��¼�ְλ����-User defined method
     *@param hierarchyId ��ι�ϵID
     *@param positionId ְλID
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
     *��ȡĬ�ϲ㼶��ϵ�е��¼�ְλ����-User defined method
     *@param positionId ְλID
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
     *ȡ����-System defined method
     *@param oql ȡ����
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
     *��ͬһ��֯�µ�ְλ��������-User defined method
     *@param ids Ҫ���������ְλID�б�
     *@param orders ����б�
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