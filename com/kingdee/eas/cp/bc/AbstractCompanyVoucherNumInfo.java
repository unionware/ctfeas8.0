package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompanyVoucherNumInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCompanyVoucherNumInfo()
    {
        this("id");
    }
    protected AbstractCompanyVoucherNumInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.cp.bc.CompanyVoucherNumEntryCollection());
    }
    /**
     * Object: 公司凭证字 's null property 
     */
    public com.kingdee.eas.cp.bc.CompanyVoucherNumEntryCollection getEntry()
    {
        return (com.kingdee.eas.cp.bc.CompanyVoucherNumEntryCollection)get("entry");
    }
    /**
     * Object: 公司凭证字 's 财务组织编码 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompanyNumber()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("companyNumber");
    }
    public void setCompanyNumber(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("companyNumber", item);
    }
    /**
     * Object:公司凭证字's 财务组织名称property 
     */
    public String getCompanyName()
    {
        return getString("companyName");
    }
    public void setCompanyName(String item)
    {
        setString("companyName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CF7D89B7");
    }
}