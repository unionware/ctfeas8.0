/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExcessSetInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ExcessSetListUI extends AbstractExcessSetListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExcessSetListUI.class);
    
    /**
     * output class constructor
     */
    public ExcessSetListUI() throws Exception
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
    	initUI();
    	this.tblMain.addKDTSelectListener(new KDTSelectListener() {
    	      public void tableSelectChanged(KDTSelectEvent e) {
    	        try {
    	        	_tblMain_tableSelectChanged(e);
    	        } catch (Exception exc) {
    	        	handUIException(exc);
    	        }
    	      }
    	});
    }
    
	private void initUI() {
		this.actionCancel.setVisible(true);
    	this.actionCancelCancel.setVisible(true);
    	
    	this.tblMain.getGroupManager().setGroup(true);
    	
    	this.tblMain.getColumn("id").setGroup(true);
    	this.tblMain.getColumn("year").setGroup(true);
    	this.tblMain.getColumn("rate").setGroup(true);
    	this.tblMain.getColumn("project.name").setGroup(true);
    	this.tblMain.getColumn("expenseType.name").setGroup(true);
    	this.tblMain.getColumn("isEnable").setGroup(true);
	}

	protected final void _tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
        int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
        if (activeRowIndex != -1) {
          if (this.tblMain.getRow(activeRowIndex).getCell("isEnable") != null) {
            boolean status = ((Boolean)this.tblMain.getCell(activeRowIndex, "isEnable").getValue()).booleanValue();
            this.actionCancelCancel.setEnabled(!(status));
            this.actionCancel.setEnabled(status);
          }
        } else {
          this.actionCancelCancel.setEnabled(false);
          this.actionCancel.setEnabled(false);
        }
     }
    
    public void actionCancel_actionPerformed(ActionEvent e)throws Exception{
	    setIsEnabled(false);
	  }

	public void actionCancelCancel_actionPerformed(ActionEvent e)throws Exception{
	    setIsEnabled(true);
	}
    
	protected void setIsEnabled(boolean flag) throws Exception {
	    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    if (activeRowIndex < 0) {
	      return;
	    }
	    String id = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
	    ExcessSetInfo info = new ExcessSetInfo();
	    info.setId(BOSUuid.read(id));
	    info.setIsEnable(flag);
	   
	    SelectorItemCollection sic = new SelectorItemCollection();

	    sic.add(new SelectorItemInfo("IsEnable"));
	    String message = null;
	    if (flag) {
	      getBizInterface().updatePartial(info, sic);
	      message = "启用成功";
	    }
	    else
	    {
	      getBizInterface().updatePartial(info, sic);
	      message = "禁用成功";
	    }

	    setMessageText(message);
	    showMessage();
	    this.tblMain.refresh();
	    loadFields();
	    this.actionCancel.setEnabled(false);
	    this.actionCancelCancel.setEnabled(false);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e)throws Exception{
		checkSelected();
	    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    if (activeRowIndex < 0) {
	      return;
	    }
	    String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
	    if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改", selectID)) {
	      return;
	    }
	    super.actionEdit_actionPerformed(e);
  }
	
	public void actionRemove_actionPerformed(ActionEvent e)throws Exception{
	     checkSelected();
	    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	   
	    String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
	    if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
	      return;
	    }
	    super.actionRemove_actionPerformed(e);
	}
	
	public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words, String selectID)throws Exception{
	    boolean flag = false;
	    FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("id", selectID));
	    filter.getFilterItems().add(new FilterItemInfo("IsEnable", Boolean.valueOf(true)));
	    if (getBizInterface().exists(filter)) {
	      MsgBox.showWarning("本记录已经启用，不能" + words + "!");
	      flag = true;
	    }
	    return flag; 
	}
	
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return ExcessSetFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return ExcessSetEditUI.class.getName();
	}

	 protected IObjectValue createNewData()
	 {
		 com.kingdee.eas.cp.bc.ExcessSetInfo objectValue = new com.kingdee.eas.cp.bc.ExcessSetInfo();		
	     return objectValue;
	 }
	 
}