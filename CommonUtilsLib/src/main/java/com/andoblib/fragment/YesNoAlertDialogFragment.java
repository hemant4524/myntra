package com.andoblib.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.andoblib.listener.YesNoAlertDialogListener;

/**
 * This is the custom yes-no alert dialog fragment used to show confirm alert to the user like <br><br/>
 * "Do you really want to close the application?" and so on.
 */
public class YesNoAlertDialogFragment extends DialogFragment
{
	private int mRequestCode;
	private String mTitle;
	private String mMessage;
	private String mPositiveButtonText;
	private String mNegativeButtonText;
	private YesNoAlertDialogListener mListener;
	
	/**
	 * Empty constructor must be there to prevent crash.
	 */
	public YesNoAlertDialogFragment()
	{

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		return new AlertDialog.Builder(getActivity())
		.setTitle(mTitle)
		.setMessage(mMessage)
		
		.setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if(mListener != null)
				{
					mListener.onYesNoDialogButtonClicked(mRequestCode, YesNoAlertDialogListener.BUTTON_POSITIVE);
				}
			}
		})
		
		.setNegativeButton(mNegativeButtonText, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if(mListener != null)
				{
					mListener.onYesNoDialogButtonClicked(mRequestCode, YesNoAlertDialogListener.BUTTON_NEGATIVE);
				}
			}
		})
		
		.create();
	}

	@Override
	public void onCancel(DialogInterface dialog)
	{
		
		if(mListener != null)
			mListener.onYesNoDialogButtonClicked(mRequestCode, YesNoAlertDialogListener.DIALOG_ON_CANCEL);
		
		super.onCancel(dialog);
	}
	
	@Override
	public void onDestroyView()
	{
		if (getDialog() != null && getRetainInstance())
		{
			getDialog().setDismissMessage(null);
		}
		super.onDestroyView();
	}

	public void setRequestCode(int pRequestCode)
	{
		this.mRequestCode = pRequestCode;
	}

	public void setTitle(String pTitle)
	{
		this.mTitle = pTitle;
	}

	public void setMessage(String pMessage)
	{
		this.mMessage = pMessage;
	}

	public void setPositiveButtonText(String pPositiveButtonText)
	{
		this.mPositiveButtonText = pPositiveButtonText;
	}
	
	public void setNegativeButtonText(String pNegativeButtonText)
	{
		this.mNegativeButtonText = pNegativeButtonText;
	}
	
	public void setListener(YesNoAlertDialogListener pListener)
	{
		this.mListener = pListener;
	}
}
