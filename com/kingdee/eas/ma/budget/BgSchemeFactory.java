package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgSchemeFactory
{
    private BgSchemeFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IBgScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4ABADCB") ,com.kingdee.eas.ma.budget.IBgScheme.class);
    }
    
    public static com.kingdee.eas.ma.budget.IBgScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4ABADCB") ,com.kingdee.eas.ma.budget.IBgScheme.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IBgScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4ABADCB"));
    }
    public static com.kingdee.eas.ma.budget.IBgScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4ABADCB"));
    }
}