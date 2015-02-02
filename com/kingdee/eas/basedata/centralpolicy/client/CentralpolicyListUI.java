/**
 * output package name
 */
package com.kingdee.eas.basedata.centralpolicy.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.EASResource;
import com.kingdee.eas.basedata.centralpolicy.CentralpolicyInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CentralpolicyListUI extends AbstractCentralpolicyListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CentralpolicyListUI.class);
	private final String resource ="com.kingdee.eas.ma.bg.client.BgResource";
	/**
	 * output class constructor
	 */
	public CentralpolicyListUI() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		this.btnCancel.setEnabled(true);
		this.btnCancel.setVisible(true);
		this.btnCancelCancel.setEnabled(true);
		this.btnCancelCancel.setVisible(true);
		super.onLoad();
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
				if(row.getCell("BIMUDF0002") != null){
					boolean status = ((Boolean)row.getCell("BIMUDF0002").getValue()).booleanValue();
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
//          if (this.tblMain.getRow(activeRowIndex).getCell("BIMUDF0002") != null) {
//            boolean status = ((Boolean)this.tblMain.getCell(activeRowIndex, "BIMUDF0002").getValue()).booleanValue();
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
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		 setIsEnabled(false);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		 
		 FilterInfo filter = new FilterInfo();
		 filter.getFilterItems().add(new FilterItemInfo("BIMUDF0002", Boolean.valueOf(true)));
		 if(getBizInterface().exists(filter)){
	    		MsgBox.showInfo("只能启用一条中央政策！");
		 }else{
			 setIsEnabled(true);
		 }

	}
	protected void setIsEnabled(boolean flag) throws Exception {
	    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    if (activeRowIndex < 0) {
	      return;
	    }
	    String id = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
	    CentralpolicyInfo info = new CentralpolicyInfo();
	    info.setId(BOSUuid.read(id));
	    info.setBIMUDF0002(flag);
	   
	    SelectorItemCollection sic = new SelectorItemCollection();

	    sic.add(new SelectorItemInfo("BIMUDF0002"));
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
	    this.btnCancelCancel.setEnabled(!(flag));
        this.btnCancel.setEnabled(flag);
	}

	private IRow checkSelected(ActionEvent e) {
		IRow row = null;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定一条记录
			MsgBox.showWarning("请先指定一条记录!");
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager()
					.getActiveRowIndex());
		}
		return row;
	}
	@Override
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
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
//	@Override
//	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		 checkSelected();
//		    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//		   
//		    String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
//		    if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
//		      return;
//		    }
//		super.actionRemove_actionPerformed(e);
//	}
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
	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.basedata.centralpolicy.CentralpolicyFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.basedata.centralpolicy.CentralpolicyInfo objectValue = new com.kingdee.eas.basedata.centralpolicy.CentralpolicyInfo();

		return objectValue;
	}

}