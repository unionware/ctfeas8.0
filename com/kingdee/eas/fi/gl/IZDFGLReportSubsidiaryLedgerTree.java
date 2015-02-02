package com.kingdee.eas.fi.gl;

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

public interface IZDFGLReportSubsidiaryLedgerTree extends IBizCtrl
{
    public ReportResultInfo findAccountTree(EntityViewInfo condition, CompanyOrgUnitInfo company) throws BOSException, EASBizException;
}