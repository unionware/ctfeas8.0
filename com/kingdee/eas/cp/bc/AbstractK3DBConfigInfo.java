package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractK3DBConfigInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractK3DBConfigInfo()
    {
        this("id");
    }
    protected AbstractK3DBConfigInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:K3���ݿ�����'s IPproperty 
     */
    public String getIp()
    {
        return getString("ip");
    }
    public void setIp(String item)
    {
        setString("ip", item);
    }
    /**
     * Object:K3���ݿ�����'s PORTproperty 
     */
    public String getPort()
    {
        return getString("port");
    }
    public void setPort(String item)
    {
        setString("port", item);
    }
    /**
     * Object:K3���ݿ�����'s ��¼�û���property 
     */
    public String getUsername()
    {
        return getString("username");
    }
    public void setUsername(String item)
    {
        setString("username", item);
    }
    /**
     * Object:K3���ݿ�����'s ����property 
     */
    public String getPwd()
    {
        return getString("pwd");
    }
    public void setPwd(String item)
    {
        setString("pwd", item);
    }
    /**
     * Object:K3���ݿ�����'s ���ݿ���property 
     */
    public String getDbname()
    {
        return getString("dbname");
    }
    public void setDbname(String item)
    {
        setString("dbname", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC100566");
    }
}