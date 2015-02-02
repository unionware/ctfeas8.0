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
public class K3ConstantConfigEditUI extends AbstractK3ConstantConfigEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(K3ConstantConfigEditUI.class);
    
    /**
     * output class constructor
     */
    public K3ConstantConfigEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        this.txtNumber.setEnabled(false);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
 
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.txtNumber.setEnabled(false);
    }
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    }
    
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	
    	this.actionAddNew.setVisible(false);
    	this.actionSubmit.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionEdit.setVisible(false);
    	
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	
    	this.actionFirst.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.menuBiz.setVisible(false);
    	this.menuEdit.setVisible(false);
    	this.menuView.setVisible(false);
    	this.menuSubmitOption.setVisible(false);
    	
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
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}