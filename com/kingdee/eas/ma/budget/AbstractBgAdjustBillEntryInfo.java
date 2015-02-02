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
     * Object: Ԥ����������� 's Ԥ�����׷�ӵ�����ͷ property 
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
     * Object: Ԥ����������� 's ������֯ property 
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
     * Object: Ԥ����������� 's �������� property 
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
     * Object: Ԥ����������� 's ����Ԥ��� property 
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
     * Object:Ԥ�����������'s ��Ŀ��ʽproperty 
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
     * Object:Ԥ�����������'s Ԥ����Ŀ���Idproperty 
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
     * Object: Ԥ����������� 's ҵ���ڼ� property 
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
     * Object: Ԥ����������� 's Ԥ��Ҫ�� property 
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
     * Object: Ԥ����������� 's �ұ� property 
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
     * Object:Ԥ�����������'s ��������property 
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
     * Object:Ԥ�����������'s ����ǰproperty 
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
     * Object:Ԥ�����������'s ������property 
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
     * Object:Ԥ�����������'s ��׼��property 
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
     * Object:Ԥ�����������'s ������ֵproperty 
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
     * Object:Ԥ�����������'s �������property 
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
     * Object:Ԥ�����������'s ����ԭ��property 
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
     * Object: Ԥ����������� 's ��¼������� property 
     */
    public com.kingdee.eas.ma.budget.BgAdjustBillAuditIdeaCollection getIdea()
    {
        return (com.kingdee.eas.ma.budget.BgAdjustBillAuditIdeaCollection)get("idea");
    }
    /**
     * Object:Ԥ�����������'s �Ƿ����property 
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
     * Object:Ԥ�����������'s �Ƿ��Կ���property 
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
     * Object:Ԥ�����������'s ���Կ���ϵ��property 
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
     * Object:Ԥ�����������'s ���Ʒ�ʽproperty 
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
     * Object:Ԥ�����������'s �Ƿ�������property 
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
     * Object:Ԥ�����������'s �����property 
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
     * Object:Ԥ�����������'s ��Ŀ�������property 
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
     * Object:Ԥ�����������'s �Ƿ�ɳ�Ԥ��property 
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
     * Object:Ԥ�����������'s Ԥ����ý��property 
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
     * Object:Ԥ�����������'s Ԥ��ʵ�ʷ�����property 
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