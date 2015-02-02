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
     * Object:预算项目's 导入预算项目内码property 
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
     * Object:预算项目's 导入预算项目编码property 
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
     * Object:预算项目's 导入预算项目名称property 
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
     * Object: 预算项目 's 所属预算项目分组 property 
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
     * Object:预算项目's 启用property 
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
     * Object: 预算项目 's 父结点 property 
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
     * Object:预算项目's 项目组合字段property 
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
     * Object:预算项目's 专业编码property 
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
     * Object: 预算项目 's 现金流量属性 property 
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
     * Object:预算项目's 现金方向property 
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
     * Object: 预算项目 's 组织 property 
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
     * Object: 预算项目 's bgItems主体 property 
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
     * Object:预算项目's 导入基础资料对应的bosTypeproperty 
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
     * Object:预算项目's 用于资金计划property 
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
     * Object:预算项目's 资金性质property 
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
     * Object: 预算项目 's 分配的预算项目 property 
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
     * Object: 预算项目 's 根项目 property 
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
     * Object:预算项目's 预算数公式是否为总计项公式property 
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
     * Object:预算项目's 预算数公式property 
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
     * Object:预算项目's 实际数初始化公式property 
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
     * Object:预算项目's 实际数日常维护公式property 
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
     * Object: 预算项目 's 预算项目分配组织 property 
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
     * Object: 预算项目 's 预算项目创建组织 property 
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
     * Object:预算项目's 抵销类型property 
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
     * Object:预算项目's 数据关系property 
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
     * Object:预算项目's 当前组织长编码property 
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
     * Object:预算项目's 帮助property 
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