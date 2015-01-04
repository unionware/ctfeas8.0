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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param oql 取值
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
     *根据EAS编码获取K3编码-User defined method
     *@param types 类型
     *@param num 编码
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