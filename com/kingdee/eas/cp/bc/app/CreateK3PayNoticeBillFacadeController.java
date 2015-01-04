package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CreateK3PayNoticeBillFacadeController extends BizController
{
    public void createByDailyLoanBill(Context ctx, BOSUuid billid) throws BOSException, EASBizException, RemoteException;
    public void createByBizAccountBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void createByEvectionLoanBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void createByTraveAccountBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void backRun(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void checkBillHasCreate(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public boolean checkBillHasCreate2(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
}