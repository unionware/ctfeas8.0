/**
 * output package name
 */
package com.kingdee.eas.ma.budget;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class BgCollectStateEnum extends IntEnum
{
    public static final int COLLECTED_VALUE = 100;//alias=�����
    public static final int SIGNED_VALUE = 101;//alias=������
    public static final int AUDIT_VALUE = 102;//alias=������
    public static final int PERIODADJUSTED_VALUE = 103;//alias=�ڼ������
    public static final int NEWCOLLECTED_VALUE = 104;//alias=������

    public static final BgCollectStateEnum COLLECTED = new BgCollectStateEnum("COLLECTED", COLLECTED_VALUE);
    public static final BgCollectStateEnum SIGNED = new BgCollectStateEnum("SIGNED", SIGNED_VALUE);
    public static final BgCollectStateEnum AUDIT = new BgCollectStateEnum("AUDIT", AUDIT_VALUE);
    public static final BgCollectStateEnum PeriodAdjusted = new BgCollectStateEnum("PeriodAdjusted", PERIODADJUSTED_VALUE);
    public static final BgCollectStateEnum NEWCOLLECTED = new BgCollectStateEnum("NEWCOLLECTED", NEWCOLLECTED_VALUE);

    /**
     * construct function
     * @param integer bgCollectStateEnum
     */
    private BgCollectStateEnum(String name, int bgCollectStateEnum)
    {
        super(name, bgCollectStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BgCollectStateEnum getEnum(String bgCollectStateEnum)
    {
        return (BgCollectStateEnum)getEnum(BgCollectStateEnum.class, bgCollectStateEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static BgCollectStateEnum getEnum(int bgCollectStateEnum)
    {
        return (BgCollectStateEnum)getEnum(BgCollectStateEnum.class, bgCollectStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BgCollectStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BgCollectStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BgCollectStateEnum.class);
    }
}