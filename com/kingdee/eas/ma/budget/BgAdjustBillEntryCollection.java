package com.kingdee.eas.ma.budget;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BgAdjustBillEntryCollection extends AbstractObjectCollection 
{
    public BgAdjustBillEntryCollection()
    {
        super(BgAdjustBillEntryInfo.class);
    }
    public boolean add(BgAdjustBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BgAdjustBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BgAdjustBillEntryInfo item)
    {
        return removeObject(item);
    }
    public BgAdjustBillEntryInfo get(int index)
    {
        return(BgAdjustBillEntryInfo)getObject(index);
    }
    public BgAdjustBillEntryInfo get(Object key)
    {
        return(BgAdjustBillEntryInfo)getObject(key);
    }
    public void set(int index, BgAdjustBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BgAdjustBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BgAdjustBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}