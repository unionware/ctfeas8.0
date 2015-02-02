package com.kingdee.eas.ma.budget.client;


import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.Map;

import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.fi.newrpt.client.designer.IDisplayValueProvider;
import com.kingdee.eas.ma.budget.BgNationalFormatUIUtil;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.util.StringUtils;







public final class BudgetDiscDisplayValueProvider
  implements IDisplayValueProvider
{
  private BgDisCountFormEditUI ui = null;
  
  public BudgetDiscDisplayValueProvider(Component component)
  {
    if ((component != null) && ((component instanceof BgDisCountFormEditUI)))
      this.ui = ((BgDisCountFormEditUI)component);
  }
  
  public BigDecimal getCoefficient() {
    return this.ui.getBgFormInfo(this.ui).getMeasureUnit().getCoefficient();
  }
  
  public int getPrecision() {
    return this.ui.getBgFormInfo(this.ui).getCurrency().getPrecision();
  }
  
  public Map getMeasureUnitMap() {
    return this.ui.getMeasreUnitMap();
  }
  
  public Map getCurrencyMap() {
    return this.ui.getCurrencyMap();
  }
  
  public Map getFormulaMap() {
    return this.ui.getFormulaMap();
  }
  
  public Object getDisplayValue(Cell cell, int viewMode) {
    if ((cell != null) && (!BgNFSHelper.checkIsEmptyValue(cell))) {
      if ((cell.getValue().getValue() instanceof GregorianCalendar)) {
        return cell.getText();
      }
      String _text = cell.getText();
      if (!StringUtils.isEmpty(_text)) {
        if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
          if (getMeasureUnitMap().isEmpty()) {
            return getMeasureUnitDisplayValue(cell, _text, false);
          }
          return getMeasureUnitDisplayValue(cell, _text, true);
        }
        
        if ((cell.getUserObject("BG_COLLECT") != null) && (cell.getUserObject("BG_FORMULA") != null)) {
          if (getMeasureUnitMap().isEmpty()) {
            return getMeasureUnitValue(cell, _text, false);
          }
          return getMeasureUnitValue(cell, _text, true);
        }
        
        return getDefMeasureUnitDisplayValue(cell, _text);
      }
    }
    

    return null;
  }
  
  private Object getMeasureUnitValue(Cell cell, Object obj, boolean useMeasureUnit)
  {
    if ("0".equals(obj)) {
      String showStr = BgNFSHelper.checkShowStrByNumberic(cell);
      if (showStr != null) {
        return showStr;
      }
    }
    
    String _formulaStr = null;
    String[] _parameter = null;
    BigDecimal _coefficient = getCoefficient();
    int _precision = getPrecision();
    CurrencyInfo _currencyInfo = null;
    
    _formulaStr = cell.getUserObject("BG_FORMULA").getValue().toString();
    _parameter = BgNFSHelper.parseFormulaPara(getFormulaMap(), _formulaStr);
    if (_parameter != null) {
      if ((useMeasureUnit) && 
        (!StringUtils.isEmpty(_parameter[2])) && (getMeasureUnitMap().containsKey(_parameter[2])))
      {
        _coefficient = (BigDecimal)getMeasureUnitMap().get(_parameter[2]);
      }
      

      if ((!StringUtils.isEmpty(_parameter[5])) && (getCurrencyMap().containsKey(_parameter[5])))
      {
        _currencyInfo = (CurrencyInfo)getCurrencyMap().get(_parameter[5]);
        _precision = _currencyInfo.getPrecision();
      }
    }
    
    return getDisplayValue(obj, _coefficient, _precision);
  }
  
  protected Object getMeasureUnitDisplayValue(Cell cell, Object obj, boolean useMeasureUnit) {
    if ("0".equals(obj)) {
      String showStr = BgNFSHelper.checkShowStrByNumberic(cell);
      if (showStr != null) {
        return showStr;
      }
    }
    
    String _formulaStr = null;
    String[] _parameter = null;
    BigDecimal _coefficient = getCoefficient();
    int _precision = getPrecision();
    
    CurrencyInfo _currencyInfo = null;
    
    _formulaStr = BgNFSHelper.getFormulaOfCell(cell);
    _parameter = BgNFSHelper.parseFormulaPara(getFormulaMap(), _formulaStr);
    if (_parameter != null) {
      if ((useMeasureUnit) && 
        (!StringUtils.isEmpty(_parameter[2])) && (getMeasureUnitMap().containsKey(_parameter[2])))
      {
        _coefficient = (BigDecimal)getMeasureUnitMap().get(_parameter[2]);
      }
      

      if ((!StringUtils.isEmpty(_parameter[5])) && (getCurrencyMap().containsKey(_parameter[5])))
      {
        _currencyInfo = (CurrencyInfo)getCurrencyMap().get(_parameter[5]);
        _precision = _currencyInfo.getPrecision();
      }
    }
    
    return getDisplayValue(obj, _coefficient, _precision);
  }
  
  protected Object getDefMeasureUnitDisplayValue(Cell cell, Object obj) {
    if ((!BgNFSHelper.checkHasNumbericOfCell(cell)) || (!BgNFSHelper.checkIsNumericOfCell(cell))) {
      return obj;
    }
    String _text = null;
    int _precision = getPrecision();
    try
    {
      _precision = BgNFSHelper.getDigitOfNumber(cell, _precision);
      _text = getDisplayValue(obj, getCoefficient(), _precision);
    } catch (Exception ex) {
      _text = obj.toString();
    }
    
    return _text;
  }
  
  protected String getDisplayValue(Object obj, BigDecimal coefficient, int precision) {
    String _text = null;
    BigDecimal _value = null;
    DecimalFormat _decFormat = null;
    try
    {
      _value = BgNationalFormatUIUtil.parseToBigDecimal(obj).divide(coefficient, precision, 4);
      _decFormat = BgNationalFormatUIUtil.getDecimalFormat(precision);
      _text = _decFormat.format(_value.doubleValue());
    } catch (Exception ex) {
      _text = obj.toString();
    }
    
    return _text;
  }
}
