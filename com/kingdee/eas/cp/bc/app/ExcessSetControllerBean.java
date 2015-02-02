package com.kingdee.eas.cp.bc.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import java.math.BigDecimal;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.cp.bc.ExcessSetFactory;
import com.kingdee.eas.cp.bc.ExcessSetInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.cp.bc.ExcessSetCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class ExcessSetControllerBean extends AbstractExcessSetControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.ExcessSetControllerBean");
    
    @Override
    protected BigDecimal _getAboveQuota(Context ctx, String projectid,
    		String expenseTypeid, String costcenterid,
    		IObjectCollection excessSetCol) throws BOSException,
    		EASBizException {
    	ExcessSetCollection excesssetInfos = (ExcessSetCollection) excessSetCol;
    	BigDecimal AboveRate = new BigDecimal("0.00");
		String setprojectid = null;
		String setexpenseTypeid = null;
		String setcostcenterid = null;
		boolean flag = false;

		if (projectid!=null) {
			if (excesssetInfos != null && excesssetInfos.size() > 0) {
				for (int i = 0; i < excesssetInfos.size(); i++) {
					flag = false;
					if(excesssetInfos.get(i).getProject()!=null){
						setprojectid = excesssetInfos.get(i).getProject().getId()
								.toString();
						setexpenseTypeid = excesssetInfos.get(i).getExpenseType()
								.getId().toString();

						if (projectid.equalsIgnoreCase(setprojectid) && expenseTypeid.equalsIgnoreCase(setexpenseTypeid)) {
							for (int j = 0; j < excesssetInfos.get(i).getEntry().size(); j++) {
								setcostcenterid = excesssetInfos.get(i).getEntry().get(
										j).getCostCenter().getId().toString();
								if (costcenterid.equalsIgnoreCase(setcostcenterid)) {
									AboveRate = excesssetInfos.get(i).getEntry().get(j).getRate();
									flag = true;
									return AboveRate;
								}
							}
							if(!flag){
								AboveRate = excesssetInfos.get(i).getRate();
								return AboveRate;
							}
						}
					}
				}
			}
		}
		if (excesssetInfos != null && excesssetInfos.size() > 0) {
			for (int i = 0; i < excesssetInfos.size(); i++) {
				flag = false;
				setexpenseTypeid = excesssetInfos.get(i).getExpenseType().getId().toString();
				if (excesssetInfos.get(i).getProject()==null && expenseTypeid.equalsIgnoreCase(setexpenseTypeid)) {
					for (int j = 0; j < excesssetInfos.get(i).getEntry().size(); j++) {
						setcostcenterid = excesssetInfos.get(i).getEntry().get(j).getCostCenter().getId().toString();
						if (costcenterid.equalsIgnoreCase(setcostcenterid)) {
							AboveRate = excesssetInfos.get(i).getEntry().get(j).getRate();
							flag = true;
							break;
						}
					}
					if(!flag){
						AboveRate = excesssetInfos.get(i).getRate();
						break;
					}
				}
			}
		}
		return AboveRate;
    }
    
    @Override
    protected IObjectCollection _getExcessSetInfos(Context ctx, long year)
    		throws BOSException, EASBizException {
    	return getExcessSets(ctx, year);
    }

	/**
	 * 判断当前项目是否存在于超额比例设置中
	 * 
	 * @param excesssetInfos
	 * @param projectid
	 * @return
	 */
	private boolean isExistProject(ExcessSetCollection excesssetInfos,
			String projectid) {
		if (excesssetInfos != null && excesssetInfos.size() > 0) {
			for (int i = 0; i < excesssetInfos.size(); i++) {
				if(excesssetInfos.get(i).getProject()!=null){
					String setProjectid = excesssetInfos.get(i).getProject()
							.getId().toString();
					if (setProjectid.equalsIgnoreCase(projectid)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前报销对应的年份的超额设置
	 * 
	 * @param ctx
	 * @param year
	 * @return ExcessSetCollection
	 * @throws BOSException
	 */
	private ExcessSetCollection getExcessSets(Context ctx, long year)
			throws BOSException {
		// 
		EntityViewInfo viewInfo = new EntityViewInfo();
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add(new SelectorItemInfo("id"));
        selector.add(new SelectorItemInfo("*"));
        selector.add(new SelectorItemInfo("entry.id"));
        selector.add(new SelectorItemInfo("entry.*"));
        selector.add(new SelectorItemInfo("entry.costCenter.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("year", year, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnable", 1, CompareType.EQUALS));
		filter.setMaskString("#0 AND #1");
		viewInfo.setFilter(filter);
		viewInfo.setSelector(selector);
		ExcessSetCollection excesssetInfos = ExcessSetFactory.getLocalInstance(
				ctx).getExcessSetCollection(viewInfo);

		return excesssetInfos;
	}
}