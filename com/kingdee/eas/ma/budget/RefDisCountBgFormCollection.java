package com.kingdee.eas.ma.budget;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RefDisCountBgFormCollection extends AbstractObjectCollection 
{
    public RefDisCountBgFormCollection()
    {
        super(RefDisCountBgFormInfo.class);
    }
    public boolean add(RefDisCountBgFormInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RefDisCountBgFormCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RefDisCountBgFormInfo item)
    {
        return removeObject(item);
    }
    public RefDisCountBgFormInfo get(int index)
    {
        return(RefDisCountBgFormInfo)getObject(index);
    }
    public RefDisCountBgFormInfo get(Object key)
    {
        return(RefDisCountBgFormInfo)getObject(key);
    }
    public void set(int index, RefDisCountBgFormInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RefDisCountBgFormInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RefDisCountBgFormInfo item)
    {
        return super.indexOf(item);
    }
}