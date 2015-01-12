package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBizAccountBillEntryInfo extends com.kingdee.eas.cp.bc.OtherExpenseEntryCoreBaseInfo implements Serializable 
{
    public AbstractBizAccountBillEntryInfo()
    {
        this("id");
    }
    protected AbstractBizAccountBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.BizAccountBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: ��¼ 's ҵ����� property 
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
     * Object: ��¼ 's ���ù������� property 
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
     * Object: ��¼ 's ����֧����˾ property 
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
     * Object:��¼'s Ӧ��״̬property 
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
     * Object:��¼'s Ӧ��״̬property 
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
     * Object:��¼'s �޷�Ʊ����property 
     */
    public boolean isIsNoInvoice()
    {
        return getBoolean("isNoInvoice");
    }
    public void setIsNoInvoice(boolean item)
    {
        setBoolean("isNoInvoice", item);
    }
    /**
     * Object:��¼'s �޷�Ʊ���property 
     */
    public java.math.BigDecimal getNoInvoiceAmt()
    {
        return getBigDecimal("noInvoiceAmt");
    }
    public void setNoInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("noInvoiceAmt", item);
    }
    /**
     * Object:��¼'s ��Ʊ���property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    /**
     * Object:��¼'s ����˰���property 
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
     * Object:��¼'s ˰��property 
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
     * Object:��¼'s ˰��property 
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
     * Object:��¼'s ��λ�Ҳ���˰���property 
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
     * Object:��¼'s ��λ��˰��property 
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
     * Object:��¼'s ���ύ��д���property 
     */
    public java.math.BigDecimal getSubmitAmt()
    {
        return getBigDecimal("submitAmt");
    }
    public void setSubmitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("submitAmt", item);
    }
    /**
     * Object:��¼'s ���ύ���ý�дproperty 
     */
    public java.math.BigDecimal getSubmitUseAmt()
    {
        return getBigDecimal("submitUseAmt");
    }
    public void setSubmitUseAmt(java.math.BigDecimal item)
    {
        setBigDecimal("submitUseAmt", item);
    }
    /**
     * Object: ��¼ 's ְԱ property 
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
        return new BOSObjectType("F2062F13");
    }
}