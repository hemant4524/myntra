package com.andoblib.customexception;

/**
 * This is the Exception constant class used to define the constants related to the custom exception.
 */
public class ExceptionConstant
{
	/**
	 * Used to show that this type of errors are related to some exceptional cases, that are not predictable like null pointer exception etc.
	 */
	public static final int ERROR_CODE_SYSTEM = 1;
	/**
	 * Used to show some predefined errors like "No Data" etc.
	 */
	public static final int ERROR_CODE_CUSTOM = 2;

	/**
	 * Error code for http error code 401
	 */
	public static final int ERROR_CODE_UNAUTHORIZE = 3;


	/**
	 * Used to show some predefined errors like "No internet connection" etc.
	 */
	public static final int ERROR_CODE_NO_INTERNET_CONNECT = 4;

}
