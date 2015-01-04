/**
 * output package name
 */
package com.kingdee.eas.basedata.assistant.client;

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
public abstract class AbstractProjectEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel labDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea kDBizMultiLangArea1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conPrjManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer belongCompanykDLabel;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox IsListItem;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSysCreate;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox kDBizMultiLangBox1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtAddress;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateScheduleStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateSchedulEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateFactStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateFactEndDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtProcess;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttachment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPrjManager;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox belongCompanyBiz;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblProjectManager;
    protected com.kingdee.eas.basedata.assistant.ProjectInfo editData = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionRemoveLine actionRemoveLine = null;
    /**
     * output class constructor
     */
    public AbstractProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLine
        this.actionRemoveLine = new ActionRemoveLine(this);
        getActionManager().registerAction("actionRemoveLine", actionRemoveLine);
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.labDescription = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDBizMultiLangArea1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conPrjManager = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.belongCompanykDLabel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.IsListItem = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsSysCreate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDBizMultiLangBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtAddress = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.dateScheduleStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateSchedulEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateFactStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateFactEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtProcess = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttachment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPrjManager = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.belongCompanyBiz = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblProjectManager = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.labDescription.setName("labDescription");
        this.kDBizMultiLangArea1.setName("kDBizMultiLangArea1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.conPrjManager.setName("conPrjManager");
        this.belongCompanykDLabel.setName("belongCompanykDLabel");
        this.IsListItem.setName("IsListItem");
        this.chkIsSysCreate.setName("chkIsSysCreate");
        this.kDContainer1.setName("kDContainer1");
        this.txtNumber.setName("txtNumber");
        this.kDBizMultiLangBox1.setName("kDBizMultiLangBox1");
        this.txtAddress.setName("txtAddress");
        this.comboType.setName("comboType");
        this.comboStatus.setName("comboStatus");
        this.dateScheduleStartDate.setName("dateScheduleStartDate");
        this.dateSchedulEndDate.setName("dateSchedulEndDate");
        this.dateFactStartDate.setName("dateFactStartDate");
        this.dateFactEndDate.setName("dateFactEndDate");
        this.txtProcess.setName("txtProcess");
        this.txtAttachment.setName("txtAttachment");
        this.prmtPrjManager.setName("prmtPrjManager");
        this.belongCompanyBiz.setName("belongCompanyBiz");
        this.tblProjectManager.setName("tblProjectManager");
        // CoreUI		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);
        // labDescription		
        this.labDescription.setText(resHelper.getString("labDescription.text"));
        // kDBizMultiLangArea1		
        this.kDBizMultiLangArea1.setMaxLength(200);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(120);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(120);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(120);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(120);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(120);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(120);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(120);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(120);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelLength(120);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(120);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(120);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // conPrjManager		
        this.conPrjManager.setBoundLabelText(resHelper.getString("conPrjManager.boundLabelText"));		
        this.conPrjManager.setBoundLabelLength(120);		
        this.conPrjManager.setBoundLabelUnderline(true);
        // belongCompanykDLabel		
        this.belongCompanykDLabel.setBoundLabelText(resHelper.getString("belongCompanykDLabel.boundLabelText"));		
        this.belongCompanykDLabel.setBoundLabelLength(120);		
        this.belongCompanykDLabel.setBoundLabelUnderline(true);
        // IsListItem		
        this.IsListItem.setText(resHelper.getString("IsListItem.text"));
        // chkIsSysCreate		
        this.chkIsSysCreate.setText(resHelper.getString("chkIsSysCreate.text"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setTitleStyle(2);		
        this.kDContainer1.setEnableActive(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // kDBizMultiLangBox1		
        this.kDBizMultiLangBox1.setMaxLength(80);		
        this.kDBizMultiLangBox1.setRequired(true);
        this.kDBizMultiLangBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDBizMultiLangBox1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtAddress		
        this.txtAddress.setPreferredSize(new Dimension(179,19));
        // comboType		
        this.comboType.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.assistant.ProjectTypeEnum").toArray());		
        this.comboType.setRequired(true);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.assistant.ProjectStatus").toArray());		
        this.comboStatus.setRequired(true);
        // dateScheduleStartDate
        // dateSchedulEndDate
        // dateFactStartDate
        // dateFactEndDate
        // txtProcess		
        this.txtProcess.setText(resHelper.getString("txtProcess.text"));
        // txtAttachment		
        this.txtAttachment.setMaxLength(80);
        // prmtPrjManager
        // belongCompanyBiz
        // tblProjectManager
		String tblProjectManagerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectManager\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"managerName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectManager}</t:Cell><t:Cell>$Resource{managerName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblProjectManager.setFormatXml(resHelper.translateString("tblProjectManager",tblProjectManagerStrXML));

                this.tblProjectManager.putBindContents("editData",new String[] {"id","projectManage","managerName"});


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
        this.setBounds(new Rectangle(10, 10, 648, 425));
        this.setLayout(null);
        labDescription.setBounds(new Rectangle(13, 192, 100, 19));
        this.add(labDescription, null);
        kDBizMultiLangArea1.setBounds(new Rectangle(10, 213, 625, 76));
        this.add(kDBizMultiLangArea1, null);
        kDLabelContainer1.setBounds(new Rectangle(13, 10, 300, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(337, 10, 300, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(13, 142, 625, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(337, 33, 300, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(13, 54, 300, 19));
        this.add(kDLabelContainer5, null);
        kDLabelContainer7.setBounds(new Rectangle(13, 98, 300, 19));
        this.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(337, 97, 300, 19));
        this.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(13, 120, 300, 19));
        this.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(337, 120, 300, 19));
        this.add(kDLabelContainer10, null);
        kDLabelContainer11.setBounds(new Rectangle(13, 76, 300, 19));
        this.add(kDLabelContainer11, null);
        kDLabelContainer12.setBounds(new Rectangle(13, 164, 300, 19));
        this.add(kDLabelContainer12, null);
        conPrjManager.setBounds(new Rectangle(337, 56, 300, 19));
        this.add(conPrjManager, null);
        belongCompanykDLabel.setBounds(new Rectangle(13, 32, 300, 19));
        this.add(belongCompanykDLabel, null);
        IsListItem.setBounds(new Rectangle(337, 79, 300, 19));
        this.add(IsListItem, null);
        chkIsSysCreate.setBounds(new Rectangle(337, 164, 220, 19));
        this.add(chkIsSysCreate, null);
        kDContainer1.setBounds(new Rectangle(13, 298, 625, 118));
        this.add(kDContainer1, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDBizMultiLangBox1);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtAddress);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(comboType);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(comboStatus);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(dateScheduleStartDate);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(dateSchedulEndDate);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(dateFactStartDate);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(dateFactEndDate);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtProcess);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtAttachment);
        //conPrjManager
        conPrjManager.setBoundEditor(prmtPrjManager);
        //belongCompanykDLabel
        belongCompanykDLabel.setBoundEditor(belongCompanyBiz);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblProjectManager, BorderLayout.CENTER);

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
		dataBinder.registerBinding("description", String.class, this.kDBizMultiLangArea1, "_multiLangItem");
		dataBinder.registerBinding("isListItem", boolean.class, this.IsListItem, "selected");
		dataBinder.registerBinding("isSysCreate", boolean.class, this.chkIsSysCreate, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.kDBizMultiLangBox1, "_multiLangItem");
		dataBinder.registerBinding("address", com.kingdee.eas.basedata.assistant.AddressInfo.class, this.txtAddress, "data");
		dataBinder.registerBinding("type", com.kingdee.eas.basedata.assistant.ProjectTypeEnum.class, this.comboType, "selectedItem");
		dataBinder.registerBinding("status", com.kingdee.eas.basedata.assistant.ProjectStatus.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("scheduleStartDate", java.util.Date.class, this.dateScheduleStartDate, "value");
		dataBinder.registerBinding("schedulEndDate", java.util.Date.class, this.dateSchedulEndDate, "value");
		dataBinder.registerBinding("factStartDate", java.util.Date.class, this.dateFactStartDate, "value");
		dataBinder.registerBinding("factEndDate", java.util.Date.class, this.dateFactEndDate, "value");
		dataBinder.registerBinding("process", java.math.BigDecimal.class, this.txtProcess, "value");
		dataBinder.registerBinding("attachment", String.class, this.txtAttachment, "text");
		dataBinder.registerBinding("prjManager", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtPrjManager, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.belongCompanyBiz, "data");
		dataBinder.registerBinding("managerList", com.kingdee.eas.basedata.assistant.ManagerListInfo.class, this.tblProjectManager, "userObject");
		dataBinder.registerBinding("managerList.id", com.kingdee.bos.util.BOSUuid.class, this.tblProjectManager, "id.text");
		dataBinder.registerBinding("managerList.projectManage", com.kingdee.eas.basedata.person.PersonInfo.class, this.tblProjectManager, "projectManager.text");
		dataBinder.registerBinding("managerList.managerName", String.class, this.tblProjectManager, "managerName.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.basedata.assistant.app.ProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.basedata.assistant.ProjectInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isListItem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSysCreate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheduleStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("schedulEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("process", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prjManager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("managerList", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("managerList.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("managerList.projectManage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("managerList.managerName", ValidateHelper.ON_SAVE);    		
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
     * output kDBizMultiLangBox1_actionPerformed method
     */
    protected void kDBizMultiLangBox1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isListItem"));
        sic.add(new SelectorItemInfo("isSysCreate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("address.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("address.id"));
        	sic.add(new SelectorItemInfo("address.number"));
        	sic.add(new SelectorItemInfo("address.name"));
		}
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("scheduleStartDate"));
        sic.add(new SelectorItemInfo("schedulEndDate"));
        sic.add(new SelectorItemInfo("factStartDate"));
        sic.add(new SelectorItemInfo("factEndDate"));
        sic.add(new SelectorItemInfo("process"));
        sic.add(new SelectorItemInfo("attachment"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("prjManager.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("prjManager.id"));
        	sic.add(new SelectorItemInfo("prjManager.number"));
        	sic.add(new SelectorItemInfo("prjManager.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("company.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("company.id"));
        	sic.add(new SelectorItemInfo("company.number"));
        	sic.add(new SelectorItemInfo("company.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("managerList.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("managerList.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("managerList.projectManage.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("managerList.projectManage.id"));
			sic.add(new SelectorItemInfo("managerList.projectManage.name"));
        	sic.add(new SelectorItemInfo("managerList.projectManage.number"));
		}
    	sic.add(new SelectorItemInfo("managerList.managerName"));
        return sic;
    }        
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveLine_actionPerformed method
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLine() {
    	return false;
    }

    /**
     * output ActionAddLine class
     */     
    protected class ActionAddLine extends ItemAction {     
    
        public ActionAddLine()
        {
            this(null);
        }

        public ActionAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveLine class
     */     
    protected class ActionRemoveLine extends ItemAction {     
    
        public ActionRemoveLine()
        {
            this(null);
        }

        public ActionRemoveLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectEditUI.this, "ActionRemoveLine", "actionRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.basedata.assistant.client", "ProjectEditUI");
    }




}