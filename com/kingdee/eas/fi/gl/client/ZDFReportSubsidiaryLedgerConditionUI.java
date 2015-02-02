package com.kingdee.eas.fi.gl.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.job.returns.Abort;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.OrgRangeCollection;
import com.kingdee.eas.base.permission.OrgRangeFactory;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.account.AccountRefContrastFactory;
import com.kingdee.eas.basedata.master.account.AccountRefContrastInfo;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountUtils;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountClientUtils;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.ReportException;
import com.kingdee.eas.fi.gl.ZDFReportConditionSubsidiaryLedger;
import com.kingdee.eas.fi.gl.client.asstf7.CostOrgProcessor;
import com.kingdee.eas.fi.gl.common.GLResUtil;
import com.kingdee.eas.fi.gl.common.RptClientUtil;
import com.kingdee.eas.framework.report.RptFilterCollection;
import com.kingdee.eas.framework.report.RptFilterInfo;
import com.kingdee.eas.framework.report.RptFilterUtils;
import com.kingdee.eas.framework.report.util.PeriodEntity;
import com.kingdee.eas.framework.report.util.RptParamsUtil;
import com.kingdee.eas.framework.report.util.SpinnerUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ZDFReportSubsidiaryLedgerConditionUI extends
		AbstractZDFReportSubsidiaryLedgerConditionUI {
	private static final long serialVersionUID = 7100804815047478364L;
	CompanyOrgUnitInfo company = null;

	boolean loading = false;

	private PeriodEntity pe = null;

	boolean isCleared = false;
	boolean isQueryByOther = false;

	private Map initDataUsingForClear = null;
	
	private Object accountViews = null;//add by tgw

	public ZDFReportSubsidiaryLedgerConditionUI() throws Exception {
		this.chkOpOnlyAsst.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ((e.getStateChange() == 1)
						|| (ZDFReportSubsidiaryLedgerConditionUI.this.chkOpAccountCusAttribute
								.isSelected())) {
					ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOtherAccount
							.setSelected(false);
					ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOtherAccount
							.setEnabled(false);
					ZDFReportSubsidiaryLedgerConditionUI.this.showQueryItems(true);
				} else {
					ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOtherAccount
							.setEnabled(true);
					ZDFReportSubsidiaryLedgerConditionUI.this
							.showQueryItems(false);
				}
			}
		});
		this.chkOpAccountCusAttribute.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ((e.getStateChange() == 1)
						|| (ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOnlyAsst
								.isSelected())) {
					ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOtherAccount
							.setSelected(false);
					ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOtherAccount
							.setEnabled(false);
					ZDFReportSubsidiaryLedgerConditionUI.this.showQueryItems(true);
				} else {
					ZDFReportSubsidiaryLedgerConditionUI.this.chkOpOtherAccount
							.setEnabled(true);
					ZDFReportSubsidiaryLedgerConditionUI.this
							.showQueryItems(false);
				}
				if (e.getStateChange() == 1)
					ZDFReportSubsidiaryLedgerConditionUI.this
							.showQueryItemsForZDY(true);
				else
					ZDFReportSubsidiaryLedgerConditionUI.this
							.showQueryItemsForZDY(false);
			}
		});
		this.cmbCurrency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZDFReportSubsidiaryLedgerConditionUI.this.beforeActionPerformed(e);
				try {
					ZDFReportSubsidiaryLedgerConditionUI.this
							.cmbCurrency_actionPerformed(e);
				} catch (Exception exc) {
					ZDFReportSubsidiaryLedgerConditionUI.this.handUIException(exc);
				} finally {
					ZDFReportSubsidiaryLedgerConditionUI.this
							.afterActionPerformed(e);
				}
			}
		});
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		//add by tgw 成本中心值改变，财务组织相应改变
		prmtCostOrg.addDataChangeListener(new DataChangeListener(){
			@Override
			public void dataChanged(DataChangeEvent arg0) {
				prmtCostOrgChanged(arg0);
		}});
	}
	
	protected void prmtCostOrgChanged(DataChangeEvent arg0) {
		if((arg0.getNewValue()==null && arg0.getOldValue()==null) || (arg0.getNewValue()!=null && arg0.getNewValue().equals(arg0.getOldValue()))){
	   		 return ;
	   	}
		CostCenterOrgUnitInfo centerOrgUnitInfo = (CostCenterOrgUnitInfo) arg0.getNewValue();
		OrgUnitCollection orgUnitCollection = null;
		CompanyOrgUnitInfo companyInfo = null;
		
		try {
			IOrgUnitRelation iOrgUnitRelation = OrgUnitRelationFactory.getRemoteInstance();
			orgUnitCollection = iOrgUnitRelation.getToUnit(centerOrgUnitInfo.getId().toString(), 5, 1);
			 if(orgUnitCollection != null && orgUnitCollection.size() > 0){
				 companyInfo = (CompanyOrgUnitInfo)orgUnitCollection.get(0);
			 }
			 
			 if(companyInfo!=null){
				 prmtCompany.setValue(companyInfo);
				 
				 companyInfo = getCompanyByID(companyInfo.getId().toString());//查询财务组织
				 
				 AccountTableInfo accountTable = companyInfo.getAccountTable();
				 AccountRefContrastInfo ar = null;
				 if(accountTable!=null){
					 ar = AccountRefContrastFactory
						.getRemoteInstance().getLastAcctInfo(
								companyInfo.getId().toString(),
								companyInfo.getAccountTable().getId().toString(),
								this.spnPeriodYearEnd.getIntegerVlaue().intValue(),
								this.spnPeriodNumberEnd.getIntegerVlaue()
										.intValue());
				 }
				 
				if (ar != null){
					accountTable = ar.getEnabledAcctTable();
				}
					
				FilterInfo filter = new FilterInfo();
				FilterItemCollection fic = filter.getFilterItems();
				fic.add(new FilterItemInfo("companyID.id", companyInfo.getId()
						.toString()));
				if(accountTable==null){
					fic.add(new FilterItemInfo("accountTableID.id",null));
				}else{
					fic.add(new FilterItemInfo("accountTableID.id", accountTable.getId()
							.toString()));
				}
				
				filter.setMaskString("#0 and #1");
				AccountPromptBox apb = new AccountPromptBox(this, accountTable, filter);
				
				this.prbAccountBegin.setSelector(apb);
				
				EntityViewInfo myevi = new EntityViewInfo();
				myevi.setFilter(filter);
				this.prbAccountBegin.setEntityViewInfo(myevi);
			 }else{
				 prmtCompany.setValue(null);
			 }
			 
		}catch (Exception e) {
			handUIException(e);
		}
	
		/*Object[] costObjects = (Object[]) arg0.getNewValue();
		Object[] companyObjects = new Object[costObjects.length];
		CompanyOrgUnitInfo info = null;
		List list = null;
		int index =0; 
	    
		try {
				if(costObjects!=null&& costObjects.length>0){
					list = new ArrayList();
					IOrgUnitRelation iOrgUnitRelation = OrgUnitRelationFactory.getRemoteInstance();
					for(int i=0;i<costObjects.length;i++){
						 CostCenterOrgUnitInfo centerOrgUnitInfo = (CostCenterOrgUnitInfo) costObjects[i];
						 OrgUnitCollection orgUnitCollection;
						
							orgUnitCollection = iOrgUnitRelation.getToUnit(centerOrgUnitInfo.getId().toString(), 5, 1);
							 if(orgUnitCollection != null && orgUnitCollection.size() > 0){
								 info = (CompanyOrgUnitInfo)orgUnitCollection.get(0);
								 if(!list.contains(info.getId().toString())){
									 list.add(info.getId().toString());
									 companyObjects[index] = info;
									 index++;
								 }
							 }
					}
					//去掉数组空值
					List tmp = new ArrayList();
					for (int i = 0; i < companyObjects.length; i++) {
						if(companyObjects[i]!=null){
							tmp.add(companyObjects[i]);
						}
					}
			        companyObjects = tmp.toArray(new Object[0]);
			        
					prmtCompany.setValue(companyObjects);
				}
		    }catch (Exception e) {
			handUIException(e);
		}*/
	}

	private CompanyOrgUnitInfo getCompanyByID(String companyId) throws BOSException {
		CompanyOrgUnitInfo info = null;
		ICompanyOrgUnit corgUnit = CompanyOrgUnitFactory.getRemoteInstance();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",companyId));
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("accountTable.*"));
		
		view.setFilter(filter);
		view.setSelector(sic);
		
		CompanyOrgUnitCollection col = corgUnit.getCompanyOrgUnitCollection(view);
		
		if(col!=null && col.size()>0){
			info = col.get(0);
		}
		return info;
	}

	//add by tgw
	public ZDFReportSubsidiaryLedgerConditionUI(Object object)throws Exception {
		this.accountViews = object;
		lblcAccountCodeTo.setVisible(false);
		this.prbAccountBegin.setValue(accountViews);
	}

	protected void cmbCurrency_actionPerformed(ActionEvent e) throws Exception {
		if (GlUtils.isCompanyUnion(this.company)) {
			return;
		}
		Object objectCurrency = this.cmbCurrency.getSelectedItem();
		if ((objectCurrency instanceof CurrencyInfo)) {
			this.chkOpOtherAccount.setEnabled(!this.chkOpOnlyAsst.isSelected());
		} else if ((objectCurrency != null)
				&& (GLResUtil.getRes("all_currency").equals(objectCurrency
						.toString()))) {
			this.chkOpOtherAccount.setEnabled(!this.chkOpOnlyAsst.isSelected());
			this.chkOpOtherAccount.setSelected(false);
		}
	}

	protected void spnPeriodYearEnd_stateChanged(ChangeEvent e)
			throws Exception {
		int yearEnd = ((Integer) this.spnPeriodYearEnd.getValue()).intValue();
		int numberEnd = ((Integer) this.spnPeriodNumberEnd.getValue())
				.intValue();
		RptClientUtil.periodChanged(this, this.company, yearEnd, numberEnd,
				this.prbAccountBegin, this.prbAccountEnd);
	}

	protected void rbnQueryByPeriod_itemStateChanged(ItemEvent e)
			throws Exception {
		super.rbnQueryByPeriod_itemStateChanged(e);
	}

	protected void spnAccountLevelBegin_stateChanged(ChangeEvent e)
			throws Exception {
		if (this.loading) {
			return;
		}
		super.spnAccountLevelBegin_stateChanged(e);

		Integer numberBegin = (Integer) this.spnAccountLevelBegin.getValue();
		Integer numberEnd = (Integer) this.spnAccountLevelEnd.getValue();

		if ((numberBegin == null) || (numberEnd == null)) {
			throw new ReportException(ReportException.INIT_DATA_ERROR);
		}

		int iNumberBegin = numberBegin.intValue();
		int iNumberEnd = numberEnd.intValue();

		if (iNumberEnd < iNumberBegin)
			this.spnAccountLevelEnd.setValue(new Integer(iNumberBegin));
	}

	protected void spnAccountLevelEnd_stateChanged(ChangeEvent e)
			throws Exception {
		if (this.loading) {
			return;
		}
		super.spnAccountLevelEnd_stateChanged(e);

		Integer numberBegin = (Integer) this.spnAccountLevelBegin.getValue();
		Integer numberEnd = (Integer) this.spnAccountLevelEnd.getValue();

		if ((numberBegin == null) || (numberEnd == null)) {
			throw new ReportException(ReportException.INIT_DATA_ERROR);
		}

		int iNumberBegin = numberBegin.intValue();
		int iNumberEnd = numberEnd.intValue();

		if (iNumberBegin > iNumberEnd)
			this.spnAccountLevelBegin.setValue(new Integer(iNumberEnd));
	}

	protected void chkOpOnlyLeaf_itemStateChanged(ItemEvent e) throws Exception {
		super.chkOpOnlyLeaf_itemStateChanged(e);

		boolean isOnlyLeaf = ((KDCheckBox) e.getSource()).isSelected();
		this.spnAccountLevelBegin.setEnabled(!isOnlyLeaf);
		if (isOnlyLeaf) {
			Object max = this.spnAccountLevelEnd.getValue();
			Object cur = null;
			while (((cur = this.spnAccountLevelEnd.getNextValue()) != null)
					&& (!max.equals(cur))) {
				max = cur;
			}
			this.spnAccountLevelEnd.setValue(max);
		}
		this.spnAccountLevelEnd.setEnabled(!isOnlyLeaf);
	}

	protected void chkOpAmountZero_itemStateChanged(ItemEvent e)
			throws Exception {
		super.chkOpAmountZero_itemStateChanged(e);

		boolean isAmountZero = ((KDCheckBox) e.getSource()).isSelected();
		this.chkOpBalanceAndAmountZero.setEnabled(!isAmountZero);
		this.chkOpNotUsed.setEnabled(!isAmountZero);
	}

	protected void chkOpBalanceAndAmountZero_itemStateChanged(ItemEvent e)
			throws Exception {
		super.chkOpBalanceAndAmountZero_itemStateChanged(e);

		boolean isBalanceAndAmountZero = ((KDCheckBox) e.getSource())
				.isSelected();
		if (!this.chkOpNotUsed.isSelected())
			this.chkOpAmountZero.setEnabled(!isBalanceAndAmountZero);
	}

	protected void chkOpNotUsed_itemStateChanged(ItemEvent e) throws Exception {
		super.chkOpNotUsed_itemStateChanged(e);

		boolean isNotUsed = ((KDCheckBox) e.getSource()).isSelected();
		if (!this.chkOpBalanceAndAmountZero.isSelected())
			this.chkOpAmountZero.setEnabled(!isNotUsed);
	}

	protected void chkOpOtherAccount_itemStateChanged(ItemEvent e)
			throws Exception {
		super.chkOpOtherAccount_itemStateChanged(e);
	}

	public ZDFReportConditionSubsidiaryLedger getCustomCondition() {
		ZDFReportConditionSubsidiaryLedger condition = new ZDFReportConditionSubsidiaryLedger();
//		MsgBox.showConfirm2("test");
		condition.setByPeriod(this.rbnQueryByPeriod.isSelected());

		condition.setPeriodYearStart(((Integer) this.spnPeriodYearBegin
				.getValue()).intValue());
		condition.setPeriodNumberStart(((Integer) this.spnPeriodNumberBegin
				.getValue()).intValue());
		condition.setPeriodYearEnd(((Integer) this.spnPeriodYearEnd.getValue())
				.intValue());
		condition.setPeriodNumberEnd(((Integer) this.spnPeriodNumberEnd
				.getValue()).intValue());

		condition.setDateBegin(this.dpkDateBegin.getSqlDate());
		condition.setDateEnd(this.dpkDateEnd.getSqlDate());

		condition.setAccountCodeStart(null);
		Object object = this.prbAccountBegin.getValue();
		condition.setAccountViewStart(object);
		if (object != null) {
			if ((object instanceof String)) {
				String strNumber = (String) object;

				if (!strNumber.trim().equals(""))
					condition.setAccountCodeStart(strNumber.trim());
			} else {
				Object ar[] = (object instanceof Object[]) ? (Object[])object : (new Object[] {
                        object
                    });
				String accountNumber = "";
				for (int i = 0; i < ar.length; i++) {
					AccountViewInfo account = (AccountViewInfo) ar[i];
					if (account != null) {
						accountNumber = accountNumber + account.getNumber()
								+ ",";
					}
				}

				if (accountNumber != "") {
					accountNumber = accountNumber.substring(0, accountNumber
							.length() - 1);
				}
				condition.setAccountCodeStart(accountNumber);
			}

		}

		condition.setAccountCodeEnd(null);
		object = this.prbAccountEnd.getValue();
		condition.setAccountViewEnd(object);
		if (object != null) {
			if ((object instanceof String)) {
				String strNumber = (String) object;

				if (!strNumber.trim().equals(""))
					condition.setAccountCodeEnd(strNumber.trim() + "z");
			} else {
				Object[] ar = (object instanceof Object[]) ? (Object[]) object: (new Object[]{} );
				String accountNumber = "";
				for (int i = 0; i < ar.length; i++) {
					AccountViewInfo account = (AccountViewInfo) ar[i];
					if (account != null) {
						accountNumber = accountNumber + account.getNumber()
								+ ",";
					}
				}

				if (accountNumber != "") {
					accountNumber = accountNumber.substring(0, accountNumber
							.length() - 1);
				}
				condition.setAccountCodeEnd(accountNumber);
			}
		}

		condition.setAccountLevelStart(((Integer) this.spnAccountLevelBegin
				.getValue()).intValue());
		condition.setAccountLevelEnd(((Integer) this.spnAccountLevelEnd
				.getValue()).intValue());

		CurrencyInfo currency = (this.cmbCurrency.getSelectedItem() instanceof CurrencyInfo) ? (CurrencyInfo) this.cmbCurrency
				.getSelectedItem()
				: null;
		if (currency != null) {
			if (currency.getId() != null) {
				condition.setCurrencyID(currency.getId().toString());
			} else {
				condition.setCurrencyID(currency.getNumber());
			}
			condition.setCurrencyName(currency.getName());
		} else if (GLResUtil.getRes("all_currency").equals(
				(String) this.cmbCurrency.getSelectedItem())) {
			condition.setCurrencyID("AllCurrency");
			condition.setCurrencyName((String) this.cmbCurrency
					.getSelectedItem());
		}

		condition.setOptionPosting(this.chkOpIncludeNotPosting.isSelected());

		condition.setOptionOnlyLeaf(this.chkOpOnlyLeaf.isSelected());

		condition.setOptionAmountZero(this.chkOpAmountZero.isSelected());

		condition.setOptionBalanceAndAmountZero(this.chkOpBalanceAndAmountZero
				.isSelected());

		condition.setOptionNoDisplayZeroTotal(this.jcbNoDisplayZeroTotal
				.isSelected());

		condition.setOptionNotUsed(this.chkOpNotUsed.isSelected());

		condition.setOptionOtherAccount(this.chkOpOtherAccount.isSelected());

		condition.setOptionOtherAccountItem(this.chkOpOtherAccountItem
				.isSelected());

		condition.setOptionShowBusinessDate(this.chkOpShowBusinessDate
				.isSelected());

		condition.setOptionOnlyAsst(this.chkOpOnlyAsst.isSelected());

		condition.setOptionDailyTotal(this.chkOpDailyTotal.isSelected());

		condition
				.setOptionShowAccountCusAttribute(this.chkOpAccountCusAttribute
						.isSelected());
		condition.getExpandInfo().put("company", this.company);
		condition.getExpandInfo().put("periodEntity", this.pe);
		condition.setOptionNotIncludePLVoucher(this.chkNotIncluePLVoucher
				.isSelected());
		//add by yangyang
		condition.setOptionShowHisData(this.chkIncludeHis.isSelected());
		
//		if(SysContext.getSysContext().getCurrentCostUnit().getId()!=null){
//			condition.setStrCostUnitID(SysContext.getSysContext().getCurrentCostUnit().getId().toString());
//		}else{
//			condition.setStrCostUnitID(null);
//		}
		
		//add by tgw
		Object costOrg = prmtCostOrg.getValue();
		Object[] costOrgs = null;
		String strCostOrgsID = "";
		if(costOrg instanceof Object[]){
			costOrgs = (Object[]) costOrg;
			for(int i=0;i<costOrgs.length;i++){
				CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) costOrgs[i];
				strCostOrgsID = strCostOrgsID + ",'" +info.getId().toString()+"'";
			}
			strCostOrgsID=strCostOrgsID.substring(2, strCostOrgsID.length()-1);
		}else{
			CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) costOrg;
			strCostOrgsID = info.getId().toString();
		}
		condition.setStrCostUnitID(strCostOrgsID);
		
		
		Object company = prmtCompany.getValue();
		Object[] companys = null;
		String strCompanyID = "";
		if(costOrg instanceof Object[]){
			companys = (Object[]) company;
			for(int i=0;i<companys.length;i++){
				CompanyOrgUnitInfo info = (CompanyOrgUnitInfo) companys[i];
				strCompanyID = strCompanyID + ",'" +info.getId().toString()+"'";
			}
			strCompanyID=strCompanyID.substring(2, strCompanyID.length()-1);
		}else{
			CompanyOrgUnitInfo info = (CompanyOrgUnitInfo) company;
			strCompanyID = info.getId().toString();
		}
		condition.setStrCompanyUnitID(strCompanyID);
		
		return condition;
	}

	public void setCustomCondition(ZDFReportConditionSubsidiaryLedger condition) {
		if (condition == null) {
			return;
		}

		this.rbnQueryByPeriod.setSelected(condition.isByPeriod());

		setSpinnerValueInRange(this.spnPeriodYearBegin, condition
				.getPeriodYearStart());
		setSpinnerValueInRange(this.spnPeriodNumberBegin, condition
				.getPeriodNumberStart());
		setSpinnerValueInRange(this.spnPeriodYearEnd, condition
				.getPeriodYearEnd());
		setSpinnerValueInRange(this.spnPeriodNumberEnd, condition
				.getPeriodNumberEnd());

		this.prbAccountBegin
				.setValue(condition.getAccountViewStart() == null ? condition
						.getAccountCodeStart() : condition
						.getAccountViewStart());

		String accountEnd = condition.getAccountCodeEnd();
		if ((accountEnd != null) && (accountEnd.endsWith("z"))) {
			accountEnd = accountEnd.substring(0, accountEnd.length() - 1);
		}
//		this.prbAccountEnd
//				.setValue(condition.getAccountViewEnd() == null ? accountEnd
//						: condition.getAccountViewEnd());

		this.spnAccountLevelBegin.setValue(new Integer(condition
				.getAccountLevelStart()));
//		this.spnAccountLevelEnd.setValue(new Integer(condition
//				.getAccountLevelEnd()));

		String currencyId = condition.getCurrencyID();
		if ((currencyId != null) && (currencyId.length() > 0)) {
			for (int i = 0; i < this.cmbCurrency.getItemCount(); i++) {
				Object objectCurrency = this.cmbCurrency.getItemAt(i);
				if ((objectCurrency instanceof CurrencyInfo)) {
					CurrencyInfo currencyInfo = (CurrencyInfo) objectCurrency;
					if ((currencyInfo != null)
							&& ((currencyId.equals(currencyInfo.getId()
									.toString())) || (currencyId
									.equals(currencyInfo.getNumber())))) {
						this.cmbCurrency.setSelectedIndex(i);
						break;
					}
				} else if (GLResUtil.getRes("all_currency").equals(
						(String) objectCurrency)) {
					this.cmbCurrency.setSelectedIndex(i);
					break;
				}
			}

		}

		this.chkOpIncludeNotPosting.setSelected(condition.getOptionPosting());

		this.chkOpOnlyLeaf.setSelected(condition.getOptionOnlyLeaf());

		this.chkOpAmountZero.setSelected(condition.getOptionAmountZero());

		this.chkOpBalanceAndAmountZero.setSelected(condition
				.getOptionBalanceAndAmountZero());

		this.jcbNoDisplayZeroTotal.setSelected(condition
				.getOptionNoDisplayZeroTotal());

		this.chkOpNotUsed.setSelected(condition.getOptionNotUsed());

		this.chkOpOtherAccount.setSelected(condition.getOptionOtherAccount());

		this.chkOpOtherAccountItem.setSelected(condition
				.getOptionOtherAccountItem());

		this.chkOpShowBusinessDate.setSelected(condition
				.getOptionShowBusinessDate());

		this.chkOpOnlyAsst.setSelected(condition.getOptionOnlyAsst());

		this.chkOpDailyTotal.setSelected(condition.getOptionDailyTotal());

		this.chkNotIncluePLVoucher.setSelected(condition
				.getOptionNotIncludePLVoucher());

		this.chkOpAccountCusAttribute.setSelected(condition
				.getOptionShowAccountCusAttribute());
		this.chkIncludeHis.setSelected(condition.isOptionShowHisData());
		
		
		if ((condition.getOptionOnlyAsst())
				|| (condition.getOptionShowAccountCusAttribute()))
			showQueryItems(true);
		else {
			showQueryItems(false);
		}
		if (condition.getOptionShowAccountCusAttribute())
			showQueryItemsForZDY(true);
		else
			showQueryItemsForZDY(false);
	}

	public void setSpinnerValueInRange(KDSpinner spinner, int value) {
		if (spinner == null) {
			return;
		}
		SpinnerNumberModel yearBeginModel = (SpinnerNumberModel) spinner
				.getModel();
		Integer max = (Integer) yearBeginModel.getMaximum();
		Integer min = (Integer) yearBeginModel.getMinimum();
		int newValue = value;
		if ((max != null) && (max.intValue() < newValue)) {
			newValue = max.intValue();
		}
		if ((min != null) && (min.intValue() > newValue)) {
			newValue = min.intValue();
		}
		spinner.setValue(new Integer(newValue));
	}

	public String getAccountCodeFromPromptBox(KDBizPromptBox prbAccount) {
		String accountCode = null;

		if (prbAccount != null) {
			Object object = prbAccount.getValue();
			if (object != null) {
				if ((object instanceof String)) {
					String strNumber = (String) object;

					if ((strNumber != null) && (strNumber.trim().length() > 0)) {
						accountCode = strNumber.trim();
					}

				}

				if ((object instanceof AccountViewInfo)) {
					AccountViewInfo account = (AccountViewInfo) object;
					accountCode = account.getNumber();
				}
			}
		}
		return accountCode;
	}

	public static Collection getInitDataItem() {
		ArrayList list = new ArrayList();
		list.add("AllCurrency");
		list.add("CurrentPeriod");
		list.add("MaxAccountLevel");
//		list.add("PeriodRange");
		list.add("DefaultCurrency");
		list.add("CurrentCompany");
//		list.add("PeriodRangeAll");

		return list;
	}

	public void setInitData(Map initData) throws ReportException {
		this.loading = true;
		this.initDataUsingForClear = initData;
		this.company = ((CompanyOrgUnitInfo) initData.get("CurrentCompany"));
		this.prmtCompany.setValue(company);
		try {
			Map allMap=getZDFPeriodInfo(company);
			this.pe = new PeriodEntity(allMap);//PeriodEntity.requestPeriodEntity(this.company);
			SpinnerUtil.managePeriodRange(this.pe, this.spnPeriodYearBegin,
					this.spnPeriodNumberBegin, this.spnPeriodYearEnd,
					this.spnPeriodNumberEnd, true);
		} catch (Exception e) {
			throw new ReportException(ReportException.PERIOD_ERROR);
		}

		Integer maxLevel = (Integer) initData.get("MaxAccountLevel");
		if (maxLevel != null) {
			SpinnerNumberModel levelStartModel = new SpinnerNumberModel(1, 1,
					maxLevel.intValue(), 1);
			this.spnAccountLevelBegin.setModel(levelStartModel);
			SpinnerNumberModel levelEndModel = new SpinnerNumberModel(1, 1,
					maxLevel.intValue(), 1);
			this.spnAccountLevelEnd.setModel(levelEndModel);

			SpinnerUtil.manageAccountLevelRange(1, maxLevel.intValue(),
					this.spnAccountLevelBegin, this.spnAccountLevelEnd);
		}

		CurrencyInfo generalLocalCurrency = null;

		CurrencyInfo generalRptCurrency = null;
		this.cmbCurrency.removeAllItems();
		CurrencyCollection currencyCollection = (CurrencyCollection) initData
				.get("AllCurrency");

		if (currencyCollection != null) {
			for (int i = 0; i < currencyCollection.size(); i++) {
				CurrencyInfo currency = currencyCollection.get(i);

				if ((currency != null)
						&& (!currency.getId().toString().trim().equals(
								CurrencyInfo.GENERAL_LOCAL_CURRENCY_ID
										.toString()))
						&& (!currency.getId().toString().trim().equals(
								CurrencyInfo.GENERAL_REPORT_CURRENCY_ID
										.toString()))) {
					this.cmbCurrency.addItem(currency);
				}

				if ((currency != null)
						&& (currency.getId().toString().trim()
								.equals(CurrencyInfo.GENERAL_LOCAL_CURRENCY_ID
										.toString()))) {
					generalLocalCurrency = currency;
				}

				if ((currency != null)
						&& (currency.getId().toString().trim()
								.equals(CurrencyInfo.GENERAL_REPORT_CURRENCY_ID
										.toString()))) {
					generalRptCurrency = currency;
				}
			}

			if ((generalLocalCurrency != null)
					&& (generalLocalCurrency.getName() != null)
					&& (!generalLocalCurrency.getName().trim().equals(""))) {
				this.cmbCurrency.addItem(generalLocalCurrency);
			}

			if ((generalRptCurrency != null)
					&& (generalRptCurrency.getName() != null)
					&& (!generalRptCurrency.getName().trim().equals(""))) {
				this.cmbCurrency.addItem(generalRptCurrency);
			}

			if (!GlUtils.isCompanyUnion(this.company)) {
				this.cmbCurrency.addItem(GLResUtil.getRes("all_currency"));
			}

		}

		CurrencyInfo defaultCurrency = (CurrencyInfo) initData
				.get("DefaultCurrency");
		if (defaultCurrency != null) {
			EasUIUtils.setComboBoxSelectByUUid(this.cmbCurrency,
					defaultCurrency);
		}

		AccountTableInfo accountTable = this.company.getAccountTable();
		try {
			AccountRefContrastInfo ar = AccountRefContrastFactory
					.getRemoteInstance().getLastAcctInfo(
							this.company.getId().toString(),
							this.company.getAccountTable().getId().toString(),
							this.spnPeriodYearEnd.getIntegerVlaue().intValue(),
							this.spnPeriodNumberEnd.getIntegerVlaue()
									.intValue());
			if (ar != null)
				accountTable = ar.getEnabledAcctTable();
		} catch (Exception ex) {
			handUIException(ex);
		}

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("companyID.id", this.company.getId()
				.toString()));
		fic.add(new FilterItemInfo("accountTableID.id", accountTable.getId()
				.toString()));
		filter.setMaskString("#0 and #1");

		AccountPromptBox apb = new AccountPromptBox(this, accountTable, filter);

		this.prbAccountBegin.setEditable(true);
		this.prbAccountBegin.setEditFormat("$number$");
		this.prbAccountBegin.setCommitFormat("$helpCode$;$number$;$name$");
		this.prbAccountBegin.setDisplayFormat("$number$ - $name$");
		this.prbAccountBegin
				.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
		this.prbAccountBegin.setEnabledMultiSelection(true);
		this.prbAccountBegin.setSelector(apb);

		this.prbAccountEnd.setEditFormat("$number$");
		this.prbAccountEnd.setCommitFormat("$helpCode$;$number$;$name$");
		this.prbAccountEnd.setDisplayFormat("$number$ - $name$");
		this.prbAccountEnd
				.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
		this.prbAccountEnd.setEnabledMultiSelection(true);
		this.prbAccountEnd.setSelector(apb);

		EntityViewInfo myevi = new EntityViewInfo();
		myevi.setFilter(filter);
		this.prbAccountBegin.setEntityViewInfo(myevi);
		this.prbAccountEnd.setEntityViewInfo(myevi);
		
		
	    //add by tgw 新增成本中心
		
		prmtCompany.setEditFormat("$number$");
		prmtCompany.setCommitFormat("$number$");
		prmtCompany.setDisplayFormat("$name$");
		prmtCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyQuery");
		prmtCompany.setEnabledMultiSelection(false);
		
		CostCenterOrgUnitInfo centerOrgUnitInfo = SysContext.getSysContext().getCurrentCostUnit();
		
		prmtCostOrg.setEditable(true);
		prmtCostOrg.setEditFormat("$number$");
		prmtCostOrg.setCommitFormat("$number$");
		prmtCostOrg.setDisplayFormat("$name$");
		prmtCostOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");
		prmtCostOrg.setEnabledMultiSelection(false);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("longnumber"));
		prmtCostOrg.setSelectorCollection(sic);
		
		
		EntityViewInfo viewrange = new EntityViewInfo();
		String orgids="";
		viewrange.getSelector().add(new SelectorItemInfo("id"));
		viewrange.getSelector().add(new SelectorItemInfo("org.*"));
		FilterInfo filterrange = new FilterInfo();
		FilterItemCollection ficrange = filterrange.getFilterItems();
		ficrange.add(new FilterItemInfo("user",SysContext.getSysContext().getCurrentUserInfo().getId()));
		viewrange.setFilter(filterrange);
		try {
			OrgRangeCollection orgranges=OrgRangeFactory.getRemoteInstance().getOrgRangeCollection(viewrange);
			if(orgranges!=null&&orgranges.size()>0){

				for(int i=0;i<orgranges.size();i++){
					if(orgranges.get(i).getOrg().isIsCostOrgUnit()){
						orgids=orgids+",'"+orgranges.get(i).getId().toString()+"'";
					}
				}
				orgids=orgids.substring(3, orgids.length());
			}
		} catch (BOSException e1) {
			handUIException(e1);
		}
		
		viewrange = new EntityViewInfo();
		filterrange = new FilterInfo();
		filterrange.getFilterItems().add(new FilterItemInfo("id",orgids,CompareType.INNER));
		viewrange.setFilter(filterrange);
		prmtCostOrg.setEntityViewInfo(viewrange);
		
		prmtCostOrg.setValue(centerOrgUnitInfo);
		
		this.loading = false;
	}

	protected void prbAccountEnd_dataChanged(DataChangeEvent e)
			throws Exception {
//		AccountClientUtils.checkCompanyAccountTable(null, this.company);
//		BOSUuid companyId = this.company.getId();
//		BOSUuid accountTableId = this.company.getAccountTable().getId();
//		IObjectPK companyPK = new ObjectUuidPK(companyId);
//		IObjectPK accountTablePK = new ObjectUuidPK(accountTableId);
//
//		if ((!(this.prbAccountBegin.getValue() instanceof Object[]))
//				|| (!(this.prbAccountEnd.getValue() instanceof Object[]))) {
//			return;
//		}
//		Object[] accountFrom = (Object[]) this.prbAccountBegin.getValue();
//		Object[] accountTo = (Object[]) this.prbAccountEnd.getValue();
//		int l = 1;
//		int j = 1;
//		int t = 1;
//
//		if (accountFrom != null) {
//			for (int i = 0; (i < accountFrom.length)
//					&& (accountFrom[i] != null); i++) {
//				String accountNumberNameFrom = accountFrom[i].toString();
//				if (accountNumberNameFrom != null) {
//					String[] accountValue = accountNumberNameFrom.split(" ");
//					String strAccountNumber = accountValue[0];
//					l = AccountUtils.getAccountViewCByNumber(strAccountNumber,
//							companyPK, accountTablePK).getLevel();
//					if (l > t) {
//						t = l;
//					}
//				}
//			}
//			l = t;
//		}
//
//		if (accountTo != null) {
//			t = 1;
//			for (int i = 0; (i < accountTo.length) && (accountTo[i] != null); i++) {
//				String accountNumberNameTo = accountTo[i].toString();
//				if (accountNumberNameTo != null) {
//					String[] accountValue = accountNumberNameTo.split(" ");
//					String strAccountNumber = accountValue[0];
//					j = AccountUtils.getAccountViewCByNumber(strAccountNumber,
//							companyPK, accountTablePK).getLevel();
//					if (j > t) {
//						t = j;
//					}
//				}
//			}
//			j = t;
//		}
//		Integer k;
//		if (l > j)
//			k = new Integer(l);
//		else {
//			k = new Integer(j);
//		}
//		this.spnAccountLevelEnd.setValue(k);
	}

	protected void prbAccountBegin_dataChanged(DataChangeEvent e)
			throws Exception {
//		Object accountFrom = this.prbAccountBegin.getValue();
//		Object accountTo = this.prbAccountEnd.getValue();
//		if ((accountFrom != null) && (accountTo == null)) {
//			if ((accountFrom instanceof Object[])) {
//				Object[] accounts = (Object[]) accountFrom;
//				for (int i = 0; i < accounts.length; i++) {
//					if (accounts[i] == null)
//						return;
//				}
//			}
//			this.prbAccountEnd.setValue(accountFrom);
//		} else {
//			prbAccountEnd_dataChanged(e);
//		}
	}
	
	private Map getZDFPeriodInfo(CompanyOrgUnitInfo com) throws Exception{
		Map allMap=new HashMap();
		Map map = new HashMap();
		String periodType = com.getAccountPeriodType().getId().toString();
		StringBuffer sb = new StringBuffer();
		sb.append("select FPeriodYear from t_bd_period where FTypeID='"+periodType+"' GROUP BY FPeriodYear");
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sb.toString()).executeSQL();
		int[] periodRange = new int[2];
		periodRange[0] = 1;
		periodRange[1] = 12;
		for(int i =0;i <rs.size();i++){
			if(rs.next()){
				map.put(new Integer(rs.getString("FPeriodYear")), periodRange);
			}
		}

