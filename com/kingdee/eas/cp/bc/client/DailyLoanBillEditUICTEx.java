package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashSet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.BankInfoCollection;
import com.kingdee.eas.basedata.person.BankInfoFactory;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.MakeControl;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.cp.bc.ReceiveObjectEnum;
import com.kingdee.eas.cp.bc.ReturnStateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class DailyLoanBillEditUICTEx extends DailyLoanBillEditUI {

	KDBizPromptBox person = null;
	KDBizPromptBox supplier = null;
	KDBizPromptBox personBank = null;
	KDBizPromptBox supplierBank = null;
	
	KDBizPromptBox prmtPersonBox = null;
	
	EntityViewInfo bankView = null;
	EntityViewInfo vnView = null;
	boolean isCreateFrom = false;
	boolean isFirstLoad = true;
	
	private KDBizPromptBox bizPromptExpenseTypeEntry = new KDBizPromptBox();  //父类中的不可见
	
	public DailyLoanBillEditUICTEx() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		isFirstLoad = true;
		super.onLoad();
		//initBillLoad();
		initFeild(); 
		isFirstLoad = false;
		
	}
	
	@Override
	public void loadFields() {
		super.loadFields();
		
		//复制并新增是还款金额和还款状态为空
		if(getUIContext().get("isListCopyAndAddNew")!=null){
			if((Boolean) getUIContext().get("isListCopyAndAddNew")){
				this.returnState.setSelectedItem(null);
				this.txtreturnAmount.setValue(null);
			}
		}
		
		try {
			initBillLoad();
		} catch (BOSException e) {
			e.printStackTrace();
			this.handUIException(e);
		}
	}
	
	@Override
	public void storeFields() {
		if(getUIContext().get("isListCopyAndAddNew")!=null){
			if((Boolean) getUIContext().get("isListCopyAndAddNew")){
				this.editData.setReturnState(ReturnStateEnum.NULL);
				this.editData.setReturnAmt(null);
			}
		}
		super.storeFields();
	}
	
	protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		super.beforeStoreFields(arg0);
		 if ((STATUS_CHECKING.equals(this.oprtState)) && 
			      (this.prmtVoucherNum.getValue() == null)) {
			      MsgBox.showWarning("凭证字不能为空");
			      SysUtil.abort();
			    }
	}
	
	
	private void initFeild(){
//		prmtVoucherNum.setEnabled(false);
		contract.setEnabled(false);
//		this.kdtEntries.getColumn("expensetype").getStyleAttributes().set(true);
		if(this.editData.getSourceBillId()!= null){
			//this.kdtEntries.getColumn("expensetype").getStyleAttributes().setLocked(true);
			this.kdtEntries.getColumn("project").getStyleAttributes().setLocked(true);
			this.kdtEntries.getColumn("CostedDept").getStyleAttributes().setLocked(true);
			
//			if(this.editData.getFirstCreateFrom() == 0){
//				txtName.setText("");
//				kDTextBankStr.setText("");
//				txtPayerAccount.setText("");
//				txtPayName.setText("");
//				txtFirstCreateFrom.setText("1");
//			}
		}
		
		this.kDLabelContainer33.setVisible(false);
		this.payCompany.setVisible(false);
		this.payCompany.setEnabled(false);
		
		
		//xulisha  分录费用归属部门添加过滤  2014-12-30
		KDBizPromptBox prmtCostedDept = new KDBizPromptBox();
		prmtCostedDept.setCommitFormat("$number$");		
		prmtCostedDept.setDisplayFormat("$name$");		
		prmtCostedDept.setEditFormat("$number$");		
		prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
		prmtCostedDept.setEditable(true);
		MakeControl.makeCostCenterF7(prmtCostedDept, (CompanyOrgUnitInfo)this.bizPromptCompany.getData(), this.paramMap, this.OrgRangeSet);
		this.kdtEntries.getColumn("CostedDept").setEditor(new KDTDefaultCellEditor(prmtCostedDept));
		
		prmtPersonBox = new KDBizPromptBox();
		prmtPersonBox.setRequired(false);
		prmtPersonBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		prmtPersonBox.setEditFormat("$number$");
		prmtPersonBox.setDisplayFormat("$name$-$number$");
		prmtPersonBox.setCommitFormat("$number$");
		
		ObjectValueRender kdtEntrys_person_OVR = new ObjectValueRender();
		kdtEntrys_person_OVR.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat("$name$-$number$"));
		
		this.kdtEntries.getColumn("person").setEditor(new KDTDefaultCellEditor(prmtPersonBox));
		this.kdtEntries.getColumn("person").setRenderer(kdtEntrys_person_OVR);
		this.kdtEntries.getColumn("person").setRequired(false);
	
		bizPromptExpenseType.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent arg0) {
				 String ids = null;
				 
				 if ((bizPromptCostedDept.getData() instanceof FullOrgUnitInfo)) {
					 FullOrgUnitInfo org =  (FullOrgUnitInfo)bizPromptCostedDept.getData();
					 if(org!=null){
						 ids = ((FullOrgUnitInfo)bizPromptCostedDept.getData()).getId().toString();
					 }
				}else  if ((bizPromptCostedDept.getData() instanceof CostCenterOrgUnitInfo)) {
					CostCenterOrgUnitInfo org =  (CostCenterOrgUnitInfo)bizPromptCostedDept.getData();
					 if(org!=null){
						 ids = ((CostCenterOrgUnitInfo)bizPromptCostedDept.getData()).getId().toString();
					 }
				}
				 
				 if (("ADDNEW".equals(getOprtState())) || ("EDIT".equals(getOprtState()))){
					 OperationTypePromptBox selector = (OperationTypePromptBox)bizPromptExpenseType.getSelector();
					 if(selector==null){
						 selector = (OperationTypePromptBox)bizPromptExpenseType.getSelector();
					 }
					 if(ids==null){
						 ids = BOSUuid.create("111111").toString();
					 }
					 selector.getUiContext().put("costCenterId", ids);
					
				 }
			}});
	}

	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
		if(this.editData.getSourceBillId()!= null){
			//this.kdtEntries.getColumn("expensetype").getStyleAttributes().setLocked(true);
			this.kdtEntries.getColumn("project").getStyleAttributes().setLocked(true);
			this.kdtEntries.getColumn("CostedDept").getStyleAttributes().setLocked(true);
		}
		prmtPayer.setValue(null);
		initBillLoad();
	}

	@Override
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
		isCreateFrom = true;
	}

	protected void bizPromptCostedDept_dataChanged(DataChangeEvent e)
			throws Exception {
		super.bizPromptCostedDept_dataChanged(e);
		
		 if (e.getNewValue() != e.getOldValue()) {
			 bizPromptExpenseType.setValue(null);
		 }
			
	}
	
	protected void initUIData() {
		super.initUIData();
		 //父类中的不可见
	     MakeControl.makeAccountF7_mul(bizPromptExpenseTypeEntry, this);
	     bizPromptExpenseTypeEntry.setEditable(true);
	     ObjectValueRender avr = new ObjectValueRender();
	     avr.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat("$typeName$-$number$"));
	     kdtEntries.getColumn("expensetype").setRenderer(avr);
	     kdtEntries.getColumn("expensetype").setEditor(new KDTDefaultCellEditor(bizPromptExpenseTypeEntry));
		
	     this.kdtEntries.addKDTEditListener(new KDTEditAdapter()
	     {
	       public void editStarting(KDTEditEvent e)
	       {
	         if (e.getColIndex() == kdtEntries.getColumnIndex("expensetype"))
	         {
	           ExpenseTypePromptBox selector = (ExpenseTypePromptBox)bizPromptExpenseTypeEntry.getSelector();
	           
	          /* if (bizPromptExpenseType.getValue() != null) {
	             selector.getUiContext().put("operationTypeId", ((OperationTypeInfo)bizPromptExpenseType.getValue()).getId().toString());
	           } else {*/
	             selector.getUiContext().put("operationTypeId", null);
	          // }
	           
	           if (bizPromptCompany.getData() != null) {
	             String ln = ((CompanyOrgUnitInfo)bizPromptCompany.getData()).getLongNumber();
	             String[] lnSecs = ln.split("!");
	             int size = lnSecs.length;
	             HashSet lnUps = new HashSet();
	             for (int i = 0; i < size; i++) {
	               lnUps.add(lnSecs[i]);
	             }
	             selector.getUiContext().put("companyId", ((CompanyOrgUnitInfo)bizPromptCompany.getData()).getId().toString());
	             selector.getUiContext().put("companyLongNumber", ((CompanyOrgUnitInfo)bizPromptCompany.getData()).getLongNumber());
	           } else {
	             selector.getUiContext().put("companyId", null);
	           }
	           String costDeptid= null;
	           if(kdtEntries.getCell(e.getRowIndex(),"CostedDept").getValue()!=null){
					costDeptid =  ((CostCenterOrgUnitInfo)kdtEntries.getCell(e.getRowIndex(), kdtEntries.getColumnIndex("CostedDept")).getValue()).getId().toString();
				}else{
					costDeptid = BOSUuid.create("111111").toString();
				}
	             selector.getUiContext().put("costCenterId", costDeptid);
	         } 
	       }
	       
	 
	 
	       public void editStopping(KDTEditEvent e) {}
	       
	 
	       public void editStopped(KDTEditEvent e)
	       {
	          editStoppingHandler(e);
	       }
	     });
	     
	}	
	
	protected void editStoppingHandler(KDTEditEvent e) {
		super.editStoppingHandler(e);
		 if (e.getColIndex() == kdtEntries.getColumnIndex("CostedDept"))
		 {
			 if(e.getOldValue()!=e.getValue()){
				 kdtEntries.getCell(e.getRowIndex(), "expensetype").setValue(null);
			 }
		 }
	}
	
	
	
	private void initBillLoad() throws BOSException{
		
		SupplierInfo spInfo = new SupplierInfo();
		SupplierCollection spCol = new SupplierCollection();
		PersonInfo pnInfo = new PersonInfo();
		PersonCollection pnCol = new PersonCollection();
		SupplierCompanyBankCollection scCol = new SupplierCompanyBankCollection();
		SupplierCompanyBankInfo scInfo = new SupplierCompanyBankInfo();
		BankInfoCollection biCol = new BankInfoCollection();
		BankInfoInfo biInfo = new BankInfoInfo();
		if(this.getOprtState().equals(STATUS_CHECKING)){
			prmtVoucherNum.setEnabled(true);
			prmtVoucherNum.setEditable(true);
			vnView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			vnView.setFilter(filter);
			if(bizPromptCompany.getValue()!=null){
				filter.getFilterItems().add(new FilterItemInfo(" companyNumber.id = '" + ((CompanyOrgUnitInfo)bizPromptCompany.getValue()).getId().toString() + "'"));
				prmtVoucherNum.setEntityViewInfo(vnView);
			}else{
				prmtVoucherNum.setValue(null);
			}
			this.actionSubmit.setEnabled(true);
		}else{
			prmtVoucherNum.setEnabled(false);
		}
		if(receiveObject.getSelectedItem() == null){
			receiveObject.setSelectedItem(ReceiveObjectEnum.personal);
		}else if(receiveObject.getSelectedItem().equals(ReceiveObjectEnum.personal)){
			prmtPayer.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
			prmtBankNumber.setEditFormat("$bandAcctNumber$");
			prmtBankNumber.setDisplayFormat("$bandAcctNumber$");
			prmtBankNumber.setCommitFormat("$bandAcctNumber$");
			prmtBankNumber.setQueryInfo("com.kingdee.eas.basedata.person.app.BankInfoQuery");
		}else{
			prmtPayer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
			prmtBankNumber.setEditFormat("$bankAccount$");
			prmtBankNumber.setDisplayFormat("$bankAccount$");
			prmtBankNumber.setCommitFormat("$bankAccount$");
			prmtBankNumber.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierCompanyInfoBankQuery");
		}
		if(this.getOprtState().equals(OprtState.ADDNEW)){
//			删除原来的默认
			kDTextBankStr.setText("");
			txtPayerAccount.setText("");
			txtPayName.setText("");
		}
		/////F7收款对象字段初始化
		if(txtpayerid.getText()!=null && !txtpayerid.getText().equals("")){
			if(receiveObject.getSelectedItem().equals(ReceiveObjectEnum.personal)){
				pnCol = PersonFactory.getRemoteInstance().getPersonCollection("where id = '" + txtpayerid.getText() + "'");
				if(pnCol.size() > 0 ){
					pnInfo = pnCol.get(0);
					prmtPayer.setValue(pnInfo);
					addBankFilter(1,pnInfo);
				}
			}else{
				spCol = SupplierFactory.getRemoteInstance().getSupplierCollection("where id = '" + txtpayerid.getText() + "'");
				if(spCol.size() > 0 ){
					spInfo = spCol.get(0);
					prmtPayer.setValue(spInfo);
					addBankFilter(2,spInfo);
				}
			}
			/////F7银行账号
			if( this.editData.getPayerAccount()!=null &&  ! this.editData.getPayerAccount().equals("") ){
				if(receiveObject.getSelectedItem().equals(ReceiveObjectEnum.personal)){
					biCol = BankInfoFactory.getRemoteInstance().getBankInfoCollection(
							"where bandAcctNumber = '" +  this.editData.getPayerAccount() + "'" );// and person = '"
//							+ pnInfo.getId().toString() + "'");
					if(biCol.size() > 0 ){
						biInfo = biCol.get(0);
						prmtBankNumber.setValue(biInfo);
					}
				}else{
					
					scCol = SupplierCompanyBankFactory.getRemoteInstance().getSupplierCompanyBankCollection(
							"where bankAccount = '" +  this.editData.getPayerAccount()+"'");
//							+ 
//							"' and supplierCompanyInfo = '" 
//							+ spInfo.getId().toString() + "'" );
					
					if(scCol.size() > 0 ){
						scInfo = scCol.get(0);
						prmtBankNumber.setValue(scInfo);
					}
				}
			}else{
				kDTextBankStr.setText("");
			}
		}else{
			kDTextBankStr.setText("");
		}
		
	}

	
