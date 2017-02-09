package com.andoblib.log;

import android.util.Log;

/**
 * This class is used to print the different types of logs like error, debug, verbose etc.
 */
public class CustomLogHandler
{
	/**
	 * This method prints the debug log.
	 * @param pTag The tag to be print.
	 * @param pMessage The message to be print.
	 */
	public static void printDebug(final String pTag, final String pMessage)
	{
		if(LogConstant.DEBUG)
		{
			Log.d(pTag, pMessage);
		}
	}

	/**
	 * This method prints the info log.
	 * @param pTag The tag to be print.
	 * @param pMessage The message to be print.
	 */
	public static void printInfo(final String pTag, final String pMessage)
	{
		if(LogConstant.DEBUG)
		{
			Log.i(pTag, pMessage);
		}
	}
	
	/**
	 * This method prints the verbose log.
	 * @param pTag The tag to be print.
	 * @param pMessage The message to be print.
	 */
	public static void printVerbose(final String pTag, final String pMessage)
	{
		if(LogConstant.DEBUG)
		{
			Log.v(pTag, pMessage);
		}
	}
	
	/**
	 * This method prints the warning log.
	 * @param pTag The tag to be print.
	 * @param pMessage The message to be print.
	 */
	public static void printWarning(final String pTag, final String pMessage)
	{
		if(LogConstant.DEBUG)
		{
			Log.w(pTag, pMessage);
		}
	}
	
	/**
	 * This method prints the error log.
	 * @param pThrowable The Throwable object.
	 */
	public static void printError(final Throwable pThrowable)
	{
		if(LogConstant.DEBUG)
		{
			Log.e(pThrowable.getStackTrace()[0].getClassName(), // Used to get the class name from which the exception is originated.
					Log.getStackTraceString(pThrowable)); // Gets actual stack trace.
		}
	}
}
