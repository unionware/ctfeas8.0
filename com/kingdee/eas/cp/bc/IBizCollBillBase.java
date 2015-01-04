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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBizCollBillBase extends IBizCollCoreBillBase
{
    public BizCollBillBaseInfo getBizCollBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BizCollBillBaseInfo getBizCollBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BizCollBillBaseInfo getBizCollBillBaseInfo(String oql) throws BOSException, EASBizException;
    public BizCollBillBaseCollection getBizCollBillBaseCollection() throws BOSException;
    public BizCollBillBaseCollection getBizCollBillBaseCollection(EntityViewInfo view) throws BOSException;
    public BizCollBillBaseCollection getBizCollBillBaseCollection(String oql) throws BOSException;
    public void antiAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void abandon(BOSUuid billId) throws BOSException, EASBizException;
}