package com.kingdee.eas.basedata.centralpolicy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CentralpolicyFactory
{
    private CentralpolicyFactory()
    {
    }
    public static com.kingdee.eas.basedata.centralpolicy.ICentralpolicy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.centralpolicy.ICentralpolicy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("452858C5") ,com.kingdee.eas.basedata.centralpolicy.ICentralpolicy.class);
    }
    
    public static com.kingdee.eas.basedata.centralpolicy.ICentralpolicy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.centralpolicy.ICentralpolicy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("452858C5") ,com.kingdee.eas.basedata.centralpolicy.ICentralpolicy.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.centralpolicy.ICentralpolicy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.centralpolicy.ICentralpolicy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("452858C5"));
    }
    public static com.kingdee.eas.basedata.centralpolicy.ICentralpolicy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.centralpolicy.ICentralpolicy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("452858C5"));
    }
}