package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BaseDataMatchFactory
{
    private BaseDataMatchFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBaseDataMatch getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatch)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A10194CC") ,com.kingdee.eas.cp.bc.IBaseDataMatch.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBaseDataMatch getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatch)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A10194CC") ,com.kingdee.eas.cp.bc.IBaseDataMatch.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBaseDataMatch getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatch)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A10194CC"));
    }
    public static com.kingdee.eas.cp.bc.IBaseDataMatch getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatch)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A10194CC"));
    }
}