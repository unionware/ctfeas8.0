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

import java.util.Map;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.ma.budget.BgItemInfo;
import java.util.Vector;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.ma.budget.BudgetRequestCategory;
import java.util.Set;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import java.util.ArrayList;
import com.kingdee.eas.ma.nbudget.BgElimTypeEnum;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.util.List;
import java.util.Hashtable;
import java.lang.String;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.nbudget.BgDataRelationEnum;
import com.kingdee.eas.ma.budget.FpCashDirectionEnum;
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import java.math.BigDecimal;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;



public abstract class AbstractBgItemControllerBean extends TreeBaseControllerBean implements BgItemController
{
    protected AbstractBgItemControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("FF67C4B9");
    }

    public BgItemInfo getBgItemInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f86ae8f0-0107-1000-e000-0003c0a81291"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgItemInfo retValue = (BgItemInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgItemInfo)svcCtx.getMethodReturnValue();
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

    public BgItemInfo getBgItemInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f86ae8f0-0107-1000-e000-0004c0a81291"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgItemInfo retValue = (BgItemInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgItemInfo)svcCtx.getMethodReturnValue();
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

    public BgItemInfo getBgItemInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f86ae8f0-0107-1000-e000-0005c0a81291"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgItemInfo retValue = (BgItemInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgItemInfo)svcCtx.getMethodReturnValue();
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

    public BgItemCollection getBgItemCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f86ae8f0-0107-1000-e000-0006c0a81291"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgItemCollection retValue = (BgItemCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgItemCollection)svcCtx.getMethodReturnValue();
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

    public BgItemCollection getBgItemCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f86ae8f0-0107-1000-e000-0007c0a81291"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgItemCollection retValue = (BgItemCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgItemCollection)svcCtx.getMethodReturnValue();
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

    public BgItemCollection getBgItemCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f86ae8f0-0107-1000-e000-0008c0a81291"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgItemCollection retValue = (BgItemCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgItemCollection)svcCtx.getMethodReturnValue();
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

    public void removeAll(Context ctx, BOSUuid bgItemGroupId, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f7b61444-0109-1000-e000-001ec0a812cb"), new Object[]{ctx, bgItemGroupId, orgUnitId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _removeAll(ctx, bgItemGroupId, orgUnitId);
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
    protected abstract void _removeAll(Context ctx, BOSUuid bgItemGroupId, BOSUuid orgUnitId) throws BOSException, EASBizException;

    public boolean isCreator(Context ctx, String creatorID, String groupID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f7b61444-0109-1000-e000-0038c0a812cb"), new Object[]{ctx, creatorID, groupID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_isCreator(ctx, creatorID, groupID);
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
    protected abstract boolean _isCreator(Context ctx, String creatorID, String groupID) throws BOSException, EASBizException;

    public String getMaxNumber(Context ctx, String startStr) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ea181d6f-010b-1000-e000-0003c0a8123a"), new Object[]{ctx, startStr});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_getMaxNumber(ctx, startStr);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
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
    protected abstract String _getMaxNumber(Context ctx, String startStr) throws BOSException, EASBizException;

    public void updateErrorData(Context ctx, Vector itemGroupNumberVec) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("471c1548-010c-1000-e000-0031c0a81284"), new Object[]{ctx, itemGroupNumberVec});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateErrorData(ctx, itemGroupNumberVec);
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
    protected abstract void _updateErrorData(Context ctx, Vector itemGroupNumberVec) throws BOSException, EASBizException;

    public void updateBgItemSuperior(Context ctx, Map bgItemInfo, boolean isInit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("004f93b1-010d-1000-e000-001bc0a81283"), new Object[]{ctx, bgItemInfo, new Boolean(isInit)});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateBgItemSuperior(ctx, bgItemInfo, isInit);
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
    protected abstract void _updateBgItemSuperior(Context ctx, Map bgItemInfo, boolean isInit) throws BOSException, EASBizException;

    public void removeBgItemSuperior(Context ctx, Map bgItemInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0b3eeb1f-010d-1000-e000-0001c0a81283"), new Object[]{ctx, bgItemInfo});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _removeBgItemSuperior(ctx, bgItemInfo);
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
    protected abstract void _removeBgItemSuperior(Context ctx, Map bgItemInfo) throws BOSException, EASBizException;

    public void removeBgItemSuperior(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0f768169-010d-1000-e000-0034c0a81283"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _removeBgItemSuperior(ctx);
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
    protected abstract void _removeBgItemSuperior(Context ctx) throws BOSException, EASBizException;

    public void updateBgItemSuperiorValue(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BigDecimal value) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0f768169-010d-1000-e000-0001c0a81283"), new Object[]{ctx, orgUnitID, bgSchemeID, formula, value});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateBgItemSuperiorValue(ctx, orgUnitID, bgSchemeID, formula, value);
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
    protected abstract void _updateBgItemSuperiorValue(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BigDecimal value) throws BOSException, EASBizException;

    public void updateBgItemSuperiorValue(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, List formula, BigDecimal value) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d7a989d9-8e59-4491-a301-b179fc3276a5"), new Object[]{ctx, orgUnitID, bgSchemeID, formula, value});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateBgItemSuperiorValue(ctx, orgUnitID, bgSchemeID, formula, value);
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
    protected abstract void _updateBgItemSuperiorValue(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, List formula, BigDecimal value) throws BOSException, EASBizException;

    public void updateBgItemSuperiorValue(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, List formula, List value) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1a323534-010d-1000-e000-0020c0a81283"), new Object[]{ctx, orgUnitID, bgSchemeID, formula, value});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateBgItemSuperiorValue(ctx, orgUnitID, bgSchemeID, formula, value);
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
    protected abstract void _updateBgItemSuperiorValue(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, List formula, List value) throws BOSException, EASBizException;

    public boolean bgItemSupriorMaintenance(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2051e631-010d-1000-e000-0001c0a81283"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_bgItemSupriorMaintenance(ctx);
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
    protected abstract boolean _bgItemSupriorMaintenance(Context ctx) throws BOSException, EASBizException;

    public boolean importBgItemCollection(Context ctx, BOSUuid bgItemGroupId, BgItemCollection bgItemCollection) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("48817006-010d-1000-e000-1af7c0a81283"), new Object[]{ctx, bgItemGroupId, bgItemCollection});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_importBgItemCollection(ctx, bgItemGroupId, bgItemCollection);
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
    protected abstract boolean _importBgItemCollection(Context ctx, BOSUuid bgItemGroupId, IObjectCollection bgItemCollection) throws BOSException, EASBizException;

    public boolean checkBgItemCollection(Context ctx, BOSUuid bgItemGroupId, BgItemCollection bgItemCollection) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("48817006-010d-1000-e000-1afbc0a81283"), new Object[]{ctx, bgItemGroupId, bgItemCollection});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_checkBgItemCollection(ctx, bgItemGroupId, bgItemCollection);
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
    protected abstract boolean _checkBgItemCollection(Context ctx, BOSUuid bgItemGroupId, IObjectCollection bgItemCollection) throws BOSException, EASBizException;

    public void setItemInefficacy(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("61c204d3-010d-1000-e000-001bc0a81282"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setItemInefficacy(ctx, id);
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
    protected abstract void _setItemInefficacy(Context ctx, String id) throws BOSException, EASBizException;

    public void setItemEfficacy(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("80c8927c-010d-1000-e000-001dc0a81282"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setItemEfficacy(ctx, id);
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
    protected abstract void _setItemEfficacy(Context ctx, String id) throws BOSException, EASBizException;

    public boolean isJunior(Context ctx, int orgUnitLevel, String itemGroupID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c39cee85-010d-1000-e000-0030c0a81286"), new Object[]{ctx, new Integer(orgUnitLevel), itemGroupID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_isJunior(ctx, orgUnitLevel, itemGroupID);
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
    protected abstract boolean _isJunior(Context ctx, int orgUnitLevel, String itemGroupID) throws BOSException, EASBizException;

    public void modifyBgItemOrg(Context ctx, Hashtable itemMap, Hashtable itemLongNumberMap, Hashtable itemTargetOrgMap, Hashtable itemNameMap, Hashtable itemNumberMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c9c6b152-010d-1000-e000-001bc0a81284"), new Object[]{ctx, itemMap, itemLongNumberMap, itemTargetOrgMap, itemNameMap, itemNumberMap});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _modifyBgItemOrg(ctx, itemMap, itemLongNumberMap, itemTargetOrgMap, itemNameMap, itemNumberMap);
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
    protected abstract void _modifyBgItemOrg(Context ctx, Hashtable itemMap, Hashtable itemLongNumberMap, Hashtable itemTargetOrgMap, Hashtable itemNameMap, Hashtable itemNumberMap) throws BOSException, EASBizException;

    public boolean updateName(Context ctx, BgItemInfo info) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e431e7a6-010e-1000-e000-0001c0a8127f"), new Object[]{ctx, info});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_updateName(ctx, info);
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
    protected abstract boolean _updateName(Context ctx, IObjectValue info) throws BOSException;

    public void updateBgItemSuperiorCtrlRecord(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, List formulaList, BgCtrlResultInfo record, BudgetRequestCategory category) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3709e9b3-a7da-418b-9c6f-08bb625886a4"), new Object[]{ctx, orgUnitID, bgSchemeID, formulaList, record, category});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateBgItemSuperiorCtrlRecord(ctx, orgUnitID, bgSchemeID, formulaList, record, category);
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
    protected abstract void _updateBgItemSuperiorCtrlRecord(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, List formulaList, IObjectValue record, BudgetRequestCategory category) throws BOSException, EASBizException;

    public void updateBgItemSuperiorCtrlRecord(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BgCtrlResultInfo record, BudgetRequestCategory category) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3bdca433-010f-1000-e000-0001c0a81284"), new Object[]{ctx, orgUnitID, bgSchemeID, formula, record, category});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateBgItemSuperiorCtrlRecord(ctx, orgUnitID, bgSchemeID, formula, record, category);
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
    protected abstract void _updateBgItemSuperiorCtrlRecord(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, IObjectValue record, BudgetRequestCategory category) throws BOSException, EASBizException;

    public void batchSetItemInefficacy(Context ctx, List bgItems) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("35f3334b-0111-1000-e000-0001c0a812ac"), new Object[]{ctx, bgItems});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _batchSetItemInefficacy(ctx, bgItems);
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
    protected abstract void _batchSetItemInefficacy(Context ctx, List bgItems) throws BOSException, EASBizException;

    public void batchSetItemEfficacy(Context ctx, List bgItems) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("35f3334b-0111-1000-e000-0003c0a812ac"), new Object[]{ctx, bgItems});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _batchSetItemEfficacy(ctx, bgItems);
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
    protected abstract void _batchSetItemEfficacy(Context ctx, List bgItems) throws BOSException, EASBizException;

    public Map getBatchBgItemName(Context ctx, BOSUuid orgUnitId, BOSUuid bgItemGroupId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4845536c-0113-1000-e000-0001c0a8120f"), new Object[]{ctx, orgUnitId, bgItemGroupId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getBatchBgItemName(ctx, orgUnitId, bgItemGroupId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getBatchBgItemName(Context ctx, BOSUuid orgUnitId, BOSUuid bgItemGroupId) throws BOSException, EASBizException;

    public Map getBatchBgItemName(Context ctx, BOSUuid orgUnitId, BOSUuid parentId, int level) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4d85101a-0113-1000-e000-0001c0a8120f"), new Object[]{ctx, orgUnitId, parentId, new Integer(level)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getBatchBgItemName(ctx, orgUnitId, parentId, level);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getBatchBgItemName(Context ctx, BOSUuid orgUnitId, BOSUuid parentId, int level) throws BOSException, EASBizException;

    public IObjectValue getBgItemName(Context ctx, BOSUuid orgUnitId, String number) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4bbe5272-0113-1000-e000-0005c0a8120f"), new Object[]{ctx, orgUnitId, number});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IObjectValue retValue = (IObjectValue)_getBgItemName(ctx, orgUnitId, number);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IObjectValue)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectValue _getBgItemName(Context ctx, BOSUuid orgUnitId, String number) throws BOSException, EASBizException;

    public void deleteBgItemName(Context ctx, BOSUuid orgUnitId, BOSUuid bgItemId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("521fa778-0113-1000-e000-0001c0a8120f"), new Object[]{ctx, orgUnitId, bgItemId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _deleteBgItemName(ctx, orgUnitId, bgItemId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _deleteBgItemName(Context ctx, BOSUuid orgUnitId, BOSUuid bgItemId) throws BOSException, EASBizException;

    public void deleteBgItemName(Context ctx, List orgUnitIds, BOSUuid bgItemId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("521fa778-0113-1000-e000-0007c0a8120f"), new Object[]{ctx, orgUnitIds, bgItemId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _deleteBgItemName(ctx, orgUnitIds, bgItemId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _deleteBgItemName(Context ctx, List orgUnitIds, BOSUuid bgItemId) throws BOSException, EASBizException;

    public Map importItems(Context ctx, BgItemCollection itemCol, String orgUnitId, String importSource) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("258bdc35-0115-1000-e000-0001c0a8125c"), new Object[]{ctx, itemCol, orgUnitId, importSource});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_importItems(ctx, itemCol, orgUnitId, importSource);
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
    protected abstract Map _importItems(Context ctx, IObjectCollection itemCol, String orgUnitId, String importSource) throws BOSException, EASBizException;

    public void sumBgItemSuperiorCtrlRecord(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, BgCtrlResultInfo resultInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8cb06a9b-0115-1000-e000-000dc0a8125b"), new Object[]{ctx, orgUnitID, bgSchemeID, formula, resultInfo});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _sumBgItemSuperiorCtrlRecord(ctx, orgUnitID, bgSchemeID, formula, resultInfo);
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
    protected abstract void _sumBgItemSuperiorCtrlRecord(Context ctx, BOSUuid orgUnitID, BOSUuid bgSchemeID, String formula, IObjectValue resultInfo) throws BOSException, EASBizException;

    public BgCtrlResultCollection getSuperiorItemCtrlData(Context ctx, BgCtrlResultCollection ctrlResultCol) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("da2880fe-0118-1000-e000-0018c0a8125b"), new Object[]{ctx, ctrlResultCol});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgCtrlResultCollection retValue = (BgCtrlResultCollection)_getSuperiorItemCtrlData(ctx, ctrlResultCol);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BgCtrlResultCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract BgCtrlResultCollection _getSuperiorItemCtrlData(Context ctx, BgCtrlResultCollection ctrlResultCol) throws BOSException, EASBizException;

    public String updateFpDirection(Context ctx, ArrayList headId, FpCashDirectionEnum fpDirection) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d4cd8c95-6feb-44d0-8b81-89987779b9da"), new Object[]{ctx, headId, fpDirection});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_updateFpDirection(ctx, headId, fpDirection);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
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
    protected abstract String _updateFpDirection(Context ctx, ArrayList headId, FpCashDirectionEnum fpDirection) throws BOSException, EASBizException;

    public String updateSumFormula(Context ctx, IObjectValue model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f15f5fbe-d725-43ca-9347-1b2d1dfbde4f"), new Object[]{ctx, model});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_updateSumFormula(ctx, model);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
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
    protected abstract String _updateSumFormula(Context ctx, IObjectValue model) throws BOSException, EASBizException;

    public IObjectCollection getItemListBySumItemFormula(Context ctx, String sumItemFormula) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5a0eedb4-b932-4a3a-9194-9646cadd3084"), new Object[]{ctx, sumItemFormula});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IObjectCollection retValue = (IObjectCollection)_getItemListBySumItemFormula(ctx, sumItemFormula);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IObjectCollection)svcCtx.getMethodReturnValue();
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
    protected abstract IObjectCollection _getItemListBySumItemFormula(Context ctx, String sumItemFormula) throws BOSException, EASBizException;

    public void batchUpdateSumFormula(Context ctx, List updateSumFormulas) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("650524c4-7ee0-40b4-b717-8e5b35f073df"), new Object[]{ctx, updateSumFormulas});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _batchUpdateSumFormula(ctx, updateSumFormulas);
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
    protected abstract void _batchUpdateSumFormula(Context ctx, List updateSumFormulas) throws BOSException, EASBizException;

    public Map checkExpIsUpdate(Context ctx, IObjectPK pk, IObjectValue newValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2fcc36f-d2ff-4025-8d55-ad8c0488a16a"), new Object[]{ctx, pk, newValue});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_checkExpIsUpdate(ctx, pk, newValue);
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
    protected abstract Map _checkExpIsUpdate(Context ctx, IObjectPK pk, IObjectValue newValue) throws BOSException, EASBizException;

    public String updateElimType(Context ctx, ArrayList headId, BgElimTypeEnum elimType, BgDataRelationEnum dataRela) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0f06aeb6-95e7-4118-ab30-18a81252dde5"), new Object[]{ctx, headId, elimType, dataRela});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_updateElimType(ctx, headId, elimType, dataRela);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
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
    protected abstract String _updateElimType(Context ctx, ArrayList headId, BgElimTypeEnum elimType, BgDataRelationEnum dataRela) throws BOSException, EASBizException;

    public String judgeRef(Context ctx, String number, String orgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8eb77b5f-45a9-4348-b169-4712e7e82be0"), new Object[]{ctx, number, orgUnitId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_judgeRef(ctx, number, orgUnitId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
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
    protected abstract String _judgeRef(Context ctx, String number, String orgUnitId) throws BOSException, EASBizException;

    public void delete(Context ctx, IObjectPK[] arrayPK, String curOrgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("57a5c4d1-68d5-4ea8-b65d-1d204696ac88"), new Object[]{ctx, arrayPK, curOrgUnitId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _delete(ctx, arrayPK, curOrgUnitId);
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
    protected abstract void _delete(Context ctx, IObjectPK[] arrayPK, String curOrgUnitId) throws BOSException, EASBizException;

    public void getSuperiorItems(Context ctx, BOSUuid orgUnitID, String formula, Set reFormulaList) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9fbea656-3d01-4439-aac5-79fbcd70d055"), new Object[]{ctx, orgUnitID, formula, reFormulaList});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _getSuperiorItems(ctx, orgUnitID, formula, reFormulaList);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _getSuperiorItems(Context ctx, BOSUuid orgUnitID, String formula, Set reFormulaList) throws BOSException;

    public TreeBaseCollection getTreeBaseCollection (Context ctx) throws BOSException
    {
    	return (TreeBaseCollection)(getBgItemCollection(ctx).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TreeBaseCollection)(getBgItemCollection(ctx, view).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TreeBaseCollection)(getBgItemCollection(ctx, oql).cast(TreeBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getBgItemCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getBgItemCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getBgItemCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgItemCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgItemCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgItemCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getBgItemCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getBgItemCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getBgItemCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}