package com.kingdee.eas.basedata.assistant;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ManagerListFactory
{
    private ManagerListFactory()
    {
    }
    public static com.kingdee.eas.basedata.assistant.IManagerList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IManagerList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C9EDDE40") ,com.kingdee.eas.basedata.assistant.IManagerList.class);
    }
    
    public static com.kingdee.eas.basedata.assistant.IManagerList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IManagerList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C9EDDE40") ,com.kingdee.eas.basedata.assistant.IManagerList.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.assistant.IManagerList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IManagerList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C9EDDE40"));
    }
    public static com.kingdee.eas.basedata.assistant.IManagerList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IManagerList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C9EDDE40"));
    }
}