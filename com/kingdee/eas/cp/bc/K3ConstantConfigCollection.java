package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class K3ConstantConfigCollection extends AbstractObjectCollection 
{
    public K3ConstantConfigCollection()
    {
        super(K3ConstantConfigInfo.class);
    }
    public boolean add(K3ConstantConfigInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(K3ConstantConfigCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(K3ConstantConfigInfo item)
    {
        return removeObject(item);
    }
    public K3ConstantConfigInfo get(int index)
    {
        return(K3ConstantConfigInfo)getObject(index);
    }
    public K3ConstantConfigInfo get(Object key)
    {
        return(K3ConstantConfigInfo)getObject(key);
    }
    public void set(int index, K3ConstantConfigInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(K3ConstantConfigInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(K3ConstantConfigInfo item)
    {
        return super.indexOf(item);
    }
}