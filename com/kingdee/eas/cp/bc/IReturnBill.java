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
import com.kingdee.eas.framework.ICoreBillBase;

public interface IReturnBill extends ICoreBillBase
{
    public ReturnBillCollection getReturnBillCollection() throws BOSException;
    public ReturnBillCollection getReturnBillCollection(EntityViewInfo view) throws BOSException;
    public ReturnBillCollection getReturnBillCollection(String oql) throws BOSException;
    public ReturnBillInfo getReturnBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ReturnBillInfo getReturnBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ReturnBillInfo getReturnBillInfo(String oql) throws BOSException, EASBizException;
    public void audit(ReturnBillInfo model) throws BOSException, EASBizException;
}