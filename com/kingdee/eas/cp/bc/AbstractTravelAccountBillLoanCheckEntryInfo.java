package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTravelAccountBillLoanCheckEntryInfo extends com.kingdee.eas.cp.bc.BizCollCheckEntryInfo implements Serializable 
{
    public AbstractTravelAccountBillLoanCheckEntryInfo()
    {
        this("id");
    }
    protected AbstractTravelAccountBillLoanCheckEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 借款核销表 's null property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillInfo getParent()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.cp.bc.TravelAccountBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 借款核销表 's 项目 property 
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
        return new BOSObjectType("C488E436");
    }
}