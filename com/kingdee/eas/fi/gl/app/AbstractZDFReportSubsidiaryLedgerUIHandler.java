/**
 * output package name
 */
package com.kingdee.eas.fi.gl.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractZDFReportSubsidiaryLedgerUIHandler extends com.kingdee.eas.fi.gl.app.ReportBaseUIHandler

{
	public void handleActionAssist(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssist(request,response,context);
	}
	protected void _handleActionAssist(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVoucher(request,response,context);
	}
	protected void _handleActionVoucher(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionGeneralLedger(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGeneralLedger(request,response,context);
	}
	protected void _handleActionGeneralLedger(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPrintParame(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrintParame(request,response,context);
	}
	protected void _handleActionPrintParame(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMenuItemPrintAccountContents(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMenuItemPrintAccountContents(request,response,context);
	}
	protected void _handleActionMenuItemPrintAccountContents(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}