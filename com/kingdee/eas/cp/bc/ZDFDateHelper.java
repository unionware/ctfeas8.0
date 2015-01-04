/**
 * 系统项目名称
 * com.kingdee.eas.lgc.bd.client
 * STODateHelper.java
 * 
 * 2013-4-19-下午06:24:56
 * @author unionware
 * 
 */
package com.kingdee.eas.cp.bc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.util.DateTimeUtils;

/**
 * 
 * STODateHelper
 * 
 * 
 * 2013-4-19 下午06:24:56
 * 
 * @version 1.0.0
 * @description:
 */
public class ZDFDateHelper {
	 public static final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	 public static final String FORMAT_DAY_STR =  "yyyy-MM-dd";

	  public static final String FORMAT_TIME_STR = "yyyy-MM-dd HH:mm:ss:sss";
	  
	  public static final DateFormat FORMAT_DAY = new SimpleDateFormat(FORMAT_DAY_STR);

	  public static final DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	  public static final DateFormat FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");

	  
	  public static List getTimeList(int beginYear, int beginMonth, int endYear, int endMonth, int k)
	  {
	    List list = new ArrayList();
	    if (beginYear == endYear) {
	      for (int j = beginMonth; j <= endMonth; j++) {
	        list.add(getTimeList(beginYear, j, k));
	      }
	    }
	    else
	    {
	      for (int j = beginMonth; j < 12; j++) {
	        list.add(getTimeList(beginYear, j, k));
	      }

	      for (int i = beginYear + 1; i < endYear; i++) {
	        for (int j = 0; j < 12; j++) {
	          list.add(getTimeList(i, j, k));
	        }
	      }
	      for (int j = 0; j <= endMonth; j++) {
	        list.add(getTimeList(endYear, j, k));
	      }
	    }

	    return list;
	  }

	  public static List getTimeList(int beginYear, int beginMonth, int k)
	  {
	    List list = new ArrayList();
	    Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
	    int max = begincal.getActualMaximum(5);
	    for (int i = 1; i < max; i += k) {
	      list.add(begincal.getTime());
	      begincal.add(5, k);
	    }
	    begincal = new GregorianCalendar(beginYear, beginMonth, max);
	    list.add(begincal.getTime());
	    return list;
	  }

