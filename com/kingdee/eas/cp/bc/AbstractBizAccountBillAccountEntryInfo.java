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
     * Object: ���ñ��������տ��˷�¼ 's ����ͷ property 
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
     * Object:���ñ��������տ��˷�¼'s �տ����property 
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