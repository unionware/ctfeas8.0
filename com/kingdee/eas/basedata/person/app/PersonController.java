package com.kingdee.eas.basedata.person.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import java.util.List;
import com.kingdee.eas.basedata.org.PositionCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PersonController extends DataBaseController
{
    public PositionInfo getPrimaryPosition(Context ctx, BOSUuid personId) throws BOSException, EASBizException, RemoteException;
    public PositionCollection getPositions(Context ctx, BOSUuid personId) throws BOSException, EASBizException, RemoteException;
    public AdminOrgUnitCollection getAllAdminOrgUnit(Context ctx, BOSUuid personId) throws BOSException, EASBizException, RemoteException;
    public AdminOrgUnitInfo getPrimaryAdminOrgUnit(Context ctx, BOSUuid personId) throws BOSException, EASBizException, RemoteException;
    public PersonInfo getPersonInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PersonInfo getPersonInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PersonCollection getPersonCollection(Context ctx) throws BOSException, RemoteException;
    public PersonCollection getPersonCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public void assignPosition(Context ctx, String[] personID, PositionInfo positionInfo) throws BOSException, EASBizException, RemoteException;
    public PersonCollection getOtherPersonCollection(Context ctx, PersonCollection personInfos) throws BOSException, EASBizException, RemoteException;
    public PersonCollection getPersonCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void batchAudit(Context ctx, String[] ids, boolean isAudit) throws BOSException, EASBizException, RemoteException;
    public void checkInfo(Context ctx, String personId) throws BOSException, EASBizException, RemoteException;
    public String[] checkInfo(Context ctx, String[] personIds) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, String[] ids) throws BOSException, EASBizException, RemoteException;
    public void antiAudit(Context ctx, String[] ids) throws BOSException, EASBizException, RemoteException;
    public PersonInfo getPersonByStr(Context ctx, String str, String type) throws BOSException, EASBizException, RemoteException;
    public PersonInfo getPersonByStr(Context ctx, String str, String type, String hrOrgId) throws BOSException, EASBizException, RemoteException;
    public PersonCollection getPersonCollectionByStrLs(Context ctx, List idCardLs, List passportLs, List numberLs) throws BOSException, EASBizException, RemoteException;
    public PersonCollection getPersonCollectionByStrLs(Context ctx, List idCardLs, List passportLs, List numberLs, String hrOrgId) throws BOSException, EASBizException, RemoteException;
    public void insertPersons(Context ctx, int index, String[] persons) throws BOSException, RemoteException;
}