/**
 * output package name
 */
package com.kingdee.eas.ma.bg.client;

import java.awt.event.*;

import org.apache.derby.tools.sysinfo;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.EASResource;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgTemplateFactory;
import com.kingdee.eas.ma.budget.BgTemplateInfo;
import com.kingdee.eas.util.StringUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class BudgetTemplateRelationEditUI extends AbstractBudgetTemplateRelationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BudgetTemplateRelationEditUI.class);
    
    static boolean canCancel = true;
    private final String resource ="com.kingdee.eas.ma.bg.client.BgResource";
    
    private KDTEditAdapter entryEditListener;
    private DataChangeListener templateNameChangedListener;
    
    final KDBizPromptBox kdtEntry_RefTemplet__PromptBox = new KDBizPromptBox();
    
    /**
     * output class constructor
     */
    public BudgetTemplateRelationEditUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	prmtTemplateName.setRequired(true);
    	chkIsEnable.setEnabled(false);
    	
    	String oprtState2 = getOprtState();
    	if (this.editData!= null)
        {
          boolean isEnabled;
          if ("VIEW".equals(oprtState2)) {
            isEnabled = editData.isIsEnable();
            this.actionCancel.setEnabled(isEnabled);
            this.actionCancelCancel.setEnabled(!(isEnabled));
            
          } else if ("ADDNEW".equals(oprtState2)) {
            this.actionCancel.setEnabled(false);
            this.actionCancelCancel.setEnabled(false);
          } else {
            isEnabled = this.editData.isIsEnable();
            this.actionCancel.setEnabled(isEnabled);
            this.actionCancelCancel.setEnabled(!(isEnabled));
          }
        }
    	
    	if("EDIT".equals(oprtState2)){
    		BgTemplateInfo info = this.editData.getTemplateName();
    	    setRefTempletView(getBgTempleateInfo(info.getId().toString()));
    	}
    	
    	initUI();
    	
    	kdtEntry_RefTemplet__PromptBox.setQueryInfo("com.kingdee.eas.ma.budget.BgTempRelationF7Query");
    	kdtEntry_RefTemplet__PromptBox.setVisible(true);
    	kdtEntry_RefTemplet__PromptBox.setEditable(true);
    	kdtEntry_RefTemplet__PromptBox.setDisplayFormat("$number$");
    	kdtEntry_RefTemplet__PromptBox.setEditFormat("$number$");
    	kdtEntry_RefTemplet__PromptBox.setCommitFormat("$number$");
    	kdtEntry_RefTemplet__PromptBox.setRequired(true);
        KDTDefaultCellEditor kdtEntry_RefTemplet__ellEditor = new KDTDefaultCellEditor(kdtEntry_RefTemplet__PromptBox);
        this.kdtTemplateEntry.getColumn("ReferencedTemplet").setEditor(kdtEntry_RefTemplet__ellEditor);
        
        //设置过滤条件-查询出新增模板
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("id = rootId"));
        view.setFilter(filter);
        prmtTemplateName.setEntityViewInfo(view);
        
    }
    
    private void initUI() {
    	actionCopy.setVisible(false);
    	actionSave.setVisible(false);
    	actionFirst.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionLast.setVisible(false);
    	
    	kDLabelContainer1.setVisible(false);
    	kDLabelContainer2.setVisible(false);
    	kDLabelContainer3.setVisible(false);
    	kDLabelContainer4.setVisible(false);
	}
    
    protected void initListener() {
		super.initListener();
	
		templateNameChangedListener = new DataChangeListener(){
			@Override
			public void dataChanged(DataChangeEvent arg0) {
				prmtTempChanged(arg0);
			}};
			
		prmtTemplateName.addDataChangeListener(templateNameChangedListener);
			
		if(entryEditListener==null){
    		entryEditListener = new KDTEditAdapter(){
    			public void editStopped(KDTEditEvent e) {
        			entryEditStoped(e);
        		}
    			@Override
    			public void editStarting(KDTEditEvent e) {
    				entryEditStarting(e);
    			}
    		};
    	}
		
		kdtTemplateEntry.addKDTEditListener(entryEditListener);
		
	}

	protected void entryEditStarting(KDTEditEvent e) {
    	int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
    	if(prmtTemplateName.getValue()==null){
			kdtTemplateEntry.getCell(rowIndex, colIndex).setValue(null);
			MsgBox.showInfo(this, "请先选择模板！");
			abort();
		}
		
	}

	protected void prmtTempChanged(DataChangeEvent arg0) {
		BgTemplateInfo news = (BgTemplateInfo) arg0.getNewValue();
		BgTemplateInfo olds = (BgTemplateInfo) arg0.getOldValue();
    	if(news!=null && olds!=null){
			   if(news.getId().toString().equals(olds.getId().toString())){
				   return ;
			   }else{
				   int isOk= MsgBox.showConfirm2(this, "修改模板名称会删除所有分录数据，请确认！");
				   if(isOk == MsgBox.OK){
					   this.kdtTemplateEntry.removeRows();
					   if(news!=null)
						   news = getBgTempleateInfo(news.getId().toString());
				    	   setRefTempletView(news);
				   }else{
					   prmtTemplateName.removeDataChangeListener(templateNameChangedListener);
					   prmtTemplateName.setValue(olds);
					   setRefTempletView(getBgTempleateInfo(olds.getId().toString()));
					   prmtTemplateName.addDataChangeListener(templateNameChangedListener);
				   }
			   }
		}
    	if(news!=null && olds==null){
    		setRefTempletView(getBgTempleateInfo(news.getId().toString()));
    	}
	}

	private BgTemplateInfo getBgTempleateInfo(String id){
		BgTemplateInfo info = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		try {
			info = BgTemplateFactory.getRemoteInstance().getBgTemplateInfo(new ObjectUuidPK(id) ,sic);
		} catch (Exception err) {
			handleException(err);
		} 
		return info;
	}

	private void setRefTempletView(BgTemplateInfo bgTemInfo) {
		EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id = rootId"));
    	filter.getFilterItems().add(new FilterItemInfo("id",bgTemInfo.getId().toString(),CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",bgTemInfo.getOrgUnit().getId().toString()));
    	view.setFilter(filter);
    	kdtEntry_RefTemplet__PromptBox.setEntityViewInfo(view);
	}

	protected void entryEditStoped(KDTEditEvent e) {
    	int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		BgTemplateInfo newInfo = null;
		BgTemplateInfo oldInfo = null;
		BgTemplateInfo compInfo = null;
		if("ReferencedTemplet".equals(kdtTemplateEntry.getColumnKey(colIndex))){
			if(e.getValue()==null){
				return ;
			}
			newInfo = (BgTemplateInfo) e.getValue();
			oldInfo = (BgTemplateInfo) e.getOldValue();
			
			if((oldInfo != newInfo)){
				   for(int i =0;i<this.kdtTemplateEntry.getRowCount();i++){
						if(i != rowIndex){
							compInfo = (BgTemplateInfo) kdtTemplateEntry.getCell(i, colIndex).getValue();
				            if(compInfo !=null){
				            	if(newInfo.getId().toString().equals(compInfo.getId().toString())){
				            		kdtTemplateEntry.getCell(rowIndex, colIndex).setValue(null);
									MsgBox.showInfo(this, "关联引用模板重复,请重新选择！");
								}
				            }
						}
				   }
	           }	
	      }
	}

	@Override
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	if(prmtTemplateName.getValue()==null){
    		prmtTemplateName.requestFocus();
    		MsgBox.showInfo(this, "模板名称不能为空！");
    		abort();
    	}
    	for(int i =0;i<this.kdtTemplateEntry.getRowCount();i++){
    		if(this.kdtTemplateEntry.getCell(i, "ReferencedTemplet").getValue()==null){
    			MsgBox.showInfo(this, "关联引用模板不能为空，请选择！");
				abort();
    		}
    	}
    	if(!(this.kdtTemplateEntry.getRowCount()>0)){
    		MsgBox.showInfo(this, "至少得有一条分录！");
			abort();
    	}
    }
    
    @Override
    protected void doBeforeSubmit(ActionEvent e) throws Exception {
    	super.doBeforeSubmit(e);
    	 BgTemplateInfo templateInfo = (BgTemplateInfo) prmtTemplateName.getValue();
    	 String str = templateInfo.getId().toString();
    	 FilterInfo filter = new FilterInfo();
    	 filter.getFilterItems().add(new FilterItemInfo("TemplateName.id",str));
    	 if(!"ADDNEW".equals(getOprtState())){
    		 filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	 }
    	 if(getBizInterface().exists(filter)){
    		 MsgBox.showInfo(this, "模板已经存在关系设置！");
    		 abort();
    	 }
    }
    
	/**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
      setIsEnable(false);
    }

    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
      setIsEnable(true);
    }

    protected void setIsEnable(boolean flag) throws Exception {
      
      
      editData.setIsEnable(flag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("IsEnable"));
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
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

      String selectID = this.editData.getId().toString();
      if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改", selectID)) {
        return;
      }
      setRefTempletView(getBgTempleateInfo(this.editData.getTemplateName().getId().toString()));
      super.actionEdit_actionPerformed(e);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

      String selectID = this.editData.getId().toString();
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
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}