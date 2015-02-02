package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.MakeControl;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class EvectionReqBillEditUICTEx extends EvectionReqBillEditUI {

	private String ENTRY_PROJECT = "project";
	private String ENTRY_PERSON = "person";
	private String ENTRY_EXPENSETYPE = "expenseType";
	private String ENTRY_COSTEDDEPT = "costedDept";
	private KDTEditAdapter entryEditListener;
	
	protected KDBizPromptBox entryCostedDept = new KDBizPromptBox();
	protected KDBizPromptBox entryPerson = new KDBizPromptBox();
	protected KDBizPromptBox bizPromptExpenseTypeEntry = new KDBizPromptBox();
	
	public EvectionReqBillEditUICTEx() throws Exception {
		super();
	}

	@Override
	public void storeFields() {
		super.storeFields();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		
		kdtEntries.getColumn(ENTRY_COSTEDDEPT).setEditor(new KDTDefaultCellEditor(bizPromptCostedDept));
		
		MakeControl.makeAccountF7_mul(this.bizPromptExpenseTypeEntry, this);
		this.bizPromptExpenseTypeEntry.setEditable(true);
		ObjectValueRender avr = new ObjectValueRender();
		avr.setFormat(new BizDataFormat("$typeName$-$number$"));
		this.kdtEntries.getColumn(this.ENTRY_EXPENSETYPE).setRenderer(avr);
		
//		bizPromptExpenseTypeEntry.setQueryInfo("com.kingdee.eas.cp.bc.app.F7ExpenseTypeQuery");
//		bizPromptExpenseTypeEntry.setEditFormat("$number$");
//		bizPromptExpenseTypeEntry.setDisplayFormat("$typeName$-$number$");
//		bizPromptExpenseTypeEntry.setCommitFormat("$number$;$name$");
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("entry.billType","EvectionReqBill"));
//		view.setFilter(filter);
//		bizPromptExpenseTypeEntry.setEntityViewInfo(view);
		
		this.kdtEntries.getColumn(this.ENTRY_EXPENSETYPE).setEditor(new KDTDefaultCellEditor(this.bizPromptExpenseTypeEntry));
		
		kdtEntries.getColumn(ENTRY_PROJECT).setRequired(false);
		this.kdtEntries.getColumn(this.ENTRY_COSTEDDEPT).setRequired(true);
		
		entryPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		entryPerson.setVisible(true);
		entryPerson.setEditable(true);
		entryPerson.setRequired(true);
		entryPerson.setDisplayFormat("$name$-$number$");
		entryPerson.setEditFormat("$number$");
		entryPerson.setCommitFormat("$number$");
		ObjectValueRender kdtEntrys_person_OVR = new ObjectValueRender();
		kdtEntrys_person_OVR.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat("$name$-$number$"));

		this.kdtEntries.getColumn(ENTRY_PERSON).setEditor(new KDTDefaultCellEditor(entryPerson));
		this.kdtEntries.getColumn(ENTRY_PERSON).setRenderer(kdtEntrys_person_OVR);
		this.kdtEntries.getColumn(ENTRY_PERSON).setRequired(true);
		
	}
	
	@Override
	protected void initUIData() {
		super.initUIData();
		this.entryCostedDept.setDisplayFormat("$name$");
		this.entryCostedDept.setEditFormat("$number$");
		this.entryCostedDept.setCommitFormat("$number$");
		this.entryCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");
		this.entryCostedDept.setEditable(true);
		entryCostedDept.setEntityViewInfo(bizPromptCostedDept.getEntityViewInfo());
		this.kdtEntries.getColumn(ENTRY_COSTEDDEPT).setEditor(new KDTDefaultCellEditor(entryCostedDept));
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		
		if(entryEditListener ==null){
			entryEditListener = new KDTEditAdapter(){
				public void editStarting(KDTEditEvent e) {
					entryStarting(e);
	    		}
				public void editStopped(KDTEditEvent e) {
					entryEditStopped(e);
	    		}
			};
		}
		
		kdtEntries.addKDTEditListener(entryEditListener);
		
		bizPromptCompany.addDataChangeListener(new DataChangeListener(){
			@Override
			public void dataChanged(DataChangeEvent e) {
				boolean isChanged = true;
				isChanged = BizCollUtil.isF7ValueChanged(e);
				if (!isChanged) {
					return;
				}
				MakeControl.makeCostCenterF7(entryCostedDept, (CompanyOrgUnitInfo)bizPromptCompany.getData(), paramMap, OrgRangeSet);
				for (int i = 0; i < kdtEntries.getRowCount(); i++) {
					kdtEntries.getCell(i, ENTRY_COSTEDDEPT).setValue(null);
				}
			}});
	}

	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		List list = null;
		String projectid = "",exTypeid = "",costDeptid = "",str="";
		if(kdtEntries.getRowCount()>0){
			list = new ArrayList();
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				if(kdtEntries.getCell(i, ENTRY_EXPENSETYPE).getValue()==null){
					MsgBox.showInfo(this,"费用类型不能为空！");
					kdtEntries.getEditManager().editCellAt(i, kdtEntries.getColumnIndex(ENTRY_EXPENSETYPE));
					abort();
				}
				if(kdtEntries.getCell(i, ENTRY_COSTEDDEPT).getValue()==null){
					MsgBox.showInfo(this,"费用归属部门不能为空！");
					kdtEntries.getEditManager().editCellAt(i, kdtEntries.getColumnIndex(ENTRY_COSTEDDEPT));
					abort();
				}
				
				if(kdtEntries.getCell(i, ENTRY_PERSON).getValue()==null){
					MsgBox.showInfo(this,"职员不能为空！");
					kdtEntries.getEditManager().editCellAt(i, kdtEntries.getColumnIndex(ENTRY_PERSON));
					abort();
				}
				
				/*if(kdtEntries.getCell(i, ENTRY_PROJECT).getValue()!=null){
					projectid = ((ProjectInfo)kdtEntries.getCell(i, ENTRY_PROJECT).getValue()).getId().toString();
				}
				exTypeid = ((ExpenseTypeInfo)kdtEntries.getCell(i, ENTRY_EXPENSETYPE).getValue()).getId().toString();
				costDeptid = ((CostCenterOrgUnitInfo)kdtEntries.getCell(i, ENTRY_COSTEDDEPT).getValue()).getId().toString();
				str = projectid+"!"+exTypeid +"!"+costDeptid;
				if(list.contains(str)){
					MsgBox.showInfo("第" + (i+1) + "行与第" + (list.indexOf(str)+1) + "行出现重复，" +  "项目+费用类型+费用归属部门要唯一！)"   );
					SysUtil.abort();
				}else{
					list.add(str);
				}*/
			}
		}
		
	}
	
	protected void entryStarting(KDTEditEvent e) {
		if (e.getColIndex() == this.kdtEntries.getColumnIndex(ENTRY_EXPENSETYPE)) {
	         ExpenseTypePromptBox selector = (ExpenseTypePromptBox)this.bizPromptExpenseTypeEntry.getSelector();
	       /*  if (bizPromptExpenseType.getValue() != null) {
	             selector.getUiContext().put("operationTypeId", ((OperationTypeInfo)bizPromptExpenseType.getValue()).getId().toString());
	           } else {*/
	             selector.getUiContext().put("operationTypeId", null);
	        //   }
	           
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
	           if(kdtEntries.getCell(e.getRowIndex(),ENTRY_COSTEDDEPT).getValue()!=null){
					costDeptid =  ((CostCenterOrgUnitInfo)kdtEntries.getCell(e.getRowIndex(), kdtEntries.getColumnIndex(ENTRY_COSTEDDEPT)).getValue()).getId().toString();
				}else{
					costDeptid = BOSUuid.create("111111").toString();
				}
	             selector.getUiContext().put("costCenterId", costDeptid);
        }
	}
	
	 public void entryEditStopped(KDTEditEvent e){
	 	  if (e.getColIndex() == this.kdtEntries.getColumnIndex(ENTRY_EXPENSETYPE)){
	 		 boolean isChanged = true;
	 	      isChanged = BizCollUtil.isF7EditedChanged(e);
	          if (!isChanged) {
	            return;
	          }
	          if ((e.getValue() != null) && (
	  	             ("ADDNEW".equals(this.getOprtState())) || ("EDIT".equals(this.getOprtState())))){
	        	  ExpenseTypeInfo expenseTypeInfo = null;
	        	  if ((e.getValue() instanceof Object[])) {
	        		  Object[] objs = (Object[])e.getValue();
		               ExpenseTypeInfo[] typeInfos = new ExpenseTypeInfo[objs.length];
		               for (int i = 0; i < objs.length; i++) {
		                  typeInfos[i] = ((ExpenseTypeInfo)objs[i]);
		               }
		               expenseTypeInfo = typeInfos[0];
	        	  }else{
	        		  if((e.getValue() instanceof ExpenseTypeInfo)){
	        			  expenseTypeInfo = (ExpenseTypeInfo)e.getValue();
	        		  }
	        	  }
	        	  this.kdtEntries.getRow(e.getRowIndex()).getCell(ENTRY_EXPENSETYPE).setValue(expenseTypeInfo);
	          }
	 	  }else  if (e.getColIndex() == kdtEntries.getColumnIndex(ENTRY_COSTEDDEPT))
	  		 {
	  			 if(e.getOldValue()!=e.getValue()){
	  				 kdtEntries.getCell(e.getRowIndex(), ENTRY_EXPENSETYPE).setValue(null);
	  			 }
	  		 }
	   }
	
}
