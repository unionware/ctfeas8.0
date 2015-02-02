package com.kingdee.eas.ma.budget.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.ma.budget.BgAdjustTypeEnum;
import com.kingdee.eas.ma.budget.BgAvlBalCalFacade;
import com.kingdee.eas.ma.budget.BgAvlBalCalFacadeFactory;
import com.kingdee.eas.ma.budget.BgElementInfo;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.eas.ma.budget.VisualItemInfo;
import com.kingdee.eas.util.client.MsgBox;

public class BgAdjustBillEditUICTEx extends BgAdjustBillEditUI {
	
	
	protected String CURRENCYCOLINDEX = "currency";//币别
	protected String ELECOLINDEX = "bgElement";//预算要素
	protected String PERIODCOLINDEX = "bgPeriod";//预算期间
	protected String ITEMCOLINDEX = "bgItem.name";//预算项目
	protected String USEABLECOLINDEX = "bgUsableAmt";
	protected String USEACTAMTLINDEX = "bgActualAmt";
	protected String BEFORECOLINDEX = "adjustBefore";
	protected String APPLYCOLINDEX = "adjustApply";
	protected String TYPECOLINDEX = "adjustType";//
	
	
	private KDTEditAdapter entryEditListener;
	
	public BgAdjustBillEditUICTEx() throws Exception {
		super();
	}
	
	@Override
	protected void initSystemInfo() throws EASBizException, BOSException {
		super.initSystemInfo();
		
		KDFormattedTextField formattedTextField = null;
		formattedTextField = new KDFormattedTextField();
        formattedTextField.setDataType(1);
        formattedTextField.setPrecision(10);
        formattedTextField.setMaximumValue(GlUtils.maxBigDecimal);
        formattedTextField.setMinimumValue(GlUtils.minBigDecimal);
        
        kdtEntry.getColumn(USEABLECOLINDEX).setEditor(new KDTDefaultCellEditor(formattedTextField));
        kdtEntry.getColumn(USEABLECOLINDEX).getStyleAttributes().setLocked(true);
        
        kdtEntry.getColumn(USEACTAMTLINDEX).setEditor(new KDTDefaultCellEditor(formattedTextField));
        kdtEntry.getColumn(USEACTAMTLINDEX).getStyleAttributes().setLocked(true);
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
		
		if(CURRENCYCOLINDEX.equals(this.kdtEntry.getColumnKey(colIndex))||
			ELECOLINDEX.equals(this.kdtEntry.getColumnKey(colIndex))||
			PERIODCOLINDEX.equals(this.kdtEntry.getColumnKey(colIndex))||
			ITEMCOLINDEX.equals(this.kdtEntry.getColumnKey(colIndex))){
			
			FullOrgUnitInfo orgUnit = (FullOrgUnitInfo) prmtOrgUnit.getValue(); //组织单元
	    	BgSchemeInfo bgScheme = (BgSchemeInfo) prmtBgScheme.getValue(); //预算方案
			CurrencyInfo currency = (CurrencyInfo) kdtEntry.getCell(rowIndex, CURRENCYCOLINDEX).getValue();
			BgElementInfo bgElement = (BgElementInfo) this.kdtEntry.getCell(rowIndex, ELECOLINDEX).getValue();
			BgPeriodInfo periodInfo = (BgPeriodInfo) this.kdtEntry.getCell(rowIndex, PERIODCOLINDEX).getValue();
			VisualItemInfo visualItem = (VisualItemInfo) this.kdtEntry.getCell(rowIndex, ITEMCOLINDEX).getValue();
			
			
			if(currency!=null && bgElement!=null && periodInfo!=null && visualItem!=null){
				
				Map param = new HashMap();
		    	
		    	int precision = currency.getPrecision();
		    	
		    	BgPeriodInfo bgPeriod = (BgPeriodInfo)this.kdtEntry.getCell(rowIndex, PERIODCOLINDEX).getValue(); //预算期间
		    	
		    	param.put("orgUnit", orgUnit);
		    	param.put("bgScheme", bgScheme);
		    	param.put("bgElement", bgElement);
		    	param.put("bgItemNumbers", visualItem.getNumber());
		    	param.put("currency", currency);
		    	param.put("bgPeriod", bgPeriod);
		    	
		    	try {
		    		//预算可用余额
					BigDecimal useableAmt = BgAvlBalCalFacadeFactory.getRemoteInstance().calAvlBalCalAmt(param);
					this.kdtEntry.getCell(rowIndex, USEABLECOLINDEX).setValue(useableAmt.setScale(precision, 4));
					
					//预算实际发生数
					BigDecimal actualAmt = BgAvlBalCalFacadeFactory.getRemoteInstance().calActualAmt(param);
					this.kdtEntry.getCell(rowIndex, USEACTAMTLINDEX).setValue(actualAmt.setScale(precision, 4));
				} catch (Exception e1) {
					handUIException(e1);
				} 
			}
		}
		
	}

	@Override
	protected void doBeforeSave(ActionEvent e) throws Exception {
		super.doBeforeSave(e);
		addVerifyCtrol();
	}
	
	@Override
	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		super.doBeforeSubmit(e);
		addVerifyCtrol();
	}
	
	private void addVerifyCtrol(){
		for(int i=0;i<kdtEntry.getRowCount();i++){
			String type = kdtEntry.getCell(i, TYPECOLINDEX).getValue().toString();
			BigDecimal apply = (BigDecimal) (kdtEntry.getCell(i, APPLYCOLINDEX).getValue()==null?BigDecimal.ZERO:kdtEntry.getCell(i, APPLYCOLINDEX).getValue());
			BigDecimal before = (BigDecimal) (kdtEntry.getCell(i, BEFORECOLINDEX).getValue()==null?BigDecimal.ZERO:kdtEntry.getCell(i, BEFORECOLINDEX).getValue());
			BigDecimal useable = (BigDecimal) (kdtEntry.getCell(i, USEABLECOLINDEX).getValue()==null?BigDecimal.ZERO:kdtEntry.getCell(i, USEABLECOLINDEX).getValue());
			if(BgAdjustTypeEnum.VALUEFASTNESS.getAlias().equals(type) && apply.compareTo(BigDecimal.ZERO)<0){
				if((BigDecimal.ZERO.subtract(apply)).compareTo(useable)>0 ||(BigDecimal.ZERO.subtract(apply)).compareTo(before)>0){
					kdtEntry.getEditManager().editCellAt(i, kdtEntry.getCell(i, APPLYCOLINDEX).getColumnIndex());
					MsgBox.showWarning(this, "第"+(i+1)+"行申请值的绝对值必须小于等于预算可用余额，且小于等于调整前金额！");
					abort();
				}
			}
			
		}
	}
}
