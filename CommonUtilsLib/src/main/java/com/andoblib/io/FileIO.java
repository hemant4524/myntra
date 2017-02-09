package com.andoblib.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.andoblib.log.CustomLogHandler;

/**
 * This is the common fileio class used to perform all file related tasks.
 */
public class FileIO
{
	private static final String TAG = FileIO.class.getSimpleName();

	/**
	 * This method copy the file from source to destination.
	 * 
	 * @param pSourceFile
	 *            Full path of source file including file name with extention. <i>e.g.</i> /storage/sdcard/test.txt.
	 * @param pDestinationDir
	 *            Destination directory path where you want to copy the file.
	 * @param pDestinationFileName
	 *            Name of the destination file name, pass null if you want to give same name as source file name.
	 * @param pShouldOverWrite
	 *            Pass true to overwrite the existing file on destination directory if exist, otherwise false.
	 * @return true if file copied successfully.
	 */
	public static boolean copyFile(final String pSourceFile, String pDestinationDir, final String pDestinationFileName, final boolean pShouldOverWrite)
	{
		try
		{
			CustomLogHandler.printDebug(TAG, "===Source File===" + pSourceFile);
			CustomLogHandler.printDebug(TAG, "===DestinationDir===" + pDestinationDir);
			CustomLogHandler.printDebug(TAG, "===DestinationFileName===" + pDestinationFileName);

			boolean mSuccess = false;
			File mDestinationDir = new File(pDestinationDir);

			// Make destination directories if not exist.
			if (!mDestinationDir.exists())
			{

				mSuccess = mDestinationDir.mkdirs();
				CustomLogHandler.printDebug(TAG, "===isDestinationDirCreated===" + mSuccess);
			}
			
			// Append file separator if not exist.
			pDestinationDir = getFileSeparatedDir(pDestinationDir);
			
			CustomLogHandler.printDebug(TAG, "===DestinationDir===" + pDestinationDir);

			File mSourceFile = new File(pSourceFile);
			File mDestinationFile = null;

			if (pDestinationFileName != null)
			{
				mDestinationFile = new File(pDestinationDir, pDestinationFileName);
			}
			else
			{
				mDestinationFile = new File(pDestinationDir, getFileName(pSourceFile));
			}

			if (!pShouldOverWrite)
			{
				if (mDestinationFile.exists())
				{
					CustomLogHandler.printDebug(TAG, "===File Already Exist===");
					return false;
				}
			}

			copyStream(new FileInputStream(mSourceFile), new FileOutputStream(mDestinationFile));

			CustomLogHandler.printDebug(TAG, "===File Coppied Successfully===");

			return true;
		}
		catch (final Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}

		return false;
	}
	
	/**
	 * This method is used to append file separator at the end of the directory if not exist. <br><br/>
	 * <b>E.g. 1.</b> Input : mnt/sdcard. Output : mnt/sdcard/. <br><br/>
	 * <b>2.</b> Input : mnt/sdcard/. Output : mnt/sdcard/. 
	 * @param pDirPath The directory in which we want to append file separator.
	 * @return The directory path with file separator.
	 */
	public static String getFileSeparatedDir(String pDirPath)
	{
		if(pDirPath == null)
		{
			return "";
		}
		
		// Append file separator if not exist.
		char mLastCharacter = pDirPath.charAt(pDirPath.length() - 1);
		if (mLastCharacter != File.separatorChar)
		{
			pDirPath = pDirPath + File.separator;
		}
		return pDirPath;
	}

