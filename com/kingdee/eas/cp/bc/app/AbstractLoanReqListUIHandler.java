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
public abstract class AbstractLoanReqListUIHandler extends com.kingdee.eas.cp.bc.app.BizCollCoreBillListUIHandler

{
	public void handleActionViewRrcdsOfLendAndRepay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewRrcdsOfLendAndRepay(request,response,context);
	}
	protected void _handleActionViewRrcdsOfLendAndRepay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPayOff(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPayOff(request,response,context);
	}
	protected void _handleActionPayOff(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReturn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReturn(request,response,context);
	}
	protected void _handleActionReturn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionComfirmReturn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionComfirmReturn(request,response,context);
	}
	protected void _handleActionComfirmReturn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}