/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
package com.kingdee.eas.fi.gl.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.QueryAgent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTLayoutManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Pattern;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.OrgRange;
import com.kingdee.eas.base.permission.OrgRangeCollection;
import com.kingdee.eas.base.permission.OrgRangeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupInfo;
import com.kingdee.eas.basedata.master.auxacct.IAsstActType;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.gl.ReportConditionSubsidiaryLedger;
import com.kingdee.eas.fi.gl.common.AsstactTypeEntity;
import com.kingdee.eas.fi.gl.common.GLf7Utils;
import com.kingdee.eas.fi.gl.common.RptClientUtil;
import com.kingdee.eas.fi.rpt.TableToolkit;
import com.kingdee.eas.fi.rpt.client.AsstGroupChooserPrompt;
import com.kingdee.eas.fi.rpt.util.AcctountAsstItemParser;
import com.kingdee.eas.framework.report.RptFilterCollection;
import com.kingdee.eas.framework.report.RptFilterInfo;
import com.kingdee.eas.framework.report.RptFilterUtils;
import com.kingdee.eas.framework.report.util.RptParamsUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.util.StringUtils;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import javax.swing.JScrollBar;

// Referenced classes of package com.kingdee.eas.fi.gl.client:
//            AbstractReportSubsidiaryLedgerConditionAdvanceUI

