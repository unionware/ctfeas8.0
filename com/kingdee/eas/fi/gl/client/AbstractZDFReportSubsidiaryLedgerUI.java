/**
 * output package name
 */
package com.kingdee.eas.fi.gl.client;

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
public abstract class AbstractZDFReportSubsidiaryLedgerUI extends com.kingdee.eas.fi.gl.client.ReportBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractZDFReportSubsidiaryLedgerUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane splpMain;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblAccount;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree tree;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssist;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVoucher;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnGeneralLedger;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrintAccountContents;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrintParame;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssist;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVoucher;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemGeneralLedger;
    protected ActionAssist actionAssist = null;
    protected ActionVoucher actionVoucher = null;
    protected ActionGeneralLedger actionGeneralLedger = null;
    protected ActionPrintParame actionPrintParame = null;
    protected ActionMenuItemPrintAccountContents actionMenuItemPrintAccountContents = null;
    /**
     * output class constructor
     */
    public AbstractZDFReportSubsidiaryLedgerUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractZDFReportSubsidiaryLedgerUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRefresh
        String _tempStr = null;
        actionRefresh.setEnabled(true);
        actionRefresh.setDaemonRun(false);

        actionRefresh.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
        _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
        actionRefresh.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
        actionRefresh.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.NAME");
        actionRefresh.putValue(ItemAction.NAME, _tempStr);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionFilter
        actionFilter.setEnabled(true);
        actionFilter.setDaemonRun(false);

        actionFilter.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Q"));
        _tempStr = resHelper.getString("ActionFilter.SHORT_DESCRIPTION");
        actionFilter.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFilter.LONG_DESCRIPTION");
        actionFilter.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFilter.NAME");
        actionFilter.putValue(ItemAction.NAME, _tempStr);
         this.actionFilter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFilter.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFilter.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
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

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAssist
        this.actionAssist = new ActionAssist(this);
        getActionManager().registerAction("actionAssist", actionAssist);
         this.actionAssist.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAssist.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAssist.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionVoucher
        this.actionVoucher = new ActionVoucher(this);
        getActionManager().registerAction("actionVoucher", actionVoucher);
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionGeneralLedger
        this.actionGeneralLedger = new ActionGeneralLedger(this);
        getActionManager().registerAction("actionGeneralLedger", actionGeneralLedger);
         this.actionGeneralLedger.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionGeneralLedger.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionGeneralLedger.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintParame
        this.actionPrintParame = new ActionPrintParame(this);
        getActionManager().registerAction("actionPrintParame", actionPrintParame);
         this.actionPrintParame.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMenuItemPrintAccountContents
        this.actionMenuItemPrintAccountContents = new ActionMenuItemPrintAccountContents(this);
        getActionManager().registerAction("actionMenuItemPrintAccountContents", actionMenuItemPrintAccountContents);
         this.actionMenuItemPrintAccountContents.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.splpMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.lblAccount = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.tree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnAssist = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVoucher = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnGeneralLedger = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemPrintAccountContents = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrintParame = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemAssist = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemVoucher = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemGeneralLedger = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.splpMain.setName("splpMain");
        this.lblAccount.setName("lblAccount");
        this.treeView.setName("treeView");
        this.tree.setName("tree");
        this.btnAssist.setName("btnAssist");
        this.btnVoucher.setName("btnVoucher");
        this.btnGeneralLedger.setName("btnGeneralLedger");
        this.menuItemPrintAccountContents.setName("menuItemPrintAccountContents");
        this.menuItemPrintParame.setName("menuItemPrintParame");
        this.kDSeparator2.setName("kDSeparator2");
        this.menuItemAssist.setName("menuItemAssist");
        this.menuItemVoucher.setName("menuItemVoucher");
        this.menuItemGeneralLedger.setName("menuItemGeneralLedger");
        // CoreUI		
        this.setPreferredSize(new Dimension(1016,600));		
        this.menuItemPageSetup.setText(resHelper.getString("menuItemPageSetup.text"));		
        this.menuItemPageSetup.setMnemonic(76);
        this.tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        
		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setMnemonic(80);		
        this.menuItemPrintView.setText(resHelper.getString("menuItemPrintView.text"));		
        this.menuItemPrintView.setMnemonic(86);		
        this.menuItemFilter.setText(resHelper.getString("menuItemFilter.text"));		
        this.menuItemFilter.setMnemonic(81);		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setMnemonic(82);
        // splpMain
        // lblAccount		
        this.lblAccount.setText(resHelper.getString("lblAccount.text"));
        // treeView		
        this.treeView.setShowButton(false);		
        this.treeView.setShowControlPanel(false);
        // tree
        this.tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    tree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAssist
        this.btnAssist.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssist, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssist.setText(resHelper.getString("btnAssist.text"));		
        this.btnAssist.setToolTipText(resHelper.getString("btnAssist.toolTipText"));		
        this.btnAssist.setVisible(false);
        // btnVoucher
        this.btnVoucher.setAction((IItemAction)ActionProxyFactory.getProxy(actionVoucher, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVoucher.setText(resHelper.getString("btnVoucher.text"));		
        this.btnVoucher.setToolTipText(resHelper.getString("btnVoucher.toolTipText"));		
        this.btnVoucher.setVisible(false);
        // btnGeneralLedger
        this.btnGeneralLedger.setAction((IItemAction)ActionProxyFactory.getProxy(actionGeneralLedger, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnGeneralLedger.setText(resHelper.getString("btnGeneralLedger.text"));		
        this.btnGeneralLedger.setToolTipText(resHelper.getString("btnGeneralLedger.toolTipText"));		
        this.btnGeneralLedger.setVisible(false);
        // menuItemPrintAccountContents
        this.menuItemPrintAccountContents.setAction((IItemAction)ActionProxyFactory.getProxy(actionMenuItemPrintAccountContents, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintAccountContents.setText(resHelper.getString("menuItemPrintAccountContents.text"));
        // menuItemPrintParame
        this.menuItemPrintParame.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintParame, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintParame.setText(resHelper.getString("menuItemPrintParame.text"));		
        this.menuItemPrintParame.setMnemonic(84);		
        this.menuItemPrintParame.setToolTipText(resHelper.getString("menuItemPrintParame.toolTipText"));
        // kDSeparator2
        // menuItemAssist
        this.menuItemAssist.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssist, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssist.setText(resHelper.getString("menuItemAssist.text"));		
        this.menuItemAssist.setMnemonic(76);		
        this.menuItemAssist.setVisible(false);
        // menuItemVoucher
        this.menuItemVoucher.setAction((IItemAction)ActionProxyFactory.getProxy(actionVoucher, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemVoucher.setText(resHelper.getString("menuItemVoucher.text"));		
        this.menuItemVoucher.setMnemonic(86);		
        this.menuItemVoucher.setVisible(false);
        // menuItemGeneralLedger
        this.menuItemGeneralLedger.setAction((IItemAction)ActionProxyFactory.getProxy(actionGeneralLedger, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemGeneralLedger.setText(resHelper.getString("menuItemGeneralLedger.text"));		
        this.menuItemGeneralLedger.setMnemonic(65);		
        this.menuItemGeneralLedger.setVisible(false);
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        lblPeriod.setBounds(new Rectangle(346, 10, 320, 19));
        this.add(lblPeriod, new KDLayout.Constraints(346, 10, 320, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblCurrency.setBounds(new Rectangle(684, 10, 320, 19));
        this.add(lblCurrency, new KDLayout.Constraints(684, 10, 320, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        splpMain.setBounds(new Rectangle(10, 35, 996, 555));
        this.add(splpMain, new KDLayout.Constraints(10, 35, 996, 555, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        lblAccount.setBounds(new Rectangle(10, 10, 320, 19));
        this.add(lblAccount, new KDLayout.Constraints(10, 10, 320, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //splpMain
        splpMain.add(tblMain, "right");
        splpMain.add(treeView, "left");
        //treeView
        treeView.setTree(tree);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuView);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemPrintView);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemPrintAccountContents);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuItemPrintParame);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuView
        menuView.add(menuItemFilter);
        menuView.add(menuItemRefresh);
        menuView.add(kDSeparator2);
        menuView.add(menuItemAssist);
        menuView.add(menuItemVoucher);
        menuView.add(menuItemGeneralLedger);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnFilter);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(sptOperation);
        this.toolBar.add(btnAssist);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnGeneralLedger);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fi.gl.app.ZDFReportSubsidiaryLedgerUIHandler";
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tree_valueChanged method
     */
    protected void tree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }
    	

    /**
     * output actionFilter_actionPerformed method
     */
    public void actionFilter_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFilter_actionPerformed(e);
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
     * output actionAssist_actionPerformed method
     */
    public void actionAssist_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVoucher_actionPerformed method
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionGeneralLedger_actionPerformed method
     */
    public void actionGeneralLedger_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintParame_actionPerformed method
     */
    public void actionPrintParame_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMenuItemPrintAccountContents_actionPerformed method
     */
    public void actionMenuItemPrintAccountContents_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRefresh(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }
	public RequestContext prepareActionFilter(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionFilter(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFilter() {
    	return false;
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
	public RequestContext prepareActionAssist(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssist() {
    	return false;
    }
	public RequestContext prepareActionVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVoucher() {
    	return false;
    }
	public RequestContext prepareActionGeneralLedger(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGeneralLedger() {
    	return false;
    }
	public RequestContext prepareActionPrintParame(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintParame() {
    	return false;
    }
	public RequestContext prepareActionMenuItemPrintAccountContents(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMenuItemPrintAccountContents() {
    	return false;
    }

    /**
     * output ActionAssist class
     */     
    protected class ActionAssist extends ItemAction {     
    
        public ActionAssist()
        {
            this(null);
        }

        public ActionAssist(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAssist.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssist.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssist.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractZDFReportSubsidiaryLedgerUI.this, "ActionAssist", "actionAssist_actionPerformed", e);
        }
    }

    /**
     * output ActionVoucher class
     */     
    protected class ActionVoucher extends ItemAction {     
    
        public ActionVoucher()
        {
            this(null);
        }

        public ActionVoucher(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionVoucher.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVoucher.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVoucher.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractZDFReportSubsidiaryLedgerUI.this, "ActionVoucher", "actionVoucher_actionPerformed", e);
        }
    }

    /**
     * output ActionGeneralLedger class
     */     
    protected class ActionGeneralLedger extends ItemAction {     
    
        public ActionGeneralLedger()
        {
            this(null);
        }

        public ActionGeneralLedger(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionGeneralLedger.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGeneralLedger.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGeneralLedger.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractZDFReportSubsidiaryLedgerUI.this, "ActionGeneralLedger", "actionGeneralLedger_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintParame class
     */     
    protected class ActionPrintParame extends ItemAction {     
    
        public ActionPrintParame()
        {
            this(null);
        }

        public ActionPrintParame(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrintParame.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintParame.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintParame.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractZDFReportSubsidiaryLedgerUI.this, "ActionPrintParame", "actionPrintParame_actionPerformed", e);
        }
    }

    /**
     * output ActionMenuItemPrintAccountContents class
     */     
    protected class ActionMenuItemPrintAccountContents extends ItemAction {     
    
        public ActionMenuItemPrintAccountContents()
        {
            this(null);
        }

        public ActionMenuItemPrintAccountContents(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMenuItemPrintAccountContents.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMenuItemPrintAccountContents.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMenuItemPrintAccountContents.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractZDFReportSubsidiaryLedgerUI.this, "ActionMenuItemPrintAccountContents", "actionMenuItemPrintAccountContents_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fi.gl.client", "ZDFReportSubsidiaryLedgerUI");
    }




}