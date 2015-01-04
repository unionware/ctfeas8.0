package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface Bc2K3VoucherFacadeController extends BizController
{
    public void crVouchByBizAccount(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void crVouchByDailyLoan(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void crVouchByEvectionLoan(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void crVouchByTraveAccount(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void backRun(Context ctx) throws BOSException, EASBizException, RemoteException;
}