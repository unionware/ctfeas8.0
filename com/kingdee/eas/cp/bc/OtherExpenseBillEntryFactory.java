package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherExpenseBillEntryFactory
{
    private OtherExpenseBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IOtherExpenseBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5C4E8C5") ,com.kingdee.eas.cp.bc.IOtherExpenseBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IOtherExpenseBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5C4E8C5") ,com.kingdee.eas.cp.bc.IOtherExpenseBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IOtherExpenseBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5C4E8C5"));
    }
    public static com.kingdee.eas.cp.bc.IOtherExpenseBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5C4E8C5"));
    }
}