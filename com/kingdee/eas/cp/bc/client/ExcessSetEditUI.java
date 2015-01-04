/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.ctc.wstx.util.StringUtil;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.cp.bc.ExcessSetEntryInfo;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExcessSetInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.IExcessSet;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ExcessSetEditUI extends AbstractExcessSetEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ExcessSetEditUI.class);

	final KDBizPromptBox kdtEntry_costCenter__PromptBox = new KDBizPromptBox();
	final KDFormattedTextField kdtEntry_rete_FormatText = new KDFormattedTextField();
	
	private KDTEditAdapter entryEditListener;
	private String ENTRY_COSTCENTER = "costCenter";
	private String ENTRY_RATE = "rate";
	
	JButton btnAddRuleNew = null;
	JButton btnRemoveRuleNew = null;
	
	EntityViewInfo view = null;
	FilterInfo filter = null;
	/**
	 * output class constructor
	 */
	public ExcessSetEditUI() throws Exception {
		super();
	}

	@Override
	public void loadFields() {
		kdtEntry.checkParsed();
		txtRate.setPrecision(2);
		kdtEntry.getColumn(ENTRY_RATE).getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}.2f");
		super.loadFields();
	}
	
	@Override
	public void onLoad() throws Exception {

		super.onLoad();
		
		String oprtState2 = getOprtState();
    	if (this.editData!= null)
        {
          boolean isEnabled;
          if ("VIEW".equals(oprtState2)) {
            isEnabled = editData.isIsEnable();
            this.actionCancel.setEnabled(isEnabled);
            this.actionCancelCancel.setEnabled(!(isEnabled));
            
          } else if ("ADDNEW".equals(oprtState2)) {
            this.actionCancel.setEnabled(false);
            this.actionCancelCancel.setEnabled(false);
          } else {
            isEnabled = this.editData.isIsEnable();
            this.actionCancel.setEnabled(isEnabled);
            this.actionCancelCancel.setEnabled(!(isEnabled));
          }
        }

		this.kDContainer1.setTitleStyle(2);
		this.kDContainer1.setEnableActive(false);
		
	    btnAddRuleNew = this.kDContainer1.add(this.actionAddLine);
	    btnRemoveRuleNew = this.kDContainer1.add(this.actionRemoveLine);
		btnAddRuleNew.setSize(22, 19);
		btnRemoveRuleNew.setSize(22, 19);
		btnAddRuleNew.setText("新增分录");
		btnRemoveRuleNew.setText("删除分录");
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemoveRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnAddRuleNew.setEnabled(true);
		btnRemoveRuleNew.setEnabled(true);
		if (this.oprtState.equals(OprtState.VIEW)) {
			btnAddRuleNew.setEnabled(false);
			btnRemoveRuleNew.setEnabled(false);
		}
		initUI();
	}

	private void initUI() {
		
		kdtEntry.checkParsed();
		
		actionCopy.setVisible(false);
    	actionSave.setVisible(false);
    	actionFirst.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionLast.setVisible(false);
    	
		prmtExpenseType.setQueryInfo("com.kingdee.eas.cp.bc.app.ExpenseTypeQuery");
		prmtExpenseType.setVisible(true);
		prmtExpenseType.setEditable(true);
		prmtExpenseType.setRequired(true);
		prmtExpenseType.setDisplayFormat("$name$");
		prmtExpenseType.setEditFormat("$number$");
		prmtExpenseType.setCommitFormat("$number$");
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isStart",Boolean.valueOf(true)));
		view.setFilter(filter);
		prmtExpenseType.setEntityViewInfo(view);
		
		prmtProject.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");
		prmtProject.setVisible(true);
		prmtProject.setEditable(true);
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		
		kdtEntry_rete_FormatText.setDataType(1);
		kdtEntry_rete_FormatText.setPrecision(2);
		kdtEntry_rete_FormatText.setSupportedEmpty(false);
		kdtEntry_rete_FormatText.setRequired(true);
		this.kdtEntry.getColumn(this.ENTRY_RATE).setEditor(new KDTDefaultCellEditor(kdtEntry_rete_FormatText));
		
		kdtEntry_costCenter__PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		kdtEntry_costCenter__PromptBox.setVisible(true);
		kdtEntry_costCenter__PromptBox.setEditable(true);
		kdtEntry_costCenter__PromptBox.setDisplayFormat("$name$");
		kdtEntry_costCenter__PromptBox.setEditFormat("$number$");
		kdtEntry_costCenter__PromptBox.setCommitFormat("$number$");
		kdtEntry_costCenter__PromptBox.setRequired(true);
		//实体成本组织
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit", Boolean.valueOf(true)));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp", Boolean.valueOf(false)));
		filter.setMaskString("#0 and #1");
		view.setFilter(filter);
		kdtEntry_costCenter__PromptBox.setEntityViewInfo(view);
		
        KDTDefaultCellEditor kdtEntry_costCenter__cellEditor = new KDTDefaultCellEditor(kdtEntry_costCenter__PromptBox);
        this.kdtEntry.getColumn(ENTRY_COSTCENTER).setEditor(kdtEntry_costCenter__cellEditor);
        
        kdYear.setValue(Calendar.getInstance().get(Calendar.YEAR));
        
        txtRate.setPrecision(2);
        txtRate.setRequired(true);
		
	}
	
	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(prmtExpenseType.getValue()==null){
			MsgBox.showInfo(this,"费用类型不能为空！");
			prmtExpenseType.requestFocus();
			abort();
		}
		if(txtRate.getBigDecimalValue()==null){
			MsgBox.showInfo(this,"超额比例不能为空！");
			txtRate.requestFocus();
			abort();
		}else{
			BigDecimal decimal = txtRate.getBigDecimalValue();
			if(decimal.compareTo(BigDecimal.ZERO)<0){
				MsgBox.showInfo(this,"超额比例必须大于等于0！");
				txtRate.requestFocus();
				abort();
			}
		}
		
		if(kdtEntry.getRowCount()>0)
			for (int i = 0; i < kdtEntry.getRowCount(); i++) {
				if(kdtEntry.getCell(i, ENTRY_COSTCENTER).getValue()==null){
					kdtEntry.getEditManager().editCellAt(i, kdtEntry.getColumnIndex(ENTRY_COSTCENTER));
					MsgBox.showInfo(this,"成本中心不能为空！");
					txtRate.requestFocus();
					abort();
				}
				if(kdtEntry.getCell(i, ENTRY_RATE).getValue()==null){
					kdtEntry.getEditManager().editCellAt(i, kdtEntry.getColumnIndex(ENTRY_RATE));
					MsgBox.showInfo(this,"超额比例不能为空！");
					txtRate.requestFocus();
					abort();
				}else{
					BigDecimal decimal = (BigDecimal) kdtEntry.getCell(i, ENTRY_RATE).getValue();
					if(decimal.compareTo(BigDecimal.ZERO)<0){
						MsgBox.showInfo(this,"超额比例必须大于等于0！");
						kdtEntry.getEditManager().editCellAt(i, kdtEntry.getColumnIndex(ENTRY_RATE));
						abort();
				    }
			}
	    }
		;
		ExpenseTypeInfo expenseTypeInfo  = (ExpenseTypeInfo) prmtExpenseType.getValue();
		ProjectInfo projectInfo = (ProjectInfo) prmtProject.getValue();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("expenseType.id",expenseTypeInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year",kdYear.getValue()));
		if(projectInfo!=null){
			filter.getFilterItems().add(new FilterItemInfo("project.id",projectInfo.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("project.id",null));
		}
		
		if(this.oprtState.equals(OprtState.EDIT)){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
			filter.setMaskString("#0 and #1 and #2 and #3");
		}else{
			filter.setMaskString("#0 and #1 and #2");
		}
		
		IExcessSet iSet = ExcessSetFactory.getRemoteInstance();
		
		if(iSet.exists(filter)){
			MsgBox.showInfo(this,"已存在'年份+费用类型+项目'的费用类型超额比例！");
			abort();
		}
		
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		if(entryEditListener==null){
    		entryEditListener = new KDTEditAdapter(){
    			public void editStopped(KDTEditEvent e) {
        			entryEditStoped(e);
        		}
    		};
    	}
		
		kdtEntry.addKDTEditListener(entryEditListener);
		
	}
	
	protected void entryEditStoped(KDTEditEvent e) {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		CostCenterOrgUnitInfo newInfo = null;
		CostCenterOrgUnitInfo oldInfo = null;
		CostCenterOrgUnitInfo compInfo = null;
		if(ENTRY_COSTCENTER.equals(kdtEntry.getColumnKey(colIndex))){
			newInfo = (CostCenterOrgUnitInfo) e.getValue();
			oldInfo = (CostCenterOrgUnitInfo) e.getOldValue();
			
			if((newInfo==null && oldInfo==null) || (newInfo!=null && newInfo.equals(oldInfo))){
		   		 return ;
		   	}
			for(int i =0;i<this.kdtEntry.getRowCount();i++){
				if(i != rowIndex){
					compInfo = (CostCenterOrgUnitInfo) kdtEntry.getCell(i, colIndex).getValue();
		            if(compInfo !=null){
		            	if(newInfo.getId().toString().equals(compInfo.getId().toString())){
		            		if(oldInfo!=null){
		            			kdtEntry.getCell(rowIndex, colIndex).setValue(oldInfo);
		            		}else{
		            			kdtEntry.getCell(rowIndex, colIndex).setValue(null);	
		            		}
		            		kdtEntry.getEditManager().editCellAt(rowIndex, colIndex);
							MsgBox.showInfo(this, "成本中心重复,请重新选择！");
						}
		            }
				}
			}
		}
	}

	@Override
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		addLine();
	}

	@Override
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
		KDTable table = this.kdtEntry;
		if (this.kdtEntry == null) {
			return;
		}
		if (table.getSelectManager().size() == 0) {
			MsgBox.showInfo(this,EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;

			Set<Integer> indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this,EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);

			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex)
						.getUserObject();

				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table
						.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else if (detailData != null) {
					int index = -1;
					if (collection != null) {
						for (int j = collection.size() - 1; j >= 0; j--) {
							if (detailData == collection.getObject(j)) {
								index = j;
							}
						}
					}
					if ((index >= 0) && (collection.size() > index)) {
						collection.removeObject(index);
					}
				}
			}
			if (table.getRow(0) != null) {
				table.getSelectManager().select(0, 0);
			}
		}
	}

	protected void addLine() {
		IObjectValue detailData = createNewDetailData(this.kdtEntry);
		if (detailData != null) {
			IRow row = this.kdtEntry.addRow();
			getUILifeCycleHandler().fireOnAddNewLine(this.kdtEntry, detailData);
			this.dataBinder.loadLineFields(this.kdtEntry, row, detailData);
		}
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception{
	  setIsEnable(false);
	}

    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
      setIsEnable(true);
    }

    protected void setIsEnable(boolean flag) throws Exception {
      
      
      editData.setIsEnable(flag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("IsEnable"));
      String message = null;
      if (flag) {
        getBizInterface().updatePartial(editData, sic);
        message = "启用成功";
      } else {
        getBizInterface().updatePartial(editData, sic);
        message = "禁用成功";
      }
      setMessageText(message);
      showMessage();

      setDataObject(getValue(new ObjectUuidPK(this.editData.getId())));
      loadFields();
      setSave(true);
      setSaved(true);

      this.btnCancelCancel.setEnabled(!(flag));
      this.btnCancel.setEnabled(flag);
    }
	    
    @Override
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	btnAddRuleNew.setEnabled(true);
    	btnRemoveRuleNew.setEnabled(true);
    	super.actionAddNew_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

      String selectID = this.editData.getId().toString();
      if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改", selectID)) {
        return;
      }
      btnAddRuleNew.setEnabled(true);
	  btnRemoveRuleNew.setEnabled(true);
      super.actionEdit_actionPerformed(e);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

      String selectID = this.editData.getId().toString();
      if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
        return;
      }
      super.actionRemove_actionPerformed(e);
    }
	    
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words, String selectID)throws Exception{
	    boolean flag = false;
	    FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("id", selectID));
	    filter.getFilterItems().add(new FilterItemInfo("IsEnable", Boolean.valueOf(true)));
	    if (getBizInterface().exists(filter)) {
	      MsgBox.showWarning("本记录已经启用，不能" + words + "!");
	      flag = true;
	    }
	    return flag;
    }
	    
	private IObjectValue createNewDetailData(KDTable kdtEntry) {
		ExcessSetEntryInfo entryInfo = new ExcessSetEntryInfo();
		return entryInfo;
	}

	@Override
	protected IObjectValue createNewData() {
		ExcessSetInfo objectValue = new ExcessSetInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUser()));
		return objectValue;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return ExcessSetFactory.getRemoteInstance();
	}

}