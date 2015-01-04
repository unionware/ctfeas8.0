package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExcessSetEntryCollection extends AbstractObjectCollection 
{
    public ExcessSetEntryCollection()
    {
        super(ExcessSetEntryInfo.class);
    }
    public boolean add(ExcessSetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExcessSetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExcessSetEntryInfo item)
    {
        return removeObject(item);
    }
    public ExcessSetEntryInfo get(int index)
    {
        return(ExcessSetEntryInfo)getObject(index);
    }
    public ExcessSetEntryInfo get(Object key)
    {
        return(ExcessSetEntryInfo)getObject(key);
    }
    public void set(int index, ExcessSetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExcessSetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExcessSetEntryInfo item)
    {
        return super.indexOf(item);
    }
}