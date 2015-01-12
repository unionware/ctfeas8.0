package com.kingdee.eas.basedata.assistant;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.basedata.assistant.app.*;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class Project extends TreeBase implements IProject
{
    public Project()
    {
        super();
        registerInterface(IProject.class, this);
    }
    public Project(Context ctx)
    {
        super(ctx);
        registerInterface(IProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DADE05EE");
    }
    private ProjectController getController() throws BOSException
    {
        return (ProjectController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ProjectInfo getProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectInfo(getContext(), pk);
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
    public ProjectInfo getProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ProjectCollection getProjectCollection() throws BOSException
    {
        try {
            return getController().getProjectCollection(getContext());
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
    public ProjectCollection getProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����˾������Ŀ-User defined method
     */
    public void isolateProject() throws BOSException, EASBizException
    {
        try {
            getController().isolateProject(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����Ŀ-User defined method
     *@param pk ��ĿID
     *@param isFromMM ������ϵͳ����
     */
    public void delete(IObjectPK pk, boolean isFromMM) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk, isFromMM);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}