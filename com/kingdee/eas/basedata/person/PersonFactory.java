package com.kingdee.eas.basedata.person;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PersonFactory
{
    private PersonFactory()
    {
    }
    public static com.kingdee.eas.basedata.person.IPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("80EF7DED") ,com.kingdee.eas.basedata.person.IPerson.class);
    }
    
    public static com.kingdee.eas.basedata.person.IPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("80EF7DED") ,com.kingdee.eas.basedata.person.IPerson.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.person.IPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("80EF7DED"));
    }
    public static com.kingdee.eas.basedata.person.IPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.person.IPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("80EF7DED"));
    }
}