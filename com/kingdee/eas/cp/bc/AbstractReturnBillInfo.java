package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReturnBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractReturnBillInfo()
    {
        this("id");
    }
    protected AbstractReturnBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.cp.bc.ReturnBillEntryCollection());
    }
    /**
     * Object: ��� 's ��¼ property 
     */
    public com.kingdee.eas.cp.bc.ReturnBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.cp.bc.ReturnBillEntryCollection)get("entrys");
    }
    /**
     * Object:���'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:���'s ����property 
     */
    public String getLoanBillNumber()
    {
        return getString("loanBillNumber");
    }
    public void setLoanBillNumber(String item)
    {
        setString("loanBillNumber", item);
    }
    /**
     * Object:���'s �����property 
     */
    public java.math.BigDecimal getLoanAmount()
    {
        return getBigDecimal("loanAmount");
    }
    public void setLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("loanAmount", item);
    }
    /**
     * Object: ��� 's ����� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getLoanor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("loanor");
    }
    public void setLoanor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("loanor", item);
    }
    /**
     * Object:���'s �����ý��property 
     */
    public java.math.BigDecimal getLoanBillAmountBalance()
    {
        return getBigDecimal("loanBillAmountBalance");
    }
    public void setLoanBillAmountBalance(java.math.BigDecimal item)
    {
        setBigDecimal("loanBillAmountBalance", item);
    }
    /**
     * Object:���'s ������property 
     */
    public java.math.BigDecimal getReturnAmount()
    {
        return getBigDecimal("returnAmount");
    }
    public void setReturnAmount(java.math.BigDecimal item)
    {
        setBigDecimal("returnAmount", item);
    }
    /**
     * Object:���'s ״̬property 
     */
    public com.kingdee.eas.cp.bc.ReturnStateEnum getBillState()
    {
        return com.kingdee.eas.cp.bc.ReturnStateEnum.getEnum(getString("billState"));
    }
    public void setBillState(com.kingdee.eas.cp.bc.ReturnStateEnum item)
    {
		if (item != null) {
        setString("billState", item.getValue());
		}
    }
    /**
     * Object:���'s �������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:���'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:���'s ���������property 
     */
    public com.kingdee.eas.cp.bc.ReturnBillTypeEnum getReturnBillType()
    {
        return com.kingdee.eas.cp.bc.ReturnBillTypeEnum.getEnum(getString("returnBillType"));
    }
    public void setReturnBillType(com.kingdee.eas.cp.bc.ReturnBillTypeEnum item)
    {
		if (item != null) {
        setString("returnBillType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F397BC35");
    }
}