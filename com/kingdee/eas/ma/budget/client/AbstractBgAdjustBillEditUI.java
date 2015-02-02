/**
 * output package name
 */
package com.kingdee.eas.ma.budget.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractBgAdjustBillEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgAdjustBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lbnForm;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBgScheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton workBtnAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton workbtnRemoveLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdataX;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtForm_global;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReport;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCtrlSet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCtrlView;
    protected javax.swing.JToolBar.Separator separator4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReportCheck;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBgExamineCheck;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOpenExaminReport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjustCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReportCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBgExamineCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem miExamineView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdjustCheck;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem miCtrlSet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItem3;
    protected com.kingdee.eas.ma.budget.BgAdjustBillInfo editData = null;
    protected ActionReferWorking actionReferWorking = null;
    protected ActionOpenReport actionOpenReport = null;
    protected ActionAddNewLine actionAddNewLine = null;
    protected ActionDeleteLine actionDeleteLine = null;
    protected ActionAudit actionAudit = null;
    protected ActionCtrlSet actionCtrlSet = null;
    protected ActionCtrlView actionCtrlView = null;
    protected ActionReportCheck actionReportCheck = null;
    protected ActionBgExamineCheck actionBgExamineCheck = null;
    protected ActionExamineInputX actionExamineInputX = null;
    protected ActionOpenExamineReport actionOpenExamineReport = null;
    protected ActionBgAdjustCheck actionBgAdjustCheck = null;
    public final static String STATUS_AUDIT = "AUDIT";
    /**
     * output class constructor
     */
    public AbstractBgAdjustBillEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractBgAdjustBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCalculator
        String _tempStr = null;
        actionCalculator.setEnabled(true);
        actionCalculator.setDaemonRun(false);

        actionCalculator.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift F12"));
        _tempStr = resHelper.getString("ActionCalculator.SHORT_DESCRIPTION");
        actionCalculator.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.LONG_DESCRIPTION");
        actionCalculator.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.NAME");
        actionCalculator.putValue(ItemAction.NAME, _tempStr);
         this.actionCalculator.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReferWorking
        this.actionReferWorking = new ActionReferWorking(this);
        getActionManager().registerAction("actionReferWorking", actionReferWorking);
         this.actionReferWorking.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOpenReport
        this.actionOpenReport = new ActionOpenReport(this);
        getActionManager().registerAction("actionOpenReport", actionOpenReport);
         this.actionOpenReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNewLine
        this.actionAddNewLine = new ActionAddNewLine(this);
        getActionManager().registerAction("actionAddNewLine", actionAddNewLine);
         this.actionAddNewLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteLine
        this.actionDeleteLine = new ActionDeleteLine(this);
        getActionManager().registerAction("actionDeleteLine", actionDeleteLine);
         this.actionDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCtrlSet
        this.actionCtrlSet = new ActionCtrlSet(this);
        getActionManager().registerAction("actionCtrlSet", actionCtrlSet);
         this.actionCtrlSet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCtrlView
        this.actionCtrlView = new ActionCtrlView(this);
        getActionManager().registerAction("actionCtrlView", actionCtrlView);
         this.actionCtrlView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReportCheck
        this.actionReportCheck = new ActionReportCheck(this);
        getActionManager().registerAction("actionReportCheck", actionReportCheck);
         this.actionReportCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBgExamineCheck
        this.actionBgExamineCheck = new ActionBgExamineCheck(this);
        getActionManager().registerAction("actionBgExamineCheck", actionBgExamineCheck);
         this.actionBgExamineCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExamineInputX
        this.actionExamineInputX = new ActionExamineInputX(this);
        getActionManager().registerAction("actionExamineInputX", actionExamineInputX);
         this.actionExamineInputX.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOpenExamineReport
        this.actionOpenExamineReport = new ActionOpenExamineReport(this);
        getActionManager().registerAction("actionOpenExamineReport", actionOpenExamineReport);
         this.actionOpenExamineReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBgAdjustCheck
        this.actionBgAdjustCheck = new ActionBgAdjustCheck(this);
        getActionManager().registerAction("actionBgAdjustCheck", actionBgAdjustCheck);
         this.actionBgAdjustCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.lbnForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBgScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.workBtnAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.workbtnRemoveLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpdataX = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtForm_global = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnRefer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnCtrlSet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCtrlView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator4 = new javax.swing.JToolBar.Separator();
        this.btnReportCheck = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBgExamineCheck = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnOpenExaminReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAdjustCheck = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemReportCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBgExamineCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.miExamineView = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdjustCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.miCtrlSet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuItem3 = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contAuditor.setName("contAuditor");
        this.contBgScheme.setName("contBgScheme");
        this.contOrgUnit.setName("contOrgUnit");
        this.kDPanel.setName("kDPanel");
        this.lbnForm.setName("lbnForm");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtBgScheme.setName("prmtBgScheme");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.kdtEntry.setName("kdtEntry");
        this.workBtnAddLine.setName("workBtnAddLine");
        this.workbtnRemoveLine.setName("workbtnRemoveLine");
        this.btnUpdataX.setName("btnUpdataX");
        this.prmtForm_global.setName("prmtForm_global");
        this.btnRefer.setName("btnRefer");
        this.btnAudit.setName("btnAudit");
        this.btnReport.setName("btnReport");
        this.kDSeparator8.setName("kDSeparator8");
        this.btnCtrlSet.setName("btnCtrlSet");
        this.btnCtrlView.setName("btnCtrlView");
        this.separator4.setName("separator4");
        this.btnReportCheck.setName("btnReportCheck");
        this.btnBgExamineCheck.setName("btnBgExamineCheck");
        this.btnOpenExaminReport.setName("btnOpenExaminReport");
        this.btnAdjustCheck.setName("btnAdjustCheck");
        this.menuItemReportCheck.setName("menuItemReportCheck");
        this.menuItemBgExamineCheck.setName("menuItemBgExamineCheck");
        this.miExamineView.setName("miExamineView");
        this.menuItemAdjustCheck.setName("menuItemAdjustCheck");
        this.kDSeparator9.setName("kDSeparator9");
        this.miCtrlSet.setName("miCtrlSet");
        this.kDMenuItem3.setName("kDMenuItem3");
        // CoreUI		
        this.setPreferredSize(new Dimension(600,1016));		
        this.menuBar.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemVoucher.setEnabled(false);		
        this.menuItemDelVoucher.setEnabled(false);		
        this.menuItemMultiapprove.setText(resHelper.getString("menuItemMultiapprove.text"));		
        this.menuItemMultiapprove.setMnemonic(77);		
        this.kDSeparator5.setOrientation(1);		
        this.kDMenuItemSendMessage.setText(resHelper.getString("kDMenuItemSendMessage.text"));		
        this.kDMenuItemSendMessage.setMnemonic(69);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(65);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(65);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(80);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(65);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(65);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contBgScheme		
        this.contBgScheme.setBoundLabelText(resHelper.getString("contBgScheme.boundLabelText"));		
        this.contBgScheme.setBoundLabelLength(80);		
        this.contBgScheme.setBoundLabelUnderline(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(80);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // kDPanel		
        this.kDPanel.setBorder(null);
        // lbnForm		
        this.lbnForm.setBoundLabelText(resHelper.getString("lbnForm.boundLabelText"));		
        this.lbnForm.setBoundLabelLength(80);		
        this.lbnForm.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");
        // prmtBgScheme		
        this.prmtBgScheme.setRequired(true);		
        this.prmtBgScheme.setQueryInfo("com.kingdee.eas.ma.budget.BgSchemeF7Query");		
        this.prmtBgScheme.setCommitFormat("$number$");		
        this.prmtBgScheme.setDisplayFormat("$name$");		
        this.prmtBgScheme.setEditFormat("$number$");
        this.prmtBgScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBgScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtOrgUnit		
        this.prmtOrgUnit.setRequired(true);
        this.prmtOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtOrgUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"orgUnit\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bgScheme\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bgform\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bgPeriod\" t:width=\"125\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"specialNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"formula\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"bgItemId\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"bgItem.name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bgElement\" t:width=\"125\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adjustType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bgActualAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"bgUsableAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"adjustBefore\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"adjustApply\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"adjustPass\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"adjustAfter\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"adjustGroup\" t:width=\"75\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"adjustCause\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"isControl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"isFlexible\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"flexParam\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"ctrlType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"isGroupCtrl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"ctrlGroupNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"isAllowAccess\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{orgUnit}</t:Cell><t:Cell>$Resource{bgScheme}</t:Cell><t:Cell>$Resource{bgform}</t:Cell><t:Cell>$Resource{bgPeriod}</t:Cell><t:Cell>$Resource{specialNumber}</t:Cell><t:Cell>$Resource{formula}</t:Cell><t:Cell>$Resource{bgItemId}</t:Cell><t:Cell>$Resource{bgItem.name}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{bgElement}</t:Cell><t:Cell>$Resource{adjustType}</t:Cell><t:Cell>$Resource{bgActualAmt}</t:Cell><t:Cell>$Resource{bgUsableAmt}</t:Cell><t:Cell>$Resource{adjustBefore}</t:Cell><t:Cell>$Resource{adjustApply}</t:Cell><t:Cell>$Resource{adjustPass}</t:Cell><t:Cell>$Resource{adjustAfter}</t:Cell><t:Cell>$Resource{adjustGroup}</t:Cell><t:Cell>$Resource{adjustCause}</t:Cell><t:Cell>$Resource{isControl}</t:Cell><t:Cell>$Resource{isFlexible}</t:Cell><t:Cell>$Resource{flexParam}</t:Cell><t:Cell>$Resource{ctrlType}</t:Cell><t:Cell>$Resource{isGroupCtrl}</t:Cell><t:Cell>$Resource{ctrlGroupNo}</t:Cell><t:Cell>$Resource{isAllowAccess}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtEntry_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"orgUnit","bgScheme","bgForm","bgPeriod","","formula","bgItemId","bgItemCmbName","currency","bgElement","adjustType","bgActualAmt","bgUsableAmt","adjustBefore","adjustApply","adjustPass","adjustAfter","adjustGroup","adjustCause","","","","","","","isAllowAccess"});


        // workBtnAddLine
        this.workBtnAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNewLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.workBtnAddLine.setToolTipText(resHelper.getString("workBtnAddLine.toolTipText"));
        // workbtnRemoveLine
        this.workbtnRemoveLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.workbtnRemoveLine.setToolTipText(resHelper.getString("workbtnRemoveLine.toolTipText"));
        // btnUpdataX
        this.btnUpdataX.setAction((IItemAction)ActionProxyFactory.getProxy(actionExamineInputX, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdataX.setText(resHelper.getString("btnUpdataX.text"));
        // prmtForm_global		
        this.prmtForm_global.setRequired(true);		
        this.prmtForm_global.setQueryInfo("com.kingdee.eas.ma.budget.BgFormF7Query");		
        this.prmtForm_global.setDisplayFormat("$name$");		
        this.prmtForm_global.setCommitFormat("$number$");		
        this.prmtForm_global.setEditFormat("$number$");
        // btnRefer
        this.btnRefer.setAction((IItemAction)ActionProxyFactory.getProxy(actionReferWorking, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefer.setText(resHelper.getString("btnRefer.text"));		
        this.btnRefer.setToolTipText(resHelper.getString("btnRefer.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // btnReport
        this.btnReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpenReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReport.setText(resHelper.getString("btnReport.text"));		
        this.btnReport.setToolTipText(resHelper.getString("btnReport.toolTipText"));
        // kDSeparator8		
        this.kDSeparator8.setOrientation(1);
        // btnCtrlSet
        this.btnCtrlSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionCtrlSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCtrlSet.setText(resHelper.getString("btnCtrlSet.text"));
        // btnCtrlView
        this.btnCtrlView.setAction((IItemAction)ActionProxyFactory.getProxy(actionCtrlView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCtrlView.setText(resHelper.getString("btnCtrlView.text"));
        // separator4		
        this.separator4.setOrientation(1);
        // btnReportCheck
        this.btnReportCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionReportCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReportCheck.setText(resHelper.getString("btnReportCheck.text"));		
        this.btnReportCheck.setToolTipText(resHelper.getString("btnReportCheck.toolTipText"));
        // btnBgExamineCheck
        this.btnBgExamineCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgExamineCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBgExamineCheck.setText(resHelper.getString("btnBgExamineCheck.text"));		
        this.btnBgExamineCheck.setToolTipText(resHelper.getString("btnBgExamineCheck.toolTipText"));
        // btnOpenExaminReport
        this.btnOpenExaminReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpenExamineReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOpenExaminReport.setText(resHelper.getString("btnOpenExaminReport.text"));
        // btnAdjustCheck
        this.btnAdjustCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgAdjustCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjustCheck.setText(resHelper.getString("btnAdjustCheck.text"));
        // menuItemReportCheck
        this.menuItemReportCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionReportCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReportCheck.setText(resHelper.getString("menuItemReportCheck.text"));		
        this.menuItemReportCheck.setToolTipText(resHelper.getString("menuItemReportCheck.toolTipText"));		
        this.menuItemReportCheck.setMnemonic(67);
        // menuItemBgExamineCheck
        this.menuItemBgExamineCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgExamineCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBgExamineCheck.setText(resHelper.getString("menuItemBgExamineCheck.text"));		
        this.menuItemBgExamineCheck.setToolTipText(resHelper.getString("menuItemBgExamineCheck.toolTipText"));		
        this.menuItemBgExamineCheck.setMnemonic(69);
        // miExamineView
        this.miExamineView.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpenExamineReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.miExamineView.setText(resHelper.getString("miExamineView.text"));		
        this.miExamineView.setToolTipText(resHelper.getString("miExamineView.toolTipText"));
        // menuItemAdjustCheck
        this.menuItemAdjustCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgAdjustCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdjustCheck.setText(resHelper.getString("menuItemAdjustCheck.text"));
        // kDSeparator9
        // miCtrlSet
        this.miCtrlSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionCtrlSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.miCtrlSet.setText(resHelper.getString("miCtrlSet.text"));		
        this.miCtrlSet.setToolTipText(resHelper.getString("miCtrlSet.toolTipText"));		
        this.miCtrlSet.setMnemonic(84);
        // kDMenuItem3
        this.kDMenuItem3.setAction((IItemAction)ActionProxyFactory.getProxy(actionCtrlView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItem3.setText(resHelper.getString("kDMenuItem3.text"));		
        this.kDMenuItem3.setToolTipText(resHelper.getString("kDMenuItem3.toolTipText"));		
        this.kDMenuItem3.setMnemonic(86);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(10, 597, 220, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 597, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(295, 597, 220, 19));
        this.add(contCreateTime, new KDLayout.Constraints(295, 597, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(10, 10, 300, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(785, 597, 220, 19));
        this.add(contBizDate, new KDLayout.Constraints(785, 597, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(540, 597, 220, 19));
        this.add(contAuditor, new KDLayout.Constraints(540, 597, 220, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBgScheme.setBounds(new Rectangle(695, 10, 300, 19));
        this.add(contBgScheme, new KDLayout.Constraints(695, 10, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(355, 10, 300, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(355, 10, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel.setBounds(new Rectangle(5, 70, 1000, 510));
        this.add(kDPanel, new KDLayout.Constraints(5, 70, 1000, 510, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lbnForm.setBounds(new Rectangle(10, 35, 300, 19));
        this.add(lbnForm, new KDLayout.Constraints(10, 35, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contBgScheme
        contBgScheme.setBoundEditor(prmtBgScheme);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //kDPanel
        kDPanel.setLayout(new KDLayout());
        kDPanel.putClientProperty("OriginalBounds", new Rectangle(5, 70, 1000, 510));        kdtEntry.setBounds(new Rectangle(10, 35, 980, 460));
        kDPanel.add(kdtEntry, new KDLayout.Constraints(10, 35, 980, 460, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        workBtnAddLine.setBounds(new Rectangle(926, 15, 30, 19));
        kDPanel.add(workBtnAddLine, new KDLayout.Constraints(926, 15, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        workbtnRemoveLine.setBounds(new Rectangle(959, 15, 30, 19));
        kDPanel.add(workbtnRemoveLine, new KDLayout.Constraints(959, 15, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnUpdataX.setBounds(new Rectangle(10, 15, 125, 19));
        kDPanel.add(btnUpdataX, new KDLayout.Constraints(10, 15, 125, 19, 0));
        //lbnForm
        lbnForm.setBoundEditor(prmtForm_global);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemReportCheck);
        menuBiz.add(menuItemBgExamineCheck);
        menuBiz.add(miExamineView);
        menuBiz.add(menuItemAdjustCheck);
        menuBiz.add(kDSeparator9);
        menuBiz.add(miCtrlSet);
        menuBiz.add(kDMenuItem3);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(kDSeparator5);
        this.toolBar.add(btnRefer);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnAudit);
        this.toolBar.add(kDSeparator6);
        this.toolBar.add(btnReport);
        this.toolBar.add(kDSeparator7);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(kDSeparator8);
        this.toolBar.add(btnCtrlSet);
        this.toolBar.add(btnCtrlView);
        this.toolBar.add(separator4);
        this.toolBar.add(btnReportCheck);
        this.toolBar.add(btnBgExamineCheck);
        this.toolBar.add(btnOpenExaminReport);
        this.toolBar.add(btnAdjustCheck);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("bgScheme", com.kingdee.eas.ma.budget.BgSchemeInfo.class, this.prmtBgScheme, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("entry.formula", String.class, this.kdtEntry, "formula.text");
		dataBinder.registerBinding("entry.bgItemId", String.class, this.kdtEntry, "bgItemId.text");
		dataBinder.registerBinding("entry.adjustBefore", java.math.BigDecimal.class, this.kdtEntry, "adjustBefore.text");
		dataBinder.registerBinding("entry.adjustApply", java.math.BigDecimal.class, this.kdtEntry, "adjustApply.text");
		dataBinder.registerBinding("entry.adjustPass", java.math.BigDecimal.class, this.kdtEntry, "adjustPass.text");
		dataBinder.registerBinding("entry.adjustAfter", java.math.BigDecimal.class, this.kdtEntry, "adjustAfter.text");
		dataBinder.registerBinding("entry.adjustGroup", int.class, this.kdtEntry, "adjustGroup.text");
		dataBinder.registerBinding("entry.adjustCause", String.class, this.kdtEntry, "adjustCause.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.ma.budget.BgAdjustBillEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.adjustType", com.kingdee.eas.ma.budget.BgAdjustTypeEnum.class, this.kdtEntry, "adjustType.text");
		dataBinder.registerBinding("entry.bgForm", com.kingdee.eas.ma.budget.BgFormInfo.class, this.kdtEntry, "bgform.text");
		dataBinder.registerBinding("entry.bgScheme", com.kingdee.eas.ma.budget.BgSchemeInfo.class, this.kdtEntry, "bgScheme.text");
		dataBinder.registerBinding("entry.orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.kdtEntry, "orgUnit.text");
		dataBinder.registerBinding("entry.bgPeriod", com.kingdee.eas.ma.budget.BgPeriodInfo.class, this.kdtEntry, "bgPeriod.text");
		dataBinder.registerBinding("entry.currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.kdtEntry, "currency.text");
		dataBinder.registerBinding("entry.bgElement", com.kingdee.eas.ma.budget.BgElementInfo.class, this.kdtEntry, "bgElement.text");
		dataBinder.registerBinding("entry.bgItemCmbName", String.class, this.kdtEntry, "bgItem.name.text");
		dataBinder.registerBinding("entry.isAllowAccess", boolean.class, this.kdtEntry, "isAllowAccess.text");
		dataBinder.registerBinding("entry.bgUsableAmt", java.math.BigDecimal.class, this.kdtEntry, "bgUsableAmt.text");
		dataBinder.registerBinding("entry.bgActualAmt", java.math.BigDecimal.class, this.kdtEntry, "bgActualAmt.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.budget.app.BgAdjustBillEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.ma.budget.BgAdjustBillInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bgScheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.formula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgItemId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustBefore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustApply", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustAfter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustGroup", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustCause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.adjustType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgForm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgScheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgElement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgItemCmbName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.isAllowAccess", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgUsableAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.bgActualAmt", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output prmtBgScheme_dataChanged method
     */
    protected void prmtBgScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtOrgUnit_dataChanged method
     */
    protected void prmtOrgUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_tableSelectChanged method
     */
    protected void kdtEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editValueChanged method
     */
    protected void kdtEntry_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_tableClicked method
     */
    protected void kdtEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bgScheme.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("bgScheme.id"));
        	sic.add(new SelectorItemInfo("bgScheme.number"));
        	sic.add(new SelectorItemInfo("bgScheme.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
    	sic.add(new SelectorItemInfo("entry.formula"));
    	sic.add(new SelectorItemInfo("entry.bgItemId"));
    	sic.add(new SelectorItemInfo("entry.adjustBefore"));
    	sic.add(new SelectorItemInfo("entry.adjustApply"));
    	sic.add(new SelectorItemInfo("entry.adjustPass"));
    	sic.add(new SelectorItemInfo("entry.adjustAfter"));
    	sic.add(new SelectorItemInfo("entry.adjustGroup"));
    	sic.add(new SelectorItemInfo("entry.adjustCause"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entry.adjustType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.bgForm.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.bgForm.id"));
			sic.add(new SelectorItemInfo("entry.bgForm.name"));
        	sic.add(new SelectorItemInfo("entry.bgForm.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.bgScheme.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.bgScheme.id"));
			sic.add(new SelectorItemInfo("entry.bgScheme.name"));
        	sic.add(new SelectorItemInfo("entry.bgScheme.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.orgUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.orgUnit.id"));
			sic.add(new SelectorItemInfo("entry.orgUnit.name"));
        	sic.add(new SelectorItemInfo("entry.orgUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.bgPeriod.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.bgPeriod.id"));
			sic.add(new SelectorItemInfo("entry.bgPeriod.name"));
        	sic.add(new SelectorItemInfo("entry.bgPeriod.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.currency.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.currency.id"));
			sic.add(new SelectorItemInfo("entry.currency.name"));
        	sic.add(new SelectorItemInfo("entry.currency.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.bgElement.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.bgElement.id"));
			sic.add(new SelectorItemInfo("entry.bgElement.name"));
        	sic.add(new SelectorItemInfo("entry.bgElement.number"));
		}
    	sic.add(new SelectorItemInfo("entry.bgItemCmbName"));
    	sic.add(new SelectorItemInfo("entry.isAllowAccess"));
    	sic.add(new SelectorItemInfo("entry.bgUsableAmt"));
    	sic.add(new SelectorItemInfo("entry.bgActualAmt"));
        return sic;
    }        
    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
    	

    /**
     * output actionReferWorking_actionPerformed method
     */
    public void actionReferWorking_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOpenReport_actionPerformed method
     */
    public void actionOpenReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddNewLine_actionPerformed method
     */
    public void actionAddNewLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteLine_actionPerformed method
     */
    public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCtrlSet_actionPerformed method
     */
    public void actionCtrlSet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCtrlView_actionPerformed method
     */
    public void actionCtrlView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReportCheck_actionPerformed method
     */
    public void actionReportCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBgExamineCheck_actionPerformed method
     */
    public void actionBgExamineCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExamineInputX_actionPerformed method
     */
    public void actionExamineInputX_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOpenExamineReport_actionPerformed method
     */
    public void actionOpenExamineReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBgAdjustCheck_actionPerformed method
     */
    public void actionBgAdjustCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCalculator(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCalculator(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalculator() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }
	public RequestContext prepareActionReferWorking(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReferWorking() {
    	return false;
    }
	public RequestContext prepareActionOpenReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOpenReport() {
    	return false;
    }
	public RequestContext prepareActionAddNewLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNewLine() {
    	return false;
    }
	public RequestContext prepareActionDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteLine() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionCtrlSet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCtrlSet() {
    	return false;
    }
	public RequestContext prepareActionCtrlView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCtrlView() {
    	return false;
    }
	public RequestContext prepareActionReportCheck(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReportCheck() {
    	return false;
    }
	public RequestContext prepareActionBgExamineCheck(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBgExamineCheck() {
    	return false;
    }
	public RequestContext prepareActionExamineInputX(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExamineInputX() {
    	return false;
    }
	public RequestContext prepareActionOpenExamineReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOpenExamineReport() {
    	return false;
    }
	public RequestContext prepareActionBgAdjustCheck(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBgAdjustCheck() {
    	return false;
    }

    /**
     * output ActionReferWorking class
     */     
    protected class ActionReferWorking extends ItemAction {     
    
        public ActionReferWorking()
        {
            this(null);
        }

        public ActionReferWorking(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReferWorking.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReferWorking.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReferWorking.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionReferWorking", "actionReferWorking_actionPerformed", e);
        }
    }

    /**
     * output ActionOpenReport class
     */     
    protected class ActionOpenReport extends ItemAction {     
    
        public ActionOpenReport()
        {
            this(null);
        }

        public ActionOpenReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOpenReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOpenReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOpenReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionOpenReport", "actionOpenReport_actionPerformed", e);
        }
    }

    /**
     * output ActionAddNewLine class
     */     
    protected class ActionAddNewLine extends ItemAction {     
    
        public ActionAddNewLine()
        {
            this(null);
        }

        public ActionAddNewLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddNewLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionAddNewLine", "actionAddNewLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteLine class
     */     
    protected class ActionDeleteLine extends ItemAction {     
    
        public ActionDeleteLine()
        {
            this(null);
        }

        public ActionDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionDeleteLine", "actionDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionCtrlSet class
     */     
    protected class ActionCtrlSet extends ItemAction {     
    
        public ActionCtrlSet()
        {
            this(null);
        }

        public ActionCtrlSet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCtrlSet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCtrlSet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCtrlSet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionCtrlSet", "actionCtrlSet_actionPerformed", e);
        }
    }

    /**
     * output ActionCtrlView class
     */     
    protected class ActionCtrlView extends ItemAction {     
    
        public ActionCtrlView()
        {
            this(null);
        }

        public ActionCtrlView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCtrlView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCtrlView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCtrlView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionCtrlView", "actionCtrlView_actionPerformed", e);
        }
    }

    /**
     * output ActionReportCheck class
     */     
    protected class ActionReportCheck extends ItemAction {     
    
        public ActionReportCheck()
        {
            this(null);
        }

        public ActionReportCheck(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReportCheck.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReportCheck.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReportCheck.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionReportCheck", "actionReportCheck_actionPerformed", e);
        }
    }

    /**
     * output ActionBgExamineCheck class
     */     
    protected class ActionBgExamineCheck extends ItemAction {     
    
        public ActionBgExamineCheck()
        {
            this(null);
        }

        public ActionBgExamineCheck(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBgExamineCheck.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBgExamineCheck.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBgExamineCheck.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionBgExamineCheck", "actionBgExamineCheck_actionPerformed", e);
        }
    }

    /**
     * output ActionExamineInputX class
     */     
    protected class ActionExamineInputX extends ItemAction {     
    
        public ActionExamineInputX()
        {
            this(null);
        }

        public ActionExamineInputX(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExamineInputX.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExamineInputX.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExamineInputX.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionExamineInputX", "actionExamineInputX_actionPerformed", e);
        }
    }

    /**
     * output ActionOpenExamineReport class
     */     
    protected class ActionOpenExamineReport extends ItemAction {     
    
        public ActionOpenExamineReport()
        {
            this(null);
        }

        public ActionOpenExamineReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOpenExamineReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOpenExamineReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOpenExamineReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionOpenExamineReport", "actionOpenExamineReport_actionPerformed", e);
        }
    }

    /**
     * output ActionBgAdjustCheck class
     */     
    protected class ActionBgAdjustCheck extends ItemAction {     
    
        public ActionBgAdjustCheck()
        {
            this(null);
        }

        public ActionBgAdjustCheck(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBgAdjustCheck.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBgAdjustCheck.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBgAdjustCheck.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgAdjustBillEditUI.this, "ActionBgAdjustCheck", "actionBgAdjustCheck_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.budget.client", "BgAdjustBillEditUI");
    }




}