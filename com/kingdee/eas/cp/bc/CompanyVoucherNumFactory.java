package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompanyVoucherNumFactory
{
    private CompanyVoucherNumFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNum getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNum)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CF7D89B7") ,com.kingdee.eas.cp.bc.ICompanyVoucherNum.class);
    }
    
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNum getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNum)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CF7D89B7") ,com.kingdee.eas.cp.bc.ICompanyVoucherNum.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNum getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNum)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CF7D89B7"));
    }
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNum getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNum)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CF7D89B7"));
    }
}