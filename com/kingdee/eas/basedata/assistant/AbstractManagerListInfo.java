package com.kingdee.eas.basedata.assistant;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractManagerListInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractManagerListInfo()
    {
        this("id");
    }
    protected AbstractManagerListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀ�����б� 's ��Ŀ������� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getProjectManage()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("projectManage");
    }
    public void setProjectManage(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("projectManage", item);
    }
    /**
     * Object:��Ŀ�����б�'s ��Ŀ��������property 
     */
    public String getManagerName()
    {
        return getString("managerName");
    }
    public void setManagerName(String item)
    {
        setString("managerName", item);
    }
    /**
     * Object: ��Ŀ�����б� 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("project", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C9EDDE40");
    }
}