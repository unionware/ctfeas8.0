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
     * Object:ְλ's ��Ч����property 
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
     * Object:ְλ's ʧЧ����property 
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
     * Object: ְλ 's ְ�� property 
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
     * Object: ְλ 's ������֯ property 
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
     * Object:ְλ's ���property 
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
     * Object: ְλ 's HR��֯ property 
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
     * Object: ְλ 's ְλ��� property 
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
     * Object:ְλ's ʧЧ״̬property 
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
     * Object: ְλ 's ְλ�������� property 
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
     * Object:ְλ's �Ƿ�����ְλproperty 
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
     * Object:ְλ's �춯������property 
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
     * Object:ְλ's ְλȫ��������property 
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
     * Object: ְλ 's ְ�� property 
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