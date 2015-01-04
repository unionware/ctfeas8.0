package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvectionLoanBillEntryCollection extends AbstractObjectCollection 
{
    public EvectionLoanBillEntryCollection()
    {
        super(EvectionLoanBillEntryInfo.class);
    }
    public boolean add(EvectionLoanBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvectionLoanBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvectionLoanBillEntryInfo item)
    {
        return removeObject(item);
    }
    public EvectionLoanBillEntryInfo get(int index)
    {
        return(EvectionLoanBillEntryInfo)getObject(index);
    }
    public EvectionLoanBillEntryInfo get(Object key)
    {
        return(EvectionLoanBillEntryInfo)getObject(key);
    }
    public void set(int index, EvectionLoanBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvectionLoanBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvectionLoanBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}