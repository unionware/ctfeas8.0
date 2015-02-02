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
public abstract class AbstractBgSchemeTreeListUIHandler extends com.kingdee.eas.ma.budget.app.BgOrgBasedListUIHandler

{
	public void handleActionAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssign(request,response,context);
	}
	protected void _handleActionAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExecute(request,response,context);
	}
	protected void _handleActionExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAntiExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiExecute(request,response,context);
	}
	protected void _handleActionAntiExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDataAccredit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDataAccredit(request,response,context);
	}
	protected void _handleActionDataAccredit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionGetDataBySchemes(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGetDataBySchemes(request,response,context);
	}
	protected void _handleActionGetDataBySchemes(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBgSchemePlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBgSchemePlan(request,response,context);
	}
	protected void _handleActionBgSchemePlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCreateCtrlInfos(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCreateCtrlInfos(request,response,context);
	}
	protected void _handleActionCreateCtrlInfos(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearCtrlInfos(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearCtrlInfos(request,response,context);
	}
	protected void _handleActionClearCtrlInfos(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVerifyCtrl(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVerifyCtrl(request,response,context);
	}
	protected void _handleActionVerifyCtrl(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAssign(request,response,context);
	}
	protected void _handleActionUnAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFiling(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFiling(request,response,context);
	}
	protected void _handleActionFiling(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnFiling(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnFiling(request,response,context);
	}
	protected void _handleActionUnFiling(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCopy(request,response,context);
	}
	protected void _handleActionCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelCopyScheme(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelCopyScheme(request,response,context);
	}
	protected void _handleActionDelCopyScheme(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPatchDelCopyForm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPatchDelCopyForm(request,response,context);
	}
	protected void _handleActionPatchDelCopyForm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}