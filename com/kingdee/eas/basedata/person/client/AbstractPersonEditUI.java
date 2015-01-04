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
public abstract class AbstractPersonEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPersonEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDLabel labComment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelAssign;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea kDTextAreaDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labGender;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labBirthday;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labKAClassfication;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lablBackEmail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labHomePhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labOfficePhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labCell;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labBackCell;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labRTX;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labAddress;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labIdNum;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboGender;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBirthday;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizKAClassfication;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBackEmail;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtHomePhone;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtOfficePhone;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtCell;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtBackCell;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtRTX;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtAddress;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPositionList;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtIdNum;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBankList;
    protected com.kingdee.eas.basedata.person.PersonInfo editData = null;
    protected EntityViewInfo personOfPositions = null;
    protected IMetaDataPK personOfPositionsPK;
    protected ActionAddLine actionAddLine = null;
    protected ActionRemoveLine actionRemoveLine = null;
    /**
     * output class constructor
     */
    public AbstractPersonEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPersonEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        personOfPositionsPK = new MetaDataPK("com.kingdee.eas.basedata.person.app", "PersonOfPositions");
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLine
        this.actionRemoveLine = new ActionRemoveLine(this);
        getActionManager().registerAction("actionRemoveLine", actionRemoveLine);
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.labComment = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnAddAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTextAreaDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.labNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labGender = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labBirthday = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labKAClassfication = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lablBackEmail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labHomePhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labOfficePhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labCell = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labBackCell = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labRTX = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.labIdNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.txtName2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.comboGender = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.dateBirthday = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizKAClassfication = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBackEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtHomePhone = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.txtOfficePhone = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.txtCell = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.txtBackCell = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.txtRTX = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.txtAddress = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.tblPositionList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtIdNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblBankList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.labComment.setName("labComment");
        this.btnAddAssign.setName("btnAddAssign");
        this.btnDelAssign.setName("btnDelAssign");
        this.kDTextAreaDescription.setName("kDTextAreaDescription");
        this.labNumber.setName("labNumber");
        this.labName.setName("labName");
        this.labGender.setName("labGender");
        this.labBirthday.setName("labBirthday");
        this.labKAClassfication.setName("labKAClassfication");
        this.labEmail.setName("labEmail");
        this.lablBackEmail.setName("lablBackEmail");
        this.labHomePhone.setName("labHomePhone");
        this.labOfficePhone.setName("labOfficePhone");
        this.labCell.setName("labCell");
        this.labBackCell.setName("labBackCell");
        this.labRTX.setName("labRTX");
        this.labAddress.setName("labAddress");
        this.kDContainer1.setName("kDContainer1");
        this.labIdNum.setName("labIdNum");
        this.kDContainer2.setName("kDContainer2");
        this.txtNumber.setName("txtNumber");
        this.txtName2.setName("txtName2");
        this.comboGender.setName("comboGender");
        this.dateBirthday.setName("dateBirthday");
        this.bizKAClassfication.setName("bizKAClassfication");
        this.txtEmail.setName("txtEmail");
        this.txtBackEmail.setName("txtBackEmail");
        this.txtHomePhone.setName("txtHomePhone");
        this.txtOfficePhone.setName("txtOfficePhone");
        this.txtCell.setName("txtCell");
        this.txtBackCell.setName("txtBackCell");
        this.txtRTX.setName("txtRTX");
        this.txtAddress.setName("txtAddress");
        this.tblPositionList.setName("tblPositionList");
        this.txtIdNum.setName("txtIdNum");
        this.tblBankList.setName("tblBankList");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);
        // kDButtonGroup1
        // labComment		
        this.labComment.setText(resHelper.getString("labComment.text"));
        // btnAddAssign		
        this.btnAddAssign.setText(resHelper.getString("btnAddAssign.text"));		
        this.btnAddAssign.setToolTipText(resHelper.getString("btnAddAssign.toolTipText"));
        this.btnAddAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddAssign_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelAssign		
        this.btnDelAssign.setText(resHelper.getString("btnDelAssign.text"));		
        this.btnDelAssign.setToolTipText(resHelper.getString("btnDelAssign.toolTipText"));
        this.btnDelAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelAssign_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDTextAreaDescription		
        this.kDTextAreaDescription.setMaxLength(150);
        // labNumber		
        this.labNumber.setBoundLabelText(resHelper.getString("labNumber.boundLabelText"));		
        this.labNumber.setBoundLabelUnderline(true);		
        this.labNumber.setBoundLabelLength(150);
        // labName		
        this.labName.setBoundLabelText(resHelper.getString("labName.boundLabelText"));		
        this.labName.setBoundLabelUnderline(true);		
        this.labName.setBoundLabelLength(150);
        // labGender		
        this.labGender.setBoundLabelText(resHelper.getString("labGender.boundLabelText"));		
        this.labGender.setBoundLabelUnderline(true);		
        this.labGender.setBoundLabelLength(150);
        // labBirthday		
        this.labBirthday.setBoundLabelText(resHelper.getString("labBirthday.boundLabelText"));		
        this.labBirthday.setBoundLabelUnderline(true);		
        this.labBirthday.setBoundLabelLength(150);
        // labKAClassfication		
        this.labKAClassfication.setBoundLabelText(resHelper.getString("labKAClassfication.boundLabelText"));		
        this.labKAClassfication.setBoundLabelUnderline(true);		
        this.labKAClassfication.setBoundLabelLength(150);
        // labEmail		
        this.labEmail.setBoundLabelText(resHelper.getString("labEmail.boundLabelText"));		
        this.labEmail.setBoundLabelUnderline(true);		
        this.labEmail.setBoundLabelLength(150);
        // lablBackEmail		
        this.lablBackEmail.setBoundLabelText(resHelper.getString("lablBackEmail.boundLabelText"));		
        this.lablBackEmail.setBoundLabelUnderline(true);		
        this.lablBackEmail.setBoundLabelLength(150);
        // labHomePhone		
        this.labHomePhone.setBoundLabelText(resHelper.getString("labHomePhone.boundLabelText"));		
        this.labHomePhone.setBoundLabelUnderline(true);		
        this.labHomePhone.setBoundLabelLength(150);
        // labOfficePhone		
        this.labOfficePhone.setBoundLabelText(resHelper.getString("labOfficePhone.boundLabelText"));		
        this.labOfficePhone.setBoundLabelUnderline(true);		
        this.labOfficePhone.setBoundLabelLength(150);
        // labCell		
        this.labCell.setBoundLabelText(resHelper.getString("labCell.boundLabelText"));		
        this.labCell.setBoundLabelUnderline(true);		
        this.labCell.setBoundLabelLength(150);
        // labBackCell		
        this.labBackCell.setBoundLabelText(resHelper.getString("labBackCell.boundLabelText"));		
        this.labBackCell.setBoundLabelUnderline(true);		
        this.labBackCell.setBoundLabelLength(150);
        // labRTX		
        this.labRTX.setBoundLabelText(resHelper.getString("labRTX.boundLabelText"));		
        this.labRTX.setBoundLabelUnderline(true);		
        this.labRTX.setBoundLabelLength(150);
        // labAddress		
        this.labAddress.setBoundLabelText(resHelper.getString("labAddress.boundLabelText"));		
        this.labAddress.setBoundLabelUnderline(true);		
        this.labAddress.setBoundLabelLength(150);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitleStyle(2);
        // labIdNum		
        this.labIdNum.setBoundLabelText(resHelper.getString("labIdNum.boundLabelText"));		
        this.labIdNum.setBoundLabelLength(150);		
        this.labIdNum.setBoundLabelUnderline(true);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setTitleStyle(2);		
        this.kDContainer2.setEnableActive(false);
        // txtNumber		
        this.txtNumber.setText(resHelper.getString("txtNumber.text"));		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setFilterType(-1);		
        this.txtNumber.setRequired(true);
        // txtName2		
        this.txtName2.setMaxLength(80);		
        this.txtName2.setRequired(true);
        // comboGender		
        this.comboGender.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.person.Genders").toArray());
        // dateBirthday
        // bizKAClassfication		
        this.bizKAClassfication.setEditable(true);
        // txtEmail		
        this.txtEmail.setMaxLength(70);
        // txtBackEmail		
        this.txtBackEmail.setText(resHelper.getString("txtBackEmail.text"));		
        this.txtBackEmail.setMaxLength(100);
        // txtHomePhone		
        this.txtHomePhone.setText(resHelper.getString("txtHomePhone.text"));		
        this.txtHomePhone.setValidCharacters("+-()");		
        this.txtHomePhone.setFilterType(1);		
        this.txtHomePhone.setMaxLength(50);
        // txtOfficePhone		
        this.txtOfficePhone.setText(resHelper.getString("txtOfficePhone.text"));		
        this.txtOfficePhone.setFilterType(1);		
        this.txtOfficePhone.setMaxLength(50);		
        this.txtOfficePhone.setValidCharacters("+-()");
        // txtCell		
        this.txtCell.setText(resHelper.getString("txtCell.text"));		
        this.txtCell.setFilterType(1);		
        this.txtCell.setMaxLength(50);		
        this.txtCell.setValidCharacters("+-()");
        // txtBackCell		
        this.txtBackCell.setText(resHelper.getString("txtBackCell.text"));		
        this.txtBackCell.setValidCharacters("+-()");		
        this.txtBackCell.setMaxLength(50);		
        this.txtBackCell.setFilterType(1);
        // txtRTX		
        this.txtRTX.setText(resHelper.getString("txtRTX.text"));		
        this.txtRTX.setFilterType(1);		
        this.txtRTX.setMaxLength(50);
        // txtAddress		
        this.txtAddress.setMaxLength(80);
        // tblPositionList
		String tblPositionListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"isPrimary\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"Position.name\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Job.name\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Admin.name\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Position.id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"Admin.id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"Job.id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isPrimary}</t:Cell><t:Cell>$Resource{Position.name}</t:Cell><t:Cell>$Resource{Job.name}</t:Cell><t:Cell>$Resource{Admin.name}</t:Cell><t:Cell>$Resource{Position.id}</t:Cell><t:Cell>$Resource{Admin.id}</t:Cell><t:Cell>$Resource{Job.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPositionList.setFormatXml(resHelper.translateString("tblPositionList",tblPositionListStrXML));
        this.tblPositionList.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPositionList_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblPositionList.putBindContents("personOfPositions",new String[] {"id","isPrimary","Position.name","Job.name","Admin.displayName","Position.id","Admin.id","Job.id"});

        this.tblPositionList.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                tblPositionList_doRequestRowSet(e);
            }
        });

        // txtIdNum		
        this.txtIdNum.setMaxLength(50);
        // tblBankList
		String tblBankListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"bandName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bandAcctNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bandAddr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"usage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"province\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"city\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bandName}</t:Cell><t:Cell>$Resource{bandAcctNumber}</t:Cell><t:Cell>$Resource{bandAddr}</t:Cell><t:Cell>$Resource{usage}</t:Cell><t:Cell>$Resource{province}</t:Cell><t:Cell>$Resource{city}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBankList.setFormatXml(resHelper.translateString("tblBankList",tblBankListStrXML));

                this.tblBankList.putBindContents("editData",new String[] {"id","bankName","bandAcctNumber","bandAddr","usage","province","city"});


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
        this.setBounds(new Rectangle(8, 10, 630, 480));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(8, 10, 630, 480));
        labComment.setBounds(new Rectangle(8, 186, 110, 19));
        this.add(labComment, new KDLayout.Constraints(8, 186, 110, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddAssign.setBounds(new Rectangle(543, 240, 22, 19));
        this.add(btnAddAssign, new KDLayout.Constraints(543, 240, 22, 19, 0));
        btnDelAssign.setBounds(new Rectangle(574, 240, 22, 19));
        this.add(btnDelAssign, new KDLayout.Constraints(574, 240, 22, 19, 0));
        kDTextAreaDescription.setBounds(new Rectangle(8, 206, 612, 60));
        this.add(kDTextAreaDescription, new KDLayout.Constraints(8, 206, 612, 60, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labNumber.setBounds(new Rectangle(8, 8, 300, 19));
        this.add(labNumber, new KDLayout.Constraints(8, 8, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labName.setBounds(new Rectangle(320, 8, 300, 19));
        this.add(labName, new KDLayout.Constraints(320, 8, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labGender.setBounds(new Rectangle(320, 30, 300, 19));
        this.add(labGender, new KDLayout.Constraints(320, 30, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labBirthday.setBounds(new Rectangle(8, 52, 300, 19));
        this.add(labBirthday, new KDLayout.Constraints(8, 52, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labKAClassfication.setBounds(new Rectangle(320, 52, 300, 19));
        this.add(labKAClassfication, new KDLayout.Constraints(320, 52, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labEmail.setBounds(new Rectangle(8, 74, 300, 19));
        this.add(labEmail, new KDLayout.Constraints(8, 74, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lablBackEmail.setBounds(new Rectangle(320, 74, 300, 19));
        this.add(lablBackEmail, new KDLayout.Constraints(320, 74, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labHomePhone.setBounds(new Rectangle(8, 96, 300, 19));
        this.add(labHomePhone, new KDLayout.Constraints(8, 96, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labOfficePhone.setBounds(new Rectangle(320, 96, 300, 19));
        this.add(labOfficePhone, new KDLayout.Constraints(320, 96, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCell.setBounds(new Rectangle(8, 118, 300, 19));
        this.add(labCell, new KDLayout.Constraints(8, 118, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labBackCell.setBounds(new Rectangle(320, 118, 300, 19));
        this.add(labBackCell, new KDLayout.Constraints(320, 118, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labRTX.setBounds(new Rectangle(8, 140, 300, 19));
        this.add(labRTX, new KDLayout.Constraints(8, 140, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labAddress.setBounds(new Rectangle(8, 162, 612, 19));
        this.add(labAddress, new KDLayout.Constraints(8, 162, 612, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(8, 270, 612, 105));
        this.add(kDContainer1, new KDLayout.Constraints(8, 270, 612, 105, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labIdNum.setBounds(new Rectangle(8, 30, 300, 19));
        this.add(labIdNum, new KDLayout.Constraints(8, 30, 300, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(8, 380, 612, 95));
        this.add(kDContainer2, new KDLayout.Constraints(8, 380, 612, 95, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //labNumber
        labNumber.setBoundEditor(txtNumber);
        //labName
        labName.setBoundEditor(txtName2);
        //labGender
        labGender.setBoundEditor(comboGender);
        //labBirthday
        labBirthday.setBoundEditor(dateBirthday);
        //labKAClassfication
        labKAClassfication.setBoundEditor(bizKAClassfication);
        //labEmail
        labEmail.setBoundEditor(txtEmail);
        //lablBackEmail
        lablBackEmail.setBoundEditor(txtBackEmail);
        //labHomePhone
        labHomePhone.setBoundEditor(txtHomePhone);
        //labOfficePhone
        labOfficePhone.setBoundEditor(txtOfficePhone);
        //labCell
        labCell.setBoundEditor(txtCell);
        //labBackCell
        labBackCell.setBoundEditor(txtBackCell);
        //labRTX
        labRTX.setBoundEditor(txtRTX);
        //labAddress
        labAddress.setBoundEditor(txtAddress);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblPositionList, BorderLayout.CENTER);
        //labIdNum
        labIdNum.setBoundEditor(txtIdNum);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblBankList, BorderLayout.CENTER);

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
		dataBinder.registerBinding("description", String.class, this.kDTextAreaDescription, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "stringValue");
		dataBinder.registerBinding("name", String.class, this.txtName2, "_multiLangItem");
		dataBinder.registerBinding("gender", com.kingdee.eas.basedata.person.Genders.class, this.comboGender, "selectedItem");
		dataBinder.registerBinding("birthday", java.util.Date.class, this.dateBirthday, "value");
		dataBinder.registerBinding("kaClassfication.name", String.class, this.bizKAClassfication, "userObject");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("backupEMail", String.class, this.txtBackEmail, "text");
		dataBinder.registerBinding("homePhone", String.class, this.txtHomePhone, "stringValue");
		dataBinder.registerBinding("officePhone", String.class, this.txtOfficePhone, "stringValue");
		dataBinder.registerBinding("cell", String.class, this.txtCell, "stringValue");
		dataBinder.registerBinding("backupCell", String.class, this.txtBackCell, "stringValue");
		dataBinder.registerBinding("rtx", String.class, this.txtRTX, "stringValue");
		dataBinder.registerBinding("addressTX", String.class, this.txtAddress, "_multiLangItem");
		dataBinder.registerBinding("idNum", String.class, this.txtIdNum, "text");
		dataBinder.registerBinding("bankInfo", com.kingdee.eas.basedata.person.BankInfoInfo.class, this.tblBankList, "userObject");
		dataBinder.registerBinding("bankInfo.id", com.kingdee.bos.util.BOSUuid.class, this.tblBankList, "id.text");
		dataBinder.registerBinding("bankInfo.bandAcctNumber", String.class, this.tblBankList, "bandAcctNumber.text");
		dataBinder.registerBinding("bankInfo.bandAddr", String.class, this.tblBankList, "bandAddr.text");
		dataBinder.registerBinding("bankInfo.usage", String.class, this.tblBankList, "usage.text");
		dataBinder.registerBinding("bankInfo.province", String.class, this.tblBankList, "province.text");
		dataBinder.registerBinding("bankInfo.city", String.class, this.tblBankList, "city.text");
		dataBinder.registerBinding("bankInfo.bankName", String.class, this.tblBankList, "bandName.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.basedata.person.app.PersonEditUIHandler";
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
        this.editData = (com.kingdee.eas.basedata.person.PersonInfo)ov;
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("personOfPositions")) {
            this.personOfPositions = (EntityViewInfo)dataObject;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gender", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("birthday", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kaClassfication.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("backupEMail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("homePhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("officePhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cell", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("backupCell", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rtx", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("addressTX", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("idNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.bandAcctNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.bandAddr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.usage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.city", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bankInfo.bankName", ValidateHelper.ON_SAVE);    		
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
     * output btnAddAssign_actionPerformed method
     */
    protected void btnAddAssign_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelAssign_actionPerformed method
     */
    protected void btnDelAssign_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblPositionList_tableClicked method
     */
    protected void tblPositionList_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblPositionList_doRequestRowSet method
     */
    protected void tblPositionList_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.personOfPositions != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.personOfPositionsPK, this.personOfPositions);
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
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("gender"));
        sic.add(new SelectorItemInfo("birthday"));
        sic.add(new SelectorItemInfo("kaClassfication.name"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("backupEMail"));
        sic.add(new SelectorItemInfo("homePhone"));
        sic.add(new SelectorItemInfo("officePhone"));
        sic.add(new SelectorItemInfo("cell"));
        sic.add(new SelectorItemInfo("backupCell"));
        sic.add(new SelectorItemInfo("rtx"));
        sic.add(new SelectorItemInfo("addressTX"));
        sic.add(new SelectorItemInfo("idNum"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bankInfo.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("bankInfo.id"));
    	sic.add(new SelectorItemInfo("bankInfo.bandAcctNumber"));
    	sic.add(new SelectorItemInfo("bankInfo.bandAddr"));
    	sic.add(new SelectorItemInfo("bankInfo.usage"));
    	sic.add(new SelectorItemInfo("bankInfo.province"));
    	sic.add(new SelectorItemInfo("bankInfo.city"));
    	sic.add(new SelectorItemInfo("bankInfo.bankName"));
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
            innerActionPerformed("eas", AbstractPersonEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPersonEditUI.this, "ActionRemoveLine", "actionRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.basedata.person.client", "PersonEditUI");
    }




}