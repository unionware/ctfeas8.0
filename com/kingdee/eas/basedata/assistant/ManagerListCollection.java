package com.kingdee.eas.basedata.assistant;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ManagerListCollection extends AbstractObjectCollection 
{
    public ManagerListCollection()
    {
        super(ManagerListInfo.class);
    }
    public boolean add(ManagerListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ManagerListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ManagerListInfo item)
    {
        return removeObject(item);
    }
    public ManagerListInfo get(int index)
    {
        return(ManagerListInfo)getObject(index);
    }
    public ManagerListInfo get(Object key)
    {
        return(ManagerListInfo)getObject(key);
    }
    public void set(int index, ManagerListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ManagerListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ManagerListInfo item)
    {
        return super.indexOf(item);
    }
}