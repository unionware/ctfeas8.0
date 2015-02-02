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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *ȡֵ-User defined method
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
     *ȡ����-System defined method
     *@param oql ȡ����
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
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
     *��ȡ��ǰ����������ģ��-User defined method
     *@param id ��ǰԤ�㷽��Id
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
     *ִ�з���-User defined method
     *@param bgSchemePK ����Id
     *@param orgUnitId ��֯Id
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
     *����ִ��ϵ�з���-User defined method
     *@param bgSchemePK Ԥ�㷽��PK
     *@param orgUnitId ��֯ID
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
     *������ִ��ϵ�з���-User defined method
     *@param bgSchemeId Ԥ�㷽��ID
     *@param orgUnitId ��֯ID
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
     *Ԥ�㷽���Ƿ�Ԥ�������-User defined method
     *@param schemeId ����Id
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
     *�Ƿ����¼�����-User defined method
     *@param schemeId Ԥ�㷽��Id
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
     *������֯�õ�Ԥ�㷽��-User defined method
     *@param orgUnitId ��֯ID
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
     *�жϱ������ڴ���֯���Ƿ�Ԥ������ù�-User defined method
     *@param schemeId ����id
     *@param ortUnitId ��֯id
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
     *��ִ�з���-User defined method
     *@param bgSchemePK ����id
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
     *���¼���֯������Ϣ�����ڷ�������󣬸���������¼���֯����֪ͨ��Ϣ-User defined method
     *@param ids ��������¼���֯����id�ַ���
     *@param orgName �ϼ���֯����
     *@param schemeName ��������
     *@param userNumber �û�����
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
     *�ṩһ��Ԥ���Id��һ����֯���ϣ���ȡԤ���������Ԥ�㷽������֯�����еĺ���-User defined method
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
     *�ṩһ��Ԥ���Id��һ����֯���ϣ���ȡԤ���������Ԥ�㷽������֯�����еĺ���-User defined method
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
     *�жϸ���ģ��ĸ��Ƿ񱻸��������ĸ�����-User defined method
     *@param templateId ģ��id
     *@param schemeId ����id
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
     *�ж�ָ��ģ���Ƿ�����ָ����������-User defined method
     *@param templateId ģ��Id
     *@param schemeId ����Id
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
     *�������ģ��ʱʹ��-User defined method
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
     *��ô���֯�µ�����ģ��-User defined method
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
     *ͬһ��֯��ֻ�ܴ���һ����ʽ��ȷ�����������֯������boolean-User defined method
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
     *ģ���Ƿ�����-User defined method
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
     *�Ƿ�Ҫ����ģ��-User defined method
     *@param orgUnit ѡ��Ҫ�������֯��Ϣ
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
     *ģ���Ƿ�����-User defined method
     *@param rootId ģ���Id
     *@param orgMap ��֯����
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
     *������֯��number�õ���ͬ�ķ���-User defined method
     *@param orgNumbers ��֯number����
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
     *ִ�мƻ� -User defined method
     *@param orgUnitId ��֯ID
     *@param bgSchemeId ����ID
     *@param bgPeriodId ҵ���ڼ�ID
     *@param command ������
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
     *���Ʒ���-User defined method
     *@param map ����
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
     *ɾ�����Ʒ���-User defined method
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