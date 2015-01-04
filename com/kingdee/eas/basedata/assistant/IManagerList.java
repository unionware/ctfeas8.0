package com.kingdee.eas.basedata.assistant;

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

public interface IManagerList extends ICoreBillEntryBase
{
    public ManagerListInfo getManagerListInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ManagerListInfo getManagerListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ManagerListInfo getManagerListInfo(String oql) throws BOSException, EASBizException;
    public ManagerListCollection getManagerListCollection() throws BOSException;
    public ManagerListCollection getManagerListCollection(EntityViewInfo view) throws BOSException;
    public ManagerListCollection getManagerListCollection(String oql) throws BOSException;
}