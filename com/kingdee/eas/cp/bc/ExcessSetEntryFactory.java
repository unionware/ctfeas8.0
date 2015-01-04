package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExcessSetEntryFactory
{
    private ExcessSetEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IExcessSetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C024106") ,com.kingdee.eas.cp.bc.IExcessSetEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IExcessSetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C024106") ,com.kingdee.eas.cp.bc.IExcessSetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IExcessSetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C024106"));
    }
    public static com.kingdee.eas.cp.bc.IExcessSetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IExcessSetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C024106"));
    }
}