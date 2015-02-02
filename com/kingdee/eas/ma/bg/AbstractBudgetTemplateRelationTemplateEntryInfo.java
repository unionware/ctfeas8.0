package com.kingdee.eas.ma.bg;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBudgetTemplateRelationTemplateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBudgetTemplateRelationTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractBudgetTemplateRelationTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 模板引用表 's null property 
     */
    public com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo getParent()
    {
        return (com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 模板引用表 's 被引用模板 property 
     */
    public com.kingdee.eas.ma.budget.BgTemplateInfo getReferencedTemplet()
    {
        return (com.kingdee.eas.ma.budget.BgTemplateInfo)get("ReferencedTemplet");
    }
    public void setReferencedTemplet(com.kingdee.eas.ma.budget.BgTemplateInfo item)
    {
        put("ReferencedTemplet", item);
    }
    /**
     * Object:模板引用表's 备注property 
     */
    public String getRemark()
    {
        return getString("Remark");
    }
    public void setRemark(String item)
    {
        setString("Remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("83BE9D3C");
    }
}