/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.IReturnBill;
import com.kingdee.eas.cp.bc.ReturnBillInfo;
import com.kingdee.eas.cp.bc.ReturnStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

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

    @Override
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkCanEdit();
    	super.actionEdit_actionPerformed(e);
    }
    
    @Override
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	checkCanEditOrRemove("删除");
    	super.actionRemove_actionPerformed(e);
    }
    
    protected void checkCanEdit() throws Exception{
		 checkCanEditOrRemove("修改");
	}
    
    protected void checkCanEditOrRemove(String opr)throws Exception{
    String id = getSelectedKeyValue();
    if (id != null)
	    {
	      ReturnBillInfo billInfo = getReturnBillInfoStatus(id);
	      checkCanEditOrRemove(billInfo,opr);
	    }
  	}
    
    protected void checkCanEditOrRemove(ReturnBillInfo billInfo,String opr) throws Exception
	  {
	    if ((!ReturnStateEnum.TEMPSAVE.equals(billInfo.getBillState())) && (!ReturnStateEnum.SUBMITEDPAID.equals(billInfo.getBillState()))
	    		)
	    {
	      MsgBox.showInfo(this, "" + billInfo.getBillState().getAlias() + "单据不能"+opr);
	      SysUtil.abort();
	    }
	  }
    
    private ReturnBillInfo getReturnBillInfoStatus(String id) throws Exception {
    	return  ((IReturnBill) this.getBizInterface()).getReturnBillInfo("select billState where id ='" + id + "'");
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