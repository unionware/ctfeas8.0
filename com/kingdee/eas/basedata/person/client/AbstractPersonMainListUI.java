/**
 * output package name
 */
package com.kingdee.eas.basedata.person.client;

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
public abstract class AbstractPersonMainListUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPersonMainListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlTable;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlOption;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIncludeChild;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkShowDisplayName;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportBankInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportPerson;
    protected ActionImportPerson actionImportPerson = null;
    protected Action_Input action_Input = null;
    protected ActionShowDiaplayName actionShowDiaplayName = null;
    protected ActionImportBankInfo actionImportBankInfo = null;
    /**
     * output class constructor
     */
    public AbstractPersonMainListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPersonMainListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.basedata.person.app", "PersonQuery");
        //actionHelp
        String _tempStr = null;
        actionHelp.setEnabled(true);
        actionHelp.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionHelp.SHORT_DESCRIPTION");
        actionHelp.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionHelp.LONG_DESCRIPTION");
        actionHelp.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionHelp.NAME");
        actionHelp.putValue(ItemAction.NAME, _tempStr);
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionHelp.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        actionAddNew.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_N));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionView
        actionView.setEnabled(true);
        actionView.setDaemonRun(false);

        actionView.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
        _tempStr = resHelper.getString("ActionView.SHORT_DESCRIPTION");
        actionView.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.LONG_DESCRIPTION");
        actionView.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionView.NAME");
        actionView.putValue(ItemAction.NAME, _tempStr);
         this.actionView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionView.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionEdit
        actionEdit.setEnabled(true);
        actionEdit.setDaemonRun(false);

        actionEdit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemove
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRefresh
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
        //actionImportPerson
        this.actionImportPerson = new ActionImportPerson(this);
        getActionManager().registerAction("actionImportPerson", actionImportPerson);
         this.actionImportPerson.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionImportPerson.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionImportPerson.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //action_Input
        this.action_Input = new Action_Input(this);
        getActionManager().registerAction("action_Input", action_Input);
         this.action_Input.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.action_Input.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.action_Input.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionShowDiaplayName
        this.actionShowDiaplayName = new ActionShowDiaplayName(this);
        getActionManager().registerAction("actionShowDiaplayName", actionShowDiaplayName);
         this.actionShowDiaplayName.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportBankInfo
        this.actionImportBankInfo = new ActionImportBankInfo(this);
        getActionManager().registerAction("actionImportBankInfo", actionImportBankInfo);
         this.actionImportBankInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlTable = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlOption = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chkIncludeChild = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkShowDisplayName = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnImportPerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportBankInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuImportPerson = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlTable.setName("pnlTable");
        this.pnlOption.setName("pnlOption");
        this.chkIncludeChild.setName("chkIncludeChild");
        this.chkShowDisplayName.setName("chkShowDisplayName");
        this.separator1.setName("separator1");
        this.btnImportPerson.setName("btnImportPerson");
        this.btnImportBankInfo.setName("btnImportBankInfo");
        this.menuImportPerson.setName("menuImportPerson");
        // CoreUI		
        this.setPreferredSize(new Dimension(1016,600));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"7\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deletedStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"position\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"false\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"adminUnit\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminUnit.displayName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"kaclassfication.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"ÄÚÂë\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{deletedStatus}</t:Cell><t:Cell>$Resource{position}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{adminUnit}</t:Cell><t:Cell>$Resource{adminUnit.displayName}</t:Cell><t:Cell>$Resource{kaclassfication.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","deletedStatus","Position.name","id","AdminOrgUnit.name","AdminOrgUnit.displayName","kaclassfication.name"});

		
        this.btnAddNew.setText(resHelper.getString("btnAddNew.text"));		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemImportData.setText(resHelper.getString("menuItemImportData.text"));		
        this.menuItemImportData.setToolTipText(resHelper.getString("menuItemImportData.toolTipText"));		
        this.menuItemImportData.setVisible(true);		
        this.menuItemExportData.setText(resHelper.getString("menuItemExportData.text"));		
        this.menuItemExportData.setVisible(true);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setVisible(true);		
        this.separatorFW1.setVisible(false);		
        this.treeView.setShowControlPanel(true);		
        this.treeView.setTitle(resHelper.getString("treeView.title"));		
        this.treeView.setShowButton(false);
        // pnlTable
        // pnlOption
        // chkIncludeChild		
        this.chkIncludeChild.setText(resHelper.getString("chkIncludeChild.text"));
        this.chkIncludeChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkInclude_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkShowDisplayName
        this.chkShowDisplayName.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowDiaplayName, new Class[] { IItemAction.class }, getServiceContext()));		
        this.chkShowDisplayName.setText(resHelper.getString("chkShowDisplayName.text"));		
        this.chkShowDisplayName.setToolTipText(resHelper.getString("chkShowDisplayName.toolTipText"));
        // separator1		
        this.separator1.setOrientation(1);
        // btnImportPerson
        this.btnImportPerson.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportPerson, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportPerson.setText(resHelper.getString("btnImportPerson.text"));		
        this.btnImportPerson.setToolTipText(resHelper.getString("btnImportPerson.toolTipText"));
        // btnImportBankInfo
        this.btnImportBankInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportBankInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportBankInfo.setText(resHelper.getString("btnImportBankInfo.text"));		
        this.btnImportBankInfo.setToolTipText(resHelper.getString("btnImportBankInfo.toolTipText"));
        // menuImportPerson
        this.menuImportPerson.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportPerson, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportPerson.setText(resHelper.getString("menuImportPerson.text"));		
        this.menuImportPerson.setToolTipText(resHelper.getString("menuImportPerson.toolTipText"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {treeMain,tblMain,btnAddNew,btnView,btnEdit,btnRemove,btnRefresh,btnPrint,btnPrintPreview,btnImportPerson}));
        this.setFocusCycleRoot(true);
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 585));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 585, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(pnlTable, "right");
        //treeView
        treeView.setTree(treeMain);
        //pnlTable
