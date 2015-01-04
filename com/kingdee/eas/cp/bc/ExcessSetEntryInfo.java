package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class ExcessSetEntryInfo extends AbstractExcessSetEntryInfo implements Serializable 
{
    public ExcessSetEntryInfo()
    {
        super();
    }
    protected ExcessSetEntryInfo(String pkField)
    {
        super(pkField);
    }
}