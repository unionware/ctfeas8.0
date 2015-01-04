package com.kingdee.eas.basedata.person;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BankInfoCollection extends AbstractObjectCollection 
{
    public BankInfoCollection()
    {
        super(BankInfoInfo.class);
    }
    public boolean add(BankInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BankInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BankInfoInfo item)
    {
        return removeObject(item);
    }
    public BankInfoInfo get(int index)
    {
        return(BankInfoInfo)getObject(index);
    }
    public BankInfoInfo get(Object key)
    {
        return(BankInfoInfo)getObject(key);
    }
    public void set(int index, BankInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BankInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BankInfoInfo item)
    {
        return super.indexOf(item);
    }
}