package com.kingdee.eas.basedata.org;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPositionInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPositionInfo()
    {
        this("id");
    }
    protected AbstractPositionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:职位's 生效日期property 
     */
    public java.util.Date getEffectDate()
    {
        return getDate("effectDate");
    }
    public void setEffectDate(java.util.Date item)
    {
        setDate("effectDate", item);
    }
    /**
     * Object:职位's 失效日期property 
     */
    public java.util.Date getValiDate()
    {
        return getDate("valiDate");
    }
    public void setValiDate(java.util.Date item)
    {
        setDate("valiDate", item);
    }
    /**
     * Object: 职位 's 职务 property 
     */
    public com.kingdee.eas.basedata.org.JobInfo getJob()
    {
        return (com.kingdee.eas.basedata.org.JobInfo)get("job");
    }
    public void setJob(com.kingdee.eas.basedata.org.JobInfo item)
    {
        put("job", item);
    }
    /**
     * Object: 职位 's 行政组织 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAdminOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("adminOrgUnit");
    }
    public void setAdminOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("adminOrgUnit", item);
    }
    /**
     * Object:职位's 序号property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object: 职位 's HR组织 property 
     */
    public com.kingdee.eas.basedata.org.HROrgUnitInfo getHrOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.HROrgUnitInfo)get("hrOrgUnit");
    }
    public void setHrOrgUnit(com.kingdee.eas.basedata.org.HROrgUnitInfo item)
    {
        put("hrOrgUnit", item);
    }
    /**
     * Object: 职位 's 职位类别 property 
     */
    public com.kingdee.eas.basedata.hraux.PositionTypeInfo getPositionType()
    {
        return (com.kingdee.eas.basedata.hraux.PositionTypeInfo)get("PositionType");
    }
    public void setPositionType(com.kingdee.eas.basedata.hraux.PositionTypeInfo item)
    {
        put("PositionType", item);
    }
    /**
     * Object:职位's 失效状态property 
     */
    public com.kingdee.eas.framework.DeletedStatusEnum getDeletedStatus()
    {
        return com.kingdee.eas.framework.DeletedStatusEnum.getEnum(getInt("deletedStatus"));
    }
    public void setDeletedStatus(com.kingdee.eas.framework.DeletedStatusEnum item)
    {
		if (item != null) {
        setInt("deletedStatus", item.getValue());
		}
    }
    /**
     * Object: 职位 's 职位公有属性 property 
     */
    public com.kingdee.eas.hr.base.PositionTypeLdapInfo getPubProperLdap()
    {
        return (com.kingdee.eas.hr.base.PositionTypeLdapInfo)get("pubProperLdap");
    }
    public void setPubProperLdap(com.kingdee.eas.hr.base.PositionTypeLdapInfo item)
    {
        put("pubProperLdap", item);
    }
    /**
     * Object:职位's 是否负责人职位property 
     */
    public boolean isIsRespPosition()
    {
        return getBoolean("isRespPosition");
    }
    public void setIsRespPosition(boolean item)
    {
        setBoolean("isRespPosition", item);
    }
    /**
     * Object:职位's 异动考察期property 
     */
    public int getFluCheckTime()
    {
        return getInt("fluCheckTime");
    }
    public void setFluCheckTime(int item)
    {
        setInt("fluCheckTime", item);
    }
    /**
     * Object:职位's 职位全集排序码property 
     */
    public int getSortCode()
    {
        return getInt("sortCode");
    }
    public void setSortCode(int item)
    {
        setInt("sortCode", item);
    }
    /**
     * Object: 职位 's 职级 property 
     */
    public com.kingdee.eas.cp.bc.PositionLevelInfo getPositionLevel()
    {
        return (com.kingdee.eas.cp.bc.PositionLevelInfo)get("positionLevel");
    }
    public void setPositionLevel(com.kingdee.eas.cp.bc.PositionLevelInfo item)
    {
        put("positionLevel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("74AE612E");
    }
}