package com.kingdee.eas.ma.budget;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBgAdjustBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractBgAdjustBillEntryInfo()
    {
        this("id");
    }
    protected AbstractBgAdjustBillEntryInfo(String pkField)
    {
        super(pkField);
        put("idea", new com.kingdee.eas.ma.budget.BgAdjustBillAuditIdeaCollection());
    }
    /**
     * Object: 预算调整单据体 's 预算调整追加单单据头 property 
     */
    public com.kingdee.eas.ma.budget.BgAdjustBillInfo getAdjustBill()
    {
        return (com.kingdee.eas.ma.budget.BgAdjustBillInfo)get("adjustBill");
    }
    public void setAdjustBill(com.kingdee.eas.ma.budget.BgAdjustBillInfo item)
    {
        put("adjustBill", item);
    }
    /**
     * Object: 预算调整单据体 's 关联组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: 预算调整单据体 's 关联方案 property 
     */
    public com.kingdee.eas.ma.budget.BgSchemeInfo getBgScheme()
    {
        return (com.kingdee.eas.ma.budget.BgSchemeInfo)get("bgScheme");
    }
    public void setBgScheme(com.kingdee.eas.ma.budget.BgSchemeInfo item)
    {
        put("bgScheme", item);
    }
    /**
     * Object: 预算调整单据体 's 所属预算表 property 
     */
    public com.kingdee.eas.ma.budget.BgFormInfo getBgForm()
    {
        return (com.kingdee.eas.ma.budget.BgFormInfo)get("bgForm");
    }
    public void setBgForm(com.kingdee.eas.ma.budget.BgFormInfo item)
    {
        put("bgForm", item);
    }
    /**
     * Object:预算调整单据体's 项目公式property 
     */
    public String getFormula()
    {
        return getString("formula");
    }
    public void setFormula(String item)
    {
        setString("formula", item);
    }
    /**
     * Object:预算调整单据体's 预算项目组合Idproperty 
     */
    public String getBgItemId()
    {
        return getString("bgItemId");
    }
    public void setBgItemId(String item)
    {
        setString("bgItemId", item);
    }
    /**
     * Object: 预算调整单据体 's 业务期间 property 
     */
    public com.kingdee.eas.ma.budget.BgPeriodInfo getBgPeriod()
    {
        return (com.kingdee.eas.ma.budget.BgPeriodInfo)get("bgPeriod");
    }
    public void setBgPeriod(com.kingdee.eas.ma.budget.BgPeriodInfo item)
    {
        put("bgPeriod", item);
    }
    /**
     * Object: 预算调整单据体 's 预算要素 property 
     */
    public com.kingdee.eas.ma.budget.BgElementInfo getBgElement()
    {
        return (com.kingdee.eas.ma.budget.BgElementInfo)get("bgElement");
    }
    public void setBgElement(com.kingdee.eas.ma.budget.BgElementInfo item)
    {
        put("bgElement", item);
    }
    /**
     * Object: 预算调整单据体 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:预算调整单据体's 调整类型property 
     */
    public com.kingdee.eas.ma.budget.BgAdjustTypeEnum getAdjustType()
    {
        return com.kingdee.eas.ma.budget.BgAdjustTypeEnum.getEnum(getString("adjustType"));
    }
    public void setAdjustType(com.kingdee.eas.ma.budget.BgAdjustTypeEnum item)
    {
		if (item != null) {
        setString("adjustType", item.getValue());
		}
    }
    /**
     * Object:预算调整单据体's 调整前property 
     */
    public java.math.BigDecimal getAdjustBefore()
    {
        return getBigDecimal("adjustBefore");
    }
    public void setAdjustBefore(java.math.BigDecimal item)
    {
        setBigDecimal("adjustBefore", item);
    }
    /**
     * Object:预算调整单据体's 申请数property 
     */
    public java.math.BigDecimal getAdjustApply()
    {
        return getBigDecimal("adjustApply");
    }
    public void setAdjustApply(java.math.BigDecimal item)
    {
        setBigDecimal("adjustApply", item);
    }
    /**
     * Object:预算调整单据体's 批准数property 
     */
    public java.math.BigDecimal getAdjustPass()
    {
        return getBigDecimal("adjustPass");
    }
    public void setAdjustPass(java.math.BigDecimal item)
    {
        setBigDecimal("adjustPass", item);
    }
    /**
     * Object:预算调整单据体's 调整后值property 
     */
    public java.math.BigDecimal getAdjustAfter()
    {
        return getBigDecimal("adjustAfter");
    }
    public void setAdjustAfter(java.math.BigDecimal item)
    {
        setBigDecimal("adjustAfter", item);
    }
    /**
     * Object:预算调整单据体's 调剂组号property 
     */
    public int getAdjustGroup()
    {
        return getInt("adjustGroup");
    }
    public void setAdjustGroup(int item)
    {
        setInt("adjustGroup", item);
    }
    /**
     * Object:预算调整单据体's 调整原因property 
     */
    public String getAdjustCause()
    {
        return getString("adjustCause");
    }
    public void setAdjustCause(String item)
    {
        setString("adjustCause", item);
    }
    /**
     * Object: 预算调整单据体 's 分录审批意见 property 
     */
    public com.kingdee.eas.ma.budget.BgAdjustBillAuditIdeaCollection getIdea()
    {
        return (com.kingdee.eas.ma.budget.BgAdjustBillAuditIdeaCollection)get("idea");
    }
    /**
     * Object:预算调整单据体's 是否控制property 
     */
    public boolean isIsControl()
    {
        return getBoolean("isControl");
    }
    public void setIsControl(boolean item)
    {
        setBoolean("isControl", item);
    }
    /**
     * Object:预算调整单据体's 是否弹性控制property 
     */
    public boolean isIsFlexible()
    {
        return getBoolean("isFlexible");
    }
    public void setIsFlexible(boolean item)
    {
        setBoolean("isFlexible", item);
    }
    /**
     * Object:预算调整单据体's 弹性控制系数property 
     */
    public java.math.BigDecimal getFlexParam()
    {
        return getBigDecimal("flexParam");
    }
    public void setFlexParam(java.math.BigDecimal item)
    {
        setBigDecimal("flexParam", item);
    }
    /**
     * Object:预算调整单据体's 控制方式property 
     */
    public com.kingdee.eas.ma.budget.BgCtrlTypeEnum getCtrlType()
    {
        return com.kingdee.eas.ma.budget.BgCtrlTypeEnum.getEnum(getInt("ctrlType"));
    }
    public void setCtrlType(com.kingdee.eas.ma.budget.BgCtrlTypeEnum item)
    {
		if (item != null) {
        setInt("ctrlType", item.getValue());
		}
    }
    /**
     * Object:预算调整单据体's 是否分组控制property 
     */
    public boolean isIsGroupCtrl()
    {
        return getBoolean("isGroupCtrl");
    }
    public void setIsGroupCtrl(boolean item)
    {
        setBoolean("isGroupCtrl", item);
    }
    /**
     * Object:预算调整单据体's 分组号property 
     */
    public String getCtrlGroupNo()
    {
        return getString("ctrlGroupNo");
    }
    public void setCtrlGroupNo(String item)
    {
        setString("ctrlGroupNo", item);
    }
    /**
     * Object:预算调整单据体's 项目组合名称property 
     */
    public String getBgItemCmbName()
    {
        return getString("bgItemCmbName");
    }
    public void setBgItemCmbName(String item)
    {
        setString("bgItemCmbName", item);
    }
    /**
     * Object:预算调整单据体's 是否可超预算property 
     */
    public boolean isIsAllowAccess()
    {
        return getBoolean("isAllowAccess");
    }
    public void setIsAllowAccess(boolean item)
    {
        setBoolean("isAllowAccess", item);
    }
    /**
     * Object:预算调整单据体's 预算可用金额property 
     */
    public java.math.BigDecimal getBgUsableAmt()
    {
        return getBigDecimal("bgUsableAmt");
    }
    public void setBgUsableAmt(java.math.BigDecimal item)
    {
        setBigDecimal("bgUsableAmt", item);
    }
    /**
     * Object:预算调整单据体's 预算实际发生数property 
     */
    public java.math.BigDecimal getBgActualAmt()
    {
        return getBigDecimal("bgActualAmt");
    }
    public void setBgActualAmt(java.math.BigDecimal item)
    {
        setBigDecimal("bgActualAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C0AAC76");
    }
}