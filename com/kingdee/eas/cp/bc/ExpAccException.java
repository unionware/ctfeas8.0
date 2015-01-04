/**
 * output package name
 */
package com.kingdee.eas.cp.bc;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class ExpAccException extends EASBizException
{
    private static final String MAINCODE = "10";

    public static final NumericExceptionSubItem NAME_NOT_NULL = new NumericExceptionSubItem("000", "NAME_NOT_NULL");
    public static final NumericExceptionSubItem NUMBER_NOT_REAPT = new NumericExceptionSubItem("005", "NUMBER_NOT_REAPT");
    public static final NumericExceptionSubItem NUMBER_NOT_NULL = new NumericExceptionSubItem("010", "NUMBER_NOT_NULL");
    public static final NumericExceptionSubItem ORGUNIT_NOT_NULL = new NumericExceptionSubItem("020", "ORGUNIT_NOT_NULL");
    public static final NumericExceptionSubItem REQDEPARTMENT_NOT_NULL = new NumericExceptionSubItem("030", "REQDEPARTMENT_NOT_NULL");
    public static final NumericExceptionSubItem APPLIER_NOT_NULL = new NumericExceptionSubItem("040", "APPLIER_NOT_NULL");
    public static final NumericExceptionSubItem EXPENSEDATE_NOT_NULL = new NumericExceptionSubItem("050", "EXPENSEDATE_NOT_NULL");
    public static final NumericExceptionSubItem BILLSTATE_NOT_NULL = new NumericExceptionSubItem("060", "BILLSTATE_NOT_NULL");
    public static final NumericExceptionSubItem TEL_NOT_NULL = new NumericExceptionSubItem("070", "TEL_NOT_NULL");
    public static final NumericExceptionSubItem MEASUREUNIT_NOT_NULL = new NumericExceptionSubItem("080", "MEASUREUNIT_NOT_NULL");
    public static final NumericExceptionSubItem CUR_NOT_NULL = new NumericExceptionSubItem("090", "CUR_NOT_NULL");
    public static final NumericExceptionSubItem PAYMODE_NOT_NULL = new NumericExceptionSubItem("100", "PAYMODE_NOT_NULL");
    public static final NumericExceptionSubItem CAUSE_NOT_NULL = new NumericExceptionSubItem("110", "CAUSE_NOT_NULL");
    public static final NumericExceptionSubItem ENTRY_NOT_NULL = new NumericExceptionSubItem("120", "ENTRY_NOT_NULL");
    public static final NumericExceptionSubItem PRIOR_NOT_NULL = new NumericExceptionSubItem("310", "PRIOR_NOT_NULL");
    public static final NumericExceptionSubItem BEGINDATE_NOT_NULL = new NumericExceptionSubItem("320", "BEGINDATE_NOT_NULL");
    public static final NumericExceptionSubItem ENDDATE_NOT_NULL = new NumericExceptionSubItem("410", "ENDDATE_NOT_NULL");
    public static final NumericExceptionSubItem BEGIN_NOT_NULL = new NumericExceptionSubItem("500", "BEGIN_NOT_NULL");
    public static final NumericExceptionSubItem END_NOT_NULL = new NumericExceptionSubItem("510", "END_NOT_NULL");
    public static final NumericExceptionSubItem VEHICLE_NOT_NULL = new NumericExceptionSubItem("520", "VEHICLE_NOT_NULL");
    public static final NumericExceptionSubItem BEGIN_LT_END = new NumericExceptionSubItem("530", "BEGIN_LT_END");
    public static final NumericExceptionSubItem DATA_IS_CHECK = new NumericExceptionSubItem("610", "DATA_IS_CHECK");
    public static final NumericExceptionSubItem DATA_IS_UNCHECK = new NumericExceptionSubItem("620", "DATA_IS_UNCHECK");
    public static final NumericExceptionSubItem DATA_IS_CLOSE = new NumericExceptionSubItem("630", "DATA_IS_CLOSE");
    public static final NumericExceptionSubItem DATA_IS_UNCLOSE = new NumericExceptionSubItem("640", "DATA_IS_UNCLOSE");
    public static final NumericExceptionSubItem UPDATE_IS_FAIL = new NumericExceptionSubItem("650", "UPDATE_IS_FAIL");
    public static final NumericExceptionSubItem MAT_NOT_NULL = new NumericExceptionSubItem("700", "MAT_NOT_NULL");
    public static final NumericExceptionSubItem ACTIVE_NOT_NULL = new NumericExceptionSubItem("710", "ACTIVE_NOT_NULL");
    public static final NumericExceptionSubItem CONTENT_NOT_NULL = new NumericExceptionSubItem("720", "CONTENT_NOT_NULL");
    public static final NumericExceptionSubItem ACTIVEDATA_NOT_NULL = new NumericExceptionSubItem("730", "ACTIVEDATA_NOT_NULL");
    public static final NumericExceptionSubItem BIZNAME_NOT_NULL = new NumericExceptionSubItem("740", "BIZNAME_NOT_NULL");
    public static final NumericExceptionSubItem PARTICIPANT_NOT_NULL = new NumericExceptionSubItem("745", "PARTICIPANT_NOT_NULL");
    public static final NumericExceptionSubItem HAPPENDATA_NOT_NULL = new NumericExceptionSubItem("750", "HAPPENDATA_NOT_NULL");
    public static final NumericExceptionSubItem PURDATA_NOT_NULL = new NumericExceptionSubItem("001", "PURDATA_NOT_NULL");
    public static final NumericExceptionSubItem CAUSE_TOO_LONG = new NumericExceptionSubItem("760", "CAUSE_TOO_LONG");
    public static final NumericExceptionSubItem TEL_TOO_LONG = new NumericExceptionSubItem("770", "TEL_TOO_LONG");
    public static final NumericExceptionSubItem NAME_TOO_LONG = new NumericExceptionSubItem("780", "NAME_TOO_LONG");
    public static final NumericExceptionSubItem BEGIN_TOO_LONG = new NumericExceptionSubItem("790", "BEGIN_TOO_LONG");
    public static final NumericExceptionSubItem END_TOO_LONG = new NumericExceptionSubItem("791", "END_TOO_LONG");
    public static final NumericExceptionSubItem MAT_TOO_LONG = new NumericExceptionSubItem("792", "MAT_TOO_LONG");
    public static final NumericExceptionSubItem PURPOSE_TOO_LONG = new NumericExceptionSubItem("793", "PURPOSE_TOO_LONG");
    public static final NumericExceptionSubItem ACTIVE_TOO_LONG = new NumericExceptionSubItem("794", "ACTIVE_TOO_LONG");
    public static final NumericExceptionSubItem COMMENT_TOO_LONG = new NumericExceptionSubItem("795", "COMMENT_TOO_LONG");
    public static final NumericExceptionSubItem EXP_TOO_LONG = new NumericExceptionSubItem("796", "EXP_TOO_LONG");
    public static final NumericExceptionSubItem PERSON_TOO_LONG = new NumericExceptionSubItem("797", "PERSON_TOO_LONG");
    public static final NumericExceptionSubItem PERSON_NOT_NULL = new NumericExceptionSubItem("798", "PERSON_NOT_NULL");
    public static final NumericExceptionSubItem MEMO_TOO_LONG = new NumericExceptionSubItem("799", "MEMO_TOO_LONG");
    public static final NumericExceptionSubItem CANNOT_DELETE = new NumericExceptionSubItem("800", "CANNOT_DELETE");
    public static final NumericExceptionSubItem DEL_ONLY_STATE = new NumericExceptionSubItem("801", "DEL_ONLY_STATE");
    public static final NumericExceptionSubItem REQDATE_FROM_GT_TO = new NumericExceptionSubItem("802", "REQDATE_FROM_GT_TO");
    public static final NumericExceptionSubItem AMOUNT_GT_ZERO = new NumericExceptionSubItem("803", "AMOUNT_GT_ZERO");
    public static final NumericExceptionSubItem ACCOUNT_VIEW_NOT_NULL = new NumericExceptionSubItem("804", "ACCOUNT_VIEW_NOT_NULL");
    public static final NumericExceptionSubItem APPROVED_GT_AMOUNT = new NumericExceptionSubItem("805", "APPROVED_GT_AMOUNT");
    public static final NumericExceptionSubItem APPROVED_GT_BUDGET = new NumericExceptionSubItem("806", "APPROVED_GT_BUDGET");
    public static final NumericExceptionSubItem CLOSE_NOT_DO = new NumericExceptionSubItem("807", "CLOSE_NOT_DO");
    public static final NumericExceptionSubItem EXPENSE_TYPE_NOT_NULL = new NumericExceptionSubItem("742", "EXPENSE_TYPE_NOT_NULL");
    public static final NumericExceptionSubItem PRODUCT_NOT_NULL = new NumericExceptionSubItem("743", "PRODUCT_NOT_NULL");
    public static final NumericExceptionSubItem FIRSTDOPAY = new NumericExceptionSubItem("744", "FIRSTDOPAY");
    public static final NumericExceptionSubItem SELECTEDUNLEGAL = new NumericExceptionSubItem("900", "SELECTEDUNLEGAL");
    public static final NumericExceptionSubItem DATATOEXCEL_ERROR = new NumericExceptionSubItem("002", "DATATOEXCEL_ERROR");
    public static final NumericExceptionSubItem COMMENT_NOT_NULL_ENTRY = new NumericExceptionSubItem("910", "COMMENT_NOT_NULL_ENTRY");
    public static final NumericExceptionSubItem PURPOSE_NOT_NULL_ENTRY = new NumericExceptionSubItem("912", "PURPOSE_NOT_NULL_ENTRY");
    public static final NumericExceptionSubItem APPLIER_NOT_NULL_WK = new NumericExceptionSubItem("913", "APPLIER_NOT_NULL_WK");
    public static final NumericExceptionSubItem APPLY_DATE_NOT_NULL = new NumericExceptionSubItem("003", "APPLY_DATE_NOT_NULL");
    public static final NumericExceptionSubItem AMOUNT_MUST_TOOBIG = new NumericExceptionSubItem("004", "AMOUNT_MUST_TOOBIG");
    public static final NumericExceptionSubItem NOTWFCANCELBECAUSENOTSUBMIT = new NumericExceptionSubItem("006", "NOTWFCANCELBECAUSENOTSUBMIT");
    public static final NumericExceptionSubItem NOTWFCANCEL_BECAUSEI_ISNOT_SUBMIT_STATE = new NumericExceptionSubItem("007", "NOTWFCANCEL_BECAUSEI_ISNOT_SUBMIT_STATE");
    public static final NumericExceptionSubItem VEHICLE_NUMBER_NOT_NULL = new NumericExceptionSubItem("008", "VEHICLE_NUMBER_NOT_NULL");
    public static final NumericExceptionSubItem VEHICLE_NUMBER_TOO_LONG = new NumericExceptionSubItem("009", "VEHICLE_NUMBER_TOO_LONG");
    public static final NumericExceptionSubItem VEHICLE_NAME_NOT_NULL = new NumericExceptionSubItem("011", "VEHICLE_NAME_NOT_NULL");
    public static final NumericExceptionSubItem VEHICLE_NAME_TOO_LONG = new NumericExceptionSubItem("012", "VEHICLE_NAME_TOO_LONG");
    public static final NumericExceptionSubItem VEHICLE_ITEMAPP_NOT_NULL = new NumericExceptionSubItem("013", "VEHICLE_ITEMAPP_NOT_NULL");
    public static final NumericExceptionSubItem CU_EXIST_SAME_VOUTEMPLATE = new NumericExceptionSubItem("014", "CU_EXIST_SAME_VOUTEMPLATE");
    public static final NumericExceptionSubItem LINK_K3_ERROR = new NumericExceptionSubItem("015", "LINK_K3_ERROR");
    public static final NumericExceptionSubItem AMOUNTAPPROVED_NOT_NULL = new NumericExceptionSubItem("016", "AMOUNTAPPROVED_NOT_NULL");
    public static final NumericExceptionSubItem BUDGER_PROPERTIES_FILE_ERROR = new NumericExceptionSubItem("017", "BUDGER_PROPERTIES_FILE_ERROR");
    public static final NumericExceptionSubItem ENTRY_NUMBER_LARGER_ZERO = new NumericExceptionSubItem("018", "ENTRY_NUMBER_LARGER_ZERO");
    public static final NumericExceptionSubItem ENTRY_NUMBER_LOWER_MAX = new NumericExceptionSubItem("019", "ENTRY_NUMBER_LOWER_MAX");
    public static final NumericExceptionSubItem APPROVEDACOUNT_NOTNULL = new NumericExceptionSubItem("021", "APPROVEDACOUNT_NOTNULL");
    public static final NumericExceptionSubItem ISBIZUNIT = new NumericExceptionSubItem("022", "ISBIZUNIT");
    public static final NumericExceptionSubItem COSTCENTER_NOT_BIZUNIT = new NumericExceptionSubItem("023", "COSTCENTER_NOT_BIZUNIT");
    public static final NumericExceptionSubItem DATEBIGTHENOLD = new NumericExceptionSubItem("024", "DATEBIGTHENOLD");
    public static final NumericExceptionSubItem NOTPASSBGAUDIT = new NumericExceptionSubItem("025", "NOTPASSBGAUDIT");
    public static final NumericExceptionSubItem SOURCEBILLID_IS_NULL = new NumericExceptionSubItem("026", "SOURCEBILLID_IS_NULL");
    public static final NumericExceptionSubItem ENTRY_COSTCENTER_NOT_NULL = new NumericExceptionSubItem("027", "ENTRY_COSTCENTER_NOT_NULL");
    public static final NumericExceptionSubItem ENTRY_COMPANY_NOT_NULL = new NumericExceptionSubItem("028", "ENTRY_COMPANY_NOT_NULL");
    public static final NumericExceptionSubItem A_COMPANY_NOTNULL = new NumericExceptionSubItem("029", "A_COMPANY_NOTNULL");
    public static final NumericExceptionSubItem A_COMPANY_NOTBIZUNIT = new NumericExceptionSubItem("031", "A_COMPANY_NOTBIZUNIT");
    public static final NumericExceptionSubItem AMOUNTNOTLESSTHENZERO = new NumericExceptionSubItem("032", "AMOUNTNOTLESSTHENZERO");
    public static final NumericExceptionSubItem FI_COMPANY_NOTBIZUNIT = new NumericExceptionSubItem("033", "FI_COMPANY_NOTBIZUNIT");
    public static final NumericExceptionSubItem NUMBER_NOT_DUP = new NumericExceptionSubItem("034", "NUMBER_NOT_DUP");
    public static final NumericExceptionSubItem EXPENSETYPE_IS_DUPLICATE = new NumericExceptionSubItem("035", "EXPENSETYPE_IS_DUPLICATE");
    public static final NumericExceptionSubItem DESCRIPTIOIN_TOO_LONG = new NumericExceptionSubItem("036", "DESCRIPTIOIN_TOO_LONG");
    public static final NumericExceptionSubItem OUTPUT_FILE_ERROR = new NumericExceptionSubItem("111", "OUTPUT_FILE_ERROR");
    public static final NumericExceptionSubItem CAN_NOT_WRITE = new NumericExceptionSubItem("112", "CAN_NOT_WRITE");
    public static final NumericExceptionSubItem REPEAT_SHEET_NAME = new NumericExceptionSubItem("117", "REPEAT_SHEET_NAME");
    public static final NumericExceptionSubItem ERROR_OPEN_EXCEL = new NumericExceptionSubItem("118", "ERROR_OPEN_EXCEL");
    public static final NumericExceptionSubItem FILE_NAME_ERROR = new NumericExceptionSubItem("119", "FILE_NAME_ERROR");
    public static final NumericExceptionSubItem PARTICIPANT_TOO_LONG = new NumericExceptionSubItem("037", "PARTICIPANT_TOO_LONG");
    public static final NumericExceptionSubItem PURPOSEF_TOO_LONG = new NumericExceptionSubItem("038", "PURPOSEF_TOO_LONG");
    public static final NumericExceptionSubItem PARTNER_TOO_LONG = new NumericExceptionSubItem("039", "PARTNER_TOO_LONG");
    public static final NumericExceptionSubItem FROM_TOO_LONG = new NumericExceptionSubItem("041", "FROM_TOO_LONG");
    public static final NumericExceptionSubItem TO_TOO_LONG = new NumericExceptionSubItem("042", "TO_TOO_LONG");
    public static final NumericExceptionSubItem PURPOSES_TOO_LONG = new NumericExceptionSubItem("043", "PURPOSES_TOO_LONG");
    public static final NumericExceptionSubItem TWO_CANNOT_CREATE = new NumericExceptionSubItem("044", "TWO_CANNOT_CREATE");
    public static final NumericExceptionSubItem EXCHANGERATENOTNULL = new NumericExceptionSubItem("045", "ExchangeRateNotNull");
    public static final NumericExceptionSubItem CHECKAMOUNTNOTNULL = new NumericExceptionSubItem("046", "checkAmountNotNull");
    public static final NumericExceptionSubItem CHECKAMOUNTEQUALAMOUNTBALANCE = new NumericExceptionSubItem("047", "checkAmountEqualAmountBalance");
    public static final NumericExceptionSubItem CHECKAMOUNTEQUALAMOUNT = new NumericExceptionSubItem("048", "checkAmountEqualAmount");
    public static final NumericExceptionSubItem BOTPREQCHECKCANNOTNULL = new NumericExceptionSubItem("049", "botpReqCheckCanNotNull");
    public static final NumericExceptionSubItem MUSTSELECTREQCHECK = new NumericExceptionSubItem("051", "mustSelectReqCheck");
    public static final NumericExceptionSubItem CHECKAMOUNTCANNOTBIGGERTHANAMOUNTBALANCE = new NumericExceptionSubItem("052", "CHECKAMOUNTCANNOTBIGGERTHANAMOUNTBALANCE");
    public static final NumericExceptionSubItem AMOUNTCANNOTBIGGERTHANSOURCE = new NumericExceptionSubItem("053", "amountCanNotBiggerThanSource");
    public static final NumericExceptionSubItem NEWLOANCHECKAMOUNTBIGGERTHANAMOUNT = new NumericExceptionSubItem("054", "NewLoanCheckAmountBiggerThanAmount");
    public static final NumericExceptionSubItem RELAMOUNTBALANCENOTENOUGHT = new NumericExceptionSubItem("055", "RelamountBalanceNotEnought");
    public static final NumericExceptionSubItem EXPENSETYPEBILLTYPE = new NumericExceptionSubItem("056", "expensetypebilltype");
    public static final NumericExceptionSubItem CURRENCYCANNOTMORETHANSOURCE = new NumericExceptionSubItem("057", "currencyCanNotMoreThanSource");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ExpAccException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ExpAccException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ExpAccException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ExpAccException(NumericExceptionSubItem info)
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