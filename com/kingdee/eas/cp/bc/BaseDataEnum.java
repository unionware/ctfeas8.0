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
public class BaseDataEnum extends StringEnum
{
    public static final String ORGUNIT_VALUE = "org";//alias=组织
    public static final String ACCOUNT_VALUE = "account";//alias=科目
    public static final String PERSON_VALUE = "person";//alias=职员
    public static final String PROJECT_VALUE = "project";//alias=项目
    public static final String DEPARTMENT_VALUE = "dept";//alias=部门

    public static final BaseDataEnum ORGUNIT = new BaseDataEnum("ORGUNIT", ORGUNIT_VALUE);
    public static final BaseDataEnum ACCOUNT = new BaseDataEnum("ACCOUNT", ACCOUNT_VALUE);
    public static final BaseDataEnum PERSON = new BaseDataEnum("PERSON", PERSON_VALUE);
    public static final BaseDataEnum PROJECT = new BaseDataEnum("PROJECT", PROJECT_VALUE);
    public static final BaseDataEnum DEPARTMENT = new BaseDataEnum("DEPARTMENT", DEPARTMENT_VALUE);

    /**
     * construct function
     * @param String baseDataEnum
     */
    private BaseDataEnum(String name, String baseDataEnum)
    {
        super(name, baseDataEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BaseDataEnum getEnum(String baseDataEnum)
    {
        return (BaseDataEnum)getEnum(BaseDataEnum.class, baseDataEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BaseDataEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BaseDataEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BaseDataEnum.class);
    }
}