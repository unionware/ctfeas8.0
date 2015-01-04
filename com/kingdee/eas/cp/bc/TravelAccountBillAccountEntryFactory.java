package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TravelAccountBillAccountEntryFactory
{
    private TravelAccountBillAccountEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0BA02118") ,com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0BA02118") ,com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0BA02118"));
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillAccountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0BA02118"));
    }
}