pnlTable.setLayout(new BorderLayout(0, 0));        pnlTable.add(tblMain, BorderLayout.CENTER);
        pnlTable.add(pnlOption, BorderLayout.NORTH);
        //pnlOption
pnlOption.setLayout(new BorderLayout(0, 0));        pnlOption.add(chkIncludeChild, BorderLayout.WEST);
        pnlOption.add(chkShowDisplayName, BorderLayout.CENTER);

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
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemPrint);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuImportPerson);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separator1);
        this.toolBar.add(btnImportPerson);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnImportBankInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.basedata.person.app.PersonMainListUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.treeMain.requestFocusInWindow();
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
     * output chkInclude_actionPerformed method
     */
    protected void chkInclude_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("deletedStatus"));
        sic.add(new SelectorItemInfo("Position.name"));
        sic.add(new SelectorItemInfo("AdminOrgUnit.name"));
        sic.add(new SelectorItemInfo("AdminOrgUnit.displayName"));
        sic.add(new SelectorItemInfo("kaclassfication.name"));
        return sic;
    }        
    	

    /**
     * output actionHelp_actionPerformed method
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionView_actionPerformed method
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }
    	

    /**
     * output actionImportPerson_actionPerformed method
     */
    public void actionImportPerson_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output action_Input_actionPerformed method
     */
    public void action_Input_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowDiaplayName_actionPerformed method
     */
    public void actionShowDiaplayName_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportBankInfo_actionPerformed method
     */
    public void actionImportBankInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionHelp(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionHelp(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHelp() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionView(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionView() {
    	return false;
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionEdit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
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
	public RequestContext prepareActionImportPerson(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportPerson() {
    	return false;
    }
	public RequestContext prepareAction_Input(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAction_Input() {
    	return false;
    }
	public RequestContext prepareActionShowDiaplayName(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowDiaplayName() {
    	return false;
    }
	public RequestContext prepareActionImportBankInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportBankInfo() {
    	return false;
    }

    /**
     * output ActionImportPerson class
     */     
    protected class ActionImportPerson extends ItemAction {     
    
        public ActionImportPerson()
        {
            this(null);
        }

        public ActionImportPerson(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportPerson.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportPerson.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportPerson.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPersonMainListUI.this, "ActionImportPerson", "actionImportPerson_actionPerformed", e);
        }
    }

    /**
     * output Action_Input class
     */     
    protected class Action_Input extends ItemAction {     
    
        public Action_Input()
        {
            this(null);
        }

        public Action_Input(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("Action_Input.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_Input.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_Input.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPersonMainListUI.this, "Action_Input", "action_Input_actionPerformed", e);
        }
    }

    /**
     * output ActionShowDiaplayName class
     */     
    protected class ActionShowDiaplayName extends ItemAction {     
    
        public ActionShowDiaplayName()
        {
            this(null);
        }

        public ActionShowDiaplayName(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowDiaplayName.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowDiaplayName.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowDiaplayName.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPersonMainListUI.this, "ActionShowDiaplayName", "actionShowDiaplayName_actionPerformed", e);
        }
    }

    /**
     * output ActionImportBankInfo class
     */     
    protected class ActionImportBankInfo extends ItemAction {     
    
        public ActionImportBankInfo()
        {
            this(null);
        }

        public ActionImportBankInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportBankInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportBankInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportBankInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPersonMainListUI.this, "ActionImportBankInfo", "actionImportBankInfo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.basedata.person.client", "PersonMainListUI");
    }




}