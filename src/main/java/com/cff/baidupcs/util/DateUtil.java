package com.cff.baidupcs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @version 1.0
 * @since 1.0
 */
public class DateUtil {

	public static String DEFAULT_FORMAT = "yyyy/MM/dd";

	public static String SPECIAL_SEPARATE = "/";

	public static DateFormat DEFAULT_FORMATTER = new SimpleDateFormat(
			DEFAULT_FORMAT);

	public static String DEFAULT_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	public static DateFormat DEFAUTL_DATETIME_FORMATTER = new SimpleDateFormat(
			DEFAULT_DATETIME_FORMAT);
	
	public static final String FullDatePattern = "yyyy-MM-dd HH:mm:ss";
	
	public static final String SimpleDatePattern = "yyyyMMdd";
	
	private DateUtil() {
	}

	/**
	 * Compares two Dates for ordering.
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &gt; basicDate</code>, else false
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @see {@link #compareDate(Calendar, Calendar)}
	 */
	public static boolean compareDate(Date basicDate, Date compareDate) {
		if (null == basicDate || null == compareDate) {
			throw new IllegalArgumentException("The date must not be null.");
		}
		Calendar basicCalendar = Calendar.getInstance();
		Calendar compareCalendar = Calendar.getInstance();
		basicCalendar.setTime(basicDate);
		compareCalendar.setTime(compareDate);
		return compareDate(basicCalendar, compareCalendar);
	}

	/**
	 * "greater than"
	 * 
	 * <pre>
	 * DateUtil.gt(2006/01/01, 2007/01/01) = true
	 * DateUtil.gt(2007/01/01, 2006/01/01) = false
	 * DateUtil.gt(2006/01/01, 2006/01/01) = false
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &gt; basicDate</code>, else false
	 * @see {@link #compareDate(Date, Date)}
	 */
	public static boolean gt(Date basicDate, Date compareDate) {
		return compareDate(basicDate, compareDate);
	}

	/**
	 * "less than"
	 * 
	 * <pre>
	 * DateUtil.lt(2007/01/01, 2006/01/01) = true
	 * DateUtil.lt(2006/01/01, 2006/01/01) = false
	 * DateUtil.lt(2006/01/01, 2007/01/01) = false
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &lt; basicDate</code>, else false
	 * @see {@link #compareDate(Date, Date)}
	 */
	public static boolean lt(Date basicDate, Date compareDate) {
		return compareDate(compareDate, basicDate);
	}

	/**
	 * "less than or equal"
	 * 
	 * <pre>
	 * DateUtil.le(2007/01/01, 2006/01/01) = true
	 * DateUtil.le(2006/01/01, 2006/01/01) = true
	 * DateUtil.le(2006/01/01, 2007/01/01) = false
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &lt;= basicDate</code>, else false
	 * @see {@link #lt(Date, Date)}
	 * @see {@link #isSameDay(Date, Date)}
	 */
	public static boolean le(Date basicDate, Date compareDate) {
		return lt(basicDate, compareDate) || isSameDay(basicDate, compareDate);
	}

	/**
	 * "greater than or equal"
	 * 
	 * <pre>
	 * DateUtil.ge(2006/01/01, 2007/01/01) = true
	 * DateUtil.ge(2006/01/01, 2006/01/01) = true
	 * DateUtil.ge(2007/01/01, 2006/01/01) = false
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &gt;= basicDate</code>, else false
	 * @see {@link #gt(Date, Date)}
	 * @see {@link #isSameDay(Date, Date)}
	 */
	public static boolean ge(Date basicDate, Date compareDate) {
		return gt(basicDate, compareDate) || isSameDay(basicDate, compareDate);
	}

