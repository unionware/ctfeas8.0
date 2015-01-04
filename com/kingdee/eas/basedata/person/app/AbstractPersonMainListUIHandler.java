/**
 * output package name
 */
package com.kingdee.eas.basedata.person.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPersonMainListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionImportPerson(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportPerson(request,response,context);
	}
	protected void _handleActionImportPerson(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleAction_Input(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAction_Input(request,response,context);
	}
	protected void _handleAction_Input(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionShowDiaplayName(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShowDiaplayName(request,response,context);
	}
	protected void _handleActionShowDiaplayName(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportBankInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportBankInfo(request,response,context);
	}
	protected void _handleActionImportBankInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}