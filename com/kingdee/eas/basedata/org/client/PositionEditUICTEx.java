package com.kingdee.eas.basedata.org.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.util.client.MsgBox;

public class PositionEditUICTEx extends PositionEditUI {

	public PositionEditUICTEx() throws Exception {
		super();
	}

	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(prmtPositionLevel.getValue()==null){
			MsgBox.showInfo("职级不能为空！");
			abort();
		}
	}
	
	@Override
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("positionLevel.id"));
    	sic.add(new SelectorItemInfo("positionLevel.number"));
    	sic.add(new SelectorItemInfo("positionLevel.name"));
		return sic;
	}
}
