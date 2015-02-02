package com.kingdee.eas.ma.budget.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.budget.BgDisCountFormFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.eas.ma.budget.BgDecException;
import com.kingdee.eas.ma.budget.BgException;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.IBgDisCountForm;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.nbudget.BgNSHelper;

public class BgDisCountFacadeControllerBean extends AbstractBgDisCountFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.ma.budget.app.BgDisCountFacadeControllerBean");
    protected BOSUuid _getCollectFormId(Context ctx, BOSUuid bgFormId)throws BOSException, EASBizException
    {
        return null;
    }
    protected Set _getCollectFormInfo(Context ctx, BOSUuid bgFormId, Set orgUnitIds, Set state, boolean isFund)throws BOSException, EASBizException
    {
        return null;
    }
    protected void _checkFormulaIsEctype(Context ctx, BOSUuid bgFormId, Map formulaMap)throws BOSException, EASBizException
    {
    }
    protected Map _loadAllRelateInfo(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
      Map resultMap = new HashMap();
      if (id == null)
        return resultMap;
      IBgDisCountForm iDec = BgDisCountFormFactory.getLocalInstance(ctx);
      BgDisCountFormInfo info = iDec.getBgDisCountFormInfo(new ObjectUuidPK(id), getSelectors1());
      IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
      ICostCenterOrgUnit iccOrgUnit = CostCenterOrgUnitFactory.getLocalInstance(ctx);
      if (info == null)
        throw new BgException(BgException.NOOBJECT);
      IObjectPK pk = new ObjectUuidPK(info.getBgForm().getId());
      BgFormInfo bgFormInfo = null;
      CostCenterOrgUnitInfo ccOrgInfo = null;
      Set orgSet = new HashSet();
      if (iBgForm.exists(pk))
      {
        bgFormInfo = iBgForm.getBgFormInfo(pk, getBgFormInfo());
        resultMap.put("bgFormInfo", bgFormInfo);
        
        ccOrgInfo = iccOrgUnit.getCostCenterOrgUnitInfo(new ObjectUuidPK(bgFormInfo.getOrgUnit().getId()));
        resultMap.put("defOrgUnitInfo", ccOrgInfo);
        orgSet.add(ccOrgInfo.getId().toString());
      } else {
        throw new BgException(BgException.NOOBJECT);
      }
      
  
      CostCenterOrgUnitCollection refOrgUnitCol = new CostCenterOrgUnitCollection();
      Set orgId = new HashSet();
      if ((info.getRefBgForms() != null) && (!info.getRefBgForms().isEmpty())) {
        RefDisCountBgFormInfo refBgDecFormInfo = null;
        RefDisCountBgFormCollection refBgDecFormCol = info.getRefBgForms();
        int i = 0; for (int n = refBgDecFormCol.size(); i < n; i++) {
          refBgDecFormInfo = refBgDecFormCol.get(i);
          if ((refBgDecFormInfo.getBgForm() == null) || (refBgDecFormInfo.getBgForm().getOrgUnit() == null)) {
            throw new BgDecException(BgDecException.NOTHAVEBGFORM);
          }
          orgId.add(refBgDecFormInfo.getBgForm().getOrgUnit().getId().toString());
          orgSet.add(refBgDecFormInfo.getBgForm().getOrgUnit().getId().toString());
        }
      }
      EntityViewInfo viewInfo = new EntityViewInfo();
      FilterInfo filter = new FilterInfo();
      FilterItemCollection item = filter.getFilterItems();
      item.add(new FilterItemInfo("id", orgId, CompareType.INCLUDE));
      viewInfo.setFilter(filter);
      viewInfo.getSorter().add(new SorterItemInfo("longNumber"));
      try {
        refOrgUnitCol = iccOrgUnit.getCostCenterOrgUnitCollection(viewInfo);
      } catch (Exception e) {
        refOrgUnitCol = new CostCenterOrgUnitCollection();
        logger.error("budget error:", e);
      }
      resultMap.put("refOrgUnitCol", refOrgUnitCol);
      
  
      EntityViewInfo view = new EntityViewInfo();
      filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("id", orgSet, CompareType.INCLUDE));
      view.setFilter(filter);
      
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add("id");
      sic.add("name");
      sic.add("number");
      sic.add("longnumber");
      view.getSelector().addObjectCollection(sic);
      
      SorterItemInfo sore = new SorterItemInfo("longNumber");
      sore.setSortType(SortType.DESCEND);
      view.getSorter().add(sore);
      
      CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
      CostCenterOrgUnitCollection costCenterOrgUnitColl = null;
      Map orgSheetCheckMap = new HashMap();
      try {
        costCenterOrgUnitColl = iccOrgUnit.getCostCenterOrgUnitCollection(view);
        int i = 0; for (int size = costCenterOrgUnitColl.size(); i < size; i++) {
          costCenterOrgUnitInfo = costCenterOrgUnitColl.get(i);
          if (costCenterOrgUnitInfo != null)
            orgSheetCheckMap.put(costCenterOrgUnitInfo.getId(), costCenterOrgUnitInfo);
        }
      } catch (Exception e) {
        costCenterOrgUnitColl = new CostCenterOrgUnitCollection();
        logger.error("budget error:", e);
      }
      
      BgNSHelper.objClear(costCenterOrgUnitColl);
      resultMap.put("orgSheetCheckMap", orgSheetCheckMap);
      
  
      Map currencyMap = new HashMap();
      CurrencyInfo currencyInfo = null;
      CurrencyCollection currencyCol = null;
      ICurrency iCurrency = CurrencyFactory.getLocalInstance(ctx);
      if (iCurrency != null) {
        currencyCol = iCurrency.getCurrencyCollection();
        for (Iterator iter = currencyCol.iterator(); iter.hasNext();) {
          currencyInfo = (CurrencyInfo)iter.next();
          currencyMap.put(currencyInfo.getNumber(), currencyInfo);
        }
        BgNSHelper.objClear(currencyCol);
      }
      resultMap.put("currencyMap", currencyMap);
      return resultMap;
    }
    
    public SelectorItemCollection getBgFormInfo() { SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("id"));
    sic.add(new SelectorItemInfo("name"));
    sic.add(new SelectorItemInfo("number"));
    sic.add(new SelectorItemInfo("data"));
    sic.add(new SelectorItemInfo("orgUnit.*"));
    sic.add(new SelectorItemInfo("bgScheme.*"));
    sic.add(new SelectorItemInfo("bgTemplate.id"));
    sic.add(new SelectorItemInfo("bgPeriod.*"));
    sic.add(new SelectorItemInfo("currency.*"));
    sic.add(new SelectorItemInfo("sheetParam.*"));
    sic.add(new SelectorItemInfo("postils.*"));
    sic.add(new SelectorItemInfo("postils.report.id"));
    sic.add(new SelectorItemInfo("postils.sheet.id"));
    sic.add(new SelectorItemInfo("postils.creator.id"));
    sic.add(new SelectorItemInfo("postils.creator.name"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.*"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.id"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.name"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.bgElement.number"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.id"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.name"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.number"));
    sic.add(new SelectorItemInfo("bgMeasureUnit.measureUnit.coefficient"));
    sic.add(new SelectorItemInfo("measureUnit.*"));
    return sic;
  }
    
  public SelectorItemCollection getSelectors1() {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("id"));
    sic.add(new SelectorItemInfo("name"));
    sic.add(new SelectorItemInfo("number"));
    sic.add(new SelectorItemInfo("orgList"));
    sic.add(new SelectorItemInfo("orgUnit.id"));
    sic.add(new SelectorItemInfo("bgform.id"));
    sic.add(new SelectorItemInfo("bgform.bgScheme.name"));
    sic.add(new SelectorItemInfo("refBgForms.*"));
    sic.add(new SelectorItemInfo("refBgForms.bgForm.id"));
    sic.add(new SelectorItemInfo("refBgForms.bgForm.orgUnit.id"));
    sic.add(new SelectorItemInfo("refBgForms.bgForm.bgScheme.id"));
    sic.add(new SelectorItemInfo("othercont"));
    sic.add(new SelectorItemInfo("state"));
    return sic;
  }
}