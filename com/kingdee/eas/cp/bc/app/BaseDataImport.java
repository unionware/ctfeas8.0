package com.kingdee.eas.cp.bc.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.cp.bc.BaseDataEnum;
import com.kingdee.eas.cp.bc.BaseDataMatchCollection;
import com.kingdee.eas.cp.bc.BaseDataMatchEntryCollection;
import com.kingdee.eas.cp.bc.BaseDataMatchEntryFactory;
import com.kingdee.eas.cp.bc.BaseDataMatchEntryInfo;
import com.kingdee.eas.cp.bc.BaseDataMatchFactory;
import com.kingdee.eas.cp.bc.BaseDataMatchInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;

public class BaseDataImport extends AbstractDataTransmission{
	private BaseDataEnum  types;
	private  BaseDataMatchInfo bill;
	@Override
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		try {
			return BaseDataMatchEntryFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(),e);
		}
	}
	
	@Override
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		BaseDataMatchEntryInfo entry = (BaseDataMatchEntryInfo)coreBaseInfo;
		try {
			getController(ctx).save(entry);
		} catch  (Exception e){
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
	
	
	protected void initTypes(BaseDataEnum  types){
		this.types = types;
	}

	@Override
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		if(bill==null){
			EntityViewInfo view = new EntityViewInfo();
	    	FilterInfo filter = new FilterInfo(); 
	    	view.setFilter(filter);
	    	filter.getFilterItems().add(new FilterItemInfo("types",types.getValue()));
	    	
	    	try {
				BaseDataMatchCollection coll =   BaseDataMatchFactory.getLocalInstance(ctx).getBaseDataMatchCollection(view);
				this.bill = coll.get(0);
			} catch (BOSException e) {
				e.printStackTrace();
				throw new TaskExternalException(e.getMessage(),e);
			}
		}
		
		BaseDataMatchEntryInfo entry = new BaseDataMatchEntryInfo();
		
		 Object souEasData = ((DataToken)hsData.get("FEASNumber")).data;
		 if ((souEasData != null) && (souEasData.toString().trim().length() > 0)) {
			 entry.setEasNum(souEasData.toString());
		 }else{
			 throw new TaskExternalException("EAS编码不能为空");
		 }
		 Object souK3Data = ((DataToken)hsData.get("FK3Number")).data;
		 if ((souK3Data != null) && (souK3Data.toString().trim().length() > 0)) {
			 entry.setK3Num(souK3Data.toString());
		 }else{
			 throw new TaskExternalException("K3编码不能为空");
		 }
		 
		 checkEntryDump(souEasData.toString(), souK3Data.toString());
		
		 entry.setSeq(bill.getEntrys().size()+1);
		 entry.setParent(bill);
		 
		return   entry;
	}
	
	
	
	private void checkEntryDump(String easNnum,String k3Nnum) throws TaskExternalException{
		BaseDataMatchEntryCollection coll =  bill.getEntrys();
		for(int i=0;i<coll.size();i++){
			if(coll.get(i).getEasNum().equals(easNnum) ){
				throw new TaskExternalException("EAS编码"+easNnum+"已存在");
			}
			
			if(coll.get(i).getK3Num().equals(k3Nnum) ){
				throw new TaskExternalException("K3编码"+k3Nnum+"已存在");
			}
		}
	}
	
	
	public int getSubmitType() {
		return 0;
	}
	
	@Override
	public boolean isSameBlock(Hashtable firstData, Hashtable currentData) {
		/*if ((firstData != null) && (currentData != null))
		    {
		 
		       return true;
	     }
		*/
		return false;
	}
	
	
	public String getMainField() {
		// TODO Auto-generated method stub
		return super.getMainField();
	}
	
}
