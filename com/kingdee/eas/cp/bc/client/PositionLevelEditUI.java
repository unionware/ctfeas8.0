/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.HierarchyGetPersonFacadeFactory;
import com.kingdee.eas.cp.bc.IPositionLevel;

/**
 * output class name
 */
public class PositionLevelEditUI extends AbstractPositionLevelEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PositionLevelEditUI.class);
    
    /**
     * output class constructor
     */
    public PositionLevelEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
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
    	initUI();
    }
    
	private void initUI() {
		actionSave.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
	}
	
	@Override
	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		super.doBeforeSubmit(e);
		IPositionLevel ie=(IPositionLevel) getBizInterface();
		ie.checkNumberBlank(this.editData);
		ie.checkNameBlank(this.editData);
		ie.checkNumberDup(this.editData);
//		ie.checkNameDup(this.editData);
	}

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.PositionLevelFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.cp.bc.PositionLevelTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.PositionLevelInfo objectValue = new com.kingdee.eas.cp.bc.PositionLevelInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}