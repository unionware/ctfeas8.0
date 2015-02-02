package com.kingdee.eas.ma.budget.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.app.CoreBaseController;
import java.util.List;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.budget.BgFormInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RefDisCountBgFormController extends CoreBaseController
{
    public RefDisCountBgFormCollection loadRefBgFormCollectionByBgForm(Context ctx, BgFormInfo bgFormInfo, List ouIdList) throws BOSException, EASBizException, RemoteException;
    public List getCommentByBgForm(Context ctx, IObjectPK bgFormPK, boolean isCollectForm) throws BOSException, EASBizException, RemoteException;
    public void addCommentByBgForm(Context ctx, IObjectPK bgFormPK, String comment) throws BOSException, EASBizException, RemoteException;
    public byte[] getKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId) throws BOSException, EASBizException, RemoteException;
    public void submitKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId, RefDisCountBgFormInfo model) throws BOSException, EASBizException, RemoteException;
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(Context ctx) throws BOSException, RemoteException;
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(Context ctx, String oql) throws BOSException, RemoteException;
}