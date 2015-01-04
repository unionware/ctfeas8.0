package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExcessSetCollection extends AbstractObjectCollection 
{
    public ExcessSetCollection()
    {
        super(ExcessSetInfo.class);
    }
    public boolean add(ExcessSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExcessSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExcessSetInfo item)
    {
        return removeObject(item);
    }
    public ExcessSetInfo get(int index)
    {
        return(ExcessSetInfo)getObject(index);
    }
    public ExcessSetInfo get(Object key)
    {
        return(ExcessSetInfo)getObject(key);
    }
    public void set(int index, ExcessSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExcessSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExcessSetInfo item)
    {
        return super.indexOf(item);
    }
}