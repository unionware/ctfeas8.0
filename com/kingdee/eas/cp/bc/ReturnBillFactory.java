package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnBillFactory
{
    private ReturnBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IReturnBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F397BC35") ,com.kingdee.eas.cp.bc.IReturnBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.IReturnBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F397BC35") ,com.kingdee.eas.cp.bc.IReturnBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IReturnBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F397BC35"));
    }
    public static com.kingdee.eas.cp.bc.IReturnBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IReturnBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F397BC35"));
    }
}