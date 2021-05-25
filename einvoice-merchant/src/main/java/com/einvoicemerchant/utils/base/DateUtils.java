package com.einvoicemerchant.utils.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.common.base.Strings;

// http://www.ofyu.com/index.php/8-java
public class DateUtils {
	public final static String DateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public final static SimpleDateFormat sDateTimeFormat = new SimpleDateFormat(DateTimeFormat);
	public final static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat sDateMonthFormat = new SimpleDateFormat("yyyy-MM");
	public static Date toDateTime(long ms) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ms);
		return cal.getTime();
	}

	public static Date toDateTime(String dateString) {
		if (Strings.isNullOrEmpty(dateString))
			return null;

		try {
			return sDateTimeFormat.parse(dateString);
		} catch (Exception pe) {
			return null;
		}
	}

	public static Date toDate(String dateString) {
		if (Strings.isNullOrEmpty(dateString))
			return null;

		try {
			return sDateFormat.parse(dateString);
		} catch (Exception pe) {
			return null;
		}
	}

	public static boolean compareDate(String beginDate, String endDate) {
		try {
			Date nowBegin = sDateTimeFormat.parse(beginDate);
			Date nowEnd = sDateTimeFormat.parse(endDate);
			if (nowBegin.getTime() < nowEnd.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static String nowString() {
		return sDateTimeFormat.format(new Date());
	}

	public static String nowString(SimpleDateFormat df) {
		return df.format(new Date());
	}

	public static String toDateString(Date date) {
		if (date == null)
			return null;
		else
			return sDateFormat.format(date);
	}

	public static String toSimpleDateString(Date date) {
		if (date == null)
			return null;
		else
			return sSimpleDateFormat.format(date);
	}

	public static String toDateTimeString(Date date) {
		if (date == null)
			return null;
		else
			return sDateTimeFormat.format(date);
	}

	public static String tosDateMonthFormat(Date date) {
		if (date == null)
			return null;
		else
			return sDateMonthFormat.format(date);
	}

	public static String toString(Date date, SimpleDateFormat df) {
		if (date == null)
			return null;
		else
			return df.format(date);
	}

	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static int monthsBetween(Date dateStart, Date dateEnd) {
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(dateStart);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dateEnd);

		return monthsBetween(calStart, calEnd);
	}

	public static int monthsBetween(Calendar calStart, Calendar calEnd) {
		int sYear = calStart.get(Calendar.YEAR);
		int sMonth = calStart.get(Calendar.MONTH);

		int eYear = calEnd.get(Calendar.YEAR);
		int eMonth = calEnd.get(Calendar.MONTH);

		return ((eYear - sYear) * 12 + (eMonth - sMonth));
	}

	public static int daysBetween(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		return daysBetween(cal1, cal2);
	}

	public static int daysBetween(Long timestamp, Long timestamp2) {
		Date date1=null;
		Date date2=null;
		try {
			date1=sDateFormat.parse(sDateTimeFormat.format(timestamp));
			date2=sDateFormat.parse(sDateTimeFormat.format(timestamp2));
		}catch (Exception ex){

		}
		return daysBetween(date1, date2);
	}

	public static int daysBetween(Calendar cal1, Calendar cal2) {
		long time1 = cal1.getTimeInMillis();
		long time2 = cal2.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String daysBetweenString(Date date1, Date date2) {
		int dayPast = DateUtils.daysBetween(date1, date2);
		if (dayPast == 0)
			return "今天";
		else if (dayPast < 0)
			return String.format("%d天前", -dayPast);
		else
			return String.format("%d天后", dayPast);
	}

	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Calendar getTodayCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Calendar getDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date getTomorrow() {
		return addDay(1);
	}

	public static Date getTomorrow(Date date) {
		return addDay(date, 1);
	}

	public static Date getYesterday() {
		return addDay(-1);
	}

	public static Date getYesterday(Date date) {
		return addDay(date, -1);
	}

	public static Date addMinute(Date date, int addMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, addMinute);
		return cal.getTime();
	}

	public static Date addDay(int addDay) {
		Calendar cal = getTodayCalendar();
		cal.add(Calendar.DATE, addDay);
		return cal.getTime();
	}

	public static Date addDay(Date date, int addDay) {
		if (date == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDay);
		return cal.getTime();
	}

	public static Date addYear(Date date,int addYear){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, addYear);
		return cal.getTime();
	}

	public static Date addMonth(Date date, int addMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonth);
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getDay(cal).getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return getDay(cal).getTime();
	}



	public static String getTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取指定时间的几个小时之前的时间
	 * @param date
	 * @return
	 */
	public static Date getDateBeforeHours(Date date,Integer hour){
		Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY)- hour);
        Date resultDate = c.getTime();
        return resultDate;
	}
}