	/**
	 * This method is used to copy the stream from input stream to output stream.
	 * 
	 * @param pInputStream
	 *            InputSream from where you want to copy.
	 * @param pOutputStream
	 *            OutPutStream to where you want to copy.
	 * @return True if success otherwise false/Exception will be thrown.
	 * @throws IOException
	 *             If any error.
	 */
	public static boolean copyStream(final InputStream pInputStream, final OutputStream pOutputStream) throws IOException
	{
		try
		{
			// Copy the bits from instream to outstream
			byte[] mBuf = new byte[1024];
			int mLength;
			while ((mLength = pInputStream.read(mBuf)) > 0)
			{
				pOutputStream.write(mBuf, 0, mLength);
			}
			pOutputStream.flush();

			return true;
		}
		catch (Exception p_e)
		{
			throw p_e;
		}
		finally
		{
			if (pOutputStream != null)
			{
				pOutputStream.close();
			}
			if (pInputStream != null)
			{
				pInputStream.close();
			}
		}
	}

	/**
	 * This method copies the give bytes to the specified file.
	 * 
	 * @param pBytes
	 *            Bytes that needs to be written.
	 * @param pDestinationFileName
	 *            To which file you want to write.
	 * @return true if bytes copied successfully copied otherwise false;
	 */
	public static boolean writeBytesToFile(final byte pBytes[], final String pDestinationFileName)
	{
		FileOutputStream mOutputStream = null;
		try
		{
			CustomLogHandler.printDebug(TAG, "===DestinationFilePath===" + pDestinationFileName);

			File mDestinationDir = new File(getDirectories(pDestinationFileName));

			if (!mDestinationDir.exists())
			{
				boolean mSuccess = mDestinationDir.mkdirs();
				CustomLogHandler.printDebug(TAG, "===Destination Directory Created===" + mSuccess);
			}

			File mDestinationFile = new File(pDestinationFileName);
			mOutputStream = new FileOutputStream(mDestinationFile);
			mOutputStream.write(pBytes, 0, pBytes.length);
			mOutputStream.flush();
			return true;
		}
		catch (final Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		finally
		{
			try
			{
				if (mOutputStream != null)
				{
					mOutputStream.close();
				}
			}
			catch (Exception p_e)
			{
				CustomLogHandler.printError(p_e);
			}
		}
		return false;
	}

	/**
	 * This method is used to extract the directory hierarchy from the specified full name of the file.
	 * 
	 * @param pFilePath
	 *            The file name from which we want directory structure.
	 * @return Directory structure.
	 */
	public static String getDirectories(final String pFilePath)
	{
		CustomLogHandler.printDebug(TAG, "===getDirectories===" + pFilePath);
		String mDirectories = null;
		int mLastIndexOfSeparator = pFilePath.lastIndexOf(File.separator) + 1;
		mDirectories = pFilePath.substring(0, mLastIndexOfSeparator);
		CustomLogHandler.printDebug(TAG, "===getDirectories===" + mDirectories);
		return mDirectories;
	}

	public static String getFileName(final String pFileName)
	{
		CustomLogHandler.printDebug(TAG, "===getFileName===" + pFileName);

		String mFileName = null;
		int mLastIndexOfSeparator = pFileName.lastIndexOf(File.separator);

		if (mLastIndexOfSeparator != -1)
		{
			mLastIndexOfSeparator = mLastIndexOfSeparator + 1;
			mFileName = pFileName.substring(mLastIndexOfSeparator);
		}
		else
		{
			mFileName = pFileName;
		}

		CustomLogHandler.printDebug(TAG, "===getFileName===" + mFileName);

		return mFileName;
	}

	/**
	 * This method copies the content from give input stream to the specified file.
	 * 
	 * @param pInputStream
	 *            InputSream from where you want to copy.
	 * @param pDestinationFileName
	 *            To which file you want to write.
	 * @return true if bytes copied successfully copied otherwise false;
	 */
	public static boolean writeInputStreamToFile(final InputStream pInputStream, final String pDestinationFileName)
	{
		try
		{
			CustomLogHandler.printDebug(TAG, "===DestinationFilePath===" + pDestinationFileName);

			File mDestinationDir = new File(getDirectories(pDestinationFileName));

			if (!mDestinationDir.exists())
			{
				boolean m_success = mDestinationDir.mkdirs();
				CustomLogHandler.printDebug(TAG, "===Destination Directory Created===" + m_success);
			}

			File mDestinationFile = new File(pDestinationFileName);
			FileOutputStream mOutputStream = new FileOutputStream(mDestinationFile);
			copyStream(pInputStream, mOutputStream);

			return true;
		}
		catch (final Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return false;
	}

	/**
	 * This method copies all files and directories of sourceDir to deestinationDir. <br><br/>
	 * <b>Note:</b> Please note that this may take time based on size of the sourceDir, so its advisable to run it on separate thread and not on UI thread.
	 * 
	 * @param pSourceDir
	 *            Source directory that we want to copy.
	 * @param pDestinationDir
	 *            The destination directory to which we want to copy.
	 * @return True if successfully otherwise false.
	 */
	public static boolean copyDirectory(final File pSourceDir, final File pDestinationDir)
	{
		try
		{

			if (pSourceDir.isDirectory())
			{
				if (!pDestinationDir.exists())
				{
					pDestinationDir.mkdirs();
				}

				String[] pChildren = pSourceDir.list();
				for (int i = 0; i < pSourceDir.listFiles().length; i++)
				{
					copyDirectory(new File(pSourceDir, pChildren[i]), new File(pDestinationDir, pChildren[i]));
				}
			}
			else
			{
				InputStream mInputStream = new FileInputStream(pSourceDir);
				OutputStream mOutputStream = new FileOutputStream(pDestinationDir);
				copyStream(mInputStream, mOutputStream);
			}

			return true;
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return false;
	}

	/**
	 * This method copies one directory to another directory using file filter criteria.
	 * 
	 * @param pSourceDir
	 *            Source directory from which we want to copy directory.
	 * @param pDestinationDir
	 *            Destination directory to which we want to copy files and/or directory.
	 * @param pFileFilterExtention
	 *            Filter that determines which file you want to copy. e.g. .txt or .jpg or .txt|.jpg|.png. <br><br/>
	 *            You can include more that one file using "|" as shown in above e.g.
	 * @return true if sucess otherwise false.
	 */
	public static boolean copyDirectory(final File pSourceDir, final File pDestinationDir, final String pFileFilterExtention)
	{
		// Add multiple filter functionality using "|" separated.
		try
		{

			if (pSourceDir.isDirectory())
			{
				if (!pDestinationDir.exists())
				{
					pDestinationDir.mkdirs();
				}

				String[] mChildren = pSourceDir.list();
				for (int i = 0; i < pSourceDir.listFiles().length; i++)
				{
					copyDirectory(new File(pSourceDir, mChildren[i]), new File(pDestinationDir, mChildren[i]), pFileFilterExtention);
				}
			}
			else
			{
				String m_filters[] = pFileFilterExtention.split("\\|");

				for (int i = 0; i < m_filters.length; i++)
				{
					// Filter the file.
					if (pSourceDir.getAbsolutePath().endsWith(m_filters[i].trim()))
					{
						InputStream mInputStream = new FileInputStream(pSourceDir);
						OutputStream mOutputStream = new FileOutputStream(pDestinationDir);
						copyStream(mInputStream, mOutputStream);
					}
				}
			}

			return true;
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return false;
	}

	/**
	 * This method is used to append the text at the end of the file.
	 * 
	 * @param pText
	 *            The text that we want to append.
	 * @param pDestinationFileName
	 *            The complete name of the file including its directory. e.g. /storage/sdcard/test/test.txt. <br><br/>
	 *            If directory structure does not exist, it will create the directories and file as well.
	 * @return true if success otherwise false.
	 */
	public static boolean appendText(final String pText, final String pDestinationFileName)
	{
		BufferedWriter mBufferWritter = null;
		try
		{
			File mDestinationDir = new File(getDirectories(pDestinationFileName));

			if (!mDestinationDir.exists())
			{
				boolean mSuccess = mDestinationDir.mkdirs();
				CustomLogHandler.printDebug(TAG, "===Destination Directory Created===" + mSuccess);
			}

			File mFile = new File(pDestinationFileName);

			// if file doesnt exists, then create it
			if (!mFile.exists())
			{
				mFile.createNewFile();
			}

			// true = append file
			FileWriter mFileWritter = new FileWriter(mFile, true);
			mBufferWritter = new BufferedWriter(mFileWritter);
			mBufferWritter.write(pText);
			mBufferWritter.flush();
			return true;
		}
		catch (Exception p_e)
		{
			return false;
		}
		finally
		{
			try
			{
				if (mBufferWritter != null)
				{
					mBufferWritter.close();
				}
			}
			catch (Exception p_e)
			{
				CustomLogHandler.printError(p_e);
			}
		}
	}

	/**
	 * This method is used to append one file at the end of another file.
	 * 
	 * @param pSourceFile
	 *            Name of the source file.
	 * @param pDestinationFileName
	 *            The name of the destination file.
	 * @return true if success othewise false.
	 */
	public static boolean appendFile(final String pSourceFile, final String pDestinationFileName)
	{
		BufferedOutputStream mBufferedOutputStream = null;
		FileInputStream mInputStream = null;

		try
		{
			File mDestinationDir = new File(getDirectories(pDestinationFileName));

			if (!mDestinationDir.exists())
			{
				boolean mSuccess = mDestinationDir.mkdirs();
				CustomLogHandler.printDebug(TAG, "===Destination Directory Created===" + mSuccess);
			}

			File mFile = new File(pDestinationFileName);

			// if file doesnt exists, then create it
			if (!mFile.exists())
			{
				mFile.createNewFile();
			}

			// true = append file
			mBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(mFile, true));

			File mSourceFile = new File(pSourceFile);
			mInputStream = new FileInputStream(mSourceFile);

			byte[] mBuf = new byte[1024];
			int mLength;
			while ((mLength = mInputStream.read(mBuf)) > 0)
			{
				mBufferedOutputStream.write(mBuf, 0, mLength);
			}

			mBufferedOutputStream.flush();
			return true;
		}
		catch (Exception p_e)
		{
			return false;
		}
		finally
		{
			try
			{
				if (mBufferedOutputStream != null)
				{
					mBufferedOutputStream.close();
				}
				if (mInputStream != null)
				{
					mInputStream.close();
				}
			}
			catch (Exception p_e)
			{
				CustomLogHandler.printError(p_e);
			}
		}
	}

	/**
	 * This method is used to delete the specified directory and its all child directories and files.
	 * 
	 * @param pDirectoryPath
	 *            The full path of the directory.
	 * @return true if success otherwise false.
	 */
	public static boolean deleteDirectory(final String pDirectoryPath)
	{
		try
		{
			File mFile = new File(pDirectoryPath);

			if (mFile.isDirectory())
			{
				for (File child : mFile.listFiles())
				{
					deleteDirectory(child.getAbsolutePath());
				}
			}
			CustomLogHandler.printDebug(TAG, "===Deleted File===" + mFile.getAbsolutePath());

			mFile.delete();

			return true;
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
			return false;
		}
	}

	/**
	 * This method is used to read as text data.
	 * 
	 * @param pFile
	 *            The file that we want to read.
	 * @return text data from the specified file.
	 */
	public static String readFile(final File pFile)
	{
		StringBuilder mBuilder = new StringBuilder();
		FileInputStream mInputStream = null;

		try
		{
			mInputStream = new FileInputStream(pFile);

			byte[] mBuf = new byte[1024];
			int mLength;
			while ((mLength = mInputStream.read(mBuf)) > 0)
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
			if (mInputStream != null)
			{
				try
				{
					mInputStream.close();
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
	 * This method is used to read the file from the asset.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pFilePath
	 *            The file name including file path from asset folder. e.g. data/test.txt or test.txt <br><br/>
	 *            Where data is the folder within assets folder.
	 * @return String from the file specified.
	 */
	public static String readFileFromAsset(final Context pContext, final String pFilePath)
	{
		StringBuilder mBuilder = new StringBuilder();
		InputStream mInputStream = null;

		try
		{
			mInputStream = pContext.getAssets().open(pFilePath);

			byte[] mBuf = new byte[1024];
			int mLength;
			while ((mLength = mInputStream.read(mBuf)) > 0)
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
			if (mInputStream != null)
			{
				try
				{
					mInputStream.close();
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
	 * This method is used to copy the particular file from assets folder to desired location like sdcard.
	 * 
	 * @param pAssetsFileName
	 *            The name of the file from assets that you want to copy. <br><br/>
	 *            <b>E.g.</b> fonts/test.html, Where "fonts" is the folder name within assets folder.
	 * @param pDestinationDir
	 *            The destination directory where you want to copy the file. <br><br/>
	 *            <b>E.g.</b> storage/sdcard/test.
	 * @param pContext
	 *            The context of the activity.
	 * @param pShouldOverwrite Pass true if you want to overwrite the file on destination, otherwise false.
	 * @throws IOException if any error.
	 * @return true if file copied successfully, otherwise false.
	 */
	
	public static boolean copyFileFromAssets(final String pAssetsFileName, final String pDestinationDir, final Context pContext, final boolean pShouldOverwrite) throws IOException
	{
		AssetManager mAssetManager = pContext.getAssets();

		InputStream mInputStream = mAssetManager.open(pAssetsFileName);
		File mDestinationDir = new File(pDestinationDir);

		if (!mDestinationDir.exists())
		{
			boolean m_success = mDestinationDir.mkdirs();
			CustomLogHandler.printDebug(TAG, "===Destination Directory Created===" + m_success);
		}

		File mDestinationFile = new File(pDestinationDir, getFileName(pAssetsFileName));
		
		//File already exist and user does not want to over write the file, so just return from here.
		if(!pShouldOverwrite && mDestinationFile.exists())
		{
			return true;
		}
		FileOutputStream mOutputStream = new FileOutputStream(mDestinationFile);
		return copyStream(mInputStream, mOutputStream);

	}

	/**
	 * This method is used to copy the particular directory from assets folder to desired location like sdcard.
	 * 
	 * @param pAssetsDirPath
	 *            The name of the directory from assets that you want to copy. <br><br/>
	 *            <b>E.g.</b> fonts, Where "fonts" is the folder name within assets folder.
	 * @param pDestinationDir
	 *            The destination directory where you want to copy the directory. <br><br/>
	 *            <b>E.g.</b> storage/sdcard/test.
	 * @param pContext
	 *            The context of the activity.
	 * @param pShouldOverwrite Pass true if you want to overwrite the file on destination, otherwise false.
	 * @throws IOException if any error.
	 */
	public static void copyDirFromAssets(final String pAssetsDirPath, final String pDestinationDir, final Context pContext, final boolean pShouldOverwrite) throws IOException
	{
		AssetManager mAssetManager = pContext.getAssets();
		String mAssetsFiles[] = null;

		mAssetsFiles = mAssetManager.list(pAssetsDirPath);

		if (mAssetsFiles.length == 0)
		{
			copyFileFromAssets(pAssetsDirPath, pDestinationDir, pContext, pShouldOverwrite);
		}
		else
		{
			String mFullPath = pDestinationDir + File.separator + getFileName(pAssetsDirPath);
			File mDir = new File(mFullPath);
			if (!mDir.exists())
			{
				mDir.mkdirs();
			}

			for (int i = 0; i < mAssetsFiles.length; i++)
			{
				copyDirFromAssets(pAssetsDirPath + File.separator + mAssetsFiles[i], mFullPath, pContext, pShouldOverwrite);
			}
		}
	}

	/**
	 * This method is used to get the image drawable from the assets folder.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pFilePath
	 *            The file name including file path from asset folder. e.g. data/test.png or test.png <br><br/>
	 *            Where data is the folder within assets folder.
	 * @return Drawable from the file specified.
	 */
	public static Drawable getDrawableFromAsset(final Context pContext, final String pFilePath)
	{
		Drawable mDrawable = null;

		try
		{
			InputStream mInputStream = pContext.getAssets().open(pFilePath);
			// load image as Drawable
			mDrawable = Drawable.createFromStream(mInputStream, null);
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return mDrawable;
	}

	/**
	 * This method is used to get the image bitmap from the assets folder.
	 * 
	 * @param pContext
	 *            The context of the activity.
	 * @param pFilePath
	 *            The file name including file path from asset folder. e.g. data/test.png or test.png <br/>
	 *            Where data is the folder within assets folder.
	 * @return Drawable from the file specified.
	 */
	public static Bitmap getBitmapFromAsset(final Context pContext, final String pFilePath)
	{
		Bitmap mBitmap = null;

		try
		{
			InputStream mInputStream = pContext.getAssets().open(pFilePath);
			// load image bitmap
			mBitmap = BitmapFactory.decodeStream(mInputStream);
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return mBitmap;
	}

	/**
	 * This method returns the size of file or folder in bytes.
	 * 
	 * @param pFileOrFolder
	 *            The file or folder for which you want to determine the size.
	 * @return The number of <b>bytes</b> in this file. Returns 0 if the file does not exist.
	 */
	public static long getFileOrFolderSize(final File pFileOrFolder)
	{
		long mLength = 0;
		try
		{
			for (File file : pFileOrFolder.listFiles())
			{
				if (file.isFile())
					mLength += file.length();
				else
					mLength += getFileOrFolderSize(file);
			}
		}
		catch (final Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return mLength;
	}

	/**
	 * This method moves all files and directories of sourceFileOrDir to deestinationDir. <br>
	 * <b>Note:</b> Please note that this may take time based on size of the sourceDir, so its advisable to run it on separate thread and not on UI thread.
	 * 
	 * @param pSourceFileOrDir
	 *            Source file or directory that we want to move.
	 * @param pDestinationDir
	 *            The destination directory to which we want to move.
	 * @return True if successfully otherwise false.
	 */
	public static boolean moveFileOrDirectory(final File pSourceFileOrDir, final File pDestinationDir)
	{
		boolean mSuccess = false;

		try
		{
			if (!pDestinationDir.exists())
			{
				pDestinationDir.mkdirs();
			}

			if (pSourceFileOrDir.isDirectory())
			{
				mSuccess = copyDirectory(pSourceFileOrDir, pDestinationDir);
				if (mSuccess)
				{
					mSuccess = deleteDirectory(pSourceFileOrDir.getAbsolutePath());
				}
			}
			else
			{
				// Copy the file.
				mSuccess = copyFile(pSourceFileOrDir.getAbsolutePath(), pDestinationDir.getAbsolutePath(), null, true);
				// If file is copied successfully, then delete it.
				if (mSuccess)
				{
					mSuccess = pSourceFileOrDir.delete();
				}
			}

			if (mSuccess)
			{
				CustomLogHandler.printDebug(TAG, "====Moved From ====" + pSourceFileOrDir.getAbsolutePath() + " To " + pDestinationDir);
			}
			else
			{
				CustomLogHandler.printDebug(TAG, "====Failed To Move From ====" + pSourceFileOrDir.getAbsolutePath() + " To " + pDestinationDir);
			}

			return mSuccess;
		}
		catch (Exception p_e)
		{
			CustomLogHandler.printError(p_e);
		}
		return mSuccess;
	}
}
