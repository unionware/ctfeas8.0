package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractBizCollBillBaseInfo extends com.kingdee.eas.cp.bc.BizCollCoreBillBaseInfo implements Serializable 
{
    public AbstractBizCollBillBaseInfo()
    {
        this("id");
    }
    protected AbstractBizCollBillBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's ���ù������� property 
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
     * Object: ҵ��ЭͬBillBase 's ��˾ property 
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
     * Object: ҵ��ЭͬBillBase 's ���óе����� property 
     */
    public com.kingdee.eas.basedata.assistant.CostObjectInfo getSupportedObj()
    {
        return (com.kingdee.eas.basedata.assistant.CostObjectInfo)get("supportedObj");
    }
    public void setSupportedObj(com.kingdee.eas.basedata.assistant.CostObjectInfo item)
    {
        put("supportedObj", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrencyType()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currencyType");
    }
    public void setCurrencyType(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currencyType", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's �������� property 
     */
    public com.kingdee.eas.cp.bc.ExpenseTypeInfo getExpenseType()
    {
        return (com.kingdee.eas.cp.bc.ExpenseTypeInfo)get("expenseType");
    }
    public void setExpenseType(com.kingdee.eas.cp.bc.ExpenseTypeInfo item)
    {
        put("expenseType", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �ϼƽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �˶����property 
     */
    public java.math.BigDecimal getAmountApproved()
    {
        return getBigDecimal("amountApproved");
    }
    public void setAmountApproved(java.math.BigDecimal item)
    {
        setBigDecimal("amountApproved", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's ������property 
     */
    public java.math.BigDecimal getAmountSendedBack()
    {
        return getBigDecimal("amountSendedBack");
    }
    public void setAmountSendedBack(java.math.BigDecimal item)
    {
        setBigDecimal("amountSendedBack", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �ϼƽ���property 
     */
    public java.math.BigDecimal getAmountCopy()
    {
        return getBigDecimal("amountCopy");
    }
    public void setAmountCopy(java.math.BigDecimal item)
    {
        setBigDecimal("amountCopy", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's ���ý��property 
     */
    public java.math.BigDecimal getAmountUsed()
    {
        return getBigDecimal("amountUsed");
    }
    public void setAmountUsed(java.math.BigDecimal item)
    {
        setBigDecimal("amountUsed", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �������property 
     */
    public java.math.BigDecimal getAmountBalance()
    {
        return getBigDecimal("amountBalance");
    }
    public void setAmountBalance(java.math.BigDecimal item)
    {
        setBigDecimal("amountBalance", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's ҵ����� property 
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
     * Object:ҵ��ЭͬBillBase's Ԥ��ۼ�ֵproperty 
     */
    public java.math.BigDecimal getBudgetDo()
    {
        return getBigDecimal("budgetDo");
    }
    public void setBudgetDo(java.math.BigDecimal item)
    {
        setBigDecimal("budgetDo", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �Ѹ����property 
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
     * Object:ҵ��ЭͬBillBase's δ�����property 
     */
    public java.math.BigDecimal getAmountNotPaid()
    {
        return getBigDecimal("amountNotPaid");
    }
    public void setAmountNotPaid(java.math.BigDecimal item)
    {
        setBigDecimal("amountNotPaid", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's ����property 
     */
    public java.math.BigDecimal getAmountStriked()
    {
        return getBigDecimal("amountStriked");
    }
    public void setAmountStriked(java.math.BigDecimal item)
    {
        setBigDecimal("amountStriked", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �տ�������property 
     */
    public String getPayerName()
    {
        return getString("payerName");
    }
    public void setPayerName(String item)
    {
        setString("payerName", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �տ����˺�property 
     */
    public String getPayerAccount()
    {
        return getString("payerAccount");
    }
    public void setPayerAccount(String item)
    {
        setString("payerAccount", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's �������� property 
     */
    public com.kingdee.eas.cp.bc.TravelTypeInfo getTravelType()
    {
        return (com.kingdee.eas.cp.bc.TravelTypeInfo)get("travelType");
    }
    public void setTravelType(com.kingdee.eas.cp.bc.TravelTypeInfo item)
    {
        put("travelType", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's �տ���Ϣ property 
     */
    public com.kingdee.eas.cp.bc.CollectionAccountInfo getCollectionAccount()
    {
        return (com.kingdee.eas.cp.bc.CollectionAccountInfo)get("CollectionAccount");
    }
    public void setCollectionAccount(com.kingdee.eas.cp.bc.CollectionAccountInfo item)
    {
        put("CollectionAccount", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's ������ property 
     */
    public com.kingdee.eas.fm.be.OpenAreaInfo getOpenArea()
    {
        return (com.kingdee.eas.fm.be.OpenAreaInfo)get("openArea");
    }
    public void setOpenArea(com.kingdee.eas.fm.be.OpenAreaInfo item)
    {
        put("openArea", item);
    }
    /**
     * Object: ҵ��ЭͬBillBase 's �տ����� f7ѡ�� property 
     */
    public com.kingdee.eas.fm.be.BEBankInfo getPayerBankStr()
    {
        return (com.kingdee.eas.fm.be.BEBankInfo)get("payerBankStr");
    }
    public void setPayerBankStr(com.kingdee.eas.fm.be.BEBankInfo item)
    {
        put("payerBankStr", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �տ�����property 
     */
    public String getPayerBank()
    {
        return getString("payerBank");
    }
    public void setPayerBank(String item)
    {
        setString("payerBank", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's Ԥ�㳬��˵��property 
     */
    public String getBudgetDescription()
    {
        return getString("budgetDescription");
    }
    public void setBudgetDescription(String item)
    {
        setString("budgetDescription", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �Ƿ�Ԥ��property 
     */
    public boolean isIsOverBudget()
    {
        return getBoolean("isOverBudget");
    }
    public void setIsOverBudget(boolean item)
    {
        setBoolean("isOverBudget", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �Ƿ�����ƾ֤property 
     */
    public boolean isIsVouchered()
    {
        return getBoolean("isVouchered");
    }
    public void setIsVouchered(boolean item)
    {
        setBoolean("isVouchered", item);
    }
    /**
     * Object:ҵ��ЭͬBillBase's �Ƿ�������������ƾ֤property 
     */
    public boolean isPcaVouchered()
    {
        return getBoolean("pcaVouchered");
    }
    public void setPcaVouchered(boolean item)
    {
        setBoolean("pcaVouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1C791319");
    }
}