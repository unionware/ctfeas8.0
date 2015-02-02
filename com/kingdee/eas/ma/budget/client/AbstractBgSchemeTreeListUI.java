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
public abstract class AbstractBgSchemeTreeListUI extends com.kingdee.eas.ma.budget.client.BgOrgBasedListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgSchemeTreeListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVerifyCtrl;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportData;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportData;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExecute;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiExecute;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSendDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnGetDataBySchemes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataAccredit;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBgSchemePlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreateCtrlInfos;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearCtrlInfos;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCopy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelCopyScheme;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPatchDelCopyForm;
    protected javax.swing.JPopupMenu.Separator separator2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExecute;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiExecute;
    protected javax.swing.JPopupMenu.Separator separator3;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSendDown;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAssign;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDataByScheme;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDataAccredit;
    protected javax.swing.JPopupMenu.Separator separator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBgSchemePlan;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem miCtreateCtrlInfos;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem miClearCtrlInfos;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFiling;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnFiling;
    protected ActionAssign actionAssign = null;
    protected ActionExecute actionExecute = null;
    protected ActionAntiExecute actionAntiExecute = null;
    protected ActionDataAccredit actionDataAccredit = null;
    protected ActionGetDataBySchemes actionGetDataBySchemes = null;
    protected ActionBgSchemePlan actionBgSchemePlan = null;
    protected ActionCreateCtrlInfos actionCreateCtrlInfos = null;
    protected ActionClearCtrlInfos actionClearCtrlInfos = null;
    protected ActionVerifyCtrl actionVerifyCtrl = null;
    protected ActionUnAssign actionUnAssign = null;
    protected ActionFiling actionFiling = null;
    protected ActionUnFiling actionUnfiling = null;
    protected ActionCopy actionCopy = null;
    protected ActionDelCopyScheme actionDelCopyScheme = null;
    protected ActionPatchDelCopyForm actionPatchDelCopyForm = null;
    /**
     * output class constructor
     */
    public AbstractBgSchemeTreeListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractBgSchemeTreeListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.ma.budget", "BgSchemeQuery");
        //actionAssign
        this.actionAssign = new ActionAssign(this);
        getActionManager().registerAction("actionAssign", actionAssign);
        this.actionAssign.setBindWorkFlow(true);
        this.actionAssign.setExtendProperty("Mutex", "TestValue1,0");
        this.actionAssign.setExtendProperty("isObjectUpdateLock", "true");
         this.actionAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExecute
        this.actionExecute = new ActionExecute(this);
        getActionManager().registerAction("actionExecute", actionExecute);
         this.actionExecute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiExecute
        this.actionAntiExecute = new ActionAntiExecute(this);
        getActionManager().registerAction("actionAntiExecute", actionAntiExecute);
         this.actionAntiExecute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDataAccredit
        this.actionDataAccredit = new ActionDataAccredit(this);
        getActionManager().registerAction("actionDataAccredit", actionDataAccredit);
         this.actionDataAccredit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionGetDataBySchemes
        this.actionGetDataBySchemes = new ActionGetDataBySchemes(this);
        getActionManager().registerAction("actionGetDataBySchemes", actionGetDataBySchemes);
         this.actionGetDataBySchemes.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBgSchemePlan
        this.actionBgSchemePlan = new ActionBgSchemePlan(this);
        getActionManager().registerAction("actionBgSchemePlan", actionBgSchemePlan);
         this.actionBgSchemePlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreateCtrlInfos
        this.actionCreateCtrlInfos = new ActionCreateCtrlInfos(this);
        getActionManager().registerAction("actionCreateCtrlInfos", actionCreateCtrlInfos);
         this.actionCreateCtrlInfos.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearCtrlInfos
        this.actionClearCtrlInfos = new ActionClearCtrlInfos(this);
        getActionManager().registerAction("actionClearCtrlInfos", actionClearCtrlInfos);
         this.actionClearCtrlInfos.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVerifyCtrl
        this.actionVerifyCtrl = new ActionVerifyCtrl(this);
        getActionManager().registerAction("actionVerifyCtrl", actionVerifyCtrl);
         this.actionVerifyCtrl.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAssign
        this.actionUnAssign = new ActionUnAssign(this);
        getActionManager().registerAction("actionUnAssign", actionUnAssign);
         this.actionUnAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFiling
        this.actionFiling = new ActionFiling(this);
        getActionManager().registerAction("actionFiling", actionFiling);
         this.actionFiling.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnfiling
        this.actionUnfiling = new ActionUnFiling(this);
        getActionManager().registerAction("actionUnfiling", actionUnfiling);
         this.actionUnfiling.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCopy
        this.actionCopy = new ActionCopy(this);
        getActionManager().registerAction("actionCopy", actionCopy);
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelCopyScheme
        this.actionDelCopyScheme = new ActionDelCopyScheme(this);
        getActionManager().registerAction("actionDelCopyScheme", actionDelCopyScheme);
         this.actionDelCopyScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPatchDelCopyForm
        this.actionPatchDelCopyForm = new ActionPatchDelCopyForm(this);
        getActionManager().registerAction("actionPatchDelCopyForm", actionPatchDelCopyForm);
         this.actionPatchDelCopyForm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnVerifyCtrl = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportData = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportData = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExecute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiExecute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSendDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnGetDataBySchemes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataAccredit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnBgSchemePlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreateCtrlInfos = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClearCtrlInfos = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCopy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelCopyScheme = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPatchDelCopyForm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator2 = new javax.swing.JPopupMenu.Separator();
        this.menuItemExecute = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiExecute = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator3 = new javax.swing.JPopupMenu.Separator();
        this.menuItemSendDown = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAssign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDataByScheme = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDataAccredit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator4 = new javax.swing.JPopupMenu.Separator();
        this.menuItemBgSchemePlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.miCtreateCtrlInfos = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.miClearCtrlInfos = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFiling = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnFiling = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnVerifyCtrl.setName("btnVerifyCtrl");
        this.btnImportData.setName("btnImportData");
        this.btnExportData.setName("btnExportData");
        this.btnExecute.setName("btnExecute");
        this.btnAntiExecute.setName("btnAntiExecute");
        this.btnSendDown.setName("btnSendDown");
        this.btnUnAssign.setName("btnUnAssign");
        this.btnGetDataBySchemes.setName("btnGetDataBySchemes");
        this.btnDataAccredit.setName("btnDataAccredit");
        this.separator1.setName("separator1");
        this.btnBgSchemePlan.setName("btnBgSchemePlan");
        this.btnCreateCtrlInfos.setName("btnCreateCtrlInfos");
        this.btnClearCtrlInfos.setName("btnClearCtrlInfos");
        this.btnCopy.setName("btnCopy");
        this.btnDelCopyScheme.setName("btnDelCopyScheme");
        this.btnPatchDelCopyForm.setName("btnPatchDelCopyForm");
        this.separator2.setName("separator2");
        this.menuItemExecute.setName("menuItemExecute");
        this.menuItemAntiExecute.setName("menuItemAntiExecute");
        this.separator3.setName("separator3");
        this.menuItemSendDown.setName("menuItemSendDown");
        this.menuItemUnAssign.setName("menuItemUnAssign");
        this.menuItemDataByScheme.setName("menuItemDataByScheme");
        this.menuItemDataAccredit.setName("menuItemDataAccredit");
        this.separator4.setName("separator4");
        this.menuItemBgSchemePlan.setName("menuItemBgSchemePlan");
        this.kDSeparator3.setName("kDSeparator3");
        this.miCtreateCtrlInfos.setName("miCtreateCtrlInfos");
        this.miClearCtrlInfos.setName("miClearCtrlInfos");
        this.menuItemFiling.setName("menuItemFiling");
        this.menuItemUnFiling.setName("menuItemUnFiling");
        // CoreUI		
        this.menuItemPageSetup.setEnabled(false);		
        this.kDSeparator1.setEnabled(false);		
        this.kDSeparator1.setVisible(false);		
        this.menuTool.setEnabled(false);		
        this.menuTool.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"orgUnit.name\" t:width=\"125\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFormal\" t:width=\"45\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"period\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dateFrom\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dateTo\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"parentScheme.name\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"orgUnit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"isFiling\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"isCopyScheme\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{isFormal}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{dateFrom}</t:Cell><t:Cell>$Resource{dateTo}</t:Cell><t:Cell>$Resource{parentScheme.name}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{orgUnit.id}</t:Cell><t:Cell>$Resource{isFiling}</t:Cell><t:Cell>$Resource{isCopyScheme}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","orgUnit.name","isFormal","period","dateFrom","dateTo","parentScheme.name","id","orgUnit.id","isFiling","isCopyScheme"});

		
        this.menuItemImportData.setText(resHelper.getString("menuItemImportData.text"));		
        this.menuItemImportData.setToolTipText(resHelper.getString("menuItemImportData.toolTipText"));		
        this.menuItemImportData.setVisible(true);		
        this.menuItemPrint.setEnabled(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setEnabled(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemQuery.setVisible(false);		
        this.menuItemQuery.setEnabled(false);		
        this.menuItemExportData.setText(resHelper.getString("menuItemExportData.text"));		
        this.menuItemExportData.setToolTipText(resHelper.getString("menuItemExportData.toolTipText"));		
        this.menuItemExportData.setVisible(true);		
        this.separatorFile1.setEnabled(false);		
        this.separatorFile1.setVisible(false);		
        this.MenuItemAttachment.setEnabled(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.separatorFW2.setVisible(true);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);		
        this.btnViewFlow.setText(resHelper.getString("btnViewFlow.text"));		
        this.btnViewFlow.setToolTipText(resHelper.getString("btnViewFlow.toolTipText"));		
        this.rbImmOrgUnit.setText(resHelper.getString("rbImmOrgUnit.text"));		
        this.rbAllOrgUnit.setText(resHelper.getString("rbAllOrgUnit.text"));		
        this.rbAllOrgUnit.setSelected(false);
        this.orgUnitGroup.add(this.rbImmOrgUnit);
        this.orgUnitGroup.add(this.rbAllOrgUnit);
        this.orgUnitGroup.add(this.rbCurrOrgUnit);		
        this.rbCurrOrgUnit.setSelected(true);
        // btnVerifyCtrl
        this.btnVerifyCtrl.setAction((IItemAction)ActionProxyFactory.getProxy(actionVerifyCtrl, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVerifyCtrl.setText(resHelper.getString("btnVerifyCtrl.text"));		
        this.btnVerifyCtrl.setToolTipText(resHelper.getString("btnVerifyCtrl.toolTipText"));
        // btnImportData
        this.btnImportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportData.setText(resHelper.getString("btnImportData.text"));		
        this.btnImportData.setToolTipText(resHelper.getString("btnImportData.toolTipText"));
        // btnExportData
        this.btnExportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportData.setText(resHelper.getString("btnExportData.text"));		
        this.btnExportData.setToolTipText(resHelper.getString("btnExportData.toolTipText"));
        // btnExecute
        this.btnExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExecute.setText(resHelper.getString("btnExecute.text"));		
        this.btnExecute.setToolTipText(resHelper.getString("btnExecute.toolTipText"));
        // btnAntiExecute
        this.btnAntiExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiExecute.setText(resHelper.getString("btnAntiExecute.text"));		
        this.btnAntiExecute.setToolTipText(resHelper.getString("btnAntiExecute.toolTipText"));
        // btnSendDown
        this.btnSendDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSendDown.setText(resHelper.getString("btnSendDown.text"));		
        this.btnSendDown.setToolTipText(resHelper.getString("btnSendDown.toolTipText"));
        // btnUnAssign
        this.btnUnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAssign.setText(resHelper.getString("btnUnAssign.text"));		
        this.btnUnAssign.setToolTipText(resHelper.getString("btnUnAssign.toolTipText"));
        // btnGetDataBySchemes
        this.btnGetDataBySchemes.setAction((IItemAction)ActionProxyFactory.getProxy(actionGetDataBySchemes, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnGetDataBySchemes.setText(resHelper.getString("btnGetDataBySchemes.text"));		
        this.btnGetDataBySchemes.setToolTipText(resHelper.getString("btnGetDataBySchemes.toolTipText"));
        // btnDataAccredit
        this.btnDataAccredit.setAction((IItemAction)ActionProxyFactory.getProxy(actionDataAccredit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDataAccredit.setText(resHelper.getString("btnDataAccredit.text"));		
        this.btnDataAccredit.setToolTipText(resHelper.getString("btnDataAccredit.toolTipText"));
        // separator1		
        this.separator1.setOrientation(1);
        // btnBgSchemePlan
        this.btnBgSchemePlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgSchemePlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBgSchemePlan.setText(resHelper.getString("btnBgSchemePlan.text"));		
        this.btnBgSchemePlan.setToolTipText(resHelper.getString("btnBgSchemePlan.toolTipText"));
        // btnCreateCtrlInfos
        this.btnCreateCtrlInfos.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateCtrlInfos, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateCtrlInfos.setText(resHelper.getString("btnCreateCtrlInfos.text"));		
        this.btnCreateCtrlInfos.setToolTipText(resHelper.getString("btnCreateCtrlInfos.toolTipText"));
        // btnClearCtrlInfos
        this.btnClearCtrlInfos.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearCtrlInfos, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearCtrlInfos.setText(resHelper.getString("btnClearCtrlInfos.text"));		
        this.btnClearCtrlInfos.setToolTipText(resHelper.getString("btnClearCtrlInfos.toolTipText"));
        // btnCopy
        this.btnCopy.setAction((IItemAction)ActionProxyFactory.getProxy(actionCopy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCopy.setText(resHelper.getString("btnCopy.text"));		
        this.btnCopy.setToolTipText(resHelper.getString("btnCopy.toolTipText"));
        // btnDelCopyScheme
        this.btnDelCopyScheme.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelCopyScheme, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelCopyScheme.setText(resHelper.getString("btnDelCopyScheme.text"));		
        this.btnDelCopyScheme.setToolTipText(resHelper.getString("btnDelCopyScheme.toolTipText"));
        // btnPatchDelCopyForm
        this.btnPatchDelCopyForm.setAction((IItemAction)ActionProxyFactory.getProxy(actionPatchDelCopyForm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPatchDelCopyForm.setText(resHelper.getString("btnPatchDelCopyForm.text"));		
        this.btnPatchDelCopyForm.setToolTipText(resHelper.getString("btnPatchDelCopyForm.toolTipText"));
        // separator2
        // menuItemExecute
        this.menuItemExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExecute.setText(resHelper.getString("menuItemExecute.text"));		
        this.menuItemExecute.setToolTipText(resHelper.getString("menuItemExecute.toolTipText"));		
        this.menuItemExecute.setMnemonic(69);
        // menuItemAntiExecute
        this.menuItemAntiExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiExecute.setText(resHelper.getString("menuItemAntiExecute.text"));		
        this.menuItemAntiExecute.setToolTipText(resHelper.getString("menuItemAntiExecute.toolTipText"));		
        this.menuItemAntiExecute.setMnemonic(87);
        // separator3
        // menuItemSendDown
        this.menuItemSendDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSendDown.setText(resHelper.getString("menuItemSendDown.text"));		
        this.menuItemSendDown.setToolTipText(resHelper.getString("menuItemSendDown.toolTipText"));		
        this.menuItemSendDown.setMnemonic(65);
        // menuItemUnAssign
        this.menuItemUnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAssign.setText(resHelper.getString("menuItemUnAssign.text"));		
        this.menuItemUnAssign.setMnemonic(85);		
        this.menuItemUnAssign.setToolTipText(resHelper.getString("menuItemUnAssign.toolTipText"));
        // menuItemDataByScheme
        this.menuItemDataByScheme.setAction((IItemAction)ActionProxyFactory.getProxy(actionGetDataBySchemes, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDataByScheme.setText(resHelper.getString("menuItemDataByScheme.text"));		
        this.menuItemDataByScheme.setToolTipText(resHelper.getString("menuItemDataByScheme.toolTipText"));		
        this.menuItemDataByScheme.setMnemonic(68);
        // menuItemDataAccredit
        this.menuItemDataAccredit.setAction((IItemAction)ActionProxyFactory.getProxy(actionDataAccredit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDataAccredit.setText(resHelper.getString("menuItemDataAccredit.text"));		
        this.menuItemDataAccredit.setEnabled(false);		
        this.menuItemDataAccredit.setVisible(false);
        // separator4
        // menuItemBgSchemePlan
        this.menuItemBgSchemePlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionBgSchemePlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBgSchemePlan.setText(resHelper.getString("menuItemBgSchemePlan.text"));		
        this.menuItemBgSchemePlan.setToolTipText(resHelper.getString("menuItemBgSchemePlan.toolTipText"));		
        this.menuItemBgSchemePlan.setMnemonic(80);
        // kDSeparator3
        // miCtreateCtrlInfos
        this.miCtreateCtrlInfos.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreateCtrlInfos, new Class[] { IItemAction.class }, getServiceContext()));		
        this.miCtreateCtrlInfos.setText(resHelper.getString("miCtreateCtrlInfos.text"));
        // miClearCtrlInfos
        this.miClearCtrlInfos.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearCtrlInfos, new Class[] { IItemAction.class }, getServiceContext()));		
        this.miClearCtrlInfos.setText(resHelper.getString("miClearCtrlInfos.text"));
        // menuItemFiling
        this.menuItemFiling.setAction((IItemAction)ActionProxyFactory.getProxy(actionFiling, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFiling.setText(resHelper.getString("menuItemFiling.text"));		
        this.menuItemFiling.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_archive"));
        // menuItemUnFiling
        this.menuItemUnFiling.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnfiling, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnFiling.setText(resHelper.getString("menuItemUnFiling.text"));		
        this.menuItemUnFiling.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unarchive"));
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
        splitPaneMain.setBounds(new Rectangle(10, 37, 996, 582));
        this.add(splitPaneMain, new KDLayout.Constraints(10, 37, 996, 582, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgType.setBounds(new Rectangle(10, 10, 250, 19));
        this.add(contOrgType, new KDLayout.Constraints(10, 10, 250, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        rbImmOrgUnit.setBounds(new Rectangle(400, 10, 100, 19));
        this.add(rbImmOrgUnit, new KDLayout.Constraints(400, 10, 100, 19, 0));
        rbAllOrgUnit.setBounds(new Rectangle(500, 10, 100, 19));
        this.add(rbAllOrgUnit, new KDLayout.Constraints(500, 10, 100, 19, 0));
        rbCurrOrgUnit.setBounds(new Rectangle(300, 10, 100, 19));
        this.add(rbCurrOrgUnit, new KDLayout.Constraints(300, 10, 100, 19, 0));
        //splitPaneMain
        splitPaneMain.add(tblMain, "right");
        splitPaneMain.add(treeViewOrgUnit, "left");
        //treeViewOrgUnit
        treeViewOrgUnit.setTree(treeOrgUnit);
        //contOrgType
        contOrgType.setBoundEditor(comboxOrgType);

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(menuItemQuery);
        menuView.add(separatorView1);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemQueryScheme);
        menuView.add(separator2);
        menuView.add(itemViewFlow);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemExecute);
        menuBiz.add(menuItemAntiExecute);
        menuBiz.add(separator3);
        menuBiz.add(menuItemSendDown);
        menuBiz.add(menuItemUnAssign);
        menuBiz.add(menuItemDataByScheme);
        menuBiz.add(menuItemDataAccredit);
        menuBiz.add(separator4);
        menuBiz.add(menuItemBgSchemePlan);
        menuBiz.add(kDSeparator3);
        menuBiz.add(miCtreateCtrlInfos);
        menuBiz.add(miClearCtrlInfos);
        menuBiz.add(menuItemFiling);
        menuBiz.add(menuItemUnFiling);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnVerifyCtrl);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnView);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnViewFlow);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnImportData);
        this.toolBar.add(btnExportData);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnExecute);
        this.toolBar.add(btnAntiExecute);
        this.toolBar.add(btnSendDown);
        this.toolBar.add(btnUnAssign);
        this.toolBar.add(btnGetDataBySchemes);
        this.toolBar.add(btnDataAccredit);
        this.toolBar.add(separator1);
        this.toolBar.add(btnBgSchemePlan);
        this.toolBar.add(btnCreateCtrlInfos);
        this.toolBar.add(btnClearCtrlInfos);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnDelCopyScheme);
        this.toolBar.add(btnPatchDelCopyForm);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.budget.app.BgSchemeTreeListUIHandler";
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("dateFrom"));
        sic.add(new SelectorItemInfo("dateTo"));
        sic.add(new SelectorItemInfo("isFormal"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("period"));
        sic.add(new SelectorItemInfo("parentScheme.name"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("isFiling"));
        sic.add(new SelectorItemInfo("isCopyScheme"));
        return sic;
    }        
    	

    /**
     * output actionAssign_actionPerformed method
     */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExecute_actionPerformed method
     */
    public void actionExecute_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiExecute_actionPerformed method
     */
    public void actionAntiExecute_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDataAccredit_actionPerformed method
     */
    public void actionDataAccredit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionGetDataBySchemes_actionPerformed method
     */
    public void actionGetDataBySchemes_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBgSchemePlan_actionPerformed method
     */
    public void actionBgSchemePlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCreateCtrlInfos_actionPerformed method
     */
    public void actionCreateCtrlInfos_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearCtrlInfos_actionPerformed method
     */
    public void actionClearCtrlInfos_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVerifyCtrl_actionPerformed method
     */
    public void actionVerifyCtrl_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAssign_actionPerformed method
     */
    public void actionUnAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFiling_actionPerformed method
     */
    public void actionFiling_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnFiling_actionPerformed method
     */
    public void actionUnFiling_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCopy_actionPerformed method
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelCopyScheme_actionPerformed method
     */
    public void actionDelCopyScheme_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPatchDelCopyForm_actionPerformed method
     */
    public void actionPatchDelCopyForm_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssign() {
    	return false;
    }
	public RequestContext prepareActionExecute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExecute() {
    	return false;
    }
	public RequestContext prepareActionAntiExecute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiExecute() {
    	return false;
    }
	public RequestContext prepareActionDataAccredit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDataAccredit() {
    	return false;
    }
	public RequestContext prepareActionGetDataBySchemes(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGetDataBySchemes() {
    	return false;
    }
	public RequestContext prepareActionBgSchemePlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBgSchemePlan() {
    	return false;
    }
	public RequestContext prepareActionCreateCtrlInfos(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateCtrlInfos() {
    	return false;
    }
	public RequestContext prepareActionClearCtrlInfos(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearCtrlInfos() {
    	return false;
    }
	public RequestContext prepareActionVerifyCtrl(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVerifyCtrl() {
    	return false;
    }
	public RequestContext prepareActionUnAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAssign() {
    	return false;
    }
	public RequestContext prepareActionFiling(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFiling() {
    	return false;
    }
	public RequestContext prepareActionUnFiling(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnFiling() {
    	return false;
    }
	public RequestContext prepareActionCopy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCopy() {
    	return false;
    }
	public RequestContext prepareActionDelCopyScheme(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelCopyScheme() {
    	return false;
    }
	public RequestContext prepareActionPatchDelCopyForm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPatchDelCopyForm() {
    	return false;
    }

    /**
     * output ActionAssign class
     */     
    protected class ActionAssign extends ItemAction {     
    
        public ActionAssign()
        {
            this(null);
        }

        public ActionAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift A"));
            _tempStr = resHelper.getString("ActionAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionAssign", "actionAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionExecute class
     */     
    protected class ActionExecute extends ItemAction {     
    
        public ActionExecute()
        {
            this(null);
        }

        public ActionExecute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift E"));
            _tempStr = resHelper.getString("ActionExecute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionExecute", "actionExecute_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiExecute class
     */     
    protected class ActionAntiExecute extends ItemAction {     
    
        public ActionAntiExecute()
        {
            this(null);
        }

        public ActionAntiExecute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift T"));
            _tempStr = resHelper.getString("ActionAntiExecute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiExecute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiExecute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionAntiExecute", "actionAntiExecute_actionPerformed", e);
        }
    }

    /**
     * output ActionDataAccredit class
     */     
    protected class ActionDataAccredit extends ItemAction {     
    
        public ActionDataAccredit()
        {
            this(null);
        }

        public ActionDataAccredit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
            _tempStr = resHelper.getString("ActionDataAccredit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataAccredit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataAccredit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionDataAccredit", "actionDataAccredit_actionPerformed", e);
        }
    }

    /**
     * output ActionGetDataBySchemes class
     */     
    protected class ActionGetDataBySchemes extends ItemAction {     
    
        public ActionGetDataBySchemes()
        {
            this(null);
        }

        public ActionGetDataBySchemes(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionGetDataBySchemes.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGetDataBySchemes.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGetDataBySchemes.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionGetDataBySchemes", "actionGetDataBySchemes_actionPerformed", e);
        }
    }

    /**
     * output ActionBgSchemePlan class
     */     
    protected class ActionBgSchemePlan extends ItemAction {     
    
        public ActionBgSchemePlan()
        {
            this(null);
        }

        public ActionBgSchemePlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBgSchemePlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBgSchemePlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBgSchemePlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionBgSchemePlan", "actionBgSchemePlan_actionPerformed", e);
        }
    }

    /**
     * output ActionCreateCtrlInfos class
     */     
    protected class ActionCreateCtrlInfos extends ItemAction {     
    
        public ActionCreateCtrlInfos()
        {
            this(null);
        }

        public ActionCreateCtrlInfos(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift R"));
            this.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
            _tempStr = resHelper.getString("ActionCreateCtrlInfos.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateCtrlInfos.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreateCtrlInfos.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionCreateCtrlInfos", "actionCreateCtrlInfos_actionPerformed", e);
        }
    }

    /**
     * output ActionClearCtrlInfos class
     */     
    protected class ActionClearCtrlInfos extends ItemAction {     
    
        public ActionClearCtrlInfos()
        {
            this(null);
        }

        public ActionClearCtrlInfos(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift X"));
            this.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
            _tempStr = resHelper.getString("ActionClearCtrlInfos.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearCtrlInfos.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearCtrlInfos.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionClearCtrlInfos", "actionClearCtrlInfos_actionPerformed", e);
        }
    }

    /**
     * output ActionVerifyCtrl class
     */     
    protected class ActionVerifyCtrl extends ItemAction {     
    
        public ActionVerifyCtrl()
        {
            this(null);
        }

        public ActionVerifyCtrl(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionVerifyCtrl.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVerifyCtrl.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVerifyCtrl.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionVerifyCtrl", "actionVerifyCtrl_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAssign class
     */     
    protected class ActionUnAssign extends ItemAction {     
    
        public ActionUnAssign()
        {
            this(null);
        }

        public ActionUnAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionUnAssign", "actionUnAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionFiling class
     */     
    protected class ActionFiling extends ItemAction {     
    
        public ActionFiling()
        {
            this(null);
        }

        public ActionFiling(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFiling.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFiling.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFiling.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionFiling", "actionFiling_actionPerformed", e);
        }
    }

    /**
     * output ActionUnFiling class
     */     
    protected class ActionUnFiling extends ItemAction {     
    
        public ActionUnFiling()
        {
            this(null);
        }

        public ActionUnFiling(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnFiling.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnFiling.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnFiling.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionUnFiling", "actionUnFiling_actionPerformed", e);
        }
    }

    /**
     * output ActionCopy class
     */     
    protected class ActionCopy extends ItemAction {     
    
        public ActionCopy()
        {
            this(null);
        }

        public ActionCopy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCopy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCopy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCopy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionCopy", "actionCopy_actionPerformed", e);
        }
    }

    /**
     * output ActionDelCopyScheme class
     */     
    protected class ActionDelCopyScheme extends ItemAction {     
    
        public ActionDelCopyScheme()
        {
            this(null);
        }

        public ActionDelCopyScheme(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelCopyScheme.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCopyScheme.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelCopyScheme.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionDelCopyScheme", "actionDelCopyScheme_actionPerformed", e);
        }
    }

    /**
     * output ActionPatchDelCopyForm class
     */     
    protected class ActionPatchDelCopyForm extends ItemAction {     
    
        public ActionPatchDelCopyForm()
        {
            this(null);
        }

        public ActionPatchDelCopyForm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPatchDelCopyForm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPatchDelCopyForm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPatchDelCopyForm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgSchemeTreeListUI.this, "ActionPatchDelCopyForm", "actionPatchDelCopyForm_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.budget.client", "BgSchemeTreeListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}