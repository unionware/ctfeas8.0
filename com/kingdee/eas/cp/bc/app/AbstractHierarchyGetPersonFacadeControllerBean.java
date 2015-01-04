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
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractHierarchyGetPersonFacadeControllerBean extends AbstractBizControllerBean implements HierarchyGetPersonFacadeController
{
    protected AbstractHierarchyGetPersonFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("B33AAE2E");
    }

    public Person[] getPersonByCPL(Context ctx, String companyId, String pLevelNum) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9c15f366-43d3-484b-be63-2c7bfdc3b0bf"), new Object[]{ctx, companyId, pLevelNum});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Person[] retValue = (Person[])_getPersonByCPL(ctx, companyId, pLevelNum);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Person[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Person[] _getPersonByCPL(Context ctx, String companyId, String pLevelNum) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Person[] getPersonByAPL(Context ctx, String adminOrgId, String pLevelNum) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dfcffedf-e973-4600-95cd-819c2827c550"), new Object[]{ctx, adminOrgId, pLevelNum});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Person[] retValue = (Person[])_getPersonByAPL(ctx, adminOrgId, pLevelNum);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Person[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Person[] _getPersonByAPL(Context ctx, String adminOrgId, String pLevelNum) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Person[] getPersonByPPlH(Context ctx, String positionNum, String pLevelNum, String hierarchyNum) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a1f6981d-45b1-471b-b707-1869c03e69c1"), new Object[]{ctx, positionNum, pLevelNum, hierarchyNum});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Person[] retValue = (Person[])_getPersonByPPlH(ctx, positionNum, pLevelNum, hierarchyNum);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Person[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Person[] _getPersonByPPlH(Context ctx, String positionNum, String pLevelNum, String hierarchyNum) throws BOSException, EASBizException
    {    	
        return null;
    }

}