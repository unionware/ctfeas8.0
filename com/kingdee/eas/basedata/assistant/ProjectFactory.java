package com.kingdee.eas.basedata.assistant;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectFactory
{
    private ProjectFactory()
    {
    }
    public static com.kingdee.eas.basedata.assistant.IProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DADE05EE") ,com.kingdee.eas.basedata.assistant.IProject.class);
    }
    
    public static com.kingdee.eas.basedata.assistant.IProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DADE05EE") ,com.kingdee.eas.basedata.assistant.IProject.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.assistant.IProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DADE05EE"));
    }
    public static com.kingdee.eas.basedata.assistant.IProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.assistant.IProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DADE05EE"));
    }
}