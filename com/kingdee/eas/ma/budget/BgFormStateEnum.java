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
public class BgFormStateEnum extends IntEnum
{
    public static final int EDITED_VALUE = 1;//alias=������
    public static final int APPROVED_VALUE = 2;//alias=������
    public static final int BASISAPPROVED_VALUE = 3;//alias=�����������
    public static final int ADJUSTED_VALUE = 4;//alias=������
    public static final int CANCELLED_VALUE = 5;//alias=������
    public static final int ROLLEDITED_VALUE = 6;//alias=����������
    public static final int APPROVING_VALUE = 14;//alias=������
    public static final int HISTORY_VALUE = 15;//alias=��ʷ�汾
    public static final int COLLECT_VALUE = 20;//alias=�����
    public static final int REFERRING_VALUE = 25;//alias=�ύ��
    public static final int DECOMPOSER_VALUE = 30;//alias=�ֽ���
    public static final int ADJUSTAPPROVING_VALUE = 35;//alias=����������
    public static final int REPORT_VALUE = 40;//alias=���ϱ�
    public static final int CERTIFICATE_VALUE = 41;//alias=���Ͽ�
    public static final int BACK_VALUE = 42;//alias=�ϼ����
    public static final int PERIODADJUSTED_VALUE = 43;//alias=�ڼ������
    public static final int CANTEDIT_VALUE = 97;//alias=δ����
    public static final int UNREPORT_VALUE = 98;//alias=δ�ϱ�
    public static final int UNCERTIFICATE_VALUE = 99;//alias=δ�Ͽ�
    public static final int COUNT_VALUE = 100;//alias=��������
    public static final int REVISING_VALUE = 101;//alias=�޶���
    public static final int DISCOUNTING_VALUE = 130;//alias=������

    public static final BgFormStateEnum Edited = new BgFormStateEnum("Edited", EDITED_VALUE);
    public static final BgFormStateEnum Approved = new BgFormStateEnum("Approved", APPROVED_VALUE);
    public static final BgFormStateEnum BasisApproved = new BgFormStateEnum("BasisApproved", BASISAPPROVED_VALUE);
    public static final BgFormStateEnum Adjusted = new BgFormStateEnum("Adjusted", ADJUSTED_VALUE);
    public static final BgFormStateEnum Cancelled = new BgFormStateEnum("Cancelled", CANCELLED_VALUE);
    public static final BgFormStateEnum RollEdited = new BgFormStateEnum("RollEdited", ROLLEDITED_VALUE);
    public static final BgFormStateEnum Approving = new BgFormStateEnum("Approving", APPROVING_VALUE);
    public static final BgFormStateEnum History = new BgFormStateEnum("History", HISTORY_VALUE);
    public static final BgFormStateEnum Collect = new BgFormStateEnum("Collect", COLLECT_VALUE);
    public static final BgFormStateEnum REFERRING = new BgFormStateEnum("REFERRING", REFERRING_VALUE);
    public static final BgFormStateEnum Decomposer = new BgFormStateEnum("Decomposer", DECOMPOSER_VALUE);
    public static final BgFormStateEnum AdjustApproving = new BgFormStateEnum("AdjustApproving", ADJUSTAPPROVING_VALUE);
    public static final BgFormStateEnum Report = new BgFormStateEnum("Report", REPORT_VALUE);
    public static final BgFormStateEnum Certificate = new BgFormStateEnum("Certificate", CERTIFICATE_VALUE);
    public static final BgFormStateEnum Back = new BgFormStateEnum("Back", BACK_VALUE);
    public static final BgFormStateEnum PeriodAdjusted = new BgFormStateEnum("PeriodAdjusted", PERIODADJUSTED_VALUE);
    public static final BgFormStateEnum CANTEDIT = new BgFormStateEnum("CANTEDIT", CANTEDIT_VALUE);
    public static final BgFormStateEnum UnReport = new BgFormStateEnum("UnReport", UNREPORT_VALUE);
    public static final BgFormStateEnum UnCertificate = new BgFormStateEnum("UnCertificate", UNCERTIFICATE_VALUE);
    public static final BgFormStateEnum Count = new BgFormStateEnum("Count", COUNT_VALUE);
    public static final BgFormStateEnum Revising = new BgFormStateEnum("Revising", REVISING_VALUE);
    public static final BgFormStateEnum DisCounting = new BgFormStateEnum("DisCounting", DISCOUNTING_VALUE);

    /**
     * construct function
     * @param integer bgFormStateEnum
     */
    private BgFormStateEnum(String name, int bgFormStateEnum)
    {
        super(name, bgFormStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BgFormStateEnum getEnum(String bgFormStateEnum)
    {
        return (BgFormStateEnum)getEnum(BgFormStateEnum.class, bgFormStateEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static BgFormStateEnum getEnum(int bgFormStateEnum)
    {
        return (BgFormStateEnum)getEnum(BgFormStateEnum.class, bgFormStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BgFormStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BgFormStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BgFormStateEnum.class);
    }
}