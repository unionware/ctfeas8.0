package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BaseDataMatchEntryFactory
{
    private BaseDataMatchEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBaseDataMatchEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatchEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C4F7A306") ,com.kingdee.eas.cp.bc.IBaseDataMatchEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBaseDataMatchEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatchEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C4F7A306") ,com.kingdee.eas.cp.bc.IBaseDataMatchEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBaseDataMatchEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatchEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C4F7A306"));
    }
    public static com.kingdee.eas.cp.bc.IBaseDataMatchEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBaseDataMatchEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C4F7A306"));
    }
}