package com.kingdee.eas.basedata.master.cssp;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ISupplierCompanyBank extends ICoreBillEntryBase
{
    public SupplierCompanyBankInfo getSupplierCompanyBankInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierCompanyBankInfo getSupplierCompanyBankInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierCompanyBankInfo getSupplierCompanyBankInfo(String oql) throws BOSException, EASBizException;
    public SupplierCompanyBankCollection getSupplierCompanyBankCollection() throws BOSException;
    public SupplierCompanyBankCollection getSupplierCompanyBankCollection(EntityViewInfo view) throws BOSException;
    public SupplierCompanyBankCollection getSupplierCompanyBankCollection(String oql) throws BOSException;
}