package com.kingdee.eas.ma.budget;

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

public interface IBgDisCountFacade extends IBizCtrl
{
    public BOSUuid getCollectFormId(BOSUuid bgFormId) throws BOSException, EASBizException;
    public Set getCollectFormInfo(BOSUuid bgFormId, Set orgUnitIds, Set state, boolean isFund) throws BOSException, EASBizException;
    public void checkFormulaIsEctype(BOSUuid bgFormId, Map formulaMap) throws BOSException, EASBizException;
    public Map loadAllRelateInfo(BOSUuid id) throws BOSException, EASBizException;
}