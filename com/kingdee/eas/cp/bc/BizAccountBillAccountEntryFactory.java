package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BizAccountBillAccountEntryFactory
{
    private BizAccountBillAccountEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88A4EDD5") ,com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88A4EDD5") ,com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88A4EDD5"));
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBillAccountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88A4EDD5"));
    }
}