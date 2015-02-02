/**
 * output package name
 */
package com.kingdee.eas.fi.gl.client;

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
public abstract class AbstractZDFReportSubsidiaryLedgerConditionUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractZDFReportSubsidiaryLedgerConditionUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpIncludeNotPosting;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpAmountZero;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpOnlyLeaf;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpBalanceAndAmountZero;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpNotUsed;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpShowBusinessDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpOtherAccount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpOtherAccountItem;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpShowQuantity;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbnQueryByPeriod;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbnQueryByData;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblPeriodNumberBegin;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblPeriodNumberEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblDateBegin;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblDateEnd;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpkDateBegin;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpkDateEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcPeriodTO;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcAccountLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcPeriodFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcPeriodYearBegin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcPeriodYearEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcAccountCodeTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblcAccountLevelTo;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpOnlyAsst;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpDailyTotal;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox jcbNoDisplayZeroTotal;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkNotIncluePLVoucher;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkOpAccountCusAttribute;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIncludeHis;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spnPeriodYearEnd;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spnAccountLevelBegin;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbCurrency;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spnPeriodYearBegin;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prbAccountBegin;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spnPeriodNumberBegin;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spnPeriodNumberEnd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prbAccountEnd;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spnAccountLevelEnd;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostOrg;
    /**
     * output class constructor
     */
    public AbstractZDFReportSubsidiaryLedgerConditionUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractZDFReportSubsidiaryLedgerConditionUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.chkOpIncludeNotPosting = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpAmountZero = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpOnlyLeaf = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpBalanceAndAmountZero = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpNotUsed = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpShowBusinessDate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpOtherAccount = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpOtherAccountItem = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpShowQuantity = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.rbnQueryByPeriod = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbnQueryByData = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.lblPeriodNumberBegin = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblPeriodNumberEnd = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblDateBegin = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblDateEnd = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.dpkDateBegin = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpkDateEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.lblcPeriodTO = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcAccountLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcPeriodFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcPeriodYearBegin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcPeriodYearEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcAccountCodeTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblcAccountLevelTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkOpOnlyAsst = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpDailyTotal = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.jcbNoDisplayZeroTotal = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkNotIncluePLVoucher = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkOpAccountCusAttribute = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIncludeHis = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spnPeriodYearEnd = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spnAccountLevelBegin = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.cmbCurrency = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.spnPeriodYearBegin = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prbAccountBegin = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spnPeriodNumberBegin = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spnPeriodNumberEnd = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prbAccountEnd = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spnAccountLevelEnd = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prmtCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkOpIncludeNotPosting.setName("chkOpIncludeNotPosting");
        this.chkOpAmountZero.setName("chkOpAmountZero");
        this.chkOpOnlyLeaf.setName("chkOpOnlyLeaf");
        this.chkOpBalanceAndAmountZero.setName("chkOpBalanceAndAmountZero");
        this.chkOpNotUsed.setName("chkOpNotUsed");
        this.chkOpShowBusinessDate.setName("chkOpShowBusinessDate");
        this.chkOpOtherAccount.setName("chkOpOtherAccount");
        this.chkOpOtherAccountItem.setName("chkOpOtherAccountItem");
        this.chkOpShowQuantity.setName("chkOpShowQuantity");
        this.rbnQueryByPeriod.setName("rbnQueryByPeriod");
        this.rbnQueryByData.setName("rbnQueryByData");
        this.lblPeriodNumberBegin.setName("lblPeriodNumberBegin");
        this.lblPeriodNumberEnd.setName("lblPeriodNumberEnd");
        this.lblDateBegin.setName("lblDateBegin");
        this.lblDateEnd.setName("lblDateEnd");
        this.dpkDateBegin.setName("dpkDateBegin");
        this.dpkDateEnd.setName("dpkDateEnd");
        this.lblcPeriodTO.setName("lblcPeriodTO");
        this.lblcAccountLevel.setName("lblcAccountLevel");
        this.lblcCurrency.setName("lblcCurrency");
        this.lblcPeriodFrom.setName("lblcPeriodFrom");
        this.lblcAccount.setName("lblcAccount");
        this.lblcPeriodYearBegin.setName("lblcPeriodYearBegin");
        this.lblcPeriodYearEnd.setName("lblcPeriodYearEnd");
        this.lblcAccountCodeTo.setName("lblcAccountCodeTo");
        this.lblcAccountLevelTo.setName("lblcAccountLevelTo");
        this.chkOpOnlyAsst.setName("chkOpOnlyAsst");
        this.chkOpDailyTotal.setName("chkOpDailyTotal");
        this.jcbNoDisplayZeroTotal.setName("jcbNoDisplayZeroTotal");
        this.chkNotIncluePLVoucher.setName("chkNotIncluePLVoucher");
        this.chkOpAccountCusAttribute.setName("chkOpAccountCusAttribute");
        this.chkIncludeHis.setName("chkIncludeHis");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.spnPeriodYearEnd.setName("spnPeriodYearEnd");
        this.spnAccountLevelBegin.setName("spnAccountLevelBegin");
        this.cmbCurrency.setName("cmbCurrency");
        this.spnPeriodYearBegin.setName("spnPeriodYearBegin");
        this.prbAccountBegin.setName("prbAccountBegin");
        this.spnPeriodNumberBegin.setName("spnPeriodNumberBegin");
        this.spnPeriodNumberEnd.setName("spnPeriodNumberEnd");
        this.prbAccountEnd.setName("prbAccountEnd");
        this.spnAccountLevelEnd.setName("spnAccountLevelEnd");
        this.prmtCompany.setName("prmtCompany");
        this.prmtCostOrg.setName("prmtCostOrg");
        // CustomerQueryPanel
        // chkOpIncludeNotPosting		
        this.chkOpIncludeNotPosting.setText(resHelper.getString("chkOpIncludeNotPosting.text"));		
        this.chkOpIncludeNotPosting.setVisible(false);
        // chkOpAmountZero		
        this.chkOpAmountZero.setText(resHelper.getString("chkOpAmountZero.text"));		
        this.chkOpAmountZero.setVisible(false);
        this.chkOpAmountZero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkOpAmountZero_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkOpOnlyLeaf		
        this.chkOpOnlyLeaf.setText(resHelper.getString("chkOpOnlyLeaf.text"));		
        this.chkOpOnlyLeaf.setVisible(false);
        this.chkOpOnlyLeaf.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkOpOnlyLeaf_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkOpBalanceAndAmountZero		
        this.chkOpBalanceAndAmountZero.setText(resHelper.getString("chkOpBalanceAndAmountZero.text"));		
        this.chkOpBalanceAndAmountZero.setVisible(false);
        this.chkOpBalanceAndAmountZero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkOpBalanceAndAmountZero_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkOpNotUsed		
        this.chkOpNotUsed.setText(resHelper.getString("chkOpNotUsed.text"));		
        this.chkOpNotUsed.setVisible(false);
        this.chkOpNotUsed.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkOpNotUsed_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkOpShowBusinessDate		
        this.chkOpShowBusinessDate.setText(resHelper.getString("chkOpShowBusinessDate.text"));		
        this.chkOpShowBusinessDate.setVisible(false);
        // chkOpOtherAccount		
        this.chkOpOtherAccount.setText(resHelper.getString("chkOpOtherAccount.text"));		
        this.chkOpOtherAccount.setVisible(false);
        this.chkOpOtherAccount.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkOpOtherAccount_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkOpOtherAccountItem		
        this.chkOpOtherAccountItem.setText(resHelper.getString("chkOpOtherAccountItem.text"));		
        this.chkOpOtherAccountItem.setVisible(false);
        // chkOpShowQuantity		
        this.chkOpShowQuantity.setText(resHelper.getString("chkOpShowQuantity.text"));		
        this.chkOpShowQuantity.setVisible(false);
        // rbnQueryByPeriod		
        this.rbnQueryByPeriod.setText(resHelper.getString("rbnQueryByPeriod.text"));		
        this.rbnQueryByPeriod.setVisible(false);		
        this.rbnQueryByPeriod.setSelected(true);
        this.rbnQueryByPeriod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    rbnQueryByPeriod_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // rbnQueryByData		
        this.rbnQueryByData.setText(resHelper.getString("rbnQueryByData.text"));		
        this.rbnQueryByData.setVisible(false);
        // lblPeriodNumberBegin		
        this.lblPeriodNumberBegin.setText(resHelper.getString("lblPeriodNumberBegin.text"));
        // lblPeriodNumberEnd		
        this.lblPeriodNumberEnd.setText(resHelper.getString("lblPeriodNumberEnd.text"));
        // lblDateBegin		
        this.lblDateBegin.setText(resHelper.getString("lblDateBegin.text"));		
        this.lblDateBegin.setVisible(false);
        // lblDateEnd		
        this.lblDateEnd.setText(resHelper.getString("lblDateEnd.text"));		
        this.lblDateEnd.setVisible(false);
        // dpkDateBegin		
        this.dpkDateBegin.setVisible(false);
        this.dpkDateBegin.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    dpkDateBegin_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // dpkDateEnd		
        this.dpkDateEnd.setVisible(false);
        this.dpkDateEnd.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    dpkDateEnd_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lblcPeriodTO		
        this.lblcPeriodTO.setBoundLabelText(resHelper.getString("lblcPeriodTO.boundLabelText"));		
        this.lblcPeriodTO.setBoundLabelLength(100);		
        this.lblcPeriodTO.setBoundLabelUnderline(true);
        // lblcAccountLevel		
        this.lblcAccountLevel.setBoundLabelText(resHelper.getString("lblcAccountLevel.boundLabelText"));		
        this.lblcAccountLevel.setBoundLabelLength(100);		
        this.lblcAccountLevel.setBoundLabelUnderline(true);
        // lblcCurrency		
        this.lblcCurrency.setBoundLabelText(resHelper.getString("lblcCurrency.boundLabelText"));		
        this.lblcCurrency.setBoundLabelLength(100);		
        this.lblcCurrency.setBoundLabelUnderline(true);
        // lblcPeriodFrom		
        this.lblcPeriodFrom.setBoundLabelText(resHelper.getString("lblcPeriodFrom.boundLabelText"));		
        this.lblcPeriodFrom.setBoundLabelLength(100);		
        this.lblcPeriodFrom.setBoundLabelUnderline(true);
        // lblcAccount		
        this.lblcAccount.setBoundLabelText(resHelper.getString("lblcAccount.boundLabelText"));		
        this.lblcAccount.setBoundLabelLength(100);		
        this.lblcAccount.setBoundLabelUnderline(true);
        // lblcPeriodYearBegin		
        this.lblcPeriodYearBegin.setBoundLabelText(resHelper.getString("lblcPeriodYearBegin.boundLabelText"));		
        this.lblcPeriodYearBegin.setBoundLabelLength(16);
        // lblcPeriodYearEnd		
        this.lblcPeriodYearEnd.setBoundLabelText(resHelper.getString("lblcPeriodYearEnd.boundLabelText"));		
        this.lblcPeriodYearEnd.setBoundLabelLength(16);
        // lblcAccountCodeTo		
        this.lblcAccountCodeTo.setBoundLabelText(resHelper.getString("lblcAccountCodeTo.boundLabelText"));		
        this.lblcAccountCodeTo.setBoundLabelLength(100);		
        this.lblcAccountCodeTo.setBoundLabelUnderline(true);
        // lblcAccountLevelTo		
        this.lblcAccountLevelTo.setBoundLabelText(resHelper.getString("lblcAccountLevelTo.boundLabelText"));		
        this.lblcAccountLevelTo.setBoundLabelLength(16);
        // chkOpOnlyAsst		
        this.chkOpOnlyAsst.setText(resHelper.getString("chkOpOnlyAsst.text"));
        this.chkOpOnlyAsst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkOpOnlyAsst_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkOpDailyTotal		
        this.chkOpDailyTotal.setText(resHelper.getString("chkOpDailyTotal.text"));		
        this.chkOpDailyTotal.setVisible(false);
        // jcbNoDisplayZeroTotal		
        this.jcbNoDisplayZeroTotal.setText(resHelper.getString("jcbNoDisplayZeroTotal.text"));		
        this.jcbNoDisplayZeroTotal.setVisible(false);
        // chkNotIncluePLVoucher		
        this.chkNotIncluePLVoucher.setText(resHelper.getString("chkNotIncluePLVoucher.text"));		
        this.chkNotIncluePLVoucher.setVisible(false);
        // chkOpAccountCusAttribute		
        this.chkOpAccountCusAttribute.setText(resHelper.getString("chkOpAccountCusAttribute.text"));		
        this.chkOpAccountCusAttribute.setVisible(false);
        // chkIncludeHis		
        this.chkIncludeHis.setText(resHelper.getString("chkIncludeHis.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // spnPeriodYearEnd
        this.spnPeriodYearEnd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spnPeriodYearEnd_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spnAccountLevelBegin
        this.spnAccountLevelBegin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spnAccountLevelBegin_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cmbCurrency
        // spnPeriodYearBegin
        this.spnPeriodYearBegin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spnPeriodYearBegin_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prbAccountBegin		
        this.prbAccountBegin.setEditable(true);
        this.prbAccountBegin.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prbAccountBegin_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spnPeriodNumberBegin
        this.spnPeriodNumberBegin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spnPeriodNumberBegin_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spnPeriodNumberEnd
        this.spnPeriodNumberEnd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spnPeriodNumberEnd_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prbAccountEnd		
        this.prbAccountEnd.setEditable(true);
        this.prbAccountEnd.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prbAccountEnd_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spnAccountLevelEnd
        this.spnAccountLevelEnd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spnAccountLevelEnd_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCompany		
        this.prmtCompany.setEnabled(false);
        // prmtCostOrg		
        this.prmtCostOrg.setEditable(true);
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
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(null);
        chkOpIncludeNotPosting.setBounds(new Rectangle(200, 256, 123, 19));
        this.add(chkOpIncludeNotPosting, null);
        chkOpAmountZero.setBounds(new Rectangle(19, 232, 120, 19));
        this.add(chkOpAmountZero, null);
        chkOpOnlyLeaf.setBounds(new Rectangle(11, 275, 123, 19));
        this.add(chkOpOnlyLeaf, null);
        chkOpBalanceAndAmountZero.setBounds(new Rectangle(10, 237, 176, 19));
        this.add(chkOpBalanceAndAmountZero, null);
        chkOpNotUsed.setBounds(new Rectangle(10, 256, 125, 19));
        this.add(chkOpNotUsed, null);
        chkOpShowBusinessDate.setBounds(new Rectangle(205, 269, 116, 19));
        this.add(chkOpShowBusinessDate, null);
        chkOpOtherAccount.setBounds(new Rectangle(207, 251, 116, 19));
        this.add(chkOpOtherAccount, null);
        chkOpOtherAccountItem.setBounds(new Rectangle(204, 266, 140, 19));
        this.add(chkOpOtherAccountItem, null);
        chkOpShowQuantity.setBounds(new Rectangle(204, 246, 110, 19));
        this.add(chkOpShowQuantity, null);
        rbnQueryByPeriod.setBounds(new Rectangle(292, 379, 140, 19));
        this.add(rbnQueryByPeriod, null);
        rbnQueryByData.setBounds(new Rectangle(294, 407, 140, 19));
        this.add(rbnQueryByData, null);
        lblPeriodNumberBegin.setBounds(new Rectangle(284, 64, 17, 19));
        this.add(lblPeriodNumberBegin, null);
        lblPeriodNumberEnd.setBounds(new Rectangle(284, 87, 17, 19));
        this.add(lblPeriodNumberEnd, null);
        lblDateBegin.setBounds(new Rectangle(33, 378, 100, 19));
        this.add(lblDateBegin, null);
        lblDateEnd.setBounds(new Rectangle(32, 402, 100, 19));
        this.add(lblDateEnd, null);
        dpkDateBegin.setBounds(new Rectangle(109, 379, 170, 19));
        this.add(dpkDateBegin, null);
        dpkDateEnd.setBounds(new Rectangle(110, 404, 170, 19));
        this.add(dpkDateEnd, null);
        lblcPeriodTO.setBounds(new Rectangle(10, 87, 175, 19));
        this.add(lblcPeriodTO, null);
        lblcAccountLevel.setBounds(new Rectangle(10, 133, 175, 19));
        this.add(lblcAccountLevel, null);
        lblcCurrency.setBounds(new Rectangle(10, 157, 270, 19));
        this.add(lblcCurrency, null);
        lblcPeriodFrom.setBounds(new Rectangle(10, 64, 175, 19));
        this.add(lblcPeriodFrom, null);
        lblcAccount.setBounds(new Rectangle(10, 110, 270, 19));
        this.add(lblcAccount, null);
        lblcPeriodYearBegin.setBounds(new Rectangle(189, 64, 91, 19));
        this.add(lblcPeriodYearBegin, null);
        lblcPeriodYearEnd.setBounds(new Rectangle(189, 87, 91, 19));
        this.add(lblcPeriodYearEnd, null);
        lblcAccountCodeTo.setBounds(new Rectangle(124, 220, 270, 19));
        this.add(lblcAccountCodeTo, null);
        lblcAccountLevelTo.setBounds(new Rectangle(189, 135, 91, 19));
        this.add(lblcAccountLevelTo, null);
        chkOpOnlyAsst.setBounds(new Rectangle(10, 185, 136, 19));
        this.add(chkOpOnlyAsst, null);
        chkOpDailyTotal.setBounds(new Rectangle(206, 242, 110, 19));
        this.add(chkOpDailyTotal, null);
        jcbNoDisplayZeroTotal.setBounds(new Rectangle(11, 249, 182, 19));
        this.add(jcbNoDisplayZeroTotal, null);
        chkNotIncluePLVoucher.setBounds(new Rectangle(10, 266, 174, 19));
        this.add(chkNotIncluePLVoucher, null);
        chkOpAccountCusAttribute.setBounds(new Rectangle(204, 250, 140, 19));
        this.add(chkOpAccountCusAttribute, null);
        chkIncludeHis.setBounds(new Rectangle(10, 205, 202, 19));
        this.add(chkIncludeHis, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 41, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 18, 270, 19));
        this.add(kDLabelContainer2, null);
        //lblcPeriodTO
        lblcPeriodTO.setBoundEditor(spnPeriodYearEnd);
        //lblcAccountLevel
        lblcAccountLevel.setBoundEditor(spnAccountLevelBegin);
        //lblcCurrency
        lblcCurrency.setBoundEditor(cmbCurrency);
        //lblcPeriodFrom
        lblcPeriodFrom.setBoundEditor(spnPeriodYearBegin);
        //lblcAccount
        lblcAccount.setBoundEditor(prbAccountBegin);
        //lblcPeriodYearBegin
        lblcPeriodYearBegin.setBoundEditor(spnPeriodNumberBegin);
        //lblcPeriodYearEnd
        lblcPeriodYearEnd.setBoundEditor(spnPeriodNumberEnd);
        //lblcAccountCodeTo
        lblcAccountCodeTo.setBoundEditor(prbAccountEnd);
        //lblcAccountLevelTo
        lblcAccountLevelTo.setBoundEditor(spnAccountLevelEnd);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtCompany);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtCostOrg);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fi.gl.app.ZDFReportSubsidiaryLedgerConditionUIHandler";
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

    /**
     * output chkOpAmountZero_itemStateChanged method
     */
    protected void chkOpAmountZero_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output chkOpOnlyLeaf_itemStateChanged method
     */
    protected void chkOpOnlyLeaf_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output chkOpBalanceAndAmountZero_itemStateChanged method
     */
    protected void chkOpBalanceAndAmountZero_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output chkOpNotUsed_itemStateChanged method
     */
    protected void chkOpNotUsed_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output chkOpOtherAccount_itemStateChanged method
     */
    protected void chkOpOtherAccount_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output rbnQueryByPeriod_itemStateChanged method
     */
    protected void rbnQueryByPeriod_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output dpkDateBegin_dataChanged method
     */
    protected void dpkDateBegin_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output dpkDateEnd_dataChanged method
     */
    protected void dpkDateEnd_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkOpOnlyAsst_actionPerformed method
     */
    protected void chkOpOnlyAsst_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output spnPeriodYearEnd_stateChanged method
     */
    protected void spnPeriodYearEnd_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spnAccountLevelBegin_stateChanged method
     */
    protected void spnAccountLevelBegin_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spnPeriodYearBegin_stateChanged method
     */
    protected void spnPeriodYearBegin_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prbAccountBegin_dataChanged method
     */
    protected void prbAccountBegin_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output spnPeriodNumberBegin_stateChanged method
     */
    protected void spnPeriodNumberBegin_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spnPeriodNumberEnd_stateChanged method
     */
    protected void spnPeriodNumberEnd_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prbAccountEnd_dataChanged method
     */
    protected void prbAccountEnd_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output spnAccountLevelEnd_stateChanged method
     */
    protected void spnAccountLevelEnd_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fi.gl.client", "ZDFReportSubsidiaryLedgerConditionUI");
    }




}