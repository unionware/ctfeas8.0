package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizCollBillBaseCollection extends AbstractObjectCollection 
{
    public BizCollBillBaseCollection()
    {
        super(BizCollBillBaseInfo.class);
    }
    public boolean add(BizCollBillBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizCollBillBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizCollBillBaseInfo item)
    {
        return removeObject(item);
    }
    public BizCollBillBaseInfo get(int index)
    {
        return(BizCollBillBaseInfo)getObject(index);
    }
    public BizCollBillBaseInfo get(Object key)
    {
        return(BizCollBillBaseInfo)getObject(key);
    }
    public void set(int index, BizCollBillBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizCollBillBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizCollBillBaseInfo item)
    {
        return super.indexOf(item);
    }
}