package com.kingdee.eas.ma.bg.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationCollection;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BudgetTemplateRelationController extends DataBaseController
{
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(Context ctx) throws BOSException, RemoteException;
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(Context ctx, String oql) throws BOSException, RemoteException;
}