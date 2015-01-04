package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTravelAccountBillInfo extends com.kingdee.eas.cp.bc.ExpenseAccountBillInfo implements Serializable 
{
    public AbstractTravelAccountBillInfo()
    {
        this("id");
    }
    protected AbstractTravelAccountBillInfo(String pkField)
    {
        super(pkField);
        put("collectionEntries", new com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryCollection());
        put("AccountRecords", new com.kingdee.eas.cp.bc.TravelAccountBillAccountRecordCollection());
        put("LoanCheckEntries", new com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryCollection());
        put("entries", new com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection());
    }
    /**
     * Object: 差旅费报销单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection)get("entries");
    }
    /**
     * Object: 差旅费报销单 's 借款核销表 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryCollection getLoanCheckEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryCollection)get("LoanCheckEntries");
    }
    /**
     * Object: 差旅费报销单 's 多收款人分录 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryCollection getCollectionEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryCollection)get("collectionEntries");
    }
    /**
     * Object: 差旅费报销单 's 记账凭证记录表 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillAccountRecordCollection getAccountRecords()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillAccountRecordCollection)get("AccountRecords");
    }
    /**
     * Object:差旅费报销单's 超标说明property 
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
     * Object:差旅费报销单's 专用发票号property 
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
     * Object: 差旅费报销单 's 银行账号 property 
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
     * Object: 差旅费报销单 's 凭证字 property 
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
     * Object:差旅费报销单's K3是否付款property 
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
     * Object:差旅费报销单's K3凭证号property 
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
     * Object:差旅费报销单's K3凭证状态property 
     */
    public int getK3VoucherStatus()
    {
        return getInt("k3VoucherStatus");
    }
    public void setK3VoucherStatus(int item)
    {
        setInt("k3VoucherStatus", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C57003BC");
    }
}