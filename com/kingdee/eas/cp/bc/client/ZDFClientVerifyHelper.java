package com.kingdee.eas.cp.bc.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class ZDFClientVerifyHelper
{
  private static String path = "com.kingdee.eas.fm.common.FMResource";

  private static final BigDecimal MAX_VALUE = new BigDecimal("9999999999999.99");
  private static final BigDecimal MIN_VALUE = new BigDecimal("-9999999999999.99");

  public static void verifyInput(CoreUIObject ui, String resourcePath, KDTable kdtEntries, IRow row, int i, String msg)
  {
    if (ZDFHelper.isEmpty(row.getCell(i).getValue())) {
      kdtEntries.getEditManager().editCellAt(row.getRowIndex(), i);
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));

      SysUtil.abort();
    }
  }

  public static void verifyInput(CoreUIObject ui, KDTable kdtEntries)
  {
    IRow row = null;
    int colcount = kdtEntries.getColumnCount();
    for(int i=0;i<colcount;i++){
    	if(kdtEntries.getColumn(i).isRequired()){
    	 for (int j = 0; j < kdtEntries.getRowCount(); j++)
    	    {
    	      row = kdtEntries.getRow(j);
    	      verifyInput(ui, kdtEntries, row, kdtEntries.getColumnKey(i));
    	    }
    	}
    }
  }
  
  
  public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key)
  {
    IRow row = null;
    for (int j = 0; j < kdtEntries.getRowCount(); j++)
    {
      row = kdtEntries.getRow(j);
      verifyInput(ui, kdtEntries, row, key);
    }
  }

  public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, IRow row, String key)
  {
    int colIndex = kdtEntries.getColumnIndex(key);

    if (ZDFHelper.isEmpty(row.getCell(key).getValue()))
    {
      kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);

      String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
      String msg = headValue + " " + EASResource.getString(path, "CanNotBeNull");
      msg = msg.replaceAll("#", " " + headValue + " ");
      MsgBox.showWarning(ui, msg);

      SysUtil.abort();
    }
  }

  public static void verifyInputIsZero(CoreUIObject ui, KDTable kdtEntries, IRow row, String key)
  {
    int colIndex = kdtEntries.getColumnIndex(key);

    Object obj = row.getCell(key).getValue();

    if (((obj instanceof BigDecimal)) && (((BigDecimal)obj).floatValue() == 0.0F)) {
      kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);

      String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
      String msg = headValue + " " + EASResource.getString(path, "CanNotBeZero");
      msg = msg.replaceAll("#", " " + headValue + " ");
      MsgBox.showWarning(ui, msg);

      SysUtil.abort();
    }
  }

  public static void verifyInputIsMaxValue(CoreUIObject ui, KDTable kdtEntries, IRow row, String key)
  {
    int colIndex = kdtEntries.getColumnIndex(key);

    Object obj = row.getCell(key).getValue();

    if ((!ZDFHelper.isEmpty(obj)) && ((obj instanceof BigDecimal)) && (((BigDecimal)obj).compareTo(MAX_VALUE) == 1))
    {
      kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);

      String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
      String msg = headValue + " " + EASResource.getString(path, "CanNotBeZero");
      msg = msg.replaceAll("#", " " + headValue + " ");
      MsgBox.showWarning(ui, msg);

      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, KDTable kdtEntries)
  {
	  verifyEmpty(ui, kdtEntries,EASResource.getString(path, "EntryCanNotBeNull"));
  }
  public static void verifyEmpty(CoreUIObject ui, KDTable kdtEntries,String msg)
  {
	  if ((kdtEntries == null) || (kdtEntries.getRowCount() < 1)) {
		  MsgBox.showWarning(ui, msg);
		  SysUtil.abort();
	  }
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDTextField txtNumber, String msg)
  {
    String txt = txtNumber.getText();
    String tooltip = txtNumber.getToolTipText();
    if ((txt == null) || (txt.trim().equals(""))) {
      txtNumber.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? (tooltip != null ? tooltip : "") + getMessage(txtNumber) : EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, KDTextField txtNumber) {
    verifyEmpty(ui, null, txtNumber, null);
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, JFormattedTextField txtField, String msg, boolean isAbort)
  {
    String txt = txtField.getText();
    if ((txt == null) || (txt.trim().equals(""))) {
      txtField.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? getMessage(txtField) : EASResource.getString(resourcePath, msg));
      if (isAbort)
        SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, JFormattedTextField txtNumber)
  {
    verifyEmpty(ui, null, txtNumber, null, true);
  }

  private static String getMessage(Component component)
  {
    String text = getCompLabelText(component);
    return text + EASResource.getString(path, "CanNotBeNull");
  }

  private static String getCompLabelText(Component component) {
    String text = "";
    if ((component.getParent() instanceof KDLabelContainer))
    {
      text = ((KDLabelContainer)component.getParent()).getBoundLabelText();
    }
    else if ((component instanceof KDTextArea))
    {
      Container cont = component.getParent();
      if (cont != null)
      {
        Container cont2 = cont.getParent();
        if ((cont2 instanceof KDScrollPane))
        {
          if ((cont2.getParent() instanceof KDLabelContainer))
          {
            text = ((KDLabelContainer)cont2.getParent()).getBoundLabelText();
          }
        }
      }
    }
    return text;
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDBizPromptBox bizBox, String msg, boolean isAbort)
  {
    Object content = bizBox.getData();
    String tooltip = bizBox.getToolTipText();
    if ((content == null) || ((content.getClass().isArray()) && (ZDFHelper.isEmpty((Object[])content)))) {
      bizBox.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? (tooltip != null ? tooltip : "") + getMessage(bizBox) : EASResource.getString(resourcePath, msg));
      if (isAbort) SysUtil.abort(); 
    }
  }

  public static void verifyEmpty(CoreUIObject ui, KDBizPromptBox bizBox)
  {
    verifyEmpty(ui, null, bizBox, null, true);
  }

  public static void verifyEmpty(CoreUIObject ui, KDComboBox comboBox) {
    verifyEmpty(ui, null, comboBox, null);
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDMultiLangBox multLangBox, String msg)
  {
    Object content = multLangBox.getSelectedItem();
    if ((content == null) || (content.toString().trim().length() <= 0)) {
      multLangBox.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? getMessage(multLangBox) : EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
    
    
   
  }

  public static void verifyEmpty(CoreUIObject ui, KDMultiLangBox multLangBox) {
    verifyEmpty(ui, null, multLangBox, null);
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDComboBox comboBox, String msg) {
    Object content = comboBox.getSelectedItem();
    if (content == null) {
      comboBox.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? getMessage(comboBox) : EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDDatePicker datePicker, String msg)
  {
    String content = datePicker.getText();
    if ((content == null) || (content.equals(""))) {
      datePicker.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? getMessage(datePicker) : EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, KDDatePicker datePicker) {
    verifyEmpty(ui, null, datePicker, null);
  }

  /*public static void verifyDateFromTo(CoreUIObject ui, String resourcePath, Date dateFrom, Date dateTo, String msg)
  {
    dateFrom = DateTimeUtils.truncateDate(dateFrom);
    dateTo = DateTimeUtils.truncateDate(dateTo);
    if (dateFrom.compareTo(dateTo) == 0) {
      dateTo = YTGDateHelper.getNextDay(dateTo);
    }
    if (dateFrom.compareTo(dateTo) > 0) {
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }*/

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDFormattedTextField txtNumber, String msg) {
    Object txt = txtNumber.getNumberValue();
    if (txt == null) {
      txtNumber.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? getMessage(txtNumber) : EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDTextArea txtArea, String msg) {
    String txt = txtArea.getText();
    if ((txt == null) || (txt.trim().length() <= 0)) {
      txtArea.requestFocus(true);
      MsgBox.showWarning(ui, (resourcePath == null) || (msg == null) ? getMessage(txtArea) : EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, KDTextArea txtArea, String name)
  {
    String txt = txtArea.getText();
    if ((txt == null) || (txt.trim().length() <= 0)) {
      txtArea.requestFocus(true);
      MsgBox.showWarning(name + EASResource.getString(path, "CanNotBeNull"));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, KDTextArea txtArea) {
    verifyEmpty(ui, null, txtArea, null);
  }

  public static void verifyEmpty(CoreUIObject ui, KDFormattedTextField txtNumber) {
    verifyEmpty(ui, null, txtNumber, null);
  }

  public static void checkStringLength(Component ui, String resourcePath, IRow row, String colName, int length)
  {
    String name = (String)row.getCell(colName).getValue();
    if ((name != null) && (name.length() > length))
    {
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, "StringLengthGreaterthan") + length + ":" + "\n\n" + name);
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, Object value, String msg)
  {
    if (ZDFHelper.isEmpty(value)) {
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmpty(CoreUIObject ui, String resourcePath, Object value, String msg, String ctrlName)
  {
    if (ZDFHelper.isEmpty(value)) {
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
      setComponentFocus(ui, ctrlName);
      SysUtil.abort();
    }
  }

  public static void setComponentFocus(JComponent ui, String componentName)
  {
    Component[] comps = ui.getComponents();
    if ((comps != null) && (comps.length > 0))
    {
      for (int i = 0; i < comps.length; i++)
      {
        if (((comps[i] instanceof KDPanel)) || ((comps[i] instanceof KDScrollPane)) || ((comps[i] instanceof KDSplitPane)) || ((comps[i] instanceof KDTabbedPane)) || ((comps[i] instanceof KDLabelContainer)) || ((comps[i] instanceof KDContainer)) || ((comps[i] instanceof KDPanel)) || ((comps[i] instanceof JPanel)))
        {
          setComponentFocus((JComponent)comps[i], componentName);
        }
        else if ((comps[i] != null) && (comps[i].getName() != null) && (comps[i].getName().equals(componentName)))
          comps[i].requestFocus();
      }
    }
  }

  public static void verifyEmptyAndNoZero(CoreUIObject ui, String resourcePath, Object value, String msg)
  {
    if (value == null) {
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
    if (((value instanceof BigDecimal)) && 
      (((BigDecimal)value).compareTo(ZDFHelper.ZERO) == 0)) {
      MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
      SysUtil.abort();
    }
  }

  public static void verifyEmptyAndNoZero(CoreUIObject ui, KDFormattedTextField txt)
  {
    verifyEmpty(ui, txt);

    BigDecimal value = txt.getBigDecimalValue();

    if (value.compareTo(ZDFHelper.ZERO) == 0) {
      txt.requestFocus(true);
      String msg = getCompLabelText(txt) + EASResource.getString(path, "CanNotBeZero");
      MsgBox.showWarning(ui, msg);
      SysUtil.abort();
    }
  }

  public static void verifyBeginEndDateRel(CoreUIObject ui, Date beginDate, Date endDate)
  {
    if ((beginDate != null) && (endDate != null) && (beginDate.after(endDate))) {
      MsgBox.showWarning(ui, EASResource.getString(path, "beginDateNotGreaterEndDate"));
      SysUtil.abort();
    }
  }

  public static void verifyRequire(CoreUIObject ui)
  {
    Component[] comps = null;
    boolean isFocusPolicy = false;
    FocusTraversalPolicy focusTraversalPolicy = ui.getFocusTraversalPolicy();
    if ((focusTraversalPolicy instanceof UIFocusTraversalPolicy)) {
      UIFocusTraversalPolicy policy = (UIFocusTraversalPolicy)focusTraversalPolicy;
      comps = policy.getComponents();
    }

    if (comps != null)
      isFocusPolicy = true;
    else {
      comps = ui.getComponents();
    }
    for (int i = 0; i < comps.length; i++) {
      Component comp = comps[i];
      if ((comp.isEnabled()) && (
        (isFocusPolicy) || ((comp instanceof KDLabelContainer)))) {
        JComponent editor = null;
        if (isFocusPolicy) {
          editor = (JComponent)comp;
        } else {
          KDLabelContainer ct = (KDLabelContainer)comp;
          editor = ct.getBoundEditor();
        }

        if (editor.isEnabled())
          if ((editor instanceof KDTextField)) {
            KDTextField txtEditor = (KDTextField)editor;
            if (txtEditor.isRequired()) {
              verifyEmpty(ui, txtEditor);
            }
          }
          else  if ((editor instanceof KDTextArea)) {
        	  KDTextArea txtEditor = (KDTextArea)editor;
              if (txtEditor.isRequired()) {
                verifyEmpty(ui, txtEditor);
              }
           }
          else if ((editor instanceof KDComboBox)) {
            KDComboBox txtEditor = (KDComboBox)editor;
            if (txtEditor.isRequired()) {
              verifyEmpty(ui, txtEditor);
            }
          }
          else if ((editor instanceof KDDatePicker)) {
            KDDatePicker txtEditor = (KDDatePicker)editor;
            if (txtEditor.isRequired()) {
              verifyEmpty(ui, txtEditor);
            }
          }
          else if ((editor instanceof KDBizPromptBox)) {
            KDBizPromptBox txtEditor = (KDBizPromptBox)editor;
            if (txtEditor.isRequired()) {
              verifyEmpty(ui, txtEditor);
            }
          }
          else if ((editor instanceof KDFormattedTextField)) {
            KDFormattedTextField txtEditor = (KDFormattedTextField)editor;
            if (txtEditor.isRequired()) {
              verifyEmpty(ui, txtEditor);
            }
          }
          else if ((editor instanceof KDBizMultiLangBox)) {
            KDBizMultiLangBox txtEditor = (KDBizMultiLangBox)editor;
            if (txtEditor.isRequired())
              verifyEmpty(ui, txtEditor);
          }
      }
    }
  }
  
  

  /* public static void verifyTableMaxMinValueByOtherCol(CoreUIObject ui, KDTable table,String colKey,String maxColKey)
  {
	  verifyTableMaxMinValueByOtherCol(ui, table, colKey, maxColKey, MIN_VALUE);
  }
  
  public static void verifyTableMaxMinValueByOtherCol(CoreUIObject ui, KDTable table,String colKey,String maxColKey,BigDecimal minVal)
  {
	  verifyTableMaxMinValueByOtherCol(ui, table, colKey, maxColKey, minVal, false);
  }
  
  public static void verifyTableMaxMinValueByOtherCol(CoreUIObject ui, KDTable table,String colKey,String maxColKey,BigDecimal minVal,boolean minCanEqu)
  {
	  verifyTableMaxMinValueByOtherCol(ui, table, colKey, maxColKey, minVal,minCanEqu, false);
  }
  
  
  public static void verifyTableMaxMinValueByOtherCol(CoreUIObject ui, KDTable table,String colKey,String maxColKey,BigDecimal minVal,boolean minCanEqu,boolean maxCanEqu)
  {
	  int rowCount = table.getRowCount();
	  YTGNumberValueVerifyRule rule =null;
	  BigDecimal maxval = null;
	  for (int i = 0; i < rowCount; i++)
	    {
		  maxval =  getBigDecimalVal(table.getCell(i, maxColKey).getValue()) ;
		  rule =  new YTGNumberValueVerifyRule(minVal,maxval);
		  verifyTableMaxMinValue(ui, table, i, colKey, rule,minCanEqu,maxCanEqu);
	    }
  }
  
  
  public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table,String colKey, YTGNumberValueVerifyRule rule)
  {
	  int rowCount = table.getRowCount();
	  for (int i = 0; i < rowCount; i++)
	    {
		  verifyTableMaxMinValue(ui, table, i, colKey, rule);
	    }
  }
  
  public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table,int rowIndex,String colKey, YTGNumberValueVerifyRule rule)
  {
	  verifyTableMaxMinValue(ui, table, rowIndex, colKey, rule, false);
  }
  
  public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table,int rowIndex,String colKey, YTGNumberValueVerifyRule rule,boolean minCanEqu)
  {
	  verifyTableMaxMinValue(ui, table, rowIndex, colKey, rule, minCanEqu,false);
  }
  
  public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table,int rowIndex,String colKey, YTGNumberValueVerifyRule rule,boolean minCanEqu,boolean maxCanEqu)
  
  {
	  IRow headRow = table.getHeadRow(0);
	  Object obj = table.getCell(rowIndex, colKey).getValue();
	  if ((obj != null) && ((obj instanceof BigDecimal) || obj instanceof BigInteger || obj instanceof Integer ))
      {
        BigDecimal numValue = (BigDecimal)getBigDecimalVal(obj);
        if(numValue==null){
        	return ;
        }
        String colCapital = (String)headRow.getCell(colKey).getValue();

        String msgMinAdd= minCanEqu?"":"等于";
        String msgMaxAdd= maxCanEqu?"":"等于";
        
        int rev = numValue.compareTo(rule.getMaxValue());
        if ((maxCanEqu && rev>0) || (!maxCanEqu && rev>=0) ) {
        	table.getEditManager().editCellAt(rowIndex, table.getColumnIndex(colKey));
            String message = "第"+(rowIndex+1)+"行"+colCapital+" 不能大于"+msgMaxAdd+rule.getMaxValue();
            MsgBox.showWarning(ui, message);
            SysUtil.abort();
          }

         rev = numValue.compareTo(rule.getMinValue());
         if ((minCanEqu && rev<0) || (!minCanEqu && rev<=0) ) {
        	  table.getEditManager().editCellAt(rowIndex, table.getColumnIndex(colKey));
        	 String message = "第"+(rowIndex+1)+"行"+colCapital+" 不能小于"+msgMinAdd+rule.getMinValue();
            MsgBox.showWarning(ui, message);
            SysUtil.abort();
          }
      }
  }*/



  

  
  public static BigDecimal getBigDecimalVal(Object val){
  	if(val instanceof BigDecimal){
  		return (BigDecimal)val;
  	}
  	if(val instanceof BigInteger){
  		return new BigDecimal(((BigInteger)val).intValue());
  	}
  	
  	return new BigDecimal(val.toString());
  }

 /* public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table, YTGNumberValueVerifyRule rule)
  {
    int rowCount = table.getRowCount();
    int colCount = table.getColumnCount();

    IRow headRow = table.getHeadRow(0);

    String resPath = "com.kingdee.eas.fm.common.client.FMCommonClientResource";
    String resNameMax = "exceedMaxValue";
    String resNameMin = "lessMinValue";

    for (int i = 0; i < rowCount; i++)
    {
      for (int j = 0; j < colCount; j++)
      {
        if (!table.getColumn(j).getStyleAttributes().isHided())
        {
          Object obj = table.getCell(i, j).getValue();

          if ((obj != null) && ((obj instanceof BigDecimal)))
          {
            BigDecimal numValue = (BigDecimal)obj;

            String colCapital = (String)headRow.getCell(j).getValue();

            if (numValue.compareTo(rule.getMaxValue()) > 0) {
              String message = FDCClientHelper.formatMessage("com.kingdee.eas.fm.common.client.FMCommonClientResource", "exceedMaxValue", new Object[] { String.valueOf(i + 1), colCapital, rule.getMaxValue() });

              MsgBox.showWarning(ui, message);
              SysUtil.abort();
            }

            if (numValue.compareTo(rule.getMinValue()) < 0) {
              String message = FDCClientHelper.formatMessage("com.kingdee.eas.fm.common.client.FMCommonClientResource", "lessMinValue", new Object[] { String.valueOf(i + 1), colCapital, rule.getMinValue() });

              MsgBox.showWarning(ui, message);
              SysUtil.abort();
            }
          }
        }
      }
    }
  }*/

  private static Set getAllComponents(Container container)
  {
    Component[] components = container.getComponents();
    Set compSet = new HashSet();
    int i = 0; for (int n = components.length; i < n; i++)
    {
      Component comp = components[i];
      compSet.add(comp);
      if ((comp instanceof Container))
      {
        compSet.addAll(getAllComponents((Container)comp));
      }
    }
    return compSet;
  }

  public static void verifyUIControlEmpty(CoreUI ui)
  {
    Set allLeafComponents = getAllComponents(ui);
    Iterator it = allLeafComponents.iterator();
    while (it.hasNext())
    {
      Component comp = (Component)it.next();
      if ((comp instanceof IKDTextComponent))
      {
        IKDTextComponent txtComp = (IKDTextComponent)comp;
        if ((txtComp.isRequired()) && 
          (comp.getName() != null))
        {
          if ((comp instanceof KDTextField))
          {
            verifyEmpty(ui, (KDTextField)comp);
          }
          else if ((comp instanceof KDBizPromptBox))
          {
            verifyEmpty(ui, (KDBizPromptBox)comp);
          }
          else if ((comp instanceof KDComboBox))
          {
            verifyEmpty(ui, (KDComboBox)comp);
          }
          else if ((comp instanceof KDMultiLangBox))
          {
            verifyEmpty(ui, (KDMultiLangBox)comp);
          }
          else if ((comp instanceof KDDatePicker))
          {
            verifyEmpty(ui, (KDDatePicker)comp);
          }
          else if ((comp instanceof KDFormattedTextField))
          {
            verifyEmpty(ui, (KDFormattedTextField)comp);
          }
          else if ((comp instanceof KDTextArea))
          {
            verifyEmpty(ui, (KDTextArea)comp);
          }
        }
      }
    }
  }
}