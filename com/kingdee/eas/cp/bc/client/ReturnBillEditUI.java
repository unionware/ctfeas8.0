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

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.IReturnBill;
import com.kingdee.eas.cp.bc.ReturnBillEntryInfo;
import com.kingdee.eas.cp.bc.ReturnBillTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

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
    	btnAudit.setEnabled(true);
    	returnBillType.setVisible(false);
    	kDDateCreateTime.setDatePattern("yyyy-MM-dd");
    	kDDateLastUpdateTime.setDatePattern("yyyy-MM-dd");
    	
    	String id = (String) getUIContext().get("ID");
    	Object obj = getUIContext().get("billInfo");
    	if(StringUtils.isEmpty(id)){
        	if(obj!=null){
        		if(obj instanceof EvectionLoanBillInfo){
            		txtloanBillNumber.setText(((EvectionLoanBillInfo) obj).getNumber());
            		txtloanAmount.setValue(((EvectionLoanBillInfo) obj).getAmountApproved());
            		prmtloanor.setValue(((EvectionLoanBillInfo) obj).getApplier());
            		txtloanBillAmountBalance.setValue(((EvectionLoanBillInfo) obj).getAmountBalance());
            		returnBillType.setSelectedItem(ReturnBillTypeEnum.EVECTIONLOAN);
            		billState.setSelectedItem(null);
            	}else if(obj instanceof DailyLoanBillInfo){
            		txtloanBillNumber.setText(((DailyLoanBillInfo) obj).getNumber());
            		txtloanAmount.setValue(((DailyLoanBillInfo) obj).getAmountApproved());
            		prmtloanor.setValue(((DailyLoanBillInfo) obj).getApplier());
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
        	}
    	}
    	
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
//    	if(txtreturnAmount.getBigDecimalValue().compareTo(txtloanBillAmountBalance.getBigDecimalValue())>0){
//    		MsgBox.showInfo(this, "还款金额必须小于等于借款单可用金额！");
//    		abort();
//    	}
    }
    
    @Override
    protected void doBeforeSubmit(ActionEvent e) throws Exception {
    	super.doBeforeSubmit(e);
//    	if(!(txtreturnAmount.getBigDecimalValue().compareTo(txtloanBillAmountBalance.getBigDecimalValue())==0)){
//    		MsgBox.showInfo(this, "还款金额必须等于借款单可用金额！");
//    		abort();
//    	}
    }
    
    @Override
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
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
        objectValue.setBillState(null);
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