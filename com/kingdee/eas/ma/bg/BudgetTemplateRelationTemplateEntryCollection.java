package com.kingdee.eas.ma.bg;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BudgetTemplateRelationTemplateEntryCollection extends AbstractObjectCollection 
{
    public BudgetTemplateRelationTemplateEntryCollection()
    {
        super(BudgetTemplateRelationTemplateEntryInfo.class);
    }
    public boolean add(BudgetTemplateRelationTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BudgetTemplateRelationTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BudgetTemplateRelationTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public BudgetTemplateRelationTemplateEntryInfo get(int index)
    {
        return(BudgetTemplateRelationTemplateEntryInfo)getObject(index);
    }
    public BudgetTemplateRelationTemplateEntryInfo get(Object key)
    {
        return(BudgetTemplateRelationTemplateEntryInfo)getObject(key);
    }
    public void set(int index, BudgetTemplateRelationTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BudgetTemplateRelationTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BudgetTemplateRelationTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}