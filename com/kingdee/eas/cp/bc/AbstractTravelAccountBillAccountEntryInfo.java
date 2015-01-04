package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTravelAccountBillAccountEntryInfo extends com.kingdee.eas.cp.bc.CollectionAccountCoreBaseInfo implements Serializable 
{
    public AbstractTravelAccountBillAccountEntryInfo()
    {
        this("id");
    }
    protected AbstractTravelAccountBillAccountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 差旅费报销单多收款人分录 's 单据头 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.TravelAccountBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object:差旅费报销单多收款人分录's 收款对象property 
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
        return new BOSObjectType("0BA02118");
    }
}