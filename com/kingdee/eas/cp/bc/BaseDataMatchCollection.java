package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseDataMatchCollection extends AbstractObjectCollection 
{
    public BaseDataMatchCollection()
    {
        super(BaseDataMatchInfo.class);
    }
    public boolean add(BaseDataMatchInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseDataMatchCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseDataMatchInfo item)
    {
        return removeObject(item);
    }
    public BaseDataMatchInfo get(int index)
    {
        return(BaseDataMatchInfo)getObject(index);
    }
    public BaseDataMatchInfo get(Object key)
    {
        return(BaseDataMatchInfo)getObject(key);
    }
    public void set(int index, BaseDataMatchInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseDataMatchInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseDataMatchInfo item)
    {
        return super.indexOf(item);
    }
}