package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectController extends TreeBaseController
{
    public ProjectInfo getProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProjectInfo getProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectCollection getProjectCollection(Context ctx) throws BOSException, RemoteException;
    public ProjectCollection getProjectCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public void isolateProject(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK pk, boolean isFromMM) throws BOSException, EASBizException, RemoteException;
}