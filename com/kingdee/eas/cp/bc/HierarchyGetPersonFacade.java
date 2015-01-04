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
     *���ݲ�����֯��ְ����ȡ������-�������ο�������ʹ��-User defined method
     *@param companyId ������֯id
     *@param pLevelNum ְ������
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
     *����������֯��ְ����ȡ������-�������ο�������ʹ��-User defined method
     *@param adminOrgId ������֯id
     *@param pLevelNum ְ������
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
     *����ְλ��ְ���ͻ㱨��ϵ��ȡ������-�������ο�������ʹ��-User defined method
     *@param positionNum ְλ����
     *@param pLevelNum ְ������
     *@param hierarchyNum �㱨��ϵ����
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