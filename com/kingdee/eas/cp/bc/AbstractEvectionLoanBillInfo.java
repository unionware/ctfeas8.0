package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvectionLoanBillInfo extends com.kingdee.eas.cp.bc.LoanBillInfo implements Serializable 
{
    public AbstractEvectionLoanBillInfo()
    {
        this("id");
    }
    protected AbstractEvectionLoanBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection());
    }
    /**
     * Object: 出差借款单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection)get("entries");
    }
    /**
     * Object: 出差借款单 's 关联采购合同 property 
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
     * Object: 出差借款单 's 付款公司 property 
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
     * Object:出差借款单's 收款对象property 
     */
    public com.kingdee.eas.cp.bc.ReceiveObjectEnum getReceiveObject()
    {
        return com.kingdee.eas.cp.bc.ReceiveObjectEnum.getEnum(getString("receiveObject"));
    }
    public void setReceiveObject(com.kingdee.eas.cp.bc.ReceiveObjectEnum item)
    {
		if (item != null) {
        setString("receiveObject", item.getValue());
		}
    }
    /**
     * Object: 出差借款单 's 凭证字 property 
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
     * Object:出差借款单's 第一次BOTP生成property 
     */
    public int getIsFirstCreateFrom()
    {
        return getInt("isFirstCreateFrom");
    }
    public void setIsFirstCreateFrom(int item)
    {
        setInt("isFirstCreateFrom", item);
    }
    /**
     * Object:出差借款单's K3凭证号property 
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
     * Object:出差借款单's K3已付款property 
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
     * Object:出差借款单's K3凭证状态property 
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
     * Object:出差借款单's peyerIDproperty 
     */
    public String getPeyerID()
    {
        return getString("peyerID");
    }
    public void setPeyerID(String item)
    {
        setString("peyerID", item);
    }
    /**
     * Object:出差借款单's 还款状态property 
     */
    public com.kingdee.eas.cp.bc.ReturnStateEnum getReturnState()
    {
        return com.kingdee.eas.cp.bc.ReturnStateEnum.getEnum(getString("returnState"));
    }
    public void setReturnState(com.kingdee.eas.cp.bc.ReturnStateEnum item)
    {
		if (item != null) {
        setString("returnState", item.getValue());
		}
    }
    /**
     * Object:出差借款单's 还款金额property 
     */
    public java.math.BigDecimal getReturnAmt()
    {
        return getBigDecimal("returnAmt");
    }
    public void setReturnAmt(java.math.BigDecimal item)
    {
        setBigDecimal("returnAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7AE53B38");
    }
}