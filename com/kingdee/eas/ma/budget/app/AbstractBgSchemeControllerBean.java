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
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import com.kingdee.bos.dao.IObjectCollection;
import java.util.Hashtable;
import java.lang.String;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.ma.budget.BgSchemeCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.ma.budget.BgTemplateCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;



public abstract class AbstractBgSchemeControllerBean extends DataBaseControllerBean implements BgSchemeController
{
    protected AbstractBgSchemeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4ABADCB");
    }

    public BgSchemeInfo getBgSchemeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d1f6f3d-0108-1000-e000-0035c0a81291"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeInfo retValue = (BgSchemeInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgSchemeInfo)svcCtx.getMethodReturnValue();
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

    public BgSchemeInfo getBgSchemeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d1f6f3d-0108-1000-e000-0036c0a81291"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeInfo retValue = (BgSchemeInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgSchemeInfo)svcCtx.getMethodReturnValue();
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

    public BgSchemeInfo getBgSchemeInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d1f6f3d-0108-1000-e000-0037c0a81291"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeInfo retValue = (BgSchemeInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgSchemeInfo)svcCtx.getMethodReturnValue();
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

    public BgSchemeInfo getValue(Context ctx, BOSUuid bgSchemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("00ccfea3-0111-1000-e000-000bc0a81283"), new Object[]{ctx, bgSchemeId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeInfo retValue = (BgSchemeInfo)_getValue(ctx, bgSchemeId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BgSchemeInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectValue _getValue(Context ctx, BOSUuid bgSchemeId) throws BOSException, EASBizException;

    public BgSchemeCollection getBgSchemeCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d1f6f3d-0108-1000-e000-0038c0a81291"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeCollection retValue = (BgSchemeCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgSchemeCollection)svcCtx.getMethodReturnValue();
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

    public BgSchemeCollection getBgSchemeCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d1f6f3d-0108-1000-e000-0039c0a81291"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeCollection retValue = (BgSchemeCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgSchemeCollection)svcCtx.getMethodReturnValue();
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

    public BgSchemeCollection getBgSchemeCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d1f6f3d-0108-1000-e000-003ac0a81291"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeCollection retValue = (BgSchemeCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (BgSchemeCollection)svcCtx.getMethodReturnValue();
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

    public BgTemplateCollection getTemplateByScheme(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("45984dcc-0108-1000-e000-000ec0a81292"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgTemplateCollection retValue = (BgTemplateCollection)_getTemplateByScheme(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BgTemplateCollection)svcCtx.getMethodReturnValue();
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
    protected abstract IObjectCollection _getTemplateByScheme(Context ctx, String id) throws BOSException, EASBizException;

    public void executeScheme(Context ctx, IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("458cd065-0108-1000-e000-000cc0a81291"), new Object[]{ctx, bgSchemePK, orgUnitId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _executeScheme(ctx, bgSchemePK, orgUnitId);
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
    protected abstract void _executeScheme(Context ctx, IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException;

    public String batchEexecuteScheme(Context ctx, IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("666665f0-0110-1000-e000-000cc0a81284"), new Object[]{ctx, bgSchemePK, orgUnitId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_batchEexecuteScheme(ctx, bgSchemePK, orgUnitId);
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
    protected abstract String _batchEexecuteScheme(Context ctx, IObjectPK bgSchemePK, BOSUuid orgUnitId) throws BOSException, EASBizException;

    public void batchAntiExecuteScheme(Context ctx, BOSUuid bgSchemeId, BOSUuid orgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7086ea7c-0110-1000-e000-0032c0a81283"), new Object[]{ctx, bgSchemeId, orgUnitId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _batchAntiExecuteScheme(ctx, bgSchemeId, orgUnitId);
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
    protected abstract void _batchAntiExecuteScheme(Context ctx, BOSUuid bgSchemeId, BOSUuid orgUnitId) throws BOSException, EASBizException;

    public boolean isQuotedByBgForm(Context ctx, String schemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("501ff02d-0108-1000-e000-000ac0a81291"), new Object[]{ctx, schemeId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_isQuotedByBgForm(ctx, schemeId);
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
    protected abstract boolean _isQuotedByBgForm(Context ctx, String schemeId) throws BOSException, EASBizException;

    public boolean hasChildScheme(Context ctx, String schemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("501ff02d-0108-1000-e000-000cc0a81291"), new Object[]{ctx, schemeId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_hasChildScheme(ctx, schemeId);
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
    protected abstract boolean _hasChildScheme(Context ctx, String schemeId) throws BOSException, EASBizException;

    public IObjectCollection getBgSchemeByOrgUnit(Context ctx, String orgUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("519ea2b0-0108-1000-e000-000ac0a812f4"), new Object[]{ctx, orgUnitId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IObjectCollection retValue = (IObjectCollection)_getBgSchemeByOrgUnit(ctx, orgUnitId);
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
    protected abstract IObjectCollection _getBgSchemeByOrgUnit(Context ctx, String orgUnitId) throws BOSException, EASBizException;

    public boolean usedByBgFormInOrgUnit(Context ctx, String schemeId, String ortUnitId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6f77a07f-0108-1000-e000-000ac0a81291"), new Object[]{ctx, schemeId, ortUnitId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_usedByBgFormInOrgUnit(ctx, schemeId, ortUnitId);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _usedByBgFormInOrgUnit(Context ctx, String schemeId, String ortUnitId) throws BOSException, EASBizException;

    public void antiExecuteScheme(Context ctx, IObjectPK bgSchemePK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("74baa1f1-0108-1000-e000-000ac0a81291"), new Object[]{ctx, bgSchemePK});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _antiExecuteScheme(ctx, bgSchemePK);
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
    protected abstract void _antiExecuteScheme(Context ctx, IObjectPK bgSchemePK) throws BOSException, EASBizException;

    public void sendMessageToSubOrgs(Context ctx, String ids, String orgName, String schemeName, String userNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("98765e89-0108-1000-e000-000cc0a81291"), new Object[]{ctx, ids, orgName, schemeName, userNumber});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _sendMessageToSubOrgs(ctx, ids, orgName, schemeName, userNumber);
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
    protected abstract void _sendMessageToSubOrgs(Context ctx, String ids, String orgName, String schemeName, String userNumber) throws BOSException, EASBizException;

    public Hashtable getDirectChildren(Context ctx, String bgFormId, List subOrgList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d5d72067-0108-1000-e000-001ec0a8128f"), new Object[]{ctx, bgFormId, subOrgList});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Hashtable retValue = (Hashtable)_getDirectChildren(ctx, bgFormId, subOrgList);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Hashtable)svcCtx.getMethodReturnValue();
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
    protected abstract Hashtable _getDirectChildren(Context ctx, String bgFormId, List subOrgList) throws BOSException, EASBizException;

    public Hashtable getInDirectChildren(Context ctx, String bgFormId, Hashtable subOrgNumberMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d5d72067-0108-1000-e000-0023c0a8128f"), new Object[]{ctx, bgFormId, subOrgNumberMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Hashtable retValue = (Hashtable)_getInDirectChildren(ctx, bgFormId, subOrgNumberMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Hashtable)svcCtx.getMethodReturnValue();
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
    protected abstract Hashtable _getInDirectChildren(Context ctx, String bgFormId, Hashtable subOrgNumberMap) throws BOSException, EASBizException;

    public boolean checkTempParentQuotedBySchemeParent(Context ctx, String templateId, String schemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8a3a31f7-0109-1000-e000-000bc0a81291"), new Object[]{ctx, templateId, schemeId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_checkTempParentQuotedBySchemeParent(ctx, templateId, schemeId);
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
    protected abstract boolean _checkTempParentQuotedBySchemeParent(Context ctx, String templateId, String schemeId) throws BOSException, EASBizException;

    public boolean checkTempAssignedInBgScheme(Context ctx, String templateId, String schemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8a3a31f7-0109-1000-e000-0011c0a81291"), new Object[]{ctx, templateId, schemeId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_checkTempAssignedInBgScheme(ctx, templateId, schemeId);
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
    protected abstract boolean _checkTempAssignedInBgScheme(Context ctx, String templateId, String schemeId) throws BOSException, EASBizException;

    public BgTemplateCollection getTemplateBySchemeToAddTem(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3f7545d1-010a-1000-e000-000dc0a812cb"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgTemplateCollection retValue = (BgTemplateCollection)_getTemplateBySchemeToAddTem(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BgTemplateCollection)svcCtx.getMethodReturnValue();
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
    protected abstract IObjectCollection _getTemplateBySchemeToAddTem(Context ctx, String id) throws BOSException, EASBizException;

    public BgTemplateCollection getTemplate(Context ctx, String orgUnitID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d3b214af-010a-1000-e000-0021c0a812cb"), new Object[]{ctx, orgUnitID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgTemplateCollection retValue = (BgTemplateCollection)_getTemplate(ctx, orgUnitID);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BgTemplateCollection)svcCtx.getMethodReturnValue();
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
    protected abstract IObjectCollection _getTemplate(Context ctx, String orgUnitID) throws BOSException, EASBizException;

    public boolean hasFormallyYearScheme(Context ctx, String unitID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("04bb2e2d-010c-1000-e000-000dc0a8123a"), new Object[]{ctx, unitID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_hasFormallyYearScheme(ctx, unitID);
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
    protected abstract boolean _hasFormallyYearScheme(Context ctx, String unitID) throws BOSException, EASBizException;

    public boolean isAreadyDispatched(Context ctx, String rootID, String orgUnitID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("09862b4a-010c-1000-e000-0021c0a8123a"), new Object[]{ctx, rootID, orgUnitID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_isAreadyDispatched(ctx, rootID, orgUnitID);
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
    protected abstract boolean _isAreadyDispatched(Context ctx, String rootID, String orgUnitID) throws BOSException, EASBizException;

    public boolean checkIsUpdate(Context ctx, Map orgUnit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fabcaaea-010c-1000-e000-000bc0a81283"), new Object[]{ctx, orgUnit});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_checkIsUpdate(ctx, orgUnit);
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
    protected abstract boolean _checkIsUpdate(Context ctx, Map orgUnit) throws BOSException, EASBizException;

    public Hashtable isAreadyDispatched(Context ctx, String rootId, Hashtable orgMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa135c82-010d-1000-e000-0010c0a81284"), new Object[]{ctx, rootId, orgMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Hashtable retValue = (Hashtable)_isAreadyDispatched(ctx, rootId, orgMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Hashtable)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Hashtable _isAreadyDispatched(Context ctx, String rootId, Hashtable orgMap) throws BOSException, EASBizException;

    public BgSchemeCollection getSameSchemeByOrgNumbers(Context ctx, String[] orgNumbers) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("288f4572-0110-1000-e000-000bc0a81284"), new Object[]{ctx, orgNumbers});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BgSchemeCollection retValue = (BgSchemeCollection)_getSameSchemeByOrgNumbers(ctx, orgNumbers);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BgSchemeCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getSameSchemeByOrgNumbers(Context ctx, String[] orgNumbers) throws BOSException, EASBizException;

    public boolean executePlan(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, BOSUuid bgPeriodId, String command) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("28b91c9a-0110-1000-e000-0015c0a81283"), new Object[]{ctx, orgUnitId, bgSchemeId, bgPeriodId, command});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_executePlan(ctx, orgUnitId, bgSchemeId, bgPeriodId, command);
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
    protected abstract boolean _executePlan(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, BOSUuid bgPeriodId, String command) throws BOSException, EASBizException;

    public void copyScheme(Context ctx, Map map) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1bc76454-64b2-46f2-ad9d-f72f44899a49"), new Object[]{ctx, map});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _copyScheme(ctx, map);
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
    protected void _copyScheme(Context ctx, Map map) throws BOSException, EASBizException
    {    	
        return;
    }

    public void delCopyScheme(Context ctx, IObjectPK bgSchemeID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("db8e15d4-51ce-47e4-827a-17b8953c2e81"), new Object[]{ctx, bgSchemeID});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _delCopyScheme(ctx, bgSchemeID);
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
    protected abstract void _delCopyScheme(Context ctx, IObjectPK bgSchemeID) throws BOSException, EASBizException;

    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getBgSchemeCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getBgSchemeCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getBgSchemeCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgSchemeCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgSchemeCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getBgSchemeCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getBgSchemeCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getBgSchemeCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getBgSchemeCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}