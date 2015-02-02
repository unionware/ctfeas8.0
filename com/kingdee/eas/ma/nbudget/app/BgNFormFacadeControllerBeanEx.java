/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.kingdee.eas.ma.nbudget.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.IParamItem;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.base.BgException;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationCollection;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationFactory;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationTemplateEntryCollection;
import com.kingdee.eas.ma.bg.BudgetTemplateRelationTemplateEntryFactory;
import com.kingdee.eas.ma.bg.IBudgetTemplateRelation;
import com.kingdee.eas.ma.bg.IBudgetTemplateRelationTemplateEntry;
import com.kingdee.eas.ma.budget.BgFormCollection;
import com.kingdee.eas.ma.budget.BgFormDataSourceEnum;
import com.kingdee.eas.ma.budget.BgFormException;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgFormStateEnum;
import com.kingdee.eas.ma.budget.BgTemplateCollection;
import com.kingdee.eas.ma.budget.BgTemplateFactory;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.IBgTemplate;
import com.kingdee.eas.ma.nbudget.BgCourseCommentFactory;
import com.kingdee.eas.ma.nbudget.BgCourseCommentInfo;
import com.kingdee.eas.ma.nbudget.BgCourseOperateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class BgNFormFacadeControllerBeanEx extends BgNFormFacadeControllerBean
{

    private static Logger logger = Logger.getLogger("com.kingdee.eas.ma.nbudget.app.BgNFormFacadeControllerBeanEx");

    EntityViewInfo view = null;
    FilterInfo filter = null;
    
    public BgNFormFacadeControllerBeanEx()
    {
    }
    
    protected List _getAccountBizCostCenterNumber(Context ctx, String sql)
        throws BOSException, EASBizException
    {
        return super._getAccountBizCostCenterNumber(ctx, sql);
    }

    /**
     * ���
     */
    @Override
    protected Map _back(Context ctx, List bgFormIds, String userId)
    		throws BOSException, EASBizException {
    	
    	Map _reMap = super._back(ctx, bgFormIds, userId);
    	if(_reMap != null && !_reMap.isEmpty())//֮ǰ�����Ƿ�ɹ�
        {
    		Boolean _course = (Boolean)_reMap.get("back_result");
            if(_course != null && _course.booleanValue()){
            	Timestamp _timestamp = new Timestamp(System.currentTimeMillis());
		    	int acflag = BgCourseOperateEnum.BACK_VALUE;
		    	updateTempRelationForm(ctx,bgFormIds,userId,_timestamp,acflag);
            }
        }
        return _reMap;
    
	}
    
    /**
     * ���Ͽ�
     */
    @Override
    protected List _antiApprove(Context ctx, List bgFormIds, String userId)
    		throws BOSException, EASBizException {
    	List _List = super._antiApprove(ctx, bgFormIds, userId);
    	if(_List != null && !_List.isEmpty())
        {
    		Boolean _course = (Boolean)_List.get(0);
            if(_course != null && _course.booleanValue()){
            	Timestamp _timestamp = new Timestamp(System.currentTimeMillis());
		    	int acflag = BgCourseOperateEnum.ANTICERTIFICATE_VALUE;
		    	updateTempRelationForm(ctx,bgFormIds,userId,_timestamp,acflag);
            }
        }
    	return _List;
    }
    
    /**
     * ��ȷ��
     */
    @Override
    protected List _batchUnComfirm(Context ctx, List bgFormIds)
    		throws BOSException, EASBizException {
    	List _reList = super._batchUnComfirm(ctx, bgFormIds);
    	List successed = (List)_reList.get(7);
    	if(successed != null && successed.size() > 0){
    		Timestamp _timestamp = new Timestamp(System.currentTimeMillis());
	    	String userId = null;
	    	int acflag = BgCourseOperateEnum.UNCONFIRM_VALUE;
	    	updateTempRelationForm(ctx,bgFormIds,userId,_timestamp,acflag);
    	}
    	return _reList;
    }
    
    /**
     * ����������
     */
    @Override
    protected List _batchAntiAudit(Context ctx, List bgFormIds)
    		throws BOSException, EASBizException {
    	List _list = super._batchAntiAudit(ctx, bgFormIds);
    	List successed = (List)_list.get(7);
    	if(successed != null && successed.size() > 0){
    		Timestamp _timestamp = new Timestamp(System.currentTimeMillis());
	    	String userId = null;
	    	int acflag = 0;
	    	Object object = ctx.get("UN_CONFIRM");
	    	if(object==null){
		    	updateTempRelationForm(ctx,bgFormIds,userId,_timestamp,acflag);
		    }
	    	
	    	
    	}
    	return _list;
    }
    
    @Override
    protected List _batchAntiAuditForRoll(Context ctx, List bgFormIds)
    		throws BOSException, EASBizException {
    	List _list = super._batchAntiAuditForRoll(ctx, bgFormIds);
    	List successed = (List)_list.get(5);
    	if(successed != null && successed.size() > 0){
    		Timestamp _timestamp = new Timestamp(System.currentTimeMillis());
	    	String userId = null;
	    	int acflag = 0;
	    	updateTempRelationForm(ctx,bgFormIds,userId,_timestamp,acflag);
    	}
    	return _list;
    }
    
    private void updateTempRelationForm(Context ctx, List bgFormIds,
			String userId, Timestamp _timestamp, int acflag) throws BOSException, EASBizException {
    	Connection conn = null;
		PreparedStatement pstmt = null;
		
		String str = null;
		int updateState = 0;
		String _bgFormId = null,_userId;;
		String strRelation = null;
    	String strRelEntrys = null;
    	String strSubTemples = null;
		String bgTemplateRootID = null;
    	String periodID = null;
        String orgUnitID = null;
        String bgSchemeID = null;
        BgFormInfo bgFormInfo = null; 
        BgFormCollection bgFormCol = null;
		
        if (StringUtils.isEmpty(userId)) {
			_userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
		} else {
			_userId = userId;
		}
        
        if(acflag == BgCourseOperateEnum.BACK_VALUE){
        	updateState= BgFormStateEnum.BACK_VALUE;
        }else if(acflag == BgCourseOperateEnum.ANTICERTIFICATE_VALUE){
        	updateState = BgFormStateEnum.REPORT_VALUE; 
        }else if(acflag == BgCourseOperateEnum.UNCONFIRM_VALUE){
        	IParamItem iParam = ParamItemFactory.getLocalInstance(ctx);
        	FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("keyID.number","BG053"));
        	filter.getFilterItems().add(new FilterItemInfo("Value","true"));
        	filter.setMaskString("#0 and #1");
        	if(iParam.exists(filter)){
        		updateState = BgFormStateEnum.CERTIFICATE_VALUE;
        	}else{
        		updateState = BgFormStateEnum.EDITED_VALUE;
        	}
        }
        else{
        	updateState = BgFormStateEnum.EDITED_VALUE;
        }
        	
        
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement("update T_BG_BgForm set FState = ?, FLastUpdateUserId = ?, FLastUpdateTime = ? where FID = ? ");
		    
			IBgForm bgForm = BgFormFactory.getLocalInstance(ctx);
			boolean flag = false;
			for(int i=0; i< bgFormIds.size(); i++){
				_bgFormId = bgFormIds.get(i).toString();
				
				bgFormInfo = bgForm.getBgFormInfo(new ObjectUuidPK(_bgFormId),getBgFormSelectCol());
	            
	            bgTemplateRootID = bgFormInfo.getBgTemplate().getRootId().toString();//���Ԥ���ģ��ĸ��ڵ�ģ��
	            
	            periodID = bgFormInfo.getBgPeriod().getId().toString();//���Ԥ���Ķ�Ӧ�ڼ� 
	            orgUnitID = bgFormInfo.getOrgUnit().getId().toString();//���Ԥ���Ķ�Ӧ�ɱ�����
	            bgSchemeID = bgFormInfo.getBgScheme().getId().toString();//���Ԥ���Ķ�Ӧ����
	            
	            strRelation = getBudgetTemplateRelation(ctx,bgTemplateRootID);//ͨ��Ԥ��ģ�����Ѱģ���ϵ��
	            
	            if(!StringUtils.isEmpty(strRelation)){
	            	strRelEntrys = getBudgetTemplateRelationTemplateEntry(ctx,strRelation);//��ϵ���ҷ�¼Ԥ��ģ��rootid��
	            	if(!StringUtils.isEmpty(strRelEntrys)){
	            		
	            		strSubTemples = getSubTemples(ctx,strRelEntrys);//���ݸ��ڵ�ģ��id�����ӽڵ�ģ��id��
	            		
	            		if(!StringUtils.isEmpty(strSubTemples))
		            		bgFormCol = getBgFormCol(ctx,strSubTemples,periodID,orgUnitID,bgSchemeID,bgForm);
	            		    if(bgFormCol!=null && bgFormCol.size()>0){
	            		    	for(int j =0;j<bgFormCol.size();j++){
									str = bgFormCol.get(j).getId().toString();
									if(!bgFormIds.contains(str)){
										pstmt.setInt(1, updateState);
						                pstmt.setString(2, _userId);
						                pstmt.setTimestamp(3, _timestamp);
						                pstmt.setString(4, str);
						                pstmt.addBatch();
						                flag = true;
									}
								}
	            		    }
	            	}
	            }
			}
			if(flag){
				pstmt.executeBatch();
			}
		} catch (Exception e) {
			logger.error("budget error:", e);
			throw new BgException(BgFormException.DATABASEERROR, new String[] {e.getMessage()});
		}finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}
    
    /**
     * ���ݸ��ڵ���Ѱ�ӽڵ�
     * @param ctx
     * @param strRelEntrys
     * @throws BOSException 
     * @return������ӽڵ�
     */
    private String getSubTemples(Context ctx, String strRelEntrys) throws BOSException {
    	IBgTemplate bgTemple = BgTemplateFactory.getLocalInstance(ctx);
		StringBuffer stringBuffer = new StringBuffer();
		BgTemplateCollection subTemplesCol = null;
		
		view = new EntityViewInfo();
    	filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("rootID",strRelEntrys,CompareType.INCLUDE));
    	subTemplesCol = bgTemple.getBgTemplateCollection(view);
    	
    	if(subTemplesCol!=null && subTemplesCol.size()>0){
    		for(int i =0;i<subTemplesCol.size();i++){
    			strRelEntrys = null;
    			strRelEntrys = subTemplesCol.get(i).getId().toString();
    			stringBuffer.append(strRelEntrys);
    			if(i!=subTemplesCol.size()-1){
    				stringBuffer.append(",");
    			}
    		}
    	}
		return stringBuffer.toString();
	}

    
	/**
     * ��ȡ�������ģ����ͬ��Ӧ�ɱ����Ķ�Ӧ�����Ԥ���
     * @param ctx
     * @param str
     * @param periodFromID
     * @param orgUnitID
	 * @param bgSchemeID 
     * @param bgForm 
     * @return 
     * @throws BOSException 
     */
	private BgFormCollection getBgFormCol(Context ctx, String str,
			String periodFromID, String orgUnitID, String bgSchemeID, IBgForm bgForm) throws BOSException {
		
		view = new EntityViewInfo();
    	filter = new FilterInfo();
    	view.setFilter(filter);
    	//������Ԥ��
    	filter.getFilterItems().add(new FilterItemInfo("bgTemplate.id",str,CompareType.INCLUDE));
    	//��Ӧ����
    	filter.getFilterItems().add(new FilterItemInfo("bgPeriod.id",periodFromID));
    	//��Ӧ��֯
    	filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnitID)); 
    	//��Ӧ����
    	filter.getFilterItems().add(new FilterItemInfo("bgScheme.id",bgSchemeID));
    	//�ų�������״̬
    	filter.getFilterItems().add(new FilterItemInfo("state",BgFormStateEnum.EDITED_VALUE,CompareType.NOTEQUALS));
    	//�������ƺ��¼�����
    	filter.getFilterItems().add(new FilterItemInfo("dataSource",BgFormDataSourceEnum.CURRENTWORKOUT_VALUE+","+BgFormDataSourceEnum.FORMCOLLECT_VALUE,CompareType.INCLUDE));
    	
		return bgForm.getBgFormCollection(view);
	}
	
    /**
     * ��ȡ���Ԥ����Ԥ��ģ�������Ԥ��ģ��
     * @param ctx
     * @param str
     * @return
     * @throws BOSException 
     * @throws EASBizException 
     */
	private String getBudgetTemplateRelationTemplateEntry(Context ctx,
			String str) throws BOSException, EASBizException {
		IBudgetTemplateRelationTemplateEntry tempRelEntry = BudgetTemplateRelationTemplateEntryFactory.getLocalInstance(ctx);
		StringBuffer stringBuffer = new StringBuffer();
		BudgetTemplateRelationTemplateEntryCollection relationEntryCol = null;
		
		view = new EntityViewInfo();
    	filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent.id",str));
    	relationEntryCol = tempRelEntry.getBudgetTemplateRelationTemplateEntryCollection(view);
    	
    	if(relationEntryCol!=null && relationEntryCol.size()>0){
    		for(int i =0;i<relationEntryCol.size();i++){
    			str = null;
    			str = relationEntryCol.get(i).getId().toString();
    			str = tempRelEntry.getBudgetTemplateRelationTemplateEntryInfo(new ObjectUuidPK(str),getReEntrySelectCol())
    			.getReferencedTemplet().getId().toString();
    			stringBuffer.append(str);
    			if(i!=relationEntryCol.size()-1){
    				stringBuffer.append(",");
    			}
    		}
    	}
		return stringBuffer.toString();
	}

	/**
	 * ���ݴ�ص�Ԥ���ģ���ҵ���Ӧ��ģ���ϵ����
	 * @param ctx
	 * @param bgTemplateID
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
    private String getBudgetTemplateRelation(Context ctx,String bgTemplateID) throws BOSException, EASBizException {
    	
    	IBudgetTemplateRelation tempRelation = BudgetTemplateRelationFactory.getLocalInstance(ctx);
    	BudgetTemplateRelationCollection relationCol = null;
    	
    	String str = null;
    	
        view = new EntityViewInfo();
        filter = new FilterInfo();
        //����Ԥ��ģ�壬���Ƿ������Ҷ�Ӧ��ģ�����ù�ϵ
        filter.getFilterItems().add(new FilterItemInfo("TemplateName.id",bgTemplateID));
        filter.getFilterItems().add(new FilterItemInfo("IsEnable",Boolean.valueOf(true)));
        view.setFilter(filter);
        if(tempRelation.exists(filter)){
        	relationCol = tempRelation.getBudgetTemplateRelationCollection(view);
        	str = relationCol.get(0).getId().toString(); //ģ��
        }
		return str;
	}

	/**
	 *��������ѯԤ�����
	 * @return
	 */
	private SelectorItemCollection getBgFormSelectCol() {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("bgPeriod.*"));
        sic.add(new SelectorItemInfo("bgTemplate.*"));
        sic.add(new SelectorItemInfo("bgScheme.*"));
		return sic;
	}
	
	/**
	 *��������ѯģ���ϵ��¼Ԥ��ģ����
	 * @return
	 */
	private SelectorItemCollection getReEntrySelectCol() {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("ReferencedTemplet.*"));
		return sic;
	}

}
