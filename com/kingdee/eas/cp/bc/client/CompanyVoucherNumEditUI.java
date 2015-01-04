/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo;
import com.kingdee.eas.cp.bc.CompanyVoucherNumFactory;
import com.kingdee.eas.cp.bc.CompanyVoucherNumInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.ICompanyVoucherNum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class CompanyVoucherNumEditUI extends AbstractCompanyVoucherNumEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CompanyVoucherNumEditUI.class);
    
    private String ENTY_VOUCHERNUMBER = "voucherNumber";
    private KDTEditAdapter bankEntryEditListener;
    private DataChangeListener prmtCompanyListener;
    
    FilterInfo filter = null;
    
	JButton btnAddRuleNew = null;
	JButton btnRemoveRuleNew = null;
	
	CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
	
    /**
     * output class constructor
     */
    public CompanyVoucherNumEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    @Override
	public void loadFields() {
		kdtEntry.checkParsed();
		super.loadFields();
	}
    
    @Override
	public void onLoad() throws Exception {
		super.onLoad();
		
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionCopy.setVisible(false);
    	actionSave.setVisible(false);
    	actionFirst.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionLast.setVisible(false);
    	
    	this.kDContainer1.setTitleStyle(2);
		this.kDContainer1.setEnableActive(false);
		
		if(btnAddRuleNew==null){
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
		}
		
		if (this.oprtState.equals(OprtState.VIEW)) {
			btnAddRuleNew.setEnabled(false);
			btnRemoveRuleNew.setEnabled(false);
		}
		
		//新增时默认当前财务组织
		if(this.oprtState.equals(OprtState.ADDNEW)){
			prmtCompanyNumber.setValue(company);
			txtCompanyName.setText(company.getName());
		}
		
		initUI();
	}

	private void initUI() {
		kdtEntry.checkParsed();
		
		prmtCompanyNumber.setEnabled(false);
		txtCompanyName.setEnabled(false);
		
//		kdtEntry.getColumn(ENTY_VOUCHERNUMBER).setRequired(true);
		
		prmtCompanyNumber.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyQuery");
		prmtCompanyNumber.setVisible(true);
		prmtCompanyNumber.setEditable(true);
		prmtCompanyNumber.setRequired(true);
		prmtCompanyNumber.setDisplayFormat("$number$");
		prmtCompanyNumber.setEditFormat("$number$");
		prmtCompanyNumber.setCommitFormat("$number$");
		
		
	}

	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(prmtCompanyNumber.getValue()==null){
			MsgBox.showWarning("财务组织编码不能为空！");
			prmtCompanyNumber.requestFocus();
			abort();
		}
		if(!(kdtEntry.getRowCount()>0)){
			MsgBox.showWarning("对应凭证字至少得有一条分录！");
			abort();
		}else{
			for (int i = 0; i < kdtEntry.getRowCount(); i++) {
				if(kdtEntry.getCell(i, ENTY_VOUCHERNUMBER).getValue()==null){
					MsgBox.showInfo(this,"凭证字不能为空！");
					kdtEntry.getEditManager().editCellAt(i, kdtEntry.getColumnIndex(ENTY_VOUCHERNUMBER));
					abort();
				}
			}
		}
		CompanyOrgUnitInfo compUnitInfo   = (CompanyOrgUnitInfo) prmtCompanyNumber.getValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyNumber.id",compUnitInfo.getId().toString()));
		
		if(this.oprtState.equals(OprtState.EDIT)){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
			filter.setMaskString("#0 and #1");
		}
		
		ICompanyVoucherNum iVoucherNum = CompanyVoucherNumFactory.getRemoteInstance();
		
		if(iVoucherNum.exists(filter)){
			MsgBox.showInfo(this,"已存在该公司的凭证字！");
			abort();
		}
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		
		bankEntryEditListener = new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
    			bankEntryEditStoped(e);
    		}
		};
		
		kdtEntry.addKDTEditListener(bankEntryEditListener);
		
		prmtCompanyListener = new DataChangeListener(){

			@Override
			public void dataChanged(DataChangeEvent e) {
				prmtCompanyDataChanged(e);
			}};
			
		prmtCompanyNumber.addDataChangeListener(prmtCompanyListener);	
			
	}
    
    protected void prmtCompanyDataChanged(DataChangeEvent e) {
    	CompanyOrgUnitInfo newInfo = null;
    	CompanyOrgUnitInfo oldInfo = null;
    	
    	newInfo = (CompanyOrgUnitInfo) e.getNewValue();
    	oldInfo = (CompanyOrgUnitInfo) e.getOldValue();
    	
    	if((newInfo==null && oldInfo==null) || (newInfo!=null && newInfo.equals(oldInfo))){
	   		 return ;
	   	}
    	txtCompanyName.setText(newInfo.getName());
	}

	protected void bankEntryEditStoped(KDTEditEvent e) {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		String newStr = null;
		String oldStr = null;
		String compStr = null;
		
		if(ENTY_VOUCHERNUMBER.equals(kdtEntry.getColumnKey(colIndex))){
			newStr =  (String) e.getValue();
			oldStr = (String) e.getOldValue();
			
			if(!StringUtils.isEmpty(newStr) && !(newStr.equals(oldStr))){
				for(int i =0;i<this.kdtEntry.getRowCount();i++){
					if(i != rowIndex){
						compStr = (String) kdtEntry.getCell(i, colIndex).getValue();
			            if(compStr !=null){
			            	if(newStr.equals(compStr)){
			            		if(!StringUtils.isEmpty(oldStr)){
			            			kdtEntry.getCell(rowIndex, colIndex).setValue(oldStr);
			            		}else{
			            			kdtEntry.getCell(rowIndex, colIndex).setValue(null);
			            		}
			            		MsgBox.showInfo("凭证字重复,请重新确认！");
			            		kdtEntry.getEditManager().editCellAt(rowIndex, colIndex);
							}
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

	private IObjectValue createNewDetailData(KDTable kdtEntry) {
		CompanyVoucherNumEntryInfo info = new CompanyVoucherNumEntryInfo();
		return info;
	}
	
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyNumber.id",company.getId().toString()));
		if(!((ICompanyVoucherNum)getBizInterface()).exists(filter)){
			super.actionAddNew_actionPerformed(e);
			prmtCompanyNumber.setValue(company);
			txtCompanyName.setText(company.getName());
		}else{
			MsgBox.showInfo(this,"该财务组织已经存在对应凭证字，无法再次新增！");
			abort();
		}
			
	}
	
	@Override
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		btnAddRuleNew.setEnabled(true);
		btnRemoveRuleNew.setEnabled(true);
		super.actionEdit_actionPerformed(e);
	}
	@Override
	protected IObjectValue createNewData() {
		CompanyVoucherNumInfo info = new CompanyVoucherNumInfo();
		return info;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return CompanyVoucherNumFactory.getRemoteInstance();
	}

}