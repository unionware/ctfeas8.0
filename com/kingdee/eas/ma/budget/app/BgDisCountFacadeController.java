package com.kingdee.eas.ma.budget.app;

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
import com.kingdee.bos.util.BOSUuid;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BgDisCountFacadeController extends BizController
{
    public BOSUuid getCollectFormId(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException, RemoteException;
    public Set getCollectFormInfo(Context ctx, BOSUuid bgFormId, Set orgUnitIds, Set state, boolean isFund) throws BOSException, EASBizException, RemoteException;
    public void checkFormulaIsEctype(Context ctx, BOSUuid bgFormId, Map formulaMap) throws BOSException, EASBizException, RemoteException;
    public Map loadAllRelateInfo(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
}