package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDailyLoanBillEntryInfo extends com.kingdee.eas.cp.bc.OtherExpenseEntryCoreBaseInfo implements Serializable 
{
    public AbstractDailyLoanBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDailyLoanBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.cp.bc.DailyLoanBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.DailyLoanBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.DailyLoanBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object:��¼'s �ѻ�����property 
     */
    public java.math.BigDecimal getAmountPaid()
    {
        return getBigDecimal("amountPaid");
    }
    public void setAmountPaid(java.math.BigDecimal item)
    {
        setBigDecimal("amountPaid", item);
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
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostDept()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costDept");
    }
    public void setCostDept(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costDept", item);
    }
    /**
     * Object:��¼'s �ϴ��ύ���property 
     */
    public java.math.BigDecimal getLastSubmitAmt()
    {
        return getBigDecimal("LastSubmitAmt");
    }
    public void setLastSubmitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("LastSubmitAmt", item);
    }
    /**
     * Object:��¼'s �ɹ���ͬIDproperty 
     */
    public String getPurContractID()
    {
        return getString("purContractID");
    }
    public void setPurContractID(String item)
    {
        setString("purContractID", item);
    }
    /**
     * Object:��¼'s �ɹ���ͬ��¼IDproperty 
     */
    public String getPurContractEntryID()
    {
        return getString("purContractEntryID");
    }
    public void setPurContractEntryID(String item)
    {
        setString("purContractEntryID", item);
    }
    /**
     * Object: ��¼ 's ְԱ property 
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
        return new BOSObjectType("F4884CE0");
    }
}