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
     * Object: �������뵥 's ��¼ property 
     */
    public com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection)get("entries");
    }
    /**
     * Object:�������뵥's �Ƿ����������뵥property 
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
     * Object:�������뵥's �������λ��property 
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
     * Object:�������뵥's �Ƿ�ͳ��property 
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
     * Object: �������뵥 's ������ property 
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
     * Object: �������뵥 's �����������뵥 property 
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