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
public abstract class AbstractTravelAccountEditUI extends com.kingdee.eas.cp.bc.client.ExpenseAccountEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTravelAccountEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane bgScrol;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promPayName;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conspecialInvoice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conpayCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conVoucherWord;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isK3Paied;
    protected com.kingdee.bos.ctrl.swing.KDTextArea bgText;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox propPayBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayerAccount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cBoxTravelType;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnCollectionEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDLoanContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCollectionEntries;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtLoanCheckEntries;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtspecialInvoice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVoucherWord;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVoucherNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRcrdsOfLendAndRepay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewRcds;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBudget;
    protected com.kingdee.eas.cp.bc.TravelAccountBillInfo editData = null;
    protected ActionViewRrcdsOfLendAndRepay actionViewRrcdsOfLendAndRepay = null;
    public final static String STATUS_VOUCHERVIEW = "VOUCHERVIEW";
    /**
     * output class constructor
     */
    public AbstractTravelAccountEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTravelAccountEditUI.class.getName());
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
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionViewRrcdsOfLendAndRepay
        this.actionViewRrcdsOfLendAndRepay = new ActionViewRrcdsOfLendAndRepay(this);
        getActionManager().registerAction("actionViewRrcdsOfLendAndRepay", actionViewRrcdsOfLendAndRepay);
         this.actionViewRrcdsOfLendAndRepay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTextArea2 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.bgScrol = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.promPayName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTabbedPane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.conspecialInvoice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conpayCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conVoucherWord = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isK3Paied = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bgText = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtPayName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.propPayBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPayerAccount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cBoxTravelType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.ctnEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.ctnCollectionEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLoanContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtCollectionEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtLoanCheckEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtspecialInvoice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtpayCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtVoucherWord = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtVoucherNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnViewRcrdsOfLendAndRepay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewRcds = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBudget = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTextArea1.setName("kDTextArea1");
        this.kDTextArea2.setName("kDTextArea2");
        this.bgScrol.setName("bgScrol");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.promPayName.setName("promPayName");
        this.kDTabbedPane.setName("kDTabbedPane");
        this.conspecialInvoice.setName("conspecialInvoice");
        this.conpayCompany.setName("conpayCompany");
        this.conVoucherWord.setName("conVoucherWord");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.isK3Paied.setName("isK3Paied");
        this.bgText.setName("bgText");
        this.txtPayName.setName("txtPayName");
        this.propPayBank.setName("propPayBank");
        this.txtPayerAccount.setName("txtPayerAccount");
        this.cBoxTravelType.setName("cBoxTravelType");
        this.ctnEntry.setName("ctnEntry");
        this.ctnCollectionEntry.setName("ctnCollectionEntry");
        this.kDLoanContainer.setName("kDLoanContainer");
        this.kdtEntries.setName("kdtEntries");
        this.kdtCollectionEntries.setName("kdtCollectionEntries");
        this.kdtLoanCheckEntries.setName("kdtLoanCheckEntries");
        this.txtspecialInvoice.setName("txtspecialInvoice");
        this.prmtpayCompany.setName("prmtpayCompany");
        this.prmtVoucherWord.setName("prmtVoucherWord");
        this.txtVoucherNumber.setName("txtVoucherNumber");
        this.btnViewRcrdsOfLendAndRepay.setName("btnViewRcrdsOfLendAndRepay");
        this.menuItemViewRcds.setName("menuItemViewRcds");
        this.menuItemViewBudget.setName("menuItemViewBudget");
        // CoreUI		
        this.setPreferredSize(new Dimension(1000,800));		
        this.btnPrintPreview.setEnabled(true);		
        this.btnPrintPreview.setVisible(true);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.menuItemCreateTo.setText(resHelper.getString("menuItemCreateTo.text"));		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contExpenseType.setBoundLabelText(resHelper.getString("contExpenseType.boundLabelText"));		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.contBudgetAmount.setVisible(false);		
        this.contAmountApproved.setBoundLabelText(resHelper.getString("contAmountApproved.boundLabelText"));		
        this.contAmountApproved.setBoundLabelLength(100);		
        this.txtName.setRequired(false);		
        this.bizPromptCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
        this.txtTel.setRequired(false);		
        this.txtAmountApproved.setEnabled(false);		
        this.txtAmountEncashed.setEnabled(false);		
        this.txtCause.setMaxLength(20);		
        this.txtDesc.setMaxLength(255);
        // kDTextArea1		
        this.kDTextArea1.setEditable(false);
        // kDTextArea2		
        this.kDTextArea2.setText(resHelper.getString("kDTextArea2.text"));		
        this.kDTextArea2.setMaxLength(400);
        // bgScrol		
        this.bgScrol.setVisible(false);		
        this.bgScrol.setAutoscrolls(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(false);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setVisible(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // promPayName		
        this.promPayName.setQueryInfo("com.kingdee.eas.cp.bc.app.CollectionAccountQuery");		
        this.promPayName.setCommitFormat("$payee$");		
        this.promPayName.setDisplayFormat("$payee$");		
        this.promPayName.setEditFormat("$payee$");		
        this.promPayName.setEditable(true);		
        this.promPayName.setVisible(false);
        // kDTabbedPane
        // conspecialInvoice		
        this.conspecialInvoice.setBoundLabelText(resHelper.getString("conspecialInvoice.boundLabelText"));		
        this.conspecialInvoice.setBoundLabelLength(100);		
        this.conspecialInvoice.setBoundLabelUnderline(true);
        // conpayCompany		
        this.conpayCompany.setBoundLabelText(resHelper.getString("conpayCompany.boundLabelText"));		
        this.conpayCompany.setBoundLabelLength(100);		
        this.conpayCompany.setBoundLabelUnderline(true);
        // conVoucherWord		
        this.conVoucherWord.setBoundLabelText(resHelper.getString("conVoucherWord.boundLabelText"));		
        this.conVoucherWord.setBoundLabelLength(100);		
        this.conVoucherWord.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // isK3Paied		
        this.isK3Paied.setText(resHelper.getString("isK3Paied.text"));		
        this.isK3Paied.setEnabled(false);
        // bgText		
        this.bgText.setMaxLength(400);		
        this.bgText.setEditable(false);
        // txtPayName		
        this.txtPayName.setEnabled(false);
        // propPayBank		
        this.propPayBank.setQueryInfo("com.kingdee.eas.fm.be.app.F7BEBankQuery");		
        this.propPayBank.setEnabled(false);
        // txtPayerAccount		
        this.txtPayerAccount.setEnabled(false);
        // cBoxTravelType
        // ctnEntry		
        this.ctnEntry.setEnableActive(false);		
        this.ctnEntry.setTitleStyle(2);
        // ctnCollectionEntry		
        this.ctnCollectionEntry.setTitleStyle(2);		
        this.ctnCollectionEntry.setEnableActive(false);
        // kDLoanContainer		
        this.kDLoanContainer.setTitleStyle(2);		
        this.kDLoanContainer.setEnableActive(false);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol33\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol34\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol35\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol36\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"operationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"expenseType\" t:width=\"75\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"costCenter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"startDate\" t:width=\"81\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"endDate\" t:width=\"81\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"from\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"to\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"vehicle\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"currencyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"11\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"taxiExpense\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"bussesExpense\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"hotelExpense\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"otherExpense\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"FEventionSubsidy\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"budgetAmountOri\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"budgetAmount\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"amountOri\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"approvedAmountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"approvedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"company\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"24\" /><t:Column t:key=\"receiveState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"payState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"sourceBillNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"sourceEntryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"convertMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"exchangeRatePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"purpose\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"noTaxAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"taxRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"tax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"noTaxAmtLocal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"taxLocal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{operationType}</t:Cell><t:Cell>$Resource{expenseType}</t:Cell><t:Cell>$Resource{costCenter}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{from}</t:Cell><t:Cell>$Resource{to}</t:Cell><t:Cell>$Resource{vehicle}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{taxiExpense}</t:Cell><t:Cell>$Resource{bussesExpense}</t:Cell><t:Cell>$Resource{hotelExpense}</t:Cell><t:Cell>$Resource{otherExpense}</t:Cell><t:Cell>$Resource{FEventionSubsidy}</t:Cell><t:Cell>$Resource{budgetAmountOri}</t:Cell><t:Cell>$Resource{budgetAmount}</t:Cell><t:Cell>$Resource{amountOri}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{approvedAmountOri}</t:Cell><t:Cell>$Resource{approvedAmount}</t:Cell><t:Cell>$Resource{company}</t:Cell><t:Cell>$Resource{receiveState}</t:Cell><t:Cell>$Resource{payState}</t:Cell><t:Cell>$Resource{sourceBillNumber}</t:Cell><t:Cell>$Resource{sourceEntryId}</t:Cell><t:Cell>$Resource{convertMode}</t:Cell><t:Cell>$Resource{exchangeRatePrecision}</t:Cell><t:Cell>$Resource{purpose}</t:Cell><t:Cell>$Resource{noTaxAmt}</t:Cell><t:Cell>$Resource{taxRate}</t:Cell><t:Cell>$Resource{tax}</t:Cell><t:Cell>$Resource{noTaxAmtLocal}</t:Cell><t:Cell>$Resource{taxLocal}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));

                this.kdtEntries.putBindContents("editData",new String[] {"id","project","operationType","expenseType","costCenter","person","startDate","endDate","from","to","vehicle","currencyType","exchangeRate","taxiExpense","bussesExpense","hotelExpense","otherExpense","eventionSubsidy","budgetAmountOri","budgetAmount","amountOri","amount","amountApprovedOri","amountApproved","company","receiveState","payState","sourceBillNumber","sourceBillEntryID","convertMode","exchangeRatePrecision","purpose","noTaxAmt","taxRate","tax","noTaxAmtLocal","taxLocal"});


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
        final KDBizPromptBox kdtEntries_operationType_PromptBox = new KDBizPromptBox();
        kdtEntries_operationType_PromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.OperationTypeQuery");
        kdtEntries_operationType_PromptBox.setVisible(true);
        kdtEntries_operationType_PromptBox.setEditable(true);
        kdtEntries_operationType_PromptBox.setDisplayFormat("$number$");
        kdtEntries_operationType_PromptBox.setEditFormat("$number$");
        kdtEntries_operationType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_operationType_CellEditor = new KDTDefaultCellEditor(kdtEntries_operationType_PromptBox);
        this.kdtEntries.getColumn("operationType").setEditor(kdtEntries_operationType_CellEditor);
        ObjectValueRender kdtEntries_operationType_OVR = new ObjectValueRender();
        kdtEntries_operationType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("operationType").setRenderer(kdtEntries_operationType_OVR);
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
        final KDBizPromptBox kdtEntries_costCenter_PromptBox = new KDBizPromptBox();
        kdtEntries_costCenter_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");
        kdtEntries_costCenter_PromptBox.setVisible(true);
        kdtEntries_costCenter_PromptBox.setEditable(true);
        kdtEntries_costCenter_PromptBox.setDisplayFormat("$number$");
        kdtEntries_costCenter_PromptBox.setEditFormat("$number$");
        kdtEntries_costCenter_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_costCenter_CellEditor = new KDTDefaultCellEditor(kdtEntries_costCenter_PromptBox);
        this.kdtEntries.getColumn("costCenter").setEditor(kdtEntries_costCenter_CellEditor);
        ObjectValueRender kdtEntries_costCenter_OVR = new ObjectValueRender();
        kdtEntries_costCenter_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("costCenter").setRenderer(kdtEntries_costCenter_OVR);
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
        final KDBizPromptBox kdtEntries_company_PromptBox = new KDBizPromptBox();
        kdtEntries_company_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");
        kdtEntries_company_PromptBox.setVisible(true);
        kdtEntries_company_PromptBox.setEditable(true);
        kdtEntries_company_PromptBox.setDisplayFormat("$number$");
        kdtEntries_company_PromptBox.setEditFormat("$number$");
        kdtEntries_company_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_company_CellEditor = new KDTDefaultCellEditor(kdtEntries_company_PromptBox);
        this.kdtEntries.getColumn("company").setEditor(kdtEntries_company_CellEditor);
        ObjectValueRender kdtEntries_company_OVR = new ObjectValueRender();
        kdtEntries_company_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("company").setRenderer(kdtEntries_company_OVR);
        KDComboBox kdtEntries_receiveState_ComboBox = new KDComboBox();
        kdtEntries_receiveState_ComboBox.setName("kdtEntries_receiveState_ComboBox");
        kdtEntries_receiveState_ComboBox.setVisible(true);
        kdtEntries_receiveState_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.EntryStateEnum").toArray());
        KDTDefaultCellEditor kdtEntries_receiveState_CellEditor = new KDTDefaultCellEditor(kdtEntries_receiveState_ComboBox);
        this.kdtEntries.getColumn("receiveState").setEditor(kdtEntries_receiveState_CellEditor);
        KDComboBox kdtEntries_payState_ComboBox = new KDComboBox();
        kdtEntries_payState_ComboBox.setName("kdtEntries_payState_ComboBox");
        kdtEntries_payState_ComboBox.setVisible(true);
        kdtEntries_payState_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.EntryStateEnum").toArray());
        KDTDefaultCellEditor kdtEntries_payState_CellEditor = new KDTDefaultCellEditor(kdtEntries_payState_ComboBox);
        this.kdtEntries.getColumn("payState").setEditor(kdtEntries_payState_CellEditor);
        KDComboBox kdtEntries_convertMode_ComboBox = new KDComboBox();
        kdtEntries_convertMode_ComboBox.setName("kdtEntries_convertMode_ComboBox");
        kdtEntries_convertMode_ComboBox.setVisible(true);
        kdtEntries_convertMode_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.assistant.ConvertModeEnum").toArray());
        KDTDefaultCellEditor kdtEntries_convertMode_CellEditor = new KDTDefaultCellEditor(kdtEntries_convertMode_ComboBox);
        this.kdtEntries.getColumn("convertMode").setEditor(kdtEntries_convertMode_CellEditor);
        KDTextField kdtEntries_purpose_TextField = new KDTextField();
        kdtEntries_purpose_TextField.setName("kdtEntries_purpose_TextField");
        kdtEntries_purpose_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntries_purpose_CellEditor = new KDTDefaultCellEditor(kdtEntries_purpose_TextField);
        this.kdtEntries.getColumn("purpose").setEditor(kdtEntries_purpose_CellEditor);
        KDFormattedTextField kdtEntries_noTaxAmt_TextField = new KDFormattedTextField();
        kdtEntries_noTaxAmt_TextField.setName("kdtEntries_noTaxAmt_TextField");
        kdtEntries_noTaxAmt_TextField.setVisible(true);
        kdtEntries_noTaxAmt_TextField.setEditable(true);
        kdtEntries_noTaxAmt_TextField.setHorizontalAlignment(2);
        kdtEntries_noTaxAmt_TextField.setDataType(1);
        kdtEntries_noTaxAmt_TextField.setPrecision(8);
        KDTDefaultCellEditor kdtEntries_noTaxAmt_CellEditor = new KDTDefaultCellEditor(kdtEntries_noTaxAmt_TextField);
        this.kdtEntries.getColumn("noTaxAmt").setEditor(kdtEntries_noTaxAmt_CellEditor);
        KDFormattedTextField kdtEntries_taxRate_TextField = new KDFormattedTextField();
        kdtEntries_taxRate_TextField.setName("kdtEntries_taxRate_TextField");
        kdtEntries_taxRate_TextField.setVisible(true);
        kdtEntries_taxRate_TextField.setEditable(true);
        kdtEntries_taxRate_TextField.setHorizontalAlignment(2);
        kdtEntries_taxRate_TextField.setDataType(1);
        kdtEntries_taxRate_TextField.setPrecision(8);
        KDTDefaultCellEditor kdtEntries_taxRate_CellEditor = new KDTDefaultCellEditor(kdtEntries_taxRate_TextField);
        this.kdtEntries.getColumn("taxRate").setEditor(kdtEntries_taxRate_CellEditor);
        KDFormattedTextField kdtEntries_tax_TextField = new KDFormattedTextField();
        kdtEntries_tax_TextField.setName("kdtEntries_tax_TextField");
        kdtEntries_tax_TextField.setVisible(true);
        kdtEntries_tax_TextField.setEditable(true);
        kdtEntries_tax_TextField.setHorizontalAlignment(2);
        kdtEntries_tax_TextField.setDataType(1);
        kdtEntries_tax_TextField.setPrecision(8);
        KDTDefaultCellEditor kdtEntries_tax_CellEditor = new KDTDefaultCellEditor(kdtEntries_tax_TextField);
        this.kdtEntries.getColumn("tax").setEditor(kdtEntries_tax_CellEditor);
        KDFormattedTextField kdtEntries_noTaxAmtLocal_TextField = new KDFormattedTextField();
        kdtEntries_noTaxAmtLocal_TextField.setName("kdtEntries_noTaxAmtLocal_TextField");
        kdtEntries_noTaxAmtLocal_TextField.setVisible(true);
        kdtEntries_noTaxAmtLocal_TextField.setEditable(true);
        kdtEntries_noTaxAmtLocal_TextField.setHorizontalAlignment(2);
        kdtEntries_noTaxAmtLocal_TextField.setDataType(1);
        kdtEntries_noTaxAmtLocal_TextField.setPrecision(8);
        KDTDefaultCellEditor kdtEntries_noTaxAmtLocal_CellEditor = new KDTDefaultCellEditor(kdtEntries_noTaxAmtLocal_TextField);
        this.kdtEntries.getColumn("noTaxAmtLocal").setEditor(kdtEntries_noTaxAmtLocal_CellEditor);
        KDFormattedTextField kdtEntries_taxLocal_TextField = new KDFormattedTextField();
        kdtEntries_taxLocal_TextField.setName("kdtEntries_taxLocal_TextField");
        kdtEntries_taxLocal_TextField.setVisible(true);
        kdtEntries_taxLocal_TextField.setEditable(true);
        kdtEntries_taxLocal_TextField.setHorizontalAlignment(2);
        kdtEntries_taxLocal_TextField.setDataType(1);
        kdtEntries_taxLocal_TextField.setPrecision(8);
        KDTDefaultCellEditor kdtEntries_taxLocal_CellEditor = new KDTDefaultCellEditor(kdtEntries_taxLocal_TextField);
        this.kdtEntries.getColumn("taxLocal").setEditor(kdtEntries_taxLocal_CellEditor);
        // kdtCollectionEntries
		String kdtCollectionEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"receiveObject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"payMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"payerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"payerBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"accountOpenArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"payerAccount\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"account\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"currencyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"convertMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"exchangeRatePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"amountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"flag\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{receiveObject}</t:Cell><t:Cell>$Resource{payMode}</t:Cell><t:Cell>$Resource{payerName}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{payerBank}</t:Cell><t:Cell>$Resource{accountOpenArea}</t:Cell><t:Cell>$Resource{payerAccount}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{convertMode}</t:Cell><t:Cell>$Resource{exchangeRatePrecision}</t:Cell><t:Cell>$Resource{amountOri}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{flag}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtCollectionEntries.setFormatXml(resHelper.translateString("kdtCollectionEntries",kdtCollectionEntriesStrXML));

                this.kdtCollectionEntries.putBindContents("editData",new String[] {"id","seq","receiveObject","payMode","payerName","","payerBank","accountOpenArea","payerAccount","","currencyType","exchangeRate","convertMode","exchangeRatePrecision","amountOri","amount",""});


        this.kdtCollectionEntries.checkParsed();
        KDComboBox kdtCollectionEntries_receiveObject_ComboBox = new KDComboBox();
        kdtCollectionEntries_receiveObject_ComboBox.setName("kdtCollectionEntries_receiveObject_ComboBox");
        kdtCollectionEntries_receiveObject_ComboBox.setVisible(true);
        kdtCollectionEntries_receiveObject_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.ReceiveObjectEnum").toArray());
        KDTDefaultCellEditor kdtCollectionEntries_receiveObject_CellEditor = new KDTDefaultCellEditor(kdtCollectionEntries_receiveObject_ComboBox);
        this.kdtCollectionEntries.getColumn("receiveObject").setEditor(kdtCollectionEntries_receiveObject_CellEditor);
        final KDBizPromptBox kdtCollectionEntries_payMode_PromptBox = new KDBizPromptBox();
        kdtCollectionEntries_payMode_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
        kdtCollectionEntries_payMode_PromptBox.setVisible(true);
        kdtCollectionEntries_payMode_PromptBox.setEditable(true);
        kdtCollectionEntries_payMode_PromptBox.setDisplayFormat("$number$");
        kdtCollectionEntries_payMode_PromptBox.setEditFormat("$number$");
        kdtCollectionEntries_payMode_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtCollectionEntries_payMode_CellEditor = new KDTDefaultCellEditor(kdtCollectionEntries_payMode_PromptBox);
        this.kdtCollectionEntries.getColumn("payMode").setEditor(kdtCollectionEntries_payMode_CellEditor);
        ObjectValueRender kdtCollectionEntries_payMode_OVR = new ObjectValueRender();
        kdtCollectionEntries_payMode_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtCollectionEntries.getColumn("payMode").setRenderer(kdtCollectionEntries_payMode_OVR);
        final KDBizPromptBox kdtCollectionEntries_currencyType_PromptBox = new KDBizPromptBox();
        kdtCollectionEntries_currencyType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        kdtCollectionEntries_currencyType_PromptBox.setVisible(true);
        kdtCollectionEntries_currencyType_PromptBox.setEditable(true);
        kdtCollectionEntries_currencyType_PromptBox.setDisplayFormat("$number$");
        kdtCollectionEntries_currencyType_PromptBox.setEditFormat("$number$");
        kdtCollectionEntries_currencyType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtCollectionEntries_currencyType_CellEditor = new KDTDefaultCellEditor(kdtCollectionEntries_currencyType_PromptBox);
        this.kdtCollectionEntries.getColumn("currencyType").setEditor(kdtCollectionEntries_currencyType_CellEditor);
        ObjectValueRender kdtCollectionEntries_currencyType_OVR = new ObjectValueRender();
        kdtCollectionEntries_currencyType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtCollectionEntries.getColumn("currencyType").setRenderer(kdtCollectionEntries_currencyType_OVR);
        KDComboBox kdtCollectionEntries_convertMode_ComboBox = new KDComboBox();
        kdtCollectionEntries_convertMode_ComboBox.setName("kdtCollectionEntries_convertMode_ComboBox");
        kdtCollectionEntries_convertMode_ComboBox.setVisible(true);
        kdtCollectionEntries_convertMode_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.assistant.ConvertModeEnum").toArray());
        KDTDefaultCellEditor kdtCollectionEntries_convertMode_CellEditor = new KDTDefaultCellEditor(kdtCollectionEntries_convertMode_ComboBox);
        this.kdtCollectionEntries.getColumn("convertMode").setEditor(kdtCollectionEntries_convertMode_CellEditor);
        // kdtLoanCheckEntries
		String kdtLoanCheckEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /><c:NumberFormat>%{yyyy-MM-dd}t</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.0000}.4f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"sourceBillNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"sourceBillDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"sourceBillCause\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"sourceBillAmountApproved\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"sourceBillOperationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"sourceBillExpenseType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"sourceBillCostCenterName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"currencyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"sourceBillAmountBalanceOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"sourceBillAmountBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"checkAmountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"checkAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"sourceBillId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"sourceBillEntryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"sourceBilAmountUsed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"hasSourceBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"sourceBillExpenseTypeId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"sourceBillCostCenterId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"convertMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{sourceBillNumber}</t:Cell><t:Cell>$Resource{sourceBillDate}</t:Cell><t:Cell>$Resource{sourceBillCause}</t:Cell><t:Cell>$Resource{sourceBillAmountApproved}</t:Cell><t:Cell>$Resource{sourceBillOperationType}</t:Cell><t:Cell>$Resource{sourceBillExpenseType}</t:Cell><t:Cell>$Resource{sourceBillCostCenterName}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{sourceBillAmountBalanceOri}</t:Cell><t:Cell>$Resource{sourceBillAmountBalance}</t:Cell><t:Cell>$Resource{checkAmountOri}</t:Cell><t:Cell>$Resource{checkAmount}</t:Cell><t:Cell>$Resource{sourceBillId}</t:Cell><t:Cell>$Resource{sourceBillEntryId}</t:Cell><t:Cell>$Resource{sourceBilAmountUsed}</t:Cell><t:Cell>$Resource{hasSourceBill}</t:Cell><t:Cell>$Resource{sourceBillExpenseTypeId}</t:Cell><t:Cell>$Resource{sourceBillCostCenterId}</t:Cell><t:Cell>$Resource{convertMode}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtLoanCheckEntries.setFormatXml(resHelper.translateString("kdtLoanCheckEntries",kdtLoanCheckEntriesStrXML));

                this.kdtLoanCheckEntries.putBindContents("editData",new String[] {"project","sourceBillNumber","sourceBillDate","sourceBillCause","sourceBillAmountApproved","sourceBillOperationType","sourceBillExpenseType","sourceBillCostCenterName","currencyType","exchangeRate","sourceBillAmountBalanceOri","sourceBillAmountBalance","checkAmountOri","checkAmount","sourceBillID","sourceBillEntryID","sourceBillAmountUsed","hasSourceBill","sourceBillExpenseTypeId","sourceBillCostCenterId","convertMode"});


        this.kdtLoanCheckEntries.checkParsed();
        final KDBizPromptBox kdtLoanCheckEntries_project_PromptBox = new KDBizPromptBox();
        kdtLoanCheckEntries_project_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CustomerProjectQuery");
        kdtLoanCheckEntries_project_PromptBox.setVisible(true);
        kdtLoanCheckEntries_project_PromptBox.setEditable(true);
        kdtLoanCheckEntries_project_PromptBox.setDisplayFormat("$number$");
        kdtLoanCheckEntries_project_PromptBox.setEditFormat("$number$");
        kdtLoanCheckEntries_project_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtLoanCheckEntries_project_CellEditor = new KDTDefaultCellEditor(kdtLoanCheckEntries_project_PromptBox);
        this.kdtLoanCheckEntries.getColumn("project").setEditor(kdtLoanCheckEntries_project_CellEditor);
        ObjectValueRender kdtLoanCheckEntries_project_OVR = new ObjectValueRender();
        kdtLoanCheckEntries_project_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtLoanCheckEntries.getColumn("project").setRenderer(kdtLoanCheckEntries_project_OVR);
        final KDBizPromptBox kdtLoanCheckEntries_currencyType_PromptBox = new KDBizPromptBox();
        kdtLoanCheckEntries_currencyType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        kdtLoanCheckEntries_currencyType_PromptBox.setVisible(true);
        kdtLoanCheckEntries_currencyType_PromptBox.setEditable(true);
        kdtLoanCheckEntries_currencyType_PromptBox.setDisplayFormat("$number$");
        kdtLoanCheckEntries_currencyType_PromptBox.setEditFormat("$number$");
        kdtLoanCheckEntries_currencyType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtLoanCheckEntries_currencyType_CellEditor = new KDTDefaultCellEditor(kdtLoanCheckEntries_currencyType_PromptBox);
        this.kdtLoanCheckEntries.getColumn("currencyType").setEditor(kdtLoanCheckEntries_currencyType_CellEditor);
        ObjectValueRender kdtLoanCheckEntries_currencyType_OVR = new ObjectValueRender();
        kdtLoanCheckEntries_currencyType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtLoanCheckEntries.getColumn("currencyType").setRenderer(kdtLoanCheckEntries_currencyType_OVR);
        KDCheckBox kdtLoanCheckEntries_hasSourceBill_CheckBox = new KDCheckBox();
        kdtLoanCheckEntries_hasSourceBill_CheckBox.setName("kdtLoanCheckEntries_hasSourceBill_CheckBox");
        KDTDefaultCellEditor kdtLoanCheckEntries_hasSourceBill_CellEditor = new KDTDefaultCellEditor(kdtLoanCheckEntries_hasSourceBill_CheckBox);
        this.kdtLoanCheckEntries.getColumn("hasSourceBill").setEditor(kdtLoanCheckEntries_hasSourceBill_CellEditor);
        KDComboBox kdtLoanCheckEntries_convertMode_ComboBox = new KDComboBox();
        kdtLoanCheckEntries_convertMode_ComboBox.setName("kdtLoanCheckEntries_convertMode_ComboBox");
        kdtLoanCheckEntries_convertMode_ComboBox.setVisible(true);
        kdtLoanCheckEntries_convertMode_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.assistant.ConvertModeEnum").toArray());
        KDTDefaultCellEditor kdtLoanCheckEntries_convertMode_CellEditor = new KDTDefaultCellEditor(kdtLoanCheckEntries_convertMode_ComboBox);
        this.kdtLoanCheckEntries.getColumn("convertMode").setEditor(kdtLoanCheckEntries_convertMode_CellEditor);
        // txtspecialInvoice
        // prmtpayCompany		
        this.prmtpayCompany.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");		
        this.prmtpayCompany.setCommitFormat("$number$");		
        this.prmtpayCompany.setEditFormat("$number$");		
        this.prmtpayCompany.setDisplayFormat("$name$");
        // prmtVoucherWord		
        this.prmtVoucherWord.setEnabled(false);
        // txtVoucherNumber		
        this.txtVoucherNumber.setEnabled(false);
        // btnViewRcrdsOfLendAndRepay
        this.btnViewRcrdsOfLendAndRepay.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRcrdsOfLendAndRepay.setText(resHelper.getString("btnViewRcrdsOfLendAndRepay.text"));		
        this.btnViewRcrdsOfLendAndRepay.setToolTipText(resHelper.getString("btnViewRcrdsOfLendAndRepay.toolTipText"));
        // menuItemViewRcds
        this.menuItemViewRcds.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewRcds.setText(resHelper.getString("menuItemViewRcds.text"));		
        this.menuItemViewRcds.setMnemonic(82);
        // menuItemViewBudget
        this.menuItemViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowBg, new Class[] { IItemAction.class }, getServiceContext()));		
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
        this.setBounds(new Rectangle(0, 0, 800, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 800, 700));
        labState.setBounds(new Rectangle(281, 611, 67, 19));
        this.add(labState, new KDLayout.Constraints(281, 611, 67, 19, 0));
        comboState.setBounds(new Rectangle(362, 618, 170, 19));
        this.add(comboState, new KDLayout.Constraints(362, 618, 170, 19, 0));
        contNumber.setBounds(new Rectangle(10, 32, 240, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(10, 10, 510, 19));
        this.add(contName, new KDLayout.Constraints(10, 10, 510, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizReqDate.setBounds(new Rectangle(280, 32, 240, 19));
        this.add(contBizReqDate, new KDLayout.Constraints(280, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(550, 32, 240, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(550, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExpenseType.setBounds(new Rectangle(279, 165, 240, 19));
        this.add(contExpenseType, new KDLayout.Constraints(279, 165, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedDept.setBounds(new Rectangle(280, 54, 240, 19));
        this.add(contCostedDept, new KDLayout.Constraints(280, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCompany.setBounds(new Rectangle(280, 76, 240, 19));
        this.add(contCompany, new KDLayout.Constraints(280, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupportedObj.setBounds(new Rectangle(550, 76, 240, 19));
        this.add(contSupportedObj, new KDLayout.Constraints(550, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplier.setBounds(new Rectangle(550, 10, 240, 19));
        this.add(contApplier, new KDLayout.Constraints(550, 10, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPosition.setBounds(new Rectangle(10, 76, 240, 19));
        this.add(contPosition, new KDLayout.Constraints(10, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTel.setBounds(new Rectangle(10, 98, 240, 19));
        this.add(contTel, new KDLayout.Constraints(10, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrencyType.setBounds(new Rectangle(10, 120, 240, 19));
        this.add(contCurrencyType, new KDLayout.Constraints(10, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayMode.setBounds(new Rectangle(280, 98, 240, 19));
        this.add(contPayMode, new KDLayout.Constraints(280, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPrior.setBounds(new Rectangle(10, 54, 240, 19));
        this.add(contPrior, new KDLayout.Constraints(10, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCause.setBounds(new Rectangle(10, 164, 98, 19));
        this.add(contCause, new KDLayout.Constraints(10, 164, 98, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(550, 120, 240, 19));
        this.add(contAmount, new KDLayout.Constraints(550, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBudgetAmount.setBounds(new Rectangle(550, 584, 240, 19));
        this.add(contBudgetAmount, new KDLayout.Constraints(550, 584, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBudgetBalance.setBounds(new Rectangle(550, 610, 240, 19));
        this.add(contBudgetBalance, new KDLayout.Constraints(550, 610, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountApproved.setBounds(new Rectangle(10, 584, 240, 19));
        this.add(contAmountApproved, new KDLayout.Constraints(10, 584, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountStriked.setBounds(new Rectangle(280, 610, 240, 19));
        this.add(contAmountStriked, new KDLayout.Constraints(280, 610, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountRefunded.setBounds(new Rectangle(10, 610, 240, 19));
        this.add(contAmountRefunded, new KDLayout.Constraints(10, 610, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountEncashed.setBounds(new Rectangle(280, 584, 240, 19));
        this.add(contAmountEncashed, new KDLayout.Constraints(280, 584, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(10, 520, 240, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 520, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBiller.setBounds(new Rectangle(550, 643, 240, 19));
        this.add(contBiller, new KDLayout.Constraints(550, 643, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBillDate.setBounds(new Rectangle(550, 673, 240, 19));
        this.add(contBillDate, new KDLayout.Constraints(550, 673, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblAuditor.setBounds(new Rectangle(10, 643, 240, 19));
        this.add(lblAuditor, new KDLayout.Constraints(10, 643, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblAuditDate.setBounds(new Rectangle(10, 673, 240, 19));
        this.add(lblAuditDate, new KDLayout.Constraints(10, 673, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblUpdate.setBounds(new Rectangle(280, 645, 240, 19));
        this.add(lblUpdate, new KDLayout.Constraints(280, 645, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblUpdateDate.setBounds(new Rectangle(280, 677, 240, 19));
        this.add(lblUpdateDate, new KDLayout.Constraints(280, 677, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 638, 780, 1));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 638, 780, 1, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(10, 185, 780, 38));
        this.add(kDScrollPane1, new KDLayout.Constraints(10, 185, 780, 38, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane2.setBounds(new Rectangle(10, 540, 780, 40));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 540, 780, 40, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAccessoryCount.setBounds(new Rectangle(550, 98, 240, 19));
        this.add(contAccessoryCount, new KDLayout.Constraints(550, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(550, 54, 240, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(550, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTextArea1.setBounds(new Rectangle(67, 147, 1, 1));
        this.add(kDTextArea1, new KDLayout.Constraints(67, 147, 1, 1, 0));
        kDTextArea2.setBounds(new Rectangle(63, 152, 1, 1));
        this.add(kDTextArea2, new KDLayout.Constraints(63, 152, 1, 1, 0));
        bgScrol.setBounds(new Rectangle(10, 225, 780, 25));
        this.add(bgScrol, new KDLayout.Constraints(10, 225, 780, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(10, 519, 290, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 519, 290, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer5.setBounds(new Rectangle(280, 519, 240, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(280, 519, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(550, 519, 240, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(550, 519, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(280, 120, 240, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(280, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        promPayName.setBounds(new Rectangle(302, 519, 19, 19));
        this.add(promPayName, new KDLayout.Constraints(302, 519, 19, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDTabbedPane.setBounds(new Rectangle(10, 250, 780, 270));
        this.add(kDTabbedPane, new KDLayout.Constraints(10, 250, 780, 270, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conspecialInvoice.setBounds(new Rectangle(10, 144, 240, 19));
        this.add(conspecialInvoice, new KDLayout.Constraints(10, 144, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conpayCompany.setBounds(new Rectangle(280, 144, 240, 19));
        this.add(conpayCompany, new KDLayout.Constraints(280, 144, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conVoucherWord.setBounds(new Rectangle(550, 144, 240, 19));
        this.add(conVoucherWord, new KDLayout.Constraints(550, 144, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer8.setBounds(new Rectangle(550, 165, 240, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(550, 165, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        isK3Paied.setBounds(new Rectangle(112, 165, 140, 19));
        this.add(isK3Paied, new KDLayout.Constraints(112, 165, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contBizReqDate
        contBizReqDate.setBoundEditor(dateBizReqDate);
        //contOrgUnit
        contOrgUnit.setBoundEditor(bizPromptOrgUnit);
        //contExpenseType
        contExpenseType.setBoundEditor(bizPromptExpenseType);
        //contCostedDept
        contCostedDept.setBoundEditor(bizPromptCostedDept);
        //contCompany
        contCompany.setBoundEditor(bizPromptCompany);
        //contSupportedObj
        contSupportedObj.setBoundEditor(bizPromptSupportedObj);
        //contApplier
        contApplier.setBoundEditor(bizPromptApplier);
        //contPosition
        contPosition.setBoundEditor(bizPromptPosition);
        //contTel
        contTel.setBoundEditor(txtTel);
        //contCurrencyType
        contCurrencyType.setBoundEditor(bizPromptCurrencyType);
        //contPayMode
        contPayMode.setBoundEditor(bizPromptPayMode);
        //contPrior
        contPrior.setBoundEditor(comboPrior);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contBudgetAmount
        contBudgetAmount.setBoundEditor(txtBudgetAmount);
        //contBudgetBalance
        contBudgetBalance.setBoundEditor(txtSendedBack);
        //contAmountApproved
        contAmountApproved.setBoundEditor(txtAmountApproved);
        //contAmountStriked
        contAmountStriked.setBoundEditor(txtAmountStriked);
        //contAmountRefunded
        contAmountRefunded.setBoundEditor(txtAmountRefunded);
        //contAmountEncashed
        contAmountEncashed.setBoundEditor(txtAmountEncashed);
        //contBiller
        contBiller.setBoundEditor(txtBiller);
        //contBillDate
        contBillDate.setBoundEditor(dateBillDate);
        //lblAuditor
        lblAuditor.setBoundEditor(txtAutitor);
        //lblAuditDate
        lblAuditDate.setBoundEditor(dateAudit);
        //lblUpdate
        lblUpdate.setBoundEditor(txtUpdate);
        //lblUpdateDate
        lblUpdateDate.setBoundEditor(dateUpdate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtCause, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDesc, null);
        //contAccessoryCount
        contAccessoryCount.setBoundEditor(txtAccessoryCount);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(bizPromptApplierCompany);
        //bgScrol
        bgScrol.getViewport().add(bgText, null);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtPayName);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(propPayBank);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtPayerAccount);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(cBoxTravelType);
        //kDTabbedPane
        kDTabbedPane.add(ctnEntry, resHelper.getString("ctnEntry.constraints"));
        kDTabbedPane.add(ctnCollectionEntry, resHelper.getString("ctnCollectionEntry.constraints"));
        kDTabbedPane.add(kDLoanContainer, resHelper.getString("kDLoanContainer.constraints"));
        //ctnEntry
ctnEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntry.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        //ctnCollectionEntry
ctnCollectionEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnCollectionEntry.getContentPane().add(kdtCollectionEntries, BorderLayout.CENTER);
        //kDLoanContainer
kDLoanContainer.getContentPane().setLayout(new BorderLayout(0, 0));        kDLoanContainer.getContentPane().add(kdtLoanCheckEntries, BorderLayout.CENTER);
        //conspecialInvoice
        conspecialInvoice.setBoundEditor(txtspecialInvoice);
        //conpayCompany
        conpayCompany.setBoundEditor(prmtpayCompany);
        //conVoucherWord
        conVoucherWord.setBoundEditor(prmtVoucherWord);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtVoucherNumber);

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
        this.menuBar.add(kDSeparator5);
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
        menuView.add(kDSeparator6);
        menuView.add(kDSeparator7);
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
        this.toolBar.add(btnViewImage);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorCommon);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW10);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnShowBg);
        this.toolBar.add(btnViewRcrdsOfLendAndRepay);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCreateToReceiveBill);
        this.toolBar.add(btnCreateToPayBill);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.bizPromptExpenseType, "data");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "stringValue");
		dataBinder.registerBinding("CollectionAccount", com.kingdee.eas.cp.bc.CollectionAccountInfo.class, this.promPayName, "data");
		dataBinder.registerBinding("isK3Paid", boolean.class, this.isK3Paied, "selected");
		dataBinder.registerBinding("payerName", String.class, this.txtPayName, "text");
		dataBinder.registerBinding("payerBankStr", com.kingdee.eas.fm.be.BEBankInfo.class, this.propPayBank, "data");
		dataBinder.registerBinding("payerAccount", String.class, this.txtPayerAccount, "text");
		dataBinder.registerBinding("travelType", com.kingdee.eas.cp.bc.TravelTypeInfo.class, this.cBoxTravelType, "selectedItem");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.cp.bc.TravelAccountBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.endDate", java.util.Date.class, this.kdtEntries, "endDate.text");
		dataBinder.registerBinding("entries.from", String.class, this.kdtEntries, "from.text");
		dataBinder.registerBinding("entries.to", String.class, this.kdtEntries, "to.text");
		dataBinder.registerBinding("entries.vehicle", com.kingdee.util.enums.Enum.class, this.kdtEntries, "vehicle.text");
		dataBinder.registerBinding("entries.taxiExpense", java.math.BigDecimal.class, this.kdtEntries, "taxiExpense.text");
		dataBinder.registerBinding("entries.bussesExpense", java.math.BigDecimal.class, this.kdtEntries, "bussesExpense.text");
		dataBinder.registerBinding("entries.hotelExpense", java.math.BigDecimal.class, this.kdtEntries, "hotelExpense.text");
		dataBinder.registerBinding("entries.otherExpense", java.math.BigDecimal.class, this.kdtEntries, "otherExpense.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.startDate", java.util.Date.class, this.kdtEntries, "startDate.text");
		dataBinder.registerBinding("entries.eventionSubsidy", java.math.BigDecimal.class, this.kdtEntries, "FEventionSubsidy.text");
		dataBinder.registerBinding("entries.expenseType", com.kingdee.eas.cp.bc.ExpenseTypeInfo.class, this.kdtEntries, "expenseType.text");
		dataBinder.registerBinding("entries.budgetAmount", java.math.BigDecimal.class, this.kdtEntries, "budgetAmount.text");
		dataBinder.registerBinding("entries.amountApproved", java.math.BigDecimal.class, this.kdtEntries, "approvedAmount.text");
		dataBinder.registerBinding("entries.operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.kdtEntries, "operationType.text");
		dataBinder.registerBinding("entries.costCenter", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.kdtEntries, "costCenter.text");
		dataBinder.registerBinding("entries.company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.kdtEntries, "company.text");
		dataBinder.registerBinding("entries.receiveState", com.kingdee.util.enums.Enum.class, this.kdtEntries, "receiveState.text");
		dataBinder.registerBinding("entries.payState", com.kingdee.util.enums.Enum.class, this.kdtEntries, "payState.text");
		dataBinder.registerBinding("entries.sourceBillNumber", String.class, this.kdtEntries, "sourceBillNumber.text");
		dataBinder.registerBinding("entries.sourceBillEntryID", String.class, this.kdtEntries, "sourceEntryId.text");
		dataBinder.registerBinding("entries.budgetAmountOri", java.math.BigDecimal.class, this.kdtEntries, "budgetAmountOri.text");
		dataBinder.registerBinding("entries.amountOri", java.math.BigDecimal.class, this.kdtEntries, "amountOri.text");
		dataBinder.registerBinding("entries.amountApprovedOri", java.math.BigDecimal.class, this.kdtEntries, "approvedAmountOri.text");
		dataBinder.registerBinding("entries.exchangeRate", java.math.BigDecimal.class, this.kdtEntries, "exchangeRate.text");
		dataBinder.registerBinding("entries.currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.kdtEntries, "currencyType.text");
		dataBinder.registerBinding("entries.convertMode", com.kingdee.eas.basedata.assistant.ConvertModeEnum.class, this.kdtEntries, "convertMode.text");
		dataBinder.registerBinding("entries.exchangeRatePrecision", int.class, this.kdtEntries, "exchangeRatePrecision.text");
		dataBinder.registerBinding("entries.project", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.kdtEntries, "project.text");
		dataBinder.registerBinding("entries.noTaxAmt", java.math.BigDecimal.class, this.kdtEntries, "noTaxAmt.text");
		dataBinder.registerBinding("entries.taxRate", java.math.BigDecimal.class, this.kdtEntries, "taxRate.text");
		dataBinder.registerBinding("entries.tax", java.math.BigDecimal.class, this.kdtEntries, "tax.text");
		dataBinder.registerBinding("entries.noTaxAmtLocal", java.math.BigDecimal.class, this.kdtEntries, "noTaxAmtLocal.text");
		dataBinder.registerBinding("entries.taxLocal", java.math.BigDecimal.class, this.kdtEntries, "taxLocal.text");
		dataBinder.registerBinding("entries.purpose", String.class, this.kdtEntries, "purpose.text");
		dataBinder.registerBinding("entries.person", com.kingdee.eas.basedata.person.PersonInfo.class, this.kdtEntries, "person.text");
		dataBinder.registerBinding("collectionEntries", com.kingdee.eas.cp.bc.TravelAccountBillAccountEntryInfo.class, this.kdtCollectionEntries, "userObject");
		dataBinder.registerBinding("collectionEntries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtCollectionEntries, "id.text");
		dataBinder.registerBinding("collectionEntries.seq", int.class, this.kdtCollectionEntries, "seq.text");
		dataBinder.registerBinding("collectionEntries.payerName", String.class, this.kdtCollectionEntries, "payerName.text");
		dataBinder.registerBinding("collectionEntries.payerBank", String.class, this.kdtCollectionEntries, "payerBank.text");
		dataBinder.registerBinding("collectionEntries.accountOpenArea", String.class, this.kdtCollectionEntries, "accountOpenArea.text");
		dataBinder.registerBinding("collectionEntries.payerAccount", String.class, this.kdtCollectionEntries, "payerAccount.text");
		dataBinder.registerBinding("collectionEntries.payMode", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.kdtCollectionEntries, "payMode.text");
		dataBinder.registerBinding("collectionEntries.currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.kdtCollectionEntries, "currencyType.text");
		dataBinder.registerBinding("collectionEntries.exchangeRate", java.math.BigDecimal.class, this.kdtCollectionEntries, "exchangeRate.text");
		dataBinder.registerBinding("collectionEntries.convertMode", com.kingdee.eas.basedata.assistant.ConvertModeEnum.class, this.kdtCollectionEntries, "convertMode.text");
		dataBinder.registerBinding("collectionEntries.exchangeRatePrecision", int.class, this.kdtCollectionEntries, "exchangeRatePrecision.text");
		dataBinder.registerBinding("collectionEntries.amountOri", java.math.BigDecimal.class, this.kdtCollectionEntries, "amountOri.text");
		dataBinder.registerBinding("collectionEntries.amount", java.math.BigDecimal.class, this.kdtCollectionEntries, "amount.text");
		dataBinder.registerBinding("collectionEntries.receiveObject", com.kingdee.eas.cp.bc.ReceiveObjectEnum.class, this.kdtCollectionEntries, "receiveObject.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillNumber", String.class, this.kdtLoanCheckEntries, "sourceBillNumber.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillDate", java.util.Date.class, this.kdtLoanCheckEntries, "sourceBillDate.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillCause", String.class, this.kdtLoanCheckEntries, "sourceBillCause.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillAmountApproved", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "sourceBillAmountApproved.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillOperationType", String.class, this.kdtLoanCheckEntries, "sourceBillOperationType.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillExpenseType", String.class, this.kdtLoanCheckEntries, "sourceBillExpenseType.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillAmountBalance", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "sourceBillAmountBalance.text");
		dataBinder.registerBinding("LoanCheckEntries.checkAmount", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "checkAmount.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillID", String.class, this.kdtLoanCheckEntries, "sourceBillId.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillEntryID", String.class, this.kdtLoanCheckEntries, "sourceBillEntryId.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillAmountUsed", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "sourceBilAmountUsed.text");
		dataBinder.registerBinding("LoanCheckEntries", com.kingdee.eas.cp.bc.TravelAccountBillLoanCheckEntryInfo.class, this.kdtLoanCheckEntries, "userObject");
		dataBinder.registerBinding("LoanCheckEntries.hasSourceBill", boolean.class, this.kdtLoanCheckEntries, "hasSourceBill.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillExpenseTypeId", String.class, this.kdtLoanCheckEntries, "sourceBillExpenseTypeId.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillCostCenterId", String.class, this.kdtLoanCheckEntries, "sourceBillCostCenterId.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillCostCenterName", String.class, this.kdtLoanCheckEntries, "sourceBillCostCenterName.text");
		dataBinder.registerBinding("LoanCheckEntries.currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.kdtLoanCheckEntries, "currencyType.text");
		dataBinder.registerBinding("LoanCheckEntries.exchangeRate", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "exchangeRate.text");
		dataBinder.registerBinding("LoanCheckEntries.sourceBillAmountBalanceOri", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "sourceBillAmountBalanceOri.text");
		dataBinder.registerBinding("LoanCheckEntries.checkAmountOri", java.math.BigDecimal.class, this.kdtLoanCheckEntries, "checkAmountOri.text");
		dataBinder.registerBinding("LoanCheckEntries.convertMode", com.kingdee.eas.basedata.assistant.ConvertModeEnum.class, this.kdtLoanCheckEntries, "convertMode.text");
		dataBinder.registerBinding("LoanCheckEntries.project", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.kdtLoanCheckEntries, "project.text");
		dataBinder.registerBinding("specialInvoice", String.class, this.txtspecialInvoice, "text");
		dataBinder.registerBinding("payCompany", com.kingdee.eas.fm.fs.InnerAccountInfo.class, this.prmtpayCompany, "data");
		dataBinder.registerBinding("voucherNum", com.kingdee.eas.cp.bc.CompanyVoucherNumEntryInfo.class, this.prmtVoucherWord, "data");
		dataBinder.registerBinding("k3VoucherNumber", String.class, this.txtVoucherNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAmountApproved, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptCostedDept, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.kDTextArea1, ActionStateConst.DISABLED);
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.prmtVoucherWord, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.dateUpdate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.dateBizReqDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.comboPrior, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptApplierCompany, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtBudgetAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.kdtLoanCheckEntries, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.cBoxTravelType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.kDTextArea2, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptPayMode, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptPosition, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAutitor, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAccessoryCount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtBiller, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptOrgUnit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtPayerAccount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAmountRefunded, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtPayName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.propPayBank, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptApplier, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptCompany, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.comboState, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtspecialInvoice, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtCause, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptSupportedObj, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtDesc, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAmount, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.dateBillDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAmountStriked, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.dateAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptExpenseType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtVoucherNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.kdtCollectionEntries, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtSendedBack, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.isK3Paied, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtAmountEncashed, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.txtUpdate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.promPayName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bizPromptCurrencyType, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.bgText, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.kdtEntries, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.prmtpayCompany, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionNext, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionHTMLForRpt, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCreateTo, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSuspenseAcc, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionViewSignature, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCopy, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCreateToPayBill, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionDelVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPre, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCreateToReceiveBill, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.updateReqDateAction, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionOnLoad, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCalculator, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCreateFrom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionViewSubmitProccess, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPersonalSite, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCloudScreen, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExcelForRpt, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionXunTongFeed, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCopyLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionLinkForRpt, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPrintPreview, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExitCurrent, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAddLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPrint, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionViewDoProccess, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExcelForMail, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.ActionAnswer, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAbout, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSendMail, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAddNew, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionReset, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionWorkFlowG, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSubmitOption, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionFirst, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionRemoveLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionHTMLForMail, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionRemove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionNumberSign, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAddReqLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionTraceDown, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionViewRrcdsOfLendAndRepay, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAddCollectionLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCloudShare, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPageSetup, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExportSelected, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.ActionRemoteAssist, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionDelLoanLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPopupPaste, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCloudFeed, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionTraceUp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionRegProduct, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAuditResult, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.ActionKnowStore, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionMsgFormat, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPrintImage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAttachment, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCancelCancel, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionWorkflowList, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExport, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExportSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionLocate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSubmit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSendMessage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionInsertLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionExportSelectedSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionVoucher, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionCopyFrom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionShowBg, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionProcductVal, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionAddLoanLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionLast, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionToolBarCustom, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionDelCollectionLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSendingMessage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionHelp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionDelReqLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionNextPerson, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionViewImage, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSave, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionPopupCopy, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionMultiapprove, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionStartWorkFlow, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VOUCHERVIEW, this.actionSignature, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.TravelAccountEditUIHandler";
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
        this.editData = (com.kingdee.eas.cp.bc.TravelAccountBillInfo)ov;
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizReqDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supportedObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prior", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountSendedBack", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountStriked", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountRefunded", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountEncashed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biller.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accessoryCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CollectionAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isK3Paid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerBankStr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payerAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("travelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.from", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.to", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.vehicle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.taxiExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.bussesExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.hotelExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.otherExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.eventionSubsidy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.expenseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costCenter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.receiveState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.payState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sourceBillNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sourceBillEntryID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.budgetAmountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApprovedOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.convertMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.exchangeRatePrecision", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.noTaxAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.taxRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.tax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.noTaxAmtLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.taxLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.payerName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.payerBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.accountOpenArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.payerAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.payMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.convertMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.exchangeRatePrecision", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.amountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectionEntries.receiveObject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillCause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillAmountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillOperationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillExpenseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillAmountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.checkAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillEntryID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillAmountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.hasSourceBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillExpenseTypeId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillCostCenterId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillCostCenterName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.sourceBillAmountBalanceOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.checkAmountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.convertMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LoanCheckEntries.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialInvoice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("voucherNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("k3VoucherNumber", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_VOUCHERVIEW.equals(this.oprtState)) {
		            this.txtAmountApproved.setEnabled(false);
		            this.bizPromptCostedDept.setEnabled(false);
		            this.kDTextArea1.setEnabled(false);
		            this.prmtVoucherWord.setEnabled(true);
		            this.dateUpdate.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.dateBizReqDate.setEnabled(false);
		            this.comboPrior.setEnabled(false);
		            this.bizPromptApplierCompany.setEnabled(false);
		            this.txtName.setEnabled(false);
		            this.txtBudgetAmount.setEnabled(false);
		            this.kdtLoanCheckEntries.setEnabled(false);
		            this.cBoxTravelType.setEnabled(false);
		            this.kDTextArea2.setEnabled(false);
		            this.bizPromptPayMode.setEnabled(false);
		            this.bizPromptPosition.setEnabled(false);
		            this.txtAutitor.setEnabled(false);
		            this.txtAccessoryCount.setEnabled(false);
		            this.txtBiller.setEnabled(false);
		            this.bizPromptOrgUnit.setEnabled(false);
		            this.txtPayerAccount.setEnabled(false);
		            this.txtAmountRefunded.setEnabled(false);
		            this.txtPayName.setEnabled(false);
		            this.propPayBank.setEnabled(false);
		            this.bizPromptApplier.setEnabled(false);
		            this.bizPromptCompany.setEnabled(false);
		            this.comboState.setEnabled(false);
		            this.txtspecialInvoice.setEnabled(false);
		            this.txtCause.setEnabled(false);
		            this.bizPromptSupportedObj.setEnabled(false);
		            this.txtDesc.setEnabled(false);
		            this.txtAmount.setEnabled(false);
		            this.dateBillDate.setEnabled(false);
		            this.txtAmountStriked.setEnabled(false);
		            this.dateAudit.setEnabled(false);
		            this.bizPromptExpenseType.setEnabled(false);
		            this.txtVoucherNumber.setEnabled(false);
		            this.kdtCollectionEntries.setEnabled(false);
		            this.txtSendedBack.setEnabled(false);
		            this.isK3Paied.setEnabled(false);
		            this.txtAmountEncashed.setEnabled(false);
		            this.txtUpdate.setEnabled(false);
		            this.promPayName.setEnabled(false);
		            this.bizPromptCurrencyType.setEnabled(false);
		            this.bgText.setEnabled(false);
		            this.kdtEntries.setEnabled(false);
		            this.prmtpayCompany.setEnabled(false);
		            this.actionNext.setEnabled(false);
		            this.actionHTMLForRpt.setEnabled(false);
		            this.actionCreateTo.setEnabled(false);
		            this.actionSuspenseAcc.setEnabled(false);
		            this.actionViewSignature.setEnabled(false);
		            this.actionCopy.setEnabled(false);
		            this.actionCreateToPayBill.setEnabled(false);
		            this.actionDelVoucher.setEnabled(false);
		            this.actionPre.setEnabled(false);
		            this.actionCreateToReceiveBill.setEnabled(false);
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
		            this.actionAddCollectionLine.setEnabled(false);
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
		            this.actionVoucher.setEnabled(false);
		            this.actionCopyFrom.setEnabled(false);
		            this.actionShowBg.setEnabled(false);
		            this.actionProcductVal.setEnabled(false);
		            this.actionAddLoanLine.setEnabled(false);
		            this.actionLast.setEnabled(false);
		            this.actionToolBarCustom.setEnabled(false);
		            this.actionDelCollectionLine.setEnabled(false);
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
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
        sic.add(new SelectorItemInfo("tel"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CollectionAccount.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CollectionAccount.id"));
        	sic.add(new SelectorItemInfo("CollectionAccount.number"));
        	sic.add(new SelectorItemInfo("CollectionAccount.name"));
        	sic.add(new SelectorItemInfo("CollectionAccount.payee"));
		}
        sic.add(new SelectorItemInfo("isK3Paid"));
        sic.add(new SelectorItemInfo("payerName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payerBankStr.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payerBankStr.id"));
        	sic.add(new SelectorItemInfo("payerBankStr.number"));
        	sic.add(new SelectorItemInfo("payerBankStr.name"));
		}
        sic.add(new SelectorItemInfo("payerAccount"));
        sic.add(new SelectorItemInfo("travelType"));
    	sic.add(new SelectorItemInfo("entries.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entries.endDate"));
    	sic.add(new SelectorItemInfo("entries.from"));
    	sic.add(new SelectorItemInfo("entries.to"));
    	sic.add(new SelectorItemInfo("entries.vehicle"));
    	sic.add(new SelectorItemInfo("entries.taxiExpense"));
    	sic.add(new SelectorItemInfo("entries.bussesExpense"));
    	sic.add(new SelectorItemInfo("entries.hotelExpense"));
    	sic.add(new SelectorItemInfo("entries.otherExpense"));
    	sic.add(new SelectorItemInfo("entries.amount"));
    	sic.add(new SelectorItemInfo("entries.startDate"));
    	sic.add(new SelectorItemInfo("entries.eventionSubsidy"));
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
    	sic.add(new SelectorItemInfo("entries.amountApproved"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.operationType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.operationType.id"));
			sic.add(new SelectorItemInfo("entries.operationType.name"));
        	sic.add(new SelectorItemInfo("entries.operationType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.costCenter.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.costCenter.id"));
			sic.add(new SelectorItemInfo("entries.costCenter.name"));
        	sic.add(new SelectorItemInfo("entries.costCenter.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.company.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.company.id"));
			sic.add(new SelectorItemInfo("entries.company.name"));
        	sic.add(new SelectorItemInfo("entries.company.number"));
		}
    	sic.add(new SelectorItemInfo("entries.receiveState"));
    	sic.add(new SelectorItemInfo("entries.payState"));
    	sic.add(new SelectorItemInfo("entries.sourceBillNumber"));
    	sic.add(new SelectorItemInfo("entries.sourceBillEntryID"));
    	sic.add(new SelectorItemInfo("entries.budgetAmountOri"));
    	sic.add(new SelectorItemInfo("entries.amountOri"));
    	sic.add(new SelectorItemInfo("entries.amountApprovedOri"));
    	sic.add(new SelectorItemInfo("entries.exchangeRate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.currencyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.currencyType.id"));
			sic.add(new SelectorItemInfo("entries.currencyType.name"));
        	sic.add(new SelectorItemInfo("entries.currencyType.number"));
		}
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
    	sic.add(new SelectorItemInfo("entries.noTaxAmt"));
    	sic.add(new SelectorItemInfo("entries.taxRate"));
    	sic.add(new SelectorItemInfo("entries.tax"));
    	sic.add(new SelectorItemInfo("entries.noTaxAmtLocal"));
    	sic.add(new SelectorItemInfo("entries.taxLocal"));
    	sic.add(new SelectorItemInfo("entries.purpose"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.person.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.person.id"));
			sic.add(new SelectorItemInfo("entries.person.name"));
        	sic.add(new SelectorItemInfo("entries.person.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("collectionEntries.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("collectionEntries.id"));
    	sic.add(new SelectorItemInfo("collectionEntries.seq"));
    	sic.add(new SelectorItemInfo("collectionEntries.payerName"));
    	sic.add(new SelectorItemInfo("collectionEntries.payerBank"));
    	sic.add(new SelectorItemInfo("collectionEntries.accountOpenArea"));
    	sic.add(new SelectorItemInfo("collectionEntries.payerAccount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("collectionEntries.payMode.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("collectionEntries.payMode.id"));
			sic.add(new SelectorItemInfo("collectionEntries.payMode.name"));
        	sic.add(new SelectorItemInfo("collectionEntries.payMode.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("collectionEntries.currencyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("collectionEntries.currencyType.id"));
			sic.add(new SelectorItemInfo("collectionEntries.currencyType.name"));
        	sic.add(new SelectorItemInfo("collectionEntries.currencyType.number"));
		}
    	sic.add(new SelectorItemInfo("collectionEntries.exchangeRate"));
    	sic.add(new SelectorItemInfo("collectionEntries.convertMode"));
    	sic.add(new SelectorItemInfo("collectionEntries.exchangeRatePrecision"));
    	sic.add(new SelectorItemInfo("collectionEntries.amountOri"));
    	sic.add(new SelectorItemInfo("collectionEntries.amount"));
    	sic.add(new SelectorItemInfo("collectionEntries.receiveObject"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillNumber"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillDate"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillCause"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillAmountApproved"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillOperationType"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillExpenseType"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillAmountBalance"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.checkAmount"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillID"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillEntryID"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillAmountUsed"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("LoanCheckEntries.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("LoanCheckEntries.hasSourceBill"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillExpenseTypeId"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillCostCenterId"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillCostCenterName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("LoanCheckEntries.currencyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("LoanCheckEntries.currencyType.id"));
			sic.add(new SelectorItemInfo("LoanCheckEntries.currencyType.name"));
        	sic.add(new SelectorItemInfo("LoanCheckEntries.currencyType.number"));
		}
    	sic.add(new SelectorItemInfo("LoanCheckEntries.exchangeRate"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.sourceBillAmountBalanceOri"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.checkAmountOri"));
    	sic.add(new SelectorItemInfo("LoanCheckEntries.convertMode"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("LoanCheckEntries.project.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("LoanCheckEntries.project.id"));
			sic.add(new SelectorItemInfo("LoanCheckEntries.project.name"));
        	sic.add(new SelectorItemInfo("LoanCheckEntries.project.number"));
		}
        sic.add(new SelectorItemInfo("specialInvoice"));
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
            innerActionPerformed("eas", AbstractTravelAccountEditUI.this, "ActionViewRrcdsOfLendAndRepay", "actionViewRrcdsOfLendAndRepay_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "TravelAccountEditUI");
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
        return com.kingdee.eas.cp.bc.client.TravelAccountEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.TravelAccountBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.TravelAccountBillInfo objectValue = new com.kingdee.eas.cp.bc.TravelAccountBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/cp/bc/TravelAccountBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.TravelAccountQuery");
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
		vo.put("amountSendedBack",new java.math.BigDecimal(0.0));
		vo.put("amountApproved",new java.math.BigDecimal(0.0));
		vo.put("amountStriked",new java.math.BigDecimal(0));
		vo.put("amountRefunded",new java.math.BigDecimal(0.00));
		vo.put("accessoryCount",new Integer(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}