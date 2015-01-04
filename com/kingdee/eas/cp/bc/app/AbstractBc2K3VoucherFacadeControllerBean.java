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



public abstract class AbstractBc2K3VoucherFacadeControllerBean extends AbstractBizControllerBean implements Bc2K3VoucherFacadeController
{
    protected AbstractBc2K3VoucherFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("12FE6D2D");
    }

    public void crVouchByBizAccount(Context ctx, String billID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("25e8675d-6da4-4bb9-9d52-5a9755b27c05"), new Object[]{ctx, billID});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _crVouchByBizAccount(ctx, billID);
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
    protected void _crVouchByBizAccount(Context ctx, String billID) throws BOSException, EASBizException
    {    	
        return;
    }

    public void crVouchByDailyLoan(Context ctx, String billID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a6704cfc-fe02-479b-9d99-3f6cb74dca48"), new Object[]{ctx, billID});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _crVouchByDailyLoan(ctx, billID);
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
    protected void _crVouchByDailyLoan(Context ctx, String billID) throws BOSException, EASBizException
    {    	
        return;
    }

    public void crVouchByEvectionLoan(Context ctx, String billID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ed690d46-e672-4283-b34a-5050de32a3b9"), new Object[]{ctx, billID});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _crVouchByEvectionLoan(ctx, billID);
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
    protected void _crVouchByEvectionLoan(Context ctx, String billID) throws BOSException, EASBizException
    {    	
        return;
    }

    public void crVouchByTraveAccount(Context ctx, String billID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cb7a02d9-5da7-4b09-82a6-e6f52e37f6e5"), new Object[]{ctx, billID});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _crVouchByTraveAccount(ctx, billID);
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
    protected void _crVouchByTraveAccount(Context ctx, String billID) throws BOSException, EASBizException
    {    	
        return;
    }

    public void backRun(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("994e5619-0adc-4843-a410-b0f339f59486"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _backRun(ctx);
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
    protected void _backRun(Context ctx) throws BOSException, EASBizException
    {    	
        return;
    }

}