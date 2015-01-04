/**
 * output package name
 */
package com.kingdee.eas.basedata.master.cssp.client;

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
public abstract class AbstractSupplierCompanyUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierCompanyUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCompanyNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCompanyOrgName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblKAClass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblPaymentType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkCSIsFreezeTicketOrPay;
    protected com.kingdee.bos.ctrl.swing.KDContainer gprBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayCondition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompanyOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountClass;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPaymentType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBankAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAccountLink;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkDuty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOfficePhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCell;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtZipCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEmail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtComCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtComUpdator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtComFreezeOrg;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayCondition;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBoxStatus;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSupplierTradeRpt;
    protected com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo editData = null;
    protected ActionSupplierTradeRpt actionSupplierTradeRpt = null;
    protected ActionAddRowBank actionAddRowBank = null;
    protected ActionDelRowBank actionDelRowBank = null;
    /**
     * output class constructor
     */
    public AbstractSupplierCompanyUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierCompanyUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSupplierTradeRpt
        this.actionSupplierTradeRpt = new ActionSupplierTradeRpt(this);
        getActionManager().registerAction("actionSupplierTradeRpt", actionSupplierTradeRpt);
         this.actionSupplierTradeRpt.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRowBank
        this.actionAddRowBank = new ActionAddRowBank(this);
        getActionManager().registerAction("actionAddRowBank", actionAddRowBank);
         this.actionAddRowBank.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelRowBank
        this.actionDelRowBank = new ActionDelRowBank(this);
        getActionManager().registerAction("actionDelRowBank", actionDelRowBank);
         this.actionDelRowBank.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.lblCompanyNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCompanyOrgName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblKAClass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblPaymentType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkCSIsFreezeTicketOrPay = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.gprBank = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contPayCondition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCompanyOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCompanyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAccountClass = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPaymentType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAccountBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankAccount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAccountLink = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkDuty = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOfficePhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCell = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtZipCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEmail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtComCreator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtComUpdator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtComFreezeOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblBank = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtPayCondition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBoxStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.menuItemSupplierTradeRpt = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.lblCompanyNumber.setName("lblCompanyNumber");
        this.lblCompanyOrgName.setName("lblCompanyOrgName");
        this.lblKAClass.setName("lblKAClass");
        this.lblCurrency.setName("lblCurrency");
        this.lblPaymentType.setName("lblPaymentType");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.chkCSIsFreezeTicketOrPay.setName("chkCSIsFreezeTicketOrPay");
        this.gprBank.setName("gprBank");
        this.contPayCondition.setName("contPayCondition");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.prmtCompanyOrgUnit.setName("prmtCompanyOrgUnit");
        this.txtCompanyName.setName("txtCompanyName");
        this.prmtAccountClass.setName("prmtAccountClass");
        this.prmtCurrency.setName("prmtCurrency");
        this.prmtPaymentType.setName("prmtPaymentType");
        this.prmtSettlementType.setName("prmtSettlementType");
        this.txtAccountBank.setName("txtAccountBank");
        this.txtBankAccount.setName("txtBankAccount");
        this.txtAccountLink.setName("txtAccountLink");
        this.txtLinkDuty.setName("txtLinkDuty");
        this.txtOfficePhone.setName("txtOfficePhone");
        this.txtCell.setName("txtCell");
        this.txtZipCode.setName("txtZipCode");
        this.txtFax.setName("txtFax");
        this.txtEmail.setName("txtEmail");
        this.txtComCreator.setName("txtComCreator");
        this.txtCreateDate.setName("txtCreateDate");
        this.txtUpdateDate.setName("txtUpdateDate");
        this.txtComUpdator.setName("txtComUpdator");
        this.prmtComFreezeOrg.setName("prmtComFreezeOrg");
        this.tblBank.setName("tblBank");
        this.prmtPayCondition.setName("prmtPayCondition");
        this.comboBoxStatus.setName("comboBoxStatus");
        this.menuItemSupplierTradeRpt.setName("menuItemSupplierTradeRpt");
        // CoreUI		
        this.setPreferredSize(new Dimension(772,502));		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.kDSeparator3.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.kDSeparator4.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);
        // lblCompanyNumber		
        this.lblCompanyNumber.setBoundLabelText(resHelper.getString("lblCompanyNumber.boundLabelText"));		
        this.lblCompanyNumber.setBoundLabelUnderline(true);		
        this.lblCompanyNumber.setBoundLabelLength(150);		
        this.lblCompanyNumber.setBoundLabelAlignment(7);		
        this.lblCompanyNumber.setVisible(true);		
        this.lblCompanyNumber.setForeground(new java.awt.Color(0,0,0));
        // lblCompanyOrgName		
        this.lblCompanyOrgName.setBoundLabelText(resHelper.getString("lblCompanyOrgName.boundLabelText"));		
        this.lblCompanyOrgName.setBoundLabelUnderline(true);		
        this.lblCompanyOrgName.setBoundLabelLength(150);
        // lblKAClass		
        this.lblKAClass.setBoundLabelText(resHelper.getString("lblKAClass.boundLabelText"));		
        this.lblKAClass.setBoundLabelUnderline(true);		
        this.lblKAClass.setBoundLabelLength(150);		
        this.lblKAClass.setBoundLabelAlignment(7);		
        this.lblKAClass.setVisible(true);		
        this.lblKAClass.setForeground(new java.awt.Color(0,0,0));
        // lblCurrency		
        this.lblCurrency.setBoundLabelText(resHelper.getString("lblCurrency.boundLabelText"));		
        this.lblCurrency.setBoundLabelUnderline(true);		
        this.lblCurrency.setBoundLabelLength(150);
        // lblPaymentType		
        this.lblPaymentType.setBoundLabelText(resHelper.getString("lblPaymentType.boundLabelText"));		
        this.lblPaymentType.setBoundLabelUnderline(true);		
        this.lblPaymentType.setBoundLabelLength(150);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(150);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelLength(180);		
        this.kDLabelContainer10.setVisible(false);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(180);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setVisible(false);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);		
        this.kDLabelContainer11.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(150);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setBoundLabelAlignment(7);		
        this.kDLabelContainer12.setVisible(true);		
        this.kDLabelContainer12.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(150);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);		
        this.kDLabelContainer13.setBoundLabelAlignment(7);		
        this.kDLabelContainer13.setVisible(true);		
        this.kDLabelContainer13.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(150);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);		
        this.kDLabelContainer14.setBoundLabelAlignment(7);		
        this.kDLabelContainer14.setVisible(true);		
        this.kDLabelContainer14.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(150);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setBoundLabelAlignment(7);		
        this.kDLabelContainer15.setVisible(true);		
        this.kDLabelContainer15.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(150);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setBoundLabelAlignment(7);		
        this.kDLabelContainer16.setVisible(true);		
        this.kDLabelContainer16.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(150);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);		
        this.kDLabelContainer17.setBoundLabelAlignment(7);		
        this.kDLabelContainer17.setVisible(true);		
        this.kDLabelContainer17.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(150);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);		
        this.kDLabelContainer18.setBoundLabelAlignment(7);		
        this.kDLabelContainer18.setVisible(true);		
        this.kDLabelContainer18.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(150);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);		
        this.kDLabelContainer19.setBoundLabelAlignment(7);		
        this.kDLabelContainer19.setVisible(true);		
        this.kDLabelContainer19.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(150);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);		
        this.kDLabelContainer20.setBoundLabelAlignment(7);		
        this.kDLabelContainer20.setVisible(true);		
        this.kDLabelContainer20.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(150);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);		
        this.kDLabelContainer21.setBoundLabelAlignment(7);		
        this.kDLabelContainer21.setVisible(true);		
        this.kDLabelContainer21.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(150);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);		
        this.kDLabelContainer22.setBoundLabelAlignment(7);		
        this.kDLabelContainer22.setVisible(true);		
        this.kDLabelContainer22.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(150);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);		
        this.kDLabelContainer23.setBoundLabelAlignment(7);		
        this.kDLabelContainer23.setVisible(false);		
        this.kDLabelContainer23.setForeground(new java.awt.Color(0,0,0));
        // chkCSIsFreezeTicketOrPay		
        this.chkCSIsFreezeTicketOrPay.setText(resHelper.getString("chkCSIsFreezeTicketOrPay.text"));		
        this.chkCSIsFreezeTicketOrPay.setVisible(true);		
        this.chkCSIsFreezeTicketOrPay.setEnabled(true);		
        this.chkCSIsFreezeTicketOrPay.setForeground(new java.awt.Color(0,0,0));		
        this.chkCSIsFreezeTicketOrPay.setHorizontalAlignment(2);
        // gprBank		
        this.gprBank.setEnableActive(false);		
        this.gprBank.setTitleStyle(2);		
        this.gprBank.setTitle(resHelper.getString("gprBank.title"));
        // contPayCondition		
        this.contPayCondition.setBoundLabelText(resHelper.getString("contPayCondition.boundLabelText"));		
        this.contPayCondition.setBoundLabelLength(150);		
        this.contPayCondition.setBoundLabelUnderline(true);		
        this.contPayCondition.setVisible(true);		
        this.contPayCondition.setBoundLabelAlignment(7);		
        this.contPayCondition.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(150);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // prmtCompanyOrgUnit		
        this.prmtCompanyOrgUnit.setDisplayFormat("$number$");		
        this.prmtCompanyOrgUnit.setEditFormat("$number$");		
        this.prmtCompanyOrgUnit.setEditable(true);		
        this.prmtCompanyOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtCompanyOrgUnit.setCommitFormat("$number$");		
        this.prmtCompanyOrgUnit.setEnabled(true);		
        this.prmtCompanyOrgUnit.setRequired(true);		
        this.prmtCompanyOrgUnit.setVisible(true);		
        this.prmtCompanyOrgUnit.setForeground(new java.awt.Color(0,0,0));
        // txtCompanyName		
        this.txtCompanyName.setText(resHelper.getString("txtCompanyName.text"));		
        this.txtCompanyName.setEditable(false);		
        this.txtCompanyName.setEnabled(false);
        // prmtAccountClass		
        this.prmtAccountClass.setDisplayFormat("$name$");		
        this.prmtAccountClass.setEditFormat("$name$");		
        this.prmtAccountClass.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7KAClassficationQuery");		
        this.prmtAccountClass.setCommitFormat("$number$");		
        this.prmtAccountClass.setEditable(true);		
        this.prmtAccountClass.setVisible(true);		
        this.prmtAccountClass.setEnabled(true);		
        this.prmtAccountClass.setForeground(new java.awt.Color(0,0,0));
        // prmtCurrency		
        this.prmtCurrency.setEditFormat("$name$");		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setEditable(true);		
        this.prmtCurrency.setRequired(true);
        // prmtPaymentType		
        this.prmtPaymentType.setDisplayFormat("$name$");		
        this.prmtPaymentType.setEditFormat("$name$");		
        this.prmtPaymentType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PaymentTypeQuery");		
        this.prmtPaymentType.setCommitFormat("$number$");		
        this.prmtPaymentType.setEditable(true);
        // prmtSettlementType		
        this.prmtSettlementType.setDisplayFormat("$name$");		
        this.prmtSettlementType.setEditFormat("$name$");		
        this.prmtSettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7SettlementTypeQuery");		
        this.prmtSettlementType.setCommitFormat("$number$");		
        this.prmtSettlementType.setEditable(true);		
        this.prmtSettlementType.setVisible(true);		
        this.prmtSettlementType.setEnabled(true);		
        this.prmtSettlementType.setForeground(new java.awt.Color(0,0,0));		
        this.prmtSettlementType.setRequired(false);
        // txtAccountBank		
        this.txtAccountBank.setText(resHelper.getString("txtAccountBank.text"));		
        this.txtAccountBank.setMaxLength(80);		
        this.txtAccountBank.setVisible(false);		
        this.txtAccountBank.setEnabled(true);		
        this.txtAccountBank.setHorizontalAlignment(2);		
        this.txtAccountBank.setForeground(new java.awt.Color(0,0,0));		
        this.txtAccountBank.setRequired(false);
        // txtBankAccount		
        this.txtBankAccount.setText(resHelper.getString("txtBankAccount.text"));		
        this.txtBankAccount.setMaxLength(80);		
        this.txtBankAccount.setVisible(false);		
        this.txtBankAccount.setEnabled(true);		
        this.txtBankAccount.setHorizontalAlignment(2);		
        this.txtBankAccount.setForeground(new java.awt.Color(0,0,0));		
        this.txtBankAccount.setRequired(false);
        // txtAccountLink		
        this.txtAccountLink.setText(resHelper.getString("txtAccountLink.text"));		
        this.txtAccountLink.setMaxLength(60);		
        this.txtAccountLink.setVisible(true);		
        this.txtAccountLink.setEnabled(true);		
        this.txtAccountLink.setHorizontalAlignment(2);		
        this.txtAccountLink.setForeground(new java.awt.Color(0,0,0));		
        this.txtAccountLink.setRequired(false);
        // txtLinkDuty		
        this.txtLinkDuty.setText(resHelper.getString("txtLinkDuty.text"));		
        this.txtLinkDuty.setMaxLength(60);		
        this.txtLinkDuty.setVisible(true);		
        this.txtLinkDuty.setEnabled(true);		
        this.txtLinkDuty.setHorizontalAlignment(2);		
        this.txtLinkDuty.setForeground(new java.awt.Color(0,0,0));		
        this.txtLinkDuty.setRequired(false);
        // txtOfficePhone		
        this.txtOfficePhone.setText(resHelper.getString("txtOfficePhone.text"));		
        this.txtOfficePhone.setMaxLength(60);		
        this.txtOfficePhone.setVisible(true);		
        this.txtOfficePhone.setEnabled(true);		
        this.txtOfficePhone.setHorizontalAlignment(2);		
        this.txtOfficePhone.setForeground(new java.awt.Color(0,0,0));		
        this.txtOfficePhone.setRequired(false);
        // txtCell		
        this.txtCell.setText(resHelper.getString("txtCell.text"));		
        this.txtCell.setMaxLength(40);		
        this.txtCell.setVisible(true);		
        this.txtCell.setEnabled(true);		
        this.txtCell.setHorizontalAlignment(2);		
        this.txtCell.setForeground(new java.awt.Color(0,0,0));		
        this.txtCell.setRequired(false);
        // txtZipCode		
        this.txtZipCode.setText(resHelper.getString("txtZipCode.text"));		
        this.txtZipCode.setMaxLength(10);		
        this.txtZipCode.setVisible(true);		
        this.txtZipCode.setEnabled(true);		
        this.txtZipCode.setHorizontalAlignment(2);		
        this.txtZipCode.setForeground(new java.awt.Color(0,0,0));		
        this.txtZipCode.setRequired(false);
        // txtFax		
        this.txtFax.setText(resHelper.getString("txtFax.text"));		
        this.txtFax.setMaxLength(40);		
        this.txtFax.setVisible(true);		
        this.txtFax.setEnabled(true);		
        this.txtFax.setHorizontalAlignment(2);		
        this.txtFax.setForeground(new java.awt.Color(0,0,0));		
        this.txtFax.setRequired(false);
        // txtEmail		
        this.txtEmail.setText(resHelper.getString("txtEmail.text"));		
        this.txtEmail.setMaxLength(80);		
        this.txtEmail.setVisible(true);		
        this.txtEmail.setEnabled(true);		
        this.txtEmail.setHorizontalAlignment(2);		
        this.txtEmail.setForeground(new java.awt.Color(0,0,0));		
        this.txtEmail.setRequired(false);
        // txtComCreator		
        this.txtComCreator.setText(resHelper.getString("txtComCreator.text"));		
        this.txtComCreator.setEditable(false);		
        this.txtComCreator.setEnabled(false);		
        this.txtComCreator.setVisible(true);		
        this.txtComCreator.setHorizontalAlignment(2);		
        this.txtComCreator.setForeground(new java.awt.Color(0,0,0));		
        this.txtComCreator.setRequired(false);
        // txtCreateDate		
        this.txtCreateDate.setEnabled(false);		
        this.txtCreateDate.setVisible(true);		
        this.txtCreateDate.setForeground(new java.awt.Color(0,0,0));		
        this.txtCreateDate.setRequired(false);
        // txtUpdateDate		
        this.txtUpdateDate.setEnabled(false);		
        this.txtUpdateDate.setVisible(true);		
        this.txtUpdateDate.setForeground(new java.awt.Color(0,0,0));		
        this.txtUpdateDate.setRequired(false);
        // txtComUpdator		
        this.txtComUpdator.setText(resHelper.getString("txtComUpdator.text"));		
        this.txtComUpdator.setEnabled(false);		
        this.txtComUpdator.setEditable(false);		
        this.txtComUpdator.setVisible(true);		
        this.txtComUpdator.setHorizontalAlignment(2);		
        this.txtComUpdator.setForeground(new java.awt.Color(0,0,0));		
        this.txtComUpdator.setRequired(false);
        // prmtComFreezeOrg		
        this.prmtComFreezeOrg.setEnabled(false);		
        this.prmtComFreezeOrg.setDisplayFormat("$name$");		
        this.prmtComFreezeOrg.setVisible(true);		
        this.prmtComFreezeOrg.setForeground(new java.awt.Color(0,0,0));		
        this.prmtComFreezeOrg.setRequired(false);
        // tblBank
		String tblBankStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"20\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"bank\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bankAccount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bankAddress\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"province\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"city\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bank}</t:Cell><t:Cell>$Resource{bankAccount}</t:Cell><t:Cell>$Resource{bankAddress}</t:Cell><t:Cell>$Resource{province}</t:Cell><t:Cell>$Resource{city}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBank.setFormatXml(resHelper.translateString("tblBank",tblBankStrXML));

                this.tblBank.putBindContents("editData",new String[] {"id","bank","bankAccount","bankAddress","province","city"});


        this.tblBank.checkParsed();
        KDTextField tblBank_bank_TextField = new KDTextField();
        tblBank_bank_TextField.setName("tblBank_bank_TextField");
        tblBank_bank_TextField.setMaxLength(255);
        KDTDefaultCellEditor tblBank_bank_CellEditor = new KDTDefaultCellEditor(tblBank_bank_TextField);
        this.tblBank.getColumn("bank").setEditor(tblBank_bank_CellEditor);
        KDTextField tblBank_bankAccount_TextField = new KDTextField();
        tblBank_bankAccount_TextField.setName("tblBank_bankAccount_TextField");
        tblBank_bankAccount_TextField.setMaxLength(80);
        KDTDefaultCellEditor tblBank_bankAccount_CellEditor = new KDTDefaultCellEditor(tblBank_bankAccount_TextField);
        this.tblBank.getColumn("bankAccount").setEditor(tblBank_bankAccount_CellEditor);
        KDTextField tblBank_bankAddress_TextField = new KDTextField();
        tblBank_bankAddress_TextField.setName("tblBank_bankAddress_TextField");
        tblBank_bankAddress_TextField.setMaxLength(255);
        KDTDefaultCellEditor tblBank_bankAddress_CellEditor = new KDTDefaultCellEditor(tblBank_bankAddress_TextField);
        this.tblBank.getColumn("bankAddress").setEditor(tblBank_bankAddress_CellEditor);
        KDTextField tblBank_province_TextField = new KDTextField();
        tblBank_province_TextField.setName("tblBank_province_TextField");
        tblBank_province_TextField.setMaxLength(80);
        KDTDefaultCellEditor tblBank_province_CellEditor = new KDTDefaultCellEditor(tblBank_province_TextField);
        this.tblBank.getColumn("province").setEditor(tblBank_province_CellEditor);
        KDTextField tblBank_city_TextField = new KDTextField();
        tblBank_city_TextField.setName("tblBank_city_TextField");
        tblBank_city_TextField.setMaxLength(80);
        KDTDefaultCellEditor tblBank_city_CellEditor = new KDTDefaultCellEditor(tblBank_city_TextField);
        this.tblBank.getColumn("city").setEditor(tblBank_city_CellEditor);
        // prmtPayCondition		
        this.prmtPayCondition.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PayConditionQuery");		
        this.prmtPayCondition.setVisible(true);		
        this.prmtPayCondition.setEditable(true);		
        this.prmtPayCondition.setDisplayFormat("$name$");		
        this.prmtPayCondition.setEditFormat("$number$");		
        this.prmtPayCondition.setCommitFormat("$number$");		
        this.prmtPayCondition.setRequired(false);		
        this.prmtPayCondition.setEnabled(true);		
        this.prmtPayCondition.setForeground(new java.awt.Color(0,0,0));
        // comboBoxStatus		
        this.comboBoxStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.master.cssp.UsingStatusEnum").toArray());		
        this.comboBoxStatus.setEnabled(false);
        // menuItemSupplierTradeRpt
        this.menuItemSupplierTradeRpt.setAction((IItemAction)ActionProxyFactory.getProxy(actionSupplierTradeRpt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSupplierTradeRpt.setText(resHelper.getString("menuItemSupplierTradeRpt.text"));		
        this.menuItemSupplierTradeRpt.setMnemonic(84);
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
        this.setBounds(new Rectangle(0, 0, 780, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 780, 500));
        lblCompanyNumber.setBounds(new Rectangle(5, 1, 350, 19));
        this.add(lblCompanyNumber, new KDLayout.Constraints(5, 1, 350, 19, 0));
        lblCompanyOrgName.setBounds(new Rectangle(415, 1, 350, 19));
        this.add(lblCompanyOrgName, new KDLayout.Constraints(415, 1, 350, 19, 0));
        lblKAClass.setBounds(new Rectangle(5, 24, 350, 19));
        this.add(lblKAClass, new KDLayout.Constraints(5, 24, 350, 19, 0));
        lblCurrency.setBounds(new Rectangle(415, 24, 350, 19));
        this.add(lblCurrency, new KDLayout.Constraints(415, 24, 350, 19, 0));
        lblPaymentType.setBounds(new Rectangle(415, 47, 350, 19));
        this.add(lblPaymentType, new KDLayout.Constraints(415, 47, 350, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(5, 47, 350, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(5, 47, 350, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(365, 471, 350, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(365, 471, 350, 19, 0));
        kDLabelContainer11.setBounds(new Rectangle(5, 471, 350, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(5, 471, 350, 19, 0));
        kDLabelContainer12.setBounds(new Rectangle(5, 93, 350, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(5, 93, 350, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(415, 93, 350, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(415, 93, 350, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(5, 116, 350, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(5, 116, 350, 19, 0));
        kDLabelContainer15.setBounds(new Rectangle(415, 116, 350, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(415, 116, 350, 19, 0));
        kDLabelContainer16.setBounds(new Rectangle(5, 139, 350, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(5, 139, 350, 19, 0));
        kDLabelContainer17.setBounds(new Rectangle(415, 139, 350, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(415, 139, 350, 19, 0));
        kDLabelContainer18.setBounds(new Rectangle(5, 162, 350, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(5, 162, 350, 19, 0));
        kDLabelContainer19.setBounds(new Rectangle(5, 185, 350, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(5, 185, 350, 19, 0));
        kDLabelContainer20.setBounds(new Rectangle(415, 185, 350, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(415, 185, 350, 19, 0));
        kDLabelContainer21.setBounds(new Rectangle(415, 208, 350, 19));
        this.add(kDLabelContainer21, new KDLayout.Constraints(415, 208, 350, 19, 0));
        kDLabelContainer22.setBounds(new Rectangle(5, 208, 350, 19));
        this.add(kDLabelContainer22, new KDLayout.Constraints(5, 208, 350, 19, 0));
        kDLabelContainer23.setBounds(new Rectangle(415, 162, 350, 19));
        this.add(kDLabelContainer23, new KDLayout.Constraints(415, 162, 350, 19, 0));
        chkCSIsFreezeTicketOrPay.setBounds(new Rectangle(6, 231, 350, 19));
        this.add(chkCSIsFreezeTicketOrPay, new KDLayout.Constraints(6, 231, 350, 19, 0));
        gprBank.setBounds(new Rectangle(5, 261, 760, 201));
        this.add(gprBank, new KDLayout.Constraints(5, 261, 760, 201, 0));
        contPayCondition.setBounds(new Rectangle(5, 70, 350, 19));
        this.add(contPayCondition, new KDLayout.Constraints(5, 70, 350, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(415, 231, 350, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(415, 231, 350, 19, 0));
        //lblCompanyNumber
        lblCompanyNumber.setBoundEditor(prmtCompanyOrgUnit);
        //lblCompanyOrgName
        lblCompanyOrgName.setBoundEditor(txtCompanyName);
        //lblKAClass
        lblKAClass.setBoundEditor(prmtAccountClass);
        //lblCurrency
        lblCurrency.setBoundEditor(prmtCurrency);
        //lblPaymentType
        lblPaymentType.setBoundEditor(prmtPaymentType);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtSettlementType);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtAccountBank);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtBankAccount);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtAccountLink);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtLinkDuty);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtOfficePhone);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtCell);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtZipCode);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(txtFax);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(txtEmail);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtComCreator);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtCreateDate);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(txtUpdateDate);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(txtComUpdator);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(prmtComFreezeOrg);
        //gprBank
gprBank.getContentPane().setLayout(new BorderLayout(0, 0));        gprBank.getContentPane().add(tblBank, BorderLayout.CENTER);
        //contPayCondition
        contPayCondition.setBoundEditor(prmtPayCondition);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboBoxStatus);

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
        menuBiz.add(menuItemSupplierTradeRpt);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnFirst);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW3);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isFreezePayment", boolean.class, this.chkCSIsFreezeTicketOrPay, "selected");
		dataBinder.registerBinding("accountingClassification", com.kingdee.eas.basedata.assistant.KAClassficationInfo.class, this.prmtAccountClass, "data");
		dataBinder.registerBinding("settlementCurrency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("paymentType", com.kingdee.eas.basedata.assistant.PaymentTypeInfo.class, this.prmtPaymentType, "data");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettlementType, "data");
		dataBinder.registerBinding("contactPerson", String.class, this.txtAccountLink, "text");
		dataBinder.registerBinding("contactPersonPost", String.class, this.txtLinkDuty, "text");
		dataBinder.registerBinding("phone", String.class, this.txtOfficePhone, "text");
		dataBinder.registerBinding("mobile", String.class, this.txtCell, "text");
		dataBinder.registerBinding("postalcode", String.class, this.txtZipCode, "text");
		dataBinder.registerBinding("fax", String.class, this.txtFax, "text");
		dataBinder.registerBinding("email", String.class, this.txtEmail, "text");
		dataBinder.registerBinding("creator.name", String.class, this.txtComCreator, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.txtCreateDate, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.txtUpdateDate, "value");
		dataBinder.registerBinding("lastUpdateUser.name", String.class, this.txtComUpdator, "text");
		dataBinder.registerBinding("freezeOrgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtComFreezeOrg, "data");
		dataBinder.registerBinding("supplierBank.id", com.kingdee.bos.util.BOSUuid.class, this.tblBank, "id.text");
		dataBinder.registerBinding("supplierBank", com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo.class, this.tblBank, "userObject");
		dataBinder.registerBinding("supplierBank.bank", String.class, this.tblBank, "bank.text");
		dataBinder.registerBinding("supplierBank.bankAccount", String.class, this.tblBank, "bankAccount.text");
		dataBinder.registerBinding("supplierBank.bankAddress", String.class, this.tblBank, "bankAddress.text");
		dataBinder.registerBinding("supplierBank.province", String.class, this.tblBank, "province.text");
		dataBinder.registerBinding("supplierBank.city", byte[].class, this.tblBank, "city.text");
		dataBinder.registerBinding("payCondition", com.kingdee.eas.basedata.assistant.PayConditionInfo.class, this.prmtPayCondition, "data");
		dataBinder.registerBinding("usingStatus", com.kingdee.eas.basedata.master.cssp.UsingStatusEnum.class, this.comboBoxStatus, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.basedata.master.cssp.app.SupplierCompanyUIHandler";
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
        this.editData = (com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("isFreezePayment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountingClassification", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementCurrency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contactPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contactPersonPost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mobile", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postalcode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("freezeOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank.bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank.bankAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank.bankAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank.province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierBank.city", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usingStatus", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("isFreezePayment"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("accountingClassification.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("accountingClassification.id"));
        	sic.add(new SelectorItemInfo("accountingClassification.number"));
        	sic.add(new SelectorItemInfo("accountingClassification.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementCurrency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementCurrency.id"));
        	sic.add(new SelectorItemInfo("settlementCurrency.number"));
        	sic.add(new SelectorItemInfo("settlementCurrency.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("paymentType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("paymentType.id"));
        	sic.add(new SelectorItemInfo("paymentType.number"));
        	sic.add(new SelectorItemInfo("paymentType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
        sic.add(new SelectorItemInfo("contactPerson"));
        sic.add(new SelectorItemInfo("contactPersonPost"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("mobile"));
        sic.add(new SelectorItemInfo("postalcode"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("freezeOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("freezeOrgUnit.id"));
        	sic.add(new SelectorItemInfo("freezeOrgUnit.number"));
        	sic.add(new SelectorItemInfo("freezeOrgUnit.name"));
		}
    	sic.add(new SelectorItemInfo("supplierBank.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplierBank.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("supplierBank.bank"));
    	sic.add(new SelectorItemInfo("supplierBank.bankAccount"));
    	sic.add(new SelectorItemInfo("supplierBank.bankAddress"));
    	sic.add(new SelectorItemInfo("supplierBank.province"));
    	sic.add(new SelectorItemInfo("supplierBank.city"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payCondition.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payCondition.id"));
        	sic.add(new SelectorItemInfo("payCondition.number"));
        	sic.add(new SelectorItemInfo("payCondition.name"));
		}
        sic.add(new SelectorItemInfo("usingStatus"));
        return sic;
    }        
    	

    /**
     * output actionSupplierTradeRpt_actionPerformed method
     */
    public void actionSupplierTradeRpt_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddRowBank_actionPerformed method
     */
    public void actionAddRowBank_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelRowBank_actionPerformed method
     */
    public void actionDelRowBank_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSupplierTradeRpt(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSupplierTradeRpt() {
    	return false;
    }
	public RequestContext prepareActionAddRowBank(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRowBank() {
    	return false;
    }
	public RequestContext prepareActionDelRowBank(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelRowBank() {
    	return false;
    }

    /**
     * output ActionSupplierTradeRpt class
     */     
    protected class ActionSupplierTradeRpt extends ItemAction {     
    
        public ActionSupplierTradeRpt()
        {
            this(null);
        }

        public ActionSupplierTradeRpt(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSupplierTradeRpt.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplierTradeRpt.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSupplierTradeRpt.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierCompanyUI.this, "ActionSupplierTradeRpt", "actionSupplierTradeRpt_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRowBank class
     */     
    protected class ActionAddRowBank extends ItemAction {     
    
        public ActionAddRowBank()
        {
            this(null);
        }

        public ActionAddRowBank(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRowBank.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRowBank.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRowBank.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierCompanyUI.this, "ActionAddRowBank", "actionAddRowBank_actionPerformed", e);
        }
    }

    /**
     * output ActionDelRowBank class
     */     
    protected class ActionDelRowBank extends ItemAction {     
    
        public ActionDelRowBank()
        {
            this(null);
        }

        public ActionDelRowBank(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelRowBank.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRowBank.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRowBank.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierCompanyUI.this, "ActionDelRowBank", "actionDelRowBank_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.basedata.master.cssp.client", "SupplierCompanyUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.basedata.master.cssp.client.SupplierCompanyUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo objectValue = new com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblBank;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}