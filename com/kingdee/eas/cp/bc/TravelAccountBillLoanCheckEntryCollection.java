package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TravelAccountBillLoanCheckEntryCollection extends AbstractObjectCollection 
{
    public TravelAccountBillLoanCheckEntryCollection()
    {
        super(TravelAccountBillLoanCheckEntryInfo.class);
    }
    public boolean add(TravelAccountBillLoanCheckEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TravelAccountBillLoanCheckEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TravelAccountBillLoanCheckEntryInfo item)
    {
        return removeObject(item);
    }
    public TravelAccountBillLoanCheckEntryInfo get(int index)
    {
        return(TravelAccountBillLoanCheckEntryInfo)getObject(index);
    }
    public TravelAccountBillLoanCheckEntryInfo get(Object key)
    {
        return(TravelAccountBillLoanCheckEntryInfo)getObject(key);
    }
    public void set(int index, TravelAccountBillLoanCheckEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TravelAccountBillLoanCheckEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TravelAccountBillLoanCheckEntryInfo item)
    {
        return super.indexOf(item);
    }
}