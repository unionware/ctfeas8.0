package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherExpenseBillEntryCollection extends AbstractObjectCollection 
{
    public OtherExpenseBillEntryCollection()
    {
        super(OtherExpenseBillEntryInfo.class);
    }
    public boolean add(OtherExpenseBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherExpenseBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherExpenseBillEntryInfo item)
    {
        return removeObject(item);
    }
    public OtherExpenseBillEntryInfo get(int index)
    {
        return(OtherExpenseBillEntryInfo)getObject(index);
    }
    public OtherExpenseBillEntryInfo get(Object key)
    {
        return(OtherExpenseBillEntryInfo)getObject(key);
    }
    public void set(int index, OtherExpenseBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherExpenseBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherExpenseBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}