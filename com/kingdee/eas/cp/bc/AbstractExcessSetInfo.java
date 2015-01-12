package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExcessSetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractExcessSetInfo()
    {
        this("id");
    }
    protected AbstractExcessSetInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.cp.bc.ExcessSetEntryCollection());
    }
    /**
     * Object: �������ͳ���������� 's null property 
     */
    public com.kingdee.eas.cp.bc.ExcessSetEntryCollection getEntry()
    {
        return (com.kingdee.eas.cp.bc.ExcessSetEntryCollection)get("entry");
    }
    /**
     * Object:�������ͳ����������'s ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object: �������ͳ���������� 's �������� property 
     */
    public com.kingdee.eas.cp.bc.ExpenseTypeInfo getExpenseType()
    {
        return (com.kingdee.eas.cp.bc.ExpenseTypeInfo)get("expenseType");
    }
    public void setExpenseType(com.kingdee.eas.cp.bc.ExpenseTypeInfo item)
    {
        put("expenseType", item);
    }
    /**
     * Object: �������ͳ���������� 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�������ͳ����������'s ���������%��property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:�������ͳ����������'s �Ƿ�����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5DC2FC0F");
    }
}