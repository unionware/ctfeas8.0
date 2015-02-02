package com.kingdee.eas.ma.nbudget.client;

import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.*;
import com.kingdee.eas.basedata.centralpolicy.*;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;

public class BgFormCourseListUICTEx extends BgFormCourseListUI
{

    public BgFormCourseListUICTEx()
        throws Exception
    {
    }

    public void onLoad()
        throws Exception
    {
        CentralpolicyCollection coll = null;
        EntityViewInfo viewinfo = new EntityViewInfo();
        FilterItemInfo itemInfo = new FilterItemInfo("BIMUDF0002", Boolean.valueOf(true), CompareType.EQUALS);
        FilterInfo filterInfo = new FilterInfo();
        filterInfo.getFilterItems().add(itemInfo);
        viewinfo.setFilter(filterInfo);
        coll = CentralpolicyFactory.getRemoteInstance().getCentralpolicyCollection(viewinfo);
        if(coll != null && coll.size() > 0)
        {
            String id = coll.get(0).getId().toString();
            UIContext uiContext = new UIContext(this);
            uiContext.put("ID", id);
            uiContext.put("isView", Boolean.valueOf(true));
            IUIWindow uiWindow = null;
            uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create("com.kingdee.eas.basedata.centralpolicy.client.CentralpolicyViewEditUI", uiContext, null, OprtState.VIEW);
            uiWindow.show();
        }
        super.onLoad();
    }
}