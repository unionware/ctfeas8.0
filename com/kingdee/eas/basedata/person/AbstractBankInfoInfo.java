package com.kingdee.eas.basedata.person;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBankInfoInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBankInfoInfo()
    {
        this("id");
    }
    protected AbstractBankInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 银行信息 's 员工 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object:银行信息's 开户银行property 
     */
    public String getBankName()
    {
        return getString("bankName");
    }
    public void setBankName(String item)
    {
        setString("bankName", item);
    }
    /**
     * Object:银行信息's 银行账户property 
     */
    public String getBandAcctNumber()
    {
        return getString("bandAcctNumber");
    }
    public void setBandAcctNumber(String item)
    {
        setString("bandAcctNumber", item);
    }
    /**
     * Object:银行信息's 银行地址property 
     */
    public String getBandAddr()
    {
        return getString("bandAddr");
    }
    public void setBandAddr(String item)
    {
        setString("bandAddr", item);
    }
    /**
     * Object:银行信息's 用途property 
     */
    public String getUsage()
    {
        return getString("usage");
    }
    public void setUsage(String item)
    {
        setString("usage", item);
    }
    /**
     * Object:银行信息's 省property 
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
     * Object:银行信息's 市property 
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
        return new BOSObjectType("79C8FDA2");
    }
}