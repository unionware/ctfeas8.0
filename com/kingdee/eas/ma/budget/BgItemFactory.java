package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgItemFactory
{
    private BgItemFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IBgItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF67C4B9") ,com.kingdee.eas.ma.budget.IBgItem.class);
    }
    
    public static com.kingdee.eas.ma.budget.IBgItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF67C4B9") ,com.kingdee.eas.ma.budget.IBgItem.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IBgItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF67C4B9"));
    }
    public static com.kingdee.eas.ma.budget.IBgItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF67C4B9"));
    }
}