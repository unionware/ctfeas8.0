/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.CompanyVoucherNumFactory;
import com.kingdee.eas.cp.bc.ICompanyVoucherNum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CompanyVoucherNumListUI extends AbstractCompanyVoucherNumListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CompanyVoucherNumListUI.class);
    
    CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
    
    FilterInfo filter = null;
    
    /**
     * output class constructor
     */
    public CompanyVoucherNumListUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
		if(!(company!=null && company.isIsBizUnit() && (!company.isIsSealUp()))){
			MsgBox.showWarning("非财务组织无法查看！");	
			abort();
		}
    	
    	super.onLoad();
    	this.tblMain.getGroupManager().setGroup(true);
    	
    	this.tblMain.getColumn("companyNumber").setGroup(true);
    	this.tblMain.getColumn("companyName").setGroup(true);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return CompanyVoucherNumFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return CompanyVoucherNumEditUI.class.getName();
	}
	
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyNumber.id",company.getId().toString()));
		if(!((ICompanyVoucherNum)getBizInterface()).exists(filter)){
			super.actionAddNew_actionPerformed(e);
		}else{
			MsgBox.showInfo(this,"该财务组织已经存在对应凭证字，无法再次新增！");
			abort();
		}
	}
	
	@Override
	protected FilterInfo getDefaultFilterForQuery() {
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyNumber.id",company.getId().toString(),CompareType.EQUALS));
		return filter;
	}
	
}