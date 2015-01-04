package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherExpenseBillInfo extends com.kingdee.eas.cp.bc.ExpenseReqBillInfo implements Serializable 
{
    public AbstractOtherExpenseBillInfo()
    {
        this("id");
    }
    protected AbstractOtherExpenseBillInfo(String pkField)
    {
        super(pkField);
        put("linkOthExpense", new com.kingdee.eas.cp.bc.LinkOthExpenseCollection());
        put("entries", new com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection());
    }
    /**
     * Object: 费用申请单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection)get("entries");
    }
    /**
     * Object:费用申请单's 是否变更费用申请单property 
     */
    public boolean isIsChanged()
    {
        return getBoolean("isChanged");
    }
    public void setIsChanged(boolean item)
    {
        setBoolean("isChanged", item);
    }
    /**
     * Object:费用申请单's 总申请金额本位币property 
     */
    public java.math.BigDecimal getApplyAmount()
    {
        return getBigDecimal("applyAmount");
    }
    public void setApplyAmount(java.math.BigDecimal item)
    {
        setBigDecimal("applyAmount", item);
    }
    /**
     * Object:费用申请单's 是否统购property 
     */
    public boolean isIsCentralPur()
    {
        return getBoolean("isCentralPur");
    }
    public void setIsCentralPur(boolean item)
    {
        setBoolean("isCentralPur", item);
    }
    /**
     * Object: 费用申请单 's 服务部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getServiceDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("serviceDept");
    }
    public void setServiceDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("serviceDept", item);
    }
    /**
     * Object: 费用申请单 's 关联费用申请单 property 
     */
    public com.kingdee.eas.cp.bc.LinkOthExpenseCollection getLinkOthExpense()
    {
        return (com.kingdee.eas.cp.bc.LinkOthExpenseCollection)get("linkOthExpense");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E76173AD");
    }
}