package com.kingdee.eas.ma.budget.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Row;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.newrpt.formula.ReportCalculateContext;
import com.kingdee.eas.fi.newrpt.formula.define.FormulaDefinition;
import com.kingdee.eas.fi.newrpt.report.VariablesHelper;
import com.kingdee.eas.fi.rpt.ReportCalculateErrorProvider;
import com.kingdee.eas.fi.rpt.util.ReportVariables;
import com.kingdee.eas.fi.rpt.util.VariableEntry;
import com.kingdee.eas.ma.budget.BgElementInfo;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgSchemeInfo;
import com.kingdee.eas.ma.budget.BookHelper;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.util.NumericExceptionSubItem;

import java.math.BigDecimal;
import java.util.Map;

public class BgAvlBalCalFacadeControllerBean extends AbstractBgAvlBalCalFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.ma.budget.app.BgAvlBalCalFacadeControllerBean");
    
    //计算预算可用余额
    @Override
    protected BigDecimal _calAvlBalCalAmt(Context ctx, Map param)
    		throws BOSException, EASBizException {
    	
    	BigDecimal userableAmt =BigDecimal.ZERO;
    	
    	FullOrgUnitInfo orgUnit = (FullOrgUnitInfo)param.get("orgUnit"); //组织单元
    	BgSchemeInfo bgScheme = (BgSchemeInfo)param.get("bgScheme"); //预算方案
    	BgElementInfo bgElement= (BgElementInfo)param.get("bgElement"); //预算要素
    	String bgItemNumbers = (String)param.get("bgItemNumbers"); //预算项目
    	CurrencyInfo currency = (CurrencyInfo)param.get("currency"); //币别
    	BgPeriodInfo bgPeriod = (BgPeriodInfo)param.get("bgPeriod"); //预算期间
    	 
    	StringBuffer sb = new StringBuffer();
    	String formula = null;
    	
    	sb.append("BgAvlBal(").append('"').append(orgUnit.getNumber()).append("\",")
    	                      .append('"').append(bgScheme.getNumber()).append("\",")
						      .append('"').append(bgElement.getNumber()).append("\",")
						      .append('"').append(bgItemNumbers).append("\",")
						      .append('"').append(currency.getNumber()).append("\",")
						      .append('"').append(bgPeriod.getNumber()).append("\")");
    	
    	formula = sb.toString();
    	
    	CompanyOrgUnitInfo company =getRelCompany(ctx,orgUnit);
    	
   	    if(company==null){
   	    	throw new EASBizException(new NumericExceptionSubItem("ffffffff","当前责任中心没有对应的财务组织!"));
   	    }
   	    
   	    BgFormInfo  form = getASimpleBgForm(orgUnit,company, bgPeriod, bgScheme); //编制表
   	    
   	    ReportCalculateContext context = this.getReportCalculateContextByCurrScheme(ctx, orgUnit,company,bgPeriod, bgScheme);
   	    
   	    try {
   	    	userableAmt = getCalculateResult(ctx,formula,context,form);
		} catch (Exception e) {
			logger.error("计算数据出错"); 
			e.printStackTrace();
			throw new BOSException(e);
		}
   	    
    	return userableAmt;
    }
    
  //计算预算实际发生数
    @Override
	protected BigDecimal _calActualAmt(Context ctx, Map param)
			throws BOSException, EASBizException {
    	BigDecimal actualAmt =BigDecimal.ZERO;
    	
    	FullOrgUnitInfo orgUnit = (FullOrgUnitInfo)param.get("orgUnit"); //组织单元
    	BgSchemeInfo bgScheme = (BgSchemeInfo)param.get("bgScheme"); //预算方案
    	BgElementInfo bgElement= (BgElementInfo)param.get("bgElement"); //预算元素
    	String bgItemNumbers = (String)param.get("bgItemNumbers"); //预算项目
    	CurrencyInfo currency = (CurrencyInfo)param.get("currency"); //币别
    	BgPeriodInfo bgPeriod = (BgPeriodInfo)param.get("bgPeriod"); //预算期间
    	
    	StringBuffer sb = new StringBuffer();
    	String formula = null;
    	
    	sb.append("BgActualS(").append('"').append(orgUnit.getNumber()).append("\",")
        .append('"').append(bgScheme.getNumber()).append("\",")
	      .append('"').append(bgElement.getNumber()).append("\",")
	      .append('"').append(bgItemNumbers).append("\",")
	      .append('"').append(currency.getNumber()).append("\",")
	      .append('"').append(bgPeriod.getNumber()).append("\")");

		formula = sb.toString();
		
		CompanyOrgUnitInfo company =getRelCompany(ctx,orgUnit);
		
		if(company==null){
		throw new EASBizException(new NumericExceptionSubItem("ffffffff","当前责任中心没有对应的财务组织!"));
		}
		
		BgFormInfo  form = getASimpleBgForm(orgUnit,company, bgPeriod, bgScheme); //编制表
		
		ReportCalculateContext context = this.getReportCalculateContextByCurrScheme(ctx, orgUnit,company,bgPeriod, bgScheme);
		
		try {
			actualAmt = getCalculateResult(ctx,formula,context,form);
		} catch (Exception e) {
		logger.error("计算数据出错"); 
		e.printStackTrace();
		throw new BOSException(e);
		}
		return actualAmt;
	}
    
    /**
     * 描述：计算公式的取值 
     * 如果列表没有
     * @param ctx
     * @param listexp  需计算的公式
     * @throws Exception 
     * @throws Exception
     */
    private BigDecimal getCalculateResult(Context ctx, String formula,
			ReportCalculateContext context, BgFormInfo form) throws Exception {
    	//校验公式
		Book calcbook=prepareCalculate(ctx, formula, context,form);
		calcbook.calc();
		
		Cell cell=null;
		Object value = null;
		BigDecimal temp = BigDecimal.ZERO;
		
		Row row =calcbook.getSheet("Sheet1").getRow(0, true);
		cell  = row.getCell(0, true);
		value = BgNFSHelper.getValue(cell);
		
		try{
			temp = (BigDecimal)value;
		}catch(Exception e){
			throw new EASBizException(new NumericExceptionSubItem("","计算公式错误 "));
		}
		return temp;
	}

    /**
	 *  * 描述：准备计算,设置计算用的公式定义,需计算的表页等
	 * @param ctx
	 * @param reportInfo
	 * @param kdf
	 * @param index
	 * @param listexp   or to Map 
	 * @return
     * @throws Exception 
	 * @throws Exception
	 */
	private Book prepareCalculate(Context ctx, String formula,
			ReportCalculateContext context, BgFormInfo form) throws Exception {
		Book book=BookHelper.getNewBook();
		if(book == null){
			book = Book.Manager.getNewBook(null, 0);
		}
		//调用新接口注册取数公式
		FormulaDefinition.registerFormulaToBook(book, context);
		book.getUndoManager().enable(false);
		book.setCalculate(false); 	

		Sheet sheet=book.getSheet("Sheet1");
		Row row = sheet.getRow(0, true);
		Cell cell  = row.getCell(0, true);
		cell.setFormula(formula);
		BgNFSHelper.checkExpression(cell, form);
		book.getUndoManager().enable(false);
		return book;
	}


	private CompanyOrgUnitInfo getRelCompany(Context ctx,FullOrgUnitInfo orgUnit) throws BOSException, EASBizException{
    	orgUnit = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(orgUnit.getId()));
    	
    	CompanyOrgUnitInfo company =null;
    	if(!orgUnit.isIsCompanyOrgUnit()){
    		
    			IOrgUnitRelation iUnitRel = OrgUnitRelationFactory.getLocalInstance(ctx);
    			 OrgUnitCollection orgCol = iUnitRel.getToUnit(orgUnit.getId().toString(),OrgType.COSTCENTER_VALUE, OrgType.COMPANY_VALUE);
       	      if (orgCol.get(0) != null) {
       	    	  company = ((CompanyOrgUnitInfo)orgCol.get(0));
       	      }
    	}else{
    		company  =CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(orgUnit.getId()));
    	}
		return company;
    }
    
    /**
	 * 创建一个预算表单
	 */
	private BgFormInfo getASimpleBgForm(FullOrgUnitInfo orgUnit,CompanyOrgUnitInfo  company,BgPeriodInfo period,BgSchemeInfo bgSchemeInfo){
		BgFormInfo formInfo = new BgFormInfo();
		formInfo.setCompany(company);
		formInfo.setId(BOSUuid.create("FF66566A"));
		formInfo.setOrgUnit(orgUnit);
		formInfo.setBgScheme(bgSchemeInfo);
		formInfo.setBgPeriod(period);
		
		return formInfo;
	}
	
	/**
	 * 获得一简单的报表计算容器
	 * @param ctx
	 * @param info    考核控制方案
	 * @param bgSchemeInfo   预算方案
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private ReportCalculateContext getReportCalculateContextByCurrScheme(Context ctx,FullOrgUnitInfo orgUnit,CompanyOrgUnitInfo company,BgPeriodInfo period,BgSchemeInfo bgSchemeInfo ) throws EASBizException, BOSException{
		//计算的context
		ReportCalculateContext context = null;
		BgRptReportPropertyAdapter adapter = null;
		//报表变量类
		ReportVariables variables = new ReportVariables();
		//计算所使用的错误处理类
		ReportCalculateErrorProvider errorProvider = new ReportCalculateErrorProvider();
		variables.add( VariablesHelper.createReservedEntry(VariablesHelper.INCLUDE_POSTED, "", "Sheet1", "Y", VariableEntry.VARIABLE_TYPE_BOOL));
		BgFormInfo formInfo = getASimpleBgForm(orgUnit,company,period,bgSchemeInfo);   //赋值虚拟的表报
		formInfo.setBgPeriod(period);  //设置期间为当前月 //用以取自动填充取数公式中的N
		adapter = new BgRptReportPropertyAdapter(formInfo);	
		context = new ReportCalculateContext(ctx,adapter,variables,errorProvider,null);	
		//context.getReportVariables().add(new VariableEntry());
		return context;
	}

}