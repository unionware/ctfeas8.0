package com.kingdee.eas.fi.gl;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fi.gl.app.*;
import com.kingdee.bos.framework.*;

public class ZDFGLReportSubsidiaryLedger extends GLReport implements IZDFGLReportSubsidiaryLedger
{
    public ZDFGLReportSubsidiaryLedger()
    {
        super();
        registerInterface(IZDFGLReportSubsidiaryLedger.class, this);
    }
    public ZDFGLReportSubsidiaryLedger(Context ctx)
    {
        super(ctx);
        registerInterface(IZDFGLReportSubsidiaryLedger.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0264DD3B");
    }
    private ZDFGLReportSubsidiaryLedgerController getController() throws BOSException
    {
        return (ZDFGLReportSubsidiaryLedgerController)getBizController();
    }
}