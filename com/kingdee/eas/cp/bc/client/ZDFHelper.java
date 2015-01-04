/**
 * 系统项目名称
 * com.kingdee.eas.lgc.bd.client
 * STOHelper.java
 * 
 * 2013-4-19-下午06:21:51
 * @author unionware
 * 
 */
package com.kingdee.eas.cp.bc.client;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.assistant.IExchangeRate;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * STOHelper
 * 
 * 
 * 2013-4-19 下午06:21:51
 * 
 * @version 1.0.0
 * @description:
 */
public class ZDFHelper {
	
	  public static final BigDecimal ZERO = new BigDecimal("0");
	  
	  public static final BigDecimal MAX_VALUE = new BigDecimal("9999999999999.99");
	  
	 
	  public static final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");

	  public static final DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	  public static final DateFormat FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");
	  
	  
	 public static boolean isEmpty(Object[] param)
	  {
	    return (param == null) || (param.length == 0) || (param[0] == null);
	  }
	 public static boolean isEmpty(String str)
	  {
	    return (str == null) || (str.trim().length() == 0);
	  }
	  public static boolean isEmpty(Object obj) {
	    if ((obj instanceof String))
	      return isEmpty((String)obj);
	    if ((obj instanceof Map)) {
	      Map map = (Map)obj;
	      return map.size() <= 0;
	    }if ((obj instanceof Collection)) {
	      Collection c = (Collection)obj;
	      return c.size() <= 0;
	    }
	    return obj == null ? true : isEmpty(obj.toString());
	  }
	  
	  public static void formatTableDate(KDTable table, String columnName)
	  {
	    formatTable(table, columnName, "yyyy-MM-dd");
	  }

	  public static void formatTableDateTime(KDTable table, String columnName) {
	    formatTable(table, columnName, "yyyy-MM-dd HH:mm:ss");
	  }
	  

	  public static void formatTableNumber(KDTable table, String columnName)
	  {
	    table.getColumn(columnName).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");

	    table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
	  }

	  public static void formatTableNumber(KDTable table, int from, int to)
	  {
	    for (int i = from; (i <= to) && 
	      (i < table.getColumnCount()); i++)
	    {
	      table.getColumn(i).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	      table.getColumn(i).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
	    }
	  }

	  public static void formatTableNumber(KDTable table, int columnIndex, String format)
	  {
	    table.getColumn(columnIndex).getStyleAttributes().setNumberFormat(format);

	    table.getColumn(columnIndex).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
	  }

	  public static void formatTableNumber(KDTable table, int[] columnIndex)
	  {
	    for (int i = 0; i < columnIndex.length; i++) {
	      int j = columnIndex[i];
	      table.getColumn(j).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	      table.getColumn(j).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
	    }
	  }

	  public static void formatTableNumber(KDTable table, String[] columnNames)
	  {
	    for (int i = 0; i < columnNames.length; i++)
	      formatTableNumber(table, columnNames[i]);
	  }

	  public static void formatTableNumber(KDTable table, String[] columnNames, String format)
	  {
	    for (int i = 0; i < columnNames.length; i++)
	      formatTableNumber(table, columnNames[i], format);
	  }

	  public static void formatTableNumber(KDTable table, String columnName, String format)
	  {
	    table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);

	    table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
	  }

	  public static void formatTable(KDTable table, String columnName, String format)
	  {
	    table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);
	  }
	  
	  
	  public static void checkBillInWorkflow(CoreUIObject ui, String id)
	  {
	    ProcessInstInfo instInfo = null;
	    ProcessInstInfo[] procInsts = null;
	    try {
	      IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();

	      procInsts = service2.getProcessInstanceByHoldedObjectId(id);
	    } catch (BOSException e) {
	      ExceptionHandler.handle(e);
	    }

	    int i = 0; for (int n = procInsts.length; i < n; i++)
	    {
	      if (("open.running".equals(procInsts[i].getState())) || ("open.not_running.suspended".equals(procInsts[i].getState())))
	      {
	        instInfo = procInsts[i];
	      }
	    }
	    if (instInfo != null) {
	      MsgBox.showWarning(ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_BindWfInstance"));

	      SysUtil.abort();
	    }
	  }
	  
	  
	  public static ExchangeRateInfo getLocalExRateBySrcCurcy(CoreUIObject ui, BOSUuid srcid, CompanyOrgUnitInfo currentFIUnit, Date bookedDate) throws BOSException, EASBizException
	  {
		  return getLocalExRateBySrcCurcy(ui, srcid, currentFIUnit, bookedDate, false);
	  }
	  
	  public static ExchangeRateInfo getLocalExRateBySrcCurcy(CoreUIObject ui, BOSUuid srcid, CompanyOrgUnitInfo currentFIUnit, Date bookedDate,boolean shouMsg)
	    throws BOSException, EASBizException
	  {
	    ExchangeTableInfo baseExchangeTable = currentFIUnit.getBaseExchangeTable();
	    if (baseExchangeTable != null) {
	      CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
	      if (baseCurrency != null)
	      {
	        if (srcid.equals(baseCurrency.getId())) {
	          return null;
	        }

	        IExchangeRate iExchangeRate = ExchangeRateFactory.getRemoteInstance();
	        ExchangeRateInfo exchangeRate = iExchangeRate.getExchangeRate(new ObjectUuidPK(baseExchangeTable.getId()), new ObjectUuidPK(srcid), new ObjectUuidPK(baseCurrency.getId()), bookedDate);

	        if (exchangeRate != null) {
	          return exchangeRate;
	        }

	      }
	      else
	      {
	    	  if(shouMsg){
		        MsgBox.showWarning(ui, "当前组织没有设置本位币");
		        SysUtil.abort();
	    	  }
	      }
	    }
	    else {
	    	if(shouMsg){
			      MsgBox.showWarning(ui, "当前财务组织没有设置基本汇率表");
		
			      SysUtil.abort();
			    	}
	    	}

	    return null;
	  }
	  
	  /**
	   * @deprecated
	   * @author michael
	   * @date: 2013-12-24
	   * @param orgPK
	   * @param permItemName
	   * @return 
	   * @exception 
	   * @description:
	   */
	  public static boolean hasFunctionPermission(IObjectPK orgPK, String permItemName)   {
		    /* try
		     {
		       IPermission service = PermissionFactory.getRemoteInstance();
		       Context ctx = WafContext.getInstance().getContext();
		       IObjectPK userPK = null;
		       CoreBaseInfo user = (CoreBaseInfo)ctx.get("UserInfo");
		       if (user != null) {
		         userPK = new ObjectUuidPK(user.getId());
		       }
		       return service.hasFunctionPermission(userPK, orgPK, permItemName);
		     } catch (EASBizException e) {
		       return false; } catch (BOSException e) {
		     }*/
		     return false;
	 }
	  public static boolean hasFunctionPermission(IObjectPK userPK, IObjectPK orgPK, String permItemName)   {
		  try
		  {
			  IPermission service = PermissionFactory.getRemoteInstance();
			  return service.hasFunctionPermission(userPK, orgPK, permItemName);
		  } catch (EASBizException e) {
			  return false;
		  	} catch (BOSException e) {
			  }
			  return false;
	  }
	  
}
