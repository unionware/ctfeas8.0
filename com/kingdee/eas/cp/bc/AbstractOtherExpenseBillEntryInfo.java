package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherExpenseBillEntryInfo extends com.kingdee.eas.cp.bc.OtherExpenseEntryCoreBaseInfo implements Serializable 
{
    public AbstractOtherExpenseBillEntryInfo()
    {
        this("id");
    }
    protected AbstractOtherExpenseBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's �����������뵥ID property 
     */
    public com.kingdee.eas.cp.bc.OtherExpenseBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.OtherExpenseBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.OtherExpenseBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: ��¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ��¼ 's ���ù������� property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostedDept()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costedDept");
    }
    public void setCostedDept(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costedDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5C4E8C5");
    }
}