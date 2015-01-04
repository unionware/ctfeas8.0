package com.kingdee.eas.basedata.org;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PositionFactory
{
    private PositionFactory()
    {
    }
    public static com.kingdee.eas.basedata.org.IPosition getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.org.IPosition)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("74AE612E") ,com.kingdee.eas.basedata.org.IPosition.class);
    }
    
    public static com.kingdee.eas.basedata.org.IPosition getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.org.IPosition)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("74AE612E") ,com.kingdee.eas.basedata.org.IPosition.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.org.IPosition getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.org.IPosition)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("74AE612E"));
    }
    public static com.kingdee.eas.basedata.org.IPosition getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.org.IPosition)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("74AE612E"));
    }
}