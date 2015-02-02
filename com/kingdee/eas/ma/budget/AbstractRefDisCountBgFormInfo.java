package com.kingdee.eas.ma.budget;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRefDisCountBgFormInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRefDisCountBgFormInfo()
    {
        this("id");
    }
    protected AbstractRefDisCountBgFormInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:引用下级预算表's 汇编意见property 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    /**
     * Object:引用下级预算表's 保存的预算表临时数据property 
     */
    public byte[] getKdtData()
    {
        return (byte[])get("kdtData");
    }
    public void setKdtData(byte[] item)
    {
        put("kdtData", item);
    }
    /**
     * Object:引用下级预算表's 调整数据映射表property 
     */
    public byte[] getAdjustMapData()
    {
        return (byte[])get("adjustMapData");
    }
    public void setAdjustMapData(byte[] item)
    {
        put("adjustMapData", item);
    }
    /**
     * Object: 引用下级预算表 's 预算汇编表 property 
     */
    public com.kingdee.eas.ma.budget.BgDisCountFormInfo getBgCollect()
    {
        return (com.kingdee.eas.ma.budget.BgDisCountFormInfo)get("bgCollect");
    }
    public void setBgCollect(com.kingdee.eas.ma.budget.BgDisCountFormInfo item)
    {
        put("bgCollect", item);
    }
    /**
     * Object: 引用下级预算表 's 预算表 property 
     */
    public com.kingdee.eas.ma.budget.BgFormInfo getBgForm()
    {
        return (com.kingdee.eas.ma.budget.BgFormInfo)get("bgForm");
    }
    public void setBgForm(com.kingdee.eas.ma.budget.BgFormInfo item)
    {
        put("bgForm", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78A6183C");
    }
}