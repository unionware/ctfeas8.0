package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgAvlBalCalFacadeFactory
{
    private BgAvlBalCalFacadeFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IBgAvlBalCalFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAvlBalCalFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5DCD038C") ,com.kingdee.eas.ma.budget.IBgAvlBalCalFacade.class);
    }
    
    public static com.kingdee.eas.ma.budget.IBgAvlBalCalFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAvlBalCalFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5DCD038C") ,com.kingdee.eas.ma.budget.IBgAvlBalCalFacade.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IBgAvlBalCalFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAvlBalCalFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5DCD038C"));
    }
    public static com.kingdee.eas.ma.budget.IBgAvlBalCalFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgAvlBalCalFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5DCD038C"));
    }
}