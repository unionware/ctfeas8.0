package com.kingdee.eas.ma.crbg.database;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IBeginHiDetailBill extends ICoreBillBase
{
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection() throws BOSException;
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(EntityViewInfo view) throws BOSException;
    public BeginHiDetailBillCollection getBeginHiDetailBillCollection(String oql) throws BOSException;
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BeginHiDetailBillInfo getBeginHiDetailBillInfo(String oql) throws BOSException, EASBizException;
    public IRowSet getProject(String ID) throws BOSException;
    public void getThread(int beginkey, int endkey) throws BOSException;
}