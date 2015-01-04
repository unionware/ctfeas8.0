package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.master.cssp.client.CSClientUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.LinkOthExpenseInfo;
import com.kingdee.eas.cp.bc.MakeControl;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryCollection;
import com.kingdee.eas.cp.bc.OtherExpenseBillEntryInfo;
import com.kingdee.eas.cp.bc.OtherExpenseBillInfo;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class OtherExpenseEditUICTEx extends OtherExpenseEditUI {

    private String ENTRY_PROJECT = "project";//清单-项目
    private String ENTRY_COSTEDDEPT = "costedDept";//清单-费用归属部门
    private String ENTRY_EXPENSETYPE = "expenseType";//清单-费用类型
    private String ENTRY_CURRENCYTYPE = "currencyType";//清单-币别类型
    
//    private String ENTRY_CONTRACTAMOUNT = "contractAmount";//清单-已生成合同金额本位币
    private String ENTRY_AMOUNTUSED = "amountUsed";//清单-本位币已用金额
    
    private String LINKENTRY_OTHEXPBILL = "otherExenpseBill";//关联费用单-费用申请单
    private String LINKENTRY_USEDAMOUNT =  "usedAmount";//关联费用单-本位币已用金额
    
    
    private String ENTRY_APPAMOUNTORI = "amountOri";//清单-原币申请金额 
    private String ENTRY_APPAMOUNT = "amount";//清单-本位币币申请金额 
    private String ENTRY_EXCHRATE = "exchangeRate";//清单-汇率
    
    
    private KDTEditAdapter entryEditListener;
    private KDTEditAdapter linkEntryEditListener;
    
    protected KDBizPromptBox promptProjectEntry = new KDBizPromptBox();
    protected KDBizPromptBox promptCostedDeptEntry = new KDBizPromptBox();
    protected KDBizPromptBox linkEntryOthExpBill = new KDBizPromptBox();
    protected KDBizPromptBox entryCostedDept = new KDBizPromptBox();
    
    private final String[] sumLinkFields=new String[]{"usedAmount"};
    
    private ActionListener isCentralActionListener = null;//是否是否同构监听
    private ActionListener isChangedActionListener = null;//是否变更使用费用申请单监听
    
    JButton btnAddRuleNew = null;
    JButton btnRemoveRuleNew = null;
    
	public OtherExpenseEditUICTEx() throws Exception {
		super();
	}
	
	@Override
	protected void loadData() throws Exception {
		super.loadData();
	}
	
	@Override
	public void onLoad() throws Exception {
		
		setRuleTableSumField(linkOthExpenseEntry, sumLinkFields);
		
		linkOthExpenseEntry.checkParsed();
		
		this.ctnLinkOthExpense.setTitleStyle(2);
		this.ctnLinkOthExpense.setEnableActive(false);
		
		btnAddRuleNew = this.ctnLinkOthExpense.add(this.actionAddLinkLine);
		btnRemoveRuleNew = this.ctnLinkOthExpense.add(this.actionRemoveLinkLine);
		btnAddRuleNew.setSize(22, 19);
		btnRemoveRuleNew.setSize(22, 19);
		btnAddRuleNew.setText("新增分录");
		btnRemoveRuleNew.setText("删除分录");
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemoveRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		
		btnAddRuleNew.setEnabled(false);
		btnRemoveRuleNew.setEnabled(false);
		
		super.onLoad();
		
		initUI();
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
	
	private void initUI(){
		
		txtApplyAmount.setEnabled(false);
		prmtServiceDept.setEnabled(false);
		
		prmtServiceDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");
		prmtServiceDept.setVisible(true);
		prmtServiceDept.setEditable(false);
		prmtServiceDept.setDisplayFormat("$name$");
		prmtServiceDept.setEditFormat("$number$");
		prmtServiceDept.setCommitFormat("$number$");
		
		promptProjectEntry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");
		promptProjectEntry.setVisible(true);
		promptProjectEntry.setEditable(true);
		promptProjectEntry.setRequired(true);
		promptProjectEntry.setDisplayFormat("$name$");
		promptProjectEntry.setEditFormat("$number$");
		promptProjectEntry.setCommitFormat("$number$");
		this.kdtEntries.getColumn(ENTRY_PROJECT).setEditor(new KDTDefaultCellEditor(promptProjectEntry));
		this.kdtEntries.getColumn(ENTRY_PROJECT).setRequired(false);
		
		linkEntryOthExpBill.setQueryInfo("com.kingdee.eas.cp.bc.app.OtherExpBillQuery");
		linkEntryOthExpBill.setVisible(true);
		linkEntryOthExpBill.setEditable(true);
		linkEntryOthExpBill.setEditFormat("$number$");
		linkEntryOthExpBill.setDisplayFormat("$number$");
		linkEntryOthExpBill.setCommitFormat("$number$");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",StateEnum.CLOSED_VALUE));
		view.setFilter(filter);
		linkEntryOthExpBill.setEntityViewInfo(view);
		this.linkOthExpenseEntry.getColumn(LINKENTRY_OTHEXPBILL).setEditor(new KDTDefaultCellEditor(linkEntryOthExpBill));
		
		this.linkOthExpenseEntry.getColumn(LINKENTRY_USEDAMOUNT).getStyleAttributes().setLocked(true);
		
		this.kdtEntries.getColumn(ENTRY_AMOUNTUSED).getStyleAttributes().setHided(false);
		this.kdtEntries.getColumn(ENTRY_AMOUNTUSED).getStyleAttributes().setLocked(true);
		
//		this.kdtEntries.getColumn(this.ENTRY_CONTRACTAMOUNT).setEditor(new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor()));
//		this.kdtEntries.getColumn(this.ENTRY_CONTRACTAMOUNT).getStyleAttributes().setLocked(true);
//		this.kdtEntries.getColumn(this.ENTRY_CONTRACTAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.txtApprovedAmount.setPrecision(2);
		this.txtApprovedAmount.setDataType(1);
		this.txtApprovedAmount.setHorizontalAlignment(2);
		this.txtApprovedAmount.setRemoveingZeroInDispaly(false);
	}
	
	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		List list = null;
		String projectid = "",exTypeid = "",costDeptid = "",str="";
		if(chkIsChanged.isSelected()){
			if(!(linkOthExpenseEntry.getRowCount()>0)){
				MsgBox.showInfo("关联费用申请单分录至少需要一条分录！");
				abort();
			}else{
				for (int i = 0; i < linkOthExpenseEntry.getRowCount(); i++) {
					if(linkOthExpenseEntry.getCell(i, LINKENTRY_OTHEXPBILL).getValue()==null){
						MsgBox.showInfo("【关联费用申请单】分录的“关联费用申请单”字段不允许为空！");
						abort();
					}
				}
				
			}
		}
		if(chkIsCentralPur.isSelected()){
			if(prmtServiceDept.getValue()==null){
				MsgBox.showInfo("服务部门不能为空！");
				prmtServiceDept.requestFocus();
				abort();
			}
		}
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
				
				/*if(kdtEntries.getCell(i, ENTRY_PROJECT).getValue()!=null){
					projectid = ((ProjectInfo)kdtEntries.getCell(i, ENTRY_PROJECT).getValue()).getId().toString();
				}
				exTypeid = ((ExpenseTypeInfo)kdtEntries.getCell(i, ENTRY_EXPENSETYPE).getValue()).getId().toString();
				costDeptid = ((CostCenterOrgUnitInfo)kdtEntries.getCell(i, ENTRY_COSTEDDEPT).getValue()).getId().toString();
				str = projectid+"!"+exTypeid +"!"+costDeptid;
				if(list.contains(str)){
					MsgBox.showInfo("第" + (i+1) + "行与第" + (list.indexOf(str)+1) + "行出现重复，" +  "项目+费用类型+费用归属部门要唯一！");
					SysUtil.abort();
				}else{
					list.add(str);
				}*/
			}
			
		}
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		
		isCentralActionListener =new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				isCentralPurAction();
		}};
			
		chkIsCentralPur.addActionListener(isCentralActionListener);
		
		isChangedActionListener =new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				isChangedAction(e);
		}};
			
		chkIsChanged.addActionListener(isChangedActionListener);
		
		entryEditListener = new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
    			entryEditStoped(e);
    		}
		};
		
		kdtEntries.addKDTEditListener(entryEditListener);
		
		linkEntryEditListener = new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
    			linkEntryEditStoped(e);
    		}
		};
		
		linkOthExpenseEntry.addKDTEditListener(linkEntryEditListener);
		
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
	
	protected void entryEditStoped(KDTEditEvent e) {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		BigDecimal oldValue =null;
		BigDecimal newValue =null;
		BigDecimal rate = null;
		
		if(ENTRY_APPAMOUNTORI.equals(kdtEntries.getColumnKey(colIndex))){
			newValue =  (BigDecimal) (e.getValue()==null?BigDecimal.ZERO:e.getValue());
			oldValue = (BigDecimal) (e.getOldValue()==null?BigDecimal.ZERO:e.getOldValue());
			
			rate = (BigDecimal) (kdtEntries.getCell(rowIndex, ENTRY_EXCHRATE).getValue()==null?BigDecimal.ONE:kdtEntries.getCell(rowIndex, ENTRY_EXCHRATE).getValue());
			
			if((newValue==null && oldValue==null) || (newValue!=null && newValue.equals(oldValue))){
		   		 return ;
		   	}
			changeApplyAmount((newValue.subtract(oldValue)).multiply(rate));
		    
		}
	}
	
	protected void linkEntryEditStoped(KDTEditEvent e) {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		OtherExpenseBillInfo newValue = null;
		OtherExpenseBillInfo oldValue = null;
		OtherExpenseBillInfo compValue = null;
		OtherExpenseBillEntryCollection othExpEntryCol = null;
		OtherExpenseBillEntryInfo othExpEntryInfo = null;
		BigDecimal linkEntryUsedAmountTotal = BigDecimal.ZERO;
		boolean b = true;
		
		
		if(LINKENTRY_OTHEXPBILL.equals(linkOthExpenseEntry.getColumnKey(colIndex))){
			newValue =  (OtherExpenseBillInfo) e.getValue();
			oldValue = (OtherExpenseBillInfo) e.getOldValue();
			
			if((newValue==null && oldValue==null) || (newValue!=null && newValue.equals(oldValue))){
		   		 return ;
		   	}
			
			for(int i =0;i<this.linkOthExpenseEntry.getRowCount();i++){
				
				if(i != rowIndex){
					compValue = (OtherExpenseBillInfo) linkOthExpenseEntry.getCell(i, colIndex).getValue();
		            if(compValue !=null){
		            	if(compValue.getId().toString().equals(newValue.getId().toString())){
		            		linkOthExpenseEntry.getCell(rowIndex, colIndex).setValue(oldValue);
		            		linkOthExpenseEntry.getEditManager().editCellAt(rowIndex, colIndex);
		            		b=false;
							MsgBox.showInfo(this, "关联费用申请单重复,请重新选择！");
		            	}
	            		
		            }
				}
			}
			
			if(b){
				othExpEntryCol = newValue.getEntries();
				if(othExpEntryCol!=null && othExpEntryCol.size()>0){
					for (int j = 0; j < othExpEntryCol.size(); j++) {
						othExpEntryInfo = othExpEntryCol.get(j);
						BigDecimal countAmount = othExpEntryInfo.getAmountUsed()==null?BigDecimal.ZERO:othExpEntryInfo.getAmountUsed();
						linkEntryUsedAmountTotal = linkEntryUsedAmountTotal.add(countAmount);
					}
				}
				BigDecimal usedAmountOldValue = (BigDecimal) (linkOthExpenseEntry.getCell(rowIndex,LINKENTRY_USEDAMOUNT).getValue()==null?BigDecimal.ZERO:linkOthExpenseEntry.getCell(rowIndex,LINKENTRY_USEDAMOUNT).getValue());
				linkOthExpenseEntry.getCell(rowIndex, LINKENTRY_USEDAMOUNT).setValue(linkEntryUsedAmountTotal);
				changeApplyAmount(linkEntryUsedAmountTotal.subtract(usedAmountOldValue));
			}
		}
	}

	/**
	 * 更新总申请金额
	 * @param subtract
	 */
	private void changeApplyAmount(BigDecimal subtract) {
		BigDecimal value = txtApplyAmount.getBigDecimalValue()==null?BigDecimal.ZERO:txtApplyAmount.getBigDecimalValue();
		value = value.add(subtract);
		txtApplyAmount.setValue(value);
	}

	protected void isChangedAction(ActionEvent e) {
		if (this.chkIsChanged.getSelectState() == 32){
			btnAddRuleNew.setEnabled(true);
			btnRemoveRuleNew.setEnabled(true);
			try {
				actionAddLinkLine_actionPerformed(e);
			} catch (Exception e1) {
				handUIException(e1);
			}
		}else{
			btnAddRuleNew.setEnabled(false);
			btnRemoveRuleNew.setEnabled(false);
			BigDecimal totalLinkEntryAmount = BigDecimal.ZERO;
			BigDecimal amount = BigDecimal.ZERO;
			if(linkOthExpenseEntry.getRowCount()>0){
				for(int i=0;i<linkOthExpenseEntry.getRowCount();i++){
					amount = (BigDecimal) (linkOthExpenseEntry.getCell(i, LINKENTRY_USEDAMOUNT).getValue()==null?BigDecimal.ZERO:linkOthExpenseEntry.getCell(i, LINKENTRY_USEDAMOUNT).getValue());
					totalLinkEntryAmount = totalLinkEntryAmount.add(amount);
					linkOthExpenseEntry.getCell(i, LINKENTRY_USEDAMOUNT).setValue(null);
				}
			}
			changeApplyAmount(BigDecimal.ZERO.subtract(totalLinkEntryAmount));
			linkOthExpenseEntry.removeRows();
			
		}
	}

	protected void isCentralPurAction() {
		if (this.chkIsCentralPur.getSelectState() == 32){
			prmtServiceDept.setRequired(true);
			prmtServiceDept.setEnabled(true);
		}else{
			prmtServiceDept.setEnabled(false);
			prmtServiceDept.setValue(null);
		}
			
	}

	@Override
	public void actionAddLinkLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddLinkLine_actionPerformed(e);
		kdTableAddRow(linkOthExpenseEntry);
	}
	
	@Override
	public void actionRemoveLinkLine_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionRemoveLinkLine_actionPerformed(arg0);
		kdTableDeleteRow(linkOthExpenseEntry);
	}
	
	/**
     * @description:表格新增行
     */
    private void kdTableAddRow(KDTable table)
    {
      if (!getOprtState().equals(OprtState.VIEW)){
    	  addLine(table);
    	  appendFootRow(table);
      }
    }
    
    
    
    /**
     * @description: 表格删除行
     */
    private void kdTableDeleteRow(KDTable table)
    {
      if (!getOprtState().equals(OprtState.VIEW))
        CSClientUtils.removeLine(this, table);
    }
    
    @Override
    protected IObjectValue createNewDetailData(KDTable table) {
    	if(table==null){
			return null;
		}
    	OtherExpenseBillEntryInfo billEntryInfo = (OtherExpenseBillEntryInfo) super.createNewDetailData(table);
    	if(table == this.kdtEntries){
//    		billEntryInfo.setContractAmount(BizCollUtil.ZERO);
    		billEntryInfo.setAmountUsed(BizCollUtil.ZERO);
    		return billEntryInfo;
    	}else if(table == this.linkOthExpenseEntry){
    		return  new LinkOthExpenseInfo ();
    	}
    	return null;
    }
	
    @Override
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
    	sic.add(new SelectorItemInfo("linkOthExpense.id"));
		sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.*"));
		sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.id"));
		sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.name"));
    	sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.number"));
    	sic.add(new SelectorItemInfo("linkOthExpense.usedAmount"));
		return sic;
	}
}
