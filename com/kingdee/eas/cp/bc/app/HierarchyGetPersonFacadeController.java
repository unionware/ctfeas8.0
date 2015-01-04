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

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface HierarchyGetPersonFacadeController extends BizController
{
    public Person[] getPersonByCPL(Context ctx, String companyId, String pLevelNum) throws BOSException, EASBizException, RemoteException;
    public Person[] getPersonByAPL(Context ctx, String adminOrgId, String pLevelNum) throws BOSException, EASBizException, RemoteException;
    public Person[] getPersonByPPlH(Context ctx, String positionNum, String pLevelNum, String hierarchyNum) throws BOSException, EASBizException, RemoteException;
}