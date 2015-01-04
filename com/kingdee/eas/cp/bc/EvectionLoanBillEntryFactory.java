package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvectionLoanBillEntryFactory
{
    private EvectionLoanBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IEvectionLoanBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9DB7201A") ,com.kingdee.eas.cp.bc.IEvectionLoanBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IEvectionLoanBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9DB7201A") ,com.kingdee.eas.cp.bc.IEvectionLoanBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IEvectionLoanBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9DB7201A"));
    }
    public static com.kingdee.eas.cp.bc.IEvectionLoanBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9DB7201A"));
    }
}