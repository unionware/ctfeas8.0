package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ICompanyVoucherNum extends IDataBase
{
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompanyVoucherNumInfo getCompanyVoucherNumInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection() throws BOSException;
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(EntityViewInfo view) throws BOSException;
    public CompanyVoucherNumCollection getCompanyVoucherNumCollection(String oql) throws BOSException;
}