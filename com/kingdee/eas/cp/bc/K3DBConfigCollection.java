package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class K3DBConfigCollection extends AbstractObjectCollection 
{
    public K3DBConfigCollection()
    {
        super(K3DBConfigInfo.class);
    }
    public boolean add(K3DBConfigInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(K3DBConfigCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(K3DBConfigInfo item)
    {
        return removeObject(item);
    }
    public K3DBConfigInfo get(int index)
    {
        return(K3DBConfigInfo)getObject(index);
    }
    public K3DBConfigInfo get(Object key)
    {
        return(K3DBConfigInfo)getObject(key);
    }
    public void set(int index, K3DBConfigInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(K3DBConfigInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(K3DBConfigInfo item)
    {
        return super.indexOf(item);
    }
}