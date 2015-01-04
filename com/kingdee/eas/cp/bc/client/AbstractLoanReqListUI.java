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
public abstract class AbstractLoanReqListUI extends com.kingdee.eas.cp.bc.client.BizCollCoreBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLoanReqListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRcrdsOfLendAndRepay;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPayOff;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReturn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnComfirmReturn;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewRcds;
    protected ActionViewRrcdsOfLendAndRepay actionViewRrcdsOfLendAndRepay = null;
    protected ActionPayOff actionPayOff = null;
    protected ActionReturn actionReturn = null;
    protected ActionComfirmReturn actionComfirmReturn = null;
    /**
     * output class constructor
     */
    public AbstractLoanReqListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLoanReqListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.cp.bc.app", "DailyLoanBillQuery");
        //actionCreateTo
        String _tempStr = null;
        actionCreateTo.setEnabled(true);
        actionCreateTo.setDaemonRun(false);

        actionCreateTo.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt R"));
        _tempStr = resHelper.getString("ActionCreateTo.SHORT_DESCRIPTION");
        actionCreateTo.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateTo.LONG_DESCRIPTION");
        actionCreateTo.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateTo.NAME");
        actionCreateTo.putValue(ItemAction.NAME, _tempStr);
        this.actionCreateTo.setBindWorkFlow(true);
         this.actionCreateTo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCreateTo.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCreateTo.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAntiAudit
        actionAntiAudit.setEnabled(true);
        actionAntiAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAntiAudit.SHORT_DESCRIPTION");
        actionAntiAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAntiAudit.LONG_DESCRIPTION");
        actionAntiAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAntiAudit.NAME");
        actionAntiAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAntiAudit.setBindWorkFlow(true);
         this.actionAntiAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAbandon
        actionAbandon.setEnabled(false);
        actionAbandon.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAbandon.SHORT_DESCRIPTION");
        actionAbandon.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAbandon.LONG_DESCRIPTION");
        actionAbandon.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAbandon.NAME");
        actionAbandon.putValue(ItemAction.NAME, _tempStr);
        this.actionAbandon.setBindWorkFlow(true);
         this.actionAbandon.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewRrcdsOfLendAndRepay
        this.actionViewRrcdsOfLendAndRepay = new ActionViewRrcdsOfLendAndRepay(this);
        getActionManager().registerAction("actionViewRrcdsOfLendAndRepay", actionViewRrcdsOfLendAndRepay);
         this.actionViewRrcdsOfLendAndRepay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPayOff
        this.actionPayOff = new ActionPayOff(this);
        getActionManager().registerAction("actionPayOff", actionPayOff);
         this.actionPayOff.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReturn
        this.actionReturn = new ActionReturn(this);
        getActionManager().registerAction("actionReturn", actionReturn);
         this.actionReturn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionComfirmReturn
        this.actionComfirmReturn = new ActionComfirmReturn(this);
        getActionManager().registerAction("actionComfirmReturn", actionComfirmReturn);
         this.actionComfirmReturn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnViewRcrdsOfLendAndRepay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPayOff = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReturn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnComfirmReturn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatchSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewRcds = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewRcrdsOfLendAndRepay.setName("btnViewRcrdsOfLendAndRepay");
        this.btnPayOff.setName("btnPayOff");
        this.btnReturn.setName("btnReturn");
        this.btnComfirmReturn.setName("btnComfirmReturn");
        this.menuItemBatchSubmit.setName("menuItemBatchSubmit");
        this.menuItemViewRcds.setName("menuItemViewRcds");
        // CoreUI		
        this.setPreferredSize(new Dimension(800,600));		
        this.btnPageSetup.setEnabled(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol34\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol37\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol38\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol39\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol40\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol41\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol42\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol43\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol46\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat></c:Style><c:Style id=\"sCol50\"><c:NumberFormat>%r-[=]{#,##0.0000}.4f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol53\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol54\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol55\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol56\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"148\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"cause\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"operationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"currencyType1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"approvedAccount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"amountPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"amountNotPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"amountUsed\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"amountSendedBack\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"amountBalance\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"payMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"amountControlType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"currencyType.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"applier.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"state\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"isOverBudget\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"isVouchered\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"applier.name\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"company.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"company.name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"applierCompanyId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"orgUnit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"orgUnit.name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"bizReqDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"billTypeCode\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"biller.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"biller.name\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"prior\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"billDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"position.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"position.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"expenseType.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"expenseType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"supportedObj.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"supportedObj.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" t:styleID=\"sCol37\" /><t:Column t:key=\"costedDept.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" t:styleID=\"sCol38\" /><t:Column t:key=\"costedDept.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" t:styleID=\"sCol39\" /><t:Column t:key=\"fundPlan.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" t:styleID=\"sCol40\" /><t:Column t:key=\"fundPlan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" t:styleID=\"sCol41\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" t:styleID=\"sCol42\" /><t:Column t:key=\"auditData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" t:styleID=\"sCol43\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"returnState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" /><t:Column t:key=\"returnAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" t:styleID=\"sCol46\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" /><t:Column t:key=\"entryexpenseType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" /><t:Column t:key=\"currencyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" t:styleID=\"sCol50\" /><t:Column t:key=\"entries.purpose\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" /><t:Column t:key=\"entries.participants\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" /><t:Column t:key=\"entries.amountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" t:styleID=\"sCol53\" /><t:Column t:key=\"entries.amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" t:styleID=\"sCol54\" /><t:Column t:key=\"entries.amountApprovedOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" t:styleID=\"sCol55\" /><t:Column t:key=\"entries.amountApproved\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol56\" /><t:Column t:key=\"entries.comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{cause}</t:Cell><t:Cell>$Resource{operationType}</t:Cell><t:Cell>$Resource{currencyType1}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{approvedAccount}</t:Cell><t:Cell>$Resource{amountPaid}</t:Cell><t:Cell>$Resource{amountNotPaid}</t:Cell><t:Cell>$Resource{amountUsed}</t:Cell><t:Cell>$Resource{amountSendedBack}</t:Cell><t:Cell>$Resource{amountBalance}</t:Cell><t:Cell>$Resource{payMode}</t:Cell><t:Cell>$Resource{amountControlType}</t:Cell><t:Cell>$Resource{currencyType.id}</t:Cell><t:Cell>$Resource{applier.id}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{isOverBudget}</t:Cell><t:Cell>$Resource{isVouchered}</t:Cell><t:Cell>$Resource{applier.name}</t:Cell><t:Cell>$Resource{company.id}</t:Cell><t:Cell>$Resource{company.name}</t:Cell><t:Cell>$Resource{applierCompanyId}</t:Cell><t:Cell>$Resource{orgUnit.id}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{bizReqDate}</t:Cell><t:Cell>$Resource{billTypeCode}</t:Cell><t:Cell>$Resource{biller.id}</t:Cell><t:Cell>$Resource{biller.name}</t:Cell><t:Cell>$Resource{prior}</t:Cell><t:Cell>$Resource{billDate}</t:Cell><t:Cell>$Resource{position.id}</t:Cell><t:Cell>$Resource{position.name}</t:Cell><t:Cell>$Resource{expenseType.id}</t:Cell><t:Cell>$Resource{expenseType.name}</t:Cell><t:Cell>$Resource{supportedObj.id}</t:Cell><t:Cell>$Resource{supportedObj.name}</t:Cell><t:Cell>$Resource{costedDept.id}</t:Cell><t:Cell>$Resource{costedDept.name}</t:Cell><t:Cell>$Resource{fundPlan.id}</t:Cell><t:Cell>$Resource{fundPlan.name}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{auditData}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{returnState}</t:Cell><t:Cell>$Resource{returnAmt}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{entryexpenseType.name}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{entries.purpose}</t:Cell><t:Cell>$Resource{entries.participants}</t:Cell><t:Cell>$Resource{entries.amountOri}</t:Cell><t:Cell>$Resource{entries.amount}</t:Cell><t:Cell>$Resource{entries.amountApprovedOri}</t:Cell><t:Cell>$Resource{entries.amountApproved}</t:Cell><t:Cell>$Resource{entries.comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","name","cause","operationType.name","currencyType.name","amount","amountApproved","amountPaid","amountNotPaid","amountUsed","returnAmount","amountBalance","payMode.name","amountControlType","currencyType.id","applier.id","state","isOverBudget","isVouchered","applier.name","company.id","company.name","applierCompany.id","orgUnit.id","orgUnit.name","bizReqDate","billTypeCode","biller.id","biller.name","prior","billDate","position.id","position.name","expenseType.id","expenseType.name","supportedObj.id","supportedObj.name","costedDept.id","costedDept.name","fundPlan.id","fundPlan.name","CU.id","auditDate","auditor.name","returnState","returnAmt","description","expenseType.name","currencyType1.name","entries.exchangeRate","entries.purpose","entries.participants","entries.amountOri","entries.amount","entries.amountApprovedOri","entries.amountApproved","entries.comment"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);		
        this.btnLocate.setEnabled(false);		
        this.btnLocate.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemImportData.setVisible(true);		
        this.menuItemExportData.setVisible(true);		
        this.separatorFW2.setVisible(true);		
        this.btnCreateTo.setEnabled(true);		
        this.btnCreateTo.setVisible(true);		
        this.btnCopyTo.setVisible(false);		
        this.btnCopyTo.setEnabled(false);		
        this.btnTraceUp.setEnabled(true);		
        this.btnTraceUp.setVisible(true);		
        this.btnTraceDown.setEnabled(true);		
        this.btnTraceDown.setVisible(true);		
        this.btnVoucher.setVisible(true);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.kDSeparator6.setEnabled(true);		
        this.kDSeparator6.setVisible(true);		
        this.btnPrintImage.setVisible(false);
        // btnViewRcrdsOfLendAndRepay
        this.btnViewRcrdsOfLendAndRepay.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRcrdsOfLendAndRepay.setText(resHelper.getString("btnViewRcrdsOfLendAndRepay.text"));		
        this.btnViewRcrdsOfLendAndRepay.setToolTipText(resHelper.getString("btnViewRcrdsOfLendAndRepay.toolTipText"));
        // btnPayOff
        this.btnPayOff.setAction((IItemAction)ActionProxyFactory.getProxy(actionPayOff, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPayOff.setText(resHelper.getString("btnPayOff.text"));		
        this.btnPayOff.setToolTipText(resHelper.getString("btnPayOff.toolTipText"));
        // btnReturn
        this.btnReturn.setAction((IItemAction)ActionProxyFactory.getProxy(actionReturn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReturn.setText(resHelper.getString("btnReturn.text"));
        // btnComfirmReturn
        this.btnComfirmReturn.setAction((IItemAction)ActionProxyFactory.getProxy(actionComfirmReturn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnComfirmReturn.setText(resHelper.getString("btnComfirmReturn.text"));
        // menuItemBatchSubmit
        this.menuItemBatchSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchSubmit.setText(resHelper.getString("menuItemBatchSubmit.text"));
        // menuItemViewRcds
        this.menuItemViewRcds.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewRcds.setText(resHelper.getString("menuItemViewRcds.text"));		
        this.menuItemViewRcds.setMnemonic(82);
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        tblMain.setBounds(new Rectangle(10, 10, 780, 570));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 780, 570, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(kDSeparator7);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuItemBatchSubmit);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemCopyAndAddNew);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemSwitchView);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemViewRcds);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(MenuItemSuspenseAcc);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAntiAudit);
        menuBiz.add(menuItemAbandon);
        menuBiz.add(menuItemCloseBill);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrintImage);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCopyAndAddNew);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnBatchsubmit);
        this.toolBar.add(btnAntiAudit);
        this.toolBar.add(btnViewRcrdsOfLendAndRepay);
        this.toolBar.add(btnAbandon);
        this.toolBar.add(btnPayOff);
        this.toolBar.add(btnCloseBill);
        this.toolBar.add(btnReturn);
        this.toolBar.add(btnComfirmReturn);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.LoanReqListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
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

	public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			return sic;
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("applier.id"));
        sic.add(new SelectorItemInfo("applier.name"));
        sic.add(new SelectorItemInfo("company.id"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("bizReqDate"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("currencyType.id"));
        sic.add(new SelectorItemInfo("billTypeCode"));
        sic.add(new SelectorItemInfo("cause"));
        sic.add(new SelectorItemInfo("biller.id"));
        sic.add(new SelectorItemInfo("biller.name"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("prior"));
        sic.add(new SelectorItemInfo("billDate"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("position.id"));
        sic.add(new SelectorItemInfo("position.name"));
        sic.add(new SelectorItemInfo("expenseType.id"));
        sic.add(new SelectorItemInfo("expenseType.name"));
        sic.add(new SelectorItemInfo("supportedObj.id"));
        sic.add(new SelectorItemInfo("supportedObj.name"));
        sic.add(new SelectorItemInfo("costedDept.id"));
        sic.add(new SelectorItemInfo("costedDept.name"));
        sic.add(new SelectorItemInfo("fundPlan.id"));
        sic.add(new SelectorItemInfo("fundPlan.name"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("amountApproved"));
        sic.add(new SelectorItemInfo("amountUsed"));
        sic.add(new SelectorItemInfo("amountBalance"));
        sic.add(new SelectorItemInfo("operationType.name"));
        sic.add(new SelectorItemInfo("payMode.name"));
        sic.add(new SelectorItemInfo("amountControlType"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("applierCompany.id"));
        sic.add(new SelectorItemInfo("entries.purpose"));
        sic.add(new SelectorItemInfo("entries.participants"));
        sic.add(new SelectorItemInfo("entries.amount"));
        sic.add(new SelectorItemInfo("entries.amountApproved"));
        sic.add(new SelectorItemInfo("entries.comment"));
        sic.add(new SelectorItemInfo("returnAmount"));
        sic.add(new SelectorItemInfo("amountNotPaid"));
        sic.add(new SelectorItemInfo("amountPaid"));
        sic.add(new SelectorItemInfo("currencyType1.name"));
        sic.add(new SelectorItemInfo("currencyType.name"));
        sic.add(new SelectorItemInfo("entries.amountOri"));
        sic.add(new SelectorItemInfo("entries.amountApprovedOri"));
        sic.add(new SelectorItemInfo("entries.exchangeRate"));
        sic.add(new SelectorItemInfo("isOverBudget"));
        sic.add(new SelectorItemInfo("isVouchered"));
        sic.add(new SelectorItemInfo("returnState"));
        sic.add(new SelectorItemInfo("returnAmt"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        sorterFieldList.add("number"); 
        sorterFieldList.add("lastUpdateTime"); 
        return sorterFieldList; 
    } 
    protected java.util.List getQueryPKFields() 
    { 
        java.util.List pkList = new ArrayList(); 
        pkList.add("id"); 
        return pkList;
    }
    	

    /**
     * output actionCreateTo_actionPerformed method
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }
    	

    /**
     * output actionAntiAudit_actionPerformed method
     */
    public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAntiAudit_actionPerformed(e);
    }
    	

    /**
     * output actionAbandon_actionPerformed method
     */
    public void actionAbandon_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbandon_actionPerformed(e);
    }
    	

    /**
     * output actionViewRrcdsOfLendAndRepay_actionPerformed method
     */
    public void actionViewRrcdsOfLendAndRepay_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPayOff_actionPerformed method
     */
    public void actionPayOff_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReturn_actionPerformed method
     */
    public void actionReturn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionComfirmReturn_actionPerformed method
     */
    public void actionComfirmReturn_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCreateTo(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCreateTo(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateTo() {
    	return false;
    }
	public RequestContext prepareActionAntiAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAntiAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiAudit() {
    	return false;
    }
	public RequestContext prepareActionAbandon(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAbandon(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAbandon() {
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
	public RequestContext prepareActionPayOff(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPayOff() {
    	return false;
    }
	public RequestContext prepareActionReturn(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReturn() {
    	return false;
    }
	public RequestContext prepareActionComfirmReturn(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionComfirmReturn() {
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
            innerActionPerformed("eas", AbstractLoanReqListUI.this, "ActionViewRrcdsOfLendAndRepay", "actionViewRrcdsOfLendAndRepay_actionPerformed", e);
        }
    }

    /**
     * output ActionPayOff class
     */     
    protected class ActionPayOff extends ItemAction {     
    
        public ActionPayOff()
        {
            this(null);
        }

        public ActionPayOff(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPayOff.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayOff.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayOff.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractLoanReqListUI.this, "ActionPayOff", "actionPayOff_actionPerformed", e);
        }
    }

    /**
     * output ActionReturn class
     */     
    protected class ActionReturn extends ItemAction {     
    
        public ActionReturn()
        {
            this(null);
        }

        public ActionReturn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReturn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReturn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReturn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractLoanReqListUI.this, "ActionReturn", "actionReturn_actionPerformed", e);
        }
    }

    /**
     * output ActionComfirmReturn class
     */     
    protected class ActionComfirmReturn extends ItemAction {     
    
        public ActionComfirmReturn()
        {
            this(null);
        }

        public ActionComfirmReturn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionComfirmReturn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComfirmReturn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionComfirmReturn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractLoanReqListUI.this, "ActionComfirmReturn", "actionComfirmReturn_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "LoanReqListUI");
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
        return com.kingdee.eas.cp.bc.client.DailyLoanBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.DailyLoanBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.DailyLoanBillInfo objectValue = new com.kingdee.eas.cp.bc.DailyLoanBillInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"id","number","name","cause","operationType","currencyType1","amount","approvedAccount","amountPaid","amountNotPaid","amountUsed","amountSendedBack","amountBalance","payMode","amountControlType","currencyType.id","applier.id","state","isOverBudget","isVouchered","applier.name","company.id","company.name","applierCompanyId","orgUnit.id","orgUnit.name","bizReqDate","billTypeCode","prior","billDate","expenseType.name","costedDept.name","CU.id","auditData","auditor","returnState","returnAmt","description","entryexpenseType.name"};
    }



	protected String getTDFileName() {
    	return "/bim/cp/bc/DailyLoanBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.DailyLoanBillQuery");
	}        
				protected boolean isFootVisible() {
			return true;
		}


}