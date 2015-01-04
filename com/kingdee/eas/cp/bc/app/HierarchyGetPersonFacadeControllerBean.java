package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.bi.model.util.StringUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnit;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.HierarchyCollection;
import com.kingdee.eas.basedata.org.HierarchyFactory;
import com.kingdee.eas.basedata.org.HierarchyInfo;
import com.kingdee.eas.basedata.org.IHierarchy;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.IPosition;
import com.kingdee.eas.basedata.org.IPositionHierarchy;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.PositionCollection;
import com.kingdee.eas.basedata.org.PositionFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyCollection;
import com.kingdee.eas.basedata.org.PositionHierarchyFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyInfo;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.IPositionLevel;
import com.kingdee.eas.cp.bc.PositionLevelCollection;
import com.kingdee.eas.cp.bc.PositionLevelFactory;
import com.kingdee.eas.cp.bc.PositionLevelInfo;
import com.kingdee.util.StringUtils;

public class HierarchyGetPersonFacadeControllerBean extends AbstractHierarchyGetPersonFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.HierarchyGetPersonFacadeControllerBean");
    
    private FilterInfo filter = null;
    private EntityViewInfo view = null;
    
    //按行政组织和职级获取参与者
    protected Person[] _getPersonByAPL(Context ctx, String adminOrgId,
    		String levelNum) throws BOSException, EASBizException {
    	if(StringUtil.isEmpty(adminOrgId)){
    		return null;
    	}
    	PositionLevelInfo pLInfo = getPositionLevelByNum(ctx,levelNum);
    	if(pLInfo==null){
    		return null;
    	}
    	Set<Person> persons = getPersonByOrgAndPL(ctx,adminOrgId,pLInfo);
    	Person[] t = null;
    	if(persons==null){
    		Person p = new Person();
    		UserInfo  us = com.kingdee.eas.util.app.ContextUtil.getCurrentUserInfo(ctx);
    		p.setUserId(us.getId().toString());
    		p.setUserName(Locale.CHINA, us.getName());
    		p.setEmployeeId(us.getPerson().getId().toString());
    		t = new Person[1];
    		t[0] = p;
    		return t;
    	}
    	 t = new Person[persons.size()];
    	return persons.toArray(t);
    }
    
    //按公司和职级获取参与者
    protected Person[] _getPersonByCPL(Context ctx, String companyId,
    		String levelNum) throws BOSException, EASBizException {
    	if(StringUtils.isEmpty(companyId)){
    		return null;
    	}
    	
    	String adminOrgID = getAdminOrgIDByCompany(ctx,companyId);
    	if(StringUtils.isEmpty(adminOrgID)){
    		return null;
    	}
    	
    	PositionLevelInfo pLInfo = getPositionLevelByNum(ctx,levelNum);
    	if(pLInfo==null){
    		return null;
    	}
    	
    	Set<Person> persons = getPersonByOrgAndPL(ctx,adminOrgID,pLInfo);
    	Person[] t = null;
    	if(persons==null){
    		Person p = new Person();
    		UserInfo  us = com.kingdee.eas.util.app.ContextUtil.getCurrentUserInfo(ctx);
    		p.setUserId(us.getId().toString());
    		p.setUserName(Locale.CHINA, us.getName());
    		p.setEmployeeId(us.getPerson().getId().toString());
    		t = new Person[1];
    		t[0] = p;
    		return t;
    	}
    	 t = new Person[persons.size()];
    	return persons.toArray(t);
    }
    
    //根据职位、职级和汇报关系获取参与人
    public Person[] _getPersonByPPlH(Context ctx, String positionNum,
    		String levelNum, String hierarchyNum) throws BOSException,
    		EASBizException {
    	String positionId = null;
    	if(StringUtils.isEmpty(positionNum)
    			||StringUtils.isEmpty(levelNum)
    			||StringUtils.isEmpty(hierarchyNum)){
    		return null;
    	}
    	PositionInfo pInfo = getPostionByNum(ctx,positionNum);//通过编码获取职位
    	if(pInfo==null){
    		return null;
    	}
    	
    	PositionLevelInfo plInfo = getPostionLevelByNum(ctx,levelNum);//通过编码获取职级
    	if(plInfo==null){
    		return null;
    	}
    	
    	HierarchyInfo hInfo = getHierarchyByNum(ctx,hierarchyNum);//通过编码获取汇报关系
    	
    	if(hInfo==null){
    		return null;
    	}
    	
    	//通过职位和汇报关系找到对用职位汇报关系;
    	positionId = getParentPostionByPH(ctx,pInfo,hInfo,plInfo);
    	if(StringUtils.isEmpty(positionId)){
    		return null;
    	}
    	
    	Set<Person> persons = getPersnByPosition(ctx,positionId);
    	
    	Person[] t = null;
    	if(persons==null){
    		Person p = new Person();
    		UserInfo  us = com.kingdee.eas.util.app.ContextUtil.getCurrentUserInfo(ctx);
    		p.setUserId(us.getId().toString());
    		p.setUserName(Locale.CHINA, us.getName());
    		p.setEmployeeId(us.getPerson().getId().toString());
    		t = new Person[1];
    		t[0] = p;
    		return t;
    	}
    	 t = new Person[persons.size()];
    	return persons.toArray(t);
    }
    
    private Set<Person> getPersnByPosition(Context ctx, String positionId) throws BOSException {
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	filter.getFilterItems().add(new FilterItemInfo("position.id",positionId));
    	
    	//职员与职位关系中取固定职位信息
    	PositionMemberCollection positionColl =  PositionMemberFactory.getLocalInstance(ctx).getPositionMemberCollection(view);
    	
    	Set personSet =new HashSet();//职员
    	for(int i=0;i<positionColl.size();i++){
    		personSet.add(positionColl.get(i).getPerson().getId().toString());
    	}
    	
    	filter.getFilterItems().clear();
    	filter.getFilterItems().add(new FilterItemInfo("person.id",personSet,CompareType.INCLUDE));
    	//职员对应的用户
    	UserCollection userColl  = UserFactory.getLocalInstance(ctx).getUserCollection(view);
    	Set<Person> rev = new HashSet<Person>();
    	for(int i=0;i<userColl.size();i++){
    		Person p = new Person();
    		p.setUserId(userColl.get(i).getId().toString());
    		p.setUserName(Locale.CHINA, userColl.get(i).getName());
    		p.setEmployeeId(userColl.get(i).getPerson().getId().toString());
    		rev.add(p);
    	}
    	return rev;
	}

	private String getParentPostionByPH(Context ctx, PositionInfo pInfo,
			HierarchyInfo hInfo, PositionLevelInfo plInfo1) throws BOSException {
		String positionId = null;
    	Map map = new HashMap();
    	String cpId = pInfo.getId().toString();
    	String ppId = null;
    	String plId = null;
    	PositionHierarchyInfo info = null;
    	PositionLevelInfo plInfo = null;
    	PositionInfo ppInfo = null;
    	IPositionHierarchy phierarchy = PositionHierarchyFactory.getLocalInstance(ctx);
    	
    	boolean b = true;
    	
		while(b){
			view = new EntityViewInfo();
	    	filter = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("child.id",cpId));
	    	filter.getFilterItems().add(new FilterItemInfo("hierarchy.id",hInfo.getId().toString()));
	    	filter.setMaskString("#0 and #1");
	    	view.setFilter(filter);
	    	
	    	SelectorItemCollection sic = new SelectorItemCollection();
	    	sic.add(new SelectorItemInfo("id"));
	    	sic.add(new SelectorItemInfo("parent.*"));
	    	sic.add(new SelectorItemInfo("parent.positionLevel.*"));
	    	view.setSelector(sic);
	    	
	    	PositionHierarchyCollection pHCol = phierarchy.getPositionHierarchyCollection(view);
	    	if(pHCol!=null && pHCol.size()>0){
	    		info = pHCol.get(0);
	    		ppInfo = info.getParent();
	    		plInfo = ppInfo.getPositionLevel();
	    		if(plInfo!=null){
	    			plId = plInfo.getId().toString();
	    		}else{
	    			plId = null;
	    		}
	    		ppId = ppInfo.getId().toString();
	    	}
	    	
	    	if(!ppId.equals(cpId)){
	    		map.put(plId, ppId);
	    		cpId = ppId;
	    	}else{
	    		b = false;
	    	}
		}
		positionId = (String) map.get(plInfo1.getId().toString());
		return positionId;
	}

	private HierarchyInfo getHierarchyByNum(Context ctx, String positionNum) throws BOSException {
    	HierarchyInfo info = null;
    	IHierarchy hierarchy = HierarchyFactory.getLocalInstance(ctx);
    	filter = new FilterInfo();
    	view = new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("number",positionNum));
    	view.setFilter(filter);
    	HierarchyCollection col = hierarchy.getHierarchyCollection(view);
    	if(col!=null && col.size()>0){
    		info = col.get(0);
    	}
		return info;
	}

	private PositionLevelInfo getPostionLevelByNum(Context ctx, String levelNum) throws BOSException {
		PositionLevelInfo info = null;
    	IPositionLevel pl = PositionLevelFactory.getLocalInstance(ctx);
    	filter = new FilterInfo();
    	view = new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("number",levelNum));
    	view.setFilter(filter);
    	PositionLevelCollection col = pl.getPositionLevelCollection(view);
    	if(col!=null && col.size()>0){
    		info = col.get(0);
    	}
		return info;
	}

	private PositionInfo getPostionByNum(Context ctx, String positionNum) throws BOSException {
		PositionInfo info = null;
    	IPosition pl = PositionFactory.getLocalInstance(ctx);
    	filter = new FilterInfo();
    	view = new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("number",positionNum));
    	view.setFilter(filter);
    	PositionCollection col = pl.getPositionCollection(view);
    	if(col!=null && col.size()>0){
    		info = col.get(0);
    	}
		return info;
	}

	private String getAdminOrgIDByCompany(Context ctx, String companyId) throws BOSException, EASBizException {
    	String adminOrgId = null;
    	
    	FullOrgUnitInfo fullOrgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(companyId));
    	if(fullOrgUnitInfo.isIsAdminOrgUnit()){
    		adminOrgId = companyId;
    	}
		return adminOrgId;
	}

	private Set<Person> getPersonByOrgAndPL(Context ctx, String adminOrgId,
			PositionLevelInfo pLinfo) throws BOSException {
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("adminOrgUnit.id",adminOrgId,CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("positionLevel",pLinfo.getId().toString()));
    	
    	//通过行政组织和职级获取职位
    	PositionCollection pcoll =  PositionFactory.getLocalInstance(ctx).getPositionCollection(view);
    	
    	Set postionSet =new HashSet();
    	for(int i=0;i<pcoll.size();i++){
    		postionSet.add(pcoll.get(i).getId().toString());
    	}
    	
    	filter.getFilterItems().clear();
    	filter.getFilterItems().add(new FilterItemInfo("position.id",postionSet,CompareType.INCLUDE));
    	
    	//职员与职位关系中取固定职位信息
    	PositionMemberCollection positionColl =  PositionMemberFactory.getLocalInstance(ctx).getPositionMemberCollection(view);
    	
    	
    	Set personSet =new HashSet();//职员
    	for(int i=0;i<positionColl.size();i++){
    		personSet.add(positionColl.get(i).getPerson().getId().toString());
    	}
    	
    	filter.getFilterItems().clear();
    	filter.getFilterItems().add(new FilterItemInfo("person.id",personSet,CompareType.INCLUDE));
    	//职员对应的用户
    	UserCollection userColl  = UserFactory.getLocalInstance(ctx).getUserCollection(view);
    	Set<Person> rev = new HashSet<Person>();
    	for(int i=0;i<userColl.size();i++){
    		Person p = new Person();
    		p.setUserId(userColl.get(i).getId().toString());
    		p.setUserName(Locale.CHINA, userColl.get(i).getName());
    		p.setEmployeeId(userColl.get(i).getPerson().getId().toString());
    		rev.add(p);
    	}
    	return rev;
	}
    
    //通过职级编码获取职级对象
    private PositionLevelInfo getPositionLevelByNum(Context ctx,String levelNum) throws BOSException {
    	PositionLevelInfo levelInfo = null;
    	if(StringUtils.isEmpty(levelNum)){
    		return null;
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("number",levelNum,CompareType.EQUALS));
    	PositionLevelCollection pLCol = PositionLevelFactory.getLocalInstance(ctx).getPositionLevelCollection(view);
    	if(pLCol!=null && pLCol.size()>0){
    		levelInfo = pLCol.get(0);
    	}
		return levelInfo;
	}
	
}