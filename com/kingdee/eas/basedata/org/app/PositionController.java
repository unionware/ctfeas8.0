package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.PositionCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PositionController extends DataBaseController
{
    public PositionInfo getPositionInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PositionInfo getPositionInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getPositionCollection(Context ctx) throws BOSException, RemoteException;
    public PositionCollection getPositionCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PersonCollection getAllPersons(Context ctx, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PersonCollection getPrimaryPersons(Context ctx, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getAllParent(Context ctx, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionInfo getParent(Context ctx, BOSUuid hierarchyId, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionInfo getParent(Context ctx, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getAllChildren(Context ctx, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getChildren(Context ctx, BOSUuid hierarchyId, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getChildren(Context ctx, BOSUuid positionId) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getPositionCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void sort(Context ctx, String[] ids, int[] orders) throws BOSException, EASBizException, RemoteException;
}