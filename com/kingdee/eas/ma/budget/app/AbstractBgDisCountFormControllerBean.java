package com.kingdee.eas.ma.budget.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import com.kingdee.eas.ma.budget.BgDisCountFormCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.app.ObjectBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.ArrayList;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.ma.budget.BgFormInfo;



public abstract class AbstractBgDisCountFormControllerBean extends ObjectBaseControllerBean implements BgDisCountFormController
{
    protected AbstractBgDisCountFormControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("A96AD12B");
    }

    public BgDisCountFormInfo getBgDisCountFormInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5dff2066-227f-4ddb-9118-181fb700d923"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgDisCountFormInfo retValue = (BgDisCountFormInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgDisCountFormInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public BgDisCountFormInfo getBgDisCountFormInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cb1b0bbd-e8fa-42ef-9b44-9223844a6df5"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgDisCountFormInfo retValue = (BgDisCountFormInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgDisCountFormInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public BgDisCountFormInfo getBgDisCountFormInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("25920a34-368c-4bab-901c-74f1d3745638"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgDisCountFormInfo retValue = (BgDisCountFormInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgDisCountFormInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public Map getBgData(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, Set formulaSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("01f6ae50-b1b1-4fc3-abca-9eedd4eff7bc"), new Object[]{ctx, orgUnitId, bgSchemeId, formulaSet});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getBgData(ctx, orgUnitId, bgSchemeId, formulaSet);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getBgData(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, Set formulaSet) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getTopBgData(Context ctx, BOSUuid topBgId, String subBgId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5ccacc6b-b8cc-4fc3-b837-194df97fbaed"), new Object[]{ctx, topBgId, subBgId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getTopBgData(ctx, topBgId, subBgId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getTopBgData(Context ctx, BOSUuid topBgId, String subBgId) throws BOSException, EASBizException
    {    	
        return null;
    }

    public IRowSet getSubBgData(Context ctx, BOSUuid topBgId, String subBgIds, String periodName, String itemKey, String fomula) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6458649b-259c-4d53-bf9c-b9903199b11f"), new Object[]{ctx, topBgId, subBgIds, periodName, itemKey, fomula});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IRowSet retValue = (IRowSet)_getSubBgData(ctx, topBgId, subBgIds, periodName, itemKey, fomula);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IRowSet)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getSubBgData(Context ctx, BOSUuid topBgId, String subBgIds, String periodName, String itemKey, String fomula) throws BOSException
    {    	
        return null;
    }

    public boolean hasBgDataFomular(Context ctx, BOSUuid bgFormId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("db724d54-5210-4e3a-bbb8-a66d89f00c36"), new Object[]{ctx, bgFormId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_hasBgDataFomular(ctx, bgFormId);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _hasBgDataFomular(Context ctx, BOSUuid bgFormId) throws BOSException
    {    	
        return false;
    }

    public void audit(Context ctx, BOSUuid bgCollectFormId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5ad1748a-4af8-4710-85db-88a05e038878"), new Object[]{ctx, bgCollectFormId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _audit(ctx, bgCollectFormId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _audit(Context ctx, BOSUuid bgCollectFormId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void auditStatus(Context ctx, BOSUuid bgCollectFormId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a8d024e9-80a0-4806-98f7-4ae6aa0989fa"), new Object[]{ctx, bgCollectFormId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _auditStatus(ctx, bgCollectFormId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _auditStatus(Context ctx, BOSUuid bgCollectFormId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void auditCollectState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b4102357-19a4-45bd-9a86-304758289aae"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _auditCollectState(ctx, id);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _auditCollectState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {    	
        return;
    }

    public IRowSet getAdjustMapByBgForm(Context ctx, BOSUuid bgFormId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1a1e7be5-b816-40e5-9be0-5e2a5f97788c"), new Object[]{ctx, bgFormId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IRowSet retValue = (IRowSet)_getAdjustMapByBgForm(ctx, bgFormId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IRowSet)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getAdjustMapByBgForm(Context ctx, BOSUuid bgFormId) throws BOSException
    {    	
        return null;
    }

    public void auditWF(Context ctx, BOSUuid bgCollectFormId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8501115a-38cc-4081-812d-94c6004dd84d"), new Object[]{ctx, bgCollectFormId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _auditWF(ctx, bgCollectFormId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _auditWF(Context ctx, BOSUuid bgCollectFormId) throws BOSException
    {    	
        return;
    }

    public byte[] getKdf(Context ctx, BOSUuid bgCollectId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e61f67f6-bda7-4781-b540-97316da6a339"), new Object[]{ctx, bgCollectId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            byte[] retValue = (byte[])_getKdf(ctx, bgCollectId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (byte[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected byte[] _getKdf(Context ctx, BOSUuid bgCollectId) throws BOSException, EASBizException
    {    	
        return null;
    }

    public byte[] getBgFormKdf(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c56d6d14-6cd3-4f23-b1eb-a9d5d03dc9ac"), new Object[]{ctx, bgFormId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            byte[] retValue = (byte[])_getBgFormKdf(ctx, bgFormId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (byte[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected byte[] _getBgFormKdf(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException
    {    	
        return null;
    }

    public boolean addBgFormToChildren(Context ctx, String orgId, BgFormInfo bgFormInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("74cb5d36-7bac-4417-9ff9-f01323d0ae8c"), new Object[]{ctx, orgId, bgFormInfo});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_addBgFormToChildren(ctx, orgId, bgFormInfo);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _addBgFormToChildren(Context ctx, String orgId, IObjectValue bgFormInfo) throws BOSException, EASBizException
    {    	
        return false;
    }

    public Object[] updateBgFormData(Context ctx, String bgFormID, BgFormInfo bgform, ArrayList tableData) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d0b3b9fa-28b7-411c-bd86-b42860d700b1"), new Object[]{ctx, bgFormID, bgform, tableData});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Object[] retValue = (Object[])_updateBgFormData(ctx, bgFormID, bgform, tableData);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Object[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Object[] _updateBgFormData(Context ctx, String bgFormID, IObjectValue bgform, ArrayList tableData) throws BOSException, EASBizException
    {    	
        return null;
    }

    public void updateDataForForm(Context ctx, String bgCollectId, Map dataMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b261bc9b-c083-4fa4-b884-a902309460f4"), new Object[]{ctx, bgCollectId, dataMap});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateDataForForm(ctx, bgCollectId, dataMap);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _updateDataForForm(Context ctx, String bgCollectId, Map dataMap) throws BOSException, EASBizException
    {    	
        return;
    }

    public Object[] updateDataToBgTmpData(Context ctx, String formId, Object data) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e5190f54-4c74-45f8-8908-7d1b3bebcb2c"), new Object[]{ctx, formId, data});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Object[] retValue = (Object[])_updateDataToBgTmpData(ctx, formId, data);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Object[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Object[] _updateDataToBgTmpData(Context ctx, String formId, Object data) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getOldData(Context ctx, Map bgFormId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6e2d64db-65e3-491b-af80-31bc8e265a4b"), new Object[]{ctx, bgFormId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getOldData(ctx, bgFormId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getOldData(Context ctx, Map bgFormId) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Object updateData(Context ctx, String colId, Map addNewData, Map delOldData, boolean idCol, byte[] newData) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a728d1ab-8017-42fa-99d6-b82fe9abcc4b"), new Object[]{ctx, colId, addNewData, delOldData, new Boolean(idCol), newData});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Object retValue = (Object)_updateData(ctx, colId, addNewData, delOldData, idCol, newData);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Object)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Object _updateData(Context ctx, String colId, Map addNewData, Map delOldData, boolean idCol, byte[] newData) throws BOSException, EASBizException
    {    	
        return null;
    }

    public void addNewData(Context ctx, Map bgCollIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("78e64ec6-2d4f-44ab-8807-11b9b1a7dbd0"), new Object[]{ctx, bgCollIds});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _addNewData(ctx, bgCollIds);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _addNewData(Context ctx, Map bgCollIds) throws BOSException, EASBizException
    {    	
        return;
    }

    public List getAllNegativeAdjustCheckData(Context ctx, Map adjustMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("da4641e1-ced4-4a89-a48c-8837888767a7"), new Object[]{ctx, adjustMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_getAllNegativeAdjustCheckData(ctx, adjustMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected List _getAllNegativeAdjustCheckData(Context ctx, Map adjustMap) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Object[] calculate(Context ctx, String bgFormId, String mainFormId, String bgCollectId, Map adjustMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("902b09b6-6753-4304-8c3b-9f663694d430"), new Object[]{ctx, bgFormId, mainFormId, bgCollectId, adjustMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Object[] retValue = (Object[])_calculate(ctx, bgFormId, mainFormId, bgCollectId, adjustMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Object[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Object[] _calculate(Context ctx, String bgFormId, String mainFormId, String bgCollectId, Map adjustMap) throws BOSException, EASBizException
    {    	
        return null;
    }

    public void updateAdjustData(Context ctx, String id, Map adjustMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dca8c15a-2813-46d9-a738-6dcf49677c6a"), new Object[]{ctx, id, adjustMap});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateAdjustData(ctx, id, adjustMap);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _updateAdjustData(Context ctx, String id, Map adjustMap) throws BOSException, EASBizException
    {    	
        return;
    }

    public void saveKdf(Context ctx, String id, byte[] data) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1b3bf499-f634-47d6-b383-f71bb26c687b"), new Object[]{ctx, id, data});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _saveKdf(ctx, id, data);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _saveKdf(Context ctx, String id, byte[] data) throws BOSException, EASBizException
    {    	
        return;
    }

    public List getAllNegativeAdjustCheckDataForView(Context ctx, Map adjustMap, BgFormInfo bgFormInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("abe476b1-1b97-4143-9880-4ddacbdbe748"), new Object[]{ctx, adjustMap, bgFormInfo});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_getAllNegativeAdjustCheckDataForView(ctx, adjustMap, bgFormInfo);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected List _getAllNegativeAdjustCheckDataForView(Context ctx, Map adjustMap, BgFormInfo bgFormInfo) throws BOSException, EASBizException
    {    	
        return null;
    }

    public List getAllNegativeAdjustCheckDataForView(Context ctx, List _bgFormIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("097f4246-e486-4c5c-8283-d8141ea1490f"), new Object[]{ctx, _bgFormIds});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_getAllNegativeAdjustCheckDataForView(ctx, _bgFormIds);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected List _getAllNegativeAdjustCheckDataForView(Context ctx, List _bgFormIds) throws BOSException, EASBizException
    {    	
        return null;
    }

    public BgDisCountFormCollection getBgDisCountFormCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b6e1ec93-8006-4def-afd9-58bdde96fc44"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgDisCountFormCollection retValue = (BgDisCountFormCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgDisCountFormCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public BgDisCountFormCollection getBgDisCountFormCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("19d0fbe6-f3a3-43ba-a9bf-90890ad282c6"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgDisCountFormCollection retValue = (BgDisCountFormCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgDisCountFormCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public BgDisCountFormCollection getBgDisCountFormCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c4e23723-3ffd-4a5c-adcd-d0f421a7b249"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgDisCountFormCollection retValue = (BgDisCountFormCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgDisCountFormCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgDisCountFormCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgDisCountFormCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgDisCountFormCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getBgDisCountFormCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getBgDisCountFormCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getBgDisCountFormCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}