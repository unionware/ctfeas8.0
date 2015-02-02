package com.kingdee.eas.ma.crbg.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBeginHiDetailBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBeginHiDetailBillInfo()
    {
        this("id");
    }
    protected AbstractBeginHiDetailBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ڳ���ʷ��ϸ�� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompanyOrg()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("CompanyOrg");
    }
    public void setCompanyOrg(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("CompanyOrg", item);
    }
    /**
     * Object:�ڳ���ʷ��ϸ��'s ��������property 
     */
    public java.util.Date getDateOfEntry()
    {
        return getDate("DateOfEntry");
    }
    public void setDateOfEntry(java.util.Date item)
    {
        setDate("DateOfEntry", item);
    }
    /**
     * Object: �ڳ���ʷ��ϸ�� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getFcurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("Fcurrency");
    }
    public void setFcurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("Fcurrency", item);
    }
    /**
     * Object: �ڳ���ʷ��ϸ�� 's ���������� property 
     */
    public com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo getFCAA()
    {
        return (com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo)get("FCAA");
    }
    public void setFCAA(com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo item)
    {
        put("FCAA", item);
    }
    /**
     * Object: �ڳ���ʷ��ϸ�� 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getFAccountID()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("FAccountID");
    }
    public void setFAccountID(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("FAccountID", item);
    }
    /**
     * Object:�ڳ���ʷ��ϸ��'s ժҪproperty 
     */
    public String getContents()
    {
        return getString("Contents");
    }
    public void setContents(String item)
    {
        setString("Contents", item);
    }
    /**
     * Object:�ڳ���ʷ��ϸ��'s ԭ�ҽ��property 
     */
    public java.math.BigDecimal getOriginalCurrency()
    {
        return getBigDecimal("OriginalCurrency");
    }
    public void setOriginalCurrency(java.math.BigDecimal item)
    {
        setBigDecimal("OriginalCurrency", item);
    }
    /**
     * Object:�ڳ���ʷ��ϸ��'s ��λ�ҽ��property 
     */
    public java.math.BigDecimal getCurrencyAmount()
    {
        return getBigDecimal("CurrencyAmount");
    }
    public void setCurrencyAmount(java.math.BigDecimal item)
    {
        setBigDecimal("CurrencyAmount", item);
    }
    /**
     * Object:�ڳ���ʷ��ϸ��'s ����ҽ��property 
     */
    public java.math.BigDecimal getReportingCurrency()
    {
        return getBigDecimal("ReportingCurrency");
    }
    public void setReportingCurrency(java.math.BigDecimal item)
    {
        setBigDecimal("ReportingCurrency", item);
    }
    /**
     * Object:�ڳ���ʷ��ϸ��'s �������property 
     */
    public com.kingdee.eas.fi.gl.EntryDC getBorrowingDirection()
    {
        return com.kingdee.eas.fi.gl.EntryDC.getEnum(getInt("BorrowingDirection"));
    }
    public void setBorrowingDirection(com.kingdee.eas.fi.gl.EntryDC item)
    {
		if (item != null) {
        setInt("BorrowingDirection", item.getValue());
		}
    }
    /**
     * Object: �ڳ���ʷ��ϸ�� 's ������ property 
     */
    public com.kingdee.eas.basedata.master.auxacct.ImpAssistantHGInfo getAssistGrp()
    {
        return (com.kingdee.eas.basedata.master.auxacct.ImpAssistantHGInfo)get("assistGrp");
    }
    public void setAssistGrp(com.kingdee.eas.basedata.master.auxacct.ImpAssistantHGInfo item)
    {
        put("assistGrp", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8A5194A7");
    }
}