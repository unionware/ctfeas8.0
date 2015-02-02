package com.kingdee.eas.ma.budget;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BgSchemeCollection extends AbstractObjectCollection 
{
    public BgSchemeCollection()
    {
        super(BgSchemeInfo.class);
    }
    public boolean add(BgSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BgSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BgSchemeInfo item)
    {
        return removeObject(item);
    }
    public BgSchemeInfo get(int index)
    {
        return(BgSchemeInfo)getObject(index);
    }
    public BgSchemeInfo get(Object key)
    {
        return(BgSchemeInfo)getObject(key);
    }
    public void set(int index, BgSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BgSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BgSchemeInfo item)
    {
        return super.indexOf(item);
    }
}