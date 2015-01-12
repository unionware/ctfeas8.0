package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.EASResource;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BgCtrlBizCollHandler;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CreateK3PayNoticeBillFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountFacadeFactory;
import com.kingdee.eas.cp.bc.ICreateK3PayNoticeBillFacade;
import com.kingdee.eas.cp.bc.IDailyLoanBill;
import com.kingdee.eas.cp.bc.IEvectionLoanBill;
import com.kingdee.eas.cp.bc.IExpenseAccountFacade;
import com.kingdee.eas.cp.bc.IReturnBill;
import com.kingdee.eas.cp.bc.ReturnBillCollection;
import com.kingdee.eas.cp.bc.ReturnBillFactory;
import com.kingdee.eas.cp.bc.ReturnBillInfo;
import com.kingdee.eas.cp.bc.ReturnStateEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.util.NBgControlCallerUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class EvectionLoanBillListUICTEx extends EvectionLoanBillListUI {

	public EvectionLoanBillListUICTEx() throws Exception {
		super();
	}

	private final static String RESIMPORT = "com.kingdee.eas.cp.bc.ImportDataResource";

	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		actionReturn.setEnabled(true);
		
		this.tblMain.addKDTSelectListener(new KDTSelectListener() {
	  	      public void tableSelectChanged(KDTSelectEvent e) {
		  	        try {
		  	        	_tblMain_tableSelectChanged(e);
		  	        } catch (Exception exc) {
		  	        	handUIException(exc);
		  	        }
	  	      }
		});
	}
	
	protected final void _tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
        int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
        if (activeRowIndex != -1) {
        	if(this.tblMain.getRow(activeRowIndex).getCell("state").getValue()!= null){
        		String state = this.tblMain.getCell(activeRowIndex, "state").getValue().toString();
        		if(!StringUtils.isEmpty(state) && StateEnum.CHECKED.getAlias().equals(state)){
        			if (this.tblMain.getRow(activeRowIndex).getCell("returnState").getValue() != null) {
        	            String status = this.tblMain.getCell(activeRowIndex, "returnState").getValue().toString();
        	            if(!StringUtils.isEmpty(status) && ReturnStateEnum.SUBMITEDPAID.getAlias().equals(status)){
        	            	this.actionReturn.setEnabled(true);
        	            	this.actionComfirmReturn.setEnabled(true);
        	            }else if(!StringUtils.isEmpty(status) && ReturnStateEnum.COMFIRMPAID.getAlias().equals(status)){
        	            	this.actionComfirmReturn.setEnabled(false);
        	            	this.actionReturn.setEnabled(false);
        	            }else{
        	            	this.actionReturn.setEnabled(true);
        	            	this.actionComfirmReturn.setEnabled(false);
        	            }
        	        }else{
        	        	  this.actionReturn.setEnabled(true);
        	        	  this.actionComfirmReturn.setEnabled(false);
        	        }
        		}else{
        			this.actionReturn.setEnabled(false);
        			this.actionComfirmReturn.setEnabled(false);
        		}
        	}
        }
     }
	
	@Override
    public void actionReturn_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    if (activeRowIndex < 0) {
	      return;
	    }
        
	    
	    String billID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
        String LoanNumber = this.tblMain.getCell(activeRowIndex, "number").getValue().toString();
        
        EvectionLoanBillInfo billInfo = getBillInfoById(billID);
        ReturnBillInfo rInfo = getBillInfoByNumber(LoanNumber);
        
        UserInfo appllierUser = null;
        if(billInfo.getApplier()!=null){
        	appllierUser = getUserInfoByPerson(billInfo.getApplier());
        }
        UserInfo userInfo =  SysContext.getSysContext().getCurrentUserInfo();
        if((appllierUser!=null  && userInfo.getId().toString().equals(appllierUser.getId().toString()))
        		|| 	(rInfo!=null && rInfo.getLoanor()!=null 
        				&& userInfo.getId().toString().equals(rInfo.getCreator().getId().toString()))
           
        ){
        	UIContext uiContext = new UIContext(this);
    		uiContext.put("billInfo", billInfo);
    		uiContext.put("return", ReturnStateEnum.TEMPSAVE_VALUE);
    		
    		IUIWindow uiWindow = null;
    		if(rInfo==null){
//    			uiContext.put("return", ReturnStateEnum.TEMPSAVE_VALUE);
    			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
    			create(ReturnBillEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
    		}else{
    			uiContext.put("ID", rInfo.getId().toString());
//    			uiContext.put("return", ReturnStateEnum.SUBMITEDPAID_VALUE);
    			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
    			create(ReturnBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
    		}
    		uiWindow.show();
    		this.refresh(e);
    		
        }else{
        	MsgBox.showInfo(this, "只有借款人和还款单制单人支持还款！");
        	abort();
        }
    }
	
	private UserInfo getUserInfoByPerson(PersonInfo personInfo) throws BOSException {
    	UserInfo userInfo = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("person.id",personInfo.getId().toString()));
		view.setFilter(filter);
		UserCollection userCol = UserFactory.getRemoteInstance().getUserCollection(view);
		if(userCol!=null && userCol.size()>0){
			userInfo = userCol.get(0);
		}
		return userInfo;
	}
    
    private EvectionLoanBillInfo getBillInfoById(String billID) throws EASBizException, BOSException, Exception {
    	EvectionLoanBillInfo info = null;
    	SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amountApproved"));
        sic.add(new SelectorItemInfo("amountBalance"));
        sic.add(new SelectorItemInfo("applier.*"));
        info = ((IEvectionLoanBill)getBizInterface()).getEvectionLoanBillInfo(new ObjectUuidPK(billID),sic);
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
	    String state = this.tblMain.getCell(activeRowIndex, "state").getValue().toString();
	    
	    EvectionLoanBillInfo billInfo = getBillInfoById(billID);
	    ReturnBillInfo rInfo = getBillInfoByNumber(LoanNumber);
	    
	    if(rInfo==null){
	    	MsgBox.showInfo("数据有误，找不到对应的还款单！");
	    	abort();
	    }
	    
	    UIContext uiContext = new UIContext(this);
	    
		uiContext.put("billInfo", billInfo);
		uiContext.put("ID", rInfo.getId().toString());
		uiContext.put("return", ReturnStateEnum.SUBMITEDPAID_VALUE);
		
		IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
		create(ReturnBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
	    if(uiWindow.getUIObject().destroyWindow()){
	    	ReturnBillInfo rEInfo = ReturnBillFactory.getRemoteInstance().getReturnBillInfo(new ObjectUuidPK(BOSUuid.read(rInfo.getId().toString())));
			if(ReturnStateEnum.COMFIRMPAID_VALUE.equals(rEInfo.getBillState().getValue())){
				int rows[] = KDTableUtil.getSelectedRows(this.tblMain);
				Arrays.sort(rows);
				IRow row = null;
				BizEnumValueInfo enumValue = null;
				StringBuffer sb = new StringBuffer();
				String errorRowIndexString = null;
				int nStatus = NOTHING;
				for (int i = 0; i < rows.length; i++) {
					row = tblMain.getRow(rows[i]);
					if (row.getCell("state") != null) {
						enumValue = (BizEnumValueInfo) row.getCell("state").getValue();
					}
					if (enumValue != null) {
						nStatus = enumValue.getInt();
					}
					if ((StateEnum.ALREADYPAYMENT_VALUE == nStatus)
							|| (StateEnum.CHECKED_VALUE == nStatus && this
									.isNotRelaFiAndVoucher())
							&& BizCollUtil.enbleByApplierCompany(tblMain)) { 
					} else {
						sb.append("" + (rows[i] + 1) + " , ");
					}
				}
				if (sb.length() > 0) {
					int lastPosition = sb.lastIndexOf(",");
					errorRowIndexString = sb.substring(0, lastPosition);
				}
				if (errorRowIndexString != null) {
					throw new BizCollException(BizCollException.ERROR_OPERATION,
							new Object[] { errorRowIndexString });
				}

//				int result = MsgBox.showConfirm2(this, EASResource.getString(RESIMPORT,
//				"IS_DO_CLOSE"));
//				if (result == KDOptionPane.YES_OPTION) {	
					
					List idList = this.getSelectIdList();
					IEvectionLoanBill iEvection = null;
					EvectionLoanBillInfo evectionInfo = null;
					String tempId = null;
					iEvection = EvectionLoanBillFactory.getRemoteInstance();
					Iterator ite = idList.iterator();
					int successCount = 0;
					int count = 0;
					StringBuffer errorMessage = new StringBuffer();
					while (ite.hasNext()) {
						tempId = (String) ite.next();
						evectionInfo = iEvection
								.getEvectionLoanBillInfo(new ObjectUuidPK(BOSUuid
										.read(tempId)));
						if (BizCollUtil.checkForBillClose(evectionInfo)) {
							if (evectionInfo.getLoanState().equals("Y")
									|| evectionInfo.getState().equals(
											StateEnum.ALREADYPAYMENT)
									&& evectionInfo.getAmountBalance().doubleValue() <= 0) {
								BizCollBillBaseInfo info = (BizCollBillBaseInfo) evectionInfo;
								if(isNeedBudget && loanBillUseBg){
									BgCtrlBizCollHandler.setBugdutDoForClose(null, info,reqBillUseBg);
									NBgControlCallerUtil.specialBudget(null, info.getId());
								}
								
								
								BizCollUtil.setBillStateReverse(evectionInfo,StateEnum.CLOSED);
								iEvection.updatePartial(evectionInfo, BizCollUtil.getBillCloseSic());
								successCount++;
							}
							else if (this.isNotRelaFiAndVoucher()
									&& evectionInfo.getState()
											.equals(StateEnum.CHECKED)) {
								BizCollBillBaseInfo info = (BizCollBillBaseInfo) evectionInfo;
								if(isNeedBudget && loanBillUseBg){
									BgCtrlBizCollHandler.setBugdutDoForClose(null,info,reqBillUseBg);
									NBgControlCallerUtil.specialBudget(null, info.getId());
									//NBgControlCallerUtil.returnBudget(null, info.getId());
								}
								IExpenseAccountFacade facade = ExpenseAccountFacadeFactory
										.getRemoteInstance();
								facade.setState(BOSUuid.read(tempId), StateEnum.CLOSED);
								if (evectionInfo.getAmountBalance() != null
										&& evectionInfo.getAmountBalance().compareTo(
												BizCollUtil.ZERO) > 0) {
									evectionInfo.setReturnAmount(evectionInfo
											.getAmountBalance());
								} else {
									evectionInfo
											.setReturnAmount(new BigDecimal("0.00"));
								}
								BizCollUtil.setBillStateReverse(evectionInfo,StateEnum.CLOSED);
								evectionInfo.setReturnDate(new Date());
								evectionInfo.setLoanState("Y");
								SelectorItemCollection sic = new SelectorItemCollection();
								sic.add(new SelectorItemInfo("loanState"));
								sic.add(new SelectorItemInfo("returnDate"));
								sic.add(new SelectorItemInfo("returnAmount"));
								sic.add(new SelectorItemInfo("amountBalance"));
								sic.add(new SelectorItemInfo("state"));
								sic.add(new SelectorItemInfo("entries.amountBalance"));
								EvectionLoanBillFactory.getRemoteInstance()
										.updatePartial(evectionInfo, sic);
								successCount++;
							} else {
								errorMessage.append("\n" + MessageFormat.format(EASResource.getString(RESIMPORT, "stateError"), new String[]{String.valueOf(count + 1)}));				
							}
						} else {
							errorMessage.append("\n" + MessageFormat.format(EASResource.getString(RESIMPORT, "closeError"), new String[]{String.valueOf(count + 1)}));
						}
						count++;
					}

					StringBuffer msg = new StringBuffer();

					msg.append(EASResource.getString(RESIMPORT, "ClOSE_SUCCESS"))
							.append(String.valueOf(successCount)).append(
									EASResource.getString(RESIMPORT, "TIAO")).append(
									",").append(
									EASResource.getString(RESIMPORT, "CLOSE_FAILD"))
							.append(String.valueOf(count - successCount)).append(
									EASResource.getString(RESIMPORT, "TIAO"));
					if (count - successCount > 0) {
						msg.append(errorMessage);
					}

//					MsgBox.showInfo(this, msg.toString());
//					this.showMessage();
					this.refresh(e);
			}
	    }
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
