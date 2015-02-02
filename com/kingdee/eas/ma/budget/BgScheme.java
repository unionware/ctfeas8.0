package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

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
import com.kingdee.eas.ma.budget.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class BgScheme extends DataBase implements IBgScheme
{
    public BgScheme()
    {
        super();
        registerInterface(IBgScheme.class, this);
    }
    public BgScheme(Context ctx)
    {
        super(ctx);
        registerInterface(IBgScheme.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D4ABADCB");
    }
    private BgSchemeController getController() throws BOSException
    {
        return (BgSchemeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BgSchemeInfo getBgSchemeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBgSchemeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public BgSchemeInfo getBgSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBgSchemeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public BgSchemeInfo getBgSchemeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBgSchemeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-User defined method
     *@param bgSchemeId bgSchemeId
     *@return
     */
    public BgSchemeInfo getValue(BOSUuid bgSchemeId) throws BOSException, EASBizException
    {
        try {
            return getController().getValue(getContext(), bgSchemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public BgSchemeCollection getBgSchemeCollection(String oql) throws BOSException
    {
        try {
            return getController().getBgSchemeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public BgSchemeCollection getBgSchemeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBgSchemeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BgSchemeCollection getBgSchemeCollection() throws BOSException
    {
        try {
            return getController().getBgSchemeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取当前方案的所有模板-User defined method
     *@param id 当前预算方案Id
     *@return
     */
    public BgTemplateCollection getTemplateByScheme(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateByScheme(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行方案-User defined method
     *@param bgSchemePK 方案Id
     *@param orgUnitId 组织Id
     */
    public void executeScheme(IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            getController().executeScheme(getContext(), bgSchemePK, orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量执行系列方案-User defined method
     *@param bgSchemePK 预算方案PK
     *@param orgUnitId 组织ID
     *@return
     */
    public String batchEexecuteScheme(IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            return getController().batchEexecuteScheme(getContext(), bgSchemePK, orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量反执行系列方案-User defined method
     *@param bgSchemeId 预算方案ID
     *@param orgUnitId 组织ID
     */
    public void batchAntiExecuteScheme(BOSUuid bgSchemeId, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            getController().batchAntiExecuteScheme(getContext(), bgSchemeId, orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预算方案是否被预算表引用-User defined method
     *@param schemeId 方案Id
     *@return
     */
    public boolean isQuotedByBgForm(String schemeId) throws BOSException, EASBizException
    {
        try {
            return getController().isQuotedByBgForm(getContext(), schemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否有下级方案-User defined method
     *@param schemeId 预算方案Id
     *@return
     */
    public boolean hasChildScheme(String schemeId) throws BOSException, EASBizException
    {
        try {
            return getController().hasChildScheme(getContext(), schemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据组织得到预算方案-User defined method
     *@param orgUnitId 组织ID
     *@return
     */
    public IObjectCollection getBgSchemeByOrgUnit(String orgUnitId) throws BOSException, EASBizException
    {
        try {
            return getController().getBgSchemeByOrgUnit(getContext(), orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断本方案在此组织下是否被预算表引用过-User defined method
     *@param schemeId 方案id
     *@param ortUnitId 组织id
     *@return
     */
    public boolean usedByBgFormInOrgUnit(String schemeId, String ortUnitId) throws BOSException, EASBizException
    {
        try {
            return getController().usedByBgFormInOrgUnit(getContext(), schemeId, ortUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反执行方案-User defined method
     *@param bgSchemePK 方案id
     */
    public void antiExecuteScheme(IObjectPK bgSchemePK) throws BOSException, EASBizException
    {
        try {
            getController().antiExecuteScheme(getContext(), bgSchemePK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *给下级组织发送消息。用于分配结束后，给所分配的下级组织发送通知消息-User defined method
     *@param ids 所分配的下级组织所有id字符串
     *@param orgName 上级组织名称
     *@param schemeName 方案名称
     *@param userNumber 用户编码
     */
    public void sendMessageToSubOrgs(String ids, String orgName, String schemeName, String userNumber) throws BOSException, EASBizException
    {
        try {
            getController().sendMessageToSubOrgs(getContext(), ids, orgName, schemeName, userNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提供一个预算表Id和一个组织集合，获取预算表所属的预算方案在组织集合中的孩子-User defined method
     *@param bgFormId bgFormId
     *@param subOrgList subOrgList
     *@return
     */
    public Hashtable getDirectChildren(String bgFormId, List subOrgList) throws BOSException, EASBizException
    {
        try {
            return getController().getDirectChildren(getContext(), bgFormId, subOrgList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提供一个预算表Id和一个组织集合，获取预算表所属的预算方案在组织集合中的孩子-User defined method
     *@param bgFormId bgFormId
     *@param subOrgNumberMap subOrgNumberMap
     *@return
     */
    public Hashtable getInDirectChildren(String bgFormId, Hashtable subOrgNumberMap) throws BOSException, EASBizException
    {
        try {
            return getController().getInDirectChildren(getContext(), bgFormId, subOrgNumberMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断给定模板的父是否被给定方案的父引用-User defined method
     *@param templateId 模板id
     *@param schemeId 方案id
     *@return
     */
    public boolean checkTempParentQuotedBySchemeParent(String templateId, String schemeId) throws BOSException, EASBizException
    {
        try {
            return getController().checkTempParentQuotedBySchemeParent(getContext(), templateId, schemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断指定模板是否已随指定方案分配-User defined method
     *@param templateId 模板Id
     *@param schemeId 方案Id
     *@return
     */
    public boolean checkTempAssignedInBgScheme(String templateId, String schemeId) throws BOSException, EASBizException
    {
        try {
            return getController().checkTempAssignedInBgScheme(getContext(), templateId, schemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *方案添加模板时使用-User defined method
     *@param id id
     *@return
     */
    public BgTemplateCollection getTemplateBySchemeToAddTem(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateBySchemeToAddTem(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得此组织下的所有模板-User defined method
     *@param orgUnitID orgUnitID
     *@return
     */
    public BgTemplateCollection getTemplate(String orgUnitID) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplate(getContext(), orgUnitID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同一组织内只能存在一个正式年度方案，传入组织，返回boolean-User defined method
     *@param unitID unitID
     *@return
     */
    public boolean hasFormallyYearScheme(String unitID) throws BOSException, EASBizException
    {
        try {
            return getController().hasFormallyYearScheme(getContext(), unitID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *模版是否分配过-User defined method
     *@param rootID rootID
     *@param orgUnitID orgUnitID
     *@return
     */
    public boolean isAreadyDispatched(String rootID, String orgUnitID) throws BOSException, EASBizException
    {
        try {
            return getController().isAreadyDispatched(getContext(), rootID, orgUnitID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否要更新模板-User defined method
     *@param orgUnit 选择要分配的组织信息
     *@return
     */
    public boolean checkIsUpdate(Map orgUnit) throws BOSException, EASBizException
    {
        try {
            return getController().checkIsUpdate(getContext(), orgUnit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *模版是否分配过-User defined method
     *@param rootId 模版根Id
     *@param orgMap 组织集合
     *@return
     */
    public Hashtable isAreadyDispatched(String rootId, Hashtable orgMap) throws BOSException, EASBizException
    {
        try {
            return getController().isAreadyDispatched(getContext(), rootId, orgMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据组织的number得到共同的方案-User defined method
     *@param orgNumbers 组织number数组
     *@return
     */
    public BgSchemeCollection getSameSchemeByOrgNumbers(String[] orgNumbers) throws BOSException, EASBizException
    {
        try {
            return getController().getSameSchemeByOrgNumbers(getContext(), orgNumbers);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行计划 -User defined method
     *@param orgUnitId 组织ID
     *@param bgSchemeId 方案ID
     *@param bgPeriodId 业务期间ID
     *@param command 命令行
     *@return
     */
    public boolean executePlan(BOSUuid orgUnitId, BOSUuid bgSchemeId, BOSUuid bgPeriodId, String command) throws BOSException, EASBizException
    {
        try {
            return getController().executePlan(getContext(), orgUnitId, bgSchemeId, bgPeriodId, command);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *复制方案-User defined method
     *@param map 集合
     */
    public void copyScheme(Map map) throws BOSException, EASBizException
    {
        try {
            getController().copyScheme(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除复制方案-User defined method
     *@param bgSchemeID bgSchemeID
     */
    public void delCopyScheme(IObjectPK bgSchemeID) throws BOSException, EASBizException
    {
        try {
            getController().delCopyScheme(getContext(), bgSchemeID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}