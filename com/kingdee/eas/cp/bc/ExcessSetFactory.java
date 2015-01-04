package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExcessSetFactory
{
    private ExcessSetFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IExcessSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5DC2FC0F") ,com.kingdee.eas.cp.bc.IExcessSet.class);
    }
    
    public static com.kingdee.eas.cp.bc.IExcessSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5DC2FC0F") ,com.kingdee.eas.cp.bc.IExcessSet.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IExcessSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5DC2FC0F"));
    }
    public static com.kingdee.eas.cp.bc.IExcessSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5DC2FC0F"));
    }
}