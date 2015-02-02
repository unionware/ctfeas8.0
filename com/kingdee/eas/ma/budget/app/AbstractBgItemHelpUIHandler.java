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
public abstract class AbstractBgItemHelpUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionEditFormula(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditFormula(request,response,context);
	}
	protected void _handleActionEditFormula(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditActRecordMaintain(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditActRecordMaintain(request,response,context);
	}
	protected void _handleActionEditActRecordMaintain(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpdateSumFormula(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdateSumFormula(request,response,context);
	}
	protected void _handleActionUpdateSumFormula(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditActRecordInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditActRecordInit(request,response,context);
	}
	protected void _handleActionEditActRecordInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}