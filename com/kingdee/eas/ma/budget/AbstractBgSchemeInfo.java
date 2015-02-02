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
     * Object:预算方案's 开始时间property 
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
     * Object:预算方案's 结束时间property 
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
     * Object:预算方案's 是否执行property 
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
     * Object: 预算方案 's 上级方案 property 
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
     * Object: 预算方案 's 预算方案节点 property 
     */
    public com.kingdee.eas.ma.budget.BgSchemeNodeCollection getBgSchemeNodes()
    {
        return (com.kingdee.eas.ma.budget.BgSchemeNodeCollection)get("bgSchemeNodes");
    }
    /**
     * Object: 预算方案 's 组织 property 
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
     * Object:预算方案's 编制周期property 
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
     * Object:预算方案's 根方案property 
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
     * Object:预算方案's 正式年度方案property 
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
     * Object:预算方案's 方案属性property 
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
     * Object:预算方案's 按方案提交property 
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
     * Object:预算方案's 是否归档property 
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
     * Object:预算方案's 是否按方案提交property 
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
     * Object:预算方案's 是否为复制预算方案property 
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