/**
 * output package name
 */
package com.kingdee.eas.ma.budget.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ActionMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class BgModifyFormDataUI extends AbstractBgModifyFormDataUI
{
    private static final Logger logger = CoreUIObject.getLogger(BgModifyFormDataUI.class);
    
    private Vector orgUnits = null;
	private Map orgProportion = null;
	private List formProportion = null;
	private List parentList = null;
	protected NumberExpendRender expendRender = null;
	protected ICostCenterOrgUnit iCostCenterOrgUnit = null;
	protected CostCenterOrgUnitCollection refOrgUnitCol = null;
	protected Map bgFormMap = null;
	protected BigDecimal oldValue = null;
	protected BigDecimal newValue = null;
	private int ORGCOL = 0;
	private int BOOLEANCOL = 1;
	private int VALUECOL = 2;
	private Map haveDataOrg = null;
	private boolean isViewForm = false;
	private Map suspendOrgUnit = null;
	private Map allOrg = new HashMap();
	private boolean isCancel = false;
	private FocusListener modifyRateListener;
	

	public boolean isCancel() {
		return this.isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	/**
	 * output class constructor
	 */
	public BgModifyFormDataUI() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		setUITitle((String) getUIContext().get("UI_Title"));
		this.tblMain.reLayoutAndPaint();
		this.contBgScheme.setEnabled(false);
		this.contBgForm.setEnabled(false);
		this.comBgScheme.addItem(getUIContext().get("bgScheme"));
		this.comBgForm.setValue(getUIContext().get("bgForm"));
		initOrgTree();
		this.refOrgUnitCol = ((CostCenterOrgUnitCollection) getUIContext().get(
				"refOrgUnitCol"));
		this.haveDataOrg = ((Map) getUIContext().get("haveDataOrg"));
		this.bgFormMap = ((Map) getUIContext().get("bgFormMap"));

		if (getUIContext().get("isViewForm") != null) {
			setViewForm(Boolean.parseBoolean(getUIContext().get("isViewForm")
					.toString()));
			dealSuspendOrgUnit(this.refOrgUnitCol, this.haveDataOrg);

			if ((isViewForm()) && (!getSuspendOrgUnit().isEmpty())) {
				int height = getHeight();
				int move = 80;
				setSize(getWidth(), height + move);
				this.kDSeparator2.setLocation(this.kDSeparator2.getX(),
						this.kDSeparator2.getY() + move);
				this.btnConfirm.setLocation(this.btnConfirm.getX(),
						this.btnConfirm.getY() + move);
				this.btnCancel.setLocation(this.btnCancel.getX(),
						this.btnCancel.getY() + move);
				KDTextArea text = new KDTextArea();
				text.setSize(this.kDSeparator2.getWidth(), move - 10);
				text.setLocation(this.kDContainer.getX(), this.kDContainer
						.getY()
						+ this.kDContainer.getHeight() + 6);
				text.setLineWrap(true);
				text.setText(EASResource.getString(
						"com.kingdee.eas.ma.view.client.BgViewClientResource",
						"decompInfo"));
				text.setEditable(false);
				text.setEnabled(false);
				add(text);
				this.haveDataOrg.putAll(getSuspendOrgUnit());
			}
		}

		showOrgUnitData();
		this.tblMain.getColumn(this.VALUECOL).setEditor(
				new KDTDefaultCellEditor(new KDNumberTextField()));
	}

	@Override
	protected void initListener() {
		super.initListener();
		modifyRateListener = new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				comModifyRateLost();
			}};
			
		comModifyRate.addFocusListener(modifyRateListener);
	}
	
	protected void comModifyRateLost() {
		NumberExpandInfo numberExpandInfo = null;
		for(int i=2;i<tblMain.getRowCount();i++){
			numberExpandInfo = (NumberExpandInfo) tblMain.getCell(i,this.ORGCOL)
				.getValue();
			if(numberExpandInfo.getId()!=null){
				tblMain.getCell(i, VALUECOL).setValue(comModifyRate.getNumberValue());
			}	
		}
	}

	private void dealSuspendOrgUnit(CostCenterOrgUnitCollection refOrgUnitCol,
			Map haveDataOrg) {
		if ((refOrgUnitCol == null) || (refOrgUnitCol.isEmpty()))
			return;
		CostCenterOrgUnitInfo info = null;
		getSuspendOrgUnit().clear();
		for (int index = 0; index < refOrgUnitCol.size(); index++) {
			info = refOrgUnitCol.get(index);
			if (!haveDataOrg.containsKey(info.getId().toString())) {
				getSuspendOrgUnit().put(info.getId().toString(), info);
			}
		}
	}

	private void initOrgTree() {
		this.orgUnits = ((Vector) getUIContext().get("orgUnits"));
		this.tblMain.checkParsed();
		NumberExpandInfo expandInfo = null;
		CostCenterOrgUnitInfo orgInfo = null;
		this.tblMain.addRow();
		this.expendRender = new NumberExpendRender();
		this.tblMain.getColumn(this.ORGCOL).setRenderer(this.expendRender);
		this.tblMain.getColumn(this.ORGCOL).getStyleAttributes()
				.setLocked(true);
		String perantLNum = (String) getUIContext().get("perantLNum");
		for (int i = 0; i < this.orgUnits.size(); i++) {
			orgInfo = (CostCenterOrgUnitInfo) this.orgUnits.get(i);

			if (orgInfo.getLongNumber().equals(perantLNum)) {
				expandInfo = new NumberExpandInfo();
				expandInfo.setId(orgInfo.getId().toString());
				expandInfo.setName(orgInfo.getName());
				expandInfo.setNumber(orgInfo.getNumber());
				expandInfo.setLongNumber(orgInfo.getLongNumber());
				expandInfo.setLevel(0);
				expandInfo.setLeaf(false);
				expandInfo.setExpandStatus(false);
				this.tblMain.getRow(0).getCell(this.ORGCOL)
						.setValue(expandInfo);
				this.tblMain.getRow(0).getCell(this.BOOLEANCOL).setValue(null);
				this.tblMain.getRow(0).getStyleAttributes().setLocked(true);
				break;
			}
		}
		for (int i = 0; i < this.orgUnits.size(); i++) {
			orgInfo = (CostCenterOrgUnitInfo) this.orgUnits.get(i);
			this.allOrg.put(orgInfo.getId().toString(), orgInfo);
		}
		ActionMap am = this.tblMain.getActionMap();
		am.remove("Paste");
		am.remove("Cut");
		am.remove("Delete");
		am.remove("Copy");
	}

	private void showOrgUnitData() throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			if ((this.tblMain.getCell(i, 0).getValue() != null)
					&& ((this.tblMain.getCell(i, 0).getValue() instanceof NumberExpandInfo))) {
				NumberExpandInfo expandInfo = (NumberExpandInfo) this.tblMain
						.getCell(i, 0).getValue();

				if ((expandInfo != null)
						&& (!expandInfo
								.getName()
								.equals(
										EASResource
												.getString(
														"com.kingdee.eas.ma.budget.client.BgRptResource",
														"collect")))) {
					setTreeDisplayStyle(this.tblMain, this.tblMain.getRow(i),
							expandInfo, 0);
				}
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) {
		if (e.getClickCount() == 1) {
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();

			KDTable table = (KDTable) e.getSource();
			if ((table.getCell(rowIndex, colIndex) != null)
					&& ((table.getCell(rowIndex, colIndex).getValue() instanceof NumberExpandInfo))) {
				NumberExpandInfo expandInfo = (NumberExpandInfo) table.getCell(
						rowIndex, colIndex).getValue();
				if ((expandInfo != null)
						&& (this.expendRender.inRect(expandInfo, e.getX(), e
								.getY())))
					setTreeDisplayStyle(table, table.getRow(rowIndex),
							expandInfo, e.getColIndex());
			}
		}
	}

	private void setTreeDisplayStyle(KDTable table, IRow row,
			NumberExpandInfo expandInfo, int colIndex) {
		table.setRefresh(false);
		expandTable(table, row, expandInfo, colIndex);
		table.setRefresh(true);
		table.reLayoutAndPaint();
	}

	protected void expandTable(KDTable table, IRow row,
			NumberExpandInfo parentExpandInfo, int colIndex) {
		if ((row == null) || (parentExpandInfo == null)) {
			return;
		}
		IRow child = null;
		NumberExpandInfo expandInfo = null;
		String longnumber = null;
		String plongnumber = null;

		plongnumber = parentExpandInfo.getLongNumber();
		if (parentExpandInfo.isExpandStatus()) {
			parentExpandInfo.setExpandStatus(false);

			int rowIndex = row.getRowIndex() + 1;
			int rowCount = table.getBody().size();
			for (; rowIndex < rowCount; rowIndex++) {
				child = table.getRow(rowIndex);
				expandInfo = (NumberExpandInfo) child.getCell(colIndex)
						.getValue();
				if (expandInfo != null) {
					longnumber = expandInfo.getLongNumber();

					if (!longnumber.startsWith(plongnumber + "!"))
						break;
					child.getStyleAttributes().setHided(true);
				}

			}
		} else {
			parentExpandInfo.setExpandStatus(true);

			int rowIndex = row.getRowIndex() + 1;
			int rowCount = table.getBody().size();
			if (rowIndex >= rowCount) {
				addChildOrgUnitByCol(table, parentExpandInfo, rowIndex,
						colIndex);
			} else {
				for (; rowIndex < rowCount; rowIndex++) {
					child = table.getRow(rowIndex);
					expandInfo = (NumberExpandInfo) child.getCell(colIndex)
							.getValue();
					if (expandInfo != null) {
						longnumber = expandInfo.getLongNumber();

						if (longnumber.startsWith(plongnumber + "!")) {
							child.getStyleAttributes().setHided(false);
							if (!expandInfo.isLeaf())
								expandInfo.setExpandStatus(false);
							if ((table.getRow(rowIndex + 1) != null)
									&& (isCol(table,
											table.getRow(rowIndex + 1),
											colIndex)))
								expandInfo.setExpandStatus(true);
						} else {
							if (rowIndex != row.getRowIndex() + 1)
								break;
							addChildOrgUnitByCol(table, parentExpandInfo,
									rowIndex, colIndex);
							break;
						}

					} else if (rowIndex == row.getRowIndex() + 1) {
						addChildOrgUnitByCol(table, parentExpandInfo, rowIndex,
								colIndex);
					}
				}
			}
		}
	}

	private boolean isCol(KDTable table, IRow row, int colIndex) {
		NumberExpandInfo info = (NumberExpandInfo) row.getCell(colIndex)
				.getValue();
		if (info.getName().equals(
				EASResource.getString(
						"com.kingdee.eas.ma.budget.client.BgRptResource",
						"collect")))
			return true;
		return false;
	}

	protected void addChildOrgUnitByCol(KDTable table,
			NumberExpandInfo expandInfo, int rowAlias, int colAlias) {
		if ((table == null) || (expandInfo == null)) {
			return;
		}

		String[] plongnumber = expandInfo.getLongNumber().split("!");
		String[] longnumber = null;

		IRow row = null;
		NumberExpandInfo expand = null;
		CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
		CostCenterOrgUnitInfo costCenterOrgUnitInfo2 = null;
		CostCenterOrgUnitCollection costCenterOrgUnitColl = new CostCenterOrgUnitCollection();
		CostCenterOrgUnitCollection tmpCol = new CostCenterOrgUnitCollection();
		String expandOrgNum = expandInfo.getLongNumber() + "!";
		for (int i = this.refOrgUnitCol.size() - 1; i >= 0; i--) {
			costCenterOrgUnitInfo2 = this.refOrgUnitCol.get(i);
			if (costCenterOrgUnitInfo2.getLongNumber().indexOf(expandOrgNum) == 0) {
				costCenterOrgUnitColl.add(costCenterOrgUnitInfo2);
			}
		}
		try {
			if ((costCenterOrgUnitColl == null)
					|| (costCenterOrgUnitColl.isEmpty())) {
				expandInfo.setLeaf(true);
			} else {
				int j = 0;
				for (int jn = costCenterOrgUnitColl.size(); j < jn; j++) {
					costCenterOrgUnitInfo = costCenterOrgUnitColl.get(j);
					longnumber = costCenterOrgUnitInfo.getLongNumber().split(
							"!");

					if (longnumber.length - plongnumber.length == 1) {
						row = table.addRow(rowAlias);
						dealMergeInfo(table, row);

						expand = new NumberExpandInfo();
						expand.setId(costCenterOrgUnitInfo.getId().toString());
						expand.setName(costCenterOrgUnitInfo.getName());
						expand.setNumber(costCenterOrgUnitInfo.getNumber());
						expand.setLongNumber(costCenterOrgUnitInfo
								.getLongNumber());
						expand.setLevel(expandInfo.getLevel() + 1);

						expandOrgNum = costCenterOrgUnitInfo.getLongNumber()
								+ "!";
						for (int i = this.refOrgUnitCol.size() - 1; i >= 0; i--) {
							costCenterOrgUnitInfo2 = this.refOrgUnitCol.get(i);
							if (costCenterOrgUnitInfo2.getLongNumber().indexOf(
									expandOrgNum) == 0) {
								tmpCol.add(costCenterOrgUnitInfo2);
							}
						}
						if ((tmpCol == null) || (tmpCol.isEmpty())) {
							expand.setLeaf(true);
						} else
							expand.setLeaf(false);
						expand.setExpandStatus(false);
						expand.setCollect(false);
						table.getCell(row.getRowIndex(), colAlias).setValue(
								expand);

						row.getCell(this.ORGCOL).setUserObject(
								getOrgUnitByLNum(expandInfo.getLongNumber()));

						if ((isViewForm())
								&& (getSuspendOrgUnit() != null)
								&& (getSuspendOrgUnit()
										.containsKey(costCenterOrgUnitInfo
												.getId().toString()))) {
							row.getCell(this.ORGCOL).getStyleAttributes()
									.setFontColor(Color.GRAY);
						}

						BgSHelper.objClear(tmpCol);
						row.getCell(this.BOOLEANCOL).setValue(
								new Boolean(true));
						if (!this.haveDataOrg.containsKey(costCenterOrgUnitInfo
								.getId().toString())) {
							row.getCell(this.BOOLEANCOL).setValue(null);
							row.getStyleAttributes().setLocked(true);
						}
					}
				}

				row = table.addRow(rowAlias);
				dealMergeInfo(table, row);
				row.getStyleAttributes().setBackground(BgSHelper.COLLECT_COLOR);
				row.getStyleAttributes().setLocked(true);
				expand = new NumberExpandInfo();
				expand.setName(EASResource.getString(
						"com.kingdee.eas.ma.budget.client.BgRptResource",
						"collect"));
				expand.setLongNumber(expandInfo.getLongNumber() + "!");
				expand.setLevel(expandInfo.getLevel() + 1);
				expand.setLeaf(true);
				expand.setExpandStatus(false);
				expand.setCollect(true);
				table.getCell(row.getRowIndex(), colAlias).setValue(expand);
				table.setRowCount(table.getBody().size());
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			BgSHelper.objClear(tmpCol);
			BgSHelper.objClear(costCenterOrgUnitColl);
		}
	}

	protected void dealMergeInfo(KDTable table, IRow row) {
		if ((table == null) || (row == null)) {
			return;
		}
		KDTMergeManager mm = table.getMergeManager();
		if (mm != null) {
			List mblist = mm.getMergeBlockList();
			if (!mblist.isEmpty()) {
				KDTMergeBlock mb = null;
				int i = 0;
				for (int n = mblist.size(); i < n; i++) {
					mb = (KDTMergeBlock) mblist.get(i);
					if (row.getRowIndex() == mb.getBottom() + 1) {
						mb.setBottom(mb.getBottom() + 1);
					}
				}
			}
		}
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		IRow row = this.tblMain.getRow(e.getRowIndex());
		if (isBoolean(e)) {
			setEditStopped(row);
		} else {
			this.newValue = new BigDecimal("0");
		}
		if (!row.getCell(this.ORGCOL).getValue().toString().trim().equals(
				EASResource.getString(
						"com.kingdee.eas.ma.budget.client.BgRptResource",
						"collect"))) {
//			addDataToColl(row);
		}
	}

	private void addDataToColl(IRow row) {
		if ((this.newValue == null) || (this.oldValue == null))
			return;
		if (this.oldValue != this.newValue) {
			for (int i = row.getRowIndex() - 1; i >= 0; i--) {
				if ((this.tblMain.getCell(i, this.ORGCOL).getValue() != null)
						&& (this.tblMain.getCell(i, this.ORGCOL).getValue()
								.toString().trim()
								.equals(EASResource
										.getString(
												"com.kingdee.eas.ma.budget.client.BgRptResource",
												"collect")))) {

					NumberExpandInfo expand1 = (NumberExpandInfo) this.tblMain
							.getCell(row.getRowIndex(), this.ORGCOL).getValue();
					NumberExpandInfo expand2 = (NumberExpandInfo) this.tblMain
							.getCell(i, this.ORGCOL).getValue();

					if ((expand1 != null) && (expand2 != null)
							&& (expand2.getLevel() == expand1.getLevel())) {
						if (this.tblMain.getCell(i, this.VALUECOL).getValue() != null) {
							String value = this.tblMain.getCell(i,
									this.VALUECOL).getValue().toString();

							this.tblMain.getCell(i, this.VALUECOL).setValue(
									new BigDecimal(value).add(this.newValue)
											.subtract(this.oldValue));

							break;
						}
						this.tblMain.getCell(i, this.VALUECOL).setValue(
								this.newValue.subtract(this.oldValue));

						break;
					}
				}
			}
		}
	}

	private void setEditStopped(IRow row) {
		ICell cell = row.getCell(this.VALUECOL);
		this.newValue = null;
		try {
			this.newValue = new BigDecimal(cell.getValue().toString());
		} catch (Exception ex) {
			this.newValue = new BigDecimal("0");
		}
	}

	protected void tblMain_editStarting(KDTEditEvent e) throws Exception {
		IRow row = this.tblMain.getRow(e.getRowIndex());
		if (isBoolean(e)) {
			getEditStarting(row);
		} else {
			this.oldValue = new BigDecimal("0");
		}
	}

	private boolean isBoolean(KDTEditEvent e) {
		return (this.tblMain.getCell(e.getRowIndex(), this.BOOLEANCOL) != null)
				&& (this.tblMain.getCell(e.getRowIndex(), this.BOOLEANCOL)
						.getValue() != null)
				&& (((Boolean) this.tblMain.getCell(e.getRowIndex(),
						this.BOOLEANCOL).getValue()).booleanValue())
				&& (this.tblMain.getCell(e.getRowIndex(), this.VALUECOL) != null);
	}

	private void getEditStarting(IRow row) {
		ICell cell = row.getCell(this.VALUECOL);
		try {
			this.oldValue = new BigDecimal(cell.getValue().toString());
		} catch (Exception ex) {
			this.oldValue = new BigDecimal("0");
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSelectAll.setEnabled(true);
		this.btnCleanAll.setEnabled(true);
	}

	public Map getData() {
		return this.orgProportion;
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		setCancel(true);
		getUIWindow().close();
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		addDataToOrgItem();
		setCancel(false);
		getUIWindow().close();
	}

	private void addDataToOrgItem() {
		this.orgProportion = new HashMap();
		IRow row = null;
		int maxLevel = 0;
		String formId = null;
		String parentFormId = null;
		String topFormID = null;
		NumberExpandInfo numberExpandInfo = null;
		Map rowMap = new HashMap();
		BigDecimal proportion = null;
		Map org = new HashMap();
		parentList = new ArrayList();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			row = this.tblMain.getRow(i);
			if(i==0){
				numberExpandInfo = (NumberExpandInfo) row.getCell(this.ORGCOL)
				.getValue();
				topFormID = (String) this.bgFormMap.get(BOSUuid
						.read(numberExpandInfo.getId()));
			}
			if ((row.getCell(this.BOOLEANCOL) != null)
					&& (row.getCell(this.BOOLEANCOL).getValue() != null)
					&& (((Boolean) row.getCell(this.BOOLEANCOL).getValue())
							.booleanValue())
					&& (row.getCell(this.VALUECOL).getValue() != null)
					&& (!StringUtils.isEmpty(row.getCell(this.VALUECOL)
							.getValue().toString()))) {
				numberExpandInfo = (NumberExpandInfo) row.getCell(this.ORGCOL)
						.getValue();
				this.orgProportion.put(numberExpandInfo.getId(),
						new BigDecimal(row.getCell(this.VALUECOL).getValue()
								.toString()));
				
				if (numberExpandInfo.getLevel() > maxLevel) {
					maxLevel = numberExpandInfo.getLevel();
				}
				rowMap.put(row, new BigDecimal(row.getCell(this.VALUECOL)
						.getValue().toString()));
			}
		}
		if (this.orgProportion.size() < 1) {
			this.orgProportion = null;

		} else {

			if (((getUIContext().get("bgForm") instanceof BgFormInfo))
					&& (isViewForm())) {
				verifySelect();
				BgFormInfo bgformInfo = (BgFormInfo) getUIContext().get(
						"bgForm");
				if (bgformInfo.getBgTemplate().getFormType().getValue() == 4) {
					return;
				}
			}
			this.formProportion = new ArrayList();
			Iterator it;
			for (int i = 0; i <= maxLevel; i++) {
				for (it = rowMap.keySet().iterator(); it.hasNext();) {
					row = (IRow) it.next();
					numberExpandInfo = (NumberExpandInfo) row.getCell(
							this.ORGCOL).getValue();
					if (numberExpandInfo.getLevel() == i) {
						formId = (String) this.bgFormMap.get(BOSUuid
								.read(numberExpandInfo.getId()));
						parentFormId = (String) this.bgFormMap
								.get(((CostCenterOrgUnitInfo) row.getCell(
										this.ORGCOL).getUserObject()).getId());
						if(!parentList.contains(parentFormId)){
							parentList.add(parentFormId);
						}
						proportion = (BigDecimal) rowMap.get(row);
						Object[] obj = new Object[4];
						obj[0] = formId;
						obj[1] = parentFormId;
						obj[2] = topFormID;
						obj[3] = proportion;
						this.formProportion.add(obj);
					}
				}
			}
		}
	}

	private void verifySelect() {
		if ((this.orgProportion == null) || (this.orgProportion.isEmpty()))
			return;
		String orgId = null;
		String perantLNum = (String) getUIContext().get("perantLNum");
		CostCenterOrgUnitInfo fatherOrg = null;
		CostCenterOrgUnitInfo orgInfo = null;
		for (Iterator it = this.orgProportion.keySet().iterator(); it.hasNext();) {
			orgId = it.next().toString();
			orgInfo = (CostCenterOrgUnitInfo) this.allOrg.get(orgId);
			fatherOrg = getFatherByOrgID(orgId);
			if ((fatherOrg != null)
					&& (!fatherOrg.getLongNumber().equals(perantLNum))
					&&

					(!this.orgProportion.containsKey(fatherOrg.getId()
							.toString()))) {
				String message = EASResource.getString(
						"com.kingdee.eas.ma.view.client.BgViewClientResource",
						"notSelectFatherOrg");
				String[] str = message.split("#");
				String newMessage = null;
				if (str.length == 3) {
					newMessage = str[0] + orgInfo.getName() + str[1]
							+ fatherOrg.getName() + str[2];
				}
				MsgBox
						.showInfo(this, newMessage != null ? newMessage
								: message);
				SysUtil.abort();
			}
		}
	}

	private CostCenterOrgUnitInfo getFatherByOrgID(String orgId) {
		if (StringUtils.isEmpty(orgId))
			return null;
		CostCenterOrgUnitInfo info = null;
		CostCenterOrgUnitInfo tempInfo = null;
		String superLongNumner;
		Iterator it;
		if (this.allOrg.containsKey(orgId)) {
			info = (CostCenterOrgUnitInfo) this.allOrg.get(orgId);
			superLongNumner = info.getLongNumber().substring(0,
					info.getLongNumber().lastIndexOf("!"));
			for (it = this.allOrg.keySet().iterator(); it.hasNext();) {
				tempInfo = (CostCenterOrgUnitInfo) this.allOrg.get(it.next()
						.toString());
				if (superLongNumner.equals(tempInfo.getLongNumber())) {
					info = tempInfo;
				}
			}
		}

		return tempInfo;
	}

	protected ICostCenterOrgUnit getOrgUnitInterface() throws BOSException {
		if (this.iCostCenterOrgUnit == null)
			this.iCostCenterOrgUnit = CostCenterOrgUnitFactory
					.getRemoteInstance();
		return this.iCostCenterOrgUnit;
	}

	public void actionDisselectAllCostCenter_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = null;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			row = this.tblMain.getRow(i);
			if ((row != null) && (!row.getStyleAttributes().isLocked())) {
				row.getCell(this.BOOLEANCOL).setValue(new Boolean(false));
				row.getCell(this.VALUECOL).setValue(null);
				comModifyRate.setValue(null);
			}
		}
	}

	public void actionSelectAllCostCenter_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = null;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			row = this.tblMain.getRow(i);
			if ((row != null) && (!row.getStyleAttributes().isLocked())) {
				row.getCell(this.BOOLEANCOL).setValue(new Boolean(true));
				dealCollect(row);
			}
		}
	}

	private void dealCollect(IRow row) {
		if (row == null)
			return;
		setEditStopped(row);
		if (!row.getCell(this.ORGCOL).getValue().toString().trim().equals(
				EASResource.getString(
						"com.kingdee.eas.ma.budget.client.BgRptResource",
						"collect"))) {
//			addDataToColl(row);
		}
	}

	private CostCenterOrgUnitInfo getOrgUnitByLNum(String plongnumber) {
		if ((plongnumber == null) || (this.orgUnits == null))
			return null;
		CostCenterOrgUnitInfo orgUnitInfo = null;
		for (Iterator it = this.orgUnits.iterator(); it.hasNext();) {
			orgUnitInfo = (CostCenterOrgUnitInfo) it.next();
			if (orgUnitInfo.getLongNumber().equals(plongnumber)) {
				return orgUnitInfo;
			}
		}
		return null;
	}

	public List getFormProportion() {
		return this.formProportion;
	}

	public List getParentList() {
		return this.parentList;
	}
	
	public boolean isViewForm() {
		return this.isViewForm;
	}

	public void setViewForm(boolean isViewForm) {
		this.isViewForm = isViewForm;
	}

	public Map getSuspendOrgUnit() {
		if (this.suspendOrgUnit == null)
			this.suspendOrgUnit = new HashMap();
		return this.suspendOrgUnit;
	}

	public void setSuspendOrgUnit(Map suspendOrgUnit) {
		this.suspendOrgUnit = suspendOrgUnit;
	}

}