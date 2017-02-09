package com.andoblib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

/**
 * This class is used to validate the fields against different types like phone number, email address etc.
 */
public class Validator
{
	public final static String EMAIL_REGEX = "^[_a-z0-9-+]+(\\.[_a-z0-9-+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,8})$";
	public final static String NUMERIC_REGEX = "^-?\\d*(\\.\\d+)?$";
	public final static String ALL_DIGITS_REGEX = "^\\d+$";
	
	public static boolean IS_ERROR = false;

	/**
	 * This method validate the field for null and empty values.
	 * 
	 * @param pEditText
	 *            The edittext that we want to validate.
	 * @param pErrMessage
	 *            The message to display when validation fails to validate.
	 * @return true if field is validated otherwise false. You can use IS_ERROR static member of the class to check for the error for multiple fields.
	 */
	public static boolean validateForEmptyOrNull(final EditText pEditText, final String pErrMessage)
	{
		if (pEditText != null && pEditText.getText() != null && pEditText.getText().toString().length() > 0)
		{
			return true;
		}
		else
		{
			if (pEditText != null)
			{
				pEditText.setError(pErrMessage);
			}
			IS_ERROR = true;
			return false;
		}
	}

	/**
	 * Tests an input string against the null value or trimmed length greater than zero
	 * 
	 * @param input
	 *            The string that we want to check against null or empty.
	 * @return true if string is null or empty.
	 */
	private static boolean isNullOrEmpty(String input)
	{
		return input == null || input.trim().length() == 0;
	}

	/**
	 * Tests an input string for a valid email address against the following regular expression {@value #EMAIL_REGEX}
	 * 
	 * @param pEditText
	 *            The EditText that we want to check.
	 * @param pErrMessage
	 *            The error message that we want to set in edittext if it is not valid email address.
	 * @return true if valid email otherwise false.
	 */
	public static boolean validateForEmail(final EditText pEditText, final String pErrMessage)
	{
		boolean mIsVallidated = false;
		if (isNullOrEmpty(pEditText.getText().toString()))
		{
			pEditText.setError(pErrMessage);
			IS_ERROR = true;
			return false;

		}

		mIsVallidated = pEditText.getText().toString().matches(EMAIL_REGEX);
		if (!mIsVallidated)
		{
			pEditText.setError(pErrMessage);
			IS_ERROR = true;
		}

		return mIsVallidated;
	}
	
	/**
	 * Tests an input string for a valid email address against the following regular expression {@value #EMAIL_REGEX}
	 * 
	 * @param pEditText
	 *            The EditText that we want to check.
	 * @return true if valid email otherwise false.
	 */
	public static boolean validateForEmail(final EditText pEditText)
	{
		boolean mIsVallidated = false;
		if (isNullOrEmpty(pEditText.getText().toString()))
		{
			return false;
		}

		mIsVallidated = pEditText.getText().toString().matches(EMAIL_REGEX);

		return mIsVallidated;
	}

	/**
	 * This method is used to validate for mobile number. Mobile number must be 10 digit numeric number only.
	 * 
	 * @param pEditText
	 *            The edit text that we want to validate.
	 * @param pErrMessage
	 *            Error message to be displayed if field is not validated.
	 * @return true if field is validated otherwise false.
	 */
	public static boolean validateForPhone(final EditText pEditText, final String pErrMessage)
	{
		boolean mIsVallidated = false;
		Pattern pattern = Pattern.compile("\\d{10}");
		Matcher matchr = pattern.matcher(pEditText.getText().toString());

		mIsVallidated = matchr.matches();

		if (!mIsVallidated)
		{
			pEditText.setError(pErrMessage);
			IS_ERROR = true;
		}

		return mIsVallidated;
	}

	/**
	 * Tests an input string can be parsed to a numeric data type using the following regular expression: {@value #NUMERIC_REGEX}
	 * 
	 * @param pInput
	 *            The string to be validated.
	 * @return True if validated otherwise false.
	 */
	public static boolean isNumeric(String pInput)
	{
		if (isNullOrEmpty(pInput))
		{
			return false;
		}
		return pInput.matches(NUMERIC_REGEX);
	}

	/**
	 * Tests first against a regular expression that the input string is all digits and then checks that the parsed value is greater than zero (zero value is not positive but passes the regular expression test)
	 * 
	 * @param pInput The string that we want to check for the validation.
	 * @return True if validated otherwise false.
	 */
	public static boolean isPositiveInteger(String pInput)
	{
		if (isNullOrEmpty(pInput))
		{
			return false;
		}

		return pInput.matches(ALL_DIGITS_REGEX) && Integer.parseInt(pInput) > 0;
	}

}