//	@Override
	protected void receiveObject_itemStateChanged(ItemEvent e) throws Exception {
		// TODO Auto-generated method stub
		Object obj = receiveObject.getSelectedItem();
		if(obj!=null){
			if(((ReceiveObjectEnum)obj) == ReceiveObjectEnum.supplier){
				prmtPayer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
				prmtBankNumber.setEditFormat("$bankAccount$");
				prmtBankNumber.setDisplayFormat("$bankAccount$");
				prmtBankNumber.setCommitFormat("$bankAccount$");
				prmtBankNumber.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierCompanyInfoBankQuery");
				if(prmtPayer.getValue() != null ){
					if(! (prmtPayer.getValue() instanceof SupplierInfo)){
						prmtPayer.setValue(null);
					}
					if(prmtBankNumber.getValue() != null ){
						prmtBankNumber.setValue(null);
					}
				}
				
			}else{
				prmtPayer.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
				prmtBankNumber.setEditFormat("$bandAcctNumber$");
				prmtBankNumber.setDisplayFormat("$bandAcctNumber$");
				prmtBankNumber.setCommitFormat("$bandAcctNumber$");
				prmtBankNumber.setQueryInfo("com.kingdee.eas.basedata.person.app.BankInfoQuery");
				
				if(prmtPayer.getValue() != null ){
					if(! (prmtPayer.getValue() instanceof PersonInfo)){
						prmtPayer.setValue(null);
					}
					if(prmtBankNumber.getValue() != null ){
						prmtBankNumber.setValue(null);
					}
				}
			}
			prmtPayer.setEnabled(true);
			prmtBankNumber.setEnabled(true);
		}else{
			prmtPayer.setEnabled(false);
			prmtBankNumber.setEnabled(false);
		}
		
		super.receiveObject_itemStateChanged(e);
	}

	@Override
	protected void prmtBankNumber_dataChanged(DataChangeEvent e)
			throws Exception {
		
		super.prmtBankNumber_dataChanged(e);
		Object obj = e.getNewValue();
		if(obj != null){
			if(receiveObject.getSelectedItem().equals(ReceiveObjectEnum.personal)){
				txtPayerAccount.setText(((BankInfoInfo)obj).getBandAcctNumber());
				kDTextBankStr.setText(((BankInfoInfo)obj).getBankName());
//				if(((BankInfoInfo)obj).getBandName()!=null){
//					BEBankInfo bankInfo = BEBankFactory.getRemoteInstance().getBEBankInfo(new ObjectUuidPK((((BankInfoInfo)obj).getBandName()).getId().toString()));
//					kDTextBankStr.setText(bankInfo.getName());
//				}
			}else{
				txtPayerAccount.setText(((SupplierCompanyBankInfo)obj).getBankAccount());
				kDTextBankStr.setText(((SupplierCompanyBankInfo)obj).getBank());
			}
		}else{
			txtPayerAccount.setText("");
			kDTextBankStr.setText("");
		}
	}

	@Override
	protected void prmtPayer_dataChanged(DataChangeEvent e) throws Exception {
		
		PersonCollection personColl = 	new PersonCollection();
		SupplierCollection supplierColl = new SupplierCollection();
		super.prmtPayer_dataChanged(e);
		if(isFirstLoad){
			return;
		}
		String sNumber = "",sid="";
		Object obj = e.getNewValue();
		
		if(obj != null){
			if(obj instanceof PersonInfo){
				sNumber = ((PersonInfo)obj).getNumber();
				sid =((PersonInfo)obj).getId().toString();
				addBankFilter(1,obj);
				setTheDefaultValue(obj);
			}else if(obj instanceof SupplierInfo){
				sNumber = ((SupplierInfo)obj).getNumber();
				sid =((SupplierInfo)obj).getId().toString();
				addBankFilter(2,obj);
				setTheDefaultValue(obj);
			}else{
//				手工录入
				if(e.getNewValue().equals("")){
					sNumber = "";
					sid = "";
					prmtPayer.setValue(null);
				}else{
					sNumber = e.getNewValue().toString();
					if(receiveObject.getSelectedItem()!=null){
						if(receiveObject.getSelectedItem().equals(ReceiveObjectEnum.personal)){
							personColl = PersonFactory.getRemoteInstance().getPersonCollection("where number = '" + sNumber + "'");
							if(personColl != null && personColl.size() > 0 ){
								prmtPayer.setValue(personColl.get(0));
								addBankFilter(1,personColl.get(0));
								setTheDefaultValue(personColl.get(0));
							}else{
								prmtPayer.setValue(null);
							}
						}else{
							supplierColl = SupplierFactory.getRemoteInstance().getSupplierCollection("where number = '" + sNumber + "'");
							if(supplierColl != null && supplierColl.size() > 0 ){
								prmtPayer.setValue(supplierColl.get(0));
								addBankFilter(2,personColl.get(0));
								setTheDefaultValue(supplierColl.get(0));
							}else{
								prmtPayer.setValue(null);
							}
						}
					}
				}
			}
			txtPayName.setText(sNumber);
			txtpayerid.setText(sid);
			prmtBankNumber.setEnabled(true);
			
//			带出一条数据
			
			
		}else{
			txtPayName.setText("");
			prmtBankNumber.setEnabled(false);
			prmtBankNumber.setValue(null);
		}
	}
	
	private void setTheDefaultValue(Object obj) throws BOSException{
		SupplierCompanyBankCollection scCol = new SupplierCompanyBankCollection();
		SupplierCompanyBankInfo scInfo = new SupplierCompanyBankInfo();
		BankInfoCollection biCol = new BankInfoCollection();
		BankInfoInfo biInfo = 			new BankInfoInfo();
		if(receiveObject.getSelectedItem().equals(ReceiveObjectEnum.personal)){
			biCol = BankInfoFactory.getRemoteInstance().getBankInfoCollection(
					"where  person.id = '" + ((PersonInfo)obj).getId().toString() + "'");
			if(biCol.size() == 1 ){
				biInfo = biCol.get(0);
				prmtBankNumber.setValue(biInfo);
			}else{
				prmtBankNumber.setValue(null);
			}
		}else{
			EntityViewInfo sbankView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
	        selector.add(new SelectorItemInfo("*"));
	        selector.add(new SelectorItemInfo("supplier.*"));
	        filter.getFilterItems().add(new FilterItemInfo(" supplier.id = '" +((SupplierInfo)obj).getId().toString() + "'"));
			sbankView.setFilter(filter);
			sbankView.setSelector(selector);
	        SupplierCompanyInfoCollection scpCol =  SupplierCompanyInfoFactory.
	        			getRemoteInstance().getSupplierCompanyInfoCollection(sbankView);
	        
	        if(scpCol.size() >= 1 ){
	        	filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo(" supplierCompanyInfo.id = '" +
								((SupplierCompanyInfoInfo)scpCol.getObject(0)).getId().toString() + "'"));
				sbankView.setFilter(filter);
				scCol = SupplierCompanyBankFactory.getRemoteInstance().getSupplierCompanyBankCollection(sbankView);
				
				if(scCol.size() == 1 ){
					scInfo = scCol.get(0);
					prmtBankNumber.setValue(scInfo);
				}else{
					prmtBankNumber.setValue(null);
				}
	        }
		}
	}
	
	private void addBankFilter(int lType,Object obj){
		bankView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		bankView.setFilter(filter);
		if(lType == 1){
			filter.getFilterItems().add(new FilterItemInfo(" person.id = '" + ((PersonInfo)obj).getId().toString() + "'"));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("  supplier.id = '" +((SupplierInfo)obj).getId().toString() + "'"));
		}
		prmtBankNumber.setEntityViewInfo(bankView);
	}

	@Override
	protected void bizPromptCompany_dataChanged(DataChangeEvent e)
			throws Exception {
		vnView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		vnView.setFilter(filter);
		if(e.getNewValue()!=null){
			filter.getFilterItems().add(new FilterItemInfo(" companyNumber.id = '" + ((CompanyOrgUnitInfo)e.getNewValue()).getId().toString() + "'"));
			prmtVoucherNum.setEntityViewInfo(vnView);
		}else{
			prmtVoucherNum.setValue(null);
		}
		super.bizPromptCompany_dataChanged(e);
	}

	@Override
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		txtFirstCreateFrom.setText("1");
		checkBeforSave("保存");
		if(this.getOprtState().equals(STATUS_CHECKING)){
			if(prmtVoucherNum.getValue()== null 
					|| ((CompanyOrgUnitInfo)prmtVoucherNum.getValue()).getId().toString().equals("")){
				MsgBox.showConfirm2("凭证字不能为空");
				SysUtil.abort();
			}
		}
		super.actionSave_actionPerformed(e);
	}

	@Override
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		txtFirstCreateFrom.setText("1");
		checkBeforSave("提交");
		if(this.getOprtState().equals(STATUS_CHECKING)){
			if(prmtVoucherNum.getValue()== null 
					|| ((CompanyOrgUnitInfo)prmtVoucherNum.getValue()).getId().toString().equals("")){
				MsgBox.showConfirm2("凭证字不能为空");
				SysUtil.abort();
			}
		}
		super.actionSubmit_actionPerformed(e);
	}

	private void checkBeforSave(String sType) throws EASBizException, BOSException{
		int iRows = 0;
		String projectid = "",exTypeid = "",costDeptid = "",compareId1 = "",compareId2="",personId="";
		String sTimp = "";
		iRows = this.editData.getEntries().size();
		if(prmtPayer.getValue()==null){
			MsgBox.showInfo("收款人不能为空");
			SysUtil.abort();
		}
		if(prmtBankNumber.getValue()==null){
			if(bizPromptPayType.getValue() != null){
				SettlementTypeInfo stInfo = SettlementTypeFactory.getRemoteInstance()
							.getSettlementTypeInfo(new ObjectUuidPK(((SettlementTypeInfo)bizPromptPayType.getValue()).getId().toString()));
				if(stInfo.getName().equals("现金")
						|| stInfo.getName().equals("其他无需付款")
						|| stInfo.getName().equals("账扣")){
					
				}else{
					MsgBox.showInfo("银行账号不能为空");
					SysUtil.abort();
				}
			}
		}
		if(iRows > 0 ){
			for(int i = 0 ; i < iRows ; i++){
				
				projectid = "";exTypeid="";costDeptid="";personId="";
				
				if("小计".equals(kdtEntries.getCell(i, 1).getValue())){
					continue;
				}
				
				if(kdtEntries.getCell(i, kdtEntries.getColumnIndex("project")).getValue()!=null){
					projectid =  ((ProjectInfo)kdtEntries.getCell(i, kdtEntries.getColumnIndex("project")).getValue()).getId().toString();
				}
				if(kdtEntries.getCell(i, kdtEntries.getColumnIndex("expensetype")).getValue()!=null){
					exTypeid =  ((ExpenseTypeInfo)kdtEntries.getCell(i, kdtEntries.getColumnIndex("expensetype")).getValue()).getId().toString();
				}else{
					MsgBox.showInfo("费用类型不能为空");
					SysUtil.abort();
				}
				if(kdtEntries.getCell(i, kdtEntries.getColumnIndex("CostedDept")).getValue()!=null){
					costDeptid =  ((CostCenterOrgUnitInfo)kdtEntries.getCell(i, kdtEntries.getColumnIndex("CostedDept")).getValue()).getId().toString();
				}else{
					MsgBox.showInfo("费用归属部门不能为空");
					SysUtil.abort();
				}
				/*if(kdtEntries.getCell(i, kdtEntries.getColumnIndex("person")).getValue()!=null){
					personId =  ((PersonInfo)kdtEntries.getCell(i, kdtEntries.getColumnIndex("person")).getValue()).getId().toString();
				}else{
					MsgBox.showInfo("费用清单职员不能为空");
					SysUtil.abort();
				}*/
				
//				compareId1 = projectid + "$" + exTypeid + "$" + costDeptid ;
//				for(int j = i+1 ; j < iRows ; j++){
//					if(editData.getEntries().get(j).getProject()!=null){
//						projectid =  editData.getEntries().get(j).getProject().getId().toString();
//					}
//					if(editData.getEntries().get(j).getExpenseType() != null){
//						exTypeid = editData.getEntries().get(j).getExpenseType().getId().toString();
//					}
//					if(editData.getEntries().get(j).getCostDept() != null){
//						costDeptid = editData.getEntries().get(j).getCostDept().getId().toString();
//					}
//					compareId2 = projectid + "$" + exTypeid + "$" + costDeptid ;
//					if(compareId2.equalsIgnoreCase(compareId1)){
//						MsgBox.showInfo("第" + (i+1) + "行与第" + (j+1) + "行出现重复，不能" + sType +  "(项目+费用类型+费用归属部门)"   );
//						SysUtil.abort();
//					}
//				}
			}
		}
	}
	
	
	
}
