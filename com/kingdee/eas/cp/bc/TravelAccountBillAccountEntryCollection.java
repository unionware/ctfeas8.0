package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TravelAccountBillAccountEntryCollection extends AbstractObjectCollection 
{
    public TravelAccountBillAccountEntryCollection()
    {
        super(TravelAccountBillAccountEntryInfo.class);
    }
    public boolean add(TravelAccountBillAccountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TravelAccountBillAccountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TravelAccountBillAccountEntryInfo item)
    {
        return removeObject(item);
    }
    public TravelAccountBillAccountEntryInfo get(int index)
    {
        return(TravelAccountBillAccountEntryInfo)getObject(index);
    }
    public TravelAccountBillAccountEntryInfo get(Object key)
    {
        return(TravelAccountBillAccountEntryInfo)getObject(key);
    }
    public void set(int index, TravelAccountBillAccountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TravelAccountBillAccountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TravelAccountBillAccountEntryInfo item)
    {
        return super.indexOf(item);
    }
}