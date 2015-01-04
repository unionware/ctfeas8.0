package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnBillEntryFactory
{
    private ReturnBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IReturnBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E04EB53D") ,com.kingdee.eas.cp.bc.IReturnBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IReturnBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E04EB53D") ,com.kingdee.eas.cp.bc.IReturnBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IReturnBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E04EB53D"));
    }
    public static com.kingdee.eas.cp.bc.IReturnBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E04EB53D"));
    }
}