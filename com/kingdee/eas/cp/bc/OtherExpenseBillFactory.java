package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherExpenseBillFactory
{
    private OtherExpenseBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IOtherExpenseBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E76173AD") ,com.kingdee.eas.cp.bc.IOtherExpenseBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.IOtherExpenseBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E76173AD") ,com.kingdee.eas.cp.bc.IOtherExpenseBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IOtherExpenseBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E76173AD"));
    }
    public static com.kingdee.eas.cp.bc.IOtherExpenseBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IOtherExpenseBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E76173AD"));
    }
}