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
     *�����¼�Ԥ�����-User defined method
     *@param bgFormInfo Ԥ���
     *@param ouIdList �¼�ou����
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
     *��û�����-User defined method
     *@param bgFormPK Ԥ���pk���߻���pk
     *@param isCollectForm �Ƿ����
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
     *�ύ���Ԥ������-User defined method
     *@param bgFormPK Ԥ���pk
     *@param comment ���
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
     *��ȡ���Ķ���������-User defined method
     *@param bgCollectId Ԥ��������ID
     *@param refBgFormId Ԥ�����¼����ID
     *@param bgFormId Ԥ�������ID
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
     *������Ԥ���Ķ���������-User defined method
     *@param bgCollectId Ԥ��������
     *@param refBgFormId Ԥ�����¼����
     *@param bgFormId Ԥ�������
     *@param model Ԥ�����¼
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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