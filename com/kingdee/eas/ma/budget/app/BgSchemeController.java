package com.kingdee.eas.ma.budget.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.util.Map;
import java.util.List;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Hashtable;
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.ma.budget.BgSchemeCollection;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.ma.budget.BgTemplateCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BgSchemeController extends DataBaseController
{
    public BgSchemeInfo getBgSchemeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BgSchemeInfo getBgSchemeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BgSchemeInfo getBgSchemeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BgSchemeInfo getValue(Context ctx, BOSUuid bgSchemeId) throws BOSException, EASBizException, RemoteException;
    public BgSchemeCollection getBgSchemeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public BgSchemeCollection getBgSchemeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BgSchemeCollection getBgSchemeCollection(Context ctx) throws BOSException, RemoteException;
    public BgTemplateCollection getTemplateByScheme(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void executeScheme(Context ctx, IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException, RemoteException;
    public String batchEexecuteScheme(Context ctx, IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException, RemoteException;
    public void batchAntiExecuteScheme(Context ctx, BOSUuid bgSchemeId, BOSUuid orgUnitId) throws BOSException, EASBizException, RemoteException;
    public boolean isQuotedByBgForm(Context ctx, String schemeId) throws BOSException, EASBizException, RemoteException;
    public boolean hasChildScheme(Context ctx, String schemeId) throws BOSException, EASBizException, RemoteException;
    public IObjectCollection getBgSchemeByOrgUnit(Context ctx, String orgUnitId) throws BOSException, EASBizException, RemoteException;
    public boolean usedByBgFormInOrgUnit(Context ctx, String schemeId, String ortUnitId) throws BOSException, EASBizException, RemoteException;
    public void antiExecuteScheme(Context ctx, IObjectPK bgSchemePK) throws BOSException, EASBizException, RemoteException;
    public void sendMessageToSubOrgs(Context ctx, String ids, String orgName, String schemeName, String userNumber) throws BOSException, EASBizException, RemoteException;
    public Hashtable getDirectChildren(Context ctx, String bgFormId, List subOrgList) throws BOSException, EASBizException, RemoteException;
    public Hashtable getInDirectChildren(Context ctx, String bgFormId, Hashtable subOrgNumberMap) throws BOSException, EASBizException, RemoteException;
    public boolean checkTempParentQuotedBySchemeParent(Context ctx, String templateId, String schemeId) throws BOSException, EASBizException, RemoteException;
    public boolean checkTempAssignedInBgScheme(Context ctx, String templateId, String schemeId) throws BOSException, EASBizException, RemoteException;
    public BgTemplateCollection getTemplateBySchemeToAddTem(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public BgTemplateCollection getTemplate(Context ctx, String orgUnitID) throws BOSException, EASBizException, RemoteException;
    public boolean hasFormallyYearScheme(Context ctx, String unitID) throws BOSException, EASBizException, RemoteException;
    public boolean isAreadyDispatched(Context ctx, String rootID, String orgUnitID) throws BOSException, EASBizException, RemoteException;
    public boolean checkIsUpdate(Context ctx, Map orgUnit) throws BOSException, EASBizException, RemoteException;
    public Hashtable isAreadyDispatched(Context ctx, String rootId, Hashtable orgMap) throws BOSException, EASBizException, RemoteException;
    public BgSchemeCollection getSameSchemeByOrgNumbers(Context ctx, String[] orgNumbers) throws BOSException, EASBizException, RemoteException;
    public boolean executePlan(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, BOSUuid bgPeriodId, String command) throws BOSException, EASBizException, RemoteException;
    public void copyScheme(Context ctx, Map map) throws BOSException, EASBizException, RemoteException;
    public void delCopyScheme(Context ctx, IObjectPK bgSchemeID) throws BOSException, EASBizException, RemoteException;
}