package com.andoblib.util;

import com.andoblib.log.CustomLogHandler;
import com.andoblib.vo.DateVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This method is used to format the date in the required format.
 */
public class DateUtil
{
	/**
	 * This method is used to format the given date into required string date format.
	 * @param pDate The date that we need to format.
	 * @param pFormat The format in which the date needs to be formatted.
	 * @return Formatted date in form of string.
	 */
	public static String format(final Date pDate, final String pFormat)
	{
		String mFormattedDate = null;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(pFormat, Locale.getDefault());
		mFormattedDate = mSimpleDateFormat.format(pDate);
		
		return mFormattedDate;
	}
	/**
	 * This method is used to format the given date into required string date format.
	 * @param pCalendar The calendar object that we need to format.
	 * @param pFormat The format in which the date needs to be formatted.
	 * @return Formatted date in form of string.
	 */
	public static String format(final Calendar pCalendar, final String pFormat)
	{
		String mFormattedDate = null;
		mFormattedDate = format(pCalendar.getTime(), pFormat);
		return mFormattedDate;
	}
	/**
	 * This method is used to get Date object from given string date.
	 * @param pDate The date that we need to format.
	 * @param pFormat The format of the date passed.
	 * @return Formatted date in form of Date.
	 */
	public static Date getDate(final String pDate, final String pFormat)
	{
		Date mFormattedDate = null;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(pFormat, Locale.getDefault());
		try
		{
			mFormattedDate = mSimpleDateFormat.parse(pDate);
		}
		catch(Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		
		return mFormattedDate;
	}
	/**
	 * This method is used to get Calendar object from given string date.
	 * @param pDate The date that we need to format.
	 * @param pFormat The format of the date passed.
	 * @return Formatted date in form of Calendar.
	 */
	public static Calendar getCalendar(final String pDate, final String pFormat)
	{
		Calendar mFormattedCalendar = Calendar.getInstance();
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(pFormat, Locale.getDefault());
		
		try
		{
			mFormattedCalendar.setTime(mSimpleDateFormat.parse(pDate));
		}
		catch(Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		
		return mFormattedCalendar;
	}
	
	/**
	 * This method is used to format the current date into required format.
	 * @param pCurrentDate The current date in form of string.
	 * @param pCurrentDateFormat The date format of the current date.
	 * @param pNewDateFormat The date format in which we want to format the date.
	 * @return Date in form of string in specified date format.
	 */
	public static String format(final String pCurrentDate, final String pCurrentDateFormat, final String pNewDateFormat)
	{
		String mNewDate = null;
		
		try
		{
			Date mCurrentDate = new SimpleDateFormat(pCurrentDateFormat, Locale.getDefault()).parse(pCurrentDate);
			mNewDate = new SimpleDateFormat(pNewDateFormat, Locale.getDefault()).format(mCurrentDate);
/*
			Date mCurrentDate = new SimpleDateFormat(pCurrentDateFormat).parse(pCurrentDate);
			mNewDate = new SimpleDateFormat(pNewDateFormat).format(mCurrentDate);*/
		}
		catch(Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		
		return mNewDate;
	}
	
	/**
	 * @param pDate The date that we want to parse.
	 * @param pFormat The format of the passed date.
	 * @return DateVo contain all information about date.  <br/>
	 * <b>Sample Output</b><br/>
	 * ===Current Date=== 23/05/2014 01:40:11:24 PM <br/>
	 * ===Format==== dd/MM/yyyy hh:mm:ss:SS a <br/>
	 * ==========Formatted Date========== <br/>
	 * AmPm :1 <br/>
	 * Day :23 <br/>
	 * DayNameFull :Friday <br/>
	 * DayNameShort :Fri <br/>
	 * Hour :1 <br/>
	 * HOUR :13 <br/>
	 * MilliSecond :24 <br/>
	 * Minute :40 <br/>
	 * MonthInDigit :4 <br/>
	 * MonthInWordFull :May <br/>
	 * MonthInWordShort :May <br/>
	 * Second :11 <br/>
	 * YearFull :2014 <br/>
	 * YearShort :14 <br/>
	 */
	public static DateVo parseDate(final String pDate, final String pFormat)
	{
		DateVo mDateVo = null;
		try
		{
			Calendar mCalendar = getCalendar(pDate, pFormat);
			mDateVo = new DateVo();
			
			mDateVo.setAmPm(mCalendar.get(Calendar.AM_PM));
			mDateVo.setDay(mCalendar.get(Calendar.DAY_OF_MONTH));
			mDateVo.setHour(mCalendar.get(Calendar.HOUR)); // 12 hour clock
			mDateVo.setHOUR(mCalendar.get(Calendar.HOUR_OF_DAY)); // 24 hour clock
			mDateVo.setMilliSecond(mCalendar.get(Calendar.MILLISECOND));
			mDateVo.setMinute(mCalendar.get(Calendar.MINUTE));
			mDateVo.setMonthInDigit(mCalendar.get(Calendar.MONTH)); // Jan = 0, dec = 11
			mDateVo.setMonthInWordFull(new SimpleDateFormat("MMMM", Locale.getDefault()).format(mCalendar.getTime()));
			mDateVo.setMonthInWordShort(new SimpleDateFormat("MMM", Locale.getDefault()).format(mCalendar.getTime()));
			mDateVo.setSecond(mCalendar.get(Calendar.SECOND));
			mDateVo.setYearFull(mCalendar.get(Calendar.YEAR));
			mDateVo.setYearShort(Integer.parseInt(new SimpleDateFormat("yy", Locale.getDefault()).format(mCalendar.getTime())));
			mDateVo.setDayNameFull(new SimpleDateFormat("EEEE", Locale.getDefault()).format(mCalendar.getTime()));
			mDateVo.setDayNameShort(new SimpleDateFormat("EEE", Locale.getDefault()).format(mCalendar.getTime()));
		}
		catch(Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return mDateVo;
	}
}
