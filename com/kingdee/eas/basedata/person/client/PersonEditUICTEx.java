package com.kingdee.eas.basedata.person.client;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.extendcontrols.IParser;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.person.BankInfoInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fm.be.BEBankInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class PersonEditUICTEx extends PersonEditUI {

	private static final Logger logger = CoreUIObject
	.getLogger(PersonEditUICTEx.class);
	
	final KDBizPromptBox kdtEntry_bank__PromptBox = new KDBizPromptBox();
	
	private KDTEditAdapter bankEntryEditListener;
	
	
	private String BANKENTRY_BANKNAME = "bandName";
	private String BANKENTRY_BANKNUMBER = "bandAcctNumber";
	private String BANKENTRY_CITY = "city";
	boolean b = false;
	
	JButton btnAddRuleNew = null;
	JButton btnRemoveRuleNew = null;
    
	public PersonEditUICTEx() throws Exception {
		super();
	}

	@Override
	public void loadFields() {
		tblBankList.checkParsed();
		super.loadFields();
	}
	
	@Override
	public void storeFields() {
		super.storeFields();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		
		btnAddAssign.setText("新增负责职位");
		btnDelAssign.setText("删除负责职位");
		
		if(btnAddRuleNew==null){
			btnAddRuleNew = this.kDContainer2.add(this.actionAddLine);
			btnRemoveRuleNew = this.kDContainer2.add(this.actionRemoveLine);
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
		
		initUI();
	}

	private void initUI() {
		tblBankList.checkParsed();
		
		//文本
		KDTextField txtForAddressField = new KDTextField();
		txtForAddressField.setMaxLength(255);
		KDTDefaultCellEditor kdtAddressCellEditor = new KDTDefaultCellEditor(txtForAddressField);
		this.tblBankList.getColumn(BANKENTRY_BANKNAME).setEditor(kdtAddressCellEditor);
		
		//F7
		kdtEntry_bank__PromptBox.setQueryInfo("com.kingdee.eas.fm.be.app.F7BEBankQuery");
		kdtEntry_bank__PromptBox.setVisible(true);
		kdtEntry_bank__PromptBox.setEditable(true);
		kdtEntry_bank__PromptBox.setRequired(true);
		kdtEntry_bank__PromptBox.setDisplayFormat("$name$");
		kdtEntry_bank__PromptBox.setEditFormat("$number$");
		kdtEntry_bank__PromptBox.setCommitFormat("$number$");
		IParser parser = new ExtendParser(kdtEntry_bank__PromptBox);
		kdtEntry_bank__PromptBox.setCommitParser(parser);
        KDTDefaultCellEditor kdtEntry_bank__cellEditor = new KDTDefaultCellEditor(kdtEntry_bank__PromptBox);
        this.tblBankList.getColumn(BANKENTRY_BANKNAME).setEditor(kdtEntry_bank__cellEditor);
        
        this.tblBankList.getColumn(BANKENTRY_CITY).setRequired(true);
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		bankEntryEditListener = new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
    			bankEntryEditStoped(e);
    		}
		};
		
		tblBankList.addKDTEditListener(bankEntryEditListener);
	}
	
	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(tblBankList.getRowCount()>0){
				if(!b){
					for (int i = 0; i <tblBankList.getRowCount(); i++) {
						if(tblBankList.getCell(i, BANKENTRY_BANKNUMBER).getValue()==null){
							MsgBox.showInfo(this,"银行账号不能为空！");
							tblBankList.getEditManager().editCellAt(i, tblBankList.getColumnIndex(BANKENTRY_BANKNUMBER));
							abort();
						
						}
						if(tblBankList.getCell(i, BANKENTRY_CITY).getValue()==null){
							MsgBox.showInfo(this,"市不能为空！");
							tblBankList.getEditManager().editCellAt(i, tblBankList.getColumnIndex(BANKENTRY_CITY));
							abort();
						}
					}
				}else{
					b = false;
					abort();
				}
		}
	}
	
	protected void bankEntryEditStoped(KDTEditEvent e) {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		String newStr = null;
		String oldStr = null;
		String compStr = null;
		
//		BEBankInfo bankInfo = null;
//		
//		if(BANKENTRY_BANKNAME.equals(tblBankList.getColumnKey(colIndex))){
//			bankInfo = (BEBankInfo) e.getValue();
//			tblBankList.getCell(rowIndex, BANKENTRY_PROVINCE).setValue(bankInfo.getBeProvince());
//			tblBankList.getCell(rowIndex, BANKENTRY_CITY).setValue(bankInfo.getBeCity());
//		}
		
		if(BANKENTRY_BANKNUMBER.equals(tblBankList.getColumnKey(colIndex))){
			newStr =  (String) e.getValue();
			oldStr = (String) e.getOldValue();
			
			if(!StringUtils.isEmpty(newStr) && !(newStr.equals(oldStr))){
				for(int i =0;i<this.tblBankList.getRowCount();i++){
					if(i != rowIndex){
						compStr = (String) tblBankList.getCell(i, colIndex).getValue();
			            if(compStr !=null){
			            	if(newStr.equals(compStr)){
			            		if(!StringUtils.isEmpty(oldStr)){
			            			tblBankList.getCell(rowIndex, colIndex).setValue(oldStr);
			            		}else{
			            			tblBankList.getCell(rowIndex, colIndex).setValue(null);
			            		}
			            		MsgBox.showInfo("银行账号重复,请重新确认！");
			            		tblBankList.getEditManager().editCellAt(rowIndex, colIndex);
			            		b = true;
							}
			            }
					}
			   }
			}
		}
	}

	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		btnAddRuleNew.setEnabled(true);
		btnRemoveRuleNew.setEnabled(true);
		super.actionAddNew_actionPerformed(e);
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
		KDTable table = this.tblBankList;
		if (this.tblBankList == null) {
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
		IObjectValue detailData = createNewDetailData(this.tblBankList);
		if (detailData != null) {
			IRow row = this.tblBankList.addRow();
			getUILifeCycleHandler().fireOnAddNewLine(this.tblBankList, detailData);
			this.dataBinder.loadLineFields(this.tblBankList, row, detailData);
		}
	}

	private IObjectValue createNewDetailData(KDTable tblBankList) {
		BankInfoInfo info = new BankInfoInfo();
		return info;
	}
	
	@Override
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		btnAddRuleNew.setEnabled(true);
		btnRemoveRuleNew.setEnabled(true);
		super.actionEdit_actionPerformed(e);
	}
	
}
