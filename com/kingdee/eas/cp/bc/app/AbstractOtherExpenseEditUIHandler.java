/**
 * output package name
 */
package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractOtherExpenseEditUIHandler extends com.kingdee.eas.cp.bc.app.ExpenseReqEditUIHandler

{
	public void handleActionAddLinkLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddLinkLine(request,response,context);
	}
	protected void _handleActionAddLinkLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveLinkLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveLinkLine(request,response,context);
	}
	protected void _handleActionRemoveLinkLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}