package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.ma.budget.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public class RefDisCountBgForm extends CoreBase implements IRefDisCountBgForm
{
    public RefDisCountBgForm()
    {
        super();
        registerInterface(IRefDisCountBgForm.class, this);
    }
    public RefDisCountBgForm(Context ctx)
    {
        super(ctx);
        registerInterface(IRefDisCountBgForm.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("78A6183C");
    }
    private RefDisCountBgFormController getController() throws BOSException
    {
        return (RefDisCountBgFormController)getBizController();
    }
    /**
     *构造下级预算表集合-User defined method
     *@param bgFormInfo 预算表
     *@param ouIdList 下级ou集合
     *@return
     */
    public RefDisCountBgFormCollection loadRefBgFormCollectionByBgForm(BgFormInfo bgFormInfo, List ouIdList) throws BOSException, EASBizException
    {
        try {
            return getController().loadRefBgFormCollectionByBgForm(getContext(), bgFormInfo, ouIdList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得汇编意见-User defined method
     *@param bgFormPK 预算表pk或者汇编表pk
     *@param isCollectForm 是否汇编表
     *@return
     */
    public List getCommentByBgForm(IObjectPK bgFormPK, boolean isCollectForm) throws BOSException, EASBizException
    {
        try {
            return getController().getCommentByBgForm(getContext(), bgFormPK, isCollectForm);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交汇编预算表意见-User defined method
     *@param bgFormPK 预算表pk
     *@param comment 意见
     */
    public void addCommentByBgForm(IObjectPK bgFormPK, String comment) throws BOSException, EASBizException
    {
        try {
            getController().addCommentByBgForm(getContext(), bgFormPK, comment);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取汇编的二进制数据-User defined method
     *@param bgCollectId 预算汇编主键ID
     *@param refBgFormId 预算汇编分录主键ID
     *@param bgFormId 预算表主键ID
     *@return
     */
    public byte[] getKdf(BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId) throws BOSException, EASBizException
    {
        try {
            return getController().getKdf(getContext(), bgCollectId, refBgFormId, bgFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存汇编预算表的二进制数据-User defined method
     *@param bgCollectId 预算汇编主键
     *@param refBgFormId 预算汇编分录主键
     *@param bgFormId 预算表主键
     *@param model 预算汇编分录
     */
    public void submitKdf(BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId, RefDisCountBgFormInfo model) throws BOSException, EASBizException
    {
        try {
            getController().submitKdf(getContext(), bgCollectId, refBgFormId, bgFormId, model);
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
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRefDisCountBgFormInfo(getContext(), pk);
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
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRefDisCountBgFormInfo(getContext(), pk, selector);
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
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRefDisCountBgFormInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection() throws BOSException
    {
        try {
            return getController().getRefDisCountBgFormCollection(getContext());
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
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRefDisCountBgFormCollection(getContext(), view);
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
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(String oql) throws BOSException
    {
        try {
            return getController().getRefDisCountBgFormCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}