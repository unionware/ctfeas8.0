package com.kingdee.eas.ma.bg;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBudgetTemplateRelationInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBudgetTemplateRelationInfo()
    {
        this("id");
    }
    protected AbstractBudgetTemplateRelationInfo(String pkField)
    {
        super(pkField);
        put("TemplateEntry", new com.kingdee.eas.ma.bg.BudgetTemplateRelationTemplateEntryCollection());
    }
    /**
     * Object: Ԥ��ģ�����ù�ϵ���� 's ģ�����ñ� property 
     */
    public com.kingdee.eas.ma.bg.BudgetTemplateRelationTemplateEntryCollection getTemplateEntry()
    {
        return (com.kingdee.eas.ma.bg.BudgetTemplateRelationTemplateEntryCollection)get("TemplateEntry");
    }
    /**
     * Object: Ԥ��ģ�����ù�ϵ���� 's ģ������ property 
     */
    public com.kingdee.eas.ma.budget.BgTemplateInfo getTemplateName()
    {
        return (com.kingdee.eas.ma.budget.BgTemplateInfo)get("TemplateName");
    }
    public void setTemplateName(com.kingdee.eas.ma.budget.BgTemplateInfo item)
    {
        put("TemplateName", item);
    }
    /**
     * Object:Ԥ��ģ�����ù�ϵ����'s ����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("IsEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("IsEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1C455C3C");
    }
}