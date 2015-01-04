/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.cp.bc.BaseDataEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BaseDataMatchListUI extends AbstractBaseDataMatchListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BaseDataMatchListUI.class);
    
    private Map<BaseDataEnum,String> entryEASTitleNameMap;
    private Map<BaseDataEnum,String> entryK3TitleNameMap;
    
    private BaseDataEnum types;
    /**
     * output class constructor
     */
    public BaseDataMatchListUI() throws Exception
    {
        super();
        initEntryTitle();
        
    }

    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	setEntryTitle(types);
    }
    
    protected void initTypes(BaseDataEnum types){
    	this.types =types; 
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionWorkFlowG.setVisible(false);
    	this.actionAuditResult.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionRemove.setVisible(false);
    	
    }
    
    
    private void initEntryTitle(){
    	entryEASTitleNameMap = new HashMap<BaseDataEnum,String>();
    	entryEASTitleNameMap.put(BaseDataEnum.ACCOUNT, "��Ŀ����");
    	entryEASTitleNameMap.put(BaseDataEnum.ORGUNIT, "������֯����");
    	entryEASTitleNameMap.put(BaseDataEnum.DEPARTMENT, "�ɱ����ı���");
    	entryEASTitleNameMap.put(BaseDataEnum.PERSON, "ְԱ����");
    	entryEASTitleNameMap.put(BaseDataEnum.PROJECT, "��Ŀ����");
    	
    	
    	entryK3TitleNameMap.put(BaseDataEnum.ACCOUNT, "��Ŀ����");
    	entryK3TitleNameMap.put(BaseDataEnum.ORGUNIT, "��֯��������");
    	entryK3TitleNameMap.put(BaseDataEnum.DEPARTMENT, "���ű���");
    	entryK3TitleNameMap.put(BaseDataEnum.PERSON, "ְԱ����");
    	entryK3TitleNameMap.put(BaseDataEnum.PROJECT, "��Ŀ����");
    }
    
    
    private void setEntryTitle(BaseDataEnum bdEnum){
    	if(bdEnum==null){
    		return ;
    	}
    	tblMain.getHeadRow(0).getCell("entrys.easNum").setValue(entryEASTitleNameMap.get(bdEnum));
    	tblMain.getHeadRow(0).getCell("entrys.k3Num").setValue(entryK3TitleNameMap.get(bdEnum));
    }
    
    
    
    @Override
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionAddNew_actionPerformed(e);
    }
    
    @Override
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionRemove_actionPerformed(e);
    }
    
    

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.BaseDataMatchFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.BaseDataMatchInfo objectValue = new com.kingdee.eas.cp.bc.BaseDataMatchInfo();
		
        return objectValue;
    }

}