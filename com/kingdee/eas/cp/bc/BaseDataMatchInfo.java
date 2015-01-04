package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class BaseDataMatchInfo extends AbstractBaseDataMatchInfo implements Serializable 
{
    public BaseDataMatchInfo()
    {
        super();
    }
    protected BaseDataMatchInfo(String pkField)
    {
        super(pkField);
    }
}