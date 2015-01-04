package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BizAccountBillLoanCheckEntryFactory
{
    private BizAccountBillLoanCheckEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A7890BD9") ,com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A7890BD9") ,com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A7890BD9"));
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillLoanCheckEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A7890BD9"));
    }
}