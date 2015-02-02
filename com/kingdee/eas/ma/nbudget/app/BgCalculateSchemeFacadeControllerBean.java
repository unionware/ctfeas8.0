package com.kingdee.eas.ma.nbudget.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.ctrl.common.util.DBUtil;
import com.kingdee.bos.ctrl.common.util.ZipUtil;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.rpt.IReport;
import com.kingdee.eas.fi.rpt.ReportFactory;
import com.kingdee.eas.fi.rpt.util.IOHelper;
import com.kingdee.eas.ma.budget.BgAdjustFormDiversityData;
import com.kingdee.eas.ma.budget.BgAdjustStateEnum;
import com.kingdee.eas.ma.budget.BgAuditIdeaEnum;
import com.kingdee.eas.ma.budget.BgCollectFormFactory;
import com.kingdee.eas.ma.budget.BgConstants;
import com.kingdee.eas.ma.budget.BgExamineFacadeFactory;
import com.kingdee.eas.ma.budget.BgException;
import com.kingdee.eas.ma.budget.BgFormDataSourceEnum;
import com.kingdee.eas.ma.budget.BgFormDiversityData;
import com.kingdee.eas.ma.budget.BgFormException;
import com.kingdee.eas.ma.budget.BgFormFacadeFactory;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgFormStateEnum;
import com.kingdee.eas.ma.budget.BgGatherFacadeFactory;
import com.kingdee.eas.ma.budget.BgGatherHelper;
import com.kingdee.eas.ma.budget.BgPastDataInfo;
import com.kingdee.eas.ma.budget.BgPastRecordFactory;
import com.kingdee.eas.ma.budget.BgPastRecordInfo;
import com.kingdee.eas.ma.budget.BgPastSourceEnum;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.BgSchemeFactory;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.eas.ma.budget.BgTemFormTypeEnum;
import com.kingdee.eas.ma.budget.BgTemplateFactory;
import com.kingdee.eas.ma.budget.BgTemplateInfo;
import com.kingdee.eas.ma.budget.IBgExamineFacade;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.IBgFormFacade;
import com.kingdee.eas.ma.budget.IBgGatherFacade;
import com.kingdee.eas.ma.budget.IBgPastRecord;
import com.kingdee.eas.ma.budget.IBgScheme;
import com.kingdee.eas.ma.nbudget.BgCalcSchemeAssignMsgObj;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemeCollection;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemeEntryInfo;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemeEntryTemplateInfo;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemeFactory;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemeInfo;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemePeriodInfo;
import com.kingdee.eas.ma.nbudget.BgCalculateSchemeTemSheetInfo;
import com.kingdee.eas.ma.nbudget.BgNConstants;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.ma.nbudget.BgNFormFacadeFactory;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.BgNSQLHelper;
import com.kingdee.eas.ma.nbudget.BgReCalcSchemeHelper;
import com.kingdee.eas.ma.nbudget.BgSchemeReCalcMsgObj;
import com.kingdee.eas.ma.nbudget.IBgCalculateScheme;
import com.kingdee.eas.ma.nbudget.IBgNFormFacade;
import com.kingdee.eas.ma.nbudget.SheetInfo;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class BgCalculateSchemeFacadeControllerBean extends AbstractBgCalculateSchemeFacadeControllerBean
{
	private static final long serialVersionUID = -232116293668453447L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.ma.nbudget.app.BgCalculateSchemeFacadeControllerBean");
	private static final int SCALE = 4;  //小数位精度8位
	Map tempVarMap = new HashMap();    //计算或汇总操作的调整数据集合
	private boolean isGatherForm = false;  //是否汇总表
    /**
     * 描述：重算方案分配
     * @author kaifei_yi
     */
	protected Object _assign(Context ctx, Object param)throws BOSException, EASBizException
    {
    	if (!(param instanceof Map)) 
    		return null;
    	Map paramMap = (Map)param;
    	String orgId = (String) paramMap.get("org_id");
    	String calcSchemeId = (String)paramMap.get("calc_scheme_id");
    	if (StringUtils.isEmpty(orgId) || StringUtils.isEmpty(calcSchemeId))
    		return null;
    	Map resultMap = new HashMap();
    	IObjectPK pk = new ObjectUuidPK(calcSchemeId);
    	IBgCalculateScheme iBgCalculateScheme = BgCalculateSchemeFactory.getLocalInstance(ctx);
    	BgCalculateSchemeInfo info = iBgCalculateScheme.getBgCalculateSchemeInfo(pk, getCalcSchemeSelectors());
    	/*下级重计算方案存在，则先删除下级的*/
    	BgCalculateSchemeInfo childInfo = null,repeatInfo = null;
    	try {
    		childInfo = iBgCalculateScheme.getBgCalculateSchemeInfo("select id where rootid = '"+info.getRootId()+"' and orgUnit.id = '"+orgId+"'");
    		if (childInfo != null){
    			iBgCalculateScheme.delete(new ObjectUuidPK(childInfo.getId().toString()));
    		}
    	} catch(ObjectNotFoundException e){
    		logger.error("error",e);
    	}
    	BgCalcSchemeAssignMsgObj key = null;
    	key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_SCHEME, 
    			ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "recalcscheme", ContextUtils.getLocaleFromEnv()),1);
    	resultMap.put(key, info.getName());
    	key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_ORG, 
    			ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignorg", ContextUtils.getLocaleFromEnv()),2);
    	FullOrgUnitInfo orgInfo = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo("select id,name,number where id = '"+orgId+"'");
    	resultMap.put(key, orgInfo.getName());
    	try {
    		repeatInfo = iBgCalculateScheme.getBgCalculateSchemeInfo("select id where rootid !='"+
    				info.getRootId()+"' and number = '"+info.getNumber()+"' and orgUnit.id = '"+orgId+"'");
    		if (repeatInfo != null){
    			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_RESULT, 
    					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignresult", ContextUtils.getLocaleFromEnv()),3);
    			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "fail", ContextUtils.getLocaleFromEnv()));
    			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.COMMENT, 
    					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "memo", ContextUtils.getLocaleFromEnv()),4);
    			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "lowerexistsamenumber", ContextUtils.getLocaleFromEnv()));
    			return resultMap;
    		}
    	} catch(ObjectNotFoundException e){
    		logger.error("error",e);
    	}
    	
    	/*取下级方案及所有的模板信息*/
    	IBgScheme iBgScheme = BgSchemeFactory.getLocalInstance(ctx);
    	BgSchemeInfo childBgSchemeInfo = null;
    	try {
    		childBgSchemeInfo = iBgScheme.getBgSchemeInfo("select id where rootid = '"+info.getBgScheme().getRootId()+"' and orgUnit.id = '"+orgId+"'");
    		if (childBgSchemeInfo != null){
    			childBgSchemeInfo = iBgScheme.getBgSchemeInfo(new ObjectUuidPK(childBgSchemeInfo.getId().toString()), getBgSchemeSelectors());	
    		} else {
    			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_RESULT,
    					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignresult", ContextUtils.getLocaleFromEnv()),3);
    			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "fail", ContextUtils.getLocaleFromEnv()));
    			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.COMMENT, 
    					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "memo", ContextUtils.getLocaleFromEnv()),4);
    			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "lowerbgschemenotexist", ContextUtils.getLocaleFromEnv()));
    			//出报告
    			return resultMap;
    		}
    	} catch (ObjectNotFoundException e){
    		key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_RESULT,
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignresult", ContextUtils.getLocaleFromEnv()),3);
			resultMap.put(key,ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "fail", ContextUtils.getLocaleFromEnv()));
			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.COMMENT, 
					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "memo", ContextUtils.getLocaleFromEnv()),4);
			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "lowerbgschemenotexist", ContextUtils.getLocaleFromEnv()));
			//出报告
    		logger.error("error",e);
    		return resultMap;
    	}
    	childInfo  = new BgCalculateSchemeInfo();
    	childInfo.setParent(info);
    	childInfo.setRootId(info.getRootId());
    	childInfo.setOrgUnit(orgInfo);
    	childInfo.setBgScheme(childBgSchemeInfo);
    	childInfo.setPeriodNums(info.getPeriodNums());
    	childInfo.setCurrency(info.getCurrency());
    	childInfo.setName(info.getName());
    	childInfo.setNumber(info.getNumber());
    	childInfo.setDescription(info.getDescription());
    	BgCalculateSchemeEntryInfo childEntryInfo = null,entryInfo = null;
		BgCalculateSchemeEntryTemplateInfo childEntryTempInfo = null,entryTempInfo = null;
		BgCalculateSchemeTemSheetInfo childSheetInfo = null,sheetInfo = null;
		BgCalculateSchemePeriodInfo childPeriodInfo = null,periodInfo = null;
		BgTemplateInfo bgTemplateInfo = null;
    	if (childBgSchemeInfo.getBgSchemeNodes() != null && childBgSchemeInfo.getBgSchemeNodes().size() > 0){
    		for (int i =0,size = childBgSchemeInfo.getBgSchemeNodes().size();i<size;i++){
    			for (int j = 0,jn = info.getEntry().size();j<jn;j++){
    				/*模板rootId一致的为相同的模板*/
    				if (childBgSchemeInfo.getBgSchemeNodes().get(i).getBgTemplate().getRootId().equals(
    						info.getEntry().get(j).getTemplate().getTemplate().getRootId())){
    					bgTemplateInfo = childBgSchemeInfo.getBgSchemeNodes().get(i).getBgTemplate();
    					childEntryInfo = new BgCalculateSchemeEntryInfo();
    					entryInfo = info.getEntry().get(j);
    					childEntryInfo.setIsCalculate(entryInfo.isIsCalculate());
    					childEntryInfo.setIsCollect(entryInfo.isIsCollect());
    					childEntryInfo.setIsCollectFirst(entryInfo.isIsCollectFirst());
    					childEntryInfo.setPeriodNum(entryInfo.getPeriodNum());
    					childEntryInfo.setSeq(entryInfo.getSeq());
    					childEntryInfo.setCurrency(entryInfo.getCurrency());
    					childEntryTempInfo = new BgCalculateSchemeEntryTemplateInfo();
    					entryTempInfo = entryInfo.getTemplate();
    					childEntryInfo.setTemplate(childEntryTempInfo);
    					childEntryTempInfo.setTemplate(bgTemplateInfo);
    					childEntryTempInfo.setSeq(entryTempInfo.getSeq());
    					/*复制表页*/
    					if (entryTempInfo.getSheet()!= null && entryTempInfo.getSheet().size() >0){
    						for (int x = 0,xn = entryTempInfo.getSheet().size();x<xn;x++){
    							childSheetInfo = new BgCalculateSchemeTemSheetInfo();
    							sheetInfo = entryTempInfo.getSheet().get(x);
    							childSheetInfo.setSheetId(sheetInfo.getSheetId());
    							childSheetInfo.setSheetName(sheetInfo.getSheetName());
    							childSheetInfo.setSheetNumber(sheetInfo.getSheetNumber());
    							childEntryTempInfo.getSheet().add(childSheetInfo);
    						}
    					}
    					/*复制期间*/
    					if (entryTempInfo.getPeriod() != null && entryTempInfo.getPeriod().size() > 0){
    						for (int x = 0,xn = entryTempInfo.getPeriod().size();x<xn;x++){
    							periodInfo = entryTempInfo.getPeriod().get(x);
    							childPeriodInfo = new BgCalculateSchemePeriodInfo();
    							childPeriodInfo.setPeriod(periodInfo.getPeriod());
    							childPeriodInfo.setSeq(periodInfo.getSeq());
    							childEntryTempInfo.getPeriod().add(childPeriodInfo);
    						}
    					}
    					childInfo.getEntry().add(childEntryInfo);
    				}
    			}
    		}
    	}
    	
    	/*下级如果没有模板直接出报告返回*/
    	if (childInfo.getEntry().size() >0){
    		iBgCalculateScheme.save(childInfo);
    		/*分录size不一致为部分成功*/
    		if (childInfo.getEntry().size() != info.getEntry().size()){
    			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_RESULT, 
    					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignresult", ContextUtils.getLocaleFromEnv()),3);
    			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "partsuccess", ContextUtils.getLocaleFromEnv()));
        		key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.COMMENT, 
        				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "memo", ContextUtils.getLocaleFromEnv()),4);
        		resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "lowerparttempnotassign", ContextUtils.getLocaleFromEnv()));
    		} else {
    			key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_RESULT, 
    					ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignresult", ContextUtils.getLocaleFromEnv()),3);
    			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "success", ContextUtils.getLocaleFromEnv()));
        		key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.COMMENT, 
        				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "memo", ContextUtils.getLocaleFromEnv()),4);
        		resultMap.put(key, "");
    		}
    	} else {
    		key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.ASSIGN_RESULT, 
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "assignresult", ContextUtils.getLocaleFromEnv()),3);
			resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "fail", ContextUtils.getLocaleFromEnv()));
    		key = new BgCalcSchemeAssignMsgObj(BgCalcSchemeAssignMsgObj.COMMENT, 
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "memo", ContextUtils.getLocaleFromEnv()),4);
    		resultMap.put(key, ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "lowerbgschemenottemp", ContextUtils.getLocaleFromEnv()));
    	}
        return resultMap;
    }
    /**
     * 描述：组织汇总或者重计算，重算或批量重算均调用此接口
     * @author kaifei_yi
     */
    protected Object _reCalculate(Context ctx, Object param) throws BOSException, EASBizException {
    	List reportList = new ArrayList();

    	if (!(param instanceof Map))
    		return reportList;

    	/*取出所有的参数*/
    	Map paramMap = (Map)param;
    	String orgId           	= (String) paramMap.get(BgReCalcSchemeHelper.ORG_ID);
    	String bgSchemeId 		= (String) paramMap.get(BgReCalcSchemeHelper.BGSCHEME_ID);
    	String bgTemplateId 	= (String) paramMap.get(BgReCalcSchemeHelper.BGTEMPLATE_ID);
    	String bgPeriodId 		= (String) paramMap.get(BgReCalcSchemeHelper.BGPERIOD_ID);
    	String currencyId 		= (String) paramMap.get(BgReCalcSchemeHelper.CURREYCY_ID);
    	Boolean isCollect 		= (Boolean)paramMap.get(BgReCalcSchemeHelper.IS_COLLECT);
    	Boolean isCollectFirst 	= (Boolean)paramMap.get(BgReCalcSchemeHelper.IS_COLLECTFIRST);
    	Boolean isCalculate 	= (Boolean)paramMap.get(BgReCalcSchemeHelper.IS_CALCULATE);
    	List sheetIdList 		= (List)   paramMap.get(BgReCalcSchemeHelper.SHEETID_LIST);
    	Boolean isMultiOrg 		= (Boolean)paramMap.get(BgReCalcSchemeHelper.IS_MULTI_ORG);

    	/*参数校验*/
    	if (StringUtils.isEmpty(orgId) || StringUtils.isEmpty(bgSchemeId) || StringUtils.isEmpty(bgTemplateId)
    			|| StringUtils.isEmpty(bgPeriodId) || StringUtils.isEmpty(currencyId)){
    		return reportList;
    	}

    	/*取出所有的预算表*/
    	List bgFormList = queryBgForm(ctx, orgId, bgSchemeId, bgTemplateId, bgPeriodId, currencyId);

    	/*取出组织、模板、期间、信息，这些信息用于报告中*/
    	FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo("select id,name,number where id = '"+orgId+"'");
    	BgTemplateInfo bgTemplateInfo = BgTemplateFactory.getLocalInstance(ctx).getBgTemplateInfo("select id,name,number where id = '"+bgTemplateId+"'");
    	BgPeriodInfo bgPeriodInfo = BgPeriodFactory.getLocalInstance(ctx).getBgPeriodInfo("select id,name,number where id = '"+bgPeriodId+"'");

    	/*没有预算表则直接返回*/
    	if (bgFormList.isEmpty()) {
    		reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, null, 
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgformnotexist", ContextUtils.getLocaleFromEnv()), isMultiOrg.booleanValue()));
    		return reportList;
    	}

    	/*校验预算表状态*/
    	checkBgFormState(bgFormList, reportList, orgUnitInfo, bgTemplateInfo, bgPeriodInfo, isMultiOrg.booleanValue());

    	/*校验预算表是否在调整单中*/
    	checkBgFormInAdjustBill(ctx,bgFormList, reportList, orgUnitInfo, bgTemplateInfo, bgPeriodInfo, isMultiOrg.booleanValue());

    	if (bgFormList.size()>0){

    		String gather = ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "gather", ContextUtils.getLocaleFromEnv());
    		String recalc = ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "recalc", ContextUtils.getLocaleFromEnv());
    		String and = ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "and", ContextUtils.getLocaleFromEnv());
    		String success = ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "success", ContextUtils.getLocaleFromEnv());
    		String fail = ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "fail", ContextUtils.getLocaleFromEnv());
    		
    		IBgNFormFacade iBgNFormFacade = BgNFormFacadeFactory.getLocalInstance(ctx);
    		IBgFormFacade iBgFormFacade =  BgFormFacadeFactory.getLocalInstance(ctx);
    		IReport iReport = ReportFactory.getLocalInstance(ctx);
    		IBgGatherFacade iBgGatherFacade = BgGatherFacadeFactory.getLocalInstance(ctx);
    		Map adjustMap = null;
    		UserInfo userInfo = (UserInfo) ContextUtil.getCurrentUserInfo(ctx);
    		IObjectPK userPK = new ObjectUuidPK(userInfo.getId());
    		IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
    		List bgFormIds = new ArrayList();
    		BOSUuid ctrlUnitId = ContextUtil.getCurrentCtrlUnit(ctx).getId();
    		Map adjustExpressionMap = new HashMap();  //取数公式的调整数据集合
    		/*
    		 *因为重计算和汇总时 adjustMap，adjustExpressionMap共用又没有清空导致汇总时有数据变化保存了一次预算表，重计算时没变化又保存了一次，影响性能。
    		 * 增加两个变量记录adjustMap，adjustExpressionMap是否为空。以及checkMap用于调整检查，用于最后的信息提示。每次汇总和重计算完成后清空 adjustMap，adjustExpressionMap
    		 * by coco_hu at 20121221 邮储性能优化 
    		 */
    		Object[] changSign = new Object[]{Boolean.FALSE, Boolean.FALSE}; //adjustMap，adjustExpressionMap是否为空
    		Map checkMap = new HashMap(); //需要进行调整检查的数据

    		for (int i = 0,size = bgFormList.size();i<size;i++){
    			isGatherForm = false;
    			BgFormInfo bgFormInfo = null;
    			try {
    				bgFormInfo = (BgFormInfo) bgFormList.get(i);
    				/*是否需要调整表*/
    				boolean needAdjustForm = (bgFormInfo.getState().getValue() == BgFormStateEnum.APPROVED_VALUE);

    				/*取出预算表的初始数据key:formulaStr,value:BigDecimal*/
    				Map initValueMap = iBgNFormFacade.getInitBudgetData(bgFormInfo.getId().toString());

    				IObjectPK bgFormPK = null;
    				if (needAdjustForm){
    					Map resultMap = iBgFormFacade.adjustBgForm(bgFormInfo.getId());
    					if (resultMap == null || resultMap.isEmpty())
    						return reportList;
    					bgFormPK = (IObjectPK)resultMap.get("pk");
    				} else {
    					bgFormPK = new ObjectUuidPK(bgFormInfo.getId());
    				}

    				adjustMap = new HashMap();
    				String result = "";
    				Object[] resultObj = null;
    				Boolean isGather = null;
    				Book newBook = null;
    				BgFormInfo info = null;
    				if (isCollect.booleanValue()){
    					if (isCollectFirst.booleanValue()){
    						/*汇总*/
    						resultObj = gather(ctx, iBgGatherFacade, bgFormPK,initValueMap, adjustMap,adjustExpressionMap, userPK,needAdjustForm,sheetIdList, bgFormInfo);
    						isGather = (Boolean)resultObj[0];
    						if (isGather.booleanValue() && resultObj[1] != null && resultObj[2] != null)
    						{
    							newBook = (Book)resultObj[1];
    							info = (BgFormInfo)resultObj[2];
    							/*设置最新的book*/
    							info.setBook(newBook);
    							BgNFSHelper.setBudget(info);
    							/*保存表*/
    							iBgNFormFacade.updateReportInfo(info);
    							
    							result = gather;
    							if(needAdjustForm){
    								checkMap.putAll(adjustMap);
    							}
    							setChangeSign(adjustMap, adjustExpressionMap, changSign);
    						}
    						if (isCalculate.booleanValue()){
    							/*计算*/
    							resultObj = calculate(ctx, iReport, bgFormPK, adjustMap,initValueMap,adjustExpressionMap, userPK, needAdjustForm);
    							if(resultObj != null && resultObj[0] != null && resultObj[1] != null)
    							{
    				    				newBook = (Book)resultObj[0];
    				    				info = (BgFormInfo)resultObj[1];
    				    				//记录差异值
    				    				processData(ctx,newBook, info.getBook(), info, initValueMap, adjustMap,adjustExpressionMap, userPK, needAdjustForm);
    							}
    							
    							if(needAdjustForm){
    								checkMap.putAll(adjustMap);
    							}
    							setChangeSign(adjustMap, adjustExpressionMap, changSign);
    							if (result.length() >0){
    								result += and;
    							}
    							result += recalc;	
    						}
    						tempVarMap.clear();
    					} else {
    						if (isCalculate.booleanValue()){
    							/*计算*/
    							resultObj = calculate(ctx, iReport, bgFormPK, adjustMap,initValueMap,adjustExpressionMap, userPK, needAdjustForm);
//    							
    							if(resultObj != null && resultObj[0] != null && resultObj[1] != null)
    							{
    				    				newBook = (Book)resultObj[0];
    				    				info = (BgFormInfo)resultObj[1];
    				    				/*设置最新的book*/
    				    				info.setBook(newBook);
    				    				BgNFSHelper.setBudget(info);
    				    				/*保存表*/
    				    				iBgNFormFacade.updateReportInfo(info);
    							}
    							
    							if(needAdjustForm){
    								checkMap.putAll(adjustMap);
    							}
    							setChangeSign(adjustMap, adjustExpressionMap, changSign);
    							result = recalc;
    						}
    						/*汇总*/
    						resultObj = gather(ctx, iBgGatherFacade, bgFormPK,initValueMap, adjustMap,adjustExpressionMap, userPK,needAdjustForm,sheetIdList, bgFormInfo);
    						isGather = (Boolean)resultObj[0];
    						if (isGather.booleanValue() && resultObj[1] != null && resultObj[2] != null)
    						{
    							newBook = (Book)resultObj[1];
    							info = (BgFormInfo)resultObj[2];
    							
    							processData( ctx,newBook, info.getBook(), info, initValueMap, adjustMap,adjustExpressionMap, userPK, needAdjustForm);
    							if(needAdjustForm){
    								checkMap.putAll(adjustMap);
    							}
    							setChangeSign(adjustMap, adjustExpressionMap, changSign);
    							if (result.length() >0){
    								result += and;
    							} 
    							result += gather;
    						}else
    						{
    							processData( ctx,newBook, info.getBook(), info, initValueMap, adjustMap,adjustExpressionMap, userPK, needAdjustForm);
    							if(needAdjustForm){
    								checkMap.putAll(adjustMap);
    							}
    							setChangeSign(adjustMap, adjustExpressionMap, changSign);
    							if (result.length() >0){
    								result += and;
    							} 
    							result += gather;
    						}
    						tempVarMap.clear();
    					}
    				} else if (isCalculate.booleanValue()){/*不汇总的表判断是否需要重计算*/
    					/*计算*/
    					resultObj = calculate(ctx, iReport, bgFormPK, adjustMap,initValueMap,adjustExpressionMap, userPK, needAdjustForm);
					if(resultObj != null && resultObj[0] != null && resultObj[1] != null)
					{
		    				newBook = (Book)resultObj[0];
		    				info = (BgFormInfo)resultObj[1];
		    				processData( ctx,newBook, info.getBook(), info, initValueMap, adjustMap,adjustExpressionMap, userPK, needAdjustForm);
					}
    					if(needAdjustForm){
							checkMap.putAll(adjustMap);
						}
    					setChangeSign(adjustMap, adjustExpressionMap, changSign);
    					result = recalc;
    				}

    				if (needAdjustForm) {
    					if (!((Boolean)changSign[0]).booleanValue()&& !((Boolean)changSign[1]).booleanValue()){
    						reportList .add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    								result+success+"，"+
    								ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "datanotchanged", ContextUtils.getLocaleFromEnv()), isMultiOrg.booleanValue()));
    						deleteAdjustForm(iBgForm, bgFormIds, bgFormPK.toString(), ctrlUnitId, BOSUuid.read(userPK.toString()));

    					} else {
    						/*表页检查*/
    						if(!reportCheck(ctx, bgFormPK.toString())){
    							reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    									result+fail+"，"+
    									ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "reportchecknotpassed", ContextUtils.getLocaleFromEnv())
    									, isMultiOrg.booleanValue()));
    							deleteAdjustForm(iBgForm, bgFormIds, bgFormPK.toString(), ctrlUnitId, BOSUuid.read(userPK.toString()));
    							continue;
    						}

    						/*调整检查*/
    						if (!adjustCheck(ctx, bgFormPK.toString(), checkMap)){
    							reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    									result+fail+"，"+
    									ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "adjustchecknotpassed", ContextUtils.getLocaleFromEnv())
    									, isMultiOrg.booleanValue()));
    							deleteAdjustForm(iBgForm, bgFormIds, bgFormPK.toString(), ctrlUnitId, BOSUuid.read(userPK.toString()));
    							continue;
    						}

    						/*勾稽关系检查*/
    						if (!examineCheck(ctx, bgFormPK.toString(),bgFormInfo.getXNum())){
    							reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    									result+fail+"，"+
    									ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "examinechecknotpassed", ContextUtils.getLocaleFromEnv())
    									, isMultiOrg.booleanValue()));
    							deleteAdjustForm(iBgForm, bgFormIds, bgFormPK.toString(), ctrlUnitId, BOSUuid.read(userPK.toString()));
    							continue;
    						}

    						/*审批调整表*/
    						auditAdjustForm(iBgFormFacade, BOSUuid.read(bgFormPK.toString()), bgFormInfo.getId(), userInfo.getId());

    						reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, 
    								bgFormInfo,result+"，"+
    								ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "datahaschanged", ContextUtils.getLocaleFromEnv())
    								, isMultiOrg.booleanValue()));
    					}
    				} else {
    					if (!((Boolean)changSign[0]).booleanValue() && !((Boolean)changSign[1]).booleanValue()){
    						reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    								result+success+"，"+
    								ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "datanotchanged", ContextUtils.getLocaleFromEnv())
    								, isMultiOrg.booleanValue()));
    					} 
    					//add by hehaijiang  当预算表项目公式的数据没有变化，但是其它单元格有变化时也须要保存数据
    					else if(!((Boolean)changSign[0]).booleanValue() && ((Boolean)changSign[1]).booleanValue())
    					{
    						reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, 
    								bgFormInfo,result+"，"+
    								ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "datahaschanged", ContextUtils.getLocaleFromEnv())
    								, isMultiOrg.booleanValue()));
    					}
    					else {
    						reportList.add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    								result+success+"，"+
    								ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "datahaschanged", ContextUtils.getLocaleFromEnv())
    								, isMultiOrg.booleanValue()));
    					}
    				}
    				adjustMap.clear();
    				adjustMap = null;
    				adjustExpressionMap.clear();
    				adjustExpressionMap = null;
    				checkMap.clear();
    				checkMap = null;
    				tempVarMap.clear();
    			} catch(Exception e){
    				logger.error("error",e);
    				reportList .add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    						ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "systemerror", ContextUtils.getLocaleFromEnv())
    						+e.getStackTrace(), isMultiOrg.booleanValue()));
    			}
    		}
    	}
    	return reportList;
    }
    
	/**
	 * 描述：提供差异记录的减法计算
	 */
	private BigDecimal compute(BigDecimal minuend, BigDecimal subtrahend) {
		if (minuend == null && subtrahend == null)
			return BgConstants.BIGZERO;
		
		if (minuend != null && subtrahend == null)
			return minuend;
		
		if (minuend == null && subtrahend != null)
			minuend = BgConstants.BIGZERO;
		
		return minuend.subtract(subtrahend);
	}
    
    private void setChangeSign(Map adjustMap, Map adjustExpressionMap, Object[] changSign) {
		if(adjustMap != null && adjustMap.size() > 0) adjustMap.clear();
		
		if(tempVarMap != null && tempVarMap.size() > 0){
			changSign[0] = Boolean.TRUE;
		}else {
			changSign[0] = Boolean.FALSE;
		}
		
		if(adjustExpressionMap != null && adjustExpressionMap.size() > 0){
			changSign[1] = Boolean.TRUE;
			adjustExpressionMap.clear();
		}
	}
    
    /*校验预算表是否正在参与预算调整单*/
    private void checkBgFormInAdjustBill(Context ctx,List bgFormList,List reportList,FullOrgUnitInfo orgUnitInfo,
    		BgTemplateInfo bgTemplateInfo,BgPeriodInfo bgPeriodInfo,boolean isMultiOrg) {
    	if (bgFormList == null || bgFormList.isEmpty()){
    		return ;
    	}
    	/*检查预算表是否已存在未审批的调整单，存在则不能做调整*/
    	StringBuffer checkHasAdjustBillSql = new StringBuffer("");
    	checkHasAdjustBillSql.append("select t.FID, t.FNumber from T_BG_BgAdjustBill t " +
    	"left outer join T_BG_BgAdjustBillEntry e on e.FAdjustBillID = t.FID");
    	checkHasAdjustBillSql.append(" where (t.FState <> " + BgAdjustStateEnum.APPROVED_VALUE + ") and e.FBgFormID = ? ");
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	try {
    		conn = getConnection(ctx); 

    		if (bgFormList.size() >0){
    			pstmt = conn.prepareStatement(checkHasAdjustBillSql.toString());
    			BgFormInfo bgFormInfo = null;
    			for (int i = bgFormList.size()-1;i>=0;i--){
    				bgFormInfo = (BgFormInfo)bgFormList.get(i);
    				/*不是已审批的表不需要校验*/
    				if (bgFormInfo.getState().getValue() != BgFormStateEnum.APPROVED_VALUE)
    					continue;
    				pstmt.setString(1, bgFormInfo.getId().toString());
    				rs = pstmt.executeQuery();
    				if (rs.next()){
    					reportList .add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, bgFormInfo, 
    							ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgforminadjustbill", ContextUtils.getLocaleFromEnv())
    							, isMultiOrg));
    					bgFormList.remove(i);//参与调整单的表需要移除
    				}
    				SQLUtils.cleanup(rs);
    			}
    		} 

    	} catch (Exception e){
    		logger.error("error",e);
    	} finally {
    		SQLUtils.cleanup(rs,pstmt,conn);
    	}
    }
    
    /*校验预算表状态,状态为编制中或已审批的才能进行汇总及重计算*/
    private void checkBgFormState(List bgFormList,List reportList,FullOrgUnitInfo orgUnitInfo,BgTemplateInfo bgTemplateInfo,
    		BgPeriodInfo bgPeriodInfo,boolean isMultiOrg) {
    	if (bgFormList == null || bgFormList.isEmpty()){
    		return;
    	}
    	BgFormInfo info = null;
    	for (int i = bgFormList.size()-1;i>=0;i--){
    		info = (BgFormInfo) bgFormList.get(i);
    		if (info.getState().getValue() == BgFormStateEnum.EDITED_VALUE|| info.getState().getValue() == BgFormStateEnum.CERTIFICATE_VALUE
    				|| info.getState().getValue() == BgFormStateEnum.APPROVED_VALUE){
				
			} else {
				reportList .add(createReport(orgUnitInfo, bgTemplateInfo, bgPeriodInfo, info, 
						ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgformstateinvalid", ContextUtils.getLocaleFromEnv())
						+info.getState().getAlias()+")。", isMultiOrg));
				bgFormList.remove(i);
			}
    	}
    }
    
    /*查询所有的预算表*/
    private List queryBgForm(Context ctx,String orgId,String bgSchemeId,String bgTemplateId,String bgPeriodId,String currencyId){
    	
    	StringBuffer queryBgFormSql = new StringBuffer();
    	queryBgFormSql.append(" select fid,fadjustid,fstate,fname,fnumber,fdatasource,fxnum from t_bg_bgform where forgunitid = ? and fbgschemeid = ? ");
    	queryBgFormSql.append(" and fbgtemplateid = ? and fbgperiodid = ? and fcurrencyid = ? and fstate not in (5,15)");
    	queryBgFormSql.append(" and fid not in (select fadjustId from t_bg_bgform where fstate in (4,35))");
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	List bgFormList = new ArrayList();
    	try {
    		conn = getConnection(ctx); 
    		pstmt = conn.prepareStatement(queryBgFormSql.toString());
    		pstmt.setString(1, orgId);
    		pstmt.setString(2, bgSchemeId);
    		pstmt.setString(3, bgTemplateId);
    		pstmt.setString(4, bgPeriodId);
    		pstmt.setString(5, currencyId);
    		rs = pstmt.executeQuery();
    		int state = 0;
    		BgFormInfo bgFormInfo = null;
    		
    		/*由于专项预算表，同组织、同方案、同模板、同期间、同币别可能会存在多张预算表*/
    		while (rs.next()){
    			bgFormInfo = new BgFormInfo();
    			bgFormInfo.setName(rs.getString("fname"));
    			bgFormInfo.setNumber(rs.getString("fnumber"));
    			bgFormInfo.setDataSource(BgFormDataSourceEnum.getEnum(rs.getInt("fdatasource")));
    			bgFormInfo.setId(BOSUuid.read(rs.getString("fid")));
    			state = rs.getInt("fstate");
    			bgFormInfo.setState(BgFormStateEnum.getEnum(state));
    			bgFormInfo.setXNum(rs.getString("fxnum"));
    			bgFormList.add(bgFormInfo);
    		}
    	} catch (Exception e){
    		logger.error("error",e);
    	} finally {
    		SQLUtils.cleanup(rs,pstmt,conn);
    	}
    	return bgFormList;
    }
    
    /*审批调整表*/
    private void auditAdjustForm(IBgFormFacade iBgFormFacade,BOSUuid bgFromID,BOSUuid adjsutID,BOSUuid userID) throws BOSException, EASBizException  {
    	//检查通过调用调整表审批接口进行审批操作，生成历史数据
		iBgFormFacade.auditFormAdjust(bgFromID);
		iBgFormFacade.submitAuditIdea(adjsutID, userID, BgAuditIdeaEnum.ADJUSTPASS, 
				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "autorecalcautidpassed", ContextUtils.getLocaleFromEnv()));
    }
    
    /*删除调整表*/
    private void deleteAdjustForm(IBgForm iBgForm,List bgFormIds,String bgFormId,BOSUuid ctrlUnitId,BOSUuid userID) throws BOSException, EASBizException  {
    	bgFormIds.clear();
    	bgFormIds.add(bgFormId);
//		iBgForm.batchRemove(bgFormIds, ctrlUnitId, userID);
    	BOSUuid[] ids = new BOSUuid[bgFormIds.size()];
    	for(int i=0; i<bgFormIds.size(); i++){
    		ids[i] = BOSUuid.read((String) bgFormIds.get(i)); 
    	}
    	iBgForm.batchRemove(ids);
    }
    
    /*报表检查*/
    private boolean reportCheck(Context ctx,String bgFormId) throws BOSException, EASBizException{
    	if (BgParamNHelper.isCompulsionReportCheck(ctx)){
    		BOSUuid[] uuid = new BOSUuid[1];
        	uuid[0] = BOSUuid.read(bgFormId);
        	IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
        	List check = iBgForm.reportCheck(uuid);
        	if (check.size() >= 3 && check.get(2) != null){
        		List nopass = (List) check.get(2);
        		if (nopass != null && nopass.size() > 0){
        			return false;
        		}
        	}
    		return true;
    	} else {
    		return true;
    	}
    }
    
    /*调整检查*/
    private boolean adjustCheck(Context ctx,String bgFormId,Map adjustMap) throws BOSException, EASBizException{
    	/*无差异记录不需要调整检查*/
    	if (adjustMap.isEmpty())
    		return true;
    	
    	if (BgSHelper.isAdjustCheckRequired(ctx)){
    		Map map = new HashMap();
    		map.put(BOSUuid.read(bgFormId), adjustMap);
    		List checkData = BgCollectFormFactory.getLocalInstance(ctx).getAllNegativeAdjustCheckData(map);
    		if (checkData == null || checkData.isEmpty())
    			return true;
    		return false;
    	} else  {
    		return true;
    	}
    }
    
    /*勾稽关系检查*/
    private boolean examineCheck(Context ctx,String bgFormId,String xNum) throws BOSException, EASBizException {
    	IBgExamineFacade iExamine = BgExamineFacadeFactory.getLocalInstance(ctx);
    	BOSUuid[] uuid = new BOSUuid[1];
    	uuid[0] = BOSUuid.read(bgFormId);
    	if (iExamine.canExamine(uuid)){
    		Object[] result = iExamine.examines(uuid, xNum);
    		return ((Boolean)result[0]).booleanValue();
    	}
    	return true;
    }
    
    private SelectorItemCollection getGahterSelectors() {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("id");
    	sic.add("data");
    	sic.add("dataSource");
    	sic.add("state");
     	sic.add("adjustMapData");
     	sic.add("adjust.id");
     	sic.add("orgUnit.id");
    	sic.add("bgScheme.id");
    	sic.add("bgScheme.rootId");
    	sic.add("bgTemplate.rootId");
    	sic.add("bgTemplate.formType");
    	sic.add("bgPeriod.id");
    	sic.add("currency.id");
    	sic.add("creator.id");
    	sic.add("lastUpdateUser.id");
    	return sic;
    }
    
    /*组织汇总*/
    private Object[] gather(Context ctx,IBgGatherFacade iBgGatherFacade,IObjectPK bgFormPK,
    		Map initValueMap,Map adjustMap,Map adjustExpressionMap,IObjectPK userPK,boolean needAdjustForm,List sheetIdList,BgFormInfo sourceFormInfo )  throws BOSException, EASBizException 
    {
	Object[] result = new Object[3];
    	IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
    	BgFormInfo info = iBgForm.getBgFormInfo(bgFormPK, getGahterSelectors());
		Book _oldbook = null;
		try {
			_oldbook = info.getBook();
		} catch (Exception e) {
			logger.error(e);
		}
		if (_oldbook != null){
			Map orgUnitMap = null;
			Map bgSchemeIdsMap = null;
			Map bgFormIdsMap = null;
			List orgUnitSort = null;
			Boolean IS_GATHER_ADJUST = null;
			
			/*判断是否有做过汇总*/
			Object obj = _oldbook.getUserObjectValue(BgNConstants.GATHER_ORGUNIT);
			/*
			 * coco_hu at 20121228 
			 * 此段代码错误，罗列汇总是汇同方案同模板的表。
			 * 此处只根据方案进行过滤。回归R120627-0285
			 */
			//add by hhj  没有做过汇总且当前的预算表是罗列表,则根据当前组织的预算表获取下级组织的预算方案,组织单元,预算表等信息
			/*if(obj == null && info.getBgTemplate().getFormType().equals(BgTemFormTypeEnum.ENUMERATEFORM))
			{
				orgUnitMap = new HashMap();
				bgSchemeIdsMap = new HashMap();
				bgFormIdsMap  = new HashMap();
				IS_GATHER_ADJUST = false;
				
				StringBuffer sb = new StringBuffer(); 
				sb.append("select t0.fid,t1.fid,t2.fid,t2.fname_l2 from t_bg_bgform t0 inner join t_bg_bgscheme t1 on t0.fbgschemeid = t1.fid ");
				sb.append("inner join t_org_baseunit t2 on t0.forgunitid = t2.fid ");
				sb.append("where t1.FParentSchemeID = ? order by t2.fnumber");
				
				IRowSet rs = DbUtil.executeQuery(ctx, sb.toString(), new Object[]{info.getBgScheme().getId().toString()});
				try {
					while(rs.next())
					{
						orgUnitMap.put(rs.getString(3), rs.getString(4));
						bgSchemeIdsMap.put(rs.getString(3), rs.getString(2));
						bgFormIdsMap.put(rs.getString(3), rs.getString(1));
					}
				} catch (SQLException e) {
					throw new BOSException(e);
				}
			}
			else*/ if (obj == null || !(obj instanceof Map)) {
				this.isGatherForm = false;
				result[0] = new Boolean(false);
				return result;
			}
			else
			{
				this.isGatherForm = true;
				orgUnitMap = (Map) _oldbook.getUserObjectValue(BgNConstants.GATHER_ORGUNIT);
				bgSchemeIdsMap = (Map)_oldbook.getUserObjectValue(BgNConstants.GATHER_SCHEME);
				bgFormIdsMap = (Map)_oldbook.getUserObjectValue(BgNConstants.GATHER_BGFORM);
				orgUnitSort = (List)_oldbook.getUserObjectValue(BgNConstants.GATHER_ORGUNIT_SORT);
				IS_GATHER_ADJUST = (Boolean)_oldbook.getUserObjectValue(BgNConstants.IS_GATHER_ADJUST);
			}
			
			Map parameter = new HashMap();
			parameter.put(BgGatherHelper.GATHER_TYPE, Boolean.TRUE);
			parameter.put(BgGatherHelper.GATHER_CELL, Boolean.FALSE);
			parameter.put(BgGatherHelper.GATHER_FORMULA, Boolean.TRUE);
			parameter.put(BgGatherHelper.GATHER_ENUMERATE,info.getBgTemplate().getFormType().getValue()== BgTemFormTypeEnum.ENUMERATEFORM_VALUE ? Boolean.TRUE : Boolean.FALSE );
			parameter.put(BgGatherHelper.GATHER_TMPDATA, IS_GATHER_ADJUST);
			if (sheetIdList != null && sheetIdList.size() > 0){
				Map sheetId2ID = new HashMap();
				Sheet sheet = null;
				for (int sheetIndex = 0,sheetCount = _oldbook.getSheetCount();sheetIndex<sheetCount;sheetIndex++){
					sheet = _oldbook.getSheet(sheetIndex);
					sheetId2ID.put(BgNFSHelper.getSheetSign(sheet),sheet.getID());
				}
				parameter.put(BgGatherHelper.GATHER_SHEET, Boolean.TRUE);
				SheetInfo[] sheetInfos = new SheetInfo[sheetIdList.size()];
				for (int i = 0,size = sheetIdList.size();i<size;i++){
					sheetInfos[i] = new SheetInfo();
					if (sheetId2ID.containsKey((String)sheetIdList.get(i))){
						sheetInfos[i].setSheetId((String)sheetId2ID.get((String)sheetIdList.get(i)));
					}
				}
				parameter.put(BgGatherHelper.GATHER_SHEET_ID, sheetInfos);//汇总表页的sheetId
				sheetId2ID.clear();
			} else {
				parameter.put(BgGatherHelper.GATHER_SHEET, Boolean.FALSE);
			}
			parameter.put(BgGatherHelper.GATHER_CLEAR_FORMULA,Boolean.FALSE);//是否清空内部公式，和需求讨论后暂时传false
			parameter.put(BgGatherHelper.GATHER_SORT, orgUnitSort);//组织排序
			parameter.put(BgGatherHelper.GATHER_SCHEME_FORMULA, Boolean.FALSE);//是否汇总其他明细，和需求讨论后暂时传false
//			parameter.put(BgGatherHelper.GATHER_TYPE_EXT, "");此属性用于实际数增量汇总,此处可以不传
			info = iBgGatherFacade.getGatherValue(info, orgUnitMap, bgSchemeIdsMap, bgFormIdsMap, parameter);
			try {
				result[1] = info.getBook();
				result[2] = info;
				//罗列表组织汇总不能在这处理，会导致调整时新增项目单元格颜色没有变化  coco_hu at 20121221
//				adjustMap = BgNAdjHelper.getTmpDataOfFormula(info, adjustMap);
//				processData(ctx,info.getBook(), _oldbook, info, initValueMap, adjustMap,adjustExpressionMap, userPK, needAdjustForm);
			} catch (Exception e) {
				logger.error("error",e);
			}
		}
		result[0] = new Boolean(true);
		return result;
    }
    
    /*后台计算*/
    private Object[] calculate(Context ctx,IReport iReport,IObjectPK bgFormPK,Map adjustMap,Map initValueMap,Map adjustExpressionMap,IObjectPK userPK,boolean needAdjustForm ) throws BOSException, EASBizException{
	        IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
		BgFormInfo info = iBgForm.getBgFormInfo(bgFormPK, BgNFSHelper.getSelectors());
		info.put("myBgPeriod",info.getBgPeriod());
		Object[] result = new Object[2];
		byte[] newData = iReport.backCacl(info, info.getZipData()); 
//		info.setData(newData);
		Book _book = null;
		try {
			_book = IOHelper.unpackBook(newData);
			Book _oldbook = info.getBook();
			/*判断是否有做过汇总*/
			Object obj = _oldbook.getUserObjectValue(BgNConstants.GATHER_ORGUNIT);
			if (obj == null || !(obj instanceof Map)) {
				this.isGatherForm = false;
			}
			else
			{
				this.isGatherForm = true;
			}
			result[0] = _book;
			result[1] = info;
			return result;
//			processData( ctx,_book, info.getBook(), info, initValueMap, adjustMap,adjustExpressionMap, userPK, needAdjustForm);
		} catch (Exception e) {
			logger.error("error",e);
		}
		return result;
    }
    
    /*处理汇总后或重计算后的差异数据*/
    private void processData( Context ctx, Book _newBook,Book _oldBook,BgFormInfo info ,Map initValueMap,
    		Map adjustMap,Map adjustExpressionMap,IObjectPK userPK,boolean needAdjustForm) throws BOSException, EASBizException{
    	BgNFSHelper.disableCal(_newBook);
		BgNFSHelper.disableUndo(_newBook);
    	if (_newBook != null){
			if (needAdjustForm){ 
				updateChangeValueInfo(_newBook,_oldBook, initValueMap, adjustMap,adjustExpressionMap, userPK,true);
				if (adjustMap != null && !adjustMap.isEmpty()) {
					//存储adjustMap						
					ByteArrayOutputStream baos = null;
					ObjectOutputStream oos = null;
					try {
						baos = new ByteArrayOutputStream();
						oos = new ObjectOutputStream(baos);
						oos.writeObject(adjustMap);
						info.setAdjustMapData(ZipUtil.pack(baos));
					} catch (Exception e){
						logger.error("error",e);
					}  finally {
						try {
							if (baos != null)
								baos.close();
							if (oos != null)
								oos.close();
						} catch (Exception e){
							logger.error("error",e);
						}
					}
				}
				
			} else {
				/*编制中的表需要记录差异Map但不需要改变单元格的颜色，差异Map用来判断重计算后是否有数据变动(在重计算报告中现实)*/
				updateChangeValueInfo(_newBook,_oldBook,initValueMap, adjustMap,adjustExpressionMap, userPK,false);
//				recoverTables(_book);
			}
			try {
				IBgNFormFacade iBgNFormFacade = BgNFormFacadeFactory.getLocalInstance(ctx);
				if (!adjustMap.isEmpty()) {
					/*设置最新的book*/
					info.setBook(_newBook);
					BgNFSHelper.setBudget(info);
					/*保存数据*/
					iBgNFormFacade.saveBgData(info, adjustMap, initValueMap);
					/*更新initMap 使数据不重复保存*/
					if(!adjustMap.isEmpty()){
						String formula = "";
						Object valueObj = "";
						for(Iterator iterator = adjustMap.keySet().iterator(); iterator.hasNext(); ){
							formula = iterator.next().toString();
							valueObj = adjustMap.get(formula);
							if(valueObj instanceof BgFormDiversityData){
								initValueMap.put(formula, ((BgFormDiversityData)valueObj).getNewValue());
							}
						}
					}
					
					/*保存表*/
					iBgNFormFacade.updateReportInfo(info);
				}else if(adjustMap.isEmpty() && adjustExpressionMap.size() > 0)
				{
					/*设置最新的book*/
					info.setBook(_newBook);
					BgNFSHelper.setBudget(info);
					/*保存表*/
					iBgNFormFacade.updateReportInfo(info);
				}
			} catch (IOException e) {
				logger.error("error",e);
			}
		}
    }
    
    
    private void updateChangeValueInfo(Book _newBook, Book _oldBook, Map initValueMap,Map adjustMap,Map adjustExpressionMap,IObjectPK userPK,boolean needChangeColor ) {
		if (_newBook == null || adjustMap == null)
			return;
		Sheet _sheet = null;
		
		for(int index=0, size=_newBook.getSheetCount(); index<size; index++) {
			_sheet = _newBook.getSheet(index);
			
			updateSheetChangeValueInfo(initValueMap,_oldBook, adjustMap,adjustExpressionMap,_sheet, userPK, needChangeColor);
		}
	}
	
    private void updateSheetChangeValueInfo(Map initValueMap, Book _oldBook, Map adjustMap ,Map adjustExpressionMap,Sheet sheet,IObjectPK userPK,boolean needChangeColor ) {
		if (sheet == null)
			return ;
		
		Cell _cell = null;
		
		int rowCount = sheet.getMaxRowIndex();
		int colCount = sheet.getMaxColIndex();
		
		for(int rowIndex=0; rowIndex<=rowCount; rowIndex++) {
			for(int colIndex=0; colIndex<=colCount; colIndex++) {
				_cell = sheet.getCell(rowIndex, colIndex, false);
				updateCellChangeValueInfo(initValueMap,_oldBook, adjustMap,adjustExpressionMap,sheet, _cell, userPK, needChangeColor);
			}
		}
	}
	
    private void updateCellChangeValueInfo(Map initValueMap, Book _oldBook, Map adjustMap,Map adjustExpressionMap,Sheet sheet,Cell cell,IObjectPK userPK, boolean needChangeColor ) {
		if (cell == null)
			return ;
		BgFormDiversityData divData = null;
		if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
			List _delFormulaList = new ArrayList();
			Object _obj = null;
			BigDecimal _newValue = null, _oldValue = null,_adjOldValue = null,_adjNewValue = null;
			String _formulaStr = null;
			_formulaStr = BgNFSHelper.getFormulaOfCell(cell);
			_obj = BgNFSHelper.getValue(cell);
			if (_obj instanceof BigDecimal) {
				_newValue = (BigDecimal) _obj;
			} else {
				try {
					_newValue = new BigDecimal(_obj.toString());
				} catch(Exception ex) {
					/* 如果有项目公式的单元格不是数值则设置为默认值零 */
					BgNFSHelper.setValue(cell, BgNConstants.ZERO);
					_newValue = BgNConstants.ZERO;
				}
			}
			
			/* 如果能查询到项目公式则比较初始值 */
			if (initValueMap.containsKey(_formulaStr)) {
				//add by xignquan_xu 2014-3-10  解决前一次操作结果对后面操作的影响
				if(tempVarMap.containsKey(_formulaStr)){
					divData = (BgFormDiversityData) tempVarMap.get(_formulaStr);
					_adjOldValue = divData.getOldValue();
					_adjNewValue = divData.getNewValue();
					_newValue = _newValue.setScale(SCALE, RoundingMode.HALF_UP);
					_adjNewValue = _adjNewValue.setScale(SCALE, RoundingMode.HALF_UP);
					if(_adjNewValue.compareTo(_newValue) != 0){
						_oldValue = _adjNewValue;
					}else {
						_oldValue = _adjOldValue;
					}
					//如果最后得到的值与最初的数值相等，则认为没有变更
					if(_newValue.compareTo(_adjOldValue) == 0){
						_delFormulaList.add(_formulaStr);
					}
				} else {
					_oldValue = (BigDecimal) initValueMap.get(_formulaStr);
				}
				
				
				//add by hehaijiang 精度值与数据库里的小数位同步
				_newValue = _newValue.setScale(SCALE,BigDecimal.ROUND_HALF_UP);
				_oldValue = _oldValue.setScale(SCALE,BigDecimal.ROUND_HALF_UP);
				
				/* 如果当前值与初始值不相等则认为是数值产生了变化  */
				if (_newValue.compareTo(_oldValue) != 0) {
					if (adjustMap.containsKey(_formulaStr)) {
						updateDivInfo(cell, _formulaStr, _newValue,adjustMap, userPK, needChangeColor);
					} else {
						createDivInfo(cell, _formulaStr, _newValue, _oldValue,adjustMap, userPK, needChangeColor);
					}
					BgNFSHelper.setBudgetValue(cell, _newValue);
				} else {
					removeDivInfo(cell, _formulaStr,adjustMap);
					BgNFSHelper.setBudgetValue(cell, _oldValue);
				}
				//删除没有变更的记录
				for(int i=0,j=_delFormulaList.size();i<j;i++){
					tempVarMap.remove(_delFormulaList.get(i));
				}
			}
			//重计算，不存在新增项目公式
			else {//罗列表组织汇总存在新增项目公式  coco_hu 
				// 如果不能查询到项目公式则判定为新增项目公式 
				createDivInfo(cell, _formulaStr, _newValue, BgNConstants.ZERO,adjustMap, userPK, needChangeColor);
			}
		}
		//单元格只有取数公式，没有项目公式，数据有调整，则记录到adjustExpressionMap集合里
		else if(BgNFSHelper.checkHasExpression(cell))
		{
			int row = cell.getRow();
			int col = cell.getCol();
			int sheetIndex = sheet.getIndex();
			try {
				Variant oldValue = _oldBook.getSheet(sheetIndex).getCell(row, col, false).getValue();
				if(!cell.getValue().equals(oldValue))
				{
					adjustExpressionMap.put(BgNFSHelper.getExpression(cell), cell.getValue());
				}
			} catch (Exception e) 
			{
				logger.error(e.toString());
			}
		}
	}
	
	private void removeDivInfo(Cell cell, String formulaStr,Map adjustMap) {
		/* 清除差异记录 */
		adjustMap.remove(formulaStr);
		/* 将单元格颜色显示为背景色 */
		BgNFSHelper.setBackGroup(cell, BgNConstants.BASE_COLOR);
	}
	
	/**
	 * 描述：根据查询到的数据信息产生差异记录，并且处理单元格的差异显示
	 */
	private void createDivInfo(Cell cell, String formulaStr, BigDecimal newValue, BigDecimal oldValue,Map adjustMap,IObjectPK userPK,boolean needChangeColor ) {
		/* 产生差异记录 */
		BgFormDiversityData divData = new BgFormDiversityData();
		divData.setFormula(formulaStr);
		divData.setNewValue(newValue);
		divData.setOldValue(oldValue);
		divData.setAdjustor(userPK.toString());
		divData.setAdjustTime(new Timestamp(System.currentTimeMillis()));
		adjustMap.put(formulaStr, divData);
		if(!tempVarMap.containsKey(formulaStr)){
			tempVarMap.put(formulaStr, divData);
		}
		
		/*编制中的表单元格不需要改变颜色*/
		if (needChangeColor) {
			/* 将单元格颜色显示为差异颜色 */
			BgNFSHelper.setBackGroup(cell, BgNConstants.EDIT_COLOR);
		}
	}
	
	private void updateDivInfo(Cell cell, String formulaStr, BigDecimal newValue,Map adjustMap,IObjectPK userPK ,boolean needChangeColor) {
		BgFormDiversityData divData = (BgFormDiversityData) adjustMap.get(formulaStr);
		if (divData != null) {
			divData.setNewValue(newValue);
			divData.setAdjustor(userPK.toString());
			divData.setAdjustTime(new Timestamp(System.currentTimeMillis()));
		}
		/*编制中的表单元格不需要改变颜色*/
		if (needChangeColor) {
			/* 将单元格颜色显示为差异颜色 */
			BgNFSHelper.setBackGroup(cell, BgNConstants.EDIT_COLOR);
		}
	}
    
    /*创建报告*/
    private Map createReport(FullOrgUnitInfo orgUnitInfo,BgTemplateInfo bgTemplateInfo,
    		BgPeriodInfo bgPeriodInfo,BgFormInfo bgFormInfo,String calcResult,boolean isMultiOrg){
    	Map map = new HashMap();
    	BgSchemeReCalcMsgObj key = null;
    	key = new BgSchemeReCalcMsgObj(BgSchemeReCalcMsgObj.TEMPLATE,
    			ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgtemplate", ContextUtils.getLocaleFromEnv()),1);
    	map.put(key, bgTemplateInfo.getName());
    	key = new BgSchemeReCalcMsgObj(BgSchemeReCalcMsgObj.PERIOD,
    			ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgperiod", ContextUtils.getLocaleFromEnv()),2);
    	map.put(key, bgPeriodInfo.getName());
    	if (bgFormInfo != null){
    		key = new BgSchemeReCalcMsgObj(BgSchemeReCalcMsgObj.BGFORM,
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgform", ContextUtils.getLocaleFromEnv()),3);
    		map.put(key, bgFormInfo.getName());
    	} else {
    		key = new BgSchemeReCalcMsgObj(BgSchemeReCalcMsgObj.BGFORM,
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "bgform", ContextUtils.getLocaleFromEnv()),3);
    		map.put(key, "");
    	}
    	
    	if(isMultiOrg){
    		key = new BgSchemeReCalcMsgObj(BgSchemeReCalcMsgObj.ORG,
    				ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "org", ContextUtils.getLocaleFromEnv()),4);
    		map.put(key, orgUnitInfo.getName());
    	}
    	key = new BgSchemeReCalcMsgObj(BgSchemeReCalcMsgObj.RECALC_RESULT,
    			ResourceBase.getString(BgReCalcSchemeHelper.RES_PATH, "recalcresult", ContextUtils.getLocaleFromEnv()),5);
    	map.put(key, calcResult);
    	return map;    	
    }
    
    private SelectorItemCollection getBgSchemeSelectors(){
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("id"));
    	sic.add(new SelectorItemInfo("name"));
    	sic.add(new SelectorItemInfo("number"));
    	sic.add(new SelectorItemInfo("bgSchemeNodes.id"));
    	sic.add(new SelectorItemInfo("bgSchemeNodes.bgTemplate.id"));
    	sic.add(new SelectorItemInfo("bgSchemeNodes.bgTemplate.rootId"));
    	sic.add(new SelectorItemInfo("bgSchemeNodes.bgTemplate.name"));
    	sic.add(new SelectorItemInfo("bgSchemeNodes.bgTemplate.number"));
    	return sic;
    }
    
    private SelectorItemCollection getCalcSchemeSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("entry.id"));
        sic.add(new SelectorItemInfo("entry.bgCalculateScheme.id"));
        sic.add(new SelectorItemInfo("entry.template.template.id"));
        sic.add(new SelectorItemInfo("entry.template.template.rootId"));
        sic.add(new SelectorItemInfo("entry.template.template.name"));
        sic.add(new SelectorItemInfo("entry.template.template.number"));
        sic.add(new SelectorItemInfo("entry.template.seq"));
        sic.add(new SelectorItemInfo("entry.template.sheet.id"));
        sic.add(new SelectorItemInfo("entry.template.sheet.sheetId"));
        sic.add(new SelectorItemInfo("entry.template.sheet.sheetName"));
        sic.add(new SelectorItemInfo("entry.template.period.id"));
        sic.add(new SelectorItemInfo("entry.template.period.seq"));
        sic.add(new SelectorItemInfo("entry.template.period.period.name"));
        sic.add(new SelectorItemInfo("entry.template.period.period.number"));
        sic.add(new SelectorItemInfo("entry.template.period.period.periodType"));
        sic.add(new SelectorItemInfo("entry.template.period.period.beginDate"));
        sic.add(new SelectorItemInfo("entry.template.period.period.endDate"));
        sic.add(new SelectorItemInfo("entry.currency.id"));
        sic.add(new SelectorItemInfo("entry.currency.name"));
        sic.add(new SelectorItemInfo("entry.isCollect"));
        sic.add(new SelectorItemInfo("entry.isCollectFirst"));
        sic.add(new SelectorItemInfo("entry.isCalculate"));
        sic.add(new SelectorItemInfo("entry.seq"));
        sic.add(new SelectorItemInfo("entry.id"));
        sic.add(new SelectorItemInfo("entry.id"));
        sic.add(new SelectorItemInfo("entry.id"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("orgUnit.number"));
        sic.add(new SelectorItemInfo("bgScheme.id"));
        sic.add(new SelectorItemInfo("bgScheme.rootId"));
        sic.add(new SelectorItemInfo("bgScheme.name"));
        sic.add(new SelectorItemInfo("bgScheme.number"));
        sic.add(new SelectorItemInfo("rootId"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("currency.id"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("currency.number"));
        sic.add(new SelectorItemInfo("periodNums"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("description"));
        return sic;
    }

	protected boolean _checkHasAssigned(Context ctx, Map param)
			throws BOSException, EASBizException {


		/*
		 * modify by bowei_duan at 2012-3-4
		 * 该方法同步705 开发分支
		 */
		if (param == null || param.isEmpty())
			return false;

		boolean re = false;
		Iterator iter = null;
		Map bgCalcSchemeMap = new HashMap(); 
		Vector vct = null;
		String orgUnitId = null, bgCalcSchemeId = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select FID from T_BG_BgCalculateScheme where FOrgUnitId in (");
		StringBuffer sqlFrootID = new StringBuffer("select FROOTID from T_BG_BgCalculateScheme where ");
		boolean sqlFrootIDIsFirst = true;
		for(iter=param.keySet().iterator(); iter.hasNext();) {
			orgUnitId = (String) iter.next();
			sql.append("'" + orgUnitId + "',");
			
			vct = (Vector) param.get(orgUnitId);
			for(int i=0, n=vct.size(); i<n; i++) {
				bgCalcSchemeId = (String) vct.get(i);
				bgCalcSchemeMap.put(bgCalcSchemeId, null);
			}
		}
		
		for(iter=bgCalcSchemeMap.keySet().iterator(); iter.hasNext();) {		
			bgCalcSchemeId = (String) iter.next();
			if(sqlFrootIDIsFirst){
				sqlFrootID.append(" FID = '").append(bgCalcSchemeId).append("'");
				sqlFrootIDIsFirst = false;
			}
			else{
				sqlFrootID.append(" OR FID = '").append(bgCalcSchemeId).append("'");
			}			
			sqlFrootID.append(" or FROOTID = '").append(bgCalcSchemeId).append("'");
		}
		bgCalcSchemeMap = new HashMap();
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rst1 = null;
		try {
			conn1 = getConnection(ctx);
			pstmt1 = conn1.prepareStatement(sqlFrootID.toString());
			rst1 = pstmt1.executeQuery();
			
			while (rst1.next()) {
				bgCalcSchemeMap.put(rst1.getString("FROOTID"),null);
			}
		} catch(BOSException ex) {
			throw ex;
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		} finally {
			SQLUtils.cleanup(rst1, pstmt1, conn1);
		}
		if(bgCalcSchemeMap == null ||bgCalcSchemeMap.isEmpty()){
			return false;
		}
		
		sql.delete(sql.length()-1, sql.length());
		sql.append(") and FRootId in (");
		for(iter=bgCalcSchemeMap.keySet().iterator(); iter.hasNext();) {
			bgCalcSchemeId = (String) iter.next();
			sql.append("'" + bgCalcSchemeId + "',");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(")");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			rst = pstmt.executeQuery();
			
			if (rst.next()) {
				re = true;
			}
		} catch(BOSException ex) {
			logger.error("error",ex);
			throw ex;
		} catch(SQLException ex) {
			logger.error("error",ex);
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
			BgNSHelper.objClear(bgCalcSchemeMap);
		}
		
		return re;
	
	}
	
	/**
	 * 方案执行
	 */
	protected Object _execute(Context ctx, Object params) throws BOSException, EASBizException
	{
		if(!(params instanceof List))
		{
			return null;
		}
		
		BgCalculateSchemeCollection coll = null;
		List list = (List)params;
		if(list != null && !list.isEmpty() && list.get(0) instanceof String)
		{
			//设置当前的数据重算方案及其子节点的状态为”执行状态“
			StringBuffer sql = new StringBuffer(); 
			sql.append("update T_BG_BgCalculateScheme set fisformal = 1 where frootid in ");
			StringBuffer filter = new StringBuffer(); 
			filter.append("(");
			for(int i=0;i<list.size();i++)
			{
				if(i == list.size() - 1)
				{
					filter.append("'").append(list.get(i)).append("'");
				}else
				{
					filter.append("'").append(list.get(i)).append("',");
				}
			}
			filter.append(")");
			sql.append(filter.toString());
			DbUtil.execute(ctx, sql.toString());
			String sql1 = "select id,name,orgUnit.id,orgUnit.name where rootid in " + filter.toString();
			coll = BgCalculateSchemeFactory.getLocalInstance(ctx).getBgCalculateSchemeCollection(sql1);
		}
		return coll;
	}
	
	/**
	 * 方案反执行
	 */
	protected boolean _antiExecute(Context ctx, Object params) throws BOSException, EASBizException
	{
		if(!(params instanceof List))
		{
			return false;
		}
		
		List list = (List)params;
		if(list != null && !list.isEmpty() && list.get(0) instanceof String)
		{
			//设置当前的数据重算方案及其子节点的状态为”反执行状态“
			StringBuffer sql = new StringBuffer(); 
			sql.append("update T_BG_BgCalculateScheme set fisformal = 0 where frootid in (");
			for(int i=0;i<list.size();i++)
			{
				if(i == list.size() - 1)
				{
					sql.append("'").append(list.get(i)).append("'");
				}else
				{
					sql.append("'").append(list.get(i)).append("',");
				}
			}
			sql.append(")");
			DbUtil.execute(ctx, sql.toString());
		}
		return true;
	}
	
	/**
	 * 重计算方案，此计算方案只供EAS后台事务调度所用
	 */
	protected void _reCalculateByTransaction(Context ctx) throws BOSException, EASBizException
	{
		//获取需要执行的重算方案
		String sql = "select fid from T_BG_BgCalculateScheme where frootId=fid and fisFormal = 1";
    	IRowSet row = DbUtil.executeQuery(ctx, sql);
    	
    	if(row != null)
    	{
    		try {
				while(row.next())
				{
					executeCalculateScheme(ctx,row.getString("fid"));
				}
			} catch (SQLException e) 
			{
				throw new BOSException(e);
			} catch (Exception e) 
			{
				throw new BOSException(e);
			}
    	}
	}
	
    /**
     * 执行数据重算方案
     * @return
     */
    public void executeCalculateScheme(Context ctx,String calculateSchemeID) throws Exception 
    {
    	//获取需要重算方案的ID集合(按照编码排序，使重算方案能够先计算叶子节点，由下往上计算)
    	List orgList = new ArrayList();
    	String sql = "select fid,flongnumber from T_ORG_BaseUnit "+
    			"where fid in (select forgunitid from T_BG_BgCalculateScheme where frootid='"+calculateSchemeID+"')"+
    			" order by flongnumber desc";
    	IRowSet row = DbUtil.executeQuery(ctx, sql);
    	if(row != null)
    	{
    		while(row.next())
    		{
    			orgList.add(row.getString("fid"));
    		}
    	}
    	
    	//获取需要重算方案的集合
    	Map bcsMap = new HashMap();
    	IBgCalculateScheme iBgCalculateScheme = BgCalculateSchemeFactory.getLocalInstance(ctx);
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("rootId", calculateSchemeID, CompareType.EQUALS));
    	view.setFilter(filter);
    	view.setSelector(BgReCalcSchemeHelper.getSelectors());
    	BgCalculateSchemeCollection coll = iBgCalculateScheme.getBgCalculateSchemeCollection(view);
    	if(coll != null)
    	{
    		for(int i=0;i<coll.size();i++)
    		{
    			BgCalculateSchemeInfo info = coll.get(i);
    			bcsMap.put(info.getOrgUnit().getId().toString(), info);
    		}
    	}
    	
    	//开始重算方案
    	BgCalculateSchemeInfo info = null;
    	for(int i=0;i<orgList.size();i++)
    	{
    		info = (BgCalculateSchemeInfo)bcsMap.get(orgList.get(i));
    		if(info != null)
    		{
    			for(int j=0;j<info.getEntry().size();j++)
    			{
    				BgCalculateSchemeEntryInfo entryInfo = info.getEntry().get(j);
    				for(int k=0;k<entryInfo.getTemplate().getPeriod().size();k++)
    				{
    	    			Map param = new HashMap();
    	    			param.put(BgReCalcSchemeHelper.ORG_ID, info.getOrgUnit().getId().toString());
    	    			param.put(BgReCalcSchemeHelper.BGSCHEME_ID, info.getBgScheme().getId().toString());
    	    			param.put(BgReCalcSchemeHelper.BGTEMPLATE_ID, entryInfo.getTemplate().getTemplate().getId().toString());
    	    			param.put(BgReCalcSchemeHelper.BGPERIOD_ID, entryInfo.getTemplate().getPeriod().get(k).getPeriod().getId().toString());
    	    			param.put(BgReCalcSchemeHelper.CURREYCY_ID, info.getCurrency().getId().toString());
    	    			param.put(BgReCalcSchemeHelper.IS_COLLECT, Boolean.valueOf(entryInfo.isIsCollect()));
    	    			param.put(BgReCalcSchemeHelper.IS_COLLECTFIRST, Boolean.valueOf(entryInfo.isIsCollectFirst()));
    	    			param.put(BgReCalcSchemeHelper.IS_CALCULATE, Boolean.valueOf(entryInfo.isIsCalculate()));
    	    			param.put(BgReCalcSchemeHelper.SHEETID_LIST, null);
    	    			param.put(BgReCalcSchemeHelper.IS_MULTI_ORG,Boolean.FALSE);
    	    			_reCalculate(ctx,param);
    				}
    			}
    		}
    	}
    }
    private class PastDataTmpInfo
    {
	    private String formula = null;
	    private BigDecimal pastValue = null;
	    private BigDecimal balance = null;
	    private String adjustorID = null;
	    private Timestamp adjustTime = null;
	    public String getFormula()
	{
		return formula;
	}
	public void setFormula(String formula)
	{
		this.formula = formula;
	}
	public BigDecimal getPastValue()
	{
		return pastValue;
	}
	public void setPastValue(BigDecimal pastValue)
	{
		this.pastValue = pastValue;
	}
	public BigDecimal getBalance()
	{
		return balance;
	}
	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}
	public String getAdjustorID()
	{
		return adjustorID;
	}
	public void setAdjustorID(String adjustorID)
	{
		this.adjustorID = adjustorID;
	}
	public Timestamp getAdjustTime()
	{
		return adjustTime;
	}
	public void setAdjustTime(Timestamp adjustTime)
	{
		this.adjustTime = adjustTime;
	}
    }
}