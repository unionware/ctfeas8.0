package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RefDisCountBgFormFactory
{
    private RefDisCountBgFormFactory()
    {
    }
    public static com.kingdee.eas.ma.budget.IRefDisCountBgForm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IRefDisCountBgForm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78A6183C") ,com.kingdee.eas.ma.budget.IRefDisCountBgForm.class);
    }
    
    public static com.kingdee.eas.ma.budget.IRefDisCountBgForm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IRefDisCountBgForm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78A6183C") ,com.kingdee.eas.ma.budget.IRefDisCountBgForm.class, objectCtx);
    }
    public static com.kingdee.eas.ma.budget.IRefDisCountBgForm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IRefDisCountBgForm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78A6183C"));
    }
    public static com.kingdee.eas.ma.budget.IRefDisCountBgForm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.budget.IRefDisCountBgForm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78A6183C"));
    }
}