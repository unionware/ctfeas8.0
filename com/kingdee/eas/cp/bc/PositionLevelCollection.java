package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PositionLevelCollection extends AbstractObjectCollection 
{
    public PositionLevelCollection()
    {
        super(PositionLevelInfo.class);
    }
    public boolean add(PositionLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PositionLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PositionLevelInfo item)
    {
        return removeObject(item);
    }
    public PositionLevelInfo get(int index)
    {
        return(PositionLevelInfo)getObject(index);
    }
    public PositionLevelInfo get(Object key)
    {
        return(PositionLevelInfo)getObject(key);
    }
    public void set(int index, PositionLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PositionLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PositionLevelInfo item)
    {
        return super.indexOf(item);
    }
}