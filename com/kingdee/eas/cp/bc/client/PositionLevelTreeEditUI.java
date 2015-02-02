/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.cp.bc.IPositionLevel;
import com.kingdee.eas.cp.bc.IPositionLevelTree;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class PositionLevelTreeEditUI extends AbstractPositionLevelTreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PositionLevelTreeEditUI.class);
    
    /**
     * output class constructor
     */
    public PositionLevelTreeEditUI() throws Exception
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

    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionSave.setVisible(false);
    	actionCopy.setVisible(false);
    	actionCancel.setVisible(false);
    	actionCancelCancel.setVisible(false);
    	txtNumber.setRequired(true);
    	txtName.setRequired(true);
    }
    
    @Override
	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		super.doBeforeSubmit(e);
		IPositionLevelTree ie=(IPositionLevelTree) getBizInterface();
		ie.checkNumberBlank(this.editData);
		ie.checkNameBlank(this.editData);
		ie.checkNumberDup(this.editData);
		ie.checkNameDup(this.editData);
	}
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.PositionLevelTreeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.PositionLevelTreeInfo objectValue = new com.kingdee.eas.cp.bc.PositionLevelTreeInfo();
		
        return objectValue;
    }

}