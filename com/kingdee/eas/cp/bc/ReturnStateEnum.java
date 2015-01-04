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
public class ReturnStateEnum extends StringEnum
{
    public static final String TEMPSAVE_VALUE = "0";//alias=暂存
    public static final String SUBMITEDPAID_VALUE = "1";//alias=已提交还款
    public static final String COMFIRMPAID_VALUE = "2";//alias=已确认还款

    public static final ReturnStateEnum TEMPSAVE = new ReturnStateEnum("TEMPSAVE", TEMPSAVE_VALUE);
    public static final ReturnStateEnum SUBMITEDPAID = new ReturnStateEnum("SUBMITEDPAID", SUBMITEDPAID_VALUE);
    public static final ReturnStateEnum COMFIRMPAID = new ReturnStateEnum("COMFIRMPAID", COMFIRMPAID_VALUE);

    /**
     * construct function
     * @param String returnStateEnum
     */
    private ReturnStateEnum(String name, String returnStateEnum)
    {
        super(name, returnStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ReturnStateEnum getEnum(String returnStateEnum)
    {
        return (ReturnStateEnum)getEnum(ReturnStateEnum.class, returnStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ReturnStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ReturnStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ReturnStateEnum.class);
    }
}