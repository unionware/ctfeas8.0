/**
 * output package name
 */
package com.kingdee.eas.ma.nbudget.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractBgNFormProcessUIHandler extends com.kingdee.eas.ma.nbudget.app.BgNFProcessUIHandler

{
	public void handleActionItemDeco(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionItemDeco(request,response,context);
	}
	protected void _handleActionItemDeco(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOpenExamineReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOpenExamineReport(request,response,context);
	}
	protected void _handleActionOpenExamineReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCreateExpFromItemProperty(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateExpFromItemProperty(request,response,context);
	}
	protected void _handleActionCreateExpFromItemProperty(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPredict(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPredict(request,response,context);
	}
	protected void _handleActionPredict(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTraceActualVal(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTraceActualVal(request,response,context);
	}
	protected void _handleActionTraceActualVal(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportBgItem(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportBgItem(request,response,context);
	}
	protected void _handleActionImportBgItem(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportBgItem(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportBgItem(request,response,context);
	}
	protected void _handleActionExportBgItem(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHelped(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHelped(request,response,context);
	}
	protected void _handleActionHelped(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCheckSubsidiaryLedger(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCheckSubsidiaryLedger(request,response,context);
	}
	protected void _handleActionCheckSubsidiaryLedger(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}