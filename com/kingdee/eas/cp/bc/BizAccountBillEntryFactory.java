package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BizAccountBillEntryFactory
{
    private BizAccountBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2062F13") ,com.kingdee.eas.cp.bc.IBizAccountBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBizAccountBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2062F13") ,com.kingdee.eas.cp.bc.IBizAccountBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2062F13"));
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2062F13"));
    }
}