/**
 * output package name
 */
package com.kingdee.eas.ma.bg.client;

import java.awt.Dimension;
import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.EASResource;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BudgetTemplateRelationListUI extends AbstractBudgetTemplateRelationListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BudgetTemplateRelationListUI.class);
    
    private final String resource ="com.kingdee.eas.ma.bg.client.BgResource";
    
    /**
     * output class constructor
     */
    public BudgetTemplateRelationListUI() throws Exception
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
    
    public void initUI(){
    	this.tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
    	
    	this.actionCancel.setVisible(true);
    	this.actionCancelCancel.setVisible(true);
    	
    	this.tblMain.getColumn("id").setGroup(true);
    	this.tblMain.getColumn("TemplateName.name").setGroup(true);
    	this.tblMain.getColumn("IsEnable").setGroup(true);
    	
    	this.tblMain.getColumn("number").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("name").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("simpleName").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("description").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("creator.number").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("lastUpdateUser.number").getStyleAttributes().setHided(true);  
    	this.tblMain.getColumn("creator.name").getStyleAttributes().setHided(true);  
    	this.tblMain.getColumn("createTime").getStyleAttributes().setHided(true);  
    	this.tblMain.getColumn("lastUpdateUser.name").getStyleAttributes().setHided(true);  
    	this.tblMain.getColumn("lastUpdateTime").getStyleAttributes().setHided(true);  
    	
    }
    
    @Override
    public void onShow() throws Exception {
    	super.onShow();
    }
    
    protected final void _tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	if (e == null) {
			return;
		}
		KDTSelectBlock selectBlock = e.getSelectBlock();
		if (selectBlock == null) {
			return;
		}
		
		int size = this.tblMain.getSelectManager().size();
		this.actionRemove.setEnabled(true);
		this.actionEdit.setEnabled(true);
		
		int ct = 0;
		int cct = 0;
		for (int i = 0; i < size; i++) {
			selectBlock = this.tblMain.getSelectManager().get(i);
			int j = selectBlock.getTop(); 
			for (int bottom = selectBlock.getBottom(); j <= bottom; j++) {
				IRow row = this.tblMain.getRow(j);
				if(row.getCell("IsEnable") != null){
					boolean status = ((Boolean)row.getCell("IsEnable").getValue()).booleanValue();
					if(status){
						this.actionCancelCancel.setEnabled(false);
						this.actionCancel.setEnabled(true);
						ct++;
					}else{
						this.actionCancelCancel.setEnabled(true);
						this.actionCancel.setEnabled(false);
						cct++;
					}
					if(status){
						this.actionRemove.setEnabled(!(status));
						this.actionEdit.setEnabled(false);
					}
				}
			}
			
		}
		if(ct>0 && cct>0){
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
		}
		
//        int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//        if (activeRowIndex != -1) {
//          if (this.tblMain.getRow(activeRowIndex).getCell("IsEnable") != null) {
//            boolean status = ((Boolean)this.tblMain.getCell(activeRowIndex, "IsEnable").getValue()).booleanValue();
//            this.actionCancelCancel.setEnabled(!(status));
//            this.actionCancel.setEnabled(status);
//            this.actionRemove.setEnabled(!(status));
//            this.actionEdit.setEnabled(!(status));
//          }
//        } else {
//          this.actionCancelCancel.setEnabled(false);
//          this.actionCancel.setEnabled(false);
//        }
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
	    BudgetTemplateRelationInfo info = new BudgetTemplateRelationInfo();
	    info.setId(BOSUuid.read(id));
	    info.setIsEnable(flag);
	   
	    SelectorItemCollection sic = new SelectorItemCollection();

	    sic.add(new SelectorItemInfo("IsEnable"));
	    String message = null;
	    if (flag) {
	      getBizInterface().updatePartial(info, sic);
	      message = EASResource.getString(resource, "Enabled_OK");
	    }
	    else
	    {
	      getBizInterface().updatePartial(info, sic);
	      message = EASResource.getString(resource, "DisEnabled_OK");
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
	
//	public void actionRemove_actionPerformed(ActionEvent e)throws Exception{
//	     checkSelected();
//	    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//	   
//	    String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
//	    if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
//	      return;
//	    }
//	    super.actionRemove_actionPerformed(e);
//	}
	
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
	
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.ma.bg.BudgetTemplateRelationFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo objectValue = new com.kingdee.eas.ma.bg.BudgetTemplateRelationInfo();
		
        return objectValue;
    }

}