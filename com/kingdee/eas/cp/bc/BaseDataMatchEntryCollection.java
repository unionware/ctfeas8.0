package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseDataMatchEntryCollection extends AbstractObjectCollection 
{
    public BaseDataMatchEntryCollection()
    {
        super(BaseDataMatchEntryInfo.class);
    }
    public boolean add(BaseDataMatchEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseDataMatchEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseDataMatchEntryInfo item)
    {
        return removeObject(item);
    }
    public BaseDataMatchEntryInfo get(int index)
    {
        return(BaseDataMatchEntryInfo)getObject(index);
    }
    public BaseDataMatchEntryInfo get(Object key)
    {
        return(BaseDataMatchEntryInfo)getObject(key);
    }
    public void set(int index, BaseDataMatchEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseDataMatchEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseDataMatchEntryInfo item)
    {
        return super.indexOf(item);
    }
}