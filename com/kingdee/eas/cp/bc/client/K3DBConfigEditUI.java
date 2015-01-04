/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.cp.bc.K3DBConfigCollection;
import com.kingdee.eas.cp.bc.K3DBConfigFactory;

/**
 * output class name
 */
public class K3DBConfigEditUI extends AbstractK3DBConfigEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(K3DBConfigEditUI.class);
    
    /**
     * output class constructor
     */
    public K3DBConfigEditUI() throws Exception
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
    
    @Override
    public void onLoad() throws Exception {
    	initContextId();
    	super.onLoad();
    }
    
    private void initContextId() throws BOSException{
    	K3DBConfigCollection coll =   K3DBConfigFactory.getRemoteInstance().getK3DBConfigCollection();
    	if(coll==null){
    		throw new BOSException("未初始化K3数据库配置信息");
    	}
    	this.getUIContext().put("ID", coll.get(0).getId().toString());
    }
    
    @Override
    public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionEdit_actionPerformed(arg0);
    }
    
    @Override
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionSave_actionPerformed(e);
    }
    
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.verifyInput(e);
    }
    
    @Override
    protected void beforeStoreFields(ActionEvent arg0) throws Exception {
    	// TODO Auto-generated method stub
    	super.beforeStoreFields(arg0);
    }

    
    @Override
    protected void initWorkButton() {
    	super.initWorkButton();
    	
    	actionAddNew.setVisible(false);
    	actionSubmit.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionEdit.setVisible(false);
    	
    	actionPrint.setVisible(false);
    	actionPrintPreview.setVisible(false);
    	
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
        return com.kingdee.eas.cp.bc.K3DBConfigFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.K3DBConfigInfo objectValue = new com.kingdee.eas.cp.bc.K3DBConfigInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}