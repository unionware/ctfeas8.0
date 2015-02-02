/**
 * output package name
 */
package com.kingdee.eas.ma.nbudget.client;

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
public abstract class AbstractBgNFormProcessUI extends com.kingdee.eas.ma.nbudget.client.BgNFProcessUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgNFormProcessUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnItemInput;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFreeze;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelFreeze;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnItemDeco;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTraceActualVal;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHelped;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCheckSubsidiaryLedger;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTraceActualVal;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemItemDeco;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem importBgItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem exportBgItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemOpenExamineReport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPredict;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuRemoveItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem rmenuItemCreateExpFromItemProperty;
    protected ActionItemDeco actionItemDeco = null;
    protected ActionOpenExamineReport actionOpenExamineReport = null;
    protected ActionCreateExpFromItemProperty actionCreateExpFromItemProperty = null;
    protected ActionPredict actionPredict = null;
    protected ActionTraceActualVal actionTraceActualVal = null;
    protected ActionImportBgItem actionImportBgItem = null;
    protected ActionExportBgItem actionExportBgItem = null;
    protected ActionHelped actionHelped = null;
    protected ActionCheckSubsidiaryLedger actionCheckSubsidiaryLedger = null;
    /**
     * output class constructor
     */
    public AbstractBgNFormProcessUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractBgNFormProcessUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPageSetup
        String _tempStr = null;
        actionPageSetup.setEnabled(true);
        actionPageSetup.setDaemonRun(false);

        actionPageSetup.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_L));
        _tempStr = resHelper.getString("ActionPageSetup.SHORT_DESCRIPTION");
        actionPageSetup.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPageSetup.LONG_DESCRIPTION");
        actionPageSetup.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPageSetup.NAME");
        actionPageSetup.putValue(ItemAction.NAME, _tempStr);
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPageSetup.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCalculator
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
        //actionExport
        actionExport.setEnabled(true);
        actionExport.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionExport.SHORT_DESCRIPTION");
        actionExport.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExport.LONG_DESCRIPTION");
        actionExport.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExport.NAME");
        actionExport.putValue(ItemAction.NAME, _tempStr);
         this.actionExport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        actionImport.setEnabled(true);
        actionImport.setDaemonRun(false);

        actionImport.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
        _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
        actionImport.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
        actionImport.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImport.NAME");
        actionImport.putValue(ItemAction.NAME, _tempStr);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSavePrintSetting
        actionSavePrintSetting.setEnabled(true);
        actionSavePrintSetting.setDaemonRun(false);

        actionSavePrintSetting.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_T));
        _tempStr = resHelper.getString("ActionSavePrintSetting.SHORT_DESCRIPTION");
        actionSavePrintSetting.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSavePrintSetting.LONG_DESCRIPTION");
        actionSavePrintSetting.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSavePrintSetting.NAME");
        actionSavePrintSetting.putValue(ItemAction.NAME, _tempStr);
         this.actionSavePrintSetting.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBgExamineCheck
        actionBgExamineCheck.setEnabled(true);
        actionBgExamineCheck.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionBgExamineCheck.SHORT_DESCRIPTION");
        actionBgExamineCheck.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionBgExamineCheck.LONG_DESCRIPTION");
        actionBgExamineCheck.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionBgExamineCheck.NAME");
        actionBgExamineCheck.putValue(ItemAction.NAME, _tempStr);
         this.actionBgExamineCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransPeriod
        actionTransPeriod.setEnabled(true);
        actionTransPeriod.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionTransPeriod.SHORT_DESCRIPTION");
        actionTransPeriod.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTransPeriod.LONG_DESCRIPTION");
        actionTransPeriod.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTransPeriod.NAME");
        actionTransPeriod.putValue(ItemAction.NAME, _tempStr);
         this.actionTransPeriod.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionGather
        actionGather.setEnabled(true);
        actionGather.setDaemonRun(false);

        actionGather.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift O"));
        actionGather.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        _tempStr = resHelper.getString("ActionGather.SHORT_DESCRIPTION");
        actionGather.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionGather.LONG_DESCRIPTION");
        actionGather.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionGather.NAME");
        actionGather.putValue(ItemAction.NAME, _tempStr);
         this.actionGather.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewCourseCause
        actionViewCourseCause.setEnabled(true);
        actionViewCourseCause.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionViewCourseCause.SHORT_DESCRIPTION");
        actionViewCourseCause.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewCourseCause.LONG_DESCRIPTION");
        actionViewCourseCause.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewCourseCause.NAME");
        actionViewCourseCause.putValue(ItemAction.NAME, _tempStr);
         this.actionViewCourseCause.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionItemInput
        actionItemInput.setEnabled(false);
        actionItemInput.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionItemInput.SHORT_DESCRIPTION");
        actionItemInput.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionItemInput.LONG_DESCRIPTION");
        actionItemInput.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionItemInput.NAME");
        actionItemInput.putValue(ItemAction.NAME, _tempStr);
         this.actionItemInput.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionItemDeco
        this.actionItemDeco = new ActionItemDeco(this);
        getActionManager().registerAction("actionItemDeco", actionItemDeco);
         this.actionItemDeco.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOpenExamineReport
        this.actionOpenExamineReport = new ActionOpenExamineReport(this);
        getActionManager().registerAction("actionOpenExamineReport", actionOpenExamineReport);
         this.actionOpenExamineReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreateExpFromItemProperty
        this.actionCreateExpFromItemProperty = new ActionCreateExpFromItemProperty(this);
        getActionManager().registerAction("actionCreateExpFromItemProperty", actionCreateExpFromItemProperty);
         this.actionCreateExpFromItemProperty.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPredict
        this.actionPredict = new ActionPredict(this);
        getActionManager().registerAction("actionPredict", actionPredict);
         this.actionPredict.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTraceActualVal
        this.actionTraceActualVal = new ActionTraceActualVal(this);
        getActionManager().registerAction("actionTraceActualVal", actionTraceActualVal);
         this.actionTraceActualVal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportBgItem
        this.actionImportBgItem = new ActionImportBgItem(this);
        getActionManager().registerAction("actionImportBgItem", actionImportBgItem);
         this.actionImportBgItem.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportBgItem
        this.actionExportBgItem = new ActionExportBgItem(this);
        getActionManager().registerAction("actionExportBgItem", actionExportBgItem);
         this.actionExportBgItem.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHelped
        this.actionHelped = new ActionHelped(this);
        getActionManager().registerAction("actionHelped", actionHelped);
         this.actionHelped.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCheckSubsidiaryLedger
        this.actionCheckSubsidiaryLedger = new ActionCheckSubsidiaryLedger(this);
        getActionManager().registerAction("actionCheckSubsidiaryLedger", actionCheckSubsidiaryLedger);
         this.actionCheckSubsidiaryLedger.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnItemInput = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFreeze = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelFreeze = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnItemDeco = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTraceActualVal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnHelped = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCheckSubsidiaryLedger = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemTraceActualVal = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemItemDeco = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.importBgItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.exportBgItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemOpenExamineReport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPredict = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuRemoveItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.rmenuItemCreateExpFromItemProperty = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnItemInput.setName("btnItemInput");
        this.btnFreeze.setName("btnFreeze");
        this.btnCancelFreeze.setName("btnCancelFreeze");
        this.btnItemDeco.setName("btnItemDeco");
        this.btnTraceActualVal.setName("btnTraceActualVal");
        this.btnHelped.setName("btnHelped");
        this.btnCheckSubsidiaryLedger.setName("btnCheckSubsidiaryLedger");
        this.menuItemTraceActualVal.setName("menuItemTraceActualVal");
        this.menuItemItemDeco.setName("menuItemItemDeco");
        this.importBgItem.setName("importBgItem");
        this.exportBgItem.setName("exportBgItem");
        this.menuItemOpenExamineReport.setName("menuItemOpenExamineReport");
        this.menuItemPredict.setName("menuItemPredict");
        this.menuRemoveItem.setName("menuRemoveItem");
        this.rmenuItemCreateExpFromItemProperty.setName("rmenuItemCreateExpFromItemProperty");
        // CoreUI		
        this.menuItemPageSetup.setText(resHelper.getString("menuItemPageSetup.text"));		
        this.menuItemCalculator.setText(resHelper.getString("menuItemCalculator.text"));		
        this.menuItemCalculator.setMnemonic(65);		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setMnemonic(73);		
        this.menuItemSavePrintSetting.setText(resHelper.getString("menuItemSavePrintSetting.text"));		
        this.menuItemSavePrintSetting.setMnemonic(84);		
        this.menuItemPreview.setText(resHelper.getString("menuItemPreview.text"));		
        this.menuItemPreview.setMnemonic(82);		
        this.menuItemRefer.setText(resHelper.getString("menuItemRefer.text"));		
        this.menuItemRefer.setMnemonic(85);
        this.bgFormulaGroup.add(this.menuItemAutoCreateBgFormula);
        this.bgFormulaGroup.add(this.menuItemManualCreateBgFormula);
        this.dataGroup.add(this.menuItemDealReplace);
        this.dataGroup.add(this.menuItemMeasReplace);		
        this.menuItemViewAdjust.setText(resHelper.getString("menuItemViewAdjust.text"));		
        this.menuItemViewAdjust.setToolTipText(resHelper.getString("menuItemViewAdjust.toolTipText"));
        this.itemItemInput.setAction((IItemAction)ActionProxyFactory.getProxy(actionItemInput, new Class[] { IItemAction.class }, getServiceContext()));		
        this.itemItemInput.setText(resHelper.getString("itemItemInput.text"));		
        this.menuItemGather.setText(resHelper.getString("menuItemGather.text"));		
        this.menuItemTransCurrency.setText(resHelper.getString("menuItemTransCurrency.text"));
        // btnItemInput
        this.btnItemInput.setAction((IItemAction)ActionProxyFactory.getProxy(actionItemInput, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnItemInput.setText(resHelper.getString("btnItemInput.text"));		
        this.btnItemInput.setToolTipText(resHelper.getString("btnItemInput.toolTipText"));
        // btnFreeze		
        this.btnFreeze.setText(resHelper.getString("btnFreeze.text"));		
        this.btnFreeze.setToolTipText(resHelper.getString("btnFreeze.toolTipText"));
        // btnCancelFreeze		
        this.btnCancelFreeze.setText(resHelper.getString("btnCancelFreeze.text"));		
        this.btnCancelFreeze.setToolTipText(resHelper.getString("btnCancelFreeze.toolTipText"));
        // btnItemDeco
        this.btnItemDeco.setAction((IItemAction)ActionProxyFactory.getProxy(actionItemDeco, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnItemDeco.setText(resHelper.getString("btnItemDeco.text"));		
        this.btnItemDeco.setToolTipText(resHelper.getString("btnItemDeco.toolTipText"));
        // btnTraceActualVal
        this.btnTraceActualVal.setAction((IItemAction)ActionProxyFactory.getProxy(actionTraceActualVal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTraceActualVal.setText(resHelper.getString("btnTraceActualVal.text"));		
        this.btnTraceActualVal.setToolTipText(resHelper.getString("btnTraceActualVal.toolTipText"));
        // btnHelped
        this.btnHelped.setAction((IItemAction)ActionProxyFactory.getProxy(actionHelped, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHelped.setText(resHelper.getString("btnHelped.text"));		
        this.btnHelped.setToolTipText(resHelper.getString("btnHelped.toolTipText"));
        // btnCheckSubsidiaryLedger
        this.btnCheckSubsidiaryLedger.setAction((IItemAction)ActionProxyFactory.getProxy(actionCheckSubsidiaryLedger, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCheckSubsidiaryLedger.setText(resHelper.getString("btnCheckSubsidiaryLedger.text"));		
        this.btnCheckSubsidiaryLedger.setToolTipText(resHelper.getString("btnCheckSubsidiaryLedger.toolTipText"));
        // menuItemTraceActualVal
        this.menuItemTraceActualVal.setAction((IItemAction)ActionProxyFactory.getProxy(actionTraceActualVal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTraceActualVal.setText(resHelper.getString("menuItemTraceActualVal.text"));		
        this.menuItemTraceActualVal.setToolTipText(resHelper.getString("menuItemTraceActualVal.toolTipText"));
        // menuItemItemDeco
        this.menuItemItemDeco.setAction((IItemAction)ActionProxyFactory.getProxy(actionItemDeco, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemItemDeco.setText(resHelper.getString("menuItemItemDeco.text"));		
        this.menuItemItemDeco.setToolTipText(resHelper.getString("menuItemItemDeco.toolTipText"));		
        this.menuItemItemDeco.setMnemonic(68);
        // importBgItem
        this.importBgItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportBgItem, new Class[] { IItemAction.class }, getServiceContext()));		
        this.importBgItem.setText(resHelper.getString("importBgItem.text"));
        // exportBgItem
        this.exportBgItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportBgItem, new Class[] { IItemAction.class }, getServiceContext()));		
        this.exportBgItem.setText(resHelper.getString("exportBgItem.text"));
        // menuItemOpenExamineReport
        this.menuItemOpenExamineReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpenExamineReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemOpenExamineReport.setText(resHelper.getString("menuItemOpenExamineReport.text"));		
        this.menuItemOpenExamineReport.setToolTipText(resHelper.getString("menuItemOpenExamineReport.toolTipText"));
        // menuItemPredict
        this.menuItemPredict.setAction((IItemAction)ActionProxyFactory.getProxy(actionPredict, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPredict.setText(resHelper.getString("menuItemPredict.text"));		
        this.menuItemPredict.setMnemonic(81);
        // menuRemoveItem
        this.menuRemoveItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveFormula, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuRemoveItem.setText(resHelper.getString("menuRemoveItem.text"));
        // rmenuItemCreateExpFromItemProperty
        this.rmenuItemCreateExpFromItemProperty.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateExpFromItemProperty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.rmenuItemCreateExpFromItemProperty.setText(resHelper.getString("rmenuItemCreateExpFromItemProperty.text"));
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
        this.setBounds(new Rectangle(10, 10, 800, 550));
this.setLayout(new BorderLayout(0, 0));

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
        this.menuBar.add(menuInsert);
        this.menuBar.add(menuStyle);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuData);
        this.menuBar.add(menuWindow);
        this.menuBar.add(menuHelp);
        this.menuBar.add(menuRangeContext);
        //menuFile
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSaveas);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemImport);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuItemExport);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemAttachement);
        menuFile.add(kDSeparator17);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemSavePrintSetting);
        menuFile.add(menuItemPreview);
        menuFile.add(menuItemPrint);
        menuFile.add(sprtOnSend);
        menuFile.add(menuSendMail);
        menuFile.add(menuPublishReport);
        menuFile.add(separatorF01);
        menuFile.add(menuItemRefer);
        menuFile.add(sprtOnExit);
        menuFile.add(menuItemExitCurrent);
        //menuSendMail
        menuSendMail.add(menuItemSendExcel);
        menuSendMail.add(menuItemSendHtml);
        //menuPublishReport
        menuPublishReport.add(menuItemPublishAsExcel);
        menuPublishReport.add(menuItemPublishAsHtml);
        menuPublishReport.add(menuItemPublishAsLink);
        //menuEdit
        menuEdit.add(menuBgFormulaPara);
        menuEdit.add(menuDealData);
        menuEdit.add(menuItemUndo);
        menuEdit.add(menuItemRedo);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCut);
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemPaste);
        menuEdit.add(menuItemPasteSelective);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuClear);
        menuEdit.add(menuItemDelete);
        menuEdit.add(menuItemDeleteWorkSheet);
        menuEdit.add(menuItemMoveOrCopySheet);
        menuEdit.add(kDSeparator5);
        menuEdit.add(menuItemFind);
        menuEdit.add(menuItemReplace);
        menuEdit.add(menuBatchEditFormula);
        menuEdit.add(kDSeparator15);
        menuEdit.add(menuSumRange);
        menuEdit.add(kmInsertMutiRow);
        //menuBgFormulaPara
        menuBgFormulaPara.add(menuItemAutoCreateBgFormula);
        menuBgFormulaPara.add(menuItemManualCreateBgFormula);
        //menuDealData
        menuDealData.add(menuItemDealReplace);
        menuDealData.add(menuItemMeasReplace);
        //menuClear
        menuClear.add(menuItemClearAll);
        menuClear.add(menuItemClearStyle);
        menuClear.add(menuItemClearContent);
        menuClear.add(menuItemClearComments);
        menuClear.add(menuItemFormula);
        //menuSumRange
        menuSumRange.add(menuItemSetSumRange);
        menuSumRange.add(menuItemClearSumRange);
        menuSumRange.add(menuItemSaveSumZone);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemViewFormula);
        menuView.add(menuItemViewValue);
        menuView.add(menuItemViewItemFormula);
        menuView.add(menuItemViewSumRange);
        menuView.add(kDSeparator6);
        menuView.add(menuItemReportVariables);
        menuView.add(menuComments);
        menuView.add(menuItemViewScale);
        menuView.add(kmNormal);
        menuView.add(kmPagin);
        menuView.add(separatorV01);
        menuView.add(menuViewInfo);
        menuView.add(menuViewGather);
        menuView.add(menuItemTraceActualVal);
        menuView.add(menuItemViewFlow);
        //menuComments
        menuComments.add(menuItemCellComments);
        menuComments.add(menuItemSheetComments);
        menuComments.add(menuItemReportComments);
        //menuViewInfo
        menuViewInfo.add(menuItemViewCause);
        menuViewInfo.add(menuItemViewAuditIdea);
        menuViewInfo.add(separatorV02);
        menuViewInfo.add(menuItemViewHistory);
        menuViewInfo.add(menuItemViewAdjust);
        menuViewInfo.add(separatorV03);
        menuViewInfo.add(menuItemViewGather);
        menuViewInfo.add(menuItemReferBgForm);
        menuViewInfo.add(menuViewGatherBook);
        //menuViewGatherBook
        menuViewGatherBook.add(menuItemViewGatherBook);
        menuViewGatherBook.add(menuItemViewGatherBookOrgCol);
        menuViewGatherBook.add(menuItemViewOrgSelector);
        //menuViewGather
        menuViewGather.add(menuItemViewCompany);
        menuViewGather.add(menuItemViewIC);
        menuViewGather.add(menuItemViewCI);
        //menuInsert
        menuInsert.add(menuItemInsertCell);
        menuInsert.add(menuItemInsertRow);
        menuInsert.add(menuItemInsertColumn);
        menuInsert.add(menuItemInsertSheet);
        menuInsert.add(menuItemInsertGraph);
        menuInsert.add(kDSeparator7);
        menuInsert.add(menuItemInsertFormula);
        menuInsert.add(menuItemInsertName);
        menuInsert.add(menuInsertComments);
        menuInsert.add(menuItemInsertPic);
        menuInsert.add(kDSeparator8);
        menuInsert.add(menuItemInsertLink);
        menuInsert.add(menuBgLink);
        //menuInsertComments
        menuInsertComments.add(menuItemInsertCellComments);
        menuInsertComments.add(menuItemInsertSheetComments);
        menuInsertComments.add(menuItemInsertReportComments);
        //menuBgLink
        menuBgLink.add(menuItemSetLink);
        menuBgLink.add(menuItemOpenLink);
        menuBgLink.add(separatorI01);
        menuBgLink.add(menuItemClearLink);
        //menuStyle
        menuStyle.add(menuItemStyleCell);
        menuStyle.add(menuItemStyleRow);
        menuStyle.add(menuItemStyleColumn);
        menuStyle.add(menuItemStyleSheet);
        menuStyle.add(kDSeparator9);
        menuStyle.add(menuItemCellMerge);
        menuStyle.add(menuItemMerge);
        menuStyle.add(kDSeparator10);
        menuStyle.add(menuItemLock);
        menuStyle.add(menuItemUnLock);
        menuStyle.add(menuItemConditionalFormat);
        //menuItemStyleRow
        menuItemStyleRow.add(menuItemRowHeight);
        menuItemStyleRow.add(menuItemRowHide);
        menuItemStyleRow.add(menuItemRowUnHide);
        //menuItemStyleColumn
        menuItemStyleColumn.add(menuItemColWidth);
        menuItemStyleColumn.add(menuItemColHide);
        menuItemStyleColumn.add(menuItemColUnHide);
        //menuItemStyleSheet
        menuItemStyleSheet.add(menuItemRename);
        menuItemStyleSheet.add(menuItemSheetHide);
        menuItemStyleSheet.add(menuItemSheetUnHide);
        menuItemStyleSheet.add(menuItemSheetLabelColor);
        //menuTool
        menuTool.add(menuProtect);
        menuTool.add(kDSeparator11);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemToolBarCustom);
        menuTool.add(menuItemSheetManager);
        menuTool.add(menuItemCalculator);
        menuTool.add(spOnFillGroup);
        menuTool.add(menuItemFormulaFill);
        menuTool.add(menuItemSheeSum);
        menuTool.add(kDSeparator13);
        menuTool.add(menuReportCheck);
        menuTool.add(menuReportRound);
        menuTool.add(menuConvert);
        menuTool.add(kDSeparator14);
        menuTool.add(menuItemAudit);
        menuTool.add(itemWorkflow);
        menuTool.add(separatorT01);
        menuTool.add(menuBgFormula);
        menuTool.add(menuItemRemoveBgFormula);
        menuTool.add(menuExpresssion);
        menuTool.add(menuItemCheckPeriod);
        menuTool.add(menuItemItemDeco);
        menuTool.add(itemItemInput);
        menuTool.add(importBgItem);
        menuTool.add(exportBgItem);
        //menuProtect
        menuProtect.add(menuItemProtectSheet);
        menuProtect.add(menuItemAllowEditingRange);
        menuProtect.add(menuItemProtectBook);
        //menuReportCheck
        menuReportCheck.add(menuItemReportCheck);
        menuReportCheck.add(menuItemReportCheckSolution);
        //menuReportRound
        menuReportRound.add(menuItemReportRound);
        menuReportRound.add(menuItemReportRoundSolution);
        //menuConvert
        menuConvert.add(menuItemConvert);
        menuConvert.add(menuItemConvertSolution);
        //menuBgFormula
        menuBgFormula.add(menuItemBgItemManager);
        menuBgFormula.add(separatorT03);
        menuBgFormula.add(menuItemAddNewBgFormula);
        menuBgFormula.add(menuItemBgFormulaFill);
        menuBgFormula.add(menuCustomFill);
        menuBgFormula.add(menuItemMultiModifyFormula);
        //menuCustomFill
        menuCustomFill.add(menuItemCreateBgFormula);
        menuCustomFill.add(separatorT02);
        menuCustomFill.add(menuItemSetBgItem);
        menuCustomFill.add(menuItemSetBgPeriod);
        menuCustomFill.add(menuItemSetBgElement);
        menuCustomFill.add(menuItemSetCurrency);
        //menuExpresssion
        menuExpresssion.add(menuAutoExp);
        menuExpresssion.add(menuBgFormulaToExp);
        menuExpresssion.add(menuItemMultiModifyExp);
        //menuAutoExp
        menuAutoExp.add(menuItemAuto_BgFormula);
        menuAutoExp.add(menuItemAuto_BgActual);
        menuAutoExp.add(menuItemAuto_BgBalance);
        menuAutoExp.add(menuItemAuto_BgAdjust);
        menuAutoExp.add(menuItemAuto_BgAvlBar);
        menuAutoExp.add(menuItemAuto_BgActualExt);
        //menuBgFormulaToExp
        menuBgFormulaToExp.add(menuItemBgFormulaToExp_BgFormula);
        menuBgFormulaToExp.add(menuItemBgFormulaToExp_BgActual);
        menuBgFormulaToExp.add(menuItemBgFormulaToExp_BgBalance);
        menuBgFormulaToExp.add(menuItemBgFormulaToExp_BgAdjust);
        menuBgFormulaToExp.add(menuItemBgFormulaToExp_BgAvlBar);
        menuBgFormulaToExp.add(menuItemBgFormulaToExp_BgActualExt);
        //menuData
        menuData.add(menuItemCalculate);
        menuData.add(menuItemSort);
        menuData.add(menuItemCalculateSelectedSheet);
        menuData.add(menuItemValidation);
        menuData.add(menuMultiCalculate);
        menuData.add(SeparatorCalculate);
        menuData.add(menuItemCalculateOptions);
        menuData.add(separatorD01);
        menuData.add(menuItemBgExamineCheck);
        menuData.add(menuItemOpenExamineReport);
        menuData.add(menuItemClassifiedStatistics);
        menuData.add(separatorD02);
        menuData.add(menuCtrl);
        menuData.add(menuItemGroup);
        menuData.add(menuFilter);
        menuData.add(menuItemViewCtrl);
        menuData.add(menuItemGather);
        menuData.add(menuItemTransPeriod);
        menuData.add(menuItemTransCurrency);
        menuData.add(menuItemPredict);
        menuData.add(menuItemQueryBizBill);
        //menuMultiCalculate
        menuMultiCalculate.add(menuItemMultiCalculate);
        menuMultiCalculate.add(menuItemMultiCalculateSolution);
        //menuCtrl
        menuCtrl.add(menuItemSetCtrl);
        //menuItemGroup
        menuItemGroup.add(menuItemCreateGroup);
        menuItemGroup.add(menuItemDelGroup);
        menuItemGroup.add(menuItemConfigGroup);
        menuItemGroup.add(menuItemClearGroup);
        menuItemGroup.add(menuItemHideGroup);
        menuItemGroup.add(menuItemShowGroup);
        //menuFilter
        menuFilter.add(menuItemAutoFilter);
        menuFilter.add(menuItemUnFilter);
        menuFilter.add(menuItemAdvancedFilter);
        //menuWindow
        menuWindow.add(menuItemFreeze);
        menuWindow.add(menuItemCancelFreeze);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);
        //menuRangeContext
        menuRangeContext.add(menuRightClear);
        menuRangeContext.add(menuItemRPasteSelective);
        menuRangeContext.add(kDSeparator16);
        menuRangeContext.add(itemRCSetFormula);
        menuRangeContext.add(menuItemRCMultiModifyExp);
        menuRangeContext.add(menuRCSumZone);
        menuRangeContext.add(menuRCBgFormulaToExp);
        menuRangeContext.add(separator2);
        menuRangeContext.add(menuItemRCCreateBgFormula);
        menuRangeContext.add(menuRCBgFormula);
        menuRangeContext.add(menuRemoveItem);
        menuRangeContext.add(menuRCBgLink);
        menuRangeContext.add(separatorRC02);
        menuRangeContext.add(rMenuSendMail);
        menuRangeContext.add(rMenuPublishReport);
        menuRangeContext.add(rmenuItemCreateExpFromItemProperty);
        //menuRightClear
        menuRightClear.add(menuItemRightClearContent);
        menuRightClear.add(menuItemRightClearAll);
        menuRightClear.add(menuItemRightClearFormula);
        menuRightClear.add(menuItemRightClearStyle);
        //menuRCSumZone
        menuRCSumZone.add(itemRCSetSumZone);
        menuRCSumZone.add(itemRCUnSetSumZone);
        //menuRCBgFormulaToExp
        menuRCBgFormulaToExp.add(menuItemRCBgFormulaToExp_BgFormula);
        menuRCBgFormulaToExp.add(menuItemRCBgFormulaToExp_BgActual);
        menuRCBgFormulaToExp.add(menuItemRCBgFormulaToExp_BgBalance);
        menuRCBgFormulaToExp.add(menuItemRCBgFormulaToExp_BgAdjust);
        menuRCBgFormulaToExp.add(menuItemRCBgFormulaToExp_BgAvlBal);
        menuRCBgFormulaToExp.add(menuItemRCBgFormulaToExp_BgActualExt);
        //menuRCBgFormula
        menuRCBgFormula.add(menuItemRCAddNewBgFormula);
        menuRCBgFormula.add(menuItemRCRemoveBgFormula);
        menuRCBgFormula.add(separatorRC00);
        menuRCBgFormula.add(menuItemRCMultiModifyFormula);
        //menuRCBgLink
        menuRCBgLink.add(menuItemRCSetLink);
        menuRCBgLink.add(menuItemRCOpenLink);
        menuRCBgLink.add(separatorRC01);
        menuRCBgLink.add(menuItemRCClearLink);
        //rMenuSendMail
        rMenuSendMail.add(rMenuItemSendExcel);
        rMenuSendMail.add(rMenuItemSendHtml);
        //rMenuPublishReport
        rMenuPublishReport.add(rMenuItemPublishAsExcel);
        rMenuPublishReport.add(rMenuItemPublishAsHtml);
        rMenuPublishReport.add(rMenuItemPublishAsLink);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnSheetManager);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(separator3);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnValueFormulaSwitch);
        this.toolBar.add(btnCalculateCurrentSheet);
        this.toolBar.add(btnCalculate);
        this.toolBar.add(separator1);
        this.toolBar.add(btnCheck);
        this.toolBar.add(btnBalance);
        this.toolBar.add(btnUnLock);
        this.toolBar.add(btnWorkUpate);
        this.toolBar.add(btnViewDivData);
        this.toolBar.add(itemShowLevelComboBox);
        this.toolBar.add(btnItemInput);
        this.toolBar.add(btnFreeze);
        this.toolBar.add(btnCancelFreeze);
        this.toolBar.add(btnItemDeco);
        this.toolBar.add(btnReferBgForm);
        this.toolBar.add(btnViewGatherBook);
        this.toolBar.add(btnViewGatherBookItemCol);
        this.toolBar.add(btnViewGatherBookOrgCol);
        this.toolBar.add(btnViewOrgSelector);
        this.toolBar.add(btnTraceActualVal);
        this.toolBar.add(btnHelped);
        this.toolBar.add(btnCheckSubsidiaryLedger);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.nbudget.app.BgNFormProcessUIHandler";
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
    }

    	

    /**
     * output actionPageSetup_actionPerformed method
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }
    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }
    	

    /**
     * output actionExport_actionPerformed method
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImport_actionPerformed(e);
    }
    	

    /**
     * output actionSavePrintSetting_actionPerformed method
     */
    public void actionSavePrintSetting_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSavePrintSetting_actionPerformed(e);
    }
    	

    /**
     * output actionBgExamineCheck_actionPerformed method
     */
    public void actionBgExamineCheck_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionBgExamineCheck_actionPerformed(e);
    }
    	

    /**
     * output actionTransPeriod_actionPerformed method
     */
    public void actionTransPeriod_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTransPeriod_actionPerformed(e);
    }
    	

    /**
     * output actionGather_actionPerformed method
     */
    public void actionGather_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGather_actionPerformed(e);
    }
    	

    /**
     * output actionViewCourseCause_actionPerformed method
     */
    public void actionViewCourseCause_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewCourseCause_actionPerformed(e);
    }
    	

    /**
     * output actionItemInput_actionPerformed method
     */
    public void actionItemInput_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionItemInput_actionPerformed(e);
    }
    	

    /**
     * output actionItemDeco_actionPerformed method
     */
    public void actionItemDeco_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOpenExamineReport_actionPerformed method
     */
    public void actionOpenExamineReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCreateExpFromItemProperty_actionPerformed method
     */
    public void actionCreateExpFromItemProperty_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPredict_actionPerformed method
     */
    public void actionPredict_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTraceActualVal_actionPerformed method
     */
    public void actionTraceActualVal_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportBgItem_actionPerformed method
     */
    public void actionImportBgItem_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportBgItem_actionPerformed method
     */
    public void actionExportBgItem_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHelped_actionPerformed method
     */
    public void actionHelped_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCheckSubsidiaryLedger_actionPerformed method
     */
    public void actionCheckSubsidiaryLedger_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPageSetup(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPageSetup(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPageSetup() {
    	return false;
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
	public RequestContext prepareActionExport(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExport(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExport() {
    	return false;
    }
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionImport(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionSavePrintSetting(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSavePrintSetting(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSavePrintSetting() {
    	return false;
    }
	public RequestContext prepareActionBgExamineCheck(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionBgExamineCheck(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBgExamineCheck() {
    	return false;
    }
	public RequestContext prepareActionTransPeriod(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionTransPeriod(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransPeriod() {
    	return false;
    }
	public RequestContext prepareActionGather(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionGather(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGather() {
    	return false;
    }
	public RequestContext prepareActionViewCourseCause(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionViewCourseCause(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewCourseCause() {
    	return false;
    }
	public RequestContext prepareActionItemInput(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionItemInput(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionItemInput() {
    	return false;
    }
	public RequestContext prepareActionItemDeco(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionItemDeco() {
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
	public RequestContext prepareActionCreateExpFromItemProperty(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateExpFromItemProperty() {
    	return false;
    }
	public RequestContext prepareActionPredict(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPredict() {
    	return false;
    }
	public RequestContext prepareActionTraceActualVal(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTraceActualVal() {
    	return false;
    }
	public RequestContext prepareActionImportBgItem(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportBgItem() {
    	return false;
    }
	public RequestContext prepareActionExportBgItem(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportBgItem() {
    	return false;
    }
	public RequestContext prepareActionHelped(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHelped() {
    	return false;
    }
	public RequestContext prepareActionCheckSubsidiaryLedger(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCheckSubsidiaryLedger() {
    	return false;
    }

    /**
     * output ActionItemDeco class
     */     
    protected class ActionItemDeco extends ItemAction {     
    
        public ActionItemDeco()
        {
            this(null);
        }

        public ActionItemDeco(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionItemDeco.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionItemDeco.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionItemDeco.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionItemDeco", "actionItemDeco_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionOpenExamineReport", "actionOpenExamineReport_actionPerformed", e);
        }
    }

    /**
     * output ActionCreateExpFromItemProperty class
     */     
    protected class ActionCreateExpFromItemProperty extends ItemAction {     
    
        public ActionCreateExpFromItemProperty()
        {
            this(null);
        }

        public ActionCreateExpFromItemProperty(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCreateExpFromItemProperty.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateExpFromItemProperty.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateExpFromItemProperty.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionCreateExpFromItemProperty", "actionCreateExpFromItemProperty_actionPerformed", e);
        }
    }

    /**
     * output ActionPredict class
     */     
    protected class ActionPredict extends ItemAction {     
    
        public ActionPredict()
        {
            this(null);
        }

        public ActionPredict(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPredict.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPredict.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPredict.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionPredict", "actionPredict_actionPerformed", e);
        }
    }

    /**
     * output ActionTraceActualVal class
     */     
    protected class ActionTraceActualVal extends ItemAction {     
    
        public ActionTraceActualVal()
        {
            this(null);
        }

        public ActionTraceActualVal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTraceActualVal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTraceActualVal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTraceActualVal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionTraceActualVal", "actionTraceActualVal_actionPerformed", e);
        }
    }

    /**
     * output ActionImportBgItem class
     */     
    protected class ActionImportBgItem extends ItemAction {     
    
        public ActionImportBgItem()
        {
            this(null);
        }

        public ActionImportBgItem(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportBgItem.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportBgItem.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportBgItem.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionImportBgItem", "actionImportBgItem_actionPerformed", e);
        }
    }

    /**
     * output ActionExportBgItem class
     */     
    protected class ActionExportBgItem extends ItemAction {     
    
        public ActionExportBgItem()
        {
            this(null);
        }

        public ActionExportBgItem(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportBgItem.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportBgItem.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportBgItem.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionExportBgItem", "actionExportBgItem_actionPerformed", e);
        }
    }

    /**
     * output ActionHelped class
     */     
    protected class ActionHelped extends ItemAction {     
    
        public ActionHelped()
        {
            this(null);
        }

        public ActionHelped(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionHelped.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHelped.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHelped.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionHelped", "actionHelped_actionPerformed", e);
        }
    }

    /**
     * output ActionCheckSubsidiaryLedger class
     */     
    protected class ActionCheckSubsidiaryLedger extends ItemAction {     
    
        public ActionCheckSubsidiaryLedger()
        {
            this(null);
        }

        public ActionCheckSubsidiaryLedger(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCheckSubsidiaryLedger.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCheckSubsidiaryLedger.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCheckSubsidiaryLedger.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgNFormProcessUI.this, "ActionCheckSubsidiaryLedger", "actionCheckSubsidiaryLedger_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.nbudget.client", "BgNFormProcessUI");
    }




}