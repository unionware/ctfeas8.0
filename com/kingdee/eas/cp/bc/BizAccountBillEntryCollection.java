package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizAccountBillEntryCollection extends AbstractObjectCollection 
{
    public BizAccountBillEntryCollection()
    {
        super(BizAccountBillEntryInfo.class);
    }
    public boolean add(BizAccountBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizAccountBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizAccountBillEntryInfo item)
    {
        return removeObject(item);
    }
    public BizAccountBillEntryInfo get(int index)
    {
        return(BizAccountBillEntryInfo)getObject(index);
    }
    public BizAccountBillEntryInfo get(Object key)
    {
        return(BizAccountBillEntryInfo)getObject(key);
    }
    public void set(int index, BizAccountBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizAccountBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizAccountBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}