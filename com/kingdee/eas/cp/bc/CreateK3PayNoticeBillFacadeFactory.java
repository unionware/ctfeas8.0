package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CreateK3PayNoticeBillFacadeFactory
{
    private CreateK3PayNoticeBillFacadeFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8E09203F") ,com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade.class);
    }
    
    public static com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8E09203F") ,com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8E09203F"));
    }
    public static com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8E09203F"));
    }
}