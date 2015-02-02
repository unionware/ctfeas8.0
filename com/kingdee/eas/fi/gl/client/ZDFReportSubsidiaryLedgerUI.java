package com.kingdee.eas.fi.gl.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTColumns;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.print.resource.Resources;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.master.account.AccountPLType;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnit;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.IGLReport;
import com.kingdee.eas.fi.gl.IZDFGLReportSubsidiaryLedgerTree;
import com.kingdee.eas.fi.gl.ReportConditionBase;
import com.kingdee.eas.fi.gl.ReportConditionGeneralLedger;
import com.kingdee.eas.fi.gl.ZDFReportConditionSubsidiaryLedger;
import com.kingdee.eas.fi.gl.ReportConditionSubsidiaryLedgerAssist;
import com.kingdee.eas.fi.gl.ReportException;
import com.kingdee.eas.fi.gl.ReportResultInfo;
import com.kingdee.eas.fi.gl.ReportTableHeadInfo;
import com.kingdee.eas.fi.gl.ReportTreeNodeExtendInfo;
import com.kingdee.eas.fi.gl.ReportTreeNodeInfo;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fi.gl.ZDFGLReportSubsidiaryLedgerFactory;
import com.kingdee.eas.fi.gl.ZDFGLReportSubsidiaryLedgerTreeFactory;
import com.kingdee.eas.fi.gl.common.AsstactTypeEntity;
import com.kingdee.eas.fi.gl.common.GLResUtil;
import com.kingdee.eas.fi.gl.common.RptClientUtil;
import com.kingdee.eas.fi.gl.common.print.IRequestTableData;
import com.kingdee.eas.fi.gl.common.print.MultiPrint;
import com.kingdee.eas.fi.rpt.util.AcctountAsstItemParser;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.report.util.ExcelExporter;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.PeriodEntity;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ZDFReportSubsidiaryLedgerUI extends
		AbstractZDFReportSubsidiaryLedgerUI implements IRequestTableData {
	private static final long serialVersionUID = -3038225896783914541L;
	CommonQueryDialog conditionDialog = null;

	ZDFReportSubsidiaryLedgerConditionUI conditionPanel = null;

	ZDFReportSubsidiaryLedgerConditionAdvanceUI conditionPanelAdvanced = null;

	String PRE_ACCOUNT = EASResource.getString(
			"com.kingdee.eas.fi.gl.ReportAssistBaseUI", "PreAccount");

	String ROOT_TITLE = EASResource.getString(
			"com.kingdee.eas.fi.gl.client.ReportSubsidiaryLedgerResource",
			"TREE_ROOT_TITLE_ACCOUNT");

	boolean enableTreeEvent = true;

	private String accountString = null;

	private String accountID = null;

	private String accountCodeStart = null;

	private String accountCodeEnd = null;
	private KDPopupMenu popMenu;
	private boolean requesting = false;

	private static CompanyOrgUnitInfo curCompany = null;

	ReportSubsidiaryParame editParame = null;

	Map mapThis = null;

	public static final Color LOCKCOLOR = new Color(228, 228, 228);
	public static final Color UNLOCKCOLOR = new Color(255, 255, 255);

	Map mapGlobalRs = new HashMap();

	public ZDFReportSubsidiaryLedgerUI() throws Exception {
		this.popMenu = createTreePopMenu();
		this.tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if ((e.getButton() == 3)
						&& (ZDFReportSubsidiaryLedgerUI.this
								.getAccountTreePath(false).length > 0))
					ZDFReportSubsidiaryLedgerUI.this.popMenu.show(e
							.getComponent(), e.getX(), e.getY());
			}
		});
		this.editParame = ReportSubsidiaryParame.getInstance();
	}

	public void initTable(KDTable tableForInit) {
		super.initTable(tableForInit);
	}
	@Override
	public String getResourceName() {
		// TODO Auto-generated method stub
		return "com.kingdee.eas.fi.gl.ZDFReportBase";
	}
	protected KDPopupMenu createTreePopMenu() {
		KDPopupMenu menu = new KDPopupMenu();
		JMenuItem item = null;
		item = new JMenuItem();
		item.setText(GLResUtil.getRes("export_d_account") + "EXCEL");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setCursorOfWair();
					joinExport2Excel(getAccountTreePath(false));
				} catch (Exception ex) {
					handleException(ex);
				} finally {
					setCursorOfDefault();
				}
			}
		});
		menu.add(item);

		return menu;
	}

	private TreePath[] getAccountTreePath(boolean all) {
		List list = new ArrayList();
		TreePath[] tps = null;

		if (all) {
			Enumeration em = ((DefaultKingdeeTreeNode) this.tree.getModel()
					.getRoot()).depthFirstEnumeration();
			while (em.hasMoreElements()) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) em
						.nextElement();
				Object nodeInfo = node.getUserObject();
				if ((nodeInfo != null)
						&& (ReportTreeNodeInfo.class.isInstance(nodeInfo))
						&& (((ReportTreeNodeInfo) nodeInfo).isItem()))
					list.add(new TreePath(node.getPath()));
			}
		} else {
			tps = this.tree.getSelectionPaths();
			if (tps != null) {
				int i = 0;
				for (int n = tps.length; i < n; i++) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tps[i]
							.getLastPathComponent();
					if (node != null) {
						Object nodeInfo = node.getUserObject();
						if ((nodeInfo != null)
								&& (ReportTreeNodeInfo.class
										.isInstance(nodeInfo))
								&& (((ReportTreeNodeInfo) nodeInfo).isItem())) {
							list.add(tps[i]);
						}
					}
				}
			}
		}
		tps = new TreePath[list.size()];
		list.toArray(tps);
		return tps;
	}

	private void joinExport2Excel(TreePath[] tps) {
		KDTable left = new KDTable();
		RptTableHeader mm = new RptTableHeader();

		RptTableColumn col = new RptTableColumn("AccountNumber");
		col.setWidth(100);
		mm.addColumn(col);
		col = new RptTableColumn("AccountName");
		col.setWidth(100);
		mm.addColumn(col);
		mm.setLabels(new String[][] {
				{ GLResUtil.getRes("accountView_number"),
						GLResUtil.getRes("accountView_name") },
				{ GLResUtil.getRes("accountView_number"),
						GLResUtil.getRes("accountView_name") } }, true);

		KDTableUtil.setHeader(mm, left);

		int i = 0;
		for (int n = tps.length; i < n; i++) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tps[i]
					.getLastPathComponent();
			Object nodeInfo = node.getUserObject();
			IRow row = left.addRow();
			row.getCell(0)
					.setValue(((ReportTreeNodeInfo) nodeInfo).getNumber());
			row.getCell(1).setValue(((ReportTreeNodeInfo) nodeInfo).getName());
		}
		try {
			this.tblMain.setRefresh(false);
			exoprtKDTable2(left, tps);
		} catch (Exception e) {
			MsgBox.showInfo(this, e.getMessage());
		} finally {
			this.tblMain.setRefresh(true);
		}
	}

	private void exoprtKDTable2(KDTable tbl1, TreePath[] tps) throws Exception {
		ExcelExporter ee = new ExcelExporter(this);
		ee.open();
		int maxAssistCount = 0;
		int asst0Index = 0;
		int[] tpsAsst = new int[tps.length];
		boolean hasSet = false;
		int i = 0;
		for (int n = tps.length; i < n; i++) {
			requestTableData2(tps[i]);
			KDTColumns allcols = this.tblMain.getColumns();
			int curAssistCount = 0;
			for (int j = 0; j < this.tblMain.getColumnCount(); j++) {
				if ((this.tblMain.getColumns() == null)
						|| (this.tblMain.getColumns().getColumnKey(j) == null)
						|| (this.tblMain.getColumns().getColumnKey(j).indexOf(
								"FAsstAccountName") == -1)) {
					continue;
				}
				curAssistCount++;
				if (!hasSet) {
					asst0Index = j + 1;
					hasSet = true;
				}
			}

			tpsAsst[i] = curAssistCount;
			if (maxAssistCount < curAssistCount) {
				ee.setHeader(tbl1, this.tblMain);
				maxAssistCount = curAssistCount;
			}
		}
		if (asst0Index == 0) {
			ee.setHeader(tbl1, this.tblMain);
		}

		ee.addMergedRegion(0, 0, 0, ee.getHeader()[0].length - 1);
		ee.export(new String[][] { { "", GLResUtil.getRes("d_account"), "",
				this.lblPeriod.getText(), "", this.lblCurrency.getText() } },
				false, true);
		Object[][] dd1 = getTableData(tbl1);
		Object[][] data = (Object[][]) null;
		Color[] colors = null;
		String[][] strFormat = (String[][]) null;
		int tblLeftColNum = tbl1.getColumnCount();

		i = 0;
		for (int n = tps.length; i < n; i++) {
			requestTableData2(tps[i]);
			int m = this.tblMain.getRowCount();
			while (m <= 0) {
				Thread.sleep(50L);
				m = this.tblMain.getRowCount();
			}
			int showColNum = 0;
			for (int ish = 0; ish < this.tblMain.getColumnCount(); ish++) {
				if (!this.tblMain.getColumn(ish).getStyleAttributes().isHided()) {
					showColNum++;
				}
			}
			int cols = tblLeftColNum + showColNum
					+ (maxAssistCount - tpsAsst[i]);
			data = new Object[m][cols];
			colors = new Color[m];
			data[0][0] = dd1[i][0];
			data[0][1] = dd1[i][1];
			strFormat = new String[this.tblMain.getRowCount()][cols];
			for (int j = 0; j < m; j++) {
				colors[j] = this.tblMain.getRow(j).getStyleAttributes()
						.getBackground();

				int hideColCount = 0;

				for (int k = 2; k < cols - (maxAssistCount - tpsAsst[i]); k++) {
					if (this.tblMain.getColumn(k - 2 + hideColCount)
							.getStyleAttributes().isHided()) {
						hideColCount++;
						k--;
					} else if (this.tblMain.getCell(j, k - 2 + hideColCount) != null) {
						if (k - 2 + hideColCount < asst0Index + tpsAsst[i] - 1) {
							data[j][k] = this.tblMain.getCell(j,
									k - 2 + hideColCount).getValue();

							String strColFormat = this.tblMain.getColumn(
									k - 2 + hideColCount).getStyleAttributes()
									.getNumberFormat();
							strFormat[j][k] = this.tblMain.getCell(j,
									k - 2 + hideColCount).getStyleAttributes()
									.getNumberFormat();
							if ((strFormat[j][k] == null)
									|| (strFormat[j][k].trim().length() == 0)) {
								if ((strColFormat == null)
										|| (strColFormat.trim().length() == 0))
									strFormat[j][k] = "@";
								else
									strFormat[j][k] = strColFormat;
							}
						} else {
							int kV = k + (maxAssistCount - tpsAsst[i]);
							data[j][kV] = this.tblMain.getCell(j,
									k - 2 + hideColCount).getValue();

							String strColFormat = this.tblMain.getColumn(
									k - 2 + hideColCount).getStyleAttributes()
									.getNumberFormat();
							strFormat[j][kV] = this.tblMain.getCell(j,
									k - 2 + hideColCount).getStyleAttributes()
									.getNumberFormat();
							if ((strFormat[j][kV] == null)
									|| (strFormat[j][kV].trim().length() == 0)) {
								if ((strColFormat == null)
										|| (strColFormat.trim().length() == 0))
									strFormat[j][kV] = "@";
								else {
									strFormat[j][kV] = strColFormat;
								}
							}
						}
					}
				}
			}

			ee.export(data, strFormat, colors, false, false);
		}
		ee.close();
		ee.openOrSaveExportFile();
	}

	private Object[][] getTableData(KDTable tbl) {
		int rows = tbl.getRowCount();
		int cols = tbl.getColumnCount();
		Object[][] dd = new Object[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				dd[i][j] = tbl.getCell(i, j).getValue();
			}
		}
		return dd;
	}
	
	@Override
	protected boolean isSupportVirtual() {
		// TODO Auto-generated method stub
		return false;
	}

	CostCenterOrgUnitInfo centerOrgUnitInfo = null;
	public void onLoad() throws Exception {
		this.mapThis = new HashMap();

		this.actionGeneralLedger.setEnabled(false);
		this.actionAssist.setEnabled(false);
		this.actionVoucher.setEnabled(false);

		this.menuItemPrintParame.setEnabled(true);
		this.menuItemPrintAccountContents.setEnabled(false);
		this.menuItemPrintAccountContents.setVisible(false);
		initTree();

		this.splpMain.setDividerLocation(240);

		Map uiContext = getUIContext();
		
		CompanyOrgUnitInfo info = null;
		centerOrgUnitInfo = SysContext.getSysContext().getCurrentCostUnit();
		
		IOrgUnitRelation iOrgUnitRelation = OrgUnitRelationFactory.getRemoteInstance();
        OrgUnitCollection orgUnitCollection = iOrgUnitRelation.getToUnit(centerOrgUnitInfo.getId().toString(), 5, 1);
        if(orgUnitCollection != null && orgUnitCollection.size() > 0)
        	info = (CompanyOrgUnitInfo)orgUnitCollection.get(0);

		if(info!=null&&info.getId()!=null){
			uiContext.put("companyId", info.getId().toString());
			uiContext.put("company", info);
		}else{
				MsgBox.showWarning("没有找到委托的财务组织！");	
				abort();
		}
		
		this.menuItemAssist.setIcon(EASResource
				.getIcon("imgTbtn_assistantlistaccount"));
		this.menuItemVoucher.setIcon(EASResource.getIcon("imgTbtn_credence"));
		this.menuItemPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.menuItemPrintView.setIcon(EASResource.getIcon("imgTbtn_preview"));
		this.menuItemGeneralLedger.setIcon(EASResource
				.getIcon("imgTbtn_overallsortaccount"));
		this.menuItemExitCurrent.setIcon(EASResource.getIcon("imgTbtn_quit"));
		this.menuItemFilter.setIcon(EASResource.getIcon("imgTbtn_filter"));
		this.menuItemRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));

		this.menuItemHelp.setIcon(EASResource.getIcon("imgTbtn_help"));

		this.menuItemFilter.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		this.menuItemRefresh.setAccelerator(KeyStroke.getKeyStroke("F5"));
		this.menuItemPrint.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		this.menuItemPrintView.setAccelerator(KeyStroke
				.getKeyStroke("ctrl shift P"));

		this.menuItemCalculator.setAccelerator(KeyStroke
				.getKeyStroke("ctrl shift F12"));

		Boolean queryByOther = (Boolean) uiContext.get("QueryByOther");
		if ((queryByOther != null) && (queryByOther.booleanValue())) {
			setQueryByOther(true);
		}

		super.onLoad();

		getMainStatusBar().setPerTitle(
				FMClientHelper.getSysStatusBarTitle(this));
	}

	public void onShow() throws Exception {
		super.onShow();

		this.tree.requestFocus();
	}

	public static void queryByCondition(EntityViewInfo currentCondition,
			CoreUIObject owner, CompanyOrgUnitInfo company, boolean isVirtual)
			throws Exception {
		curCompany = company;
		RptClientUtil.checkFIUnit(company, false);
		if (currentCondition == null) {
			return;
		}
		ReportConditionBase glCondition = (ReportConditionBase) currentCondition
				.get("GLFixCondition");

		if (glCondition == null) {
			return;
		}

		if (!ZDFReportConditionSubsidiaryLedger.class.isInstance(glCondition)) {
			throw new ReportException(ReportException.CONDITION_ERROR);
		}
		if (owner == null) {
			throw new ReportException(ReportException.TODO_UNTYPE);
		}

		ZDFReportConditionSubsidiaryLedger rslcondition = (ZDFReportConditionSubsidiaryLedger) glCondition;
		int startyear = rslcondition.getPeriodYearStart();
		int startnumber = rslcondition.getPeriodNumberStart();
		int endyear = rslcondition.getPeriodYearEnd();
		if (RptClientUtil.isBetweenAdjPeriod(owner, endyear, startyear,
				startnumber)) {
			return;
		}

		if (rslcondition.getAssisthgId() != null) {
			rslcondition.setTableData(RptClientUtil.queryAsstActByAssisthgID(
					rslcondition.getAssisthgId(), SysContext.getSysContext()
							.getCurrentFIUnit().getCU().getId().toString()));
			rslcondition.setAssisthgId(null);
		}

		if (rslcondition.getAcctAsstItems() != null) {
			String acctAsstItems = rslcondition.getAcctAsstItems();
			rslcondition.setTableData(acctAsstItemsToList(acctAsstItems));
			rslcondition.setAcctAsstItems(null);
		}
		if (rslcondition.getTableData() == null) {
			List list = RptClientUtil.queryAsstAct(null, SysContext
					.getSysContext().getCurrentFIUnit().getCU().getId()
					.toString());
			for (int i = 0; i < list.size(); i++) {
				AsstactTypeEntity at = (AsstactTypeEntity) list.get(i);
				at.setSelected(false);
			}
			rslcondition.setTableData(list);
		}

		UIContext uiContext = new UIContext(owner);
		uiContext.put("company", company);
		ReportBaseUI.fetchCompany(uiContext);
		uiContext.put("QueryByOther", Boolean.TRUE);
		String period = "" + glCondition.getPeriodYearStart()
				+ glCondition.getPeriodNumberStart()
				+ glCondition.getPeriodYearEnd()
				+ glCondition.getPeriodNumberEnd();
		uiContext.put("UIClassParam", glCondition.getAccountId()
				+ glCondition.getCurrencyID() + period);
		String mode = (((CoreUI) owner).getUIWindow() instanceof UINewFrame) ? UIFactoryName.NEWWIN
				: UIFactoryName.NEWTAB;
		IUIWindow uiWindow = UIFactory.createUIFactory(mode).create(
				ZDFReportSubsidiaryLedgerUI.class.getName(), uiContext, null);
		ZDFReportSubsidiaryLedgerUI ui = (ZDFReportSubsidiaryLedgerUI) uiWindow
				.getUIObject();

		ui.setQueryByOther(true);
		ui.setCondition(currentCondition);
		ui.refreshAccountTree();

		if (isVirtual) {
			ui.btnFilter.setEnabled(false);
			ui.menuItemFilter.setEnabled(false);
		}

		ui.setTitlePeriodName(ui.getPeriodTitle(glCondition
				.getPeriodYearStart(), glCondition.getPeriodNumberStart(),
				glCondition.getPeriodYearEnd(), glCondition
						.getPeriodNumberEnd()));
		ui.setTitleCurrencyName(glCondition.getCurrencyName());

		ui.lblPeriod.setText(EASResource.getString(
				"com.kingdee.eas.fi.gl.ReportPeriodExtraRes", "PREPERIOD")
				+ ui.getTitlePeriodName());
		ui.lblCurrency.setText(EASResource.getString(
				"com.kingdee.eas.fi.gl.ReportPeriodExtraRes", "PRECURRENCY")
				+ ui.getTitleCurrencyName());

		uiWindow.show();
	}

	protected void initTree() {
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode("KDTree");
		this.tree.setModel(new DefaultTreeModel(root));

		this.tree.setRootVisible(true);
	}

	protected void tree_valueChanged(TreeSelectionEvent e) throws Exception {
		if ((this.enableTreeEvent) && (this.tree.getSelectionPaths() != null)
				&& (this.tree.getSelectionPaths().length == 1)) {
			super.tree_valueChanged(e);
			this.actionVoucher.setEnabled(false);
			requestTableData2(e.getNewLeadSelectionPath());
		}
	}

	public void requestTableData2(TreePath path) {
		requestTableData(path);
	}

	public void requestTableData(Object thepath) {
		if (thepath == null) {
			return;
		}
		TreePath path = null;
		if ((thepath instanceof TreePath))
			path = (TreePath) thepath;
		else {
			return;
		}
		String accountID = null;
		String accountNumber = null;

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();
		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if ((nodeInfo != null)
					&& (ReportTreeNodeInfo.class.isInstance(nodeInfo))
					&& (((ReportTreeNodeInfo) nodeInfo).isItem())) {
				accountID = ((ReportTreeNodeInfo) nodeInfo).getId();
				accountNumber = ((ReportTreeNodeInfo) nodeInfo).getNumber();
			}
		}

		ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
				.get("GLFixCondition");
		fixCondition.setAccountId(accountID);
		fixCondition.setAccountCodeEnd(accountNumber);
		fixCondition.setAccountCodeStart(accountNumber);
		if (fixCondition.isAllCurrency())
			setShowForPreCol(true);
		else {
			setShowForPreCol(false);
		}
		boolean isAccountIdEmpty = (accountID == null)
				|| (accountID.length() <= 0);
		this.tblMain.removeRows(!isAccountIdEmpty);

		if (isAccountIdEmpty) {
			this.lblAccount.setText(this.PRE_ACCOUNT);
			setAccountString(null);
		}
		this.actionGeneralLedger.setEnabled((canJoinQueryForPeriodLimit())
				&& (canJoinQuery()) && (!isAccountIdEmpty));
		if (isAccountIdEmpty)
			this.actionAssist.setEnabled(false);
	}

	protected Collection getInitList() {
		Collection list = ZDFReportSubsidiaryLedgerConditionUI.getInitDataItem();
		list.add("DefaultTableHead");

		list.add("CurrentCompany");

		return list;
	}

	protected IGLReport getRemoteInstance() throws BOSException {
		return ZDFGLReportSubsidiaryLedgerFactory.getRemoteInstance();
	}

	private boolean canJoinQueryForPeriodLimit() {
		ZDFReportConditionSubsidiaryLedger cc = (ZDFReportConditionSubsidiaryLedger) this.condition
				.get("GLFixCondition");
		if (cc == null) {
			return true;
		}
		PeriodEntity pe = (PeriodEntity) cc.getExpandInfo().get("periodEntity");
		if (pe == null) {
			try {
				pe = PeriodEntity
						.requestPeriodEntity((CompanyOrgUnitInfo) getUIContext()
								.get("company"));
			} catch (Exception e) {
				handleException(e);
			}
		}
		return true;
	}

	private boolean canJoinQuery() {
		ReportConditionBase glCondition = (ReportConditionBase) getCondition()
				.get("GLFixCondition");
		return glCondition.isCanJoinQueryOther();
	}

	protected void query(EntityViewInfo mainQuery) throws Exception {
		ZDFReportConditionSubsidiaryLedger condition = ((ZDFReportSubsidiaryLedgerConditionUI) getConditionPanel()).getCustomCondition();

		if (this.conditionPanelAdvanced == null) {
			this.conditionPanelAdvanced = new ZDFReportSubsidiaryLedgerConditionAdvanceUI();
		}
		condition.setTableData(this.conditionPanelAdvanced.getTableData());
		condition.setOptionAsstGroup(this.conditionPanelAdvanced.chkOpAsstGroup
				.isSelected());

		if ((condition.getOptionOtherAccount())
				|| (condition.getOptionOtherAccountItem())) {
			getTable().getDataRequestManager().setPageRowCount(2147483647);
		}
		mainQuery.put("GLFixCondition", condition);

		int yearEnd = condition.getPeriodYearEnd();
		int numberEnd = condition.getPeriodNumberEnd();
//		getUIContext().put("company",
//				RptClientUtil.getCompany(yearEnd, numberEnd));

		setCondition(mainQuery);

		this.accountID = condition.getAccountId();
		this.accountCodeStart = condition.getAccountCodeStart();
		this.accountCodeEnd = condition.getAccountCodeEnd();

		setTitlePeriodName(getPeriodTitle(condition.getPeriodYearStart(),
				condition.getPeriodNumberStart(), condition.getPeriodYearEnd(),
				condition.getPeriodNumberEnd()));
		setTitleCurrencyName(condition.getCurrencyName());

		this.lblPeriod.setText(EASResource.getString(
				"com.kingdee.eas.fi.gl.ReportPeriodExtraRes", "PREPERIOD")
				+ getTitlePeriodName());
		this.lblCurrency.setText(EASResource.getString(
				"com.kingdee.eas.fi.gl.ReportPeriodExtraRes", "PRECURRENCY")
				+ getTitleCurrencyName());

		this.actionAssist.setEnabled(false);

		refreshAccountTree();
	}

	protected boolean isNeedUpdateTableHead() {
		return true;
	}

	protected void refreshAccountTree() {
		try {
			ReportResultInfo result = findAccountTreeData();
			if (result != null)
				showDataToTree(result.getData(), this.tree);
		} catch (Exception err) {
			handUIException(err);
		}
	}

	protected ReportResultInfo findAccountTreeData() throws Exception {
		ReportResultInfo result = null;
		IZDFGLReportSubsidiaryLedgerTree report = null;

		report = ZDFGLReportSubsidiaryLedgerTreeFactory.getRemoteInstance();

		if (!isQueryByOther()) {
			ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
					.get("GLFixCondition");
			fixCondition.setAccountId(this.accountID);
			fixCondition.setAccountCodeStart(this.accountCodeStart);
			fixCondition.setAccountCodeEnd(this.accountCodeEnd);
		}

		result = report.findAccountTree(getCondition(),
				(CompanyOrgUnitInfo) getUIContext().get("company"));

		report = null;

		return result;
	}

	protected void addAccountToType(Map accountDataMap, Map typeInfoMap)
			throws EASBizException {
		for (Iterator iterator = accountDataMap.values().iterator(); iterator
				.hasNext();) {
			ReportTreeNodeExtendInfo nodeAccountInfo = (ReportTreeNodeExtendInfo) iterator
					.next();
			if (nodeAccountInfo.getParent() == null) {
				ReportTreeNodeExtendInfo nodeTypeInfo = null;

				if (nodeAccountInfo.getTypeId() != null) {
					nodeTypeInfo = (ReportTreeNodeExtendInfo) typeInfoMap
							.get(nodeAccountInfo.getTypeId());
				}

				if (nodeTypeInfo == null) {
					throw new ReportException(ReportException.ACCOUNT_NO_TYPE);
				}

				nodeTypeInfo.add(nodeAccountInfo, nodeTypeInfo.getChildCount());
				nodeTypeInfo.setItem(true);
			}
		}
	}

	protected ArrayList buildTypeInfoTree(Map typeDataMap)
			throws EASBizException {
		ArrayList rootList = new ArrayList();
		ReportTreeNodeExtendInfo rootExtend = new ReportTreeNodeExtendInfo();
		Map typeUseInfoMap = new HashMap();
		for (Iterator iterator = typeDataMap.values().iterator(); iterator
				.hasNext();) {
			ReportTreeNodeExtendInfo node = (ReportTreeNodeExtendInfo) iterator
					.next();

			if (node.isItem()) {
				node.setItem(false);

				while (node != null) {
					typeUseInfoMap.put(node.getId(), node);
					ReportTreeNodeExtendInfo parentNode = node.getParent();
					if (parentNode != null) {
						parentNode.addSortedChildNode(node, 0);
					}
					node = parentNode;
				}
			}

		}

		for (Iterator iterator = typeUseInfoMap.values().iterator(); iterator
				.hasNext();) {
			ReportTreeNodeExtendInfo node = (ReportTreeNodeExtendInfo) iterator
					.next();
			if (node.getParent() == null) {
				rootExtend.addSortedChildNode(node, 0);
			}

		}

		ReportTreeNodeInfo node = rootExtend.getFirstChild();
		while (node != null) {
			rootList.add(node);
			ReportTreeNodeInfo nextNode = node.getNextBrother();
			node.setNextBrother(null);
			node = nextNode;
		}

		return rootList;
	}

	protected void showDataToTree(ArrayList data, KDTree treeForShowData)
			throws EASBizException {
		Iterator tempIterator = data.iterator();
		ArrayList arrayList = (ArrayList) tempIterator.next();
		Iterator iterator1 = arrayList.iterator();
		Map accountDataMap = (TreeMap) iterator1.next();
		Map typeDataMap = (HashMap) iterator1.next();

		addAccountToType(accountDataMap, typeDataMap);

		ArrayList list = buildTypeInfoTree(typeDataMap);
		ArrayList list1 = new ArrayList();
		list1.addAll(list);
		this.enableTreeEvent = false;

		DefaultKingdeeTreeNode root = null;
		root = new DefaultKingdeeTreeNode(this.ROOT_TITLE);

		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			addChildNode(root, (ReportTreeNodeInfo) iterator.next());
		}

		DefaultTreeModel treeModel = new DefaultTreeModel(root);

		treeForShowData.setModel(treeModel);

		this.enableTreeEvent = true;

		DefaultKingdeeTreeNode firstAccountNode = getFirstAccountNodePath(root);
		if (firstAccountNode != null) {
			treeForShowData.setSelectionPath(new TreePath(firstAccountNode
					.getPath()));
		} else {
			getTable().removeRows(false);
			this.lblAccount.setText(this.PRE_ACCOUNT);
			setAccountString(null);
		}
	}

	protected DefaultKingdeeTreeNode getFirstAccountNodePath(
			DefaultKingdeeTreeNode node) {
		if (node == null) {
			return null;
		}

		Object nodeInfo = node.getUserObject();
		if ((nodeInfo != null)
				&& (ReportTreeNodeInfo.class.isInstance(nodeInfo))
				&& (((ReportTreeNodeInfo) nodeInfo).isItem())) {
			return node;
		}

		DefaultKingdeeTreeNode child = null;
		if (node.getChildCount() > 0) {
			child = getFirstAccountNodePath((DefaultKingdeeTreeNode) node
					.getFirstChild());
		}
		if (child != null) {
			return child;
		}

		DefaultKingdeeTreeNode brother = getFirstAccountNodePath((DefaultKingdeeTreeNode) node
				.getNextNode());
		if (brother != null) {
			return brother;
		}

		return null;
	}

	protected void addChildNode(DefaultKingdeeTreeNode parentNode,
			ReportTreeNodeInfo nodeInfo) {
		if (nodeInfo == null) {
			return;
		}

		String nodeTitle = null;
		if (nodeInfo.isItem()) {
			StringBuffer sb = new StringBuffer();
			sb.append(nodeInfo.getNumber());
			sb.append(" - ");
			sb.append(nodeInfo.getName());
			nodeTitle = sb.toString();
		} else {
			nodeTitle = nodeInfo.getName();
		}
		DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode(nodeTitle);
		node.setUserObject(nodeInfo);

		parentNode.add(node);

		addChildNode(parentNode, nodeInfo.getNextBrother());

		addChildNode(node, nodeInfo.getFirstChild());
	}

	public CommonQueryDialog getConditionDialog() throws Exception {
		boolean byOther = false;

		if (isQueryByOther()) {
			getConditionPanel();
			byOther = true;
		}
		if (this.conditionDialog == null) {
			this.conditionDialog = new CommonQueryDialog();

			if (byOther)
				this.conditionDialog.setOwner(InitClientHelp
						.getFrameAncestor(this));
			else {
				this.conditionDialog.setOwner((Component) getUIContext().get(
						"Owner"));
			}
			this.conditionDialog.setParentUIClassName(getMetaDataPK()
					.getFullName());
			this.conditionDialog.setQueryObjectPK(getQueryMetaDataPK());

			this.conditionDialog.setShowFilter(true);
			this.conditionDialog.setShowSorter(true);
			this.conditionDialog.setWidth(400);
			this.conditionDialog.setHeight(330);
			this.conditionDialog.addUserPanel(getConditionPanel());
			this.conditionDialog.setTitle(getConditionPanel().getUITitle());

			if (this.conditionPanelAdvanced == null) {
				this.conditionPanelAdvanced = new ZDFReportSubsidiaryLedgerConditionAdvanceUI();
			}

			this.conditionPanelAdvanced
					.setPanelName(this.conditionPanelAdvanced.getToolTipText());
			this.conditionDialog.addUserPanel(this.conditionPanelAdvanced);

			this.conditionPanel.chkOpOnlyAsst
					.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean isSelect = ZDFReportSubsidiaryLedgerUI.this.conditionPanel.chkOpOnlyAsst
									.isSelected();
							ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.tblAct
									.setEnabled(isSelect);
							ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.chkOpAsstGroup
									.setEnabled(isSelect);
							ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.kdUp
									.setEnabled(isSelect);
							ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.kdDown
									.setEnabled(isSelect);
							if (!isSelect) {
								ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.chkOpAsstGroup
										.setSelected(false);
							}

							for (int i = 0; i < ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.tblAct
									.getColumnCount(); i++)
								if (i != 2)
									if (isSelect)
										ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.tblAct
												.getColumn(i)
												.getStyleAttributes()
												.setBackground(
														ZDFReportSubsidiaryLedgerUI.UNLOCKCOLOR);
									else
										ZDFReportSubsidiaryLedgerUI.this.conditionPanelAdvanced.tblAct
												.getColumn(i)
												.getStyleAttributes()
												.setBackground(
														ZDFReportSubsidiaryLedgerUI.LOCKCOLOR);
						}
					});
			if (!this.conditionPanel.chkOpOnlyAsst.isSelected()) {
				this.conditionPanelAdvanced.tblAct.setEnabled(false);
				this.conditionPanelAdvanced.chkOpAsstGroup.setEnabled(false);
				this.conditionPanelAdvanced.chkOpAsstGroup.setSelected(false);
				this.conditionPanelAdvanced.kdUp.setEnabled(false);
				this.conditionPanelAdvanced.kdDown.setEnabled(false);

				for (int i = 0; i < this.conditionPanelAdvanced.tblAct
						.getColumnCount(); i++) {
					if (i != 2) {
						this.conditionPanelAdvanced.tblAct.getColumn(i)
								.getStyleAttributes().setBackground(LOCKCOLOR);
					}

				}

			}

		}

		return this.conditionDialog;
	}

	public CustomerQueryPanel getConditionPanel() throws Exception {
		if (this.conditionPanel == null) {
			this.conditionPanel = new ZDFReportSubsidiaryLedgerConditionUI(getUIContext().get("accountViews"));
			if (getInitData() == null) {
				setInitData(findInitData());
			}
			this.conditionPanel.setInitData(getInitData());
		}

		if (this.conditionPanelAdvanced == null) {
			this.conditionPanelAdvanced = new ZDFReportSubsidiaryLedgerConditionAdvanceUI();
		}

		if (isQueryByOther()) {
			this.conditionPanel.setPeriodEntity(getCurrentCompany());

			if (getCondition() != null) {
				ZDFReportConditionSubsidiaryLedger glCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
						.get("GLFixCondition");
				if ((glCondition.getExpandInfo().get("isSumLineQuery") != null)
						&& (((Boolean) glCondition.getExpandInfo().get(
								"isSumLineQuery")).booleanValue())) {
					glCondition.setAccountCodeEnd(null);
					glCondition.setAccountCodeStart(null);
				}
				if (glCondition != null) {
					this.conditionPanel.setParam(glCondition.toMap());

					this.conditionPanelAdvanced.setParam(glCondition.toMap());
					this.conditionPanelAdvanced.setJoinQuery(true);
				}
			}
			setQueryByOther(false);
		}

		return this.conditionPanel;
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		if (getCondition() == null) {
			openConditionDialog();
		} else {
			if (!isQueryByOther()) {
				ZDFReportConditionSubsidiaryLedger glCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
						.get("GLFixCondition");
				if (glCondition != null) {
					glCondition.setAccountId(null);
				}
			}
			refreshAccountTree();
		}
	}

	public void actionAssist_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo currentCondition = getCondition();
		if (currentCondition == null) {
			return;
		}

		ZDFReportConditionSubsidiaryLedger glCondition = (ZDFReportConditionSubsidiaryLedger) currentCondition
				.get("GLFixCondition");
		if (glCondition == null) {
			return;
		}
		if (glCondition.getOptionNotIncludePLVoucher()) {
			AccountViewInfo acc = null;
			if ((glCondition.getAccountId() != null)
					&& (glCondition.getAccountId().trim().length() > 0)) {
				acc = AccountViewFactory.getRemoteInstance()
						.getAccountViewInfo(
								new ObjectUuidPK(glCondition.getAccountId()));
			}
			if (((acc == null) || ((acc != null) && (acc.getPLType() != AccountPLType.NONE)))
					&& (!MsgBox
							.isOk(MsgBox
									.showConfirm2(
											this,
											EASResource
													.getString(
															"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
															"174_ReportSubsidiaryLedgerUI"))))) {
				SysUtil.abort();
			}

		}

		EntityViewInfo assistCondition = (EntityViewInfo) currentCondition
				.clone();

		ReportConditionSubsidiaryLedgerAssist fixCondition = new ReportConditionSubsidiaryLedgerAssist(
				glCondition);
		fixCondition.setAccountId(glCondition.getAccountId());

		fixCondition.setOptionOnlyAsst(glCondition.getOptionOnlyAsst());
		fixCondition.setTableData(glCondition.getTableData());
		fixCondition.setOptionOtherAccount(false);
		fixCondition.setOptionAsstGroup(glCondition.getOptionAsstGroup());
		fixCondition.setAcctAsstItems(glCondition.getAcctAsstItems());

		assistCondition.put("GLFixCondition", fixCondition);
		if (fixCondition.isAllCurrency()) {
			if (this.tblMain.getSelectManager().get() == null) {
				return;
			}
			int selectIndex = this.tblMain.getSelectManager().get().getTop();
			IRow row = this.tblMain.getRow(selectIndex);
			String str = row.getCell("fcurrencyid") == null ? null
					: (String) row.getCell("fcurrencyid").getValue();
			if (str != null) {
				fixCondition.setCurrencyID(str);
				fixCondition
						.setCurrencyName(row.getCell("FCurrencyName") == null ? null
								: (String) row.getCell("FCurrencyName")
										.getValue());
			}
		}
		ReportSubsidiaryLedgerAssistUI.queryByCondition(assistCondition, this,
				(CompanyOrgUnitInfo) getUIContext().get("company"));
	}

	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);

		queryVoucher();
	}

	protected void queryVoucher() throws Exception {
		if (getTable().getSelectManager().size() > 0) {
			int selectIndex = getTable().getSelectManager().get().getTop();

			IRow row = getTable().getRow(selectIndex);

			if (row != null) {
				Object voucherId = row.getUserObject();
				if ((voucherId != null) && (String.class.isInstance(voucherId))
						&& (((String) voucherId).length() > 0)) {
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", ((String) voucherId).trim());
					uiContext.put("company",
							(CompanyOrgUnitInfo) getUIContext().get("company"));

					uiContext.put("NOTLICHA", "NOTLICHA");

					IUIWindow uiWindow = getUI(this, VoucherInfo
							.getEditUIName(), uiContext);
					uiWindow.show();
				}
			}
		}
	}

	public void actionGeneralLedger_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionGeneralLedger_actionPerformed(e);

		EntityViewInfo currentCondition = getCondition();
		if (currentCondition == null) {
			return;
		}

		ReportConditionBase glCondition = (ReportConditionBase) currentCondition
				.get("GLFixCondition");
		if (glCondition == null) {
			return;
		}
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) getCondition()
				.get("GLFixCondition");
		if (condition == null) {
			return;
		}
		if (condition.getOptionNotIncludePLVoucher()) {
			AccountViewInfo acc = null;
			if ((glCondition.getAccountId() != null)
					&& (glCondition.getAccountId().trim().length() > 0)) {
				acc = AccountViewFactory.getRemoteInstance()
						.getAccountViewInfo(
								new ObjectUuidPK(glCondition.getAccountId()));
			}
			if (((acc == null) || ((acc != null) && (acc.getPLType() != AccountPLType.NONE)))
					&& (!MsgBox
							.isOk(MsgBox
									.showConfirm2(
											this,
											EASResource
													.getString(
															"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
															"175_ReportSubsidiaryLedgerUI"))))) {
				SysUtil.abort();
			}

		}

		EntityViewInfo generalLedgerCondition = (EntityViewInfo) currentCondition
				.clone();
		ReportConditionGeneralLedger fixCondition = new ReportConditionGeneralLedger(
				glCondition);
		fixCondition
				.setOptionNoDisplayZeroTotal(((ZDFReportConditionSubsidiaryLedger) glCondition)
						.getOptionNoDisplayZeroTotal());
		fixCondition
				.setOptionNOTDisplayIfZeroNoAmount(((ZDFReportConditionSubsidiaryLedger) glCondition)
						.getOptionBalanceAndAmountZero());
		fixCondition.setAccountId(glCondition.getAccountId());
		fixCondition
				.setOptionNOTDisplayIfNoAmount(((ZDFReportConditionSubsidiaryLedger) glCondition)
						.getOptionAmountZero());
		generalLedgerCondition.put("GLFixCondition", fixCondition);
		if (fixCondition.isAllCurrency()) {
			if (this.tblMain.getSelectManager().get() == null) {
				return;
			}
			int selectIndex = this.tblMain.getSelectManager().get().getTop();
			IRow row = this.tblMain.getRow(selectIndex);
			String str = row.getCell("fcurrencyid") == null ? null
					: (String) row.getCell("fcurrencyid").getValue();
			if (str != null) {
				fixCondition.setCurrencyID(str);
				fixCondition
						.setCurrencyName(row.getCell("FCurrencyName") == null ? null
								: (String) row.getCell("FCurrencyName")
										.getValue());
			}
		}
		ReportGeneralLedgerUI.queryByCondition(generalLedgerCondition, this,
				(CompanyOrgUnitInfo) getUIContext().get("company"), !SysContext
						.getSysContext().getCurrentFIUnit().isIsBizUnit());
	}

	public void tableDataRequest(KDTDataRequestEvent e) {
		try {
			this.requesting = true;

			super.tableDataRequest(e);

			ReportResultInfo result = getResult();

			if ((result != null) && (e.getFirstRow() == 0)) {
				AccountViewInfo accountViewInfo = (AccountViewInfo) result
						.getExtendData().get("Account");

				if (accountViewInfo != null) {
					if (isQueryByOther()) {
						EntityViewInfo condition = getCondition();
						ZDFReportConditionSubsidiaryLedger glCondition = (ZDFReportConditionSubsidiaryLedger) condition
								.get("GLFixCondition");
						if ((glCondition != null)
								&& ((glCondition.getExpandInfo().get(
										"isSumLineQuery") == null) || (!((Boolean) glCondition
										.getExpandInfo().get("isSumLineQuery"))
										.booleanValue()))) {
							glCondition.setAccountCodeStart(accountViewInfo
									.getNumber());
							glCondition.setAccountCodeEnd(accountViewInfo
									.getNumber());
							glCondition.setAccountLevelStart(accountViewInfo
									.getLevel());
							glCondition.setAccountLevelEnd(accountViewInfo
									.getLevel());
						}

					}

					StringBuffer sb = new StringBuffer();
					sb.append("[");
					sb.append(accountViewInfo.getNumber());
					sb.append("]");

					Boolean accountShowLongName = (Boolean) result
							.getExtendData().get("AccountShowLongName");
					if ((accountShowLongName != null)
							&& (accountShowLongName.booleanValue()))
						sb.append(accountViewInfo.getLongName());
					else {
						sb.append(accountViewInfo.getName());
					}

					setAccountString(sb.toString());

					this.lblAccount.setText(this.PRE_ACCOUNT + sb.toString());

					this.actionAssist.setEnabled((canJoinQueryForPeriodLimit())
							&& (canJoinQuery())
							&& (accountViewInfo.getCAA() != null)
							&& (accountViewInfo.isIsLeaf()));
				} else {
					this.actionAssist.setEnabled(false);
				}
			}
		} finally {
			this.requesting = false;
		}
	}

	protected void showResult(ReportResultInfo result,
			KDTable tableForShowResult, int startRow) {
		if ((result == null) || (tableForShowResult == null)) {
			return;
		}
		tableForShowResult.setRefresh(false);
		if ((startRow == 0)
				&& (result.getTableHeadInfo() != null)
				&& ((getCurrentCompany() == null)
						|| (!getCurrentCompany().isIsBizUnit()) || ((getCurrentCompany()
						.isIsBizUnit()) && (result.getRowCount() >= 0)))) {
			initTableHead(result.getTableHeadInfo(), tableForShowResult);

			if (!isQueryByOther()) {
				try {
					ZDFReportConditionSubsidiaryLedger condition = ((ZDFReportSubsidiaryLedgerConditionUI) getConditionPanel())
							.getCustomCondition();

					if (condition.getOptionShowAccountCusAttribute()) {
						ReportTableHeadInfo tableHeadInfo = result
								.getTableHeadInfo();

						for (int i = 0; i < tableForShowResult.getColumnCount(); i++) {
							if ((!tableForShowResult.getColumn(i).getKey()
									.equals("FBizNumber"))
									&& (!tableForShowResult.getColumn(i)
											.getKey().equals("FFeeType"))
									&& (!tableForShowResult.getColumn(i)
											.getKey().equals("FHandler"))
									&& (!tableForShowResult.getColumn(i)
											.getKey().equals("FInvoiceNumber"))
									&& (!tableForShowResult.getColumn(i)
											.getKey().equals("FSettlementCode"))
									&& (!tableForShowResult.getColumn(i)
											.getKey().equals("FSettlementType"))
									&& (!tableForShowResult.getColumn(i)
											.getKey().equals("FTicketNumber"))) {
								continue;
							}

							if ((tableHeadInfo.getVisibles() != null)
									&& (!tableHeadInfo.getVisibles()[i])) {
								tableForShowResult.getColumn(i)
										.getStyleAttributes().setHided(true);
							} else {
								if ((tableHeadInfo.getVisibles() == null)
										|| (tableHeadInfo.getVisibles()[i]))
									tableForShowResult.getColumn(i)
											.getStyleAttributes().setHided(
													false);
							}
						}
					}
				} catch (Exception e) {
				}

			}

			tableForShowResult.setRowCount(result.getRowCount());
		}

		showData(result.getData(), tableForShowResult, startRow);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);

		this.actionVoucher.setEnabled(isEnabledVoucher());
	}

	protected boolean isEnabledVoucher() throws Exception {
		boolean isEnabledVoucher = false;

		if (getTable().getSelectManager().size() > 0) {
			int selectIndex = getTable().getSelectManager().get().getTop();

			IRow row = getTable().getRow(selectIndex);

			if (row != null) {
				Object accountId = row.getUserObject();
				if ((accountId != null) && (String.class.isInstance(accountId))
						&& (((String) accountId).length() > 0)) {
					isEnabledVoucher = true;
				}
			}
		}

		return isEnabledVoucher;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);

		if (e.getClickCount() == 2)
			try {
//				setCursorOfWair();
//
//				queryVoucher();
			} finally {
				setCursorOfDefault();
			}
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.btnAssist.setIcon(EASResource
				.getIcon("imgTbtn_assistantlistaccount"));
		this.btnGeneralLedger.setIcon(EASResource
				.getIcon("imgTbtn_overallsortaccount"));
		this.btnVoucher.setIcon(EASResource.getIcon("imgTbtn_credence"));
	}

	protected Variant tableRequestPrintDataOther(String varName) {
		if (varName.equals("Account")) {
			Variant v = new Variant();
			String AccountString = getAccountString() == null ? ""
					: getAccountString();

			v.setObject(AccountString);
			return v;
		}

		return super.tableRequestPrintDataOther(varName);
	}

	public String getAccountString() {
		return this.accountString;
	}

	public void setAccountString(String string) {
		this.accountString = string;
	}

	protected int appendHeadRow(HeadFootModel header) {
		int rowheight = 35;
		int appendHeight = 70;

		StyleAttributes sa = Styles.getDefaultSA();
		String account = this.ROOT_TITLE + ":&[Account]";

		header.addRow(account + "&|" + " ", sa);
		return appendHeight;
	}

	public void confirmEnd() {
		try {
			while (this.requesting)
				Thread.sleep(100L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected boolean isAllowLoadPrintSetting() {
		return true;
	}

	public void actionPrintParame_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("editParame", this.editParame);
		Integer maxLevel = (Integer) this.initData.get("MaxAccountLevel");
		if (maxLevel != null) {
			this.editParame.setMaxLevel(maxLevel.intValue());
		}
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(
						"com.kingdee.eas.fi.gl.client.ReportSubsidiaryParameUI",
						uiContext, null, OprtState.EDIT);

		uiWindow.show();
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.tblMain.getBody().getRows().size() != 0) {
			if (!this.editParame.isChkPrintParame()) {
				print(false);
			} else {
				if ((this.editParame.isChkPrintAccountContents())
						&& (invokePrintFuncAccount(e, false) == 2)) {
					return;
				}

				invokePrintFunc(e, false);
			}
		} else
			showInfoCom();
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.tblMain.getBody().getRows().size() != 0) {
			if (!this.editParame.isChkPrintParame()) {
				print(true);
			} else {
				if (this.editParame.isChkPrintAccountContents()) {
					invokePrintFuncAccount(e, true);
				}

				invokePrintFunc(e, true);
			}
		} else
			showInfoCom();
	}

	private void showInfoCom() {
		KDTable tbl = getTableForPrintSetting();
		tbl.getPrintManager().getNewPrintManager().getPrinter().prompt(
				Resources.getMsg("prompt.nocontent"));
	}

	private void print(boolean preview) throws Exception {
		TreePath tps[] = null;
		MultiPrint mp;
		int i;
		int n;
		Map var;
		try {
			setCursorOfWair();
			mp = new MultiPrint(this);
			if (!mp.showPrintOption())
				return;
		} finally {
			setNeedInitHead(true);
			if (tps != null)
				tree.setSelectionPath(tps[tps.length - 1]);
		}
		preparePrintPage(tblMain);
		mp.getMultiPrintModel().setTable(tblMain);
		tps = getAccountTreePath(mp.isPrintAll());
		mp.getMultiPrintModel().setRequestData(this);
		i = 0;
		for (n = tps.length; i < n; i++) {
			var = new HashMap();
			var.put("CompanyName", getUIContext().get("company").toString());
			var.put("User", SysContext.getSysContext().getUserName());
			var.put("Period", lblPeriod.getText().substring(3));
			var.put("Currency", lblCurrency.getText().substring(3));
			var.put("Account", tps[i].getLastPathComponent().toString());
			mp.getMultiPrintModel().addVarParser("PrintJob_" + i, var);
			mp.getMultiPrintModel().addTreePath("PrintJob_" + i, tps[i]);
		}

		if (tps.length == 1)
			setNeedInitHead(false);
		if (preview) {
			mp.preview();
		} else {
			mp.setPrintJobName(getUITitle());
			mp.print();
		}
	}

	protected void invokePrintFunc(ActionEvent evt, boolean noPreview)
			throws Exception {
		boolean bBF = this.editParame.isChkBF();
		boolean bSR = false;
		boolean ischkOpDailyTotal = false;
		if (isQueryByOther()) {
			ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
					.get("GLFixCondition");
			if (fixCondition.getOptionOnlyAsst()) {
				bSR = this.editParame.isChkSR();
				ischkOpDailyTotal = fixCondition.getOptionDailyTotal();
			}
		} else if (this.conditionPanel.chkOpOnlyAsst.isSelected()) {
			bSR = this.editParame.isChkSR();
			ischkOpDailyTotal = this.conditionPanel.chkOpDailyTotal
					.isSelected();
		}

		String strTemplatepathPath = null;
		strTemplatepathPath = this.editParame.getStrTempletpathPath();
		String RESOURCE_PATH = "com.kingdee.eas.fi.gl.client.VoucherEditResource";
		if ((StringUtils.isEmpty(strTemplatepathPath))
				|| ("null".equalsIgnoreCase(strTemplatepathPath.trim()))) {
			String msg = EASResource.getString(RESOURCE_PATH, "notTemplet");
			MsgBox.showInfo(msg);
			SysUtil.abort();
		}

		TreePath[] tps = getAccountTreePath(false);

		Map printParamMap = new HashMap();
		printParamMap.put("isSumPeriod", new Boolean(this.editParame
				.isSumPeriod()));
		printParamMap
				.put("isSumYear", new Boolean(this.editParame.isSumYear()));

		printParamMap.put("isSRPage", new Boolean(this.editParame.isSRPage()));

		if ((bSR) && (this.editParame.isSRPage())) {
			bSR = false;
		}
		printParamMap.put("isSubPage", new Boolean(bSR));

		ICrossPrintDataProvider[] dps = { new DataProvider(this, tps, bBF,
				printParamMap, ischkOpDailyTotal) };

		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.crossPrint(new String[] { strTemplatepathPath }, dps, noPreview,
				this);
	}

	protected int invokePrintFuncAccount(ActionEvent evt, boolean noPreview)
			throws Exception {
		String strTemplatepathPathAccount = this.editParame
				.getStrTempletpathPathAccount();
		String RESOURCE_PATH = "com.kingdee.eas.fi.gl.client.VoucherEditResource";
		if ((StringUtils.isEmpty(strTemplatepathPathAccount))
				|| (StringUtils.isEmpty(this.editParame
						.getStrTempletpathNameAccount()))
				|| ("null".equalsIgnoreCase(strTemplatepathPathAccount.trim()))) {
			String msg = EASResource.getString(RESOURCE_PATH, "notTemplet");
			MsgBox.showInfo(msg);
			SysUtil.abort();
		}

		boolean bBF = this.editParame.isChkBF();
		boolean bSR = this.editParame.isChkSR();
		boolean isSRPage = this.editParame.isSRPage();
		if ((bSR) && (isSRPage)) {
			bSR = false;
		}
		boolean ischkOpDailyTotal = false;
		String mothy = "";
		String periodTime = "";
		if (isQueryByOther()) {
			ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
					.get("GLFixCondition");
			if (fixCondition.getPeriodNumberStart() < 10)
				mothy = "0" + fixCondition.getPeriodNumberStart();
			else {
				mothy = String.valueOf(fixCondition.getPeriodNumberStart());
			}
			periodTime = fixCondition.getPeriodYearStart() + "-" + mothy
					+ "-01";
		} else {
			ischkOpDailyTotal = this.conditionPanel.chkOpDailyTotal
					.isSelected();
			if (this.conditionPanel.getCustomCondition().getPeriodNumberStart() < 10)
				mothy = "0"
						+ this.conditionPanel.getCustomCondition()
								.getPeriodNumberStart();
			else {
				mothy = String.valueOf(this.conditionPanel.getCustomCondition()
						.getPeriodNumberStart());
			}
			periodTime = this.conditionPanel.getCustomCondition()
					.getPeriodYearStart()
					+ "-" + mothy + "-01";
		}

		TreePath[] tps = getAccountTreePath(false);
		ReportResultInfo rs = null;

		Map mapGetResult = new HashMap();

		int i = 0;
		for (int n = tps.length; i < n; i++) {
			ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) getCondition()
					.get("GLFixCondition");
			boolean isChkSum = this.editParame.isChkSum();
			if (isChkSum) {
				boolean isRdoIsAccount = this.editParame.isRdoIsAccount();
				boolean isRdoIsAccountAndDesc = this.editParame
						.isRdoIsAccountAndDesc();
				boolean isChkIsZeroNoDisplay = this.editParame
						.isChkIsZeroNoDisplay();
				int spnAccountLevel = this.editParame.getSpnAccountLevel();
				condition.getExpandInfo().put("isChkSum",
						Boolean.valueOf(isChkSum));
				condition.getExpandInfo().put("isRdoIsAccount",
						Boolean.valueOf(isRdoIsAccount));
				condition.getExpandInfo().put("isRdoIsAccountAndDesc",
						Boolean.valueOf(isRdoIsAccountAndDesc));
				condition.getExpandInfo().put("isChkIsZeroNoDisplay",
						Boolean.valueOf(isChkIsZeroNoDisplay));
				condition.getExpandInfo().put("spnAccountLevel",
						Integer.valueOf(spnAccountLevel + ""));

				int oldCount = this.tblMain.getDataRequestManager()
						.getPageRowCount();
				this.tblMain.getDataRequestManager()
						.setPageRowCount(2147483647);
				requestTableData2(tps[i]);
				rs = this.result;
				rs = (ReportResultInfo) rs.clone();

				condition.getExpandInfo().remove("isChkSum");
				condition.getExpandInfo().remove("isRdoIsAccount");
				condition.getExpandInfo().remove("isRdoIsAccountAndDesc");
				condition.getExpandInfo().remove("isChkIsZeroNoDisplay");
				condition.getExpandInfo().remove("spnAccountLevel");
				this.tblMain.getDataRequestManager().setPageRowCount(oldCount);
				requestTableData2(tps[i]);
			} else {
				condition.getExpandInfo().put("isForTaoda", Boolean.TRUE);
				int oldCount = this.tblMain.getDataRequestManager()
						.getPageRowCount();
				this.tblMain.getDataRequestManager()
						.setPageRowCount(2147483647);
				requestTableData2(tps[i]);

				condition.getExpandInfo().remove("isForTaoda");
				this.tblMain.getDataRequestManager().setPageRowCount(oldCount);
				rs = this.result;
				rs = (ReportResultInfo) rs.clone();
			}
			ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
					.get("GLFixCondition");
			String dateStr = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (fixCondition.isByPeriod())
				try {
					PeriodInfo periodinfo = PeriodUtils.getPeriodInfo(null,
							fixCondition.getPeriodYearEnd(), fixCondition
									.getPeriodNumberEnd(), SysContext
									.getSysContext().getCurrentFIUnit());
					dateStr = sdf.format(periodinfo.getEndDate());
				} catch (Exception e) {
					e.printStackTrace();
				}
			else {
				dateStr = sdf.format(fixCondition.getDateEnd());
			}
			rs.getExtendData().put("endDate", dateStr);
			mapGetResult.put(new Integer(i), rs);
			this.mapGlobalRs.put(new Integer(i), rs);
		}

		KDNoteHelper appHlp = new KDNoteHelper();
		ReportSubsidiaryLedgerTaodaAccount rsprv = null;

		if (isQueryByOther()) {
			ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) getCondition()
					.get("GLFixCondition");
			if (fixCondition.getOptionOnlyAsst()) {
				rsprv = new ReportSubsidiaryLedgerTaodaAccount(mapGetResult,
						tps, bBF, bSR, isSRPage, ischkOpDailyTotal, periodTime);
			} else {
				rsprv = new ReportSubsidiaryLedgerTaodaAccount(mapGetResult,
						tps, bBF);
			}
		} else if (this.conditionPanel.chkOpOnlyAsst.isSelected()) {
			rsprv = new ReportSubsidiaryLedgerTaodaAccount(mapGetResult, tps,
					bBF, bSR, isSRPage, ischkOpDailyTotal, periodTime);
		} else {
			rsprv = new ReportSubsidiaryLedgerTaodaAccount(mapGetResult, tps,
					bBF);
		}

		return appHlp.directPrint(strTemplatepathPathAccount, rsprv, noPreview,
				this);
	}

	protected static List acctAsstItemsToList(String acctAsstItems) {
		AcctountAsstItemParser.AsstItem[] asstItems = null;
		try {
			AcctountAsstItemParser.AccountAsstItem accountAsstItem = AcctountAsstItemParser
					.parse(acctAsstItems);
			asstItems = accountAsstItem.asstItems;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List list = null;
		try {
			list = RptClientUtil.queryAsstAct(null, SysContext.getSysContext()
					.getCurrentFIUnit().getCU().getId().toString());
			for (int j = 0; j < list.size(); j++) {
				AsstactTypeEntity at = (AsstactTypeEntity) list.get(j);
				at.setSelected(false);
			}
			if (asstItems != null) {
				for (int i = 0; i < asstItems.length; i++) {
					AcctountAsstItemParser.AsstItem asstItem = asstItems[i];
					for (int j = 0; j < list.size(); j++) {
						AsstactTypeEntity at = (AsstactTypeEntity) list.get(j);
						if (at.getNumber().equals(asstItem.asstTypeNumber)) {
							at.setSelected(true);

							if (asstItem.group != null) {
								String asstGroup = "";
								for (int l = 0; l < asstItem.group.length; l++) {
									AcctountAsstItemParser.Group group = asstItem.group[l];
									if (asstGroup != "") {
										asstGroup = asstGroup + "&";
									}
									asstGroup = asstGroup
											+ group.standardNumber;
									if ((group.startNumber != null)
											&& (group.startNumber.length() > 0)) {
										asstGroup = asstGroup + '#'
												+ group.startNumber;
									}
									if ((group.endNumber != null)
											&& (group.endNumber.length() > 0)) {
										asstGroup = asstGroup + ':'
												+ group.endNumber;
									}
								}
								at.setAsstGroup(asstGroup);
							}
							if ((asstItem.startNumber != null)
									&& (asstItem.startNumber.length() > 0)) {
								Object asstActInfo = GlUtils.findAsstActObject(
										at.getHgAttribute(),
										asstItem.startNumber, SysContext
												.getSysContext()
												.getCurrentFIUnit().getCU()
												.getId().toString());
								at.setFrom(asstActInfo);
							}
							if ((asstItem.endNumber == null)
									|| (asstItem.endNumber.length() <= 0))
								break;
							Object asstActInfo = GlUtils.findAsstActObject(at
									.getHgAttribute(), asstItem.endNumber,
									SysContext.getSysContext()
											.getCurrentFIUnit().getCU().getId()
											.toString());
							at.setTo(asstActInfo);
							break;
						}
					}
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return list;
	}

	protected IObjectPK getOrgPK(ItemAction action) {
		if ((curCompany == null) || (curCompany.getId() == null)) {
			return super.getOrgPK(action);
		}
		IObjectPK orgPK = new ObjectUuidPK(curCompany.getId());

		return orgPK;
	}

	class DataProvider implements ICrossPrintDataProvider {
		private int point = 0;
		private ZDFReportSubsidiaryLedgerUI ui;
		private TreePath[] tps;
		private boolean bBF = false;
		boolean isBusinessTime = false;
		boolean ischkOpDailyTotal = false;

		private Map printParamMap = new HashMap();

		public DataProvider(ZDFReportSubsidiaryLedgerUI ui, TreePath[] tps,
				boolean bBF) {
			this.ui = ui;
			this.tps = tps;
			this.bBF = bBF;
			if (ui.isQueryByOther()) {
				ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) ui
						.getCondition().get("GLFixCondition");
				if (fixCondition.getOptionOnlyAsst())
					this.ischkOpDailyTotal = fixCondition.getOptionDailyTotal();
			} else {
				this.ischkOpDailyTotal = ZDFReportSubsidiaryLedgerUI.this.conditionPanel.chkOpDailyTotal
						.isSelected();
			}
		}

		public DataProvider(ZDFReportSubsidiaryLedgerUI ui, TreePath[] tps,
				boolean bBF, Map printParamMap) {
			this.ui = ui;
			this.tps = tps;
			this.bBF = bBF;
			if (ui.isQueryByOther()) {
				ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) ui
						.getCondition().get("GLFixCondition");
				if (fixCondition.getOptionOnlyAsst())
					this.ischkOpDailyTotal = fixCondition.getOptionDailyTotal();
			} else {
				this.ischkOpDailyTotal = ZDFReportSubsidiaryLedgerUI.this.conditionPanel.chkOpDailyTotal
						.isSelected();
			}
			if (bBF)
				this.printParamMap = printParamMap;
		}

		public DataProvider(ZDFReportSubsidiaryLedgerUI ui, TreePath[] tps,
				boolean bBF, Map printParamMap, boolean ischkOpDailyTotal) {
			this.ui = ui;
			this.tps = tps;
			this.bBF = bBF;
			this.ischkOpDailyTotal = ischkOpDailyTotal;
			if (bBF) {
				this.printParamMap = printParamMap;
			}
			if (ui.isQueryByOther()) {
				ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) ui
						.getCondition().get("GLFixCondition");
				if (fixCondition.getOptionOnlyAsst()) {
					this.printParamMap = printParamMap;
				}
			} else if (ZDFReportSubsidiaryLedgerUI.this.conditionPanel.chkOpOnlyAsst
					.isSelected()) {
				this.printParamMap = printParamMap;
			}
		}

		public boolean hasNext() {
			return this.point < this.tps.length;
		}

		public IRowSet execute(BOSQueryDataSource ds) {
			TreePath tp = null;
			ReportResultInfo rs = null;

			String mothy = "";
			String periodTime = "";
			if (this.ui.isQueryByOther()) {
				ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) this.ui
						.getCondition().get("GLFixCondition");
				if (fixCondition.getPeriodNumberStart() < 10)
					mothy = "0" + fixCondition.getPeriodNumberStart();
				else {
					mothy = String.valueOf(fixCondition.getPeriodNumberStart());
				}
				periodTime = fixCondition.getPeriodYearStart() + "-" + mothy
						+ "-01";
			} else {
				if (ZDFReportSubsidiaryLedgerUI.this.conditionPanel
						.getCustomCondition().getPeriodNumberStart() < 10)
					mothy = "0"
							+ ZDFReportSubsidiaryLedgerUI.this.conditionPanel
									.getCustomCondition()
									.getPeriodNumberStart();
				else {
					mothy = String
							.valueOf(ZDFReportSubsidiaryLedgerUI.this.conditionPanel
									.getCustomCondition()
									.getPeriodNumberStart());
				}
				periodTime = ZDFReportSubsidiaryLedgerUI.this.conditionPanel
						.getCustomCondition().getPeriodYearStart()
						+ "-" + mothy + "-01";
			}

			if (ZDFReportSubsidiaryLedgerUI.this.editParame
					.isChkPrintAccountContents()) {
				rs = (ReportResultInfo) ZDFReportSubsidiaryLedgerUI.this.mapGlobalRs
						.get(new Integer(this.point));
				rs = (ReportResultInfo) rs.clone();
				tp = this.tps[(this.point++)];
			} else {
				tp = this.tps[(this.point++)];

				ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) this.ui
						.getCondition().get("GLFixCondition");
				boolean isChkSum = ZDFReportSubsidiaryLedgerUI.this.editParame
						.isChkSum();
				if (isChkSum) {
					boolean isRdoIsAccount = ZDFReportSubsidiaryLedgerUI.this.editParame
							.isRdoIsAccount();
					boolean isRdoIsAccountAndDesc = ZDFReportSubsidiaryLedgerUI.this.editParame
							.isRdoIsAccountAndDesc();
					boolean isChkIsZeroNoDisplay = ZDFReportSubsidiaryLedgerUI.this.editParame
							.isChkIsZeroNoDisplay();
					int spnAccountLevel = ZDFReportSubsidiaryLedgerUI.this.editParame
							.getSpnAccountLevel();
					condition.getExpandInfo().put("isChkSum",
							Boolean.valueOf(isChkSum));
					condition.getExpandInfo().put("isRdoIsAccount",
							Boolean.valueOf(isRdoIsAccount));
					condition.getExpandInfo().put("isRdoIsAccountAndDesc",
							Boolean.valueOf(isRdoIsAccountAndDesc));
					condition.getExpandInfo().put("isChkIsZeroNoDisplay",
							Boolean.valueOf(isChkIsZeroNoDisplay));
					condition.getExpandInfo().put("spnAccountLevel",
							Integer.valueOf(spnAccountLevel + ""));

					int oldCount = ZDFReportSubsidiaryLedgerUI.this.tblMain
							.getDataRequestManager().getPageRowCount();
					ZDFReportSubsidiaryLedgerUI.this.tblMain
							.getDataRequestManager()
							.setPageRowCount(2147483647);
					this.ui.requestTableData2(tp);
					rs = ZDFReportSubsidiaryLedgerUI.this.result;

					condition.getExpandInfo().remove("isChkSum");
					condition.getExpandInfo().remove("isRdoIsAccount");
					condition.getExpandInfo().remove("isRdoIsAccountAndDesc");
					condition.getExpandInfo().remove("isChkIsZeroNoDisplay");
					condition.getExpandInfo().remove("spnAccountLevel");
					ZDFReportSubsidiaryLedgerUI.this.tblMain
							.getDataRequestManager().setPageRowCount(oldCount);
					this.ui.requestTableData2(tp);
				} else {
					condition.getExpandInfo().put("isForTaoda", Boolean.TRUE);
					int oldCount = ZDFReportSubsidiaryLedgerUI.this.tblMain
							.getDataRequestManager().getPageRowCount();
					ZDFReportSubsidiaryLedgerUI.this.tblMain
							.getDataRequestManager()
							.setPageRowCount(2147483647);
					this.ui.requestTableData2(tp);
					condition.getExpandInfo().remove("isForTaoda");
					ZDFReportSubsidiaryLedgerUI.this.tblMain
							.getDataRequestManager().setPageRowCount(oldCount);
					rs = ZDFReportSubsidiaryLedgerUI.this.result;
				}

			}

			ZDFReportSubsidiaryLedgerUI.this.mapThis.put("lblPeriod", this.ui
					.getTitlePeriodName());
			ZDFReportSubsidiaryLedgerUI.this.mapThis.put("lblCurrency", this.ui
					.getTitleCurrencyName());
			ZDFReportConditionSubsidiaryLedger fixCondition = (ZDFReportConditionSubsidiaryLedger) this.ui
					.getCondition().get("GLFixCondition");
			String dateStr = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (fixCondition.isByPeriod())
				try {
					PeriodInfo periodinfo = PeriodUtils.getPeriodInfo(null,
							fixCondition.getPeriodYearEnd(), fixCondition
									.getPeriodNumberEnd(), SysContext
									.getSysContext().getCurrentFIUnit());
					dateStr = sdf.format(periodinfo.getEndDate());
				} catch (Exception e) {
					e.printStackTrace();
				}
			else {
				dateStr = sdf.format(fixCondition.getDateEnd());
			}
			rs.getExtendData().put("endDate", dateStr);

			if ((this.bBF) && (this.printParamMap.size() > 0)) {
				ZDFReportSubsidiaryLedgerUI.this.mapThis.put("isSumPeriod",
						this.printParamMap.get("isSumPeriod"));
				ZDFReportSubsidiaryLedgerUI.this.mapThis.put("isSumYear",
						this.printParamMap.get("isSumYear"));
			}

			if (this.printParamMap.size() > 0) {
				ZDFReportSubsidiaryLedgerUI.this.mapThis.put("isSRPage",
						this.printParamMap.get("isSRPage"));
				ZDFReportSubsidiaryLedgerUI.this.mapThis.put("isSubPage",
						this.printParamMap.get("isSubPage"));
			}
			ReportSubsidiaryLedgerTaoda rsprv = new ReportSubsidiaryLedgerTaoda(
					rs, ZDFReportSubsidiaryLedgerUI.this.mapThis, tp, this.bBF,
					this.ischkOpDailyTotal, periodTime);

			return rsprv.execute(ds);
		}
	}
}