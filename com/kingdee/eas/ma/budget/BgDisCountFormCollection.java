package com.kingdee.eas.ma.budget;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BgDisCountFormCollection extends AbstractObjectCollection 
{
    public BgDisCountFormCollection()
    {
        super(BgDisCountFormInfo.class);
    }
    public boolean add(BgDisCountFormInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BgDisCountFormCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BgDisCountFormInfo item)
    {
        return removeObject(item);
    }
    public BgDisCountFormInfo get(int index)
    {
        return(BgDisCountFormInfo)getObject(index);
    }
    public BgDisCountFormInfo get(Object key)
    {
        return(BgDisCountFormInfo)getObject(key);
    }
    public void set(int index, BgDisCountFormInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BgDisCountFormInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BgDisCountFormInfo item)
    {
        return super.indexOf(item);
    }
}