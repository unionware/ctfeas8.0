package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class K3ConstantConfigFactory
{
    private K3ConstantConfigFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IK3ConstantConfig getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3ConstantConfig)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F0A59BAC") ,com.kingdee.eas.cp.bc.IK3ConstantConfig.class);
    }
    
    public static com.kingdee.eas.cp.bc.IK3ConstantConfig getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3ConstantConfig)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F0A59BAC") ,com.kingdee.eas.cp.bc.IK3ConstantConfig.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IK3ConstantConfig getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3ConstantConfig)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F0A59BAC"));
    }
    public static com.kingdee.eas.cp.bc.IK3ConstantConfig getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IK3ConstantConfig)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F0A59BAC"));
    }
}