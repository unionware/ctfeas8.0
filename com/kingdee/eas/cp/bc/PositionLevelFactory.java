package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PositionLevelFactory
{
    private PositionLevelFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IPositionLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("028A903D") ,com.kingdee.eas.cp.bc.IPositionLevel.class);
    }
    
    public static com.kingdee.eas.cp.bc.IPositionLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("028A903D") ,com.kingdee.eas.cp.bc.IPositionLevel.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IPositionLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("028A903D"));
    }
    public static com.kingdee.eas.cp.bc.IPositionLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IPositionLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("028A903D"));
    }
}