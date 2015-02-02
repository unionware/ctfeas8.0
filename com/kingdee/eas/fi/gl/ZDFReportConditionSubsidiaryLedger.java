package com.kingdee.eas.fi.gl;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.kingdee.eas.fi.gl:
//            ReportConditionBase

public class ZDFReportConditionSubsidiaryLedger extends ReportConditionBase
    implements Serializable, Cloneable
{

    public ZDFReportConditionSubsidiaryLedger()
    {
        isByPeriod = false;
        dateBegin = null;
        dateEnd = null;
        optionOnlyLeaf = false;
        optionAmountZero = false;
        optionBalanceAndAmountZero = false;
        noDisplayZeroTotal = false;
        optionNotUsed = false;
        optionShowBusinessDate = false;
        optionOtherAccount = false;
        optionOtherAccountItem = false;
        optionOnlyAsst = false;
        optionDailyTotal = false;
        assisthgId = null;
        TableData = null;
        acctAsstItems = null;
        optionAsstGroup = false;
        optionNotIncludePLVoucher = false;
        optionShowAccountCusAttribute = false;
        optionShowHisData=false;
        strCostUnitID=null;
        strCompanyUnitID=null;
    }

    public ZDFReportConditionSubsidiaryLedger(ReportConditionBase base)
    {
        super(base);
        isByPeriod = false;
        dateBegin = null;
        dateEnd = null;
        optionOnlyLeaf = false;
        optionAmountZero = false;
        optionBalanceAndAmountZero = false;
        noDisplayZeroTotal = false;
        optionNotUsed = false;
        optionShowBusinessDate = false;
        optionOtherAccount = false;
        optionOtherAccountItem = false;
        optionOnlyAsst = false;
        optionDailyTotal = false;
        assisthgId = null;
        TableData = null;
        acctAsstItems = null;
        optionAsstGroup = false;
        optionNotIncludePLVoucher = false;
        optionShowAccountCusAttribute = false;
        optionShowHisData=false;
        strCostUnitID=null;
        strCompanyUnitID=null;
    }

    public ZDFReportConditionSubsidiaryLedger(Map map)
        throws CloneNotSupportedException
    {
        super(map);
        isByPeriod = false;
        dateBegin = null;
        dateEnd = null;
        optionOnlyLeaf = false;
        optionAmountZero = false;
        optionBalanceAndAmountZero = false;
        noDisplayZeroTotal = false;
        optionNotUsed = false;
        optionShowBusinessDate = false;
        optionOtherAccount = false;
        optionOtherAccountItem = false;
        optionOnlyAsst = false;
        optionDailyTotal = false;
        assisthgId = null;
        TableData = null;
        acctAsstItems = null;
        optionAsstGroup = false;
        optionNotIncludePLVoucher = false;
        optionShowAccountCusAttribute = false;
        optionShowHisData=false;
        strCostUnitID=null;
        strCompanyUnitID=null;
        setByPeriod(getBoolean((Boolean)map.get("QueryByPeriod")));
        setDateBegin((Date)map.get("DateBegin"));
        setDateEnd((Date)map.get("DateEnd"));
        setOptionOnlyLeaf(getBoolean((Boolean)map.get("OptionOnlyLeaf")));
        setOptionAmountZero(getBoolean((Boolean)map.get("OptionAmountZero")));
        setOptionNoDisplayZeroTotal(getBoolean((Boolean)map.get("noDisplayZeroTotal")));
        setOptionBalanceAndAmountZero(getBoolean((Boolean)map.get("OptionBalanceAndAmountZero")));
        setOptionNotUsed(getBoolean((Boolean)map.get("OptionNotUsed")));
        setOptionOtherAccount(getBoolean((Boolean)map.get("OptionOtherAccount")));
        setOptionOtherAccountItem(getBoolean((Boolean)map.get("OptionOtherAccountItem")));
        setOptionShowBusinessDate(getBoolean((Boolean)map.get("OptionShowBusinessDate")));
        setOptionOnlyAsst(getBoolean((Boolean)map.get("OptionOnlyAsst")));
        setOptionDailyTotal(getBoolean((Boolean)map.get("OptionDailyTotal")));
        setAssisthgId((String)map.get("AssisthgId"));
        setTableData((List)map.get("TableData"));
        setOptionAsstGroup(getBoolean((Boolean)map.get("OptionAsstGroup")));
        setOptionNotIncludePLVoucher(getBoolean((Boolean)map.get("optionNotIncludePLVoucher")));
        setOptionShowAccountCusAttribute(getBoolean((Boolean)map.get("optionShowAccountCusAttribute")));
        setOptionShowHisData(getBoolean((Boolean)map.get("optionShowHisData")));
        setStrCostUnitID((String)map.get("CostUnitID"));
        setStrCompanyUnitID((String)map.get("CompanyUnitID"));
    }

    public Map toMap()
    {
        Map map = super.toMap();
        map.put("QueryByPeriod", Boolean.valueOf(isByPeriod));
        map.put("DateBegin", getDateBegin());
        map.put("DateEnd", getDateEnd());
        map.put("OptionOnlyLeaf", Boolean.valueOf(getOptionOnlyLeaf()));
        map.put("OptionAmountZero", Boolean.valueOf(getOptionAmountZero()));
        map.put("noDisplayZeroTotal", Boolean.valueOf(getOptionNoDisplayZeroTotal()));
        map.put("OptionBalanceAndAmountZero", Boolean.valueOf(getOptionBalanceAndAmountZero()));
        map.put("OptionNotUsed", Boolean.valueOf(getOptionNotUsed()));
        map.put("OptionOtherAccount", Boolean.valueOf(getOptionOtherAccount()));
        map.put("OptionOtherAccountItem", Boolean.valueOf(getOptionOtherAccountItem()));
        map.put("OptionShowBusinessDate", Boolean.valueOf(getOptionShowBusinessDate()));
        map.put("OptionOnlyAsst", Boolean.valueOf(getOptionOnlyAsst()));
        map.put("OptionDailyTotal", Boolean.valueOf(getOptionDailyTotal()));
        map.put("AssisthgId", getAssisthgId());
        map.put("TableData", getTableData());
        map.put("OptionAsstGroup", Boolean.valueOf(getOptionAsstGroup()));
        map.put("optionNotIncludePLVoucher", Boolean.valueOf(getOptionNotIncludePLVoucher()));
        map.put("optionShowAccountCusAttribute", Boolean.valueOf(getOptionShowAccountCusAttribute()));
        map.put("optionShowHisData",Boolean.valueOf(isOptionShowHisData()));
        map.put("CostUnitID", getStrCostUnitID());
        map.put("CompanyUnitID", getStrCompanyUnitID());
        
        return map;
    }

    public boolean isByPeriod()
    {
        return isByPeriod;
    }

    public void setByPeriod(boolean b)
    {
        isByPeriod = b;
    }

    public Date getDateBegin()
    {
        return dateBegin;
    }

    public void setDateBegin(Date date)
    {
        dateBegin = date;
    }

    public Date getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Date date)
    {
        dateEnd = date;
    }

    public boolean getOptionOnlyLeaf()
    {
        return optionOnlyLeaf;
    }

    public void setOptionOnlyLeaf(boolean option)
    {
        optionOnlyLeaf = option;
    }

    public boolean getOptionAmountZero()
    {
        return optionAmountZero;
    }

    public void setOptionAmountZero(boolean b)
    {
        optionAmountZero = b;
    }

    public boolean getOptionBalanceAndAmountZero()
    {
        return optionBalanceAndAmountZero;
    }

    public void setOptionBalanceAndAmountZero(boolean b)
    {
        optionBalanceAndAmountZero = b;
    }

    public boolean getOptionNoDisplayZeroTotal()
    {
        return noDisplayZeroTotal;
    }

    public void setOptionNoDisplayZeroTotal(boolean b)
    {
        noDisplayZeroTotal = b;
    }

    public boolean getOptionNotUsed()
    {
        return optionNotUsed;
    }

    public void setOptionNotUsed(boolean b)
    {
        optionNotUsed = b;
    }

    public boolean getOptionOtherAccount()
    {
        return optionOtherAccount;
    }

    public void setOptionOtherAccount(boolean b)
    {
        optionOtherAccount = b;
    }

    public boolean getOptionOtherAccountItem()
    {
        return optionOtherAccountItem;
    }

    public void setOptionOtherAccountItem(boolean b)
    {
        optionOtherAccountItem = b;
    }

    public boolean getOptionShowBusinessDate()
    {
        return optionShowBusinessDate;
    }

    public void setOptionShowBusinessDate(boolean b)
    {
        optionShowBusinessDate = b;
    }

    public void setOptionOnlyAsst(boolean option)
    {
        optionOnlyAsst = option;
    }

    public void setOptionDailyTotal(boolean option)
    {
        optionDailyTotal = option;
    }

    public boolean getOptionOnlyAsst()
    {
        return optionOnlyAsst;
    }

    public boolean getOptionDailyTotal()
    {
        return optionDailyTotal;
    }

    public String getAssisthgId()
    {
        return assisthgId;
    }

    public void setAssisthgId(String assisthgId)
    {
        this.assisthgId = assisthgId;
    }

    public void setTableData(List TableDate)
    {
        TableData = TableDate;
    }

    public List getTableData()
    {
        return TableData;
    }

    public String getAcctAsstItems()
    {
        return acctAsstItems;
    }

    public void setAcctAsstItems(String acctAsstItems)
    {
        this.acctAsstItems = acctAsstItems;
    }

    public boolean getOptionAsstGroup()
    {
        return optionAsstGroup;
    }

    public void setOptionAsstGroup(boolean isAsstGroup)
    {
        optionAsstGroup = isAsstGroup;
    }

    public boolean getOptionNotIncludePLVoucher()
    {
        return optionNotIncludePLVoucher;
    }

    public void setOptionNotIncludePLVoucher(boolean optionNotIncludePLVoucher)
    {
        this.optionNotIncludePLVoucher = optionNotIncludePLVoucher;
    }

    public boolean getOptionShowAccountCusAttribute()
    {
        return optionShowAccountCusAttribute;
    }

    public void setOptionShowAccountCusAttribute(boolean optionShowAccountCusAttrbute)
    {
        optionShowAccountCusAttribute = optionShowAccountCusAttrbute;
    }
    
	public boolean isOptionShowHisData() {
		return optionShowHisData;
	}

	public void setOptionShowHisData(boolean optionShowHisData) {
		this.optionShowHisData = optionShowHisData;
	}
	public String getStrCostUnitID() {
		return strCostUnitID;
	}

	public void setStrCostUnitID(String strCostUnitID) {
		this.strCostUnitID = strCostUnitID;
	}
	
	public String getStrCompanyUnitID() {
		return strCompanyUnitID;
	}

	public void setStrCompanyUnitID(String strCompanyUnitID) {
		this.strCompanyUnitID = strCompanyUnitID;
	}
	
    private static final long serialVersionUID = 6596071702243430165L;
    private boolean isByPeriod;
    private Date dateBegin;
    private Date dateEnd;
    private boolean optionOnlyLeaf;
    private boolean optionAmountZero;
    private boolean optionBalanceAndAmountZero;
    private boolean noDisplayZeroTotal;
    private boolean optionNotUsed;
    private boolean optionShowBusinessDate;
    private boolean optionOtherAccount;
    private boolean optionOtherAccountItem;
    private boolean optionOnlyAsst;
    private boolean optionDailyTotal;
    private String assisthgId;
    private List TableData;
    private String acctAsstItems;
    private boolean optionAsstGroup;
    private boolean optionNotIncludePLVoucher;
    private boolean optionShowAccountCusAttribute;
    private boolean optionShowHisData;
    private String strCostUnitID;
    private String strCompanyUnitID; //add by tgw 财务组织

}
