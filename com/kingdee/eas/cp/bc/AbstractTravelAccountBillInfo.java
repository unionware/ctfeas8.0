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
     * Object: ���÷ѱ����� 's ��¼ property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection)get("entries");
    }
    /**
     * Object: ���÷ѱ����� 's �������� property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryCollection getLoanCheckEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryCollection)get("LoanCheckEntries");
    }
    /**
     * Object: ���÷ѱ����� 's ���տ��˷�¼ property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryCollection getCollectionEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryCollection)get("collectionEntries");
    }
    /**
     * Object: ���÷ѱ����� 's ����ƾ֤��¼�� property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillAccountRecordCollection getAccountRecords()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillAccountRecordCollection)get("AccountRecords");
    }
    /**
     * Object:���÷ѱ�����'s ����˵��property 
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
     * Object:���÷ѱ�����'s ר�÷�Ʊ��property 
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
     * Object: ���÷ѱ����� 's �����˺� property 
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
     * Object: ���÷ѱ����� 's ƾ֤�� property 
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
     * Object:���÷ѱ�����'s K3�Ƿ񸶿�property 
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
     * Object:���÷ѱ�����'s K3ƾ֤��property 
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
     * Object:���÷ѱ�����'s K3ƾ֤״̬property 
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