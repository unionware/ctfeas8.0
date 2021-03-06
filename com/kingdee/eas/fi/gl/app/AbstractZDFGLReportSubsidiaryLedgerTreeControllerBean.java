package com.kingdee.eas.fi.gl.app;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.fi.gl.ReportResultInfo;



public abstract class AbstractZDFGLReportSubsidiaryLedgerTreeControllerBean extends AbstractBizControllerBean implements ZDFGLReportSubsidiaryLedgerTreeController
{
    protected AbstractZDFGLReportSubsidiaryLedgerTreeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E15AF79");
    }

    public ReportResultInfo findAccountTree(Context ctx, EntityViewInfo condition, CompanyOrgUnitInfo company) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d60d1025-6f80-48fc-bc0e-dfca9d0a0b70"), new Object[]{ctx, condition, company});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ReportResultInfo retValue = (ReportResultInfo)_findAccountTree(ctx, condition, company);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (ReportResultInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract ReportResultInfo _findAccountTree(Context ctx, EntityViewInfo condition, CompanyOrgUnitInfo company) throws BOSException, EASBizException;

}