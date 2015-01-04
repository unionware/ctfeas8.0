package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class Bc2K3VoucherFacadeFactory
{
    private Bc2K3VoucherFacadeFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBc2K3VoucherFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBc2K3VoucherFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("12FE6D2D") ,com.kingdee.eas.cp.bc.IBc2K3VoucherFacade.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBc2K3VoucherFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBc2K3VoucherFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("12FE6D2D") ,com.kingdee.eas.cp.bc.IBc2K3VoucherFacade.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBc2K3VoucherFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBc2K3VoucherFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("12FE6D2D"));
    }
    public static com.kingdee.eas.cp.bc.IBc2K3VoucherFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBc2K3VoucherFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("12FE6D2D"));
    }
}