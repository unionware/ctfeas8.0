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

public interface IExcessSetEntry extends ICoreBillEntryBase
{
    public ExcessSetEntryInfo getExcessSetEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ExcessSetEntryInfo getExcessSetEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ExcessSetEntryInfo getExcessSetEntryInfo(String oql) throws BOSException, EASBizException;
    public ExcessSetEntryCollection getExcessSetEntryCollection(String oql) throws BOSException;
    public ExcessSetEntryCollection getExcessSetEntryCollection(EntityViewInfo view) throws BOSException;
    public ExcessSetEntryCollection getExcessSetEntryCollection() throws BOSException;
}