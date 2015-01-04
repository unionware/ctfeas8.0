package com.kingdee.eas.cp.bc;

import java.io.Serializable;

public class PositionLevelInfo extends AbstractPositionLevelInfo implements Serializable 
{
    public PositionLevelInfo()
    {
        super();
    }
    protected PositionLevelInfo(String pkField)
    {
        super(pkField);
    }
}