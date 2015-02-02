package com.kingdee.eas.ma.crbg.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BeginHiDetailBillFactory
{
    private BeginHiDetailBillFactory()
    {
    }
    public static com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8A5194A7") ,com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill.class);
    }
    
    public static com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8A5194A7") ,com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill.class, objectCtx);
    }
    public static com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8A5194A7"));
    }
    public static com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.crbg.database.IBeginHiDetailBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8A5194A7"));
    }
}