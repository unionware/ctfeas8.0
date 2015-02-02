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
public abstract class AbstractBgDisCountFormAddNewUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSelectAllCostCenter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectAllCostCenter(request,response,context);
	}
	protected void _handleActionSelectAllCostCenter(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisselectAllCostCenter(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisselectAllCostCenter(request,response,context);
	}
	protected void _handleActionDisselectAllCostCenter(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCmbBgSchemaChanged(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCmbBgSchemaChanged(request,response,context);
	}
	protected void _handleActionCmbBgSchemaChanged(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCmbBgFormChanged(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCmbBgFormChanged(request,response,context);
	}
	protected void _handleActionCmbBgFormChanged(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHelpContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHelpContent(request,response,context);
	}
	protected void _handleActionHelpContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuit(request,response,context);
	}
	protected void _handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}