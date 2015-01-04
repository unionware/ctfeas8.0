package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PositionLevelTreeCollection extends AbstractObjectCollection 
{
    public PositionLevelTreeCollection()
    {
        super(PositionLevelTreeInfo.class);
    }
    public boolean add(PositionLevelTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PositionLevelTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PositionLevelTreeInfo item)
    {
        return removeObject(item);
    }
    public PositionLevelTreeInfo get(int index)
    {
        return(PositionLevelTreeInfo)getObject(index);
    }
    public PositionLevelTreeInfo get(Object key)
    {
        return(PositionLevelTreeInfo)getObject(key);
    }
    public void set(int index, PositionLevelTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PositionLevelTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PositionLevelTreeInfo item)
    {
        return super.indexOf(item);
    }
}