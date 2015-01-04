package com.kingdee.eas.basedata.org;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PositionCollection extends AbstractObjectCollection 
{
    public PositionCollection()
    {
        super(PositionInfo.class);
    }
    public boolean add(PositionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PositionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PositionInfo item)
    {
        return removeObject(item);
    }
    public PositionInfo get(int index)
    {
        return(PositionInfo)getObject(index);
    }
    public PositionInfo get(Object key)
    {
        return(PositionInfo)getObject(key);
    }
    public void set(int index, PositionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PositionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PositionInfo item)
    {
        return super.indexOf(item);
    }
}