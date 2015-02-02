package com.kingdee.eas.ma.budget.client;

import java.awt.Color;
import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.ma.budget.BgSchemeFactory;
import com.kingdee.eas.ma.budget.IBgScheme;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class BgSchemeTreeListUICTEx extends BgSchemeTreeListUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7573471266388009920L;

	public BgSchemeTreeListUICTEx() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		btnCopy.setIcon(EASResource.getIcon("imgTbtn_copy"));
	}
	
	@Override
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeOrgUnit.getLastSelectedPathComponent();
		if(node!=null && ((OrgStructureInfo)node.getUserObject()).getUnit()!=null && "00000000-0000-0000-0000-000000000000CCE7AED4".equals(((OrgStructureInfo)node.getUserObject()).getUnit().getId().toString())){
			String selectId = getSelectedKeyValue();
			
			/*Map map = getSelectedIdNumber();

			if (selectId != null) {
				if(map.get(selectId)!=null){
					if(BgSchemeFactory.getRemoteInstance().exists("where number='"+"Copy_"+map.get(selectId)+"'")){
						MsgBox.showInfo("预算方案编码不允许重复");
						SysUtil.abort();
					}
				}
				
				BgSchemeFactory.getRemoteInstance().copyScheme(BOSUuid.read(selectId));
							
			}*/
			if(selectId!=null && !"".equals(selectId)){
				UIContext uiContext = new UIContext(this);
				uiContext.put("SourceID", selectId);
	    		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BgSchemeCopyUI.class.getName(),uiContext);
	    		window.show();
			}
			
			refreshList();
		}else{
			MsgBox.showInfo("非集团组织，不能做该操作！");
			SysUtil.abort();
		}
	}
	
	@Override
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		int selectRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (this.tblMain.getRow(selectRowIndex).getCell("isCopyScheme") != null) {
			 Boolean isCopyScheme = (Boolean)this.tblMain.getRow(selectRowIndex).getCell("isCopyScheme").getValue();
			 if (isCopyScheme.equals(Boolean.TRUE)) {
				 this.btnDelCopyScheme.setVisible(true);
				 this.btnDelCopyScheme.setEnabled(true);
				 this.btnPatchDelCopyForm.setVisible(true);
				 this.btnPatchDelCopyForm.setEnabled(true);
				 this.tblMain.getRow(selectRowIndex).getStyleAttributes().setBackground(new Color(230, 230, 230));
			 }else{
				 this.btnDelCopyScheme.setVisible(false);
				 this.btnDelCopyScheme.setEnabled(false);
				 this.btnPatchDelCopyForm.setVisible(false);
				 this.btnPatchDelCopyForm.setEnabled(false);
			 }
		}
	}
	
	@Override
	public void actionDelCopyScheme_actionPerformed(ActionEvent e)
			throws Exception {
		if(MsgBox.isYes(MsgBox.showConfirm2(this, "该预算方案为复制的预算方案，删除会同时将该预算方案对应的所有预算表同步删除，是否确认删除？"))){
			checkSelected();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeOrgUnit.getLastSelectedPathComponent();
			if(node!=null && ((OrgStructureInfo)node.getUserObject()).getUnit()!=null && "00000000-0000-0000-0000-000000000000CCE7AED4".equals(((OrgStructureInfo)node.getUserObject()).getUnit().getId().toString())){
				String id = getSelectedKeyValue();
				if (id == null) {
					return;
				}
				IBgScheme iBgScheme = BgSchemeFactory.getRemoteInstance();
			}else{
				MsgBox.showInfo("非集团组织，不能做该操作！");
				SysUtil.abort();
			}
		}
	}
	
	@Override
	public void actionPatchDelCopyForm_actionPerformed(ActionEvent e)
			throws Exception {
	}
}
