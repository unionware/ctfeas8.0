/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

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
public abstract class AbstractEvectionLoanBillEditUI extends com.kingdee.eas.cp.bc.client.LoanReqEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEvectionLoanBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntry;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDLabel mark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckAmountControl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane bgScrol;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer29;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer30;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer31;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer32;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer33;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer34;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer35;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isK3Paid;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayerAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promPayName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer36;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer37;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayerid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer38;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer39;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptPosition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPrior;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCurrencyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField FormattedTextFieldAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptSupportedObj;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptExpenseType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBillDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptBiller;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptPayMode;
    protected com.kingdee.bos.ctrl.swing.KDFilterTextField txtTel;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateReqDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateAuditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptUpdator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateUpdateDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextAreaCause;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextAreaDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateYjBack;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField FormattedTextFieldApprovedAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplierCompany;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountUsed;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountBalance;
    protected com.kingdee.bos.ctrl.swing.KDTextArea bgText;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayer;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextBankStr;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBankNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cBoxTravelType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox receiveObject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox contract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox payCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVoucherNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVoucherNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox returnState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReturnAmt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRcrdsOfLendAndRepay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewRcds;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBudget;
    protected com.kingdee.eas.cp.bc.EvectionLoanBillInfo editData = null;
    protected ActionViewRrcdsOfLendAndRepay actionViewRrcdsOfLendAndRepay = null;
    public final static String STATUS_CHECKING = "CHECKING";
    /**
     * output class constructor
     */
    public AbstractEvectionLoanBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEvectionLoanBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionTraceDown
        actionTraceDown.setEnabled(true);
        actionTraceDown.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionTraceDown.SHORT_DESCRIPTION");
        actionTraceDown.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceDown.LONG_DESCRIPTION");
        actionTraceDown.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceDown.NAME");
        actionTraceDown.putValue(ItemAction.NAME, _tempStr);
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionViewBudgetBalance
        actionViewBudgetBalance.setEnabled(true);
        actionViewBudgetBalance.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionViewBudgetBalance.SHORT_DESCRIPTION");
        actionViewBudgetBalance.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewBudgetBalance.LONG_DESCRIPTION");
        actionViewBudgetBalance.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewBudgetBalance.NAME");
        actionViewBudgetBalance.putValue(ItemAction.NAME, _tempStr);
         this.actionViewBudgetBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreatePayNotifyBill
        actionCreatePayNotifyBill.setEnabled(true);
        actionCreatePayNotifyBill.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionCreatePayNotifyBill.SHORT_DESCRIPTION");
        actionCreatePayNotifyBill.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreatePayNotifyBill.LONG_DESCRIPTION");
        actionCreatePayNotifyBill.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreatePayNotifyBill.NAME");
        actionCreatePayNotifyBill.putValue(ItemAction.NAME, _tempStr);
        this.actionCreatePayNotifyBill.setBindWorkFlow(true);
         this.actionCreatePayNotifyBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreateVoucher
        actionCreateVoucher.setEnabled(true);
        actionCreateVoucher.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionCreateVoucher.SHORT_DESCRIPTION");
        actionCreateVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateVoucher.LONG_DESCRIPTION");
        actionCreateVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateVoucher.NAME");
        actionCreateVoucher.putValue(ItemAction.NAME, _tempStr);
         this.actionCreateVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewRrcdsOfLendAndRepay
        this.actionViewRrcdsOfLendAndRepay = new ActionViewRrcdsOfLendAndRepay(this);
        getActionManager().registerAction("actionViewRrcdsOfLendAndRepay", actionViewRrcdsOfLendAndRepay);
         this.actionViewRrcdsOfLendAndRepay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.mark = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDCheckAmountControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bgScrol = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer29 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer30 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer31 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer32 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer33 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer34 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer35 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isK3Paid = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtPayName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPayerAccount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.promPayName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer36 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer37 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPayerid = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer38 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer39 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptApplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptPosition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPrior = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.bizPromptCurrencyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.FormattedTextFieldAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bizPromptSupportedObj = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptExpenseType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptBiller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptPayMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDFilterTextField();
        this.dateReqDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptUpdator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTextAreaCause = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTextAreaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.dateYjBack = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.FormattedTextFieldApprovedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bizPromptApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAmountUsed = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmountBalance = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bgText = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtPayer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTextBankStr = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtBankNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cBoxTravelType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.receiveObject = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.payCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtVoucherNum = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtVoucherNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.returnState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtReturnAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnViewRcrdsOfLendAndRepay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewRcds = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBudget = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
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
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.ctnEntry.setName("ctnEntry");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.mark.setName("mark");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.kDCheckAmountControl.setName("kDCheckAmountControl");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.bgScrol.setName("bgScrol");
        this.kDLabelContainer29.setName("kDLabelContainer29");
        this.kDLabelContainer30.setName("kDLabelContainer30");
        this.kDLabelContainer31.setName("kDLabelContainer31");
        this.kDLabelContainer32.setName("kDLabelContainer32");
        this.kDLabelContainer33.setName("kDLabelContainer33");
        this.kDLabelContainer34.setName("kDLabelContainer34");
        this.kDLabelContainer35.setName("kDLabelContainer35");
        this.isK3Paid.setName("isK3Paid");
        this.txtPayName.setName("txtPayName");
        this.txtPayerAccount.setName("txtPayerAccount");
        this.promPayName.setName("promPayName");
        this.kDLabelContainer36.setName("kDLabelContainer36");
        this.kDLabelContainer37.setName("kDLabelContainer37");
        this.txtPayerid.setName("txtPayerid");
        this.kDLabelContainer38.setName("kDLabelContainer38");
        this.kDLabelContainer39.setName("kDLabelContainer39");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.bizPromptApplier.setName("bizPromptApplier");
        this.bizPromptOrgUnit.setName("bizPromptOrgUnit");
        this.bizPromptPosition.setName("bizPromptPosition");
        this.bizPromptCompany.setName("bizPromptCompany");
        this.comboPrior.setName("comboPrior");
        this.bizPromptCurrencyType.setName("bizPromptCurrencyType");
        this.bizPromptCostedDept.setName("bizPromptCostedDept");
        this.FormattedTextFieldAmount.setName("FormattedTextFieldAmount");
        this.bizPromptSupportedObj.setName("bizPromptSupportedObj");
        this.bizPromptExpenseType.setName("bizPromptExpenseType");
        this.dateBillDate.setName("dateBillDate");
        this.bizPromptBiller.setName("bizPromptBiller");
        this.bizPromptPayMode.setName("bizPromptPayMode");
        this.txtTel.setName("txtTel");
        this.dateReqDate.setName("dateReqDate");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.dateAuditDate.setName("dateAuditDate");
        this.bizPromptUpdator.setName("bizPromptUpdator");
        this.dateUpdateDate.setName("dateUpdateDate");
        this.kdtEntries.setName("kdtEntries");
        this.kDTextAreaCause.setName("kDTextAreaCause");
        this.kDTextAreaDescription.setName("kDTextAreaDescription");
        this.dateYjBack.setName("dateYjBack");
        this.FormattedTextFieldApprovedAmount.setName("FormattedTextFieldApprovedAmount");
        this.bizPromptApplierCompany.setName("bizPromptApplierCompany");
        this.txtAmountUsed.setName("txtAmountUsed");
        this.txtAmountBalance.setName("txtAmountBalance");
        this.bgText.setName("bgText");
        this.prmtPayer.setName("prmtPayer");
        this.kDTextBankStr.setName("kDTextBankStr");
        this.prmtBankNumber.setName("prmtBankNumber");
        this.cBoxTravelType.setName("cBoxTravelType");
        this.receiveObject.setName("receiveObject");
        this.contract.setName("contract");
        this.payCompany.setName("payCompany");
        this.prmtVoucherNum.setName("prmtVoucherNum");
        this.txtVoucherNumber.setName("txtVoucherNumber");
        this.returnState.setName("returnState");
        this.txtReturnAmt.setName("txtReturnAmt");
        this.btnViewRcrdsOfLendAndRepay.setName("btnViewRcrdsOfLendAndRepay");
        this.menuItemViewRcds.setName("menuItemViewRcds");
        this.menuItemViewBudget.setName("menuItemViewBudget");
        // CoreUI		
        this.setPreferredSize(new Dimension(1000,800));		
        this.btnPrintPreview.setEnabled(true);		
        this.btnPrintPreview.setVisible(true);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.btnCreateTo.setText(resHelper.getString("btnCreateTo.text"));		
        this.separatorFW8.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.btnPrintImage.setVisible(false);		
        this.btnCreatePayNotifyBill.setVisible(false);		
        this.btnCreateVoucher.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setEnabled(false);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(60);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(60);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setEnabled(false);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);		
        this.kDLabelContainer17.setEnabled(false);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelUnderline(true);		
        this.kDLabelContainer18.setBoundLabelLength(100);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelUnderline(true);		
        this.kDLabelContainer19.setBoundLabelLength(100);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelUnderline(true);		
        this.kDLabelContainer20.setBoundLabelLength(100);
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelUnderline(true);		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setEnabled(false);
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);		
        this.kDLabelContainer22.setEnabled(false);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);		
        this.kDLabelContainer23.setEnabled(false);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelUnderline(true);		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setEnabled(false);
        // ctnEntry		
        this.ctnEntry.setTitle(resHelper.getString("ctnEntry.title"));		
        this.ctnEntry.setTitleStyle(2);		
        this.ctnEntry.setEnableActive(false);
        // kDScrollPane1
        // kDScrollPane2
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelLength(100);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(110);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);
        // mark		
        this.mark.setVisible(false);
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelLength(100);		
        this.kDLabelContainer28.setBoundLabelUnderline(true);
        // kDCheckAmountControl		
        this.kDCheckAmountControl.setText(resHelper.getString("kDCheckAmountControl.text"));		
        this.kDCheckAmountControl.setEnabled(false);
        this.kDCheckAmountControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDCheckAmountControl_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelLength(100);		
        this.kDLabelContainer26.setBoundLabelUnderline(true);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelUnderline(true);		
        this.kDLabelContainer27.setBoundLabelLength(100);
        // bgScrol		
        this.bgScrol.setVisible(false);		
        this.bgScrol.setAutoscrolls(true);
        // kDLabelContainer29		
        this.kDLabelContainer29.setBoundLabelText(resHelper.getString("kDLabelContainer29.boundLabelText"));		
        this.kDLabelContainer29.setBoundLabelLength(60);		
        this.kDLabelContainer29.setBoundLabelUnderline(true);
        // kDLabelContainer30		
        this.kDLabelContainer30.setBoundLabelText(resHelper.getString("kDLabelContainer30.boundLabelText"));		
        this.kDLabelContainer30.setBoundLabelLength(60);		
        this.kDLabelContainer30.setBoundLabelUnderline(true);
        // kDLabelContainer31		
        this.kDLabelContainer31.setBoundLabelText(resHelper.getString("kDLabelContainer31.boundLabelText"));		
        this.kDLabelContainer31.setBoundLabelLength(60);		
        this.kDLabelContainer31.setBoundLabelUnderline(true);
        // kDLabelContainer32		
        this.kDLabelContainer32.setBoundLabelText(resHelper.getString("kDLabelContainer32.boundLabelText"));		
        this.kDLabelContainer32.setBoundLabelLength(100);		
        this.kDLabelContainer32.setBoundLabelUnderline(true);
        // kDLabelContainer33		
        this.kDLabelContainer33.setBoundLabelText(resHelper.getString("kDLabelContainer33.boundLabelText"));		
        this.kDLabelContainer33.setBoundLabelLength(60);		
        this.kDLabelContainer33.setBoundLabelUnderline(true);
        // kDLabelContainer34		
        this.kDLabelContainer34.setBoundLabelText(resHelper.getString("kDLabelContainer34.boundLabelText"));		
        this.kDLabelContainer34.setBoundLabelUnderline(true);		
        this.kDLabelContainer34.setBoundLabelLength(100);
        // kDLabelContainer35		
        this.kDLabelContainer35.setBoundLabelText(resHelper.getString("kDLabelContainer35.boundLabelText"));		
        this.kDLabelContainer35.setBoundLabelLength(100);		
        this.kDLabelContainer35.setBoundLabelUnderline(true);
        // isK3Paid		
        this.isK3Paid.setText(resHelper.getString("isK3Paid.text"));		
        this.isK3Paid.setEnabled(false);
        // txtPayName		
        this.txtPayName.setEnabled(false);		
        this.txtPayName.setVisible(false);
        // txtPayerAccount		
        this.txtPayerAccount.setEnabled(false);		
        this.txtPayerAccount.setVisible(false);
        // promPayName		
        this.promPayName.setQueryInfo("com.kingdee.eas.cp.bc.app.CollectionAccountQuery");		
        this.promPayName.setCommitFormat("$number$");		
        this.promPayName.setEditFormat("$name$");		
        this.promPayName.setDisplayFormat("$name$");		
        this.promPayName.setEditable(true);		
        this.promPayName.setVisible(false);
        this.promPayName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    promPayName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer36		
        this.kDLabelContainer36.setBoundLabelText(resHelper.getString("kDLabelContainer36.boundLabelText"));		
        this.kDLabelContainer36.setBoundLabelLength(100);		
        this.kDLabelContainer36.setBoundLabelUnderline(true);
        // kDLabelContainer37		
        this.kDLabelContainer37.setBoundLabelText(resHelper.getString("kDLabelContainer37.boundLabelText"));		
        this.kDLabelContainer37.setBoundLabelLength(100);		
        this.kDLabelContainer37.setBoundLabelUnderline(true);
        // txtPayerid		
        this.txtPayerid.setVisible(false);
        // kDLabelContainer38		
        this.kDLabelContainer38.setBoundLabelText(resHelper.getString("kDLabelContainer38.boundLabelText"));		
        this.kDLabelContainer38.setBoundLabelLength(100);		
        this.kDLabelContainer38.setBoundLabelUnderline(true);		
        this.kDLabelContainer38.setEnabled(false);
        // kDLabelContainer39		
        this.kDLabelContainer39.setBoundLabelText(resHelper.getString("kDLabelContainer39.boundLabelText"));		
        this.kDLabelContainer39.setBoundLabelLength(100);		
        this.kDLabelContainer39.setBoundLabelUnderline(true);		
        this.kDLabelContainer39.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(80);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(60);
        // bizPromptApplier		
        this.bizPromptApplier.setCommitFormat("$number$");		
        this.bizPromptApplier.setDisplayFormat("$name$");		
        this.bizPromptApplier.setEditFormat("$number$");		
        this.bizPromptApplier.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.bizPromptApplier.setEditable(true);
        this.bizPromptApplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizPromptApplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // bizPromptOrgUnit		
        this.bizPromptOrgUnit.setDisplayFormat("$name$");		
        this.bizPromptOrgUnit.setEditFormat("$number$");		
        this.bizPromptOrgUnit.setCommitFormat("$number$");		
        this.bizPromptOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.bizPromptOrgUnit.setEditable(true);
        // bizPromptPosition		
        this.bizPromptPosition.setCommitFormat("$number$");		
        this.bizPromptPosition.setDisplayFormat("$name$");		
        this.bizPromptPosition.setEditFormat("$number$");		
        this.bizPromptPosition.setEditable(true);
        // bizPromptCompany		
        this.bizPromptCompany.setDisplayFormat("$name$");		
        this.bizPromptCompany.setEditFormat("$number$");		
        this.bizPromptCompany.setCommitFormat("$number$");		
        this.bizPromptCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.bizPromptCompany.setEditable(true);
        // comboPrior		
        this.comboPrior.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.PriorEnum").toArray());		
        this.comboPrior.setRequired(true);
        // bizPromptCurrencyType		
        this.bizPromptCurrencyType.setDisplayFormat("$name$");		
        this.bizPromptCurrencyType.setEditFormat("$number$");		
        this.bizPromptCurrencyType.setCommitFormat("$number$");		
        this.bizPromptCurrencyType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.bizPromptCurrencyType.setEditable(true);		
        this.bizPromptCurrencyType.setEnabled(false);
        // bizPromptCostedDept		
        this.bizPromptCostedDept.setCommitFormat("$number$");		
        this.bizPromptCostedDept.setDisplayFormat("$name$");		
        this.bizPromptCostedDept.setEditFormat("$number$");		
        this.bizPromptCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
        this.bizPromptCostedDept.setEditable(true);
        // FormattedTextFieldAmount		
        this.FormattedTextFieldAmount.setText(resHelper.getString("FormattedTextFieldAmount.text"));		
        this.FormattedTextFieldAmount.setEnabled(false);		
        this.FormattedTextFieldAmount.setRequired(true);		
        this.FormattedTextFieldAmount.setPrecision(2);
        // bizPromptSupportedObj		
        this.bizPromptSupportedObj.setDisplayFormat("$name$");		
        this.bizPromptSupportedObj.setEditFormat("$number$");		
        this.bizPromptSupportedObj.setCommitFormat("$number$");		
        this.bizPromptSupportedObj.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CostObjectQuery");		
        this.bizPromptSupportedObj.setEditable(true);
        // bizPromptExpenseType		
        this.bizPromptExpenseType.setDisplayFormat("$name$");		
        this.bizPromptExpenseType.setEditFormat("$number$");		
        this.bizPromptExpenseType.setCommitFormat("$id$");		
        this.bizPromptExpenseType.setQueryInfo("com.kingdee.eas.cp.bc.app.F7OperationTypeQuery");		
        this.bizPromptExpenseType.setEditable(true);
        // dateBillDate		
        this.dateBillDate.setEnabled(false);
        // bizPromptBiller		
        this.bizPromptBiller.setEnabled(false);		
        this.bizPromptBiller.setDisplayFormat("$name$");
        // bizPromptPayMode
        // txtTel		
        this.txtTel.setFilterType(1);		
        this.txtTel.setMaxLength(50);		
        this.txtTel.setValidCharacters("+-()");
        // dateReqDate
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setDisplayFormat("$name$");
        // dateAuditDate		
        this.dateAuditDate.setEnabled(false);
        // bizPromptUpdator		
        this.bizPromptUpdator.setEnabled(false);		
        this.bizPromptUpdator.setDisplayFormat("$name$");
        // dateUpdateDate		
        this.dateUpdateDate.setEnabled(false);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>%{yyyy-MM-dd}t</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%{yyyy-MM-dd}t</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"costDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"expenseType\" t:width=\"75\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"startDate\" t:width=\"81\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"endDate\" t:width=\"81\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"from\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"7\" /><t:Column t:key=\"to\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"8\" /><t:Column t:key=\"vehicle\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"9\" /><t:Column t:key=\"currencyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"10\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"bussesExpense\" t:width=\"75\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"taxiExpense\" t:width=\"75\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"hotelExpense\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"otherExpense\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"budgetAmountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"budgetAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"amountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"approvedAmountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"approvedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"amountUsedOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"amountUsed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"amountBalanceOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"amountBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"convertMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"exchangeRatePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{costDept}</t:Cell><t:Cell>$Resource{expenseType}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{from}</t:Cell><t:Cell>$Resource{to}</t:Cell><t:Cell>$Resource{vehicle}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{bussesExpense}</t:Cell><t:Cell>$Resource{taxiExpense}</t:Cell><t:Cell>$Resource{hotelExpense}</t:Cell><t:Cell>$Resource{otherExpense}</t:Cell><t:Cell>$Resource{budgetAmountOri}</t:Cell><t:Cell>$Resource{budgetAmount}</t:Cell><t:Cell>$Resource{amountOri}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{approvedAmountOri}</t:Cell><t:Cell>$Resource{approvedAmount}</t:Cell><t:Cell>$Resource{amountUsedOri}</t:Cell><t:Cell>$Resource{amountUsed}</t:Cell><t:Cell>$Resource{amountBalanceOri}</t:Cell><t:Cell>$Resource{amountBalance}</t:Cell><t:Cell>$Resource{convertMode}</t:Cell><t:Cell>$Resource{exchangeRatePrecision}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));
        this.kdtEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntries.putBindContents("editData",new String[] {"id","project","costedDept","expenseType","person","startDate","endDate","from","to","vehicle","currencyType","exchangeRate","bussesExpense","taxiExpense","hotelExpense","otherExpense","budgetAmountOri","budgetAmount","amountOri","amount","amountApprovedOri","amountApproved","amountUsedOri","amountUsed","amountBalanceOri","amountBalance","convertMode","exchangeRatePrecision"});


        this.kdtEntries.checkParsed();
        final KDBizPromptBox kdtEntries_project_PromptBox = new KDBizPromptBox();
        kdtEntries_project_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CustomerProjectQuery");
        kdtEntries_project_PromptBox.setVisible(true);
        kdtEntries_project_PromptBox.setEditable(true);
        kdtEntries_project_PromptBox.setDisplayFormat("$number$");
        kdtEntries_project_PromptBox.setEditFormat("$number$");
        kdtEntries_project_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_project_CellEditor = new KDTDefaultCellEditor(kdtEntries_project_PromptBox);
        this.kdtEntries.getColumn("project").setEditor(kdtEntries_project_CellEditor);
        ObjectValueRender kdtEntries_project_OVR = new ObjectValueRender();
        kdtEntries_project_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("project").setRenderer(kdtEntries_project_OVR);
        final KDBizPromptBox kdtEntries_costDept_PromptBox = new KDBizPromptBox();
        kdtEntries_costDept_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");
        kdtEntries_costDept_PromptBox.setVisible(true);
        kdtEntries_costDept_PromptBox.setEditable(true);
        kdtEntries_costDept_PromptBox.setDisplayFormat("$number$");
        kdtEntries_costDept_PromptBox.setEditFormat("$number$");
        kdtEntries_costDept_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_costDept_CellEditor = new KDTDefaultCellEditor(kdtEntries_costDept_PromptBox);
        this.kdtEntries.getColumn("costDept").setEditor(kdtEntries_costDept_CellEditor);
        ObjectValueRender kdtEntries_costDept_OVR = new ObjectValueRender();
        kdtEntries_costDept_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("costDept").setRenderer(kdtEntries_costDept_OVR);
        final KDBizPromptBox kdtEntries_expenseType_PromptBox = new KDBizPromptBox();
        kdtEntries_expenseType_PromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.ExpenseTypeQuery");
        kdtEntries_expenseType_PromptBox.setVisible(true);
        kdtEntries_expenseType_PromptBox.setEditable(true);
        kdtEntries_expenseType_PromptBox.setDisplayFormat("$number$");
        kdtEntries_expenseType_PromptBox.setEditFormat("$number$");
        kdtEntries_expenseType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_expenseType_CellEditor = new KDTDefaultCellEditor(kdtEntries_expenseType_PromptBox);
        this.kdtEntries.getColumn("expenseType").setEditor(kdtEntries_expenseType_CellEditor);
        ObjectValueRender kdtEntries_expenseType_OVR = new ObjectValueRender();
        kdtEntries_expenseType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("expenseType").setRenderer(kdtEntries_expenseType_OVR);
        final KDBizPromptBox kdtEntries_person_PromptBox = new KDBizPromptBox();
        kdtEntries_person_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtEntries_person_PromptBox.setVisible(true);
        kdtEntries_person_PromptBox.setEditable(true);
        kdtEntries_person_PromptBox.setDisplayFormat("$number$");
        kdtEntries_person_PromptBox.setEditFormat("$number$");
        kdtEntries_person_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_person_CellEditor = new KDTDefaultCellEditor(kdtEntries_person_PromptBox);
        this.kdtEntries.getColumn("person").setEditor(kdtEntries_person_CellEditor);
        ObjectValueRender kdtEntries_person_OVR = new ObjectValueRender();
        kdtEntries_person_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("person").setRenderer(kdtEntries_person_OVR);
        KDComboBox kdtEntries_vehicle_ComboBox = new KDComboBox();
        kdtEntries_vehicle_ComboBox.setName("kdtEntries_vehicle_ComboBox");
        kdtEntries_vehicle_ComboBox.setVisible(true);
        kdtEntries_vehicle_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.VehicleEnum").toArray());
        KDTDefaultCellEditor kdtEntries_vehicle_CellEditor = new KDTDefaultCellEditor(kdtEntries_vehicle_ComboBox);
        this.kdtEntries.getColumn("vehicle").setEditor(kdtEntries_vehicle_CellEditor);
        final KDBizPromptBox kdtEntries_currencyType_PromptBox = new KDBizPromptBox();
        kdtEntries_currencyType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        kdtEntries_currencyType_PromptBox.setVisible(true);
        kdtEntries_currencyType_PromptBox.setEditable(true);
        kdtEntries_currencyType_PromptBox.setDisplayFormat("$number$");
        kdtEntries_currencyType_PromptBox.setEditFormat("$number$");
        kdtEntries_currencyType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_currencyType_CellEditor = new KDTDefaultCellEditor(kdtEntries_currencyType_PromptBox);
        this.kdtEntries.getColumn("currencyType").setEditor(kdtEntries_currencyType_CellEditor);
        ObjectValueRender kdtEntries_currencyType_OVR = new ObjectValueRender();
        kdtEntries_currencyType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("currencyType").setRenderer(kdtEntries_currencyType_OVR);
        KDComboBox kdtEntries_convertMode_ComboBox = new KDComboBox();
        kdtEntries_convertMode_ComboBox.setName("kdtEntries_convertMode_ComboBox");
        kdtEntries_convertMode_ComboBox.setVisible(true);
        kdtEntries_convertMode_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.assistant.ConvertModeEnum").toArray());
        KDTDefaultCellEditor kdtEntries_convertMode_CellEditor = new KDTDefaultCellEditor(kdtEntries_convertMode_ComboBox);
        this.kdtEntries.getColumn("convertMode").setEditor(kdtEntries_convertMode_CellEditor);
        // kDTextAreaCause		
        this.kDTextAreaCause.setText(resHelper.getString("kDTextAreaCause.text"));		
        this.kDTextAreaCause.setMaxLength(20);
        // kDTextAreaDescription		
        this.kDTextAreaDescription.setText(resHelper.getString("kDTextAreaDescription.text"));		
        this.kDTextAreaDescription.setMaxLength(255);
        // dateYjBack
        // FormattedTextFieldApprovedAmount		
        this.FormattedTextFieldApprovedAmount.setEnabled(false);		
        this.FormattedTextFieldApprovedAmount.setPrecision(2);
        // bizPromptApplierCompany		
        this.bizPromptApplierCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.bizPromptApplierCompany.setRequired(true);
        // txtAmountUsed		
        this.txtAmountUsed.setPrecision(2);
        // txtAmountBalance		
        this.txtAmountBalance.setPrecision(2);
        // bgText		
        this.bgText.setMaxLength(400);		
        this.bgText.setEditable(false);
        // prmtPayer		
        this.prmtPayer.setRequired(true);
        this.prmtPayer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPayer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTextBankStr		
        this.kDTextBankStr.setEnabled(false);
        // prmtBankNumber		
        this.prmtBankNumber.setCommitFormat("$number$");		
        this.prmtBankNumber.setDisplayFormat("$name$");		
        this.prmtBankNumber.setEditFormat("$number$");
        this.prmtBankNumber.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBankNumber_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cBoxTravelType
        // receiveObject		
        this.receiveObject.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.ReceiveObjectEnum").toArray());
        this.receiveObject.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    receiveObject_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contract		
        this.contract.setEnabled(false);
        // payCompany		
        this.payCompany.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");
        // prmtVoucherNum
        // txtVoucherNumber		
        this.txtVoucherNumber.setEnabled(false);
        // returnState		
        this.returnState.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.ReturnStateEnum").toArray());		
        this.returnState.setEnabled(false);
        // txtReturnAmt		
        this.txtReturnAmt.setDataType(1);		
        this.txtReturnAmt.setPrecision(2);		
        this.txtReturnAmt.setEnabled(false);
        // btnViewRcrdsOfLendAndRepay
        this.btnViewRcrdsOfLendAndRepay.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRcrdsOfLendAndRepay.setText(resHelper.getString("btnViewRcrdsOfLendAndRepay.text"));		
        this.btnViewRcrdsOfLendAndRepay.setToolTipText(resHelper.getString("btnViewRcrdsOfLendAndRepay.toolTipText"));
        // menuItemViewRcds
        this.menuItemViewRcds.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewRcds.setText(resHelper.getString("menuItemViewRcds.text"));		
        this.menuItemViewRcds.setMnemonic(82);
        // menuItemViewBudget
        this.menuItemViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudgetBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBudget.setText(resHelper.getString("menuItemViewBudget.text"));		
        this.menuItemViewBudget.setMnemonic(66);
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
        this.setBounds(new Rectangle(0, 0, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 800, 600));
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 510, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 10, 510, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 32, 240, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(550, 10, 240, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(550, 10, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(550, 32, 240, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(550, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(10, 76, 240, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(10, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(280, 76, 240, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(280, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(10, 54, 240, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(10, 98, 240, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(280, 54, 240, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(280, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(550, 120, 240, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(550, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(550, 76, 240, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(550, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer13.setBounds(new Rectangle(280, 120, 240, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(280, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(10, 228, 240, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(10, 228, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer15.setBounds(new Rectangle(10, 479, 270, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(10, 479, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(549, 568, 240, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(549, 568, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer17.setBounds(new Rectangle(549, 544, 240, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(549, 544, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer18.setBounds(new Rectangle(280, 98, 240, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(280, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer19.setBounds(new Rectangle(10, 120, 240, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(10, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer20.setBounds(new Rectangle(280, 32, 240, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(280, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer21.setBounds(new Rectangle(10, 544, 240, 19));
        this.add(kDLabelContainer21, new KDLayout.Constraints(10, 544, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer22.setBounds(new Rectangle(10, 568, 240, 19));
        this.add(kDLabelContainer22, new KDLayout.Constraints(10, 568, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer23.setBounds(new Rectangle(279, 544, 240, 19));
        this.add(kDLabelContainer23, new KDLayout.Constraints(279, 544, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer24.setBounds(new Rectangle(279, 568, 240, 19));
        this.add(kDLabelContainer24, new KDLayout.Constraints(279, 568, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ctnEntry.setBounds(new Rectangle(10, 302, 780, 153));
        this.add(ctnEntry, new KDLayout.Constraints(10, 302, 780, 153, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(10, 249, 780, 24));
        this.add(kDScrollPane1, new KDLayout.Constraints(10, 249, 780, 24, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane2.setBounds(new Rectangle(10, 499, 780, 43));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 499, 780, 43, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer10.setBounds(new Rectangle(550, 98, 240, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(550, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer25.setBounds(new Rectangle(550, 142, 240, 19));
        this.add(kDLabelContainer25, new KDLayout.Constraints(550, 142, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        mark.setBounds(new Rectangle(535, 215, 241, 19));
        this.add(mark, new KDLayout.Constraints(535, 215, 241, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer28.setBounds(new Rectangle(550, 54, 240, 19));
        this.add(kDLabelContainer28, new KDLayout.Constraints(550, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDCheckAmountControl.setBounds(new Rectangle(10, 142, 100, 19));
        this.add(kDCheckAmountControl, new KDLayout.Constraints(10, 142, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer26.setBounds(new Rectangle(280, 184, 240, 19));
        this.add(kDLabelContainer26, new KDLayout.Constraints(280, 184, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer27.setBounds(new Rectangle(10, 184, 240, 19));
        this.add(kDLabelContainer27, new KDLayout.Constraints(10, 184, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        bgScrol.setBounds(new Rectangle(10, 275, 780, 25));
        this.add(bgScrol, new KDLayout.Constraints(10, 275, 780, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer29.setBounds(new Rectangle(197, 459, 200, 19));
        this.add(kDLabelContainer29, new KDLayout.Constraints(197, 459, 200, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer30.setBounds(new Rectangle(416, 459, 196, 19));
        this.add(kDLabelContainer30, new KDLayout.Constraints(416, 459, 196, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer31.setBounds(new Rectangle(624, 459, 167, 19));
        this.add(kDLabelContainer31, new KDLayout.Constraints(624, 459, 167, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer32.setBounds(new Rectangle(280, 140, 240, 19));
        this.add(kDLabelContainer32, new KDLayout.Constraints(280, 140, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer33.setBounds(new Rectangle(10, 459, 160, 19));
        this.add(kDLabelContainer33, new KDLayout.Constraints(10, 459, 160, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer34.setBounds(new Rectangle(10, 162, 240, 19));
        this.add(kDLabelContainer34, new KDLayout.Constraints(10, 162, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer35.setBounds(new Rectangle(281, 162, 240, 19));
        this.add(kDLabelContainer35, new KDLayout.Constraints(281, 162, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        isK3Paid.setBounds(new Rectangle(115, 140, 131, 19));
        this.add(isK3Paid, new KDLayout.Constraints(115, 140, 131, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtPayName.setBounds(new Rectangle(406, 479, 118, 19));
        this.add(txtPayName, new KDLayout.Constraints(406, 479, 118, 19, 0));
        txtPayerAccount.setBounds(new Rectangle(647, 474, 107, 19));
        this.add(txtPayerAccount, new KDLayout.Constraints(647, 474, 107, 19, 0));
        promPayName.setBounds(new Rectangle(241, 474, 140, 19));
        this.add(promPayName, new KDLayout.Constraints(241, 474, 140, 19, 0));
        kDLabelContainer36.setBounds(new Rectangle(550, 164, 240, 19));
        this.add(kDLabelContainer36, new KDLayout.Constraints(550, 164, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer37.setBounds(new Rectangle(551, 184, 240, 19));
        this.add(kDLabelContainer37, new KDLayout.Constraints(551, 184, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        txtPayerid.setBounds(new Rectangle(520, 212, 170, 19));
        this.add(txtPayerid, new KDLayout.Constraints(520, 212, 170, 19, 0));
        kDLabelContainer38.setBounds(new Rectangle(10, 206, 240, 19));
        this.add(kDLabelContainer38, new KDLayout.Constraints(10, 206, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer39.setBounds(new Rectangle(280, 206, 240, 19));
        this.add(kDLabelContainer39, new KDLayout.Constraints(280, 206, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(bizPromptApplier);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(bizPromptOrgUnit);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptPosition);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(bizPromptCompany);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(comboPrior);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptCurrencyType);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(bizPromptCostedDept);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(FormattedTextFieldAmount);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(bizPromptSupportedObj);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(bizPromptExpenseType);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(dateBillDate);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(bizPromptBiller);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(bizPromptPayMode);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtTel);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(dateReqDate);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(dateAuditDate);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(bizPromptUpdator);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(dateUpdateDate);
        //ctnEntry
ctnEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntry.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTextAreaCause, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(kDTextAreaDescription, null);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(dateYjBack);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(FormattedTextFieldApprovedAmount);
        //kDLabelContainer28
        kDLabelContainer28.setBoundEditor(bizPromptApplierCompany);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(txtAmountUsed);
        //kDLabelContainer27
        kDLabelContainer27.setBoundEditor(txtAmountBalance);
        //bgScrol
        bgScrol.getViewport().add(bgText, null);
        //kDLabelContainer29
        kDLabelContainer29.setBoundEditor(prmtPayer);
        //kDLabelContainer30
        kDLabelContainer30.setBoundEditor(kDTextBankStr);
        //kDLabelContainer31
        kDLabelContainer31.setBoundEditor(prmtBankNumber);
        //kDLabelContainer32
        kDLabelContainer32.setBoundEditor(cBoxTravelType);
        //kDLabelContainer33
        kDLabelContainer33.setBoundEditor(receiveObject);
        //kDLabelContainer34
        kDLabelContainer34.setBoundEditor(contract);
        //kDLabelContainer35
        kDLabelContainer35.setBoundEditor(payCompany);
        //kDLabelContainer36
        kDLabelContainer36.setBoundEditor(prmtVoucherNum);
        //kDLabelContainer37
        kDLabelContainer37.setBoundEditor(txtVoucherNumber);
        //kDLabelContainer38
        kDLabelContainer38.setBoundEditor(returnState);
        //kDLabelContainer39
        kDLabelContainer39.setBoundEditor(txtReturnAmt);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemUdateReqDate);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(kDSeparator5);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewRcds);
        menuView.add(menuItemViewBudget);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(MenuItemSuspenseAcc);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnPrintImage);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW10);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnViewBudgetBalance);
        this.toolBar.add(btnViewRcrdsOfLendAndRepay);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreatePayNotifyBill);
        this.toolBar.add(btnCreateVoucher);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW6);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isK3Paid", boolean.class, this.isK3Paid, "selected");
		dataBinder.registerBinding("payerName", String.class, this.txtPayName, "text");
		dataBinder.registerBinding("payerAccount", String.class, this.txtPayerAccount, "text");
		dataBinder.registerBinding("CollectionAccount", com.kingdee.eas.cp.bc.CollectionAccountInfo.class, this.promPayName, "data");
		dataBinder.registerBinding("peyerID", String.class, this.txtPayerid, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("applier", com.kingdee.eas.basedata.person.PersonInfo.class, this.bizPromptApplier, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.bizPromptOrgUnit, "data");
		dataBinder.registerBinding("position", com.kingdee.eas.basedata.org.PositionInfo.class, this.bizPromptPosition, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptCompany, "data");
		dataBinder.registerBinding("prior", com.kingdee.eas.cp.bc.PriorEnum.class, this.comboPrior, "selectedItem");
		dataBinder.registerBinding("currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.bizPromptCurrencyType, "data");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.bizPromptCostedDept, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.FormattedTextFieldAmount, "value");
		dataBinder.registerBinding("supportedObj", com.kingdee.eas.basedata.assistant.CostObjectInfo.class, this.bizPromptSupportedObj, "data");
		dataBinder.registerBinding("operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.bizPromptExpenseType, "data");
		dataBinder.registerBinding("billDate", java.util.Date.class, this.dateBillDate, "value");
		dataBinder.registerBinding("biller", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptBiller, "data");
		dataBinder.registerBinding("payMode", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.bizPromptPayMode, "data");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "stringValue");
		dataBinder.registerBinding("bizReqDate", java.util.Date.class, this.dateReqDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.dateAuditDate, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptUpdator, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.dateUpdateDate, "value");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries.startDate", java.util.Date.class, this.kdtEntries, "startDate.text");
		dataBinder.registerBinding("entries.from", String.class, this.kdtEntries, "from.text");
		dataBinder.registerBinding("entries.endDate", java.util.Date.class, this.kdtEntries, "endDate.text");
		dataBinder.registerBinding("entries.to", String.class, this.kdtEntries, "to.text");
		dataBinder.registerBinding("entries.vehicle", com.kingdee.eas.cp.bc.VehicleEnum.class, this.kdtEntries, "vehicle.text");
		dataBinder.registerBinding("entries.taxiExpense", java.math.BigDecimal.class, this.kdtEntries, "taxiExpense.text");
		dataBinder.registerBinding("entries.bussesExpense", java.math.BigDecimal.class, this.kdtEntries, "bussesExpense.text");
		dataBinder.registerBinding("entries.hotelExpense", java.math.BigDecimal.class, this.kdtEntries, "hotelExpense.text");
		dataBinder.registerBinding("entries.otherExpense", java.math.BigDecimal.class, this.kdtEntries, "otherExpense.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.cp.bc.EvectionLoanBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.expenseType", com.kingdee.eas.cp.bc.ExpenseTypeInfo.class, this.kdtEntries, "expenseType.text");
		dataBinder.registerBinding("entries.budgetAmount", java.math.BigDecimal.class, this.kdtEntries, "budgetAmount.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.amountApproved", java.math.BigDecimal.class, this.kdtEntries, "approvedAmount.text");
		dataBinder.registerBinding("entries.amountUsed", java.math.BigDecimal.class, this.kdtEntries, "amountUsed.text");
		dataBinder.registerBinding("entries.amountBalance", java.math.BigDecimal.class, this.kdtEntries, "amountBalance.text");
		dataBinder.registerBinding("entries.currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.kdtEntries, "currencyType.text");
		dataBinder.registerBinding("entries.exchangeRate", java.math.BigDecimal.class, this.kdtEntries, "exchangeRate.text");
		dataBinder.registerBinding("entries.budgetAmountOri", java.math.BigDecimal.class, this.kdtEntries, "budgetAmountOri.text");
		dataBinder.registerBinding("entries.amountOri", java.math.BigDecimal.class, this.kdtEntries, "amountOri.text");
		dataBinder.registerBinding("entries.amountApprovedOri", java.math.BigDecimal.class, this.kdtEntries, "approvedAmountOri.text");
		dataBinder.registerBinding("entries.convertMode", com.kingdee.eas.basedata.assistant.ConvertModeEnum.class, this.kdtEntries, "convertMode.text");
		dataBinder.registerBinding("entries.exchangeRatePrecision", int.class, this.kdtEntries, "exchangeRatePrecision.text");
		dataBinder.registerBinding("entries.project", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.kdtEntries, "project.text");
		dataBinder.registerBinding("entries.costedDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.kdtEntries, "costDept.text");
		dataBinder.registerBinding("entries.amountUsedOri", java.math.BigDecimal.class, this.kdtEntries, "amountUsedOri.text");
		dataBinder.registerBinding("entries.amountBalanceOri", java.math.BigDecimal.class, this.kdtEntries, "amountBalanceOri.text");
		dataBinder.registerBinding("entries.person", com.kingdee.eas.basedata.person.PersonInfo.class, this.kdtEntries, "person.text");
		dataBinder.registerBinding("cause", String.class, this.kDTextAreaCause, "text");
		dataBinder.registerBinding("description", String.class, this.kDTextAreaDescription, "text");
		dataBinder.registerBinding("foreseeDate", java.util.Date.class, this.dateYjBack, "value");
		dataBinder.registerBinding("amountApproved", java.math.BigDecimal.class, this.FormattedTextFieldApprovedAmount, "value");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptApplierCompany, "data");
		dataBinder.registerBinding("amountUsed", java.math.BigDecimal.class, this.txtAmountUsed, "value");
		dataBinder.registerBinding("amountBalance", java.math.BigDecimal.class, this.txtAmountBalance, "value");
		dataBinder.registerBinding("payerBank", String.class, this.kDTextBankStr, "text");
		dataBinder.registerBinding("travelType", com.kingdee.eas.cp.bc.TravelTypeInfo.class, this.cBoxTravelType, "selectedItem");
		dataBinder.registerBinding("receiveObject", String.class, this.receiveObject, "selectedItem");
		dataBinder.registerBinding("contract", com.kingdee.eas.scm.sm.pur.PurContractInfo.class, this.contract, "data");
		dataBinder.registerBinding("payCompany", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.payCompany, "data");
		dataBinder.registerBinding("voucherNum", com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo.class, this.prmtVoucherNum, "data");
		dataBinder.registerBinding("k3VoucherNumber", String.class, this.txtVoucherNumber, "text");
		dataBinder.registerBinding("returnState", com.kingdee.eas.cp.bc.ReturnStateEnum.class, this.returnState, "selectedItem");
		dataBinder.registerBinding("returnAmt", java.math.BigDecimal.class, this.txtReturnAmt, "value");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.prmtPayer, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.FormattedTextFieldApprovedAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptCostedDept, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_CHECKING, this.prmtVoucherNum, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.kDTextAreaDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.kDTextBankStr, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptSupportedObj, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.comboPrior, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.dateYjBack, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.dateBillDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptApplierCompany, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.kDTextAreaCause, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtAmountUsed, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptBiller, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.cBoxTravelType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptPayMode, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptUpdator, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.contract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.receiveObject, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptPosition, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptExpenseType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtVoucherNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptOrgUnit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.prmtBankNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptAuditor, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.dateUpdateDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtPayerAccount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.kDCheckAmountControl, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtAmountBalance, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.isK3Paid, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.txtPayName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptApplier, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.dateReqDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.promPayName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptCurrencyType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bgText, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.bizPromptCompany, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.kdtEntries, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.dateAuditDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.FormattedTextFieldAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.payCompany, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionNext, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionHTMLForRpt, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCreateTo, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSuspenseAcc, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionViewSignature, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCopy, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionDelVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPre, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.updateReqDateAction, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionOnLoad, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCalculator, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCreateFrom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionViewSubmitProccess, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPersonalSite, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCloudScreen, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExcelForRpt, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionXunTongFeed, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCopyLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionLinkForRpt, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPrintPreview, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExitCurrent, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAddLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPrint, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionViewDoProccess, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExcelForMail, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCreateVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.ActionAnswer, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAbout, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSendMail, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAddNew, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionReset, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionWorkFlowG, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSubmitOption, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionFirst, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionRemoveLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionHTMLForMail, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionRemove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionNumberSign, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAddReqLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionTraceDown, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionViewRrcdsOfLendAndRepay, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCloudShare, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPageSetup, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExportSelected, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.ActionRemoteAssist, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionDelLoanLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPopupPaste, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCloudFeed, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionTraceUp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionRegProduct, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAuditResult, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.ActionKnowStore, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionMsgFormat, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPrintImage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAttachment, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCancelCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionWorkflowList, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExport, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExportSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionLocate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSubmit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSendMessage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionInsertLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionExportSelectedSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCreatePayNotifyBill, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionCopyFrom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionProcductVal, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionAddLoanLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionViewBudgetBalance, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionLast, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionToolBarCustom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSendingMessage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionHelp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionDelReqLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionNextPerson, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionViewImage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionPopupCopy, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionMultiapprove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionStartWorkFlow, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_CHECKING, this.actionSignature, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.EvectionLoanBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.cp.bc.EvectionLoanBillInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Company",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Company");
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
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isK3Paid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CollectionAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("peyerID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prior", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supportedObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biller", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizReqDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.from", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.to", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.vehicle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.taxiExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.bussesExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.hotelExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.otherExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.expenseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.budgetAmountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApprovedOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.convertMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.exchangeRatePrecision", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountUsedOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountBalanceOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("foreseeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("travelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiveObject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("voucherNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("k3VoucherNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("returnState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("returnAmt", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        } else if (STATUS_CHECKING.equals(this.oprtState)) {
		            this.prmtPayer.setEnabled(false);
		            this.FormattedTextFieldApprovedAmount.setEnabled(false);
		            this.bizPromptCostedDept.setEnabled(false);
		            this.prmtVoucherNum.setEnabled(true);
		            this.kDTextAreaDescription.setEnabled(false);
		            this.kDTextBankStr.setEnabled(false);
		            this.bizPromptSupportedObj.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.comboPrior.setEnabled(false);
		            this.dateYjBack.setEnabled(false);
		            this.dateBillDate.setEnabled(false);
		            this.bizPromptApplierCompany.setEnabled(false);
		            this.txtName.setEnabled(false);
		            this.kDTextAreaCause.setEnabled(false);
		            this.txtAmountUsed.setEnabled(false);
		            this.bizPromptBiller.setEnabled(false);
		            this.cBoxTravelType.setEnabled(false);
		            this.bizPromptPayMode.setEnabled(false);
		            this.bizPromptUpdator.setEnabled(false);
		            this.contract.setEnabled(false);
		            this.receiveObject.setEnabled(false);
		            this.bizPromptPosition.setEnabled(false);
		            this.bizPromptExpenseType.setEnabled(false);
		            this.txtVoucherNumber.setEnabled(false);
		            this.bizPromptOrgUnit.setEnabled(false);
		            this.prmtBankNumber.setEnabled(false);
		            this.bizPromptAuditor.setEnabled(false);
		            this.dateUpdateDate.setEnabled(false);
		            this.txtPayerAccount.setEnabled(false);
		            this.kDCheckAmountControl.setEnabled(false);
		            this.txtAmountBalance.setEnabled(false);
		            this.isK3Paid.setEnabled(false);
		            this.txtPayName.setEnabled(false);
		            this.bizPromptApplier.setEnabled(false);
		            this.dateReqDate.setEnabled(false);
		            this.promPayName.setEnabled(false);
		            this.bizPromptCurrencyType.setEnabled(false);
		            this.bgText.setEnabled(false);
		            this.bizPromptCompany.setEnabled(false);
		            this.kdtEntries.setEnabled(false);
		            this.dateAuditDate.setEnabled(false);
		            this.FormattedTextFieldAmount.setEnabled(false);
		            this.payCompany.setEnabled(false);
		            this.actionNext.setEnabled(false);
		            this.actionHTMLForRpt.setEnabled(false);
		            this.actionCreateTo.setEnabled(false);
		            this.actionSuspenseAcc.setEnabled(false);
		            this.actionViewSignature.setEnabled(false);
		            this.actionCopy.setEnabled(false);
		            this.actionDelVoucher.setEnabled(false);
		            this.actionPre.setEnabled(false);
		            this.updateReqDateAction.setEnabled(false);
		            this.actionOnLoad.setEnabled(false);
		            this.actionCalculator.setEnabled(false);
		            this.actionCreateFrom.setEnabled(false);
		            this.actionViewSubmitProccess.setEnabled(false);
		            this.actionPersonalSite.setEnabled(false);
		            this.actionCancel.setEnabled(false);
		            this.actionCloudScreen.setEnabled(false);
		            this.actionExcelForRpt.setEnabled(false);
		            this.actionXunTongFeed.setEnabled(false);
		            this.actionCopyLine.setEnabled(false);
		            this.actionLinkForRpt.setEnabled(false);
		            this.actionEdit.setEnabled(false);
		            this.actionPrintPreview.setEnabled(false);
		            this.actionExitCurrent.setEnabled(false);
		            this.actionAddLine.setEnabled(false);
		            this.actionPrint.setEnabled(false);
		            this.actionViewDoProccess.setEnabled(false);
		            this.actionExcelForMail.setEnabled(false);
		            this.actionCreateVoucher.setEnabled(false);
		            this.ActionAnswer.setEnabled(false);
		            this.actionAbout.setEnabled(false);
		            this.actionSendMail.setEnabled(false);
		            this.actionAddNew.setEnabled(false);
		            this.actionReset.setEnabled(false);
		            this.actionWorkFlowG.setEnabled(false);
		            this.actionSubmitOption.setEnabled(false);
		            this.actionFirst.setEnabled(false);
		            this.actionRemoveLine.setEnabled(false);
		            this.actionHTMLForMail.setEnabled(false);
		            this.actionRemove.setEnabled(false);
		            this.actionNumberSign.setEnabled(false);
		            this.actionAddReqLine.setEnabled(false);
		            this.actionTraceDown.setEnabled(false);
		            this.actionViewRrcdsOfLendAndRepay.setEnabled(false);
		            this.actionCloudShare.setEnabled(false);
		            this.actionPageSetup.setEnabled(false);
		            this.actionExportSelected.setEnabled(false);
		            this.ActionRemoteAssist.setEnabled(false);
		            this.actionDelLoanLine.setEnabled(false);
		            this.actionPopupPaste.setEnabled(false);
		            this.actionCloudFeed.setEnabled(false);
		            this.actionTraceUp.setEnabled(false);
		            this.actionRegProduct.setEnabled(false);
		            this.actionAuditResult.setEnabled(false);
		            this.ActionKnowStore.setEnabled(false);
		            this.actionMsgFormat.setEnabled(false);
		            this.actionPrintImage.setEnabled(false);
		            this.actionAttachment.setEnabled(false);
		            this.actionCancelCancel.setEnabled(false);
		            this.actionWorkflowList.setEnabled(false);
		            this.actionExport.setEnabled(false);
		            this.actionExportSave.setEnabled(false);
		            this.actionLocate.setEnabled(false);
		            this.actionSubmit.setEnabled(false);
		            this.actionSendMessage.setEnabled(false);
		            this.actionInsertLine.setEnabled(false);
		            this.actionExportSelectedSave.setEnabled(false);
		            this.actionCreatePayNotifyBill.setEnabled(false);
		            this.actionVoucher.setEnabled(false);
		            this.actionCopyFrom.setEnabled(false);
		            this.actionProcductVal.setEnabled(false);
		            this.actionAddLoanLine.setEnabled(false);
		            this.actionViewBudgetBalance.setEnabled(false);
		            this.actionLast.setEnabled(false);
		            this.actionToolBarCustom.setEnabled(false);
		            this.actionSendingMessage.setEnabled(false);
		            this.actionHelp.setEnabled(false);
		            this.actionDelReqLine.setEnabled(false);
		            this.actionNextPerson.setEnabled(false);
		            this.actionViewImage.setEnabled(false);
		            this.actionSave.setEnabled(false);
		            this.actionPopupCopy.setEnabled(false);
		            this.actionMultiapprove.setEnabled(false);
		            this.actionStartWorkFlow.setEnabled(false);
		            this.actionSignature.setEnabled(false);
        }
    }

    /**
     * output kDCheckAmountControl_actionPerformed method
     */
    protected void kDCheckAmountControl_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output promPayName_dataChanged method
     */
    protected void promPayName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output bizPromptApplier_dataChanged method
     */
    protected void bizPromptApplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStopped method
     */
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtPayer_dataChanged method
     */
    protected void prmtPayer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtBankNumber_dataChanged method
     */
    protected void prmtBankNumber_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output receiveObject_itemStateChanged method
     */
    protected void receiveObject_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isK3Paid"));
        sic.add(new SelectorItemInfo("payerName"));
        sic.add(new SelectorItemInfo("payerAccount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CollectionAccount.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CollectionAccount.id"));
        	sic.add(new SelectorItemInfo("CollectionAccount.number"));
        	sic.add(new SelectorItemInfo("CollectionAccount.name"));
		}
        sic.add(new SelectorItemInfo("peyerID"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applier.id"));
        	sic.add(new SelectorItemInfo("applier.number"));
        	sic.add(new SelectorItemInfo("applier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("position.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("position.id"));
        	sic.add(new SelectorItemInfo("position.number"));
        	sic.add(new SelectorItemInfo("position.name"));
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
        sic.add(new SelectorItemInfo("prior"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("currencyType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currencyType.id"));
        	sic.add(new SelectorItemInfo("currencyType.number"));
        	sic.add(new SelectorItemInfo("currencyType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedDept.id"));
        	sic.add(new SelectorItemInfo("costedDept.number"));
        	sic.add(new SelectorItemInfo("costedDept.name"));
		}
        sic.add(new SelectorItemInfo("amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supportedObj.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supportedObj.id"));
        	sic.add(new SelectorItemInfo("supportedObj.number"));
        	sic.add(new SelectorItemInfo("supportedObj.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("operationType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("operationType.id"));
        	sic.add(new SelectorItemInfo("operationType.number"));
        	sic.add(new SelectorItemInfo("operationType.name"));
		}
        sic.add(new SelectorItemInfo("billDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("biller.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("biller.id"));
        	sic.add(new SelectorItemInfo("biller.number"));
        	sic.add(new SelectorItemInfo("biller.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payMode.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payMode.id"));
        	sic.add(new SelectorItemInfo("payMode.number"));
        	sic.add(new SelectorItemInfo("payMode.name"));
		}
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("bizReqDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
    	sic.add(new SelectorItemInfo("entries.id"));
    	sic.add(new SelectorItemInfo("entries.startDate"));
    	sic.add(new SelectorItemInfo("entries.from"));
    	sic.add(new SelectorItemInfo("entries.endDate"));
    	sic.add(new SelectorItemInfo("entries.to"));
    	sic.add(new SelectorItemInfo("entries.vehicle"));
    	sic.add(new SelectorItemInfo("entries.taxiExpense"));
    	sic.add(new SelectorItemInfo("entries.bussesExpense"));
    	sic.add(new SelectorItemInfo("entries.hotelExpense"));
    	sic.add(new SelectorItemInfo("entries.otherExpense"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.expenseType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.expenseType.id"));
			sic.add(new SelectorItemInfo("entries.expenseType.name"));
        	sic.add(new SelectorItemInfo("entries.expenseType.number"));
		}
    	sic.add(new SelectorItemInfo("entries.budgetAmount"));
    	sic.add(new SelectorItemInfo("entries.amount"));
    	sic.add(new SelectorItemInfo("entries.amountApproved"));
    	sic.add(new SelectorItemInfo("entries.amountUsed"));
    	sic.add(new SelectorItemInfo("entries.amountBalance"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.currencyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.currencyType.id"));
			sic.add(new SelectorItemInfo("entries.currencyType.name"));
        	sic.add(new SelectorItemInfo("entries.currencyType.number"));
		}
    	sic.add(new SelectorItemInfo("entries.exchangeRate"));
    	sic.add(new SelectorItemInfo("entries.budgetAmountOri"));
    	sic.add(new SelectorItemInfo("entries.amountOri"));
    	sic.add(new SelectorItemInfo("entries.amountApprovedOri"));
    	sic.add(new SelectorItemInfo("entries.convertMode"));
    	sic.add(new SelectorItemInfo("entries.exchangeRatePrecision"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.project.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.project.id"));
			sic.add(new SelectorItemInfo("entries.project.name"));
        	sic.add(new SelectorItemInfo("entries.project.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.costedDept.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.costedDept.id"));
			sic.add(new SelectorItemInfo("entries.costedDept.name"));
        	sic.add(new SelectorItemInfo("entries.costedDept.number"));
		}
    	sic.add(new SelectorItemInfo("entries.amountUsedOri"));
    	sic.add(new SelectorItemInfo("entries.amountBalanceOri"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.person.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.person.id"));
			sic.add(new SelectorItemInfo("entries.person.name"));
        	sic.add(new SelectorItemInfo("entries.person.number"));
		}
        sic.add(new SelectorItemInfo("cause"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("foreseeDate"));
        sic.add(new SelectorItemInfo("amountApproved"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applierCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applierCompany.id"));
        	sic.add(new SelectorItemInfo("applierCompany.number"));
        	sic.add(new SelectorItemInfo("applierCompany.name"));
		}
        sic.add(new SelectorItemInfo("amountUsed"));
        sic.add(new SelectorItemInfo("amountBalance"));
        sic.add(new SelectorItemInfo("payerBank"));
        sic.add(new SelectorItemInfo("travelType"));
        sic.add(new SelectorItemInfo("receiveObject"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contract.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contract.id"));
        	sic.add(new SelectorItemInfo("contract.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payCompany.id"));
        	sic.add(new SelectorItemInfo("payCompany.number"));
        	sic.add(new SelectorItemInfo("payCompany.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("voucherNum.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("voucherNum.id"));
		}
        sic.add(new SelectorItemInfo("k3VoucherNumber"));
        sic.add(new SelectorItemInfo("returnState"));
        sic.add(new SelectorItemInfo("returnAmt"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionTraceDown_actionPerformed method
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }
    	

    /**
     * output actionViewBudgetBalance_actionPerformed method
     */
    public void actionViewBudgetBalance_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewBudgetBalance_actionPerformed(e);
    }
    	

    /**
     * output actionCreatePayNotifyBill_actionPerformed method
     */
    public void actionCreatePayNotifyBill_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreatePayNotifyBill_actionPerformed(e);
    }
    	

    /**
     * output actionCreateVoucher_actionPerformed method
     */
    public void actionCreateVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateVoucher_actionPerformed(e);
    }
    	

    /**
     * output actionViewRrcdsOfLendAndRepay_actionPerformed method
     */
    public void actionViewRrcdsOfLendAndRepay_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionTraceDown(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionTraceDown(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTraceDown() {
    	return false;
    }
	public RequestContext prepareActionViewBudgetBalance(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionViewBudgetBalance(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBudgetBalance() {
    	return false;
    }
	public RequestContext prepareActionCreatePayNotifyBill(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCreatePayNotifyBill(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreatePayNotifyBill() {
    	return false;
    }
	public RequestContext prepareActionCreateVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCreateVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateVoucher() {
    	return false;
    }
	public RequestContext prepareActionViewRrcdsOfLendAndRepay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewRrcdsOfLendAndRepay() {
    	return false;
    }

    /**
     * output ActionViewRrcdsOfLendAndRepay class
     */     
    protected class ActionViewRrcdsOfLendAndRepay extends ItemAction {     
    
        public ActionViewRrcdsOfLendAndRepay()
        {
            this(null);
        }

        public ActionViewRrcdsOfLendAndRepay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
            _tempStr = resHelper.getString("ActionViewRrcdsOfLendAndRepay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewRrcdsOfLendAndRepay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewRrcdsOfLendAndRepay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEvectionLoanBillEditUI.this, "ActionViewRrcdsOfLendAndRepay", "actionViewRrcdsOfLendAndRepay_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "EvectionLoanBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.cp.bc.client.EvectionLoanBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.EvectionLoanBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.EvectionLoanBillInfo objectValue = new com.kingdee.eas.cp.bc.EvectionLoanBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/cp/bc/EvectionLoanBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.EvectionLoanBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntries;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("amount",new java.math.BigDecimal(0.00));
		vo.put("amountApproved",new java.math.BigDecimal(0.0));
		vo.put("amountUsed",new java.math.BigDecimal(0.0));
		vo.put("amountBalance",new java.math.BigDecimal(0.0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}