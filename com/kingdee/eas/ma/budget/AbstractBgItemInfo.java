package com.kingdee.eas.ma.budget;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBgItemInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractBgItemInfo()
    {
        this("id");
    }
    protected AbstractBgItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Ԥ����Ŀ's ����Ԥ����Ŀ����property 
     */
    public com.kingdee.bos.util.BOSUuid getItemID()
    {
        return getBOSUuid("itemID");
    }
    public void setItemID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("itemID", item);
    }
    /**
     * Object:Ԥ����Ŀ's ����Ԥ����Ŀ����property 
     */
    public String getItemNumber()
    {
        return getString("itemNumber");
    }
    public void setItemNumber(String item)
    {
        setString("itemNumber", item);
    }
    /**
     * Object:Ԥ����Ŀ's ����Ԥ����Ŀ����property 
     */
    public String getItemName()
    {
        return getItemName((Locale)null);
    }
    public void setItemName(String item)
    {
		setItemName(item,(Locale)null);
    }
    public String getItemName(Locale local)
    {
        return TypeConversionUtils.objToString(get("itemName", local));
    }
    public void setItemName(String item, Locale local)
    {
        put("itemName", item, local);
    }
    /**
     * Object: Ԥ����Ŀ 's ����Ԥ����Ŀ���� property 
     */
    public com.kingdee.eas.ma.budget.BgItemGroupInfo getBgItemGroup()
    {
        return (com.kingdee.eas.ma.budget.BgItemGroupInfo)get("bgItemGroup");
    }
    public void setBgItemGroup(com.kingdee.eas.ma.budget.BgItemGroupInfo item)
    {
        put("bgItemGroup", item);
    }
    /**
     * Object:Ԥ����Ŀ's ����property 
     */
    public boolean isIsEffective()
    {
        return getBoolean("isEffective");
    }
    public void setIsEffective(boolean item)
    {
        setBoolean("isEffective", item);
    }
    /**
     * Object: Ԥ����Ŀ 's ����� property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getParent()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:Ԥ����Ŀ's ��Ŀ����ֶ�property 
     */
    public String getRefField()
    {
        return getString("refField");
    }
    public void setRefField(String item)
    {
        setString("refField", item);
    }
    /**
     * Object:Ԥ����Ŀ's רҵ����property 
     */
    public String getSpecialNumber()
    {
        return getString("specialNumber");
    }
    public void setSpecialNumber(String item)
    {
        setString("specialNumber", item);
    }
    /**
     * Object: Ԥ����Ŀ 's �ֽ��������� property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getCashFlow()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("cashFlow");
    }
    public void setCashFlow(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("cashFlow", item);
    }
    /**
     * Object:Ԥ����Ŀ's �ֽ���property 
     */
    public com.kingdee.eas.ma.budget.BgCashDirectionEnum getCashDirection()
    {
        return com.kingdee.eas.ma.budget.BgCashDirectionEnum.getEnum(getInt("cashDirection"));
    }
    public void setCashDirection(com.kingdee.eas.ma.budget.BgCashDirectionEnum item)
    {
		if (item != null) {
        setInt("cashDirection", item.getValue());
		}
    }
    /**
     * Object: Ԥ����Ŀ 's ��֯ property 
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
     * Object: Ԥ����Ŀ 's bgItems���� property 
     */
    public com.kingdee.eas.ma.budget.BgItemEntryInfo getBgItems()
    {
        return (com.kingdee.eas.ma.budget.BgItemEntryInfo)get("bgItems");
    }
    public void setBgItems(com.kingdee.eas.ma.budget.BgItemEntryInfo item)
    {
        put("bgItems", item);
    }
    /**
     * Object:Ԥ����Ŀ's ����������϶�Ӧ��bosTypeproperty 
     */
    public String getBosType()
    {
        return getString("bosType");
    }
    public void setBosType(String item)
    {
        setString("bosType", item);
    }
    /**
     * Object:Ԥ����Ŀ's �����ʽ�ƻ�property 
     */
    public boolean isIsForFP()
    {
        return getBoolean("isForFP");
    }
    public void setIsForFP(boolean item)
    {
        setBoolean("isForFP", item);
    }
    /**
     * Object:Ԥ����Ŀ's �ʽ�����property 
     */
    public com.kingdee.eas.ma.budget.FpCashDirectionEnum getFpDirection()
    {
        return com.kingdee.eas.ma.budget.FpCashDirectionEnum.getEnum(getInt("fpDirection"));
    }
    public void setFpDirection(com.kingdee.eas.ma.budget.FpCashDirectionEnum item)
    {
		if (item != null) {
        setInt("fpDirection", item.getValue());
		}
    }
    /**
     * Object: Ԥ����Ŀ 's �����Ԥ����Ŀ property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getAssignParent()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("assignParent");
    }
    public void setAssignParent(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("assignParent", item);
    }
    /**
     * Object: Ԥ����Ŀ 's ����Ŀ property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getAssignRoot()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("assignRoot");
    }
    public void setAssignRoot(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("assignRoot", item);
    }
    /**
     * Object:Ԥ����Ŀ's Ԥ������ʽ�Ƿ�Ϊ�ܼ��ʽproperty 
     */
    public boolean isIsSumFormula()
    {
        return getBoolean("isSumFormula");
    }
    public void setIsSumFormula(boolean item)
    {
        setBoolean("isSumFormula", item);
    }
    /**
     * Object:Ԥ����Ŀ's Ԥ������ʽproperty 
     */
    public String getFormula()
    {
        return getString("formula");
    }
    public void setFormula(String item)
    {
        setString("formula", item);
    }
    /**
     * Object:Ԥ����Ŀ's ʵ������ʼ����ʽproperty 
     */
    public String getAcctInit()
    {
        return getString("acctInit");
    }
    public void setAcctInit(String item)
    {
        setString("acctInit", item);
    }
    /**
     * Object:Ԥ����Ŀ's ʵ�����ճ�ά����ʽproperty 
     */
    public String getAcctMaintain()
    {
        return getString("acctMaintain");
    }
    public void setAcctMaintain(String item)
    {
        setString("acctMaintain", item);
    }
    /**
     * Object: Ԥ����Ŀ 's Ԥ����Ŀ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAssignParentOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("assignParentOrg");
    }
    public void setAssignParentOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("assignParentOrg", item);
    }
    /**
     * Object: Ԥ����Ŀ 's Ԥ����Ŀ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAssignRootOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("assignRootOrg");
    }
    public void setAssignRootOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("assignRootOrg", item);
    }
    /**
     * Object:Ԥ����Ŀ's ��������property 
     */
    public com.kingdee.eas.ma.nbudget.BgElimTypeEnum getElimType()
    {
        return com.kingdee.eas.ma.nbudget.BgElimTypeEnum.getEnum(getInt("elimType"));
    }
    public void setElimType(com.kingdee.eas.ma.nbudget.BgElimTypeEnum item)
    {
		if (item != null) {
        setInt("elimType", item.getValue());
		}
    }
    /**
     * Object:Ԥ����Ŀ's ���ݹ�ϵproperty 
     */
    public com.kingdee.eas.ma.nbudget.BgDataRelationEnum getDataRelation()
    {
        return com.kingdee.eas.ma.nbudget.BgDataRelationEnum.getEnum(getInt("dataRelation"));
    }
    public void setDataRelation(com.kingdee.eas.ma.nbudget.BgDataRelationEnum item)
    {
		if (item != null) {
        setInt("dataRelation", item.getValue());
		}
    }
    /**
     * Object:Ԥ����Ŀ's ��ǰ��֯������property 
     */
    public String getCurOrgUnitLongNumber()
    {
        return getString("curOrgUnitLongNumber");
    }
    public void setCurOrgUnitLongNumber(String item)
    {
        setString("curOrgUnitLongNumber", item);
    }
    /**
     * Object:Ԥ����Ŀ's ����property 
     */
    public String getHelp()
    {
        return getString("Help");
    }
    public void setHelp(String item)
    {
        setString("Help", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FF67C4B9");
    }
}