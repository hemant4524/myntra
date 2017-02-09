package com.andoblib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.andoblib.constant.Constants;
import com.andoblib.customexception.CustomException;
import com.andoblib.customexception.ExceptionConstant;
import com.andoblib.fragment.ProgressDialogFragment;
import com.andoblib.fragment.YesNoAlertDialogFragment;
import com.andoblib.listener.ProgressOnCancelListener;
import com.andoblib.listener.SimpleAlertDialogListener;
import com.andoblib.listener.YesNoAlertDialogListener;
import com.andoblib.log.CustomLogHandler;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This is the common utility class used to perform all utility based
 * operations.
 */
public class CommonUtil
{
	private static final String TAG = CommonUtil.class.getSimpleName();
	private static final String TAG_FRAGMENT = "Dialog";

	private static DialogFragment mDialogFragment;

	public static void showProgressDialog(final Activity pContext, final String pTitle, final String pMessage,
			final boolean pIsCancellable, final ProgressOnCancelListener pOnCancelListener, final int pRequestCode)
	{
		if (pContext == null || pContext.isFinishing())
		{
			return;
		}

		ProgressDialog mProgressDialog = ProgressDialog.show(pContext, pTitle, pMessage, true);
	}

	/**
	 * This method is used to show indeterminate progress dialog while calling
	 * web service etc. <br>
	 * <b> By default this progress dialog is not Cancellable on outside touch.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pTitle
	 *            The title of the dialog. Pass null if you don't want title.
	 * @param pMessage
	 *            The message to be displayed on progress dialog, like
	 *            "please wait".
	 * @param pIsCancellable
	 *            Pass true if you want to make Cancellable dialog otherwise
	 *            false.
	 * @param pOnCancelListener
	 *            The listener to be called when dialog is cancelled. Used to
	 *            break long running task when user cancel the progress dialog.
	 * @param pRequestCode
	 *            The request sent during calling this method will be passed
	 *            when user cancel the dialog.
	 * @param pFragmentManager
	 *            The fragment manager object.
	 */
	public static void showProgressDialog(final Activity pContext, final String pTitle, final String pMessage,
			final boolean pIsCancellable, final ProgressOnCancelListener pOnCancelListener, final int pRequestCode,
			final FragmentManager pFragmentManager)
	{
		if (pContext == null || pContext.isFinishing())
		{
			return;
		}

		if (mDialogFragment != null && mDialogFragment.isVisible())
		{
			return;
		}

		DialogFragment mPrevious = mDialogFragment;

		mDialogFragment = new ProgressDialogFragment();


		((ProgressDialogFragment) mDialogFragment).setTitle(pTitle);
		((ProgressDialogFragment) mDialogFragment).setMessage(pMessage);
		((ProgressDialogFragment) mDialogFragment).setCancelable(pIsCancellable);
		((ProgressDialogFragment) mDialogFragment).setCanceledOnTouchOutside(false);
		((ProgressDialogFragment) mDialogFragment).setOnCancelListener(pOnCancelListener);
		((ProgressDialogFragment) mDialogFragment).setRequestCode(pRequestCode);



//		pFragmentManager.beginTransaction().add(mDialogFragment, pMessage).commitAllowingStateLoss();
		pFragmentManager.beginTransaction().add(mDialogFragment, TAG_FRAGMENT).commitAllowingStateLoss();
		if(mPrevious != null)
		{
			pFragmentManager.beginTransaction().remove(mPrevious).commitAllowingStateLoss();
		}

		// mDialogFragment.show(pFragmentManager, pMessage);

	}

