package com.kingdee.eas.basedata.org;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.IDataBase;

public interface IPosition extends IDataBase
{
    public PositionInfo getPositionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PositionInfo getPositionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PositionCollection getPositionCollection() throws BOSException;
    public PositionCollection getPositionCollection(EntityViewInfo view) throws BOSException;
    public PersonCollection getAllPersons(BOSUuid positionId) throws BOSException, EASBizException;
    public PersonCollection getPrimaryPersons(BOSUuid positionId) throws BOSException, EASBizException;
    public PositionCollection getAllParent(BOSUuid positionId) throws BOSException, EASBizException;
    public PositionInfo getParent(BOSUuid hierarchyId, BOSUuid positionId) throws BOSException, EASBizException;
    public PositionInfo getParent(BOSUuid positionId) throws BOSException, EASBizException;
    public PositionCollection getAllChildren(BOSUuid positionId) throws BOSException, EASBizException;
    public PositionCollection getChildren(BOSUuid hierarchyId, BOSUuid positionId) throws BOSException, EASBizException;
    public PositionCollection getChildren(BOSUuid positionId) throws BOSException, EASBizException;
    public PositionCollection getPositionCollection(String oql) throws BOSException;
    public void sort(String[] ids, int[] orders) throws BOSException, EASBizException;
}