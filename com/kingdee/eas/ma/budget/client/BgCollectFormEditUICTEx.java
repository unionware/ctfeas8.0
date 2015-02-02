package com.kingdee.eas.ma.budget.client;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.SwingUtilities;

import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.CellBlock;
import com.kingdee.bos.ctrl.excel.model.struct.Range;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gr.cslrpt.ItemFormula;
import com.kingdee.eas.fi.rpt.util.IOHelper;
import com.kingdee.eas.ma.budget.BgAdjustFormDiversityData;
import com.kingdee.eas.ma.budget.BgConstants;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.nbudget.BgNConstants;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.client.BgNFCHelper;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class BgCollectFormEditUICTEx extends BgCollectFormEditUI {

	private String CELL_KEY = "cell_expr_key_col";
	private boolean isLockedExprCell = false;
	protected Vector costOrgUnits = null;
	private Map paramValue = null;

	private Book colBook = null;
	private byte[] bookByte = null;
	private String sheetId = null;

	private boolean isChanged = false;
	private Map oldData = null;
	private HashMap otherData = null;

	public BgCollectFormEditUICTEx() throws Exception {
		super();
	}

	@Override
	public void loadFields() {
		super.loadFields();
		try {
			this.otherData = BgHelper.buildHashMapByByte(this.editData.getOtherCont());
			loadOldData();
		} catch (Exception e) {
			handUIException(e);
		} 
		
	}
	
	private void loadOldData() throws Exception {
		if(oldData != null && !oldData.isEmpty()){
			return;
		}else{
			if(otherData != null && otherData.get("oldData") != null)
				oldData = BgHelper.buildHashMapByByte((byte[])otherData.get("oldData"));
		}		
	}
	
	@Override
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnModifyBgRate.setIcon(EASResource.getIcon("imgTbtn_compute"));
	}
	
	@Override
	public void onLoad() throws Exception {
		this.oldData = new HashMap();
		this.otherData = new HashMap();
		this.paramValue = BgNSHelper.getAllParamValue();
		this.isLockedExprCell = BgNSHelper.isLockedOfOpen(this.paramValue);
		super.onLoad();
		if (getOprtState().equals(OprtState.VIEW)) {
			this.actionModifyBgRate.setEnabled(false);
			this.actionModifyBgRate.setVisible(false);
		}else{
			this.actionModifyBgRate.setEnabled(true);
			this.actionModifyBgRate.setVisible(true);
		}
	}

	@Override
	public void actionModifyBgRate_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionModifyBgRate_actionPerformed(e);

		if(costOrgUnits == null){
			costOrgUnits = new Vector();
			CostCenterOrgUnitInfo cCOrginfo = null;
			costOrgUnits.add(defOrgUnitInfo);
			for(int i = 0; i < editData.getRefBgForms().size(); i++){
				cCOrginfo = getOrgUnitInterface().getCostCenterOrgUnitInfo(new ObjectUuidPK(editData.getRefBgForms().get(i).getBgForm().getOrgUnit().getId()));
				costOrgUnits.add(cCOrginfo);
			}
		}
		//将选择区域的项目公式加组织当成key，数值为value，并记录选择的列号； 并取出他们共同组织的最高级
		String perantLNum = null;		
		//存放以组织为key的map，value为map
		Map itemsMap = new HashMap();
		itemMapToShow = new HashMap();
		colMapToShow = new HashMap();
		String colIndex = "";
		Sheet table =  getSheetPage();
		Range range = table.getSelectionRange();
		int maxRow = table.getMaxRowIndex();
		int maxCol = table.getMaxColIndex();
		boolean canSetCoefficient = true; 
		for (int i = 0; i < range.size(); i++) {
			CellBlock block = (CellBlock) range.getBlock(i);
			int rowBegin = block.getRow()>maxRow?maxRow:block.getRow();
			int rowEnd = block.getRow2()>maxRow?maxRow:block.getRow2();
			int colBegin = block.getCol()>maxCol?maxCol:block.getCol();
			int colEnd = block.getCol2()>maxCol?maxCol:block.getCol2();
			for (int j = rowBegin; j <= rowEnd; j++) {
				for (int k = colBegin; k <= colEnd; k++) {
					Cell cell = table.getCell(j, k, true);
					if (BgNFSHelper.checkHasFormulaOfCell(cell)) {	
						//找到组织的最上层（出现选择同级的将上级带出）
						Cell perCell = table.getCell(j , ((Integer)expandColIndex.get(table.getID())).intValue(), true);								
						if(perCell.getUserObject(BgNConstants.NEName) == null || (com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(perCell)).isLeaf())
							continue;
						if(perCell.getUserObject(CELL_KEY) != null && isLockedExprCell){
							canSetCoefficient = false;
							continue;
						}
						String lNum = (com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(perCell)).getLongNumber();
						if(perantLNum == null || (perantLNum.split("!") != null && lNum.split("!") != null 
								&& perantLNum.split("!").length > lNum.split("!").length)){
							perantLNum = lNum;
						}else if(perantLNum == null || (perantLNum.split("!") != null && lNum.split("!") != null 
								&& perantLNum.split("!").length == lNum.split("!").length) && !perantLNum.equals(lNum) && perantLNum.lastIndexOf("!") > 1){
							perantLNum = perantLNum.substring(0, perantLNum.lastIndexOf("!")-1);
						}							
						ItemFormula itemFormula = BgNFSHelper.getItemFormula(cell);							
						itemsMap.put(itemFormula.getFormula(),null);
						itemMapToShow.put(itemFormula.getFormula(), null);
						colMapToShow.put(new Integer(k), null);
						colIndex = colIndex + k + "#";
					}
				}					
			}
		}
		if(!canSetCoefficient && (perantLNum == null || perantLNum.split("!") == null)){
			MsgBox.showInfo("所选单元格无法对下级进行调整预算比例，请重新选择！");
			return;
		}
		if(perantLNum == null || perantLNum.split("!") == null ){
			//|| orgUnitLNum <= perantLNum.split("!").length
			MsgBox.showInfo("所选择的单元格无法对下级进行进行调整预算比例，请重新选择！");
			return;
		}
		if(itemsMap.size() < 1){
			MsgBox.showInfo("所选区域内的组织均无下级组织，无法进行调整预算比例，请重新选择！");
			return;
		}	
		
		//将需要的信息保存到uicontext
		UIContext uiContext = new UIContext(this);	
		uiContext.put("orgUnits", costOrgUnits);
		if(editData.getBgForm().getBgScheme() == null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("name"));		        
			sic.add(new SelectorItemInfo("bgScheme.*")); 
			sic.add(new SelectorItemInfo("bgTemplate.*"));		        
			BgFormInfo info = getFormInterface().getBgFormInfo(new ObjectUuidPK(editData.getBgForm().getId()), sic);
			uiContext.put("bgScheme",info.getBgScheme());
			uiContext.put("bgForm",info);
		}else{
			uiContext.put("bgScheme",editData.getBgForm().getBgScheme());
			uiContext.put("bgForm",editData.getBgForm());
		}
		
		uiContext.put("perantLNum", perantLNum);
		uiContext.put("bgFormMap", bgFormMap);
		//uiContext.put("itemMap", itemMap);
		uiContext.put("refOrgUnitCol", refOrgUnitCol);
		uiContext.put("haveDataOrg",haveDataOrg);
		uiContext.put("UI_Title","预算比例调整设置");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(getBgModifyFormDataUI(), uiContext, null, OprtState.EDIT);
		
		uiWindow.show();
		setMap(uiWindow, colIndex, table, itemsMap);
		
		Map orgProportion = ((BgModifyFormDataUI) (uiWindow).getUIObject()).getData();
		if(orgProportion == null){
			return ;
		}
		colBook = getCurrentPage();
		bookByte = IOHelper.packBook(colBook);
		sheetId = getSheetPage().getID();
		Window win = SwingUtilities.getWindowAncestor(this);
		LongTimeDialog dialog =null;
		if(win instanceof UIModelDialog)
			dialog = new LongTimeDialog((UIModelDialog)SwingUtilities.getWindowAncestor(this));
		else if(win instanceof UINewFrame)
			dialog = new LongTimeDialog((UINewFrame)SwingUtilities.getWindowAncestor(this));
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
//				存放组织+项目的key，用来获取分解比例
				Map tMap = getMap();
				List formProportion = ((BgModifyFormDataUI) ((IUIWindow) tMap.get("uiWindow")).getUIObject()).getFormProportion();
				List parentList = ((BgModifyFormDataUI) ((IUIWindow) tMap.get("uiWindow")).getUIObject()).getParentList();
    			if(formProportion == null){
    				return null;
    			}   			

				//循环表格，将根据返回的比例进行数据反添
    			decTableWithProportionNew((Map)tMap.get("itemsMap"),formProportion,parentList);
    			updateCellWithDecProportion();
    			return null;
			}
			
			public void afterExec(Object result) throws Exception {}
		});
		try{
			dialog.show();
		}catch (Exception ee){
			handleException(ee);
		}

		itemMapToShow = null;
		colMapToShow = null;
	}

	private String getBgModifyFormDataUI() {
		return BgModifyFormDataUI.class.getName();
	}

	private void setMap(IUIWindow uiWindow, String colIndex, Sheet table,
			Map itemsMap) {
		this.timeMap = new HashMap();
		this.timeMap.put("uiWindow", uiWindow);
		this.timeMap.put("colIndex", colIndex);
		this.timeMap.put("table", table);
		this.timeMap.put("itemsMap", itemsMap);
	}

	private Map getMap() {
		return this.timeMap;
	}

	
	private void decTableWithProportionNew(Map itemsMap, List formProportion, List parentList) {
		if(itemsMap == null || formProportion == null){
			return;
		}
		Object[] obj = null;
		String formId = null;
		String parentFormId = null;
		String topFormID = null;
		BigDecimal proportion = null;
		Map childAdjMap = null;
		Map parentAdjMap = null;
		String formulaString = null;
		BgAdjustFormDiversityData divInfo = null, parentDivInfo = null;;
		BigDecimal oldValue = null, newValue = null, parentNewValue = null, parentOldValue = null;
		for(Iterator it = formProportion.iterator(); it.hasNext(); ){
			obj = (Object[]) it.next();
			if(obj == null)
				continue;
			formId = (String) obj[0];
			parentFormId = (String) obj[1];
			topFormID = (String) obj[2];
			proportion = (BigDecimal) obj[3];
			try {
				childAdjMap = getAdjustMap(BOSUuid.read(formId));
				parentAdjMap = getAdjustMap(BOSUuid.read(topFormID));
			} catch (Exception ex) {
				childAdjMap = new HashMap();
				parentAdjMap = new HashMap();
			}
			
			for(Iterator it2 = itemsMap.keySet().iterator(); it2.hasNext(); ){
				formulaString = (String) it2.next();
				if(childAdjMap.containsKey(formulaString)){
					divInfo = (BgAdjustFormDiversityData) childAdjMap.get(formulaString);
					if(divInfo.getOldValue()!=null){
						oldValue = divInfo.getOldValue();
					}else{
						oldValue = BgConstants.BIGZERO;
					}
				} else{
					if(oldData.get(formId + formulaString) != null)
						oldValue = (BigDecimal) oldData.get(formId + formulaString);
					else
						oldValue = BgConstants.BIGZERO;
				}
				
				if(parentAdjMap.containsKey(formulaString)){
					parentDivInfo = (BgAdjustFormDiversityData) parentAdjMap.get(formulaString);
					if(parentDivInfo.getNewValue()!=null){
						parentOldValue = parentDivInfo.getNewValue();
					}else{
						parentOldValue = BgConstants.BIGZERO;
					}
				} else{
					if(oldData.get(topFormID + formulaString) != null)
						parentOldValue = (BigDecimal) oldData.get(topFormID + formulaString);
					else
						parentOldValue = BgConstants.BIGZERO;
				}
				
				if(proportion == null || proportion.intValue()==0){
					newValue = BgConstants.BIGZERO;
				} else{
					proportion = proportion.setScale(4);
					newValue = oldValue.multiply(proportion).divide(new BigDecimal("100"), 4);
				}
				
				if (newValue.compareTo(oldValue) != 0) {
					parentNewValue = parentOldValue.add(newValue.subtract(oldValue));
					if (childAdjMap.containsKey(formulaString)) {
						divInfo = (BgAdjustFormDiversityData) childAdjMap.get(formulaString);
						divInfo.setOldValue(oldValue);
						divInfo.setNewValue(newValue);
						divInfo.setAdjustor(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString());
						divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
						
					} else {
						divInfo = new BgAdjustFormDiversityData();
						divInfo.setFormula(formulaString);
						divInfo.setNewValue(newValue);
						divInfo.setOldValue(oldValue);
						divInfo.setAdjustor(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString());
						divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
						childAdjMap.put(formulaString, divInfo);
					}
					if(!parentList.contains(formId)){
						if (parentAdjMap.containsKey(formulaString)) {
							parentDivInfo = (BgAdjustFormDiversityData) parentAdjMap.get(formulaString);
							parentDivInfo.setOldValue(parentOldValue);
							parentDivInfo.setNewValue(parentNewValue);
							parentDivInfo.setAdjustor(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString());
							parentDivInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
							
						} else {
							parentDivInfo = new BgAdjustFormDiversityData();
							parentDivInfo.setFormula(formulaString);
							parentDivInfo.setNewValue(parentNewValue);
							parentDivInfo.setOldValue(parentOldValue);
							parentDivInfo.setAdjustor(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString());
							parentDivInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
							parentAdjMap.put(formulaString, parentDivInfo);
						}
					}
					
					isChanged = true;
				}
				
			}
			if(!adjustMap.containsKey(BOSUuid.read(formId))){
				adjustMap.put(BOSUuid.read(formId), childAdjMap);
			}
		}
	}

	private void updateCellWithDecProportion() throws Exception {
		Book table =  getCurrentPage();
		Sheet sheet = null;
		for(int n = 0; n < table.getSheetCount(); n++){
			sheet = table.getSheet(n);
			for (int i = 0; i <= sheet.getMaxRowIndex(); i++) {
				for (int j = 0; j <= sheet.getMaxColIndex(); j++) {
					Cell cell = sheet.getCell(i, j, false);

					if (BgNFSHelper.checkHasItemFormula(cell)) {						

						Cell orgCell = sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
						if (orgCell != null && orgCell.getUserObject(BgNConstants.NEName) != null) {							
							String tmpFormId = getExpInfo(cell, BGFORMID);
							if(tmpFormId == null){
								continue;
							}
							Map childAdjustMap = (Map) getAdjustMap(BOSUuid.read(tmpFormId));
							if(childAdjustMap == null){
								continue;
							}
							String formulaString = BgNFSHelper.getFormulaOfCell(cell);
							if (childAdjustMap.containsKey(formulaString) && childAdjustMap.get(formulaString) != null) {
								BgAdjustFormDiversityData divData = (BgAdjustFormDiversityData)childAdjustMap.get(formulaString);
								if(divData.getNewValue()!=null){
									BgNFSHelper.setValue(cell, divData.getNewValue());
								}else if(divData.getOldValue()!=null){
									BgNFSHelper.setValue(cell, divData.getOldValue());
								}else{
									BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
								}
								cellChanged(cell);
								updateCollectValue(sheet, i, j);
							}
						}
					}
				}
			}				
		}
	}
}
