package com.kingdee.eas.basedata.centralpolicy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CentralpolicyCollection extends AbstractObjectCollection 
{
    public CentralpolicyCollection()
    {
        super(CentralpolicyInfo.class);
    }
    public boolean add(CentralpolicyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CentralpolicyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CentralpolicyInfo item)
    {
        return removeObject(item);
    }
    public CentralpolicyInfo get(int index)
    {
        return(CentralpolicyInfo)getObject(index);
    }
    public CentralpolicyInfo get(Object key)
    {
        return(CentralpolicyInfo)getObject(key);
    }
    public void set(int index, CentralpolicyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CentralpolicyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CentralpolicyInfo item)
    {
        return super.indexOf(item);
    }
}