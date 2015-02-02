 package com.kingdee.eas.ma.budget.app;
 
 import com.kingdee.bos.BOSException;
 import com.kingdee.bos.Context;
 import com.kingdee.bos.dao.IObjectCollection;
 import com.kingdee.bos.dao.IObjectPK;
 import com.kingdee.bos.dao.IObjectValue;
 import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
 import com.kingdee.bos.metadata.entity.EntityViewInfo;
 import com.kingdee.bos.metadata.entity.FilterInfo;
 import com.kingdee.bos.metadata.entity.FilterItemCollection;
 import com.kingdee.bos.metadata.entity.FilterItemInfo;
 import com.kingdee.bos.metadata.entity.SelectorItemCollection;
 import com.kingdee.bos.util.BOSUuid;
 import com.kingdee.eas.basedata.assistant.CurrencyInfo;
 import com.kingdee.eas.common.EASBizException;
 import com.kingdee.eas.ma.budget.BgCollectException;
 import com.kingdee.eas.ma.budget.BgException;
 import com.kingdee.eas.ma.budget.BgFormCollection;
 import com.kingdee.eas.ma.budget.BgFormFactory;
 import com.kingdee.eas.ma.budget.BgFormInfo;
 import com.kingdee.eas.ma.budget.BgFormStateEnum;
 import com.kingdee.eas.ma.budget.BgPeriodInfo;
 import com.kingdee.eas.ma.budget.BgSchemeInfo;
 import com.kingdee.eas.ma.budget.BgTemplateInfo;
 import com.kingdee.eas.ma.budget.IBgForm;
 import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
 import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
 import com.kingdee.eas.ma.nbudget.BgInfoHelper;
 import com.kingdee.eas.util.app.DbUtil;
 import com.kingdee.jdbc.rowset.IRowSet;
 import com.kingdee.util.db.SQLUtils;
 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 public class RefDisCountBgFormControllerBean
   extends AbstractRefDisCountBgFormControllerBean
 {
   public RefDisCountBgFormControllerBean() {}
   
   private static Logger logger = Logger.getLogger("com.kingdee.eas.ma.budget.app.RefDisCountBgFormControllerBean");
   
   protected IObjectCollection _loadRefBgFormCollectionByBgForm(Context ctx, IObjectValue bgFormInfo, List ouIdList) throws BOSException, EASBizException {
     RefDisCountBgFormCollection refBgFormCols = new RefDisCountBgFormCollection();
     if ((ouIdList == null) || (ouIdList.size() == 0)) {
       throw new BgCollectException(BgCollectException.HASNOOUSELECTED);
     }
     
     IBgForm iBgForm = BgFormFactory.getLocalInstance(ctx);
     SelectorItemCollection bgSic = new SelectorItemCollection();
     bgSic.add("bgTemplate.rootId");
     bgSic.add("bgScheme.rootId");
     bgSic.add("bgPeriod.number");
     bgSic.add("currency.number");
     BgFormInfo selectedBgFormInfo = iBgForm.getBgFormInfo(new ObjectUuidPK(bgFormInfo.get("id").toString()), bgSic);
     EntityViewInfo evi = new EntityViewInfo();
     evi.getSelector().add("id");
     evi.getSelector().add("number");
     evi.getSelector().add("name");
     evi.getSelector().add("orgUnit");
     FilterInfo filter = new FilterInfo();
     evi.setFilter(filter);
     filter.getFilterItems().add(new FilterItemInfo("bgTemplate.rootId", selectedBgFormInfo.getBgTemplate().getRootId().toString()));
     filter.getFilterItems().add(new FilterItemInfo("bgScheme.rootId", selectedBgFormInfo.getBgScheme().getRootId().toString()));
     filter.getFilterItems().add(new FilterItemInfo("bgPeriod.number", selectedBgFormInfo.getBgPeriod().getNumber()));
     filter.getFilterItems().add(new FilterItemInfo("currency.number", selectedBgFormInfo.getCurrency().getNumber()));
     filter.getFilterItems().add(new FilterItemInfo("state", new Integer(BgFormStateEnum.Certificate.getValue())));
     
     filter.getFilterItems().add(new FilterItemInfo("orgUnit", ""));
     String sqlToGetOuNumber = "SELECT TOP 1 FNUMBER FROM T_ORG_BASEUNIT WHERE FID = ?";
     int i = 0; for (int count = ouIdList.size(); i < count; i++) {
       FilterItemInfo filterItemInfo = filter.getFilterItems().get(5);
       String ouId = (String)ouIdList.get(i);
       filterItemInfo.setCompareValue(ouId);
       BgFormCollection bgFormCols = iBgForm.getBgFormCollection(evi);
       if ((bgFormCols == null) || (bgFormCols.size() == 0)) {
         IRowSet rsToGetOuNumber = DbUtil.executeQuery(ctx, sqlToGetOuNumber, new Object[] { ouId });
         String ouNumber = "";
         try {
           if ((rsToGetOuNumber != null) && (rsToGetOuNumber.next())) {
             ouNumber = rsToGetOuNumber.getString("FNUMBER");
           }
         } catch (SQLException e) {
           throw new BOSException(e);
         }
       }
       else
       {
         if (bgFormCols.size() > 1) {
           logger.debug("can not happen");
         }
         RefDisCountBgFormInfo child;
         try {
           child = new RefDisCountBgFormInfo();
         } catch (Exception e) {
           throw new BOSException(e);
         }
         child.setBgForm(bgFormCols.get(0));
         refBgFormCols.add(child);
       }
     }
     
     return refBgFormCols;
   }
   
   protected List _getCommentByBgForm(Context ctx, IObjectPK bgFormPK, boolean isCollectForm) throws BOSException, BgCollectException {
     List commentList = new ArrayList();
     
 
 
 
 
 
     StringBuffer sqlToGetComment = new StringBuffer("SELECT ORG.FName_l2 AS FORGNAME, REFBGFORM.FCOMMENT AS FCOMMENT FROM T_BG_RefDisCountBgForm REFBGFORM");
     sqlToGetComment.append(" LEFT OUTER JOIN T_BG_BgForm BGFORM ON REFBGFORM.FBgFormID = BGFORM.FID ");
     sqlToGetComment.append(" LEFT OUTER JOIN T_ORG_BaseUnit ORG ON ORG.FID = BGFORM.FOrgUnitID ");
     if (isCollectForm) {
       sqlToGetComment.append(" where REFBGFORM.FBgCollectID = ?");
     }
     else {
       sqlToGetComment.append(" where REFBGFORM.FID = ?");
     }
     IRowSet rsToGetComment = DbUtil.executeQuery(ctx, sqlToGetComment.toString(), new Object[] { bgFormPK.toString() });
     try {
       while (rsToGetComment.next()) {
         if (rsToGetComment.getObject("FCOMMENT") != null) {
           commentList.add(rsToGetComment.getString("FORGNAME") + "    " + rsToGetComment.getString("FCOMMENT"));
         }
       }
     } catch (SQLException e) {
       throw new BOSException(e);
     }
     return commentList;
   }
   
   protected void _addCommentByBgForm(Context ctx, IObjectPK bgFormPK, String comment) throws BOSException, EASBizException
   {
     Connection conn = null;
     PreparedStatement pstmt = null;
     ResultSet rst = null;
     
     String commentStr = null;
     try
     {
       conn = getConnection(ctx);
       pstmt = conn.prepareStatement("select FCOMMENT from T_BG_RefDisCountBgForm where FBGFORMID=?");
       pstmt.setString(1, bgFormPK.toString());
       rst = pstmt.executeQuery();
       
       if (rst.next()) {
         commentStr = rst.getString(1);
         comment = commentStr + "\r\n" + comment;
       }
       
 
       pstmt.close();
       pstmt = conn.prepareStatement("UPDATE T_BG_RefDisCountBgForm SET FCOMMENT = ? where FBGFORMID = ?");
       pstmt.setString(1, comment);
       pstmt.setString(2, bgFormPK.toString());
       pstmt.executeUpdate();
     }
     catch (SQLException ex) {
       logger.error(ex.getMessage());
       throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
     } finally {
       SQLUtils.cleanup(rst, pstmt, conn);
     }
   }
   
   protected byte[] _getKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId) throws BOSException, EASBizException {
     if ((bgCollectId == null) && (refBgFormId != null) && (bgFormId == null)) {
       return getKdfFromID(ctx, refBgFormId);
     }
     if ((bgCollectId == null) || (refBgFormId == null) || (bgFormId == null)) {
       return null;
     }
     Connection conn = null;
     PreparedStatement pstmt = null;
     ResultSet rst = null;
     
     StringBuffer sql = new StringBuffer();
     sql.append("select FKdtData from T_BG_RefDisCountBgForm where FBgCollectID = ? and FID = ? and FBgFormID = ?");
     
     byte[] data = null;
     try
     {
       conn = getConnection(ctx);
       pstmt = conn.prepareStatement(sql.toString());
       pstmt.setString(1, bgCollectId.toString());
       pstmt.setString(2, refBgFormId.toString());
       pstmt.setString(3, bgFormId.toString());
       rst = pstmt.executeQuery();
       
       if (rst.next()) {
         data = BgInfoHelper.getBytes(rst, 1);
       }
     } catch (SQLException ex) {
       logger.error(ex);
       throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
     } catch (BOSException ex) {
       throw ex;
     } catch (IOException ex) {
       logger.error(ex);
       throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
     } finally {
       SQLUtils.cleanup(rst, pstmt, conn);
     }
     return data;
   }
   
   private byte[] getKdfFromID(Context ctx, BOSUuid refBgFormId)
     throws BOSException, EASBizException
   {
     Connection conn = null;
     PreparedStatement pstmt = null;
     ResultSet rst = null;
     
     StringBuffer sql = new StringBuffer();
     sql.append("select FKdtData from T_BG_RefDisCountBgForm where  FID = ? ");
     
     byte[] data = null;
     try
     {
       conn = getConnection(ctx);
       pstmt = conn.prepareStatement(sql.toString());
       pstmt.setString(1, refBgFormId.toString());
       rst = pstmt.executeQuery();
       
       if (rst.next()) {
         data = BgInfoHelper.getBytes(rst, 1);
       }
     } catch (SQLException ex) {
       logger.error(ex);
       throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
     } catch (BOSException ex) {
       throw ex;
     } catch (IOException ex) {
       logger.error(ex);
       throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
     } finally {
       SQLUtils.cleanup(rst, pstmt, conn);
     }
     return data;
   }
   
   protected void _submitKdf(Context ctx, BOSUuid bgCollectId, BOSUuid refBgFormId, BOSUuid bgFormId, IObjectValue model) throws BOSException, EASBizException {
     if ((bgCollectId == null) || (refBgFormId == null) || (bgFormId == null) || (model == null)) {
       return;
     }
     RefDisCountBgFormInfo refBgFormInfo = (RefDisCountBgFormInfo)model;
     
     Connection conn = null;
     PreparedStatement pstmt = null;
     
     StringBuffer sql = new StringBuffer();
     sql.append("update T_BG_RefDisCountBgForm set FKdtData = ? where FBgCollectID = ? and FID = ? and FBgFormID = ?");
     try
     {
       conn = getConnection(ctx);
       pstmt = conn.prepareStatement(sql.toString());
       pstmt.setBytes(1, refBgFormInfo.getKdtData());
       pstmt.setString(2, bgCollectId.toString());
       pstmt.setString(3, refBgFormId.toString());
       pstmt.setString(4, bgFormId.toString());
       pstmt.executeUpdate();
     } catch (SQLException ex) {
       logger.error(ex);
       throw new BgException(BgException.DATABASEERROR, new String[] { ex.getMessage() });
     } catch (BOSException ex) {
       throw ex;
     } finally {
       SQLUtils.cleanup(pstmt, conn);
     }
   }
 }
 