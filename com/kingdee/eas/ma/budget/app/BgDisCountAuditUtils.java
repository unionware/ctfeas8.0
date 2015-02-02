package com.kingdee.eas.ma.budget.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.excel.model.struct.UserObject;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.rpt.IReport;
import com.kingdee.eas.fi.rpt.ReportFactory;
import com.kingdee.eas.fi.rpt.TableToolkit;
import com.kingdee.eas.ma.budget.BgAdjustFormDiversityData;
import com.kingdee.eas.ma.budget.BgCollectException;
import com.kingdee.eas.ma.budget.BgConstants;
import com.kingdee.eas.ma.budget.BgCtrSettingFacadeFactory;
import com.kingdee.eas.ma.budget.BgDataInfo;
import com.kingdee.eas.ma.budget.BgDisCountFormFactory;
import com.kingdee.eas.ma.budget.BgDisCountFormInfo;
import com.kingdee.eas.ma.budget.BgElementCollection;
import com.kingdee.eas.ma.budget.BgElementFactory;
import com.kingdee.eas.ma.budget.BgElementInfo;
import com.kingdee.eas.ma.budget.BgException;
import com.kingdee.eas.ma.budget.BgFSHelper;
import com.kingdee.eas.ma.budget.BgFormDataSourceEnum;
import com.kingdee.eas.ma.budget.BgFormDiversityData;
import com.kingdee.eas.ma.budget.BgFormException;
import com.kingdee.eas.ma.budget.BgFormFactory;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgFormStateEnum;
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
import com.kingdee.eas.ma.budget.BgTemplateFactory;
import com.kingdee.eas.ma.budget.IBgCtrSettingFacade;
import com.kingdee.eas.ma.budget.IBgDisCountForm;
import com.kingdee.eas.ma.budget.IBgElement;
import com.kingdee.eas.ma.budget.IBgForm;
import com.kingdee.eas.ma.budget.IBgPastRecord;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.eas.ma.budget.IBgTemplate;
import com.kingdee.eas.ma.budget.IRefDisCountBgForm;
import com.kingdee.eas.ma.budget.RefDisCountBgFormCollection;
import com.kingdee.eas.ma.budget.RefDisCountBgFormFactory;
import com.kingdee.eas.ma.budget.RefDisCountBgFormInfo;
import com.kingdee.eas.ma.nbudget.BgInfoHelper;
import com.kingdee.eas.ma.nbudget.BgNConstants;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.ma.nbudget.BgNSHelper;
import com.kingdee.eas.ma.nbudget.BgNSQLHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.db.SQLUtils;

public class BgDisCountAuditUtils {

	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.ma.budget.app.BgDisCountAuditUtils");

	protected Connection getConnection(Context ctx) throws SQLDataException {
		try {
			return EJBFactory.getConnection(ctx);
		} catch (SQLException sqle) {
			throw new SQLDataException(sqle);
		}
	}

