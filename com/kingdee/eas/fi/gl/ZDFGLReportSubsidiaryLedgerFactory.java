package com.kingdee.eas.fi.gl;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ZDFGLReportSubsidiaryLedgerFactory
{
    private ZDFGLReportSubsidiaryLedgerFactory()
    {
    }
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0264DD3B") ,com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger.class);
    }
    
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0264DD3B") ,com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger.class, objectCtx);
    }
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0264DD3B"));
    }
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedger)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0264DD3B"));
    }
}