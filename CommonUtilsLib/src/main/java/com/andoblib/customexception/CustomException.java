package com.andoblib.customexception;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the custom exception class used to handle several exceptions like network failure error, file not found exception.
 *
 */
public class CustomException extends Throwable implements Parcelable
{
	private static final long serialVersionUID = 4664456874499611218L;

	private String mMessage = null;
	private int mErrorCode = 0;

	/**
	 * Parameterized constructor.
	 * @param pMessage The message to be set.
	 */
	public CustomException(String pMessage)
	{
		super(pMessage);
	}

	/**
	 * Parameterized constructor.
	 * @param pThrowable The exception to be set.
	 */
	public CustomException(Throwable pThrowable)
	{
		super(pThrowable);
	}

	/**
	 * Parameterized constructor.
	 * @param pMessage The message to be set.
	 * @param pThrowable THe exception to be set.
	 * @param pErrorCode The error code to be set.
	 */
	public CustomException(String pMessage, Throwable pThrowable, int pErrorCode)
	{
		super(pMessage, pThrowable);
		this.mMessage = pMessage;
		this.mErrorCode = pErrorCode;
	}
	/**
	 * Parameterized constructor.
	 * @param pMessage The message to be set.
	 * @param pErrorCode The error code to be set.
	 */
	public CustomException(String pMessage, int pErrorCode)
	{
		super(pMessage);
		this.mMessage = pMessage;
		this.mErrorCode = pErrorCode;
	}

	/**
	 * Gets the error code.
	 * @return Error code.
	 */
	public int getErrorCode()
	{
		return mErrorCode;
	}

	/**
	 * Gets message.
	 * @return The message.
	 */
	public String getMessage()
	{
		return mMessage;
	}


	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.mMessage);
		dest.writeInt(this.mErrorCode);
	}

	protected CustomException(Parcel in)
	{
		this.mMessage = in.readString();
		this.mErrorCode = in.readInt();
	}

	public static final Creator<CustomException> CREATOR = new Creator<CustomException>()
	{
		public CustomException createFromParcel(Parcel source)
		{
			return new CustomException(source);
		}

		public CustomException[] newArray(int size)
		{
			return new CustomException[size];
		}
	};
}
