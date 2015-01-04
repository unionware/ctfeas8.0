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
import com.kingdee.eas.cp.bc.BizAccountBillEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BizAccountBillEntryController extends OtherExpenseEntryCoreBaseController
{
    public BizAccountBillEntryInfo getBizAccountBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BizAccountBillEntryInfo getBizAccountBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BizAccountBillEntryInfo getBizAccountBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BizAccountBillEntryCollection getBizAccountBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public BizAccountBillEntryCollection getBizAccountBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BizAccountBillEntryCollection getBizAccountBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}