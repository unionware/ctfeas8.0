package com.kingdee.eas.basedata.person;

import java.io.Serializable;

public class BankInfoInfo extends AbstractBankInfoInfo implements Serializable 
{
    public BankInfoInfo()
    {
        super();
    }
    protected BankInfoInfo(String pkField)
    {
        super(pkField);
    }
}