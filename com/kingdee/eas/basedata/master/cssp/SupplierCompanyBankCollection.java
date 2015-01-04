package com.kingdee.eas.basedata.master.cssp;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierCompanyBankCollection extends AbstractObjectCollection 
{
    public SupplierCompanyBankCollection()
    {
        super(SupplierCompanyBankInfo.class);
    }
    public boolean add(SupplierCompanyBankInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierCompanyBankCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierCompanyBankInfo item)
    {
        return removeObject(item);
    }
    public SupplierCompanyBankInfo get(int index)
    {
        return(SupplierCompanyBankInfo)getObject(index);
    }
    public SupplierCompanyBankInfo get(Object key)
    {
        return(SupplierCompanyBankInfo)getObject(key);
    }
    public void set(int index, SupplierCompanyBankInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierCompanyBankInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierCompanyBankInfo item)
    {
        return super.indexOf(item);
    }
}