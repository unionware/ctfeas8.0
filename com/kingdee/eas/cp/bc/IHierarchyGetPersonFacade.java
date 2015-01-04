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

public interface IHierarchyGetPersonFacade extends IBizCtrl
{
    public Person[] getPersonByCPL(String companyId, String pLevelNum) throws BOSException, EASBizException;
    public Person[] getPersonByAPL(String adminOrgId, String pLevelNum) throws BOSException, EASBizException;
    public Person[] getPersonByPPlH(String positionNum, String pLevelNum, String hierarchyNum) throws BOSException, EASBizException;
}