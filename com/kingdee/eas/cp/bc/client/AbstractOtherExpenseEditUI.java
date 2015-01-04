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
public abstract class AbstractOtherExpenseEditUI extends com.kingdee.eas.cp.bc.client.ExpenseReqEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOtherExpenseEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckAmountControl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane bgScrol;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsChanged;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCentralPur;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer29;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer30;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnLinkOthExpense;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountUsed;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountBalance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplierCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmpPayMode;
    protected com.kingdee.bos.ctrl.swing.KDTextArea bgText;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCurrencyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtServiceDept;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApplyAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable linkOthExpenseEntry;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBudget;
    protected com.kingdee.eas.cp.bc.OtherExpenseBillInfo editData = null;
    protected ActionAddLinkLine actionAddLinkLine = null;
    protected ActionRemoveLinkLine actionRemoveLinkLine = null;
    /**
     * output class constructor
     */
    public AbstractOtherExpenseEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOtherExpenseEditUI.class.getName());
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
        //actionAddLinkLine
        this.actionAddLinkLine = new ActionAddLinkLine(this);
        getActionManager().registerAction("actionAddLinkLine", actionAddLinkLine);
         this.actionAddLinkLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLinkLine
        this.actionRemoveLinkLine = new ActionRemoveLinkLine(this);
        getActionManager().registerAction("actionRemoveLinkLine", actionRemoveLinkLine);
         this.actionRemoveLinkLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDCheckAmountControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bgScrol = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsChanged = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsCentralPur = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer29 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer30 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnLinkOthExpense = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtAmountUsed = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmountBalance = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bizPromptApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmpPayMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bgText = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.bizPromptCurrencyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtServiceDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtApplyAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.linkOthExpenseEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.menuItemViewBudget = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.kDCheckAmountControl.setName("kDCheckAmountControl");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.bgScrol.setName("bgScrol");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.chkIsChanged.setName("chkIsChanged");
        this.chkIsCentralPur.setName("chkIsCentralPur");
        this.kDLabelContainer29.setName("kDLabelContainer29");
        this.kDLabelContainer30.setName("kDLabelContainer30");
        this.ctnLinkOthExpense.setName("ctnLinkOthExpense");
        this.kdtEntries.setName("kdtEntries");
        this.txtAmountUsed.setName("txtAmountUsed");
        this.txtAmountBalance.setName("txtAmountBalance");
        this.bizPromptApplierCompany.setName("bizPromptApplierCompany");
        this.prmpPayMode.setName("prmpPayMode");
        this.bgText.setName("bgText");
        this.bizPromptCurrencyType.setName("bizPromptCurrencyType");
        this.prmtServiceDept.setName("prmtServiceDept");
        this.txtApplyAmount.setName("txtApplyAmount");
        this.linkOthExpenseEntry.setName("linkOthExpenseEntry");
        this.menuItemViewBudget.setName("menuItemViewBudget");
        // CoreUI		
        this.btnPrintPreview.setEnabled(true);		
        this.btnPrintPreview.setVisible(true);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuBiz.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.separatorFW4.setEnabled(true);		
        this.separatorFW7.setEnabled(false);		
        this.separatorFW7.setVisible(false);		
        this.separatorFW5.setVisible(true);		
        this.separatorFW5.setEnabled(true);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.btnWFViewdoProccess.setVisible(false);		
        this.btnWFViewSubmitProccess.setVisible(false);		
        this.menuItemCreateTo.setText(resHelper.getString("menuItemCreateTo.text"));		
        this.btnPrintImage.setVisible(false);		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setVisible(false);		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setVisible(true);		
        this.kDLabelContainer17.setForeground(new java.awt.Color(0,0,0));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.ctnEntries.setEnableActive(false);		
        this.ctnEntries.setTitleStyle(2);		
        this.txtName.setRequired(false);		
        this.bizPromptOrgUnit.setEnabled(true);		
        this.bizPromptCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
        this.bizPromptCompany.setEnabled(true);		
        this.bizPromptPosition.setEnabled(true);		
        this.bizPromptPosition.setEditable(true);		
        this.txtTel.setRequired(false);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelUnderline(true);		
        this.kDLabelContainer26.setBoundLabelLength(100);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelLength(100);		
        this.kDLabelContainer27.setBoundLabelUnderline(true);
        // kDCheckAmountControl		
        this.kDCheckAmountControl.setText(resHelper.getString("kDCheckAmountControl.text"));
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelLength(100);		
        this.kDLabelContainer28.setBoundLabelUnderline(true);
        // bgScrol		
        this.bgScrol.setAutoscrolls(true);		
        this.bgScrol.setVisible(false);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // chkIsChanged		
        this.chkIsChanged.setText(resHelper.getString("chkIsChanged.text"));
        // chkIsCentralPur		
        this.chkIsCentralPur.setText(resHelper.getString("chkIsCentralPur.text"));
        // kDLabelContainer29		
        this.kDLabelContainer29.setBoundLabelText(resHelper.getString("kDLabelContainer29.boundLabelText"));		
        this.kDLabelContainer29.setBoundLabelLength(100);		
        this.kDLabelContainer29.setBoundLabelUnderline(true);
        // kDLabelContainer30		
        this.kDLabelContainer30.setBoundLabelText(resHelper.getString("kDLabelContainer30.boundLabelText"));		
        this.kDLabelContainer30.setBoundLabelLength(100);		
        this.kDLabelContainer30.setBoundLabelUnderline(true);
        // ctnLinkOthExpense		
        this.ctnLinkOthExpense.setTitle(resHelper.getString("ctnLinkOthExpense.title"));		
        this.ctnLinkOthExpense.setTitleStyle(2);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%r-[=]{#,##0.0000}.4f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"expenseType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"costedDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"purpose\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"currencyType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" /><t:Column t:key=\"exchangeRate\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"amountOri\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"approvedAmountOri\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"approvedAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"amountUsedOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"amountUsed\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"amountBalanceOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"amountBalance\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"comment\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"convertMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"exchangeRatePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{expenseType}</t:Cell><t:Cell>$Resource{costedDept}</t:Cell><t:Cell>$Resource{purpose}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{amountOri}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{approvedAmountOri}</t:Cell><t:Cell>$Resource{approvedAmount}</t:Cell><t:Cell>$Resource{amountUsedOri}</t:Cell><t:Cell>$Resource{amountUsed}</t:Cell><t:Cell>$Resource{amountBalanceOri}</t:Cell><t:Cell>$Resource{amountBalance}</t:Cell><t:Cell>$Resource{comment}</t:Cell><t:Cell>$Resource{convertMode}</t:Cell><t:Cell>$Resource{exchangeRatePrecision}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntries.putBindContents("editData",new String[] {"id","project","expenseType","costedDept","purpose","currencyType","exchangeRate","amountOri","amount","amountApprovedOri","amountApproved","amountUsedOri","amountUsed","amountBalanceOri","amountBalance","comment","convertMode","exchangeRatePrecision"});


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
        final KDBizPromptBox kdtEntries_costedDept_PromptBox = new KDBizPromptBox();
        kdtEntries_costedDept_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");
        kdtEntries_costedDept_PromptBox.setVisible(true);
        kdtEntries_costedDept_PromptBox.setEditable(true);
        kdtEntries_costedDept_PromptBox.setDisplayFormat("$number$");
        kdtEntries_costedDept_PromptBox.setEditFormat("$number$");
        kdtEntries_costedDept_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_costedDept_CellEditor = new KDTDefaultCellEditor(kdtEntries_costedDept_PromptBox);
        this.kdtEntries.getColumn("costedDept").setEditor(kdtEntries_costedDept_CellEditor);
        ObjectValueRender kdtEntries_costedDept_OVR = new ObjectValueRender();
        kdtEntries_costedDept_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("costedDept").setRenderer(kdtEntries_costedDept_OVR);
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
        // txtAmountUsed		
        this.txtAmountUsed.setPrecision(2);
        // txtAmountBalance		
        this.txtAmountBalance.setPrecision(2);
        // bizPromptApplierCompany		
        this.bizPromptApplierCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.bizPromptApplierCompany.setRequired(true);
        // prmpPayMode
        // bgText		
        this.bgText.setMaxLength(400);		
        this.bgText.setEditable(false);
        // bizPromptCurrencyType		
        this.bizPromptCurrencyType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");		
        this.bizPromptCurrencyType.setEnabled(false);
        // prmtServiceDept
        // txtApplyAmount		
        this.txtApplyAmount.setPrecision(2);		
        this.txtApplyAmount.setEnabled(false);
        // linkOthExpenseEntry
		String linkOthExpenseEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"otherExenpseBill\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"usedAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{otherExenpseBill}</t:Cell><t:Cell>$Resource{usedAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.linkOthExpenseEntry.setFormatXml(resHelper.translateString("linkOthExpenseEntry",linkOthExpenseEntryStrXML));

                this.linkOthExpenseEntry.putBindContents("editData",new String[] {"id","otherExpenseBill","usedAmount"});


        this.linkOthExpenseEntry.checkParsed();
        final KDBizPromptBox linkOthExpenseEntry_otherExenpseBill_PromptBox = new KDBizPromptBox();
        linkOthExpenseEntry_otherExenpseBill_PromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.OtherExpBillQuery");
        linkOthExpenseEntry_otherExenpseBill_PromptBox.setVisible(true);
        linkOthExpenseEntry_otherExenpseBill_PromptBox.setEditable(true);
        linkOthExpenseEntry_otherExenpseBill_PromptBox.setDisplayFormat("$number$");
        linkOthExpenseEntry_otherExenpseBill_PromptBox.setEditFormat("$number$");
        linkOthExpenseEntry_otherExenpseBill_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor linkOthExpenseEntry_otherExenpseBill_CellEditor = new KDTDefaultCellEditor(linkOthExpenseEntry_otherExenpseBill_PromptBox);
        this.linkOthExpenseEntry.getColumn("otherExenpseBill").setEditor(linkOthExpenseEntry_otherExenpseBill_CellEditor);
        ObjectValueRender linkOthExpenseEntry_otherExenpseBill_OVR = new ObjectValueRender();
        linkOthExpenseEntry_otherExenpseBill_OVR.setFormat(new BizDataFormat("$name$"));
        this.linkOthExpenseEntry.getColumn("otherExenpseBill").setRenderer(linkOthExpenseEntry_otherExenpseBill_OVR);
        KDFormattedTextField linkOthExpenseEntry_usedAmount_TextField = new KDFormattedTextField();
        linkOthExpenseEntry_usedAmount_TextField.setName("linkOthExpenseEntry_usedAmount_TextField");
        linkOthExpenseEntry_usedAmount_TextField.setVisible(true);
        linkOthExpenseEntry_usedAmount_TextField.setEditable(true);
        linkOthExpenseEntry_usedAmount_TextField.setHorizontalAlignment(2);
        linkOthExpenseEntry_usedAmount_TextField.setDataType(1);
        	linkOthExpenseEntry_usedAmount_TextField.setMinimumValue(new java.math.BigDecimal("-99999.99999999"));
        	linkOthExpenseEntry_usedAmount_TextField.setMaximumValue(new java.math.BigDecimal("99999.99999999"));
        linkOthExpenseEntry_usedAmount_TextField.setPrecision(8);
        KDTDefaultCellEditor linkOthExpenseEntry_usedAmount_CellEditor = new KDTDefaultCellEditor(linkOthExpenseEntry_usedAmount_TextField);
        this.linkOthExpenseEntry.getColumn("usedAmount").setEditor(linkOthExpenseEntry_usedAmount_CellEditor);
        // menuItemViewBudget
        this.menuItemViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionBudget, new Class[] { IItemAction.class }, getServiceContext()));		
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
        kDLabelContainer1.setBounds(new Rectangle(10, 32, 240, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 510, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 10, 510, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(550, 32, 240, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(550, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(10, 54, 240, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(280, 54, 240, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(280, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(550, 76, 240, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(550, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(10, 164, 240, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 164, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(550, 10, 240, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(550, 10, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer9.setBounds(new Rectangle(280, 32, 240, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(280, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(280, 76, 240, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(280, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer12.setBounds(new Rectangle(280, 120, 240, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(280, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(10, 488, 240, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(10, 488, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(10, 76, 240, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(10, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(550, 98, 240, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(550, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer17.setBounds(new Rectangle(10, 299, 240, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(10, 299, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer18.setBounds(new Rectangle(10, 549, 240, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(10, 549, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer19.setBounds(new Rectangle(279, 549, 240, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(279, 549, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer20.setBounds(new Rectangle(549, 549, 240, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(549, 549, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer21.setBounds(new Rectangle(10, 571, 240, 19));
        this.add(kDLabelContainer21, new KDLayout.Constraints(10, 571, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer22.setBounds(new Rectangle(279, 571, 240, 19));
        this.add(kDLabelContainer22, new KDLayout.Constraints(279, 571, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer23.setBounds(new Rectangle(549, 571, 240, 19));
        this.add(kDLabelContainer23, new KDLayout.Constraints(549, 571, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        ctnEntries.setBounds(new Rectangle(10, 382, 780, 104));
        this.add(ctnEntries, new KDLayout.Constraints(10, 382, 780, 104, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(10, 319, 780, 38));
        this.add(kDScrollPane1, new KDLayout.Constraints(10, 319, 780, 38, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane2.setBounds(new Rectangle(10, 509, 780, 37));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 509, 780, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(0, 0, 1, 1));
        this.add(kDLabelContainer15, new KDLayout.Constraints(0, 0, 1, 1, 0));
        kDLabelContainer24.setBounds(new Rectangle(550, 120, 240, 19));
        this.add(kDLabelContainer24, new KDLayout.Constraints(550, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer25.setBounds(new Rectangle(280, 164, 240, 19));
        this.add(kDLabelContainer25, new KDLayout.Constraints(280, 164, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer26.setBounds(new Rectangle(550, 164, 240, 19));
        this.add(kDLabelContainer26, new KDLayout.Constraints(550, 164, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer27.setBounds(new Rectangle(550, 54, 240, 19));
        this.add(kDLabelContainer27, new KDLayout.Constraints(550, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDCheckAmountControl.setBounds(new Rectangle(10, 120, 240, 19));
        this.add(kDCheckAmountControl, new KDLayout.Constraints(10, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer28.setBounds(new Rectangle(280, 98, 240, 19));
        this.add(kDLabelContainer28, new KDLayout.Constraints(280, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        bgScrol.setBounds(new Rectangle(10, 359, 780, 21));
        this.add(bgScrol, new KDLayout.Constraints(10, 359, 780, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(10, 98, 240, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(10, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsChanged.setBounds(new Rectangle(10, 142, 140, 19));
        this.add(chkIsChanged, new KDLayout.Constraints(10, 142, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsCentralPur.setBounds(new Rectangle(168, 142, 82, 19));
        this.add(chkIsCentralPur, new KDLayout.Constraints(168, 142, 82, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer29.setBounds(new Rectangle(280, 142, 240, 19));
        this.add(kDLabelContainer29, new KDLayout.Constraints(280, 142, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer30.setBounds(new Rectangle(550, 142, 240, 19));
        this.add(kDLabelContainer30, new KDLayout.Constraints(550, 142, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        ctnLinkOthExpense.setBounds(new Rectangle(11, 187, 780, 110));
        this.add(ctnLinkOthExpense, new KDLayout.Constraints(11, 187, 780, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(bizPromptOrgUnit);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(comboPrior);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptCostedDept);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(bizPromptSupportedObj);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptExpenseType);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptApplier);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(dateBizReqDate);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(bizPromptCompany);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtAmount);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(bizPromptPosition);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtTel);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(txtAuditor);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtUpdateEditor);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtBiller);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(dateAudit);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(dateUpdate);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(dateBillMake);
        //ctnEntries
ctnEntries.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntries.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(areaCause, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(areaDescription, null);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtAmountSendBack);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(txtApprovedAmount);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(txtAmountUsed);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(txtAmountBalance);
        //kDLabelContainer27
        kDLabelContainer27.setBoundEditor(bizPromptApplierCompany);
        //kDLabelContainer28
        kDLabelContainer28.setBoundEditor(prmpPayMode);
        //bgScrol
        bgScrol.getViewport().add(bgText, null);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(bizPromptCurrencyType);
        //kDLabelContainer29
        kDLabelContainer29.setBoundEditor(prmtServiceDept);
        //kDLabelContainer30
        kDLabelContainer30.setBoundEditor(txtApplyAmount);
        //ctnLinkOthExpense
ctnLinkOthExpense.getContentPane().setLayout(new BorderLayout(0, 0));        ctnLinkOthExpense.getContentPane().add(linkOthExpenseEntry, BorderLayout.CENTER);

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
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnBudget);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(separatorFW9);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.bizPromptExpenseType, "data");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "stringValue");
		dataBinder.registerBinding("isChanged", boolean.class, this.chkIsChanged, "selected");
		dataBinder.registerBinding("isCentralPur", boolean.class, this.chkIsCentralPur, "selected");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.cp.bc.OtherExpenseBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.purpose", String.class, this.kdtEntries, "purpose.text");
		dataBinder.registerBinding("entries.comment", String.class, this.kdtEntries, "comment.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.expenseType", com.kingdee.eas.cp.bc.ExpenseTypeInfo.class, this.kdtEntries, "expenseType.text");
		dataBinder.registerBinding("entries.amountUsed", java.math.BigDecimal.class, this.kdtEntries, "amountUsed.text");
		dataBinder.registerBinding("entries.amountBalance", java.math.BigDecimal.class, this.kdtEntries, "amountBalance.text");
		dataBinder.registerBinding("entries.amountOri", java.math.BigDecimal.class, this.kdtEntries, "amountOri.text");
		dataBinder.registerBinding("entries.exchangeRate", java.math.BigDecimal.class, this.kdtEntries, "exchangeRate.text");
		dataBinder.registerBinding("entries.currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.kdtEntries, "currencyType.text");
		dataBinder.registerBinding("entries.amountApproved", java.math.BigDecimal.class, this.kdtEntries, "approvedAmount.text");
		dataBinder.registerBinding("entries.amountApprovedOri", java.math.BigDecimal.class, this.kdtEntries, "approvedAmountOri.text");
		dataBinder.registerBinding("entries.convertMode", com.kingdee.eas.basedata.assistant.ConvertModeEnum.class, this.kdtEntries, "convertMode.text");
		dataBinder.registerBinding("entries.exchangeRatePrecision", int.class, this.kdtEntries, "exchangeRatePrecision.text");
		dataBinder.registerBinding("entries.amountUsedOri", java.math.BigDecimal.class, this.kdtEntries, "amountUsedOri.text");
		dataBinder.registerBinding("entries.amountBalanceOri", java.math.BigDecimal.class, this.kdtEntries, "amountBalanceOri.text");
		dataBinder.registerBinding("entries.project", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.kdtEntries, "project.text");
		dataBinder.registerBinding("entries.costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.kdtEntries, "costedDept.text");
		dataBinder.registerBinding("amountUsed", java.math.BigDecimal.class, this.txtAmountUsed, "value");
		dataBinder.registerBinding("amountBalance", java.math.BigDecimal.class, this.txtAmountBalance, "value");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptApplierCompany, "data");
		dataBinder.registerBinding("payMode", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmpPayMode, "data");
		dataBinder.registerBinding("currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.bizPromptCurrencyType, "data");
		dataBinder.registerBinding("serviceDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtServiceDept, "data");
		dataBinder.registerBinding("applyAmount", java.math.BigDecimal.class, this.txtApplyAmount, "value");
		dataBinder.registerBinding("linkOthExpense", com.kingdee.eas.cp.bc.LinkOthExpenseInfo.class, this.linkOthExpenseEntry, "userObject");
		dataBinder.registerBinding("linkOthExpense.id", com.kingdee.bos.util.BOSUuid.class, this.linkOthExpenseEntry, "id.text");
		dataBinder.registerBinding("linkOthExpense.otherExpenseBill", com.kingdee.eas.cp.bc.LinkOthExpenseInfo.class, this.linkOthExpenseEntry, "otherExenpseBill.text");
		dataBinder.registerBinding("linkOthExpense.usedAmount", java.math.BigDecimal.class, this.linkOthExpenseEntry, "usedAmount.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.OtherExpenseEditUIHandler";
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
        this.editData = (com.kingdee.eas.cp.bc.OtherExpenseBillInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prior", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supportedObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizReqDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biller.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountSendedBack", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isChanged", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCentralPur", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.expenseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApprovedOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.convertMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.exchangeRatePrecision", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountUsedOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountBalanceOri", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("serviceDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkOthExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkOthExpense.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkOthExpense.otherExpenseBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkOthExpense.usedAmount", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output kdtEntries_editStopped method
     */
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isChanged"));
        sic.add(new SelectorItemInfo("isCentralPur"));
    	sic.add(new SelectorItemInfo("entries.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
			sic.add(new SelectorItemInfo("entries.name"));
		}
    	sic.add(new SelectorItemInfo("entries.purpose"));
    	sic.add(new SelectorItemInfo("entries.comment"));
    	sic.add(new SelectorItemInfo("entries.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.expenseType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.expenseType.id"));
			sic.add(new SelectorItemInfo("entries.expenseType.name"));
        	sic.add(new SelectorItemInfo("entries.expenseType.number"));
		}
    	sic.add(new SelectorItemInfo("entries.amountUsed"));
    	sic.add(new SelectorItemInfo("entries.amountBalance"));
    	sic.add(new SelectorItemInfo("entries.amountOri"));
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
    	sic.add(new SelectorItemInfo("entries.amountApproved"));
    	sic.add(new SelectorItemInfo("entries.amountApprovedOri"));
    	sic.add(new SelectorItemInfo("entries.convertMode"));
    	sic.add(new SelectorItemInfo("entries.exchangeRatePrecision"));
    	sic.add(new SelectorItemInfo("entries.amountUsedOri"));
    	sic.add(new SelectorItemInfo("entries.amountBalanceOri"));
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
        sic.add(new SelectorItemInfo("amountUsed"));
        sic.add(new SelectorItemInfo("amountBalance"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applierCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applierCompany.id"));
        	sic.add(new SelectorItemInfo("applierCompany.number"));
        	sic.add(new SelectorItemInfo("applierCompany.name"));
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
			sic.add(new SelectorItemInfo("serviceDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("serviceDept.id"));
        	sic.add(new SelectorItemInfo("serviceDept.number"));
        	sic.add(new SelectorItemInfo("serviceDept.name"));
		}
        sic.add(new SelectorItemInfo("applyAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("linkOthExpense.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("linkOthExpense.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.id"));
			sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.name"));
        	sic.add(new SelectorItemInfo("linkOthExpense.otherExpenseBill.number"));
		}
    	sic.add(new SelectorItemInfo("linkOthExpense.usedAmount"));
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
     * output actionAddLinkLine_actionPerformed method
     */
    public void actionAddLinkLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveLinkLine_actionPerformed method
     */
    public void actionRemoveLinkLine_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAddLinkLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLinkLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveLinkLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLinkLine() {
    	return false;
    }

    /**
     * output ActionAddLinkLine class
     */     
    protected class ActionAddLinkLine extends ItemAction {     
    
        public ActionAddLinkLine()
        {
            this(null);
        }

        public ActionAddLinkLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddLinkLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLinkLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLinkLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherExpenseEditUI.this, "ActionAddLinkLine", "actionAddLinkLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveLinkLine class
     */     
    protected class ActionRemoveLinkLine extends ItemAction {     
    
        public ActionRemoveLinkLine()
        {
            this(null);
        }

        public ActionRemoveLinkLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveLinkLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLinkLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLinkLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherExpenseEditUI.this, "ActionRemoveLinkLine", "actionRemoveLinkLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "OtherExpenseEditUI");
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
        return com.kingdee.eas.cp.bc.client.OtherExpenseEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.OtherExpenseBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.OtherExpenseBillInfo objectValue = new com.kingdee.eas.cp.bc.OtherExpenseBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/cp/bc/OtherExpenseBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.OtherExpBillQuery");
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
		vo.put("amountUsed",new java.math.BigDecimal(0.0));
		vo.put("amountBalance",new java.math.BigDecimal(0.0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}