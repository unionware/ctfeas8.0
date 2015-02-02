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
public abstract class AbstractBgModifyFormDataUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgModifyFormDataUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgForm;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCleanAll;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgModifyRate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comBgScheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox comBgForm;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField comModifyRate;
    protected ActionSelectAllCostCenter actionSelectAllCostCenter = null;
    protected ActionDisselectAllCostCenter actionDisselectAllCostCenter = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionCancel actionCancel = null;
    /**
     * output class constructor
     */
    public AbstractBgModifyFormDataUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBgModifyFormDataUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSelectAllCostCenter
        this.actionSelectAllCostCenter = new ActionSelectAllCostCenter(this);
        getActionManager().registerAction("actionSelectAllCostCenter", actionSelectAllCostCenter);
         this.actionSelectAllCostCenter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisselectAllCostCenter
        this.actionDisselectAllCostCenter = new ActionDisselectAllCostCenter(this);
        getActionManager().registerAction("actionDisselectAllCostCenter", actionDisselectAllCostCenter);
         this.actionDisselectAllCostCenter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        this.actionCancel = new ActionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBgScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnSelectAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCleanAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contBgModifyRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comBgScheme = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comBgForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.comModifyRate = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.contBgScheme.setName("contBgScheme");
        this.contBgForm.setName("contBgForm");
        this.kDContainer.setName("kDContainer");
        this.btnSelectAll.setName("btnSelectAll");
        this.btnCleanAll.setName("btnCleanAll");
        this.kDSeparator2.setName("kDSeparator2");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.contBgModifyRate.setName("contBgModifyRate");
        this.comBgScheme.setName("comBgScheme");
        this.comBgForm.setName("comBgForm");
        this.kDPanel.setName("kDPanel");
        this.tblMain.setName("tblMain");
        this.comModifyRate.setName("comModifyRate");
        // CoreUI
        // contBgScheme		
        this.contBgScheme.setBoundLabelText(resHelper.getString("contBgScheme.boundLabelText"));		
        this.contBgScheme.setBoundLabelLength(100);		
        this.contBgScheme.setBoundLabelUnderline(true);
        // contBgForm		
        this.contBgForm.setBoundLabelText(resHelper.getString("contBgForm.boundLabelText"));		
        this.contBgForm.setBoundLabelLength(100);		
        this.contBgForm.setBoundLabelUnderline(true);
        // kDContainer
        // btnSelectAll
        this.btnSelectAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectAllCostCenter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelectAll.setText(resHelper.getString("btnSelectAll.text"));
        // btnCleanAll
        this.btnCleanAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisselectAllCostCenter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCleanAll.setText(resHelper.getString("btnCleanAll.text"));
        // kDSeparator2
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // contBgModifyRate		
        this.contBgModifyRate.setBoundLabelText(resHelper.getString("contBgModifyRate.boundLabelText"));		
        this.contBgModifyRate.setBoundLabelLength(100);		
        this.contBgModifyRate.setBoundLabelUnderline(true);
        // comBgScheme
        this.comBgScheme.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comBgScheme_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comBgForm
        // kDPanel
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"orgUnit\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"isSelect\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"modifyRate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{orgUnit}</t:Cell><t:Cell>$Resource{isSelect}</t:Cell><t:Cell>$Resource{modifyRate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
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
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // comModifyRate
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
        this.setBounds(new Rectangle(10, 10, 556, 385));
        this.setLayout(null);
        contBgScheme.setBounds(new Rectangle(10, 9, 270, 19));
        this.add(contBgScheme, null);
        contBgForm.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contBgForm, null);
        kDContainer.setBounds(new Rectangle(10, 89, 533, 258));
        this.add(kDContainer, null);
        btnSelectAll.setBounds(new Rectangle(391, 62, 66, 19));
        this.add(btnSelectAll, null);
        btnCleanAll.setBounds(new Rectangle(470, 62, 66, 19));
        this.add(btnCleanAll, null);
        kDSeparator2.setBounds(new Rectangle(8, 353, 540, 10));
        this.add(kDSeparator2, null);
        btnConfirm.setBounds(new Rectangle(372, 358, 73, 21));
        this.add(btnConfirm, null);
        btnCancel.setBounds(new Rectangle(462, 358, 73, 21));
        this.add(btnCancel, null);
        contBgModifyRate.setBounds(new Rectangle(10, 62, 270, 19));
        this.add(contBgModifyRate, null);
        //contBgScheme
        contBgScheme.setBoundEditor(comBgScheme);
        //contBgForm
        contBgForm.setBoundEditor(comBgForm);
        //kDContainer
        kDContainer.getContentPane().setLayout(null);        kDPanel.setBounds(new Rectangle(5, 5, 521, 240));
        kDContainer.getContentPane().add(kDPanel, null);
        //kDPanel
        kDPanel.setLayout(null);        tblMain.setBounds(new Rectangle(0, 0, 520, 233));
        kDPanel.add(tblMain, null);
        //contBgModifyRate
        contBgModifyRate.setBoundEditor(comModifyRate);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
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
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.budget.app.BgModifyFormDataUIHandler";
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
     * output comBgScheme_itemStateChanged method
     */
    protected void comBgScheme_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblMain_editStarting method
     */
    protected void tblMain_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSelectAllCostCenter_actionPerformed method
     */
    public void actionSelectAllCostCenter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisselectAllCostCenter_actionPerformed method
     */
    public void actionDisselectAllCostCenter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSelectAllCostCenter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelectAllCostCenter() {
    	return false;
    }
	public RequestContext prepareActionDisselectAllCostCenter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisselectAllCostCenter() {
    	return false;
    }
	public RequestContext prepareActionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirm() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }

    /**
     * output ActionSelectAllCostCenter class
     */     
    protected class ActionSelectAllCostCenter extends ItemAction {     
    
        public ActionSelectAllCostCenter()
        {
            this(null);
        }

        public ActionSelectAllCostCenter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectAllCostCenter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAllCostCenter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAllCostCenter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgModifyFormDataUI.this, "ActionSelectAllCostCenter", "actionSelectAllCostCenter_actionPerformed", e);
        }
    }

    /**
     * output ActionDisselectAllCostCenter class
     */     
    protected class ActionDisselectAllCostCenter extends ItemAction {     
    
        public ActionDisselectAllCostCenter()
        {
            this(null);
        }

        public ActionDisselectAllCostCenter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisselectAllCostCenter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisselectAllCostCenter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisselectAllCostCenter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgModifyFormDataUI.this, "ActionDisselectAllCostCenter", "actionDisselectAllCostCenter_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirm class
     */     
    protected class ActionConfirm extends ItemAction {     
    
        public ActionConfirm()
        {
            this(null);
        }

        public ActionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgModifyFormDataUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionCancel class
     */     
    protected class ActionCancel extends ItemAction {     
    
        public ActionCancel()
        {
            this(null);
        }

        public ActionCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgModifyFormDataUI.this, "ActionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.budget.client", "BgModifyFormDataUI");
    }




}