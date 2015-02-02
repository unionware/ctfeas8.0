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

import com.kingdee.eas.common.EASBizException;
import java.util.Map;
import com.kingdee.bos.util.BOSUuid;
import java.util.Set;



public abstract class AbstractBgDisCountFacadeControllerBean extends AbstractBizControllerBean implements BgDisCountFacadeController
{
    protected AbstractBgDisCountFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F92EF741");
    }

    public BOSUuid getCollectFormId(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("da2fc45d-e55d-4933-8e17-26c9ce5895cb"), new Object[]{ctx, bgFormId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BOSUuid retValue = (BOSUuid)_getCollectFormId(ctx, bgFormId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BOSUuid)svcCtx.getMethodReturnValue();
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
    protected abstract BOSUuid _getCollectFormId(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException;

    public Set getCollectFormInfo(Context ctx, BOSUuid bgFormId, Set orgUnitIds, Set state, boolean isFund) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd2e41f8-8cc3-4275-abe0-5f2a82fd2595"), new Object[]{ctx, bgFormId, orgUnitIds, state, new Boolean(isFund)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Set retValue = (Set)_getCollectFormInfo(ctx, bgFormId, orgUnitIds, state, isFund);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Set)svcCtx.getMethodReturnValue();
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
    protected abstract Set _getCollectFormInfo(Context ctx, BOSUuid bgFormId, Set orgUnitIds, Set state, boolean isFund) throws BOSException, EASBizException;

    public void checkFormulaIsEctype(Context ctx, BOSUuid bgFormId, Map formulaMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cc0f3410-7b8d-4556-9089-17d8bd6f1168"), new Object[]{ctx, bgFormId, formulaMap});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _checkFormulaIsEctype(ctx, bgFormId, formulaMap);
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
    protected abstract void _checkFormulaIsEctype(Context ctx, BOSUuid bgFormId, Map formulaMap) throws BOSException, EASBizException;

    public Map loadAllRelateInfo(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9eece093-9a19-4341-919c-8beb8060a6f3"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_loadAllRelateInfo(ctx, id);
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
    protected abstract Map _loadAllRelateInfo(Context ctx, BOSUuid id) throws BOSException, EASBizException;

}