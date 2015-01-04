package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class ReturnBillEntryInfo extends AbstractReturnBillEntryInfo implements Serializable 
{
    public ReturnBillEntryInfo()
    {
        super();
    }
    protected ReturnBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}