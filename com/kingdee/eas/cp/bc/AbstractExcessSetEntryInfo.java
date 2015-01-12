package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExcessSetEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractExcessSetEntryInfo()
    {
        this("id");
    }
    protected AbstractExcessSetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������ͳ���������÷�¼ 's �ɱ����� property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostCenter()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costCenter");
    }
    public void setCostCenter(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costCenter", item);
    }
    /**
     * Object:�������ͳ���������÷�¼'s ����%property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:�������ͳ���������÷�¼'s ��עproperty 
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
     * Object: �������ͳ���������÷�¼ 's  property 
     */
    public com.kingdee.eas.cp.bc.ExcessSetInfo getParent()
    {
        return (com.kingdee.eas.cp.bc.ExcessSetInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.cp.bc.ExcessSetInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C024106");
    }
}