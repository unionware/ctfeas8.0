/* 
 * @(#) BgDisCountFormControllerBean.java 
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.ma.budget.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.ZipUtil;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.excel.model.struct.UserObject;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.netctrl.IMutexServiceControl;
import com.kingdee.eas.base.netctrl.MutexServiceControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.newrpt.formula.ReportCalculateContext;
import com.kingdee.eas.fi.rpt.AuditedStatusEnum;
import com.kingdee.eas.fi.rpt.CheckedStatusEnum;
import com.kingdee.eas.fi.rpt.IReport;
import com.kingdee.eas.fi.rpt.ReportCalculateErrorProvider;
import com.kingdee.eas.fi.rpt.ReportFactory;
import com.kingdee.eas.fi.rpt.RptSrcTypeEnum;
import com.kingdee.eas.fi.rpt.TableToolkit;
import com.kingdee.eas.fi.rpt.util.IOHelper;
import com.kingdee.eas.fi.rpt.util.ReportVariables;
import com.kingdee.eas.ma.budget.BgAdjustFormDiversityData;
import com.kingdee.eas.ma.budget.BgCollectException;
import com.kingdee.eas.ma.budget.BgCollectFormFactory;
import com.kingdee.eas.ma.budget.BgCollectFormInfo;
import com.kingdee.eas.ma.budget.BgCollectStateEnum;
import com.kingdee.eas.ma.budget.BgConstants;
import com.kingdee.eas.ma.budget.BgCtrSettingFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgDaoSqlObject;
import com.kingdee.eas.ma.budget.BgDataInfo;
import com.kingdee.eas.ma.budget.BgDataTypeEnum;
import com.kingdee.eas.ma.budget.BgDisCountFormFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.eas.ma.budget.BgElementCollection;
import com.kingdee.eas.ma.budget.BgElementFactory;
import com.kingdee.eas.ma.budget.BgElementInfo;
import com.kingdee.eas.ma.budget.BgException;
import com.kingdee.eas.ma.budget.BgFSHelper;
import com.kingdee.eas.ma.budget.BgFormAdjustData;
import com.kingdee.eas.ma.budget.BgFormCollection;
import com.kingdee.eas.ma.budget.BgFormDataSourceEnum;
import com.kingdee.eas.ma.budget.BgFormDiversityData;
import com.kingdee.eas.ma.budget.BgFormException;
import com.kingdee.eas.ma.budget.BgFormFacadeFactory;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgFormStateEnum;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemFormulaHelper;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgPastDataInfo;
import com.kingdee.eas.ma.budget.BgPastRecordFactory;
import com.kingdee.eas.ma.budget.BgPastRecordInfo;
import com.kingdee.eas.ma.budget.BgPastSourceEnum;
import com.kingdee.eas.ma.budget.BgPeriodCollection;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRefBgCurrencyInfo;
import com.kingdee.eas.ma.budget.BgRefBgItemCombinInfo;
import com.kingdee.eas.ma.budget.BgRefBgPeriodInfo;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.BgSchemeFactory;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.eas.ma.budget.BgTemplateCollection;
import com.kingdee.eas.ma.budget.BgTemplateFactory;
import com.kingdee.eas.ma.budget.BgTemplateInfo;
import com.kingdee.eas.ma.budget.IBgCollectForm;
import com.kingdee.eas.ma.budget.IBgCtrSettingFacade;
import com.kingdee.eas.ma.budget.IBgDisCountForm;
import com.kingdee.eas.ma.budget.IBgElement;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.IBgFormFacade;
import com.kingdee.eas.ma.budget.IBgItem;
import com.kingdee.eas.ma.budget.IBgPastRecord;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.eas.ma.budget.IBgScheme;
import com.kingdee.eas.ma.budget.IBgTemplate;
import com.kingdee.eas.ma.budget.IRefBgForm;
import com.kingdee.eas.ma.budget.IRefDisCountBgForm;
import com.kingdee.eas.ma.budget.RefBgFormCollection;
import com.kingdee.eas.ma.budget.RefBgFormFactory;
import com.kingdee.eas.ma.budget.RefBgFormInfo;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormFactory;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.nbudget.BgInfoHelper;
import com.kingdee.eas.ma.nbudget.BgNAdjHelper;
import com.kingdee.eas.ma.nbudget.BgNConstants;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.BgNSQLHelper;
import com.kingdee.eas.ma.nbudget.app.formula.BgCalculatorProcess;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;


public class BgDisCountFormControllerBean extends AbstractBgDisCountFormControllerBean
{
   
	private static final long serialVersionUID = -7969938781426721873L;
	 private static Logger logger =  Logger.getLogger("com.kingdee.eas.ma.budget.app.BgDisCountFormControllerBean");
    
	Locale locale_L1 = LocaleUtils.getLocale("L1");
	Locale locale_L2 = LocaleUtils.getLocale("L2");
    Locale locale_L3 = LocaleUtils.getLocale("L3");
    
    private final static String CURRENCY_NUMBER_TYPE="currency";
    private final static String ELEMENT_NUMBER_TYPE ="element";
    private final static String BGITEM_NUMBER_TYPE ="bgitem";
    
    protected SelectorItemCollection getBgFormInfo() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("bgScheme.id")); 
        sic.add(new SelectorItemInfo("state"));
        return sic;
	}
    
    /**
     * 描述：获取上级预算表的数据及下级预算表的汇总数据
     * @author:longl
     */
    protected Map _getTopBgData(Context ctx, BOSUuid topBgId, String subBgId) throws BOSException, EASBizException {
        
    	Map result = new HashMap();
    	if (topBgId == null)
    		return result;
    	
    	IBgForm  ibgform = BgFormFactory.getLocalInstance(ctx);
    	if (ibgform == null)
    		throw new BgException(BgException.NOINSTANCE);
    	else {
    		IObjectPK pk = new ObjectUuidPK(topBgId);
    		BgFormInfo bgFormInfo = ibgform.getBgFormInfo(pk, getBgFormInfo());
            BgDaoSqlObject sqlOb = new BgDaoSqlObject();
            
            /* 取出项目公式的要素信息 */
            sqlOb.setSelect("distinct e.FName_" + ctx.getLocale() + " as elementname, e.FNumber as elementnumber, e.FDataType as datatype ");
            sqlOb.setFrom("T_BG_BgData as t left outer join T_BG_BgElement e on t.FBgElementId = e.FID ");
            sqlOb.setWhere("t.FOrgUnitID = '" + bgFormInfo.getOrgUnit().getId() + "' and t.FBgSchemeID = '" + 
            		bgFormInfo.getBgScheme().getId() + "' and t.FBgFormId = '" + bgFormInfo.getId() + 
            		"' and t.FFormula like '%" + BgDataTypeEnum.BgData.getName() + "%'");
            result.put("element", DbUtil.executeQuery(ctx, sqlOb.getSql()));

            BgDaoSqlObject topSql = new BgDaoSqlObject();
            BgDaoSqlObject subSql = new BgDaoSqlObject();
            
            topSql.setSelect("period.FName_" + ctx.getLocale() + " as periodname, itemcom.fkey as itemKey, " +
            		"itemcom.fkey as itemName, e.FName_" + ctx.getLocale() + " as elementname, " +
                    "e.FNumber as elementnumber, t.FBgValue as bgvalue, 0 as isSum, " +
                    "t.FBgFormId as bgform, t.FFormula as formula");
            
            subSql.setSelect("period.fname_" + ctx.getLocale() + " as periodname, itemcom.fkey as itemKey, " +
            		"itemcom.fkey as itemName, e.FName_" + ctx.getLocale() + " as elementname," +
                    "e.FNumber as elementnumber, sum(t.FBgValue) as bgvalue, 1 as isSum, " +
                    "' ' as bgform, t.FFormula as formula");
            
            topSql.setFrom("T_BG_BgData t " +
                    "left outer join T_BG_BgPeriod period on t.fbgperiodid = period.fid " +
                    "left outer join T_BG_BgElement e on t.fbgelementid = e.fid " +
                    "left outer join T_BG_BgItemCombination itemcom on t.fbgitemcombinid = itemcom.fid");
            
            subSql.setFrom(topSql.getFrom());
            topSql.setWhere(sqlOb.getWhere());

            subSql.setWhere("FBgFormId in " + subBgId + " and FFormula in (" +
                    "select FFormula from T_BG_BgData topdata where FBgFormId = '" + topBgId.toString() + 
                    "' and FFormula like '%" + BgDataTypeEnum.BgData.getName() + "%')");

            subSql.setGroup("period.fname_" + ctx.getLocale() + ", itemcom.fkey , e.fname_" + ctx.getLocale() + 
            		", e.fnumber, t.FFormula ");
            
            BgDaoSqlObject allSql = new BgDaoSqlObject();
            allSql.setSelect("*");
            allSql.setFrom("(" + topSql.getSql() + " union all " + subSql.getSql() + ") AS temp ");
            allSql.setOrder("periodname, itemKey, isSum, elementname");
              
            IRowSet data = DbUtil.executeQuery(ctx, allSql.getSql());
            
            Set itemNumberSet = new HashSet();
            try {
                while(data.next()){
                    String itemCombine = data.getString("itemKey");
                    if( itemCombine!=null ){
                        BgHelper.addArray2Set(itemNumberSet, itemCombine.split("_"));
                    }
                }
                data.beforeFirst();
                
            } catch (SQLException e) {
                throw new BOSException(e);
            }
            result.put("data",data);
            result.put("itemMap", getItemNumber2NameMap(ctx, itemNumberSet, bgFormInfo.getOrgUnit().getId().toString()));
    	}
        
        return result;
    }

    /**
     * 描述：获取某一要素的下级预算表的数据
     * @author:longl
     */
    protected IRowSet _getSubBgData(Context ctx, BOSUuid topBgId, String subBgIds, String periodName, String itemKey, String formula) throws BOSException {
        BgDaoSqlObject subSql = new BgDaoSqlObject();
        
        subSql.setSelect("'" + periodName + "' as periodname, '" + itemKey + "' as itemKey, org.fname_" + ctx.getLocale() + " as itemName, element.fname_" + ctx.getLocale() + " as elementname," +
                "element.fnumber as elementnumber , data.fbgvalue as bgvalue , 0 as isSum, data.fbgformid as bgform, data.fformula as formula");
        
        subSql.setFrom("t_bg_bgdata data " +
//                "left outer join t_bg_bgperiod period on data.fbgperiodid = period.fid " +
                "left outer join T_BG_BgElement element on data.fbgelementid = element.fid " +
                "left outer join T_ORG_BaseUnit org on data.forgunitid = org.fid " );
//                "left outer join t_bg_bgitemcombination itemcom on data.fbgitemcombinid = itemcom.fid");
        subSql.setWhere("Fbgformid in " + subBgIds + " and fformula in " + formula ) ;

//        subSql.setWhere("Fbgformid in " + subBgIds + " and itemcom.fkey = '" + itemKey 
//                + "' and period.fname_" + ctx.getLocale() + " = '" + periodName + "'and fformula in (" +
//                "select fformula from t_bg_bgdata topdata where Fbgformid = '" + topBgId.toString() + "' and data.fformula  like '%" + BgDataTypeEnum.BgData.getName() + "%')");

//                "left outer join t_bg_bgitemcombination itemcom on data.fbgitemcombinid = itemcom.fid " + 
//                "left outer join t_bg_bgform bgform on data.FBgFormId = bgform.fid ");
//        
//        subSql.setWhere("data.FOrgUnitId = bgform.FOrgUnitID and data.FBgSchemeId = bgform.FBgSchemeID and fformula in (" +
//                "select fformula from t_bg_bgdata topdata where Fbgformid = '" + topBgId.toString() +
//                "' and data.fformula  like '%" + BgDataTypeEnum.BgData.getName() + "%' )" +
//                " and Fbgformid in " + subBgIds + " and itemcom.fkey = '" + itemKey  + "' and period.fname_" + ctx.getLocale() + " = '" + periodName + "'");
        
//        subSql.setGroup("period.fname_" + ctx.getLocale() + " , itemcom.fkey , element.fname_" + ctx.getLocale() + " ,element.fnumber ");
        
        return DbUtil.executeQuery(ctx,subSql.getSql()); 
    }
   
    /**
     * 
     * 描述：根据要素的编码集合来获取以要素编码为KEY，要素名称为ＶＡＬＵＥ的MAP
     * @param ctx
     * @param numberSet
     * @return
     * @throws BOSException
     * @throws EASBizException
     * @author:longl
     * 创建时间：2006-4-12 <p>
     */
    private Map getItemNumber2NameMap(Context ctx, Set numberSet, String orgUnitId) throws BOSException, EASBizException {
		
    	Map itemMap = new HashMap(); 
        if(numberSet.size() == 0){
            return itemMap;
        }
        
        EntityViewInfo viewInfo = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("number",numberSet, CompareType.INCLUDE));
        filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",BgSHelper.getIsolateOrg(ctx,orgUnitId), CompareType.EQUALS));
        viewInfo.setFilter(filter);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add(new SelectorItemInfo("id"));
        selector.add(new SelectorItemInfo("number"));
        selector.add(new SelectorItemInfo("name"));  
        viewInfo.getSelector().addObjectCollection(selector);
        IBgItem iBgItem = BgItemFactory.getLocalInstance(ctx);
        BgItemCollection bgItemColl = iBgItem.getBgItemCollection(viewInfo);
        if( bgItemColl !=null && bgItemColl.size()>0 ){
            for(int i=0; i<bgItemColl.size(); i++ ){
                BgItemInfo bgItemInfo = bgItemColl.get(i);
                itemMap.put(bgItemInfo.getNumber(), bgItemInfo.getName());                
            }
        }
        
        return itemMap;
    }
    
    /**
     * 将汇编中的预算表置为汇编中状态
     */
    protected void _addnew(Context ctx , IObjectPK pk , IObjectValue model) throws BOSException, EASBizException {
    	checkBgFormState(ctx, model);
    	setBgFormStatus(ctx, (BgDisCountFormInfo)model, BgFormStateEnum.DisCounting);
    	addNewGetOldData(ctx, model);
    	super._addnew(ctx, pk, model);
    }
    /**
     * 将汇编中的预算表置为汇编中状态
     */
    protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	checkBgFormState(ctx, model);
    	setBgFormStatus(ctx, (BgDisCountFormInfo)model, BgFormStateEnum.DisCounting);
    	addNewGetOldData(ctx, model);
    	return super._addnew(ctx, model);
    }
    
    private void addNewGetOldData(Context ctx, IObjectValue model) {
    	BgDisCountFormInfo info = (BgDisCountFormInfo) model;
		Map formIds = new HashMap();
		List mutexIds = new ArrayList();
		formIds.put(info.getBgForm().getId().toString(), null);
		for(int i=0, n=info.getRefBgForms().size(); i<n ; i++) {
			formIds.put(info.getRefBgForms().get(i).getBgForm().getId().toString(), null);
			mutexIds.add(info.getRefBgForms().get(i).getBgForm().getId().toString());
		}
		try{
			//取得预算表数据
			IMutexServiceControl iMC = MutexServiceControlFactory.getLocalInstance(ctx);
			iMC.batchRequestObjIDForUpdate(mutexIds);
			Map oldData = BgDisCountFormFactory.getLocalInstance(ctx).getOldData(formIds);
			HashMap otherData = new HashMap();			
			otherData.put("oldData", BgHelper.storeHashMapToByte((HashMap) oldData));
			if(otherData != null && !otherData.isEmpty()){
				info.setOtherCont(BgHelper.storeHashMapToByte(otherData));
			}
		}catch (Exception ee){
			logger.error("budget error:", ee);
		}		
	}
    
    
    private void checkBgFormState(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	if (model == null)
    		return ;
    	
    	BgDisCountFormInfo info = (BgDisCountFormInfo) model;
    	if (info != null) {
    		String bgFormId = null, bgFormNumber = null;
    		BgFormInfo bgFormInfo = null;
        	Map collectInfo = new HashMap();
        	Map filterInfo = new HashMap();
        	List state = new ArrayList();
        	state.add(BgFormStateEnum.Certificate);
        	
        	bgFormInfo = info.getBgForm();
    		collectInfo.put(bgFormInfo.getId().toString(), bgFormInfo.getNumber());
    		
    		RefDisCountBgFormInfo refInfo = null;
    		for(Iterator iter=info.getRefBgForms().iterator(); iter.hasNext();) {
    			refInfo = (RefDisCountBgFormInfo) iter.next();
    			bgFormInfo = refInfo.getBgForm();
    			if (!collectInfo.containsKey(bgFormInfo.getId().toString()))
    				collectInfo.put(bgFormInfo.getId().toString(), bgFormInfo.getNumber());
    		}
    		try {
    			IBgForm ibgForm = BgFormFactory.getLocalInstance(ctx);
    			filterInfo = ibgForm.getFormFilterInfo(info.getBgForm().getId(), state);
    			filterInfo = ibgForm.getFormFilterInfo(filterInfo);
    			
    			for(Iterator iter=collectInfo.keySet().iterator(); iter.hasNext();) {
    				bgFormId = (String) iter.next();
    				bgFormNumber = (String) collectInfo.get(bgFormId);
    				if (!filterInfo.containsKey(bgFormId)) {
    					//throw new BgCollectException(BgCollectException.BGCOLLECTFORMNOSAVE, new String[] {bgFormNumber});
    					throw new EASBizException(new NumericExceptionSubItem("ffffff","编码为"+bgFormNumber+"的预算表不是已认可状态或者进行了预算调整"));
    				}
    			}
    		} catch(BOSException ex) {
    			throw ex;
    		} catch(EASBizException ex) {
    			throw ex;
    		} finally {
    			BgNSHelper.objClear(filterInfo);
    			BgNSHelper.objClear(collectInfo);
    		}
    	}
    }
    
    
    /**
     * TODO 好像是要求要新增或者删除一条记录，并设置其状态
     * @param bgDisCountFormInfo
     * @param satte
     * @throws BOSException 
     */
	private void setBgFormStatus(Context ctx, BgDisCountFormInfo bgDisCountFormInfo, BgFormStateEnum state) throws BOSException {
		StringBuffer sqlToUpdateBgFormStatus = new StringBuffer("UPDATE T_BG_BgForm SET FState = ? WHERE");
		RefDisCountBgFormCollection refBgFormCols = bgDisCountFormInfo.getRefBgForms();
		sqlToUpdateBgFormStatus.append(" FID = "); 
		sqlToUpdateBgFormStatus.append("'");
		sqlToUpdateBgFormStatus.append(bgDisCountFormInfo.getBgForm().getId().toString());
		sqlToUpdateBgFormStatus.append("'");
		for(int i = 0, count = refBgFormCols.size(); i < count; i++){
			sqlToUpdateBgFormStatus.append(" OR ");
			sqlToUpdateBgFormStatus.append(" FID = "); 
			sqlToUpdateBgFormStatus.append("'");
			sqlToUpdateBgFormStatus.append(refBgFormCols.get(i).getBgForm().getId().toString());
			sqlToUpdateBgFormStatus.append("'");
		}
		try {
			DbUtil.execute(ctx, sqlToUpdateBgFormStatus.toString(), new Object[]{new Integer(state.getValue())});
		} catch (BOSException e) {
			throw new BOSException(e);
		}
	}    
   
    private boolean checkHaveAudit(Context ctx, IObjectPK pk) throws EASBizException, BOSException{
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add("id");
        sic.add("state");

        BgDisCountFormInfo bgDisCountFormInfo = (BgDisCountFormInfo) getValue(ctx, pk, sic);
        return BgCollectStateEnum.SIGNED.equals(bgDisCountFormInfo.getState());
    }
    
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
        if(checkHaveAudit(ctx, pk)){
            throw new BgCollectException(BgCollectException.BGCOLLECTFORMNOCHANGE);
        }
        //处理汇编意见(将以前的汇编意见存到引用预算表中,即最新的汇编意见）
        BgDisCountFormInfo bgDisCountFormInfo = (BgDisCountFormInfo) model;
        if (!bgDisCountFormInfo.getRefBgForms().isEmpty()){
        	Map subForm=new HashMap();
        	RefDisCountBgFormInfo refForm=null;
        	for(int i=0;i<bgDisCountFormInfo.getRefBgForms().size();i++){
        		refForm=bgDisCountFormInfo.getRefBgForms().get(i);
        		subForm.put(refForm.getId().toString(), refForm);
        	}        	
        	Connection conn=null;
        	PreparedStatement pstmt=null;
        	ResultSet res=null;
        	try{
        		StringBuffer sql=new StringBuffer("SELECT FCOMMENT,FID FROM T_BG_RefDisCountBgForm WHERE FID IN (");
        		Object[] params=subForm.keySet().toArray();
        		BgNSQLHelper.addPlaceHolder(sql, BgNSQLHelper.BATCH_COUNT).append(" )");
        		conn=getConnection(ctx);
        		pstmt=conn.prepareStatement(sql.toString());
        		int count=(params.length-1)/BgNSQLHelper.BATCH_COUNT+1;
        		for(int i=0;i<count;i++){
        			BgNSQLHelper.setParam4Batch(pstmt, i, BgNSQLHelper.BATCH_COUNT, params);
        			res=pstmt.executeQuery();
        			while(res.next()){
        				refForm=(RefDisCountBgFormInfo)subForm.get(res.getObject("FID"));
        				if (refForm!=null){
        					refForm.setComment(res.getString("FCOMMENT"));
        				}
        			}        			
        			SQLUtils.cleanup(res);
        		}
        	}catch(SQLException e){
        		logger.error("",e);
        		throw new BOSException(e);
        	}finally{
        		SQLUtils.cleanup(res, pstmt, conn);
        	}        	
        }        
        super._update(ctx, pk, model);
    }
    
    /**
     * 将汇编中的预算表置为审核状态
     */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException{
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add("state");
        sic.add("bgForm.id");
        sic.add("refBgForms");
        sic.add("refBgForms.bgForm.id");

		BgDisCountFormInfo bgDisCountFormInfo = (BgDisCountFormInfo) getValue(ctx, pk, sic);
        if(BgCollectStateEnum.SIGNED.equals(bgDisCountFormInfo.getState())){
            throw new BgCollectException(BgCollectException.BGCOLLECTFORMNODELETE);
        }
		
        setBgFormStatus(ctx, bgDisCountFormInfo, BgFormStateEnum.Certificate);
		
        Connection conn = null;
        
        try {
			conn = getConnection(ctx);
			String[] bgFormIds = new String[bgDisCountFormInfo.getRefBgForms().size() +1];
			bgFormIds[0] = bgDisCountFormInfo.getBgForm().getId().toString();
			StringBuffer deleteSql = new StringBuffer("delete from T_BG_BgTmpData where FBgFormId in(?,");
			for(int i=0, n=bgDisCountFormInfo.getRefBgForms().size(); i<n; i++) {
				if(i == n-1)
					deleteSql.append("?)");
				else
					deleteSql.append("?,");
				bgFormIds[i+1] = bgDisCountFormInfo.getRefBgForms().get(i).getBgForm().getId().toString();
    		}
			DbUtil.execute(ctx, deleteSql.toString() , bgFormIds);
//			SQLUtils.execute(conn, "delete from T_BG_BgTmpData where FBgFormId = '" + bgCollectFormInfo.getBgForm().getId().toString() + "'");
//			
//    		for(int i=0, n=bgCollectFormInfo.getRefBgForms().size(); i<n; i++) {
//        		SQLUtils.execute(conn, "delete from T_BG_BgTmpData where FBgFormId = '" + bgCollectFormInfo.getRefBgForms().get(i).getBgForm().getId().toString() + "'");
//    		}
        }
//        catch (SQLException ex) {
//        	logger.error(ex);
//        	throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
//		}
        catch(BOSException ex) {
        	throw ex;
        } finally {
        	SQLUtils.cleanup(conn);
        }
        
        super._delete(ctx, pk);
	} 

    protected boolean _hasBgDataFomular(Context ctx, BOSUuid BgFormId) throws BOSException {
        BgDaoSqlObject sqlOb = new BgDaoSqlObject();
        
        sqlOb.setSelect("top 1 * ");
        sqlOb.setFrom("t_bg_bgdata as data ");
        sqlOb.setWhere("data.Fbgformid = '" + BgFormId.toString() + "' and data.fformula  like '%" + BgDataTypeEnum.BgData.getName() + "%'");

        try {
            return DbUtil.executeQuery(ctx,sqlOb.getSql()).next();
        }  catch (SQLException e) {
            throw new BOSException("Sql execute exception : " + sqlOb.getSql(), e);  
        }
    }

    protected void _audit(Context ctx, BOSUuid bgDisCountFormId) throws BOSException, EASBizException {
        if(checkHaveAudit(ctx,new ObjectUuidPK( bgDisCountFormId))){
            throw new BgCollectException(BgCollectException.BGCOLLECTFORMNOAUDIT);
        }
        //if(BgFormFacadeFactory.getLocalInstance(ctx).auditFormCollect(bgDisCountFormId)){
        if(new BgDisCountAuditUtils()._auditFormCollect(ctx,bgDisCountFormId)){
        
            BgDisCountFormInfo info = getBgDisCountFormInfo(ctx,new ObjectUuidPK(bgDisCountFormId));
            setBgFormStatus(ctx, info, BgFormStateEnum.Certificate);
            //setBgFormDataSource(ctx, info, BgFormDataSourceEnum.DISCOUNT);           
            setCollectState(ctx, info.getId().toString(), BgCollectStateEnum.SIGNED_VALUE);
            //super._update(ctx,new ObjectUuidPK(bgCollectFormId), info);
        }

    }
    
   

















    
    
    
    
    
    

	private void setCollectState(Context ctx, String id, int signed_value) throws BOSException{
		StringBuffer sqlToUpdateBgFormDataSource = new StringBuffer("UPDATE T_BG_BgDisCountForm SET Fstate = ? WHERE fid = ?");		
		try {
			DbUtil.execute(ctx, sqlToUpdateBgFormDataSource.toString(), new Object[]{new Integer(signed_value), id});
		} catch (BOSException e) {
			throw new BOSException(e);
		}
		
	}

	private void setBgFormDataSource(Context ctx, BgDisCountFormInfo bgDisCountFormInfo, BgFormDataSourceEnum dataSourceEnum) throws BOSException {
		StringBuffer sqlToUpdateBgFormDataSource = new StringBuffer("UPDATE T_BG_BgForm SET FDataSource = ? WHERE");
		RefDisCountBgFormCollection refBgFormCols = bgDisCountFormInfo.getRefBgForms();
		//assert refBgFormCols.size() > 0;
//		assert bgCollectFormInfo.getBgForm() != null;
		sqlToUpdateBgFormDataSource.append(" FID = "); 
		sqlToUpdateBgFormDataSource.append("'");
		sqlToUpdateBgFormDataSource.append(bgDisCountFormInfo.getBgForm().getId().toString());
		sqlToUpdateBgFormDataSource.append("'");
		for(int i = 0, count = refBgFormCols.size(); i < count; i++){
//			if(i != 0){
				sqlToUpdateBgFormDataSource.append(" OR ");
//			}
			sqlToUpdateBgFormDataSource.append(" FID = "); 
			sqlToUpdateBgFormDataSource.append("'");
			sqlToUpdateBgFormDataSource.append(refBgFormCols.get(i).getBgForm().getId().toString());
			sqlToUpdateBgFormDataSource.append("'");
		}
		try {
			DbUtil.execute(ctx, sqlToUpdateBgFormDataSource.toString(), new Object[]{new Integer(dataSourceEnum.getValue())});
		} catch (BOSException e) {
			throw new BOSException(e);
		}
		
	}

    protected void _auditWF(Context ctx, BOSUuid bgDisCountFormId) throws BOSException {
    }

    protected IRowSet _getAdjustMapByBgForm(Context ctx, BOSUuid bgFormId) throws BOSException {
        BgDaoSqlObject sqlOb = new BgDaoSqlObject();
        sqlOb.setSelect(" TOP 1 collectForm.fadjustMapData AS adjustMapData");
        sqlOb.setFrom("T_BG_BgDisCountForm AS collectForm");
        sqlOb.setWhere("collectForm.fBgformID = '" + bgFormId.toString() + "'");
        sqlOb.setOrder(" collectForm.fCreateTime DESC ");
        
        IRowSet result = DbUtil.executeQuery(ctx,sqlOb.getSql());
        if( result.size() == 0 ){
            sqlOb.setSelect(" TOP 1 refForm.fadjustMapData AS adjustMapData");
            sqlOb.setFrom("T_BG_RefDisCountBgForm AS refForm " +
                    "LEFT OUTER JOIN T_BG_BgDisCountForm AS collectForm ON refForm.FBgCollectID = collectForm.FID");
            sqlOb.setWhere("refForm.fBgformID = '" + bgFormId.toString() + "'");
            sqlOb.setOrder(" collectForm.fCreateTime DESC ");
            result = DbUtil.executeQuery(ctx,sqlOb.getSql());
        }
        return result;
    }

    protected void _auditStatus(Context ctx, BOSUuid bgDisCountFormId) throws BOSException, EASBizException {
        if(checkHaveAudit(ctx,new ObjectUuidPK( bgDisCountFormId))){
            throw new BgCollectException(BgCollectException.BGCOLLECTFORMNOSUBMIT);
        }
        BgDisCountFormInfo info = getBgDisCountFormInfo(ctx,new ObjectUuidPK(bgDisCountFormId));
        info.setState(BgCollectStateEnum.AUDIT);
        super._update(ctx,new ObjectUuidPK(bgDisCountFormId), info);
    }

    
    /**
     * 描述：在工作流中审批不通过时至汇编中状态
     * @UpdateTime 2006/6/19 hua_yang
     */
	protected void _auditCollectState(Context ctx, BOSUuid id) throws BOSException, EASBizException {
		
		if (id == null)
			return ;
		
		IObjectPK pk = new ObjectUuidPK(id);
		IBgDisCountForm ibgDisCountForm = BgDisCountFormFactory.getLocalInstance(ctx);
		if (ibgDisCountForm.exists(pk)) {
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
	        sic.add(new SelectorItemInfo("name"));
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("state"));
			
			BgDisCountFormInfo bgDisCountFormInfo = ibgDisCountForm.getBgDisCountFormInfo(pk, sic);
			bgDisCountFormInfo.setState(BgCollectStateEnum.COLLECTED);
			ibgDisCountForm.update(pk, bgDisCountFormInfo);
		}
	}

	/**
	 * 描述：获得组织展开时的数据
	 */
	protected Map _getBgData(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, Set formulaSet) throws BOSException, EASBizException {
		if (orgUnitId == null || bgSchemeId == null || formulaSet == null || formulaSet.isEmpty())
			return new HashMap();
		
		Map budgetMap = new HashMap();
		String formulaString = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select FBgValue, FFormula from T_BG_BgData where FOrgUnitId = ? and FBgSchemeId = ? and FFormula in (");
		for(Iterator iter=formulaSet.iterator(); iter.hasNext();) {
			formulaString = (String) iter.next();
			sql.append("'" + formulaString + "',");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(")");
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, orgUnitId.toString());
			pstmt.setString(2, bgSchemeId.toString());
			rst = pstmt.executeQuery();
			
			while (rst.next()) {
				budgetMap.put(rst.getString(2), rst.getBigDecimal(1));
			}
			
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		} catch(BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}
		return budgetMap;
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		BgDisCountFormInfo bgDisCountFormInfo = (BgDisCountFormInfo) model;
		
		InputStream is = null;
		ObjectInputStream ois = null;
		Map adjustMap = null;
		
		Connection conn = null;
		
		BOSUuid bgFormId = null;
		BgFormInfo bgFormInfo = null;
		
		try {
			conn = getConnection(ctx);
			bgDisCountFormInfo.remove("kdtData");
			byte[] data = bgDisCountFormInfo.getAdjustMapData();
			if (data != null && data.length > 0) {
				try {
					is = ZipUtil.unpack(data);
					ois = new ObjectInputStream(is);
					adjustMap = (HashMap) ois.readObject();
				} finally {
					try {
						if (is != null)
							is.close();
					} catch (Exception e) {
					}
		    		try {
		    			if (ois != null)
		    				ois.close();
					} catch (Exception e) {
					}
				}
				
				bgFormId = bgDisCountFormInfo.getBgForm().getId();
				if (adjustMap != null && !adjustMap.isEmpty()) {
					bgFormInfo = BgFSHelper.getBgFormInfo(conn, bgFormId, false);
					
					/*BgFSHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(), 
															bgFormInfo.getBgScheme().getId(), 
															bgFormInfo.getBgTemplate().getId(),
															bgFormInfo.getId(), adjustMap);*/
					BgNAdjHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(),
							                                  bgFormInfo.getBgScheme().getId(), 
							                                  bgFormInfo.getBgTemplate().getId(),
							                                  bgFormInfo.getId(), adjustMap);
					adjustMap.clear();
				} else {
		    		SQLUtils.execute(conn, "delete from T_BG_BgTmpData where FBgFormId = '" + bgFormId.toString() + "'");
				}
			}

			
			RefDisCountBgFormInfo refBgFormInfo = null;
			for(Iterator iter=bgDisCountFormInfo.getRefBgForms().iterator(); iter.hasNext();) {
				refBgFormInfo = (RefDisCountBgFormInfo) iter.next();
				
				refBgFormInfo.remove("kdtData");
				data = refBgFormInfo.getAdjustMapData();
				if (data != null && data.length > 0) {
					try {
						is = ZipUtil.unpack(data);
						ois = new ObjectInputStream(is);
						adjustMap = (HashMap) ois.readObject();
					} finally {
						try {
							if (is != null)
								is.close();
						} catch (Exception e) {
						}
			    		try {
			    			if (ois != null)
			    				ois.close();
						} catch (Exception e) {
						}
					}
					
					bgFormId = refBgFormInfo.getBgForm().getId();
					if (adjustMap != null && !adjustMap.isEmpty()) {
						bgFormInfo = BgFSHelper.getBgFormInfo(conn, bgFormId, false);
						
						/*BgFSHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(), 
															bgFormInfo.getBgScheme().getId(), 
															bgFormInfo.getBgTemplate().getId(),
															bgFormId, adjustMap);*/
						BgNAdjHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(),
                                bgFormInfo.getBgScheme().getId(), 
                                bgFormInfo.getBgTemplate().getId(),
                                bgFormId, adjustMap);
						adjustMap.clear();
					} else {
			    		SQLUtils.execute(conn, "delete from T_BG_BgTmpData where FBgFormId = '" + bgFormId.toString() + "'");
					}
				}
			}			
			
		} catch (IOException ex) {
			throw new BOSException(ex);
		} catch (ClassNotFoundException ex) {
			throw new BOSException(ex);
		} catch (SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()} );
		} finally {
			SQLUtils.cleanup(conn);
		}
		IObjectPK pk = super._submit(ctx, model);
		return pk;
	}
	
    private Map getColIdsMap(BgDisCountFormInfo editData) {
		Map decIds = new HashMap();
		Map refIds = new HashMap();
		decIds.put("colId", editData.getId().toString());
		decIds.put("mainFormId", editData.getBgForm().getId().toString());
		RefDisCountBgFormCollection coll = editData.getRefBgForms(); 
		for(int i = 0; i < coll.size(); i++){
			refIds.put(coll.get(i).getBgForm().getId().toString(), null);
		}
		decIds.put("refIds",refIds);
		return decIds;
	}
	
	public String getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		String logInfo = "";
		if (ctx == null || pk == null)
			return logInfo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select f.FNumber, f.FName from T_BG_BgDisCountForm t " +
					"left outer join T_BG_BgForm f on t.FBgFormId = f.FID " +
					"where t.FID = ?");
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, pk.toString());
			rst = pstmt.executeQuery();
			
			if (rst.next()) {
				logInfo = rst.getString(1) + " - " + rst.getString(2);
			}
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}
		return logInfo;
	}

	protected byte[] _getKdf(Context ctx, BOSUuid bgDisCountId) throws BOSException, EASBizException {
		if (bgDisCountId == null)
			return null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select FKdtData from T_BG_BgDisCountForm where FID = ?");
		
		byte[] data = null;
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, bgDisCountId.toString());
			rst = pstmt.executeQuery();
			
			if (rst.next()) {
				data = BgInfoHelper.getBytes(rst, 1);
			}
		} catch (SQLException ex) {
			logger.error(ex);
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} catch (IOException ex) {
			logger.error(ex);
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}
		return data;
	}

	protected boolean _addBgFormToChildren(Context ctx, String orgUnitId, IObjectValue formInfo) throws BOSException, EASBizException {
		IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
		IBgTemplate iBgTemplate = BgTemplateFactory.getLocalInstance(ctx);
		IBgScheme iBgScheme = BgSchemeFactory.getLocalInstance(ctx);
		//将orgIdsMap中的等于formInfo的rootId的模板和方案取出，如果有表就跳过
		if (formInfo == null || orgUnitId == null) {
			return false;
		}
		String templateRootId = ((BgFormInfo) formInfo).getBgTemplate()
				.getRootId().toString();
		String schemeRootId = ((BgFormInfo) formInfo).getBgScheme().getRootId()
				.toString();
		BgFormInfo info = null;		
		int n = 1;		
			info = null;			
			try {
				info = iBgForm.getBgFormInfo("where bgTemplate.rootId = '"
						+ templateRootId
						+ "' and bgScheme.rootId = '"
						+ schemeRootId
						+ "' and orgUnit = '"
						+ orgUnitId
						+ "' and bgPeriod = '"
						+ ((BgFormInfo) formInfo).getBgPeriod().getId().toString() + "'" 
						+ "  and FcurrencyID = '"+((BgFormInfo) formInfo).getCurrency().getId().toString()+"'");
				if(info != null){
					return false;
				}
			} catch (BOSException ex) {
				if (info == null) {
					BgTemplateInfo bgTemplateInfo = null;
					BgTemplateCollection templateCol = iBgTemplate
							.getBgTemplateCollection("where rootId = '"
									+ templateRootId + "' and orgUnit = '"
									+ orgUnitId + "' order by createTime ");
					if (templateCol.size() > 0){
						bgTemplateInfo = templateCol.get(0);
					}else{
						return false;
					}
					BgSchemeInfo bgSchemeInfo = iBgScheme
							.getBgSchemeInfo("where rootId = '" + schemeRootId
									+ "' and orgUnit = '" + orgUnitId + "'");
					BgFormInfo bgFormInfo = new BgFormInfo();
					bgFormInfo.setId(BOSUuid.create(bgFormInfo.getBOSType()));
					bgFormInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
					bgFormInfo.setCreateTime(new Timestamp(System
							.currentTimeMillis()));
					bgFormInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
					bgFormInfo.setAdjust(bgFormInfo); /* 设置调整ROOTID */
					bgFormInfo.setVersionNo(BgConstants.ONE); /* 设置预算表版本号 */

					/* 设置报表公司 -------------------------------------------yanghua*/
					bgFormInfo.setCompany(ContextUtil.getCurrentFIUnit(ctx));
					/* 设置报表状态 */
					bgFormInfo.setState(BgFormStateEnum.Approved);
					/* 设置报表审核状态 */
					bgFormInfo.setAuditedStatus(AuditedStatusEnum.UNAUDITED);
					/* 设置报表检查状态 */
					bgFormInfo.setCheckedStatus(CheckedStatusEnum.UNCHECKED);

					/* 设置报表来源 */
					bgFormInfo
							.setDataSource(BgFormDataSourceEnum.SUPERIORSPLIT);
					bgFormInfo.setSourceType(RptSrcTypeEnum.BGFORMREPORT);

					//设置编码规则-------------------------------------------------------wuzhimin
					//					bgFormInfo.setNumber("");
					//					String companyId = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
					BgHelper.initNumber(bgFormInfo, bgFormInfo.getCU().getId()
							.toString(), ctx);
					if (bgFormInfo.getNumber() == null
							|| bgFormInfo.getNumber().equals("")) {
						bgFormInfo.setNumber(((BgFormInfo) formInfo)
								.getNumber()
								+ "_" + n);
						n++;
					}

					bgFormInfo.setStartDate(new Date());
					bgFormInfo.setEndDate(bgFormInfo.getStartDate());

					bgFormInfo.setBgTemplate(bgTemplateInfo);
					bgFormInfo.setBgScheme(bgSchemeInfo);

					//组织
					FullOrgUnitInfo fullOrgUnit = new FullOrgUnitInfo();
					IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory
							.getLocalInstance(ctx);
					fullOrgUnit = iFullOrgUnit.getFullOrgUnitInfo("where id = '"+ bgTemplateInfo.getOrgUnit().getId().toString() + "'");
					bgFormInfo.setOrgUnit(fullOrgUnit);

					//期间
					bgFormInfo.setBgPeriod(((BgFormInfo) formInfo)
							.getBgPeriod());

					//币别
					bgFormInfo.setCurrency(((BgFormInfo) formInfo)
							.getCurrency());

					//预算类型				
					bgFormInfo.setBgType(bgTemplateInfo.getBgType());

					//名称
					String bgFormName = null;
					if (bgFormInfo.getCU().getName() != null) {
						if (bgFormInfo.getCU().getId().toString().equals(
								bgFormInfo.getOrgUnit().getId().toString()))
							bgFormName = bgFormInfo.getCU().getName().trim()
									+ "_"
									+ bgFormInfo.getBgPeriod().getName().trim()
									+ "_"
									+ bgFormInfo.getBgTemplate().getName()
											.trim();
						else
							bgFormName = bgFormInfo.getCU().getName().trim()
									+ "_"
									+ bgFormInfo.getOrgUnit().getName().trim()
									+ "_"
									+ bgFormInfo.getBgPeriod().getName().trim()
									+ "_"
									+ bgFormInfo.getBgTemplate().getName()
											.trim();
					}
					bgFormInfo.setName((bgFormName.length() > 80) ? bgFormName
							.substring(1, 80) : bgFormName);

					/* 设置预算表的计量单位 */
					bgFormInfo.setMeasureUnit(bgTemplateInfo
							.getDefaultMeasureUnit());

					iBgForm.submit(bgFormInfo.getId(), bgFormInfo, true);
				}
			}
		return true;
	}

	protected byte[] _getBgFormKdf(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException {
		if (bgFormId == null)
			return null;
		
		byte[] data = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement("select t.FKdtData from T_BG_RefDisCountBgForm t" +
						" left outer join T_BG_BgDisCountForm c on t.FBgCollectId = c.FID " + 
						" where t.FBgFormId = ? and c.FState <> 102");
			pstmt.setString(1, bgFormId.toString());
			rst = pstmt.executeQuery();
			
			if (rst.next()) {
				data = BgInfoHelper.getBytes(rst, 1);
			}
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}
		
		return data;
	}

	protected Object[] _updateBgFormData(Context ctx, String bgFormID, IObjectValue bgForm, ArrayList datas) throws BOSException, EASBizException {
		Book book = null;

		try {
			book = ((BgFormInfo) bgForm).getBook();
		} catch (Exception e) {
			return null;
		}

		if (book == null)
			return null;
		Object[] obj = new Object[3];
		HashMap adjustMap = new HashMap();
		/*取数公式对应的Map*/
		Map valueMap = new HashMap();
		 if (datas != null) {
				int index = 0;
				
				ArrayList tbl = null, row = null;
				book.setCalculate(false);
				for(int n=0 ; n < book.getSheetCount(); n++) {
					Sheet table = book.getSheet(n);
//					table.setFormulaMode(KDTStyleConstants.FORMULA_VALUE_CONCURRENCE);
					
					tbl = (ArrayList) datas.get(index);
					if (tbl != null) {
						int rowCount = table.getMaxRowIndex() + 1;
						int colCount = table.getMaxColIndex() + 1;
						
						for (int r=0; r<rowCount; r++) {
			                for (int c=0; c<colCount; c++) {
			                	Cell cell = table.getCell(r, c, true);
			                	/* 判断有没有取数公式 */
			                	boolean b = BgNFSHelper.checkHasExpression(cell);
			                	
			                	// 原因未知,不调用该代码,会导致保存的时候,按A1格式保存公式
			                	if (b)
			                		cell.updateFormula();
			                	
			                	if (b && tbl.size() >= r) {
			                		row = (ArrayList) tbl.get(r);
			                		
			                		if (row.get(c) != null)
			                			BgNFSHelper.setValue(cell, row.get(c));
			                		else
			                			BgNFSHelper.setValue(cell, null);
			                		
			                		/* 只更新项目公式的对象值 */
			                		if (row != null && BgNFSHelper.checkHasFormulaOfCell(cell)) {			                				
		                				String formulaString = BgNFSHelper.getFormulaOfCell(cell);
		                				
		                				if (formulaString != null && formulaString.trim().length() > 0) {
		                					if (row.get(c) != null) {
		                						BgNFSHelper.getItemFormula(cell).putExt(BgItemFormulaHelper.BG_BUDGET_VALUE, String.valueOf(row.get(c)));			          
		                					} else {
		                						BgNFSHelper.getItemFormula(cell).putExt(BgItemFormulaHelper.BG_BUDGET_VALUE, null);
		                					}
		                					adjustMap.put(formulaString, row.get(c));
		                				}
		                			}
			                		/**
		                			 * 处理只有取数公式的地方
		                			 * @author kaifei_yi
		                			 * 增加时间：2008-06-12
		                			 */
		                			else{
		                				String expr = BgNFSHelper.getExpression(cell);
		                				valueMap.put(expr, row.get(c));
		                			}
			                	}
			                }
			            }
					}
					index++;
				}
			}
		 byte[] data = null;
		try {
			data = IOHelper.packBook(book);
		} catch (IOException e) {
			throw new BgException(BgException.DATABASEERROR, new String[] { e.getMessage() });
		} 
		
		obj[0] = adjustMap;
		obj[1] = data;
		obj[2] = valueMap;
		return obj;
	}

	protected void _updateDataForForm(Context ctx, String bgDisCountId, Map dataMap) throws BOSException, EASBizException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement refPstmt = null;	
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement("update T_BG_BgDisCountForm " +
						" set  FKdtData = ?" + 
						" where FBgFormID = ? and fid = ?");
			refPstmt = conn.prepareStatement("update T_BG_RefDisCountBgForm " +
					" set  FKdtData = ?" + 
					" where FBgFormID = ? and FBgCollectID = ?");
			String typeId = null;
			String[] type = null;
			for(Iterator iter=dataMap.keySet().iterator(); iter.hasNext();) {
				typeId = (String) iter.next();
				type = typeId.split("`");
				if(type[0].equals("from")){
					pstmt.setBytes(1,  ((RefDisCountBgFormInfo)dataMap.get(typeId)).getKdtData());
					pstmt.setString(2, (String)type[1]);
					pstmt.setString(3, bgDisCountId);
					pstmt.executeUpdate();		
				} else if (type[0].equals("ref")) {
					refPstmt.setBytes(1, ((RefDisCountBgFormInfo)dataMap.get(typeId)).getKdtData());
					refPstmt.setString(2, (String)type[1]);
					refPstmt.setString(3, bgDisCountId);
					refPstmt.executeUpdate();		
				}
			}				
			
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(refPstmt);
			SQLUtils.cleanup(pstmt, conn);
		}
		
		
	}

	protected Object[] _updateDataToBgTmpData(Context ctx, String formId, Object datas) throws BOSException, EASBizException {
		Map data = (HashMap)datas;
		String[] items = getStrings(data);
		Map haveData = new HashMap();	
		Map notHaveData = new HashMap();
		BgDataInfo bgData = new BgDataInfo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtBgData = null;
		PreparedStatement pstmtBgDataIds = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rst = null;
		Map spData = null;
		
		String[] bgDataIds = new String[6];
		
		
		for(int n = 1; (n-1) * 50 < items.length; n ++){
			spData = new HashMap();
			String[] cutItems = null;
			if(n * 50 > items.length){
				cutItems = new String[items.length - (n-1) * 50];
				for(int m = 0; (n-1) * 50 + m < items.length; m ++){
					cutItems[m] = items[(n-1) * 50 + m];
					spData.put(items[(n-1) * 50 + m], data.get(items[(n-1) * 50 + m]));
				}
			}else{
				cutItems = new String[50];
				for(int m = 0; m < 50 ; m ++){
					cutItems[m] = items[(n-1) * 50 + m];
					spData.put(items[(n-1) * 50 + m], data.get(items[(n-1) * 50 + m]));
				}
				
			}

			List _tmpList = new ArrayList();
			
			StringBuffer sql = new StringBuffer();
			sql.append("select fformula from T_BG_BgTmpData where Fbgformid = ?");
			
			StringBuffer bgDataSql = new StringBuffer();
			bgDataSql.append("select fformula, fBgValue from t_bg_bgdata where fbgformid = ? and fformula in (" + BgNSQLHelper.getParamSizeString(cutItems.length) + ")");
			
			_tmpList.add(formId);
			for(int i=0, in=cutItems.length; i<in; i++)
				_tmpList.add(cutItems[i]);
			
			StringBuffer bgDataIdsSql = new StringBuffer();
			bgDataIdsSql.append("select FOrgUnitId, FBgSchemeId, FBgTemplateId, " +
	    			"  FBgPeriodId, FCurrencyId from t_bg_bgform where fid = ? ");
			
			
			StringBuffer sql2 = new StringBuffer();
			sql2.append("Insert Into T_BG_BgTmpData (FID, FBgValue,  FBizActual, FOrgUnitId, FBgSchemeId, FBgTemplateId, " +
	    			"FBgFormId, FFormula,  FBgElementId, FBgPeriodId, FCurrencyId) " +
	    			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			StringBuffer sql3 = new StringBuffer();
			sql3.append("update t_bg_bgTmpdata set fbgvalue = ?" +
					" where fbgformId = ? and fformula = ? ");
			
			StringBuffer sql4 = new StringBuffer();
			sql4.append("update t_bg_bgTmpdata set FBgBalance = FBgValue - FBizActual " +
					" where fbgformId = ? ");
		
			try {
				conn = getConnection(ctx);

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, formId);
				rst = pstmt.executeQuery();

				while (rst.next()) {
					haveData.put(rst.getString(1), null);
				}
				SQLUtils.cleanup(rst, pstmt);

				pstmtBgData = conn.prepareStatement(bgDataSql.toString());
				BgNSQLHelper.setParams(pstmtBgData, _tmpList);
				rst = pstmtBgData.executeQuery();

				while (rst.next()) {
					if (!haveData.containsKey(rst.getString(1)))
						notHaveData.put(rst.getString(1), rst.getBigDecimal(2));
				}
				SQLUtils.cleanup(rst, pstmtBgData);
			
				if (bgDataIds[0] == null) {
					try {
						pstmtBgDataIds = conn.prepareStatement(bgDataIdsSql.toString());
						pstmtBgDataIds.setString(1, formId);
						rst = pstmtBgDataIds.executeQuery();

						if (rst.next()) {
							bgDataIds[0] = rst.getString(1);
							bgDataIds[1] = rst.getString(2);
							bgDataIds[2] = rst.getString(3);
							bgDataIds[3] = "element";
							bgDataIds[4] = rst.getString(4);
							bgDataIds[5] = rst.getString(5);
						}
					} finally {
						SQLUtils.cleanup(rst, pstmtBgDataIds);
					}
				}

				for (Iterator iter = spData.keySet().iterator(); iter.hasNext();) {
					String formula = (String) iter.next();
					if (haveData.containsKey(formula)) {
						try {
							pstmt3 = conn.prepareStatement(sql3.toString());
							if (spData.get(formula) instanceof BigDecimal) {
								pstmt3.setBigDecimal(1, (BigDecimal) spData.get(formula));
							} else {
								pstmt3.setBigDecimal(1, new BigDecimal("0"));
							}
							pstmt3.setString(2, formId);
							pstmt3.setString(3, formula);
							pstmt3.executeUpdate();
						} finally {
							SQLUtils.cleanup(pstmt3);
						}
						
					} else {
						try {
							pstmt2 = conn.prepareStatement(sql2.toString());
							pstmt2.setString(1, BOSUuid.create(bgData.getBOSType()).toString());
							if (spData.get(formula) instanceof BigDecimal) {
								pstmt2.setBigDecimal(2, (BigDecimal) spData.get(formula));
							} else {
								pstmt2.setBigDecimal(2, new BigDecimal("0"));
							}
							if (notHaveData.get(formula) instanceof BigDecimal) {
								pstmt2.setBigDecimal(3, (BigDecimal) notHaveData.get(formula));
							} else {
								pstmt2.setBigDecimal(3, new BigDecimal("0"));
							}
							pstmt2.setString(4, bgDataIds[0]);
							pstmt2.setString(5, bgDataIds[1]);
							pstmt2.setString(6, bgDataIds[2]);
							pstmt2.setString(7, formId);
							pstmt2.setString(8, formula);
							pstmt2.setString(9, bgDataIds[3]);
							pstmt2.setString(10, bgDataIds[4]);
							pstmt2.setString(11, bgDataIds[5]);
							pstmt2.executeUpdate();
						} finally {
							SQLUtils.cleanup(pstmt2);
						}
					}
				}

				pstmt4 = conn.prepareStatement(sql4.toString());
				pstmt4.setString(1, formId);
				pstmt4.executeUpdate();

			} catch (SQLException ex) {
				throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
			} catch (BOSException ex) {
				throw ex;
			} finally {
				SQLUtils.cleanup(rst, pstmt, conn);
				SQLUtils.cleanup(pstmt2);
				SQLUtils.cleanup(pstmt3);
				SQLUtils.cleanup(pstmt4);
				SQLUtils.cleanup(pstmtBgData);
				SQLUtils.cleanup(pstmtBgDataIds);
				SQLUtils.cleanup(conn);
			}
		}
		return null;
	}
	
	private String[] getStrings(Map data) {
		String[] items = new String[data.size()];
		int i = 0;
		for(Iterator iter = data.keySet().iterator(); iter.hasNext();){
			items[i] = (String) iter.next();
			i++;
		}
		return items;
	}

	protected Map _getOldData(Context ctx, Map bgFormId) throws BOSException , EASBizException{
		Map itemValue = new HashMap();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select fbgformid, fformula, fbgvalue  from T_bg_bgdata where fbgformid = ?");
		try {
			conn = getConnection(ctx);
			
			pstmt = conn.prepareStatement(sql.toString());
			for(Iterator ite = bgFormId.keySet().iterator();ite.hasNext();){
				String id = (String) ite.next();
				pstmt.setString(1, id);
				rst = pstmt.executeQuery();
				
				while(rst.next()) {
					itemValue.put(rst.getString(1)+ rst.getString(2), rst.getBigDecimal(3)) ;
				}
			}
			
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);			
		}
		return itemValue;
	}

	protected Object _updateData(Context ctx, String colId, Map addNewData, Map delOldData, boolean isCol, byte[] newData) throws BOSException, EASBizException {
		byte[] data = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("kdtData");
		sic.add("bgForm.id");
		SelectorItemCollection formSic = new SelectorItemCollection();
		formSic.add(new SelectorItemInfo("id"));       
		formSic.add(new SelectorItemInfo("data"));
       
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(colId));
		BgDisCountFormInfo info = null;
		RefDisCountBgFormInfo refInfo = null;
		BgFormInfo bgFormInfo = null;
		IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
		try {
			if(isCol){
				if(newData != null){				
					info = new BgDisCountFormInfo();				
			        info.setKdtData(newData);
			    }else{
			    	info = (BgDisCountFormInfo) getValue(ctx, pk, sic);
			    }			
			}else{
				if(newData != null){
					refInfo = new RefDisCountBgFormInfo();
					refInfo.setKdtData(newData);
			    }else{
			    	IRefDisCountBgForm iRef = RefDisCountBgFormFactory.getLocalInstance(ctx);
					refInfo = iRef.getRefDisCountBgFormInfo(pk, sic);
			    }			
			}
		} catch (IOException e) {
			throw new BgException(BgException.DATABASEERROR, new String[] { e.getMessage() });
		} catch (ClassNotFoundException e) {
			throw new BgException(BgException.DATABASEERROR, new String[] { e.getMessage() });
		}
		if(info != null){
			try {						
				Book book = null;
				if(isCol){
					if(info.getKdtData() != null){
						book = info.getBook();
					}else{
						bgFormInfo = iBgForm.getBgFormInfo(new ObjectUuidPK(info.getBgForm().getId()), formSic);
						book = bgFormInfo.getBook();
					}
					
				}else{
					if(refInfo.getKdtData() != null){
						book = refInfo.getBook();
					}else{
						bgFormInfo = iBgForm.getBgFormInfo(new ObjectUuidPK(refInfo.getBgForm().getId()), formSic);
						book = bgFormInfo.getBook();
					}				
				}
				Sheet sheet = null;
				Vector cellObj = null;
				for(Iterator ite = delOldData.keySet().iterator(); ite.hasNext(); ){
					String fomulaString = (String) ite.next();
					cellObj = (Vector) delOldData.get(fomulaString);
					sheet = book.getSheetByID((String) cellObj.get(0));
					int rowIndex = ((Integer) cellObj.get(1)).intValue();
					int colIndex = ((Integer) cellObj.get(2)).intValue();
					Cell nCell = sheet.getCell(rowIndex, colIndex, true);
					BgNFSHelper.setItemFormula(nCell, null);
					BgNFSHelper.setValue(nCell,null);						
				}				
				for(Iterator ite = addNewData.keySet().iterator(); ite.hasNext(); ){
					String fomulaString = (String) ite.next();
					cellObj = (Vector) addNewData.get(fomulaString);	
					sheet = book.getSheetByID((String) cellObj.get(0));
					int rowIndex = ((Integer) cellObj.get(1)).intValue();
					int colIndex = ((Integer) cellObj.get(2)).intValue();
					Variant value = (Variant) cellObj.get(3);
					Cell nCell = sheet.getCell(rowIndex, colIndex, true);
					BgNFSHelper.setFormulaOfCell(nCell,fomulaString, true);
					BgNFSHelper.setValue(nCell, value);					
				}
				BgNFSHelper.checkBookSign(book);
				
				data = IOHelper.packBook(book);
			} catch(SQLException ex) {
				throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
			} catch(BOSException ex) {
				throw ex;
			} catch (Exception e) {
				throw new BgException(BgException.DATABASEERROR, new String[] { e.getMessage() });
			}finally {
				
			}
		}
		return data;
	
	}

	protected void _addNewData(Context ctx, Map bgCollIds) throws BOSException, EASBizException {		
		if(bgCollIds == null)
			return;
		String decId = (String) bgCollIds.get("colId");
		String mainFormID = (String) bgCollIds.get("mainFormId");
		Map refIds = (Map) bgCollIds.get("refIds");
		byte[] data = null;
		//填充主表
		String mainSql = "update t_bg_bgDisCountform set fkdtdata = ? where fid = ?";
		String valueSql = "select fdata from t_bg_bgform where fid = ?";
		
		Connection conn = null;
		PreparedStatement pstmtM = null;
		PreparedStatement pstmtV = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_BG_RefDisCountBgForm set fkdtdata = ? where fbgformid = ? and fbgcollectid = ?");
		try {
			conn = getConnection(ctx);
			
			try {
				pstmtV = conn.prepareStatement(valueSql);
				pstmtV.setString(1,mainFormID);
				rst = pstmtV.executeQuery();
				
				if (rst.next()) {
					data = BgInfoHelper.getBytes(rst, 1);
				}
			} finally {
				SQLUtils.cleanup(rst, pstmtV);
			}
			
			
			try {
				pstmtM = conn.prepareStatement(mainSql);
				pstmtM.setBytes(1, data);
				pstmtM.setString(2, decId);
				pstmtM.executeUpdate();
				
				SQLUtils.cleanup(pstmtM);
				
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmtV = conn.prepareStatement(valueSql);
				
				for(Iterator ite = refIds.keySet().iterator();ite.hasNext();){
					String id = (String) ite.next();
					pstmtV.setString(1,id);
					rst = pstmtV.executeQuery();
					
					data = null;
//					pstmtV = null;
					
					if (rst.next()) {
						data = BgInfoHelper.getBytes(rst, 1);
					}
					SQLUtils.cleanup(rst);
					
					pstmt.setBytes(1, data);
					pstmt.setString(2, id);
					pstmt.setString(3, decId);
					pstmt.executeUpdate();				
				}
			} finally {
				SQLUtils.cleanup(rst);
				SQLUtils.cleanup(pstmt);
				SQLUtils.cleanup(pstmtV);
				SQLUtils.cleanup(pstmtM);
			}
			
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(rst, pstmtV);
			SQLUtils.cleanup(pstmtM);
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	/**
	 * 获取调整后可用预算余额为负数的所有数据(用于视图)
	 * adjustMap:差异记录Map<组织,Map<项目公式,BgAdjustFormDiversityData>>
	 * bgFormInfo:预算表(取根模板，根方案，期间，币别)
	 */
	protected List _getAllNegativeAdjustCheckDataForView(Context ctx, Map adjustMap,BgFormInfo bgFormInfo) throws BOSException, EASBizException{
		Map inputMap = new HashMap();
		IBgFormFacade iBgFormFacade = BgFormFacadeFactory.getLocalInstance(ctx);
		Map result = iBgFormFacade.queryBgFormByOrgUnitId(bgFormInfo, adjustMap.keySet().toArray());
		for(Iterator iterator = result.keySet().iterator(); iterator.hasNext();){
			BgFormInfo obj = (BgFormInfo)result.get(iterator.next());
			inputMap.put(obj.getId(), adjustMap.get(obj.getOrgUnit().getId().toString()));
		}
		if(result.size() == 0){
			inputMap = null;
		}
		IBgDisCountForm iBgDisCountionForm = BgDisCountFormFactory.getLocalInstance(ctx);
		return iBgDisCountionForm.getAllNegativeAdjustCheckData(inputMap);
		
	}
	
	protected List _getAllNegativeAdjustCheckData(Context ctx, Map adjustMap) throws BOSException, EASBizException {
		if(adjustMap == null || adjustMap.isEmpty())
			return new ArrayList();
		String bgFormIdStr = "";
		Map newMap = new HashMap();//key:bgFormId,value:List<BgFormDiversityData>,得到每个预算表需要做调整检查的数据
		List dataList = null;
		String formulaStr = null;
		BgFormDiversityData divInfo = null;
		BgFormAdjustData adjustData = null;
		Object obj = null;
		com.kingdee.bos.util.BOSUuid bgFormId = null;
		Map resultMap = null;
		
		StringBuffer idStr = new StringBuffer();         //
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String formIdStrForFilter = null;
		String formulaStrForFilter = null;
		Set ctrlSet = new HashSet();

		List _tmpList = new ArrayList();
		
		//过滤掉没有设置控制方式的数据记录 R101222-365 add by coco_hu at 2011-8-3
		//由于视图的加入，t_bg_bgdata中的数据只能通过  组织+方案+项目公式 去查询  modified by coco_hu at 2012-6-18
		ctrlSet = filterNoCtrlFormula(ctx, adjustMap);
		
		
		/*for (Iterator it = adjustMap.keySet().iterator(); it.hasNext();) {
			obj = it.next();
			if (obj instanceof com.kingdee.bos.util.BOSUuid)
				bgFormId = (com.kingdee.bos.util.BOSUuid) obj;

			idStr.append("?,");
			_tmpList.add(bgFormId.toString());
		}
		
		if (idStr.length() > 0)
			idStr.delete(idStr.length()-1, idStr.length());
			
		StringBuffer sql = new StringBuffer();
		sql.append("select form.FID as formId,bgdata.FFormula as formula" +
				" from T_BG_BgForm form inner join  T_BG_BgData bgdata on form.FAdjustID = bgdata.FBgFormId " +
				" where form.FID in(" +idStr.toString()+")" +
						" and bgdata.FCtrlType != ?");
		

		_tmpList.add(new Integer(BgCtrlTypeEnum.NOCTRL_VALUE));
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			BgNSQLHelper.setParams(pstmt, _tmpList);
			
			rst = pstmt.executeQuery();
			while(rst.next()){
				formIdStrForFilter =  rst.getString("formId");
				formulaStrForFilter = rst.getString("formula");
				ctrlSet.add(formIdStrForFilter+formulaStrForFilter);
			}
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}	*/
		
		
		//将需要做调整检查的数据重新封装成一个map
		for(Iterator it = adjustMap.keySet().iterator();it.hasNext();){
			dataList = new ArrayList();
			obj = it.next();			
			if(obj instanceof com.kingdee.bos.util.BOSUuid )				
				bgFormId = (com.kingdee.bos.util.BOSUuid)obj;
			else
				continue;
			bgFormIdStr = bgFormId.toString();

			Map dataMap = (Map)adjustMap.get(bgFormId);
			if(dataMap == null)
				continue;
			for(Iterator dataIt = dataMap.keySet().iterator();dataIt.hasNext();){
				formulaStr = (String)dataIt.next();
				
				if(!ctrlSet.contains(bgFormId+formulaStr)){
					continue;
				}
				divInfo = (BgFormDiversityData) dataMap.get(formulaStr);
				if(divInfo == null )
					continue;
				adjustData = new BgFormAdjustData(divInfo);					
				divInfo.setFormula(formulaStr);
				dataList.add(adjustData);
				newMap.put(bgFormIdStr,dataList);
			}			
		}
		
		resultMap = getAllOrgAndSchemeNumByFormId(ctx,newMap);
		
		return getInvalidAdjustValue(ctx,resultMap);
	}

	private Set filterNoCtrlFormula(Context ctx, Map adjustMap) throws BOSException, BgException {
		Set ctrlSet = new HashSet();
		
		EntityViewInfo bgFormView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", adjustMap.keySet(), CompareType.INCLUDE));
		bgFormView.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("bgScheme.id");
		sic.add("orgUnit.id");
		bgFormView.setSelector(sic);
		BgFormCollection bgFormCol = BgFormFactory.getLocalInstance(ctx).getBgFormCollection(bgFormView);
		Map bgFormMap = new HashMap();
		if(bgFormCol != null){
			for(int i = 0; i < bgFormCol.size(); i++){
				bgFormMap.put(bgFormCol.get(i).getId(), bgFormCol.get(i));
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select FFormula from T_BG_BgData where FctrlType != ? and FOrgUnitId = ? and FBgSchemeId = ? and FFormula in (");
		(BgNSQLHelper.addPlaceHolder(sql, BgNSQLHelper.BATCH_COUNT)).append(")");
		Object  bgFormId = null;
		BgFormInfo bgFormInfo = null;
		Object[] paraArr = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try{
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			for(Iterator iterator = bgFormMap.keySet().iterator(); iterator.hasNext(); ){
				bgFormId = iterator.next();
				bgFormInfo = (BgFormInfo) bgFormMap.get(bgFormId);
				if(adjustMap.get(bgFormId) != null){
					paraArr = ((Map)adjustMap.get(bgFormId)).keySet().toArray();
					if(paraArr.length == 0){
						continue;
					}
					int batchCount = (paraArr.length- 1) / BgNSQLHelper.BATCH_COUNT + 1;
					for (int i = 0; i < batchCount; i++) {
						pstmt.setInt(1, new Integer(BgCtrlTypeEnum.NOCTRL_VALUE));
						pstmt.setString(2, bgFormInfo.getOrgUnit().getId().toString());
						pstmt.setString(3, bgFormInfo.getBgScheme().getId().toString());
						BgNSQLHelper.setParam4Batch(pstmt, 4, i, BgNSQLHelper.BATCH_COUNT, paraArr);
						rst = pstmt.executeQuery();
						while(rst.next()){
							ctrlSet.add(bgFormId.toString()+rst.getString("FFormula"));
						}
					}
				}
			}
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}	
		return ctrlSet;
	}
	
	/**
	 * 描述：根据预算表id找到方案编码和组织编码，然后组装成Parameter中的Object[]数组
	 * @param ctx
	 * @param map
	 * @return List 
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getAllOrgAndSchemeNumByFormId(Context ctx,Map map) throws BOSException, EASBizException{
		if(map == null)
			return new HashMap();
		List divInfoList = null;
		Map resultMap = new HashMap();
		StringBuffer idStr = new StringBuffer();
		if(map.keySet().size()<=0)
			return new HashMap();
		for(Iterator it = map.keySet().iterator();it.hasNext();){
			
			idStr.append("'"+(String)it.next()+"',");
		}		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		BgFormAdjustData adjustData = null;
		Parameter param = null;
		String[] formulaParams = null;
		String formIdStr = null;
		String schemeNumber = null;
		String unitNumber = null;
		String bgFormName = null;
		String orgName = null;
		String itemNumber = null;
		String periodNumber = null;
		String elementNumber = null;
		String currencyNumber = null;
		
		String ln = "";
		Locale locale = ctx.getLocale();
		if(locale.equals(locale_L1))
			ln="L1";
		if(locale.equals(locale_L2))
			ln="L2";
		if(locale.equals(locale_L3))
			ln="L3";
		
		StringBuffer sql = new StringBuffer();
		sql.append("select form.FID as formId,form.FName as bgFormName,scheme.FNumber as schemeNumber,unit.FNumber as unitNumber," +
				" unit.FName_"+ln+" as orgName from T_BG_BgForm form inner join T_BG_BgScheme scheme on form.FBgSchemeID = scheme.FID" +
				" inner join T_ORG_BaseUnit unit on form.FOrgUnitID = unit.FID " +
				"where  form.FID in(" +idStr.toString().substring(0,idStr.length()-1)+") ");
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			rst = pstmt.executeQuery();
			while(rst.next()){
				formIdStr = rst.getString("formId");
				schemeNumber = rst.getString("schemeNumber");
				unitNumber = rst.getString("unitNumber");
				bgFormName = rst.getString("bgFormName");
				orgName = rst.getString("orgName");
				if(map.containsKey(formIdStr)){
					Map  adjustDataMap = null;
					adjustDataMap = (Map)resultMap.get(formIdStr);
					if(adjustDataMap == null ){
						adjustDataMap = new HashMap();
						resultMap.put(formIdStr,adjustDataMap);
					}						
					divInfoList = (List)map.get(formIdStr);
					for(int i=0;i<divInfoList.size();i++){
						adjustData = (BgFormAdjustData)divInfoList.get(i);
						formulaParams = BgNFSHelper.parseFormulaPara(null,adjustData.getFormula());
						itemNumber = "";
						periodNumber = "";
						elementNumber = "";
						currencyNumber = "";
						if(formulaParams.length==4){
							itemNumber = formulaParams[BgNFSHelper.paraItem];
							periodNumber =	formulaParams[BgNFSHelper.paraPeriod];
							elementNumber = formulaParams[BgNFSHelper.paraElement];
							currencyNumber = formulaParams[BgNFSHelper.paraCurrency];							
						}
						if(formulaParams.length==6){
							itemNumber = formulaParams[BgNFSHelper.paraOldItem];
							periodNumber =	formulaParams[BgNFSHelper.paraOldPeriod];
							elementNumber = formulaParams[BgNFSHelper.paraOldElement];
							currencyNumber = formulaParams[BgNFSHelper.paraOldCurrency];
						}
						itemNumber=itemNumber.replaceAll("_",",");
						Object[] obj = new Object[8];
						obj[0] = new Variant(unitNumber);
						obj[1] = new Variant(schemeNumber); 
						obj[2] = new Variant(elementNumber);
						obj[3] = new Variant(itemNumber);
						obj[4] = new Variant(currencyNumber);
						obj[5] = new Variant(periodNumber);
						obj[6] = new Variant(adjustData.getNewValue());
						obj[7] = new Variant(adjustData.getOldValue());
						
						param = new Parameter(obj,"",false);
						
						adjustData.setParam(param);
						adjustData.setBgFormName(bgFormName);
						adjustData.setOrgName(orgName);
						adjustData.setCurrency(currencyNumber);
						adjustData.setElement(elementNumber);
						adjustData.setPeriod(periodNumber);
						adjustData.setItemNumber(itemNumber);
						adjustDataMap.put(param,adjustData);							
					}						
				}				
					
			}			
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);		
		}		
		return resultMap;
	}
	
	private String itemNumber2Name(String itemNumber,List itemList,Locale locale){
		String itemName = "";
		String [] itemNumbers = itemNumber.split(",");
		BgItemInfo itemInfo = null;
		if(itemNumbers!=null && itemList != null){
			for(int i = 0,numSize = itemNumbers.length;i<numSize; i++){
				for(int j = 0,itemSize =itemList.size();j<itemSize;j++){
					itemInfo = (BgItemInfo)itemList.get(j);
					if(itemNumbers[i].equalsIgnoreCase(itemInfo.getNumber()))
						itemName+=itemInfo.getName(locale)+"_";
				}
			}
		}
		
		return itemName.length()>0 ? itemName.substring(0,itemName.length()-1) : itemName;
	}
	private String elementNumber2Name(String number,List list,Locale locale){
		String name = "";
		BgElementInfo info = null;
		for(int i = 0,size = list.size(); i < size; i++){
			info = (BgElementInfo)list.get(i);
			if(number.equalsIgnoreCase(info.getNumber()))
				name = info.getName(locale);
		}
		return name;
	}
	private String currencyNumber2Name(String number,List list,Locale locale){
		String name = "";
		CurrencyInfo info = null;
		for(int i = 0,size =list.size(); i<size; i++){
			info = (CurrencyInfo)list.get(i);
			if(number.equalsIgnoreCase(info.getNumber()))
				name = info.getName(locale);
		}
		return name;
	}
	
	private int getCurrencyPrecision(String number,List list){
		int Precision = 0;
		CurrencyInfo info = null;
		for(int i = 0,size =list.size(); i<size; i++){
			info = (CurrencyInfo)list.get(i);
			if(number.equalsIgnoreCase(info.getNumber()))
				Precision = info.getPrecision();
		}
		return Precision;
	}

	private void batchConvertElementNameByNumber(Connection conn,Context ctx,List numberList) throws EASBizException,BOSException{
		if(numberList == null ||numberList.size() ==0)
			return;
		
		PreparedStatement pstmt = null;
		ResultSet rst = null;

		StringBuffer query = new StringBuffer();
		List resultList = new ArrayList();
		List _tmpList = new ArrayList();
		Locale lc = ctx.getLocale();
		BgFormAdjustData data = null;
		Set _tmpSet = getNumberQueryString(numberList,ELEMENT_NUMBER_TYPE);
		
		_tmpList.addAll(_tmpSet);
		
		try {
			query.append("select Fnumber,FName_l1,Fname_l2,Fname_l3 from T_BG_BgElement ");
			if (!_tmpSet.isEmpty())
				query.append(" where FNumber in ("+BgNSQLHelper.getParamSizeString(_tmpSet) + ")");
			
			pstmt = conn.prepareStatement(query.toString());
			
			BgNSQLHelper.setParams(pstmt, _tmpList);
			
			rst = pstmt.executeQuery();
			
			while (rst.next()) {					
				BgElementInfo _info = new BgElementInfo();
				_info.setNumber(rst.getString("FNumber"));
				_info.setName(rst.getString("FName_L1"), locale_L1);
				_info.setName(rst.getString("FName_L2"), locale_L2);
				_info.setName(rst.getString("FName_L3"), locale_L3);					
				resultList.add(_info);
			}
		} catch (SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		} finally {
			SQLUtils.cleanup(rst, pstmt);
		}
		for(int i=0,size = numberList.size();i<size;i++){
			data = (BgFormAdjustData)numberList.get(i);
			data.setElement(elementNumber2Name(data.getElement(),resultList,lc));
		}	
	
	}
	private void batchConvertCurrencyNameByNumber(Connection conn,Context ctx, List numberList) throws EASBizException,BOSException{
		
		if(numberList == null || numberList.size()==0)
			return;
	
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		StringBuffer query = new StringBuffer();
		List resultList = new ArrayList();
		List _tmpList = new ArrayList();
		Set _tmpSet = getNumberQueryString(numberList,CURRENCY_NUMBER_TYPE);
		_tmpList.addAll(_tmpSet);
		Locale lc = ctx.getLocale();
		BgFormAdjustData data = null;
		
		try {
			query.append("select FPrecision,Fnumber,FName_l1,Fname_l2,Fname_l3 from t_bd_currency");
			if (!_tmpSet.isEmpty())
				query.append(" where FNumber in ("+BgNSQLHelper.getParamSizeString(_tmpSet) + ")");
			
			pstmt = conn.prepareStatement(query.toString());
			
			BgNSQLHelper.setParams(pstmt, _tmpList);
			
			rst = pstmt.executeQuery();
			
			while (rst.next()) {
				
				CurrencyInfo _info = new CurrencyInfo();
				_info.setNumber(rst.getString("FNumber"));
				_info.setName(rst.getString("FName_L1"), locale_L1);
				_info.setName(rst.getString("FName_L2"), locale_L2);
				_info.setName(rst.getString("FName_L3"), locale_L3);
				_info.setPrecision(rst.getInt("FPrecision"));
				resultList.add(_info);
			}
		} catch (SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		}finally {
			SQLUtils.cleanup(rst, pstmt);
		} 
		for(int i=0,size = numberList.size();i<size;i++){
			data = (BgFormAdjustData)numberList.get(i);
			data.setPrecision(getCurrencyPrecision(data.getCurrency(),resultList));
			data.setCurrency(currencyNumber2Name(data.getCurrency(),resultList,lc));
		}		
	
	}
	private void batchConvertItemNameByNumber(Connection conn,Context ctx,List numberList, boolean canRepeat) throws EASBizException, BOSException{		
		if(numberList == null || numberList.size()==0)
			return;
		BgItemInfo _bgItemInfo = null;

		CostCenterOrgUnitInfo ccOrgUnitInfo = ContextUtil.getCurrentCostUnit(ctx);
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		String _number = null;
		StringBuffer query = new StringBuffer();
		List resultList = new ArrayList();
		List _tmpList = new ArrayList();
		
		Locale lc = ctx.getLocale();
		BgFormAdjustData data = null;
		
		Set _tmpSet = getNumberQueryString(numberList,BGITEM_NUMBER_TYPE);
		_tmpList.addAll(_tmpSet);
		_tmpList.add(BgSHelper.getIsolateOrg(ctx,ccOrgUnitInfo.getId().toString()));
		
		try {
			if (ccOrgUnitInfo != null ) {
				query.append("select FNumber,FName_L1,FName_L2,FName_L3 from T_BG_BgItem where " +
						"FNumber in ("+BgNSQLHelper.getParamSizeString(_tmpSet) +") and FOrgUnitId = ?");
			
			pstmt = conn.prepareStatement(query.toString());
			
			BgNSQLHelper.setParams(pstmt, _tmpList);
			
			rst = pstmt.executeQuery();
			
			while (rst.next()) {				
					_bgItemInfo = new BgItemInfo();
					_bgItemInfo.setNumber(rst.getString("FNumber"));
					_bgItemInfo.setName(rst.getString("FName_L1"), locale_L1);
					_bgItemInfo.setName(rst.getString("FName_L2"), locale_L2);
					_bgItemInfo.setName(rst.getString("FName_L3"), locale_L3);					
					resultList.add(_bgItemInfo);
				}
			}
			
		} catch (SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		} catch (Exception e) {
			logger.error("budget error:", e);
		} finally {
			SQLUtils.cleanup(rst, pstmt);
		}
		for(int i=0,size=numberList.size();i<size;i++){
			data = (BgFormAdjustData)numberList.get(i);
			data.setItemName(itemNumber2Name(data.getItemNumber(),resultList,lc));
		}
	}
	
	
	private Set getNumberQueryString(List numberList,String numberType){
		Set _tmpSet = new HashSet();
		BgFormAdjustData data = null;
		String[] number = null;
		
		for(int i = 0; i < numberList.size(); i++){
			number = null;
			data = (BgFormAdjustData)numberList.get(i);
			if(numberType.equals(CURRENCY_NUMBER_TYPE)){
				number = new String[1];
				number[0] = data.getCurrency();
			}else if(numberType.equals(ELEMENT_NUMBER_TYPE)){
				number = new String[1];
				number[0] = data.getElement();
			}else if(numberType.equals(BGITEM_NUMBER_TYPE)){
				number = data.getItemNumber().split(",");
			}
			
			if (number == null || number.length <= 0)
				continue;
			
			for(int j = 0,size = number.length; j<size; j++){
				_tmpSet.add(number[j]);
			}				
		}
		return _tmpSet;
	}
	protected Set getOrgUnitIds(Connection conn, BOSUuid orgUnitId,boolean canRepeat) throws EASBizException {
    	Set orgUnitIdSet = new HashSet();
		if (conn == null || orgUnitId == null)
			return orgUnitIdSet;

		String[] numberStr = null;
		String _number = null, _longnumber = null, _orgUnitId = null;
		StringBuffer query = new StringBuffer();
		_orgUnitId = orgUnitId.toString();
		
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		try {

			pstmt = conn.prepareStatement("select FNumber, FLongNumber from T_ORG_CostCenter where FID = ?");
			pstmt.setString(1, _orgUnitId);
			rst = pstmt.executeQuery();
			
			if (rst.next()) {
				_number = rst.getString(1);
				_longnumber = rst.getString(2);
			}

			SQLUtils.cleanup(rst, pstmt);
			
			/* 获取指定的上下级组织ID */
			if (!StringUtils.isEmpty(_number) && !StringUtils.isEmpty(_number)) {
				query.append("select FID from T_ORG_CostCenter where ");
				
				orgUnitIdSet = new HashSet();
				List _tmpList = new ArrayList();
				
		    	numberStr = _longnumber.split("\\!");
		    	if (numberStr != null) {
	    			query.append(" FNumber IN (");
		    		for(int i=0, in=numberStr.length; i<in; i++) {
		    			query.append("?,");
		    			_tmpList.add(numberStr[i]);
		    		}
		    		query.delete(query.length()-1, query.length());
		    		query.append(") ");
		    	}
		    	
		    	if(!canRepeat) {
		    		query.append(" or FLongNumber like ?");
		    		_tmpList.add(_longnumber + "!%");
		    	}
		    	
		    	pstmt = conn.prepareStatement(query.toString());
		    	
		    	BgNSQLHelper.setParams(pstmt, _tmpList);
		    	
		    	rst = pstmt.executeQuery();
		    	
		    	while (rst.next()) {
		    		orgUnitIdSet.add(rst.getString(1));
		    	}
		    	
				SQLUtils.cleanup(rst, pstmt);
			}
		} catch(SQLException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] {ex.getMessage()});
		} finally {
			SQLUtils.cleanup(rst, pstmt);
		}
		
		return orgUnitIdSet;
	}
	
	/**
	 * 描述：得到调整后可用预算余额为负数的集合
	 * @author kaifei_yi
	 * @param ctx
	 * @param map
	 * @return List
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private List getInvalidAdjustValue(Context ctx,Map map) throws BOSException, EASBizException{
		if(map == null)
			return new ArrayList();
		Connection conn = null;
		List result = new ArrayList();
		Map bgFormMap = new HashMap();
		boolean canRepeat = false;

		/*预算表id*/
		String formIdStr = null;
		/*调整的数据Map*/
		Map adjustDataMap = null;
		/*BgForm接口*/
		IBgForm iBgForm  = BgFormFactory.getLocalInstance(ctx);
		/*预算表info*/
		BgFormInfo bgForm =null;
		/*调整的数据对象*/
		BgFormAdjustData adjustData = null;
		/*调整后的可用预算余额*/
		BigDecimal afterAdjustBalance = null;
		/*批量计算的参数*/
		SortedParameterArray params = new SortedParameterArray();
		/*参数数组*/
		Parameter []parameters = null;
		/*参数*/
		Parameter param = null;
		/*参数*/
		Parameter _parameter = null;
		/*后台计算处理类*/
		BgCalculatorProcess process = null;
		/*计算的context*/
		ReportCalculateContext context = null;
		/*预算计算的adapter*/
		com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter adapter = null;
		/*报表变量类*/
		ReportVariables variables = new ReportVariables();
		/*计算所使用的错误处理类*/
		ReportCalculateErrorProvider errorProvider = new ReportCalculateErrorProvider();
		
		/*将所有已变化的预算数的值传入params中*/
		for(Iterator it = map.keySet().iterator();it.hasNext();){
			formIdStr = (String)it.next();
			adjustDataMap = (Map)map.get(formIdStr);
			parameters = new Parameter[adjustDataMap.keySet().size()];
			int index = 0;
			for(Iterator dataIt = adjustDataMap.keySet().iterator();dataIt.hasNext();){
				param = (Parameter)dataIt.next();
				parameters [index]=param;
				index++;
			}
			index = 0;
			params.insertAll(parameters);
		}
		//由于sql通讯超标,改为一次性把所有的预算表都取出来.
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getBgFormInfo2());
		FilterInfo filter = new FilterInfo();
		Set bgFormIdSet = new HashSet();
		for(Iterator it = map.keySet().iterator();it.hasNext();){
			process = new BgCalculatorProcess();
			String _formIdStr = (String)it.next();
			adjustDataMap = (Map)map.get(formIdStr);
			if(adjustDataMap == null || adjustDataMap.isEmpty())
				continue;
			bgFormIdSet.add(_formIdStr);
			
		}
		
		filter.getFilterItems().add(new FilterItemInfo("id", bgFormIdSet, CompareType.INCLUDE));
		evi.setFilter(filter);
		BgFormCollection bgFormCols = null;
		try{
			bgFormCols = iBgForm.getBgFormCollection(evi);
		}catch(Exception e){
			logger.error("budget error:", e);
		}
		if(bgFormCols != null && bgFormCols.size() >0){
			for(int i=0,size = bgFormCols.size();i<size;i++){
				BgFormInfo info = bgFormCols.get(i);
				bgFormMap.put(info.getId().toString(), info);
			}
		}
		/*根据预算表Id循环计算调整后可用预算余额*/
		for(Iterator it = map.keySet().iterator();it.hasNext();){
			process = new BgCalculatorProcess();
			formIdStr = (String)it.next();
			adjustDataMap = (Map)map.get(formIdStr);
			if(adjustDataMap == null || adjustDataMap.isEmpty() || !bgFormMap.containsKey(formIdStr))
				continue;	
			bgForm = (BgFormInfo)bgFormMap.get(formIdStr);
			if(bgForm == null)
				continue;
			adapter = new com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter(bgForm);
			
			context = new ReportCalculateContext(ctx,adapter,variables,errorProvider,null);

			process.initialize("bgNAvlBal", context, ctx, params);
			//计算可用预算余额
			process.process();
			_parameter = null;
			for (int i = 0, in = params.size(); i < in; i++) {
				_parameter = params.getParameter(i);
				if(adjustDataMap.containsKey(_parameter)){
					adjustData = (BgFormAdjustData)adjustDataMap.get(_parameter);
					if(_parameter.getValue() instanceof BigDecimal){
						afterAdjustBalance = (BigDecimal)_parameter.getValue();
						if( afterAdjustBalance!=null && afterAdjustBalance.doubleValue()<0){
							adjustData.setAdjustBalance(afterAdjustBalance);
							result.add(adjustData.replicate());
						}
					}else if(_parameter.getValue() instanceof String){
						adjustData.setBadFunction((String)_parameter.getValue());
						result.add(adjustData.replicate());
					}					
				}					
			}
		}
		if(result.size()==0)
			return result;
		canRepeat = BgSHelper.isBgItemNumberRepeat(ctx);
		try{
			conn = getConnection(ctx);
			/*通过币别编码转换成相应的名称*/
			batchConvertCurrencyNameByNumber(conn,ctx,result);
			/*通过要素编码转换成相应的名称*/
			batchConvertElementNameByNumber(conn,ctx,result);
			/*通过项目编码转换成相应的名称*/
			batchConvertItemNameByNumber(conn,ctx,result,canRepeat);
		}catch(BOSException ex) {
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
					throw new BgException(BgException.DATABASEERROR, new String[] { e.getMessage() });
				}
			}	
		}		
		return result;		
	}
	
	public SelectorItemCollection getBgFormInfo2() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
       	sic.add(new SelectorItemInfo("data"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("bgScheme.*")); 
        sic.add(new SelectorItemInfo("bgTemplate.*"));
        sic.add(new SelectorItemInfo("currency.*"));
       	sic.add(new SelectorItemInfo("sheetParam.*"));
       	sic.add(new SelectorItemInfo("bgPeriod.*"));
        sic.add(new SelectorItemInfo("state"));
        return sic;
	}

	/**
	 * 描述：重计算性能优化
	 * @author kaifei_yi 
	 */
	protected Object[] _calculate(Context ctx, String bgFormId, String mainFormId, String bgDisCountId, Map adjustMap) throws BOSException, EASBizException {
		if(bgFormId == null || mainFormId == null || bgDisCountId == null)
			return null;
		
		IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
		
		IObjectPK pk = new ObjectUuidPK(bgFormId);
		IObjectPK mainPk = new ObjectUuidPK(mainFormId);

		if(!iBgForm.exists(pk))
			return null;
		if(!iBgForm.exists(mainPk))
			return null;
		
		
		byte[] newData = null;
		Object[] obj = null;
		String key = null;
		
		
		BgFormInfo info = iBgForm.getBgFormInfo(new ObjectUuidPK(bgFormId), getBgFormInfoToRefCal());
		info.put("myBgPeriod",info.getBgPeriod());
		try{
			//得到预算表book的二进制数据
			if (info.getId().toString().equals(mainFormId)) {
				newData = getMainNewData(ctx,info.getId(), bgDisCountId);
				key = "from`"+info.getId().toString();
			} else {
				newData = getRefNewData(ctx,info.getId(), bgDisCountId);
				key = "ref`"+info.getId().toString();
			}
			
			//将差异数据解析后放入到二进制对象中
			if (newData != null) {
				info.setData(getNewDataToRefCal(newData, IOHelper.unpackBook(newData), info.getId(), adjustMap));
			} else if (info.getData() != null) {
				info.setData(getNewDataToRefCal(info.getData(),	IOHelper.unpackBook(info.getData()), info.getId(), adjustMap));
			}
			
			Map newAdjustMap = new HashMap();
			if(!adjustMap.isEmpty())
			{
				BgAdjustFormDiversityData value = null;
				for(Iterator it = adjustMap.keySet().iterator();it.hasNext();)
				{
					key = (String)it.next();
					value = (BgAdjustFormDiversityData)adjustMap.get(key);
					key = info.getOrgUnit().getId().toString()+BgNConstants.SEP_ONE_STR+info.getBgScheme().getId().toString()
							+BgNConstants.SEP_ONE_STR+key;
					newAdjustMap.put(key, value.getNewValue());
				}
			}
			
			//R130315-0045    回迁 R121115-0319  把调整值传入计算后台，  让后台在计算时先计算调整数，确保在没有保存数据的情况下取数公式计算正确
			BgNFSHelper.setFormulaMap(info, newAdjustMap);
			
			
			//后台计算
			ArrayList tableData = ReportFactory.getLocalInstance(ctx).backCalcEx(info, info.getZipData(), null);
			
			//根据计算结果得到返回结果object数组
			obj = _updateBgFormData(ctx,bgFormId, info, tableData);
			
			//更新临时数据
			_updateDataToBgTmpData(ctx,info.getId().toString(), obj[0]);
			
		}catch(Exception e){
			logger.error("budget error:", e);
		}
		Object[] result = new Object[2];
		if(obj != null){
			result[0] = obj[0];
			//只有主表的有取数公式无项目公式的地方需要更新，分录表不需要
			if(bgFormId.equals(mainFormId))
				result[1] = (Map)obj[2];
			else
				result[1] = null;
			RefDisCountBgFormInfo refInfo = null;
			Map dataMap = new HashMap();
			try {
				refInfo = new RefDisCountBgFormInfo();
			} catch (IOException e) {
				logger.error("budget error:", e);
			} catch (ClassNotFoundException e) {
				logger.error("budget error:", e);
			}
			refInfo.setKdtData((byte[])obj[1]);
			dataMap.put(key,refInfo);
			if(refInfo != null)
				_updateDataForForm(ctx,bgDisCountId,dataMap);
		}
		
		return result;
	}
	/**
	 * 将差异数据存入二进制数据中
	 * @param data
	 * @param book
	 * @param bgFormId
	 * @param adjustMap
	 * @return
	 * @throws Exception
	 */
	private byte[] getNewDataToRefCal(byte[] data, Book book, BOSUuid bgFormId, Map adjustMap) throws Exception {
		if (adjustMap == null || adjustMap.isEmpty() || adjustMap.toString().equals("{}")) 
			return data;
		
		if(book == null)
			return data;		
		Sheet table = null;	
		
		
		int rowIndex = 0, colIndex = 0, rowCount = 0, colCount = 0;
		String formulaString = null;		
		BgFormDiversityData adjustData = null;			
		book.setCalculate(false);
		BgNFSHelper.disableCal(book);
		BgNFSHelper.disableUndo(book);
		for(int n = 0; n < book.getSheetCount(); n++) {
			table = book.getSheet(n);
			rowCount = table.getMaxRowIndex()+1;
			colCount = table.getMaxColIndex()+1;
			
			for(rowIndex=0; rowIndex<rowCount; rowIndex++) {
				for(colIndex=0; colIndex<colCount; colIndex++) {
					Cell cell = table.getCell(rowIndex, colIndex, true);
					
					if (BgNFSHelper.checkHasFormulaOfCell(cell)) {					
						formulaString = BgNFSHelper.getFormulaOfCell(cell);
						
						if (adjustMap.containsKey(formulaString)) {
							adjustData = (BgFormDiversityData) adjustMap.get(formulaString);
							String valueString = null;
							if (adjustData == null || adjustData.getNewValue() == null) {
//								valueString = CtrlSwingUtilities.bigDecimalToPlainString(BgConstants.BIGZERO);
								BgNFSHelper.setValue(cell, null);
								BgNFSHelper.getItemFormula(cell).putExt(BgItemFormulaHelper.BG_BUDGET_VALUE, null);
							} else {
//								valueString = CtrlSwingUtilities.bigDecimalToPlainString(adjustData.getNewValue());
								BgNFSHelper.setValue(cell, adjustData.getNewValue());
								BgNFSHelper.getItemFormula(cell).putExt(BgItemFormulaHelper.BG_BUDGET_VALUE, adjustData.getNewValue().toString());
							}
							if(adjustData.getOldValue() == null)
								adjustData.setOldValue(BgConstants.BIGZERO);
							
							if(adjustData.getNewValue().compareTo(adjustData.getOldValue()) != 0)
								BgNFSHelper.setBackGroup(cell, BgSHelper.EDIT_COLOR);
						}
					}
				}
			}
		}		
		
		return  IOHelper.packBook(book);
	}
	
	/**
	 * 取汇编主表的预算表二进制数据
	 * @param ctx
	 * @param uuid
	 * @param bgColId
	 * @return
	 * @throws Exception
	 */
	private byte[] getMainNewData(Context ctx,BOSUuid uuid, String bgColId) throws Exception {
		BgDisCountFormInfo info = BgDisCountFormFactory.getLocalInstance(ctx).getBgDisCountFormInfo("select id, kdtData where id = '"+bgColId+"'");
		return info == null ? null:info.getKdtData();
	}
	
	/**
	 * 取汇编分录表的预算表二进制数据
	 * @param ctx
	 * @param uuid
	 * @param bgColId
	 * @return
	 * @throws Exception
	 */
	private byte[] getRefNewData(Context ctx,BOSUuid uuid,  String bgColId) throws Exception {
		RefDisCountBgFormInfo info = RefDisCountBgFormFactory.getLocalInstance(ctx).getRefDisCountBgFormInfo("select id , kdtData where bgCollect.id = '"+bgColId+"' and bgForm.id = '"+uuid.toString()+"'");
		return info==null ?null:info.getKdtData();
	}
	
	/**
	 * 
	 * @return
	 */
	public SelectorItemCollection getBgFormInfoToRefCal() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add("startDate");
		sic.add("endDate");
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("data"));
		sic.add(new SelectorItemInfo("bgPeriod.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("bgScheme.*")); 
		sic.add(new SelectorItemInfo("currency.*"));
		sic.add(new SelectorItemInfo("company.*"));
		sic.add(new SelectorItemInfo("auditedStatus"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("CU.name"));
		sic.add(new SelectorItemInfo("sheetParam.*"));
		sic.add(new SelectorItemInfo("postils.*"));
		sic.add(new SelectorItemInfo("postils.report.id"));
		sic.add(new SelectorItemInfo("postils.sheet.id"));
		sic.add(new SelectorItemInfo("postils.creator.id"));
		sic.add(new SelectorItemInfo("postils.creator.name"));
		sic.add(new SelectorItemInfo("sheetParam.*"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.id"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgType"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgUnit.id"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgUnit.type"));
		sic.add(new SelectorItemInfo("sheetParam.orgRela.orgUnit.name"));
		sic.add(new SelectorItemInfo("sheetParam.currency.id"));
		sic.add(new SelectorItemInfo("sheetParam.currency.number"));
		sic.add(new SelectorItemInfo("sheetParam.currency.name"));
		sic.add(new SelectorItemInfo("sheetParam.currency.precision"));
		sic.add(new SelectorItemInfo("sheetParam.postils.*"));
		sic.add(new SelectorItemInfo("sheetParam.postils.report.id"));
		sic.add(new SelectorItemInfo("sheetParam.postils.sheet.id"));
		sic.add(new SelectorItemInfo("sheetParam.postils.creator.id"));
		sic.add(new SelectorItemInfo("sheetParam.postils.creator.name"));
		sic.add(new SelectorItemInfo("sourceType"));
		return sic;
	}

	protected void _updateAdjustData(Context ctx, String id, Map adjustMap)
			throws BOSException, EASBizException {
		if(id == null || adjustMap == null){
			return;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id" ));
		sic.add(new SelectorItemInfo("bgForm.id" ));
		sic.add(new SelectorItemInfo("refBgForms.id" ));
		sic.add(new SelectorItemInfo("refBgForms.bgForm.id" ));
		IObjectPK pk = new ObjectUuidPK(id);
		BgDisCountFormInfo info = getBgDisCountFormInfo(ctx, pk, sic);
		String maiFormSql   = "update T_BG_BgDisCountForm set FAdjustMapData = ? where FId = ?";
		String entryFormSql = "update T_BG_RefDisCountBgForm set FAdjustMapData = ? where FId = ?";
		BgFormInfo bgFormInfo = null;
        Connection conn = null;
        PreparedStatement mainPrst = null;
        PreparedStatement entryPrst = null;
        try {        	
        	conn = getConnection(ctx);
        	//将差异纪录写入临时表数据，同时更新差异纪录的二进制流
        	//处理主表
        	BOSUuid mainBgFormId = info.getBgForm().getId();
        	if(adjustMap.containsKey(mainBgFormId)){
        		HashMap childAdjMap = (HashMap)adjustMap.get(mainBgFormId);

        		if (childAdjMap != null && !childAdjMap.isEmpty()) {
        			bgFormInfo = BgFSHelper.getBgFormInfo(conn, mainBgFormId, false);

        			/*BgFSHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(), 
        					bgFormInfo.getBgScheme().getId(), 
        					bgFormInfo.getBgTemplate().getId(),
        					bgFormInfo.getId(), childAdjMap);*/
        			BgNAdjHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(),
                            bgFormInfo.getBgScheme().getId(), 
                            bgFormInfo.getBgTemplate().getId(),
                            bgFormInfo.getId(), childAdjMap);
        			
        		} else {
        			SQLUtils.execute(conn, "delete from T_BG_BgTmpData where FBgFormId = '" + mainBgFormId.toString() + "'");
        		}
        		mainPrst = conn.prepareStatement(maiFormSql);            
        		mainPrst.setBytes(1, BgHelper.storeHashMapToByte(childAdjMap));
        		mainPrst.setString(2, info.getId().toString());
        		mainPrst.executeUpdate();
        		childAdjMap.clear();
        	}
        	
        	//处理每个分录表
        	RefDisCountBgFormInfo refInfo = null;
        	entryPrst = conn.prepareStatement(entryFormSql);
        	for(int i=0,size = info.getRefBgForms().size();i<size;i++){
        		refInfo = info.getRefBgForms().get(i);
        		BOSUuid entryFormId = refInfo.getBgForm().getId();
            	if(adjustMap.containsKey(entryFormId)){
            		HashMap childAdjMap = (HashMap)adjustMap.get(entryFormId);

            		if (childAdjMap != null && !childAdjMap.isEmpty()) {
            			bgFormInfo = BgFSHelper.getBgFormInfo(conn, entryFormId, false);

            			BgFSHelper.createTmpDataOfFormula(ctx, conn, bgFormInfo.getOrgUnit().getId(), 
            					bgFormInfo.getBgScheme().getId(), 
            					bgFormInfo.getBgTemplate().getId(),
            					bgFormInfo.getId(), childAdjMap);
            		} else {
            			SQLUtils.execute(conn, "delete from T_BG_BgTmpData where FBgFormId = '" + entryFormId.toString() + "'");
            		}
            		entryPrst.setBytes(1, BgHelper.storeHashMapToByte(childAdjMap));
            		entryPrst.setString(2, refInfo.getId().toString());
            		entryPrst.addBatch();
            		childAdjMap.clear();
            	}
        	}
        	entryPrst.executeBatch();
           
		} catch (SQLException e) {
			throw new BOSException(e);
		} catch (IOException e) {
			throw new BOSException(e);
		} finally {
            SQLUtils.cleanup(entryPrst);
            SQLUtils.cleanup(mainPrst, conn);
        }
	}

	protected void _saveKdf(Context ctx, String id, byte[] data)
			throws BOSException, EASBizException {
		if(id == null || data == null )
			return;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_BG_BgDisCountForm set FKdtData = ? where FId = ?");
		
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setBytes(1, data);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			logger.error(ex);
			throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
		} catch(BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	@Override
	protected List _getAllNegativeAdjustCheckDataForView(Context ctx,
			List bgFormIds) throws BOSException, EASBizException {
		if(bgFormIds == null || bgFormIds.isEmpty()){
			return null;
		}
		Map adjustMap = queryBgTmpDataInfoForRollView(ctx, bgFormIds);
		return BgDisCountFormFactory.getLocalInstance(ctx).getAllNegativeAdjustCheckData(adjustMap);
	}
	/**
	 * 根据表ID查询临时表中的数据(适用于滚动视图表)
	 * @param ctx
	 * @param bgFormIds
	 * @return
	 */
	private  Map queryBgTmpDataInfoForRollView(Context ctx, List bgFormIds)throws BOSException{
		Map result = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select bgData.FbgFormId, bgData.FFormula, bgData.FBgValue, bgData.FBizActual, bgData.FAdjustorID, bgData.FAdjustTime, bgData.FAdjustReason")
		   .append(" from t_bg_bgtmpdata bgData where FbgFormId in (");
		BgNSQLHelper.addPlaceHolder(sql, BgNSQLHelper.BATCH_COUNT).append(")");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		String formula = null;
		try {
			conn = EJBFactory.getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());
			Map subMap = null;
			BOSUuid fBgFormId = null;
			Object[] params = bgFormIds.toArray();
			int batchCount = (params.length - 1) / BgNSQLHelper.BATCH_COUNT + 1;
			for (int i = 0; i < batchCount; i++) {
				BgNSQLHelper.setParam4Batch(pstmt, 1, i, BgNSQLHelper.BATCH_COUNT, params);
				res = pstmt.executeQuery();
				while (res.next()) {
					fBgFormId = BOSUuid.read(res.getString("FbgFormId"));
					if (!result.containsKey(fBgFormId)) {
						result.put(fBgFormId, new HashMap());
					}
					subMap = (Map)result.get(fBgFormId);
					formula = res.getString("FFormula");
					BgAdjustFormDiversityData bgDiversityData = new BgAdjustFormDiversityData();
					bgDiversityData.setOldValue(res.getBigDecimal("FBizActual"));
					bgDiversityData.setNewValue(res.getBigDecimal("FBgValue"));
					bgDiversityData.setAdjustor(res.getString("FAdjustorID"));
					bgDiversityData.setAdjustTime(res.getTimestamp("FAdjustTime"));
					bgDiversityData.setAdjustReson(res.getString("FAdjustReason"));
					bgDiversityData.setFormula(formula);
					subMap.put(formula, bgDiversityData);
				}					
				SQLUtils.cleanup(res);
			}
		} catch (SQLException e) {
			logger.error("",e);
			throw new BOSException(e);
		} finally {
			SQLUtils.cleanup(res, pstmt, conn);
		}
		return result;
	}
	
}



