package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IOtherExpenseBillEntry extends IOtherExpenseEntryCoreBase
{
    public OtherExpenseBillEntryInfo getOtherExpenseBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OtherExpenseBillEntryInfo getOtherExpenseBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OtherExpenseBillEntryInfo getOtherExpenseBillEntryInfo(String oql) throws BOSException, EASBizException;
    public OtherExpenseBillEntryCollection getOtherExpenseBillEntryCollection() throws BOSException;
    public OtherExpenseBillEntryCollection getOtherExpenseBillEntryCollection(EntityViewInfo view) throws BOSException;
    public OtherExpenseBillEntryCollection getOtherExpenseBillEntryCollection(String oql) throws BOSException;
}