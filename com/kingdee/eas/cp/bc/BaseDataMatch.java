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
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class BaseDataMatch extends CoreBillBase implements IBaseDataMatch
{
    public BaseDataMatch()
    {
        super();
        registerInterface(IBaseDataMatch.class, this);
    }
    public BaseDataMatch(Context ctx)
    {
        super(ctx);
        registerInterface(IBaseDataMatch.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A10194CC");
    }
    private BaseDataMatchController getController() throws BOSException
    {
        return (BaseDataMatchController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BaseDataMatchCollection getBaseDataMatchCollection() throws BOSException
    {
        try {
            return getController().getBaseDataMatchCollection(getContext());
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
    public BaseDataMatchCollection getBaseDataMatchCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBaseDataMatchCollection(getContext(), view);
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
    public BaseDataMatchCollection getBaseDataMatchCollection(String oql) throws BOSException
    {
        try {
            return getController().getBaseDataMatchCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BaseDataMatchInfo getBaseDataMatchInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseDataMatchInfo(getContext(), pk);
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
    public BaseDataMatchInfo getBaseDataMatchInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseDataMatchInfo(getContext(), pk, selector);
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
    public BaseDataMatchInfo getBaseDataMatchInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseDataMatchInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����EAS�����ȡK3����-User defined method
     *@param types ����
     *@param num ����
     *@return
     */
    public String getK3NumByEasNum(BaseDataEnum types, String num) throws BOSException, EASBizException
    {
        try {
            return getController().getK3NumByEasNum(getContext(), types, num);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}