	/**
	 * This method is used to show indeterminate progress dialog while calling
	 * web service etc.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pTitle
	 *            The title of the dialog. Pass null if you don't want title.
	 * @param pMessage
	 *            The message to be displayed on progress dialog, like
	 *            "please wait".
	 * @param pIsCancellable
	 *            Pass true if you want to make Cancellable dialog otherwise
	 *            false.
	 * @param pIsCanceledOnTouchOutside
	 *            Pass true if you want to make it Cancellable when user tab
	 *            outside of the progressbar otherwise pass false.
	 * @param pOnCancelListener
	 *            The listener to be called when dialog is cancelled. Used to
	 *            break long running task when user cancel the progress dialog.
	 * @param pRequestCode
	 *            The request sent during calling this method will be passed
	 *            when user cancel the dialog.
	 * @param pFragmentManager
	 *            The fragment manager object.
	 */
	public static void showProgressDialog(final Activity pContext, final String pTitle, final String pMessage,
			final boolean pIsCancellable, final boolean pIsCanceledOnTouchOutside, final ProgressOnCancelListener pOnCancelListener,
			final int pRequestCode, final FragmentManager pFragmentManager)
	{
		if (pContext == null || pContext.isFinishing())
		{
			return;
		}

		if (mDialogFragment != null && mDialogFragment.isVisible())
		{
			return;
		}

		mDialogFragment = new ProgressDialogFragment();

		((ProgressDialogFragment) mDialogFragment).setTitle(pTitle);
		((ProgressDialogFragment) mDialogFragment).setMessage(pMessage);
		((ProgressDialogFragment) mDialogFragment).setCancelable(pIsCancellable);
		((ProgressDialogFragment) mDialogFragment).setCanceledOnTouchOutside(pIsCanceledOnTouchOutside);
		((ProgressDialogFragment) mDialogFragment).setOnCancelListener(pOnCancelListener);
		((ProgressDialogFragment) mDialogFragment).setRequestCode(pRequestCode);

		pFragmentManager.beginTransaction().add(mDialogFragment, pMessage).commitAllowingStateLoss();

		// mDialogFragment.show(pFragmentManager, pMessage);

	}

	/**
	 * This method is used to cancel the progress dialog.
	 */
	public static void dismissProgressDialog()
	{
		if (mDialogFragment != null)
		{
			mDialogFragment.dismissAllowingStateLoss();
			mDialogFragment = null;
		}
	}

