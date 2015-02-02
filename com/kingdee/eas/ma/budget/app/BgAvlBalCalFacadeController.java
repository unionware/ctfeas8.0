package com.kingdee.eas.ma.budget.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BgAvlBalCalFacadeController extends BizController
{
    public BigDecimal calAvlBalCalAmt(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public BigDecimal calActualAmt(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}