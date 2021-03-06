package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.ReturnBillInfo;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.cp.bc.ReturnBillCollection;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ReturnBillController extends CoreBillBaseController
{
    public ReturnBillCollection getReturnBillCollection(Context ctx) throws BOSException, RemoteException;
    public ReturnBillCollection getReturnBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ReturnBillCollection getReturnBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ReturnBillInfo getReturnBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ReturnBillInfo getReturnBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ReturnBillInfo getReturnBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, ReturnBillInfo model) throws BOSException, EASBizException, RemoteException;
}