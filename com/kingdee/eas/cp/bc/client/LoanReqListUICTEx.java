package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.EASResource;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade;
import com.kingdee.eas.cp.bc.IDailyLoanBill;
import com.kingdee.eas.cp.bc.IReturnBill;
import com.kingdee.eas.cp.bc.ReturnBillCollection;
import com.kingdee.eas.cp.bc.ReturnBillFactory;
import com.kingdee.eas.cp.bc.ReturnBillInfo;
import com.kingdee.eas.util.client.MsgBox;

public class LoanReqListUICTEx extends LoanReqListUI {

	public LoanReqListUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		btnReturn.setEnabled(true);
		btnComfirmReturn.setEnabled(true);
	}
	
	@Override
    public void actionReturn_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    if (activeRowIndex < 0) {
	      return;
	    }
        
	    String billID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
        String LoanNumber = this.tblMain.getCell(activeRowIndex, "number").getValue().toString();
        
        DailyLoanBillInfo billInfo = getBillInfoById(billID);
        ReturnBillInfo rInfo = getBillInfoByNumber(LoanNumber);
        
        UIContext uiContext = new UIContext(this);
		uiContext.put("billInfo", billInfo);
		IUIWindow uiWindow = null;
        if(rInfo==null){
        	uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
    		create(ReturnBillEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
        }else{
        	uiContext.put("ID", rInfo.getId().toString());
        	uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
    		create(ReturnBillEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        }
		uiWindow.show();
    }
    
    private DailyLoanBillInfo getBillInfoById(String billID) throws EASBizException, BOSException, Exception {
    	DailyLoanBillInfo info = null;
    	SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amountApproved"));
        sic.add(new SelectorItemInfo("amountBalance"));
        sic.add(new SelectorItemInfo("applier.*"));
        info = ((IDailyLoanBill)getBizInterface()).getDailyLoanBillInfo(new ObjectUuidPK(billID),sic);
		return info;
	}

	@Override
    public void actionComfirmReturn_actionPerformed(ActionEvent e)
    		throws Exception {
    	int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    if (activeRowIndex < 0) {
	      return;
	    }
	    String billID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
	    String LoanNumber = this.tblMain.getCell(activeRowIndex, "number").getValue().toString();
	    
	    DailyLoanBillInfo billInfo = getBillInfoById(billID);
	    ReturnBillInfo rInfo = getBillInfoByNumber(LoanNumber);
	    
	    if(rInfo==null){
	    	MsgBox.showInfo("数据有误，找不到对应的还款单！");
	    	abort();
	    }
	    
	    UIContext uiContext = new UIContext(this);
	    
		uiContext.put("billInfo", billInfo);
		uiContext.put("ID", rInfo.getId().toString());
		
		IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
		create(ReturnBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
	    
        
    }
    
	private ReturnBillInfo getBillInfoByNumber(String loanNumber) throws BOSException {
		IReturnBill returnBill = ReturnBillFactory.getRemoteInstance();
		ReturnBillInfo info = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("loanBillNumber",loanNumber));
		view.setFilter(filter);
		ReturnBillCollection col = returnBill.getReturnBillCollection(view);
		if(col!=null && col.size()>0){
			info = col.get(0);
		}
		return info;
	}

	@Override
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,
			EntityViewInfo arg1) {
		// TODO Auto-generated method stub
		IQueryExecutor iq =  super.getQueryExecutor(arg0, arg1);
		return iq;
	}
	
	
	@Override
	protected FilterInfo getDefaultFilterForQuery() {
		// TODO Auto-generated method stub
		return super.getDefaultFilterForQuery();
	}

//	@Override
//	public void actionPayOff_actionPerformed(ActionEvent arg0) throws Exception {
//		super.checkSelected();
//		ArrayList billIdlist = getSelectedIdValues();
//	    if (billIdlist.size() > 1) {
//	    	throw new BizCollException(BizCollException.CANNOT_MULTI_CHOICE);
//	    }
//		super.actionPayOff_actionPerformed(arg0);
//	}
	
	
	
//	public void actionPayOff_actionPerformed(ActionEvent e)
//    throws Exception
//  {
//    checkSelected();
//    ArrayList billIdlist = getSelectedIdValues();
//    if (billIdlist.size() > 1) {
//      throw new BizCollException(BizCollException.CANNOT_MULTI_CHOICE);
//    }
//
//    if (!this.isRelaCn) {
//      String id = (String)billIdlist.get(0);
//      if (!BizCollUtil.checkVoucherHasSubmit(id, true)) {
//        throw new BizCollException(BizCollException.CANNOTVOUCHER);
//      }
//      ObjectUuidPK objpk = new ObjectUuidPK(id);
//      IDynamicObject iDynamicObject = DynamicObjectFactory.getRemoteInstance();
//
//      DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
//
//      dailyLoanBillinfo.setFiVouchered(false);
//      iDynamicObject.update(objpk.getObjectType(), objpk, dailyLoanBillinfo);
//
//      actionVoucher_actionPerformed(e);
//    }
//    else {
//      this.stateList = getSelectedByStatus();
//      this.botpState = "payOff";
//      super.actionCreateTo_actionPerformed(e);
//    }
//  }
	
	
	@Override
	public void actionAntiAudit_actionPerformed(ActionEvent arg0)
			throws Exception {
		checkMulSelected(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "OPERATE_ANTIAUDIT")));
    	if (getSelectedKeyValue() == null) {
	       return;
	     }
	     ICreateK3PayNoticeBillFacade iBiz = CreateK3PayNoticeBillFacadeFactory.getRemoteInstance();
	     BOSUuid id = BOSUuid.read(getSelectedKeyValue());
	     iBiz.checkBillHasCreate(id);
		super.actionAntiAudit_actionPerformed(arg0);
	}
	
	
}
