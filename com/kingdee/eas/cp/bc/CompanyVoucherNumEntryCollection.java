package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompanyVoucherNumEntryCollection extends AbstractObjectCollection 
{
    public CompanyVoucherNumEntryCollection()
    {
        super(CompanyVoucherNumEntryInfo.class);
    }
    public boolean add(CompanyVoucherNumEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompanyVoucherNumEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompanyVoucherNumEntryInfo item)
    {
        return removeObject(item);
    }
    public CompanyVoucherNumEntryInfo get(int index)
    {
        return(CompanyVoucherNumEntryInfo)getObject(index);
    }
    public CompanyVoucherNumEntryInfo get(Object key)
    {
        return(CompanyVoucherNumEntryInfo)getObject(key);
    }
    public void set(int index, CompanyVoucherNumEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompanyVoucherNumEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompanyVoucherNumEntryInfo item)
    {
        return super.indexOf(item);
    }
}