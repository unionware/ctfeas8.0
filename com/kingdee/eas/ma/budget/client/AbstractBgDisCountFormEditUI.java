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
public abstract class AbstractBgDisCountFormEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgDisCountFormEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlMain;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReportCheck;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBgExamineCheck;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgForm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewComment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjustView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataExamine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHideCol;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelHide;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdjustCheck;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefCalculate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOpenExaminReport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowAllOrg;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCollectCoefficient;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModifyBgRate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgForm;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewComment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReportCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBgExamineCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem miOpenExamineReport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAdjustCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.eas.ma.budget.BgDisCountFormInfo editData = null;
    protected ActionViewBgForm actionViewBgForm = null;
    protected ActionViewComment actionViewComment = null;
    protected ActionAudit actionAudit = null;
    protected ActionAdjustView actionAdjustView = null;
    protected ActionBgExamineCheck actionBgExamineCheck = null;
    protected ActionReportCheck actionReportCheck = null;
    protected ActionDataExamine actionDataExamine = null;
    protected ActionHideCol actionHideCol = null;
    protected ActionCancelHide actionCancelHide = null;
    protected ActionRefCalculate actionRefCalculate = null;
    protected ActionOpenExamineReport actionOpenExamineReport = null;
    protected ActionShowAllOrg actionShowAllOrg = null;
    protected ActionCollectCoefficient actionCollectCoefficient = null;
    protected ActionBgAdjustCheck actionBgAdjustCheck = null;
    protected ActionModifyBgRate actionModifyBgRate = null;
    /**
     * output class constructor
     */
    public AbstractBgDisCountFormEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBgDisCountFormEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPrint
        String _tempStr = null;
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionViewBgForm
        this.actionViewBgForm = new ActionViewBgForm(this);
        getActionManager().registerAction("actionViewBgForm", actionViewBgForm);
         this.actionViewBgForm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewComment
        this.actionViewComment = new ActionViewComment(this);
        getActionManager().registerAction("actionViewComment", actionViewComment);
         this.actionViewComment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionAdjustView
        this.actionAdjustView = new ActionAdjustView(this);
        getActionManager().registerAction("actionAdjustView", actionAdjustView);
         this.actionAdjustView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBgExamineCheck
        this.actionBgExamineCheck = new ActionBgExamineCheck(this);
        getActionManager().registerAction("actionBgExamineCheck", actionBgExamineCheck);
         this.actionBgExamineCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReportCheck
        this.actionReportCheck = new ActionReportCheck(this);
        getActionManager().registerAction("actionReportCheck", actionReportCheck);
         this.actionReportCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDataExamine
        this.actionDataExamine = new ActionDataExamine(this);
        getActionManager().registerAction("actionDataExamine", actionDataExamine);
         this.actionDataExamine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHideCol
        this.actionHideCol = new ActionHideCol(this);
        getActionManager().registerAction("actionHideCol", actionHideCol);
         this.actionHideCol.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelHide
        this.actionCancelHide = new ActionCancelHide(this);
        getActionManager().registerAction("actionCancelHide", actionCancelHide);
         this.actionCancelHide.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefCalculate
        this.actionRefCalculate = new ActionRefCalculate(this);
        getActionManager().registerAction("actionRefCalculate", actionRefCalculate);
         this.actionRefCalculate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOpenExamineReport
        this.actionOpenExamineReport = new ActionOpenExamineReport(this);
        getActionManager().registerAction("actionOpenExamineReport", actionOpenExamineReport);
         this.actionOpenExamineReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowAllOrg
        this.actionShowAllOrg = new ActionShowAllOrg(this);
        getActionManager().registerAction("actionShowAllOrg", actionShowAllOrg);
         this.actionShowAllOrg.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCollectCoefficient
        this.actionCollectCoefficient = new ActionCollectCoefficient(this);
        getActionManager().registerAction("actionCollectCoefficient", actionCollectCoefficient);
         this.actionCollectCoefficient.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBgAdjustCheck
        this.actionBgAdjustCheck = new ActionBgAdjustCheck(this);
        getActionManager().registerAction("actionBgAdjustCheck", actionBgAdjustCheck);
         this.actionBgAdjustCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModifyBgRate
        this.actionModifyBgRate = new ActionModifyBgRate(this);
        getActionManager().registerAction("actionModifyBgRate", actionModifyBgRate);
         this.actionModifyBgRate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.paneMain = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblMain1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnReportCheck = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBgExamineCheck = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnViewBgForm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewComment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAdjustView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataExamine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHideCol = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelHide = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAdjustCheck = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefCalculate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnOpenExaminReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShowAllOrg = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCollectCoefficient = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnModifyBgRate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBgForm = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewComment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReportCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBgExamineCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.miOpenExamineReport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAdjustCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.paneMain.setName("paneMain");
        this.pnlMain.setName("pnlMain");
        this.tblMain1.setName("tblMain1");
        this.btnReportCheck.setName("btnReportCheck");
        this.btnBgExamineCheck.setName("btnBgExamineCheck");
        this.btnAudit.setName("btnAudit");
        this.separator1.setName("separator1");
        this.btnViewBgForm.setName("btnViewBgForm");
        this.btnViewComment.setName("btnViewComment");
        this.btnAdjustView.setName("btnAdjustView");
        this.btnDataExamine.setName("btnDataExamine");
        this.btnHideCol.setName("btnHideCol");
        this.btnCancelHide.setName("btnCancelHide");
        this.btnAdjustCheck.setName("btnAdjustCheck");
        this.btnRefCalculate.setName("btnRefCalculate");
        this.btnOpenExaminReport.setName("btnOpenExaminReport");
        this.btnShowAllOrg.setName("btnShowAllOrg");
        this.btnCollectCoefficient.setName("btnCollectCoefficient");
        this.btnModifyBgRate.setName("btnModifyBgRate");
        this.menuItemViewBgForm.setName("menuItemViewBgForm");
        this.menuItemViewComment.setName("menuItemViewComment");
        this.menuItemReportCheck.setName("menuItemReportCheck");
        this.menuItemBgExamineCheck.setName("menuItemBgExamineCheck");
        this.miOpenExamineReport.setName("miOpenExamineReport");
        this.menuItemAdjustCheck.setName("menuItemAdjustCheck");
        this.menuItemAudit.setName("menuItemAudit");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.menuTool.setVisible(false);		
        this.menuItemSendMessage.setVisible(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.kDSeparator4.setVisible(false);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemFirst.setEnabled(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemPre.setEnabled(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemNext.setEnabled(false);		
        this.menuItemLast.setVisible(false);		
        this.menuItemLast.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // paneMain
        // pnlMain
        // tblMain1
		String tblMain1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"periodname\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"itemName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"itemKey\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"isSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"bgform\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{periodname}</t:Cell><t:Cell>$Resource{itemName}</t:Cell><t:Cell>$Resource{itemKey}</t:Cell><t:Cell>$Resource{isSum}</t:Cell><t:Cell>$Resource{bgform}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain1.setFormatXml(resHelper.translateString("tblMain1",tblMain1StrXML));		
        this.tblMain1.setVisible(false);

        

        // btnReportCheck
        this.btnReportCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionReportCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReportCheck.setText(resHelper.getString("btnReportCheck.text"));		
        this.btnReportCheck.setToolTipText(resHelper.getString("btnReportCheck.toolTipText"));
        // btnBgExamineCheck
        this.btnBgExamineCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgExamineCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBgExamineCheck.setText(resHelper.getString("btnBgExamineCheck.text"));		
        this.btnBgExamineCheck.setToolTipText(resHelper.getString("btnBgExamineCheck.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // separator1		
        this.separator1.setOrientation(1);
        // btnViewBgForm
        this.btnViewBgForm.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgForm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgForm.setText(resHelper.getString("btnViewBgForm.text"));		
        this.btnViewBgForm.setToolTipText(resHelper.getString("btnViewBgForm.toolTipText"));
        // btnViewComment
        this.btnViewComment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewComment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewComment.setText(resHelper.getString("btnViewComment.text"));		
        this.btnViewComment.setToolTipText(resHelper.getString("btnViewComment.toolTipText"));
        // btnAdjustView
        this.btnAdjustView.setAction((IItemAction)ActionProxyFactory.getProxy(actionAdjustView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjustView.setText(resHelper.getString("btnAdjustView.text"));		
        this.btnAdjustView.setToolTipText(resHelper.getString("btnAdjustView.toolTipText"));
        // btnDataExamine
        this.btnDataExamine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDataExamine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDataExamine.setText(resHelper.getString("btnDataExamine.text"));		
        this.btnDataExamine.setToolTipText(resHelper.getString("btnDataExamine.toolTipText"));
        // btnHideCol
        this.btnHideCol.setAction((IItemAction)ActionProxyFactory.getProxy(actionHideCol, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHideCol.setText(resHelper.getString("btnHideCol.text"));		
        this.btnHideCol.setToolTipText(resHelper.getString("btnHideCol.toolTipText"));
        // btnCancelHide
        this.btnCancelHide.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelHide, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelHide.setText(resHelper.getString("btnCancelHide.text"));		
        this.btnCancelHide.setToolTipText(resHelper.getString("btnCancelHide.toolTipText"));
        // btnAdjustCheck
        this.btnAdjustCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgAdjustCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAdjustCheck.setText(resHelper.getString("btnAdjustCheck.text"));		
        this.btnAdjustCheck.setToolTipText(resHelper.getString("btnAdjustCheck.toolTipText"));
        // btnRefCalculate
        this.btnRefCalculate.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefCalculate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefCalculate.setText(resHelper.getString("btnRefCalculate.text"));		
        this.btnRefCalculate.setToolTipText(resHelper.getString("btnRefCalculate.toolTipText"));
        // btnOpenExaminReport
        this.btnOpenExaminReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpenExamineReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOpenExaminReport.setText(resHelper.getString("btnOpenExaminReport.text"));		
        this.btnOpenExaminReport.setToolTipText(resHelper.getString("btnOpenExaminReport.toolTipText"));
        // btnShowAllOrg
        this.btnShowAllOrg.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowAllOrg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShowAllOrg.setText(resHelper.getString("btnShowAllOrg.text"));		
        this.btnShowAllOrg.setToolTipText(resHelper.getString("btnShowAllOrg.toolTipText"));
        // btnCollectCoefficient
        this.btnCollectCoefficient.setAction((IItemAction)ActionProxyFactory.getProxy(actionCollectCoefficient, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCollectCoefficient.setText(resHelper.getString("btnCollectCoefficient.text"));		
        this.btnCollectCoefficient.setToolTipText(resHelper.getString("btnCollectCoefficient.toolTipText"));
        // btnModifyBgRate
        this.btnModifyBgRate.setAction((IItemAction)ActionProxyFactory.getProxy(actionModifyBgRate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModifyBgRate.setText(resHelper.getString("btnModifyBgRate.text"));		
        this.btnModifyBgRate.setToolTipText(resHelper.getString("btnModifyBgRate.toolTipText"));
        // menuItemViewBgForm
        this.menuItemViewBgForm.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgForm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgForm.setText(resHelper.getString("menuItemViewBgForm.text"));		
        this.menuItemViewBgForm.setToolTipText(resHelper.getString("menuItemViewBgForm.toolTipText"));		
        this.menuItemViewBgForm.setMnemonic(70);
        // menuItemViewComment
        this.menuItemViewComment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewComment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewComment.setText(resHelper.getString("menuItemViewComment.text"));		
        this.menuItemViewComment.setToolTipText(resHelper.getString("menuItemViewComment.toolTipText"));		
        this.menuItemViewComment.setMnemonic(67);
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
        // miOpenExamineReport
        this.miOpenExamineReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpenExamineReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.miOpenExamineReport.setText(resHelper.getString("miOpenExamineReport.text"));
        // menuItemAdjustCheck
        this.menuItemAdjustCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgAdjustCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAdjustCheck.setText(resHelper.getString("menuItemAdjustCheck.text"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));		
        this.menuItemAudit.setMnemonic(83);
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
        paneMain.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(paneMain, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //paneMain
        paneMain.add(pnlMain, resHelper.getString("pnlMain.constraints"));
        //pnlMain
        pnlMain.setLayout(new KDLayout());
        pnlMain.putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 576));        tblMain1.setBounds(new Rectangle(5, 5, 980, 568));
        pnlMain.add(tblMain1, new KDLayout.Constraints(5, 5, 980, 568, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(MenuItemAttachment);
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
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemRemove);
        menuEdit.add(menuItemReset);
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
        menuView.add(menuItemViewBgForm);
        menuView.add(menuItemViewComment);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemReportCheck);
        menuBiz.add(menuItemBgExamineCheck);
        menuBiz.add(miOpenExamineReport);
        menuBiz.add(menuItemAdjustCheck);
        menuBiz.add(menuItemAudit);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnReportCheck);
        this.toolBar.add(btnBgExamineCheck);
        this.toolBar.add(btnAudit);
        this.toolBar.add(separator1);
        this.toolBar.add(btnViewBgForm);
        this.toolBar.add(btnViewComment);
        this.toolBar.add(btnAdjustView);
        this.toolBar.add(btnDataExamine);
        this.toolBar.add(btnHideCol);
        this.toolBar.add(btnCancelHide);
        this.toolBar.add(btnAdjustCheck);
        this.toolBar.add(btnRefCalculate);
        this.toolBar.add(btnOpenExaminReport);
        this.toolBar.add(btnShowAllOrg);
        this.toolBar.add(btnCollectCoefficient);
        this.toolBar.add(btnModifyBgRate);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.budget.app.BgDisCountFormEditUIHandler";
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
        this.editData = (com.kingdee.eas.ma.budget.BgDisCountFormInfo)ov;
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
        }
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
        return sic;
    }        
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output actionViewBgForm_actionPerformed method
     */
    public void actionViewBgForm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewComment_actionPerformed method
     */
    public void actionViewComment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAdjustView_actionPerformed method
     */
    public void actionAdjustView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBgExamineCheck_actionPerformed method
     */
    public void actionBgExamineCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReportCheck_actionPerformed method
     */
    public void actionReportCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDataExamine_actionPerformed method
     */
    public void actionDataExamine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHideCol_actionPerformed method
     */
    public void actionHideCol_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelHide_actionPerformed method
     */
    public void actionCancelHide_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefCalculate_actionPerformed method
     */
    public void actionRefCalculate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOpenExamineReport_actionPerformed method
     */
    public void actionOpenExamineReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowAllOrg_actionPerformed method
     */
    public void actionShowAllOrg_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCollectCoefficient_actionPerformed method
     */
    public void actionCollectCoefficient_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBgAdjustCheck_actionPerformed method
     */
    public void actionBgAdjustCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModifyBgRate_actionPerformed method
     */
    public void actionModifyBgRate_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionViewBgForm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBgForm() {
    	return false;
    }
	public RequestContext prepareActionViewComment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewComment() {
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
	public RequestContext prepareActionAdjustView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAdjustView() {
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
	public RequestContext prepareActionDataExamine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDataExamine() {
    	return false;
    }
	public RequestContext prepareActionHideCol(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHideCol() {
    	return false;
    }
	public RequestContext prepareActionCancelHide(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelHide() {
    	return false;
    }
	public RequestContext prepareActionRefCalculate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefCalculate() {
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
	public RequestContext prepareActionShowAllOrg(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowAllOrg() {
    	return false;
    }
	public RequestContext prepareActionCollectCoefficient(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCollectCoefficient() {
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
	public RequestContext prepareActionModifyBgRate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModifyBgRate() {
    	return false;
    }

    /**
     * output ActionViewBgForm class
     */     
    protected class ActionViewBgForm extends ItemAction {     
    
        public ActionViewBgForm()
        {
            this(null);
        }

        public ActionViewBgForm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBgForm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgForm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgForm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionViewBgForm", "actionViewBgForm_actionPerformed", e);
        }
    }

    /**
     * output ActionViewComment class
     */     
    protected class ActionViewComment extends ItemAction {     
    
        public ActionViewComment()
        {
            this(null);
        }

        public ActionViewComment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewComment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewComment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewComment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionViewComment", "actionViewComment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionAdjustView class
     */     
    protected class ActionAdjustView extends ItemAction {     
    
        public ActionAdjustView()
        {
            this(null);
        }

        public ActionAdjustView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAdjustView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjustView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAdjustView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionAdjustView", "actionAdjustView_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionBgExamineCheck", "actionBgExamineCheck_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionReportCheck", "actionReportCheck_actionPerformed", e);
        }
    }

    /**
     * output ActionDataExamine class
     */     
    protected class ActionDataExamine extends ItemAction {     
    
        public ActionDataExamine()
        {
            this(null);
        }

        public ActionDataExamine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDataExamine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataExamine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataExamine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionDataExamine", "actionDataExamine_actionPerformed", e);
        }
    }

    /**
     * output ActionHideCol class
     */     
    protected class ActionHideCol extends ItemAction {     
    
        public ActionHideCol()
        {
            this(null);
        }

        public ActionHideCol(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionHideCol.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHideCol.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHideCol.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionHideCol", "actionHideCol_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelHide class
     */     
    protected class ActionCancelHide extends ItemAction {     
    
        public ActionCancelHide()
        {
            this(null);
        }

        public ActionCancelHide(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelHide.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelHide.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelHide.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionCancelHide", "actionCancelHide_actionPerformed", e);
        }
    }

    /**
     * output ActionRefCalculate class
     */     
    protected class ActionRefCalculate extends ItemAction {     
    
        public ActionRefCalculate()
        {
            this(null);
        }

        public ActionRefCalculate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefCalculate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefCalculate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefCalculate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionRefCalculate", "actionRefCalculate_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionOpenExamineReport", "actionOpenExamineReport_actionPerformed", e);
        }
    }

    /**
     * output ActionShowAllOrg class
     */     
    protected class ActionShowAllOrg extends ItemAction {     
    
        public ActionShowAllOrg()
        {
            this(null);
        }

        public ActionShowAllOrg(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShowAllOrg.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowAllOrg.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowAllOrg.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionShowAllOrg", "actionShowAllOrg_actionPerformed", e);
        }
    }

    /**
     * output ActionCollectCoefficient class
     */     
    protected class ActionCollectCoefficient extends ItemAction {     
    
        public ActionCollectCoefficient()
        {
            this(null);
        }

        public ActionCollectCoefficient(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCollectCoefficient.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCollectCoefficient.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCollectCoefficient.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionCollectCoefficient", "actionCollectCoefficient_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionBgAdjustCheck", "actionBgAdjustCheck_actionPerformed", e);
        }
    }

    /**
     * output ActionModifyBgRate class
     */     
    protected class ActionModifyBgRate extends ItemAction {     
    
        public ActionModifyBgRate()
        {
            this(null);
        }

        public ActionModifyBgRate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionModifyBgRate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyBgRate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModifyBgRate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormEditUI.this, "ActionModifyBgRate", "actionModifyBgRate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.budget.client", "BgDisCountFormEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}