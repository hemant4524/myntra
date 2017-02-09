package com.andoblib.vo;

/**
 * This is the simple POJO to store information related to date.
 */
public class DateVo
{
	private int mDay;
	private int mMonthInDigit;
	private String mMonthInWordShort;
	private String mMonthInWordFull;
	private int mYearShort;
	private int mYearFull;

	private int mHour;
	private int mAmPm;
	private int mHOUR;
	private int mMinute;
	private int mSecond;
	private int mMilliSecond;

	private String mDayNameShort;
	private String mDayNameFull;

	public int getDay()
	{
		return mDay;
	}

	public void setDay(int day)
	{
		mDay = day;
	}

	/**
	 * <b>Note:</b> The month starts from 0 index. So January = 0, December = 11.
	 * @return Month in numeric format.
	 */
	public int getMonthInDigit()
	{
		return mMonthInDigit;
	}

	/**
	 * <b>Note:</b> The month starts from 0 index. So January = 0, December = 11.
	 * @param monthInDigit The month io numeric format. Starts with 0 index.
	 */
	public void setMonthInDigit(int monthInDigit)
	{
		mMonthInDigit = monthInDigit;
	}

	public String getMonthInWordShort()
	{
		return mMonthInWordShort;
	}

	public void setMonthInWordShort(String monthInWordShort)
	{
		mMonthInWordShort = monthInWordShort;
	}

	public String getMonthInWordFull()
	{
		return mMonthInWordFull;
	}

	public void setMonthInWordFull(String monthInWordFull)
	{
		mMonthInWordFull = monthInWordFull;
	}

	public int getYearShort()
	{
		return mYearShort;
	}

	public void setYearShort(int yearShort)
	{
		mYearShort = yearShort;
	}

	public int getYearFull()
	{
		return mYearFull;
	}

	public void setYearFull(int yearFull)
	{
		mYearFull = yearFull;
	}

	public int getHour()
	{
		return mHour;
	}

	public void setHour(int hour)
	{
		mHour = hour;
	}

	public int getAmPm()
	{
		return mAmPm;
	}

	public void setAmPm(int amPm)
	{
		mAmPm = amPm;
	}

	public int getHOUR()
	{
		return mHOUR;
	}

	public void setHOUR(int hOUR)
	{
		mHOUR = hOUR;
	}

	public int getMinute()
	{
		return mMinute;
	}

	public void setMinute(int minute)
	{
		mMinute = minute;
	}

	public int getSecond()
	{
		return mSecond;
	}

	public void setSecond(int second)
	{
		mSecond = second;
	}

	public int getMilliSecond()
	{
		return mMilliSecond;
	}

	public void setMilliSecond(int milliSecond)
	{
		mMilliSecond = milliSecond;
	}

	public String getDayNameShort()
	{
		return mDayNameShort;
	}

	public void setDayNameShort(String dayNameShort)
	{
		mDayNameShort = dayNameShort;
	}

	public String getDayNameFull()
	{
		return mDayNameFull;
	}

	public void setDayNameFull(String dayNameFull)
	{
		mDayNameFull = dayNameFull;
	}
	
	@Override
	public String toString()
	{
		String mString = "==========\n" + 
				  "AmPm :" + getAmPm()  
				+ "\nDay :" + getDay()
				+ "\nDayNameFull :" + getDayNameFull()
				+ "\nDayNameShort :" + getDayNameShort()
				+ "\nHour :" + getHour()
				+ "\nHOUR :" + getHOUR()
				+ "\nMilliSecond :" + getMilliSecond()
				+ "\nMinute :" + getMinute()
				+ "\nMonthInDigit :" + getMonthInDigit()
				+ "\nMonthInWordFull :" + getMonthInWordFull()
				+ "\nMonthInWordShort :" + getMonthInWordShort()
				+ "\nSecond :" + getSecond()
				+ "\nYearFull :" + getYearFull()
				+ "\nYearShort :" + getYearShort();
		
		return mString;
	}
}
