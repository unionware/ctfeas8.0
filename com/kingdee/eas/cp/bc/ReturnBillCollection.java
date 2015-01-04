package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReturnBillCollection extends AbstractObjectCollection 
{
    public ReturnBillCollection()
    {
        super(ReturnBillInfo.class);
    }
    public boolean add(ReturnBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReturnBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReturnBillInfo item)
    {
        return removeObject(item);
    }
    public ReturnBillInfo get(int index)
    {
        return(ReturnBillInfo)getObject(index);
    }
    public ReturnBillInfo get(Object key)
    {
        return(ReturnBillInfo)getObject(key);
    }
    public void set(int index, ReturnBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReturnBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReturnBillInfo item)
    {
        return super.indexOf(item);
    }
}