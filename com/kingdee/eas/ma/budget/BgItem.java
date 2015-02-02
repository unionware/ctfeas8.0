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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param oql 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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
     *删除全部-User defined method
     *@param bgItemGroupId 项目分组ID
     *@param orgUnitId 组织ID
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
     *isCreator是否为创建者-User defined method
     *@param creatorID 现在的操作员
     *@param groupID 项目分组ID
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
     *取得以某个字符开头的最大编码-User defined method
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
     *updateErrorData修复错误数据-User defined method
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
     *更新预算项目的引用表数据-User defined method
     *@param bgItemInfo 项目组合信息
     *@param isInit 是否是初始化数据（用于更新历史数据）
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
     *删除项目组合的上级项目-User defined method
     *@param bgItemInfo 预算项目组合信息
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
     *删除项目组合的上级项目-User defined method
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
     *更新预算项目上级项目的实际数值-User defined method
     *@param orgUnitID 组织ID
     *@param bgSchemeID 预算方案ID
     *@param formula 项目公式
     *@param value 值
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
     *更新预算项目上级项目的实际数值-User defined method
     *@param orgUnitID 组织ID
     *@param bgSchemeID 预算方案ID
     *@param formula 项目公式
     *@param value 值
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
     *更新预算项目上级项目的实际数值-User defined method
     *@param orgUnitID 组织ID
     *@param bgSchemeID 预算方案ID
     *@param formula 项目公式
     *@param value 值
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
     *bgItemSupriorMaintenance维护上级项目-User defined method
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
     *导入预算项目集合-User defined method
     *@param bgItemGroupId 预算分组ID
     *@param bgItemCollection 预算项目集合
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
     *检查预算项目集合是否重复-User defined method
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
     *禁用预算项目-User defined method
     *@param id 预算项目ID
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
     *启用预算项目-User defined method
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
     *是否是子节点-User defined method
     *@param orgUnitLevel 组织级次
     *@param itemGroupID 项目分组ID
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
     *更新预算项目组织-User defined method
     *@param itemMap 项目集合
     *@param itemLongNumberMap 预算项目长编码集合
     *@param itemTargetOrgMap 预算项目目标组织长编码集合
     *@param itemNameMap 预算项目名称集合
     *@param itemNumberMap 预算项目编码集合
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
     *合同正式号，更新名称-User defined method
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
     *updateBgItemSuperiorCtrlRecord更新上级项目控制记录-User defined method
     *@param orgUnitID 组织
     *@param bgSchemeID 预算方案
     *@param formulaList 项目公式
     *@param record 控制结果
     *@param category 允许为空，默认为抛异常
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
     *updateBgItemSuperiorCtrlRecord更新上级项目控制记录-User defined method
     *@param orgUnitID 组织
     *@param bgSchemeID 预算方案
     *@param formula 项目公式
     *@param record 控制结果
     *@param category 允许为空，默认为抛异常
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
     *预算项目批量停用-User defined method
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
     *预算项目批量启用-User defined method
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
     *getBatchBgItemName批量取项目-User defined method
     *@param orgUnitId orgUnitId
     *@param bgItemGroupId 预算分组ID
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
     *getBatchBgItemName批量取项目-User defined method
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
     *getBgItemName取项目-User defined method
     *@param orgUnitId 组织ID
     *@param number 预算项目编码
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
     *删除预算项目辅助记录-User defined method
     *@param orgUnitId 组织ID
     *@param bgItemId 预算项目ID
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
     *删除预算项目辅助记录-User defined method
     *@param orgUnitIds 组织ID
     *@param bgItemId 预算项目ID
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
     *项目引入保存-User defined method
     *@param itemCol 项目Col
     *@param orgUnitId orgUnitId
     *@param importSource 导入数据来源（如系统或Excel导入）
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
     *更新上级组织的上级项目控制记录,在定位的组织按照项目汇总,然后记录汇总记录明细-User defined method
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
     *取得所有上级项目控制数据-User defined method
     *@param ctrlResultCol 控制结果集合
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
     *更新资金性质-User defined method
     *@param headId 项目ID
     *@param fpDirection 资金性质
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
     *更新总计项公式-User defined method
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
     *按总计项公式获取子项目列表-User defined method
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
     *批量更新总计项公式-User defined method
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
     *检查公式属性是否更新-User defined method
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
     *更新抵销类型-User defined method
     *@param headId 项目ID
     *@param elimType 抵销类型
     *@param dataRela 数据关系
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
     *判断被哪种单据引用-User defined method
     *@param number 预算项目编码
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
     *@param curOrgUnitId 如果在表里面会取表的所属组织
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
     *获取上级预算项目-User defined method
     *@param orgUnitID 组织ID
     *@param formula 项目公式
     *@param reFormulaList 返回项目公式集合
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