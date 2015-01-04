package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LinkOthExpenseFactory
{
    private LinkOthExpenseFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ILinkOthExpense getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ILinkOthExpense)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F83AF2D") ,com.kingdee.eas.cp.bc.ILinkOthExpense.class);
    }
    
    public static com.kingdee.eas.cp.bc.ILinkOthExpense getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ILinkOthExpense)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F83AF2D") ,com.kingdee.eas.cp.bc.ILinkOthExpense.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ILinkOthExpense getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ILinkOthExpense)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F83AF2D"));
    }
    public static com.kingdee.eas.cp.bc.ILinkOthExpense getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ILinkOthExpense)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F83AF2D"));
    }
}