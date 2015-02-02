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
public abstract class AbstractTravelExpenseAccountListUI extends com.kingdee.eas.cp.bc.client.AccountBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTravelExpenseAccountListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRcrdsOfLendAndRepay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewRcds;
    protected ActionViewRrcdsOfLendAndRepay actionViewRrcdsOfLendAndRepay = null;
    /**
     * output class constructor
     */
    public AbstractTravelExpenseAccountListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTravelExpenseAccountListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.cp.bc.app", "TravelAccountQuery");
        //actionCopyTo
        String _tempStr = null;
        actionCopyTo.setEnabled(true);
        actionCopyTo.setDaemonRun(false);

        actionCopyTo.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt C"));
        _tempStr = resHelper.getString("ActionCopyTo.SHORT_DESCRIPTION");
        actionCopyTo.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopyTo.LONG_DESCRIPTION");
        actionCopyTo.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopyTo.NAME");
        actionCopyTo.putValue(ItemAction.NAME, _tempStr);
        this.actionCopyTo.setBindWorkFlow(true);
         this.actionCopyTo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCopyTo.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCopyTo.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionVoucher
        actionVoucher.setEnabled(true);
        actionVoucher.setDaemonRun(false);

        actionVoucher.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt V"));
        _tempStr = resHelper.getString("ActionVoucher.SHORT_DESCRIPTION");
        actionVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.LONG_DESCRIPTION");
        actionVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.NAME");
        actionVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionVoucher.setBindWorkFlow(true);
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAntiAudit
        actionAntiAudit.setEnabled(false);
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
        this.btnViewRcrdsOfLendAndRepay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatchSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewRcds = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewRcrdsOfLendAndRepay.setName("btnViewRcrdsOfLendAndRepay");
        this.menuItemBatchSubmit.setName("menuItemBatchSubmit");
        this.menuItemViewRcds.setName("menuItemViewRcds");
        // CoreUI		
        this.setPreferredSize(new Dimension(800,600));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat></c:Style><c:Style id=\"sCol40\"><c:NumberFormat>%r-[=]{#,##0.0000}.4f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol41\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol42\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol46\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol47\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol48\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol49\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol50\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol51\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol52\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol53\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol54\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol56\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol57\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol58\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol59\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol60\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol61\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"208\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"operationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"travelType.name\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"currencyType1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"amountApproved\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"amountEncashed\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"actionFlag\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"amountPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"amountNotPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"amountStriked\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"payMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"state\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"applier\" t:width=\"55\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"orgUnit\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"bizReqDate\" t:width=\"81\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol17\" /><t:Column t:key=\"billTypeCode\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol18\" /><t:Column t:key=\"amountSendedBack\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol19\" /><t:Column t:key=\"budgetAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol20\" /><t:Column t:key=\"company\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol21\" /><t:Column t:key=\"applierCompanyId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol22\" /><t:Column t:key=\"sourceId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol23\" /><t:Column t:key=\"cause\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"auditData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol26\" /><t:Column t:key=\"amountEncashed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol27\" /><t:Column t:key=\"specialInvoice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"voucherNum.voucherNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"k3VoucherNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"isK3Paid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"payCompany\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"discription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"entries.project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"operationType1.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"expenseType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"costCenter.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"person.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"currencyType\" t:width=\"55\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" t:styleID=\"sCol40\" /><t:Column t:key=\"entries.startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" t:styleID=\"sCol41\" /><t:Column t:key=\"entries.endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" t:styleID=\"sCol42\" /><t:Column t:key=\"entries.from\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"entries.to\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"entries.vehicle\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" /><t:Column t:key=\"entries.taxiExpense\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" t:styleID=\"sCol46\" /><t:Column t:key=\"entries.bussesExpense\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol47\" /><t:Column t:key=\"entries.hotelExpense\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" t:styleID=\"sCol48\" /><t:Column t:key=\"entries.eventionSubsidy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" t:styleID=\"sCol49\" /><t:Column t:key=\"entries.otherExpense\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" t:styleID=\"sCol50\" /><t:Column t:key=\"entries.amountOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" t:styleID=\"sCol51\" /><t:Column t:key=\"entries.amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" t:styleID=\"sCol52\" /><t:Column t:key=\"entries.amountApprovedOri\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" t:styleID=\"sCol53\" /><t:Column t:key=\"entries.amountApproved\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" t:styleID=\"sCol54\" /><t:Column t:key=\"company1.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" /><t:Column t:key=\"entries.noTaxAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol56\" /><t:Column t:key=\"entries.taxRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" t:styleID=\"sCol57\" /><t:Column t:key=\"entries.tax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" t:styleID=\"sCol58\" /><t:Column t:key=\"entries.noTaxAmtLocal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" t:styleID=\"sCol59\" /><t:Column t:key=\"entries.taxLocal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" t:styleID=\"sCol60\" /><t:Column t:key=\"orgUnit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" t:styleID=\"sCol61\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{operationType}</t:Cell><t:Cell>$Resource{travelType.name}</t:Cell><t:Cell>$Resource{currencyType1}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{amountApproved}</t:Cell><t:Cell>$Resource{amountEncashed}</t:Cell><t:Cell>$Resource{actionFlag}</t:Cell><t:Cell>$Resource{amountPaid}</t:Cell><t:Cell>$Resource{amountNotPaid}</t:Cell><t:Cell>$Resource{amountStriked}</t:Cell><t:Cell>$Resource{payMode}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{applier}</t:Cell><t:Cell>$Resource{orgUnit}</t:Cell><t:Cell>$Resource{bizReqDate}</t:Cell><t:Cell>$Resource{billTypeCode}</t:Cell><t:Cell>$Resource{amountSendedBack}</t:Cell><t:Cell>$Resource{budgetAmount}</t:Cell><t:Cell>$Resource{company}</t:Cell><t:Cell>$Resource{applierCompanyId}</t:Cell><t:Cell>$Resource{sourceId}</t:Cell><t:Cell>$Resource{cause}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditData}</t:Cell><t:Cell>$Resource{amountEncashed}</t:Cell><t:Cell>$Resource{specialInvoice}</t:Cell><t:Cell>$Resource{voucherNum.voucherNumber}</t:Cell><t:Cell>$Resource{k3VoucherNumber}</t:Cell><t:Cell>$Resource{isK3Paid}</t:Cell><t:Cell>$Resource{payCompany}</t:Cell><t:Cell>$Resource{discription}</t:Cell><t:Cell>$Resource{entries.project}</t:Cell><t:Cell>$Resource{operationType1.name}</t:Cell><t:Cell>$Resource{expenseType.name}</t:Cell><t:Cell>$Resource{costCenter.name}</t:Cell><t:Cell>$Resource{person.name}</t:Cell><t:Cell>$Resource{currencyType}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{entries.startDate}</t:Cell><t:Cell>$Resource{entries.endDate}</t:Cell><t:Cell>$Resource{entries.from}</t:Cell><t:Cell>$Resource{entries.to}</t:Cell><t:Cell>$Resource{entries.vehicle}</t:Cell><t:Cell>$Resource{entries.taxiExpense}</t:Cell><t:Cell>$Resource{entries.bussesExpense}</t:Cell><t:Cell>$Resource{entries.hotelExpense}</t:Cell><t:Cell>$Resource{entries.eventionSubsidy}</t:Cell><t:Cell>$Resource{entries.otherExpense}</t:Cell><t:Cell>$Resource{entries.amountOri}</t:Cell><t:Cell>$Resource{entries.amount}</t:Cell><t:Cell>$Resource{entries.amountApprovedOri}</t:Cell><t:Cell>$Resource{entries.amountApproved}</t:Cell><t:Cell>$Resource{company1.name}</t:Cell><t:Cell>$Resource{entries.noTaxAmt}</t:Cell><t:Cell>$Resource{entries.taxRate}</t:Cell><t:Cell>$Resource{entries.tax}</t:Cell><t:Cell>$Resource{entries.noTaxAmtLocal}</t:Cell><t:Cell>$Resource{entries.taxLocal}</t:Cell><t:Cell>$Resource{orgUnit.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","name","operationType.name","travelType.name","currencyType.name","amount","amountApproved","amountEncashed","actionFlag","amountPaid","amountNotPaid","amountStriked","payMode.name","state","applier.name","orgUnit.name","bizReqDate","billTypeCode","amountSendedBack","budgetAmount","","applierCompany.id","sourceBillId","cause","auditor.name","auditDate","","specialInvoice","voucherNum.voucherNumber","k3VoucherNumber","isK3Paid","payCompany.name","description","project.name","operationType1.name","expenseType.name","costCenter.name","person.name","currencyType1.name","entries.exchangeRate","entries.startDate","entries.endDate","entries.from","entries.to","entries.vehicle","entries.taxiExpense","entries.bussesExpense","entries.hotelExpense","entries.eventionSubsidy","entries.otherExpense","entries.amountOri","entries.amount","entries.amountApprovedOri","entries.amountApproved","company1.name","entries.noTaxAmt","entries.taxRate","entries.tax","entries.noTaxAmtLocal","entries.taxLocal","orgUnit.id"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);		
        this.btnPrint.setEnabled(true);		
        this.btnPrint.setVisible(true);		
        this.btnPrintPreview.setEnabled(true);		
        this.btnPrintPreview.setVisible(true);		
        this.menuItemImportData.setVisible(true);		
        this.menuItemImportData.setEnabled(true);		
        this.menuItemExportData.setVisible(true);		
        this.menuItemExportData.setEnabled(true);		
        this.separatorFW2.setVisible(true);		
        this.separatorFW1.setVisible(false);		
        this.btnTraceUp.setEnabled(true);		
        this.btnTraceUp.setVisible(true);		
        this.btnTraceDown.setEnabled(true);		
        this.btnTraceDown.setVisible(true);		
        this.btnVoucher.setEnabled(true);		
        this.btnVoucher.setVisible(true);
        // btnViewRcrdsOfLendAndRepay
        this.btnViewRcrdsOfLendAndRepay.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRcrdsOfLendAndRepay.setText(resHelper.getString("btnViewRcrdsOfLendAndRepay.text"));		
        this.btnViewRcrdsOfLendAndRepay.setToolTipText(resHelper.getString("btnViewRcrdsOfLendAndRepay.toolTipText"));
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
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrintImage);
        this.toolBar.add(btnViewImage);
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
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnBatchsubmit);
        this.toolBar.add(btnAntiAudit);
        this.toolBar.add(btnViewRcrdsOfLendAndRepay);
        this.toolBar.add(btnAbandon);
        this.toolBar.add(btnCloseBill);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.TravelExpenseAccountListUIHandler";
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("bizReqDate"));
        sic.add(new SelectorItemInfo("billTypeCode"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("applier.name"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("sourceBillId"));
        sic.add(new SelectorItemInfo("amountSendedBack"));
        sic.add(new SelectorItemInfo("budgetAmount"));
        sic.add(new SelectorItemInfo("amountApproved"));
        sic.add(new SelectorItemInfo("operationType.name"));
        sic.add(new SelectorItemInfo("payMode.name"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("cause"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("applierCompany.id"));
        sic.add(new SelectorItemInfo("amountEncashed"));
        sic.add(new SelectorItemInfo("operationType1.name"));
        sic.add(new SelectorItemInfo("expenseType.name"));
        sic.add(new SelectorItemInfo("entries.startDate"));
        sic.add(new SelectorItemInfo("entries.endDate"));
        sic.add(new SelectorItemInfo("entries.from"));
        sic.add(new SelectorItemInfo("entries.to"));
        sic.add(new SelectorItemInfo("entries.vehicle"));
        sic.add(new SelectorItemInfo("entries.taxiExpense"));
        sic.add(new SelectorItemInfo("entries.bussesExpense"));
        sic.add(new SelectorItemInfo("entries.hotelExpense"));
        sic.add(new SelectorItemInfo("entries.eventionSubsidy"));
        sic.add(new SelectorItemInfo("entries.otherExpense"));
        sic.add(new SelectorItemInfo("entries.amount"));
        sic.add(new SelectorItemInfo("entries.amountApproved"));
        sic.add(new SelectorItemInfo("costCenter.name"));
        sic.add(new SelectorItemInfo("company1.name"));
        sic.add(new SelectorItemInfo("amountNotPaid"));
        sic.add(new SelectorItemInfo("amountStriked"));
        sic.add(new SelectorItemInfo("amountPaid"));
        sic.add(new SelectorItemInfo("travelType.name"));
        sic.add(new SelectorItemInfo("currencyType1.name"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("entries.amountOri"));
        sic.add(new SelectorItemInfo("entries.amountApprovedOri"));
        sic.add(new SelectorItemInfo("entries.exchangeRate"));
        sic.add(new SelectorItemInfo("currencyType.name"));
        sic.add(new SelectorItemInfo("actionFlag"));
        sic.add(new SelectorItemInfo("isRefPaperPark"));
        sic.add(new SelectorItemInfo("specialInvoice"));
        sic.add(new SelectorItemInfo("payCompany.name"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("entries.noTaxAmt"));
        sic.add(new SelectorItemInfo("entries.taxRate"));
        sic.add(new SelectorItemInfo("entries.tax"));
        sic.add(new SelectorItemInfo("entries.noTaxAmtLocal"));
        sic.add(new SelectorItemInfo("entries.taxLocal"));
        sic.add(new SelectorItemInfo("voucherNum.voucherNumber"));
        sic.add(new SelectorItemInfo("k3VoucherNumber"));
        sic.add(new SelectorItemInfo("isK3Paid"));
        sic.add(new SelectorItemInfo("person.name"));
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
     * output actionCopyTo_actionPerformed method
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }
    	

    /**
     * output actionVoucher_actionPerformed method
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
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
	public RequestContext prepareActionCopyTo(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCopyTo(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCopyTo() {
    	return false;
    }
	public RequestContext prepareActionVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVoucher() {
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
            innerActionPerformed("eas", AbstractTravelExpenseAccountListUI.this, "ActionViewRrcdsOfLendAndRepay", "actionViewRrcdsOfLendAndRepay_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "TravelExpenseAccountListUI");
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
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"id","number","name","operationType","travelType.name","currencyType1","amount","amountApproved","amountEncashed","actionFlag","amountPaid","amountNotPaid","amountStriked","payMode","state","applier","orgUnit","bizReqDate","billTypeCode","amountSendedBack","budgetAmount","applierCompanyId","sourceId","cause","auditor","auditData","amountEncashed","specialInvoice","voucherNum.voucherNumber","k3VoucherNumber","isK3Paid","payCompany","discription","orgUnit.id"};
    }



	protected String getTDFileName() {
    	return "/bim/cp/bc/TravelAccountBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.TravelAccountQuery");
	}

}