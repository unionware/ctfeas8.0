package com.kingdee.eas.basedata.person;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import java.util.List;
import com.kingdee.eas.basedata.person.app.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.basedata.org.PositionCollection;

public class Person extends DataBase implements IPerson
{
    public Person()
    {
        super();
        registerInterface(IPerson.class, this);
    }
    public Person(Context ctx)
    {
        super(ctx);
        registerInterface(IPerson.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("80EF7DED");
    }
    private PersonController getController() throws BOSException
    {
        return (PersonController)getBizController();
    }
    /**
     *取职员的主要职位-User defined method
     *@param personId 人员ID
     *@return
     */
    public PositionInfo getPrimaryPosition(BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            return getController().getPrimaryPosition(getContext(), personId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取职员的所有职位的集合-User defined method
     *@param personId 人员 ID
     *@return
     */
    public PositionCollection getPositions(BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            return getController().getPositions(getContext(), personId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取职员的所有行政组织的集合-User defined method
     *@param personId 人员ID
     *@return
     */
    public AdminOrgUnitCollection getAllAdminOrgUnit(BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            return getController().getAllAdminOrgUnit(getContext(), personId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取职员的主要部门-User defined method
     *@param personId 人员ID
     *@return
     */
    public AdminOrgUnitInfo getPrimaryAdminOrgUnit(BOSUuid personId) throws BOSException, EASBizException
    {
        try {
            return getController().getPrimaryAdminOrgUnit(getContext(), personId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PersonInfo getPersonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public PersonInfo getPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PersonCollection getPersonCollection() throws BOSException
    {
        try {
            return getController().getPersonCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public PersonCollection getPersonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPersonCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *给无职位人员分配职位-User defined method
     *@param personID 人员ID
     *@param positionInfo 职位ID
     */
    public void assignPosition(String[] personID, PositionInfo positionInfo) throws BOSException, EASBizException
    {
        try {
            getController().assignPosition(getContext(), personID, positionInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得当前人员集合的职位，组织-User defined method
     *@param personInfos personInfos
     *@return
     */
    public PersonCollection getOtherPersonCollection(PersonCollection personInfos) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherPersonCollection(getContext(), personInfos);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public PersonCollection getPersonCollection(String oql) throws BOSException
    {
        try {
            return getController().getPersonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核、反审核-User defined method
     *@param ids ids
     *@param isAudit isAudit
     */
    public void batchAudit(String[] ids, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            getController().batchAudit(getContext(), ids, isAudit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *资料检查-User defined method
     *@param personId personId
     */
    public void checkInfo(String personId) throws BOSException, EASBizException
    {
        try {
            getController().checkInfo(getContext(), personId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *资料检查-User defined method
     *@param personIds personIds
     *@return
     */
    public String[] checkInfo(String[] personIds) throws BOSException, EASBizException
    {
        try {
            return getController().checkInfo(getContext(), personIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *职员信息审核-User defined method
     *@param ids ids
     */
    public void audit(String[] ids) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *职员信息反审核-User defined method
     *@param ids ids
     */
    public void antiAudit(String[] ids) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据身份证或护照号或编码取得职员信息-User defined method
     *@param str 身份证或护照号或编码
     *@param type 证件类型
     *@return
     */
    public PersonInfo getPersonByStr(String str, String type) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonByStr(getContext(), str, type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据身份证或护照号或编码取得职员信息-User defined method
     *@param str 身份证或护照号或编码
     *@param type 证件类型
     *@param hrOrgId 当前HR组织
     *@return
     */
    public PersonInfo getPersonByStr(String str, String type, String hrOrgId) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonByStr(getContext(), str, type, hrOrgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据身份证或护照号或编码集合取得职员信息集合-User defined method
     *@param idCardLs 身份证集合
     *@param passportLs 护照号集合
     *@param numberLs 编码集合
     *@return
     */
    public PersonCollection getPersonCollectionByStrLs(List idCardLs, List passportLs, List numberLs) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonCollectionByStrLs(getContext(), idCardLs, passportLs, numberLs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据身份证或护照号或编码集合取得职员信息集合-User defined method
     *@param idCardLs 身份证集合
     *@param passportLs 护照号集合
     *@param numberLs 编码集合
     *@param hrOrgId 当前HR组织
     *@return
     */
    public PersonCollection getPersonCollectionByStrLs(List idCardLs, List passportLs, List numberLs, String hrOrgId) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonCollectionByStrLs(getContext(), idCardLs, passportLs, numberLs, hrOrgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *将人员插入至指定位置-User defined method
     *@param index 顺序号
     *@param persons 人员
     */
    public void insertPersons(int index, String[] persons) throws BOSException
    {
        try {
            getController().insertPersons(getContext(), index, persons);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}