	/**
	 * <pre>
	 * DateUtil.isBetween(2006/01/01, 2000/01/01, 3000/01/01) = true
	 * DateUtil.isBetween(2000/01/01, 2000/01/01, 3000/01/01) = true
	 * DateUtil.isBetween(3000/01/01, 2000/01/01, 3000/01/01) = true
	 * DateUtil.isBetween(3000/01/01, 3000/01/01, 3000/01/01) = true
	 * DateUtil.isBetween(1999/12/31, 2000/01/01, 3000/01/01) = false
	 * DateUtil.isBetween(3999/12/31, 2000/01/01, 3000/01/01) = false
	 * </pre>
	 * 
	 * @param compareDate
	 *            the referrence date, not altered, not null
	 * @param startDate
	 *            the start date, not altered, not null
	 * @param endDate
	 *            the end date, not altered, not null
	 * @return true if
	 *         <code>compareDate &gt;= startDate && compareDate &lt;= endDate</code>,
	 *         else false
	 * @see {@link #le(Date, Date)}
	 * @see {@link #ge(Date, Date)}
	 */
	public static boolean isBetween(Date compareDate, Date startDate,
			Date endDate) {
		return le(endDate, compareDate) && ge(startDate, compareDate);
	}

	/**
	 * Compares two Calendar for ordering.
	 * 
	 * @param basicCalendar
	 *            the first calendar, not altered, not null
	 * @param compareCalendar
	 *            the second calendar, not altered, not null
	 * @return true if <code>compareCalendar > basicCalendar</code>, else
	 *         false
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean compareDate(Calendar basicCalendar,
			Calendar compareCalendar) {
		if (null == basicCalendar || null == compareCalendar) {
			throw new IllegalArgumentException("The date must not be null.");
		}
		if (basicCalendar.get(Calendar.ERA) == compareCalendar
				.get(Calendar.ERA)) {
			if (basicCalendar.get(Calendar.YEAR) == compareCalendar
					.get(Calendar.YEAR)) {
				return (basicCalendar.get(Calendar.DAY_OF_YEAR) < compareCalendar
						.get(Calendar.DAY_OF_YEAR));
			}
			return (basicCalendar.get(Calendar.YEAR) < compareCalendar
					.get(Calendar.YEAR));
		}
		return (basicCalendar.get(Calendar.ERA) < compareCalendar
				.get(Calendar.ERA));
	}

	/**
	 * <p>
	 * Checks if two date objects are on the same day ignoring time.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @see #isSameDay(Calendar, Calendar)
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if two calendar objects are on the same day ignoring time.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * Returns the previous day of specified date.
	 * 
	 * @param date
	 *            the specified date.
	 * @return the previous day of specified date.
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static Date prevDate(Date date) {
		if (null == date) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * Returns the next day of specified date.
	 * 
	 * @param date
	 *            date the specified date.
	 * @return the next day of specified date.
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static Date nextDate(Date date) {
		if (null == date) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public synchronized static Date convertDate2DefaultFormat(Date date) {
		try {
			return DEFAULT_FORMATTER.parse(DEFAULT_FORMATTER.format(date));
		} catch (ParseException e) {
		} finally {
		}
		return date;
	}

	/**
	 * Returns the smaller of two <code>Date</code> values.
	 * 
	 * @param date1
	 *            an argument.
	 * @param date2
	 *            another argument.
	 * @return the smaller of <code>date1</code> and <code>date2</code>.
	 */
	public static Date min(Date date1, Date date2) {
		if (lt(date1, date2)) {
			return date2;
		}
		return date1;
	}

	/**
	 * Returns the greater of two <code>Date</code> values.
	 * 
	 * @param date1
	 *            an argument.
	 * @param date2
	 *            another argument.
	 * @return the greater of <code>date1</code> and <code>date2</code>.
	 */
	public static Date max(Date date1, Date date2) {
		if (gt(date1, date2)) {
			return date2;
		}
		return date1;
	}
    
