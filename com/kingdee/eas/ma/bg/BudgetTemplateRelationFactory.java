package com.kingdee.eas.ma.bg;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BudgetTemplateRelationFactory
{
    private BudgetTemplateRelationFactory()
    {
    }
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1C455C3C") ,com.kingdee.eas.ma.bg.IBudgetTemplateRelation.class);
    }
    
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1C455C3C") ,com.kingdee.eas.ma.bg.IBudgetTemplateRelation.class, objectCtx);
    }
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1C455C3C"));
    }
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1C455C3C"));
    }
}