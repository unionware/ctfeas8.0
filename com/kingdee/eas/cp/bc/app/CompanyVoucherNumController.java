package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.CompanyVoucherNumInfo;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.eas.cp.bc.CompanyVoucherNumCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CompanyVoucherNumController extends DataBaseController
{
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(Context ctx) throws BOSException, RemoteException;
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(Context ctx, String oql) throws BOSException, RemoteException;
}