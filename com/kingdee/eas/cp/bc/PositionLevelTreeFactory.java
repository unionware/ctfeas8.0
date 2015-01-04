package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PositionLevelTreeFactory
{
    private PositionLevelTreeFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IPositionLevelTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevelTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E46117B") ,com.kingdee.eas.cp.bc.IPositionLevelTree.class);
    }
    
    public static com.kingdee.eas.cp.bc.IPositionLevelTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevelTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E46117B") ,com.kingdee.eas.cp.bc.IPositionLevelTree.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IPositionLevelTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevelTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E46117B"));
    }
    public static com.kingdee.eas.cp.bc.IPositionLevelTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevelTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E46117B"));
    }
}