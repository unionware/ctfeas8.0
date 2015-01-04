package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DailyLoanBillEntryFactory
{
    private DailyLoanBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IDailyLoanBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F4884CE0") ,com.kingdee.eas.cp.bc.IDailyLoanBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IDailyLoanBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F4884CE0") ,com.kingdee.eas.cp.bc.IDailyLoanBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IDailyLoanBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F4884CE0"));
    }
    public static com.kingdee.eas.cp.bc.IDailyLoanBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F4884CE0"));
    }
}