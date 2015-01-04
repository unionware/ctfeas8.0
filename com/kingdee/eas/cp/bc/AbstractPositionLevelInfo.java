package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPositionLevelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPositionLevelInfo()
    {
        this("id");
    }
    protected AbstractPositionLevelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 职级 's 组别 property 
     */
    public com.kingdee.eas.cp.bc.PositionLevelTreeInfo getTreeid()
    {
        return (com.kingdee.eas.cp.bc.PositionLevelTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.cp.bc.PositionLevelTreeInfo item)
    {
        put("treeid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("028A903D");
    }
}