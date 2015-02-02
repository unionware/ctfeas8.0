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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *���Ԥ����-User defined method
     *@param orgUnitId ��֯ID
     *@param bgSchemeId ����ID
     *@param formulaSet ��Ŀ��ʽ
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
     *��ȡ�ϼ�Ԥ�������ݼ��¼�Ԥ���Ļ�������-User defined method
     *@param topBgId �ϼ�Ԥ����ID
     *@param subBgId �¼�Ԥ����ID����
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
     *��ȡ�¼�Ԥ��������-User defined method
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
     *Ԥ������Ƿ����Ԥ������ʽ-User defined method
     *@param bgFormId Ԥ���ID
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
     *����-User defined method
     *@param bgCollectFormId ����ID
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
     *�����е���-User defined method
     *@param bgCollectFormId ����ID
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
     *�������״̬-User defined method
     *@param id ��������
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
     *ͨ��Ԥ����ȡ����������-User defined method
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
     *����ǰ����-User defined method
     *@param bgCollectFormId ����ID
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
     *��ȡ���Ķ���������-User defined method
     *@param bgCollectId Ԥ���������
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
     *��ȡ����������-User defined method
     *@param bgFormId Ԥ�������
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
     *�����¼�������-User defined method
     *@param orgId orgId
     *@param bgFormInfo Ԥ���
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
     *�ؼ���Ԥ�������byte[]-User defined method
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
     *updateDataForForm���±�����-User defined method
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
     *�ؼ����޸���ʱ����-User defined method
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
     *����Ԥ���id����ȡ�ù���Ԥ����bgdata-User defined method
     *@param bgFormId Ԥ���id����
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
     *��Ԥ������ɾ������������Ŀ��ʽ-User defined method
     *@param colId ����id
     *@param addNewData ������Ŀ
     *@param delOldData ɾ����Ŀ
     *@param idCol �Ƿ�������
     *@param newData ��������
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
     *�����������������-User defined method
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
     *��ȡ���������Ԥ�����Ϊ��������������-User defined method
     *@param adjustMap ��������Map
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
     *����ؼ��������Ż�-User defined method
     *@param bgFormId ��ǰ�����Ԥ���id
     *@param mainFormId ���������Ӧ��Ԥ���id
     *@param bgCollectId ����id
     *@param adjustMap �����¼map
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
     *���µ�������-User defined method
     *@param id ����id
     *@param adjustMap ��������map
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
     *�������������-User defined method
     *@param id ����id
     *@param data ������
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
     *��ȡ���������Ԥ�����Ϊ��������������(������ͼ)-User defined method
     *@param adjustMap �����¼Map<��֯,Map<��Ŀ��ʽ,BgAdjustFormDiversityData>>
     *@param bgFormInfo Ԥ���(ȡ��ģ�壬���������ڼ䣬�ұ�)
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
     *��ȡ���������Ԥ�����Ϊ��������������(������ͼ)-User defined method
     *@param _bgFormIds ��Ҫ���������ı�ID����
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