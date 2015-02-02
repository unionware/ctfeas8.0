package com.kingdee.eas.fi.gl;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fi.gl.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;

public class ZDFGLReportSubsidiaryLedgerTree extends AbstractBizCtrl implements IZDFGLReportSubsidiaryLedgerTree
{
    public ZDFGLReportSubsidiaryLedgerTree()
    {
        super();
        registerInterface(IZDFGLReportSubsidiaryLedgerTree.class, this);
    }
    public ZDFGLReportSubsidiaryLedgerTree(Context ctx)
    {
        super(ctx);
        registerInterface(IZDFGLReportSubsidiaryLedgerTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5E15AF79");
    }
    private ZDFGLReportSubsidiaryLedgerTreeController getController() throws BOSException
    {
        return (ZDFGLReportSubsidiaryLedgerTreeController)getBizController();
    }
    /**
     *²éÕÒ¿ÆÄ¿Ê÷-User defined method
     *@param condition condition
     *@param company company
     *@return
     */
    public ReportResultInfo findAccountTree(EntityViewInfo condition, CompanyOrgUnitInfo company) throws BOSException, EASBizException
    {
        try {
            return getController().findAccountTree(getContext(), condition, company);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}