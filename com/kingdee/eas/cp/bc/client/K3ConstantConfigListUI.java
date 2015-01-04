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
public class K3ConstantConfigListUI extends AbstractK3ConstantConfigListUI
{
    private static final Logger logger = CoreUIObject.getLogger(K3ConstantConfigListUI.class);
    
    /**
     * output class constructor
     */
    public K3ConstantConfigListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
 
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionAddNew.setVisible(false);
    	this.actionAddNew.setEnabled(false);
    	this.actionAttachment.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionRemove.setEnabled(false);
    	
    	this.actionPrint.setEnabled(false);
    	this.actionPrint.setVisible(false);
    	
    	this.actionPrintPreview.setEnabled(false);
    	this.actionPrintPreview.setVisible(false);
    	
    	
    }
    

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.K3ConstantConfigFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.K3ConstantConfigInfo objectValue = new com.kingdee.eas.cp.bc.K3ConstantConfigInfo();
		
        return objectValue;
    }

}