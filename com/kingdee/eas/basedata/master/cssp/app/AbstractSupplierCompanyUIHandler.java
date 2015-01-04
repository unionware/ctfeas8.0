/**
 * output package name
 */
package com.kingdee.eas.basedata.master.cssp.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSupplierCompanyUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSupplierTradeRpt(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSupplierTradeRpt(request,response,context);
	}
	protected void _handleActionSupplierTradeRpt(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddRowBank(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRowBank(request,response,context);
	}
	protected void _handleActionAddRowBank(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelRowBank(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelRowBank(request,response,context);
	}
	protected void _handleActionDelRowBank(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}