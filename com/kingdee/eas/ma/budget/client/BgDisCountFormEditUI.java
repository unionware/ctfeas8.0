/* 
 * @(#) BgDisCountFormEditUI.java 
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.ma.budget.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.IllegalSessionStateException;
import com.kingdee.bos.ctrl.common.util.ZipUtil;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.core.KDSpread;
import com.kingdee.bos.ctrl.excel.impl.SpreadContext;
import com.kingdee.bos.ctrl.excel.impl.action.NotifyEvent;
import com.kingdee.bos.ctrl.excel.impl.action.NotifyListener;
import com.kingdee.bos.ctrl.excel.impl.action.SpreadAction;
import com.kingdee.bos.ctrl.excel.impl.action.SpreadActionFactory;
import com.kingdee.bos.ctrl.excel.impl.state.mouse.TableMouseController;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.CellBlock;
import com.kingdee.bos.ctrl.excel.model.struct.Column;
import com.kingdee.bos.ctrl.excel.model.struct.MergeBlocks;
import com.kingdee.bos.ctrl.excel.model.struct.Range;
import com.kingdee.bos.ctrl.excel.model.struct.Row;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.excel.model.struct.UserObject;
import com.kingdee.bos.ctrl.excel.model.struct.event.SheetChangeEvent;
import com.kingdee.bos.ctrl.excel.model.struct.event.SheetChangeListener;
import com.kingdee.bos.ctrl.excel.model.util.SortedCellBlockArray;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.rpc.RPCException;
import com.kingdee.bos.rpcwrapper.Utils;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.message.client.MessageCenterUI;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUI;
import com.kingdee.eas.base.myeas.ToolBarStyleEnum;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFrameContext;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gr.cslrpt.ItemFormula;
import com.kingdee.eas.fi.gr.cslrpt.client.ShowEditorPanelUI;
import com.kingdee.eas.fi.newrpt.client.designer.SpreadManager;
import com.kingdee.eas.fi.rpt.IReport;
import com.kingdee.eas.fi.rpt.ReportFactory;
import com.kingdee.eas.fi.rpt.RptResourceHelper;
import com.kingdee.eas.fi.rpt.TableToolkit;
import com.kingdee.eas.fi.rpt.util.IOHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.budget.BgAdjustFormDiversityData;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgCollectException;
import com.kingdee.eas.ma.budget.BgCollectStateEnum;
import com.kingdee.eas.ma.budget.BgConstants;
import com.kingdee.eas.ma.budget.BgDecColFacadeFactory;
import com.kingdee.eas.ma.budget.BgDisCountFacadeFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.eas.ma.budget.BgExamineFacadeFactory;
import com.kingdee.eas.ma.budget.BgExamineHelper;
import com.kingdee.eas.ma.budget.BgException;
import com.kingdee.eas.ma.budget.BgFSHelper;
import com.kingdee.eas.ma.budget.BgFormDiversityData;
import com.kingdee.eas.ma.budget.BgFormException;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormHelper;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.BgItemFormulaHelper;
import com.kingdee.eas.ma.budget.BgMeasureUnitCollection;
import com.kingdee.eas.ma.budget.BgMeasureUnitInfo;
import com.kingdee.eas.ma.budget.BgNationalFormatUIUtil;
import com.kingdee.eas.ma.budget.BgPeriodCollection;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgDecColFacade;
import com.kingdee.eas.ma.budget.IBgDisCountFacade;
import com.kingdee.eas.ma.budget.IBgDisCountForm;
import com.kingdee.eas.ma.budget.IBgExamineFacade;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.eas.ma.budget.IRefDisCountBgForm;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormFactory;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.nbudget.BgNConstants;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.client.BgNFCHelper;
import com.kingdee.eas.ma.nbudget.client.BgNFormCollectProcessUI;
import com.kingdee.eas.ma.nbudget.client.BgParamCHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 描述:汇编表编辑界面
 * @author hua_yang
 * @CreateTime 2006-3-31
 * @version EAS5.1
 */
public class BgDisCountFormEditUI extends AbstractBgDisCountFormEditUI {
	
	private static final long serialVersionUID = -8534125614209922971L;
	private static final Logger logger = CoreUIObject.getLogger(BgDisCountFormEditUI.class);
	
	protected final static String BGFORMID = "BGFORMID";
	protected final static String UPDATE = "UPDATE";
	
    protected final static String VERIFY_HAND = "HAND";//认为的检查上下级数据是否相等
    protected final static String VERIFY_AUTO = "AUTO";//根据系统参数自动检查
	
	protected NumberExpendRender expendRender = null;
	
	protected LongTimeDialog dialog = null;
	protected BOSUuid[] refBgFormIds = null;
	protected BOSUuid[] bgFormIds = null;
	
	protected Map measureMap = null;
	protected Map editorMap = null;
	protected Map renderMap = null;
	protected Map formulaMap = null;
	protected Map currencyMap = null;
	protected Map bgSchemeMap = null;
	protected Map adjustMap = null;
	protected Map bgFormMap = null;
	protected Map refCalDataMap = null;
	protected Map refCalAdjustMap = null;
	
	protected MeasureUnitInfo defMeasureUnit = null;
	protected ICellEditor defEditer = null, editor = null;
	protected BgFormCellRender defRender = null, render = null;
	
	protected CostCenterOrgUnitInfo defOrgUnitInfo = null;
	protected CostCenterOrgUnitCollection refOrgUnitCol = null;
	protected CurrencyInfo defCurrency = null, currency = null;
	
	protected String bgSelectorFormId = null;
	protected Map expandColIndex = null;
	
	protected boolean processValue = true;
	protected boolean loadFields = true;	/* 是否重新读取数据 */
	
	protected String titleName = "";
	
	protected int dataRow = -1;
	protected int dataEndRow = -1;
	protected String period = null;
	
	//保存隐藏列 key为页签id，value为列号
	protected HashMap hideCol = null;
	protected HashMap hideColNum = null;
	
	//保存重计算的表信息
	protected HashMap refCalOrgs = null;
	
	protected Book bookMain;
	
	private BgFormInfo returnBgFormInfo = null;
	
	private Map measreUnitMap = null;	/* 计量单位参数 */
	
	//存储预算表旧数据，新建时生成，以后打开调用即可
	private Map oldData = null;
	
	//存储汇编表其他属性数据
	private HashMap otherData = null;
	
	private String xNum = null;
	
	private static String CellMap = "CellMap"; 
	
	private String operState = "";  //操作状态
	
	private boolean parentNotEqualsChildCantSave = false;//默认为上级数据不等于下级数据时可以保存
	private boolean adjustCheckIsNotPass = false;//默认为调整后可用预算数为负数可以保存
	private boolean isFromSubmit = false;
	
	protected Map itemMapToShow = null;
	protected Map colMapToShow = null;
	private String sheetId = null;
	protected Vector costOrgUnits = null;
	protected Map timeMap = null;
	private Book colBook = null;    
	private byte[] bookByte = null;
	protected Map haveDataOrg = null;
	
	boolean isFromCalculate = false;
	
	private boolean isFormWfApproveUI = false;
	
	private BgDisCountFormEditUI wfUI = null;
	
	/**
	 * 表页检查不展开上下级，直接到AdjustMap及oldData中取数
	 * key:orgId,value:orgInfo
	 */
	private Map orgSheetCheckMap = null;
	
	/**
	 * 用于更新有取数公式无项目公式的单元格
	 * @author kaifei_yi
	 * 增加时间：2008-06-12
	 */
	private Map valueMap = null;
	
	private String CELL_KEY = "cell_expr_key_col";
	
	private boolean isChanged = false;
	
	/*将预算的系统参数一次加载到缓存*/
	private Map paramValue = null;
	/*将可以一次加载出来的信息一次性存入该map中*/
	private Map allRelateMap = null;
	
	/*定义全局变量判断是否来自于复制*/
	private boolean isFromCopy = false;
	
	/**
	 *参数是否锁定带取数公式的单元格
	 */
	private boolean isLockedExprCell = false;
	
	public BgDisCountFormEditUI() throws Exception {
		super();
	}
	
	
	public void onLoad() throws Exception {
		
		measureMap = new HashMap();
		editorMap = new HashMap();
		renderMap = new HashMap();
		formulaMap = new HashMap();
		currencyMap = new HashMap();
		bgSchemeMap = new HashMap();
		bgFormMap = new HashMap();
		measreUnitMap = new HashMap();
		oldData = new HashMap();
		otherData = new HashMap();
		expandColIndex = new HashMap();
		orgSheetCheckMap = new HashMap();
		paramValue = BgNSHelper.getAllParamValue();
        isLockedExprCell = BgNSHelper.isLockedOfOpen(paramValue);
		super.onLoad();	
		
		/* 判断是否是汇编中状态，如果是则可以使用汇编审批按钮 */
		if(BgCollectStateEnum.NEWCOLLECTED_VALUE == editData.getState().getValue()) {
			btnAudit.setEnabled(true);
			menuItemAudit.setEnabled(true);
		} else {
			btnAudit.setEnabled(false);
			menuItemAudit.setEnabled(false);
		}
		
		/* 判断是否是在工作流中，在工作流中不显示工具栏 */
		Boolean isMultiApprove = (Boolean) getUIContext().get("isMultiApprove");
		if (isMultiApprove != null && isMultiApprove.booleanValue()) {
			/*工作流判断*/
			if(OprtState.EDIT.equals(getOprtState()) || OprtState.VIEW.equalsIgnoreCase(getOprtState())){
				setOprtState(OprtState.VIEW);
				isFormWfApproveUI = true;
				this.setPreferredSize(new Dimension(50,50));
			}else if("FINDVIEW".equals(getOprtState())){
				wfUI = (BgDisCountFormEditUI)((MultiApproveUI)getUIContext().get("Owner")).getBillUI();
				this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			}
			/*toolBar.setEnabled(false);
			toolBar.setVisible(false);*/
			/* 如果是在工作流中则设置为查看状态 */
			/*setOprtState(OprtState.VIEW);*/
			btnAudit.setEnabled(false);
			btnAudit.setVisible(false);
			menuItemAudit.setEnabled(false);
			menuItemAudit.setVisible(false);
		}
		
		if (getOprtState().equals(OprtState.VIEW)) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionCollectCoefficient.setEnabled(false);
			actionCollectCoefficient.setVisible(false);
			btnCollectCoefficient.setVisible(false);
			btnRefCalculate.setVisible(false);
			
			setLockForSheetForView();
		}
		
