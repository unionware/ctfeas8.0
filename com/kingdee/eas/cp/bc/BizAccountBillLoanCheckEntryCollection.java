package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizAccountBillLoanCheckEntryCollection extends AbstractObjectCollection 
{
    public BizAccountBillLoanCheckEntryCollection()
    {
        super(BizAccountBillLoanCheckEntryInfo.class);
    }
    public boolean add(BizAccountBillLoanCheckEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizAccountBillLoanCheckEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizAccountBillLoanCheckEntryInfo item)
    {
        return removeObject(item);
    }
    public BizAccountBillLoanCheckEntryInfo get(int index)
    {
        return(BizAccountBillLoanCheckEntryInfo)getObject(index);
    }
    public BizAccountBillLoanCheckEntryInfo get(Object key)
    {
        return(BizAccountBillLoanCheckEntryInfo)getObject(key);
    }
    public void set(int index, BizAccountBillLoanCheckEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizAccountBillLoanCheckEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizAccountBillLoanCheckEntryInfo item)
    {
        return super.indexOf(item);
    }
}