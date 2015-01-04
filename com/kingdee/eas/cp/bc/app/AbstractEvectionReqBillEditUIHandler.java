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
public abstract class AbstractEvectionReqBillEditUIHandler extends com.kingdee.eas.cp.bc.app.BizCollCoreBillEditUIHandler

{
	public void handleActionViewBudgetBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBudgetBalance(request,response,context);
	}
	protected void _handleActionViewBudgetBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}