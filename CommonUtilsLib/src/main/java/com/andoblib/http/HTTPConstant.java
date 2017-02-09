package com.andoblib.http;

/**
 * This class is used to define http constants like read time out, connection time out etc.
 */
public class HTTPConstant
{
	/**
	 * Connection time out. Default is 10 seconds. You can change it any time based on your requirement.
	 */
	public static int CONNECTION_TIME_OUT = 10 * 1000;
	
	/**
	 * Read time out. Default is 10 seconds. You can change it any time based on your requirement.
	 */
	public static int READ_TIME_OUT = 10 * 1000;
	
	/**
	 * Http connection content type parameters.
	 */
	public static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	

}
