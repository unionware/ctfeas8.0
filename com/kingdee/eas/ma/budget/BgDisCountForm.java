package com.kingdee.eas.ma.budget;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.ma.budget.app.*;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.IObjectBase;

public class BgDisCountForm extends ObjectBase implements IBgDisCountForm
{
    public BgDisCountForm()
    {
        super();
        registerInterface(IBgDisCountForm.class, this);
    }
    public BgDisCountForm(Context ctx)
    {
        super(ctx);
        registerInterface(IBgDisCountForm.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A96AD12B");
    }
    private BgDisCountFormController getController() throws BOSException
    {
        return (BgDisCountFormController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BgDisCountFormInfo getBgDisCountFormInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBgDisCountFormInfo(getContext(), pk);
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
    public BgDisCountFormInfo getBgDisCountFormInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBgDisCountFormInfo(getContext(), pk, selector);
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
    public BgDisCountFormInfo getBgDisCountFormInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBgDisCountFormInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得预算数-User defined method
     *@param orgUnitId 组织ID
     *@param bgSchemeId 方案ID
     *@param formulaSet 项目公式
     *@return
     */
    public Map getBgData(BOSUuid orgUnitId, BOSUuid bgSchemeId, Set formulaSet) throws BOSException, EASBizException
    {
        try {
            return getController().getBgData(getContext(), orgUnitId, bgSchemeId, formulaSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取上级预算表的数据及下级预算表的汇总数据-User defined method
     *@param topBgId 上级预算表的ID
     *@param subBgId 下级预算表的ID集合
     *@return
     */
    public Map getTopBgData(BOSUuid topBgId, String subBgId) throws BOSException, EASBizException
    {
        try {
            return getController().getTopBgData(getContext(), topBgId, subBgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取下级预算表的数据-User defined method
     *@param topBgId topBgId
     *@param subBgIds subBgIds
     *@param periodName periodName
     *@param itemKey itemKey
     *@param fomula fomula
     *@return
     */
    public IRowSet getSubBgData(BOSUuid topBgId, String subBgIds, String periodName, String itemKey, String fomula) throws BOSException
    {
        try {
            return getController().getSubBgData(getContext(), topBgId, subBgIds, periodName, itemKey, fomula);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预算表中是否存在预算数公式-User defined method
     *@param bgFormId 预算表ID
     *@return
     */
    public boolean hasBgDataFomular(BOSUuid bgFormId) throws BOSException
    {
        try {
            return getController().hasBgDataFomular(getContext(), bgFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param bgCollectFormId 汇编表ID
     */
    public void audit(BOSUuid bgCollectFormId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), bgCollectFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批中调用-User defined method
     *@param bgCollectFormId 汇编表ID
     */
    public void auditStatus(BOSUuid bgCollectFormId) throws BOSException, EASBizException
    {
        try {
            getController().auditStatus(getContext(), bgCollectFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *至汇编中状态-User defined method
     *@param id 汇编表主键
     */
    public void auditCollectState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().auditCollectState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过预算表获取汇编调整数据-User defined method
     *@param bgFormId bgFormId
     *@return
     */
    public IRowSet getAdjustMapByBgForm(BOSUuid bgFormId) throws BOSException
    {
        try {
            return getController().getAdjustMapByBgForm(getContext(), bgFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批前调用-User defined method
     *@param bgCollectFormId 汇编表ID
     */
    public void auditWF(BOSUuid bgCollectFormId) throws BOSException
    {
        try {
            getController().auditWF(getContext(), bgCollectFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取汇编的二进制数据-User defined method
     *@param bgCollectId 预算汇编表主键
     *@return
     */
    public byte[] getKdf(BOSUuid bgCollectId) throws BOSException, EASBizException
    {
        try {
            return getController().getKdf(getContext(), bgCollectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取二进制数据-User defined method
     *@param bgFormId 预算表主键
     *@return
     */
    public byte[] getBgFormKdf(BOSUuid bgFormId) throws BOSException, EASBizException
    {
        try {
            return getController().getBgFormKdf(getContext(), bgFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *增加下级表并审批-User defined method
     *@param orgId orgId
     *@param bgFormInfo 预算表
     *@return
     */
    public boolean addBgFormToChildren(String orgId, BgFormInfo bgFormInfo) throws BOSException, EASBizException
    {
        try {
            return getController().addBgFormToChildren(getContext(), orgId, bgFormInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *重计算预算表，返回byte[]-User defined method
     *@param bgFormID bgFormID
     *@param bgform bgform
     *@param tableData tableData
     *@return
     */
    public Object[] updateBgFormData(String bgFormID, BgFormInfo bgform, ArrayList tableData) throws BOSException, EASBizException
    {
        try {
            return getController().updateBgFormData(getContext(), bgFormID, bgform, tableData);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateDataForForm更新表数据-User defined method
     *@param bgCollectId bgCollectId
     *@param dataMap dataMap
     */
    public void updateDataForForm(String bgCollectId, Map dataMap) throws BOSException, EASBizException
    {
        try {
            getController().updateDataForForm(getContext(), bgCollectId, dataMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *重计算修改临时数据-User defined method
     *@param formId formId
     *@param data data
     *@return
     */
    public Object[] updateDataToBgTmpData(String formId, Object data) throws BOSException, EASBizException
    {
        try {
            return getController().updateDataToBgTmpData(getContext(), formId, data);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据预算表id集合取得关联预算表的bgdata-User defined method
     *@param bgFormId 预算表id集合
     *@return
     */
    public Map getOldData(Map bgFormId) throws BOSException, EASBizException
    {
        try {
            return getController().getOldData(getContext(), bgFormId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *打开预算表后处理删除和新增的项目公式-User defined method
     *@param colId 汇编表id
     *@param addNewData 新增项目
     *@param delOldData 删除项目
     *@param idCol 是否是主表
     *@param newData 最新数据
     *@return
     */
    public Object updateData(String colId, Map addNewData, Map delOldData, boolean idCol, byte[] newData) throws BOSException, EASBizException
    {
        try {
            return getController().updateData(getContext(), colId, addNewData, delOldData, idCol, newData);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增汇编表二进制数据-User defined method
     *@param bgCollIds bgCollIds
     */
    public void addNewData(Map bgCollIds) throws BOSException, EASBizException
    {
        try {
            getController().addNewData(getContext(), bgCollIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取调整后可用预算余额为负数的所有数据-User defined method
     *@param adjustMap 调整差异Map
     *@return
     */
    public List getAllNegativeAdjustCheckData(Map adjustMap) throws BOSException, EASBizException
    {
        try {
            return getController().getAllNegativeAdjustCheckData(getContext(), adjustMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *汇编重计算性能优化-User defined method
     *@param bgFormId 当前计算的预算表id
     *@param mainFormId 汇编表主表对应的预算表id
     *@param bgCollectId 汇编表id
     *@param adjustMap 差异记录map
     *@return
     */
    public Object[] calculate(String bgFormId, String mainFormId, String bgCollectId, Map adjustMap) throws BOSException, EASBizException
    {
        try {
            return getController().calculate(getContext(), bgFormId, mainFormId, bgCollectId, adjustMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新调整数据-User defined method
     *@param id 主键id
     *@param adjustMap 差异数据map
     */
    public void updateAdjustData(String id, Map adjustMap) throws BOSException, EASBizException
    {
        try {
            getController().updateAdjustData(getContext(), id, adjustMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存二进制数据-User defined method
     *@param id 主键id
     *@param data 二进制
     */
    public void saveKdf(String id, byte[] data) throws BOSException, EASBizException
    {
        try {
            getController().saveKdf(getContext(), id, data);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取调整后可用预算余额为负数的所有数据(用于视图)-User defined method
     *@param adjustMap 差异记录Map<组织,Map<项目公式,BgAdjustFormDiversityData>>
     *@param bgFormInfo 预算表(取根模板，根方案，期间，币别)
     *@return
     */
    public List getAllNegativeAdjustCheckDataForView(Map adjustMap, BgFormInfo bgFormInfo) throws BOSException, EASBizException
    {
        try {
            return getController().getAllNegativeAdjustCheckDataForView(getContext(), adjustMap, bgFormInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取调整后可用预算余额为负数的所有数据(用于视图)-User defined method
     *@param _bgFormIds 需要做调整检查的表ID集合
     *@return
     */
    public List getAllNegativeAdjustCheckDataForView(List _bgFormIds) throws BOSException, EASBizException
    {
        try {
            return getController().getAllNegativeAdjustCheckDataForView(getContext(), _bgFormIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}