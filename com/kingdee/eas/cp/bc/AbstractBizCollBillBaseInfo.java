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
     * Object: 业务协同BillBase 's 费用归属部门 property 
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
     * Object: 业务协同BillBase 's 公司 property 
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
     * Object: 业务协同BillBase 's 费用承担对象 property 
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
     * Object: 业务协同BillBase 's 币别 property 
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
     * Object: 业务协同BillBase 's 费用类型 property 
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
     * Object:业务协同BillBase's 合计金额property 
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
     * Object:业务协同BillBase's 核定金额property 
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
     * Object:业务协同BillBase's 还款金额property 
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
     * Object:业务协同BillBase's 合计金额副本property 
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
     * Object:业务协同BillBase's 已用金额property 
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
     * Object:业务协同BillBase's 可用余额property 
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
     * Object: 业务协同BillBase 's 业务类别 property 
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
     * Object:业务协同BillBase's 预算扣减值property 
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
     * Object:业务协同BillBase's 已付金额property 
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
     * Object:业务协同BillBase's 未付金额property 
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
     * Object:业务协同BillBase's 冲帐property 
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
     * Object:业务协同BillBase's 收款人姓名property 
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
     * Object:业务协同BillBase's 收款人账号property 
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
     * Object: 业务协同BillBase 's 出差类型 property 
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
     * Object: 业务协同BillBase 's 收款信息 property 
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
     * Object: 业务协同BillBase 's 开户地 property 
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
     * Object: 业务协同BillBase 's 收款银行 f7选择 property 
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
     * Object:业务协同BillBase's 收款银行property 
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
     * Object:业务协同BillBase's 预算超额说明property 
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
     * Object:业务协同BillBase's 是否超预算property 
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
     * Object:业务协同BillBase's 是否生成凭证property 
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
     * Object:业务协同BillBase's 是否生成利润中心凭证property 
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