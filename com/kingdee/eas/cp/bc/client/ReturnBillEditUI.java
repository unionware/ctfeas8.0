/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bouncycastle.pqc.math.linearalgebra.BigEndianConversions;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFacade;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.IReturnBill;
import com.kingdee.eas.cp.bc.ReturnBillEntryInfo;
import com.kingdee.eas.cp.bc.ReturnBillTypeEnum;
import com.kingdee.eas.cp.bc.ReturnStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.bos.bi.model.util.StringUtil;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;

/**
 * output class name
 */
public class ReturnBillEditUI extends AbstractReturnBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReturnBillEditUI.class);
    
    private String  COL_RETURNAMOUNT = "returnAmount";
    private String  COL_RETURNDATE = "returnDate";
    
    private KDTEditAdapter editAdapter = null;
    
    /**
     * output class constructor
     */
    public ReturnBillEditUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	returnBillType.setVisible(false);
    	kDDateCreateTime.setDatePattern("yyyy-MM-dd");
    	kDDateLastUpdateTime.setDatePattern("yyyy-MM-dd");
    	
    	this.kdtEntrys.getColumn("returnAmount").setEditor(null);
    	KDFormattedTextField kdtEntrys_returnAmount_TextField = new KDFormattedTextField();
        kdtEntrys_returnAmount_TextField.setName("kdtEntrys_returnAmount_TextField");
        kdtEntrys_returnAmount_TextField.setVisible(true);
        kdtEntrys_returnAmount_TextField.setEditable(true);
        kdtEntrys_returnAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_returnAmount_TextField.setDataType(1);
        	kdtEntrys_returnAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_returnAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_returnAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_returnAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_returnAmount_TextField);
        this.kdtEntrys.getColumn("returnAmount").setEditor(kdtEntrys_returnAmount_CellEditor);
        
    	String id = (String) getUIContext().get("ID");
    	Object obj = getUIContext().get("billInfo");
    	String returnAct = (String) getUIContext().get("return");
    	if(StringUtils.isEmpty(id)){
        	if(obj!=null){
        		if(obj instanceof EvectionLoanBillInfo){
            		txtloanBillNumber.setText(((EvectionLoanBillInfo) obj).getNumber());
            		txtloanAmount.setValue(((EvectionLoanBillInfo) obj).getAmountApproved());
            		PersonInfo personInfo = ((EvectionLoanBillInfo) obj).getApplier();
            		
            		UserInfo userInfo = getUserInfoByPerson(personInfo);
            		prmtloanor.setValue(userInfo);
            		
            		txtloanBillAmountBalance.setValue(((EvectionLoanBillInfo) obj).getAmountBalance());
            		returnBillType.setSelectedItem(ReturnBillTypeEnum.EVECTIONLOAN);
            		billState.setSelectedItem(null);
            	}else if(obj instanceof DailyLoanBillInfo){
            		txtloanBillNumber.setText(((DailyLoanBillInfo) obj).getNumber());
            		txtloanAmount.setValue(((DailyLoanBillInfo) obj).getAmountApproved());
            		
            		PersonInfo personInfo = ((DailyLoanBillInfo) obj).getApplier();
            		UserInfo userInfo = getUserInfoByPerson(personInfo);
            		
            		prmtloanor.setValue(userInfo);
            		txtloanBillAmountBalance.setValue(((DailyLoanBillInfo) obj).getAmountBalance());
            		returnBillType.setSelectedItem(ReturnBillTypeEnum.DAILYLOAN);
            		billState.setSelectedItem(null);
            	}
        	}
    	}else{
    		if(obj!=null){
        		if(obj instanceof EvectionLoanBillInfo){
            		txtloanBillAmountBalance.setValue(((EvectionLoanBillInfo) obj).getAmountBalance());
            	}else if(obj instanceof DailyLoanBillInfo){
            		txtloanBillAmountBalance.setValue(((DailyLoanBillInfo) obj).getAmountBalance());
            	}
        		if(ReturnStateEnum.SUBMITEDPAID_VALUE.equals(returnAct)){
        			actionAudit.setEnabled(true);
        			actionEdit.setEnabled(false);
        		}else{
        			actionAudit.setEnabled(false);
        		}
        	}
    	}
    	
    	if(getOprtState().equals(OprtState.VIEW)){
    		if(editData.getBillState()!=null){
    			String state = editData.getBillState().toString();
    			if(ReturnStateEnum.COMFIRMPAID.getAlias().equals(state)){
    				actionAudit.setEnabled(false);
        			actionEdit.setEnabled(false);
        			actionSave.setEnabled(false);
        			actionSubmit.setEnabled(false);
    			}
    		}
    		
    	}
    	
    }
    
    private UserInfo getUserInfoByPerson(PersonInfo personInfo) throws BOSException {
    	UserInfo userInfo = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("person.id",personInfo.getId().toString()));
		view.setFilter(filter);
		UserCollection userCol = UserFactory.getRemoteInstance().getUserCollection(view);
		if(userCol!=null && userCol.size()>0){
			userInfo = userCol.get(0);
		}
		return userInfo;
	}

	@Override
    protected void initWorkButton() {
    	super.initWorkButton();
    	actionAddNew.setVisible(false);
    	actionFirst.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionLast.setVisible(false);
    	actionCreateTo.setVisible(false);
    	actionCreateFrom.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionMultiapprove.setVisible(false);
    	actionNextPerson.setVisible(false);
    	actionCopy.setVisible(false);
    	actionWorkFlowG.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionAttachment.setVisible(false);
    }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    @Override
    protected void initListener() {
    	super.initListener();
    	if(editAdapter == null){
    		editAdapter =  new KDTEditAdapter(){
    			public void editStopped(KDTEditEvent e) {
        			entryEditStoped(e);
        		}
    		};
    	}
    	kdtEntrys.addKDTEditListener(editAdapter);
    }
    
    protected void entryEditStoped(KDTEditEvent e) {
    	int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		BigDecimal oldValue = null;
		BigDecimal newValue = null;
		
		if(COL_RETURNAMOUNT.equals(kdtEntrys.getColumnKey(colIndex))){
			newValue = e.getValue()==null?BigDecimal.ZERO:(BigDecimal)e.getValue();
			oldValue = e.getOldValue()==null?BigDecimal.ZERO:(BigDecimal)e.getOldValue();
			
			if((newValue.compareTo(BigDecimal.ZERO)>0) ){
				if(!(newValue.compareTo(oldValue)==0)){
					BigDecimal returnAmount = txtreturnAmount.getBigDecimalValue();
					txtreturnAmount.setValue(returnAmount.add(newValue.subtract(oldValue)));
				}
			}else{
				MsgBox.showInfo("还款金额必须大于0！");
				kdtEntrys.getCell(rowIndex, COL_RETURNAMOUNT).setValue(oldValue);
			}
		}
	}
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	int n = kdtEntrys.getRowCount();
    	if(n==0){
    		MsgBox.showInfo(this, "至少需要一条分录！");
    		abort();
    	}else{
    		for (int i = 0; i < n; i++) {
				if(kdtEntrys.getCell(i, COL_RETURNDATE).getValue()==null){
					MsgBox.showInfo(this, "还款时间不能为空！");
		    		abort();
				}
				if(kdtEntrys.getCell(i, COL_RETURNAMOUNT).getValue()==null){
					MsgBox.showInfo(this, "还款金额不能为空！");
		    		abort();
				}else{
					BigDecimal returnAmt = (BigDecimal) kdtEntrys.getCell(i, COL_RETURNAMOUNT).getValue();
					if(!(returnAmt.compareTo(BigDecimal.ZERO)>0)){
						MsgBox.showInfo("还款金额必须大于0！");
						abort();
					}
				}
			}
    	}
    }
    
    @Override
    protected void doBeforeSave(ActionEvent e) throws Exception {
    	super.doBeforeSave(e);
    	if(txtreturnAmount.getBigDecimalValue().compareTo(txtloanBillAmountBalance.getBigDecimalValue())>0){
    		MsgBox.showInfo(this, "还款金额必须小于等于借款单可用金额！");
    		abort();
    	}
    }
    
    @Override
    protected void doBeforeSubmit(ActionEvent e) throws Exception {
    	super.doBeforeSubmit(e);
    	if(!(txtreturnAmount.getBigDecimalValue().compareTo(txtloanBillAmountBalance.getBigDecimalValue())==0)){
    		MsgBox.showInfo(this, "还款金额必须等于借款单可用金额！");
    		abort();
    	}
    }
    
    @Override
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkCanAudit(ReturnStateEnum.SUBMITEDPAID);
    	String id = getSelectBOID();
    	if (id != null) {
			int rev = MsgBox.showConfirm2(this, "确定审核吗?");
			if(rev==MsgBox.YES){
				storeFields();
				getBizInterface().update(new ObjectUuidPK(editData.getId().toString()), editData);
				((IReturnBill)getBizInterface()).audit(editData);
				MsgBox.showInfo(this,"审核成功!");
				syncDataFromDB();
				handleOldData();
			}
		}
    }
    
    private void checkCanAudit(ReturnStateEnum returnStateEnum) {
    	boolean b = (editData != null)
		&& (editData.getBillState() != null)
		&& (editData.getBillState().equals(returnStateEnum));

		if (!b) {
			MsgBox.showWarning(this, "已提交还款状态的还款单才能审核！");
			SysUtil.abort();
		}
	}

	@Override
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if (getOprtState().equals("VIEW")) {
			checkCanEdit();
		}
    	super.actionEdit_actionPerformed(e);
    }

    protected void checkCanEdit() throws Exception {
		if ((this.editData == null) || (this.editData.getId() == null)) {
			SysUtil.abort();
		} else {
			ReturnStateEnum baseStatus = this.editData.getBillState();
			if (baseStatus != null) {
				if (!(ReturnStateEnum.TEMPSAVE.equals(baseStatus))) {
					MsgBox.showError(this, "" + baseStatus.getAlias() + "单据不能修改！");
					SysUtil.abort();
				}
			}
		}
	}
    
    @Override
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeRemove();
    	super.actionRemove_actionPerformed(e);
    }
    
    protected void checkBeforeRemove() {
    	ReturnStateEnum state = this.editData.getBillState();
		if ((state != null)
				&& (!(ReturnStateEnum.TEMPSAVE.equals(state)))) {
			MsgBox.showWarning(this, state.getAlias()+"单据不能删除！");
			SysUtil.abort();
		}
	}
    
	protected void handleOldData() {
		storeFields();
		initOldData(this.editData);
	}

	protected void syncDataFromDB() throws Exception {
		if (getUIContext().get("ID") == null) {
			String s = EASResource
					.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_IDIsNull");
			MsgBox.showError(s);
			SysUtil.abort();
		}
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get("ID")
				.toString()));
		setDataObject(getValue(pk));
		loadFields();
	}
	
	/**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.ReturnBillFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		ReturnBillEntryInfo entryInfo = new ReturnBillEntryInfo();
        return entryInfo;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.ReturnBillInfo objectValue = new com.kingdee.eas.cp.bc.ReturnBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setCreateTime(new Timestamp((new Date()).getTime()));
        return objectValue;
    }

    @Override
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add(new SelectorItemInfo("loanor.id"));
    	sic.add(new SelectorItemInfo("loanor.number"));
    	sic.add(new SelectorItemInfo("loanor.name"));
    	return sic;
    }
}