	public boolean _auditFormCollect(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {
		if (id == null) {
			return false;
		}
		IBgForm ibgForm = BgFormFactory.getLocalInstance(ctx);
		IBgDisCountForm ibgCollect = BgDisCountFormFactory
				.getLocalInstance(ctx);
		IRefDisCountBgForm irefBgForm = RefDisCountBgFormFactory
				.getLocalInstance(ctx);
		if ((ibgCollect == null) || (ibgForm == null) || (irefBgForm == null)) {
			throw new BgException(BgException.NOINSTANCE);
		}
		BgDisCountFormInfo bgCollectFormInfo = null;
		IObjectPK pk = new ObjectUuidPK(id);
		if (!ibgCollect.exists(pk)) {
			throw new BgCollectException(BgCollectException.NODATA);
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("KdtData");
		sic.add("adjustMapData");
		sic.add("state");
		sic.add("bgForm.id");
		sic.add("bgForm.orgUnit.id");
		sic.add("bgForm.bgScheme.id");
		sic.add("refBgForms.id");
		sic.add("refBgForms.adjustMapData");
		sic.add("refBgForms.bgForm.id");
		sic.add("refBgForms.bgForm.orgUnit.id");
		sic.add("refBgForms.bgForm.bgScheme.id");
		bgCollectFormInfo = ibgCollect.getBgDisCountFormInfo(pk, sic);

		Map adjustMap = null;
		try {
			adjustMap = bgCollectFormInfo.getAdjustMap();
		} catch (Exception e) {
			throw new BgCollectException(BgCollectException.HASHMAPREADERROR);
		}

		BOSUuid nid = null;
		Connection conn = null;
		BgFormInfo bgFormInfo = null;
		try {
			conn = getConnection(ctx);

			nid = bgCollectFormInfo.getBgForm().getId();
			if ((adjustMap != null) && (!adjustMap.isEmpty())) {
				pk = new ObjectUuidPK(nid);
				if (ibgForm.exists(pk)) {
					bgFormInfo = ibgForm.getBgFormInfo(pk, BgNFSHelper
							.getSelectors());
					//将尚未计算的预算表 备份
					_cloneCollect(ctx, nid,
							BgFormDataSourceEnum.DISCOUNT,
							BgFormStateEnum.History, bgFormInfo);
					//*更新预算表的data
					processUpdateBgFormDataByte(conn, nid, bgCollectFormInfo
							.getKdtData());
					bgFormInfo.setData(bgCollectFormInfo.getKdtData());
					
					processUpdateBgFormInfoData(ctx, ibgForm, nid, adjustMap,
							BgPastSourceEnum.COLLECT, bgFormInfo);

					BgNSQLHelper
							.execute(
									ctx,
									"Update T_BG_BgForm set FVersionNo = FVersionNo + 0.1  where FID = ?",
									new Object[] {nid.toString() });
				}
			}

			BgNSQLHelper.execute(ctx,
					"delete from T_BG_BgTmpData where FBgFormId = ?",
					new Object[] { nid.toString() });

			RefDisCountBgFormInfo refInfo = null;
			RefDisCountBgFormCollection refCol = bgCollectFormInfo
					.getRefBgForms();
			if ((refCol != null) && (!refCol.isEmpty())) {
				int i = 0;
				for (int n = refCol.size(); i < n; i++) {
					refInfo = refCol.get(i);
					try {
						adjustMap = refInfo.getAdjustMap();
					} catch (Exception e) {
						throw new BgCollectException(
								BgCollectException.HASHMAPREADERROR);
					}

					refInfo.setKdtData(irefBgForm.getKdf(bgCollectFormInfo
							.getId(), refInfo.getId(), refInfo.getBgForm()
							.getId()));

					nid = refInfo.getBgForm().getId();
					if ((adjustMap != null) && (!adjustMap.isEmpty())) {
						pk = new ObjectUuidPK(nid);
						if (ibgForm.exists(pk)) {
							bgFormInfo = ibgForm.getBgFormInfo(pk, BgNFSHelper
									.getSelectors());

							_cloneCollect(ctx, nid,
									BgFormDataSourceEnum.DISCOUNT,
									BgFormStateEnum.History, bgFormInfo);

							processUpdateBgFormDataByte(conn, nid, refInfo
									.getKdtData());
							bgFormInfo.setData(refInfo.getKdtData());

							processUpdateBgFormInfoData(ctx, ibgForm, nid,
									adjustMap, BgPastSourceEnum.COLLECT,
									bgFormInfo);

							BgNSQLHelper
									.execute(
											ctx,
											"Update T_BG_BgForm set FVersionNo = FVersionNo + 0.1   where FID = ?",
											new Object[] { 
													nid.toString() });
						}
					}
					BgNSQLHelper.execute(ctx,
							"delete from T_BG_BgTmpData where FBgFormId = ?",
							new Object[] { nid.toString() });
				}
			}
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgException(BgException.DATABASEERROR, new String[] { ex
					.getMessage() });
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(conn);
		}

		return true;
	}

	//将尚未计算的预算表 备份
	protected IObjectPK _cloneCollect(Context ctx, BOSUuid id,
			BgFormDataSourceEnum dataSource, BgFormStateEnum state,
			BgFormInfo bgFormInfo) throws BOSException, EASBizException {
		if (bgFormInfo == null) {
			throw new BgFormException(BgFormException.NOBGFORMINFO);
		}
		IBgForm ibgForm = BgFormFactory.getLocalInstance(ctx);
		IObjectPK npk = null;
		BgFormInfo cloneInfo = null;

		cloneInfo = bgFormInfo.deepclone();
		if (dataSource != null)
			cloneInfo.setDataSource(dataSource);
		if (state != null) {
			cloneInfo.setState(state);
		}
		cloneInfo.setStartDate(bgFormInfo.getStartDate());
		cloneInfo.setEndDate(bgFormInfo.getEndDate());

		cloneInfo.setAdjust(bgFormInfo);
		cloneInfo.setAdjustMapData(null);
		cloneInfo.setVersionNo(bgFormInfo.getVersionNo());
		cloneInfo.setAuditTime(new Timestamp(System.currentTimeMillis()));

		npk = ibgForm.addnew(cloneInfo);
		return npk;
	}

	/**更新预算表的data
	 */
	protected void processUpdateBgFormDataByte(Connection conn,
			BOSUuid bgFormId, byte[] data) throws EASBizException {
		if ((conn == null) || (bgFormId == null) || (data == null)
				|| (data.length == 0)) {
			return;
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = conn
					.prepareStatement("update T_BG_BgForm set FData = ? where FID = ?");
			pstmt.setBytes(1, data);
			pstmt.setString(2, bgFormId.toString());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(pstmt);
		}
	}
	
	//
	private void processUpdateBgFormInfoData(Context ctx, IBgForm ibgForm,
			BOSUuid bgFormId, Map adjustMap, BgPastSourceEnum tableEnum,
			BgFormInfo bgFormInfo) throws BOSException, EASBizException {
		if ((ctx == null) || (ibgForm == null) || (bgFormId == null)
				|| (adjustMap == null))
			return;
		if (bgFormInfo == null) {
			throw new BgFormException(BgFormException.NOBGFORMINFO);
		}
		if (adjustMap.isEmpty())
			return;
		Book book = null;
		Sheet sheet = null;
		Collection tableCol = null;

		List formulaList = new ArrayList();
		List valueList = new ArrayList();
		List valueRow = new ArrayList();
		List valueCol = new ArrayList();

		List isCtrl = new ArrayList();
		List isFlexCtrl = new ArrayList();
		List flexCtrlParam = new ArrayList();
		List periodCtrlPolicy = new ArrayList();
		List isGroupCtrl = new ArrayList();
		List groupCtrlNo = new ArrayList();
		List isAllowAccess = new ArrayList();

		int rowIndex = 0;
		int colIndex = 0;
		int rowCount = 0;
		int colCount = 0;
		String formulaString = null;
		String bgItemString = null;
		String ctrlString = null;

		BgFormDiversityData adjustData = null;

		try {
			book = bgFormInfo.getBook();
			BgNFSHelper.disableCal(book);
			BgNFSHelper.disableUndo(book);

			for (int n = 0; n < book.getSheetCount(); n++) {
				sheet = book.getSheet(n);

				rowCount = sheet.getMaxRowIndex() + 1;
				colCount = sheet.getMaxColIndex() + 1;

				for (rowIndex = 0; rowIndex < rowCount; rowIndex++) {
					for (colIndex = 0; colIndex < colCount; colIndex++) {
						Cell cell = sheet.getCell(rowIndex, colIndex, false);

						if (BgNFSHelper.checkHasFormulaOfCell(cell)) {
							formulaString = BgNFSHelper.getFormulaOfCell(cell);

							if (adjustMap.containsKey(formulaString)) {
								adjustData = (BgFormDiversityData) adjustMap
										.get(formulaString);
								if ((adjustData == null)
										|| (adjustData.getNewValue() == null)) {
									BgNFSHelper.setValue(cell,
											BgNConstants.ZERO);
									BgNFSHelper.getItemFormula(cell).putExt(
											"BG_BUDGET_VALUE", null);
								} else {
									BgNFSHelper.setValue(cell, adjustData
											.getNewValue());
									BgNFSHelper.getItemFormula(cell)
											.putExt(
													"BG_BUDGET_VALUE",
													adjustData.getNewValue()
															.toString());
								}
								if (adjustData.getNewValue().compareTo(
										adjustData.getOldValue()) != 0) {
									/*BgNFSHelper.setBackGroup(cell,
											BgSHelper.EDIT_COLOR);*/
								} else {
									BgNFSHelper.setBackGroup(cell,
											BgSHelper.BASE_COLOR);
								}
							}

							if (BgNFSHelper.checkCanEditFormulaOfCell(cell)) {
								formulaList.add(formulaString);

								if (adjustMap.containsKey(formulaString)) {
									adjustData = (BgFormDiversityData) adjustMap
											.get(formulaString);
									if ((adjustData == null)
											|| (adjustData.getNewValue() == null)) {
										valueList.add("0");
									} else {
										valueList.add(adjustData.getNewValue()
												.toString());
									}
								} else if (cell.getValue() != Variant.nullVariant) {
									valueList.add(cell.getValue().toString());
								} else {
									valueList.add("0");
								}

								valueRow.add(String.valueOf(rowIndex));
								valueCol.add(String.valueOf(colIndex));

								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("isCtrl");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									isCtrl.add(null);
								} else {
									isCtrl.add(ctrlString);
								}
								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("isFlexCtrl");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									isFlexCtrl.add(null);
								} else {
									isFlexCtrl.add(ctrlString);
								}
								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("flexCtrlParam");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									flexCtrlParam.add(null);
								} else {
									flexCtrlParam.add(ctrlString);
								}
								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("periodCtrlPolicy");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									periodCtrlPolicy.add(null);
								} else {
									periodCtrlPolicy.add(ctrlString);
								}

								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("isGroupCtrl");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									isGroupCtrl.add(null);
								} else {
									isGroupCtrl.add(ctrlString);
								}
								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("groupCtrlNo");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									groupCtrlNo.add(null);
								} else {
									groupCtrlNo.add(ctrlString
											+ "_"
											+ bgFormInfo.getBgTemplate()
													.getId());
								}
								ctrlString = BgNFSHelper.getItemFormula(cell)
										.getExt("isAllowAccess");
								if ((ctrlString == null)
										|| (ctrlString.trim().length() == 0)) {
									isAllowAccess.add(null);
								} else
									isAllowAccess.add(ctrlString);
							}
						}
					}
				}
			}
			if (bgFormInfo.getAuditTime() != null)
				bgFormInfo.setAuditTime(new Timestamp(System
						.currentTimeMillis()));
			bgFormInfo.setBook(book);

			UserObject userObject = book
					.getUserObject("DECOMPOSER_RECALCULATE");
			if ((userObject != null)
					&& ((userObject.getValue() instanceof Boolean))
					&& (((Boolean) userObject.getValue()).booleanValue())) {
				IReport iReport = ReportFactory.getLocalInstance(ctx);
				byte[] data = iReport.backCacl(bgFormInfo, bgFormInfo
						.getZipData());
				bgFormInfo.setZipData(data);
			}

			ibgForm.submit(bgFormInfo); //更新BOOK

			if (!formulaList.isEmpty()) {
				List bgItemList = new ArrayList();
				List bgPeriodList = new ArrayList();
				List bgElementList = new ArrayList();
				List bgCurrencyList = new ArrayList();
				List bgDataTypeList = new ArrayList();

				processClearFormulaIsAdjust(ctx, bgFormId, formulaList);

				String[] formulaInfo = processCheckFormulaIsEctype(ctx,
						bgFormInfo, formulaList);
				if (formulaInfo != null) {
					int index = formulaList.indexOf(formulaInfo[0]);
					rowIndex = Integer.valueOf(valueRow.get(index).toString())
							.intValue();
					colIndex = Integer.valueOf(valueCol.get(index).toString())
							.intValue();
					throw new BgFormException(
							BgFormException.EXISTFORMFORMULASCHEME,
							new String[] {
									TableToolkit.xy2range(colIndex, rowIndex),
									formulaInfo[0], formulaInfo[1] });
				}

				String[] parameter = null;

				for (Iterator iter = formulaList.iterator(); iter.hasNext();) {
					formulaString = (String) iter.next();

					parameter = BgFSHelper.getFormulaInfo(formulaString);
					if ((parameter[0] == null)
							|| (parameter[0].trim().length() == 0)) {
						throw new BgFormException(
								BgFormException.ERRORPARAMETERNOITEM,
								new String[] { formulaString });
					}
					bgItemString = parameter[0].replaceAll(",", "_");
					bgItemList.add(bgItemString);

					if ((parameter[1] == null)
							|| (parameter[1].trim().length() == 0))
						parameter[1] = bgFormInfo.getBgPeriod().getNumber();
					bgPeriodList.add(parameter[1]);

					if ((parameter[2] == null)
							|| (parameter[2].trim().length() == 0)) {
						throw new BgFormException(
								BgFormException.ERRORPARAMETERNOELEMENT,
								new String[] { formulaString });
					}
					bgElementList.add(parameter[2]);

					if ((parameter[3] == null)
							|| (parameter[3].trim().length() == 0)) {
						throw new BgFormException(
								BgFormException.ERRORPARAMETERNODATATYPE,
								new String[] { formulaString });
					}
					bgDataTypeList.add(parameter[3]);

					if ((parameter[5] == null)
							|| (parameter[5].trim().length() == 0))
						parameter[5] = bgFormInfo.getCurrency().getNumber();
					bgCurrencyList.add(parameter[5]);
				}

				Map bgItemMap = getBudgetItemMap(ctx, bgItemList);
				if (!bgItemList.isEmpty()) {
					getBudgetNewItemMap(ctx, bgFormInfo.getId(), bgItemMap,
							bgItemList);
				}
				getBgItemUseMap(ctx, bgFormInfo.getId(), bgItemMap);

				Map bgPeriodMap = getBudgetPeriodMap(ctx, bgFormInfo.getId(),
						bgPeriodList, null);

				Map bgElementMap = getBudgetElementMap(ctx, bgFormInfo.getId(),
						bgElementList, null);

				Map bgCurrencyMap = getBudgetCurrencyMap(ctx, bgFormInfo
						.getId(), bgCurrencyList, null);

				processFormulaInsertData(ctx, bgFormInfo, formulaList,
						valueList, null, bgItemList, bgPeriodList,
						bgElementList, bgDataTypeList, bgCurrencyList,
						bgItemMap, bgPeriodMap, bgElementMap, bgCurrencyMap,
						isCtrl, isFlexCtrl, flexCtrlParam, periodCtrlPolicy,
						isGroupCtrl, groupCtrlNo, isAllowAccess);
			}

			adjustMap = updateAdjustMapWithOutSame(adjustMap);
			if (adjustMap.isEmpty()) {
				return;
			}
			processFormulaUpdateData(ctx, bgFormInfo, adjustMap);

			//processPastRecord(ctx, bgFormInfo, adjustMap, tableEnum);
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Error: ", ex);
			throw new BOSException(ex);
		} finally {
			BgNSHelper.objClear(tableCol);
			book = null;
		}
	}

	protected void processClearFormulaIsAdjust(Context ctx, BOSUuid id,
			List formulas) throws EASBizException, BOSException {
		if ((id == null) || (formulas == null) || (formulas.isEmpty())) {
			return;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;

		BgFormInfo bgFormInfo = null;
		IBgForm ibgForm = null;
		IObjectPK pk = null;

		try {
			conn = getConnection(ctx);
			ibgForm = BgFormFactory.getLocalInstance(ctx);

			if (ibgForm == null) {
				throw new BgFormException(BgFormException.NOINSTANCE);
			}
			pk = new ObjectUuidPK(id);
			if (ibgForm.exists(pk)) {
				bgFormInfo = BgInfoHelper.getBgFormInfo(conn, id, false);
			} else {
				return;
			}

			int index = 0;
			int batchNum = 50;
			int size = formulas.size();
			int count = (size - 1) / batchNum + 1;
			int pos = 0;

			String noFindStr = "#";
			StringBuffer sql = new StringBuffer(1024);
			sql
					.append("DELETE FROM T_BG_BGDATA WHERE FOrgUnitId = ? AND FBgSchemeId = ? AND FFormula IN (");
			for (index = 1; index < batchNum; index++) {
				sql.append("?, ");
			}
			sql.append("?) AND FBgFormId = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, bgFormInfo.getOrgUnit().getId().toString());
			pstmt.setString(2, bgFormInfo.getBgScheme().getId().toString());

			for (index = 0; index < count; index++) {
				for (int k = 1; k <= batchNum; pos++) {
					if (pos < size) {
						pstmt.setString(k + 2, (String) formulas.get(pos));
					} else {
						pstmt.setString(k + 2, noFindStr);
					}
					k++;
				}

				pstmt.setString(batchNum + 3, id.toString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (BOSException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private HashMap getBudgetItemMap(Context ctx, List bgItemList)
			throws EASBizException, BOSException {
		if ((bgItemList == null) || (bgItemList.isEmpty())) {
			return new HashMap();
		}
		String bgItemString = null;
		HashMap bgItemMap = new HashMap();
		Set bgItemSet = new HashSet();
		List bgItemNewList = new ArrayList();

		int index = 0;
		for (int count = bgItemList.size(); index < count; index++) {
			bgItemString = (String) bgItemList.get(index);
			if (!bgItemSet.contains(bgItemString)) {
				bgItemSet.add(bgItemString);
				bgItemNewList.add(bgItemString);
			}
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;

		index = 0;
		int batchNum = 50;
		int size = bgItemNewList.size();
		int count = (size - 1) / batchNum + 1;
		int pos = 0;

		String sqlstr = null;
		String noFindStr = "#";
		StringBuffer sql = new StringBuffer(1024);
		sql
				.append("select FID, FKey from T_BG_BgItemCombination where FKey IN (");
		for (index = 1; index < batchNum; index++) {
			sql.append("?,");
		}
		sql.append("?)");
		sqlstr = sql.toString();
		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sqlstr);
			for (index = 0; index < count; index++) {
				for (int k = 1; k <= batchNum; pos++) {
					if (pos < size) {
						pstmt.setString(k, (String) bgItemNewList.get(pos));
					} else {
						pstmt.setString(k, noFindStr);
					}
					k++;
				}

				rst = pstmt.executeQuery();
				while (rst.next()) {
					if (!bgItemMap.containsKey(rst.getString("FKey")))
						bgItemMap.put(rst.getString("FKey"), rst
								.getString("FID"));
				}
				rst.close();
			}
			/*
			 * } catch (BOSException ex) { throw ex;
			 */
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
			BgNSHelper.objClear(bgItemSet);
			BgNSHelper.objClear(bgItemNewList);
		}

		return bgItemMap;
	}

	protected String[] processCheckFormulaIsEctype(Context ctx,
			BgFormInfo bgFormInfo, List formulas) throws EASBizException,
			BOSException {
		if ((formulas == null) || (formulas.isEmpty())
				|| (bgFormInfo.getOrgUnit().getId() == null)
				|| (bgFormInfo.getBgScheme().getId() == null)) {
			return null;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;

		BgFormInfo bgInfo = null;

		int index = 0;
		int batchNum = 50;
		int size = formulas.size();
		int count = (size - 1) / batchNum + 1;
		int pos = 0;

		String noFindStr = "#";
		String formulaString = null;
		String bgFormId = null;
		String[] formulaInfo = null;
		StringBuffer sql = new StringBuffer(1024);
		StringBuffer tmpsql = new StringBuffer(1024);
		sql
				.append("Select TOP 1 FFormula, FBgFormId from T_BG_BgData where FOrgUnitId = ? AND FBgSchemeId = ? AND FFormula in (");
		tmpsql
				.append("Select TOP 1 FFormula, FBgFormId from T_BG_BgTmpData where FOrgUnitId = ? AND FBgSchemeId = ? AND FFormula in (");
		for (index = 1; index < batchNum; index++) {
			sql.append("?,");
			tmpsql.append("?,");
		}
		sql.append("?)");
		tmpsql.append("?)");

		boolean re = (bgFormInfo.getState().getValue() == 4)
				&& (!bgFormInfo.getId().equals(bgFormInfo.getAdjust().getId()));

		if (re) {
			sql.append(" and (FBgFormId <> ?)");
			tmpsql.append(" and (FBgFormId <> ?)");
		}

		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sql.toString());

			for (index = 0; index < count; index++) {
				if ((formulaString != null) && (bgFormId != null)) {
					break;
				}
				pstmt.setString(1, bgFormInfo.getOrgUnit().getId().toString());
				pstmt.setString(2, bgFormInfo.getBgScheme().getId().toString());

				for (int k = 0; k < batchNum; pos++) {
					if (pos < size) {
						pstmt.setString(k + 3, (String) formulas.get(pos));
					} else {
						pstmt.setString(k + 3, noFindStr);
					}
					k++;
				}

				if (re) {
					pstmt.setString(batchNum + 3, bgFormInfo.getAdjust()
							.getId().toString());
				}
				rst = pstmt.executeQuery();
				if (rst.next()) {
					formulaString = rst.getString(1);
					bgFormId = rst.getString(2);
				}
				SQLUtils.cleanup(rst);
			}

			SQLUtils.cleanup(rst, pstmt);

			if ((formulaString != null) && (bgFormId != null)) {
				bgInfo = BgFSHelper.getBgFormInfo(conn, BOSUuid.read(bgFormId),
						false);
				if (bgInfo != null) {
					formulaInfo = new String[2];
					formulaInfo[0] = formulaString;
					formulaInfo[1] = bgInfo.getName();
				} else {
					throw new BgFormException(BgFormException.NOBGFORMINFO);
				}
			}

			if (formulaInfo == null) {
				pstmt = conn.prepareStatement(tmpsql.toString());
				for (index = 0; index < count; index++) {
					pstmt.setString(1, bgFormInfo.getOrgUnit().getId()
							.toString());
					pstmt.setString(2, bgFormInfo.getBgScheme().getId()
							.toString());

					for (int k = 0; k < batchNum; pos++) {
						if (pos < size) {
							pstmt.setString(k + 3, (String) formulas.get(pos));
						} else {
							pstmt.setString(k + 3, noFindStr);
						}
						k++;
					}

					if (re) {
						pstmt.setString(batchNum + 3, bgFormInfo.getAdjust()
								.getId().toString());
					}
					rst = pstmt.executeQuery();
					if (rst.next()) {
						formulaString = rst.getString(1);
						bgFormId = rst.getString(2);
						break;
					}
					SQLUtils.cleanup(rst);
				}

				SQLUtils.cleanup(rst, pstmt);

				if ((formulaString != null) && (bgFormId != null)) {
					bgInfo = BgFSHelper.getBgFormInfo(conn, BOSUuid
							.read(bgFormId), false);
					if (bgInfo != null) {
						formulaInfo = new String[2];
						formulaInfo[0] = formulaString;
						formulaInfo[1] = bgInfo.getName();
					} else {
						throw new BgFormException(BgFormException.NOBGFORMINFO);
					}
				}
			}
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
			/*
			 * } catch (BOSException ex) { throw ex;
			 */
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
		}

		return formulaInfo;
	}

	private HashMap getBudgetPeriodMap(Context ctx, BOSUuid id,
			List bgPeriodList, List bgPeriodNew) throws EASBizException,
			BOSException {
		if ((ctx == null) || (id == null) || (bgPeriodList == null)) {
			return new HashMap();
		}

		String periodString = null;
		HashMap bgPeriodMap = new HashMap();
		HashMap bgPeriodNewMap = null;
		Set bgPeriodSet = new HashSet();
		BgPeriodInfo bgPeriodInfo = null;
		BgPeriodCollection bgPeriodCol = null;

		for (int index = 0, count = bgPeriodList.size(); index < count; index++) {
			periodString = (String) bgPeriodList.get(index);
			if (!bgPeriodSet.contains(periodString)) {
				bgPeriodSet.add(periodString);
			}
		}
		IBgPeriod ibgPeriod = null;
		try {
			if ((bgPeriodNew != null) && (!bgPeriodNew.isEmpty())) {
				bgPeriodNewMap = new HashMap();
				for (int index = 0, count = bgPeriodNew.size(); index < count; index++) {
					periodString = (String) bgPeriodNew.get(index);

					if (!bgPeriodSet.contains(periodString)) {
						bgPeriodSet.add(periodString);
						if (!bgPeriodNewMap.containsKey(periodString)) {
							bgPeriodNewMap.put(periodString, null);
						}
					}
				}
			}
			ibgPeriod = BgPeriodFactory.getLocalInstance(ctx);
			if (ibgPeriod != null) {
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("number", bgPeriodSet,
						CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				viewInfo.getSelector().addObjectCollection(getSelectorsQuery());
				bgPeriodCol = ibgPeriod.getBgPeriodCollection(viewInfo);

				int index = 0;
				for (int count = bgPeriodCol.size(); index < count; index++) {
					bgPeriodInfo = bgPeriodCol.get(index);
					bgPeriodMap.put(bgPeriodInfo.getNumber(), bgPeriodInfo);
				}

				getBgPeriodUseMap(ctx, id, bgPeriodMap);
			} else {
				throw new BgFormException(BgFormException.NOINSTANCE);
			}
		} catch (BOSException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (EASBizException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} finally {
			BgNSHelper.objClear(bgPeriodCol);
			BgNSHelper.objClear(bgPeriodSet);
			BgNSHelper.objClear(bgPeriodNewMap);
		}

		return bgPeriodMap;
	}

	private HashMap getBudgetElementMap(Context ctx, BOSUuid id,
			List bgElementList, List bgElementNew) throws EASBizException,
			BOSException {
		if ((ctx == null) || (id == null) || (bgElementList == null)) {
			return new HashMap();
		}
		String bgElementString = null;
		HashMap bgElementMap = new HashMap();
		HashMap bgElementNewMap = null;
		Set bgElementSet = new HashSet();
		BgElementInfo bgElementInfo = null;
		BgElementCollection bgElementCol = null;

		for (int index = 0, count = bgElementList.size(); index < count; index++) {
			bgElementString = (String) bgElementList.get(index);
			if (!bgElementSet.contains(bgElementString)) {
				bgElementSet.add(bgElementString);
			}
		}
		IBgElement ibgElement = null;
		try {
			if ((bgElementNew != null) && (!bgElementNew.isEmpty())) {
				bgElementNewMap = new HashMap();
				for (int index = 0, count = bgElementNew.size(); index < count; index++) {
					bgElementString = (String) bgElementNew.get(index);

					if (!bgElementSet.contains(bgElementString)) {
						bgElementSet.add(bgElementString);
						if (!bgElementNewMap.containsKey(bgElementString)) {
							bgElementNewMap.put(bgElementString, null);
						}
					}
				}
			}
			ibgElement = BgElementFactory.getLocalInstance(ctx);
			if (ibgElement != null) {
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("number", bgElementSet,
						CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				viewInfo.getSelector().addObjectCollection(getSelectorsQuery());

				bgElementCol = ibgElement.getBgElementCollection(viewInfo);

				for (int index = 0, count = bgElementCol.size(); index < count; index++) {
					bgElementInfo = bgElementCol.get(index);
					bgElementMap.put(bgElementInfo.getNumber(), bgElementInfo);
				}

				getBgElementUseMap(ctx, id, bgElementMap);
			} else {
				throw new BgFormException(BgFormException.NOINSTANCE);
			}
		} catch (EASBizException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (BOSException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} finally {
			BgNSHelper.objClear(bgElementCol);
			BgNSHelper.objClear(bgElementSet);
			BgNSHelper.objClear(bgElementNewMap);
		}

		return bgElementMap;
	}

	private HashMap getBudgetCurrencyMap(Context ctx, BOSUuid id,
			List currencyList, List currencyNew) throws EASBizException,
			BOSException {
		if ((ctx == null) || (id == null) || (currencyList == null)) {
			return new HashMap();
		}
		String currencyString = null;
		HashMap currencyMap = new HashMap();
		HashMap currencyNewMap = null;
		Set currencySet = new HashSet();
		CurrencyInfo currencyInfo = null;
		CurrencyCollection currencyCol = null;

		for (int index = 0, count = currencyList.size(); index < count; index++) {
			currencyString = (String) currencyList.get(index);
			if (!currencySet.contains(currencyString)) {
				currencySet.add(currencyString);
			}
		}
		ICurrency iCurrency = null;
		try {
			if ((currencyNew != null) && (!currencyNew.isEmpty())) {
				currencyNewMap = new HashMap();
				for (int index = 0, count = currencyNew.size(); index < count; index++) {
					currencyString = (String) currencyNew.get(index);

					if (!currencySet.contains(currencyString)) {
						currencySet.add(currencyString);

						if (!currencyNewMap.containsKey(currencyString)) {
							currencyNewMap.put(currencyString, null);
						}
					}
				}
			}

			iCurrency = CurrencyFactory.getLocalInstance(ctx);
			if (iCurrency != null) {
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("number", currencySet,
						CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				viewInfo.getSelector().addObjectCollection(getSelectorsQuery());

				currencyCol = iCurrency.getCurrencyCollection(viewInfo);

				for (int index = 0, count = currencyCol.size(); index < count; index++) {
					currencyInfo = currencyCol.get(index);
					currencyMap.put(currencyInfo.getNumber(), currencyInfo);
				}

				getCurrencyUseMap(ctx, id, currencyMap);
			} else {
				throw new BgFormException(BgFormException.NOINSTANCE);
			}
		} catch (EASBizException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (BOSException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} finally {
			BgNSHelper.objClear(currencyNewMap);
			BgNSHelper.objClear(currencyCol);
			BgNSHelper.objClear(currencySet);
		}

		return currencyMap;
	}

	private void getBgItemUseMap(Context ctx, BOSUuid id, Map bgItemAllMap)
			throws EASBizException, BOSException {
		if ((ctx == null) || (id == null) || (bgItemAllMap == null)
				|| (bgItemAllMap.isEmpty())) {
			return;
		}
		String bgItemNumbrString = null;
		String bgItemId = null;
		BgRefBgItemCombinInfo bgRefItemInfo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection(ctx);
			pstmt = conn
					.prepareStatement("DELETE FROM T_BG_BgRefBgItemCombin WHERE FBgFormID = ?");
			pstmt.setString(1, id.toString());
			pstmt.executeUpdate();
			pstmt.close();

			pstmt = conn
					.prepareStatement("INSERT INTO T_BG_BgRefBgItemCombin(FID, FBgFormID, FBgItemCombinId) VALUES (?,?,?)");

			bgRefItemInfo = new BgRefBgItemCombinInfo();
			for (Iterator iter = bgItemAllMap.keySet().iterator(); iter
					.hasNext();) {
				bgItemNumbrString = (String) iter.next();
				bgItemId = (String) bgItemAllMap.get(bgItemNumbrString);

				if (bgItemId != null) {
					pstmt.setString(1, BOSUuid.create(
							bgRefItemInfo.getBOSType()).toString());
					pstmt.setString(2, id.toString());
					pstmt.setString(3, bgItemId);
					pstmt.addBatch();
				} else {
					throw new BgFormException(BgFormException.SUBMITNOPERIOD,
							new String[] { bgItemNumbrString });
				}
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (EASBizException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private void getBgElementUseMap(Context ctx, BOSUuid id, Map bgElementMap)
			throws EASBizException, BOSException {
		if ((ctx == null) || (id == null) || (bgElementMap == null)
				|| (bgElementMap.isEmpty())) {
			return;
		}
		String bgElementNumbrString = null;
		BgElementInfo bgElementInfo = null;
		BgRefBgPeriodInfo bgRefElementInfo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection(ctx);
			pstmt = conn
					.prepareStatement("DELETE FROM T_BG_BgRefBgElement WHERE FBgFormID = ?");
			pstmt.setString(1, id.toString());
			pstmt.executeUpdate();
			SQLUtils.cleanup(pstmt);

			pstmt = conn
					.prepareStatement("INSERT INTO T_BG_BgRefBgElement(FID, FBgFormID, FBgElementID) VALUES (?,?,?)");

			bgRefElementInfo = new BgRefBgPeriodInfo();
			for (Iterator iter = bgElementMap.keySet().iterator(); iter
					.hasNext();) {
				bgElementNumbrString = (String) iter.next();
				bgElementInfo = (BgElementInfo) bgElementMap
						.get(bgElementNumbrString);

				if (bgElementInfo != null) {
					pstmt.setString(1, BOSUuid.create(
							bgRefElementInfo.getBOSType()).toString());
					pstmt.setString(2, id.toString());
					pstmt.setString(3, bgElementInfo.getId().toString());
					pstmt.addBatch();
				} else {
					throw new BgFormException(BgFormException.SUBMITNOELEMENT,
							new String[] { bgElementNumbrString });
				}
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (EASBizException ex) {
			logger.error(ex.getMessage());
			throw ex;
			/*
			 * } catch (BOSException ex) { logger.error(ex.getMessage()); throw
			 * ex;
			 */
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private void getBgPeriodUseMap(Context ctx, BOSUuid id, Map bgPeriodMap)
			throws EASBizException, BOSException {
		if ((ctx == null) || (id == null) || (bgPeriodMap == null)
				|| (bgPeriodMap.isEmpty())) {
			return;
		}
		String bgPeriodNumbrString = null;
		BgPeriodInfo bgPeriodInfo = null;
		BgRefBgPeriodInfo bgRefPeriodInfo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection(ctx);
			pstmt = conn
					.prepareStatement("DELETE FROM T_BG_BgRefBgPeriod WHERE FBgFormID = ?");
			pstmt.setString(1, id.toString());
			pstmt.executeUpdate();
			pstmt.close();

			pstmt = conn
					.prepareStatement("INSERT INTO T_BG_BgRefBgPeriod(FID, FBgFormID, FBgPeriodID) VALUES (?,?,?)");

			bgRefPeriodInfo = new BgRefBgPeriodInfo();
			for (Iterator iter = bgPeriodMap.keySet().iterator(); iter
					.hasNext();) {
				bgPeriodNumbrString = (String) iter.next();
				bgPeriodInfo = (BgPeriodInfo) bgPeriodMap
						.get(bgPeriodNumbrString);

				if (bgPeriodInfo != null) {
					pstmt.setString(1, BOSUuid.create(
							bgRefPeriodInfo.getBOSType()).toString());
					pstmt.setString(2, id.toString());
					pstmt.setString(3, bgPeriodInfo.getId().toString());
					pstmt.addBatch();
				} else {
					throw new BgFormException(BgFormException.SUBMITNOPERIOD,
							new String[] { bgPeriodNumbrString });
				}
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (EASBizException ex) {
			throw ex;
			/*
			 * } catch (BOSException ex) { throw ex;
			 */
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private void getCurrencyUseMap(Context ctx, BOSUuid id, Map currencyMap)
			throws EASBizException, BOSException {
		if ((ctx == null) || (id == null) || (currencyMap == null)
				|| (currencyMap.isEmpty())) {
			return;
		}
		String bgCurrencyNumbrString = null;
		CurrencyInfo currencyInfo = null;
		BgRefBgCurrencyInfo bgRefCurrencyInfo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection(ctx);
			pstmt = conn
					.prepareStatement("DELETE FROM T_BG_BgRefBgCurrency WHERE FBgFormID = ?");
			pstmt.setString(1, id.toString());
			pstmt.executeUpdate();

			pstmt = conn
					.prepareStatement("INSERT INTO T_BG_BgRefBgCurrency(FID, FBgFormID, FBgCurrencyID) VALUES (?,?,?)");

			bgRefCurrencyInfo = new BgRefBgCurrencyInfo();
			for (Iterator iter = currencyMap.keySet().iterator(); iter
					.hasNext();) {
				bgCurrencyNumbrString = (String) iter.next();
				currencyInfo = (CurrencyInfo) currencyMap
						.get(bgCurrencyNumbrString);

				if (currencyInfo != null) {
					pstmt.setString(1, BOSUuid.create(
							bgRefCurrencyInfo.getBOSType()).toString());
					pstmt.setString(2, id.toString());
					pstmt.setString(3, currencyInfo.getId().toString());
					pstmt.addBatch();
				} else {
					throw new BgFormException(BgFormException.SUBMITNOCURRENCY,
							new String[] { bgCurrencyNumbrString });
				}
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (EASBizException ex) {
			throw ex;
			/*
			 * } catch (BOSException ex) { throw ex;
			 */
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private SelectorItemCollection getSelectorsQuery() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		return sic;
	}

	private void processFormulaInsertData(Context ctx, BgFormInfo bgFormInfo,
			List formulas, List bValues, List aValues, List bgItemList,
			List bgPeriodList, List bgElementList, List bgDataTypeList,
			List bgCurrencyList, Map bgItemMap, Map bgPeriodMap,
			Map bgElementMap, Map bgCurrencyMap, List isCtrl, List isFlexCtrl,
			List flexCtrlParam, List periodCtrlPolicy, List isGroupCtrl,
			List groupCtrlNo, List isAllowAccess) throws BOSException,
			EASBizException {
		if ((formulas == null) || (bValues == null) || (bgFormInfo == null)
				|| (formulas.isEmpty()) || (bgItemList == null)
				|| (bgPeriodList == null) || (bgElementList == null)
				|| (bgDataTypeList == null) || (bgCurrencyList == null)
				|| (bgItemMap == null) || (bgPeriodMap == null)
				|| (bgElementMap == null) || (bgCurrencyMap == null)
				|| (isCtrl == null) || (isFlexCtrl == null)
				|| (flexCtrlParam == null) || (periodCtrlPolicy == null)
				|| (isGroupCtrl == null) || (groupCtrlNo == null)) {

			return;
		}

		if (formulas.size() != bValues.size()) {
			return;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		BigDecimal bValue = null;
		BigDecimal aValue = null;
		BigDecimal cValue = null;

		BgPeriodInfo bgPeriodInfo = null;
		BgElementInfo bgElementInfo = null;
		CurrencyInfo currencyInfo = null;
		String bgItemId = null;
		String ctrlString = null;

		BOSObjectType type = new BgDataInfo().getBOSType();
		IBgCtrSettingFacade localInstance = BgCtrSettingFacadeFactory
				.getLocalInstance(ctx);
		Map ctrlModeMap = localInstance.getCtrlModeMapFromTmpData(bgFormInfo
				.getId());
		Map formulaCtrlMap = null;
		String formula = null;
		Boolean isControl = null;
		Boolean isFlexible = null;
		BigDecimal flexParam = null;
		Integer ctrlType = null;
		Boolean isGroupCtr = null;
		String groupNo = null;
		Boolean allowAccess = null;
		Boolean isCtrlAllowChange = null;
		Boolean isFromSuper = null;
		try {
			conn = getConnection(ctx);

			pstmt = conn
					.prepareStatement("Insert Into T_BG_BgData (FID,FBgValue,FBizActual,FBgBalance,FFormula,FBgPeriodId,FBgFormId,FBgElementId,FCurrencyId,FOrgUnitId,FBgSchemeId,FBgTemplateId,FBgItemCombinId,FIsControl,FIsFlexible,FFlexParam,FCtrlType,FIsGroupCtrl,FGroupNo,FisAllowAccess,FIsFromSuper,FIsCtrlAllowChange,FCtrlModeFrom, FDataState) Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)");

			int index = 0;
			for (int count = formulas.size(); index < count; index++) {
				formula = formulas.get(index).toString();
				formulaCtrlMap = (Map) ctrlModeMap.get(formula);
				if (formulaCtrlMap == null) {
					formulaCtrlMap = new HashMap();
				}
				try {
					bValue = new BigDecimal(bValues.get(index).toString());
				} catch (Exception ex) {
					bValue = BgConstants.BIGZERO;
				}

				if ((aValues != null) && (aValues.size() == bValues.size())) {
					try {
						aValue = new BigDecimal(aValues.get(index).toString());
					} catch (Exception ex) {
						aValue = BgConstants.BIGZERO;
					}
				} else {
					aValue = BgConstants.BIGZERO;
				}

				pstmt.setString(1, BOSUuid.create(type).toString());
				pstmt.setBigDecimal(2, bValue.setScale(8, 4));
				pstmt.setBigDecimal(3, aValue.setScale(8, 4));
				pstmt.setBigDecimal(4, bValue.setScale(8, 4));
				pstmt.setString(5, formula);

				bgPeriodInfo = (BgPeriodInfo) bgPeriodMap.get(bgPeriodList
						.get(index));

				if (bgPeriodInfo == null) {
					logger.debug("bgperiod number:"
							+ bgPeriodList.get(index).toString());
					throw new BgFormException(BgFormException.SUBMITNOPERIOD,
							new String[] { bgPeriodList.get(index).toString() });
				}
				pstmt.setString(6, bgPeriodInfo.getId().toString());
				pstmt.setString(7, bgFormInfo.getId().toString());

				bgElementInfo = (BgElementInfo) bgElementMap.get(bgElementList
						.get(index));
				if (bgElementInfo == null)
					throw new BgFormException(
							BgFormException.SUBMITNOELEMENT,
							new String[] { bgElementList.get(index).toString() });
				pstmt.setString(8, bgElementInfo.getId().toString());

				currencyInfo = (CurrencyInfo) bgCurrencyMap.get(bgCurrencyList
						.get(index));

				if (currencyInfo == null)
					throw new BgFormException(
							BgFormException.SUBMITNOCURRENCY,
							new String[] { bgCurrencyList.get(index).toString() });
				pstmt.setString(9, currencyInfo.getId().toString());
				pstmt.setString(10, bgFormInfo.getOrgUnit().getId().toString());
				pstmt
						.setString(11, bgFormInfo.getBgScheme().getId()
								.toString());
				pstmt.setString(12, bgFormInfo.getBgTemplate().getId()
						.toString());

				bgItemId = (String) bgItemMap.get(bgItemList.get(index));
				if (bgItemId == null)
					throw new BgFormException(BgFormException.SUBMITNOITEM,
							new String[] { bgItemList.get(index).toString() });
				pstmt.setString(13, bgItemId);

				isControl = (Boolean) formulaCtrlMap.get("isCtrl");
				if (isControl != null) {
					pstmt.setBoolean(14, isControl.booleanValue());
				} else {
					pstmt.setBoolean(14, false);
				}

				isFlexible = (Boolean) formulaCtrlMap.get("isFlexCtrl");
				if (isFlexible != null) {
					pstmt.setBoolean(15, isFlexible.booleanValue());
				} else {
					pstmt.setBoolean(15, false);
				}

				flexParam = (BigDecimal) formulaCtrlMap.get("flexCtrlParam");
				if (flexParam != null) {
					pstmt.setBigDecimal(16, flexParam);
				} else {
					pstmt.setBigDecimal(16, new BigDecimal("1.000000"));
				}

				ctrlType = (Integer) formulaCtrlMap.get("periodCtrlPolicy");
				if (flexParam != null) {
					pstmt.setInt(17, ctrlType.intValue());
				} else {
					pstmt.setInt(17, 10);
				}

				isGroupCtr = (Boolean) formulaCtrlMap.get("isGroupCtrl");
				if (isGroupCtr != null) {
					pstmt.setBoolean(18, isGroupCtr.booleanValue());
				} else {
					pstmt.setBoolean(18, false);
				}

				groupNo = (String) formulaCtrlMap.get("groupCtrlNo");
				pstmt.setString(19, groupNo);

				allowAccess = (Boolean) formulaCtrlMap.get("isAllowAccess");
				if (allowAccess != null) {
					pstmt.setBoolean(20, allowAccess.booleanValue());
				} else {
					pstmt.setBoolean(20, false);
				}

				isFromSuper = (Boolean) formulaCtrlMap.get("isFromSuper");
				if (isFromSuper != null) {
					pstmt.setBoolean(21, isFromSuper.booleanValue());
				} else {
					pstmt.setBoolean(21, false);
				}

				isCtrlAllowChange = (Boolean) formulaCtrlMap
						.get("isCtrlAllowChange");
				if (isCtrlAllowChange != null) {
					pstmt.setBoolean(22, isCtrlAllowChange.booleanValue());
				} else {
					pstmt.setBoolean(22, true);
				}

				Object obj = formulaCtrlMap.get("ctrlModeFrom");
				if ((obj instanceof Integer)) {
					pstmt.setInt(23, ((Integer) obj).intValue());
				} else {
					pstmt.setInt(23, 1);
				}
				pstmt.setInt(24, 9);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (EASBizException ex) {
			logger.error(ex.getMessage());
			throw ex;
			/*
			 * } catch (BOSException ex) { logger.error(ex.getMessage()); throw
			 * ex;
			 */
		} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private Map updateAdjustMapWithOutSame(Map adjustMap) {
		String formulaString = null;
		BgAdjustFormDiversityData adjustData = null;
		BgFormDiversityData data = null;
		Object obj = null;
		for (Iterator iter = adjustMap.keySet().iterator(); iter.hasNext();) {
			formulaString = (String) iter.next();
			obj = adjustMap.get(formulaString);

			if ((obj != null) && ((obj instanceof BgFormDiversityData))) {
				data = (BgFormDiversityData) obj;

				if (data.getNewValue().compareTo(data.getOldValue()) == 0) {
					iter.remove();
				}
			} else if ((obj != null)
					&& ((obj instanceof BgAdjustFormDiversityData))) {
				data = (BgAdjustFormDiversityData) obj;
				if (data.getNewValue().compareTo(data.getOldValue()) == 0) {
					iter.remove();
				}
			}
		}
		return adjustMap;
	}

	private void processFormulaUpdateData(Context ctx, BgFormInfo bgFormInfo,
			Map diversityMap) throws BOSException, EASBizException {
		if ((diversityMap == null) || (diversityMap.isEmpty())) {
			return;
		}
		Set formulaSet = diversityMap.keySet();
		if ((formulaSet == null) || (formulaSet.isEmpty())) {
			return;
		}
		String formulaString = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		BgFormDiversityData data = null;
		Object obj = null;

		String _orgUnitId = bgFormInfo.getOrgUnit().getId().toString();
		String _bgSchemeId = bgFormInfo.getBgScheme().getId().toString();
		String sqlNoCtrl = "Update T_BG_BgData set FBgValue = ?  where FOrgUnitId = ? AND FBgSchemeId = ? AND FFormula = ?";

		int count = 0;
		int batch = 100;

		try {
			conn = getConnection(ctx);
			pstmt = conn.prepareStatement(sqlNoCtrl);

			for (Iterator iter = formulaSet.iterator(); iter.hasNext();) {
				formulaString = (String) iter.next();
				obj = diversityMap.get(formulaString);

				if ((obj instanceof BgFormDiversityData)) {
					data = (BgFormDiversityData) obj;
					if (data.getNewValue().compareTo(data.getOldValue()) != 0) {

						if ((data == null) || (data.getNewValue() == null)) {
							pstmt.setBigDecimal(1, BgConstants.BIGZERO);
						} else {
							pstmt.setBigDecimal(1, data.getNewValue().setScale(
									8, 4));
						}
						pstmt.setString(2, _orgUnitId);
						pstmt.setString(3, _bgSchemeId);
						pstmt.setString(4, data.getFormula());
						pstmt.addBatch();
						count++;

						if (count >= batch) {
							pstmt.executeBatch();
							count = 0;
						}
					}
				}
			}
			if (count != 0) {
				pstmt.executeBatch();
			}
		} catch (SQLException ex) {
			logger.error("BGBillError: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
			/*
			 * } catch (BOSException ex) { throw ex;
			 */} finally {
			SQLUtils.cleanup(pstmt, conn);
		}
	}

	private void processPastRecord(Context ctx, BgFormInfo bgFormInfo,
			Map divMap, BgPastSourceEnum type) throws BOSException,
			EASBizException {
		if ((bgFormInfo == null) || (divMap == null) || (divMap.isEmpty())) {
			return;
		}
		String formulaId = null;
		String formulaString = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;

		Set formulaSet = null;
		Map formulaIdMap = new HashMap();
		List _tmp = new ArrayList();
		_tmp.add(bgFormInfo.getOrgUnit().getId().toString());
		_tmp.add(bgFormInfo.getBgScheme().getId().toString());

		StringBuffer sql = new StringBuffer(1024);

		sql
				.append("select FID, FFormula from T_BG_BgData where FOrgUnitId = ? AND FBgSchemeId = ?  ");
		sql.append(" AND FFormula IN(");

		formulaSet = divMap.keySet();
		for (Iterator iter = formulaSet.iterator(); iter.hasNext();) {
			sql.append("?,");
			_tmp.add(iter.next());
		}
		sql.delete(sql.length() - 1, sql.length());
		sql.append(")");
		try {
			conn = getConnection(ctx);

			pstmt = conn.prepareStatement(sql.toString());
			BgNSQLHelper.setParams(pstmt, _tmp);
			rst = pstmt.executeQuery();

			while (rst.next()) {
				formulaId = rst.getString(1);
				formulaString = rst.getString(2);
				if (!formulaIdMap.containsKey(formulaString)) {
					formulaIdMap.put(formulaString, formulaId);
				}
			}
			UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
			Timestamp time = new Timestamp(System.currentTimeMillis());

			IBgPastRecord ibgPastRecod = BgPastRecordFactory
					.getLocalInstance(ctx);
			if (ibgPastRecod != null) {
				BgPastRecordInfo bgPastRecordInfo = new BgPastRecordInfo();
				bgPastRecordInfo.setId(BOSUuid.create(bgPastRecordInfo
						.getBOSType()));
				bgPastRecordInfo.setPastSource(type);
				bgPastRecordInfo.setBgFormId(bgFormInfo.getId().toString());
				Object _obj;
				BgFormDiversityData divData;
				BgAdjustFormDiversityData adjData;
				BgPastDataInfo bgPastDataInfo;
				Iterator iter;
				if (!divMap.isEmpty()) {
					_obj = null;
					divData = null;
					adjData = null;
					bgPastDataInfo = null;

					for (iter = formulaSet.iterator(); iter.hasNext();) {
						formulaString = (String) iter.next();

						if (formulaIdMap.containsKey(formulaString)) {
							_obj = divMap.get(formulaString);

							if ((_obj instanceof BgAdjustFormDiversityData)) {
								adjData = (BgAdjustFormDiversityData) _obj;
							} else if ((_obj instanceof BgFormDiversityData)) {
								divData = (BgFormDiversityData) _obj;
								adjData = new BgAdjustFormDiversityData(divData);
							}

							if ((adjData != null)
									&& (adjData.getNewValue().compareTo(
											adjData.getOldValue()) != 0)) {

								bgPastDataInfo = new BgPastDataInfo();
								bgPastDataInfo.setPastRecord(bgPastRecordInfo);
								bgPastDataInfo
										.setBalance(adjData.getOldValue());
								bgPastDataInfo.setPastValue(compute(adjData
										.getNewValue(), adjData.getOldValue()));
								formulaString = adjData.getFormula();
								bgPastDataInfo.setFormula(formulaString);
								bgPastDataInfo.setBgData(formulaIdMap.get(
										formulaString).toString());
								bgPastDataInfo.setAdjReason(adjData
										.getAdjustReson());

								if (adjData.getAdjustor() != null) {
									bgPastDataInfo.setAdjustor(new UserInfo());
									bgPastDataInfo.getAdjustor()
											.setId(
													BOSUuid.read(adjData
															.getAdjustor()));
								} else {
									bgPastDataInfo.setAdjustor(new UserInfo());
									bgPastDataInfo.getAdjustor().setId(
											user.getId());
								}

								if (adjData.getAdjustTime() != null) {
									bgPastDataInfo.setAdjusttime(new Timestamp(
											adjData.getAdjustTime().getTime()));
								} else {
									bgPastDataInfo.setAdjusttime(time);
								}

								bgPastDataInfo.setAdjValue(BgConstants.BIGZERO);

								bgPastRecordInfo.getPastDatas().addObject(
										bgPastDataInfo);
							}
						}
					}
				}
				ibgPastRecod.addnew(bgPastRecordInfo);
			} else {
				throw new BgFormException(BgFormException.NOINSTANCE);
			}
		} catch (SQLException ex) {
			logger.error("Error: ", ex);
			throw new BgFormException(BgFormException.DATABASEERROR,
					new String[] { ex.getMessage() });
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} finally {
			SQLUtils.cleanup(rst, pstmt, conn);
			BgNSHelper.objClear(formulaIdMap);
			BgNSHelper.objClear(formulaSet);
		}
	}

	private BigDecimal compute(BigDecimal minuend, BigDecimal subtrahend) {
		if ((minuend == null) && (subtrahend == null)) {
			return BgConstants.BIGZERO;
		}
		if ((minuend != null) && (subtrahend == null)) {
			return minuend;
		}
		if ((minuend == null) && (subtrahend != null)) {
			minuend = BgConstants.BIGZERO;
		}
		return minuend.subtract(subtrahend);
	}

	private HashMap getBudgetNewItemMap(Context ctx, BOSUuid id,
			Map hasItemMap, List hasItemList) throws EASBizException,
			BOSException {
		if ((ctx == null) || (hasItemMap == null) || (hasItemList == null)) {
			return new HashMap();
		}
		if (hasItemList.isEmpty()) {
			return (HashMap) hasItemMap;
		}
		String bgItemString = null;
		String bgItemId = null;
		List sqlSearch = new ArrayList();
		List newCreate = new ArrayList();
		HashMap reItemMap = (HashMap) hasItemMap;
		HashMap sqlItemMap = null;
		Hashtable bgItemTab = null;
		Set sqlItemSet = null;

		for (int i = 0, n = hasItemList.size(); i < n; i++) {
			bgItemString = (String) hasItemList.get(i);

			if (!hasItemMap.containsKey(bgItemString)) {
				sqlSearch.add(bgItemString);
			}
		}
		try {
			if (!sqlSearch.isEmpty()) {
				sqlItemMap = getBudgetItemMap(ctx, sqlSearch);

				Iterator iter;
				if (!sqlItemMap.isEmpty()) {
					sqlItemSet = sqlItemMap.keySet();

					for (iter = sqlItemSet.iterator(); iter.hasNext();) {
						bgItemString = (String) iter.next();

						if (!reItemMap.containsKey(bgItemString)) {
							bgItemId = (String) sqlItemMap.get(bgItemString);
							reItemMap.put(bgItemString, bgItemId);
						}
					}
				}

				if (sqlSearch.size() != sqlItemMap.size()) {
					for (int i = 0, n = sqlSearch.size(); i < n; i++) {
						bgItemString = (String) sqlSearch.get(i);

						if (!sqlItemMap.containsKey(bgItemString.replaceAll(
								"_", ","))) {
							newCreate.add(bgItemString.replaceAll("_", ","));
						}
					}

					IBgTemplate ibgTemplate = BgTemplateFactory
							.getLocalInstance(ctx);
					if (ibgTemplate != null) {
						bgItemTab = ibgTemplate.getItemCombinIds(newCreate);
						Hashtable temp = new Hashtable();
						for (Iterator it = bgItemTab.keySet().iterator(); it
								.hasNext();) {
							String key = (String) it.next();
							Object obj = bgItemTab.get(key);
							temp.put(key.replaceAll(",", "_"), obj);
						}
						bgItemTab = temp;

						if ((bgItemTab != null) && (!bgItemTab.isEmpty())) {
							sqlItemSet = bgItemTab.keySet();
							for (iter = sqlItemSet.iterator(); iter.hasNext();) {
								bgItemString = (String) iter.next();

								if (!reItemMap.containsKey(bgItemString)) {
									bgItemId = (String) bgItemTab
											.get(bgItemString);
									reItemMap.put(bgItemString, bgItemId);
								}
							}
						}
					} else {
						throw new BgFormException(BgFormException.NOINSTANCE);
					}
				}
			}
		} catch (BOSException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (EASBizException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} finally {
			BgNSHelper.objClear(sqlItemSet);
			BgNSHelper.objClear(sqlItemMap);
			BgNSHelper.objClear(sqlSearch);
			BgNSHelper.objClear(newCreate);
		}

		return reItemMap;
	}
}
