package com.kingdee.eas.ma.budget;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBgDisCountFormInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractBgDisCountFormInfo()
    {
        this("id");
    }
    protected AbstractBgDisCountFormInfo(String pkField)
    {
        super(pkField);
        put("refBgForms", new com.kingdee.eas.ma.budget.RefDisCountBgFormCollection());
    }
    /**
     * Object:Ԥ����������'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:Ԥ����������'s number����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:Ԥ����������'s �����Ԥ�����ʱ����property 
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
     * Object:Ԥ����������'s �ϼ���������ӳ���property 
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
     * Object:Ԥ����������'s state״̬property 
     */
    public com.kingdee.eas.ma.budget.BgCollectStateEnum getState()
    {
        return com.kingdee.eas.ma.budget.BgCollectStateEnum.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.ma.budget.BgCollectStateEnum item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    /**
     * Object: Ԥ���������� 's �������� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: Ԥ���������� 's ����Ԥ��� property 
     */
    public com.kingdee.eas.ma.budget.BgFormInfo getBgForm()
    {
        return (com.kingdee.eas.ma.budget.BgFormInfo)get("bgForm");
    }
    public void setBgForm(com.kingdee.eas.ma.budget.BgFormInfo item)
    {
        put("bgForm", item);
    }
    /**
     * Object: Ԥ���������� 's �����¼�Ԥ��� property 
     */
    public com.kingdee.eas.ma.budget.RefDisCountBgFormCollection getRefBgForms()
    {
        return (com.kingdee.eas.ma.budget.RefDisCountBgFormCollection)get("refBgForms");
    }
    /**
     * Object: Ԥ���������� 's auditor����� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAuditor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("auditor");
    }
    public void setAuditor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("auditor", item);
    }
    /**
     * Object:Ԥ����������'s ��������property 
     */
    public byte[] getOtherCont()
    {
        return (byte[])get("otherCont");
    }
    public void setOtherCont(byte[] item)
    {
        put("otherCont", item);
    }
    /**
     * Object:Ԥ����������'s �����֯property 
     */
    public byte[] getOrgList()
    {
        return (byte[])get("orgList");
    }
    public void setOrgList(byte[] item)
    {
        put("orgList", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A96AD12B");
    }
}