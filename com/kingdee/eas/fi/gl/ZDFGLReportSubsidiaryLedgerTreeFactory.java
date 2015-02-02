package com.kingdee.eas.fi.gl;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ZDFGLReportSubsidiaryLedgerTreeFactory
{
    private ZDFGLReportSubsidiaryLedgerTreeFactory()
    {
    }
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E15AF79") ,com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree.class);
    }
    
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E15AF79") ,com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree.class, objectCtx);
    }
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E15AF79"));
    }
    public static com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E15AF79"));
    }
}