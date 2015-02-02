package com.kingdee.eas.ma.budget;

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

public interface IBgAvlBalCalFacade extends IBizCtrl
{
    public BigDecimal calAvlBalCalAmt(Map param) throws BOSException, EASBizException;
    public BigDecimal calActualAmt(Map param) throws BOSException, EASBizException;
}