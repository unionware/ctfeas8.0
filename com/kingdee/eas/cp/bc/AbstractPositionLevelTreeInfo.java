package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPositionLevelTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPositionLevelTreeInfo()
    {
        this("id");
    }
    protected AbstractPositionLevelTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 职级组别 's 父节点 property 
     */
    public com.kingdee.eas.cp.bc.PositionLevelTreeInfo getParent()
    {
        return (com.kingdee.eas.cp.bc.PositionLevelTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.cp.bc.PositionLevelTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9E46117B");
    }
}