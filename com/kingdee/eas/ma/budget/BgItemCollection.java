package com.kingdee.eas.ma.budget;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BgItemCollection extends AbstractObjectCollection 
{
    public BgItemCollection()
    {
        super(BgItemInfo.class);
    }
    public boolean add(BgItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BgItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BgItemInfo item)
    {
        return removeObject(item);
    }
    public BgItemInfo get(int index)
    {
        return(BgItemInfo)getObject(index);
    }
    public BgItemInfo get(Object key)
    {
        return(BgItemInfo)getObject(key);
    }
    public void set(int index, BgItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BgItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BgItemInfo item)
    {
        return super.indexOf(item);
    }
}