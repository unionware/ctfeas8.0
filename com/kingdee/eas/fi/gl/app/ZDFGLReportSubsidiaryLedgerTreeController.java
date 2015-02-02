package com.kingdee.eas.fi.gl.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.fi.gl.ReportResultInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ZDFGLReportSubsidiaryLedgerTreeController extends BizController
{
    public ReportResultInfo findAccountTree(Context ctx, EntityViewInfo condition, CompanyOrgUnitInfo company) throws BOSException, EASBizException, RemoteException;
}