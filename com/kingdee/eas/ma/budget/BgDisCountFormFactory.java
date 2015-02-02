package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgDisCountFormFactory
{
    private BgDisCountFormFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IBgDisCountForm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountForm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A96AD12B") ,com.kingdee.eas.ma.budget.IBgDisCountForm.class);
    }
    
    public static com.kingdee.eas.ma.budget.IBgDisCountForm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountForm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A96AD12B") ,com.kingdee.eas.ma.budget.IBgDisCountForm.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IBgDisCountForm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountForm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A96AD12B"));
    }
    public static com.kingdee.eas.ma.budget.IBgDisCountForm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IBgDisCountForm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A96AD12B"));
    }
}