package com.kingdee.eas.basedata.person;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPersonInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPersonInfo()
    {
        this("id");
    }
    protected AbstractPersonInfo(String pkField)
    {
        super(pkField);
        put("bankInfo", new com.kingdee.eas.basedata.person.BankInfoCollection());
    }
    /**
     * Object:员工_个人信息's 性别property 
     */
    public com.kingdee.eas.basedata.person.Genders getGender()
    {
        return com.kingdee.eas.basedata.person.Genders.getEnum(getInt("gender"));
    }
    public void setGender(com.kingdee.eas.basedata.person.Genders item)
    {
		if (item != null) {
        setInt("gender", item.getValue());
		}
    }
    /**
     * Object:员工_个人信息's 出生日期property 
     */
    public java.util.Date getBirthday()
    {
        return getDate("birthday");
    }
    public void setBirthday(java.util.Date item)
    {
        setDate("birthday", item);
    }
    /**
     * Object:员工_个人信息's 电子邮件property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:员工_个人信息's 通信地址property 
     */
    public String getAddressTX()
    {
        return getAddressTX((Locale)null);
    }
    public void setAddressTX(String item)
    {
		setAddressTX(item,(Locale)null);
    }
    public String getAddressTX(Locale local)
    {
        return TypeConversionUtils.objToString(get("addressTX", local));
    }
    public void setAddressTX(String item, Locale local)
    {
        put("addressTX", item, local);
    }
    /**
     * Object:员工_个人信息's 审核状态property 
     */
    public com.kingdee.eas.basedata.person.PersonStates getState()
    {
        return com.kingdee.eas.basedata.person.PersonStates.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.basedata.person.PersonStates item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    /**
     * Object:员工_个人信息's 家庭电话property 
     */
    public String getHomePhone()
    {
        return getString("homePhone");
    }
    public void setHomePhone(String item)
    {
        setString("homePhone", item);
    }
    /**
     * Object:员工_个人信息's 办公室电话property 
     */
    public String getOfficePhone()
    {
        return getString("officePhone");
    }
    public void setOfficePhone(String item)
    {
        setString("officePhone", item);
    }
    /**
     * Object:员工_个人信息's 手机号码property 
     */
    public String getCell()
    {
        return getString("cell");
    }
    public void setCell(String item)
    {
        setString("cell", item);
    }
    /**
     * Object:员工_个人信息's 备用手机号码property 
     */
    public String getBackupCell()
    {
        return getString("backupCell");
    }
    public void setBackupCell(String item)
    {
        setString("backupCell", item);
    }
    /**
     * Object:员工_个人信息's 备用电子邮件 property 
     */
    public String getBackupEMail()
    {
        return getString("backupEMail");
    }
    public void setBackupEMail(String item)
    {
        setString("backupEMail", item);
    }
    /**
     * Object:员工_个人信息's RTX号码property 
     */
    public String getRtx()
    {
        return getString("rtx");
    }
    public void setRtx(String item)
    {
        setString("rtx", item);
    }
    /**
     * Object:员工_个人信息's 身份证号码property 
     */
    public String getIdCardNO()
    {
        return getString("idCardNO");
    }
    public void setIdCardNO(String item)
    {
        setString("idCardNO", item);
    }
    /**
     * Object:员工_个人信息's 护照号码property 
     */
    public String getPassportNO()
    {
        return getString("passportNO");
    }
    public void setPassportNO(String item)
    {
        setString("passportNO", item);
    }
    /**
     * Object:员工_个人信息's 曾用名property 
     */
    public String getOldName()
    {
        return getOldName((Locale)null);
    }
    public void setOldName(String item)
    {
		setOldName(item,(Locale)null);
    }
    public String getOldName(Locale local)
    {
        return TypeConversionUtils.objToString(get("oldName", local));
    }
    public void setOldName(String item, Locale local)
    {
        put("oldName", item, local);
    }
    /**
     * Object:员工_个人信息's 身高property 
     */
    public int getHeight()
    {
        return getInt("height");
    }
    public void setHeight(int item)
    {
        setInt("height", item);
    }
    /**
     * Object: 员工_个人信息 's 国籍 property 
     */
    public com.kingdee.eas.basedata.hraux.NationalityInfo getNationality()
    {
        return (com.kingdee.eas.basedata.hraux.NationalityInfo)get("nationality");
    }
    public void setNationality(com.kingdee.eas.basedata.hraux.NationalityInfo item)
    {
        put("nationality", item);
    }
    /**
     * Object:员工_个人信息's 籍贯property 
     */
    public String getNativePlace()
    {
        return getNativePlace((Locale)null);
    }
    public void setNativePlace(String item)
    {
		setNativePlace(item,(Locale)null);
    }
    public String getNativePlace(Locale local)
    {
        return TypeConversionUtils.objToString(get("nativePlace", local));
    }
    public void setNativePlace(String item, Locale local)
    {
        put("nativePlace", item, local);
    }
    /**
     * Object:员工_个人信息's 血型property 
     */
    public com.kingdee.eas.basedata.person.BloodType getBloodType()
    {
        return com.kingdee.eas.basedata.person.BloodType.getEnum(getInt("bloodType"));
    }
    public void setBloodType(com.kingdee.eas.basedata.person.BloodType item)
    {
		if (item != null) {
        setInt("bloodType", item.getValue());
		}
    }
    /**
     * Object:员工_个人信息's 实际工龄（已废弃）property 
     */
    public int getLenOfActualService()
    {
        return getInt("lenOfActualService");
    }
    public void setLenOfActualService(int item)
    {
        setInt("lenOfActualService", item);
    }
    /**
     * Object: 员工_个人信息 's 记帐分类 property 
     */
    public com.kingdee.eas.basedata.assistant.KAClassficationInfo getKaClassfication()
    {
        return (com.kingdee.eas.basedata.assistant.KAClassficationInfo)get("kaClassfication");
    }
    public void setKaClassfication(com.kingdee.eas.basedata.assistant.KAClassficationInfo item)
    {
        put("kaClassfication", item);
    }
    /**
     * Object: 员工_个人信息 's 婚姻状况 property 
     */
    public com.kingdee.eas.basedata.hraux.WedInfo getWed()
    {
        return (com.kingdee.eas.basedata.hraux.WedInfo)get("wed");
    }
    public void setWed(com.kingdee.eas.basedata.hraux.WedInfo item)
    {
        put("wed", item);
    }
    /**
     * Object: 员工_个人信息 's 健康状况 property 
     */
    public com.kingdee.eas.basedata.hraux.HealthInfo getHealth()
    {
        return (com.kingdee.eas.basedata.hraux.HealthInfo)get("health");
    }
    public void setHealth(com.kingdee.eas.basedata.hraux.HealthInfo item)
    {
        put("health", item);
    }
    /**
     * Object: 员工_个人信息 's 政治面貌 property 
     */
    public com.kingdee.eas.basedata.hraux.PoliticalFaceInfo getPoliticalFace()
    {
        return (com.kingdee.eas.basedata.hraux.PoliticalFaceInfo)get("politicalFace");
    }
    public void setPoliticalFace(com.kingdee.eas.basedata.hraux.PoliticalFaceInfo item)
    {
        put("politicalFace", item);
    }
    /**
     * Object: 员工_个人信息 's 个人成份 property 
     */
    public com.kingdee.eas.basedata.hraux.StandingInfo getStanding()
    {
        return (com.kingdee.eas.basedata.hraux.StandingInfo)get("standing");
    }
    public void setStanding(com.kingdee.eas.basedata.hraux.StandingInfo item)
    {
        put("standing", item);
    }
    /**
     * Object: 员工_个人信息 's 民族 property 
     */
    public com.kingdee.eas.basedata.hraux.FolkInfo getFolk()
    {
        return (com.kingdee.eas.basedata.hraux.FolkInfo)get("folk");
    }
    public void setFolk(com.kingdee.eas.basedata.hraux.FolkInfo item)
    {
        put("folk", item);
    }
    /**
     * Object: 员工_个人信息 's 家庭出身 property 
     */
    public com.kingdee.eas.basedata.hraux.BirthInfo getBirth()
    {
        return (com.kingdee.eas.basedata.hraux.BirthInfo)get("birth");
    }
    public void setBirth(com.kingdee.eas.basedata.hraux.BirthInfo item)
    {
        put("birth", item);
    }
    /**
     * Object: 员工_个人信息 's HR组织 property 
     */
    public com.kingdee.eas.basedata.org.HROrgUnitInfo getHrOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.HROrgUnitInfo)get("hrOrgUnit");
    }
    public void setHrOrgUnit(com.kingdee.eas.basedata.org.HROrgUnitInfo item)
    {
        put("hrOrgUnit", item);
    }
    /**
     * Object: 员工_个人信息 's 员工状态 property 
     */
    public com.kingdee.eas.hr.base.EmployeeTypeInfo getEmployeeType()
    {
        return (com.kingdee.eas.hr.base.EmployeeTypeInfo)get("employeeType");
    }
    public void setEmployeeType(com.kingdee.eas.hr.base.EmployeeTypeInfo item)
    {
        put("employeeType", item);
    }
    /**
     * Object: 员工_个人信息 's 最高职称 property 
     */
    public com.kingdee.eas.hr.base.TechnicalPostInfo getHighestTechPost()
    {
        return (com.kingdee.eas.hr.base.TechnicalPostInfo)get("highestTechPost");
    }
    public void setHighestTechPost(com.kingdee.eas.hr.base.TechnicalPostInfo item)
    {
        put("highestTechPost", item);
    }
    /**
     * Object: 员工_个人信息 's 公司聘任职称 property 
     */
    public com.kingdee.eas.hr.base.TechnicalPostInfo getEmployTechPost()
    {
        return (com.kingdee.eas.hr.base.TechnicalPostInfo)get("employTechPost");
    }
    public void setEmployTechPost(com.kingdee.eas.hr.base.TechnicalPostInfo item)
    {
        put("employTechPost", item);
    }
    /**
     * Object: 员工_个人信息 's 最高学历 property 
     */
    public com.kingdee.eas.basedata.hraux.DiplomaInfo getHighestDegree()
    {
        return (com.kingdee.eas.basedata.hraux.DiplomaInfo)get("highestDegree");
    }
    public void setHighestDegree(com.kingdee.eas.basedata.hraux.DiplomaInfo item)
    {
        put("highestDegree", item);
    }
    /**
     * Object: 员工_个人信息 's 人员类型 property 
     */
    public com.kingdee.eas.hr.base.EmployeeClassifyInfo getEmployeeClassify()
    {
        return (com.kingdee.eas.hr.base.EmployeeClassifyInfo)get("employeeClassify");
    }
    public void setEmployeeClassify(com.kingdee.eas.hr.base.EmployeeClassifyInfo item)
    {
        put("employeeClassify", item);
    }
    /**
     * Object:员工_个人信息's 助记码property 
     */
    public String getIdNum()
    {
        return getString("idNum");
    }
    public void setIdNum(String item)
    {
        setString("idNum", item);
    }
    /**
     * Object:员工_个人信息's 失效状态property 
     */
    public com.kingdee.eas.framework.DeletedStatusEnum getDeletedStatus()
    {
        return com.kingdee.eas.framework.DeletedStatusEnum.getEnum(getInt("deletedStatus"));
    }
    public void setDeletedStatus(com.kingdee.eas.framework.DeletedStatusEnum item)
    {
		if (item != null) {
        setInt("deletedStatus", item.getValue());
		}
    }
    /**
     * Object:员工_个人信息's 检查状态property 
     */
    public com.kingdee.eas.basedata.person.CheckStateEnum getCheckState()
    {
        return com.kingdee.eas.basedata.person.CheckStateEnum.getEnum(getInt("checkState"));
    }
    public void setCheckState(com.kingdee.eas.basedata.person.CheckStateEnum item)
    {
		if (item != null) {
        setInt("checkState", item.getValue());
		}
    }
    /**
     * Object:员工_个人信息's 顺序号property 
     */
    public int getIndexOf()
    {
        return getInt("indexOf");
    }
    public void setIndexOf(int item)
    {
        setInt("indexOf", item);
    }
    /**
     * Object: 员工_个人信息 's 最高学位 property 
     */
    public com.kingdee.eas.basedata.hraux.DegreeInfo getHighestSubDegree()
    {
        return (com.kingdee.eas.basedata.hraux.DegreeInfo)get("highestSubDegree");
    }
    public void setHighestSubDegree(com.kingdee.eas.basedata.hraux.DegreeInfo item)
    {
        put("highestSubDegree", item);
    }
    /**
     * Object:员工_个人信息's 姓名全拼property 
     */
    public String getFullNamePingYin()
    {
        return getString("fullNamePingYin");
    }
    public void setFullNamePingYin(String item)
    {
        setString("fullNamePingYin", item);
    }
    /**
     * Object:员工_个人信息's 姓名简拼property 
     */
    public String getSimpleNamePingYin()
    {
        return getString("simpleNamePingYin");
    }
    public void setSimpleNamePingYin(String item)
    {
        setString("simpleNamePingYin", item);
    }
    /**
     * Object:员工_个人信息's 是否后备人才property 
     */
    public boolean isIsStandby()
    {
        return getBoolean("isStandby");
    }
    public void setIsStandby(boolean item)
    {
        setBoolean("isStandby", item);
    }
    /**
     * Object:员工_个人信息's 是否干部property 
     */
    public boolean isIsStandbyCadre()
    {
        return getBoolean("isStandbyCadre");
    }
    public void setIsStandbyCadre(boolean item)
    {
        setBoolean("isStandbyCadre", item);
    }
    /**
     * Object: 员工_个人信息 's 最高职业资格 property 
     */
    public com.kingdee.eas.hr.base.CertifiedCompetencyInfo getHighestCompetency()
    {
        return (com.kingdee.eas.hr.base.CertifiedCompetencyInfo)get("highestCompetency");
    }
    public void setHighestCompetency(com.kingdee.eas.hr.base.CertifiedCompetencyInfo item)
    {
        put("highestCompetency", item);
    }
    /**
     * Object:员工_个人信息's 名称拼音property 
     */
    public String getNamePinYin()
    {
        return getString("namePinYin");
    }
    public void setNamePinYin(String item)
    {
        setString("namePinYin", item);
    }
    /**
     * Object:员工_个人信息's 名称简拼property 
     */
    public String getNameShortPinYin()
    {
        return getString("nameShortPinYin");
    }
    public void setNameShortPinYin(String item)
    {
        setString("nameShortPinYin", item);
    }
    /**
     * Object: 员工_个人信息 's 银行信息 property 
     */
    public com.kingdee.eas.basedata.person.BankInfoCollection getBankInfo()
    {
        return (com.kingdee.eas.basedata.person.BankInfoCollection)get("bankInfo");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("80EF7DED");
    }
}