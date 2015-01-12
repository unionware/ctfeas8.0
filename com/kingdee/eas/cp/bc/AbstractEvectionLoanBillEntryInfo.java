package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvectionLoanBillEntryInfo extends com.kingdee.eas.cp.bc.EvectionExpBillEntryBaseInfo implements Serializable 
{
    public AbstractEvectionLoanBillEntryInfo()
    {
        this("id");
    }
    protected AbstractEvectionLoanBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.cp.bc.EvectionLoanBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.EvectionLoanBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.EvectionLoanBillInfo item)
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
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostedDept()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costedDept");
    }
    public void setCostedDept(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costedDept", item);
    }
    /**
     * Object:��¼'s �ϴ��ύ���property 
     */
    public java.math.BigDecimal getLastSubmitAmt()
    {
        return getBigDecimal("lastSubmitAmt");
    }
    public void setLastSubmitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lastSubmitAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9DB7201A");
    }
}