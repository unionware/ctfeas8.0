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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.budget.BgFormInfo;



public abstract class AbstractRefDisCountBgFormControllerBean extends CoreBaseControllerBean implements RefDisCountBgFormController
{
    protected AbstractRefDisCountBgFormControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("78A6183C");
    }

    public RefDisCountBgFormCollection loadRefBgFormCollectionByBgForm(Context ctx, BgFormInfo bgFormInfo, List ouIdList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a3cdec68-cd23-4acd-8b7e-930d172aeeae"), new Object[]{ctx, bgFormInfo, ouIdList});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormCollection retValue = (RefDisCountBgFormCollection)_loadRefBgFormCollectionByBgForm(ctx, bgFormInfo, ouIdList);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (RefDisCountBgFormCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _loadRefBgFormCollectionByBgForm(Context ctx, IObjectValue bgFormInfo, List ouIdList) throws BOSException, EASBizException;

    public List getCommentByBgForm(Context ctx, IObjectPK bgFormPK, boolean isCollectForm) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2b556b8c-6dae-444b-95df-df2d5a2518fc"), new Object[]{ctx, bgFormPK, new Boolean(isCollectForm)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_getCommentByBgForm(ctx, bgFormPK, isCollectForm);
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
    protected abstract List _getCommentByBgForm(Context ctx, IObjectPK bgFormPK, boolean isCollectForm) throws BOSException, EASBizException;

    public void addCommentByBgForm(Context ctx, IObjectPK bgFormPK, String comment) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5fbf12dc-4b2b-4d97-a6b8-8c50b7da997a"), new Object[]{ctx, bgFormPK, comment});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _addCommentByBgForm(ctx, bgFormPK, comment);
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
    protected abstract void _addCommentByBgForm(Context ctx, IObjectPK bgFormPK, String comment) throws BOSException, EASBizException;

    public byte[] getKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d92cdd11-3907-41b3-8dec-7b7ef9858472"), new Object[]{ctx, bgCollectId, refBgFormId, bgFormId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            byte[] retValue = (byte[])_getKdf(ctx, bgCollectId, refBgFormId, bgFormId);
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
    protected abstract byte[] _getKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId) throws BOSException, EASBizException;

    public void submitKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId, RefDisCountBgFormInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8a5dd39f-c835-4bd3-b1d3-4c9b5f455e20"), new Object[]{ctx, bgCollectId, refBgFormId, bgFormId, model});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _submitKdf(ctx, bgCollectId, refBgFormId, bgFormId, model);
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
    protected abstract void _submitKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId, IObjectValue model) throws BOSException, EASBizException;

    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("82b8f9a9-f4fe-4408-ba2b-00569a53fc4c"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormInfo retValue = (RefDisCountBgFormInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RefDisCountBgFormInfo)svcCtx.getMethodReturnValue();
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

    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("23d07322-c5c9-4ffb-84b7-0f29603d6c1c"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormInfo retValue = (RefDisCountBgFormInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RefDisCountBgFormInfo)svcCtx.getMethodReturnValue();
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

    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("031684e7-49e5-4f2c-8d1c-4906aa27c4d7"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormInfo retValue = (RefDisCountBgFormInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RefDisCountBgFormInfo)svcCtx.getMethodReturnValue();
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

    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d6e1a33f-68bd-4d6a-baeb-64b8ed7ab95d"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormCollection retValue = (RefDisCountBgFormCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RefDisCountBgFormCollection)svcCtx.getMethodReturnValue();
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

    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d4878a4d-d8ce-4f85-b6b1-f04002cd61a5"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormCollection retValue = (RefDisCountBgFormCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RefDisCountBgFormCollection)svcCtx.getMethodReturnValue();
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

    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d3c678b8-1e49-4b58-9a4d-2f9690bdf3e1"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RefDisCountBgFormCollection retValue = (RefDisCountBgFormCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RefDisCountBgFormCollection)svcCtx.getMethodReturnValue();
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

    public CoreBaseCollection getCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getRefDisCountBgFormCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getRefDisCountBgFormCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getRefDisCountBgFormCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}