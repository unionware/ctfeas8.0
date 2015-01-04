package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBizAccountBillInfo extends com.kingdee.eas.cp.bc.ExpenseAccountBillInfo implements Serializable 
{
    public AbstractBizAccountBillInfo()
    {
        this("id");
    }
    protected AbstractBizAccountBillInfo(String pkField)
    {
        super(pkField);
        put("collectionEntries", new com.kingdee.eas.cp.bc.BizAccountBillAccountEntryCollection());
        put("ReqCheckEntries", new com.kingdee.eas.cp.bc.BizAccountBillReqCheckEntryCollection());
        put("AccountRecords", new com.kingdee.eas.cp.bc.BizAccountBillAccountRecordCollection());
        put("LoanCheckEntries", new com.kingdee.eas.cp.bc.BizAccountBillLoanCheckEntryCollection());
        put("entries", new com.kingdee.eas.cp.bc.BizAccountBillEntryCollection());
    }
    /**
     * Object: 费用报销单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillEntryCollection)get("entries");
    }
    /**
     * Object: 费用报销单 's 借款核销表 property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillLoanCheckEntryCollection getLoanCheckEntries()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillLoanCheckEntryCollection)get("LoanCheckEntries");
    }
    /**
     * Object: 费用报销单 's 申请核销表 property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillReqCheckEntryCollection getReqCheckEntries()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillReqCheckEntryCollection)get("ReqCheckEntries");
    }
    /**
     * Object: 费用报销单 's 多收款人分录 property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillAccountEntryCollection getCollectionEntries()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillAccountEntryCollection)get("collectionEntries");
    }
    /**
     * Object: 费用报销单 's 记账记录表 property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillAccountRecordCollection getAccountRecords()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillAccountRecordCollection)get("AccountRecords");
    }
    /**
     * Object:费用报销单's 超标说明property 
     */
    public String getOverAmountDesc()
    {
        return getString("overAmountDesc");
    }
    public void setOverAmountDesc(String item)
    {
        setString("overAmountDesc", item);
    }
    /**
     * Object:费用报销单's 专用发票号property 
     */
    public String getSpecialInvoice()
    {
        return getString("specialInvoice");
    }
    public void setSpecialInvoice(String item)
    {
        setString("specialInvoice", item);
    }
    /**
     * Object: 费用报销单 's 关联采购合同 property 
     */
    public com.kingdee.eas.scm.sm.pur.PurContractInfo getContract()
    {
        return (com.kingdee.eas.scm.sm.pur.PurContractInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.scm.sm.pur.PurContractInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 费用报销单 's 银行账号 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPayCompany()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("payCompany");
    }
    public void setPayCompany(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("payCompany", item);
    }
    /**
     * Object: 费用报销单 's 凭证字 property 
     */
    public com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo getVoucherNum()
    {
        return (com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo)get("voucherNum");
    }
    public void setVoucherNum(com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo item)
    {
        put("voucherNum", item);
    }
    /**
     * Object:费用报销单's K3是否付款property 
     */
    public boolean isIsK3Paid()
    {
        return getBoolean("isK3Paid");
    }
    public void setIsK3Paid(boolean item)
    {
        setBoolean("isK3Paid", item);
    }
    /**
     * Object:费用报销单's K3凭证号property 
     */
    public String getK3VoucherNumber()
    {
        return getString("k3VoucherNumber");
    }
    public void setK3VoucherNumber(String item)
    {
        setString("k3VoucherNumber", item);
    }
    /**
     * Object:费用报销单's K3凭证状态property 
     */
    public int getK3VoucherStatus()
    {
        return getInt("k3VoucherStatus");
    }
    public void setK3VoucherStatus(int item)
    {
        setInt("k3VoucherStatus", item);
    }
    /**
     * Object:费用报销单's 合同号property 
     */
    public String getContractNO()
    {
        return getString("contractNO");
    }
    public void setContractNO(String item)
    {
        setString("contractNO", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4A44F49F");
    }
}