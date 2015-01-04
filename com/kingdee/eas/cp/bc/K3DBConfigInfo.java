package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class K3DBConfigInfo extends AbstractK3DBConfigInfo implements Serializable 
{
    public K3DBConfigInfo()
    {
        super();
    }
    protected K3DBConfigInfo(String pkField)
    {
        super(pkField);
    }
}