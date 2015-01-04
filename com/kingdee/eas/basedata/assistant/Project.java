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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *按公司隔离项目-User defined method
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
     *删除项目-User defined method
     *@param pk 项目ID
     *@param isFromMM 从制造系统调用
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