//		map.put(new Integer(2012), periodRange);
//		map.put(new Integer(2013), periodRange);
//		map.put(new Integer(2014), periodRange);
//		map.put(new Integer(2015), periodRange);
		Map map2 = new HashMap();
		Calendar calendar = Calendar.getInstance();
		
		Date date1=new Date();
		map2.putAll(map);
		allMap.put("usedPeriod", map);
		allMap.put("queryPeriod", map2);
		allMap.put("currentPeriod", new int[] { calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1});
		return allMap;
	}
	public void setPeriodEntity(CompanyOrgUnitInfo com) throws ReportException {
		this.loading = true;
		try {
//			this.pe = PeriodEntity.requestPeriodEntity(com);
			Map allMap=getZDFPeriodInfo(com);
			this.pe = new PeriodEntity(allMap);//PeriodEntity.requestPeriodEntity(this.company);
			SpinnerUtil.managePeriodRange(this.pe, this.spnPeriodYearBegin,
					this.spnPeriodNumberBegin, this.spnPeriodYearEnd,
					this.spnPeriodNumberEnd, true);
		} catch (Exception e) {
			throw new ReportException(ReportException.PERIOD_ERROR);
		}
	}

	public void setCustomerParams(CustomerParams cp) {
		try {
			Object param = RptParamsUtil.getFromCustomerParams(cp);
			if (((param instanceof RptFilterInfo))
					|| ((param instanceof RptFilterCollection))
					|| ((param instanceof RptFilterInfo[]))
					|| ((param instanceof RptFilterCollection[]))) {
				setParam(RptFilterUtils.toObjectFromRptFilterMap(RptParamsUtil
						.getFromCustomerParams(cp)));
			} else
				setParam(RptParamsUtil.getFromCustomerParams(cp));
		} catch (EASBizException e) {
			ExceptionHandler.handle(this, e);
		}
	}

	public CustomerParams getCustomerParams() {
		CustomerParams cp = new CustomerParams();

		RptParamsUtil.setToCustomerParams(cp, RptFilterUtils
				.toRptFilterMapFromObject(getParam()));
		return cp;
	}

	public Object getParam() {
		return getCustomCondition().toMap();
	}

	public void setParam(Object param) {
		this.isQueryByOther = true;
		if (this.isCleared) {
			this.isQueryByOther = false;
		}
		if (param != null)
			try {
				setCustomCondition(new ZDFReportConditionSubsidiaryLedger(
						(Map) param));
			} catch (CloneNotSupportedException ex) {
				handUIException(ex);
			}
	}

	public boolean verify() {
		
		Object object = prmtCompany.getValue();
		boolean b = false;
		if(object instanceof Object[]){
			if(!(((Object[]) object).length>0)){
				b=true;
			}
		}else{
			if(object==null){
				b=true;
			}
		}
		if(b){
			MsgBox.showWarning("财务组织不能为空，请重现选择成本中心！");
			SysUtil.abort();
		}
		
		Integer beginLevel = (Integer) this.spnAccountLevelBegin.getValue();
		Integer endLevel = (Integer) this.spnAccountLevelEnd.getValue();
		if ((beginLevel != null) && (endLevel != null)) {
			if (beginLevel.intValue() > endLevel.intValue()) {
				MsgBox.showInfo(this.spnAccountLevelBegin, EASResource
						.getString(
								"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
								"18_ReportSubsidiaryLedgerConditionUI"));
				return false;
			}
		}
		
		int yearEnd = ((Integer) this.spnPeriodYearEnd.getValue()).intValue();
		int yearBegin = ((Integer) this.spnPeriodYearBegin.getValue())
				.intValue();
		int numberBegin = ((Integer) this.spnPeriodNumberBegin.getValue())
				.intValue();

		return true;//!RptClientUtil.isBetweenAdjPeriod(this, yearEnd, yearBegin,
				//numberBegin);
	}

	public Component getFocusComponent() {
		return ((KDSpinner.DefaultNumberEditor) this.spnPeriodYearBegin
				.getEditor()).getTextField();
	}

	public void clear() {
		super.clear();
		this.isCleared = true;

		if (this.isQueryByOther) {
			this.isQueryByOther = false;
			return;
		}

		try {
			setInitData(this.initDataUsingForClear);
		} catch (ReportException e) {
			handUIException(e);
		}

		
		this.spnAccountLevelBegin.setValue(new Integer(1));
		this.spnAccountLevelEnd.setValue(new Integer(1));
		this.prbAccountBegin.setValue(accountViews);
		this.prbAccountEnd.setValue(null);

		this.chkOpIncludeNotPosting.setSelected(false);
		this.chkOpAmountZero.setSelected(false);
		this.chkOpOnlyLeaf.setSelected(false);
		this.chkOpBalanceAndAmountZero.setSelected(false);
		this.chkOpNotUsed.setSelected(false);
		this.chkOpShowBusinessDate.setSelected(false);
		this.chkOpOtherAccount.setSelected(false);
		this.chkOpOtherAccountItem.setSelected(false);
		this.chkOpShowQuantity.setSelected(false);
		this.chkOpOnlyAsst.setSelected(false);
		this.chkOpDailyTotal.setSelected(false);
		this.jcbNoDisplayZeroTotal.setSelected(false);
		this.chkNotIncluePLVoucher.setSelected(false);
		this.chkOpAccountCusAttribute.setSelected(false);

		showQueryItems(false);
		showQueryItemsForZDY(false);
	}

	private void showQueryItems(boolean isShowItem) {
		String[] QueryItemsForRemove = { "assistRecords.description",
				"assistRecords.originalAmount", "assistRecords.localAmount",
				"assistRecords.reportingAmount", "assistRecords.quantity" };

		try {
			RptClientUtil.showCommonQueryItems(getCommonQueryPanel(),
					QueryItemsForRemove, isShowItem);
		} catch (BOSException e) {
			handUIException(e);
		}
	}

	private void showQueryItemsForZDY(boolean isShowItem) {
		String[] QueryItemsForRemove = { "assistRecords.settlementCode",
				"settlementType.number" };

		try {
			RptClientUtil.showCommonQueryItems(getCommonQueryPanel(),
					QueryItemsForRemove, isShowItem);
		} catch (BOSException e) {
			handUIException(e);
		}
	}
}