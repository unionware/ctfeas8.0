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
public abstract class AbstractBgDisCountFormAddNewUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgDisCountFormAddNewUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgForm;
    protected com.kingdee.bos.ctrl.swing.KDContainer costCenterContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnQuit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddComment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewComment;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comBgScheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBgForm;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree tblTree;
    protected com.kingdee.eas.ma.budget.BgDisCountFormInfo editData = null;
    protected ActionSelectAllCostCenter actionSelectAllCostCenter = null;
    protected ActionDisselectAllCostCenter actionDisselectAllCostCenter = null;
    protected ActionCmbBgSchemaChanged actionCmbBgSchemaChanged = null;
    protected ActionCmbBgFormChanged actionCmbBgFormChanged = null;
    protected ActionHelpContent actionHelpContent = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionQuit actionQuit = null;
    /**
     * output class constructor
     */
    public AbstractBgDisCountFormAddNewUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBgDisCountFormAddNewUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSelectAllCostCenter
        this.actionSelectAllCostCenter = new ActionSelectAllCostCenter(this);
        getActionManager().registerAction("actionSelectAllCostCenter", actionSelectAllCostCenter);
         this.actionSelectAllCostCenter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisselectAllCostCenter
        this.actionDisselectAllCostCenter = new ActionDisselectAllCostCenter(this);
        getActionManager().registerAction("actionDisselectAllCostCenter", actionDisselectAllCostCenter);
         this.actionDisselectAllCostCenter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCmbBgSchemaChanged
        this.actionCmbBgSchemaChanged = new ActionCmbBgSchemaChanged(this);
        getActionManager().registerAction("actionCmbBgSchemaChanged", actionCmbBgSchemaChanged);
         this.actionCmbBgSchemaChanged.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCmbBgFormChanged
        this.actionCmbBgFormChanged = new ActionCmbBgFormChanged(this);
        getActionManager().registerAction("actionCmbBgFormChanged", actionCmbBgFormChanged);
         this.actionCmbBgFormChanged.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHelpContent
        this.actionHelpContent = new ActionHelpContent(this);
        getActionManager().registerAction("actionHelpContent", actionHelpContent);
         this.actionHelpContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuit
        this.actionQuit = new ActionQuit(this);
        getActionManager().registerAction("actionQuit", actionQuit);
         this.actionQuit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBgScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.costCenterContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnQuit = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnAddComment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewComment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.comBgScheme = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtBgForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.tblTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.contBgScheme.setName("contBgScheme");
        this.contBgForm.setName("contBgForm");
        this.costCenterContainer.setName("costCenterContainer");
        this.btnConfirm.setName("btnConfirm");
        this.btnQuit.setName("btnQuit");
        this.btnAddComment.setName("btnAddComment");
        this.btnViewComment.setName("btnViewComment");
        this.kDSeparator5.setName("kDSeparator5");
        this.comBgScheme.setName("comBgScheme");
        this.prmtBgForm.setName("prmtBgForm");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.tblTree.setName("tblTree");
        // CoreUI		
        this.setPreferredSize(new Dimension(556,435));		
        this.setMinimumSize(new Dimension(556,435));		
        this.setMaximumSize(new Dimension(556,435));
        // contBgScheme		
        this.contBgScheme.setBoundLabelText(resHelper.getString("contBgScheme.boundLabelText"));		
        this.contBgScheme.setBoundLabelLength(84);		
        this.contBgScheme.setBoundLabelUnderline(true);
        // contBgForm		
        this.contBgForm.setBoundLabelText(resHelper.getString("contBgForm.boundLabelText"));		
        this.contBgForm.setBoundLabelLength(84);		
        this.contBgForm.setBoundLabelUnderline(true);
        // costCenterContainer		
        this.costCenterContainer.setTitle(resHelper.getString("costCenterContainer.title"));		
        this.costCenterContainer.setEnableActive(false);
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));		
        this.btnConfirm.setToolTipText(resHelper.getString("btnConfirm.toolTipText"));		
        this.btnConfirm.setVisible(false);
        // btnQuit
        this.btnQuit.setAction((IItemAction)ActionProxyFactory.getProxy(actionExitCurrent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuit.setText(resHelper.getString("btnQuit.text"));		
        this.btnQuit.setToolTipText(resHelper.getString("btnQuit.toolTipText"));		
        this.btnQuit.setVisible(false);
        // btnAddComment		
        this.btnAddComment.setText(resHelper.getString("btnAddComment.text"));
        this.btnAddComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddComment_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnViewComment		
        this.btnViewComment.setText(resHelper.getString("btnViewComment.text"));
        this.btnViewComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewComment_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSeparator5		
        this.kDSeparator5.setVisible(false);
        // comBgScheme		
        this.comBgScheme.setRequired(true);		
        this.comBgScheme.setActionCommand("");
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
        // prmtBgForm		
        this.prmtBgForm.setEditFormat("$number$");		
        this.prmtBgForm.setDisplayFormat("$name$");		
        this.prmtBgForm.setCommitFormat("$number$");		
        this.prmtBgForm.setQueryInfo("com.kingdee.eas.ma.budget.F7BgFormForCollectQuery");		
        this.prmtBgForm.setEditable(true);		
        this.prmtBgForm.setRequired(true);
        this.prmtBgForm.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBgForm_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane1
        // tblTree
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
        this.setBounds(new Rectangle(10, 10, 556, 390));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 556, 390));
        contBgScheme.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contBgScheme, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contBgForm.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contBgForm, new KDLayout.Constraints(10, 32, 270, 19, 0));
        costCenterContainer.setBounds(new Rectangle(10, 60, 536, 324));
        this.add(costCenterContainer, new KDLayout.Constraints(10, 60, 536, 324, 0));
        btnConfirm.setBounds(new Rectangle(393, 404, 73, 21));
        this.add(btnConfirm, new KDLayout.Constraints(393, 404, 73, 21, 0));
        btnQuit.setBounds(new Rectangle(473, 404, 73, 21));
        this.add(btnQuit, new KDLayout.Constraints(473, 404, 73, 21, 0));
        btnAddComment.setBounds(new Rectangle(300, 10, 64, 19));
        this.add(btnAddComment, new KDLayout.Constraints(300, 10, 64, 19, 0));
        btnViewComment.setBounds(new Rectangle(300, 32, 64, 19));
        this.add(btnViewComment, new KDLayout.Constraints(300, 32, 64, 19, 0));
        kDSeparator5.setBounds(new Rectangle(3, 394, 550, 10));
        this.add(kDSeparator5, new KDLayout.Constraints(3, 394, 550, 10, 0));
        //contBgScheme
        contBgScheme.setBoundEditor(comBgScheme);
        //contBgForm
        contBgForm.setBoundEditor(prmtBgForm);
        //costCenterContainer
        costCenterContainer.getContentPane().setLayout(null);        kDScrollPane1.setBounds(new Rectangle(0, 0, 536, 295));
        costCenterContainer.getContentPane().add(kDScrollPane1, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(tblTree, null);

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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.budget.app.BgDisCountFormAddNewUIHandler";
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
     * output btnAddComment_actionPerformed method
     */
    protected void btnAddComment_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnViewComment_actionPerformed method
     */
    protected void btnViewComment_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comBgScheme_itemStateChanged method
     */
    protected void comBgScheme_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtBgForm_dataChanged method
     */
    protected void prmtBgForm_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        return sic;
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
     * output actionCmbBgSchemaChanged_actionPerformed method
     */
    public void actionCmbBgSchemaChanged_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCmbBgFormChanged_actionPerformed method
     */
    public void actionCmbBgFormChanged_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHelpContent_actionPerformed method
     */
    public void actionHelpContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuit_actionPerformed method
     */
    public void actionQuit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCmbBgSchemaChanged(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCmbBgSchemaChanged() {
    	return false;
    }
	public RequestContext prepareActionCmbBgFormChanged(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCmbBgFormChanged() {
    	return false;
    }
	public RequestContext prepareActionHelpContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHelpContent() {
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
	public RequestContext prepareActionQuit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuit() {
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionSelectAllCostCenter", "actionSelectAllCostCenter_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionDisselectAllCostCenter", "actionDisselectAllCostCenter_actionPerformed", e);
        }
    }

    /**
     * output ActionCmbBgSchemaChanged class
     */     
    protected class ActionCmbBgSchemaChanged extends ItemAction {     
    
        public ActionCmbBgSchemaChanged()
        {
            this(null);
        }

        public ActionCmbBgSchemaChanged(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCmbBgSchemaChanged.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCmbBgSchemaChanged.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCmbBgSchemaChanged.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionCmbBgSchemaChanged", "actionCmbBgSchemaChanged_actionPerformed", e);
        }
    }

    /**
     * output ActionCmbBgFormChanged class
     */     
    protected class ActionCmbBgFormChanged extends ItemAction {     
    
        public ActionCmbBgFormChanged()
        {
            this(null);
        }

        public ActionCmbBgFormChanged(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCmbBgFormChanged.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCmbBgFormChanged.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCmbBgFormChanged.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionCmbBgFormChanged", "actionCmbBgFormChanged_actionPerformed", e);
        }
    }

    /**
     * output ActionHelpContent class
     */     
    protected class ActionHelpContent extends ItemAction {     
    
        public ActionHelpContent()
        {
            this(null);
        }

        public ActionHelpContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionHelpContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHelpContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHelpContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionHelpContent", "actionHelpContent_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionQuit class
     */     
    protected class ActionQuit extends ItemAction {     
    
        public ActionQuit()
        {
            this(null);
        }

        public ActionQuit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgDisCountFormAddNewUI.this, "ActionQuit", "actionQuit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.budget.client", "BgDisCountFormAddNewUI");
    }




}