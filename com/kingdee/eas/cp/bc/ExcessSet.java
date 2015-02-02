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
import java.math.BigDecimal;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ExcessSet extends DataBase implements IExcessSet
{
    public ExcessSet()
    {
        super();
        registerInterface(IExcessSet.class, this);
    }
    public ExcessSet(Context ctx)
    {
        super(ctx);
        registerInterface(IExcessSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5DC2FC0F");
    }
    private ExcessSetController getController() throws BOSException
    {
        return (ExcessSetController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ExcessSetInfo getExcessSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ExcessSetInfo getExcessSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ExcessSetInfo getExcessSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ExcessSetCollection getExcessSetCollection() throws BOSException
    {
        try {
            return getController().getExcessSetCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public ExcessSetCollection getExcessSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getExcessSetCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ExcessSetCollection getExcessSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getExcessSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取超额比例-User defined method
     *@param projectid 项目ID
     *@param expenseTypeid 费用类型
     *@param costcenterid 费用归属部门
     *@param excessSetCol 符合年份的超额比例集合
     *@return
     */
    public BigDecimal getAboveQuota(String projectid, String expenseTypeid, String costcenterid, ExcessSetCollection excessSetCol) throws BOSException, EASBizException
    {
        try {
            return getController().getAboveQuota(getContext(), projectid, expenseTypeid, costcenterid, excessSetCol);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取相同年份的超额比例集合-User defined method
     *@param year 年份
     *@return
     */
    public ExcessSetCollection getExcessSetInfos(long year) throws BOSException, EASBizException
    {
        try {
            return getController().getExcessSetInfos(getContext(), year);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}