/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
package com.kingdee.eas.fi.gl.app;

import com.kingdee.bos.*;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.gl.*;
import com.kingdee.eas.fi.gl.common.*;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.LowTimer;
import com.kingdee.util.db.SQLUtils;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

// Referenced classes of package com.kingdee.eas.fi.gl.app:
//            AbstractGLReportSubsidiaryLedgerTreeControllerBean, ReportPartner

public class ZDFGLReportSubsidiaryLedgerTreeControllerBean extends
		AbstractZDFGLReportSubsidiaryLedgerTreeControllerBean {

	public ZDFGLReportSubsidiaryLedgerTreeControllerBean() {
		AsstWhereSp = new SqlParams();
	}

	protected ReportResultInfo _findAccountTree(Context ctx,
			EntityViewInfo condition, CompanyOrgUnitInfo company)
			throws BOSException, EASBizException {
		LowTimer t = new LowTimer();
		t.reset();

		ReportPartner partner = new ReportPartner(ctx);
		partner.setCompany(company);
		partner.setCondition(condition);
		try {
			partner.setConnection(getConnection(ctx));
			ReportResultInfo result = new ReportResultInfo();

			result.setRowCount(0);

			result.getData().add(getAccountTree(partner));

			result.setTime(t.msValue());
			t.reset();

			ReportResultInfo localReportResultInfo1 = result;
			return localReportResultInfo1;
		} finally {
			SQLUtils.cleanup(partner.getConnection());
		}
	}

	protected ArrayList getAccountTree(ReportPartner partner)
			throws BOSException, EASBizException {
		Map typeDataMap = getAccountType(partner);
		Map accountDataMap = getAccountListByPeriod(partner);
		ArrayList list = new ArrayList();
		list.add(accountDataMap);
		list.add(typeDataMap);
		return list;
	}

	protected void addAccountToType(Map accountDataMap, Map typeInfoMap)
			throws EASBizException {
		Iterator iterator = accountDataMap.values().iterator();
		do {
			if (!iterator.hasNext())
				break;
			ReportTreeNodeExtendInfo nodeAccountInfo = (ReportTreeNodeExtendInfo) iterator
					.next();
			if (nodeAccountInfo.getParent() == null) {
				ReportTreeNodeExtendInfo nodeTypeInfo = null;
				if (nodeAccountInfo.getTypeId() != null)
					nodeTypeInfo = (ReportTreeNodeExtendInfo) typeInfoMap
							.get(nodeAccountInfo.getTypeId());
				if (nodeTypeInfo == null)
					throw new ReportException(ReportException.ACCOUNT_NO_TYPE);
				nodeTypeInfo.add(nodeAccountInfo, nodeTypeInfo.getChildCount());
				nodeTypeInfo.setItem(true);
			}
		} while (true);
	}

	protected ArrayList buildTypeInfoTree(Map typeDataMap)
			throws EASBizException {
		ArrayList rootList = new ArrayList();
		ReportTreeNodeExtendInfo rootExtend = new ReportTreeNodeExtendInfo();
		Map typeUseInfoMap = new HashMap();
		Iterator iterator = typeDataMap.values().iterator();
		do {
			if (!iterator.hasNext())
				break;
			ReportTreeNodeExtendInfo node = (ReportTreeNodeExtendInfo) iterator
					.next();
			if (node.isItem()) {
				node.setItem(false);
				while (node != null) {
					typeUseInfoMap.put(node.getId(), node);
					ReportTreeNodeExtendInfo parentNode = node.getParent();
					if (parentNode != null)
						parentNode.addSortedChildNode(node, 0);
					node = parentNode;
				}
			}
		} while (true);
		iterator = typeUseInfoMap.values().iterator();
		do {
			if (!iterator.hasNext())
				break;
			ReportTreeNodeExtendInfo node = (ReportTreeNodeExtendInfo) iterator
					.next();
			if (node.getParent() == null)
				rootExtend.addSortedChildNode(node, 0);
		} while (true);
		ReportTreeNodeInfo nextNode;
		for (ReportTreeNodeInfo node = rootExtend.getFirstChild(); node != null; node = nextNode) {
			rootList.add(node);
			nextNode = node.getNextBrother();
			node.setNextBrother(null);
		}

		return rootList;
	}

	protected Map getAccountType(ReportPartner partner)
        throws BOSException, EASBizException
    {
		Map map = new HashMap();

	    String sql = getAccountTypeSql(partner);

	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try
	    {
	      stmt = partner.getConnection().prepareStatement(sql);
	      stmt.setFetchSize(100);
	      SqlParameter2 parameter = getParametersAccountType(partner);

	      parameter.putToStatement(stmt);
	      rs = stmt.executeQuery();

	      while (rs.next()) {
	        ReportTreeNodeExtendInfo node = new ReportTreeNodeExtendInfo();
	        node.setId(rs.getString("FID"));
	        node.setNumber(rs.getString("FNumber"));
	        node.setName(rs.getString("FName"));
	        node.setItem(false);
	        node.setParentId(rs.getString("FParentID"));

	        map.put(node.getId(), node);
	      }
	    } catch (SQLException sqle) {
	      sqle.setNextException(new SQLException("The query sql is:" + sql));
	      throw new SQLDataException(sqle);
	    } finally {
	      SQLUtils.cleanup(rs, stmt);
	    }

	    for (Iterator iterator = map.values().iterator(); iterator.hasNext(); ) {
	      ReportTreeNodeExtendInfo node = (ReportTreeNodeExtendInfo)iterator.next();
	      if ((node.getParentId() != null) && (!node.getId().equals(node.getParentId()))) {
	        ReportTreeNodeExtendInfo parentNode = (ReportTreeNodeExtendInfo)map.get(node.getParentId());
	        if (parentNode != null) {
	          node.setParent(parentNode);
	        }
	      }
	    }

	    return map;
    }

	protected String getAccountTypeSql(ReportPartner partner)
			throws BOSException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tat.FID FID,");
		sql.append("tat.FNumber,");
		sql.append("tat.FName");
		sql.append(partner.getFieldNameExtend());
		sql.append(" FName,tat.FParentID \r\n");
		sql.append("FROM T_BD_AccountType tat \r\n");
		sql.append("WHERE tat.FAccountTableID = ? \r\n");
		sql.append("ORDER BY tat.FProperty ");
		return sql.toString();
	}

	protected SqlParameter2 getParametersAccountType(ReportPartner partner)
			throws BOSException, EASBizException {
		SqlParameter2 parameters = new SqlParameter2();
		parameters.addValueChar(partner.getAccountTableId());
		return parameters;
	}

	protected Map getAccountListByPeriod(ReportPartner partner)
        throws BOSException, EASBizException
    {
		SortedMap map = new TreeMap();
	    String sql = getAccountListSqlByPeriod(partner);
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	      stmt = partner.getConnection().prepareStatement(sql);
	      stmt.setFetchSize(100);
	      SqlParameter2 parameter = getParametersAccountListByPeriod(partner);

	      parameter.putToStatement(stmt);
	      rs = stmt.executeQuery();
	      while (rs.next()) {
	        ReportTreeNodeExtendInfo node = new ReportTreeNodeExtendInfo();
	        node.setId(rs.getString("FAccountID"));
	        node.setNumber(rs.getString("FNumber"));
	        node.setName(rs.getString("FName"));
	        node.setParentId(rs.getString("FParentID"));
	        node.setTypeId(rs.getString("FAccountTypeID"));
	        node.setItem(true);

	        map.put(node.getNumber() + " " + node.getId(), node);
	      }
	    } catch (SQLException ex) {
	      throw new BOSException(ex);
	    } finally {
	      SQLUtils.cleanup(rs, stmt);
	    }

	    for (Iterator iterator = map.values().iterator(); iterator.hasNext(); ) {
	      ReportTreeNodeExtendInfo node = (ReportTreeNodeExtendInfo)iterator.next();
	      if ((node.getParentId() != null) && (!node.getId().equals(node.getParentId()))) {
	        ReportTreeNodeExtendInfo parentNode = (ReportTreeNodeExtendInfo)map.get(node.getParentId());
	        if (parentNode != null) {
	          parentNode.addSortedChildNode(node, 0);
	        }
	      }
	    }

	    return map;
    }

	protected String getAccountListSqlByPeriod(ReportPartner partner)
			throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		boolean isShowLeafAccount = ((ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition()).getOptionOnlyLeaf();
		boolean isAsstBalanceTabel = condition.getOptionOnlyAsst()
				&& includeAssFilter(condition);
		String tableName = isAsstBalanceTabel ? "T_GL_AssistBalance"
				: "T_GL_AccountBalance";
		String fName = partner.isParmAccountShowLongName() ? "FLongName"
				: "FName";
		sql.append(" select PAV.FID FAccountID,PAV.FAccountTypeID,");
		sql.append(" PAV.FNumber FNumber, PAV.").append(fName);
		sql.append(partner.getFieldNameExtend());
		sql.append(" FName,PAV.FParentID \r\n");
		sql.append(" from t_bd_accountview AV \r\n");
		sql
				.append(" inner join T_BD_AccountView as PAV on PAV.Fcompanyid = AV.Fcompanyid ");
		sql
				.append(" and PAV.faccounttableid = AV.faccounttableid  and charindex(PAV.FLongNumber||'!',AV.FLongNumber||'!') = 1  \r\n");
		Context ctx = partner.getContext();
		String userID = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
		String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		sql.append("\n").append(
				RptServerUtil.innerJoinAccountPermissionSql("AV", userID,
						companyID, ctx));
		if (condition.getOptionAmountZero() || condition.getOptionNotUsed()
				&& !condition.getOptionBalanceAndAmountZero()
				|| !condition.getOptionNotUsed()
				&& condition.getOptionBalanceAndAmountZero()
				|| condition.getOptionNotUsed()
				&& condition.getOptionBalanceAndAmountZero()) {
			sql.append(" where exists (select * FROM ").append(tableName)
					.append(" TB \r\n");
			sql
					.append(" INNER JOIN T_BD_Period TP ON TB.FPeriodID = TP.FID \r\n");
			sql.append(" WHERE TP.FTypeID = ? \r\n");
			sql.append(" AND TP.FNumber>= ? AND TP.FNumber<= ? \r\n");
			if (condition.isAllCurrency())
				sql
						.append(" AND TB.FCurrencyID <> '11111111-1111-1111-1111-111111111111DEB58FDC' and TB.FCurrencyId <> '22222222-2222-2222-2222-222222222222DEB58FDC' \r\n");
			else
				sql.append(" AND TB.FCurrencyID = ?  \r\n");
			if (condition.getOptionAmountZero()
					|| !condition.getOptionNotUsed()
					&& condition.getOptionBalanceAndAmountZero()
					|| condition.getOptionNotUsed()
					&& condition.getOptionBalanceAndAmountZero()) {
				sql.append(" AND (TB.FDebitFor <> 0 OR ");
				sql.append(" TB.FCreditFor <> 0  OR ");
				sql.append(" TB.FDebitLocal <> 0 OR ");
				sql.append(" TB.FCreditLocal <> 0 OR ");
				sql.append(" TB.FDebitRpt <> 0 OR ");
				sql.append(" TB.FCreditRpt <> 0 ");
				if (!condition.getOptionNotUsed()
						&& condition.getOptionBalanceAndAmountZero()
						|| condition.getOptionNotUsed()
						&& condition.getOptionBalanceAndAmountZero()) {
					sql.append(" OR ");
					sql.append(" TB.FEndBalanceFor <> 0 OR \r\n");
					sql.append(" TB.FEndBalanceLocal <> 0 OR \r\n");
					sql.append(" TB.FEndBalanceRpt <> 0 \r\n");
				}
				sql.append(" ) \r\n");
			}
			sql.append(" and TB.FBalType = ? ");
			sql.append(" and TB.FOrgUnitID = ? \r\n");
			sql.append(" and TB.FAccountID = AV.FID ");
			if (condition.getOptionOnlyAsst()) {
				AsstWhereSp.clear();
				if (condition.getOptionAmountZero()
						|| condition.getOptionNotUsed()
						|| condition.getOptionBalanceAndAmountZero())
					try {
						sql.append(" and ");
						sql.append(RptServerUtil.GetAsstSql(ctx, condition
								.getTableData(),
								condition.getOptionAsstGroup(), true,
								"TB.FAssistGrpID", AsstWhereSp));
					} catch (ParseException e) {
						throw new BOSException(e);
					}
			}
			sql.append(") \r\n");
		} else {
			sql.append(" where 1=1 ");
		}
		sql.append(" and AV.Fcompanyid = ?  \r\n");
		sql.append(" and AV.FAccountTableID = ? \r\n");
		String anotherAlias = "PAV";
		if (condition.isInputCodeStart() || condition.isInputCodeEnd()) {
			sql.append(" and (");
			if (condition.isInputCodeStart() && condition.isInputCodeEnd()) {
				String starAcc[] = condition.getAccountCodeStart().split(",");
				String endAcc[] = condition.getAccountCodeEnd().split(",");
				sql.append(anotherAlias).append(".FNumber in ( '");
				sql.append(condition.getAccountCodeStart().replaceAll(",",
						"','"));
				sql.append("','");
				sql
						.append(condition.getAccountCodeEnd().replaceAll(",",
								"','"));
				sql.append("' ) ");
				sql.append(" or (").append(anotherAlias).append(".FNumber > '")
						.append(starAcc[starAcc.length - 1]).append("'")
						.append(" and ").append(anotherAlias).append(
								".FNumber < '").append(endAcc[0]);
				sql.append("~~");
				sql.append("')");
			} else if (condition.isInputCodeStart()) {
				String starAcc[] = condition.getAccountCodeStart().split(",");
				sql.append(anotherAlias).append(".FNumber in ( '");
				sql.append(condition.getAccountCodeStart().replaceAll(",",
						"','"));
				sql.append("' ) ");
//				sql.append(" or (").append(anotherAlias).append(".FNumber > '")
//						.append(starAcc[starAcc.length - 1]).append("')");
			} else if (condition.isInputCodeEnd()) {
				String endAcc[] = condition.getAccountCodeEnd().split(",");
				sql.append(anotherAlias).append(".FNumber in ( '");
				sql
						.append(condition.getAccountCodeEnd().replaceAll(",",
								"','"));
				sql.append("' ) ");
				sql.append(" or (").append(anotherAlias).append(".FNumber < '")
						.append(endAcc[0]);
				sql.append("~~");
				sql.append("')");
			}
			sql.append(" )  \r\n ");
		}
		if (condition.getOptionOnlyAsst() && !condition.getOptionAmountZero()
				&& !condition.getOptionNotUsed()
				&& !condition.getOptionBalanceAndAmountZero()) {
			sql.append(" and ");
			sql.append(getAssAccountSql(condition.getTableData(), "AV.FCAA"));
		}
		if (isShowLeafAccount)
			sql.append(" AND PAV.FIsLeaf = 1 ");
		else
			sql.append(" AND PAV.FLevel >= ? AND PAV.FLevel <= ? \r\n");
		String accountId = partner.getFixCondition().getAccountId();
		if (accountId != null && accountId.length() > 0)
			sql.append(" AND PAV.FID = ? ");
		sql.append(" ORDER BY PAV.FNumber ");
		return sql.toString();
	}

	protected SqlParameter2 getParametersAccountListByPeriod(
			ReportPartner partner) throws BOSException, EASBizException {
		ZDFReportConditionSubsidiaryLedger condition = (ZDFReportConditionSubsidiaryLedger) partner
				.getFixCondition();
		SqlParameter2 parameter = new SqlParameter2();
		Integer isPosted = new Integer(condition.getOptionPosting() ? 1 : 5);
		if (condition.getOptionAmountZero() || condition.getOptionNotUsed()
				&& !condition.getOptionBalanceAndAmountZero()
				|| !condition.getOptionNotUsed()
				&& condition.getOptionBalanceAndAmountZero()
				|| condition.getOptionNotUsed()
				&& condition.getOptionBalanceAndAmountZero()) {
			parameter.addValueId(partner.getCompany().getAccountPeriodType());
			if (condition.getOptionNotUsed()
					&& !condition.getOptionAmountZero()
					&& !condition.getOptionBalanceAndAmountZero()) {
				PeriodInfo currentPeriod = partner.getCurrentPeriod();
				if (condition.getPeriodYearStart() * 100
						+ condition.getPeriodNumberStart() > currentPeriod
						.getNumber())
					parameter.addValue(currentPeriod.getNumber());
				else
					parameter.addValue(new Integer(condition
							.getPeriodYearStart()
							* 100 + condition.getPeriodNumberStart()));
			} else {
				parameter.addValue(new Integer(condition.getPeriodYearStart()
						* 100 + condition.getPeriodNumberStart()));
			}
			parameter.addValue(new Integer(condition.getPeriodYearEnd() * 100
					+ condition.getPeriodNumberEnd()));
			if (!condition.isAllCurrency())
				parameter.addValueChar(condition.getCurrencyID());
			parameter.addValue(isPosted);
			parameter.addValueChar(partner.getCompanyId());
		}
		boolean CheckBal = condition.getOptionAmountZero()
				|| condition.getOptionNotUsed()
				|| condition.getOptionBalanceAndAmountZero();
		if (condition.getOptionOnlyAsst() && CheckBal) {
			Object Asst[] = AsstWhereSp.getParams();
			for (int i = 0; i < Asst.length; i++)
				parameter.addValueChar(Asst[i].toString());

		}
		parameter.addValueChar(partner.getCompany().getId().toString());
		parameter.addValueChar(partner.getAccountTableId());
		if (!((ZDFReportConditionSubsidiaryLedger) partner.getFixCondition())
				.getOptionOnlyLeaf()) {
			parameter.addValue(new Integer(partner.getFixCondition()
					.getAccountLevelStart()));
			parameter.addValue(new Integer(partner.getFixCondition()
					.getAccountLevelEnd()));
		}
		String accountId = partner.getFixCondition().getAccountId();
		if (accountId != null && accountId.length() > 0)
			parameter.addValueChar(accountId);
		return parameter;
	}

	protected String getAuthorityName() {
		return "SubsidiaryLedger";
	}

	protected String getQueryName() {
		return "com.kingdee.eas.fi.gl.app.GLReportSubsidiaryLedgerQuery";
	}

	protected String getAuthoritySql(ReportPartner partner, String authorityName)
			throws BOSException, EASBizException {
		String authoritySql = (String) partner.getVariable("AuthoritySql");
		return authoritySql;
	}

	protected String getAssAccountSql(List list, String fieldString) {
		if (list == null)
			list = new ArrayList();
		int ActCount = 0;
		StringBuffer ActSql = new StringBuffer();
		int i = 0;
		for (int size = list.size(); i < size; i++) {
			AsstactTypeEntity at = (AsstactTypeEntity) list.get(i);
			boolean isSelect = at.isSelected();
			if (isSelect) {
				ActSql.append((ActCount <= 0 ? "" : ",") + "'" + at.getId()
						+ "'");
				ActCount++;
			}
		}

		StringBuffer sql = new StringBuffer();
		if (ActCount > 0) {
			sql.append(fieldString + " in ( \r\n");
			sql.append("select FAsstAccountID \r\n");
			sql.append(" from T_BD_AsstActGroupDetail \r\n");
			sql.append(" where FAsstActTypeID in (");
			sql.append(ActSql.toString());
			sql.append(") \r\n");
			sql.append("group by FAsstAccountID \r\n");
			sql.append("having count(*) >= " + ActCount + ") \r\n");
		} else {
			sql.append("1=1");
		}
		return sql.toString();
	}

	private boolean includeAssFilter(ZDFReportConditionSubsidiaryLedger condition) {
		boolean isIncludeAssFilter = false;
		List list = condition.getTableData();
		int i = 0;
		int size = list.size();
		do {
			if (i >= size)
				break;
			AsstactTypeEntity at = (AsstactTypeEntity) list.get(i);
			if (at.isSelected()) {
				isIncludeAssFilter = true;
				break;
			}
			i++;
		} while (true);
		return isIncludeAssFilter;
	}

	private static final long serialVersionUID = -3352628329641055380L;
	private SqlParams AsstWhereSp;
}