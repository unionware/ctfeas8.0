package com.kingdee.eas.cp.bc;

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

public interface ICostProjectMgrPersonFacade extends IBizCtrl
{
    public Person[] getPersonObjsByBill(BOSUuid billId) throws BOSException, EASBizException;
    public String[] getPersonIdsByBill(BOSUuid billId) throws BOSException, EASBizException;
    public Person[] getPersonObjsByOrg(BOSUuid billId) throws BOSException, EASBizException;
}