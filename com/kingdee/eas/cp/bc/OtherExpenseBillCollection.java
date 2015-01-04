package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherExpenseBillCollection extends AbstractObjectCollection 
{
    public OtherExpenseBillCollection()
    {
        super(OtherExpenseBillInfo.class);
    }
    public boolean add(OtherExpenseBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherExpenseBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherExpenseBillInfo item)
    {
        return removeObject(item);
    }
    public OtherExpenseBillInfo get(int index)
    {
        return(OtherExpenseBillInfo)getObject(index);
    }
    public OtherExpenseBillInfo get(Object key)
    {
        return(OtherExpenseBillInfo)getObject(key);
    }
    public void set(int index, OtherExpenseBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherExpenseBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherExpenseBillInfo item)
    {
        return super.indexOf(item);
    }
}