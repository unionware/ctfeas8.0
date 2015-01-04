package com.kingdee.eas.basedata.assistant;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProjectInfo()
    {
        this("id");
    }
    protected AbstractProjectInfo(String pkField)
    {
        super(pkField);
        put("managerList", new com.kingdee.eas.basedata.assistant.ManagerListCollection());
    }
    /**
     * Object:项目's 项目类型property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectTypeEnum getType()
    {
        return com.kingdee.eas.basedata.assistant.ProjectTypeEnum.getEnum(getInt("type"));
    }
    public void setType(com.kingdee.eas.basedata.assistant.ProjectTypeEnum item)
    {
		if (item != null) {
        setInt("type", item.getValue());
		}
    }
    /**
     * Object:项目's 项目状态property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectStatus getStatus()
    {
        return com.kingdee.eas.basedata.assistant.ProjectStatus.getEnum(getInt("status"));
    }
    public void setStatus(com.kingdee.eas.basedata.assistant.ProjectStatus item)
    {
		if (item != null) {
        setInt("status", item.getValue());
		}
    }
    /**
     * Object:项目's 责任人property 
     */
    public String getDutyPerson()
    {
        return getString("dutyPerson");
    }
    public void setDutyPerson(String item)
    {
        setString("dutyPerson", item);
    }
    /**
     * Object:项目's 计划开始时间property 
     */
    public java.util.Date getScheduleStartDate()
    {
        return getDate("scheduleStartDate");
    }
    public void setScheduleStartDate(java.util.Date item)
    {
        setDate("scheduleStartDate", item);
    }
    /**
     * Object:项目's 计划结束时间property 
     */
    public java.util.Date getSchedulEndDate()
    {
        return getDate("schedulEndDate");
    }
    public void setSchedulEndDate(java.util.Date item)
    {
        setDate("schedulEndDate", item);
    }
    /**
     * Object: 项目 's 父节点 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getParent()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目's 实际开始时间property 
     */
    public java.util.Date getFactStartDate()
    {
        return getDate("factStartDate");
    }
    public void setFactStartDate(java.util.Date item)
    {
        setDate("factStartDate", item);
    }
    /**
     * Object:项目's 实际结束时间property 
     */
    public java.util.Date getFactEndDate()
    {
        return getDate("factEndDate");
    }
    public void setFactEndDate(java.util.Date item)
    {
        setDate("factEndDate", item);
    }
    /**
     * Object:项目's 项目进度property 
     */
    public java.math.BigDecimal getProcess()
    {
        return getBigDecimal("process");
    }
    public void setProcess(java.math.BigDecimal item)
    {
        setBigDecimal("process", item);
    }
    /**
     * Object:项目's 附件property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: 项目 's 项目经理 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPrjManager()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("prjManager");
    }
    public void setPrjManager(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("prjManager", item);
    }
    /**
     * Object: 项目 's 所属公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:项目's 是否明细项目property 
     */
    public boolean isIsListItem()
    {
        return getBoolean("isListItem");
    }
    public void setIsListItem(boolean item)
    {
        setBoolean("isListItem", item);
    }
    /**
     * Object: 项目 's 项目地点 property 
     */
    public com.kingdee.eas.basedata.assistant.AddressInfo getAddress()
    {
        return (com.kingdee.eas.basedata.assistant.AddressInfo)get("address");
    }
    public void setAddress(com.kingdee.eas.basedata.assistant.AddressInfo item)
    {
        put("address", item);
    }
    /**
     * Object:项目's 是否系统生成property 
     */
    public boolean isIsSysCreate()
    {
        return getBoolean("isSysCreate");
    }
    public void setIsSysCreate(boolean item)
    {
        setBoolean("isSysCreate", item);
    }
    /**
     * Object: 项目 's 项目经理列表 property 
     */
    public com.kingdee.eas.basedata.assistant.ManagerListCollection getManagerList()
    {
        return (com.kingdee.eas.basedata.assistant.ManagerListCollection)get("managerList");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DADE05EE");
    }
}