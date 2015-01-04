/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.HierarchyGetPersonFacadeFactory;

/**
 * output class name
 */
public class PositionLevelListUI extends AbstractPositionLevelListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PositionLevelListUI.class);
    
    /**
     * output class constructor
     */
    public PositionLevelListUI() throws Exception
    {
    	super();
    	HierarchyGetPersonFacadeFactory.getRemoteInstance().getPersonByPPlH("103815", "ZJ40", "H001");
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
    }

	/**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.PositionLevelFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.PositionLevelTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.cp.bc.client.PositionLevelTreeEditUI.class.getName();
    }

    /**
     * output getQueryFieldName method
     */
    protected String getQueryFieldName()
    {
        return "treeid.id";
    }

    /**
     * output getKeyFieldName method
     */
    protected String getKeyFieldName()
    {
        return "id";
    }

    /**
     * output getRootName method
     */
    protected String getRootName()
    {
        return "Ö°¼¶";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.PositionLevelInfo objectValue = new com.kingdee.eas.cp.bc.PositionLevelInfo();
		
        return objectValue;
    }

}