		operState = this.getOprtState();
		
	}
    public boolean isModify() {
    	return isChanged;
    }
	private void setLockForSheetForView() throws Exception {
		Book book = getCurrentPage();
		BgNFSHelper.setBookProtection(book, true, null);
		Sheet sheet = null;
		for(int i = book.getSheetCount(); i >= 0 ; i--){
			sheet = book.getSheet(i);	
			BgNFSHelper.setSheetLocked(sheet, true);
			BgDCClientHelper.setSheetProtection(sheet, true, BgDCClientHelper.getPertection());
		}		
	}
	
	
	public void onShow() throws Exception {
		super.onShow();
		if (!StringUtils.isEmpty(titleName)) {
			setUITitle(titleName);
		}else  	if (editData.getBgForm() != null && editData.getBgForm().getBgScheme() != null){
			titleName = editData.getBgForm().getBgScheme().getName() + " " + editData.getBgForm().getName();
			setUITitle(titleName);
		}
		
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		
		separatorFile1.setVisible(false);
		separatorFW1.setVisible(false);
		separatorFW2.setVisible(false);
		separatorFW3.setVisible(false);
		
		actionRemove.setEnabled(false);
		btnRemove.setVisible(false);
		
		btnViewBgForm.setEnabled(false);
		
		btnReportCheck.setIcon(EASResource.getIcon("imgTbtn_check"));
		btnBgExamineCheck.setIcon(EASResource.getIcon("imgTbtn_examinecheck"));
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		btnViewBgForm.setIcon(EASResource.getIcon("imgTbtn_newdialog"));
		btnViewComment.setIcon(EASResource.getIcon("imgTbtn_showpostil"));
		btnAdjustView.setIcon(EASResource.getIcon("imgTbtn_view"));
		
		menuItemReportCheck.setIcon(EASResource.getIcon("imgTbtn_check"));
		menuItemBgExamineCheck.setIcon(EASResource.getIcon("imgTbtn_examinecheck"));
		menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		menuItemViewBgForm.setIcon(EASResource.getIcon("imgTbtn_newdialog"));
		menuItemViewComment.setIcon(EASResource.getIcon("imgTbtn_showpostil"));
		
		btnDataExamine.setIcon(EASResource.getIcon("imgTbtn_check"));
		ToolBarStyleEnum toolBarStyleEnum = (ToolBarStyleEnum) UIFrameContext.getInstance().getProperty(UIFrameContext.MYEAS_TOOLBARSTYLE);
		if (toolBarStyleEnum == null || toolBarStyleEnum.equals(ToolBarStyleEnum.IconText)) {
			btnViewBgForm.setIsControlByParent(false);
			btnViewBgForm.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
			btnViewComment.setIsControlByParent(false);
			btnViewComment.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		} else {
			btnViewBgForm.setIsControlByParent(true);
			btnViewComment.setIsControlByParent(true);
		}
		btnDataExamine.setVisible(true);
		btnDataExamine.setEnabled(true);
//		if(BgNSHelper.isFodian()){
//		btnDataExamine.setVisible(true);
//		btnDataExamine.setEnabled(true);
//		}else{
//		btnDataExamine.setVisible(false);
//		btnDataExamine.setEnabled(false);
//		}
		btnRefCalculate.setIcon(RptResourceHelper.getIcon("imgTbtn_autocount"));
		btnRefCalculate.setEnabled(true);
		btnHideCol.setVisible(true);
		btnHideCol.setEnabled(true);
		btnCancelHide.setVisible(true);
		btnCancelHide.setEnabled(true);
		btnHideCol.setIcon(EASResource.getIcon("imgTbtn_hiderow"));
		btnCancelHide.setIcon(EASResource.getIcon("imgTbtn_cancelhiderow"));
		btnAddNew.setVisible(false);
		
		miOpenExamineReport.setVisible(true);
		miOpenExamineReport.setEnabled(true);
		
		btnOpenExaminReport.setVisible(true);
		btnOpenExaminReport.setEnabled(true);
		
		btnOpenExaminReport.setIcon(EASResource.getIcon("imgTbtn_descrydispose"));
		miOpenExamineReport.setIcon(EASResource.getIcon("imgTbtn_descrydispose"));
		
		btnShowAllOrg.setVisible(true);
		btnShowAllOrg.setEnabled(true);
		btnShowAllOrg.setIcon(EASResource.getIcon("imgTbtn_spread"));
		btnCollectCoefficient.setIcon(EASResource.getIcon("imgTbtn_definedistributeratio"));
		btnCollectCoefficient.setVisible(true);
		btnCollectCoefficient.setEnabled(true); 
		
    	btnAdjustCheck.setEnabled(true);
    	btnAdjustCheck.setVisible(true);
    	btnAdjustCheck.setIcon(EASResource.getIcon("imgTbtn_descryrefer"));
    	menuItemAdjustCheck.setEnabled(true);
    	menuItemAdjustCheck.setVisible(true);
    	menuItemAdjustCheck.setIcon(EASResource.getIcon("imgTbtn_descryrefer"));    	
    	
    	//edit by hhj  编辑界面去除新增功能(BT657277)
    	menuItemAddNew.setVisible(false);
    	
    	
    	actionReportCheck.setEnabled(false);
    	actionBgExamineCheck.setEnabled(false);
    	actionViewBgForm.setEnabled(false);
    	actionViewComment.setEnabled(false);
    	actionAdjustView.setEnabled(false);
    	actionBgAdjustCheck.setEnabled(false);
    	actionOpenExamineReport.setEnabled(false);
    	actionCollectCoefficient.setEnabled(false);
    	actionRefCalculate.setEnabled(false);
    	
    	actionReportCheck.setVisible(false);
    	actionBgExamineCheck.setVisible(false);
    	actionViewBgForm.setVisible(false);
    	actionViewComment.setVisible(false);
    	actionAdjustView.setVisible(false);
    	actionBgAdjustCheck.setVisible(false);
    	actionOpenExamineReport.setVisible(false);
    	actionCollectCoefficient.setVisible(false);
    	actionRefCalculate.setVisible(false);
    	
    	this.btnModifyBgRate.setIcon(EASResource.getIcon("imgTbtn_compute"));
    	if (getOprtState().equals(OprtState.VIEW)) {
			this.actionModifyBgRate.setEnabled(false);
			this.actionModifyBgRate.setVisible(false);
		}else{
			this.actionModifyBgRate.setEnabled(true);
			this.actionModifyBgRate.setVisible(true);
		}
	}
	
	
	/**
	 * 描述：
	 * @param map
	 * @author:longl
	 * 创建时间：2006-4-12 <p>
	 */
	public void saveChangeData(BOSUuid bgFormId, Map adjustMap, Map addNewMap) throws Exception {
		
//		if (adjustMap == null || adjustMap.isEmpty())
//		return ;
		
		Cell cell = null;
		Book book = getCurrentPage();
		Sheet sheet = null;
		String formulaString = null, bgFormIdString = null;
		BgAdjustFormDiversityData divInfo = null, tmpDiv = null;
		
		
		
		Map childAdjustMap = getAdjustMap(bgFormId);
		Map updateMap = new HashMap();
		if (childAdjustMap == null)
			return ;
		for(int n = 0; n < book.getSheetCount(); n++){
			sheet = book.getSheet(n);
			int rowCount = sheet.getMaxRowIndex() + 1;
			int colCount = sheet.getMaxColIndex() + 1;
			for(int rowIndex=0; rowIndex<rowCount; rowIndex++) {
				for(int colIndex=0; colIndex<colCount; colIndex++) {
					cell = sheet.getCell(rowIndex, colIndex, true);
					if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
						formulaString = BgNFSHelper.getFormulaOfCell(cell);
						bgFormIdString = getExpInfo(cell, BGFORMID);
						
						if (bgFormIdString.equals(bgFormId.toString())) {
							if (adjustMap.containsKey(formulaString)) {
								/* 如果有预算表中更改 */
								divInfo = convert(adjustMap.get(formulaString));
								if (childAdjustMap.containsKey(formulaString) && childAdjustMap.get(formulaString) instanceof BgAdjustFormDiversityData) {
									tmpDiv = (BgAdjustFormDiversityData) childAdjustMap.get(formulaString);
									if (divInfo != null && tmpDiv != null && divInfo.getNewValue() != null && tmpDiv.getOldValue() != null && 
											divInfo.getNewValue().compareTo(tmpDiv.getOldValue()) == 0) {
										//childAdjustMap.remove(formulaString);
										tmpDiv.setNewValue(divInfo.getNewValue());
										BgNFSHelper.setValue(cell, divInfo.getNewValue());
										BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
									} else {
										childAdjustMap.put(formulaString, divInfo);
										if (divInfo == null || divInfo.getNewValue() == null)
											BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
										else
											BgNFSHelper.setValue(cell, divInfo.getNewValue());
										BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
									}
								} else {
									childAdjustMap.put(formulaString, divInfo);
									if (divInfo.getNewValue() == null)
										BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
									else
										BgNFSHelper.setValue(cell, divInfo.getNewValue());
									BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
								}
								updateMap.put(formulaString, null);
								
								if (BgNFSHelper.isCellLockedAndProtection(cell)){
									Range range = cell.getSheet().getRange(cell);
									range.setIgnoreProtected(true);
									range.setCellLocked(false);  
								}  
							} else {
								if (childAdjustMap.containsKey(formulaString)) {
									//childAdjustMap.remove(formulaString);
									divInfo = convert(childAdjustMap.get(formulaString));
									if (divInfo == null || divInfo.getOldValue() == null)
										BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
									else{
										divInfo.setNewValue(divInfo.getOldValue());
										BgNFSHelper.setValue(cell, divInfo.getOldValue());
									}
									BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
								}
							}
							
							if (addNewMap.containsKey(formulaString)) {
									Range range = cell.getSheet().getRange(cell);
									range.setIgnoreProtected(true);
									range.setCellLocked(false);  
							}
						}
						updateCollectValue(sheet, rowIndex, colIndex);
					}
				}
			}
		}
		
		for(Iterator iter=adjustMap.keySet().iterator(); iter.hasNext();) {
			formulaString = (String) iter.next();
			if (!updateMap.containsKey(formulaString)) {
				divInfo=convert(adjustMap.get(formulaString));
				divInfo.setAdjustor(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
				divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
				childAdjustMap.put(formulaString, divInfo);
			}
		}
		
		BgNSHelper.objClear(updateMap);
	}
	
	/**
	 * 将对象转换成 BgAdjustFormDiversityData
	 * 如果对象是 BgAdjustFormDiversityData，将直接取得，进行类型转换
	 * 如果对象是 BgFormDiversityData，将通过构造函数以该对象为原型，生成一对象
	 * 否则新生成一个原始的该对象
	 * @param obj
	 * @return
	 */
	private BgAdjustFormDiversityData convert(Object obj){
		if (obj instanceof BgAdjustFormDiversityData){
			return (BgAdjustFormDiversityData) obj;
		}else if (obj instanceof BgFormDiversityData){
			return new BgAdjustFormDiversityData((BgFormDiversityData)obj);
		}else{			
			return new BgAdjustFormDiversityData();
		}
	}
	
	/**
	 * 描述：更新从预算表删除的项目公式数据
	 * @param formulaMap
	 */
	public void removeBgFormulaOfForm(BOSUuid bgFormId, Map formulaMap) {
		/*在工作流中修改后需要刷新*/
		if(wfUI != null){
			wfUI.adjustMap = this.adjustMap;
			try {
				wfUI.showOrgUnitData();
				wfUI.updateCellWithAdjustMap();
			} catch (Exception e) {
				handUIException(e);
			}
		}
		
		if (bgFormId == null || formulaMap == null || formulaMap.isEmpty())
			return ;
		
		Map adjustMap = getAdjustMap(bgFormId);
		if (adjustMap != null && !adjustMap.isEmpty()) {
			String formulaString = null;
			for(Iterator iter=formulaMap.keySet().iterator(); iter.hasNext();) {
				formulaString = (String) iter.next();
				if (adjustMap.containsKey(formulaString))
					adjustMap.remove(formulaString);
			}
		}
		
	}
	
	
	/**
	 * 描述：根据装载的数据进行处理
	 * @author:longl
	 */
	public void loadFields() {
		super.loadFields();
		
		if(editData == null)
			SysUtil.abort();
		
		if (!loadFields)
			return ;
		
		try {
			otherData = BgHelper.buildHashMapByByte(editData.getOtherCont());
			
			loadOldData();
			
			loadDiversityInfo();
			
			/*将后面几个方法需要加载的信息调用此方法一次性加载出来*/
        	allRelateMap = getBgDisCountFacade().loadAllRelateInfo(editData.getId());
			
			loadAllCurrency();
			
			loadAllTableStyle();
			
			loadOrgSheetCheckMap();
			
			loadDefinedData();
			
		} catch (Exception e) {
			handUIException(e);
		}
		
		loadFields = false;
		
	}     
	
    protected IBgDisCountFacade getBgDisCountFacade()throws Exception{
    	return BgDisCountFacadeFactory.getRemoteInstance();
    }

	private void loadOrgSheetCheckMap() throws Exception{
		orgSheetCheckMap = (Map)allRelateMap.get("orgSheetCheckMap");
//		if(orgSheetCheckMap == null)
//			orgSheetCheckMap = new HashMap();
//		if(bgFormMap == null || bgFormMap.isEmpty())
//			loadAllTableStyle();
//		Set orgSet = new HashSet();
//		for(Iterator it = bgFormMap.keySet().iterator();it.hasNext();){
//			String id = ((BOSUuid)it.next()).toString();
//			orgSet.add(id);
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id", orgSet, CompareType.INCLUDE));
//		view.setFilter(filter);
//		
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("id");
//		sic.add("name");
//		sic.add("number");
//		sic.add("longnumber");
//		view.getSelector().addObjectCollection(sic);
//		
//		SorterItemInfo sore = new SorterItemInfo("longNumber");
//		sore.setSortType(SortType.DESCEND);
//		view.getSorter().add(sore);
//		
//		CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
//		CostCenterOrgUnitCollection costCenterOrgUnitColl = null;
//		
//		try {
//			costCenterOrgUnitColl = getOrgUnitInterface().getCostCenterOrgUnitCollection(view);
//			for(int i=0,size = costCenterOrgUnitColl.size();i<size;i++){
//				costCenterOrgUnitInfo = costCenterOrgUnitColl.get(i);
//				if(costCenterOrgUnitInfo != null)
//					orgSheetCheckMap.put(costCenterOrgUnitInfo.getId(),costCenterOrgUnitInfo);
//			}
//		}catch(Exception e){
//			logger.error(e);
//			e.printStackTrace();
//		}
	}
	
	private void setLockForSheet() throws Exception {
		Book book = getCurrentPage();
		BgNFSHelper.setBookProtection(book, true, null);
		Sheet sheet = null;
		for(int i = 0; i < book.getSheetCount(); i++){
			sheet = book.getSheet(i);				
			BgDCClientHelper.setSheetProtection(sheet, true, BgDCClientHelper.getPertection());
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
	
	/**
	 * 加载在空白处自定义输入的数据，不包含项目公式的单元格
	 * @author haijiang_he
	 * 2011-11-9
	 */
	private void loadDefinedData()
	{
		bookMain.getSheetCount();
		Map map = (Map)this.getAdjustMap().get(editData.getBgForm().getId());
		if(map != null)
		{
			Map cellColl = (Map)map.get(CellMap);
			if(cellColl != null)
			{
				Iterator it = cellColl.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry entry = (Map.Entry)it.next();
					Object key = entry.getKey();
					Object value = entry.getValue();
					String[] str = key.toString().split("\\$");
					if(str != null && str.length ==3)
					{
						String sheet = str[0].substring(0, str[0].length() - 1);
						String col = str[1];
						String row = str[2];
						int colIndex = bookMain.getSheet(sheet).getColumnIndexByName(col);
						bookMain.getSheet(sheet).getCell(Integer.valueOf(row).intValue() - 1, colIndex, true).setValue(new Variant(value));
					}
				}
			}
		}
	}
	
	/**
	 * 描述　查看预算表
	 */
	public void actionViewBgForm_actionPerformed(ActionEvent e) throws Exception {
		
		if(bgSelectorFormId == null || bgSelectorFormId.length() == 0)
			return ;
		
		storeDiversityInfo();
		
		Map editValueMap = getEditValue(bgSelectorFormId);
		
		String uiClassName = BgNFormCollectProcessUI.class.getName();
		UIContext uiContext = new UIContext(this);
		uiContext.putAll(getUIContext()) ;
		uiContext.put(UIContext.ID, bgSelectorFormId);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put(BgFormHelper.isOpenReport, Boolean.TRUE);
		uiContext.put(BgFormHelper.isBizActual, Boolean.FALSE);
		uiContext.put(BgFormHelper.OpenReportState, getOprtState());
		uiContext.put(BgFormHelper.bgFormType, BgFormHelper.budgetEditType);
		uiContext.put("isCollectBgFormUI", Boolean.TRUE);
		uiContext.put(BgFormHelper.EDIT_VALUE, editValueMap);
		uiContext.put(UIContext.INIT_DATAOBJECT, null);
		uiContext.put("state", editData.getState());
		String oprtState = OprtState.EDIT;
		if(OprtState.VIEW.equals(getOprtState())){
			oprtState = getOprtState();
		}else if(!BgNSHelper.isCanUpdataForCollect() && bgSelectorFormId.equals(returnBgFormInfo.getId().toString())){
			oprtState = OprtState.VIEW;
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
		.create(uiClassName, 
				uiContext, 
				null, 
				oprtState);
		uiWindow.show();
	}
	
	protected Map getEditValue(String bgFormId) {
		Map editValueMap = new HashMap();
		if (bgFormId == null)
			return editValueMap;
		
		Component[] comps = null, compkds = null;
		Component comp = null, compkd = null;
		KDPanel panel = null;
		
		int rowCount = 0, rowIndex = 0;
		int colCount = 0, colIndex = 0;
		
		Book book = null;
		Cell cell = null;
		
		String tmpFormId = null, formulaString = null;
		
		comps = (Component[]) paneMain.getComponents();
		for(int i=0, in=comps.length; i<in; i++) {
			comp = comps[i];
			if (comp instanceof KDPanel) {
				panel = (KDPanel) comp;
				
				compkds = panel.getComponents();
				for(int j=0, jn=compkds.length; j<jn; j++) {
					compkd = compkds[j];
					
					if (compkd != null && compkd instanceof SpreadContext) {
						book = ((SpreadContext) compkd).getBook();
						Sheet sheet = null;
						for(int n = 0; n < book.getSheetCount(); n++){
							sheet = book.getSheet(n);			
							rowCount = sheet.getMaxRowIndex()+1;
							colCount = sheet.getMaxColIndex()+1;
							
							for(rowIndex=0; rowIndex<rowCount; rowIndex++) {
								for(colIndex=0; colIndex<colCount; colIndex++) {
									cell = sheet.getCell(rowIndex, colIndex, true);
									
									if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
										tmpFormId = getExpInfo(cell, BGFORMID);
										if (bgFormId.equals(tmpFormId)) {
											formulaString = BgNFSHelper.getFormulaOfCell(cell);
											editValueMap.put(formulaString, cell.getValue().toString());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return editValueMap;
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (BgFormHelper.checkInProInst(this.editData.getId().toString())) {
			MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOSUBMIT"));
			SysUtil.abort();
		}               
		
		int re = MsgBox.showConfirm3(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLECTFORMSUBMITINFO"));
		if (re == MsgBox.NO || re == MsgBox.CANCEL)
			return;
		
		if (isModify()) {
			isFromSubmit = true;
			actionSubmit_actionPerformed(e);
		} else {
			//sqm 2011.03 add BT525537
			BgDisCountFormInfo info = getInterface().getBgDisCountFormInfo("select id, state where id = '"+this.editData.getId().toString()+"'");
			if(BgCollectStateEnum.SIGNED.equals(info.getState())) {
				throw new BgCollectException(BgCollectException.BGCOLLECTFORMNOCHANGE);
			}
		}
		
		
		/* 表页检查 */
		if (BgParamCHelper.isCompulsionReportCheck()) {
			checkReport();
		}
		
		
		if (getExamineInterface().canExamine( getBgExmCheckReportId() )) {
			xNum = BgNFCHelper.returnExamineX(getBgExmCheckReportId(), this);
			Window win = SwingUtilities.getWindowAncestor(this);
			if (getUIWindow() instanceof Dialog || win instanceof Dialog) {
				dialog = new LongTimeDialog((Dialog) win);
			} else if (getUIWindow() instanceof Frame || win instanceof Frame) {
				dialog = new LongTimeDialog((Frame) win);
			} else {
				dialog = null;
			}
			
			if (dialog != null) {
				dialog.setLongTimeTask(new ILongTimeTask() {
					public Object exec() throws Exception {
						Object[] result = getExamineInterface().examines( getBgExmCheckReportId(), xNum);
						Map bgExamineCheckMap = new HashMap();
						bgExamineCheckMap.put("BgExamineChecked", result[0]);
						bgExamineCheckMap.put("BgExamineCheckResult", result[1]);
						return bgExamineCheckMap;
					}
					
					public void afterExec(Object result) throws Exception {
						if (result != null && result instanceof HashMap) {
							openBgExamineViewUI(result, null);
							Map bgExamineCheckMap = (Map) result;
							Boolean bgExCheck = (Boolean) bgExamineCheckMap.get("BgExamineChecked");
							if (bgExCheck.booleanValue()) {
								BgDisCountFormFactory.getRemoteInstance().auditWF(editData.getId());
							} else {
								MsgBox.showWarning(getMessage("submitWorkDefeat"));
							}
						} else {
							MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "error"));
						}
					}
				});
				dialog.show();
			}
		} else {
			BgDisCountFormFactory.getRemoteInstance().auditWF(editData.getId());
		}
		showSubmitSuccess();
	}
	
	/**
	 * 描述：查看汇编意见
	 * @author:longl
	 */
	public void actionViewComment_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("ISCOLLECTFORM", Boolean.TRUE);
		uiContext.put("BGCOLLECTFORM", editData.getId().toString());//放入当前编辑预算表的对象即可
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(BgCollectFormCommentViewDlgUI.class.getName(), 
				uiContext);
		uiWindow.show();
	}
	
	
	/**
	 * 描述：根据下级预算表ID获取该预算表的汇编调整数据MAP
	 * @param bgFormId 预算表主键
	 */
	public HashMap getAdjustMap(String bgFormId) throws IOException, ClassNotFoundException{
		return editData.getAdjustMapByBgFormId(bgFormId);
	}
	
	/**
	 * 
	 * 描述：通过下级预算表的ID来获取下级预算表
	 * @param bgFormId
	 * @return
	 * @author:longl
	 * 创建时间：2006-4-12 <p>
	 */
	public RefDisCountBgFormInfo getRefBgFormInfo(String bgFormId){
		
		if(editData.getBgForm().getId().toString().equals(bgFormId)){
			return null;
		}
		
		Iterator iter = editData.getRefBgForms().iterator();
		RefDisCountBgFormInfo refForm;
		while(iter.hasNext()){
			refForm = (RefDisCountBgFormInfo)iter.next();
			if(refForm.getBgForm().getId().toString().equals(bgFormId)){
				return refForm;
			}
		}
		
		return null;
	}
	
	
	public void storeFields() {
		super.storeFields();
		
		try {
			storeDiversityInfo();
			
			if(OprtState.ADDNEW.equals(getOprtState())){
				setOprtState(OprtState.EDIT);
			}
			
		} catch (Exception e) {
			handUIException(e);
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (BgFormHelper.checkInProInst(this.editData.getId().toString())) {
			MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOCHANGE"));
			SysUtil.abort();
		}
		super.actionSave_actionPerformed(e);
		isChanged = false;
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		chkMenuItemSubmitAndAddNew.setVisible(false);
		parentNotEqualsChildCantSave = false;//默认为上级数据不等于下级数据时可以保存
		adjustCheckIsNotPass =  false;//调整检查默认通过
//		autoCheckBySystemParam();
		if(parentNotEqualsChildCantSave){
			if(isFromSubmit)
				MsgBox.showWarning(
						EASResource.getString(
								"com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", 
								"parentNotEqualChildCantSubmit"));
			else
				MsgBox.showWarning(
						EASResource.getString("com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", 
								"parentNotEqualChildCantSave"));
			SysUtil.abort();
		}    	
		boolean needsCheck = BgSHelper.isAdjustCheckRequired(null);
		if(needsCheck)
			adjustCheck(needsCheck);
		if(adjustCheckIsNotPass){
			if(isFromSubmit)
				MsgBox.showWarning(EASResource.getString(
						"com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", "adjustCheckNoPassCantSubmit"));
			else
				MsgBox.showWarning(EASResource.getString(
						"com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", "adjustCheckNoPassCantSave"));
			isFromSubmit = false;
			SysUtil.abort();
		}
		isFromSubmit = false;
		
		editData.setAdjustMapData(BgHelper.storeHashMapToByte(editData.getAdjustMap()));
//		editData.remove("kdtData");
		RefDisCountBgFormInfo refBgFormInfo1 = new RefDisCountBgFormInfo();
		for(Iterator iter=editData.getRefBgForms().iterator(); iter.hasNext();) {
			refBgFormInfo1 = (RefDisCountBgFormInfo) iter.next();
//			refBgFormInfo1.remove("kdtData");
		}
		checkFormulaIsEctype();        
//		if(refCalDataMap != null){        	
//			RefBgFormInfo refBgFormInfo1 = new RefBgFormInfo();
//			if(refCalDataMap.get(editData.getBgForm().getId().toString()) != null){
//				editData.setKdtData((byte[]) refCalDataMap.get(editData.getBgForm().getId().toString()));
//			}
//			for(Iterator iter=editData.getRefBgForms().iterator(); iter.hasNext();) {
//				refBgFormInfo1 = (RefBgFormInfo) iter.next();
//				if(refCalDataMap.get(refBgFormInfo1.getBgForm().getId().toString()) != null){
//					editData.getRefBgFormInfo(refBgFormInfo1.getBgForm().getId().toString()).setKdtData((byte[]) refCalDataMap.get(refBgFormInfo1.getBgForm().getId().toString()));
//				}        		
//			}
//		}
		
		super.actionSubmit_actionPerformed(e);
		
//		if(refCalDataMap != null){
//			Map dataMap = new HashMap();
//			RefBgFormInfo refBgFormInfo = new RefBgFormInfo();
//			if(refCalDataMap.get(editData.getBgForm().getId().toString()) != null){
//				refBgFormInfo.setKdtData((byte[]) refCalDataMap.get(editData.getBgForm().getId().toString()));
//				dataMap.put("from`"+editData.getBgForm().getId().toString(), refBgFormInfo);
//			}
//			for(Iterator iter=editData.getRefBgForms().iterator(); iter.hasNext();) {
//				refBgFormInfo = (RefBgFormInfo) iter.next();
//				if(refCalDataMap.get(refBgFormInfo.getBgForm().getId().toString()) != null){
//					refBgFormInfo.setKdtData((byte[]) refCalDataMap.get(refBgFormInfo.getBgForm().getId().toString()));
//					dataMap.put("ref`"+refBgFormInfo.getBgForm().getId().toString(), refBgFormInfo);
//				}        		
//			}
//			getInterface().updateDataForForm(editData.getId().toString(), dataMap);
//		}
		if(wfUI != null){
			wfUI.adjustMap = this.adjustMap;
			wfUI.showOrgUnitData();
			wfUI.updateCellWithAdjustMap();
			
		}
		isChanged = false;
		
	}
	public Map getAdjustMap(){
		return adjustMap;
	}
	private void updateCellWithAdjustMap() throws Exception {
		if(adjustMap != null){
			Book table =  getCurrentPage();
			Sheet sheet = null;
			for(int n = 0; n < table.getSheetCount(); n++){
				sheet = table.getSheet(n);		
				for (int i = 0; i < sheet.getMaxRowIndex()+1; i++) {
					for (int j = 0; j < sheet.getMaxColIndex()+1; j++) {
						Cell cell = sheet.getCell(i, j, true);
						
						if (BgNFSHelper.checkHasItemFormula(cell))
						{						
							
							Cell orgCell = sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
							if (orgCell != null && orgCell.getUserObject(BgNConstants.NEId) != null) {							
								String tmpFormId = getExpInfo(cell, BGFORMID);
								if(tmpFormId == null){
									continue;
								}
								Map childAdjustMap = (Map)adjustMap.get(BOSUuid.read(tmpFormId));
								if(childAdjustMap == null){
									continue;
								}
								String formulaString = BgNFSHelper.getFormulaOfCell(cell);
								if (childAdjustMap.containsKey(formulaString) && childAdjustMap.get(formulaString) != null) {
									BigDecimal newValue = ((BgAdjustFormDiversityData)childAdjustMap.get(formulaString)).getNewValue();
									BgNFSHelper.setValue(cell, newValue);
									cellChanged(cell);
									updateCollectValue(sheet, i, j);
								}
							}
						}
					}
				}	
			}
			if(table.getSheet(0) != null){
				Range selectRange = table.getSheet(0).getRange(0,0);		
				selectRange.select();
			}
		}
	
	}
	protected void checkFormulaIsEctype() throws Exception {
		
		Book book = null;
		Sheet sheet = null;
		Map checkMap = new HashMap();    	
		Map formulaMap = new HashMap();
		byte[] data = null;
		IBgDecColFacade iBgDecColFacade = BgDecColFacadeFactory.getRemoteInstance();
		RefDisCountBgFormInfo refBgFormInfo = null;
		
		try {    		
			data = editData.getKdtData();
			if (data != null && data.length > 0) {
				book = editData.getBook();
				for(int i=0, n= book.getSheetCount(); i<n; i++) {
					sheet = book.getSheet(i);
					formulaMap = getFormulaMap(sheet, formulaMap);
				}
				checkMap.put(editData.getBgForm().getId(), formulaMap);        		
			}
			
			for(Iterator iter=editData.getRefBgForms().iterator(); iter.hasNext();) {
				refBgFormInfo = (RefDisCountBgFormInfo) iter.next();
				checkMap.put(refBgFormInfo.getBgForm().getId(), refBgFormInfo.getId());        		
			}
			iBgDecColFacade.checkFormulaIsEctype(checkMap);
		} catch(Exception ex) {
			throw ex;
		} finally {
			BgNSHelper.objClear(formulaMap);
			data = null;
		}
	}
	
	protected Map getFormulaMap(Sheet sheet, Map formulaMap) {
		if (sheet == null || formulaMap == null)
			return null;
		
		Cell cell = null;
		String formulaString = null, localString = null;
		
		int rowCount = sheet.getMaxRowIndex()+1;
		int colCount = sheet.getMaxColIndex()+1;
		
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int colIndex = 0; colIndex < colCount; colIndex++) {
				cell = sheet.getCell(rowIndex, colIndex, true);
				
				if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
					formulaString = BgNFSHelper.getFormulaOfCell(cell);
					localString = TableToolkit.xy2range(colIndex, rowIndex);
					
					formulaMap.put(formulaString, localString);
				}
			}
		}
		
		return formulaMap;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (BgFormHelper.checkInProInst(this.editData.getId().toString())) {
			MsgBox.showWarning(EASResource.getString(BgClientHelper.COLLECTFORMRESOURCE, "BGCOLLFORMINWFNOREMOVE"));
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionBgAdjustCheck_actionPerformed(ActionEvent e) throws Exception {
    	
    	super.actionBgAdjustCheck_actionPerformed(e);
    	//loadDiversityInfo();
    	adjustCheck(false);//手工检查时与能否保存无关

    		
	}
	    
    /**
     * 参数判断是否必须需要检查，不通过不能保存
     * @param needsCheck
     * @throws EASBizException
     * @throws BOSException
     */
    private void adjustCheck(boolean needsCheck) throws EASBizException, BOSException{
    	List list =	getInterface().getAllNegativeAdjustCheckData(adjustMap);
    	if(list != null && list.size() <1 && !isFromCalculate)
    		MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", "adjustCheckPass"));
    	else if(list !=null && list.size() >0)
    	{
    		UIContext uiContext = new UIContext(this);
    		uiContext.put("adjustData", list);
            IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
            								.create(BgAdjustCheckReportUI.class.getName(),
            										uiContext,
            										null,
            										OprtState.VIEW);
            uiWindow.show();
           	adjustCheckIsNotPass = needsCheck;
    	}
    }
	
	/**
	 * 描述：显示汇编新增界面
	 */
	protected IObjectValue createNewData() {
		String uiClassName = AssignSolutionUI.class.getName();
		UIContext uiContext = new UIContext(this);
		uiContext.putAll(getUIContext());
		uiContext.put(UIContext.ID, null);
		uiContext.put("selfPanelUiName",BgDisCountFormAddNewUI.class.getName());
		if(isShowing()) {
			uiContext.put(UIContext.OWNER, this);
		}
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(uiClassName,
					uiContext,
					null, 
					OprtState.ADDNEW);
			uiWindow.show();
			Map returnData = (HashMap) ((AssignSolutionUI)uiWindow.getUIObject()).getReturnData();
			if(returnData == null){
				SysUtil.abort();
			}
			titleName = (String) returnData.get("setUITitle");
			if(!((Boolean)returnData.get("isCancel")).booleanValue()){
				if(getUIContext().get(UIContext.OWNER) instanceof BgDisCountFormListUI) {
					((BgDisCountFormListUI) getUIContext().get(UIContext.OWNER)).refreshList();                    
				}
				BgDisCountFormInfo bgDisCountFormInfo = (BgDisCountFormInfo)returnData.get("getData");                
				return bgDisCountFormInfo;
			}else{
				if(isShowing())
					getUIWindow().close();
				SysUtil.abort();
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return null;
	}
	
	/**  
	 * ================================================================================
	 * ===== @author hua_ynag                                                     =====
	 * ===== @CreateTime 2006/9/21                                                =====
	 * ================================================================================
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if(getSC() != null){
			getSC().getPrintManager().print();
		}
	}
	
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		if(getSC() != null){
			getSC().getPrintManager().printPreview();
		}
	}
//	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
//	super.actionPrint_actionPerformed(e);
//	
//	Book table = getCurrentPage();
////	table.getPrintManager().print();
//	}
//	
//	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
//	super.actionPrintPreview_actionPerformed(e);
//	
//	Book table = getCurrentPage();
////	preparePrintPage(table);
////	table.getPrintManager().printPreview();
//	}
	
//	public KDTable getCurrentPage() throws Exception {
//	KDTable table = null;
//	KDPanel panel = (KDPanel) paneMain.getSelectedComponent();
//	for(int i=0, n=panel.getComponentCount(); i<n; i++) {
//	if (panel.getComponent(i) instanceof KDTable) {
//	table = (KDTable) panel.getComponent(i);
//	break;
//	}
//	}
//	if (table == null)
//	throw new BgException(BgException.CHECKBLANK, new String[] {"KDTable"});
//	return table;
//	}
	
	public Book getCurrentPage() throws Exception {
		Book book = null;
		KDPanel panel = (KDPanel) paneMain.getSelectedComponent();
		for(int i=0, n=panel.getComponentCount(); i<n; i++) {
			if (panel.getComponent(i) instanceof KDSpread) {
				book = ((KDSpread) panel.getComponent(i)).getBook();
				break;
			}
		}
		if (book == null)
			throw new BgException(BgException.CHECKBLANK, new String[] {"KDTable"});
		return book;
	}
	
	/**
	 * 描述：读取汇编的调整记录
	 */
	protected void loadDiversityInfo() throws Exception {    	
		Map childAdjustMap = null;
		adjustMap = new HashMap();
		
		if (editData != null) {
			byte[] data = editData.getAdjustMapData();
			if (data == null || data.length == 0) {
				childAdjustMap = new HashMap();
				adjustMap.put(editData.getBgForm().getId(), childAdjustMap);
			} else {
				/* 初始化差异记录信息 */
				InputStream is = ZipUtil.unpack(data);
				ObjectInputStream ois = new ObjectInputStream(is);
				childAdjustMap = (Map) ois.readObject();
				
				if (childAdjustMap == null)
					childAdjustMap = new HashMap();
				adjustMap.put(editData.getBgForm().getId(), childAdjustMap);
				
				is.close();
				ois.close();
			}
			
			RefDisCountBgFormInfo refBgFormInfo = null;
			for(int i=0, n=editData.getRefBgForms().size(); i<n ; i++) {
				refBgFormInfo = editData.getRefBgForms().get(i);
				data = refBgFormInfo.getAdjustMapData();
				
				if (data == null || data.length == 0) {
					childAdjustMap = new HashMap();
					adjustMap.put(refBgFormInfo.getBgForm().getId(), childAdjustMap);
				} else {
					/* 初始化差异记录信息 */
					InputStream is = ZipUtil.unpack(data);
					ObjectInputStream ois = new ObjectInputStream(is);
					childAdjustMap = (Map) ois.readObject();
					
					if (childAdjustMap == null)
						childAdjustMap = new HashMap();
					adjustMap.put(refBgFormInfo.getBgForm().getId(), childAdjustMap);
					
					is.close();
					ois.close();
				}
			}
			
			Object _obj = null;
			Entry _entry = null;
			for (Iterator iter = getAdjustMap().entrySet().iterator(); iter.hasNext();) {
				_entry = (Entry) iter.next();

				_obj = _entry.getValue();
				if (_obj instanceof BgAdjustFormDiversityData) {
					
				} else if (_obj instanceof BgFormDiversityData) {
					_entry.setValue(new BgAdjustFormDiversityData((BgFormDiversityData) _obj));
				} else if (_obj instanceof Map){
					for(Iterator itSub=((Map)_obj).entrySet().iterator();itSub.hasNext();){
						Entry entrySub=(Entry)itSub.next();
						if (entrySub.getValue() instanceof BgFormDiversityData){
							entrySub.setValue(new BgAdjustFormDiversityData((BgFormDiversityData)entrySub.getValue()));
						}
					}
				}
			}
			
		} else {
			throw new BgFormException(BgFormException.NOBGFORMINFO);
		}
	}
	
	//读取隐藏列
	private void loadTableHide() {
		Book table = null;
		try{
			if(otherData != null && otherData.get("hideCol") != null)
				hideCol = BgHelper.buildHashMapByByte((byte[]) otherData.get("hideCol"));
			table = getCurrentPage();
		}catch (Exception e) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","166_BgCollectFormEditUI"));
		}
		if(hideCol == null || hideCol.size() == 0 || table == null)
			return;
		String id = null;
		Sheet sheet = null;
		Integer i = null;
		for(int n = 0; n < table.getSheetCount(); n++){
			sheet = table.getSheet(n);
			for(Iterator iter=hideCol.keySet().iterator(); iter.hasNext();) {
				id = (String) iter.next();
				hideColNum = (HashMap) hideCol.get(id);
				for(Iterator iter2=hideColNum.keySet().iterator(); iter2.hasNext();) {
					i = (Integer) iter2.next(); 
					Column col = sheet.getColumn(i.intValue(), true);
					if(col != null){
						BgNFSHelper.setHided(col, true);
					}
				}
			}
		}
	}
	
	
	/**
	 * 描述：保存调整记录到汇编对象
	 */
	protected void storeDiversityInfo() throws Exception {
		if(isFormWfApproveUI){
			editData.remove("adjustMapData");
			if (editData.getRefBgForms() != null && !editData.getRefBgForms().isEmpty()) {
				RefDisCountBgFormInfo refBgFormInfo = null;
				for(int i=0, n=editData.getRefBgForms().size(); i<n ; i++) {
					refBgFormInfo = editData.getRefBgForms().get(i);
					refBgFormInfo.remove("adjustMapData");
				}
			}
			return;
		}
		
		/* 如果差异记录大于零才保存差异记录数据 */
		if (editData != null && adjustMap != null) {
			
			Map childAdjustMap = null;
			ByteArrayOutputStream baos = null;
			ObjectOutputStream oos = null;
			
			if (adjustMap.containsKey(editData.getBgForm().getId())) {
				childAdjustMap = (Map) adjustMap.get(editData.getBgForm().getId());
				
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(childAdjustMap);
				editData.setAdjustMapData(ZipUtil.pack(baos));
				
				baos.close();
				oos.close();
			}
			
			if (editData.getRefBgForms() != null && !editData.getRefBgForms().isEmpty()) {
				RefDisCountBgFormInfo refBgFormInfo = null;
				for(int i=0, n=editData.getRefBgForms().size(); i<n ; i++) {
					refBgFormInfo = editData.getRefBgForms().get(i);
					
					if (adjustMap.containsKey(refBgFormInfo.getBgForm().getId())) {
						childAdjustMap = (Map) adjustMap.get(refBgFormInfo.getBgForm().getId());
						
						baos = new ByteArrayOutputStream();
						oos = new ObjectOutputStream(baos);
						oos.writeObject(childAdjustMap);
						refBgFormInfo.setAdjustMapData(ZipUtil.pack(baos));
						
						baos.close();
						oos.close();
					}
				}
			}
			
			baos.close();
			oos.close();
		}
	}
	
	protected Map getAdjustMap(BOSUuid bgFormId) {
		if (adjustMap == null)
			adjustMap = new HashMap();
		
		if (adjustMap.containsKey(bgFormId)) {
			return (Map) adjustMap.get(bgFormId);
		} else {
			Map childMap = new HashMap();
			adjustMap.put(bgFormId, childMap);
			return new HashMap();
		}
	}
	
//	protected String getExpInfo(ICell cell, String key) {
//	if (cell == null || key == null)
//	return "";
//	
//	if (cell.getUserObject() != null && cell.getUserObject() instanceof RptCell) {
//	RptCell rptCell = (RptCell) cell.getUserObject();
//	if (rptCell.getItemFormula() != null)
//	return rptCell.getItemFormula().getExt(key);
//	}
//	return "";
//	}
	
	protected String getExpInfo(Cell cell, String key) {
		if (cell == null || key == null)
			return "";
		
		if (BgNFSHelper.checkHasFormulaOfCell(cell)) {    		
			return BgNFSHelper.getItemFormula(cell).getExt(key);
		}
		return "";
	}    
	
	protected ICoreBase getBizInterface() throws BOSException {        
		return BgDisCountFormFactory.getRemoteInstance();
	}
	
	protected IBgDisCountForm getInterface() throws BOSException  {
		return BgDisCountFormFactory.getRemoteInstance();
	}
	
	protected IBgForm getFormInterface() throws BOSException {
		return BgFormFactory.getRemoteInstance();
	}
	
	protected IReport getReportInterface() throws BOSException {    	
		return ReportFactory.getRemoteInstance();
	}
	
	
	protected ICostCenterOrgUnit getOrgUnitInterface() throws BOSException {
		return CostCenterOrgUnitFactory.getRemoteInstance();
	}
	
	protected IRefDisCountBgForm getRefInterface() throws Exception {
		return RefDisCountBgFormFactory.getRemoteInstance();
	}
	
	protected void loadAllCurrency() throws BOSException, EASBizException {
		currencyMap = (Map)allRelateMap.get("currencyMap");
//		CurrencyInfo currencyInfo = null;
//		CurrencyCollection currencyCol = null;
//		ICurrency iCurrency = CurrencyFactory.getRemoteInstance();
//		if (iCurrency != null) {
//			currencyCol = iCurrency.getCurrencyCollection();
//			for(Iterator iter=currencyCol.iterator(); iter.hasNext(); ) {
//				currencyInfo = (CurrencyInfo) iter.next();
//				currencyMap.put(currencyInfo.getNumber(), currencyInfo);
//			}
//			BgNSHelper.objClear(currencyCol);
//		} else {
//			throw new BgFormException(BgFormException.NOINSTANCE);
//		}
	}
	
	/**
	 * 描述：将当前预算表的样式复制到KdTable中
	 * @param table
	 * @throws Exception 
	 */
	public void loadAllTableStyle() throws Exception {
		bookMain = Book.Manager.getNewBook(titleName, 0);   	
		boolean isFromKdf = false;
		int sheetIndex = 0, sheetCount = 0;
		BgFormInfo bgFormInfo = null;
		haveDataOrg = new HashMap();
		Book book = null;
		Sheet nSheet = null;
		
		paneMain.removeAll();
		
		bgFormInfo = (BgFormInfo)allRelateMap.get("bgFormInfo");
		if(bgFormInfo == null)
			throw new BgException(BgException.NOOBJECT);	
		if(period == null)
			period = bgFormInfo.getBgPeriod().getName();
		defOrgUnitInfo = (CostCenterOrgUnitInfo)allRelateMap.get("defOrgUnitInfo");
		defCurrency = bgFormInfo.getCurrency();
		bgFormMap.put(defOrgUnitInfo.getId(), bgFormInfo.getId().toString());
		titleName = bgFormInfo.getName();
		if (bgFormInfo.getMeasureUnit() != null)
			defMeasureUnit = bgFormInfo.getMeasureUnit();

		if (defMeasureUnit == null) {
			defMeasureUnit = new MeasureUnitInfo();
			defMeasureUnit.setCoefficient(new BigDecimal("1"));
		}

		if (bgFormInfo.getBgMeasureUnit() != null && !bgFormInfo.getBgMeasureUnit().isEmpty()) {
			BgMeasureUnitInfo bgMuInfo = null;
			BgMeasureUnitCollection bgMuColl = bgFormInfo.getBgMeasureUnit();
			for(int i=0, n=bgMuColl.size(); i<n; i++) {
				bgMuInfo = bgMuColl.get(i);
				measureMap.put(bgMuInfo.getBgElement().getNumber(), bgMuInfo.getMeasureUnit());
			}
		}

		try {
			if (editData.getKdtData() != null && editData.getKdtData().length > 0) {
				bgFormInfo.setZipData(editData.getKdtData());
				book = editData.getBook();
				isFromKdf = true;
			} else {
				book = bgFormInfo.getBook();
				isFromKdf = false;
			}
		} catch (Exception ex) {
			throw new BgException(BgException.UNSERIALIZABLEERROR, new String[] { ex.getMessage() });
		}
		returnBgFormInfo = bgFormInfo;
		initMeasureUnitInfo();

		sheetCount = book.getSheetCount();
		for (sheetIndex=0; sheetIndex<sheetCount; sheetIndex++) {
			nSheet = book.getSheet(sheetIndex);
			addSheetToBook(nSheet, isFromKdf);
		}
		for(int i=0; i<bookMain.getSheetCount(); i++){
    		if(!bookMain.getSheet(i).isHide()){
    			bookMain.activeSheet(i);
    			break;
    		}
    	}
		addBookToPanel(paneMain, false, isFromKdf);

		paneMain.setSelectedIndex(0);

		refOrgUnitCol = (CostCenterOrgUnitCollection)allRelateMap.get("refOrgUnitCol");
		if(refOrgUnitCol == null || refOrgUnitCol.size() == 0){
			refOrgUnitCol = new CostCenterOrgUnitCollection();
			try{
				List orgList = new ArrayList();
				HashMap orgMap = BgHelper.buildHashMapByByte(editData.getOrgList()); 
				if(orgMap != null && orgMap.get("orgList") != null){
					orgList = (List) orgMap.get("orgList");
					String orgId = null;
					for(Iterator ite = orgList.iterator(); ite.hasNext();){
						orgId = (String) ite.next();
						refOrgUnitCol.add(getOrgUnitInterface().getCostCenterOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(orgId))));
					}
				}
			}catch (Exception e){
				logger.error("budget error:", e);
			}

			Set orgId = new HashSet();
			if (editData.getRefBgForms() != null && !editData.getRefBgForms().isEmpty()) {
				RefDisCountBgFormInfo refBgFormInfo = null;
				RefDisCountBgFormCollection refBgFormCol = editData.getRefBgForms();
				for(int i=0, n=refBgFormCol.size(); i<n; i++) {
					refBgFormInfo = refBgFormCol.get(i);        			
					orgId.add(refBgFormInfo.getBgForm().getOrgUnit().getId().toString());    			
					bgSchemeMap.put(refBgFormInfo.getBgForm().getOrgUnit().getId(), refBgFormInfo.getBgForm().getBgScheme().getId());
					bgFormMap.put(refBgFormInfo.getBgForm().getOrgUnit().getId(), refBgFormInfo.getBgForm().getId().toString());
					haveDataOrg.put(refBgFormInfo.getBgForm().getOrgUnit().getId().toString(), null);
				}        		
			}    
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection item = filter.getFilterItems();
			item.add(new FilterItemInfo("id", orgId, CompareType.INCLUDE)); 	   
			viewInfo.setFilter(filter);
			viewInfo.getSorter().add(new SorterItemInfo("longNumber"));
			if(refOrgUnitCol == null || refOrgUnitCol.size() == 0)
				refOrgUnitCol = getOrgUnitInterface().getCostCenterOrgUnitCollection(viewInfo);
		}
		if (editData.getRefBgForms() != null && !editData.getRefBgForms().isEmpty()) {
			RefDisCountBgFormInfo refBgFormInfo = null;
			RefDisCountBgFormCollection refBgFormCol = editData.getRefBgForms();
			for(int i=0, n=refBgFormCol.size(); i<n; i++) {
				refBgFormInfo = refBgFormCol.get(i);        			
				bgSchemeMap.put(refBgFormInfo.getBgForm().getOrgUnit().getId(), refBgFormInfo.getBgForm().getBgScheme().getId());
				bgFormMap.put(refBgFormInfo.getBgForm().getOrgUnit().getId(), refBgFormInfo.getBgForm().getId().toString());
				haveDataOrg.put(refBgFormInfo.getBgForm().getOrgUnit().getId().toString(), null);
			}        		
		} 
		
		loadTableHide();
		
		setLockForSheet();      
	}
	
	private void addSheetToBook(Sheet sheet, boolean isFromKdf) {
		Sheet nSheet = new Sheet(bookMain, sheet.getSheetName());
		boolean isFirstSheet = false;
		if(bookMain.getSheetCount() == 0)
			isFirstSheet = true;
		bookMain.addSheet(nSheet, isFirstSheet);
		nSheet.setID(sheet.getID());
//		nSheet.setUserCellDisplayParser(new MyCellDisplayParser());
		
		dataRow = -1;
		dataEndRow = -1;		
		/* 处理表格的样式及数据 */
		copyStyleFromTable(nSheet, sheet, isFromKdf);
		
		/* 处理表格融合 */
		dealMergeManager(nSheet, sheet);
		
		/* 处理组织展开样式 */
		dealOrgUnitColInfo(nSheet);
		
		/* 注册表格的处理事件 */
		dealTableListener(nSheet);
	}
	
	private void addBookToPanel(KDTabbedPane paneMain, boolean b, boolean isFromKdf) {    	
		SpreadContext  sc = new SpreadContext();
		sc.setBook(bookMain);
		sc.setName(titleName);
		sc.getSpread().setMouseController(KDSpread.VIEW_TABLE, new MyTableMouseController(sc));
		sc.addSheetChangeListener(new SheetChangeListener()
				{
			public void changed(SheetChangeEvent e) {
				if (e.hasState(SheetChangeEvent.Changed_Content)) {
					try {
						cell_Content_Changed(e);
					} catch (Exception ex) {
						logger.error(ex);
						handleException(ex);
					}
				}
				
				if (e.hasState(SheetChangeEvent.Changed_Selection) ||
						e.hasState(SheetChangeEvent.Changed_SelectionActive)) {
					try {
						cell_Selection_Changed(e);
					} catch (Exception ex) {
						logger.error(ex);
						handleException(ex);
					}
				}
			}
				});
		SpreadAction cellMergeAction = sc.getActionManager().getAction(SpreadAction.Paste);
		cellMergeAction.setBeforeAction(new NotifyListener() {
			
			public void doNotify(NotifyEvent evt) {
				try {
					actionBeforePaste(evt);
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
		});
		cellMergeAction.setAfterAction(new NotifyListener() {
			
			public void doNotify(NotifyEvent evt) {
				try {
					actionAfterPaste(evt);
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
		});
		SpreadManager.registerRenderProvider(new OrgUnitRender());
		BudgetUserCellDisplayProvider a = new BudgetUserCellDisplayProvider();
		a.setDisplayValueProvider(new BudgetDiscDisplayValueProvider(this));
		a.addRenderProvider(new OrgUnitRender());
		sc.getRenderManager().setUserCellDisplayProvider(a);	
		sc.getActionManager().getAction(SpreadActionFactory.Fill).setEnabled(false);
		paneMain.add(titleName, sc);
	}
	
    protected void actionBeforePaste(NotifyEvent evt){
    	isFromCopy = true;
    }
    
    protected void actionAfterPaste(NotifyEvent evt){
    	isFromCopy = false;
    }
	
	/**
	 * 描述：单元格选择事件
	 */
	protected void cell_Selection_Changed(SheetChangeEvent e) throws Exception {
		Sheet table = e.getSheet();
		Range range = table.getSelectionRange();
		int rowIndex = range.getBlock(0).getRow();
		int colIndex = range.getBlock(0).getCol();
		Cell cell = table.getCell(rowIndex, colIndex, true);
		if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
			String tmpString = getExpInfo(cell, BGFORMID);
			if (tmpString != null && tmpString.trim().length() > 0) {
				bgSelectorFormId = tmpString;
				actionViewBgForm.setEnabled(true);
				btnViewBgForm.setEnabled(true);
			} else {
				bgSelectorFormId = null;
				actionViewBgForm.setEnabled(false);
			}
		} else {
			bgSelectorFormId = null;
			actionViewBgForm.setEnabled(false);
		}
	}
	
	private class MyTableMouseController extends TableMouseController
	{
		
		public MyTableMouseController(SpreadContext context) 
		{
			super(context);
		}
		
		public void mouseClicked(MouseEvent e) 
		{		
			Sheet activeSheet = null;
			try {
				activeSheet = getSheetPage();
			} catch (Exception e1) {
				MsgBox.showInfo(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","119_BgAllFormCollectionUI"));
			}
			if(activeSheet == null)
				return;
			Row row = activeSheet.getRow(activeSheet.getActiveRow(), true);
			Cell cell = activeSheet.getActiveCell();
			
			if("VIEW".equalsIgnoreCase(operState))
			{
				BgNFSHelper.setCellLocked(cell, true);
			}else
			{
				
				//对空白的单元格解锁
				Map map = (Map)adjustMap.get(editData.getBgForm().getId());
				Map cellCollMap = new HashMap();
				if(map != null && map.get(CellMap) != null)
				{
					cellCollMap = (Map)map.get(CellMap);
				}
				
				String newKey = getNewMapKey(cell.getName(true, true),cell);
				String cellKey = cell.getRowObject().getUserObjectValue("rowKey") != null?(String)cell.getRowObject().getUserObjectValue("rowKey") + "|" + cell.getCol():"";
				if(cell.getValue().getValue() == null || BgNFSHelper.checkHasFormulaOfCell(cell)
						|| cellCollMap.get(newKey) != null || cellCollMap.get(cellKey) != null)
				{
					
					BgNFSHelper.setCellLocked(cell, false);
				}else
				{
					BgNFSHelper.setCellLocked(cell, true);
				}
				
				//edit by hhj 对单元格在预算表中是锁定的，则不允许修改
				UserObject ub = cell.getUserObject("hasLock");
				if(ub != null && ub.getValue().equals(Boolean.TRUE))
				{
					BgNFSHelper.setCellLocked(cell, true);
				}
			}
			
			if( row != null && cell != null)
			{						
				NumberExpandInfo numberExpandInfo = null;
				if(cell.getUserObject(BgNConstants.NEId) == null){
					numberExpandInfo = null;
					if (e.getClickCount() >= 2)
						_context.getStateManager().addState(
								_context.getStateManager().createEditState(e));
				}
				else
					numberExpandInfo = BgNFCHelper.getNumberExpandInfo(cell);
				
				if(numberExpandInfo != null)
					setTreeDisplayStyle(activeSheet, row, numberExpandInfo, ((Integer)expandColIndex.get(activeSheet.getID())).intValue());
			}
		}
		
	}
	/**
	 * 描述：根据本级预算表的样式增加表页到汇编样式中
	 * @param tabPane
	 * @param table 预算表表页
	 * @param showIcon 是否显示图标
	 */
	protected void addTable(KDTabbedPane tabPane, KDTable table, boolean showIcon, boolean isFromKdf) {
		if (tabPane == null || table == null)
			return ;
		
		KDPanel panel = new KDPanel();
		panel.setLayout(new BorderLayout());
		
		KDTable nTable = new KDTable();
		nTable.setID(table.getID());
		nTable.setName(table.getName());
//		nTable.setUserCellDisplayParser(new MyCellDisplayParser());
		panel.add(nTable, BorderLayout.CENTER);
		if (showIcon)
			tabPane.addTab(table.getName(), EASResource.getIcon("imgTbtn_showdata"), panel);
		else
			tabPane.addTab(table.getName(), panel);
		
//		/* 处理表格的样式及数据 */
//		copyStyleFromTable(nTable, table, isFromKdf);
//		
//		/* 处理表格融合 */
//		dealMergeManager(nTable, table);
//		
//		/* 处理组织展开样式 */
//		dealOrgUnitColInfo(nTable);
//		
//		/* 注册表格的处理事件 */
//		dealTableListener(nTable);
	}
	
	/**
	 * 描述：根据表页的样式，复制有效数据行列
	 * @param sheet 复制表
	 * @param sheet2 样式表
	 */
	protected void copyStyleFromTable(Sheet nSheet, Sheet sheet, boolean isFromKdf) {
		
		if (nSheet == null || sheet == null)
			return ;
		BgDCClientHelper.setSheetLock(nSheet, true);
		Row row = null, sRow = null;
		Column column = null, sColumn = null;
		Cell cell = null, sCell = null;
		String alias = null;
		
		int rowIndex = 0, colIndex = 0;
		int rowCount = sheet.getMaxRowIndex()+1;
		int colCount = sheet.getMaxColIndex()+1;    
		
		nSheet.setHide(sheet.isHide());
		sheet.getBook().setCalculate(false);
		
		BgNFSHelper.addRows(nSheet, rowIndex, rowCount);
		BgNFSHelper.addColumns(nSheet, colIndex, colCount);    	
		
		Map adjustMap = getAdjustMap(editData.getBgForm().getId());
		
		for(rowIndex=0; rowIndex<rowCount; rowIndex++) {
			sRow = sheet.getRow(rowIndex, true);
			alias = BgNFSHelper.getRowSign(sRow);
			row = nSheet.getRow(rowIndex, true);
			BgNFSHelper.setRowObject(row, BgFSHelper.SIGN_DATA_ID, alias);    		
			
			for(colIndex=0; colIndex<colCount; colIndex++) {
				sColumn = sheet.getColumn(colIndex, true);
				if(sColumn == null || !BgNFSHelper.checkHasSign(sColumn))
					continue;
				alias = BgNFSHelper.getColSign(sColumn);
				column = nSheet.getColumn(colIndex, true);
				BgNFSHelper.setColSign(column, alias);
				
				
				sCell = sheet.getCell(rowIndex, colIndex, true);
				cell = nSheet.getCell(rowIndex, colIndex, true);
				copyDataFormCell(cell, sCell, adjustMap, isFromKdf);
				
			}
			
			if(BgNFSHelper.checkHasFormulaOfRow(row, colCount) && dataEndRow < rowIndex){
				dataEndRow = rowIndex;
			}
		}  	
		
	}
	
	protected void copyDataFormCell(Cell cell, Cell style, Map adjustMap, boolean isFromKdf) {
		if (cell == null || style == null)
			return ;
		
		if (style.getValue() != Variant.nullVariant)
			cell.setValue(style.getValue());
		
		if (style.getSSA().getNumberFormat() != null)
			BgNFSHelper.setNumberFormat(cell, style.getSSA().getNumberFormat());
		
    	if(BgNFSHelper.checkHasTextOfCell(style))
    		BgNFSHelper.setNumberFormat(cell, "@");
		
		if (BgNFSHelper.checkHasFormulaOfCell(style)) {
			if(dataRow == -1){
				dataRow = style.getRow();
			}
			String formulaString = BgNFSHelper.getFormulaOfCell(style);
			
			BigDecimal value = null;
			try{
				value = new BigDecimal(getExpInfo(style, BgItemFormulaHelper.BG_BUDGET_VALUE));
			}catch (Exception e) {
				value = BgConstants.BIGZERO;
			}
			
			BgNFSHelper.setFormulaOfCell(cell, formulaString, true);
			BgNFSHelper.getItemFormula(cell).putExt(BGFORMID, (String) bgFormMap.get(defOrgUnitInfo.getId()));
			//如果存在取数公式，根据系统参数：打开预算表是否锁定来进行处理 及汇编表是否可修改本级数据
			//后者优先判断
			if(BgNFSHelper.checkHasExpression(style) && BgNSHelper.isCanUpdataForCollect(paramValue)){
				Range range = cell.getSheet().getRange(cell);
				range.setIgnoreProtected(isLockedExprCell);
				range.setCellLocked(isLockedExprCell);
			}
			if (isFromKdf) {
				if (BgNFSHelper.checkCanEditFormulaOfCell(style)) {
					BgNFSHelper.getItemFormula(cell).putExt(UPDATE, Boolean.TRUE.toString());
				}else{
					BgNFSHelper.getItemFormula(cell).putExt(UPDATE, Boolean.FALSE.toString());
				}
			} else {
				BgNFSHelper.getItemFormula(cell).putExt(UPDATE, Boolean.FALSE.toString());
			}	
			
			if (adjustMap != null && adjustMap.containsKey(formulaString) && adjustMap.get(formulaString) instanceof BgAdjustFormDiversityData ) {
				BgAdjustFormDiversityData divData = (BgAdjustFormDiversityData) adjustMap.get(formulaString);
				if (divData == null || divData.getNewValue() == null)
					BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
				else
					BgNFSHelper.setValue(cell, divData.getNewValue());
				if(divData.getOldValue() == null)
					divData.setOldValue(BgConstants.BIGZERO);
				if(divData.getNewValue().compareTo(divData.getOldValue()) != 0)
					BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
			} else {
				BgNFSHelper.setValue(cell, value);        		
			}
			if(!getOprtState().equals(OprtState.VIEW)  && !BgNFSHelper.checkHasExpression(style) && BgNSHelper.isCanUpdataForCollect(paramValue)) {
				Range range = cell.getSheet().getRange(cell);
				range.setIgnoreProtected(true);
				range.setCellLocked(false);  
			}
			if(BgNFSHelper.checkHasExpression(style)){
	    		String expr = BgNFSHelper.getExpression(style);
	    		cell.setUserObject(CELL_KEY, expr);
	    	}
		}
		/**
    	 * 有取数公式无项目公式的地方需要把取数公式复制到单元格中
    	 * @author kaifei_yi
    	 * 增加时间：2008-06-12
    	 */
		else if(BgNFSHelper.checkHasExpression(style)){
    		String expr = BgNFSHelper.getExpression(style);
    		cell.setUserObject(CELL_KEY, expr);
    		Range range = cell.getSheet().getRange(cell);
			range.setIgnoreProtected(true);
			range.setCellLocked(true);    
    	}else {
			Range range = cell.getSheet().getRange(cell);
			range.setIgnoreProtected(true);
			range.setCellLocked(true);      
		}
		
		if(BgNFSHelper.checkHasExpression(style))
		{
			cell.setUserObject("hasLock", Boolean.TRUE);
		}
	}
	
	protected void dealMergeManager(Sheet nSheet, Sheet sheet) {
		if (nSheet == null || sheet == null)
			return ;
		int top = 0;
		int left = 0;
		Cell cell = null;
		Object obj = null;
		if (sheet.getMerger(false) != null) {
			MergeBlocks mblist = sheet.getMerger(false);
			if (mblist != null && !mblist.isEmpty()) {
				for(int n = 0; n < mblist.size(); n++){
					CellBlock cellBlock = mblist.getBlock(n);    			
					top = cellBlock.getRow();
					left = cellBlock.getCol();
					obj = sheet.getCell(top, left, true).getValue();
					sheet.getCell(top, left, true).getStyle();					
					if( cellBlock.getRow2() < dataRow|| (dataRow < dataEndRow && cellBlock.getRow2() > dataEndRow)){
						nSheet.getCell(top, left, true).setSSA(sheet.getCell(top, left, true).getSSA());
						nSheet.getRange(top, left, cellBlock.getRow2(), cellBlock.getCol2()).merge();
					}else{
						if (obj != Variant.nullVariant ) {
							for(int x=cellBlock.getCol(), xn=cellBlock.getCol2(); x<=xn; x++) {
								for(int y=cellBlock.getRow(), yn=cellBlock.getRow2(); y<=yn; y++) {
									cell = nSheet.getCell(y, x, true);
									if (cell != null)
										BgNFSHelper.setValue(cell, obj);
								}
							}
						}
					}					
				}
			}
		}
	}
	
	
	protected void dealOrgUnitColInfo(Sheet table) {
		if (table == null)
			return ;
		
		String alias = null;
		Row row = null;
		Column column = null;
		NumberExpandInfo expandInfo = null;
		
		int rowCount = table.getMaxRowIndex() + 1;
		int colCount = table.getMaxColIndex() + 1; 
		
		for(int colIndex=0; colIndex<colCount; colIndex++) {
			column = table.getColumn(colIndex, true);
			alias = (String) BgNFSHelper.getColObject(column, BgFSHelper.SIGN_DATA_ID);
			if (alias != null) {
				expandColIndex.put(table.getID(), new Integer(colIndex - 1));
				break;
			}
		}
		if(expandColIndex.get(table.getID()) == null)
			return;
		if (((Integer)expandColIndex.get(table.getID())).intValue() == -1) {
			column = BgNFSHelper.addColumn(table,0);
			expandColIndex.put(table.getID(), new Integer(0));    		
		} else {
			column = table.getColumn(((Integer)expandColIndex.get(table.getID())).intValue(), true);
		}
		table.getColRange(column.getCol(), column.getCol()).setColumnWidth(250);
		table.getColRange(column.getCol(), column.getCol()).setCellLocked(true);
		expendRender = new NumberExpendRender();
		
		colCount = table.getMaxColIndex() + 1;
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++) {
			row = table.getRow(rowIndex, true);
			if (BgNFSHelper.checkHasFormulaOfRow(row, colCount)) {
				expandInfo = new NumberExpandInfo();
				expandInfo.setId(defOrgUnitInfo.getId().toString());
				expandInfo.setName(defOrgUnitInfo.getName());
				expandInfo.setNumber(defOrgUnitInfo.getNumber());
				expandInfo.setLongNumber(defOrgUnitInfo.getLongNumber());
				expandInfo.setLevel(0);
				expandInfo.setLeaf(false);
				expandInfo.setExpandStatus(false);
				BgNFCHelper.setNumberExpandInfo(row.getCell(column.getCol(), true), expandInfo);
			}
		}
	}
	
	protected void dealTableListener(Sheet sheet) {
//		if (table == null)
//		return ;
//		
//		ActionMap am = table.getActionMap();
//		am.remove(KDTAction.PASTE);
//		am.remove(KDTAction.CUT);
//		am.remove(KDTAction.DELETE);
//		am.remove(KDTAction.COPY);
//		
//		table.addKDTMouseListener(new KDTMouseListener() {
//		
//		public void tableClicked(KDTMouseEvent e) {
//		table_Clicked(e);
//		}
//		
//		});
//		
//		table.addKDTPropertyChangeListener(new KDTPropertyChangeListener() {
//		
//		public void propertyChange(KDTPropertyChangeEvent evt) {
//		table_PropertyChange(evt);
//		}
//		
//		});
//		
//		table.addKDTSelectListener(new KDTSelectListener() {
//		
//		public void tableSelectChanged(KDTSelectEvent e) {
//		table_SelectChanged(e);
//		}
//		
//		});
	}
	
	protected void table_Clicked(KDTMouseEvent e) {
		if (e.getClickCount() == 1) {
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			Sheet table = (Sheet) e.getSource();
			NumberExpandInfo expandInfo = null;
			if (table != null && table.getCell(rowIndex, colIndex, true) != null &&
					table.getCell(rowIndex, colIndex, true).getUserObject(BgNConstants.NEId) != null) {
				if(table.getCell(rowIndex, colIndex, false).getUserObject(BgNConstants.NEId) == null)
					expandInfo = null;
				else
					expandInfo =  BgNFCHelper.getNumberExpandInfo(table.getCell(rowIndex, colIndex, false)) ;
				if (expandInfo != null && expendRender.inRect(expandInfo, e.getX(), e.getY()))
					setTreeDisplayStyle(table, table.getRow(rowIndex, true), expandInfo, e.getColIndex());
			}
		}
	}
	
//	protected void table_PropertyChange(KDTPropertyChangeEvent e) {
//	if (!processValue)
//	return ;
//	
//	Cell cell = null;
//	Sheet table = null;
//	
//	BgAdjustFormDiversityData divInfo = null;
//	Map valueMap = null;
//	String formulaString = null;
//	BigDecimal oldValue = null, newValue = null, value = null;
//	boolean tmpValue = false;
//	
//	table = (Sheet) e.getSource();
//	int rowIndex = e.getRowIndex();
//	int colIndex = e.getColIndex();
//	
//	cell = table.getCell(rowIndex, colIndex, true);
//	if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
//	formulaString = BgNFSHelper.getFormulaOfCell(cell);
//	try {
//	valueMap = getAdjustMap(BOSUuid.read(getExpInfo(cell, BGFORMID)));
//	} catch (Exception ex) {
//	valueMap = new HashMap();
//	}
//	
//	if (e.getOldValue() != null) {
//	try {
//	oldValue = new BigDecimal(e.getOldValue().toString());
//	} catch(Exception ex) {
//	oldValue = BgConstants.BIGZERO;
//	}
//	} else {
//	oldValue = BgConstants.BIGZERO;
//	}
//	
//	if (e.getNewValue() != null) {
//	try {
//	newValue = new BigDecimal(e.getNewValue().toString());
//	} catch(Exception ex) {
//	newValue = BgConstants.BIGZERO;
//	}
//	
//	if (newValue.compareTo(BgConstants.BIGZERO) > 0) {
//	if (BgFSHelper.checkHasOverstepMax(value))
//	newValue = GlUtils.maxBigDecimal;
//	} else if (newValue.compareTo(BgConstants.BIGZERO) < 0) {
//	if (BgFSHelper.checkHasOverstepMin(value))
//	newValue = GlUtils.minBigDecimal;
//	}
//	} else {
//	newValue = BgConstants.BIGZERO;
//	}
//	
//	if (newValue.compareTo(oldValue) != 0) {
//	if (valueMap.containsKey(formulaString)) {
//	divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
//	if (divInfo.getOldValue() == null)
//	divInfo.setOldValue(BgConstants.BIGZERO);
//	if (newValue.compareTo(divInfo.getOldValue()) == 0) {
//	if (isUpdate(cell)){
//	divInfo.setNewValue(newValue);
//	BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
////	cell.getStyleAttributes().setBackground(BgSHelper.EDIT_COLOR);
//	}else{
//	valueMap.remove(formulaString);
//	BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
////	cell.getStyleAttributes().setBackground(BgSHelper.BASE_COLOR);
//	}
//	} else {
//	divInfo.setNewValue(newValue);
//	BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
////	cell.getStyleAttributes().setBackground(BgSHelper.EDIT_COLOR);
//	}
//	divInfo.setAdjustor(SysContext.getSysContext()
//	.getCurrentUserInfo().getId().toString());
//	divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
//	} else {
//	divInfo = new BgAdjustFormDiversityData();
//	divInfo.setFormula(formulaString);
//	divInfo.setNewValue(newValue);
//	divInfo.setOldValue(oldValue);
//	divInfo.setAdjustor(SysContext.getSysContext()
//	.getCurrentUserInfo().getId().toString());
//	divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
//	valueMap.put(formulaString, divInfo);
//	BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
////	cell.getStyleAttributes().setBackground(BgSHelper.EDIT_COLOR);
//	}
//	
//	updateCollectValue(table, rowIndex, colIndex);
//	}
//	} else {
//	if (e.getNewValue() != null && BgSHelper.isNumerical(e.getNewValue())) {
//	try {
//	value = new BigDecimal(e.getNewValue().toString());
//	value = value.multiply(defMeasureUnit.getCoefficient());
//	} catch(Exception ex) {
//	value = BgConstants.BIGZERO;
//	}
//	
//	tmpValue = processValue;
//	processValue = false;
//	BgNFSHelper.setValue(cell, value.toString());
//	processValue = tmpValue;
//	}
//	}
//	}
	
//	protected void table_SelectChanged(KDTSelectEvent e) {
//	KDTable table = (KDTable) e.getSource();
//	KDTSelectBlock block = e.getSelectBlock();
//	int rowIndex = block.getBeginRow();
//	int colIndex = block.getBeginCol();
//	ICell cell = table.getCell(rowIndex, colIndex);
//	if (BgFSHelper.checkHasFormulaOfCell(cell)) {
//	String tmpString = getExpInfo(cell, BGFORMID);
//	if (tmpString != null && tmpString.trim().length() > 0) {
//	bgSelectorFormId = tmpString;
//	actionViewBgForm.setEnabled(true);
//	} else {
//	bgSelectorFormId = null;
//	actionViewBgForm.setEnabled(false);
//	}
//	} else {
//	bgSelectorFormId = null;
//	actionViewBgForm.setEnabled(false);
//	}
//	}
	
	protected void updateCollectValue(Sheet table, int rowAlias, int colAlias) {
		if (table == null)
			return ;
		
		Cell cell = null, expCell = null, formulaCell = null;
		NumberExpandInfo expandInfo = null, tmpExpand = null;
		int collLevel = -1;
		int rowCount = table.getMaxRowIndex() + 1;
		int rowIndex = 0, endIndex = 0;
		
		String formulaString = null, tmpString = null;
		
		BigDecimal value = BgConstants.BIGZERO;
		
		cell = table.getCell(rowAlias, ((Integer)expandColIndex.get(table.getID())).intValue(), true);
		formulaCell = table.getCell(rowAlias, colAlias, true);
		formulaString = BgNFSHelper.getFormulaOfCell(formulaCell);
		if (cell.getValue() != null && cell.getUserObjectValue(BgNConstants.NEName) != null) {
			expandInfo = BgNFCHelper.getNumberExpandInfo(cell);
			collLevel = expandInfo.getLevel();
		}
		
		
		if (expandInfo != null && collLevel > 0) {
			for(rowIndex=rowAlias; rowIndex<rowCount; rowIndex++) {
				expCell = table.getCell(rowIndex, ((Integer)expandColIndex.get(table.getID())).intValue(), true);
				formulaCell = table.getCell(rowIndex, colAlias, true);
				tmpString = BgNFSHelper.getFormulaOfCell(formulaCell);
				
				if (expCell.getValue() != null && expCell.getUserObjectValue(BgNConstants.NEName) != null) {
					tmpExpand = BgNFCHelper.getNumberExpandInfo(expCell);
					if (tmpExpand.getLevel() == collLevel) {
						if (rowIndex != rowAlias && tmpExpand.isCollect())
							break;
						if (tmpString != null && tmpString.equals(formulaString))
							endIndex = rowIndex;
					}
				}
			}
			
			for(rowIndex=endIndex; rowIndex>=0; rowIndex--) {
				cell = table.getCell(rowIndex, colAlias, true);
				expCell = table.getCell(rowIndex, ((Integer)expandColIndex.get(table.getID())).intValue(), true);
				
				if (expCell.getValue() != null && expCell.getUserObjectValue(BgNConstants.NEName) != null) {
					tmpExpand = BgNFCHelper.getNumberExpandInfo(expCell);
					
					if (tmpExpand.getLevel() == collLevel) {
						if (tmpExpand.isCollect()) {
							processValue = false;
							BgNFSHelper.setValue(cell, value);
							processValue = true;
							break;
						}
						
						if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
							try {
								value = value.add(new BigDecimal(cell.getValue().toString()));
							} catch(Exception ex) {
							}
						}
					}
				}
			}
		}
	}
	
	private void setTreeDisplayStyle(Sheet table, Row row, NumberExpandInfo expandInfo, int colIndex) {
		
		expandTable(table, row, expandInfo, colIndex);
		
		//table.reLayoutAndPaint();
	}
	
	protected void expandTable(Sheet table, Row row, NumberExpandInfo parentExpandInfo, int colIndex) {
		if (row == null || parentExpandInfo == null)
			return ;
		
		Row child = null;
		NumberExpandInfo expandInfo = null;
		String longnumber = null, plongnumber = null;
		
		plongnumber = parentExpandInfo.getLongNumber();
		if (parentExpandInfo.isExpandStatus()) {
			parentExpandInfo.setExpandStatus(false);
			BgNFCHelper.setNumberExpandInfo(row.getCell(colIndex, true), parentExpandInfo);
			/* 如果是展开则关闭 */
			
			int rowIndex = row.getRow() + 1;
			int rowCount = table.getMaxRowIndex() + 1;
			for(; rowIndex<rowCount; rowIndex++) {
				child = table.getRow(rowIndex, true);
				if(child == null ||child.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
					expandInfo = null;
				else
					expandInfo = BgNFCHelper.getNumberExpandInfo(child.getCell(colIndex, false));
				if (expandInfo != null) {
					longnumber = expandInfo.getLongNumber();
					
					if (longnumber.startsWith(plongnumber + "!")) {
						BgNFSHelper.setHided(child, true);
					} else {
						break;
					}
				}
			}
		} else {
			parentExpandInfo.setExpandStatus(true);
			BgNFCHelper.setNumberExpandInfo(row.getCell(colIndex, true), parentExpandInfo);
			/* 如果是关闭则展开 */
			int rowIndex = row.getRow() + 1;
			int rowCount = table.getMaxRowIndex() + 1;
			if (rowIndex >= rowCount) {
				addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex);
			} else {
				for(; rowIndex<rowCount; rowIndex++) {
					child = table.getRow(rowIndex, true);
					if(child.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
						expandInfo = null;
					else
						expandInfo = BgNFCHelper.getNumberExpandInfo(child.getCell(colIndex, true));
					if (expandInfo != null) {
						longnumber = expandInfo.getLongNumber();
						
						if (longnumber.startsWith(plongnumber + "!")) {
							BgNFSHelper.setHided(child, false);
							if (!expandInfo.isLeaf())
								expandInfo.setExpandStatus(false);
							if(table.getRow(rowIndex+1, false) != null && isCol(table, table.getRow(rowIndex+1, true), colIndex))
								expandInfo.setExpandStatus(true);
							BgNFCHelper.setNumberExpandInfo(child.getCell(colIndex, true), expandInfo);
						} else {
							if (rowIndex == row.getRow() + 1)
								addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex);
							break;
						}
					} else {
						if (rowIndex == row.getRow() + 1)
							addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex);
					}
				}
			}
		}
	}
	
	protected void addChildOrgUnitByCol(Sheet table, NumberExpandInfo expandInfo, int rowAlias, int colAlias) {
		if (table == null || expandInfo == null)
			return ;
		
		processValue = false;
		
		String[] plongnumber = expandInfo.getLongNumber().split("!");
		String[] longnumber = null;
		
		Set companySet = new HashSet();
		for(int i=0; i<refOrgUnitCol.size(); i++)
			companySet.add(refOrgUnitCol.get(i).getId().toString());
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longnumber", expandInfo.getLongNumber() + "!%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("id", companySet, CompareType.INCLUDE));
		view.setFilter(filter);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longnumber");
		view.getSelector().addObjectCollection(sic);
		
		SorterItemInfo sore = new SorterItemInfo("longNumber");
		sore.setSortType(SortType.DESCEND);
		view.getSorter().add(sore);
		
		Row row = null;
		Cell cell = null;
		NumberExpandInfo expand = null;
		CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
		CostCenterOrgUnitCollection costCenterOrgUnitColl = null, tmpCol = null;
		
		int rowIndex = 0, aliasIndex = 0;
		
		try {
			costCenterOrgUnitColl = getOrgUnitInterface().getCostCenterOrgUnitCollection(view);
			
			if (costCenterOrgUnitColl == null || costCenterOrgUnitColl.isEmpty()) {
				expandInfo.setLeaf(true);
			} else {
				for(rowIndex=rowAlias-1; rowIndex>=0; rowIndex--) {
					cell = table.getCell(rowIndex, colAlias, true);
					if (cell.getValue() != null && cell.getUserObjectValue(BgNConstants.NEId) != null) {
						expand = BgNFCHelper.getNumberExpandInfo(cell);
						if (expand.getLevel() == 0) {
							aliasIndex = rowIndex;
							break;
						}
					}
				}
				
				//展开时，记录当前行的项目公式
				Row currRow = table.getRow(rowIndex, true);
				String formulaStr = null;
				Cell currCell = null;
				for(int i=0;i<=table.getMaxColIndex();i++)
				{
					currCell = currRow.getCell(i, true);
					if(BgNFSHelper.checkHasFormulaOfCell(currCell))
					{
						formulaStr = BgNFSHelper.getFormulaOfCell(currCell);
						break;
					}
				}
				
				String key = null;
				Map map = (Map)getAdjustMap().get(editData.getBgForm().getId());
				Map cellMap = map.get(CellMap) != null?(Map)map.get(CellMap):new HashMap();
				for(int j=0, jn=costCenterOrgUnitColl.size(); j<jn; j++) {
					costCenterOrgUnitInfo = costCenterOrgUnitColl.get(j);
					longnumber = costCenterOrgUnitInfo.getLongNumber().split("!");
					
					if (longnumber.length - plongnumber.length == 1) {
						Range colRange = table.getRowRange(rowAlias, rowAlias);
						colRange.setIgnoreProtected(true);
						colRange.insert();
						colRange.setCellLocked(true);
						row = table.getRow(rowAlias, true);
						
						//当前行的项目公式和插入行的相对行数组合
						key = formulaStr +"|" +  j;
						row.setUserObject("hasAddRow", true);
						row.setUserObject("rowKey", key);
						
						//填充空白单元格的值
						String cellKey = null;
						for(int i=0;i<table.getMaxColIndex();i++)
						{
							cellKey = key + "|" + i;
							row.getCell(i, true).setValue(new Variant(cellMap.get(cellKey)));
						}
						
						for(int i = 0; i < table.getMaxColIndex() + 1; i++){
							BgNFSHelper.setBackGroup(row.getCell(i, true), BgSHelper.BASE_COLOR);					
						}	
						expand = new NumberExpandInfo();
						expand.setId(costCenterOrgUnitInfo.getId().toString());
						expand.setName(costCenterOrgUnitInfo.getName());
						expand.setNumber(costCenterOrgUnitInfo.getNumber());
						expand.setLongNumber(costCenterOrgUnitInfo.getLongNumber());
						expand.setLevel(expandInfo.getLevel() + 1);
						
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("longnumber", costCenterOrgUnitInfo.getLongNumber() + "!%", CompareType.LIKE));
						filter.getFilterItems().add(new FilterItemInfo("id", companySet, CompareType.INCLUDE));
						view.setFilter(filter);
						
						tmpCol = getOrgUnitInterface().getCostCenterOrgUnitCollection(view);
						if (tmpCol == null || tmpCol.isEmpty())
							expand.setLeaf(true);
						else
							expand.setLeaf(false);
						expand.setExpandStatus(false);
						expand.setCollect(false);
						BgNFCHelper.setNumberExpandInfo(table.getCell(row.getRow(), colAlias, true), expand);
						BgNSHelper.objClear(tmpCol);
						
						fillChildOrgUnitData(table, row, aliasIndex, colAlias, costCenterOrgUnitInfo.getId(), (BOSUuid) bgSchemeMap.get(costCenterOrgUnitInfo.getId()));
					}
				}
				
				Range colRange = table.getRowRange(rowAlias, rowAlias);
				colRange.setIgnoreProtected(true);
				colRange.insert();
				colRange.setCellLocked(true);
				row = table.getRow(rowAlias, true);
				row.setUserObject("hasAddRow", true);
				
				//当前行的项目公式和插入行的相对行数组合
				key = formulaStr +"|" +  costCenterOrgUnitColl.size();
				row.setUserObject("hasAddRow", true);
				row.setUserObject("rowKey", key);
				
				//填充空白单元格的值
				String cellKey = null;
				for(int i=0;i<table.getMaxColIndex();i++)
				{
					cellKey = key + "|" + i;
					row.getCell(i, true).setValue(new Variant(cellMap.get(cellKey)));
				}				
				
				for(int i = 0; i < table.getMaxColIndex() + 1; i++){
					BgNFSHelper.setBackGroup(row.getCell(i, true), BgSHelper.COLLECT_COLOR);
					//cell.getSheet().getRange(row.getCell(i,true)).setCellLocked(true);  
				}
				expand = new NumberExpandInfo();
				expand.setName(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect"));
				expand.setLongNumber(expandInfo.getLongNumber() + "!");
				expand.setLevel(expandInfo.getLevel() + 1);
				expand.setLeaf(true);
				expand.setExpandStatus(false);
				expand.setCollect(true);
				BgNFCHelper.setNumberExpandInfo(table.getCell(row.getRow(), colAlias, true), expand);
				
				fillCollectRowData(table, row, colAlias);				
			}
		} catch(Exception ex) {
		} finally {
			BgNSHelper.objClear(tmpCol);
			BgNSHelper.objClear(costCenterOrgUnitColl);
			BgNSHelper.objClear(companySet);
		}
		
		processValue = true;
	}
	
	protected void fillChildOrgUnitData(Sheet table, Row row, int rowAlias, int colAlias, 
			BOSUuid orgUnitId, BOSUuid bgSchemeId) {
		if (table == null || row == null || orgUnitId == null)
			return ;
		
		Cell cell = null, sCell = null;
		
		String formulaString = null, bgFormIdString = null;
		BgAdjustFormDiversityData divInfo = null;
		Map childAdjustMap = null;
		Set formulaSet = new HashSet();
		
		int colIndex = 0, colCount = 0;
		boolean isUpdate = false;
		
		try {
			colCount = table.getMaxColIndex()+1;
			for(colIndex=0; colIndex<colCount; colIndex++) {
				sCell = table.getCell(rowAlias, colIndex, true);
				cell  = table.getCell(row.getRow(), colIndex, true);
				
				if (BgNFSHelper.checkHasFormulaOfCell(sCell)) {
					formulaString = BgNFSHelper.getFormulaOfCell(sCell);
					isUpdate = isUpdate(sCell);
					formulaSet.add(formulaString);
					
					BgNFSHelper.setFormulaOfCell(cell, formulaString, true);
					BgNFSHelper.getItemFormula(cell).putExt(BGFORMID, (String) bgFormMap.get(orgUnitId));
					if (isUpdate)
						BgNFSHelper.getItemFormula(cell).putExt(UPDATE, Boolean.TRUE.toString());
					else
						BgNFSHelper.getItemFormula(cell).putExt(UPDATE, Boolean.FALSE.toString());
					if(!getOprtState().equals(OprtState.VIEW)) {
						Range range = cell.getSheet().getRange(cell);
						if(sCell != null && sCell.getUserObject(CELL_KEY) != null && isLockedExprCell){
							range.setIgnoreProtected(true);
							range.setCellLocked(true);
							cell.setUserObject(CELL_KEY, sCell.getUserObject(CELL_KEY));
						}else{
							range.setIgnoreProtected(true);
							range.setCellLocked(false);
						}
					}  
				}
			}		
			
			for(colIndex=0; colIndex<colCount; colIndex++) {
				sCell = table.getCell(rowAlias, colIndex, true);
				cell = table.getCell(row.getRow(), colIndex, true);
				if (BgNFSHelper.checkHasFormulaOfCell(cell) && getExpInfo(cell, BGFORMID) != null && !getExpInfo(cell, BGFORMID).equals("")) {
					formulaString = BgNFSHelper.getFormulaOfCell(cell);
					bgFormIdString = getExpInfo(cell, BGFORMID);
					childAdjustMap = getAdjustMap(BOSUuid.read(bgFormIdString));
					
					if (childAdjustMap.containsKey(formulaString)) {
						divInfo = (BgAdjustFormDiversityData) childAdjustMap.get(formulaString);
						if (divInfo == null || divInfo.getNewValue() == null)
							BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
						else
							BgNFSHelper.setValue(cell, divInfo.getNewValue());
						if(divInfo.getOldValue() == null)
							divInfo.setOldValue(BgConstants.BIGZERO);
						if(divInfo.getNewValue().compareTo(divInfo.getOldValue()) != 0)
							BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
						if(!getOprtState().equals(OprtState.VIEW)) {
							Range range = cell.getSheet().getRange(cell);
							
							if(sCell != null && sCell.getUserObject(CELL_KEY) != null && isLockedExprCell){
								range.setIgnoreProtected(true);
								range.setCellLocked(true);
								cell.setUserObject(CELL_KEY, sCell.getUserObject(CELL_KEY));
							}else{
								range.setIgnoreProtected(true);
								range.setCellLocked(false);
							}
						}  
					} else {
						if(oldData.get(getExpInfo(cell, BGFORMID) + formulaString) != null){
							BgNFSHelper.setValue(cell, oldData.get(getExpInfo(cell, BGFORMID) + formulaString));
							if(!getOprtState().equals(OprtState.VIEW) && !BgNFSHelper.checkHasExpression(cell)){
								Range range = cell.getSheet().getRange(cell);
								if(sCell != null && sCell.getUserObject(CELL_KEY) != null && isLockedExprCell){
									range.setIgnoreProtected(true);
									range.setCellLocked(true);
									cell.setUserObject(CELL_KEY, sCell.getUserObject(CELL_KEY));
								}else{
									range.setIgnoreProtected(true);
									range.setCellLocked(false);
								}
							}	
						} else {
							BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
							Range range = cell.getSheet().getRange(cell);
							range.setIgnoreProtected(true);
							range.setCellLocked(true);    
						}
					}
				}
				else{
					Range range = cell.getSheet().getRange(cell);
					range.setIgnoreProtected(true);
					range.setCellLocked(true);    
				}
			}
		} catch(Exception ex) {
			Range range = cell.getSheet().getRange(cell);
			range.setIgnoreProtected(true);
			range.setCellLocked(true);   
		} finally {
			BgNSHelper.objClear(formulaSet);
			BgNSHelper.objClear(formulaMap);
		}
	}
	
	protected boolean isUpdate(Cell cell) {
		if (cell == null)
			return true;
		
		if (BgNFSHelper.checkHasFormulaOfCell(cell) && BgNFSHelper.checkHasBgItemInfo(cell)) {
			
			String isUpdate = BgNFSHelper.getItemFormula(cell).getExt(UPDATE);
			if (isUpdate.equals(Boolean.FALSE.toString()))
				return false;
			
		}
		return true;
	}
	
	
	protected void fillCollectRowData(Sheet table, Row collRow, int colAlias) {
		if (table == null || collRow == null)
			return ;
		
		int rowBegin = collRow.getRow() + 1;
		int rowEnd = rowBegin;
		int rowIndex = 0, rowCount = table.getMaxRowIndex()+1;
		int colIndex = 0, colCount = table.getMaxColIndex()+1;
		
		Cell cell = null;
		Row row = null;
		NumberExpandInfo expandInfo = null, tmpExpand = null;
		BigDecimal value = null;
		if(collRow.getCell(colAlias, true).getUserObject(BgNConstants.NEName) != null){			
			expandInfo = BgNFCHelper.getNumberExpandInfo(collRow.getCell(colAlias, false)) ;
			for(rowIndex=rowBegin; rowIndex<rowCount; rowIndex++) {
				row = table.getRow(rowIndex, true);
				cell = row.getCell(colAlias, true);			
				if (cell.getValue() != null && cell.getUserObject(BgNConstants.NEName) != null) {
					tmpExpand = BgNFCHelper.getNumberExpandInfo(cell);				
					if (expandInfo.getLevel() == tmpExpand.getLevel())
						rowEnd = rowIndex;
					else
						break;
				}
			}
		}
		for(colIndex=0; colIndex<colCount; colIndex++) {
			if (BgNFSHelper.checkHasFormulaOfCol(table, colIndex, rowBegin, rowEnd + 1)) {
				value = BgConstants.BIGZERO;
				for(rowIndex=rowBegin; rowIndex<=rowEnd; rowIndex++) {
					cell = table.getCell(rowIndex, colIndex, true);
					if (cell != null && cell.getValue() != null) {
						try {
							value = value.add(new BigDecimal(cell.getValue().toString().replaceAll(",", "")));
						} catch(Exception ex) {
						}
					}
				}
				BgNFSHelper.setValue(collRow.getCell(colIndex, true), value);
				collRow.getCell(colIndex, true).setUserObject(BgNConstants.BG_COLLECT, BgNConstants.BG_COLLECT);
				if(BgNFSHelper.checkHasFormulaOfCell(table.getCell(collRow.getRow() - 1, colIndex, false))){
					BgNFSHelper.setValue(collRow.getCell(colIndex, true), value);
					collRow.getCell(colIndex, true).setUserObject(BgNConstants.BG_FORMULA, 
							BgNFSHelper.getFormulaOfCell(table.getCell(collRow.getRow() - 1, colIndex, false)));
				}
			}
		}
	}
	
	protected Object formatCellDisplay(int rowIndex, int colIndex, Cell cell, Object svalue) {
		if (svalue == null)
			return svalue;
		
		if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
			String formulaString = BgNFSHelper.getFormulaOfCell(cell);
			String[] parameter = BgFSHelper.getFormulaInfo(formulaMap, formulaString);
			
			int precision = 0;
			BigDecimal measure = BgConstants.ONE, value = null;
			String currencyNumber = null;
			CurrencyInfo currencyInfo = null;
			
			if (parameter != null && parameter[BgSHelper.paraElement] != null) {
				if (measureMap.containsKey(parameter[BgSHelper.paraElement])) {
					MeasureUnitInfo measureInfo = (MeasureUnitInfo) measureMap.get(parameter[BgSHelper.paraElement]);
					measure = measureInfo.getCoefficient();
				} else {
					measure = defMeasureUnit.getCoefficient();
				}
			}
			
			/* 处理币别信息 */
			if (parameter != null && parameter[BgSHelper.paraCurrency] != null)
				currencyNumber = parameter[BgSHelper.paraCurrency];
			else
				currencyNumber = defCurrency.getNumber();
			
			if (currencyMap.containsKey(currencyNumber)) {
				currencyInfo = (CurrencyInfo) currencyMap.get(currencyNumber);
				precision = currencyInfo.getPrecision();
			} else {
				precision = BgSHelper.decimal;
			}
			
			String text = null;
			try {
				value = new BigDecimal(svalue.toString().replaceAll(",", "")).divide(measure, precision, BigDecimal.ROUND_HALF_UP);
				DecimalFormat decFormat = new DecimalFormat("#,##0." + BgFormHelper.getNumberFormat("0", precision));
				text = decFormat.format(value.doubleValue());
			} catch(Exception ex) {
				text = svalue.toString();
			}
			return text;
		} else {
			if (BgNFSHelper.checkIsNumericOfCell(cell)) {
				String text = null;
				int precision = defCurrency.getPrecision();
				BigDecimal measure = defMeasureUnit.getCoefficient(), value = null;
//				if(cell != null && cell.getEditor() != null &&
//				cell.getEditor() instanceof BgFormCellEditer && ((BgFormCellEditer)cell.getEditor()).getMeasure() != null)
//				measure = ((BgFormCellEditer)cell.getEditor()).getMeasure();
				try {
					value = new BigDecimal(svalue.toString().replaceAll(",", "")).divide(measure, precision, BigDecimal.ROUND_HALF_UP);
					DecimalFormat decFormat = new DecimalFormat("#,##0." + BgFormHelper.getNumberFormat("0", precision));
					text = decFormat.format(value.doubleValue());
				} catch(Exception ex) {
					text = svalue.toString();
				}
				return text;
			}
		}
		return svalue;
	}
	
	
	public void actionReportCheck_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			actionSubmit_actionPerformed(e);
		}
		
		if (checkReport())
			MsgBox.showInfo(this, getMessage("repotCheckSucceed"));
	}
	
	public void actionBgExamineCheck_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			actionSubmit_actionPerformed(e);
		}
		
		if (getExamineInterface().canExamine( getBgExmCheckReportId() )) {
			xNum = BgNFCHelper.returnExamineX(getBgExmCheckReportId(), this);
			Window win = SwingUtilities.getWindowAncestor(this);
			if (win instanceof Frame) {
				dialog = new LongTimeDialog((Frame) win);
			} else if (win instanceof Dialog) {
				dialog = new LongTimeDialog((Dialog) win);
			} else {
				dialog = null;
			}
			
			if (dialog != null) {
				dialog.setLongTimeTask(new ILongTimeTask() {
					public Object exec() throws Exception {
						Object[] result = getExamineInterface().examines( getBgExmCheckReportId(), xNum);
						Map bgExamineCheckMap = new HashMap();
						bgExamineCheckMap.put("BgExamineChecked", result[0]);
						bgExamineCheckMap.put("BgExamineCheckResult", result[1]);
						return bgExamineCheckMap;
					}
					
					public void afterExec(Object result) throws Exception {
						if (result != null && result instanceof HashMap) {
							openBgExamineViewUI(result, null);
						} else {
							MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "error"));
						}
					}
				});
				dialog.show();
			}
		} else {
			MsgBox.showInfo(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "cantCheckBgExamine"));
		}
	}
	
	protected boolean checkReport() throws Exception {
		boolean isExit = false;
		List check = getFormInterface().reportCheck( editData.getId(), getRefBgFormIds(), getBgExmCheckReportId(), BgDisCountFormInfo.class.getName() );
//		if (check.get(0).equals(Boolean.FALSE)) {
//		isExit = true;
//		UIContext uiCtx = new UIContext(this);
//		uiCtx.put("Title", EASResource.getString(BgFormHelper.STRRESOURCE, "ReportCheckError"));
//		uiCtx.put("ShowOpen", Boolean.FALSE);
//		SimpleNotePad.showMessageByModel(uiCtx, check.get(3).toString());
//		}
		if (check.get(0).equals(Boolean.FALSE)) {
			isExit = true;
			UIContext uiContext = new UIContext(this);
			uiContext.put("String",check.get(3).toString());
			uiContext.put("id",this.getUIContext().get("ID"));
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
			.create(ShowEditorPanelUI.class.getName(),uiContext, null);
			uiWindow.show();
		}
		BgNSHelper.objAllClear(check);
		
		/* 如果要求强制通过报表检查 */
		if (isExit)
			SysUtil.abort();
		return true;
	}
	
	protected BOSUuid[] getRefBgFormIds() {
		if (refBgFormIds == null) {
			refBgFormIds = new BOSUuid[1 + editData.getRefBgForms().size()];
			
			refBgFormIds[0] = editData.getId();
			for(int i=0, n=editData.getRefBgForms().size(); i<n; i++) {
				refBgFormIds[i+1] = editData.getRefBgForms().get(i).getId();
			}
		}
		return refBgFormIds;
	}
	
	protected BOSUuid[] getBgExmCheckReportId() {
		if (bgFormIds == null) {
			bgFormIds = new BOSUuid[1 + editData.getRefBgForms().size()];
			RefDisCountBgFormInfo refBgFormInfo = null;
			
			bgFormIds[0] = editData.getBgForm().getId();
			for(int i=0, n=editData.getRefBgForms().size(); i<n; i++) {
				refBgFormInfo = editData.getRefBgForms().get(i);
				bgFormIds[i+1] = refBgFormInfo.getBgForm().getId();
			}
		}
		return bgFormIds;
	}
	
	protected IBgExamineFacade getExamineInterface() throws Exception {
		return BgExamineFacadeFactory.getRemoteInstance();
	}
	
	protected void openBgExamineViewUI(Object obj, Boolean isModel) throws Exception {
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(BgExamineHelper.EXAMINE_DATA, obj);
		IUIWindow uiWindow = UIFactory.createUIFactory(openBgExamineViewType(isModel))
											.create(BgExamineViewUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	protected String openBgExamineViewType(Boolean isModel) {
		if (isModel != null && isModel.booleanValue())
			return UIFactoryName.MODEL;
		
		if (getUIContext().get(UIContext.OWNER) != null && 
				(getUIContext().get(UIContext.OWNER) instanceof MultiApproveUI || 
						getUIContext().get(UIContext.OWNER) instanceof MessageCenterUI)) {
			return UIFactoryName.MODEL;
		} else {
			return UIFactoryName.NEWWIN;
		}
	}
	
	
	protected String getMessage(String key) {
		return EASResource.getString("com.kingdee.eas.ma.budget.client.BgCollectResource", key);
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("kdtdata"));
		sic.add(new SelectorItemInfo("orgList"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("bgform.*")); 
		sic.add(new SelectorItemInfo("bgform.bgScheme.name"));
		sic.add(new SelectorItemInfo("refBgForms.*"));   
		sic.add(new SelectorItemInfo("refBgForms.bgForm.*"));
		sic.add(new SelectorItemInfo("refBgForms.bgForm.orgUnit.*"));
		sic.add(new SelectorItemInfo("refBgForms.bgForm.bgScheme.*"));
		sic.add(new SelectorItemInfo("cu.*"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("othercont"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("adjustmapdata"));
		return sic;
	}
	
	public SelectorItemCollection getBgFormInfo() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("data"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("bgScheme.*")); 
		sic.add(new SelectorItemInfo("bgTemplate.*"));
		sic.add(new SelectorItemInfo("bgPeriod.*"));
		sic.add(new SelectorItemInfo("currency.*"));
		sic.add(new SelectorItemInfo("sheetParam.*"));
		sic.add(new SelectorItemInfo("postils.*"));
		sic.add(new SelectorItemInfo("postils.report.id"));
		sic.add(new SelectorItemInfo("postils.sheet.id"));
		sic.add(new SelectorItemInfo("postils.creator.id"));
		sic.add(new SelectorItemInfo("postils.creator.name"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.*"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.id"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.name"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.number"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.id"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.name"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.number"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.coefficient"));
		sic.add(new SelectorItemInfo("measureUnit.*"));
		return sic;
	}
	
	public boolean destroyWindow() {
		boolean re = super.destroyWindow();
		if (re) {
			BgNSHelper.objClear(bgFormMap);
			BgNSHelper.objClear(adjustMap);
			BgNSHelper.objClear(bgSchemeMap);
			BgNSHelper.objClear(formulaMap);
			BgNSHelper.objClear(editorMap);
			BgNSHelper.objClear(renderMap);
			BgNSHelper.objClear(measureMap);
			BgNSHelper.objClear(currencyMap);
			BgNSHelper.objClear(refOrgUnitCol);
			
			removeNotify();
		}
		return re;
	}
		
	public void actionAdjustView_actionPerformed(ActionEvent e) throws Exception {	
		Sheet sheet = getSheetPage();
		Range range = sheet.getSelectionRange();
		Vector differNotesData = new Vector();
		String operation = EASResource.getString("com.kingdee.eas.ma.budget.client.BgCollectResource", "Collect");
		String adjustUser = null, auditUser = null, auditTime = null;
		Map bgItems = new HashMap();
		Map bgElements = new HashMap();
		DateFormat sdf = BgNationalFormatUIUtil.getNationalDateFormat();
		int maxRowIndex = sheet.getMaxRowIndex();
		int maxColIndex = sheet.getMaxColIndex();
		for (int i = 0; i < range.size(); i++) {
			CellBlock block = (CellBlock) range.getBlock(i);
			int rowEnd = block.getRow2() > maxRowIndex ? maxRowIndex :block.getRow2();
			int colEnd = block.getCol2() > maxColIndex ? maxColIndex :block.getCol2();
			for (int j = block.getRow(); j <= rowEnd; j++) {
				for (int k = block.getCol(); k <= colEnd; k++) {
					Cell cell = sheet.getCell(j, k, true);
					if (BgNFSHelper.checkHasFormulaOfCell(cell) &&
							!BgNFSHelper.isHided(sheet.getRow(j, false))) {					
						
						Cell orgCell = sheet.getCell(j, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
						if (orgCell != null && orgCell.getUserObject(BgNConstants.NEId) != null) {							
							String tmpFormId = getExpInfo(cell, BGFORMID);
							if(tmpFormId == null || tmpFormId.length() < 1){
								continue;
							}
							Map childAdjustMap = (Map)adjustMap.get(BOSUuid.read(tmpFormId));
							if(childAdjustMap == null){
								continue;
							}
							String formulaString = BgNFSHelper.getFormulaOfCell(cell);
							String[] formula = BgFSHelper.getFormulaInfo(formulaString);
							
							BgAdjustFormDiversityData tmpDiv = null;
							if (childAdjustMap.containsKey(formulaString)) {
								Object obj = childAdjustMap.get(formulaString);
								
								if (obj instanceof BgAdjustFormDiversityData) {
									tmpDiv = (BgAdjustFormDiversityData) obj;
									
								} else if (obj instanceof BgFormDiversityData) {
									tmpDiv = new BgAdjustFormDiversityData((BgFormDiversityData) obj);
								}
								
								if(tmpDiv.getNewValue().compareTo(tmpDiv.getOldValue()) == 0)
									continue;
								
								if (tmpDiv != null ) {
									Map data = new HashMap();
									//项目
									data.put("bgItem",formula[BgSHelper.paraItem]);
									bgItems = putBgItemTobgItems(bgItems, formula[BgSHelper.paraItem]);
									//要素
									data.put("bgElement",formula[BgSHelper.paraElement]);
									data.put("bgPeriod",formula[BgSHelper.paraPeriod]);
									if(!bgElements.containsKey(formula[BgSHelper.paraElement])){
										bgElements.put(formula[BgSHelper.paraElement],null);
									}
									//组织
									data.put("orgUnit",orgCell.getUserObject(BgNConstants.NEName).getValue().toString());
									//期间
									data.put("period", period);
									//操作
									data.put("operation", operation);
									//调整前数据
									if (tmpDiv.getOldValue() != null)
										data.put("oldValue", tmpDiv.getOldValue().divide(new BigDecimal("1"), defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
									else
										data.put("oldValue", new BigDecimal("0"));
									//调整后数据
									if (tmpDiv.getNewValue() != null)//defMeasureUnit.getCoefficient()
										data.put("newValue", tmpDiv.getNewValue().divide(new BigDecimal("1"), defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
									else
										data.put("newValue", new BigDecimal("0"));
									//差异
									data.put("difValue", getDifferValue(tmpDiv.getNewValue(), tmpDiv.getOldValue()));
									//调整人
									if(tmpDiv.getAdjustor() != null){
										UserInfo userInfo = UserFactory.getRemoteInstance().getUserInfo("where id = '"+tmpDiv.getAdjustor()+"'");
										adjustUser = userInfo.getName();
										data.put("adjustUser", adjustUser);
									}else{
										data.put("adjustUser", null);
									}
									
									//调整时间
									if(tmpDiv.getAdjustTime() != null)
										data.put("adjustTime", sdf.format(tmpDiv.getAdjustTime()));
									else
										data.put("adjustTime", null);
									//审批人-
									data.put("auditUser", auditUser);
									//审批时间-
									data.put("auditTime", auditTime);
									if(tmpDiv instanceof BgAdjustFormDiversityData)
										data.put("adjustReson",((BgAdjustFormDiversityData)tmpDiv).getAdjustReson());
									differNotesData.add(data);
								} 
							} 
						}
					}
				}
			}
		}
		IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getRemoteInstance();
		bgItems = iBgBudget.getSelectBgItemName(bgItems,defOrgUnitInfo.getId().toString());
		bgElements = iBgBudget.getSelectElementName(bgElements);
		
		differNotesData = putItemsToDiffData(bgItems, bgElements, differNotesData);
		setBgPeriodName(differNotesData);
		
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("differNotesData", differNotesData);
		
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(BgDifferNotesViewUI.class.getName(),
				uiContext,
				null,
				OprtState.VIEW);
		uiWindow.show();
	}
	
	private void setBgPeriodName(List differNotesData) throws BOSException{
		StringBuffer periodNums = new StringBuffer("");
		for(int i = 0,size = differNotesData.size();i<size; i++){
			Map data = new HashMap();
			String formula = null;
			data = (Map) differNotesData.get(i);
			if(i == 0)
				periodNums.append("('"+(String)data.get("bgPeriod")+"',");
			else if(i == size-1)
				periodNums.append("'"+(String)data.get("bgPeriod")+"')");
			else
				periodNums.append("'"+(String)data.get("bgPeriod")+"',");
		}
		IBgPeriod ibgPeriod = BgPeriodFactory.getRemoteInstance();
		EntityViewInfo view = new EntityViewInfo();		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		view.getSelector().addObjectCollection(sic);
		BgPeriodCollection periodCol = new BgPeriodCollection();
		try{
			periodCol = ibgPeriod.getBgPeriodCollection("select id,name,number where number in " + periodNums.toString());
			Map periodMap = new HashMap();
			if(periodCol != null){
				for(int i=0,size = periodCol.size();i<size;i++){
					BgPeriodInfo period = periodCol.get(i);
					periodMap.put(period.getNumber(),period.getName());
				}
			}
			
			for(int i = 0; i < differNotesData.size(); i++){
				Map data = new HashMap();
				String formula = null;
				data = (Map) differNotesData.get(i);
				String period = (String)data.get("bgPeriod");
				data.put("bgPeriod",periodMap.get(period));
			}
				
		}catch(Exception e){
			logger.error("budget error:", e);
		}
	}
	private Vector putItemsToDiffData(Map bgItems, Map bgElements, Vector differNotesData) {
		for(int i = 0; i < differNotesData.size(); i++){
			Map data = new HashMap();
			String formula = null;
			data = (Map) differNotesData.get(i);
			formula = (String) data.get("bgItem");
			formula = combItem(bgItems, formula);
			data.put("bgItem",formula);
			data.put("bgElement",bgElements.get((String)data.get("bgElement")));
		}
		return differNotesData;
	}
	
	
	private String combItem(Map bgItems, String formula) {
		String[] items = formula.split(",");
		String item = "";
		for(int i = 0; i < items.length; i++){
			if(i != 0){
				item = item + "_";
			}
			item = item + (String) bgItems.get(items[i]);			
		}
		return item;
	}
	
	
	private Map putBgItemTobgItems(Map bgItems, String bgItem) {
		String[] items = bgItem.split(",");
		if(items != null && items.length > 0){
			for(int i = 0; i < items.length; i++){
				if(!bgItems.containsKey(items[i])){
					bgItems.put(items[i], null);
				}
			}
		}
		return bgItems;
	}
	
	
	private BigDecimal getDifferValue(BigDecimal newValue, BigDecimal oldValue) {
		if (newValue == null)
			newValue = BgConstants.BIGZERO;
		if (oldValue == null)
			oldValue = BgConstants.BIGZERO;
		return newValue.subtract(oldValue).divide(new BigDecimal("1"), defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP);
	}
	
	
	public void actionDataExamine_actionPerformed(ActionEvent e) throws Exception {
		//提示操作时间长,是否继续
//		if(MsgBox.showConfirm2(this, "检查时间较长，是否继续？") != MsgBox.YES){
//			return;
//		}	
		getCurrentPage().setCalculate(false);
		verify(VERIFY_HAND);
		getCurrentPage().setCalculate(true);  
		
//		//将表中的组织都展开
//		showOrgUnitData();
//		
//		//检查数据
//		List msg = new Vector(); 
//		
//		
//		msg = examineData();
//		
//		//还原组织展开
//		showOrgUnitData();
//		
//		//输出报告
//		if(msg.size() < 1){
//		MsgBox.showWarning(this, "表中上下级数据平衡！");
//		}else{
//		showMsg(msg);
//		}		
//		Book table = getCurrentPage();
//		if(table.getSheet(0) != null){
//			Range selectRange = table.getSheet(0).getRange(0,0);		
//			selectRange.select();
//		}
	}
	
	/**
	 * 根据系统参数“上级组织数据等于直接下级组织数据之和”
	 * 来判断是否需要做自动检查
	 * @throws Exception 
	 */
	private void autoCheckBySystemParam() throws Exception{
		if(BgSHelper.isParentOrgBudgetValueEqualAllChildOrgSum(null)){
			getCurrentPage().setCalculate(false);
			verify(VERIFY_AUTO);
			getCurrentPage().setCalculate(true);
		}
							
	}


	private void verify(String isAuto) throws Exception{
		List msg = examineDataNew();
		if(msg != null && msg.size() > 0){
			showMsg(msg);
			if(VERIFY_AUTO.equals(isAuto)){
				parentNotEqualsChildCantSave = true;
			}	        	
		}
    	else if(msg != null && msg.size() < 1){
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", "parentEqualChild"));
		}	
		
	}
	
	private void showMsg(List msg) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("dataExamine", msg);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(BgDataExamineViewUI.class.getName(), 
				uiContext);
		uiWindow.show();
		/*UIContext uiContext = new UIContext(this);
		uiContext.put("dataExamine", msg);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
		.create(BgDataExamineViewUI.class.getName(),
				uiContext,
				null,
				OprtState.VIEW);
		uiWindow.show();*/		
	}
	
	/**
	 * 描述：优化上下级数据是否相等检查
	 * examineData 作废
	 * @return
	 * @throws Exception
	 */
	private List examineDataNew() throws Exception {
		List erorrData = new Vector();
		Vector erorr = null;
		Map bgItems = new HashMap();
		if(bgFormMap == null || bgFormMap.isEmpty())
			return erorrData;
		for(Iterator it = bgFormMap.keySet().iterator();it.hasNext();){
			BOSUuid orgId = (BOSUuid)it.next();
			CostCenterOrgUnitInfo orgInfo = (CostCenterOrgUnitInfo)orgSheetCheckMap.get(orgId);
			if(orgInfo == null)
				continue;
			String longNumber = orgInfo.getLongNumber();
			String name = orgInfo.getName();
			int longNumberLength = longNumber.split("!").length;
			Map childBgFormMap = null;
			//上级预算表id
			String bgFormId = (String)bgFormMap.get(orgId);
			
			//取出当前组织对应的所有下级组织汇编预算表
			for(Iterator itOrg = orgSheetCheckMap.keySet().iterator();itOrg.hasNext();){
				BOSUuid childOrgId = (BOSUuid)itOrg.next();
				CostCenterOrgUnitInfo childOrgInfo = (CostCenterOrgUnitInfo)orgSheetCheckMap.get(childOrgId);
				if(childOrgInfo == null)
					continue;
				String childLongNumber = childOrgInfo.getLongNumber();
				if(childLongNumber.startsWith(longNumber+"!")){
					int childLongNumLength = childLongNumber.split("!").length;
					if(childLongNumLength - longNumberLength == 1){
						if(childBgFormMap == null)
							childBgFormMap = new HashMap();
						childBgFormMap.put((String)bgFormMap.get(childOrgId),null);						
					}
				}
			}
			
			if(childBgFormMap != null && childBgFormMap.size()>0){
				
				Set formulaSet = getAllParentBgFormFormula(bgFormId ,(Map)adjustMap.get(bgFormId));
				
				for(Iterator formulaIt = formulaSet.iterator();formulaIt.hasNext();){		
				
					String formulaStr = (String)formulaIt.next();
					BigDecimal parentValue = null;
					BigDecimal sumValue = BgConstants.BIGZERO ;
					//如果下级组织预算表没有该项目公式不做检查
					boolean needSum = false;
					
					Map adjMap = (Map)adjustMap.get(BOSUuid.read(bgFormId));
					//如果有差异记录则用差异记录的newValue做为上级预算数
					if(adjMap != null && adjMap.containsKey(formulaStr)){
						BgFormDiversityData data = (BgFormDiversityData)adjMap.get(formulaStr);
						parentValue =  data.getNewValue();
					}else if(oldData.containsKey(bgFormId +formulaStr)){
						parentValue = (BigDecimal)oldData.get(bgFormId +formulaStr);
					}				
					
					//计算下级组织数据之和
					for(Iterator childIt = childBgFormMap.keySet().iterator();childIt.hasNext();){
						String childBgFormId = (String)childIt.next();
						Map childAdjMap = (Map)adjustMap.get(BOSUuid.read(childBgFormId));
						//如果有差异记录则用差异记录的newValue做为下级预算数
						if(childAdjMap != null && childAdjMap.containsKey(formulaStr)){
							BgFormDiversityData data = (BgFormDiversityData)childAdjMap.get(formulaStr);
							BigDecimal childValue =  data.getNewValue();
							sumValue = sumValue.add(childValue);
							needSum = true;
						}else if(oldData.containsKey(childBgFormId+formulaStr)){
							BigDecimal childValue = (BigDecimal)oldData.get(childBgFormId+formulaStr);
							needSum = true;
							sumValue = sumValue.add(childValue);
						}
					}
					
					if(parentValue != null && needSum){
						erorr = new Vector();
						String[] formula = BgFSHelper.getFormulaInfo(formulaStr);
//						判断大小于 
						if(parentValue.compareTo(sumValue) > 0){
							erorr.add(0,name);
							erorr.add(1,formula[BgSHelper.paraItem]);
							bgItems = putBgItemTobgItems(bgItems, formula[BgSHelper.paraItem]);
							erorr.add(2,"");
							erorr.add(3,parentValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
							erorr.add(4,">");
							erorr.add(5,sumValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
							
						} else if (parentValue.compareTo(sumValue) < 0) {
							erorr.add(0,name);
							erorr.add(1,formula[BgSHelper.paraItem]);
							bgItems = putBgItemTobgItems(bgItems, formula[BgSHelper.paraItem]);
							erorr.add(2,"");
							erorr.add(3,parentValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
							erorr.add(4,"<");
							erorr.add(5,sumValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
						}
					}
					if (erorr != null && erorr.size() > 0) {
						erorrData.add(erorr);
						erorr = null;
					}
					
				}
			}
			
		}

		IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getRemoteInstance();
		bgItems = iBgBudget.getSelectBgItemName(bgItems,defOrgUnitInfo.getId().toString());		
		erorrData = putItemsToDiffData(bgItems, erorrData);
		return erorrData;	
	}
	
	/**
	 * 得到上级预算表所有的项目公式
	 * @param bgFormId
	 * @param adjustMap
	 * @return
	 */
	private Set getAllParentBgFormFormula(String bgFormId,Map adjustMap){
		Set formulaSet = new HashSet();
		for(Iterator it = oldData.keySet().iterator();it.hasNext();){
			String key = (String)it.next();
			if(key != null && key.startsWith(bgFormId)){
				String formulaStr = key.substring(bgFormId.length(),key.length());
				if(formulaStr != null)
					formulaSet.add(formulaStr);
			}
		}
		if(adjustMap != null){
			for(Iterator it = adjustMap.keySet().iterator();it.hasNext();){
				String formulaStr = (String)it.next();
				if(formulaStr != null)
					formulaSet.add(formulaStr);
			}
		}
		return formulaSet;
	}
	
	private List examineData() throws Exception {
		List erorrData = new Vector();
		Vector erorr = null;
		Map bgItems = new HashMap();
		Book book = getCurrentPage();
		Sheet sheet = null;
		Cell orgCell = null;
		for(int n = 0; n < book.getSheetCount(); n++ ){
			sheet = book.getSheet(n);		
			int rows = sheet.getMaxRowIndex() + 1;
			int cols = sheet.getMaxColIndex() + 1;
			
			for(int i = 0; i < rows ; i++){
				orgCell = sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
				
				if(orgCell != null && orgCell.getUserObject(BgNConstants.NEName)  != null 
						&& BgNFCHelper.getNumberExpandInfo(orgCell).getName().toString().equals(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect"))){
					for(int y = 0; y < cols ; y++){
						Cell cell = sheet.getCell(i-1, y, true);
						Cell cell1 = sheet.getCell(i, y, true);
						
						if(BgNFSHelper.checkHasItemFormula(cell)){
							erorr = new Vector();
							String formulaString = BgNFSHelper.getFormulaOfCell(cell);
							String[] formula = BgFSHelper.getFormulaInfo(formulaString);
							BigDecimal itemValue = BgNConstants.ZERO;
							BigDecimal sumValue = BgNConstants.ZERO;
							//确认大小于
							if (!BgNFSHelper.checkIsEmptyValue(cell))
								itemValue = new BigDecimal(BgNFSHelper.getValue(cell).toString());
							if (!BgNFSHelper.checkIsEmptyValue(cell1))
								sumValue = new BigDecimal(BgNFSHelper.getValue(cell1).toString());
							//判断大小于 
							if(itemValue.compareTo(sumValue) > 0){
								erorr.add(0,BgNFCHelper.getNumberExpandInfo(sheet.getCell(i-1, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true)));
								erorr.add(1,formula[BgSHelper.paraItem]);
								bgItems = putBgItemTobgItems(bgItems, formula[BgSHelper.paraItem]);
								erorr.add(2,TableToolkit.xy2range(y,i-1));
								erorr.add(3,itemValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
								erorr.add(4,">");
								erorr.add(5,sumValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
								
							} else if (itemValue.compareTo(sumValue) < 0) {
								erorr.add(0,BgNFCHelper.getNumberExpandInfo(sheet.getCell(i-1, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true)));
								erorr.add(1,formula[BgSHelper.paraItem]);
								bgItems = putBgItemTobgItems(bgItems, formula[BgSHelper.paraItem]);
								erorr.add(2,TableToolkit.xy2range(y,i-1));
								erorr.add(3,itemValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
								erorr.add(4,"<");
								erorr.add(5,sumValue.divide(BgNConstants.ONE, defCurrency.getPrecision(), BigDecimal.ROUND_HALF_UP));
							}
							
						}
						if (erorr != null && erorr.size() > 0) {
							erorrData.add(erorr);
							erorr = null;
						}
					}
					
				}
				
			}
		}
		IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getRemoteInstance();
		bgItems = iBgBudget.getSelectBgItemName(bgItems,defOrgUnitInfo.getId().toString());		
		erorrData = putItemsToDiffData(bgItems, erorrData);
		return erorrData;		
	}
	
	
	private List putItemsToDiffData(Map bgItems, List erorrData) {
		for(int i = 0; i < erorrData.size(); i++){
			List data = new Vector();
			String formula = null;
			data = (Vector) erorrData.get(i);
			formula = (String) data.get(1);
			formula = combItem(bgItems, formula);
			data.set(1,formula);
		}
		return erorrData;
	}
	
	
	private void showOrgUnitData() throws Exception {
		Book table = getCurrentPage();
		Sheet sheet = null;
		for(int n = 0; n < table.getSheetCount(); n++){
			sheet = table.getSheet(n);
			if(expandColIndex.get(sheet.getID()) == null)
				continue;
			for (int i = 0; i < sheet.getMaxRowIndex() + 1; i++) {			
				Cell orgCell = sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
				if (orgCell != null 	&& orgCell.getUserObject(BgNConstants.NEId) != null) {
					NumberExpandInfo expandInfo = BgNFCHelper.getNumberExpandInfo(sheet.getCell(
							i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true));
					if (expandInfo != null	&&  !expandInfo.getName().equals(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect")))
						setTreeDisplayStyle(sheet, sheet.getRow(i, true), expandInfo,
								((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
				}
				//table = getCurrentPage();
			}
		}
	}
	
	
	public void actionCancelHide_actionPerformed(ActionEvent e) throws Exception {
		if(hideCol == null)
			return;
		Sheet table =  getSheetPage();
		Range range = table.getSelectionRange();
		
		for (int i = 0; i < range.size(); i++) {
			CellBlock block = (CellBlock) range.getBlock(i);
			for (int k = block.getCol(); k <= block.getCol2(); k++) {				
				if(table.getColumn(k, true) == null)
					continue;
				Range colRange = table.getColRange(k, k);
				colRange.setIgnoreProtected(true);
				colRange.setHidden(false,false);				
				if(hideCol.get(table.getID()) != null)
					((HashMap)hideCol.get(table.getID())).remove(new Integer(k));
			}
		}	
		otherData.put("hideCol", BgHelper.storeHashMapToByte(hideCol));
	}
	
	
	public void actionHideCol_actionPerformed(ActionEvent e) throws Exception {
		if(hideCol == null)
			hideCol = new HashMap();
		Sheet sheet =  getSheetPage();
		Range range = sheet.getSelectionRange();
		if(hideCol.get(sheet.getID()) == null)
			hideColNum = new HashMap();
		else
			hideColNum = (HashMap) hideCol.get(sheet.getID());
		for (int i = 0; i < range.size(); i++) {
			CellBlock block = (CellBlock) range.getBlock(i);
			for (int k = block.getCol(); k <= block.getCol2(); k++) {
				if(sheet.getColumn(k, true) == null)
					continue;
				Range colRange = sheet.getColRange(k, k);
				colRange.setIgnoreProtected(true);
				colRange.setHidden(true,false);		
				hideColNum.put(new Integer(k),null);
				hideCol.put(sheet.getID(), hideColNum);				
			}
		}	
		if(hideCol != null && hideCol.size() > 0){        	
			otherData.put("hideCol", BgHelper.storeHashMapToByte(hideCol));			
		}
	}
	
	/**
	 * 重计算检查及保存临时数据
	 * @author kaifei_yi
	 * @throws Exception
	 */
	private void calculateCheckAndSaveTmpData() throws Exception{
		checkFormulaIsEctype();
		parentNotEqualsChildCantSave = false;//默认为上级数据不等于下级数据时可以保存
		adjustCheckIsNotPass =  false;//调整检查默认通过
		autoCheckBySystemParam();
		isFromCalculate = true;
		if(parentNotEqualsChildCantSave){
			MsgBox.showWarning(
					EASResource.getString("com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", 
					"parentNotEqualChildCantCalucate"));
			isFromCalculate = false;
			SysUtil.abort();
		}    	
		boolean needsCheck = BgSHelper.isAdjustCheckRequired(null);
		if(needsCheck)
			adjustCheck(needsCheck);
		if(adjustCheckIsNotPass){
			MsgBox.showWarning(EASResource.getString(
					"com.kingdee.eas.ma.budget.client.BgAdjustCheckResource", "adjustCheckNoPassCantCalculate"));
			isFromCalculate = false;
			SysUtil.abort();
		}
		isFromCalculate = false;
		//数据校验通过则更新调整数据
		getInterface().updateAdjustData(editData.getId().toString(), adjustMap);
		isChanged = false;
	}
	
	public void actionRefCalculate_actionPerformed(ActionEvent e) throws Exception {
		
		refCalDataMap = new HashMap();
		refCalAdjustMap = new HashMap();
		//actionSubmit_actionPerformed(e);
		//获得组织的map，key为longNumber,value为id。并排序
		Map orgMapForF7 = new HashMap();
		String rootOrgId = null;
		Set orgId = new HashSet(); 
		orgMapForF7.put(defOrgUnitInfo.getId().toString(), editData.getBgForm().getId().toString());
		rootOrgId = defOrgUnitInfo.getId().toString();
		orgId.add(rootOrgId) ;
		RefDisCountBgFormCollection cols = editData.getRefBgForms();
		for (int i = 0, size = cols.size(); i < size; i++) {
			orgMapForF7.put(cols.get(i)
					.getBgForm().getOrgUnit().getId().toString(), cols.get(i).getBgForm().getId().toString());
			orgId.add(cols.get(i).getBgForm().getOrgUnit().getId().toString());
		}
		
		BgSelectCostCenterOrgViewF7 orgF7 = new BgSelectCostCenterOrgViewF7(this);	
		orgF7.setOrgMap(orgMapForF7);
		orgF7.setCuID(rootOrgId);	        
		orgF7.setMultiSelect(true);	       
		orgF7.setIsShowSub(true);
		orgF7.setTitle(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "SelectOrgUnit"));
		
		FilterInfo filter = orgF7.getOuterFilterInfo() ;
		if( filter == null)
			filter = new FilterInfo() ; 
		
		filter.getFilterItems().add(new FilterItemInfo("unit.id", orgId, CompareType.INCLUDE)) ;
		orgF7.setOuterFilterInfo(filter);
		
		orgF7.show();
		
		OrgUnitInfo[] orgsf7 = (OrgUnitInfo[]) orgF7.getData();
		if(orgsf7 == null || orgsf7.length == 0)
			return;
		
		
		Map orgMap = new LinkedHashMap();
		orgMap = getCalOrg(orgsf7, orgMapForF7);
		//根据longNum排序
		List orgList = new ArrayList();
		String longNum = null;
		for(Iterator iter=orgMap.keySet().iterator(); iter.hasNext();) {
			longNum = (String) iter.next();
			orgList.add(longNum);
		}
		Object[] longNums = new Object[orgList.size()];
		//得到排好序的longnum数组
		longNums = orgList.toArray();
		//得到重计算顺序id
		List orgs = new ArrayList();
		for(int i = longNums.length - 1; i >= 0 ; i--){
			orgs.add(orgMap.get(longNums[i].toString()));
		}
		
		//R130315-0045    回迁  R121115-0319  重计算时不检查上下级的预算数是否一致，在保存的时候检查
		//calculateCheckAndSaveTmpData()方法里上下级预算数是否一致检查、项目公式是否得复检查
		//是否强制进行调整检查，三个检查中在汇编表保存的时候都有检查，因此取消此方法的调用
//		calculateCheckAndSaveTmpData();
		//重计算 增加滚动条
		UIContext map = new UIContext(this);
		IUIWindow window =UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgressDialogUI.class.getName(),map);
		if(window != null && window.getUIObject() instanceof ProgressDialogUI){
			ProgressDialogUI ui = (ProgressDialogUI) window.getUIObject();
			initParameterValue(ui, orgs, editData.getBgForm().getId().toString(), editData.getId().toString());
			ui.init();	
			window.show();
			putReturnDataToMap(ui.getReturnData());
			updateAdjustMap(ui.getReturnData());
		}			
//		反添数据
		updateCellWithRefCal();
		// R130315-0045  预算分解时最上级组织设置了求和公式的单元格数据无法得到（包括汇编）
//		calculateCheckAndSaveTmpData();
	}
	
	private void updateAdjustMap(List returnData){
		Map reCalMap = null;
		if (returnData == null)
			return;
		Object[] obj = new Object[3];
		for (int i = 0; i < returnData.size(); i++) {
			if (returnData.get(i) instanceof Object[]) {
				obj = (Object[]) returnData.get(i);
				if (obj[0] != null && obj[1] != null) {
					String bgFormId = (String)obj[0];
					Map adjMap = (Map)adjustMap.get(BOSUuid.read(bgFormId));
					if(adjMap == null){
						adjMap = new HashMap();
						adjustMap.put(BOSUuid.read(bgFormId),adjMap);
					}
					reCalMap = (HashMap)obj[1];
					if(reCalMap!=null && reCalMap.size()>0)
					{
						for(Iterator it = reCalMap.keySet().iterator();it.hasNext();)
						{
							String formulaStr =(String)it.next();
							Object objValue = reCalMap.get(formulaStr);
							BigDecimal value = null;
							if (objValue instanceof BigDecimal)
								value = (BigDecimal)objValue;
							if (objValue instanceof String){
								try{
									value = new BigDecimal((String)objValue);
								}catch(Exception e){
									value = BgConstants.BIGZERO;
								}
							}
							if(adjMap.containsKey(formulaStr))
							{
								BgAdjustFormDiversityData data = (BgAdjustFormDiversityData)adjMap.get(formulaStr);
								
								if(value != null && data != null)
								{
									data.setNewValue(value);
								}
							}
							else
							{
								if(oldData.containsKey(bgFormId+formulaStr))
								{
									BigDecimal oldValue = null ;
									oldValue = (BigDecimal)oldData.get(bgFormId+formulaStr);
									if(oldValue == null)
										oldValue = BgConstants.BIGZERO; 
									if(value != null && oldValue.compareTo(value) != 0 )
									{
										BgAdjustFormDiversityData data = new BgAdjustFormDiversityData();
										data.setFormula(formulaStr);
										data.setOldValue(oldValue);
										data.setNewValue(value);
										data.setAdjustor(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
										data.setAdjustTime(new Timestamp(System.currentTimeMillis()));
										adjMap.put(formulaStr,data);
									}
									
								}
							}
							
						}
					}
					reCalMap = null;
				}
			}
		}
		
	}
	
	private void showOrgUnitDataEnd() throws Exception {
		Book table = getCurrentPage();
		Sheet sheet = null;
		for(int n = 0; n < table.getSheetCount(); n++){
			sheet =table.getSheet(n);
			if(expandColIndex.get(sheet.getID()) == null)
				continue;
			for (int i = 0; i < sheet.getMaxRowIndex()+1 ; i++) {
				if (sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true).getValue() != null 	
						&& sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true).getUserObject(BgNConstants.NEId) != null) {
					NumberExpandInfo expandInfo = BgNFCHelper.getNumberExpandInfo(sheet.getCell(
							i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true));
					if (expandInfo != null	&&  !expandInfo.getName().equals(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect"))
							&& !BgNFSHelper.isHided(sheet.getRow(i, true)))
						setTreeDisplayStyleEnd(sheet, sheet.getRow(i, true), expandInfo,
								((Integer)expandColIndex.get(sheet.getID())).intValue());
				}
			}
		}
	}
	
	private void setTreeDisplayStyleEnd(Sheet sheet, Row row, NumberExpandInfo expandInfo, int colIndex) {	    	
		expandTableEnd(sheet, row, expandInfo, colIndex);	 	 
	}
	
	protected void expandTableEnd(Sheet sheet, Row row, NumberExpandInfo parentExpandInfo, int colIndex) {
		if (row == null || parentExpandInfo == null)
			return ;
		
		Row child = null;
		NumberExpandInfo expandInfo = null;
		String longnumber = null, plongnumber = null;
		
		plongnumber = parentExpandInfo.getLongNumber();
		if (parentExpandInfo.isExpandStatus()) {
			parentExpandInfo.setExpandStatus(false);
			BgNFCHelper.setNumberExpandInfo(row.getCell(colIndex, true), parentExpandInfo);
			/* 如果是展开则关闭 */
			
			int rowIndex = row.getRow() + 1;
			int rowCount = sheet.getMaxRowIndex() + 1;
			for(; rowIndex<rowCount; rowIndex++) {
				child = sheet.getRow(rowIndex, true);
				if(child.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
					expandInfo = null;
				else
					expandInfo =  BgNFCHelper.getNumberExpandInfo(child.getCell(colIndex, true));
				if (expandInfo != null) {
					longnumber = expandInfo.getLongNumber();
					
					if (longnumber.startsWith(plongnumber + "!") ) {
						BgNFSHelper.setHided(child, true);
					} else {
						break;
					}
				}
			}
		} else {
			parentExpandInfo.setExpandStatus(true);
			BgNFCHelper.setNumberExpandInfo(row.getCell(colIndex, true), parentExpandInfo);
			/* 如果是关闭则展开 */
			int rowIndex = row.getRow() + 1;
			int rowCount = sheet.getMaxRowIndex() + 1;
			if (rowIndex >= rowCount) {
				addChildOrgUnitByCol(sheet, parentExpandInfo, rowIndex, colIndex);
			} else {
				for(; rowIndex<rowCount; rowIndex++) {
					child = sheet.getRow(rowIndex, true);
					if(child.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
						expandInfo = null;
					else
						expandInfo = BgNFCHelper.getNumberExpandInfo(child.getCell(colIndex, true));
					if (expandInfo != null) {
						longnumber = expandInfo.getLongNumber();
						
						if (longnumber.startsWith(plongnumber + "!")  ) {
							BgNFSHelper.setHided(child, false);
							if (!expandInfo.isLeaf())
								expandInfo.setExpandStatus(false);
							if(sheet.getRow(rowIndex+1, false) != null && isCol(sheet, sheet.getRow(rowIndex+1, true), colIndex))
								expandInfo.setExpandStatus(true);
							BgNFCHelper.setNumberExpandInfo(child.getCell(colIndex, true), expandInfo);					
						} 						
					} 					
				}
			}
		}
	}
	
	
	
	private void putReturnDataToMap(List returnData) {
		if (returnData == null)
			return;
		Object[] obj = new Object[3];
		for (int i = 0; i < returnData.size(); i++) {
			if (returnData.get(i) instanceof Object[]) {
				obj = (Object[]) returnData.get(i);
				if (obj[0] != null && obj[1] != null) {
					refCalAdjustMap.put(obj[0], obj[1]);
				}
				if(obj[2] != null)
					valueMap = (Map)obj[2];
			}
		}		
	}
	
	
	private void initParameterValue(ProgressDialogUI ui, List orgs, String mainFormId , String bgColId) {
		Class[] cla = new Class[8];
		cla[0] = String.class;
		cla[1] = String.class;
		cla[2] = boolean.class;
		cla[3] = String.class;
		cla[4] = String.class;	
		cla[5] = Map.class;
		cla[6] = String.class;
		cla[7] = String.class;
		ui.setUITitle(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","159_BgColForPeriodEditUI"));
		ui.setInvokeClass(this.getMetaDataPK().getFullName());
		ui.setInvokeMethod("invokeMethodNew");
		ui.setInvokeMethodParmeterType(cla);
		
		Vector vecPrameterValue= new Vector();
		String value = null;
		String key = null;
		Object[] objOld = null;
		Object[] objNew = null;
		for(int i = 0; i < orgs.size(); i++) {
			key = (String) orgs.get(i);
			value = (String) refCalOrgs.get(orgs.get(i));
			if(objOld != null){
				objNew = new Object[8];
				objNew[0] = objOld[0];
				objNew[1] = objOld[1];
				objNew[2] = Boolean.TRUE;
				objNew[3] = value;
				objNew[4] = key;	
				objNew[5] = adjustMap;
				objNew[6] = mainFormId;
				objNew[7] = bgColId;
				objOld = new Object[3];
				objOld[0] = value;
				objOld[1] = key;
				objOld[2] = Boolean.FALSE;
			}
			else{
				objOld = new Object[3];
				objOld[0] = value;
				objOld[1] = key;
				objOld[2] = Boolean.FALSE;
				objNew = new Object[8];
				objNew[0] = value;
				objNew[1] = key;
				objNew[2] = Boolean.FALSE;
				objNew[3] = value;
				objNew[4] = key;		
				objNew[5] = adjustMap;
				objNew[6] = mainFormId;
				objNew[7] = bgColId;
			}
			vecPrameterValue.add(objNew);
		}
		if(objOld != null){
			objNew = new Object[8];
			objNew[0] = objOld[0];
			objNew[1] = objOld[1];
			objNew[2] = Boolean.TRUE;
			objNew[3] = value;
			objNew[4] = key;	
			objNew[5] = adjustMap;
			objNew[6] = mainFormId;
			objNew[7] = bgColId;
			vecPrameterValue.add(objNew);
		}
		
		
		int size = vecPrameterValue.size();
		int objNewlength= objNew.length;
		Object[][] valueMethod= new Object[size][objNewlength];
		for (int i=0 ;i<size;i++){
			Object[] obj = (Object[]) vecPrameterValue.get(i);
			for(int j=0;j<objNewlength;j++){
				valueMethod[i][j] = obj[j];
			}
		}
		ui.setInvokeMethodParmeterValue(valueMethod);
		ui.setCountSum(vecPrameterValue.size()-1);
	}
	
	public Object invokeMethodNew(String orgThis,String bgFormId,boolean isTrueUpdate, 
			String orgNext,String nextBgFormId, Map adjustMap, String mainFormId, String bgColId)throws Exception{
		Object[] obj = new Object[3];
		try{
			boolean isAssignSuccess = true;
			if(orgThis != null && bgFormId != null && orgNext != null && nextBgFormId != null ){
				if(isTrueUpdate){
					try{
						Map adjMap = null;
						if(adjustMap != null)
							adjMap = (Map)adjustMap.get(BOSUuid.read(bgFormId));
						Object[] obj1 = getInterface().calculate(bgFormId,mainFormId,bgColId,adjMap);
						if(obj1 != null && obj1.length == 2){
							Object[] obj2 = new Object[3];
							obj2[0] = bgFormId;
							obj2[1] = (HashMap) obj1[0];
							if(bgFormId.equals(mainFormId))
								obj2[2] = (Map)obj1[1];
							else
								obj2[2] = null;
							obj[2] = obj2;
						}
						if(isAssignSuccess){
							obj[1] = MessageFormat.format(EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "orgMade"), new Object[]{orgThis});
						} else{
							obj[1] = MessageFormat.format(EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "orgError"), new Object[]{orgThis});
						}
					} catch(Exception e){
						obj[1] = MessageFormat.format(EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "orgError"), new Object[]{orgThis});
						logger.error(e);
						isAssignSuccess = false;						
					}					
				}
				obj[0] = MessageFormat.format(EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "calcuting"), new Object[]{orgNext});
			}	
		}
		catch(Exception exc){
			if ( (Utils.isRPCException(exc) && Utils.getRPCErrorCode(exc)==RPCException.SESSION_NOTFOUND ) || 
					exc instanceof IllegalSessionStateException ||
					(Utils.isRPCException(exc) &&
							exc.getCause() instanceof IllegalStateException &&
							exc.getCause().getMessage().indexOf("Cannot found session info")!=-1
					) ) {
				throw new BOSException(EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "lost"));
			}
			else{
				throw exc;
			}
		}
		return obj;		
	}	
	
	public Object invokeMethod(String orgThis,String bgFormId,boolean isTrueUpdate, 
			String orgNext,String nextBgFormId, Map adjustMap, String mainFormId, String bgColId)throws Exception{
		Object[] obj = new Object[3];
		byte[] newData = null;
		try{
			boolean isAssignSuccess = true;
			if(orgThis != null && bgFormId != null && orgNext != null && nextBgFormId != null ){
				if(isTrueUpdate){
					try{						
						BgFormInfo info = getFormInterface().getBgFormInfo(new ObjectUuidPK(bgFormId), getBgFormInfoToRefCal());
						info.put("myBgPeriod",info.getBgPeriod());
						if (info.getId().toString().equals(mainFormId)) {
							newData = getMainNewData(info.getId(), bgColId);
						} else {
							newData = getRefNewData(info.getId(), bgColId);
						}
						if (newData != null) {
							info.setData(getNewDataToRefCal(newData, IOHelper.unpackBook(newData), info.getId(), adjustMap));
						} else if (info.getData() != null) {
							info.setData(getNewDataToRefCal(info.getData(),	IOHelper.unpackBook(info.getData()), info.getId(), adjustMap));
						}
						
						ArrayList tableData = getReportInterface().backCalcEx(info, info.getZipData(), null);
						if (tableData != null) {
							Object[] obj1 = getInterface().updateBgFormData(bgFormId, info, tableData);							
							Object[] obj2 = new Object[3];
							obj2[0] = bgFormId;
							obj2[1] = obj1[1];
							obj2[2] = (HashMap) obj1[0];
							obj[2] = obj2;
							getInterface().updateDataToBgTmpData(info.getId().toString(), obj2[2]);
						}
						if(isAssignSuccess){
							obj[1] = com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","167_BgCollectFormEditUI") + orgThis + com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","168_BgCollectFormEditUI");
						}
						else{
							obj[1] = com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","167_BgCollectFormEditUI") +  orgThis + com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","169_BgCollectFormEditUI");
						}
					}
					catch(Exception e){
						logger.error(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","169_BgCollectFormEditUI"),e);
						obj[1] = com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","167_BgCollectFormEditUI") +  orgThis + com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","169_BgCollectFormEditUI");
						isAssignSuccess = false;						
					}					
				}
				obj[0] = com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","170_BgCollectFormEditUI") + orgNext + com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","171_BgCollectFormEditUI");
			}	
		}
		catch(Exception exc){
			if ( (Utils.isRPCException(exc) && Utils.getRPCErrorCode(exc)==RPCException.SESSION_NOTFOUND ) || 
					exc instanceof IllegalSessionStateException ||
					(Utils.isRPCException(exc) &&
							exc.getCause() instanceof IllegalStateException &&
							exc.getCause().getMessage().indexOf("Cannot found session info")!=-1
					) ) {
				throw new BOSException(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","172_BgCollectFormEditUI"));
			}
			else{
				throw exc;
			}
		}
		return obj;		
	}	
	
	private byte[] getRefNewData(BOSUuid uuid,  String bgColId) throws Exception {
		RefDisCountBgFormInfo info = getRefInterface().getRefDisCountBgFormInfo("select id , kdtData where bgCollect.id = '"+bgColId+"' and bgForm.id = '"+uuid.toString()+"'");
		return info.getKdtData();
	}
	
	
	private byte[] getMainNewData(BOSUuid uuid, String bgColId) throws Exception {
		BgDisCountFormInfo info = getInterface().getBgDisCountFormInfo("select id, kdtData where id = '"+bgColId+"'");
		return info.getKdtData();
	}
	
	private byte[] getNewDataToRefCal(byte[] data, Book book, BOSUuid bgFormId, Map adjustMap) throws Exception {
		if (adjustMap == null || adjustMap.isEmpty() || adjustMap.get(bgFormId) == null || adjustMap.get(bgFormId).toString().equals("{}")) 
			return data;
		
		if(book == null)
			return data;		
		Sheet table = null;	
		
		Map adjMap = (Map) adjustMap.get(bgFormId);
		
		int rowIndex = 0, colIndex = 0, rowCount = 0, colCount = 0;
		String formulaString = null;		
		BgAdjustFormDiversityData adjustData = null;			
		book.setCalculate(false);
		for(int n = 0; n < book.getSheetCount(); n++) {
			table = book.getSheet(n);
			/* 设置报表不能进行自动计算及模式为公式和值状态 */
//			table.getScriptManager().setAutoRun(false);
//			table.getScriptManager().setAutoAdjustFormula(true);
//			table.setFormulaMode(KDTStyleConstants.FORMULA_VALUE_CONCURRENCE);
			rowCount = table.getMaxRowIndex()+1;
			colCount = table.getMaxColIndex()+1;
			
			for(rowIndex=0; rowIndex<rowCount; rowIndex++) {
				for(colIndex=0; colIndex<colCount; colIndex++) {
					Cell cell = table.getCell(rowIndex, colIndex, true);
					
					if (BgNFSHelper.checkHasFormulaOfCell(cell)) {					
						formulaString = BgNFSHelper.getFormulaOfCell(cell);
						
						if (adjMap.containsKey(formulaString)) {
							adjustData = (BgAdjustFormDiversityData) adjMap.get(formulaString);
							String valueString = null;
							if (adjustData == null || adjustData.getNewValue() == null) {
								valueString = CtrlSwingUtilities.bigDecimalToPlainString(BgConstants.BIGZERO);
								BgNFSHelper.setValue(cell, valueString);
								BgNFSHelper.getItemFormula(cell).putExt(BgItemFormulaHelper.BG_BUDGET_VALUE, valueString);
							} else {
								valueString = CtrlSwingUtilities.bigDecimalToPlainString(adjustData.getNewValue());
								BgNFSHelper.setValue(cell, valueString);
								BgNFSHelper.getItemFormula(cell).putExt(BgItemFormulaHelper.BG_BUDGET_VALUE, valueString);
							}
							if(adjustData.getNewValue().compareTo(adjustData.getOldValue()) != 0)
								BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
						}
					}
				}
			}
		}		
		
		return  IOHelper.packBook(book);
	}
	
	/**
	 * 描述：获取参加重计算的组织
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private Map getCalOrg(OrgUnitInfo[] orgsf7, Map orgMapForF7) throws EASBizException, BOSException {
		refCalOrgs = new HashMap();
		Map orgMap = new LinkedHashMap();		
		for (int i = 0; i < orgsf7.length; i++) {
			if(orgMapForF7.get(((OrgUnitInfo)orgsf7[i]).getId().toString()) != null){
				orgMap.put(((OrgUnitInfo)orgsf7[i]).getLongNumber(),orgMapForF7.get(((OrgUnitInfo)orgsf7[i]).getId().toString()));
				refCalOrgs.put(orgMapForF7.get(((OrgUnitInfo)orgsf7[i]).getId().toString()),((OrgUnitInfo)orgsf7[i]).getName());
			}			
		}		
		return orgMap;
	}
	
	private void updateCellWithRefCal() throws Exception {
		if(valueMap == null)
			valueMap = new HashMap();
		if(refCalAdjustMap != null){
			Book table =  getCurrentPage();
			Sheet sheet = null;
			for(int n = 0; n < table.getSheetCount(); n++){
				sheet = table.getSheet(n);		
				for (int i = 0; i < sheet.getMaxRowIndex()+1; i++) {
					for (int j = 0; j < sheet.getMaxColIndex()+1; j++) {
						Cell cell = sheet.getCell(i, j, false);
						
						if (BgNFSHelper.checkHasItemFormula(cell))
						{						
							
							Cell orgCell = sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
							if (orgCell != null && orgCell.getUserObject(BgNConstants.NEId) != null) {							
								String tmpFormId = getExpInfo(cell, BGFORMID);
								if(tmpFormId == null){
									continue;
								}
								Map childAdjustMap = (Map)refCalAdjustMap.get(tmpFormId);
								if(childAdjustMap == null){
									continue;
								}
								String formulaString = BgNFSHelper.getFormulaOfCell(cell);
								if (childAdjustMap.containsKey(formulaString) && childAdjustMap.get(formulaString) != null) {
//									Range range = cell.getSheet().getRange(cell);
//									range.setValue(new Variant(childAdjustMap.get(formulaString)));
									BgNFSHelper.setValue(cell, new BigDecimal(childAdjustMap.get(formulaString).toString()));
									cellChanged(cell);
									updateCollectValue(sheet, i, j);
								}
							}
						}						
						/**
						 * 有取数公式无项目公式的单元格也要回填数据
						 * @author kaifei_yi
						 * 增加时间：2008-06-12
						 */
						else if(cell != null && cell.getUserObject(CELL_KEY) != null){
							String expr = (String)cell.getUserObjectValue(CELL_KEY);
							if(valueMap.containsKey(expr)){
								BgNFSHelper.setValue(cell,valueMap.get(expr));
							}
						}
					}
				}	
			}
			if(table.getSheet(0) != null){
				Range selectRange = table.getSheet(0).getRange(0,0);		
				selectRange.select();
			}
		}
		
	}
	
	/**
	 * 功能描述：处理单元格计量单位数值转换处理
	 */
	protected void updateCellValue(Cell cell) {
		/* 判断单元格对象是否是空对象及是否处理系统 */
		if (cell != null &&  !BgNFSHelper.checkIsEmptyValue(cell)) {
			/* 判断是否有项目公式 */
			if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
				/* 如果有项目公式则查询是否是要素计量单位，如果没有则按预算表默认计量单位处理 */
				if (!getMeasreUnitMap().isEmpty()) {
					updateMeasureUnitValue(cell);
				} else {
					upateDefMeasureUnitValue(cell);
				}
			} else {
				/* 如果没有项目公式则按预算表默认计量单位处理 */
				upateDefMeasureUnitValue(cell);
			}
		}
	}
	
	/**
	 * 描述：单元格内容修改事件
	 */
	protected void cellChanged(Cell cell) throws Exception {
		if (!processValue)
			return ;		
		Sheet table = null;
		
		BgAdjustFormDiversityData divInfo = null;
		Map valueMap = null;
		String formulaString = null;
		BigDecimal oldValue = null, newValue = null, value = null;
		Color color = null;
		boolean tmpValue = false;
		
		table = (Sheet) cell.getSheet();
		int rowIndex = cell.getRow();
		int colIndex = cell.getCol();		
		if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
			formulaString = BgNFSHelper.getFormulaOfCell(cell);
			//updateCellValue(cell);
			try {
				valueMap = getAdjustMap(BOSUuid.read(getExpInfo(cell, BGFORMID)));
			} catch (Exception ex) {
				valueMap = new HashMap();
			}
			if (valueMap.containsKey(formulaString)) {
				divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
				if (divInfo.getOldValue() == null && cell.getValue() != null)
					divInfo.setOldValue(BgConstants.BIGZERO);
				
				oldValue = divInfo.getOldValue();
			}else{		
				if(oldData.get(getExpInfo(cell, BGFORMID) + formulaString) != null)
					oldValue = (BigDecimal) oldData.get(getExpInfo(cell, BGFORMID) + formulaString);
				else
					oldValue = BgConstants.BIGZERO;
			}
			
			if(cell != null && cell.getValue() != null &&
					BgNFSHelper.getValue(cell) instanceof BigDecimal)
				newValue = (BigDecimal) BgNFSHelper.getValue(cell);
			else{
				newValue =  BgConstants.BIGZERO;
				BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
			}
			
			if (newValue.compareTo(oldValue) != 0) {
				if (valueMap.containsKey(formulaString)) {
					divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
					if (divInfo.getOldValue() == null)
						divInfo.setOldValue(BgConstants.BIGZERO);
					if (newValue.compareTo(divInfo.getOldValue()) == 0) {
						if (isUpdate(cell)){
							divInfo.setNewValue(newValue);
							BgNFSHelper.setBackGroup(cell, BgNConstants.EDIT_COLOR);
							color = BgNConstants.EDIT_COLOR;
						}else{
							divInfo.setNewValue(newValue);
							//valueMap.remove(formulaString);
							BgNFSHelper.setBackGroup(cell, BgNConstants.BASE_COLOR);
							color = BgNConstants.BASE_COLOR;
						}
					} else {
						divInfo.setNewValue(newValue);
						BgNFSHelper.setBackGroup(cell, BgNConstants.EDIT_COLOR);
						color = BgNConstants.EDIT_COLOR;
					}
					divInfo.setAdjustor(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
					divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
				} else {
					divInfo = new BgAdjustFormDiversityData();
					divInfo.setFormula(formulaString);
					divInfo.setNewValue(newValue);
					divInfo.setOldValue(oldValue);
					divInfo.setAdjustor(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
					divInfo.setAdjustTime(new Timestamp(System.currentTimeMillis()));
					valueMap.put(formulaString, divInfo);
					BgNFSHelper.setBackGroup(cell, BgNConstants.EDIT_COLOR);
					color = BgNConstants.EDIT_COLOR;
				}				
				updateCollectValue(table, rowIndex, colIndex);
			}else{
				if (valueMap.containsKey(formulaString)) {
					divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
					divInfo.setNewValue(newValue);
				}
				//valueMap.remove(formulaString);
				BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
				color = BgNConstants.BASE_COLOR;
				updateCollectValue(table, rowIndex, colIndex);
			}
			
			if(!getOprtState().equals(OprtState.VIEW) && !BgNFSHelper.checkHasExpression(cell)) {
				BgNFSHelper.setCellLocked(cell, false, color);
			}
		} else {
			if (BgNFSHelper.checkIsNumeric(BgNFSHelper.getValue(cell))) {
				try {
					value = (BigDecimal) BgNFSHelper.getValue(cell);
					value = value.multiply(defMeasureUnit.getCoefficient());
				} catch(Exception ex) {
					value = BgConstants.BIGZERO;
				}
				
				tmpValue = processValue;
				processValue = false;
				BgNFSHelper.setValue(cell, value.toString());
				processValue = tmpValue;
			}
		}
		
	}
	
	public SelectorItemCollection getBgFormInfoToRefCal() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add("startDate");
		sic.add("endDate");
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("bgType.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("data"));
		sic.add(new SelectorItemInfo("bgPeriod.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("bgScheme.*")); 
		sic.add(new SelectorItemInfo("bgTemplate.*"));
		sic.add(new SelectorItemInfo("currency.*"));
		sic.add(new SelectorItemInfo("company.*"));
		sic.add(new SelectorItemInfo("auditedStatus"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("dataSource"));
		sic.add(new SelectorItemInfo("versionNo"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("adjust.id"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("CU.name"));
		sic.add(new SelectorItemInfo("sheetParam.*"));
		sic.add(new SelectorItemInfo("postils.*"));
		sic.add(new SelectorItemInfo("postils.report.id"));
		sic.add(new SelectorItemInfo("postils.sheet.id"));
		sic.add(new SelectorItemInfo("postils.creator.id"));
		sic.add(new SelectorItemInfo("postils.creator.name"));
		sic.add(new SelectorItemInfo("sheetParam.*"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.id"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgType"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgUnit.id"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgUnit.type"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgUnit.name"));
		sic.add(new SelectorItemInfo("sheetParam.currency.id"));
		sic.add(new SelectorItemInfo("sheetParam.currency.number"));
		sic.add(new SelectorItemInfo("sheetParam.currency.name"));
		sic.add(new SelectorItemInfo("sheetParam.currency.precision"));
		sic.add(new SelectorItemInfo("sheetParam.postils.*"));
		sic.add(new SelectorItemInfo("sheetParam.postils.report.id"));
		sic.add(new SelectorItemInfo("sheetParam.postils.sheet.id"));
		sic.add(new SelectorItemInfo("sheetParam.postils.creator.id"));
		sic.add(new SelectorItemInfo("sheetParam.postils.creator.name"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.*"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.id"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.name"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.number"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.id"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.name"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.number"));
		sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.coefficient"));
		sic.add(new SelectorItemInfo("measureUnit.*"));
		return sic;
	}
	
	public Sheet getSheetPage() throws Exception {
		Book book = getCurrentPage();
		Sheet sheet= null;
		sheet = book.getActiveSheet();
		if (sheet == null)
			throw new BgException(BgException.CHECKBLANK, new String[] {"KDTable"});
		return sheet;
	}
	
	public BgFormInfo getBgFormInfo(BgDisCountFormEditUI editUI) {
		if(returnBgFormInfo != null){
			return returnBgFormInfo;
		}
		try {
			returnBgFormInfo = getFormInterface().getBgFormInfo(new ObjectUuidPK(editData.getBgForm().getId()), getBgFormInfo());
		} catch (EASBizException e) {
			logger.error(e);
			handleException(e);
		} catch (BOSException e) {
			logger.error(e);
			handleException(e);
		} 
		return returnBgFormInfo;
	}
	
	/**
	 * 描述：单元格内容修改事件
	 */
	protected void cell_Content_Changed(SheetChangeEvent e) throws Exception {
		if (!processValue)
			return ;
		
		Cell cell = null;
		Sheet table = null;
		
		BgAdjustFormDiversityData divInfo = null;
		Map valueMap = null;
		String formulaString = null;
		BigDecimal oldValue = null, newValue = null, value = null;
		boolean tmpValue = false;
		Color color = null;
		table = (Sheet) e.getSheet();
		SortedCellBlockArray _blocks = null;
		_blocks = e.getChangedBlocks();
		CellBlock _block = null;
		int rowCount = table.getMaxRowIndex();
		int colCount = table.getMaxColIndex();
		for(int index=0, size=_blocks.size(); index<size; index++) {
			_block = _blocks.getBlock(index);
			if (_block == null)
				continue;
			int maxRow = (_block.getRow2() > rowCount) ? rowCount : _block.getRow2();
			int maxCol = (_block.getCol2() > colCount) ? colCount : _block.getCol2();
			
			int rowBegin = _block.getRow();
			int colBegin=_block.getCol();
			
			for(int rowIndex=rowBegin, rowEnd=maxRow; rowIndex<=rowEnd; rowIndex++) {
				for(int colIndex=colBegin, colEnd=maxCol; colIndex<=colEnd; colIndex++) {
					cell = table.getCell(rowIndex, colIndex, true);
					if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
						formulaString = BgNFSHelper.getFormulaOfCell(cell);
						if(!isFromCopy)
							updateCellValue(cell);
						try {
							valueMap = getAdjustMap(BOSUuid.read(getExpInfo(cell, BGFORMID)));
						} catch (Exception ex) {
							valueMap = new HashMap();
						}
						if (valueMap.containsKey(formulaString)) {
							divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
							if (divInfo.getOldValue() == null && cell.getValue() != null)			
								divInfo.setOldValue(BgConstants.BIGZERO);
							
							oldValue = divInfo.getOldValue();
						}else{		
							if(oldData.get(getExpInfo(cell, BGFORMID) + formulaString) != null)
								oldValue = (BigDecimal) oldData.get(getExpInfo(cell, BGFORMID) + formulaString);
							else
								oldValue = BgConstants.BIGZERO;
						}
						
						if(cell != null && cell.getValue() != null &&
								BgNFSHelper.getValue(cell) instanceof BigDecimal)
							newValue = (BigDecimal) BgNFSHelper.getValue(cell);
						else{
							newValue =  BgConstants.BIGZERO;
							BgNFSHelper.setValue(cell, BgConstants.BIGZERO);
						}
						
						if (newValue.compareTo(oldValue) != 0) {
							if (valueMap.containsKey(formulaString)) {
								divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
								if (divInfo.getOldValue() == null)
									divInfo.setOldValue(BgConstants.BIGZERO);
								if (newValue.compareTo(divInfo.getOldValue()) == 0) {
									if (isUpdate(cell)){
										divInfo.setNewValue(newValue);
										BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
										color = BgNConstants.EDIT_COLOR;
									}else{
										divInfo.setNewValue(newValue);
										//valueMap.remove(formulaString);
										BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
										color = BgNConstants.BASE_COLOR;
									}
								} else {
									divInfo.setNewValue(newValue);
									BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
									color = BgNConstants.EDIT_COLOR;
								}
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
								valueMap.put(formulaString, divInfo);
								BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
								color = BgNConstants.EDIT_COLOR;
							}
							
							updateCollectValue(table, rowIndex, colIndex);
						}else{
							if (valueMap.containsKey(formulaString)) {
								divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
								divInfo.setNewValue(newValue);
							}
							//valueMap.remove(formulaString);
							BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
							color = BgNConstants.BASE_COLOR;
							updateCollectValue(table, rowIndex, colIndex);
						}
						if(!getOprtState().equals(OprtState.VIEW)) {
							Range range = cell.getSheet().getRange(cell);
							range.setIgnoreProtected(true);
							range.setCellLocked(false);  
						}
						isChanged = true;
					} else {
						if (BgNFSHelper.checkIsNumeric(BgNFSHelper.getValue(cell))) {
							try {
								value = (BigDecimal) BgNFSHelper.getValue(cell);
								value = value.multiply(defMeasureUnit.getCoefficient());
							} catch(Exception ex) {
								value = BgConstants.BIGZERO;
							}
							
							tmpValue = processValue;
							processValue = false;
							BgNFSHelper.setValue(cell, value.toString());
							processValue = tmpValue;
						}
					}
				}
			}
		}
		
		
		//add by hehaijiang  输入的数据保存在Map集合里
		Cell c = e.getSheet().getActiveCell();
		if(!BgNFSHelper.checkHasFormulaOfCell(c))
		{
			Map map = (Map)getAdjustMap().get(editData.getBgForm().getId());
			String cellName = null;
			Variant cellValue = c.getValue();
			Map m = map.get(CellMap) != null?(Map)map.get(CellMap):new HashMap();
			
			Boolean hasAddRow = (Boolean)c.getRowObject().getUserObjectValue("hasAddRow");
			if(hasAddRow == null || !hasAddRow.booleanValue())
			{
				cellName = c.getName(true, true);
			}else
			{
				Row row = c.getRowObject();
				cellName = (String)row.getUserObjectValue("rowKey") + "|" + c.getCol();
			}
			m.put(cellName, cellValue);
			map.put(CellMap, m);
		}
	}
	
	/**
	 * add by hehaijiang
	 * 返回组织结构展后的在集合Map里的key值
	 * @param rowNum
	 */
	private String getNewMapKey(String mapKey,Cell cell)
	{
//		if(expandRowNum > 0 && cell.getRow() > expandIndexRow)
//		{
//			String[] str = mapKey.split("\\$");
//			if(str.length == 3)
//			{
//				StringBuffer temp = new StringBuffer();
//				int newRow = Integer.valueOf(str[2]).intValue() - expandRowNum;
//				
////				int totalRow = expandIndexRow + expandRowNum + (expandRowNum / 2);  //总行数
////				int surRow = totalRow - cell.getRow();   //剩余行
////				if(surRow % 2 == 0)
////				{
////					newRow = cell.getRow() - expandRowNum + expandIndexRow;
////				}
//				
//				if(newRow <= expandIndexRow)
//				{
//					newRow = expandIndexRow + 1;
//				}
//				
//				str[2] = String.valueOf(newRow);
//				for(int i=0;i<str.length;i++)
//				{
//					temp.append(str[i]).append("$");
//				}
//				String key = temp.substring(0, temp.length() - 1);
//				return key;
//			}
//		}
		return mapKey;
	}
	
	public Map getMeasreUnitMap() {
		return measreUnitMap;
	}
	
	public void setMeasreUnitMap(Map measreUnitMap) {
		this.measreUnitMap = measreUnitMap;
	}
	
	public Map getCurrencyMap() {
		return currencyMap;
	}
	public void setCurrencyMap(Map currencyMap) {
		this.currencyMap = currencyMap;
	}
	
	protected void initMeasureUnitInfo() throws Exception {
		setMeasreUnitMap(BgNFSHelper.getMeasureUnitMap(getBgFormInfo(this)));
		if (getMeasreUnitMap() == null)
			setMeasreUnitMap(new HashMap());		
	}
	
	public Map getFormulaMap() {
		return formulaMap;
	}
	public void setFormulaMap(Map formulaMap) {
		this.formulaMap = formulaMap;
	}
	
	/**
	 * 功能描述：处理单元格计量单位数值转换处理
	 */
	protected void upateMeasureUnitValue(Cell cell) {
		/* 判断单元格对象是否是空对象及是否处理系统 */
		if (cell != null &&  !BgNFSHelper.checkIsEmptyValue(cell)) {
			/* 判断是否有项目公式 */
			if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
				/* 如果有项目公式则查询是否是要素计量单位，如果没有则按预算表默认计量单位处理 */
				if (!getMeasreUnitMap().isEmpty()) {
					upateMeasureUnitValue(cell);
				} else {
					upateDefMeasureUnitValue(cell);
				}
			} else {
				/* 如果没有项目公式则按预算表默认计量单位处理 */
				upateDefMeasureUnitValue(cell);
			}
		}
	}
	
	
	/**
	 * 功能描述：按预算默认计量单位处理单元格内容变化
	 */
	protected void upateDefMeasureUnitValue(Cell cell) {
		BigDecimal _value = null, _coefficient = getBgFormInfo(this).getMeasureUnit().getCoefficient();
		Object obj = BgNFSHelper.getValue(cell);
		if(obj instanceof BigDecimal)
			_value = (BigDecimal) BgNFSHelper.getValue(cell);
		else if (obj instanceof String ){
			try{
				_value = new BigDecimal((String)obj);
			}catch(Exception e){
				_value = BgConstants.BIGZERO;
			}
		}else{
			_value = BgConstants.BIGZERO;
		}
		
		if (BgNFSHelper.checkHasPercentage(cell) || 
				BgNFSHelper.checkHasTextOfCell(cell)) {
			/* 如果格式是比率或者文本则不处理计量单位 */
		}else if (BgNFSHelper.checkIsNumericOfCell(cell)) {
			if (_coefficient != null)
				_value = _value.multiply(_coefficient);
		}
		
		updateCellValue(cell, _value);
	}
	
	/**
	 * 功能描述：按预算要素计量单位处理单元格内容变化（如果要素MAP中没有该项目公式的要素则按预算表默认计量单位处理）
	 */
	protected void updateMeasureUnitValue(Cell cell) {
		String _formulaStr = null, _bgElementNumber = null;
		String[] _parameter = null;
		BigDecimal _value = null, _coefficient = getBgFormInfo(this).getMeasureUnit().getCoefficient();
		if(BgNFSHelper.getValue(cell) == null)
			BgNFSHelper.setValue(cell, BgNConstants.ZERO);
		if(BgNFSHelper.getValue(cell) instanceof BigDecimal)
			_value = (BigDecimal) BgNFSHelper.getValue(cell);
		else if(BgNFSHelper.getValue(cell) instanceof String){
			try{
				_value = new BigDecimal((String)BgNFSHelper.getValue(cell));
			}catch(Exception e){
				BgNFSHelper.setValue(cell, BgNConstants.ZERO);
			}
		}else{
			BgNFSHelper.setValue(cell, BgNConstants.ZERO);
			_value = BgNConstants.ZERO;
		}
		
		
		if (BgNFSHelper.checkHasPercentage(cell) || 
				BgNFSHelper.checkHasTextOfCell(cell)) {
			/* 如果格式是比率或者文本则不处理计量单位 */
		}else{
			_formulaStr = BgNFSHelper.getFormulaOfCell(cell);
			_parameter = BgNFSHelper.parseFormulaPara(getFormulaMap(), _formulaStr);
			if (_parameter != null && !StringUtils.isEmpty(_parameter[BgNFSHelper.paraOldElement])) {
				_bgElementNumber = _parameter[BgNFSHelper.paraOldElement];

				if (getMeasreUnitMap().containsKey(_bgElementNumber))
					_coefficient = (BigDecimal) getMeasreUnitMap().get(_bgElementNumber);
				if (_coefficient != null)
					_value = _value.multiply(_coefficient);

				updateCellValue(cell, _value);
			} else {
				if (_coefficient != null)
					_value = _value.multiply(_coefficient);
			}
		}
		
		updateCellValue(cell, _value);
	}
	
	/**
	 * 功能描述：更新单元格的数值（在更新前必须在单元格标记MAP中记录单元格的内容）
	 */
	protected void updateCellValue(Cell cell, BigDecimal value) {
		BgNFSHelper.setValue(cell, value);
	}
	
	public BgDisCountFormInfo getEditData(){
		return editData;
	}
	
	protected SpreadContext getSC() {
		Object obj = null;
		
		obj = paneMain.getSelectedComponent();
		
		if(obj != null && obj instanceof SpreadContext)
			return (SpreadContext) obj;
		
		return null; 
	}
	
	
	public void actionOpenExamineReport_actionPerformed(ActionEvent e) throws Exception {
		getBgExmCheckReportId();
		
		if (bgFormIds == null || bgFormIds.length == 0)
			return;
		
		if (getExamineInterface().canExamine(getBgExmCheckReportId())) {
			UIContext uiContext = new UIContext(this);
			List formIds = new ArrayList();
			for (int i = 0; i < bgFormIds.length; i++) {
				formIds.add(bgFormIds[i].toString());
			}
			
			uiContext.put(BgFormExamineResultUI.BG_FORM_IDS, formIds);
			uiContext.put(BgFormExamineResultUI.BG_EXAMINE_IDS, null);
			
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.EDITWIN)
			.create(BgFormExamineResultUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
			
		} else {
			MsgBox.showInfo(EASResource.getString("com.kingdee.eas.ma.budget.client.BgExamineCheckResource", "cantCheckBgExamine"));
		}
	}
	
	
	public void actionShowAllOrg_actionPerformed(ActionEvent e) throws Exception {		
		Book table = getCurrentPage();
		Sheet sheet = null;
		for(int n = 0; n < table.getSheetCount(); n++){
			sheet = table.getSheet(n);
			if(expandColIndex.get(sheet.getID()) == null)
				continue;
			for (int i = 0; i < sheet.getMaxRowIndex() + 1; i++) {			
				Cell orgCell = sheet.getCell(i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true);
				if (orgCell != null 	&& orgCell.getUserObject(BgNConstants.NEId) != null) {
					NumberExpandInfo expandInfo = BgNFCHelper.getNumberExpandInfo(sheet.getCell(
							i, ((Integer)expandColIndex.get(sheet.getID())).intValue(), true));
					if (expandInfo != null	&&  !expandInfo.getName().equals(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect")))
						setTreeDisplayStyleAll(sheet, sheet.getRow(i, true), expandInfo,
								((Integer)expandColIndex.get(sheet.getID())).intValue());
				}				
			}			
		}
	}
	
	private void setTreeDisplayStyleAll(Sheet table, Row row, NumberExpandInfo expandInfo, int colIndex) {	    	
		expandTableAll(table, row, expandInfo, colIndex);	 	  
	}
	
	protected void expandTableAll(Sheet table, Row row, NumberExpandInfo parentExpandInfo, int colIndex) {
		if (row == null || parentExpandInfo == null)
			return ;
		
		Row child = null;
		NumberExpandInfo expandInfo = null;
		String longnumber = null, plongnumber = null;
		
		plongnumber = parentExpandInfo.getLongNumber();
		if (!parentExpandInfo.isExpandStatus()) {		
			parentExpandInfo.setExpandStatus(true);
			BgNFCHelper.setNumberExpandInfo(row.getCell(colIndex, true), parentExpandInfo);
			/* 如果是关闭则展开 */
			int rowIndex = row.getRow() + 1;
			int rowCount = table.getMaxRowIndex() + 1;
			if (rowIndex >= rowCount) {
				addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex);
			} else {
				for(; rowIndex<rowCount; rowIndex++) {
					child = table.getRow(rowIndex, true);
					if(child.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
						expandInfo = null;
					else
						expandInfo = BgNFCHelper.getNumberExpandInfo(child.getCell(colIndex, true));
					if (expandInfo != null) {
						longnumber = expandInfo.getLongNumber();
						
						if (longnumber.startsWith(plongnumber + "!")) {
							BgNFSHelper.setHided(child, false);
							if (!expandInfo.isLeaf())
								expandInfo.setExpandStatus(false);
							if(table.getRow(rowIndex+1, false) != null && isCol(table, table.getRow(rowIndex+1, true), colIndex))
								expandInfo.setExpandStatus(true);
							BgNFCHelper.setNumberExpandInfo(child.getCell(colIndex, true), expandInfo);
						} else {
							if (rowIndex == row.getRow() + 1)
								addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex);
							break;
						}
					} else {
						if (rowIndex == row.getRow() + 1)
							addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex);
					}
				}
			}
		}
	}
	
	
	public void actionCollectCoefficient_actionPerformed(ActionEvent e) throws Exception {
		if(costOrgUnits == null){
			costOrgUnits = new Vector();
			CostCenterOrgUnitInfo cCOrginfo = null;
			costOrgUnits.add(defOrgUnitInfo);
			for(int i = 0; i < editData.getRefBgForms().size(); i++){
				cCOrginfo = getOrgUnitInterface().getCostCenterOrgUnitInfo(new ObjectUuidPK(editData.getRefBgForms().get(i).getBgForm().getOrgUnit().getId()));
				costOrgUnits.add(cCOrginfo);
			}
		}
		//		//将最末级的组织LongNum的级次取出
		//		int orgUnitLNum = getOrgLNum();
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
			MsgBox.showInfo(this,com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","160_BgColForPeriodEditUI"));
			return;
		}
		if(perantLNum == null || perantLNum.split("!") == null ){
			//|| orgUnitLNum <= perantLNum.split("!").length
			MsgBox.showInfo(this,EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "notDec"));
			return;
		}
		if(itemsMap.size() < 1){
			MsgBox.showInfo(this,EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "notHaveSon"));
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
		uiContext.put("UI_Title",com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.ma.budget.BUDGETAutoGenerateResource","173_BgCollectFormEditUI"));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(getBgDecFormDataUI(), uiContext, null, OprtState.EDIT);
		
		uiWindow.show();
		setMap(uiWindow, colIndex, table, itemsMap);
		
		Map orgProportion = ((BgDecomposerFormDataUI) (uiWindow).getUIObject()).getData();
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
//				Map orgProportion = ((BgDecomposerFormDataUI) ((IUIWindow) tMap.get("uiWindow")).getUIObject()).getData();
				List formProportion = ((BgDecomposerFormDataUI) ((IUIWindow) tMap.get("uiWindow")).getUIObject()).getFormProportion();
    			if(formProportion == null){
    				return null;
    			}   			
				//Sheet table = getSheetPage();
				//KDTable newTable = new KDTable();
				//clone(table)
//				Book nBook = IOHelper.unpackBook(bookByte);	    			
//				showAllRows(nBook, orgProportion, sheetId);
//				colBook = nBook;
				//table.reLayoutAndPaint();
				
				//将string转换为map
//				Map colIndexs = stringToMap((String) tMap.get("colIndex"));
				//循环表格，将根据返回的比例进行数据反添
//				decTableWithProportion(nBook.getSheetByID(sheetId), (Map)tMap.get("itemsMap"), orgProportion, colIndexs);
    			decTableWithProportionNew((Map)tMap.get("itemsMap"),formProportion);
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
//		getSC().setBook(colBook);
//		colBook = null;
		
		//存放组织+项目的key，用来获取分解比例
//		Map tMap = getMap();
////		Map orgProportion = ((BgDecomposerFormDataUI) ((IUIWindow) tMap.get("uiWindow")).getUIObject()).getData();
////		if(orgProportion == null){
////		return null;
////		}    			
//		//Sheet table = getSheetPage();
//		//KDTable newTable = new KDTable();
//		//clone(table)
//		
//		showAllRows(orgProportion);
//		
//		//table.reLayoutAndPaint();
//		
//		//将string转换为map
//		Map colIndexs = stringToMap((String) tMap.get("colIndex"));
//		//循环表格，将根据返回的比例进行数据反添
//		decTableWithProportion((Sheet)tMap.get("table"), (Map)tMap.get("itemsMap"), orgProportion, colIndexs);
		
		
		
		itemMapToShow = null;
		colMapToShow = null;
	}
	
	private String getBgDecFormDataUI() {
		return BgDecomposerFormDataUI.class.getName();
	}
	
	private void setMap(IUIWindow uiWindow, String colIndex, Sheet table, Map itemsMap) {
		timeMap = new HashMap();
		timeMap.put("uiWindow",uiWindow);
		timeMap.put("colIndex",colIndex);
		timeMap.put("table",table);
		timeMap.put("itemsMap",itemsMap);
		
	}
	
	private Map getMap(){
		return timeMap;
	}
	
	private void showAllRows(Book book, Map orgProportion, String sheetId) throws Exception{  	
		Sheet table = book.getSheetByID(sheetId);
		for (int i = 0; i < table.getMaxRowIndex()+1; i++) {
			if(itemMapToShow != null && itemMapToShow.size() > 0 && colMapToShow.size() > 0){
				for(Iterator ite = colMapToShow.keySet().iterator(); ite.hasNext();){
					Integer colIndex = (Integer) ite.next();
					if(table.getCell(i, ((Integer)expandColIndex.get(table.getID())).intValue(), true).getValue() == null)
						continue;					
					Cell cell = table.getCell(i, colIndex.intValue(), true);					
					if(!BgNFSHelper.checkHasFormulaOfCell(cell))
						continue;
					String itemFormula = BgNFSHelper.getItemFormula(cell).getFormula();
					if (table.getCell(i, ((Integer)expandColIndex.get(table.getID())).intValue(), true).getUserObject(BgNConstants.NEName) != null
							//&& orgProportion.containsKey(((NumberExpandInfo)table.getCell(i, expandColIndex).getValue()).getId().toString())
							&& itemMapToShow.containsKey(itemFormula)) {
						NumberExpandInfo expandInfo = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(table.getCell(i, ((Integer)expandColIndex.get(table.getID())).intValue(), true));
						
						if (expandInfo != null
								&& !expandInfo
								.getName()
								.equals(
										EASResource
										.getString(
												"com.kingdee.eas.ma.budget.client.BgRptResource",
										"collect")))
							setTreeDisplayStyle(table, table.getRow(i,true), expandInfo,
									((Integer)expandColIndex.get(table.getID())).intValue(), true);
					}
				}
				
			}else{
				if (table.getCell(i, ((Integer)expandColIndex.get(table.getID())).intValue(), true).getUserObject(BgNConstants.NEName) != null) {
					NumberExpandInfo expandInfo = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(table.getCell(i, ((Integer)expandColIndex.get(table.getID())).intValue(), true));
					
					if (expandInfo != null
							&& !expandInfo
							.getName()
							.equals(
									EASResource
									.getString(
											"com.kingdee.eas.ma.budget.client.BgRptResource",
									"collect")))
						setTreeDisplayStyle(table, table.getRow(i, true), expandInfo,
								((Integer)expandColIndex.get(table.getID())).intValue(), true);
				}				
			}
		}		
	}
	
	private void setTreeDisplayStyle(Sheet table, Row row, NumberExpandInfo expandInfo, int colIndex, boolean isShow) {		
		expandTable(table, row, expandInfo, colIndex, isShow);		
	}
	
	protected void expandTable(Sheet table, Row row, NumberExpandInfo parentExpandInfo, int colIndex, boolean isShow) {
		if (row == null || parentExpandInfo == null)
			return ;		
		Row child = null;
		NumberExpandInfo expandInfo = null;
		String longnumber = null, plongnumber = null;
		
		plongnumber = parentExpandInfo.getLongNumber();
			
			/* 如果是关闭则展开 */
			int rowIndex = row.getRow() + 1;
			int rowCount = table.getMaxRowIndex() + 1 ;
			if (rowIndex >= rowCount) {
				addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex, isShow);
			} else {
				for(; rowIndex<rowCount; rowIndex++) {
					child = table.getRow(rowIndex, true);
					if(child.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
						expandInfo = null;
					else
						expandInfo = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(child.getCell(colIndex, true));
					if (expandInfo != null) {
						longnumber = expandInfo.getLongNumber();
						
						if (longnumber.startsWith(plongnumber + "!")) {							
						} else {
							if (rowIndex == row.getRow() + 1)
								addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex, isShow);
							break;
						}
					} else {
						if (rowIndex == row.getRow() + 1)
							addChildOrgUnitByCol(table, parentExpandInfo, rowIndex, colIndex, isShow);
					}
				}
			}
//		}
	}
	
	protected void addChildOrgUnitByCol(Sheet table, NumberExpandInfo expandInfo, int rowAlias, int colAlias, boolean isShow) {
		if (table == null || expandInfo == null)
			return ;		
		processValue = false;
		
		String[] plongnumber = expandInfo.getLongNumber().split("!");
		String[] longnumber = null;
		
		Set companySet = new HashSet();
		for(int i=0; i<refOrgUnitCol.size(); i++)
			companySet.add(refOrgUnitCol.get(i).getId().toString());
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longnumber", expandInfo.getLongNumber() + "!%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("id", companySet, CompareType.INCLUDE));
		view.setFilter(filter);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longnumber");
		view.getSelector().addObjectCollection(sic);
		
		SorterItemInfo sore = new SorterItemInfo("longNumber");
		sore.setSortType(SortType.DESCEND);
		view.getSorter().add(sore);
		
		Row row = null;
		Cell cell = null;
		NumberExpandInfo expand = null;
		CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
		CostCenterOrgUnitCollection costCenterOrgUnitColl = null, tmpCol = null;
		
		int rowIndex = 0, aliasIndex = 0;
		
		try {
			costCenterOrgUnitColl = getOrgUnitInterface().getCostCenterOrgUnitCollection(view);
			
			if (costCenterOrgUnitColl == null || costCenterOrgUnitColl.isEmpty()) {
				expandInfo.setLeaf(true);
			} else {
				for(rowIndex=rowAlias-1; rowIndex>=0; rowIndex--) {
					cell = table.getCell(rowIndex, colAlias, true);
					if (cell.getValue() != null && cell.getUserObject(BgNConstants.NEName) != null) {
						expand = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(cell);
						if (expand.getLevel() == 0) {
							aliasIndex = rowIndex;
							break;
						}
					}
				}
				
				for(int j=0, jn=costCenterOrgUnitColl.size(); j<jn; j++) {
					costCenterOrgUnitInfo = costCenterOrgUnitColl.get(j);
					longnumber = costCenterOrgUnitInfo.getLongNumber().split("!");
					
					if (longnumber.length - plongnumber.length == 1) {
						Range colRange = table.getRowRange(rowAlias, rowAlias);
						colRange.setIgnoreProtected(true);
						colRange.insert();
						colRange.setHidden(true,true);
						colRange.setCellLocked(true);
						row = table.getRow(rowAlias, false);//table.addRow(rowAlias);
						
						
						//colRange.setHidden(true, true);
						BgNFSHelper.setHided(row, true);
						dealMergeInfo(table, row);
						for(int i = 0; i < table.getMaxColIndex() + 1; i++){
							BgNFSHelper.setBackGroup(row.getCell(i, true), BgSHelper.BASE_COLOR);					
						}	
						table.getRowRange(row.getRow(), row.getRow()).setCellLocked(true);
						expand = new NumberExpandInfo();
						expand.setId(costCenterOrgUnitInfo.getId().toString());
						expand.setName(costCenterOrgUnitInfo.getName());
						expand.setNumber(costCenterOrgUnitInfo.getNumber());
						expand.setLongNumber(costCenterOrgUnitInfo.getLongNumber());
						expand.setLevel(expandInfo.getLevel() + 1);
						
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("longnumber", costCenterOrgUnitInfo.getLongNumber() + "!%", CompareType.LIKE));
						filter.getFilterItems().add(new FilterItemInfo("id", companySet, CompareType.INCLUDE));
						view.setFilter(filter);
						
						tmpCol = getOrgUnitInterface().getCostCenterOrgUnitCollection(view);
						if (tmpCol == null || tmpCol.isEmpty())
							expand.setLeaf(true);
						else
							expand.setLeaf(false);
						expand.setExpandStatus(false);
						expand.setCollect(false);
						com.kingdee.eas.ma.nbudget.client.BgNFCHelper.setNumberExpandInfo(table.getCell(row.getRow(), colAlias, true), expand);
						BgNSHelper.objClear(tmpCol);
						
						fillChildOrgUnitData(table, row, aliasIndex, colAlias, costCenterOrgUnitInfo.getId(), (BOSUuid) bgSchemeMap.get(costCenterOrgUnitInfo.getId()));
					}
				}
				
				
				Range colRange = table.getRowRange(rowAlias, rowAlias);
				colRange.setIgnoreProtected(true);
				colRange.insert();
				colRange.setCellLocked(true);
				colRange.setHidden(true, true);	
				row = table.getRow(rowAlias, true);
				dealMergeInfo(table, row);
				for(int i = 0; i < table.getMaxColIndex() + 1; i++){
					cell.getSheet().getRange(row.getCell(i, true)).setCellLocked(true);  
					BgNFSHelper.setBackGroup(row.getCell(i, true), BgSHelper.COLLECT_COLOR);
				}
				
				expand = new NumberExpandInfo();
				expand.setName(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect"));
				expand.setLongNumber(expandInfo.getLongNumber() + "!");
				expand.setLevel(expandInfo.getLevel() + 1);
				expand.setLeaf(true);
				expand.setExpandStatus(false);
				expand.setCollect(true);
				com.kingdee.eas.ma.nbudget.client.BgNFCHelper.setNumberExpandInfo(table.getCell(row.getRow(), colAlias, true), expand);
				
				fillCollectRowData(table, row, colAlias);		
			}
		} catch(Exception ex) {
		} finally {
			BgNSHelper.objClear(tmpCol);
			BgNSHelper.objClear(costCenterOrgUnitColl);
			BgNSHelper.objClear(companySet);
		}
		
		processValue = true;
	}
	
	private void dealMergeInfo(Sheet table, Row row) {
		if (table == null || row == null)
			return ;
		
		MergeBlocks mm = table.getMerger(true);
		if (mm != null) {
			
			CellBlock mb = null;
			for(int i=0, n=mm.getBlocksColumnNum(); i<n; i++) {
				mb = mm.getBlock(i);
				if (row.getRow() == mb.getRow2() + 1)
					mb.setRow2(mb.getRow2() + 1);
			}			
		}		
	}
	
	private Map stringToMap(String colIndex) {
		String[] col  = colIndex.split("#");
		Map colMap = new HashMap();
		for(int n = 0; n < col.length; n++){			
			colMap.put(col[n], null);
		}
		if(colMap.size() < 1)
			return null;
		return colMap;
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
	
	private void decTableWithProportionNew(Map itemsMap, List formProportion) {
		if(itemsMap == null || formProportion == null){
			return;
		}
		Object[] obj = null;
		String formId = null;
		String parentFormId = null;
		BigDecimal proportion = null;
		Map childAdjMap = null;
		Map parentAdjMap = null;
		String formulaString = null;
		BgAdjustFormDiversityData divInfo = null, parentDivInfo = null;;
		BigDecimal oldValue = null, newValue = null, parentData = null;
		for(Iterator it = formProportion.iterator(); it.hasNext(); ){
			obj = (Object[]) it.next();
			if(obj == null)
				continue;
			formId = (String) obj[0];
			parentFormId = (String) obj[1];
			proportion = (BigDecimal) obj[2];
			try {
				childAdjMap = getAdjustMap(BOSUuid.read(formId));
				parentAdjMap = getAdjustMap(BOSUuid.read(parentFormId));
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
				if(proportion == null || proportion.intValue()==0){
					newValue = BgConstants.BIGZERO;
				} else{
					if(parentAdjMap.containsKey(formulaString)){
						parentDivInfo = (BgAdjustFormDiversityData) parentAdjMap.get(formulaString);
						if(parentDivInfo.getNewValue()!=null){
							parentData = parentDivInfo.getNewValue();
						} else if(parentDivInfo.getOldValue()!=null){
							parentData = parentDivInfo.getOldValue();
						}else{
							parentData = BgConstants.BIGZERO;
						}
					} else{
						if(oldData.get(parentFormId + formulaString) != null)
							parentData = (BigDecimal) oldData.get(parentFormId + formulaString);
						else
							parentData = BgConstants.BIGZERO;
					}
					proportion = proportion.setScale(4);
					newValue = parentData.multiply(proportion).divide(new BigDecimal("100"), 4);
				}
				
				if (newValue.compareTo(oldValue) != 0) {
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
					isChanged = true;
				}
				
			}
			if(!adjustMap.containsKey(BOSUuid.read(formId))){
				adjustMap.put(BOSUuid.read(formId), childAdjMap);
			}
		}
	}
	
	private void decTableWithProportion(Sheet table, Map itemsMap, Map orgProportion, Map colIndexs) {
		if(table == null || itemsMap == null || colIndexs == null || 
				orgProportion == null || itemsMap.size() < 1 || orgProportion.size() < 1)
			return;
		String index = "";
		int colIndex = 0;		
		for (Iterator iter = colIndexs.keySet().iterator(); iter.hasNext();) {
			index = (String) iter.next();
			try {
				colIndex = (new Integer(index)).intValue();
			} catch (Exception e) {
				MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.ma.budget.client.BgDecomposerFormResource", "colError"));
				continue;
			}
			for (int i = 0; i <= table.getMaxRowIndex(); i++) {
				Cell cell = table.getCell(i, colIndex, true);	
				Cell orgCell = table.getCell(i, ((Integer)expandColIndex.get(table.getID())).intValue(), true);
				if (cell != null && !cell.isLocked() && BgNFSHelper.checkHasItemFormula(cell) 
						&& itemsMap.containsKey(BgNFSHelper.getFormulaOfCell(cell))
						&& orgCell.getUserObject(BgNConstants.NEName) != null 	
						&& orgCell.getUserObject(BgNConstants.NEId) != null
						&& orgCell.getUserObject(BgNConstants.NEId).getValue() != null
						&& orgProportion.containsKey(orgCell.getUserObject(BgNConstants.NEId).getValue().toString())) {	
					BigDecimal perantData = getPerantData(orgCell.getUserObject(BgNConstants.NELNum).getValue().toString(),cell,table);
					//logger.error("比例分解：perantData   "+perantData);
					if(perantData == null){
						BgNFSHelper.setValue(cell, new BigDecimal("0"));
						continue;
					}
					BigDecimal proportion = (BigDecimal) orgProportion.get(orgCell.getUserObject(BgNConstants.NEId).getValue().toString());
					//logger.error("比例分解：系数   "+proportion);
					if(proportion == null || proportion.equals(new BigDecimal("0"))){
						BgNFSHelper.setValue(cell, new BigDecimal("0"));
						updateAdjustMap(table, cell);
					}else{
						proportion = proportion.setScale(4);
						BgNFSHelper.setValue(cell, (perantData.multiply(proportion)).divide(new BigDecimal("100"), 4));
						updateAdjustMap(table, cell);
					}					
				}
			}
		}
	}
	
	private void updateAdjustMap(Sheet table, Cell cell) {	
		
		BgAdjustFormDiversityData divInfo = null;
		Map valueMap = null;
		String formulaString = null;
		BigDecimal oldValue = null, newValue = null;			
		
		if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
			formulaString = BgNFSHelper.getFormulaOfCell(cell);
			try {
				valueMap = getAdjustMap(BOSUuid.read(getExpInfo(cell, BGFORMID)));
			} catch (Exception ex) {
				valueMap = new HashMap();
			}
			if (valueMap.containsKey(formulaString)) {
				divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
				if (divInfo.getOldValue() == null && cell.getValue() != null)
					divInfo.setOldValue((BigDecimal) BgNFSHelper.getValue(cell));
				if(cell == null || cell.getValue() == null)
					divInfo.setOldValue(BgConstants.BIGZERO);
				
				oldValue = divInfo.getOldValue();
			}else{		
				if(oldData.get(getExpInfo(cell, BGFORMID) + formulaString) != null)
					oldValue = (BigDecimal) oldData.get(getExpInfo(cell, BGFORMID) + formulaString);
				else
					oldValue = BgConstants.BIGZERO;
			}
			
			if(cell != null && cell.getValue() != null)
				newValue = (BigDecimal) BgNFSHelper.getValue(cell);
			else
				newValue =  BgConstants.BIGZERO;
			if (newValue.compareTo(oldValue) != 0) {
				if (valueMap.containsKey(formulaString)) {
					divInfo = (BgAdjustFormDiversityData) valueMap.get(formulaString);
					if (divInfo.getOldValue() == null)
						divInfo.setOldValue(BgConstants.BIGZERO);
					if (newValue.compareTo(divInfo.getOldValue()) == 0) {
						if (isUpdate(cell)){
							divInfo.setNewValue(newValue);
							BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
						}else{
							divInfo.setNewValue(newValue);
							//valueMap.remove(formulaString);
							BgNFSHelper.setBackGroup(cell, BgSHelper.BASE_COLOR);
						}
					} else {
						divInfo.setNewValue(newValue);
						BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
					}
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
					valueMap.put(formulaString, divInfo);
					BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
				}
				isChanged = true;
			}
			updateCollectValue(table, cell.getRow(), cell.getCol());
		} 		
	}
	
	private BigDecimal getPerantData(String longNumber, Cell cell, Sheet table) {
		int rowIndex = cell.getRow();
		int colIndex = cell.getCol();		
		rowIndex--;
		for (; rowIndex > 0; rowIndex--) {
			Cell orgCell = table.getCell(rowIndex, ((Integer)expandColIndex.get(table.getID())).intValue(), true);
			if (orgCell != null
					&& orgCell.getUserObject(BgNConstants.NEName) != null
					&& (longNumber.indexOf( orgCell
							.getUserObject(BgNConstants.NELNum).getValue().toString()) == 0)
							&& longNumber.lastIndexOf("!") !=orgCell
							.getUserObject(BgNConstants.NELNum).getValue().toString().lastIndexOf("!")) {
				try {
					BigDecimal value = null;
					if(table.getCell(rowIndex, colIndex, true)
							.getValue().toString().lastIndexOf(".") < 0){
						new BigDecimal(table.getCell(rowIndex, colIndex, true)
								.getValue().toString());
						value = new BigDecimal(table.getCell(rowIndex, colIndex, true)
								.getValue().toString()+".000000");
					}else{
						value = new BigDecimal(table.getCell(rowIndex, colIndex, true)
								.getValue().toString());
					}
					return value;
				} catch (Exception e1) {					
					if(longNumber.indexOf("!") > 0){
						return getPerantData(longNumber.substring(0, longNumber.lastIndexOf("!")), table.getCell(rowIndex, colIndex, true),table);
					}else{
						return null;
					}
				}
			}
		}		
		return null;
	}
	
	private boolean isCol(Sheet table, Row row, int colIndex) {
		if(row.getCell(colIndex, true).getUserObject(BgNConstants.NEName) == null)
			return false;
		NumberExpandInfo info = com.kingdee.eas.ma.nbudget.client.BgNFCHelper.getNumberExpandInfo(row.getCell(colIndex, true));
		if(info != null && info.getName() != null && 
				info.getName().equals(EASResource.getString("com.kingdee.eas.ma.budget.client.BgRptResource", "collect")))
			return true;
		return false;
	}
	
	
	@Override
	public void actionModifyBgRate_actionPerformed(ActionEvent e)
			throws Exception {
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
	
}



