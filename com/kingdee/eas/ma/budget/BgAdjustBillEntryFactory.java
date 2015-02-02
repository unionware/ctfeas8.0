package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgAdjustBillEntryFactory
{
    private BgAdjustBillEntryFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IBgAdjustBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAdjustBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C0AAC76") ,com.kingdee.eas.ma.budget.IBgAdjustBillEntry.class);
    }
    
    public static com.kingdee.eas.ma.budget.IBgAdjustBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAdjustBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C0AAC76") ,com.kingdee.eas.ma.budget.IBgAdjustBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IBgAdjustBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAdjustBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C0AAC76"));
    }
    public static com.kingdee.eas.ma.budget.IBgAdjustBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAdjustBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C0AAC76"));
    }
}