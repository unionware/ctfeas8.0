package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseDataMatchInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBaseDataMatchInfo()
    {
        this("id");
    }
    protected AbstractBaseDataMatchInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.cp.bc.BaseDataMatchEntryCollection());
    }
    /**
     * Object: 基础资料对照 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.BaseDataMatchEntryCollection getEntrys()
    {
        return (com.kingdee.eas.cp.bc.BaseDataMatchEntryCollection)get("entrys");
    }
    /**
     * Object:基础资料对照's 类型property 
     */
    public com.kingdee.eas.cp.bc.BaseDataEnum getTypes()
    {
        return com.kingdee.eas.cp.bc.BaseDataEnum.getEnum(getString("types"));
    }
    public void setTypes(com.kingdee.eas.cp.bc.BaseDataEnum item)
    {
		if (item != null) {
        setString("types", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A10194CC");
    }
}