package com.kingdee.eas.ma.crbg.database.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGFactory;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.IAssistantHG;
import com.kingdee.eas.basedata.master.material.app.DataImportTools;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnit;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.gl.EntryDC;
import com.kingdee.eas.fi.gl.GlWebServiceUtil;
import com.kingdee.eas.fi.gl.InitImportBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillFactory;
import com.kingdee.eas.ma.crbg.database.BeginHiDetailBillInfo;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class BeginHiDetailImportDataProsess extends InitImportBase {
	private static final Logger logger = Logger
			.getLogger(BeginHiDetailImportDataProsess.class);
	private int fPK;
	private Map infomap = new HashMap();
	private String actualAssistantHGInfoID;

	public BeginHiDetailImportDataProsess() {
		actualAssistantHGInfoID = null;
	}

	@Override
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		// TODO Auto-generated method stub
		try {
			return BeginHiDetailBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			throw new TaskExternalException(getType(), e);
		}
	}

	// ������ȡ��ʱ�������ݱ��е�����
	private IRowSet getBeginImp(int beginkey, int endkey, Context ctx) {
		IRowSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select fPK,FSeqNo,fcompanyNumber,FbizDate,");
		sql.append("fbookedDate,fcurrencyNumber,");
		sql.append("fasstActTypeName,faccountNumber,fvoucherAbstract,");
		sql.append("fasstActType1,fasstActNumber1,fasstActName1,");
		sql.append("fasstActType2,fasstActNumber2,fasstActName2,");
		sql.append("fasstActType3,fasstActNumber3,fasstActName3,");
		sql.append("fasstActType4,fasstActNumber4,fasstActName4,");
		sql.append("fasstActType5,fasstActNumber5,fasstActName5,");
		sql.append("fasstActType6,fasstActNumber6,fasstActName6,");
		sql.append("fasstActType7,fasstActNumber7,fasstActName7,");
		sql.append("fasstActType8,fasstActNumber8,fasstActName8,");
		sql.append("foriginalAmount,flocalAmount,freportAmount,");
		sql
				.append("fentryDCName,fentryDCName,fisfinish,fmessage,fmemo from CTF_IMP_BeginHiDetailBill  ");
		sql.append("where fPK between " + beginkey + " and " + endkey
				+ " and fisfinish = '0' order by fPK ASC ");
		try {
			rs = DbUtil.executeQuery(ctx, sql.toString());
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kingdee.eas.tools.datatask.AbstractDataTransmission#transmit(java
	 * .util.Hashtable, com.kingdee.bos.Context)
	 */

	/*
	 * ����У��ʹ������
	 */
	public void transmit(Context ctx, int beginkey, int endkey)
			throws TaskExternalException, SQLException {
		// TODO Auto-generated method stub
		String str = null;
		int rowcount;
		IRowSet rs = getBeginImp(beginkey, endkey, ctx);
		rowcount = rs.size();
		String mapKey;

		if (rs != null) {
			try {
				while (rs.next()) {
					BeginHiDetailBillInfo info = new BeginHiDetailBillInfo();
					// ������֯

					fPK = rs.getInt("fPK");
					if (rs.getString("fcompanyNumber") != null
							&& rs.getString("fcompanyNumber").trim().length() != 0) {
						CompanyOrgUnitCollection orgCollection = null;
						try {
							EntityViewInfo classViewInfo = new EntityViewInfo();
							FilterItemInfo filterItem = new FilterItemInfo(
									"number", rs.getString("fcompanyNumber"),
									CompareType.EQUALS);
							FilterInfo filterInfo = new FilterInfo();
							filterInfo.getFilterItems().add(filterItem);
							classViewInfo.setFilter(filterInfo);
							orgCollection = CompanyOrgUnitFactory
									.getLocalInstance(ctx)
									.getCompanyOrgUnitCollection(classViewInfo);
						} catch (BOSException e) {
							logger.error(e);
						}
						if (orgCollection != null && (orgCollection.size() > 0)) {
							info.setCompanyOrg(orgCollection.get(0));
						} else {
							// ����ʱ���в��������Ϣ������
							// mapKey = rs.getInt("fPK")+"fcompanyNumber";
							str = rs.getString("fmemo") + "��"
									+ rs.getString("FSeqNo")
									+ "��fcompanyNumberû�����Ӧ������";
							// strMap.put(mapKey, str);
							updateFisfinish(str, fPK, ctx);

						}

					} else {
						// ����Ϊ��
					}
					// ҵ������
					if (rs.getDate("FbizDate") != null) {
						info.setBizDate(rs.getDate("FbizDate"));

					}

					// ��������

					if (rs.getDate("fbookedDate") != null) {
						info.setDateOfEntry(rs.getDate("fbookedDate"));
					}

					// �ұ�
					if (rs.getString("fcurrencyNumber") != null
							&& rs.getString("fcurrencyNumber").trim().length() != 0) {
						CurrencyCollection currencycoll = null;

						try {
							EntityViewInfo classViewInfo = new EntityViewInfo();
							FilterItemInfo filterItem = new FilterItemInfo(
									"number", rs.getString("fcurrencyNumber"),
									CompareType.EQUALS);
							FilterInfo filterInfo = new FilterInfo();
							filterInfo.getFilterItems().add(filterItem);
							classViewInfo.setFilter(filterInfo);
							currencycoll = CurrencyFactory
									.getLocalInstance(ctx)
									.getCurrencyCollection(classViewInfo);
						} catch (BOSException e) {
							logger.error(e);
						}
						if (currencycoll != null && (currencycoll.size() > 0)) {
							info.setFcurrency(currencycoll.get(0));
						} else {
							// �����ݿ��в��뱨�����ݺ�ԭ��
							// mapKey = fPK+"fcurrencyNumber";
							str = rs.getString("fmemo") + "��"
									+ rs.getString("FSeqNo")
									+ "��fcurrencyNumberû�����Ӧ������";
							updateFisfinish(str, fPK, ctx);
						}

					} else {
						// ����Ϊ��
					}

					// ����������
					if (rs.getString("fasstActTypeName") != null
							&& rs.getString("fasstActTypeName").trim().length() != 0) {
						AsstAccountCollection asstcoll = null;
						try {
							EntityViewInfo classViewInfo = new EntityViewInfo();
							FilterItemInfo filterItem = new FilterItemInfo(
									"number", rs.getString("fasstActTypeName"),
									CompareType.EQUALS);
							FilterInfo filterInfo = new FilterInfo();
							filterInfo.getFilterItems().add(filterItem);
							classViewInfo.setFilter(filterInfo);
							asstcoll = AsstAccountFactory.getLocalInstance(ctx)
									.getAsstAccountCollection(classViewInfo);
						} catch (BOSException e) {
							logger.error(e);
						}
						if (asstcoll != null && asstcoll.size() > 0) {
							info.setFCAA(asstcoll.get(0));
						} else {
							// �����ݿ��в���������ݺ�ԭ��
							// mapKey = rs.getInt("fPK")+"fasstActTypeName";
							str = rs.getString("fmemo") + "��"
									+ rs.getString("FSeqNo")
									+ "��fcurrencyNumberû�����Ӧ������";
							updateFisfinish(str, fPK, ctx);
						}
					}

					// ��Ŀ
					if (rs.getString("faccountNumber") != null
							&& rs.getString("faccountNumber").trim().length() != 0) {
						AccountViewCollection viewcoll = null;
						try {
							EntityViewInfo classViewInfo = new EntityViewInfo();
							FilterItemInfo filterItem = new FilterItemInfo(
									"number", rs.getString("faccountNumber"),
									CompareType.EQUALS);
							FilterInfo filterInfo = new FilterInfo();
							filterInfo.getFilterItems().add(filterItem);
							classViewInfo.setFilter(filterInfo);
							viewcoll = AccountViewFactory.getLocalInstance(ctx)
									.getAccountViewCollection(classViewInfo);
						} catch (BOSException e) {
							logger.error(e);
						}
						if (viewcoll != null && viewcoll.size() != 0) {
							info.setFAccountID(viewcoll.get(0));
						} else {
							// ����������ݺ�ԭ��
							// mapKey = rs.getInt("fPK")+"faccountNumber";
							str = rs.getString("fmemo") + "��"
									+ rs.getString("FSeqNo")
									+ "faccountNumberû�����Ӧ������";
							updateFisfinish(str, fPK, ctx);
						}
					} else {
						// ����Ϊ��
					}

					// ժҪ
					if (rs.getString("fvoucherAbstract") != null
							&& (rs.getString("fvoucherAbstract").trim()
									.length()) != 0) {
						info.setContents(rs.getString("fvoucherAbstract"));
					}
					// ԭ�ҽ��
					if ((rs.getBigDecimal("foriginalAmount") != null)) {
						info.setOriginalCurrency(rs
								.getBigDecimal("foriginalAmount"));
					}

					// ��λ�ҽ��
					if (rs.getBigDecimal("flocalAmount") != null) {
						info
								.setCurrencyAmount(rs
										.getBigDecimal("flocalAmount"));
					}

					// ����ҽ��
					if (rs.getBigDecimal("freportAmount") != null) {

						info.setReportingCurrency(rs
								.getBigDecimal("freportAmount"));

					}

					if ((rs.getString("fentryDCName") != null)
							&& (rs.getString("fentryDCName").trim().length()) != 0) {
						if (rs.getString("fentryDCName").trim().equals("��")) {
							info.setBorrowingDirection(EntryDC.DEBIT);
						} else if (rs.getString("fentryDCName").trim().equals(
								"��")) {
							info.setBorrowingDirection(EntryDC.CREDIT);
						} else {
							// ���ݴ��������ݿ���������Ϣ
							// mapKey = rs.getInt("fPK")+"fentryDCName";
							str = rs.getString("fmemo") + "��"
									+ rs.getString("FSeqNo")
									+ "fentryDCNameֻ��Ϊ����";
							updateFisfinish(str, fPK, ctx);
						}

					}

					// ������Ŀ

					String FID = info.getFCAA().getId().toString();
					String sqltoacctaccount = "select c.Fname_"
							+ ctx.getLocale()
							+ "  Fname ,a.FNumber fcaaNumber  from T_BD_AsstAccount a  inner join T_BD_AsstActGroupDetail b on a.fid=b.fasstaccountid  inner join  t_bd_asstacttype c on b.fasstacttypeid=c.fid where a.FID=? order by c.fnumber";
					IRowSet rsToGetAcctname = DbUtil.executeQuery(ctx,
							sqltoacctaccount, new Object[] { FID });
					if (rsToGetAcctname != null) {
						int siz = rsToGetAcctname.size();
						String[] asstactTypeName = new String[siz];
						String[] asstactTypeNumber = new String[siz];
						String[] asstactType = new String[siz];
						for (int iop = 0; iop < siz; ++iop) {
							rsToGetAcctname.next();
							asstactType[iop] = rsToGetAcctname
									.getString("Fname");
						}
						for (int i = 0; i < siz; ++i) {
							int ii = 0;
							for (int counts = 8; ii < counts; ++ii) {
								String Fitem = (rs.getString("fasstActType"
										+ String.valueOf(ii + 1)));
								if ((!(Fitem.equals(asstactType[i])))
										|| ((rs.getString("fasstActNumber"
												+ String.valueOf(ii + 1))) == null)
										|| ((rs.getString("fasstActName"
												+ String.valueOf(ii + 1))) == null))
									continue;
								asstactTypeNumber[i] = (rs
										.getString("fasstActNumber"
												+ String.valueOf(ii + 1)));
								asstactTypeName[i] = (rs
										.getString("fasstActName"
												+ String.valueOf(ii + 1)));
								break;
							}
							if (asstactTypeNumber[i] == null) {
								// throw new Exception(ResourceBase.getString(
								// "com.kingdee.eas.fi.gl.GLAutoGenerateResource"
								// , "1016_InitImportBase", ctx.getLocale()) +
								// (i + 1) + ResourceBase.getString(
								// "com.kingdee.eas.fi.gl.GLAutoGenerateResource"
								// , "1017_InitImportBase", ctx.getLocale()));
								// mapKey = rs.getInt("fPK")+"faccountNumber";
								str = rs.getString("fmemo")
										+ "��"
										+ rs.getString("FSeqNo")
										+ ResourceBase
												.getString(
														"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
														"1016_InitImportBase",
														ctx.getLocale())
										+ (i + 1)
										+ ResourceBase
												.getString(
														"com.kingdee.eas.fi.gl.GLAutoGenerateResource",
														"1017_InitImportBase",
														ctx.getLocale());
								updateFisfinish(str, fPK, ctx);
							}
						}
						CostCenterOrgUnitInfo unitinfo = null;
						AssistantHGInfo assistantHGInfo = new AssistantHGInfo();
						AsstActTypeCollection arrayAsstActTypeInfo = new AsstActTypeCollection();
						int count = asstactTypeName.length;
						for (int i = 0; i < count; ++i) {
							if (asstactType[i].trim().equals("�ɱ�����")) {
								unitinfo = getCostOrgUnitId(asstactTypeName[i],
										asstactTypeNumber[i], ctx);
								AsstActTypeInfo asstActTypeInfo = GlWebServiceUtil
										.findAsstActtypeNumberByName(ctx,
												asstactType[i]);
								if (asstActTypeInfo != null) {
									arrayAsstActTypeInfo.add(asstActTypeInfo);
								} else {
									str = rs.getString("fmemo") + "��"
											+ rs.getString("FSeqNo") + "������Ŀ"
											+ asstactTypeNumber[i] + "�������������";
									updateFisfinish(str, fPK, ctx);
								}
							} else {
								constructAssistInfo(ctx, assistantHGInfo,
										asstactType[i], asstactTypeNumber[i],
										asstactTypeName[i],
										arrayAsstActTypeInfo);
							}
						}
//						IAssistantHG igCtrl = AssistantHGFactory
//								.getLocalInstance(ctx);
//						AssistantHGInfo actualAssistantHGInfo = igCtrl
//								.getAssistantHG(assistantHGInfo, FID,
//										arrayAsstActTypeInfo);
//						this.actualAssistantHGInfoID = actualAssistantHGInfo
//								.getId().toString();
//						info.setAssistGrp(actualAssistantHGInfo);
					}
					if (str == null) {
						infomap.put(rs.getInt("fPK"), info);
						updateFisfinish(str, rs.getInt("fPK"), ctx);
					}
				}
				getInfo(infomap, rs, ctx);
			} catch (Exception e) {
				logger.error(e);
				throw new TaskExternalException(e.getMessage(), e);
			}
		}
	}

	private String getDataString(Hashtable hsData, String fieldName) {
		return DataImportTools.getDataString(hsData, fieldName);
	}

	@Override
	protected String getType() {
		// TODO Auto-generated method stub
		return "BeginHiDetailBill";
	}

	private void getInfo(Map map, IRowSet rs, Context ctx) throws SQLException {
		try {
			if (infomap.size() != 0) {
				BeginHiDetailBillInfo info = new BeginHiDetailBillInfo();
				Set<Map.Entry<String, BeginHiDetailBillInfo>> set = map
						.entrySet();
				for (Iterator<Map.Entry<String, BeginHiDetailBillInfo>> it = set
						.iterator(); it.hasNext();) {
					Map.Entry<String, BeginHiDetailBillInfo> entry = (Map.Entry<String, BeginHiDetailBillInfo>) it
							.next();
					info = entry.getValue();
					submit(info, ctx);
				}
				infomap.clear();

			}

		} catch (TaskExternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateFisfinish(String messge, int fPK, Context ctx) {
		String sql;
		if (messge == null) {
			sql = "update CTF_IMP_BeginHiDetailBill set fisfinish =1 where fPK = "
					+ fPK;
		} else {
			sql = "update CTF_IMP_BeginHiDetailBill set fisfinish =2, fmessage = '"
					+ messge + "' where fPk = " + fPK;
		}
		try {
			DbUtil.execute(ctx, sql);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private CostCenterOrgUnitInfo getCostOrgUnitId(String name, String number,
			Context ctx) throws BOSException {
		String id = null;
		CostCenterOrgUnitCollection coll = null;
		CostCenterOrgUnitInfo info = null;

		EntityViewInfo classViewInfo = new EntityViewInfo();
		FilterItemInfo filterItem = new FilterItemInfo("number", number,
				CompareType.EQUALS);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(filterItem);
		classViewInfo.setFilter(filterInfo);
		coll = CostCenterOrgUnitFactory.getLocalInstance(ctx)
				.getCostCenterOrgUnitCollection(classViewInfo);
		if (coll.size() > 0 && coll != null) {
			id = coll.get(0).getId().toString();

			try {
				info = CostCenterOrgUnitFactory.getLocalInstance(ctx)
						.getCostCenterOrgUnitInfo(new ObjectUuidPK(id));
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return info;
	}

	@Override
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		super.submit(coreBaseInfo, ctx);
	}

}
