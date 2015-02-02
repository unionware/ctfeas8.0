/**
 * output package name
 */
package com.kingdee.eas.cp.bc;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class ExpenseReqException extends EASBizException
{
    private static final String MAINCODE = "10";

    public static final NumericExceptionSubItem NULL_NAME_EXCEPTION = new NumericExceptionSubItem("010", "Null_Name_Exception");
    public static final NumericExceptionSubItem NULL_NUMBER_EXCEPTION = new NumericExceptionSubItem("000", "Null_Number_Exception");
    public static final NumericExceptionSubItem NULL_COSTDEPT_EXCEPTION = new NumericExceptionSubItem("001", "Null_CostDept_Exception");
    public static final NumericExceptionSubItem NULL_COMAPNY_EXCEPTION = new NumericExceptionSubItem("002", "Null_Comapny_Exception");
    public static final NumericExceptionSubItem NULL_PRIOR_EXCEPTION = new NumericExceptionSubItem("003", "Null_Prior_Exception");
    public static final NumericExceptionSubItem NULL_CURRENCY_EXCEPTION = new NumericExceptionSubItem("004", "Null_Currency_Exception");
    public static final NumericExceptionSubItem NULL_REQDATE_EXCEPTION = new NumericExceptionSubItem("005", "Null_ReqDate_Exception");
    public static final NumericExceptionSubItem NULL_COSTTYPE_EXCEPTION = new NumericExceptionSubItem("006", "Null_CostType_Exception");
    public static final NumericExceptionSubItem NULL_SUPORTOBJ_EXCEPTION = new NumericExceptionSubItem("007", "Null_SuportObj_Exception");
    public static final NumericExceptionSubItem NULL_APLLIER_EXCEPTION = new NumericExceptionSubItem("008", "Null_Apllier_Exception");
    public static final NumericExceptionSubItem NULL_REQDEPT_EXCEPTION = new NumericExceptionSubItem("009", "Null_ReqDept_Exception");
    public static final NumericExceptionSubItem NULL_ENTRY_EXCEPTION = new NumericExceptionSubItem("011", "Null_Entry_Exception");
    public static final NumericExceptionSubItem NULL_ENTRYAMOUNT_EXCEPTION = new NumericExceptionSubItem("012", "Null_EntryAmount_Exception");
    public static final NumericExceptionSubItem NULL_REASON_EXCEPTION = new NumericExceptionSubItem("013", "Null_Reason_Exception");
    public static final NumericExceptionSubItem NULL_ACTIVITYNAME_EXCEPTION = new NumericExceptionSubItem("014", "Null_ActivityName_Exception");
    public static final NumericExceptionSubItem NULL_ENTRYMATERIALNAME_EXCEPTION = new NumericExceptionSubItem("015", "Null_EntryMaterialName_Exception");
    public static final NumericExceptionSubItem NULL_ENTRYCOUNT_EXCEPTION = new NumericExceptionSubItem("016", "Null_EntryCount_Exception");
    public static final NumericExceptionSubItem NULL_ENTRYPRICE_EXCEPTION = new NumericExceptionSubItem("017", "Null_EntryPrice_Exception");
    public static final NumericExceptionSubItem NULL_ACTIVITYCONTENT_EXCEPTION = new NumericExceptionSubItem("018", "Null_ActivityContent_Exception");
    public static final NumericExceptionSubItem NULL_ACTIVITYTIME_EXCEPTION = new NumericExceptionSubItem("019", "Null_ActivityTime_Exception");
    public static final NumericExceptionSubItem DUP_NUMBER_EXCEPTION = new NumericExceptionSubItem("020", "Dup_Number_Exception");
    public static final NumericExceptionSubItem CAN_NOT_DELETE_EXCEPTION = new NumericExceptionSubItem("021", "Can_Not_Delete_Exception");
    public static final NumericExceptionSubItem DELETE_ERROR_OCCURED = new NumericExceptionSubItem("022", "Delete_Error_Occured");
    public static final NumericExceptionSubItem NEGTIVE_SENDBACKAMOUNT_ERROR = new NumericExceptionSubItem("023", "Negtive_SendBackAmount_Error");
    public static final NumericExceptionSubItem NEGTIVE_PRICE_ERROR = new NumericExceptionSubItem("024", "Negtive_Price_Error");
    public static final NumericExceptionSubItem NEGTIVE_COUNT_ERROR = new NumericExceptionSubItem("025", "Negtive_Count_Error");
    public static final NumericExceptionSubItem NEGTIVE_ENTRY_AMOUNT_ERROR = new NumericExceptionSubItem("026", "Negtive_Entry_Amount_Error");
    public static final NumericExceptionSubItem NULL_TEL_EXCEPTION = new NumericExceptionSubItem("027", "NULL_TEL_Exception");
    public static final NumericExceptionSubItem NULL_PAYMODE_EXCEPTION = new NumericExceptionSubItem("028", "NULL_PAYMODE_EXCEPTION");
    public static final NumericExceptionSubItem NUMBER_NOT_DUP = new NumericExceptionSubItem("029", "NUMBER_NOT_DUP");
    public static final NumericExceptionSubItem REASON_TOO_LONG = new NumericExceptionSubItem("030", "REASON_TOO_LONG");
    public static final NumericExceptionSubItem COMMON_TO0_LONG = new NumericExceptionSubItem("031", "COMMON_TO0_LONG");
    public static final NumericExceptionSubItem CAUSE_TOO_LONG = new NumericExceptionSubItem("032", "CAUSE_TOO_LONG");
    public static final NumericExceptionSubItem DESCRIPTION_TOO_LONG = new NumericExceptionSubItem("033", "DESCRIPTION_TOO_LONG");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ExpenseReqException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ExpenseReqException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ExpenseReqException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ExpenseReqException(NumericExceptionSubItem info)
    {
        this(info, null, null);
    }

    /**
     * getMainCode function
     */
    public String getMainCode()
    {
        return MAINCODE;
    }
}