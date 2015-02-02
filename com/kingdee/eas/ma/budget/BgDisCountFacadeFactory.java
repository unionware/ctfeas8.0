package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgDisCountFacadeFactory
{
    private BgDisCountFacadeFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IBgDisCountFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F92EF741") ,com.kingdee.eas.ma.budget.IBgDisCountFacade.class);
    }
    
    public static com.kingdee.eas.ma.budget.IBgDisCountFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F92EF741") ,com.kingdee.eas.ma.budget.IBgDisCountFacade.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IBgDisCountFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F92EF741"));
    }
    public static com.kingdee.eas.ma.budget.IBgDisCountFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F92EF741"));
    }
}