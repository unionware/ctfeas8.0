package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DailyLoanBillEntryCollection extends AbstractObjectCollection 
{
    public DailyLoanBillEntryCollection()
    {
        super(DailyLoanBillEntryInfo.class);
    }
    public boolean add(DailyLoanBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DailyLoanBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DailyLoanBillEntryInfo item)
    {
        return removeObject(item);
    }
    public DailyLoanBillEntryInfo get(int index)
    {
        return(DailyLoanBillEntryInfo)getObject(index);
    }
    public DailyLoanBillEntryInfo get(Object key)
    {
        return(DailyLoanBillEntryInfo)getObject(key);
    }
    public void set(int index, DailyLoanBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DailyLoanBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DailyLoanBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}