package com.andoblib.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.andoblib.listener.SimpleAlertDialogListener;

/**
 * This is the custom simple alert dialog fragment used to show alert to the user.
 */
public class SimpleAlertDialogFragment extends DialogFragment
{
	private int mRequestCode;
	private String mTitle;
	private String mMessage;
	private String mButtonText;
	private SimpleAlertDialogListener mListener;

	/**
	 * Empty constructor must be there to prevent crash.
	 */
	public SimpleAlertDialogFragment()
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
		.setPositiveButton(mButtonText, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if(mListener != null)
					mListener.onSimpleDialogButtonClicked(mRequestCode);
			}
		}).create();
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

	public void setButtonText(String pButtonText)
	{
		this.mButtonText = pButtonText;
	}

	public void setListener(SimpleAlertDialogListener pListener)
	{
		this.mListener = pListener;
	}
}
