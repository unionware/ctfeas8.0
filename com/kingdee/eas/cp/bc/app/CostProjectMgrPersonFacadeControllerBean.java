package com.kingdee.eas.cp.bc.app;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.ManagerListCollection;
import com.kingdee.eas.basedata.assistant.ManagerListFactory;
import com.kingdee.eas.basedata.assistant.ManagerListInfo;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionCollection;
import com.kingdee.eas.basedata.org.PositionFactory;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.K3ConstantConfigCollection;
import com.kingdee.eas.cp.bc.K3ConstantConfigFactory;
import com.kingdee.eas.cp.bc.app.dbutil.K3WebAccConfigInfos;

public class CostProjectMgrPersonFacadeControllerBean extends AbstractCostProjectMgrPersonFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.CostProjectMgrPersonFacadeControllerBean");

    
    @Override
    protected String[] _getPersonIdsByBill(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	BizCollBillBaseInfo bill = getSourceObj(ctx, billId);
    	Set<Person> persons = getPersonByBill(ctx, bill);
    	return convertObj2Str(persons);
    }
    //�����ò���ȡ
    protected Person[] _getPersonObjsByOrg(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	if(billId==null){
    		logger.info("�����ò���ȡID  is null");
    		return null;
    	}
    	logger.info("�����ò���ȡID :"+billId.toString());
    	BizCollBillBaseInfo bill = getSourceObj(ctx, billId);
    	Set<Person> persons = getPersonByOrg(ctx, bill);
    	Person[] t = null;
    	if(persons==null){
    		return null;
    		/*Person p = new Person();
    		UserInfo  us = com.kingdee.eas.util.app.ContextUtil.getCurrentUserInfo(ctx);
    		p.setUserId(us.getId().toString());
    		p.setUserName(Locale.CHINA, us.getName());
    		p.setEmployeeId(us.getPerson().getId().toString());
    		t = new Person[1];
    		t[0] = p;
    		return t;*/
    	}
    	 t = new Person[persons.size()];
    	return persons.toArray(t);
    	
    }
    
    private BizCollBillBaseInfo getSourceObj(Context ctx,BOSUuid id) throws BOSException{
    	IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
    	BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)iDynamicObject.getValue(id.getType(), new ObjectUuidPK(id), getSelector());
    	
    	return billInfo; 
    }
    
    private SelectorItemCollection getSelector() {
    	    SelectorItemCollection sic = new SelectorItemCollection();
    	    sic.add(new SelectorItemInfo("name"));
			sic.add(new SelectorItemInfo("bizReqDate"));
			sic.add(new SelectorItemInfo("billDate"));
			sic.add(new SelectorItemInfo("costedDept.id"));
			sic.add(new SelectorItemInfo("costedDept.number"));
			sic.add(new SelectorItemInfo("costedDept.name"));
			sic.add(new SelectorItemInfo("company.number"));
			sic.add(new SelectorItemInfo("company.name"));
			sic.add(new SelectorItemInfo("company.id"));
			sic.add(new SelectorItemInfo("supportedObj.number"));
			sic.add(new SelectorItemInfo("supportedObj.name"));
			sic.add(new SelectorItemInfo("supportedObj.id"));
			sic.add(new SelectorItemInfo("currencyType.id"));
			sic.add(new SelectorItemInfo("currencyType.name"));
			sic.add(new SelectorItemInfo("currencyType.number"));
	
			sic.add(new SelectorItemInfo("expenseType.id"));
			sic.add(new SelectorItemInfo("expenseType.name"));
			sic.add(new SelectorItemInfo("expenseType.number"));
			sic.add(new SelectorItemInfo("amount"));
			sic.add(new SelectorItemInfo("amountApproved"));
			sic.add(new SelectorItemInfo("amountEncashed"));
			sic.add(new SelectorItemInfo("amountPaid"));
			sic.add(new SelectorItemInfo("amountNotPaid"));
			sic.add(new SelectorItemInfo("budgetAmount"));
			sic.add(new SelectorItemInfo("sourceBillId"));
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("entries.*"));
	
			sic.add(new SelectorItemInfo("amountControlType"));
			sic.add(new SelectorItemInfo("ReqCheckEntries.*"));
			sic.add(new SelectorItemInfo("LoanCheckEntries.*"));
    	    return sic;
    	   }
    
    //����Ŀȡ
    protected Person[] _getPersonObjsByBill(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	if(billId==null){
    		logger.info("����ĿȡID  is null");
    		return null;
    	}
    	logger.info("����ĿȡID :"+billId.toString());
    	BizCollBillBaseInfo bill = getSourceObj(ctx, billId);
    	Set<Person> persons = getPersonByBill(ctx, bill);
    	Person[] t = null;
    	if(persons==null){
    		
    		return null;
    	}
    	 t = new Person[persons.size()];
    	return persons.toArray(t);
    }
    
    /**
     * ����ĿȡְԱ
     * @param ctx
     * @param bill
     * @return
     * @throws BOSException
     */
    private Set<Person> getPersonByBill(Context ctx, IObjectValue bill) throws BOSException{
    	BizCollBillBaseInfo info = (BizCollBillBaseInfo)bill;
    	AbstractObjectCollection entrys = getBillEntrys(info);
    	Set projectList = getProjects(entrys);
    	Set<Person> persons =  getProjectMgr(ctx,projectList);
    	return persons;
    }
    
    /**
     * ����֯ȡְԱ
     * @param ctx
     * @param bill
     * @return
     * @throws BOSException
     */
    private Set<Person> getPersonByOrg(Context ctx, IObjectValue bill) throws BOSException{
    	BizCollBillBaseInfo info = (BizCollBillBaseInfo)bill;
    	AbstractObjectCollection entrys = getBillEntrys(info);
    	Set<String> orgList = getOrgs(entrys);
    	Set<Person> persons =  getOrgMainPerson(ctx,orgList,getValByNum(ctx,K3WebAccConfigInfos.NUM_CHARGE_NAME));
    	return persons;
    }
    
    
    /**
     * ȡ��¼
     * @param info
     * @return
     */
    private AbstractObjectCollection getBillEntrys(BizCollBillBaseInfo info){
    	return  (AbstractObjectCollection)info.get("entries");
    }
    
    /**
     * ��ȡ�����е���Ŀ
     * @param entrys
     * @return
     */
    private Set getProjects(AbstractObjectCollection entrys){
    	Set<String> projectSet = new HashSet<String>();
    	if(entrys!=null && entrys.size()>0){
    		ProjectInfo project = null;
    		for(int i=0;i<entrys.size();i++){
    			project = (ProjectInfo)entrys.getObject(i).get("project");
    			if(project==null){
    				continue;
    			}
    			projectSet.add(project.getId().toString());
    		}
    	}
    	return projectSet;
    }
    /**
     * ��ȡ���ݷ��ò���
     * @param entrys
     * @return
     */
    private Set<String> getOrgs(AbstractObjectCollection entrys){
    	Set<String> projectSet = new HashSet<String>();
    	logger.info("��ȡ���ݷ��ò���ID����entrys.size()="+entrys.size());
    	StringBuffer pjStr = new StringBuffer();
    	if(entrys!=null && entrys.size()>0){
    		CostCenterOrgUnitInfo orgUnit = null;
    		for(int i=0;i<entrys.size();i++){
    			orgUnit = (CostCenterOrgUnitInfo)entrys.getObject(i).get("costCenter");//������
    			if(orgUnit==null){
    					orgUnit = (CostCenterOrgUnitInfo)entrys.getObject(i).get("costedDept"); //���뵥�������
    					if(orgUnit==null){
    						orgUnit = (CostCenterOrgUnitInfo)entrys.getObject(i).get("costDept"); // ��
    	    			}else{
    	    				logger.info("û�л�ȡ�����ò����ֶ�");
    	    			}
    			}
    			pjStr.append(orgUnit.getId().toString()+",");
    			projectSet.add(orgUnit.getId().toString());
    		}
    	}
    	logger.info("��ȡ���ݷ��ò���ID����orgSet.size()="+projectSet.size()+"   prjStr+"+pjStr.toString());
    	return projectSet;
    }
    
    private Set<Person> getProjectMgr(Context ctx,Set projectSet) throws BOSException{
    	if(projectSet==null || projectSet.size()==0){
    		return null;
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("project.id",projectSet,CompareType.INCLUDE));
    	//��Ŀ�е���Ŀ����
    	ManagerListCollection coll =  ManagerListFactory.getLocalInstance(ctx).getManagerListCollection(view);
    	if(coll==null || coll.size()==0){
    		return null;
    	}
    	 Set<String> personSet = new HashSet<String>(); //��Ŀ�е�������Ŀ����ְԱ
    	ManagerListInfo entry = null;
    	for( int i=0;i<coll.size();i++){
    		entry = coll.get(i);
    		personSet.add(entry.getProjectManage().getId().toString());
    	}
    	filter.getFilterItems().clear();
    	filter.getFilterItems().add(new FilterItemInfo("person.id",personSet,CompareType.INCLUDE));
    	UserCollection userColl  = UserFactory.getLocalInstance(ctx).getUserCollection(view); //ְԱ��Ӧ�����û�
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
    
    /**
     * ��ȡ���ò��ŵ����� -�û�
     * @param ctx
     * @param orgSet
     * @param posistionName
     * @return
     * @throws BOSException
     */
    private Set<Person> getOrgMainPerson(Context ctx,Set<String> orgSet,String posistionName) throws BOSException{
    	if(orgSet==null || orgSet.size()==0){
    		return null;
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("adminOrgUnit.id",orgSet,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("name",posistionName));
    	
    	
    	//ְλ + ��֯
    	PositionCollection pcoll =  PositionFactory.getLocalInstance(ctx).getPositionCollection(view);
    	Set<String> postionSet =new HashSet<String>();//ְλID
    	StringBuffer postionStr = new StringBuffer();
    	for(int i=0;i<pcoll.size();i++){
    		postionStr.append(pcoll.get(i).getId().toString()+",");
    		postionSet.add(pcoll.get(i).getId().toString());
    	}
    	logger.info("posistionName = "+posistionName+"   orgSet.size()="+orgSet.size()+"  postionSet.size()= "+postionSet.size()+"  str="+postionStr.toString());
    	
    	filter.getFilterItems().clear();
    	filter.getFilterItems().add(new FilterItemInfo("position.id",postionSet,CompareType.INCLUDE));
    	//ְԱ��ְλ��ϵ��ȡ�̶�ְλ��Ϣ
    	PositionMemberCollection positionColl =  PositionMemberFactory.getLocalInstance(ctx).getPositionMemberCollection(view);
    	
    	
    	Set<String> personSet =new HashSet<String>(); //ְԱ
    	 
    	for(int i=0;i<positionColl.size();i++){
    		personSet.add(positionColl.get(i).getPerson().getId().toString());
    	}
    	
    	filter.getFilterItems().clear();
    	filter.getFilterItems().add(new FilterItemInfo("person.id",personSet,CompareType.INCLUDE));
    	//ְԱ��Ӧ���û�
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
    
    private String[] convertObj2Str(Set<Person> person){
    	return null;
    }
    
    private  String getValByNum(Context ctx,String number) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number",number));
		K3ConstantConfigCollection coll =  K3ConstantConfigFactory.getLocalInstance(ctx).getK3ConstantConfigCollection(view);
		if(coll!=null && coll.size()>0){
			return coll.get(0).getVal();
		}else{
			return null;
		}
	}
    

}