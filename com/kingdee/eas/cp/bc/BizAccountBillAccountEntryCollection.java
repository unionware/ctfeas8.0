package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizAccountBillAccountEntryCollection extends AbstractObjectCollection 
{
    public BizAccountBillAccountEntryCollection()
    {
        super(BizAccountBillAccountEntryInfo.class);
    }
    public boolean add(BizAccountBillAccountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizAccountBillAccountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizAccountBillAccountEntryInfo item)
    {
        return removeObject(item);
    }
    public BizAccountBillAccountEntryInfo get(int index)
    {
        return(BizAccountBillAccountEntryInfo)getObject(index);
    }
    public BizAccountBillAccountEntryInfo get(Object key)
    {
        return(BizAccountBillAccountEntryInfo)getObject(key);
    }
    public void set(int index, BizAccountBillAccountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizAccountBillAccountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizAccountBillAccountEntryInfo item)
    {
        return super.indexOf(item);
    }
}