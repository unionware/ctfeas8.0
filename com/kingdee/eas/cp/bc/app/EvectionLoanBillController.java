package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.cp.bc.EvectionLoanBillCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.bg.BgCtrlParam;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface EvectionLoanBillController extends LoanBillController
{
    public EvectionLoanBillInfo getEvectionLoanBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public EvectionLoanBillInfo getEvectionLoanBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public EvectionLoanBillInfo getEvectionLoanBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public EvectionLoanBillCollection getEvectionLoanBillCollection(Context ctx) throws BOSException, RemoteException;
    public EvectionLoanBillCollection getEvectionLoanBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public EvectionLoanBillCollection getEvectionLoanBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void setPassState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setNotPassState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public BgCtrlParam[] getBgParam(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setPaymentState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setCloseState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public boolean needBgAudit(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void reBackBg(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void payment(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setCheckingState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setDraftState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public List getCanDeleteIDs(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public boolean isCanModify(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setState(Context ctx, BOSUuid id, StateEnum state) throws BOSException, EASBizException, RemoteException;
    public String payBack(Context ctx, String billID) throws BOSException, RemoteException;
}