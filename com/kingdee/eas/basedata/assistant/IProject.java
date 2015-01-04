package com.kingdee.eas.basedata.assistant;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProject extends ITreeBase
{
    public ProjectInfo getProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectInfo getProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectCollection getProjectCollection() throws BOSException;
    public ProjectCollection getProjectCollection(EntityViewInfo view) throws BOSException;
    public void isolateProject() throws BOSException, EASBizException;
    public void delete(IObjectPK pk, boolean isFromMM) throws BOSException, EASBizException;
}