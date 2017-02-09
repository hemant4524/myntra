package com.andoblib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This is the common class used to store the values of the application in shared preference.
 */
public class AppSetting
{
	private static Context mContext;
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor mEditor;
	
	/**
	 * This method initiliaze the shared preference class.
	 * @param pContext The context of the activity.
	 */
	public static void init(final Context pContext)
	{
		mContext = pContext;
		
		if(mSharedPreferences == null)
		{
			mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
		}
		if(mEditor == null)
		{
			mEditor = mSharedPreferences.edit();
		}
	}
	
	//==============String
	/**
	 * Used to store the string value in shared preference.
	 * @param pContext The context of the activity.
	 * @param pKey The key.
	 * @param pValue The value.
	 * @return true if stored successfully otherwise false.
	 */
	public static boolean setString(final Context pContext, final String pKey, final String pValue)
	{
		init(pContext);
		mEditor.putString(pKey, pValue);
		return (mEditor.commit());
	}
	/**
	 * Used to get the value from the shared preference using key.
	 * @param pContext The context of the activity.
	 * @param pKey The key from which we want to retrieve the value.
	 * @param pDefaultValue  The default value if there is no value associated with this key.
	 * @return Value as string from specified key.
	 */
	public static String getString(final Context pContext, final String pKey, final String pDefaultValue)
	{
		init(pContext);
		return mSharedPreferences.getString(pKey, pDefaultValue);
	}
	
	//============Integer
	/**
	 * Used to store the integer value in shared preference.
	 * @param pContext The context of the activity.
	 * @param pKey The key.
	 * @param pValue The value.
	 * @return true if stored successfully otherwise false.
	 */
	public static boolean setInt(final Context pContext, final String pKey, final int pValue)
	{
		init(pContext);
		mEditor.putInt(pKey, pValue);
		return (mEditor.commit());
	}
	/**
	 * Used to get the value from the shared preference using key.
	 * @param pContext The context of the activity.
	 * @param pKey The key from which we want to retrieve the value.
	 * @param pDefaultValue  The default value if there is no value associated with this key.
	 * @return Value as integer from specified key.
	 */
	public static int getInt(final Context pContext, final String pKey, final int pDefaultValue)
	{
		init(pContext);
		return mSharedPreferences.getInt(pKey, pDefaultValue);
	}
	
	//==============Boolean
	/**
	 * Used to store the boolean value in shared preference.
	 * @param pContext The context of the activity.
	 * @param pKey The key.
	 * @param pValue The value.
	 * @return true if stored successfully otherwise false.
	 */
	public static boolean setBoolean(final Context pContext, final String pKey, final boolean pValue)
	{
		init(pContext);
		mEditor.putBoolean(pKey, pValue);
		return (mEditor.commit());
	}
	/**
	 * Used to get the value from the shared preference using key.
	 * @param pContext The context of the activity.
	 * @param pKey The key from which we want to retrieve the value.
	 * @param pDefaultValue  The default value if there is no value associated with this key.
	 * @return Value as boolean from specified key.
	 */
	public static boolean getBoolean(final Context pContext, final String pKey, final boolean pDefaultValue)
	{
		init(pContext);
		return mSharedPreferences.getBoolean(pKey, pDefaultValue);
	}
	
	//==============Float
	/**
	 * Used to store the float value in shared preference.
	 * @param pContext The context of the activity.
	 * @param pKey The key.
	 * @param pValue The value.
	 * @return true if stored successfully otherwise false.
	 */
	public static boolean setFloat(final Context pContext, final String pKey, final float pValue)
	{
		init(pContext);
		mEditor.putFloat(pKey, pValue);
		return (mEditor.commit());
	}
	/**
	 * Used to get the value from the shared preference using key.
	 * @param pContext The context of the activity.
	 * @param pKey The key from which we want to retrieve the value.
	 * @param pDefaultValue  The default value if there is no value associated with this key.
	 * @return Value as float from specified key.
	 */
	public static float getBoolean(final Context pContext, final String pKey, final float pDefaultValue)
	{
		init(pContext);
		return mSharedPreferences.getFloat(pKey, pDefaultValue);
	}
	
	//==============Long
	/**
	 * Used to store the long value in shared preference.
	 * @param pContext The context of the activity.
	 * @param pKey The key.
	 * @param pValue The value.
	 * @return true if stored successfully otherwise false.
	 */
	public static boolean setLong(final Context pContext, final String pKey, final long pValue)
	{
		init(pContext);
		mEditor.putLong(pKey, pValue);
		return (mEditor.commit());
	}
	/**
	 * Used to get the value from the shared preference using key.
	 * @param pContext The context of the activity.
	 * @param pKey The key from which we want to retrieve the value.
	 * @param pDefaultValue  The default value if there is no value associated with this key.
	 * @return Value as long from specified key.
	 */
	public static long getLong(final Context pContext, final String pKey, final long pDefaultValue)
	{
		init(pContext);
		return mSharedPreferences.getLong(pKey, pDefaultValue);
	}

	public static void clearPreference(final Context pContext)
	{
		init(pContext);
		mEditor.clear().apply();
	}
}
