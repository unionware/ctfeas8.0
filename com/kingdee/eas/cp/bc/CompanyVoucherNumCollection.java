package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompanyVoucherNumCollection extends AbstractObjectCollection 
{
    public CompanyVoucherNumCollection()
    {
        super(CompanyVoucherNumInfo.class);
    }
    public boolean add(CompanyVoucherNumInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompanyVoucherNumCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompanyVoucherNumInfo item)
    {
        return removeObject(item);
    }
    public CompanyVoucherNumInfo get(int index)
    {
        return(CompanyVoucherNumInfo)getObject(index);
    }
    public CompanyVoucherNumInfo get(Object key)
    {
        return(CompanyVoucherNumInfo)getObject(key);
    }
    public void set(int index, CompanyVoucherNumInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompanyVoucherNumInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompanyVoucherNumInfo item)
    {
        return super.indexOf(item);
    }
}