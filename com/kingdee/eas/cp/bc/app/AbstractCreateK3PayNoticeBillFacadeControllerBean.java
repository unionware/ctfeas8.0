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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.BOSUuid;



public abstract class AbstractCreateK3PayNoticeBillFacadeControllerBean extends AbstractBizControllerBean implements CreateK3PayNoticeBillFacadeController
{
    protected AbstractCreateK3PayNoticeBillFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("8E09203F");
    }

    public void createByDailyLoanBill(Context ctx, BOSUuid billid) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3a1ef718-fe0f-4ba8-b487-21c7f9393bbb"), new Object[]{ctx, billid});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _createByDailyLoanBill(ctx, billid);
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
    protected void _createByDailyLoanBill(Context ctx, BOSUuid billid) throws BOSException, EASBizException
    {    	
        return;
    }

    public void createByBizAccountBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4ef5d7bc-5c79-4c8a-985b-ff0bc824546f"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _createByBizAccountBill(ctx, billId);
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
    protected void _createByBizAccountBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void createByEvectionLoanBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("31bd98ea-202b-4cd6-8fcd-6adb4d9cbe0a"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _createByEvectionLoanBill(ctx, billId);
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
    protected void _createByEvectionLoanBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void createByTraveAccountBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9be98268-16a7-45bb-9069-8b655c555f12"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _createByTraveAccountBill(ctx, billId);
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
    protected void _createByTraveAccountBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void backRun(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eb5d5a98-6c29-449e-85d2-42bcd416512d"), new Object[]{ctx});
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