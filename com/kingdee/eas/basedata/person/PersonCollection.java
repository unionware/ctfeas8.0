package com.kingdee.eas.basedata.person;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PersonCollection extends AbstractObjectCollection 
{
    public PersonCollection()
    {
        super(PersonInfo.class);
    }
    public boolean add(PersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PersonInfo item)
    {
        return removeObject(item);
    }
    public PersonInfo get(int index)
    {
        return(PersonInfo)getObject(index);
    }
    public PersonInfo get(Object key)
    {
        return(PersonInfo)getObject(key);
    }
    public void set(int index, PersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PersonInfo item)
    {
        return super.indexOf(item);
    }
}