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
public abstract class AbstractBaseDataMatchEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionImportData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportData(request,response,context);
	}
	protected void _handleActionImportData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}