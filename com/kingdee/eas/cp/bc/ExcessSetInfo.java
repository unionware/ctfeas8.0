package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class ExcessSetInfo extends AbstractExcessSetInfo implements Serializable 
{
    public ExcessSetInfo()
    {
        super();
    }
    protected ExcessSetInfo(String pkField)
    {
        super(pkField);
    }
}