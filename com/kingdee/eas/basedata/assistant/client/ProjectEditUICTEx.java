package com.kingdee.eas.basedata.assistant.client;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.ManagerListInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.cp.bc.ExcessSetEntryInfo;
import com.kingdee.eas.cp.bc.client.ExcessSetEditUI;
import com.kingdee.eas.ma.budget.BgTemplateInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class ProjectEditUICTEx extends ProjectEditUI {

	private static final Logger logger = CoreUIObject
	.getLogger(ProjectEditUICTEx.class);
	
    KDBizPromptBox kdtEntry_person__PromptBox = null;
	private KDTEditAdapter entryEditListener;
	private String COL_MANAGERNUM = "projectManager";
	private String COL_MANAGERNAME = "managerName";
	
	JButton btnAddRuleNew = null;
	JButton btnRemoveRuleNew = null;
	
	public ProjectEditUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void loadFields() {
		tblProjectManager.checkParsed();
		super.loadFields();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		
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
		
		initUI();
	}
	
	private void initUI() {
		tblProjectManager.checkParsed();
		
		conPrjManager.setVisible(false);
		prmtPrjManager.setSelector(null);
		prmtPrjManager.setQueryInfo(null);
		
		kdtEntry_person__PromptBox = new KDBizPromptBox();
		kdtEntry_person__PromptBox.setRequired(true);
		kdtEntry_person__PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonForBcQuery");
		kdtEntry_person__PromptBox.setDisplayFormat("$number$");
		kdtEntry_person__PromptBox.setCommitFormat("$number$");
		kdtEntry_person__PromptBox.setEditFormat("$number$");
		
		ObjectValueRender kdtEntry_person__OVR = new ObjectValueRender();
		kdtEntry_person__OVR.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat("$number$"));
		
        this.tblProjectManager.getColumn(COL_MANAGERNUM).setEditor(new KDTDefaultCellEditor(kdtEntry_person__PromptBox));
        this.tblProjectManager.getColumn(COL_MANAGERNUM).setRenderer(kdtEntry_person__OVR);
        this.tblProjectManager.getColumn(COL_MANAGERNUM).setRequired(true);
        
        this.tblProjectManager.getColumn(COL_MANAGERNAME).getStyleAttributes().setLocked(true);
	}

	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(!(tblProjectManager.getRowCount()>0)){
			MsgBox.showWarning("项目经理列表至少得有一条分录！");
			abort();
		}else{
			for (int i = 0; i < tblProjectManager.getRowCount(); i++) {
				if(tblProjectManager.getCell(i, COL_MANAGERNUM).getValue()==null){
					MsgBox.showInfo(this,"项目经理编码不能为空！");
					tblProjectManager.getEditManager().editCellAt(i, tblProjectManager.getColumnIndex(COL_MANAGERNUM));
					abort();
				}
			}
		}
		
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		
		entryEditListener = new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
    			entryEditStoped(e);
    		}
		};
		
		tblProjectManager.addKDTEditListener(entryEditListener);
	}
	
	@Override
	public void onShow() throws Exception {
		super.onShow();
		kdtEntry_person__PromptBox.setDisplayFormat("$number$");
	}
	
	protected void entryEditStoped(KDTEditEvent e) {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		PersonInfo newInfo = null;
		PersonInfo oldInfo = null;
		PersonInfo compInfo = null;
		if(COL_MANAGERNUM.equals(tblProjectManager.getColumnKey(colIndex))){
			newInfo = (PersonInfo) e.getValue();
			oldInfo = (PersonInfo) e.getOldValue();
			
			if((newInfo==null && oldInfo==null) || (newInfo!=null && newInfo.equals(oldInfo))){
		   		 return ;
		   	}
			
			for(int i =0;i<this.tblProjectManager.getRowCount();i++){
				if(tblProjectManager.getRowCount()==1){
					tblProjectManager.getCell(rowIndex, COL_MANAGERNAME).setValue(newInfo.getName());
				}
				if(i != rowIndex){
					compInfo = (PersonInfo) tblProjectManager.getCell(i, colIndex).getValue();
		            if(compInfo !=null){
		            	if(newInfo.getId().toString().equals(compInfo.getId().toString())){
		            		if(oldInfo!=null){
		            			tblProjectManager.getCell(rowIndex, colIndex).setValue(oldInfo);
		            		}else{
		            			tblProjectManager.getCell(rowIndex, colIndex).setValue(null);	
		            		}
		            		tblProjectManager.getEditManager().editCellAt(rowIndex, colIndex);
							MsgBox.showInfo(this, "项目经理重复,请重新选择！");
						}else{
							tblProjectManager.getCell(rowIndex, COL_MANAGERNAME).setValue(newInfo.getName());
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
		KDTable table = this.tblProjectManager;
		if (this.tblProjectManager == null) {
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
		IObjectValue detailData = createNewDetailData(this.tblProjectManager);
		if (detailData != null) {
			IRow row = this.tblProjectManager.addRow();
			getUILifeCycleHandler().fireOnAddNewLine(this.tblProjectManager, detailData);
			this.dataBinder.loadLineFields(this.tblProjectManager, row, detailData);
		}
	}

	private IObjectValue createNewDetailData(KDTable kdtEntry) {
		ManagerListInfo entryInfo = new ManagerListInfo();
		return entryInfo;
	}
	
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		btnAddRuleNew.setEnabled(true);
		btnRemoveRuleNew.setEnabled(true);
		super.actionAddNew_actionPerformed(e);
	}
	
	@Override
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		btnAddRuleNew.setEnabled(true);
		btnRemoveRuleNew.setEnabled(true);
		super.actionEdit_actionPerformed(e);
	}
	
	@Override
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("managerList.id"));
		sic.add(new SelectorItemInfo("managerList.projectManage.*"));
    	sic.add(new SelectorItemInfo("managerList.managerName"));
		return sic;
	}
}