    /* 涓嬮潰鏂规硶鏄互绉掍负鍗曚綅鐨勬瘮杈� */
	/**
	 * <p>
	 * Checks if two date objects are on the same date.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return false. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same date
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @see #isSameSecond(Calendar, Calendar)
	 */
	public static boolean isSameSecond(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.MILLISECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.set(Calendar.MILLISECOND, 0);
		return isSameSecond(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if two calendar objects are on the same date.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return false. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same date
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean isSameSecond(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return cal1.equals(cal2);
	}


	/**
	 * "greater than"
	 * 
	 * <pre>
	 * DateUtil.gt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:11) = false
	 * DateUtil.gt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:12) = false
	 * DateUtil.gt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:13) = true
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &gt; basicDate</code>, else false
	 * @see {@link #gt4Second(Calendar, Calendar)}
	 */
	public static boolean gt4Second(Date basicDate, Date compareDate) {
		if (basicDate == null || compareDate == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(basicDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(compareDate);
		return gt4Second(cal1, cal2);
	}

	public static boolean gt4Second(Calendar basicDate, Calendar compareDate) {
		basicDate.set(Calendar.MILLISECOND, 0);
		compareDate.set(Calendar.MILLISECOND, 0);
		return basicDate.before(compareDate);
	}

	/**
	 * "less than"
	 * 
	 * <pre>
	 * DateUtil.lt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:11) = true
	 * DateUtil.lt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:12) = false
	 * DateUtil.lt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:13) = false
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &lt; basicDate</code>, else false
	 * @see {@link #lt4Second(Date, Date)}
	 */
	public static boolean lt4Second(Date basicDate, Date compareDate) {
		if (basicDate == null || compareDate == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(basicDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(compareDate);
		return lt4Second(cal1, cal2);
	}

	public static boolean lt4Second(Calendar basicDate, Calendar compareDate) {
		basicDate.set(Calendar.MILLISECOND, 0);
		compareDate.set(Calendar.MILLISECOND, 0);
		return basicDate.after(compareDate);
	}

	/**
	 * "greater than or equal"
	 * 
	 * <pre>
	 * DateUtil.gt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:11) = false
	 * DateUtil.gt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:12) = true
	 * DateUtil.gt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:13) = true
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &gt;= basicDate</code>, else false
	 * @see {@link #gt4Second(Date, Date)}
	 * @see {@link #isSameSecond(Date, Date)}
	 */
	public static boolean ge4Second(Date basicDate, Date compareDate) {
		return gt4Second(basicDate, compareDate) || isSameSecond(basicDate, compareDate);
	}
	
	public static boolean ge4Second(Calendar basicDate, Calendar compareDate) {
		return gt4Second(basicDate, compareDate) || isSameSecond(basicDate, compareDate);
	}


	/**
	 * "less than or equal"
	 * 
	 * <pre>
	 * DateUtil.lt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:11) = true
	 * DateUtil.lt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:12) = true
	 * DateUtil.lt4Second(2006/01/01 09:10:12, 2006/01/01 09:10:13) = false
	 * </pre>
	 * 
	 * @param basicDate
	 *            the first date, not altered, not null
	 * @param compareDate
	 *            the second date, not altered, not null
	 * @return true if <code>compareDate &lt;= basicDate</code>, else false
	 * @see {@link #le4Second(Date, Date)}
	 * @see {@link #isSameSecond(Date, Date)}
	 */
	public static boolean le4Second(Date basicDate, Date compareDate) {
		return lt4Second(basicDate, compareDate) || isSameSecond(basicDate, compareDate);
	}
	
	public static boolean le4Second(Calendar basicDate, Calendar compareDate) {
		return lt4Second(basicDate, compareDate) || isSameSecond(basicDate, compareDate);
	}

	/**
	 * 返回当前时间
	 * 
	 * @return Date实例
	 */
	public static Date now() {
		return nowCal().getTime();
	}

	/**
	 * 当前时间
	 * 
	 * @return Calendar实例
	 */
	public static Calendar nowCal() {
		return Calendar.getInstance();
	}
	
	/**
	 * 使用参数Format格式化Date成字符串
	 * 
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 使用参数Format将字符串转为Date
	 * 
	 * @return Date
	 */
	public static Date parse(String strDate, String pattern)
			throws ParseException {
		return StringUtil.isEmpty(strDate) ? null : new SimpleDateFormat(
				pattern).parse(strDate);
	}
	
	/**
	 * 将使用参数Format1格式化的日期字符串重新格式化丿 Format2格式化的字符串㿿
	 * @author chenping
	 * @return String
	 */
	public static String reformat(String strDate, String pattern1, String pattern2)
			throws ParseException {
		return format(parse(strDate, pattern1), pattern2);
	}
}
