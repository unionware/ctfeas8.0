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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IExcessSet extends IDataBase
{
    public ExcessSetInfo getExcessSetInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ExcessSetInfo getExcessSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ExcessSetInfo getExcessSetInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public ExcessSetCollection getExcessSetCollection() throws BOSException;
    public ExcessSetCollection getExcessSetCollection(EntityViewInfo view) throws BOSException;
    public ExcessSetCollection getExcessSetCollection(String oql) throws BOSException;
}