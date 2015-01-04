package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class ReturnBillInfo extends AbstractReturnBillInfo implements Serializable 
{
    public ReturnBillInfo()
    {
        super();
    }
    protected ReturnBillInfo(String pkField)
    {
        super(pkField);
    }
}