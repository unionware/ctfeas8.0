package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CostProjectMgrPersonFacadeController extends BizController
{
    public Person[] getPersonObjsByBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public String[] getPersonIdsByBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public Person[] getPersonObjsByOrg(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}