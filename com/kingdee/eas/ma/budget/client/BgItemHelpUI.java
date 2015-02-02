/**
 * output package name
 */
package com.kingdee.eas.ma.budget.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fm.nt.OprtEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgItemFactory;

/**
 * output class name
 */
public class BgItemHelpUI extends AbstractBgItemHelpUI
{
    private static final Logger logger = CoreUIObject.getLogger(BgItemHelpUI.class);
    
    /**
     * output class constructor
     */
    public BgItemHelpUI() throws Exception
    {
        super();
        
    	this.toolBar.setVisible(false);
    	this.toolBar.setEnabled(false);
        
    	this.menuBar.setVisible(false);
    	this.menuBar.setEnabled(false);
    	
    	txtHelp.setSelectAllOnFocus(false);
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	String help = (String) getUIContext().get("porjectHelp");
    	
    	txtHelp.setText(help);
    }

	@Override
	protected IObjectValue createNewData() {
		return null;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return BgItemFactory.getRemoteInstance();
	}

}