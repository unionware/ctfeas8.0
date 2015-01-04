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

public interface IBaseDataMatchEntry extends ICoreBillEntryBase
{
    public BaseDataMatchEntryInfo getBaseDataMatchEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BaseDataMatchEntryInfo getBaseDataMatchEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BaseDataMatchEntryInfo getBaseDataMatchEntryInfo(String oql) throws BOSException, EASBizException;
    public BaseDataMatchEntryCollection getBaseDataMatchEntryCollection() throws BOSException;
    public BaseDataMatchEntryCollection getBaseDataMatchEntryCollection(EntityViewInfo view) throws BOSException;
    public BaseDataMatchEntryCollection getBaseDataMatchEntryCollection(String oql) throws BOSException;
}