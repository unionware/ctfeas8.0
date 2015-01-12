package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLinkOthExpenseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractLinkOthExpenseInfo()
    {
        this("id");
    }
    protected AbstractLinkOthExpenseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����������뵥 's �����������뵥 property 
     */
    public com.kingdee.eas.cp.bc.OtherExpenseBillInfo getOtherExpenseBill()
    {
        return (com.kingdee.eas.cp.bc.OtherExpenseBillInfo)get("otherExpenseBill");
    }
    public void setOtherExpenseBill(com.kingdee.eas.cp.bc.OtherExpenseBillInfo item)
    {
        put("otherExpenseBill", item);
    }
    /**
     * Object:�����������뵥's ��λ�����ý��property 
     */
    public java.math.BigDecimal getUsedAmount()
    {
        return getBigDecimal("usedAmount");
    }
    public void setUsedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("usedAmount", item);
    }
    /**
     * Object: �����������뵥 's  property 
     */
    public com.kingdee.eas.cp.bc.OtherExpenseBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.OtherExpenseBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.OtherExpenseBillInfo item)
    {
        put("bill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F83AF2D");
    }
}