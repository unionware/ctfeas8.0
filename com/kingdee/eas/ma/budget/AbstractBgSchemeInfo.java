package com.kingdee.eas.ma.budget;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBgSchemeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBgSchemeInfo()
    {
        this("id");
    }
    protected AbstractBgSchemeInfo(String pkField)
    {
        super(pkField);
        put("bgSchemeNodes", new com.kingdee.eas.ma.budget.BgSchemeNodeCollection());
    }
    /**
     * Object:Ԥ�㷽��'s ��ʼʱ��property 
     */
    public java.util.Date getDateFrom()
    {
        return getDate("dateFrom");
    }
    public void setDateFrom(java.util.Date item)
    {
        setDate("dateFrom", item);
    }
    /**
     * Object:Ԥ�㷽��'s ����ʱ��property 
     */
    public java.util.Date getDateTo()
    {
        return getDate("dateTo");
    }
    public void setDateTo(java.util.Date item)
    {
        setDate("dateTo", item);
    }
    /**
     * Object:Ԥ�㷽��'s �Ƿ�ִ��property 
     */
    public boolean isIsFormal()
    {
        return getBoolean("isFormal");
    }
    public void setIsFormal(boolean item)
    {
        setBoolean("isFormal", item);
    }
    /**
     * Object: Ԥ�㷽�� 's �ϼ����� property 
     */
    public com.kingdee.eas.ma.budget.BgSchemeInfo getParentScheme()
    {
        return (com.kingdee.eas.ma.budget.BgSchemeInfo)get("parentScheme");
    }
    public void setParentScheme(com.kingdee.eas.ma.budget.BgSchemeInfo item)
    {
        put("parentScheme", item);
    }
    /**
     * Object: Ԥ�㷽�� 's Ԥ�㷽���ڵ� property 
     */
    public com.kingdee.eas.ma.budget.BgSchemeNodeCollection getBgSchemeNodes()
    {
        return (com.kingdee.eas.ma.budget.BgSchemeNodeCollection)get("bgSchemeNodes");
    }
    /**
     * Object: Ԥ�㷽�� 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:Ԥ�㷽��'s ��������property 
     */
    public com.kingdee.eas.ma.budget.BgPeriodEnum getPeriod()
    {
        return com.kingdee.eas.ma.budget.BgPeriodEnum.getEnum(getInt("period"));
    }
    public void setPeriod(com.kingdee.eas.ma.budget.BgPeriodEnum item)
    {
		if (item != null) {
        setInt("period", item.getValue());
		}
    }
    /**
     * Object:Ԥ�㷽��'s ������property 
     */
    public com.kingdee.bos.util.BOSUuid getRootId()
    {
        return getBOSUuid("rootId");
    }
    public void setRootId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("rootId", item);
    }
    /**
     * Object:Ԥ�㷽��'s ��ʽ��ȷ���property 
     */
    public boolean isFormallyYearScheme()
    {
        return getBoolean("formallyYearScheme");
    }
    public void setFormallyYearScheme(boolean item)
    {
        setBoolean("formallyYearScheme", item);
    }
    /**
     * Object:Ԥ�㷽��'s ��������property 
     */
    public com.kingdee.eas.ma.budget.BgSchemeStateEnum getState()
    {
        return com.kingdee.eas.ma.budget.BgSchemeStateEnum.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.ma.budget.BgSchemeStateEnum item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    /**
     * Object:Ԥ�㷽��'s �������ύproperty 
     */
    public boolean isIsSubmitByScheme()
    {
        return getBoolean("isSubmitByScheme");
    }
    public void setIsSubmitByScheme(boolean item)
    {
        setBoolean("isSubmitByScheme", item);
    }
    /**
     * Object:Ԥ�㷽��'s �Ƿ�鵵property 
     */
    public boolean isIsFiling()
    {
        return getBoolean("isFiling");
    }
    public void setIsFiling(boolean item)
    {
        setBoolean("isFiling", item);
    }
    /**
     * Object:Ԥ�㷽��'s �Ƿ񰴷����ύproperty 
     */
    public boolean isIsReportByScheme()
    {
        return getBoolean("isReportByScheme");
    }
    public void setIsReportByScheme(boolean item)
    {
        setBoolean("isReportByScheme", item);
    }
    /**
     * Object:Ԥ�㷽��'s �Ƿ�Ϊ����Ԥ�㷽��property 
     */
    public boolean isIsCopyScheme()
    {
        return getBoolean("isCopyScheme");
    }
    public void setIsCopyScheme(boolean item)
    {
        setBoolean("isCopyScheme", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4ABADCB");
    }
}