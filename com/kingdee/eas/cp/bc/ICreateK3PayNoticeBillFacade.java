package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface ICreateK3PayNoticeBillFacade extends IBizCtrl
{
    public void createByDailyLoanBill(BOSUuid billid) throws BOSException, EASBizException;
    public void createByBizAccountBill(BOSUuid billId) throws BOSException, EASBizException;
    public void createByEvectionLoanBill(BOSUuid billId) throws BOSException, EASBizException;
    public void createByTraveAccountBill(BOSUuid billId) throws BOSException, EASBizException;
    public void backRun() throws BOSException, EASBizException;
    public void checkBillHasCreate(BOSUuid id) throws BOSException, EASBizException;
    public boolean checkBillHasCreate2(BOSUuid id) throws BOSException, EASBizException;
}