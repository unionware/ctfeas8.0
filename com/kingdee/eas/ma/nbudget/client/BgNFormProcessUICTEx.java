package com.kingdee.eas.ma.nbudget.client;


import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.CellBlock;
import com.kingdee.bos.ctrl.excel.model.struct.Range;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.IAccountView;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gl.client.ZDFReportSubsidiaryLedgerUI;
import com.kingdee.eas.fi.rpt.excelformula.ExcelFormulaPaserHelper;
import com.kingdee.eas.fi.rpt.excelformula.expr.Expression;
import com.kingdee.eas.fi.rpt.excelformula.expr.FunctionExpr;
import com.kingdee.eas.ma.budget.BgFormInfo;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgMessageClientHelper;
import com.kingdee.eas.ma.budget.BgSHelper;
import com.kingdee.eas.ma.budget.client.BgItemHelpUI;
import com.kingdee.eas.ma.nbudget.BgNFSHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class BgNFormProcessUICTEx extends BgNFormProcessUI {

	public BgNFormProcessUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.btnHelped.setEnabled(true);
		this.btnHelped.setIcon(EASResource.getIcon("imgTbtn_help"));
		this.btnCheckSubsidiaryLedger.setEnabled(true);
		this.btnCheckSubsidiaryLedger.setIcon(EASResource.getIcon("imgTbtn_assetchangeinfo"));

	}
	
	@Override
	public void actionHelped_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
    	
    	Object value = null;
    	String formula = null;
    	CellBlock _block = null;
    	Cell _cell = null; 
    	
    	Sheet sheet = getBook().getActiveSheet();
    	Range range = sheet.getSelectionRange();
    	
    	int rowCount = sheet.getMaxRowIndex();
    	int colCount = sheet.getMaxColIndex();
    	
    	for (int i = 0; i < range.size(); i++) {
			_block = range.getBlock(i);
			
			int rowBegin = _block.getRow();
    		int rowEnd = (_block.getRow2() > rowCount) ? rowCount : _block.getRow2();
    		int colBegin = _block.getCol();
			int colEnd = (_block.getCol2() > colCount) ? colCount : _block.getCol2();
			
			for (; rowBegin <= rowEnd; rowBegin++) {
				for (; colBegin <= colEnd; colBegin++) {
					_cell = sheet.getCell(rowBegin, colBegin, false);
					
					if (_cell == null) {
						continue;
					}
					
					formula = BgNFSHelper.getFormulaOfCell(_cell);
					if(!StringUtils.isEmpty(formula)){
						value = BgNFSHelper.getValue(_cell);
						break;
					}
				}
		
			}
    	}
    	
    	if(!StringUtils.isEmpty(formula)){
    		try{
    			value = new BigDecimal(value.toString());
    		}catch(Exception ex){
    			value = null;
    		}
    		if(value == null){
    			MsgBox.showInfo(BgMessageClientHelper.getMessage("please.choose.itemformula.have.value"));
    			SysUtil.abort();
    		}
    		
    		BgFormInfo form = getBgFormInfo(); 
        	form.setBook(getBook());
    		List list = new ArrayList();
    		Expression exp;
            try {
                exp = ExcelFormulaPaserHelper.parse(formula.substring(1));
            } catch (Exception e1) {
                return ; //解析错误
            };
            String[] valueString = null;
            List params = null;
            if(exp instanceof FunctionExpr){
                FunctionExpr foo = (FunctionExpr)exp;
                params = foo.parameters;
                valueString = new String[params.size()];
                for (int i = 0; i < params.size(); i++) {
                    valueString[i] = params.get(i).toString().replaceAll("\"","");
                }
            } else {
                return ;
            }
            
            String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
            orgUnitId = BgSHelper.getIsolateOrg(orgUnitId);
            String itemcombin = valueString[0].toString();
            String[] item = itemcombin.split(",");
            String sql = "select id,name,number,Help ";
            for(int i=0,length=item.length;i<length;i++){
            	if(i==0){
            		sql += " where number = '" + item[i] + "'";
            	}else{
            		sql += " or number = '" + item[i] + "'";
            	}
            }
            sql += " and orgUnit.id = '"+orgUnitId+"'";
            
            BgItemCollection itemCol = BgItemFactory.getRemoteInstance().getBgItemCollection(sql);
            String help = null;
            for(int i=0;i<itemCol.size();i++){
            	if(itemCol.get(i).getHelp()==null || "".equals(itemCol.get(i).getHelp())){
            		continue;
            	}
            	if(help==null){
            		help = "预算项目编码："+itemCol.get(i).getNumber()+"预算项目名称："+itemCol.get(i).getName()+"\n"+itemCol.get(i).getHelp();
            	}else{
            		help += "\n预算项目编码："+itemCol.get(i).getNumber()+"预算项目名称："+itemCol.get(i).getName()+"\n"+itemCol.get(i).getHelp();
            	}
            }
            
        	if(help==null || "".equals(help)){
        		MsgBox.showInfo("不存在帮助信息");
        	}else{
        		uiContext.put("porjectHelp", help);
        		uiContext.put(UIContext.ID, itemCol.get(0).getId().toString());
        		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BgItemHelpUI.class.getName(),uiContext,null,OprtState.VIEW);
        		window.show();
        	}
            
    	}else {
    		MsgBox.showInfo("请选择需要查看帮助的预算项目");
    	}
	}
	
	
	@Override
	public void actionCheckSubsidiaryLedger_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		Object value = null;
		Cell _cell = null;

		Sheet sheet = getBook().getActiveSheet();
		_cell =sheet.getCell(sheet.getActiveRow(), sheet.getActiveCol(), false);
		value = BgNFSHelper.getValue(_cell);
		
		IAccountView accountView = AccountViewFactory.getRemoteInstance();
		Object[] objAccs = null;
		String[] strAccs = null;
		String str =null;
		int size = 0;
		EntityViewInfo view = null;
		FilterInfo filter = null;
		AccountViewInfo accViewInfo = null;
		AccountViewCollection accViewCol = null;
		if(value!=null){
			strAccs = value.toString().split(";");
			objAccs = new Object[strAccs.length];
			for (int i =0;i<strAccs.length;i++) {
				view = new EntityViewInfo();
				filter = new FilterInfo();
				str = strAccs[i];
				if(!StringUtils.isEmpty(str)){
					filter.getFilterItems().add(new FilterItemInfo("number",str));
					view.setFilter(filter);
					accViewCol = accountView.getAccountViewCollection(view);
					if (accViewCol!=null && accViewCol.size()>0) {
						accViewInfo = accViewCol.get(0);
						objAccs[size] = accViewInfo;
						size++;
					}
				}
			}
			uiContext.put("accountViews", objAccs);
		}
		
		IUIWindow window = UIFactory.createUIFactory(
				UIFactoryName.MODEL).create(
				ZDFReportSubsidiaryLedgerUI.class.getName(), uiContext, null,
				OprtState.VIEW);
		window.show();
	}
	
}
