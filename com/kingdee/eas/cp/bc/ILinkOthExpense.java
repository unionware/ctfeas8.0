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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ILinkOthExpense extends ICoreBillEntryBase
{
    public LinkOthExpenseInfo getLinkOthExpenseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public LinkOthExpenseInfo getLinkOthExpenseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public LinkOthExpenseInfo getLinkOthExpenseInfo(String oql) throws BOSException, EASBizException;
    public LinkOthExpenseCollection getLinkOthExpenseCollection() throws BOSException;
    public LinkOthExpenseCollection getLinkOthExpenseCollection(EntityViewInfo view) throws BOSException;
    public LinkOthExpenseCollection getLinkOthExpenseCollection(String oql) throws BOSException;
}