package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.IBillEntryBase;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IBgAdjustBillEntry extends IBillEntryBase
{
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BgAdjustBillEntryInfo getBgAdjustBillEntryInfo(String oql) throws BOSException, EASBizException;
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection() throws BOSException;
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(EntityViewInfo view) throws BOSException;
    public BgAdjustBillEntryCollection getBgAdjustBillEntryCollection(String oql) throws BOSException;
}