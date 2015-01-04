package com.kingdee.eas.basedata.person.app;

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
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import java.util.List;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.basedata.org.PositionCollection;



public abstract class AbstractPersonControllerBean extends DataBaseControllerBean implements PersonController
{
    protected AbstractPersonControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("80EF7DED");
    }

    public PositionInfo getPrimaryPosition(Context ctx, BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c99ef796-00f8-1000-e000-0226c0a8101f"), new Object[]{ctx, personId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PositionInfo retValue = (PositionInfo)_getPrimaryPosition(ctx, personId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PositionInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectValue _getPrimaryPosition(Context ctx, BOSUuid personId) throws BOSException, EASBizException;

    public PositionCollection getPositions(Context ctx, BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c99ef796-00f8-1000-e000-0227c0a8101f"), new Object[]{ctx, personId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PositionCollection retValue = (PositionCollection)_getPositions(ctx, personId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PositionCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getPositions(Context ctx, BOSUuid personId) throws BOSException, EASBizException;

    public AdminOrgUnitCollection getAllAdminOrgUnit(Context ctx, BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c99ef796-00f8-1000-e000-0228c0a8101f"), new Object[]{ctx, personId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            AdminOrgUnitCollection retValue = (AdminOrgUnitCollection)_getAllAdminOrgUnit(ctx, personId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (AdminOrgUnitCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getAllAdminOrgUnit(Context ctx, BOSUuid personId) throws BOSException, EASBizException;

    public AdminOrgUnitInfo getPrimaryAdminOrgUnit(Context ctx, BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c99ef796-00f8-1000-e000-0229c0a8101f"), new Object[]{ctx, personId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            AdminOrgUnitInfo retValue = (AdminOrgUnitInfo)_getPrimaryAdminOrgUnit(ctx, personId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (AdminOrgUnitInfo)svcCtx.getMethodReturnValue();
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
    protected abstract IObjectValue _getPrimaryAdminOrgUnit(Context ctx, BOSUuid personId) throws BOSException, EASBizException;

    public PersonInfo getPersonInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e83180bd-00f8-1000-e000-1333c0a81027"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonInfo retValue = (PersonInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PersonInfo)svcCtx.getMethodReturnValue();
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

    public PersonInfo getPersonInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e83180bd-00f8-1000-e000-1334c0a81027"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonInfo retValue = (PersonInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PersonInfo)svcCtx.getMethodReturnValue();
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

    public PersonCollection getPersonCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e83180bd-00f8-1000-e000-133bc0a81027"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonCollection retValue = (PersonCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PersonCollection)svcCtx.getMethodReturnValue();
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

    public PersonCollection getPersonCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e83180bd-00f8-1000-e000-133cc0a81027"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonCollection retValue = (PersonCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PersonCollection)svcCtx.getMethodReturnValue();
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

    public void assignPosition(Context ctx, String[] personID, PositionInfo positionInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e45d96a8-00fc-1000-e000-0001c0a813c7"), new Object[]{ctx, personID, positionInfo});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _assignPosition(ctx, personID, positionInfo);
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
    protected abstract void _assignPosition(Context ctx, String[] personID, IObjectValue positionInfo) throws BOSException, EASBizException;

    public PersonCollection getOtherPersonCollection(Context ctx, PersonCollection personInfos) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5a23af3d-0101-1000-e000-0010c0a813cb"), new Object[]{ctx, personInfos});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonCollection retValue = (PersonCollection)_getOtherPersonCollection(ctx, personInfos);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PersonCollection)svcCtx.getMethodReturnValue();
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
    protected abstract IObjectCollection _getOtherPersonCollection(Context ctx, IObjectCollection personInfos) throws BOSException, EASBizException;

    public PersonCollection getPersonCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2ff62b9e-0106-1000-e000-000bc0a81239"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonCollection retValue = (PersonCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PersonCollection)svcCtx.getMethodReturnValue();
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

    public void batchAudit(Context ctx, String[] ids, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9558fa54-0114-1000-e000-0007c0a813a0"), new Object[]{ctx, ids, new Boolean(isAudit)});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _batchAudit(ctx, ids, isAudit);
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
    protected abstract void _batchAudit(Context ctx, String[] ids, boolean isAudit) throws BOSException, EASBizException;

    public void checkInfo(Context ctx, String personId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("efc719ba-0115-1000-e000-0008c0a813a0"), new Object[]{ctx, personId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _checkInfo(ctx, personId);
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
    protected abstract void _checkInfo(Context ctx, String personId) throws BOSException, EASBizException;

    public String[] checkInfo(Context ctx, String[] personIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("efc719ba-0115-1000-e000-000ac0a813a0"), new Object[]{ctx, personIds});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_checkInfo(ctx, personIds);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
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
    protected abstract String[] _checkInfo(Context ctx, String[] personIds) throws BOSException, EASBizException;

    public void audit(Context ctx, String[] ids) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0f1ac07f-0116-1000-e000-000fc0a813a0"), new Object[]{ctx, ids});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _audit(ctx, ids);
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
    protected abstract void _audit(Context ctx, String[] ids) throws BOSException, EASBizException;

    public void antiAudit(Context ctx, String[] ids) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0f1ac07f-0116-1000-e000-0011c0a813a0"), new Object[]{ctx, ids});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _antiAudit(ctx, ids);
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
    protected abstract void _antiAudit(Context ctx, String[] ids) throws BOSException, EASBizException;

    public PersonInfo getPersonByStr(Context ctx, String str, String type) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("15396280-f4a2-4b2a-8c6b-0b7b716334b7"), new Object[]{ctx, str, type});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonInfo retValue = (PersonInfo)_getPersonByStr(ctx, str, type);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PersonInfo)svcCtx.getMethodReturnValue();
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
    protected abstract PersonInfo _getPersonByStr(Context ctx, String str, String type) throws BOSException, EASBizException;

    public PersonInfo getPersonByStr(Context ctx, String str, String type, String hrOrgId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("160b0b03-16d8-4162-88a8-fa563e5a3404"), new Object[]{ctx, str, type, hrOrgId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonInfo retValue = (PersonInfo)_getPersonByStr(ctx, str, type, hrOrgId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PersonInfo)svcCtx.getMethodReturnValue();
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
    protected abstract PersonInfo _getPersonByStr(Context ctx, String str, String type, String hrOrgId) throws BOSException, EASBizException;

    public PersonCollection getPersonCollectionByStrLs(Context ctx, List idCardLs, List passportLs, List numberLs) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3662443e-58d9-4ac1-b10a-6aac07431725"), new Object[]{ctx, idCardLs, passportLs, numberLs});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonCollection retValue = (PersonCollection)_getPersonCollectionByStrLs(ctx, idCardLs, passportLs, numberLs);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PersonCollection)svcCtx.getMethodReturnValue();
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
    protected abstract PersonCollection _getPersonCollectionByStrLs(Context ctx, List idCardLs, List passportLs, List numberLs) throws BOSException, EASBizException;

    public PersonCollection getPersonCollectionByStrLs(Context ctx, List idCardLs, List passportLs, List numberLs, String hrOrgId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9deb2047-9933-43c2-8478-2e8772743f16"), new Object[]{ctx, idCardLs, passportLs, numberLs, hrOrgId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PersonCollection retValue = (PersonCollection)_getPersonCollectionByStrLs(ctx, idCardLs, passportLs, numberLs, hrOrgId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (PersonCollection)svcCtx.getMethodReturnValue();
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
    protected abstract PersonCollection _getPersonCollectionByStrLs(Context ctx, List idCardLs, List passportLs, List numberLs, String hrOrgId) throws BOSException, EASBizException;

    public void insertPersons(Context ctx, int index, String[] persons) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("227fc241-e854-4ae0-8254-bdc71aad4e6f"), new Object[]{ctx, new Integer(index), persons});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _insertPersons(ctx, index, persons);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _insertPersons(Context ctx, int index, String[] persons) throws BOSException;

    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getPersonCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getPersonCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getPersonCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getPersonCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getPersonCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getPersonCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getPersonCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getPersonCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getPersonCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}