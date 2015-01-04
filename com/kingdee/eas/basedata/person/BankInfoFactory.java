package com.kingdee.eas.basedata.person;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BankInfoFactory
{
    private BankInfoFactory()
    {
    }
    public static com.kingdee.eas.basedata.person.IBankInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IBankInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("79C8FDA2") ,com.kingdee.eas.basedata.person.IBankInfo.class);
    }
    
    public static com.kingdee.eas.basedata.person.IBankInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IBankInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("79C8FDA2") ,com.kingdee.eas.basedata.person.IBankInfo.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.person.IBankInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IBankInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("79C8FDA2"));
    }
    public static com.kingdee.eas.basedata.person.IBankInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IBankInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("79C8FDA2"));
    }
}