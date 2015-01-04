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
public class ReturnBillTypeEnum extends StringEnum
{
    public static final String DAILYLOAN_VALUE = "0";//alias=还款
    public static final String EVECTIONLOAN_VALUE = "1";//alias=出差还款

    public static final ReturnBillTypeEnum DAILYLOAN = new ReturnBillTypeEnum("DAILYLOAN", DAILYLOAN_VALUE);
    public static final ReturnBillTypeEnum EVECTIONLOAN = new ReturnBillTypeEnum("EVECTIONLOAN", EVECTIONLOAN_VALUE);

    /**
     * construct function
     * @param String returnBillTypeEnum
     */
    private ReturnBillTypeEnum(String name, String returnBillTypeEnum)
    {
        super(name, returnBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReturnBillTypeEnum getEnum(String returnBillTypeEnum)
    {
        return (ReturnBillTypeEnum)getEnum(ReturnBillTypeEnum.class, returnBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReturnBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReturnBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReturnBillTypeEnum.class);
    }
}