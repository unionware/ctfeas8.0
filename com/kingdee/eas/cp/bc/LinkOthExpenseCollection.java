package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LinkOthExpenseCollection extends AbstractObjectCollection 
{
    public LinkOthExpenseCollection()
    {
        super(LinkOthExpenseInfo.class);
    }
    public boolean add(LinkOthExpenseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LinkOthExpenseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LinkOthExpenseInfo item)
    {
        return removeObject(item);
    }
    public LinkOthExpenseInfo get(int index)
    {
        return(LinkOthExpenseInfo)getObject(index);
    }
    public LinkOthExpenseInfo get(Object key)
    {
        return(LinkOthExpenseInfo)getObject(key);
    }
    public void set(int index, LinkOthExpenseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LinkOthExpenseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LinkOthExpenseInfo item)
    {
        return super.indexOf(item);
    }
}