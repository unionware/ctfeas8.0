package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class K3DBConfigFactory
{
    private K3DBConfigFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IK3DBConfig getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3DBConfig)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FC100566") ,com.kingdee.eas.cp.bc.IK3DBConfig.class);
    }
    
    public static com.kingdee.eas.cp.bc.IK3DBConfig getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3DBConfig)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FC100566") ,com.kingdee.eas.cp.bc.IK3DBConfig.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IK3DBConfig getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3DBConfig)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FC100566"));
    }
    public static com.kingdee.eas.cp.bc.IK3DBConfig getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3DBConfig)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FC100566"));
    }
}