package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompanyVoucherNumEntryFactory
{
    private CompanyVoucherNumEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5AD708FB") ,com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5AD708FB") ,com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5AD708FB"));
    }
    public static com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ICompanyVoucherNumEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5AD708FB"));
    }
}