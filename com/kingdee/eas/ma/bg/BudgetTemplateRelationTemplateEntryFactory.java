package com.kingdee.eas.ma.bg;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BudgetTemplateRelationTemplateEntryFactory
{
    private BudgetTemplateRelationTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("83BE9D3C") ,com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry.class);
    }
    
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("83BE9D3C") ,com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("83BE9D3C"));
    }
    public static com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("83BE9D3C"));
    }
}