/**
 * output package name
 */
package com.kingdee.eas.ma.budget.client;

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
public abstract class AbstractBgItemElimEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBgItemElimEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerItemGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isForFp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFormulaEdit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActRecordInitEdit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActRecordMaintainEdit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox btnIsSumFormula;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea mltDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBoxUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBoxPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox mltName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBoxItemGroup;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox1;
    protected com.kingdee.bos.ctrl.swing.KDTextField specialNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox fpDirection;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFormula;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtActRecordInit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtActRecordMaintain;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtHelp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbAdd;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbMinus;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup dataRelationGroup1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comElimType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpdateSumFormula;
    protected com.kingdee.eas.ma.budget.BgItemInfo editData = null;
    protected ActionEditFormula actionEditFormula = null;
    protected ActionEditActRecordMaintain actionEditActRecordMaintain = null;
    protected ActionUpdateSumFormula actionUpdateSumFormula = null;
    protected ActionEditActRecordInit actionEditActRecordInit = null;
    /**
     * output class constructor
     */
    public AbstractBgItemElimEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBgItemElimEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionEditFormula
        this.actionEditFormula = new ActionEditFormula(this);
        getActionManager().registerAction("actionEditFormula", actionEditFormula);
         this.actionEditFormula.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditActRecordMaintain
        this.actionEditActRecordMaintain = new ActionEditActRecordMaintain(this);
        getActionManager().registerAction("actionEditActRecordMaintain", actionEditActRecordMaintain);
         this.actionEditActRecordMaintain.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdateSumFormula
        this.actionUpdateSumFormula = new ActionUpdateSumFormula(this);
        getActionManager().registerAction("actionUpdateSumFormula", actionUpdateSumFormula);
         this.actionUpdateSumFormula.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditActRecordInit
        this.actionEditActRecordInit = new ActionEditActRecordInit(this);
        getActionManager().registerAction("actionEditActRecordInit", actionEditActRecordInit);
         this.actionEditActRecordInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainerItemGroup = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSpecialNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isForFp = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnFormulaEdit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnActRecordInitEdit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnActRecordMaintainEdit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnIsSumFormula = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.lblDescription = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.mltDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDBizPromptBoxUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDBizPromptBoxPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDatePicker = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.mltName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDBizPromptBoxItemGroup = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDBizPromptBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.specialNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.fpDirection = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtFormula = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtActRecordInit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtActRecordMaintain = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtHelp = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.rbAdd = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbMinus = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.dataRelationGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.comElimType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnUpdateSumFormula = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.kDLabelContainerItemGroup.setName("kDLabelContainerItemGroup");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contSpecialNumber.setName("contSpecialNumber");
        this.isForFp.setName("isForFp");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.btnFormulaEdit.setName("btnFormulaEdit");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.btnActRecordInitEdit.setName("btnActRecordInitEdit");
        this.btnActRecordMaintainEdit.setName("btnActRecordMaintainEdit");
        this.btnIsSumFormula.setName("btnIsSumFormula");
        this.lblDescription.setName("lblDescription");
        this.mltDescription.setName("mltDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDBizPromptBoxUnit.setName("kDBizPromptBoxUnit");
        this.kDBizPromptBoxPerson.setName("kDBizPromptBoxPerson");
        this.kDDatePicker.setName("kDDatePicker");
        this.mltName.setName("mltName");
        this.txtNumber.setName("txtNumber");
        this.kDBizPromptBoxItemGroup.setName("kDBizPromptBoxItemGroup");
        this.kDBizPromptBox1.setName("kDBizPromptBox1");
        this.specialNumber.setName("specialNumber");
        this.fpDirection.setName("fpDirection");
        this.txtFormula.setName("txtFormula");
        this.txtActRecordInit.setName("txtActRecordInit");
        this.txtActRecordMaintain.setName("txtActRecordMaintain");
        this.txtHelp.setName("txtHelp");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.rbAdd.setName("rbAdd");
        this.rbMinus.setName("rbMinus");
        this.kDTextArea1.setName("kDTextArea1");
        this.comElimType.setName("comElimType");
        this.btnUpdateSumFormula.setName("btnUpdateSumFormula");
        // CoreUI		
        this.menuBiz.setVisible(false);		
        this.menuBiz.setEnabled(false);
        // kDTabbedPane1
        // kDPanel1
        // kDPanel2
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(80);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(80);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(80);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(80);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(80);		
        this.contNumber.setBoundLabelUnderline(true);
        // kDLabelContainerItemGroup		
        this.kDLabelContainerItemGroup.setBoundLabelText(resHelper.getString("kDLabelContainerItemGroup.boundLabelText"));		
        this.kDLabelContainerItemGroup.setBoundLabelUnderline(true);		
        this.kDLabelContainerItemGroup.setBoundLabelLength(80);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(80);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contSpecialNumber		
        this.contSpecialNumber.setBoundLabelText(resHelper.getString("contSpecialNumber.boundLabelText"));		
        this.contSpecialNumber.setBoundLabelLength(80);		
        this.contSpecialNumber.setBoundLabelUnderline(true);
        // isForFp		
        this.isForFp.setText(resHelper.getString("isForFp.text"));
        this.isForFp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chBoxAllowChange_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.isForFp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    isForFp_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelLength(0);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setEnabled(false);
        // btnFormulaEdit
        this.btnFormulaEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditFormula, new Class[] { IItemAction.class }, getServiceContext()));
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(130);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(130);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(130);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // btnActRecordInitEdit
        this.btnActRecordInitEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditActRecordInit, new Class[] { IItemAction.class }, getServiceContext()));
        // btnActRecordMaintainEdit
        this.btnActRecordMaintainEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditActRecordMaintain, new Class[] { IItemAction.class }, getServiceContext()));
        // btnIsSumFormula		
        this.btnIsSumFormula.setText(resHelper.getString("btnIsSumFormula.text"));
        this.btnIsSumFormula.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    btnIsSumFormula_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lblDescription		
        this.lblDescription.setText(resHelper.getString("lblDescription.text"));
        // mltDescription
        // kDScrollPane1
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));
        // kDBizPromptBoxUnit		
        this.kDBizPromptBoxUnit.setEnabled(false);
        // kDBizPromptBoxPerson		
        this.kDBizPromptBoxPerson.setEnabled(false);
        // kDDatePicker		
        this.kDDatePicker.setEnabled(false);
        // mltName		
        this.mltName.setRequired(true);		
        this.mltName.setMaxLength(100);
        // txtNumber		
        this.txtNumber.setMaxLength(100);		
        this.txtNumber.setRequired(true);
        this.txtNumber.addInputMethodListener(new com.kingdee.bos.ctrl.swing.event.InputMethodAdapter() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent e) {
                try {
                    txtNumber_inputMethodTextChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDBizPromptBoxItemGroup		
        this.kDBizPromptBoxItemGroup.setLabelLength(100);		
        this.kDBizPromptBoxItemGroup.setRequired(true);		
        this.kDBizPromptBoxItemGroup.setDefaultF7UIName("com.kingdee.eas.ma.budget.BgItemGroupDialog");		
        this.kDBizPromptBoxItemGroup.setEnabled(false);
        this.kDBizPromptBoxItemGroup.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kDBizPromptBoxItemGroup_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDBizPromptBox1		
        this.kDBizPromptBox1.setEnabled(false);
        this.kDBizPromptBox1.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kDBizPromptBox1_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // specialNumber		
        this.specialNumber.setMaxLength(1000);		
        this.specialNumber.setText(resHelper.getString("specialNumber.text"));
        // fpDirection		
        this.fpDirection.addItems(EnumUtils.getEnumList("com.kingdee.eas.ma.budget.FpCashDirectionEnum").toArray());		
        this.fpDirection.setRequired(true);		
        this.fpDirection.setEnabled(false);
        // txtFormula
        this.txtFormula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    txtFormula_mouseEntered(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtActRecordInit
        this.txtActRecordInit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    txtActRecordInit_mouseEntered(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtActRecordMaintain
        this.txtActRecordMaintain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    txtActRecordMaintain_mouseEntered(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtActRecordMaintain.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                try {
                    txtActRecordMaintain_mouseDragged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtHelp		
        this.txtHelp.setMaxLength(1000);		
        this.txtHelp.setRows(5);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(84);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelLength(84);
        // rbAdd		
        this.rbAdd.setText(resHelper.getString("rbAdd.text"));		
        this.rbAdd.setSelected(true);
        // rbMinus		
        this.rbMinus.setText(resHelper.getString("rbMinus.text"));
        // kDTextArea1		
        this.kDTextArea1.setText(resHelper.getString("kDTextArea1.text"));		
        this.kDTextArea1.setLineWrap(true);		
        this.kDTextArea1.setEditable(false);		
        this.kDTextArea1.setFont(resHelper.getFont("kDTextArea1.font"));		
        this.kDTextArea1.setEnabled(false);
        // dataRelationGroup1
        this.dataRelationGroup1.add(this.rbAdd);
        this.dataRelationGroup1.add(this.rbMinus);
        // comElimType		
        this.comElimType.setRequired(true);		
        this.comElimType.addItems(EnumUtils.getEnumList("com.kingdee.eas.ma.nbudget.BgElimTypeEnum").toArray());
        this.comElimType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cmbSubSystem_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnUpdateSumFormula
        this.btnUpdateSumFormula.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateSumFormula, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateSumFormula.setText(resHelper.getString("btnUpdateSumFormula.text"));		
        this.btnUpdateSumFormula.setToolTipText(resHelper.getString("btnUpdateSumFormula.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 500, 500));
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(1, 3, 478, 480));
        this.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabelContainer3.setBounds(new Rectangle(10, 410, 215, 19));
        kDPanel1.add(kDLabelContainer3, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 385, 215, 19));
        kDPanel1.add(kDLabelContainer2, null);
        kDLabelContainer4.setBounds(new Rectangle(240, 385, 215, 19));
        kDPanel1.add(kDLabelContainer4, null);
        contName.setBounds(new Rectangle(248, 9, 215, 19));
        kDPanel1.add(contName, null);
        contNumber.setBounds(new Rectangle(9, 9, 215, 19));
        kDPanel1.add(contNumber, null);
        kDLabelContainerItemGroup.setBounds(new Rectangle(9, 31, 215, 19));
        kDPanel1.add(kDLabelContainerItemGroup, null);
        kDLabelContainer1.setBounds(new Rectangle(248, 31, 215, 19));
        kDPanel1.add(kDLabelContainer1, null);
        contSpecialNumber.setBounds(new Rectangle(9, 54, 215, 19));
        kDPanel1.add(contSpecialNumber, null);
        isForFp.setBounds(new Rectangle(248, 56, 107, 19));
        kDPanel1.add(isForFp, null);
        kDLabelContainer7.setBounds(new Rectangle(358, 54, 69, 19));
        kDPanel1.add(kDLabelContainer7, null);
        btnFormulaEdit.setBounds(new Rectangle(312, 79, 22, 19));
        kDPanel1.add(btnFormulaEdit, null);
        kDLabelContainer5.setBounds(new Rectangle(9, 79, 300, 19));
        kDPanel1.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(9, 101, 300, 19));
        kDPanel1.add(kDLabelContainer6, null);
        kDLabelContainer8.setBounds(new Rectangle(9, 123, 300, 19));
        kDPanel1.add(kDLabelContainer8, null);
        btnActRecordInitEdit.setBounds(new Rectangle(312, 101, 22, 19));
        kDPanel1.add(btnActRecordInitEdit, null);
        btnActRecordMaintainEdit.setBounds(new Rectangle(312, 123, 22, 19));
        kDPanel1.add(btnActRecordMaintainEdit, null);
        btnIsSumFormula.setBounds(new Rectangle(343, 81, 140, 19));
        kDPanel1.add(btnIsSumFormula, null);
        lblDescription.setBounds(new Rectangle(10, 284, 100, 19));
        kDPanel1.add(lblDescription, null);
        mltDescription.setBounds(new Rectangle(9, 306, 455, 68));
        kDPanel1.add(mltDescription, null);
        kDScrollPane1.setBounds(new Rectangle(9, 177, 455, 100));
        kDPanel1.add(kDScrollPane1, null);
        kDLabelContainer11.setBounds(new Rectangle(10, 153, 100, 19));
        kDPanel1.add(kDLabelContainer11, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(kDBizPromptBoxUnit);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDBizPromptBoxPerson);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kDDatePicker);
        //contName
        contName.setBoundEditor(mltName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //kDLabelContainerItemGroup
        kDLabelContainerItemGroup.setBoundEditor(kDBizPromptBoxItemGroup);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDBizPromptBox1);
        //contSpecialNumber
        contSpecialNumber.setBoundEditor(specialNumber);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(fpDirection);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtFormula);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtActRecordInit);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtActRecordMaintain);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtHelp, null);
        //kDPanel2
        kDPanel2.setLayout(null);        kDLabelContainer9.setBounds(new Rectangle(8, 22, 254, 19));
        kDPanel2.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(8, 66, 88, 19));
        kDPanel2.add(kDLabelContainer10, null);
        rbAdd.setBounds(new Rectangle(95, 66, 82, 19));
        kDPanel2.add(rbAdd, null);
        rbMinus.setBounds(new Rectangle(198, 66, 98, 19));
        kDPanel2.add(rbMinus, null);
        kDTextArea1.setBounds(new Rectangle(11, 120, 441, 104));
        kDPanel2.add(kDTextArea1, null);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(comElimType);

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
        this.toolBar.add(btnUpdateSumFormula);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isForFP", boolean.class, this.isForFp, "selected");
		dataBinder.registerBinding("isSumFormula", boolean.class, this.btnIsSumFormula, "selected");
		dataBinder.registerBinding("description", String.class, this.mltDescription, "_multiLangItem");
		dataBinder.registerBinding("assignRootOrg", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.kDBizPromptBoxUnit, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.kDBizPromptBoxPerson, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDatePicker, "value");
		dataBinder.registerBinding("name", String.class, this.mltName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bgItemGroup", com.kingdee.eas.ma.budget.BgItemGroupInfo.class, this.kDBizPromptBoxItemGroup, "data");
		dataBinder.registerBinding("parent", com.kingdee.eas.ma.budget.BgItemInfo.class, this.kDBizPromptBox1, "data");
		dataBinder.registerBinding("specialNumber", String.class, this.specialNumber, "text");
		dataBinder.registerBinding("fpDirection", com.kingdee.eas.ma.budget.FpCashDirectionEnum.class, this.fpDirection, "selectedItem");
		dataBinder.registerBinding("formula", String.class, this.txtFormula, "text");
		dataBinder.registerBinding("acctInit", String.class, this.txtActRecordInit, "text");
		dataBinder.registerBinding("acctMaintain", String.class, this.txtActRecordMaintain, "text");
		dataBinder.registerBinding("Help", String.class, this.txtHelp, "text");
		dataBinder.registerBinding("elimType", com.kingdee.eas.ma.nbudget.BgElimTypeEnum.class, this.comElimType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.ma.budget.app.BgItemElimEditUIHandler";
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
        this.editData = (com.kingdee.eas.ma.budget.BgItemInfo)ov;
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
		getValidateHelper().registerBindProperty("isForFP", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSumFormula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assignRootOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bgItemGroup", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fpDirection", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("formula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("acctInit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("acctMaintain", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Help", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elimType", ValidateHelper.ON_SAVE);    		
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
     * output chBoxAllowChange_actionPerformed method
     */
    protected void chBoxAllowChange_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output isForFp_itemStateChanged method
     */
    protected void isForFp_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output btnIsSumFormula_itemStateChanged method
     */
    protected void btnIsSumFormula_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_inputMethodTextChanged method
     */
    protected void txtNumber_inputMethodTextChanged(java.awt.event.InputMethodEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kDBizPromptBoxItemGroup_dataChanged method
     */
    protected void kDBizPromptBoxItemGroup_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDBizPromptBox1_dataChanged method
     */
    protected void kDBizPromptBox1_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtFormula_mouseEntered method
     */
    protected void txtFormula_mouseEntered(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtActRecordInit_mouseEntered method
     */
    protected void txtActRecordInit_mouseEntered(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtActRecordMaintain_mouseEntered method
     */
    protected void txtActRecordMaintain_mouseEntered(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtActRecordMaintain_mouseDragged method
     */
    protected void txtActRecordMaintain_mouseDragged(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output cmbSubSystem_itemStateChanged method
     */
    protected void cmbSubSystem_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isForFP"));
        sic.add(new SelectorItemInfo("isSumFormula"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("assignRootOrg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("assignRootOrg.id"));
        	sic.add(new SelectorItemInfo("assignRootOrg.number"));
        	sic.add(new SelectorItemInfo("assignRootOrg.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bgItemGroup.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("bgItemGroup.id"));
        	sic.add(new SelectorItemInfo("bgItemGroup.number"));
        	sic.add(new SelectorItemInfo("bgItemGroup.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("parent.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("parent.id"));
        	sic.add(new SelectorItemInfo("parent.number"));
        	sic.add(new SelectorItemInfo("parent.name"));
		}
        sic.add(new SelectorItemInfo("specialNumber"));
        sic.add(new SelectorItemInfo("fpDirection"));
        sic.add(new SelectorItemInfo("formula"));
        sic.add(new SelectorItemInfo("acctInit"));
        sic.add(new SelectorItemInfo("acctMaintain"));
        sic.add(new SelectorItemInfo("Help"));
        sic.add(new SelectorItemInfo("elimType"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionEditFormula_actionPerformed method
     */
    public void actionEditFormula_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditActRecordMaintain_actionPerformed method
     */
    public void actionEditActRecordMaintain_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdateSumFormula_actionPerformed method
     */
    public void actionUpdateSumFormula_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditActRecordInit_actionPerformed method
     */
    public void actionEditActRecordInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareActionEditFormula(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditFormula() {
    	return false;
    }
	public RequestContext prepareActionEditActRecordMaintain(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditActRecordMaintain() {
    	return false;
    }
	public RequestContext prepareActionUpdateSumFormula(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateSumFormula() {
    	return false;
    }
	public RequestContext prepareActionEditActRecordInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditActRecordInit() {
    	return false;
    }

    /**
     * output ActionEditFormula class
     */     
    protected class ActionEditFormula extends ItemAction {     
    
        public ActionEditFormula()
        {
            this(null);
        }

        public ActionEditFormula(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditFormula.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditFormula.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditFormula.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgItemElimEditUI.this, "ActionEditFormula", "actionEditFormula_actionPerformed", e);
        }
    }

    /**
     * output ActionEditActRecordMaintain class
     */     
    protected class ActionEditActRecordMaintain extends ItemAction {     
    
        public ActionEditActRecordMaintain()
        {
            this(null);
        }

        public ActionEditActRecordMaintain(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditActRecordMaintain.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditActRecordMaintain.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditActRecordMaintain.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgItemElimEditUI.this, "ActionEditActRecordMaintain", "actionEditActRecordMaintain_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdateSumFormula class
     */     
    protected class ActionUpdateSumFormula extends ItemAction {     
    
        public ActionUpdateSumFormula()
        {
            this(null);
        }

        public ActionUpdateSumFormula(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpdateSumFormula.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateSumFormula.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateSumFormula.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgItemElimEditUI.this, "ActionUpdateSumFormula", "actionUpdateSumFormula_actionPerformed", e);
        }
    }

    /**
     * output ActionEditActRecordInit class
     */     
    protected class ActionEditActRecordInit extends ItemAction {     
    
        public ActionEditActRecordInit()
        {
            this(null);
        }

        public ActionEditActRecordInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditActRecordInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditActRecordInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditActRecordInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBgItemElimEditUI.this, "ActionEditActRecordInit", "actionEditActRecordInit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.ma.budget.client", "BgItemElimEditUI");
    }




}