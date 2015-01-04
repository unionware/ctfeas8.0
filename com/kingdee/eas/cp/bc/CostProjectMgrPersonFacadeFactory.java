package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostProjectMgrPersonFacadeFactory
{
    private CostProjectMgrPersonFacadeFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E2EC4939") ,com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade.class);
    }
    
    public static com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E2EC4939") ,com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E2EC4939"));
    }
    public static com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICostProjectMgrPersonFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E2EC4939"));
    }
}