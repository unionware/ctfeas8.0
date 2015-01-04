/**
 * output package name
 */
package com.kingdee.eas.basedata.org.client;

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
public abstract class AbstractPositionEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPositionEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel labDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateEffectDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateValiDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptJob;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAdminOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox btnDefaultPosition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea kDBizMultiLangArea1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labAdminOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labEffectDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labJob;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labValiDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptSuperPosition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labSuperPosition;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labPositionLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPositionLevel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPersonList;
    protected com.kingdee.eas.basedata.org.PositionInfo editData = null;
    protected EntityViewInfo positionHierarchy = null;
    protected IMetaDataPK positionHierarchyPK;
    protected EntityViewInfo positionOfPerson = null;
    protected IMetaDataPK positionOfPersonPK;
    /**
     * output class constructor
     */
    public AbstractPositionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPositionEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        positionHierarchyPK = new MetaDataPK("com.kingdee.eas.basedata.org.app", "PositionHierarchy");
        positionOfPersonPK = new MetaDataPK("com.kingdee.eas.basedata.org.app", "PositionOfPerson");
        this.labDescription = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.dateEffectDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateValiDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptJob = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptAdminOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnDefaultPosition = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDBizMultiLangArea1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.labNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labAdminOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labEffectDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labJob = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labValiDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizPromptSuperPosition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.labSuperPosition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.labPositionLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPositionLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblPersonList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.labDescription.setName("labDescription");
        this.dateEffectDate.setName("dateEffectDate");
        this.dateValiDate.setName("dateValiDate");
        this.bizPromptJob.setName("bizPromptJob");
        this.bizPromptAdminOrgUnit.setName("bizPromptAdminOrgUnit");
        this.btnDefaultPosition.setName("btnDefaultPosition");
        this.txtName.setName("txtName");
        this.kDBizMultiLangArea1.setName("kDBizMultiLangArea1");
        this.labNumber.setName("labNumber");
        this.labName.setName("labName");
        this.labAdminOrgUnit.setName("labAdminOrgUnit");
        this.labEffectDate.setName("labEffectDate");
        this.labJob.setName("labJob");
        this.labValiDate.setName("labValiDate");
        this.bizPromptSuperPosition.setName("bizPromptSuperPosition");
        this.labSuperPosition.setName("labSuperPosition");
        this.kDContainer1.setName("kDContainer1");
        this.txtNumber.setName("txtNumber");
        this.labPositionLevel.setName("labPositionLevel");
        this.prmtPositionLevel.setName("prmtPositionLevel");
        this.tblPersonList.setName("tblPersonList");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);
        // labDescription		
        this.labDescription.setText(resHelper.getString("labDescription.text"));
        // dateEffectDate
        // dateValiDate
        // bizPromptJob		
        this.bizPromptJob.setEditable(true);
        // bizPromptAdminOrgUnit		
        this.bizPromptAdminOrgUnit.setEditable(true);		
        this.bizPromptAdminOrgUnit.setRequired(true);
        // btnDefaultPosition		
        this.btnDefaultPosition.setText(resHelper.getString("btnDefaultPosition.text"));
        this.btnDefaultPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDefaultPosition_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // kDBizMultiLangArea1		
        this.kDBizMultiLangArea1.setMaxLength(255);
        // labNumber		
        this.labNumber.setBoundLabelText(resHelper.getString("labNumber.boundLabelText"));		
        this.labNumber.setBoundLabelUnderline(true);
        // labName		
        this.labName.setBoundLabelText(resHelper.getString("labName.boundLabelText"));		
        this.labName.setBoundLabelUnderline(true);
        // labAdminOrgUnit		
        this.labAdminOrgUnit.setBoundLabelText(resHelper.getString("labAdminOrgUnit.boundLabelText"));		
        this.labAdminOrgUnit.setBoundLabelUnderline(true);
        // labEffectDate		
        this.labEffectDate.setBoundLabelText(resHelper.getString("labEffectDate.boundLabelText"));		
        this.labEffectDate.setBoundLabelUnderline(true);
        // labJob		
        this.labJob.setBoundLabelText(resHelper.getString("labJob.boundLabelText"));		
        this.labJob.setBoundLabelUnderline(true);
        // labValiDate		
        this.labValiDate.setBoundLabelText(resHelper.getString("labValiDate.boundLabelText"));		
        this.labValiDate.setBoundLabelUnderline(true);
        // bizPromptSuperPosition		
        this.bizPromptSuperPosition.setEditable(true);		
        this.bizPromptSuperPosition.setRequired(true);
        // labSuperPosition		
        this.labSuperPosition.setBoundLabelText(resHelper.getString("labSuperPosition.boundLabelText"));		
        this.labSuperPosition.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitleStyle(2);
        // txtNumber		
        this.txtNumber.setText(resHelper.getString("txtNumber.text"));		
        this.txtNumber.setFilterType(25);		
        this.txtNumber.setValidCharacters("-_./\\&*()+|@#$%?");		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // labPositionLevel		
        this.labPositionLevel.setBoundLabelText(resHelper.getString("labPositionLevel.boundLabelText"));		
        this.labPositionLevel.setBoundLabelUnderline(true);
        // prmtPositionLevel		
        this.prmtPositionLevel.setQueryInfo("com.kingdee.eas.cp.bc.app.PositionLevelQuery");		
        this.prmtPositionLevel.setCommitFormat("$number$");		
        this.prmtPositionLevel.setEditFormat("$number$");		
        this.prmtPositionLevel.setDisplayFormat("$name$");
        // tblPersonList
		String tblPersonListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /><t:Column t:key=\"number\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /><t:Column t:key=\"state\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /><t:Column t:key=\"name\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPersonList.setFormatXml(resHelper.translateString("tblPersonList",tblPersonListStrXML));
                this.tblPersonList.putBindContents("positionOfPerson",new String[] {"id","number","PH.isPrimary","name"});

        this.tblPersonList.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                tblPersonList_doRequestRowSet(e);
            }
        });

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
        this.setBounds(new Rectangle(10, 10, 548, 392));
        this.setLayout(null);
        labDescription.setBounds(new Rectangle(8, 117, 70, 19));
        this.add(labDescription, null);
        dateEffectDate.setBounds(new Rectangle(88, 52, 170, 19));
        this.add(dateEffectDate, null);
        dateValiDate.setBounds(new Rectangle(370, 52, 170, 19));
        this.add(dateValiDate, null);
        bizPromptJob.setBounds(new Rectangle(370, 30, 170, 19));
        this.add(bizPromptJob, null);
        bizPromptAdminOrgUnit.setBounds(new Rectangle(88, 30, 170, 19));
        this.add(bizPromptAdminOrgUnit, null);
        btnDefaultPosition.setBounds(new Rectangle(8, 96, 140, 19));
        this.add(btnDefaultPosition, null);
        txtName.setBounds(new Rectangle(370, 8, 170, 19));
        this.add(txtName, null);
        kDBizMultiLangArea1.setBounds(new Rectangle(8, 139, 532, 53));
        this.add(kDBizMultiLangArea1, null);
        labNumber.setBounds(new Rectangle(8, 8, 80, 19));
        this.add(labNumber, null);
        labName.setBounds(new Rectangle(290, 8, 80, 19));
        this.add(labName, null);
        labAdminOrgUnit.setBounds(new Rectangle(8, 30, 80, 19));
        this.add(labAdminOrgUnit, null);
        labEffectDate.setBounds(new Rectangle(8, 52, 80, 19));
        this.add(labEffectDate, null);
        labJob.setBounds(new Rectangle(290, 30, 80, 19));
        this.add(labJob, null);
        labValiDate.setBounds(new Rectangle(290, 52, 80, 19));
        this.add(labValiDate, null);
        bizPromptSuperPosition.setBounds(new Rectangle(88, 74, 170, 19));
        this.add(bizPromptSuperPosition, null);
        labSuperPosition.setBounds(new Rectangle(8, 74, 80, 19));
        this.add(labSuperPosition, null);
        kDContainer1.setBounds(new Rectangle(8, 195, 532, 196));
        this.add(kDContainer1, null);
        txtNumber.setBounds(new Rectangle(88, 8, 170, 19));
        this.add(txtNumber, null);
        labPositionLevel.setBounds(new Rectangle(288, 76, 80, 19));
        this.add(labPositionLevel, null);
        prmtPositionLevel.setBounds(new Rectangle(370, 76, 170, 19));
        this.add(prmtPositionLevel, null);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        tblPersonList.setBounds(new Rectangle(0, 0, 532, 179));
        kDContainer1.getContentPane().add(tblPersonList, null);

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
		dataBinder.registerBinding("effectDate", java.util.Date.class, this.dateEffectDate, "value");
		dataBinder.registerBinding("valiDate", java.util.Date.class, this.dateValiDate, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.kDBizMultiLangArea1, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "stringValue");
		dataBinder.registerBinding("positionLevel", com.kingdee.eas.cp.bc.PositionLevelInfo.class, this.prmtPositionLevel, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.basedata.org.app.PositionEditUIHandler";
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
        this.editData = (com.kingdee.eas.basedata.org.PositionInfo)ov;
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("positionHierarchy")) {
            this.positionHierarchy = (EntityViewInfo)dataObject;
		}
        else if (key.equalsIgnoreCase("positionOfPerson")) {
            this.positionOfPerson = (EntityViewInfo)dataObject;
		}
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
		getValidateHelper().registerBindProperty("effectDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valiDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("positionLevel", ValidateHelper.ON_SAVE);    		
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
     * output btnDefaultPosition_actionPerformed method
     */
    protected void btnDefaultPosition_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblPersonList_doRequestRowSet method
     */
    protected void tblPersonList_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.positionOfPerson != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.positionOfPersonPK, this.positionOfPerson);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
    }


    /**
     * output getQueryExecutor method
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo)
    {
        IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK);
        exec.setObjectView(viewInfo);
        return exec;
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
        sic.add(new SelectorItemInfo("effectDate"));
        sic.add(new SelectorItemInfo("valiDate"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("positionLevel.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("positionLevel.id"));
        	sic.add(new SelectorItemInfo("positionLevel.number"));
        	sic.add(new SelectorItemInfo("positionLevel.name"));
		}
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.basedata.org.client", "PositionEditUI");
    }




}