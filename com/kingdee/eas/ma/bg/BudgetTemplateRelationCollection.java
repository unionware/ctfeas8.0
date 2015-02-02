package com.kingdee.eas.ma.bg;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BudgetTemplateRelationCollection extends AbstractObjectCollection 
{
    public BudgetTemplateRelationCollection()
    {
        super(BudgetTemplateRelationInfo.class);
    }
    public boolean add(BudgetTemplateRelationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BudgetTemplateRelationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BudgetTemplateRelationInfo item)
    {
        return removeObject(item);
    }
    public BudgetTemplateRelationInfo get(int index)
    {
        return(BudgetTemplateRelationInfo)getObject(index);
    }
    public BudgetTemplateRelationInfo get(Object key)
    {
        return(BudgetTemplateRelationInfo)getObject(key);
    }
    public void set(int index, BudgetTemplateRelationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BudgetTemplateRelationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BudgetTemplateRelationInfo item)
    {
        return super.indexOf(item);
    }
}