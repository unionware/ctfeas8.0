package com.kingdee.eas.basedata.centralpolicy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCentralpolicyInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCentralpolicyInfo()
    {
        this("id");
    }
    protected AbstractCentralpolicyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:中央发布政策's 启用property 
     */
    public boolean isBIMUDF0002()
    {
        return getBoolean("BIMUDF0002");
    }
    public void setBIMUDF0002(boolean item)
    {
        setBoolean("BIMUDF0002", item);
    }
    /**
     * Object:中央发布政策's 中央政策内容property 
     */
    public String getContent()
    {
        return getString("Content");
    }
    public void setContent(String item)
    {
        setString("Content", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("452858C5");
    }
}