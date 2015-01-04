package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HierarchyGetPersonFacadeFactory
{
    private HierarchyGetPersonFacadeFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B33AAE2E") ,com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade.class);
    }
    
    public static com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B33AAE2E") ,com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B33AAE2E"));
    }
    public static com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IHierarchyGetPersonFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B33AAE2E"));
    }
}