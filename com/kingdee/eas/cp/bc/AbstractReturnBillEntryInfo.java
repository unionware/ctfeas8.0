package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReturnBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReturnBillEntryInfo()
    {
        this("id");
    }
    protected AbstractReturnBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.cp.bc.ReturnBillInfo getParent()
    {
        return (com.kingdee.eas.cp.bc.ReturnBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.cp.bc.ReturnBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 还款日期property 
     */
    public java.util.Date getReturnDate()
    {
        return getDate("returnDate");
    }
    public void setReturnDate(java.util.Date item)
    {
        setDate("returnDate", item);
    }
    /**
     * Object:分录's 还款金额property 
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
     * Object:分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E04EB53D");
    }
}