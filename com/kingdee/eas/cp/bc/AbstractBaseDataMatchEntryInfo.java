package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseDataMatchEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBaseDataMatchEntryInfo()
    {
        this("id");
    }
    protected AbstractBaseDataMatchEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.cp.bc.BaseDataMatchInfo getParent()
    {
        return (com.kingdee.eas.cp.bc.BaseDataMatchInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.cp.bc.BaseDataMatchInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 编码property 
     */
    public String getEasNum()
    {
        return getString("easNum");
    }
    public void setEasNum(String item)
    {
        setString("easNum", item);
    }
    /**
     * Object:分录's 编码property 
     */
    public String getK3Num()
    {
        return getString("k3Num");
    }
    public void setK3Num(String item)
    {
        setString("k3Num", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C4F7A306");
    }
}