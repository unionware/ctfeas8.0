package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompanyVoucherNumEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCompanyVoucherNumEntryInfo()
    {
        this("id");
    }
    protected AbstractCompanyVoucherNumEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��˾ƾ֤�ַ�¼'s ƾ֤��property 
     */
    public String getVoucherNumber()
    {
        return getString("voucherNumber");
    }
    public void setVoucherNumber(String item)
    {
        setString("voucherNumber", item);
    }
    /**
     * Object: ��˾ƾ֤�ַ�¼ 's  property 
     */
    public com.kingdee.eas.cp.bc.CompanyVoucherNumInfo getParent()
    {
        return (com.kingdee.eas.cp.bc.CompanyVoucherNumInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.cp.bc.CompanyVoucherNumInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5AD708FB");
    }
}