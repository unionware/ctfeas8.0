/**
 * output package name
 */
package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPositionMainListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionChangeView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangeView(request,response,context);
	}
	protected void _handleActionChangeView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}