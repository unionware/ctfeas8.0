package com.kingdee.eas.ma.nbudget.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BgCalculateSchemeDisFacadeController extends BizController
{
    public Object assign(Context ctx, Object param) throws BOSException, EASBizException, RemoteException;
    public Object reCalculate(Context ctx, Object param) throws BOSException, EASBizException, RemoteException;
    public boolean checkHasAssigned(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Object execute(Context ctx, Object params) throws BOSException, EASBizException, RemoteException;
    public boolean antiExecute(Context ctx, Object schemeID) throws BOSException, EASBizException, RemoteException;
    public void reCalculateByTransaction(Context ctx) throws BOSException, EASBizException, RemoteException;
}