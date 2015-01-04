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

public interface IEvectionLoanBillEntry extends IEvectionExpBillEntryBase
{
    public EvectionLoanBillEntryInfo getEvectionLoanBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EvectionLoanBillEntryInfo getEvectionLoanBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EvectionLoanBillEntryInfo getEvectionLoanBillEntryInfo(String oql) throws BOSException, EASBizException;
    public EvectionLoanBillEntryCollection getEvectionLoanBillEntryCollection() throws BOSException;
    public EvectionLoanBillEntryCollection getEvectionLoanBillEntryCollection(EntityViewInfo view) throws BOSException;
    public EvectionLoanBillEntryCollection getEvectionLoanBillEntryCollection(String oql) throws BOSException;
}