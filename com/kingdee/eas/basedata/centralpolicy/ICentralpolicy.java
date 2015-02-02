package com.kingdee.eas.basedata.centralpolicy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ICentralpolicy extends IDataBase
{
    public CentralpolicyInfo getCentralpolicyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CentralpolicyInfo getCentralpolicyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CentralpolicyInfo getCentralpolicyInfo(String oql) throws BOSException, EASBizException;
    public CentralpolicyCollection getCentralpolicyCollection() throws BOSException;
    public CentralpolicyCollection getCentralpolicyCollection(EntityViewInfo view) throws BOSException;
    public CentralpolicyCollection getCentralpolicyCollection(String oql) throws BOSException;
}