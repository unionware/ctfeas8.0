package com.kingdee.eas.ma.bg;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IBudgetTemplateRelation extends IDataBase
{
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BudgetTemplateRelationInfo getBudgetTemplateRelationInfo(String oql) throws BOSException, EASBizException;
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection() throws BOSException;
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(EntityViewInfo view) throws BOSException;
    public BudgetTemplateRelationCollection getBudgetTemplateRelationCollection(String oql) throws BOSException;
}