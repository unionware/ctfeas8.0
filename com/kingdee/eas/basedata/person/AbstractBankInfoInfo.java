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
     * Object: ������Ϣ 's Ա�� property 
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
     * Object:������Ϣ's ��������property 
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
     * Object:������Ϣ's �����˻�property 
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
     * Object:������Ϣ's ���е�ַproperty 
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
     * Object:������Ϣ's ��;property 
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
     * Object:������Ϣ's ʡproperty 
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
     * Object:������Ϣ's ��property 
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