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
public class BgFormDataSourceEnum extends IntEnum
{
    public static final int SUBORDINATEDECLARE_VALUE = 1;//alias=�¼��걨
    public static final int CURRENTWORKOUT_VALUE = 0;//alias=��������
    public static final int SUPERIORSPLIT_VALUE = -1;//alias=�ϼ��ֽ�
    public static final int ADJUST_VALUE = 2;//alias=Ԥ�����
    public static final int INCREASE_VALUE = 3;//alias=׷��
    public static final int FORMCOLLECT_VALUE = 4;//alias=�¼�����
    public static final int SUPERIORCOLLECT_VALUE = 5;//alias=���
    public static final int BGPERIODEDIT_VALUE = 6;//alias=�ڼ����
    public static final int CURRENCYCONVERSION_VALUE = 7;//alias=�ұ�ת��
    public static final int CSLREPORT_VALUE = 8;//alias=�ϲ�������
    public static final int PERIODDEC_VALUE = 9;//alias=�ڼ�ֽ�
    public static final int PERIODADJUSTED_VALUE = 10;//alias=�ڼ����
    public static final int APPROVE_VALUE = 11;//alias=�Ͽ�
    public static final int MAMAKE_VALUE = 12;//alias=ģ��Ԥ������
    public static final int ROLLBGFORM_VALUE = 13;//alias=Ԥ�����
    public static final int PLANUPDATE_VALUE = 14;//alias=�ƻ�����
    public static final int DISCOUNT_VALUE = 19;//alias=��������

    public static final BgFormDataSourceEnum SUBORDINATEDECLARE = new BgFormDataSourceEnum("SUBORDINATEDECLARE", SUBORDINATEDECLARE_VALUE);
    public static final BgFormDataSourceEnum CURRENTWORKOUT = new BgFormDataSourceEnum("CURRENTWORKOUT", CURRENTWORKOUT_VALUE);
    public static final BgFormDataSourceEnum SUPERIORSPLIT = new BgFormDataSourceEnum("SUPERIORSPLIT", SUPERIORSPLIT_VALUE);
    public static final BgFormDataSourceEnum ADJUST = new BgFormDataSourceEnum("ADJUST", ADJUST_VALUE);
    public static final BgFormDataSourceEnum INCREASE = new BgFormDataSourceEnum("INCREASE", INCREASE_VALUE);
    public static final BgFormDataSourceEnum FORMCOLLECT = new BgFormDataSourceEnum("FORMCOLLECT", FORMCOLLECT_VALUE);
    public static final BgFormDataSourceEnum SUPERIORCOLLECT = new BgFormDataSourceEnum("SUPERIORCOLLECT", SUPERIORCOLLECT_VALUE);
    public static final BgFormDataSourceEnum BGPERIODEDIT = new BgFormDataSourceEnum("BGPERIODEDIT", BGPERIODEDIT_VALUE);
    public static final BgFormDataSourceEnum CURRENCYCONVERSION = new BgFormDataSourceEnum("CURRENCYCONVERSION", CURRENCYCONVERSION_VALUE);
    public static final BgFormDataSourceEnum CSLREPORT = new BgFormDataSourceEnum("CSLREPORT", CSLREPORT_VALUE);
    public static final BgFormDataSourceEnum PERIODDEC = new BgFormDataSourceEnum("PERIODDEC", PERIODDEC_VALUE);
    public static final BgFormDataSourceEnum PERIODADJUSTED = new BgFormDataSourceEnum("PERIODADJUSTED", PERIODADJUSTED_VALUE);
    public static final BgFormDataSourceEnum APPROVE = new BgFormDataSourceEnum("APPROVE", APPROVE_VALUE);
    public static final BgFormDataSourceEnum MAMAKE = new BgFormDataSourceEnum("MAMAKE", MAMAKE_VALUE);
    public static final BgFormDataSourceEnum ROLLBGFORM = new BgFormDataSourceEnum("ROLLBGFORM", ROLLBGFORM_VALUE);
    public static final BgFormDataSourceEnum PLANUPDATE = new BgFormDataSourceEnum("PLANUPDATE", PLANUPDATE_VALUE);
    public static final BgFormDataSourceEnum DISCOUNT = new BgFormDataSourceEnum("DISCOUNT", DISCOUNT_VALUE);

    /**
     * construct function
     * @param integer bgFormDataSourceEnum
     */
    private BgFormDataSourceEnum(String name, int bgFormDataSourceEnum)
    {
        super(name, bgFormDataSourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BgFormDataSourceEnum getEnum(String bgFormDataSourceEnum)
    {
        return (BgFormDataSourceEnum)getEnum(BgFormDataSourceEnum.class, bgFormDataSourceEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static BgFormDataSourceEnum getEnum(int bgFormDataSourceEnum)
    {
        return (BgFormDataSourceEnum)getEnum(BgFormDataSourceEnum.class, bgFormDataSourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BgFormDataSourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BgFormDataSourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BgFormDataSourceEnum.class);
    }
}