package com.kingdee.eas.ma.nbudget;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BgCalculateSchemeDisFacadeFactory
{
    private BgCalculateSchemeDisFacadeFactory()
    {
    }
    public static com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF6307DF") ,com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade.class);
    }
    
    public static com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF6307DF") ,com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade.class, objectCtx);
    }
    public static com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF6307DF"));
    }
    public static com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.ma.nbudget.IBgCalculateSchemeDisFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF6307DF"));
    }
}