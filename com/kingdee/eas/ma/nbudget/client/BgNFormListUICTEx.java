package com.kingdee.eas.ma.nbudget.client;


import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.centralpolicy.CentralpolicyCollection;
import com.kingdee.eas.basedata.centralpolicy.CentralpolicyFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

public class BgNFormListUICTEx extends BgNFormListUI {

	public BgNFormListUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		CentralpolicyCollection coll = null;
		EntityViewInfo viewinfo = new EntityViewInfo();
		FilterItemInfo itemInfo = new FilterItemInfo("BIMUDF0002", true,
				CompareType.EQUALS);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(itemInfo);
		viewinfo.setFilter(filterInfo);
		coll = CentralpolicyFactory.getRemoteInstance()
				.getCentralpolicyCollection(viewinfo);
		if (coll != null && coll.size() > 0) {
			String id = coll.get(0).getId().toString();
			UIContext uiContext = new UIContext(this);
    		uiContext.put("ID", id);
    		uiContext.put("isView", true);
    		IUIWindow uiWindow = null;
    		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create("com.kingdee.eas.basedata.centralpolicy.client.CentralpolicyViewEditUI", uiContext, null, OprtState.VIEW);
    		uiWindow.show(); 
			
		}
		super.onLoad();
	}

}
