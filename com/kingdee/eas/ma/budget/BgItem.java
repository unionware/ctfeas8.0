package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.ITreeBase;
import java.util.Map;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.TreeBase;
import java.util.Vector;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.util.Set;
import java.util.ArrayList;
import com.kingdee.eas.ma.nbudget.BgElimTypeEnum;
import com.kingdee.bos.dao.IObjectValue;
import java.util.List;
import java.util.Hashtable;
import java.lang.String;
import com.kingdee.eas.ma.nbudget.BgDataRelationEnum;
import com.kingdee.eas.ma.budget.app.*;
import java.math.BigDecimal;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public class BgItem extends TreeBase implements IBgItem
{
    public BgItem()
    {
        super();
        registerInterface(IBgItem.class, this);
    }
    public BgItem(Context ctx)
    {
        super(ctx);
        registerInterface(IBgItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FF67C4B9");
    }
    private BgItemController getController() throws BOSException
    {
        return (BgItemController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BgItemInfo getBgItemInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBgItemInfo(getContext(), pk);
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
    public BgItemInfo getBgItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBgItemInfo(getContext(), pk, selector);
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
    public BgItemInfo getBgItemInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBgItemInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BgItemCollection getBgItemCollection() throws BOSException
    {
        try {
            return getController().getBgItemCollection(getContext());
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
    public BgItemCollection getBgItemCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBgItemCollection(getContext(), view);
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
    public BgItemCollection getBgItemCollection(String oql) throws BOSException
    {
        try {
            return getController().getBgItemCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��ȫ��-User defined method
     *@param bgItemGroupId ��Ŀ����ID
     *@param orgUnitId ��֯ID
     */
    public void removeAll(BOSUuid bgItemGroupId, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            getController().removeAll(getContext(), bgItemGroupId, orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *isCreator�Ƿ�Ϊ������-User defined method
     *@param creatorID ���ڵĲ���Ա
     *@param groupID ��Ŀ����ID
     *@return
     */
    public boolean isCreator(String creatorID, String groupID) throws BOSException, EASBizException
    {
        try {
            return getController().isCreator(getContext(), creatorID, groupID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����ĳ���ַ���ͷ��������-User defined method
     *@param startStr startStr
     *@return
     */
    public String getMaxNumber(String startStr) throws BOSException, EASBizException
    {
        try {
            return getController().getMaxNumber(getContext(), startStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateErrorData�޸���������-User defined method
     *@param itemGroupNumberVec itemGroupNumberVec
     */
    public void updateErrorData(Vector itemGroupNumberVec) throws BOSException, EASBizException
    {
        try {
            getController().updateErrorData(getContext(), itemGroupNumberVec);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ�����ñ�����-User defined method
     *@param bgItemInfo ��Ŀ�����Ϣ
     *@param isInit �Ƿ��ǳ�ʼ�����ݣ����ڸ�����ʷ���ݣ�
     */
    public void updateBgItemSuperior(Map bgItemInfo, boolean isInit) throws BOSException, EASBizException
    {
        try {
            getController().updateBgItemSuperior(getContext(), bgItemInfo, isInit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����Ŀ��ϵ��ϼ���Ŀ-User defined method
     *@param bgItemInfo Ԥ����Ŀ�����Ϣ
     */
    public void removeBgItemSuperior(Map bgItemInfo) throws BOSException, EASBizException
    {
        try {
            getController().removeBgItemSuperior(getContext(), bgItemInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����Ŀ��ϵ��ϼ���Ŀ-User defined method
     */
    public void removeBgItemSuperior() throws BOSException, EASBizException
    {
        try {
            getController().removeBgItemSuperior(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ�ϼ���Ŀ��ʵ����ֵ-User defined method
     *@param orgUnitID ��֯ID
     *@param bgSchemeID Ԥ�㷽��ID
     *@param formula ��Ŀ��ʽ
     *@param value ֵ
     */
    public void updateBgItemSuperiorValue(BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BigDecimal value) throws BOSException, EASBizException
    {
        try {
            getController().updateBgItemSuperiorValue(getContext(), orgUnitID, bgSchemeID, formula, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ�ϼ���Ŀ��ʵ����ֵ-User defined method
     *@param orgUnitID ��֯ID
     *@param bgSchemeID Ԥ�㷽��ID
     *@param formula ��Ŀ��ʽ
     *@param value ֵ
     */
    public void updateBgItemSuperiorValue(BOSUuid orgUnitID, BOSUuid bgSchemeID, List formula, BigDecimal value) throws BOSException, EASBizException
    {
        try {
            getController().updateBgItemSuperiorValue(getContext(), orgUnitID, bgSchemeID, formula, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ�ϼ���Ŀ��ʵ����ֵ-User defined method
     *@param orgUnitID ��֯ID
     *@param bgSchemeID Ԥ�㷽��ID
     *@param formula ��Ŀ��ʽ
     *@param value ֵ
     */
    public void updateBgItemSuperiorValue(BOSUuid orgUnitID, BOSUuid bgSchemeID, List formula, List value) throws BOSException, EASBizException
    {
        try {
            getController().updateBgItemSuperiorValue(getContext(), orgUnitID, bgSchemeID, formula, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *bgItemSupriorMaintenanceά���ϼ���Ŀ-User defined method
     *@return
     */
    public boolean bgItemSupriorMaintenance() throws BOSException, EASBizException
    {
        try {
            return getController().bgItemSupriorMaintenance(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ����-User defined method
     *@param bgItemGroupId Ԥ�����ID
     *@param bgItemCollection Ԥ����Ŀ����
     *@return
     */
    public boolean importBgItemCollection(BOSUuid bgItemGroupId, BgItemCollection bgItemCollection) throws BOSException, EASBizException
    {
        try {
            return getController().importBgItemCollection(getContext(), bgItemGroupId, bgItemCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���Ԥ����Ŀ�����Ƿ��ظ�-User defined method
     *@param bgItemGroupId bgItemGroupId
     *@param bgItemCollection bgItemCollection
     *@return
     */
    public boolean checkBgItemCollection(BOSUuid bgItemGroupId, BgItemCollection bgItemCollection) throws BOSException, EASBizException
    {
        try {
            return getController().checkBgItemCollection(getContext(), bgItemGroupId, bgItemCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ-User defined method
     *@param id Ԥ����ĿID
     */
    public void setItemInefficacy(String id) throws BOSException, EASBizException
    {
        try {
            getController().setItemInefficacy(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ-User defined method
     *@param id id
     */
    public void setItemEfficacy(String id) throws BOSException, EASBizException
    {
        try {
            getController().setItemEfficacy(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ����ӽڵ�-User defined method
     *@param orgUnitLevel ��֯����
     *@param itemGroupID ��Ŀ����ID
     *@return
     */
    public boolean isJunior(int orgUnitLevel, String itemGroupID) throws BOSException, EASBizException
    {
        try {
            return getController().isJunior(getContext(), orgUnitLevel, itemGroupID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ����Ŀ��֯-User defined method
     *@param itemMap ��Ŀ����
     *@param itemLongNumberMap Ԥ����Ŀ�����뼯��
     *@param itemTargetOrgMap Ԥ����ĿĿ����֯�����뼯��
     *@param itemNameMap Ԥ����Ŀ���Ƽ���
     *@param itemNumberMap Ԥ����Ŀ���뼯��
     */
    public void modifyBgItemOrg(Hashtable itemMap, Hashtable itemLongNumberMap, Hashtable itemTargetOrgMap, Hashtable itemNameMap, Hashtable itemNumberMap) throws BOSException, EASBizException
    {
        try {
            getController().modifyBgItemOrg(getContext(), itemMap, itemLongNumberMap, itemTargetOrgMap, itemNameMap, itemNumberMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ͬ��ʽ�ţ���������-User defined method
     *@param info info
     *@return
     */
    public boolean updateName(BgItemInfo info) throws BOSException
    {
        try {
            return getController().updateName(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateBgItemSuperiorCtrlRecord�����ϼ���Ŀ���Ƽ�¼-User defined method
     *@param orgUnitID ��֯
     *@param bgSchemeID Ԥ�㷽��
     *@param formulaList ��Ŀ��ʽ
     *@param record ���ƽ��
     *@param category ����Ϊ�գ�Ĭ��Ϊ���쳣
     */
    public void updateBgItemSuperiorCtrlRecord(BOSUuid orgUnitID, BOSUuid bgSchemeID, List formulaList, BgCtrlResultInfo record, BudgetRequestCategory category) throws BOSException, EASBizException
    {
        try {
            getController().updateBgItemSuperiorCtrlRecord(getContext(), orgUnitID, bgSchemeID, formulaList, record, category);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateBgItemSuperiorCtrlRecord�����ϼ���Ŀ���Ƽ�¼-User defined method
     *@param orgUnitID ��֯
     *@param bgSchemeID Ԥ�㷽��
     *@param formula ��Ŀ��ʽ
     *@param record ���ƽ��
     *@param category ����Ϊ�գ�Ĭ��Ϊ���쳣
     */
    public void updateBgItemSuperiorCtrlRecord(BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BgCtrlResultInfo record, BudgetRequestCategory category) throws BOSException, EASBizException
    {
        try {
            getController().updateBgItemSuperiorCtrlRecord(getContext(), orgUnitID, bgSchemeID, formula, record, category);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ԥ����Ŀ����ͣ��-User defined method
     *@param bgItems bgItems
     */
    public void batchSetItemInefficacy(List bgItems) throws BOSException, EASBizException
    {
        try {
            getController().batchSetItemInefficacy(getContext(), bgItems);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ԥ����Ŀ��������-User defined method
     *@param bgItems bgItems
     */
    public void batchSetItemEfficacy(List bgItems) throws BOSException, EASBizException
    {
        try {
            getController().batchSetItemEfficacy(getContext(), bgItems);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getBatchBgItemName����ȡ��Ŀ-User defined method
     *@param orgUnitId orgUnitId
     *@param bgItemGroupId Ԥ�����ID
     *@return
     */
    public Map getBatchBgItemName(BOSUuid orgUnitId, BOSUuid bgItemGroupId) throws BOSException, EASBizException
    {
        try {
            return getController().getBatchBgItemName(getContext(), orgUnitId, bgItemGroupId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getBatchBgItemName����ȡ��Ŀ-User defined method
     *@param orgUnitId orgUnitId
     *@param parentId parentId
     *@param level level
     *@return
     */
    public Map getBatchBgItemName(BOSUuid orgUnitId, BOSUuid parentId, int level) throws BOSException, EASBizException
    {
        try {
            return getController().getBatchBgItemName(getContext(), orgUnitId, parentId, level);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getBgItemNameȡ��Ŀ-User defined method
     *@param orgUnitId ��֯ID
     *@param number Ԥ����Ŀ����
     *@return
     */
    public IObjectValue getBgItemName(BOSUuid orgUnitId, String number) throws BOSException, EASBizException
    {
        try {
            return getController().getBgItemName(getContext(), orgUnitId, number);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��Ԥ����Ŀ������¼-User defined method
     *@param orgUnitId ��֯ID
     *@param bgItemId Ԥ����ĿID
     */
    public void deleteBgItemName(BOSUuid orgUnitId, BOSUuid bgItemId) throws BOSException, EASBizException
    {
        try {
            getController().deleteBgItemName(getContext(), orgUnitId, bgItemId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��Ԥ����Ŀ������¼-User defined method
     *@param orgUnitIds ��֯ID
     *@param bgItemId Ԥ����ĿID
     */
    public void deleteBgItemName(List orgUnitIds, BOSUuid bgItemId) throws BOSException, EASBizException
    {
        try {
            getController().deleteBgItemName(getContext(), orgUnitIds, bgItemId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ŀ���뱣��-User defined method
     *@param itemCol ��ĿCol
     *@param orgUnitId orgUnitId
     *@param importSource ����������Դ����ϵͳ��Excel���룩
     *@return
     */
    public Map importItems(BgItemCollection itemCol, String orgUnitId, String importSource) throws BOSException, EASBizException
    {
        try {
            return getController().importItems(getContext(), itemCol, orgUnitId, importSource);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ϼ���֯���ϼ���Ŀ���Ƽ�¼,�ڶ�λ����֯������Ŀ����,Ȼ���¼���ܼ�¼��ϸ-User defined method
     *@param orgUnitID orgUnitID
     *@param bgSchemeID bgSchemeID
     *@param formula formula
     *@param resultInfo resultInfo
     */
    public void sumBgItemSuperiorCtrlRecord(BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BgCtrlResultInfo resultInfo) throws BOSException, EASBizException
    {
        try {
            getController().sumBgItemSuperiorCtrlRecord(getContext(), orgUnitID, bgSchemeID, formula, resultInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ�������ϼ���Ŀ��������-User defined method
     *@param ctrlResultCol ���ƽ������
     *@return
     */
    public BgCtrlResultCollection getSuperiorItemCtrlData(BgCtrlResultCollection ctrlResultCol) throws BOSException, EASBizException
    {
        try {
            return getController().getSuperiorItemCtrlData(getContext(), ctrlResultCol);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ʽ�����-User defined method
     *@param headId ��ĿID
     *@param fpDirection �ʽ�����
     *@return
     */
    public String updateFpDirection(ArrayList headId, FpCashDirectionEnum fpDirection) throws BOSException, EASBizException
    {
        try {
            return getController().updateFpDirection(getContext(), headId, fpDirection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ܼ��ʽ-User defined method
     *@param model model
     *@return
     */
    public String updateSumFormula(IObjectValue model) throws BOSException, EASBizException
    {
        try {
            return getController().updateSumFormula(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ܼ��ʽ��ȡ����Ŀ�б�-User defined method
     *@param sumItemFormula sumItemFormula
     *@return
     */
    public IObjectCollection getItemListBySumItemFormula(String sumItemFormula) throws BOSException, EASBizException
    {
        try {
            return getController().getItemListBySumItemFormula(getContext(), sumItemFormula);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������ܼ��ʽ-User defined method
     *@param updateSumFormulas updateSumFormulas
     */
    public void batchUpdateSumFormula(List updateSumFormulas) throws BOSException, EASBizException
    {
        try {
            getController().batchUpdateSumFormula(getContext(), updateSumFormulas);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��鹫ʽ�����Ƿ����-User defined method
     *@param pk pk
     *@param newValue newValue
     *@return
     */
    public Map checkExpIsUpdate(IObjectPK pk, IObjectValue newValue) throws BOSException, EASBizException
    {
        try {
            return getController().checkExpIsUpdate(getContext(), pk, newValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���µ�������-User defined method
     *@param headId ��ĿID
     *@param elimType ��������
     *@param dataRela ���ݹ�ϵ
     *@return
     */
    public String updateElimType(ArrayList headId, BgElimTypeEnum elimType, BgDataRelationEnum dataRela) throws BOSException, EASBizException
    {
        try {
            return getController().updateElimType(getContext(), headId, elimType, dataRela);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�жϱ����ֵ�������-User defined method
     *@param number Ԥ����Ŀ����
     *@param orgUnitId orgUnitId
     *@return
     */
    public String judgeRef(String number, String orgUnitId) throws BOSException, EASBizException
    {
        try {
            return getController().judgeRef(getContext(), number, orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-User defined method
     *@param arrayPK arrayPK
     *@param curOrgUnitId ����ڱ������ȡ���������֯
     */
    public void delete(IObjectPK[] arrayPK, String curOrgUnitId) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK, curOrgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�ϼ�Ԥ����Ŀ-User defined method
     *@param orgUnitID ��֯ID
     *@param formula ��Ŀ��ʽ
     *@param reFormulaList ������Ŀ��ʽ����
     */
    public void getSuperiorItems(BOSUuid orgUnitID, String formula, Set reFormulaList) throws BOSException
    {
        try {
            getController().getSuperiorItems(getContext(), orgUnitID, formula, reFormulaList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}