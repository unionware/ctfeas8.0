package com.kingdee.eas.ma.nbudget;

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

public interface IBgCalculateSchemeDisFacade extends IBizCtrl
{
    public Object assign(Object param) throws BOSException, EASBizException;
    public Object reCalculate(Object param) throws BOSException, EASBizException;
    public boolean checkHasAssigned(Map param) throws BOSException, EASBizException;
    public Object execute(Object params) throws BOSException, EASBizException;
    public boolean antiExecute(Object schemeID) throws BOSException, EASBizException;
    public void reCalculateByTransaction() throws BOSException, EASBizException;
}