/**
 * output package name
 */
package com.kingdee.eas.ma.budget.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.ma.budget.BgSchemeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BgSchemeCopyUI extends AbstractBgSchemeCopyUI
{
    private static final Logger logger = CoreUIObject.getLogger(BgSchemeCopyUI.class);
    
    /**
     * output class constructor
     */
    public BgSchemeCopyUI() throws Exception
    {
        super();
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(txtNumber.getText()==null || "".equals(txtNumber.getText())){
    		MsgBox.showWarning("Ŀ��Ԥ�㷽�����벻��Ϊ��");
    		SysUtil.abort();
    	}
    	if(txtName.getText()==null || "".equals(txtName.getText())){
    		MsgBox.showWarning("Ŀ��Ԥ�㷽�����Ʋ���Ϊ��");
    		SysUtil.abort();
    	}
		if(txtPrefix.getText()==null || "".equals(txtPrefix.getText())){
			MsgBox.showWarning("Ŀ��Ԥ���ǰ׺����Ϊ��");
    		SysUtil.abort();
		}
		
		String id = (String) getUIContext().get("SourceID");
		if(id!=null && !"".equals(id)){
			if(BgSchemeFactory.getRemoteInstance().exists("where number='"+txtNumber.getText()+"'")){
				MsgBox.showInfo("Ԥ�㷽�����벻�����ظ�");
				SysUtil.abort();
			}
			if(BgSchemeFactory.getRemoteInstance().exists("where name='"+txtName.getText()+"'")){
				MsgBox.showInfo("Ԥ�㷽�����Ʋ������ظ�");
				SysUtil.abort();
			}
			
			Map map = new HashMap();
			map.put("SourceID", id);
			map.put("number", txtNumber.getText());
			map.put("name", txtName.getText());
			map.put("prefix", txtPrefix.getText());
			
			BgSchemeFactory.getRemoteInstance().copyScheme(map);
		}
		
		getUIWindow().close();
		
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	getUIWindow().close(); 
    }

}