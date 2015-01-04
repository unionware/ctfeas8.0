package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TravelAccountBillLoanCheckEntryFactory
{
    private TravelAccountBillLoanCheckEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C488E436") ,com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C488E436") ,com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C488E436"));
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillLoanCheckEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C488E436"));
    }
}