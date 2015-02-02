package com.kingdee.eas.ma.budget.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.ma.budget.BgAdjustBillEntryCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.BillEntryBaseController;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.budget.BgAdjustBillEntryInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BgAdjustBillEntryController extends BillEntryBaseController
{
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}