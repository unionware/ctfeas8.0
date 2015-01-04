package com.kingdee.eas.basedata.master.cssp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierCompanyBankFactory
{
    private SupplierCompanyBankFactory()
    {
    }
    public static com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E62C04BD") ,com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank.class);
    }
    
    public static com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E62C04BD") ,com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E62C04BD"));
    }
    public static com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E62C04BD"));
    }
}