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
import com.kingdee.eas.cp.bc.BizCollBillBaseCollection;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BizCollBillBaseController extends BizCollCoreBillBaseController
{
    public BizCollBillBaseInfo getBizCollBillBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BizCollBillBaseInfo getBizCollBillBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BizCollBillBaseInfo getBizCollBillBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BizCollBillBaseCollection getBizCollBillBaseCollection(Context ctx) throws BOSException, RemoteException;
    public BizCollBillBaseCollection getBizCollBillBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BizCollBillBaseCollection getBizCollBillBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void antiAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void abandon(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}