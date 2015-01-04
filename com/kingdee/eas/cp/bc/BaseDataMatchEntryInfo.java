package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class BaseDataMatchEntryInfo extends AbstractBaseDataMatchEntryInfo implements Serializable 
{
    public BaseDataMatchEntryInfo()
    {
        super();
    }
    protected BaseDataMatchEntryInfo(String pkField)
    {
        super(pkField);
    }
}