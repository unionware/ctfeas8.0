package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTravelAccountBillEntryInfo extends com.kingdee.eas.cp.bc.EvectionExpBillEntryBaseInfo implements Serializable 
{
    public AbstractTravelAccountBillEntryInfo()
    {
        this("id");
    }
    protected AbstractTravelAccountBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.TravelAccountBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: 分录 's 业务类别 property 
     */
    public com.kingdee.eas.cp.bc.OperationTypeInfo getOperationType()
    {
        return (com.kingdee.eas.cp.bc.OperationTypeInfo)get("operationType");
    }
    public void setOperationType(com.kingdee.eas.cp.bc.OperationTypeInfo item)
    {
        put("operationType", item);
    }
    /**
     * Object: 分录 's 费用归属部门 property 
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
     * Object: 分录 's 费用承担公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:分录's 应收状态property 
     */
    public com.kingdee.eas.cp.bc.EntryStateEnum getReceiveState()
    {
        return com.kingdee.eas.cp.bc.EntryStateEnum.getEnum(getInt("receiveState"));
    }
    public void setReceiveState(com.kingdee.eas.cp.bc.EntryStateEnum item)
    {
		if (item != null) {
        setInt("receiveState", item.getValue());
		}
    }
    /**
     * Object:分录's 应付状态property 
     */
    public com.kingdee.eas.cp.bc.EntryStateEnum getPayState()
    {
        return com.kingdee.eas.cp.bc.EntryStateEnum.getEnum(getInt("payState"));
    }
    public void setPayState(com.kingdee.eas.cp.bc.EntryStateEnum item)
    {
		if (item != null) {
        setInt("payState", item.getValue());
		}
    }
    /**
     * Object: 分录 's 出差地域 property 
     */
    public com.kingdee.eas.cp.bc.TravelAreaInfo getTravelArea()
    {
        return (com.kingdee.eas.cp.bc.TravelAreaInfo)get("travelArea");
    }
    public void setTravelArea(com.kingdee.eas.cp.bc.TravelAreaInfo item)
    {
        put("travelArea", item);
    }
    /**
     * Object:分录's 住宿天数property 
     */
    public java.math.BigDecimal getRoomDays()
    {
        return getBigDecimal("roomDays");
    }
    public void setRoomDays(java.math.BigDecimal item)
    {
        setBigDecimal("roomDays", item);
    }
    /**
     * Object:分录's 补助天数property 
     */
    public java.math.BigDecimal getAllowanceDays()
    {
        return getBigDecimal("allowanceDays");
    }
    public void setAllowanceDays(java.math.BigDecimal item)
    {
        setBigDecimal("allowanceDays", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getPurpose()
    {
        return getString("purpose");
    }
    public void setPurpose(String item)
    {
        setString("purpose", item);
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
     * Object:分录's 不含税金额property 
     */
    public java.math.BigDecimal getNoTaxAmt()
    {
        return getBigDecimal("noTaxAmt");
    }
    public void setNoTaxAmt(java.math.BigDecimal item)
    {
        setBigDecimal("noTaxAmt", item);
    }
    /**
     * Object:分录's 税率property 
     */
    public java.math.BigDecimal getTaxRate()
    {
        return getBigDecimal("taxRate");
    }
    public void setTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("taxRate", item);
    }
    /**
     * Object:分录's 税额property 
     */
    public java.math.BigDecimal getTax()
    {
        return getBigDecimal("tax");
    }
    public void setTax(java.math.BigDecimal item)
    {
        setBigDecimal("tax", item);
    }
    /**
     * Object:分录's 本位币不含税金额property 
     */
    public java.math.BigDecimal getNoTaxAmtLocal()
    {
        return getBigDecimal("noTaxAmtLocal");
    }
    public void setNoTaxAmtLocal(java.math.BigDecimal item)
    {
        setBigDecimal("noTaxAmtLocal", item);
    }
    /**
     * Object:分录's 本位币税额property 
     */
    public java.math.BigDecimal getTaxLocal()
    {
        return getBigDecimal("taxLocal");
    }
    public void setTaxLocal(java.math.BigDecimal item)
    {
        setBigDecimal("taxLocal", item);
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
        return new BOSObjectType("83E90A16");
    }
}