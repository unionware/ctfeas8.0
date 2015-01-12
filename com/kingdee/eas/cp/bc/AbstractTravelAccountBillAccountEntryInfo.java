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
     * Object: ���÷ѱ��������տ��˷�¼ 's ����ͷ property 
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
     * Object:���÷ѱ��������տ��˷�¼'s �տ����property 
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