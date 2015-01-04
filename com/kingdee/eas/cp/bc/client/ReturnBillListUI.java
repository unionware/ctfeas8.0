/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ReturnBillListUI extends AbstractReturnBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReturnBillListUI.class);
    
    /**
     * output class constructor
     */
    public ReturnBillListUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionAddNew.setVisible(false);
    	actionEdit.setVisible(false);
    	actionTraceUp.setVisible(false);
    	actionTraceDown.setVisible(false);
    	actionCreateTo.setVisible(false);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.ReturnBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.ReturnBillInfo objectValue = new com.kingdee.eas.cp.bc.ReturnBillInfo();
		
        return objectValue;
    }

}