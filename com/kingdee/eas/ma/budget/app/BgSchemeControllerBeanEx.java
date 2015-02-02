package com.kingdee.eas.ma.budget.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.ma.budget.BgDataCollection;
import com.kingdee.eas.ma.budget.BgDataFactory;
import com.kingdee.eas.ma.budget.BgDataInfo;
import com.kingdee.eas.ma.budget.BgFormCollection;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgSchemeCollection;
import com.kingdee.eas.ma.budget.BgSchemeFactory;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.eas.ma.budget.BgSchemeNodeInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class BgSchemeControllerBeanEx extends BgSchemeControllerBean {

	private static final long serialVersionUID = 4564890033416165077L;

	public BgSchemeControllerBeanEx() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void _copyScheme(Context ctx, Map map) throws BOSException,
			EASBizException {
		String pk = (String) map.get("SourceID");
		String number = (String) map.get("number");
		String name = (String) map.get("name");
		String prefix = (String) map.get("prefix");
		
		//处理预算方案
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("rootId",pk));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("bgSchemeNodes.*");
		/*sic.add("bgSchemeNodes.bgTemplate.*");
		sic.add("bgSchemeNodes.bgTemplate.bgMeasureUnit.*");
		sic.add("bgSchemeNodes.bgTemplate.bgExamineNode.*");*/
		view.setFilter(filter);
		view.setSelector(sic);
		SorterItemCollection sortCol = new SorterItemCollection();
		SorterItemInfo sort1 = new SorterItemInfo("orgUnit.level");
		sort1.setSortType(SortType.ASCEND);
		sortCol.add(sort1);
		view.setSorter(sortCol);
		
		BgSchemeCollection col = BgSchemeFactory.getLocalInstance(ctx).getBgSchemeCollection(view);
		
		BgSchemeInfo info = null;
		BgSchemeInfo parentInfo = null;
		BgSchemeNodeInfo nodeInfo = null;
		
		BOSUuid newID = null;
		
		CoreBaseCollection coreCol = new CoreBaseCollection();

		Set<String> idSet = new HashSet<String>();
		
		Map<String,BgSchemeInfo> schemeMap = new HashMap<String,BgSchemeInfo>();
		for(int i=0;i<col.size();i++){
			info = col.get(i);
			newID = BOSUuid.create(info.getBOSType());
			
			schemeMap.put(info.getId().toString(), info);
			idSet.add(info.getId().toString());
			
			if(info.getParentScheme()!=null && info.getParentScheme().getId()!=null){
				parentInfo = schemeMap.get(info.getParentScheme().getId().toString());
				if(parentInfo!=null){
					info.setParentScheme(parentInfo);
				}
			}
			
			info.setId(newID);
			info.setRootId(schemeMap.get(pk.toString()).getId());
			/*info.setNumber("Copy_"+info.getNumber());
			info.setName("Copy_"+info.getName());*/
			info.setNumber(number);
			info.setName(name);
			info.setIsFormal(false);
			info.setIsCopyScheme(true);
			
			for(int j=0;j<info.getBgSchemeNodes().size();j++){
				nodeInfo = info.getBgSchemeNodes().get(j);
				nodeInfo.setId(null);
				nodeInfo.setBgScheme(info);
			}
			
			coreCol.add(info);
		}
		
		BgSchemeFactory.getLocalInstance(ctx).addnew(coreCol);
		
		for(int i=0;i<coreCol.size();i++){
			((BgSchemeInfo)coreCol.get(i)).setRootId(schemeMap.get(pk.toString()).getId());
		}
		BgSchemeFactory.getLocalInstance(ctx).update(coreCol);
		
		//处理预算表
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("bgScheme.id",idSet,CompareType.INCLUDE));
		sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("operLogs.*");
		sic.add("bgMeasureUnit.*");
		sic.add("entries.*");
		sic.add("entries.schemes.*");
		view.setFilter(filter);
		view.setSelector(sic);
		
		BgFormCollection bgFormCol = BgFormFactory.getLocalInstance(ctx).getBgFormCollection(view);
		coreCol = new CoreBaseCollection();
		
		BgFormInfo bgFormInfo = null;
		BOSUuid schemeID = null;

		Set<String> formIdSet = new HashSet<String>();
		Map<String,BgFormInfo> formMap = new HashMap<String,BgFormInfo>();
		
		for(int i=0;i<bgFormCol.size();i++){
			bgFormInfo = bgFormCol.get(i);
			if(bgFormInfo.getBgScheme()!=null){
				info=schemeMap.get(bgFormInfo.getBgScheme().getId().toString());
				if(info!=null){
					bgFormInfo.setBgScheme(info);
				}
			}
			
			if(BgFormFactory.getLocalInstance(ctx).exists("where number='"+prefix+"_"+bgFormInfo.getNumber()+"'")){
				throw new EASBizException(new NumericExceptionSubItem("11111","预算表编码不能重复"));
			}
			
			formMap.put(bgFormInfo.getId().toString(), bgFormInfo);
			formIdSet.add(bgFormInfo.getId().toString());
			
			bgFormInfo.setId(BOSUuid.create(bgFormInfo.getBOSType()));
			/*bgFormInfo.setName("Copy_"+bgFormInfo.getName());
			bgFormInfo.setNumber("Copy_"+bgFormInfo.getNumber());*/
			bgFormInfo.setName(prefix+"_"+bgFormInfo.getName());
			bgFormInfo.setNumber(prefix+"_"+bgFormInfo.getNumber());
			
			for(int j=0;j<bgFormInfo.getOperLogs().size();j++){
				bgFormInfo.getOperLogs().get(j).setBgForm(bgFormInfo);
				bgFormInfo.getOperLogs().get(j).setId(null);
			}
			
			for(int j=0;j<bgFormInfo.getBgMeasureUnit().size();j++){
				bgFormInfo.getBgMeasureUnit().get(j).setBgForm(bgFormInfo);
				bgFormInfo.getBgMeasureUnit().get(j).setId(null);
			}
			
			for(int x=0;x<bgFormInfo.getEntries().size();x++){
				bgFormInfo.getEntries().get(x).setBgForm(bgFormInfo);
				bgFormInfo.getEntries().get(x).setId(null);
				
				for(int y=0;y<bgFormInfo.getEntries().get(x).getSchemes().size();y++){
					bgFormInfo.getEntries().get(x).getSchemes().get(y).setId(null);
					bgFormInfo.getEntries().get(x).getSchemes().get(y).setFormEntry(bgFormInfo.getEntries().get(x));
				}
			}
			coreCol.add(bgFormInfo);
		}
		BgFormFactory.getLocalInstance(ctx).addnew(coreCol);
		
		
		//处理预算数据表
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("bgFormId.id",formIdSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("bgScheme.id",idSet,CompareType.INCLUDE));
		sic = new SelectorItemCollection();
		sic.add("*");
		sic.add(new SelectorItemInfo("bgFormId.*"));
		sic.add(new SelectorItemInfo("bgScheme.*"));
		view.setFilter(filter);
		view.setSelector(sic);
		
		CoreBaseCollection bgDataCol = BgDataFactory.getLocalInstance(ctx).getCollection(view);
		coreCol = new CoreBaseCollection();
		
		BgDataInfo bgDataInfo = null;
		
		for(int i=0;i<bgDataCol.size();i++){
			bgDataInfo = (BgDataInfo) bgDataCol.get(i);
			
			if(bgDataInfo.getBgScheme()!=null){
				info=schemeMap.get(bgDataInfo.getBgScheme().getId().toString());
				if(info!=null){
					bgDataInfo.setBgScheme(info);
				}
			}
			
			if(bgDataInfo.getBgFormId()!=null){
				bgFormInfo=formMap.get(bgDataInfo.getBgFormId().getId().toString());
				if(bgFormInfo!=null){
					bgDataInfo.setBgFormId(bgFormInfo);
				}
			}
			
			bgDataInfo.setId(BOSUuid.create(bgDataInfo.getBOSType()));
			
			coreCol.add(bgDataInfo);
		}
		BgDataFactory.getLocalInstance(ctx).addnew(coreCol);
	}

	@Override
	protected void _delCopyScheme(Context ctx, IObjectPK bgSchemeID)
			throws BOSException, EASBizException {
		
	}
}
