package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBizAccountBillAccountEntryInfo extends com.kingdee.eas.cp.bc.CollectionAccountCoreBaseInfo implements Serializable 
{
    public AbstractBizAccountBillAccountEntryInfo()
    {
        this("id");
    }
    protected AbstractBizAccountBillAccountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 费用报销单多收款人分录 's 单据头 property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.BizAccountBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object:费用报销单多收款人分录's 收款对象property 
     */
    public com.kingdee.eas.cp.bc.ReceiveObjectEnum getReceiveObject()
    {
        return com.kingdee.eas.cp.bc.ReceiveObjectEnum.getEnum(getString("receiveObject"));
    }
    public void setReceiveObject(com.kingdee.eas.cp.bc.ReceiveObjectEnum item)
    {
		if (item != null) {
        setString("receiveObject", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("88A4EDD5");
    }
}