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
public abstract class AbstractBgModifyFormDataUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

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
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancel(request,response,context);
	}
	protected void _handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}