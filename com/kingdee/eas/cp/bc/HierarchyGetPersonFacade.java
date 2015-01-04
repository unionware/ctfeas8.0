package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.app.*;

public class HierarchyGetPersonFacade extends AbstractBizCtrl implements IHierarchyGetPersonFacade
{
    public HierarchyGetPersonFacade()
    {
        super();
        registerInterface(IHierarchyGetPersonFacade.class, this);
    }
    public HierarchyGetPersonFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IHierarchyGetPersonFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B33AAE2E");
    }
    private HierarchyGetPersonFacadeController getController() throws BOSException
    {
        return (HierarchyGetPersonFacadeController)getBizController();
    }
    /**
     *根据财务组织和职级获取参与人-仅供二次开发单据使用-User defined method
     *@param companyId 财务组织id
     *@param pLevelNum 职级编码
     *@return
     */
    public Person[] getPersonByCPL(String companyId, String pLevelNum) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonByCPL(getContext(), companyId, pLevelNum);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据行政组织和职级获取参与人-仅供二次开发单据使用-User defined method
     *@param adminOrgId 行政组织id
     *@param pLevelNum 职级编码
     *@return
     */
    public Person[] getPersonByAPL(String adminOrgId, String pLevelNum) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonByAPL(getContext(), adminOrgId, pLevelNum);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据职位、职级和汇报关系获取参与人-仅供二次开发单据使用-User defined method
     *@param positionNum 职位编码
     *@param pLevelNum 职级编码
     *@param hierarchyNum 汇报关系编码
     *@return
     */
    public Person[] getPersonByPPlH(String positionNum, String pLevelNum, String hierarchyNum) throws BOSException, EASBizException
    {
        try {
            return getController().getPersonByPPlH(getContext(), positionNum, pLevelNum, hierarchyNum);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}