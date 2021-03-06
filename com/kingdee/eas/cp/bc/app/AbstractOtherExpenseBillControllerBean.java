package com.kingdee.eas.cp.bc.app;

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
import com.kingdee.eas.cp.bc.ExpenseReqBillCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.OtherExpenseBillInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;



public abstract class AbstractOtherExpenseBillControllerBean extends ExpenseReqBillControllerBean implements OtherExpenseBillController
{
    protected AbstractOtherExpenseBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("E76173AD");
    }

    public OtherExpenseBillInfo getOtherExpenseBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8864533a-0104-1000-e000-0015c0a813b3"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            OtherExpenseBillInfo retValue = (OtherExpenseBillInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (OtherExpenseBillInfo)svcCtx.getMethodReturnValue();
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

    public OtherExpenseBillInfo getOtherExpenseBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8864533a-0104-1000-e000-0016c0a813b3"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            OtherExpenseBillInfo retValue = (OtherExpenseBillInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (OtherExpenseBillInfo)svcCtx.getMethodReturnValue();
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

    public OtherExpenseBillInfo getOtherExpenseBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8864533a-0104-1000-e000-0017c0a813b3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            OtherExpenseBillInfo retValue = (OtherExpenseBillInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (OtherExpenseBillInfo)svcCtx.getMethodReturnValue();
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

    public OtherExpenseBillCollection getOtherExpenseBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8864533a-0104-1000-e000-0018c0a813b3"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            OtherExpenseBillCollection retValue = (OtherExpenseBillCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (OtherExpenseBillCollection)svcCtx.getMethodReturnValue();
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

    public OtherExpenseBillCollection getOtherExpenseBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8864533a-0104-1000-e000-0019c0a813b3"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            OtherExpenseBillCollection retValue = (OtherExpenseBillCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (OtherExpenseBillCollection)svcCtx.getMethodReturnValue();
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

    public OtherExpenseBillCollection getOtherExpenseBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8864533a-0104-1000-e000-001ac0a813b3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            OtherExpenseBillCollection retValue = (OtherExpenseBillCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (OtherExpenseBillCollection)svcCtx.getMethodReturnValue();
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

    public ExpenseReqBillCollection getExpenseReqBillCollection (Context ctx) throws BOSException
    {
    	return (ExpenseReqBillCollection)(getOtherExpenseBillCollection(ctx).cast(ExpenseReqBillCollection.class));
    }
    public ExpenseReqBillCollection getExpenseReqBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ExpenseReqBillCollection)(getOtherExpenseBillCollection(ctx, view).cast(ExpenseReqBillCollection.class));
    }
    public ExpenseReqBillCollection getExpenseReqBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (ExpenseReqBillCollection)(getOtherExpenseBillCollection(ctx, oql).cast(ExpenseReqBillCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getOtherExpenseBillCollection(ctx).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getOtherExpenseBillCollection(ctx, view).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getOtherExpenseBillCollection(ctx, oql).cast(BizCollBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getOtherExpenseBillCollection(ctx).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getOtherExpenseBillCollection(ctx, view).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getOtherExpenseBillCollection(ctx, oql).cast(BizCollCoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getOtherExpenseBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getOtherExpenseBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getOtherExpenseBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getOtherExpenseBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getOtherExpenseBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getOtherExpenseBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getOtherExpenseBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getOtherExpenseBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getOtherExpenseBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}