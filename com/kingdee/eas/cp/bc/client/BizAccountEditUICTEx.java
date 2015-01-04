package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.person.BankInfoCollection;
import com.kingdee.eas.basedata.person.BankInfoFactory;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.ReceiveObjectEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


public class BizAccountEditUICTEx extends BizAccountEditUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2000610796675151742L;
	
	KDBizPromptBox person = null;
	KDBizPromptBox supplier = null;
	KDBizPromptBox personBank = null;
	KDBizPromptBox supplierBank = null;
	KDBizPromptBox project = null;
	EntityViewInfo view = null;
	FilterInfo filter = null;
	

	public BizAccountEditUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void loadFields() {
		super.loadFields();
		
		if(STATUS_ADDNEW.equals(oprtState) && (getUIContext().get("isListCopyAndAddNew")==null || !Boolean.valueOf(getUIContext().get("isListCopyAndAddNew").toString()))){
			if(kdtCollectionEntries.getRowCount()>1){
				kdtCollectionEntries.removeRow(0);
			}
			for(int i=0;i<kdtCollectionEntries.getRowCount();i++){
				kdtCollectionEntries.getCell(i, "person").setValue(kdtCollectionEntries.getCell(i, "payerName").getValue());
				kdtCollectionEntries.getCell(i, "account").setValue(kdtCollectionEntries.getCell(i, "payerAccount").getValue());
			}
		}else{
			for(int i=0;i<kdtCollectionEntries.getRowCount();i++){
				kdtCollectionEntries.getCell(i, "person").setValue(kdtCollectionEntries.getCell(i, "payerName").getValue());
				kdtCollectionEntries.getCell(i, "account").setValue(kdtCollectionEntries.getCell(i, "payerAccount").getValue());
				
				if(kdtCollectionEntries.getCell(i, "receiveObject").getValue()!=null){
					setPerson(kdtCollectionEntries.getCell(i, "receiveObject").getValue(), i);
					setAccount(kdtCollectionEntries.getCell(i, "receiveObject").getValue(), kdtCollectionEntries.getCell(i, "person").getValue(), i);
				}
			}
		}
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		bizPromptCompany.addDataChangeListener(new DataChangeListener(){

			@Override
			public void dataChanged(DataChangeEvent arg0) {
				companyDataChanged(arg0);
			}});
	}
	
	private void companyDataChanged(DataChangeEvent arg0) {
		CompanyOrgUnitInfo newValue =  (CompanyOrgUnitInfo) arg0.getNewValue();
		CompanyOrgUnitInfo oldValue =  (CompanyOrgUnitInfo) arg0.getOldValue();
		
		if((newValue==null && oldValue==null) || (newValue!=null && newValue.equals(oldValue))){
	   		 return ;
	   	}
		prmtVoucherWord.setValue(null);
		setprmtVoucherWordView(prmtVoucherWord,newValue);
	}
	
	private void setprmtVoucherWordView(KDBizPromptBox prmt,
			CompanyOrgUnitInfo company) {
		view = new EntityViewInfo();
		filter = new FilterInfo();
		if(company != null){
			filter.getFilterItems().add(new FilterItemInfo("companyNumber.id",company.getId().toString()));
		}
		view.setFilter(filter);
		prmtVoucherWord.setEntityViewInfo(view);
		
	}

	@Override
	public void onLoad() throws Exception {
		
		prmtVoucherWord.setEnabled(true);
		prmtVoucherWord.setQueryInfo("com.kingdee.eas.cp.bc.app.F7CompanyVoucherNumber");
		prmtVoucherWord.setDisplayFormat("$voucherNumber$");
		prmtVoucherWord.setCommitFormat("$voucherNumber$");
		prmtVoucherWord.setEditFormat("$voucherNumber$");
		
		person = new KDBizPromptBox();
		person.setEditable(true);
		person.setRequired(true);
		person.setDisplayFormat("$name$");
		person.setEditFormat("$number$");
		person.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		
		supplier = new KDBizPromptBox();
		supplier.setEditable(true);
		supplier.setRequired(true);
		supplier.setDisplayFormat("$name$");
		supplier.setEditFormat("$number$");
		supplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
		
		personBank = new KDBizPromptBox();
		personBank.setEditable(true);
		personBank.setRequired(true);
		personBank.setDisplayFormat("$bandAcctNumber$");
		personBank.setEditFormat("$bandAcctNumber$");
		personBank.setQueryInfo("com.kingdee.eas.basedata.person.app.BankInfoQuery");
		
		supplierBank = new KDBizPromptBox();
		supplierBank.setEditable(true);
		supplierBank.setRequired(true);
		supplierBank.setDisplayFormat("$bankAccount$");
		supplierBank.setEditFormat("$bankAccount$");
		supplierBank.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierCompanyInfoBankQuery"); 
		
		super.onLoad();
		
		CompanyOrgUnitInfo companyOrgUnitInfo = (CompanyOrgUnitInfo) bizPromptCompany.getValue();
		setprmtVoucherWordView(prmtVoucherWord, companyOrgUnitInfo);
		
		initEntry();
	}
	
	protected void initEntry(){
		
		KDBizPromptBox person1 = new KDBizPromptBox();
		person1.setEditable(true);
		person1.setRequired(true);
		person1.setDisplayFormat("$name$");
		person1.setEditFormat("$number$");
		person1.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		
		getDetailTable().getColumn("person").setEditor(new KDTDefaultCellEditor(person1));
		
		getDetailTable().getColumn("project").setRequired(false);
		if(editData.getSourceBillId()!=null){
			getDetailTable().getColumn("project").getStyleAttributes().setLocked(true);
			/*//需求要求去掉该逻辑   xulisha   2015-1-8
			getDetailTable().getColumn("expenseType").getStyleAttributes().setLocked(true);*/
			getDetailTable().getColumn("costCenter").getStyleAttributes().setLocked(true);
			
			actionAddLine.setEnabled(false);
			actionCopyLine.setEnabled(false);
		}
		getDetailTable().getColumn("noInvoiceAmt").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("noTaxAmtLocal").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("taxLocal").getStyleAttributes().setLocked(true);
		
		getDetailTable().getColumn("noInvoiceAmt").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		getDetailTable().getColumn("invoiceAmt").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		getDetailTable().getColumn("noTaxAmt").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		getDetailTable().getColumn("taxRate").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		getDetailTable().getColumn("tax").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		getDetailTable().getColumn("noTaxAmtLocal").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		getDetailTable().getColumn("taxLocal").setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
		
		KDCheckBox cb = new KDCheckBox();
		getDetailTable().getColumn("isNoInvoice").setEditor(new KDTDefaultCellEditor(cb));
		
		/*getDetailTable().addKDTEditListener(new KDTEditAdapter(){
			@Override
			public void editStopped(KDTEditEvent e) {
				super.editStopped(e);
				
				BigDecimal handred = new BigDecimal(100);
				Object obj = e.getValue();
				
				if(e.getColIndex()==getDetailTable().getColumnIndex("isNoInvoice")){
					if(obj!=null){
						boolean isNoInvoice = Boolean.valueOf(obj+"");
						
						if(isNoInvoice){
							getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").getStyleAttributes().setLocked(false);
						}else{
							getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").setValue(null);
							getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").getStyleAttributes().setLocked(true);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").setValue(null);
						getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").getStyleAttributes().setLocked(true);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("noTaxAmt")){
					if(obj!=null){
						BigDecimal noTaxAmt = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
							BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(noTaxAmt.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						}
						
						if(getDetailTable().getCell(e.getRowIndex(), "taxRate").getValue()!=null){
							BigDecimal taxRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "taxRate").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(noTaxAmt.multiply(taxRate.divide(handred)).setScale(2, BigDecimal.ROUND_HALF_UP));
							
							if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
								BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(noTaxAmt.multiply(taxRate.divide(handred)).multiply(exRate));
							}else{
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
							}
						}else{
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("taxRate")){
					if(obj!=null){
						BigDecimal taxRate = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue()!=null){
							BigDecimal noTaxAmt = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(noTaxAmt.multiply(taxRate.divide(handred)).setScale(2, BigDecimal.ROUND_HALF_UP));
							
							if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
								BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(noTaxAmt.multiply(taxRate.divide(handred)).multiply(exRate));
							}else{
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
							}
						}else{
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
						getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("tax")){
					if(obj!=null){
						BigDecimal tax = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
							BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(tax.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("exchangeRate")){
					if(obj!=null){
						BigDecimal exRate = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue()!=null){
							BigDecimal noTaxAmt = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(noTaxAmt.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						}
						
						if(getDetailTable().getCell(e.getRowIndex(), "tax").getValue()!=null){
							BigDecimal tax = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "tax").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(tax.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
					}
				}
				
			}
		});*/
		
		project = new KDBizPromptBox();
		project.setEditable(true);
		project.setRequired(true);
		project.setDisplayFormat("$name$");
		project.setEditFormat("$number$");
		project.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");
		
		getDetailTable().getColumn("project").setEditor(new KDTDefaultCellEditor(project));
		
		
		getDetailTable().addKDTPropertyChangeListener(new KDTPropertyChangeListener(){

			@Override
			public void propertyChange(KDTPropertyChangeEvent e) {
				BigDecimal handred = new BigDecimal(100);
				Object obj = e.getNewValue();
				
				if(e.getColIndex()==getDetailTable().getColumnIndex("isNoInvoice")){
					if(obj!=null){
						boolean isNoInvoice = Boolean.valueOf(obj+"");
						
						if(isNoInvoice){
							getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").getStyleAttributes().setLocked(false);
						}else{
							getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").setValue(null);
							getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").getStyleAttributes().setLocked(true);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").setValue(null);
						getDetailTable().getCell(e.getRowIndex(),"noInvoiceAmt").getStyleAttributes().setLocked(true);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("noTaxAmt")){
					if(obj!=null){
						BigDecimal noTaxAmt = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
							BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(noTaxAmt.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						}
						
						if(getDetailTable().getCell(e.getRowIndex(), "taxRate").getValue()!=null){
							BigDecimal taxRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "taxRate").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(noTaxAmt.multiply(taxRate.divide(handred)).setScale(2, BigDecimal.ROUND_HALF_UP));
							
							if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
								BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(noTaxAmt.multiply(taxRate.divide(handred)).multiply(exRate));
							}else{
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
							}
						}else{
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("taxRate")){
					if(obj!=null){
						BigDecimal taxRate = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue()!=null){
							BigDecimal noTaxAmt = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(noTaxAmt.multiply(taxRate.divide(handred)).setScale(2, BigDecimal.ROUND_HALF_UP));
							
							if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
								BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(noTaxAmt.multiply(taxRate.divide(handred)).multiply(exRate));
							}else{
								getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
							}
						}else{
							getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "tax").setValue(null);
						getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("tax")){
					if(obj!=null){
						BigDecimal tax = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue()!=null){
							BigDecimal exRate = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "exchangeRate").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(tax.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
					}
				}else if(e.getColIndex()==getDetailTable().getColumnIndex("exchangeRate")){
					if(obj!=null){
						BigDecimal exRate = new BigDecimal(obj.toString());
						if(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue()!=null){
							BigDecimal noTaxAmt = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "noTaxAmt").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(noTaxAmt.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						}
						
						if(getDetailTable().getCell(e.getRowIndex(), "tax").getValue()!=null){
							BigDecimal tax = new BigDecimal(getDetailTable().getCell(e.getRowIndex(), "tax").getValue().toString());
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(tax.multiply(exRate));
						}else{
							getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
						}
					}else{
						getDetailTable().getCell(e.getRowIndex(), "noTaxAmtLocal").setValue(null);
						getDetailTable().getCell(e.getRowIndex(), "taxLocal").setValue(null);
					}
				}
			}
			
		});

		
		/*kdtCollectionEntries.getColumn("payerName").getStyleAttributes().setHided(false);
		kdtCollectionEntries.getColumn("payerAccount").getStyleAttributes().setHided(false);*/
		
		KDComboBox comBox = new KDComboBox(ReceiveObjectEnum.getEnumList().toArray());
		
		kdtCollectionEntries.getColumn("receiveObject").setEditor(new KDTDefaultCellEditor(comBox));
		
		kdtCollectionEntries.addKDTEditListener(new KDTEditListener(){

			@Override
			public void editCanceled(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void editStarted(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void editStarting(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void editStopped(KDTEditEvent e) {
				Object obj = e.getValue();
				if(e.getColIndex()==kdtCollectionEntries.getColumnIndex("receiveObject")){
					if(obj!=null){
						if(e.getOldValue()!=obj){
							/*if(((ReceiveObjectEnum)obj) == ReceiveObjectEnum.supplier){
								kdtCollectionEntries.getCell(e.getRowIndex(), "person").setEditor(new KDTDefaultCellEditor(supplier));
							}else{
								kdtCollectionEntries.getCell(e.getRowIndex(), "person").setEditor(new KDTDefaultCellEditor(person));
							}
							
							kdtCollectionEntries.getCell(e.getRowIndex(), "person").getStyleAttributes().setLocked(false);*/
							
							setPerson(obj, e.getRowIndex());
							
							kdtCollectionEntries.getCell(e.getRowIndex(), "person").setValue(null);
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerName").setValue(null);
							kdtCollectionEntries.getCell(e.getRowIndex(), "account").setValue(null);
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(null);
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(null);
						}
					}else{
						kdtCollectionEntries.getCell(e.getRowIndex(), "person").getStyleAttributes().setLocked(true);
						kdtCollectionEntries.getCell(e.getRowIndex(), "account").getStyleAttributes().setLocked(true);
					}
				}else if(e.getColIndex()==kdtCollectionEntries.getColumnIndex("person")){
					if(obj!=null){
						/*EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						if(obj instanceof SupplierInfo){
							filter.getFilterItems().add(new FilterItemInfo("supplier.id",((SupplierInfo) obj).getId()));
							view.setFilter(filter);
							supplierBank.setEntityViewInfo(view);
							kdtCollectionEntries.getCell(e.getRowIndex(), "account").setEditor(new KDTDefaultCellEditor(supplierBank));

							display_OVR.setFormat(new BizDataFormat("$bankAccount$"));  
							kdtCollectionEntries.getCell(e.getRowIndex(),"account").setRenderer(display_OVR); 
							
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerName").setValue(((SupplierInfo) obj).getName());
						}else if(obj instanceof PersonInfo){
							filter.getFilterItems().add(new FilterItemInfo("person.id",((PersonInfo) obj).getId()));
							view.setFilter(filter);
							SelectorItemCollection sic = new SelectorItemCollection();
							sic.add("*");
							sic.add("bandName.name");
							view.setSelector(sic);
							personBank.setSelectorCollection(sic);
							kdtCollectionEntries.getCell(e.getRowIndex(), "account").setEditor(new KDTDefaultCellEditor(personBank));

							display_OVR.setFormat(new BizDataFormat("$bandAcctNumber$"));  
							kdtCollectionEntries.getCell(e.getRowIndex(),"account").setRenderer(display_OVR);
							
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerName").setValue(((PersonInfo) obj).getName());
						}
						
						kdtCollectionEntries.getCell(e.getRowIndex(), "account").getStyleAttributes().setLocked(false);*/
						
						setAccount(kdtCollectionEntries.getCell(e.getRowIndex(), "receiveObject").getValue(), obj, e.getRowIndex());
						
						if(obj instanceof SupplierInfo){
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerName").setValue(((SupplierInfo) obj).getName());
							try {
								SupplierCompanyBankCollection coll = SupplierCompanyBankFactory.getRemoteInstance().getSupplierCompanyBankCollection("where supplierCompanyInfo.supplier.id='"+((SupplierInfo) obj).getId()+"'");
								if(!(e.getOldValue() instanceof SupplierInfo) || !((SupplierInfo)e.getOldValue()).getId().toString().equals(((SupplierInfo)obj).getId().toString())){
									if(coll.size()==1){
										kdtCollectionEntries.getCell(e.getRowIndex(), "account").setValue(coll.get(0));
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(coll.get(0).getBankAccount());
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(coll.get(0).getBank());
									}else{
										kdtCollectionEntries.getCell(e.getRowIndex(), "account").setValue(null);
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(null);
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(null);
									}
								}
							} catch (BOSException ex) {
								handleException(ex);
							}
							
						}else if(obj instanceof PersonInfo){
							kdtCollectionEntries.getCell(e.getRowIndex(), "payerName").setValue(((PersonInfo) obj).getName());
							try {
								EntityViewInfo view = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("person.id",((PersonInfo) obj).getId()));
								view.setFilter(filter);
								/*SelectorItemCollection sic = new SelectorItemCollection();
								sic.add("*");
								sic.add("bandName.*");
								view.setSelector(sic);*/
								BankInfoCollection coll = BankInfoFactory.getRemoteInstance().getBankInfoCollection(view);
								if(!(e.getOldValue() instanceof PersonInfo) || !((PersonInfo)e.getOldValue()).getId().toString().equals(((PersonInfo)obj).getId().toString())){
									if(coll.size()==1){
										kdtCollectionEntries.getCell(e.getRowIndex(), "account").setValue(coll.get(0));
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(coll.get(0).getBandAcctNumber());
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(coll.get(0).getBankName());
									}else{
										kdtCollectionEntries.getCell(e.getRowIndex(), "account").setValue(null);
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(null);
										kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(null);
									}
								}
							} catch (BOSException ex) {
								handleException(ex);
							}
						}
					}else{
						kdtCollectionEntries.getCell(e.getRowIndex(), "account").setValue(null);
						kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(null);
						kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(null);
						kdtCollectionEntries.getCell(e.getRowIndex(), "account").getStyleAttributes().setLocked(true);
					}
				}else if(e.getColIndex()==kdtCollectionEntries.getColumnIndex("account")){
					if(obj!=null){
						if(e.getOldValue()!=obj){
							if(obj instanceof SupplierCompanyBankInfo){
								kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(((SupplierCompanyBankInfo) obj).getBank());
	
								kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(((SupplierCompanyBankInfo) obj).getBankAccount());
							}else if(obj instanceof BankInfoInfo){
								kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(((BankInfoInfo) obj).getBankName());
	
								kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(((BankInfoInfo) obj).getBandAcctNumber());
							}
						}
					}else{
						kdtCollectionEntries.getCell(e.getRowIndex(), "payerBank").setValue(null);
						kdtCollectionEntries.getCell(e.getRowIndex(), "payerAccount").setValue(null);
					}
				}
			}

			@Override
			public void editStopping(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void editValueChanged(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	protected void setAccount(Object typeobj, Object obj, int rowIndex){
		ObjectValueRender display_OVR = new ObjectValueRender(); 
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(obj instanceof SupplierInfo){
			filter.getFilterItems().add(new FilterItemInfo("supplier.id",((SupplierInfo) obj).getId()));
			view.setFilter(filter);
			supplierBank.setEntityViewInfo(view);
			kdtCollectionEntries.getCell(rowIndex, "account").setEditor(new KDTDefaultCellEditor(supplierBank));

			display_OVR.setFormat(new BizDataFormat("$bankAccount$"));  
			kdtCollectionEntries.getCell(rowIndex,"account").setRenderer(display_OVR); 
		}else if(obj instanceof PersonInfo){
			filter.getFilterItems().add(new FilterItemInfo("person.id",((PersonInfo) obj).getId()));
			view.setFilter(filter);
			/*SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("bandName.name");*/
			personBank.setEntityViewInfo(view);
			/*personBank.setSelectorCollection(sic);*/
			kdtCollectionEntries.getCell(rowIndex, "account").setEditor(new KDTDefaultCellEditor(personBank));

			display_OVR.setFormat(new BizDataFormat("$bandAcctNumber$"));  
			kdtCollectionEntries.getCell(rowIndex,"account").setRenderer(display_OVR);
		}else if(obj instanceof String){
			if(((ReceiveObjectEnum)typeobj) == ReceiveObjectEnum.supplier){
				filter.getFilterItems().add(new FilterItemInfo("supplier.name",obj));
				view.setFilter(filter);
				supplierBank.setEntityViewInfo(view);
				kdtCollectionEntries.getCell(rowIndex, "account").setEditor(new KDTDefaultCellEditor(supplierBank));

				display_OVR.setFormat(new BizDataFormat("$bankAccount$"));  
				kdtCollectionEntries.getCell(rowIndex,"account").setRenderer(display_OVR); 
			}else{
				filter.getFilterItems().add(new FilterItemInfo("person.name",obj));
				view.setFilter(filter);
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("bandName.name");
				personBank.setEntityViewInfo(view);
				personBank.setSelectorCollection(sic);
				kdtCollectionEntries.getCell(rowIndex, "account").setEditor(new KDTDefaultCellEditor(personBank));

				display_OVR.setFormat(new BizDataFormat("$bandAcctNumber$"));  
				kdtCollectionEntries.getCell(rowIndex,"account").setRenderer(display_OVR);
			}
		}
		
		kdtCollectionEntries.getCell(rowIndex, "account").getStyleAttributes().setLocked(false);
	}
	
	protected void setPerson(Object obj, int rowIndex){
		if(((ReceiveObjectEnum)obj) == ReceiveObjectEnum.supplier){
			kdtCollectionEntries.getCell(rowIndex, "person").setEditor(new KDTDefaultCellEditor(supplier));
		}else{
			kdtCollectionEntries.getCell(rowIndex, "person").setEditor(new KDTDefaultCellEditor(person));
		}
		
		kdtCollectionEntries.getCell(rowIndex, "person").getStyleAttributes().setLocked(false);
	}
	
	@Override
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		
		
		for(int i=0;i < kdtCollectionEntries.getRowCount() ;i++){
			////必录校验-----ZRG
			if(kdtCollectionEntries.getCell(i, "person").getValue()!=null  && 
					kdtCollectionEntries.getCell(i, "person").getValue().equals("小计")){
				continue;
			}
				if(kdtCollectionEntries.getCell(i, "payMode").getValue() == null ||
						kdtCollectionEntries.getCell(i, "payMode").getValue().toString().equals("")){
					MsgBox.showWarning("第" + (i+1) + "行支付方式不能为空");
					SysUtil.abort();
				}
				if(kdtCollectionEntries.getCell(i, "payerName").getValue() == null ||
						kdtCollectionEntries.getCell(i, "payerName").getValue().toString().equals("")){
					MsgBox.showWarning("第" + (i+1) + "行收款人不能为空");
					SysUtil.abort();
				}
				if(kdtCollectionEntries.getCell(i, "receiveObject").getValue() == null ||
						kdtCollectionEntries.getCell(i, "receiveObject").getValue().toString().equals("")){
					MsgBox.showWarning("第" + (i+1) + "行收款对象不能为空");
					SysUtil.abort();
				}
				if(kdtCollectionEntries.getCell(i, "account").getValue() == null ||
						kdtCollectionEntries.getCell(i, "account").getValue().toString().equals("")){
					if(kdtCollectionEntries.getCell(i, "payMode").getValue()!=null && !kdtCollectionEntries.getCell(i, "payMode").getValue().toString().equals("") ){
						SettlementTypeInfo stInfo = SettlementTypeFactory.getRemoteInstance()
									.getSettlementTypeInfo(new ObjectUuidPK(((SettlementTypeInfo)
											kdtCollectionEntries.getCell(i, "payMode").getValue()).getId().toString()));
						if(stInfo.getName().equals("现金")
								|| stInfo.getName().equals("其他无需付款")
								|| stInfo.getName().equals("账扣")){
							
						}else{
							MsgBox.showWarning("第" + (i+1) + "行银行账号不能为空");
							SysUtil.abort();
						}
					}else{
						MsgBox.showWarning("银行账号不能为空");
						SysUtil.abort();
					}
				}
				if(kdtCollectionEntries.getCell(i, "amountOri").getValue() == null ||
						BigDecimal.ZERO.compareTo(new BigDecimal(kdtCollectionEntries.getCell(i, "amountOri").getValue().toString()))==0){
					MsgBox.showWarning("第" + (i+1) + "行原币金额不能为空或者为0");
					SysUtil.abort();
				}
				if(kdtCollectionEntries.getCell(i, "amount").getValue() == null ||
						BigDecimal.ZERO.compareTo(new BigDecimal(kdtCollectionEntries.getCell(i, "amount").getValue().toString()))==0){
					MsgBox.showWarning("第" + (i+1) + "行本位币金额不能为空或者为0");
					SysUtil.abort();
				}
				
			////必录校验-----ZRG---END
			}
		
		for(int i=0;i<getDetailTable().getRowCount();i++){
			/*if(getDetailTable().getCell(i, "project").getValue()==null){
				MsgBox.showWarning("分录项目不能为空");
				SysUtil.abort();
			}*/
			
			if(getDetailTable().getCell(i, "isNoInvoice").getValue()!=null && Boolean.valueOf(getDetailTable().getCell(i, "isNoInvoice").getValue()+"") && getDetailTable().getCell(i, "noInvoiceAmt").getValue()==null){
				MsgBox.showWarning("分录无发票金额不能为空");
				SysUtil.abort();
			}

			if(getDetailTable().getCell(i, "person").getValue()==null){
				MsgBox.showWarning("分录职员不能为空");
				SysUtil.abort();
			}
			/*//需求要求去掉该逻辑   xulisha   2015-1-8
			BigDecimal tax = getDetailTable().getCell(i, "tax").getValue()==null?BigDecimal.ZERO:new BigDecimal(getDetailTable().getCell(i, "tax").getValue().toString());
			BigDecimal noTaxAmt = getDetailTable().getCell(i, "noTaxAmt").getValue()==null?BigDecimal.ZERO:new BigDecimal(getDetailTable().getCell(i, "noTaxAmt").getValue().toString());
			BigDecimal amountOri = getDetailTable().getCell(i, "amountOri").getValue()==null?BigDecimal.ZERO:new BigDecimal(getDetailTable().getCell(i, "amountOri").getValue().toString());
			if((noTaxAmt.add(tax)).compareTo(amountOri)!=0){
				MsgBox.showWarning("不含税金额+税额需等于原币申请金额");
				SysUtil.abort();
			}*/
		}
	}
	
	@Override
	public void actionAddCollectionLine_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = null;
		if (checkIsCountLine(kdtCollectionEntries.getRow(kdtCollectionEntries.getRowCount() - 1))) {
			row = kdtCollectionEntries.addRow(kdtCollectionEntries.getRowCount() - 1);
		} else {
			row = kdtCollectionEntries.addRow();
		}
		row.getCell("receiveObject").setValue(ReceiveObjectEnum.personal);
		row.getCell("payMode").setValue(bizPromptPayMode.getValue());
		setPerson(ReceiveObjectEnum.personal, row.getRowIndex());
	}
	
	@Override
	public void actionAddLoanLine_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionAddLoanLine_actionPerformed(arg0);
		int iCurrentRow = kdtLoanCheckEntries.getRowCount();
		Set<String> idSet = new HashSet<String>();
		for(int i=0;i<kdtLoanCheckEntries.getRowCount();i++){
			if(kdtLoanCheckEntries.getCell(i, "sourceBillEntryId").getValue()!=null){
				idSet.add(kdtLoanCheckEntries.getCell(i, "sourceBillEntryId").getValue()+"");
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("project.*");
		view.setSelector(sic);
		
		DailyLoanBillEntryCollection dailyCol = DailyLoanBillEntryFactory.getRemoteInstance().getDailyLoanBillEntryCollection(view);

		DailyLoanBillEntryInfo dailyEntry = null;
		for(int i=0;i<dailyCol.size();i++){
			dailyEntry = dailyCol.get(i);
			for(int j=0;j<kdtLoanCheckEntries.getRowCount();j++){
				if(dailyEntry.getId().toString().equals(kdtLoanCheckEntries.getCell(j, "sourceBillEntryId").getValue())){
					kdtLoanCheckEntries.getCell(j, "project").setValue(dailyEntry.getProject());
				}
			}
		}	

	}
	
	@Override
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
		
		String billStr = null;
		if ((this.editData instanceof BizAccountBillInfo)) {
			billStr = EASResource.getString("com.kingdee.eas.cp.bc.BCPortalResource", "bcBizAccountBill");
		}
		
		KDTable table = getDetailTable();
		if (table == null) {
			return;
		}
		KDTSelectManager manager = table.getSelectManager();
		if (manager.getBlocks().size() > 0){
			IRow copyFromRowObj = null;
			IRow copyToRowObj = null;
			int copyFromRowIndex = 0;
			int copyToRowIndex = 0;
			int count = 0;
			for (int i = manager.getBlocks().size()-1; i >= 0; i--) {
				KDTSelectBlock selectedBlock = (KDTSelectBlock)manager.getBlocks().get(i);
				copyFromRowIndex = selectedBlock.getBeginRow();
				for (int j = selectedBlock.getEndRow(); j >= copyFromRowIndex; j--) {
					copyFromRowObj = table.getRow(j);
					copyToRowIndex = KDTableUtil.getLastVisibleRowIndex(table);
					copyToRowObj = table.getRow(copyToRowIndex-count);
					for (int cellIndex = 0; cellIndex < table.getColumnCount(); cellIndex++) {
	//					if ((table.getColumn(cellIndex).getKDTColumn().getFieldName().indexOf("operationType") >= 0) || (table.getColumn(cellIndex).getKDTColumn().getFieldName().indexOf("expenseType") >= 0) || (table.getColumn(cellIndex).getKDTColumn().getFieldName().indexOf("costCenter") >= 0) || (table.getColumn(cellIndex).getKDTColumn().getFieldName().indexOf("company") >= 0) || (table.getColumn(cellIndex).getKDTColumn().getFieldName().indexOf("currencyType") >= 0)){
						if (table.getColumn(cellIndex).getKDTColumn().getFieldName().indexOf("project") >= 0){
							ICell copyFromCell = copyFromRowObj.getCell(cellIndex);
							ICell copyToCell = copyToRowObj.getCell(cellIndex);
							if ((copyFromCell != null) && (copyToCell != null)) {
								if ((copyFromCell.getValue() instanceof ExpenseTypeInfo)) {
									String expenseTypebillList = ((ExpenseTypeInfo)copyFromCell.getValue()).getBillTypeName();
									if ((expenseTypebillList != null) && (!expenseTypebillList.contains(billStr))) {
										throw new ExpAccException(ExpAccException.EXPENSETYPEBILLTYPE);
									}
								}
								Object orgValue = copyFromCell.getValue();
								copyToCell.setValue(orgValue);
							}
						}
					}
					if (getSubKeyFieldName() != null) {
						ICell cellId = copyToRowObj.getCell(getSubKeyFieldName());
						if (cellId != null) {
							cellId.setValue(null);
						}
					}
					this.dataBinder.storeLineFields(table, copyToRowObj, (IObjectValue)copyToRowObj.getUserObject());
					count++;
				}
			}
		}
		else {
			MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneRow"));
			return;
		}
		purpose_autoFitRowHeight();
	}

}
