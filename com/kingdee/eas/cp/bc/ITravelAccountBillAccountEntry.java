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

public interface ITravelAccountBillAccountEntry extends ICollectionAccountCoreBase
{
    public TravelAccountBillAccountEntryInfo getTravelAccountBillAccountEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TravelAccountBillAccountEntryInfo getTravelAccountBillAccountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TravelAccountBillAccountEntryInfo getTravelAccountBillAccountEntryInfo(String oql) throws BOSException, EASBizException;
    public TravelAccountBillAccountEntryCollection getTravelAccountBillAccountEntryCollection() throws BOSException;
    public TravelAccountBillAccountEntryCollection getTravelAccountBillAccountEntryCollection(EntityViewInfo view) throws BOSException;
    public TravelAccountBillAccountEntryCollection getTravelAccountBillAccountEntryCollection(String oql) throws BOSException;
}