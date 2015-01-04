package com.kingdee.eas.basedata.person;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import java.util.List;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.basedata.org.PositionCollection;

public interface IPerson extends IDataBase
{
    public PositionInfo getPrimaryPosition(BOSUuid personId) throws BOSException, EASBizException;
    public PositionCollection getPositions(BOSUuid personId) throws BOSException, EASBizException;
    public AdminOrgUnitCollection getAllAdminOrgUnit(BOSUuid personId) throws BOSException, EASBizException;
    public AdminOrgUnitInfo getPrimaryAdminOrgUnit(BOSUuid personId) throws BOSException, EASBizException;
    public PersonInfo getPersonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PersonInfo getPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PersonCollection getPersonCollection() throws BOSException;
    public PersonCollection getPersonCollection(EntityViewInfo view) throws BOSException;
    public void assignPosition(String[] personID, PositionInfo positionInfo) throws BOSException, EASBizException;
    public PersonCollection getOtherPersonCollection(PersonCollection personInfos) throws BOSException, EASBizException;
    public PersonCollection getPersonCollection(String oql) throws BOSException;
    public void batchAudit(String[] ids, boolean isAudit) throws BOSException, EASBizException;
    public void checkInfo(String personId) throws BOSException, EASBizException;
    public String[] checkInfo(String[] personIds) throws BOSException, EASBizException;
    public void audit(String[] ids) throws BOSException, EASBizException;
    public void antiAudit(String[] ids) throws BOSException, EASBizException;
    public PersonInfo getPersonByStr(String str, String type) throws BOSException, EASBizException;
    public PersonInfo getPersonByStr(String str, String type, String hrOrgId) throws BOSException, EASBizException;
    public PersonCollection getPersonCollectionByStrLs(List idCardLs, List passportLs, List numberLs) throws BOSException, EASBizException;
    public PersonCollection getPersonCollectionByStrLs(List idCardLs, List passportLs, List numberLs, String hrOrgId) throws BOSException, EASBizException;
    public void insertPersons(int index, String[] persons) throws BOSException;
}