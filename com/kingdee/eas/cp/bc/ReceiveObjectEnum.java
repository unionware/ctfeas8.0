/**
 * output package name
 */
package com.kingdee.eas.cp.bc;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ReceiveObjectEnum extends StringEnum
{
    public static final String SUPPLIER_VALUE = "supplier";//alias=供应商
    public static final String PERSONAL_VALUE = "personal";//alias=个人

    public static final ReceiveObjectEnum supplier = new ReceiveObjectEnum("supplier", SUPPLIER_VALUE);
    public static final ReceiveObjectEnum personal = new ReceiveObjectEnum("personal", PERSONAL_VALUE);

    /**
     * construct function
     * @param String receiveObjectEnum
     */
    private ReceiveObjectEnum(String name, String receiveObjectEnum)
    {
        super(name, receiveObjectEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReceiveObjectEnum getEnum(String receiveObjectEnum)
    {
        return (ReceiveObjectEnum)getEnum(ReceiveObjectEnum.class, receiveObjectEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReceiveObjectEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReceiveObjectEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReceiveObjectEnum.class);
    }
}