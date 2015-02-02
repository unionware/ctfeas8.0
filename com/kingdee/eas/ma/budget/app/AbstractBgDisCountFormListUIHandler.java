/**
 * output package name
 */
package com.kingdee.eas.ma.budget.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractBgDisCountFormListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewFlow(request,response,context);
	}
	protected void _handleActionViewFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}