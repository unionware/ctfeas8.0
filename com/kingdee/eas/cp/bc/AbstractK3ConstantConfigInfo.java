package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractK3ConstantConfigInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractK3ConstantConfigInfo()
    {
        this("id");
    }
    protected AbstractK3ConstantConfigInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:K3接口科目配置's 值property 
     */
    public String getVal()
    {
        return getString("val");
    }
    public void setVal(String item)
    {
        setString("val", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F0A59BAC");
    }
}