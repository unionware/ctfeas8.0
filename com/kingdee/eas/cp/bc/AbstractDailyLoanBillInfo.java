package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDailyLoanBillInfo extends com.kingdee.eas.cp.bc.LoanBillInfo implements Serializable 
{
    public AbstractDailyLoanBillInfo()
    {
        this("id");
    }
    protected AbstractDailyLoanBillInfo(String pkField)
    {
        super(pkField);
        put("ReqCheckEntries", new com.kingdee.eas.cp.bc.DailyLoanBillReqCheckEntryCollection());
        put("entries", new com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection());
    }
    /**
     * Object: 借款单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection)get("entries");
    }
    /**
     * Object:借款单's 借款类别property 
     */
    public com.kingdee.eas.cp.bc.LoanTypeEnum getLoanType()
    {
        return com.kingdee.eas.cp.bc.LoanTypeEnum.getEnum(getInt("loanType"));
    }
    public void setLoanType(com.kingdee.eas.cp.bc.LoanTypeEnum item)
    {
		if (item != null) {
        setInt("loanType", item.getValue());
		}
    }
    /**
     * Object: 借款单 's 申请核销表 property 
     */
    public com.kingdee.eas.cp.bc.DailyLoanBillReqCheckEntryCollection getReqCheckEntries()
    {
        return (com.kingdee.eas.cp.bc.DailyLoanBillReqCheckEntryCollection)get("ReqCheckEntries");
    }
    /**
     * Object: 借款单 's 关联合同编号 property 
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
     * Object: 借款单 's 付款公司 property 
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
     * Object:借款单's 收款对象property 
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
     * Object: 借款单 's 凭证字 property 
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
     * Object:借款单's 第一次关联生成property 
     */
    public int getFirstCreateFrom()
    {
        return getInt("firstCreateFrom");
    }
    public void setFirstCreateFrom(int item)
    {
        setInt("firstCreateFrom", item);
    }
    /**
     * Object:借款单's 采购合同IDproperty 
     */
    public String getPurContractID()
    {
        return getString("purContractID");
    }
    public void setPurContractID(String item)
    {
        setString("purContractID", item);
    }
    /**
     * Object:借款单's K3凭证号property 
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
     * Object:借款单's K3已付款property 
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
     * Object:借款单's K3凭证状态property 
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
     * Object:借款单's 收款人IDproperty 
     */
    public String getPayerid()
    {
        return getString("payerid");
    }
    public void setPayerid(String item)
    {
        setString("payerid", item);
    }
    /**
     * Object:借款单's 还款状态property 
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
     * Object:借款单's 还款金额property 
     */
    public java.math.BigDecimal getReturnAmt()
    {
        return getBigDecimal("returnAmt");
    }
    public void setReturnAmt(java.math.BigDecimal item)
    {
        setBigDecimal("returnAmt", item);
    }
    /**
     * Object:借款单's 合同号property 
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
        return new BOSObjectType("8110AAB2");
    }
}