public class ZDFReportSubsidiaryLedgerConditionAdvanceUI extends
		AbstractZDFReportSubsidiaryLedgerConditionAdvanceUI {
	private static class GroupSelectorListener implements SelectorListener {

		public void willShow(SelectorEvent e) {
			KDBizPromptBox box = (KDBizPromptBox) e.getSource();
			AsstGroupChooserPrompt prompt = (AsstGroupChooserPrompt) box
					.getSelector();
			prompt.setInitData(groupCell.getValue());
		}

		ICell groupCell;

		public GroupSelectorListener(ICell cell) {
			groupCell = cell;
		}
	}

	private final class AssItemSelectorListener implements SelectorListener {

		public void willShow(SelectorEvent e) {
			IRow row = tblAct.getRow(tblAct.getSelectManager()
					.getActiveRowIndex());
			Object startGroup = row.getCell("group").getValue();
			if (startGroup == null)
				return;
			String groups = startGroup.toString();
			KDBizPromptBox pb = (KDBizPromptBox) e.getSource();
			if (StringUtils.isEmpty(groups)) {
				pb.setEntityViewInfo(null);
				pb.getQueryAgent().resetRuntimeEntityView();
				return;
			}
			EntityViewInfo view = pb.getEntityViewInfo();
			if (view == null)
				view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection fic = filter.getFilterItems();
			view.setFilter(filter);
			pb.setEntityViewInfo(view);
			pb.getQueryAgent().resetRuntimeEntityView();
			com.kingdee.eas.fi.rpt.util.AcctountAsstItemParser.AccountAsstItem item;
			com.kingdee.eas.fi.rpt.util.AcctountAsstItemParser.Group group[];
			String mask;
			int i;
			String std;
			try {
				item = TableToolkit.parseAcctItem("1002|001|" + groups + "#");
				if (item == null)
					return;
			} catch (ParseException e1) {
				return;
			}
			group = item.asstItems[0].group;
			mask = null;
			for (i = 0; group != null && i < group.length; i++) {
				std = group[i].standardNumber;
				if (type == 1)
					fic
							.add(new FilterItemInfo(
									"customerGroupDetails.CustomerGroupStandard.number",
									std));
				else if (type == 2)
					fic
							.add(new FilterItemInfo(
									"supplierGroupDetails.SupplierGroupStandard.number",
									std));
				else if (type == 3)
					fic
							.add(new FilterItemInfo(
									"materialGroupDetails.MaterialGroupStandard.number",
									std));
				if (i == 0)
					mask = "#" + i;
				else
					mask = mask + " or #" + i;
			}

			if (i > 1)
				filter.setMaskString(mask);
		}

		int type;

		public AssItemSelectorListener(int type) {
			super();
			this.type = -1;
			this.type = type;
		}
	}
	private final class AssItemCostSelectorListener implements SelectorListener {

		public void willShow(SelectorEvent e) {
			KDBizPromptBox pb = (KDBizPromptBox) e.getSource();
			EntityViewInfo viewrange=new EntityViewInfo();
			String orgids="";
			viewrange.getSelector().add(new SelectorItemInfo("id"));
			viewrange.getSelector().add(new SelectorItemInfo("org.*"));
			FilterInfo filterrange = new FilterInfo();
			FilterItemCollection ficrange = filterrange.getFilterItems();
			ficrange.add(new FilterItemInfo("user",SysContext.getSysContext().getCurrentUserInfo().getId()));
			filterrange.setMaskString("#0");
			viewrange.setFilter(filterrange);
			try {
				OrgRangeCollection orgranges=OrgRangeFactory.getRemoteInstance().getOrgRangeCollection(viewrange);
				if(orgranges!=null&&orgranges.size()>0){

					for(int i=0;i<orgranges.size();i++){
						if(orgranges.get(i).getOrg().isIsCostOrgUnit()){
							orgids=orgids+",'"+orgranges.get(i).getId().toString()+"'";
						}
					}
					orgids=orgids.substring(3, orgids.length());
				}
			} catch (BOSException e1) {
				handUIException(e1);
			}
			EntityViewInfo view = pb.getEntityViewInfo();
			if (view == null)
				view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id",orgids,CompareType.INNER));
			view.setFilter(filter);
			pb.setEntityViewInfo(view);
			pb.getQueryAgent().resetRuntimeEntityView();

		}
	}
	public ZDFReportSubsidiaryLedgerConditionAdvanceUI() throws Exception {
		isJoinQuery = false;
		tblAct.checkParsed();
		tblAct.getLayoutManager().setVerticalScrollBar(new JScrollBar(1));
		tblAct.getLayoutManager().setHorizonScrollBar(new JScrollBar(0));
		tblAct.addKDTEditListener(new KDTEditAdapter() {

			public void editStopped(KDTEditEvent e) {
				tblEditStopped(e);
			}
		});
		chkOpAsstGroup.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					tblAct.getColumn("group").getStyleAttributes().setHided(
							false);
				else
					tblAct.getColumn("group").getStyleAttributes().setHided(
							true);
			}
		});
		tblAct.getColumn("group").getStyleAttributes().setHided(true);
		kdUp.setText(null);
		kdUp.setIcon(EASResource.getIcon("imgTbtn_movetop"));
		kdUp.setToolTipText(EASResource.getString(
				"com.kingdee.eas.base.commonquery.client.CommonSorterPanel",
				"moveUp"));
		kdDown.setText(null);
		kdDown.setIcon(EASResource.getIcon("imgTbtn_movedown"));
		kdDown.setToolTipText(EASResource.getString(
				"com.kingdee.eas.base.commonquery.client.CommonSorterPanel",
				"moveDown"));
	}

	protected void kdDown_actionPerformed(ActionEvent e) throws Exception {
		super.kdDown_actionPerformed(e);
		moveRow(1);
	}

	protected void kdUp_actionPerformed(ActionEvent e) throws Exception {
		super.kdUp_actionPerformed(e);
		moveRow(-1);
	}

	private void moveRow(int offset) {
		if (tblAct.getSelectManager().get() != null) {
			int r = tblAct.getSelectManager().get().getTop();
			int newr = r + offset;
			if (r < 0 || newr < 0 || newr >= tblAct.getRowCount())
				return;
			IRow row = tblAct.removeRow(r);
			tblAct.addRow(newr, row);
			tblAct.getSelectManager().select(newr, 0, newr,
					tblAct.getColumnCount());
			tblAct.getLayoutManager().scrollToShow(newr, 0);
		}
	}

	protected void tblEditStopped(KDTEditEvent e) {
		if (e.getColIndex() == 0) {
			tblAct.getCell(e.getRowIndex(), 0).setValue(e.getValue());
			boolean isSelect = ((Boolean) e.getValue()).booleanValue();
			IRow row = tblAct.getRow(e.getRowIndex());
			if (isSelect
					&& (row.getCell("from").getEditor() == null || row.getCell(
							"to").getEditor() == null)) {
				row.getCell("from").setValue(null);
				row.getCell("to").setValue(null);
				AsstactTypeEntity at = (AsstactTypeEntity) row.getUserObject();
				addF7(row, at);
			}
		}
	}

	public void setCustomerParams(CustomerParams cp) {
		try {
			Object param = RptParamsUtil.getFromCustomerParams(cp);
			if ((param instanceof RptFilterInfo)
					|| (param instanceof RptFilterCollection)
					|| (param instanceof RptFilterInfo[])
					|| (param instanceof RptFilterCollection[]))
				setParam(RptFilterUtils.toObjectFromRptFilterMap(RptParamsUtil
						.getFromCustomerParams(cp)));
			else
				setParam(RptParamsUtil.getFromCustomerParams(cp));
		} catch (EASBizException e) {
			ExceptionHandler.handle(this, e);
		}
	}

	protected void setParam(Object param) {
		if (param != null)
			try {
				setCustomCondition(new ReportConditionSubsidiaryLedger(
						(Map) param));
			} catch (CloneNotSupportedException ex) {
				handUIException(ex);
			}
	}

	public void setCustomCondition(ReportConditionSubsidiaryLedger condition) {
		if (condition == null) {
			return;
		} else {
			setTableData(condition.getTableData());
			chkOpAsstGroup.setSelected(condition.getOptionAsstGroup());
			return;
		}
	}

	public CustomerParams getCustomerParams() {
		CustomerParams cp = new CustomerParams();
		RptParamsUtil.setToCustomerParams(cp, RptFilterUtils
				.toRptFilterMapFromObject(getParam()));
		return cp;
	}

	public Object getParam() {
		return getCustomCondition().toMap();
	}

	public ReportConditionSubsidiaryLedger getCustomCondition() {
		ReportConditionSubsidiaryLedger condition = new ReportConditionSubsidiaryLedger();
		condition.setTableData(getTableData());
		condition.setOptionAsstGroup(chkOpAsstGroup.isSelected());
		CustomerParams cp = new CustomerParams();
		RptParamsUtil.setToCustomerParams(cp, condition.toMap());
		return condition;
	}

	public void onLoad() throws Exception {
		if (!isJoinQuery) {
			super.onLoad();
			chkOpAsstGroup.setVisible(false);
			chkOpAsstGroup.setSelected(true);
			tblActInit();
		}
	}

	private void tblActInit() throws Exception {
		java.util.List list = RptClientUtil.queryAsstAct(null, SysContext
				.getSysContext().getCurrentFIUnit().getCU().getId().toString());
		if (list != null) {
			int i = 0;
			for (int n = list.size(); i < n; i++) {
				AsstactTypeEntity at = (AsstactTypeEntity) list.get(i);
				at.setSelected(false);
			}

		}
		setTableData(list);
	}

	protected java.util.List getTableData() {
		java.util.List list = new ArrayList();
		AsstactTypeEntity at = null;
		IRow row = null;
		int i = 0;
		for (int n = tblAct.getRowCount(); i < n; i++) {
			row = tblAct.getRow(i);
			at = (AsstactTypeEntity) row.getUserObject();
			boolean select = ((Boolean) row.getCell("selected").getValue())
					.booleanValue();
			at.setSelected(select);
			at.setFrom(row.getCell("from").getValue());
			at.setTo(row.getCell("to").getValue());
			if (row.getCell("group").getValue() != null)
				at.setAsstGroup(row.getCell("group").getValue().toString());
			else
				at.setAsstGroup(null);
			list.add(at);
		}

		return list;
	}

	protected void setTableData(java.util.List list) {
		tblAct.setRefresh(false);
		tblAct.removeRows();
		if (list == null)
			return;
		HashSet idSet = new HashSet();
		int i = 0;
		for (int n = list.size(); i < n; i++) {
			AsstactTypeEntity at = (AsstactTypeEntity) list.get(i);
			if (at.getId() != null)
				idSet.add(at.getId());
		}

		if (idSet.size() == 0)
			return;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("name"));
		view.getSelector().add(new SelectorItemInfo("number"));
		FilterInfo fi = new FilterInfo();
		fi.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(fi);
		AsstActTypeCollection asstActColl = null;
		try {
			asstActColl = AsstActTypeFactory.getRemoteInstance()
					.getAsstActTypeCollection(view);
		} catch (BOSException e) {
			handUIException(e);
		}
		i = 0;
		for (int n = list.size(); i < n; i++) {

			AsstactTypeEntity at = (AsstactTypeEntity) list.get(i);
			String att = at.getHgAttribute();
//			if (att.equalsIgnoreCase("Customer")
//					|| att.equalsIgnoreCase("provider")) {
				
				IRow row = tblAct.addRow();
				row.setUserObject(at);
				AsstActTypeInfo asstActTypeInfo = findAsstActTypeInfo(
						asstActColl, at.getId());
				if (asstActTypeInfo != null)
					row.getCell("type").setValue(asstActTypeInfo.getName());
				boolean isSelected = at.isSelected();
				row.getCell("selected").setValue(Boolean.valueOf(isSelected));
				if (isSelected) {
					addF7(row, at);
					row.getCell("from").setValue(at.getFrom());
					row.getCell("to").setValue(at.getTo());
					row.getCell("group").setValue(at.getAsstGroup());
				}

				if (!att.equalsIgnoreCase("Customer")
						&& !att.equalsIgnoreCase("provider")
						&& !att.equalsIgnoreCase("Material"))
					lockGroup(row);
//			}
		}

		tblAct.setRefresh(true);
		tblAct.updateUI();
	}

	private AsstActTypeInfo findAsstActTypeInfo(
			AsstActTypeCollection asstActCol, String id) {
		if (asstActCol == null || id == null || id.trim().length() == 0)
			return null;
		for (int i = 0; i < asstActCol.size(); i++)
			if (BOSUuid.read(id).equals(asstActCol.get(i).getId()))
				return asstActCol.get(i);

		return null;
	}

	private void addF7(IRow row, AsstactTypeEntity at) {
		KDBizPromptBox bizPromptBox = new KDBizPromptBox();
		bizPromptBox.setEditable(true);
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.setDisplayFormat("$number$ - $name$");
		bizPromptBox.setQueryInfo(at.getQueryName());
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		if (at.isUseLevel())
			sic.add(new SelectorItemInfo("longnumber"));
		bizPromptBox.setSelectorCollection(sic);
		GeneralAsstActTypeGroupInfo grp = null;
		if (at.getGeneralAsstactGroupId() != null) {
			grp = new GeneralAsstActTypeGroupInfo();
			grp.setId(BOSUuid.read(at.getGeneralAsstactGroupId()));
		}
		GLf7Utils.assignSelector(bizPromptBox, grp, at.getHgAttribute(), at
				.isForCompany(), false, true, null, this);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(bizPromptBox);
		row.getCell("to").setEditor(editor);
		bizPromptBox = new KDBizPromptBox();
		bizPromptBox.setEditable(true);
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.setDisplayFormat("$number$ - $name$");
		bizPromptBox.setQueryInfo(at.getQueryName());
		sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		if (at.isUseLevel())
			sic.add(new SelectorItemInfo("longnumber"));
		bizPromptBox.setSelectorCollection(sic);
//		String att1 = at.getHgAttribute();
//		if(att1.equalsIgnoreCase("costOrg")){
//			bizPromptBox.addSelectorListener(new AssItemCostSelectorListener(
//					));
//		}
		grp = null;
		if (at.getGeneralAsstactGroupId() != null) {
			grp = new GeneralAsstActTypeGroupInfo();
			grp.setId(BOSUuid.read(at.getGeneralAsstactGroupId()));
		}
		GLf7Utils.assignSelector(bizPromptBox, grp, at.getHgAttribute(), at
				.isForCompany(), false, true, null, this);
		bizPromptBox.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent e) {
				try {
					int beginRow = tblAct.getSelectManager().get()
							.getBeginRow();
					IRow row = tblAct.getRow(beginRow);
					if (e.getNewValue() != null
							&& row.getCell("to").getValue() == null)
						row.getCell("to").setValue(e.getNewValue());
				} catch (Exception exc) {
					handUIException(exc);
				}
			}
		});
		editor = new KDTDefaultCellEditor(bizPromptBox);
		row.getCell("from").setEditor(editor);
		String att = at.getHgAttribute();
		if (att == null || att.toString().trim().length() < 1) {
			lockGroup(row);
		} else {
			KDBizPromptBox groupPromptBox = new KDBizPromptBox();
			groupPromptBox.setEditable(true);
			groupPromptBox.setEditFormat("$number$");
			groupPromptBox.setDisplayFormat("$number$ - $name$");
			groupPromptBox.setCommitFormat("$number$");
			if (att.equalsIgnoreCase("Customer")
					|| att.equalsIgnoreCase("provider")
					|| att.equalsIgnoreCase("Material")
					) {
				AsstGroupChooserPrompt prompt = new AsstGroupChooserPrompt(this);
				int asstGroupChooser = 0;
				if (att.equalsIgnoreCase("Customer"))
					asstGroupChooser = 1;
				else if (att.equalsIgnoreCase("Material"))
					asstGroupChooser = 3;
				else
					asstGroupChooser = 2;
				groupPromptBox.setSelector(prompt);
				groupPromptBox.addSelectorListener(new GroupSelectorListener(
						row.getCell("group")));
				prompt.setGroupType(asstGroupChooser);
				bizPromptBox.addSelectorListener(new AssItemSelectorListener(
						asstGroupChooser));
			}
			if(att.equalsIgnoreCase("costOrg")){
				bizPromptBox.addSelectorListener(new AssItemCostSelectorListener(
						));
			}
			com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor groupEditor = new KDTDefaultCellEditor(
					groupPromptBox);
			ICell cell = row.getCell("group");
			if (cell != null)
				cell.setEditor(groupEditor);
		}
	}

	public boolean isJoinQuery() {
		return isJoinQuery;
	}

	public void setJoinQuery(boolean isJoinQuery) {
		this.isJoinQuery = isJoinQuery;
	}

	private void lockGroup(IRow row) {
		StyleAttributes sa = row.getCell("group").getStyleAttributes();
		sa.setLocked(true);
		sa.setBackground(LOCKCOLOR);
		sa.setPattern(Pattern.Solid);
	}

	public boolean isPrepareInit() {
		return true;
	}

	private static final long serialVersionUID = 5950222891496423491L;
	private static final String commonSorterRes = "com.kingdee.eas.base.commonquery.client.CommonSorterPanel";
	private boolean isJoinQuery;
	public static final Color LOCKCOLOR = new Color(228, 228, 228);

}