package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import com.kingdee.bos.dao.IObjectCollection;
import java.util.Hashtable;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IBgScheme extends IDataBase
{
    public BgSchemeInfo getBgSchemeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BgSchemeInfo getBgSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BgSchemeInfo getBgSchemeInfo(String oql) throws BOSException, EASBizException;
    public BgSchemeInfo getValue(BOSUuid bgSchemeId) throws BOSException, EASBizException;
    public BgSchemeCollection getBgSchemeCollection(String oql) throws BOSException;
    public BgSchemeCollection getBgSchemeCollection(EntityViewInfo view) throws BOSException;
    public BgSchemeCollection getBgSchemeCollection() throws BOSException;
    public BgTemplateCollection getTemplateByScheme(String id) throws BOSException, EASBizException;
    public void executeScheme(IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException;
    public String batchEexecuteScheme(IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException;
    public void batchAntiExecuteScheme(BOSUuid bgSchemeId, BOSUuid orgUnitId) throws BOSException, EASBizException;
    public boolean isQuotedByBgForm(String schemeId) throws BOSException, EASBizException;
    public boolean hasChildScheme(String schemeId) throws BOSException, EASBizException;
    public IObjectCollection getBgSchemeByOrgUnit(String orgUnitId) throws BOSException, EASBizException;
    public boolean usedByBgFormInOrgUnit(String schemeId, String ortUnitId) throws BOSException, EASBizException;
    public void antiExecuteScheme(IObjectPK bgSchemePK) throws BOSException, EASBizException;
    public void sendMessageToSubOrgs(String ids, String orgName, String schemeName, String userNumber) throws BOSException, EASBizException;
    public Hashtable getDirectChildren(String bgFormId, List subOrgList) throws BOSException, EASBizException;
    public Hashtable getInDirectChildren(String bgFormId, Hashtable subOrgNumberMap) throws BOSException, EASBizException;
    public boolean checkTempParentQuotedBySchemeParent(String templateId, String schemeId) throws BOSException, EASBizException;
    public boolean checkTempAssignedInBgScheme(String templateId, String schemeId) throws BOSException, EASBizException;
    public BgTemplateCollection getTemplateBySchemeToAddTem(String id) throws BOSException, EASBizException;
    public BgTemplateCollection getTemplate(String orgUnitID) throws BOSException, EASBizException;
    public boolean hasFormallyYearScheme(String unitID) throws BOSException, EASBizException;
    public boolean isAreadyDispatched(String rootID, String orgUnitID) throws BOSException, EASBizException;
    public boolean checkIsUpdate(Map orgUnit) throws BOSException, EASBizException;
    public Hashtable isAreadyDispatched(String rootId, Hashtable orgMap) throws BOSException, EASBizException;
    public BgSchemeCollection getSameSchemeByOrgNumbers(String[] orgNumbers) throws BOSException, EASBizException;
    public boolean executePlan(BOSUuid orgUnitId, BOSUuid bgSchemeId, BOSUuid bgPeriodId, String command) throws BOSException, EASBizException;
    public void copyScheme(Map map) throws BOSException, EASBizException;
    public void delCopyScheme(IObjectPK bgSchemeID) throws BOSException, EASBizException;
}