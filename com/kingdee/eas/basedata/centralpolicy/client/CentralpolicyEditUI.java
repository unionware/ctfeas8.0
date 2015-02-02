/**
 * output package name
 */
package com.kingdee.eas.basedata.centralpolicy.client;

import java.awt.event.ActionEvent;

import org.apache.derby.tools.sysinfo;
import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.EASResource;
import com.kingdee.eas.basedata.centralpolicy.CentralpolicyCollection;
import com.kingdee.eas.basedata.centralpolicy.CentralpolicyFactory;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CentralpolicyEditUI extends AbstractCentralpolicyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CentralpolicyEditUI.class);
    private final String resource ="com.kingdee.eas.basedata.centralpolicy.client.CentralpolicyResource";
    /**
     * output class constructor
     */
    public CentralpolicyEditUI() throws Exception
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
		// TODO Auto-generated method stub
		super.onLoad();
//		this.btnAddNew.setEnabled(false);
//		this.btnAddNew.setVisible(false);
		this.kdtxtContent.setRequired(true);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		this.kdtxtContent.setLineWrap(true);
		String oprtState = getOprtState();
		if (this.editData!= null)
        {
          boolean isEnabled;
          if ("VIEW".equals(oprtState)) {
            isEnabled = editData.isBIMUDF0002();
            this.actionCancel.setEnabled(isEnabled);
            this.actionCancelCancel.setEnabled(!(isEnabled));
            this.txtName.setEditable(false);
            this.kDLabelContainer5.setEnabled(false);
            this.kDLabelContainer2.setEnabled(false);
            this.kdtxtContent.setEnabled(false);
            
            
          } else if ("ADDNEW".equals(oprtState)) {
            this.actionCancel.setEnabled(false);
            this.actionCancelCancel.setEnabled(false);
          } else {
            isEnabled = this.editData.isBIMUDF0002();
            this.actionCancel.setEnabled(isEnabled);
            this.actionCancelCancel.setEnabled(!(isEnabled));
          }
        }
		
		
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    @Override
    public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
    	// TODO Auto-generated method stub
    	if(this.txtNumber.getText().equals("")){
    		MsgBox.showInfo("中央政策编码不能为空！");
    		abort();
    	}
    	if(this.txtName.getText().equals("")){
    		MsgBox.showInfo("中央政策名称不能为空！");
    		abort();
    	}
    	if(this.kdtxtContent.getText().equals("")){
    		MsgBox.showInfo("中央政策内容不能为空！");
    		abort();
    	}
    	String oprtState = getOprtState();
    	String Number = this.txtNumber.getText();
    	if ("ADDNEW".equals(oprtState)){
    		FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("number", Number));
        	if(getBizInterface().exists(filter)){
        		MsgBox.showInfo("中央政策编码重复!");
        	}else{
        		super.actionSubmit_actionPerformed(arg0);
        	}
    	}else{
    		String ID = editData.getId().toString();
    		CentralpolicyCollection coll = null;
    		EntityViewInfo viewinfo = new EntityViewInfo();
    		FilterItemInfo itemInfo = new FilterItemInfo("number",Number,CompareType.EQUALS);
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(itemInfo);
    		viewinfo.setFilter(filter);
    		coll = CentralpolicyFactory.getRemoteInstance().getCentralpolicyCollection(viewinfo);
    		if(coll.size()>=1&&!ID.equals(coll.get(0).getId().toString())){
    			MsgBox.showInfo("中央政策编码重复!");
    		}else{
    			super.actionSubmit_actionPerformed(arg0);
    		}
    	}
    	
    }
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.verifyInput(e);
    	
    }
    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
        
    }

   

    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	 setIsEnable(false);
    }
    

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("BIMUDF0002", Boolean.valueOf(true)));
    	if(getBizInterface().exists(filter)){
    		MsgBox.showInfo("只能启用一条中央政策！");
    	}else{
    		setIsEnable(true);
    	}
        
    }
    protected void setIsEnable(boolean flag) throws Exception {
        
        
        editData.setBIMUDF0002(flag);
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("BIMUDF0002"));
        String message = null;
        if (flag) {
          getBizInterface().updatePartial(editData, sic);
          message = EASResource.getString(this.resource, "Enabled_OK");
        } else {
          getBizInterface().updatePartial(editData, sic);
          message = EASResource.getString(this.resource, "DisEnabled_OK");
        }
        setMessageText(message);
        showMessage();

        setDataObject(getValue(new ObjectUuidPK(this.editData.getId())));
        loadFields();
        setSave(true);
        setSaved(true);

        this.btnCancelCancel.setEnabled(!(flag));
        this.btnCancel.setEnabled(flag);
      }
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words, String selectID)throws Exception{
	    boolean flag = false;
	    FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("id", selectID));
	    filter.getFilterItems().add(new FilterItemInfo("BIMUDF0002", Boolean.valueOf(true)));
	    if (getBizInterface().exists(filter)) {
	      MsgBox.showWarning("本记录已经启用，不能" + words + "!");
	      flag = true;
	    }
	    return flag;
    }
    
    @Override
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	String selectID = this.editData.getId().toString();
        if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改", selectID)) {
          return;
        }
    	super.actionEdit_actionPerformed(e);
    }
    @Override
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	String selectID = this.editData.getId().toString();
        if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
          return;
        }
    	super.actionRemove_actionPerformed(e);
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.basedata.centralpolicy.CentralpolicyFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.basedata.centralpolicy.CentralpolicyInfo objectValue = new com.kingdee.eas.basedata.centralpolicy.CentralpolicyInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}