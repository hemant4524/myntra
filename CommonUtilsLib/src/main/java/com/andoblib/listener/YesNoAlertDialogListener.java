package com.andoblib.listener;

/**
 * This is the interface used to listen button click of the yes-no alert dialog.
 */
public interface YesNoAlertDialogListener
{
	public static final int BUTTON_POSITIVE = 1;
	public static final int BUTTON_NEGATIVE = 2;
	public static final int DIALOG_ON_CANCEL = 3;
	
	/**
	 * Called when use press the button from yes-no alert dialog.
	 * @param pRequestCode The request code that you send when showing the dialog.
	 * @param pWhichButton Determins which button is pressed like positive or negative.
	 */
	public void onYesNoDialogButtonClicked(final int pRequestCode, final int pWhichButton);
}
