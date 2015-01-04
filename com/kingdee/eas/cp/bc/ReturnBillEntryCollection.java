package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReturnBillEntryCollection extends AbstractObjectCollection 
{
    public ReturnBillEntryCollection()
    {
        super(ReturnBillEntryInfo.class);
    }
    public boolean add(ReturnBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReturnBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReturnBillEntryInfo item)
    {
        return removeObject(item);
    }
    public ReturnBillEntryInfo get(int index)
    {
        return(ReturnBillEntryInfo)getObject(index);
    }
    public ReturnBillEntryInfo get(Object key)
    {
        return(ReturnBillEntryInfo)getObject(key);
    }
    public void set(int index, ReturnBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReturnBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReturnBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}