package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvectionReqBillEntryInfo extends com.kingdee.eas.cp.bc.EvectionExpBillEntryBaseInfo implements Serializable 
{
    public AbstractEvectionReqBillEntryInfo()
    {
        this("id");
    }
    protected AbstractEvectionReqBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:分录's 同行人property 
     */
    public String getPartner()
    {
        return getString("partner");
    }
    public void setPartner(String item)
    {
        setString("partner", item);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.cp.bc.EvectionReqBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.EvectionReqBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.EvectionReqBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: 分录 's 项目 property 
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
     * Object: 分录 's 费用归属部门 property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostedDept()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costedDept");
    }
    public void setCostedDept(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costedDept", item);
    }
    /**
     * Object: 分录 's 职员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5902774E");
    }
}