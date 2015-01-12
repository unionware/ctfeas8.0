package com.kingdee.eas.basedata.master.cssp;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierCompanyBankInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierCompanyBankInfo()
    {
        this("id");
    }
    protected AbstractSupplierCompanyBankInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӧ�̲�������'s ��������property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:��Ӧ�̲�������'s �����ʺ�property 
     */
    public String getBankAccount()
    {
        return getString("bankAccount");
    }
    public void setBankAccount(String item)
    {
        setString("bankAccount", item);
    }
    /**
     * Object:��Ӧ�̲�������'s ���е�ַproperty 
     */
    public String getBankAddress()
    {
        return getString("bankAddress");
    }
    public void setBankAddress(String item)
    {
        setString("bankAddress", item);
    }
    /**
     * Object: ��Ӧ�̲������� 's ��Ӧ�̲��� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo getSupplierCompanyInfo()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo)get("supplierCompanyInfo");
    }
    public void setSupplierCompanyInfo(com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo item)
    {
        put("supplierCompanyInfo", item);
    }
    /**
     * Object:��Ӧ�̲�������'s ʡproperty 
     */
    public String getProvince()
    {
        return getString("province");
    }
    public void setProvince(String item)
    {
        setString("province", item);
    }
    /**
     * Object:��Ӧ�̲�������'s ��property 
     */
    public String getCity()
    {
        return getString("city");
    }
    public void setCity(String item)
    {
        setString("city", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E62C04BD");
    }
}