package com.andoblib.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.andoblib.constant.Constants;
import com.andoblib.customexception.CustomException;
import com.andoblib.customexception.ExceptionConstant;
import com.andoblib.log.CustomLogHandler;
import com.andoblib.util.CommonUtil;

/**
 * This class is used to get the http connection and related methods like is net connected etc.
 */
public class HTTPConnection
{
	/**
	 * This method checks for the Internet connection.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @return True if internet connection is there otherwise false.
	 */
	public static boolean isNetConnected(Context pContext)
	{
		ConnectivityManager mConnectivity = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnectivity != null)
		{
			NetworkInfo[] mInfo = mConnectivity.getAllNetworkInfo();
			if (mInfo != null)
				for (int i = 0; i < mInfo.length; i++)
					if (mInfo[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}

		}
		return false;
	}

	/**
	 * This method is used to get the http connection.
	 * @param pUrl The url for which we want to get the connection.
	 * @param pIsPost Pass true if we want to post some data in post method otherwise false.
	 * @param pContext The context of the activity.
	 * @param pContentType The content type to be set.
	 * @return HttpConnection.
	 * @throws CustomException If any error occurs.
	 */
	public static HttpURLConnection getHttpConnection(final String pUrl, final boolean pIsPost, final Context pContext, final String pContentType) throws CustomException
	{
		URL mUrl = null;
		HttpURLConnection mConnection = null;
		try
		{
			if(!isNetConnected(pContext))
			{
				throw new CustomException(Constants.ERR_NO_INTERNET_CONNECTION, ExceptionConstant.ERROR_CODE_CUSTOM);
			}
			mUrl = new URL(pUrl);
			mConnection = (HttpURLConnection) mUrl.openConnection();
			mConnection.setRequestProperty("Content-Type", pContentType);
			mConnection.setReadTimeout(HTTPConstant.READ_TIME_OUT);
			mConnection.setConnectTimeout(HTTPConstant.CONNECTION_TIME_OUT);
			if (pIsPost)
			{
				mConnection.setRequestMethod("POST");
			}
			mConnection.setDoInput(true);
			mConnection.setDoOutput(pIsPost);
		}
		catch (SocketTimeoutException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_CONNECTION_TIMEOUT, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		catch (ProtocolException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_SOMETHING_WENT_WRONG, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		catch (IOException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_SOMETHING_WENT_WRONG, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		catch (Throwable p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_SOMETHING_WENT_WRONG, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}

		return mConnection;
	}

	/**
	 * This method is used to get the data from server.
	 * @param pUrl The url to which we want to make request.
	 * @param pPostData The data that we want to submit on the server.
	 * @param pContext The context of the activity.
	 * @param pContentType The content type to be set.
	 * @return Data in form of the string.
	 * @throws CustomException If any error occurs.
	 */
	public static String getData(final String pUrl, final String pPostData, final Context pContext, final String pContentType) throws CustomException
	{
		String mData = null;
		HttpURLConnection mConnection = null;

		try
		{
			if (pPostData == null)
			{
				mConnection = getHttpConnection(pUrl, false, pContext, pContentType);
			}
			else
			{
				mConnection = getHttpConnection(pUrl, true, pContext, pContentType);
				OutputStream mOutputStream = mConnection.getOutputStream();
				BufferedWriter mWriter = new BufferedWriter(new OutputStreamWriter(mOutputStream, "UTF-8"));
				mWriter.write(pPostData);
				mWriter.flush();
				mWriter.close();
				mOutputStream.close();
			}
			mConnection.connect();

			// Read the response data.
			if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				mData = CommonUtil.getStringFromStream(mConnection.getInputStream());
			}
			else
			{
				throw new CustomException(String.format(Constants.ERR_STATUS_CODE, String.valueOf(mConnection.getResponseCode())), ExceptionConstant.ERROR_CODE_CUSTOM);
			}
		}
		catch (SocketTimeoutException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_CONNECTION_TIMEOUT, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		catch (IOException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_READ_DATA, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		finally
		{
			// Disconnect the connection once used.
			if (mConnection != null)
			{
				mConnection.disconnect();
			}
		}

		return mData;
	}
	
	/**
	 * This method is used to get the input stream from server.
	 * @param pUrl The url to which we want to make request.
	 * @param pPostData The data that we want to submit on the server.
	 * @param pContext The context of the activity.
	 * @param pContentType The content type to be set.
	 * @return Input stream from http connection.
	 * @throws CustomException If any error occurs.
	 */
	public static InputStream getInputStream(final String pUrl, final String pPostData, final Context pContext, final String pContentType) throws CustomException
	{
		HttpURLConnection mConnection = null;

		try
		{
			if (pPostData == null)
			{
				mConnection = getHttpConnection(pUrl, false, pContext, pContentType);
			}
			else
			{
				mConnection = getHttpConnection(pUrl, true, pContext, pContentType);
				OutputStream mOutputStream = mConnection.getOutputStream();
				BufferedWriter mWriter = new BufferedWriter(new OutputStreamWriter(mOutputStream, "UTF-8"));
				mWriter.write(pPostData);
				mWriter.flush();
				mWriter.close();
				mOutputStream.close();
			}
			mConnection.connect();

			// Read the response data.
			if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				if ("gzip".equals(mConnection.getContentEncoding())) 
				{
					return new GZIPInputStream(mConnection.getInputStream());
				}
				else
				{
					return mConnection.getInputStream();
				}
			}
			else
			{
				throw new CustomException(String.format(Constants.ERR_STATUS_CODE, String.valueOf(mConnection.getResponseCode())), ExceptionConstant.ERROR_CODE_CUSTOM);
			}
		}
		catch (SocketTimeoutException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_CONNECTION_TIMEOUT, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		catch (IOException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_READ_DATA, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		finally
		{
			// Disconnect the connection once used.
			/*if (mConnection != null)
			{
				mConnection.disconnect();
			}*/
		}
	}
	
	/**
	 * This method is used to get the Reader from server.
	 * @param pUrl The url to which we want to make request.
	 * @param pPostData The data that we want to submit on the server.
	 * @param pContext The context of the activity.
	 * @param pContentType The content type to be set.
	 * @return Input stream from http connection.
	 * @throws CustomException If any error occurs.
	 */
	public static Reader getReader(final String pUrl, final String pPostData, final Context pContext, final String pContentType) throws CustomException
	{
		HttpURLConnection mConnection = null;

		try
		{
			if (pPostData == null)
			{
				mConnection = getHttpConnection(pUrl, false, pContext, pContentType);
			}
			else
			{
				mConnection = getHttpConnection(pUrl, true, pContext, pContentType);
				OutputStream mOutputStream = mConnection.getOutputStream();
				BufferedWriter mWriter = new BufferedWriter(new OutputStreamWriter(mOutputStream, "UTF-8"));
				mWriter.write(pPostData);
				mWriter.flush();
				mWriter.close();
				mOutputStream.close();
			}
			mConnection.connect();

			// Read the response data.
			if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				if ("gzip".equals(mConnection.getContentEncoding())) 
				{
					return new InputStreamReader(new GZIPInputStream(mConnection.getInputStream()));
				}
				else
				{
					return new InputStreamReader(mConnection.getInputStream());
				}
			}
			else
			{
				throw new CustomException(String.format(Constants.ERR_STATUS_CODE, String.valueOf(mConnection.getResponseCode())), ExceptionConstant.ERROR_CODE_CUSTOM);
			}
		}
		catch (SocketTimeoutException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_CONNECTION_TIMEOUT, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		catch (IOException p_e)
		{
			CustomLogHandler.printError(p_e);
			throw new CustomException(Constants.ERR_READ_DATA, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
		finally
		{
			// Disconnect the connection once used.
			/*if (mConnection != null)
			{
				mConnection.disconnect();
			}*/
		}
	}
}
