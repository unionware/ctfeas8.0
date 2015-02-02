package com.kingdee.eas.ma.crbg.database.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BeginHiDetailBillController extends CoreBillBaseController
{
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(Context ctx) throws BOSException, RemoteException;
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IRowSet getProject(Context ctx, String ID) throws BOSException, RemoteException;
    public void getThread(Context ctx, int beginkey, int endkey) throws BOSException, RemoteException;
}