	  public static java.util.Date getDayBegin()
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.set(11, 0);
	    cal.set(12, 0);
	    cal.set(13, 0);
	    cal.set(14, 0);
	    return cal.getTime();
	  }

	  public static Timestamp getTimeStampDayBegin()
	  {
	    return new Timestamp(getDayBegin().getTime());
	  }

	  public static java.util.Date getDayEnd()
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.set(11, 23);
	    cal.set(12, 59);
	    cal.set(13, 59);

	    return cal.getTime();
	  }

	  public static java.util.Date getNextDay(java.util.Date date)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.set(5, cal.get(5) + 1);
	    return cal.getTime();
	  }

	  public static java.util.Date getNextMonth(java.util.Date date)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.set(2, cal.get(2) + 1);
	    return cal.getTime();
	  }

	  public static java.util.Date getPreMonth(java.util.Date date)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.set(2, cal.get(2) - 1);
	    return cal.getTime();
	  }

	  public static java.util.Date getPreMonthMaxDate(java.util.Date date)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.set(5, 1);
	    cal.set(2, cal.get(2) - 1);
	    cal.set(5, cal.getActualMaximum(5));
	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.util.Date getNextMonthMaxDate(java.util.Date date)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.set(5, 1);
	    cal.set(2, cal.get(2) + 1);
	    cal.set(5, cal.getActualMaximum(5));
	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.sql.Date getSQLBegin(java.util.Date da)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(da);

	    cal.set(11, 0);
	    cal.set(12, 0);
	    cal.set(13, 0);
	    cal.set(14, 0);
	    java.sql.Date ds = new java.sql.Date(cal.getTimeInMillis());
	    return ds;
	  }

	  public static java.sql.Date getSQLEnd(java.util.Date da)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(da);
	    cal.set(11, 23);
	    cal.set(12, 59);
	    cal.set(13, 59);
	    return new java.sql.Date(cal.getTimeInMillis());
	  }

	  public static java.util.Date getFirstSeasonDate(java.util.Date date)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int sean = getSeason(cal.get(2));
	    cal.set(2, sean * 3 - 2);
	    return cal.getTime();
	  }

	  public static java.util.Date getFirstYearDate(java.util.Date date)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(2, 0);
	    cal.set(5, 1);

	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.util.Date getLastYearDate(java.util.Date date)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(2, 11);
	    cal.set(5, 31);

	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static int getSeason(int mouth)
	  {
	    return SEASON[mouth];
	  }

	  public static java.util.Date getBeforeDay(java.util.Date thisDate)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(thisDate);
	    cal.add(5, -1);

	    return cal.getTime();
	  }

	  public static java.util.Date getAfterDay(java.util.Date thisDate)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(thisDate);
	    cal.add(5, 1);

	    return cal.getTime();
	  }

	  public static boolean isFirstDayOfYear(java.util.Date date)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return (cal.get(2) == 0) && (cal.get(5) == 1);
	  }

	  public static java.util.Date getFirstYearDate(int year)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(1, year);

	    cal.set(2, 0);
	    cal.set(5, 1);

	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.util.Date stringToDate(String s) {
	    java.util.Date d = null;
	    if (FMHelper.isEmpty(s))
	      d = getDayBegin();
	    else {
	      try {
	        d = FORMAT_DAY.parse(s);
	      } catch (ParseException e) {
	        try {
	          d = new SimpleDateFormat().parse(s);
	        }
	        catch (ParseException e1) {
	          d = getDayBegin();
	        }
	      }
	    }

	    return d;
	  }

	  public static String DateToString(java.util.Date s)
	  {
	    return FORMAT_DAY.format(s);
	  }

	  public static  String getDateStrByFormat(java.util.Date date,String format){
			if(format==null){
				format="yyyy-MM-dd";
			}
			return  new SimpleDateFormat(format).format(date);
			
		}
	  
	  public static  String getDateStartStrByFormat(java.util.Date date){
		  return  getDateStartStrByFormat(date,FORMAT_TIME_STR);
	  }
	  
	  public static  String getDateStartStrByFormat(java.util.Date date,String format){
		  if(format==null){
			  format="yyyy-MM-dd";
		  }
		  return  new SimpleDateFormat(format).format(getDateStartTime(date));
	  }
	  
	  public static java.util.Date getLastYearDate(int year)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(1, year);

	    cal.set(2, 11);

	    cal.set(5, 31);

	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static long dateDiff(java.util.Date date1, java.util.Date date2)
	  {
	    long date1ms = date1.getTime();
	    long date2ms = date2.getTime();
	    return date2ms - date1ms;
	  }

	  public static java.util.Date getDayBegin(java.util.Date da)
	  {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(da);

	    cal.set(11, 0);
	    cal.set(12, 0);
	    cal.set(13, 0);
	    cal.set(14, 0);
	    java.util.Date ds = new java.util.Date(cal.getTimeInMillis());
	    return ds;
	  }


	  public static java.util.Date getFirstDayOfCurMonth()
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(5, cal.getActualMinimum(5));
	    return DateTimeUtils.truncateDate(cal.getTime());
	  }
	  public static java.util.Date getFirstDayOfMonth(java.util.Date d) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    cal.set(5, cal.getActualMinimum(5));
	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.util.Date getEndDayOfCurMonth()
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(5, cal.getActualMaximum(5));
	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.util.Date getLastDayOfCurMonth()
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(5, cal.getActualMaximum(5));
	    return DateTimeUtils.truncateDate(cal.getTime());
	  }

	  public static java.util.Date getLastDayOfMonth(java.util.Date date)
	  {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(2, 1);
	    calendar.set(5, 1);
	    calendar.add(5, -1);
	    calendar.set(11, calendar.getActualMaximum(11));
	    calendar.set(12, calendar.getActualMaximum(12));
	    calendar.set(13, calendar.getActualMaximum(13));
	    return calendar.getTime();
	  }

	  public static java.util.Date getLastDayOfMonth2(java.util.Date date)
	  {
	    return DateTimeUtils.truncateDate(getLastDayOfMonth(date));
	  }

	  public static String formatDate(java.util.Date d)
	  {
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	    return df.format(d);
	  }

	  public static String formatDate2(java.util.Date d)
	  {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    return df.format(d);
	  }

	  public static java.util.Date max(java.util.Date date1, java.util.Date date2)
	  {
	    if (date1 == null) {
	      return date2;
	    }
	    if (date2 == null) {
	      return date1;
	    }
	    if (date1.after(date2)) {
	      return date1;
	    }
	    return date2;
	  }

	  public static java.util.Date min(java.util.Date date1, java.util.Date date2)
	  {
	    if (date1 == null) {
	      return date2;
	    }
	    if (date2 == null) {
	      return date1;
	    }
	    if (date1.after(date2)) {
	      return date2;
	    }
	    return date1;
	  }
	  public static java.sql.Date truncateSqlDate(java.sql.Date sqlDate) {
	    java.util.Date date = new java.util.Date(sqlDate.getTime());
	    return new java.sql.Date(DateTimeUtils.truncateDate(date).getTime());
	  }

	  public static int getDiffDays(java.util.Date beginDate, java.util.Date endDate)
	  {
	    if ((beginDate == null) || (endDate == null)) {
	      throw new IllegalArgumentException("getDiffDays param is null!");
	    }

	    long diff = (endDate.getTime() - beginDate.getTime()) / 86400000L;

	    int days = new Long(diff).intValue() + 1;

	    return days;
	  }

	  public static java.sql.Date getSqlDate(java.util.Date date) {
	    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	    return sqlDate;
	  }

	  public static java.util.Date getYearDate(java.util.Date date)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(cal.get(1) + 1, 0, 0, 0, 0, 0);
	    return new java.util.Date(cal.getTimeInMillis() / 1000L * 1000L);
	  }

	  public static java.util.Date getMonthDate(java.util.Date date)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(cal.get(1), cal.get(2) + 1, 0, 0, 0, 0);
	    return new java.util.Date(cal.getTimeInMillis() / 1000L * 1000L);
	  }

	  public static java.util.Date getDayDate(java.util.Date date)
	  {
	    return DateTimeUtils.truncateDate(date);
	  }

	  public static java.util.Date addDays(java.util.Date date, int day)
	  {
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    calendar.add(5, day);
	    return calendar.getTime();
	  }

	  public static final  Date getAMonthStartDate(Date date){
	    	Calendar c  = Calendar.getInstance();
	    	c.setTime(date);
	    	c.set(Calendar.DAY_OF_MONTH, 1);
	    	c.set(Calendar.HOUR_OF_DAY, 0);
	    	c.set(Calendar.MINUTE, 0);
	    	c.set(Calendar.SECOND, 0);
	    	c.set(Calendar.MILLISECOND, 000);
	    	return c.getTime();
	    }
	  
	  public static final  Date getDateStartTime(Date date){
	    	Calendar c  = Calendar.getInstance();
	    	c.setTime(date);
	    	c.set(Calendar.HOUR_OF_DAY, 0);
	    	c.set(Calendar.MINUTE, 0);
	    	c.set(Calendar.SECOND, 0);
	    	c.set(Calendar.MILLISECOND, 000);
	    	return c.getTime();
	    }
	  
	  public static final  String getNextOrPreXMonthStartDateStr(Date date,int x,String format){
	    	return getDateStrByFormat(getNextOrPreXMonthStartDate(date, x), format);
	    }
	    
	    public static final  Date getNextOrPreXMonthStartDate(Date date,int x){
	    	date = getAMonthStartDate(date);
	    	Calendar c  = Calendar.getInstance();
	    	c.setTime(date);
	    	c.add(Calendar.MONTH, x);
	    	
	    	return c.getTime();
	    }
	    
	    
	    public static final  String getNextOrPreXDateStartDateStr(Date date,int x){
	    	return getDateStrByFormat(getNextOrPreXDateStartDate(date, x), FORMAT_TIME_STR);
	    }
	    public static final  String getNextOrPreXDateStartDateStr(Date date,int x,String format){
	    	return getDateStrByFormat(getNextOrPreXDateStartDate(date, x), format);
	    }
	    
	    public static final  Date getNextOrPreXDateStartDate(Date date,int x){
	    	date = getDateStartTime(date);
	    	Calendar c  = Calendar.getInstance();
	    	c.setTime(date);
	    	c.add(Calendar.DAY_OF_MONTH, x);
	    	return c.getTime();
	    }
}
