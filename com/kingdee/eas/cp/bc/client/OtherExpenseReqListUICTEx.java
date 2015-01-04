package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.IOtherExpenseBill;
import com.kingdee.eas.cp.bc.OtherExpenseBillFactory;
import com.kingdee.eas.cp.bc.OtherExpenseBillInfo;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.sm.pur.IPurContract;
import com.kingdee.eas.scm.sm.pur.PurContractCollection;
import com.kingdee.eas.scm.sm.pur.PurContractFactory;
import com.kingdee.eas.scm.sm.pur.PurContractInfo;

public class OtherExpenseReqListUICTEx extends OtherExpenseReqListUI {

	public OtherExpenseReqListUICTEx() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		if (!this.isShowEntry) {
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.project.name"));
			this.tblMain.removeColumn(this.tblMain.getColumnIndex("entries.costedDept.name"));
		}
	}
	
	/**
	 * 新增关闭费用申请单关闭关联的采购合同
	 */
	@Override
	public void actionCloseBill_actionPerformed(ActionEvent e) throws Exception {
		super.actionCloseBill_actionPerformed(e);
		
		List<Object> idList = getSelectIdList();
		IOtherExpenseBill iOtherExpense = null;
		IPurContract iPurContract = null;
		PurContractCollection purContractCol = null;
		PurContractInfo purContractInfo = null;
        OtherExpenseBillInfo otherExpenseInfo = null;
		String tempId = null;
		iOtherExpense = OtherExpenseBillFactory.getRemoteInstance();
		iPurContract = PurContractFactory.getRemoteInstance();
		Iterator<Object> ite = idList.iterator();
		while (ite.hasNext()) {
			 tempId = (String)ite.next();
		     otherExpenseInfo = iOtherExpense.getOtherExpenseBillInfo(new ObjectUuidPK(BOSUuid.read(tempId)));
		     if ((StateEnum.CHECKED.equals(otherExpenseInfo.getState())) && (BizCollUtil.checkForBillClose(otherExpenseInfo)))
	         {
		    	 EntityViewInfo view = new EntityViewInfo();
		    	 FilterInfo filter = new FilterInfo();
		    	 filter.getFilterItems().add(new FilterItemInfo("sourceBillId",tempId));
		    	 view.setFilter(filter);
		    	 purContractCol = iPurContract.getPurContractCollection(view);
		    	 if(purContractCol!=null && purContractCol.size()>0){
		    		 for (int i = 0; i < purContractCol.size(); i++) {
		    			 purContractInfo = purContractCol.get(i);
		    			 purContractInfo.setBaseStatus(BillBaseStatusEnum.CLOSED);
		    			 iPurContract.updatePartial(purContractInfo, getPurContractCloseSic());
					}
		    	 }
		    	 
	         }
		}
	}

	private SelectorItemCollection getPurContractCloseSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("baseStatus"));
		return sic;
	}
	

}
