package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.IObjectBase;

public interface IBgDisCountForm extends IObjectBase
{
    public BgDisCountFormInfo getBgDisCountFormInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BgDisCountFormInfo getBgDisCountFormInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BgDisCountFormInfo getBgDisCountFormInfo(String oql) throws BOSException, EASBizException;
    public Map getBgData(BOSUuid orgUnitId, BOSUuid bgSchemeId, Set formulaSet) throws BOSException, EASBizException;
    public Map getTopBgData(BOSUuid topBgId, String subBgId) throws BOSException, EASBizException;
    public IRowSet getSubBgData(BOSUuid topBgId, String subBgIds, String periodName, String itemKey, String fomula) throws BOSException;
    public boolean hasBgDataFomular(BOSUuid bgFormId) throws BOSException;
    public void audit(BOSUuid bgCollectFormId) throws BOSException, EASBizException;
    public void auditStatus(BOSUuid bgCollectFormId) throws BOSException, EASBizException;
    public void auditCollectState(BOSUuid id) throws BOSException, EASBizException;
    public IRowSet getAdjustMapByBgForm(BOSUuid bgFormId) throws BOSException;
    public void auditWF(BOSUuid bgCollectFormId) throws BOSException;
    public byte[] getKdf(BOSUuid bgCollectId) throws BOSException, EASBizException;
    public byte[] getBgFormKdf(BOSUuid bgFormId) throws BOSException, EASBizException;
    public boolean addBgFormToChildren(String orgId, BgFormInfo bgFormInfo) throws BOSException, EASBizException;
    public Object[] updateBgFormData(String bgFormID, BgFormInfo bgform, ArrayList tableData) throws BOSException, EASBizException;
    public void updateDataForForm(String bgCollectId, Map dataMap) throws BOSException, EASBizException;
    public Object[] updateDataToBgTmpData(String formId, Object data) throws BOSException, EASBizException;
    public Map getOldData(Map bgFormId) throws BOSException, EASBizException;
    public Object updateData(String colId, Map addNewData, Map delOldData, boolean idCol, byte[] newData) throws BOSException, EASBizException;
    public void addNewData(Map bgCollIds) throws BOSException, EASBizException;
    public List getAllNegativeAdjustCheckData(Map adjustMap) throws BOSException, EASBizException;
    public Object[] calculate(String bgFormId, String mainFormId, String bgCollectId, Map adjustMap) throws BOSException, EASBizException;
    public void updateAdjustData(String id, Map adjustMap) throws BOSException, EASBizException;
    public void saveKdf(String id, byte[] data) throws BOSException, EASBizException;
    public List getAllNegativeAdjustCheckDataForView(Map adjustMap, BgFormInfo bgFormInfo) throws BOSException, EASBizException;
    public List getAllNegativeAdjustCheckDataForView(List _bgFormIds) throws BOSException, EASBizException;
}