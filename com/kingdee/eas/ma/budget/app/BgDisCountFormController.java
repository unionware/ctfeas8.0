package com.kingdee.eas.ma.budget.app;

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
import com.kingdee.eas.framework.app.ObjectBaseController;
import com.kingdee.eas.ma.budget.BgDisCountFormCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.ma.budget.BgFormInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BgDisCountFormController extends ObjectBaseController
{
    public BgDisCountFormInfo getBgDisCountFormInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BgDisCountFormInfo getBgDisCountFormInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BgDisCountFormInfo getBgDisCountFormInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public Map getBgData(Context ctx, BOSUuid orgUnitId, BOSUuid bgSchemeId, Set formulaSet) throws BOSException, EASBizException, RemoteException;
    public Map getTopBgData(Context ctx, BOSUuid topBgId, String subBgId) throws BOSException, EASBizException, RemoteException;
    public IRowSet getSubBgData(Context ctx, BOSUuid topBgId, String subBgIds, String periodName, String itemKey, String fomula) throws BOSException, RemoteException;
    public boolean hasBgDataFomular(Context ctx, BOSUuid bgFormId) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid bgCollectFormId) throws BOSException, EASBizException, RemoteException;
    public void auditStatus(Context ctx, BOSUuid bgCollectFormId) throws BOSException, EASBizException, RemoteException;
    public void auditCollectState(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public IRowSet getAdjustMapByBgForm(Context ctx, BOSUuid bgFormId) throws BOSException, RemoteException;
    public void auditWF(Context ctx, BOSUuid bgCollectFormId) throws BOSException, RemoteException;
    public byte[] getKdf(Context ctx, BOSUuid bgCollectId) throws BOSException, EASBizException, RemoteException;
    public byte[] getBgFormKdf(Context ctx, BOSUuid bgFormId) throws BOSException, EASBizException, RemoteException;
    public boolean addBgFormToChildren(Context ctx, String orgId, BgFormInfo bgFormInfo) throws BOSException, EASBizException, RemoteException;
    public Object[] updateBgFormData(Context ctx, String bgFormID, BgFormInfo bgform, ArrayList tableData) throws BOSException, EASBizException, RemoteException;
    public void updateDataForForm(Context ctx, String bgCollectId, Map dataMap) throws BOSException, EASBizException, RemoteException;
    public Object[] updateDataToBgTmpData(Context ctx, String formId, Object data) throws BOSException, EASBizException, RemoteException;
    public Map getOldData(Context ctx, Map bgFormId) throws BOSException, EASBizException, RemoteException;
    public Object updateData(Context ctx, String colId, Map addNewData, Map delOldData, boolean idCol, byte[] newData) throws BOSException, EASBizException, RemoteException;
    public void addNewData(Context ctx, Map bgCollIds) throws BOSException, EASBizException, RemoteException;
    public List getAllNegativeAdjustCheckData(Context ctx, Map adjustMap) throws BOSException, EASBizException, RemoteException;
    public Object[] calculate(Context ctx, String bgFormId, String mainFormId, String bgCollectId, Map adjustMap) throws BOSException, EASBizException, RemoteException;
    public void updateAdjustData(Context ctx, String id, Map adjustMap) throws BOSException, EASBizException, RemoteException;
    public void saveKdf(Context ctx, String id, byte[] data) throws BOSException, EASBizException, RemoteException;
    public List getAllNegativeAdjustCheckDataForView(Context ctx, Map adjustMap, BgFormInfo bgFormInfo) throws BOSException, EASBizException, RemoteException;
    public List getAllNegativeAdjustCheckDataForView(Context ctx, List _bgFormIds) throws BOSException, EASBizException, RemoteException;
}