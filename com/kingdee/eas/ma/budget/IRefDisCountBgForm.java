package com.kingdee.eas.ma.budget;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public interface IRefDisCountBgForm extends ICoreBase
{
    public RefDisCountBgFormCollection loadRefBgFormCollectionByBgForm(BgFormInfo bgFormInfo, List ouIdList) throws BOSException, EASBizException;
    public List getCommentByBgForm(IObjectPK bgFormPK, boolean isCollectForm) throws BOSException, EASBizException;
    public void addCommentByBgForm(IObjectPK bgFormPK, String comment) throws BOSException, EASBizException;
    public byte[] getKdf(BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId) throws BOSException, EASBizException;
    public void submitKdf(BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId, RefDisCountBgFormInfo model) throws BOSException, EASBizException;
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RefDisCountBgFormInfo getRefDisCountBgFormInfo(String oql) throws BOSException, EASBizException;
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection() throws BOSException;
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(EntityViewInfo view) throws BOSException;
    public RefDisCountBgFormCollection getRefDisCountBgFormCollection(String oql) throws BOSException;
}