	/**
	 * This method is used to show the alert to the user.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pTitle
	 *            The title of the alert. Pass null if you don't want/
	 * @param pMessage
	 *            The message to be displayed on the alert.
	 * @param pButtonText
	 *            Button text for the alert.
	 * @param pIsCancellable
	 *            Pass true if you want to make it cancellable.
	 * @param pRequestCode
	 *            The request code passed back on callback.
	 * @param pListener
	 *            Listener to listen call back when dialog button is clicked,
	 *            pass null if you don't want.
	 * @param pFragmentManager
	 *            Fragment manager.
	 */
	public static void showSimpleDialog(final Activity pContext, final String pTitle, final String pMessage, final String pButtonText,
			final boolean pIsCancellable, final int pRequestCode, SimpleAlertDialogListener pListener,
			final FragmentManager pFragmentManager)
	{
		final SimpleAlertDialogListener simpleAlertDialogListener=pListener;
		if (pContext == null || pContext.isFinishing())
		{
			return;
		}

		AlertDialog.Builder mBuilder = new AlertDialog.Builder(pContext);
		mBuilder.setTitle(pTitle);
		mBuilder.setMessage(pMessage);
		mBuilder.setCancelable(pIsCancellable);
		mBuilder.setPositiveButton(pButtonText, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
				if (simpleAlertDialogListener!=null)
					simpleAlertDialogListener.onSimpleDialogButtonClicked(pRequestCode);
			}
		});
		if (pIsCancellable)
		{
			mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					dialog.cancel();
				}
			});
		}
		AlertDialog mAlert1Dialog = mBuilder.create();
		mAlert1Dialog.show();
		
		// DialogFragment m_dialogFragment = new SimpleAlertDialogFragment();
		// ((SimpleAlertDialogFragment)
		// m_dialogFragment).setRequestCode(pRequestCode);
		// ((SimpleAlertDialogFragment) m_dialogFragment).setTitle(pTitle);
		// ((SimpleAlertDialogFragment) m_dialogFragment).setMessage(pMessage);
		// ((SimpleAlertDialogFragment)
		// m_dialogFragment).setCancelable(pIsCancellable);
		// ((SimpleAlertDialogFragment)
		// m_dialogFragment).setButtonText(pButtonText);
		// ((SimpleAlertDialogFragment)
		// m_dialogFragment).setListener(pListener);
		//
		// pFragmentManager.beginTransaction().add(m_dialogFragment,
		// pMessage).commitAllowingStateLoss();

		// m_dialogFragment.show(pFragmentManager, pMessage);
	}

	/**
	 * This method is used to show yes-no type of dialog. Like
	 * "Do you want to close the application?" and so on.
	 * 
	 * @param pContext
	 *            Context of the activity.
	 * @param pTitle
	 *            The title of the alert.
	 * @param pMessage
	 *            The message of the alert.
	 * @param pPositiveButtonText
	 *            Positive button text for the alert.
	 * @param pNegativeButtonText
	 *            Nagative button text for the alert.
	 * @param pIsCancellable
	 *            Pass true to make alert dialog cancellable.
	 * @param pRequestCode
	 *            The request code to be passed back to the call back.
	 * @param pListener
	 *            The listener to listen button click even, pass null if you
	 *            don't want.
	 * @param pFragmentManager
	 *            fragment manager to show alert.
	 */
	public static void showYesNoDialog(final Activity pContext, final String pTitle, final String pMessage,
			final String pPositiveButtonText, final String pNegativeButtonText, final boolean pIsCancellable, final int pRequestCode,
			YesNoAlertDialogListener pListener, final FragmentManager pFragmentManager)
	{
		if (pContext == null || pContext.isFinishing())
		{
			return;
		}

		DialogFragment m_dialogFragment = new YesNoAlertDialogFragment();
		((YesNoAlertDialogFragment) m_dialogFragment).setRequestCode(pRequestCode);
		((YesNoAlertDialogFragment) m_dialogFragment).setTitle(pTitle);
		((YesNoAlertDialogFragment) m_dialogFragment).setMessage(pMessage);
		((YesNoAlertDialogFragment) m_dialogFragment).setCancelable(pIsCancellable);
		((YesNoAlertDialogFragment) m_dialogFragment).setPositiveButtonText(pPositiveButtonText);
		((YesNoAlertDialogFragment) m_dialogFragment).setNegativeButtonText(pNegativeButtonText);
		((YesNoAlertDialogFragment) m_dialogFragment).setListener(pListener);

		pFragmentManager.beginTransaction().add(m_dialogFragment, pMessage).commitAllowingStateLoss();

		// m_dialogFragment.show(pFragmentManager, pMessage);
	}

	/**
	 * Clear the application cache.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 */
	public static void clearCache(final Context pContext)
	{
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();
		trimCache(pContext);
	}

	private static void trimCache(Context pContext)
	{
		boolean mIsSuccess;

		try
		{
			File mDir = pContext.getCacheDir();
			if (mDir != null && mDir.isDirectory())
			{
				deleteDir(mDir);
			}
			mIsSuccess = pContext.deleteDatabase("webview.db");
			CustomLogHandler.printDebug(TAG, "====delete webview.db=====" + mIsSuccess);

			mIsSuccess = pContext.deleteDatabase("webviewCache.db");
			CustomLogHandler.printDebug(TAG, "====delete webviewCache.db=====" + mIsSuccess);
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
	}

	private static boolean deleteDir(File pDir)
	{
		if (pDir != null && pDir.isDirectory())
		{
			String[] children = pDir.list();
			for (String child : children)
			{
				boolean success = deleteDir(new File(pDir, child));
				CustomLogHandler.printDebug(TAG, "====deleteDir=====" + "p_dir=" + pDir + " child=" + child + " Result:" + success);
				if (!success)
				{
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return pDir.delete();
	}

	/**
	 * This method is used to get device's unique id. This method can also
	 * return null of unique id is not returned.
	 * 
	 * @param pContext
	 *            Context of the application.
	 * @return Unique id of the device.
	 */
	public static String getDeviceUniqueID(final Context pContext)
	{
		String mDeviceId = Secure.getString(pContext.getContentResolver(), Secure.ANDROID_ID);
		return mDeviceId;
	}

	/**
	 * This method sets the custom fonts on the view.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pTextView
	 *            View on which we want to set the custom font.
	 * @param pFontName
	 *            The name of the font which we want to set. The font must be in
	 *            "fonts" folder of "assets" folder.
	 */
	public static void applyCustomFont(final Context pContext, final TextView pTextView, final String pFontName)
	{
		Typeface mTypeFace = Typeface.createFromAsset(pContext.getAssets(), "fonts" + File.separator + pFontName);
		pTextView.setTypeface(mTypeFace);
	}

	// http://stackoverflow.com/questions/6656540/android-convert-px-to-dp-video-aspect-ratio
	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param p_dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param p_context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	/*
	 * public static int convertDpToPixel(final float p_dp, final Context
	 * p_context) { Resources m_resources = p_context.getResources();
	 * DisplayMetrics metrics = m_resources.getDisplayMetrics(); int p_pixel =
	 * (int) (p_dp * (metrics.densityDpi / 160f)); return p_pixel; }
	 */

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param p_pixel
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param p_context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	/*
	 * public static float convertPixelsToDp(final float p_pixel, final Context
	 * p_context) { Resources m_resources = p_context.getResources();
	 * DisplayMetrics m_metrics = m_resources.getDisplayMetrics(); float m_dp =
	 * p_pixel / (m_metrics.densityDpi / 160f); return m_dp; }
	 */
	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param pDp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param pContext
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static int convertDpToPixel(final float pDp, final Context pContext)
	{
		DisplayMetrics mMetrics = new DisplayMetrics();
		mMetrics = pContext.getResources().getDisplayMetrics();
		float mLogicalDensity = mMetrics.density;
		int mPx = (int) Math.ceil(pDp * mLogicalDensity);
		return mPx;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param pPixel
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param pContext
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static int convertPixelsToDp(final float pPixel, final Context pContext)
	{
		DisplayMetrics mMetrics = new DisplayMetrics();
		mMetrics = pContext.getResources().getDisplayMetrics();
		float mLogicalDensity = mMetrics.density;
		int mDp = (int) Math.ceil(pPixel / mLogicalDensity);
		return mDp;
	}

	/**
	 * This method finds the display height.
	 * 
	 * @param pContext
	 *            Context of the activity.
	 * @return Device height in pixels.
	 */
	public static int getDisplayHeight(final Context pContext)
	{
		DisplayMetrics mMetrics = pContext.getResources().getDisplayMetrics();
		return mMetrics.heightPixels;
	}

	/**
	 * This method finds the display width.
	 * 
	 * @param pContext
	 *            Context of the activity.
	 * @return Device width in pixels.
	 */
	public static int getDisplayWidth(final Context pContext)
	{
		DisplayMetrics mMetrics = pContext.getResources().getDisplayMetrics();
		return mMetrics.widthPixels;
	}

	/**
	 * This method is used to read the string data from the given inputstream.
	 * 
	 * @param pInputStream
	 *            The input stream that we want to read.
	 * @return String data from the file.
	 */
	public static String getStringFromStream(final InputStream pInputStream)
	{
		StringBuilder mBuilder = new StringBuilder();

		try
		{
			byte[] mBuf = new byte[1024];
			int mLength;
			while ((mLength = pInputStream.read(mBuf)) > 0)
			{
				mBuilder.append(new String(mBuf, 0, mLength));
			}
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		finally
		{
			if (pInputStream != null)
			{
				try
				{
					pInputStream.close();
				}
				catch (Exception p_e)
				{
					CustomLogHandler.printError(p_e);
				}
			}
		}

		return mBuilder.toString();
	}

	/**
	 * This method is used to get the application directory path. e.g.
	 * mnt/sdcard/Android/data/com.officebrain.appname
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @return Application directory path.
	 */
	public static String getApplicationDirPath(final Context pContext)
	{
		String mPath = null;
		final String mStaticPath = "Android" + File.separator + "data" + File.separator + pContext.getPackageName();

		boolean mExistOrCreated = false;
		File mFile = null;

		/**
		 * Check If sdcard exist.
		 */
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + mStaticPath;

			mFile = new File(mPath);

			if (!mFile.exists())
			{
				mExistOrCreated = mFile.mkdirs();
				CustomLogHandler.printDebug(TAG, "====getApplicationDirPath Directory Created===" + mPath);
			}
			// Its already exist. So no need to create it.
			else
			{
				CustomLogHandler.printDebug(TAG, "====getApplicationDirPath Directory already exist===" + mPath);
				mExistOrCreated = true;
			}

			// Directory created successfully.
			if (mExistOrCreated)
			{
				CustomLogHandler.printDebug(TAG, "====getApplicationDirPath Path===" + mPath);
				return mPath;
			}
			else
			{
				CustomLogHandler.printDebug(TAG, "====getApplicationDirPath Path===" + "null");
				return null;
			}
		}
		else
		{
			mPath = pContext.getCacheDir().getAbsolutePath();
			CustomLogHandler.printDebug(TAG, "====getApplicationDirPath Path===" + mPath);
			return mPath;
		}
	}

	/**
	 * This method is used to check whether an app exists or not.
	 * 
	 * @param pAppIntent
	 *            The intent of the application through which we want to share.
	 * @param pContext
	 *            The context of the activity.
	 * @return True if the application is installed otherwise false.
	 */
	public static boolean isAppInstalled(final Intent pAppIntent, final Context pContext)
	{
		if (pContext.getPackageManager().queryIntentActivities(pAppIntent, 0).size() == 0)
			return false;
		else
			return true;

		/*
		 * if (pAppIntent.resolveActivity(pContext.getPackageManager()) != null)
		 * { return true; } else { return false; }
		 */
	}

	/**
	 * This method is used to share the content or file vial gmail.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pToEmails
	 *            The array of to email addresses.
	 * @param pCcEmails
	 *            The array of cc email addresses.
	 * @param pSubject
	 *            The subject of the email.
	 * @param pSpannedBody
	 *            The body of the message like Html.fromHtml(new
	 *            StringBuilder().append(
	 *            "This is the demo application. &ltp&gt&ltb>Some Content&lt/b&gt&lt/p>"
	 *            ).append("&ltsmall&gt&ltp>More content&lt/p&gt&lt/small&gt").
	 *            toString())
	 * @param pAttachments
	 *            The list of the attachments as files. Pass null if there is no
	 *            any attachment.
	 * @throws CustomException
	 *             If any error occurs. <br/>
	 * 
	 *             <b>Note:</b> Html.fromHtml(String) does not support all HTML
	 *             tags. In this very case, &ltul&gt and &ltli&gt are not
	 *             supported. <br/>
	 *             <b>Allowed Html Tags are </b> br, p, div, em, b, strong,
	 *             cite, dfn, i, big, small, font, blockquote, tt, monospace, a,
	 *             u, sup, sub
	 */
	public static void shareViaGmail(final Context pContext, final String pToEmails[], final String pCcEmails[], final String pSubject,
			final Spanned pSpannedBody, final File pAttachments[]) throws CustomException
	{
		ArrayList<Uri> mAttachments = new ArrayList<Uri>();

		Intent mIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		mIntent.setType("plain/text");
		mIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
		mIntent.putExtra(Intent.EXTRA_EMAIL, pToEmails);
		mIntent.putExtra(Intent.EXTRA_CC, pCcEmails);
		mIntent.putExtra(Intent.EXTRA_SUBJECT, pSubject);

		if (pSpannedBody != null)
		{
			mIntent.putExtra(Intent.EXTRA_TEXT, pSpannedBody);
		}

		if (pAttachments != null)
		{
			for (int i = 0; i < pAttachments.length; i++)
			{
				mAttachments.add(Uri.fromFile(pAttachments[i]));
			}
			mIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mAttachments);
		}
		// Check if gmail application is installed in your device or not.
		if (isAppInstalled(mIntent, pContext))
		{
			pContext.startActivity(mIntent);
		}
		else
		{
			throw new CustomException(Constants.ERR_GMAIL_APP_IS_NOT_INSTALLED, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
	}

	/**
	 * Used to share photos/images via facebook. Facebook does not allow to
	 * share text but only http link. And even http link can only be shared by
	 * using ACTION_SEND and not using ACTION_SEND_MULTIPLE. <br/>
	 * https://developers.facebook.com/bugs/332619626816423
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pAttachments
	 *            The attachment if any otherwise null.
	 * @throws CustomException
	 *             If any error.
	 */
	public static void shareImagesViaFacebook(final Context pContext, final File pAttachments[]) throws CustomException
	{
		ArrayList<Uri> mAttachments = new ArrayList<Uri>();

		Intent mIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		mIntent.setType("image/*");
		mIntent.setPackage("com.facebook.katana");

		if (pAttachments != null)
		{
			for (int i = 0; i < pAttachments.length; i++)
			{
				mAttachments.add(Uri.fromFile(pAttachments[i]));
			}
			mIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mAttachments);
		}
		// Check if facebook application is installed in your device or not.
		if (isAppInstalled(mIntent, pContext))
		{
			pContext.startActivity(mIntent);
		}
		else
		{
			throw new CustomException(Constants.ERR_FACEBOOK_APP_IS_NOT_INSTALLED, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
	}

	/**
	 * Used to share link via facebook. Facebook does not allow to share text
	 * but only http link. And even http link can only be shared by using
	 * ACTION_SEND and not using ACTION_SEND_MULTIPLE. <br/>
	 * https://developers.facebook.com/bugs/332619626816423
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pBody
	 *            The body or text that you want to share via facebook. But
	 *            facebook only support link in its extra text field.
	 * @throws CustomException
	 *             if any error.
	 */
	public static void shareLinkViaFacebook(final Context pContext, final String pBody) throws CustomException
	{
		Intent mIntent = new Intent(Intent.ACTION_SEND);
		mIntent.setType("text/plain");
		mIntent.setPackage("com.facebook.katana");

		if (pBody != null)
		{
			mIntent.putExtra(Intent.EXTRA_TEXT, pBody);
		}
		// Check if facebook application is installed in your device or not.
		if (isAppInstalled(mIntent, pContext))
		{
			pContext.startActivity(mIntent);
		}
		else
		{
			throw new CustomException(Constants.ERR_FACEBOOK_APP_IS_NOT_INSTALLED, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
	}

	public static void shareImagesViaTwitter(final Context pContext, final File pAttachments[]) throws CustomException
	{
		ArrayList<Uri> mAttachments = new ArrayList<Uri>();

		Intent mIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		mIntent.setType("image/*");
		mIntent.setPackage("com.twitter.android");

		/*
		 * if (pAttachments != null) { for (int i = 0; i < pAttachments.length;
		 * i++) { mAttachments.add(Uri.fromFile(pAttachments[i])); }
		 * mIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
		 * mAttachments); }
		 */
		// Check if facebook application is installed in your device or not.
		if (isAppInstalled(mIntent, pContext))
		{
			pContext.startActivity(mIntent);
		}
		else
		{
			throw new CustomException(Constants.ERR_TWITTER_APP_IS_NOT_INSTALLED, ExceptionConstant.ERROR_CODE_CUSTOM);
		}
	}

	/**
	 * This method is used to hide the keyboard.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pView
	 *            The view reference from which you want to hide the keyboard.
	 */
	public static void hideKeyboard(final Context pContext, final View pView)
	{
		InputMethodManager imm = (InputMethodManager) pContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(pView.getWindowToken(), 0);
	}

	/**
	 * This method is used to show the keyboard.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pView
	 *            The view reference from which you want to show the keyboard.
	 */
	public static void showKeyboard(final Context pContext, final View pView)
	{
		InputMethodManager imm = (InputMethodManager) pContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(pView, 0);
	}
}
