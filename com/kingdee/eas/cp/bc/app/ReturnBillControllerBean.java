package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.DailyLoanBillCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillCollection;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.IDailyLoanBill;
import com.kingdee.eas.cp.bc.IEvectionLoanBill;
import com.kingdee.eas.cp.bc.ReturnBillInfo;
import com.kingdee.eas.cp.bc.ReturnBillTypeEnum;
import com.kingdee.eas.cp.bc.ReturnStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.cp.bc.ReturnBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class ReturnBillControllerBean extends AbstractReturnBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.ReturnBillControllerBean");
    
    //保存
    @Override
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	ReturnBillInfo info = (ReturnBillInfo) model;
    	info.setBillState(ReturnStateEnum.TEMPSAVE);
    	IObjectPK pk = super._save(ctx, model);
    	if(pk!=null){
    		updateLoanBill(ctx,info,"SAVE");
    	}
    	return pk;
    }
    
    //提交
	@Override
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	ReturnBillInfo info =  (ReturnBillInfo) model;
    	info.setBillState(ReturnStateEnum.SUBMITEDPAID);
    	IObjectPK pk = super._submit(ctx, model);
    	if(pk!=null){
    		updateLoanBill(ctx,info,"SUBMIT");
    	}
    	return pk;
    }
    
	//审核
	@Override
	protected void _audit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = null;
		ReturnBillInfo info =  (ReturnBillInfo) model;
		info.setAuditDate(new Date());
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	info.setBillState(ReturnStateEnum.COMFIRMPAID);
    	
    	sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("auditDate"));
    	sic.add(new SelectorItemInfo("auditor"));
    	sic.add(new SelectorItemInfo("billState"));
    	
    	_updatePartial(ctx, info, sic);
    		
    	updateLoanBill(ctx,info,"AUDIT");
	}
	
	//更新借款单
	private void updateLoanBill(Context ctx, ReturnBillInfo info, String str) throws BOSException, EASBizException {
		SelectorItemCollection sic = null;
		
		if(ReturnBillTypeEnum.EVECTIONLOAN_VALUE.equals(info.getReturnBillType().getValue())){//出差借款单还款
			IEvectionLoanBill iBill = EvectionLoanBillFactory.getLocalInstance(ctx);
			EvectionLoanBillInfo loanInfo = getEvectionLoanByNumber(iBill,info.getLoanBillNumber());
			if(loanInfo!=null){
				EvectionLoanBillInfo loanBillInfo = new EvectionLoanBillInfo();
				loanBillInfo.setId(BOSUuid.read(loanInfo.getId().toString()));
				loanBillInfo.setReturnAmt(info.getReturnAmount());
				
				sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("returnAmt"));
			    sic.add(new SelectorItemInfo("returnState"));
			    if("AUDIT".equals(str)){
			    	loanBillInfo.setReturnState(ReturnStateEnum.COMFIRMPAID);
			    }else if("SAVE".equals(str)){
			    	loanBillInfo.setReturnState(ReturnStateEnum.TEMPSAVE);
			    }else if("SUBMIT".equals(str)){
			    	loanBillInfo.setReturnState(ReturnStateEnum.SUBMITEDPAID);
			    }else if("DELETE".equals(str)){
			    	loanBillInfo.setReturnAmt(null);
			    	loanBillInfo.setReturnState(null);
			    }
			    iBill.updatePartial(loanBillInfo, sic);
			}
		}else if(ReturnBillTypeEnum.DAILYLOAN_VALUE.equals(info.getReturnBillType().getValue())){//借款单还款
			IDailyLoanBill loanBill = DailyLoanBillFactory.getLocalInstance(ctx);
			DailyLoanBillInfo loanInfo = getDailyLoanByNumber(loanBill,info.getLoanBillNumber());
    		if(loanInfo!=null){
    			DailyLoanBillInfo loanBillInfo = new DailyLoanBillInfo();
    			loanBillInfo.setId(BOSUuid.read(loanInfo.getId().toString()));
    			loanBillInfo.setReturnAmt(info.getReturnAmount());
    			
    			sic = new SelectorItemCollection();
    		    sic.add(new SelectorItemInfo("returnState"));
    		    sic.add(new SelectorItemInfo("returnAmt"));
    		    
    		    if("AUDIT".equals(str)){
			    	loanBillInfo.setReturnState(ReturnStateEnum.COMFIRMPAID);
			    }else if("SAVE".equals(str)){
			    	loanBillInfo.setReturnState(ReturnStateEnum.TEMPSAVE);
			    }else if("SUBMIT".equals(str)){
			    	loanBillInfo.setReturnState(ReturnStateEnum.SUBMITEDPAID);
			    }else if("DELETE".equals(str)){
			    	loanBillInfo.setReturnState(null);
			    }
    			loanBill.updatePartial(loanBillInfo, sic);
    		}
		}
	}

	//删除
	@Override
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		ReturnBillInfo returnBillInfo = (ReturnBillInfo) _getValue(ctx, pk);
		super._delete(ctx, pk);
		updateLoanBill(ctx, returnBillInfo, "DELETE");
		
	}
	
	//通过编码获取借款单
	private DailyLoanBillInfo getDailyLoanByNumber(
			IDailyLoanBill loanBill, String billNum) throws BOSException {
    	DailyLoanBillInfo billInfo = null;
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("number",billNum));
    	view.setFilter(filter);
    	DailyLoanBillCollection billCols = loanBill.getDailyLoanBillCollection(view);
    	if(billCols!=null && billCols.size()>0){
    		billInfo = billCols.get(0);
    	}
		return billInfo;
	}
	
	//通过编码获取出差借款单
	private EvectionLoanBillInfo getEvectionLoanByNumber(
			IEvectionLoanBill bill, String billNum) throws BOSException {
		EvectionLoanBillInfo billInfo = null;
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("number",billNum));
    	view.setFilter(filter);
    	EvectionLoanBillCollection billCols = bill.getEvectionLoanBillCollection(view);
    	if(billCols!=null && billCols.size()>0){
    		billInfo = billCols.get(0);
    	}
		return billInfo;
	}
}