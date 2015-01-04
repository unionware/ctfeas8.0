package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface IBc2K3VoucherFacade extends IBizCtrl
{
    public void crVouchByBizAccount(String billID) throws BOSException, EASBizException;
    public void crVouchByDailyLoan(String billID) throws BOSException, EASBizException;
    public void crVouchByEvectionLoan(String billID) throws BOSException, EASBizException;
    public void crVouchByTraveAccount(String billID) throws BOSException, EASBizException;
    public void backRun() throws BOSException, EASBizException;
}