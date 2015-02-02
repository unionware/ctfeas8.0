package com.kingdee.eas.ma.crbg.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BeginHiDetailBillCollection extends AbstractObjectCollection 
{
    public BeginHiDetailBillCollection()
    {
        super(BeginHiDetailBillInfo.class);
    }
    public boolean add(BeginHiDetailBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BeginHiDetailBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BeginHiDetailBillInfo item)
    {
        return removeObject(item);
    }
    public BeginHiDetailBillInfo get(int index)
    {
        return(BeginHiDetailBillInfo)getObject(index);
    }
    public BeginHiDetailBillInfo get(Object key)
    {
        return(BeginHiDetailBillInfo)getObject(key);
    }
    public void set(int index, BeginHiDetailBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BeginHiDetailBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BeginHiDetailBillInfo item)
    {
        return super.indexOf(item);
    }
}