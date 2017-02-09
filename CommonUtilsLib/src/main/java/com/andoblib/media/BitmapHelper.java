package com.andoblib.media;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * This method is used to perform image utility functions.
 */
public class BitmapHelper
{
	/**
	 * Used to get the completely rounded shape bitmap with specified height and width.
	 * 
	 * @param p_scaleBitmapImage
	 *            The bitmap that we want to make it rounded.
	 * @param p_targetWidth
	 *            The targeted width.
	 * @param p_targetHeight
	 *            The targeted height.
	 * @return Rounded shape bitmap.
	 */
	public static Bitmap getRoundedShapeBitmap(final Bitmap p_scaleBitmapImage, final int p_targetWidth, final int p_targetHeight)
	{
		Bitmap targetBitmap = Bitmap.createBitmap(p_targetWidth, p_targetHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) p_targetWidth - 1) / 2, ((float) p_targetHeight - 1) / 2,
				(Math.min(((float) p_targetWidth), ((float) p_targetHeight)) / 2), Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = p_scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, p_targetWidth,
				p_targetHeight), null);
		return targetBitmap;
	}

	/**
	 * This method is used to convert the bitmap image into completely rounded shape.
	 * 
	 * @param pOriginalBitmap
	 *            The bitmap that we want to convert into rounded shape.
	 * @return Rounded bitmap.
	 */
	public static Bitmap getRoundedShapeBitmap(Bitmap pOriginalBitmap)
	{
		Bitmap mTargetBitmap = Bitmap.createBitmap(pOriginalBitmap.getWidth(), pOriginalBitmap.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(mTargetBitmap);
		Path path = new Path();
		path.addCircle(((float) pOriginalBitmap.getWidth() - 1) / 2, ((float) pOriginalBitmap.getHeight() - 1) / 2,
				(Math.min(((float) pOriginalBitmap.getWidth()), ((float) pOriginalBitmap.getHeight())) / 2), Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = pOriginalBitmap;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, pOriginalBitmap.getWidth(),
				pOriginalBitmap.getHeight()), null);
		return mTargetBitmap;
	}

	/**
	 * This method is used to get rounded shape bitmap with specified rounded angle.
	 * 
	 * @param pOriginalBitmap
	 *            The bitmap that we want to convert into rounded shape.
	 * @param p_roundAngle
	 *            The angle of the image rounded shape.
	 * @return Rounded bitmap.
	 */
	public static Bitmap getRoundedShapeBitmap(Bitmap pOriginalBitmap, float p_roundAngle)
	{
		// Source image size
		int width = pOriginalBitmap.getWidth();
		int height = pOriginalBitmap.getHeight();

		// create result bitmap output
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		// set canvas for painting
		Canvas canvas = new Canvas(result);
		canvas.drawARGB(0, 0, 0, 0);

		// configure paint
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);

		// configure rectangle for embedding
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);

		// draw Round rectangle to canvas
		canvas.drawRoundRect(rectF, p_roundAngle, p_roundAngle, paint);

		// create Xfer mode
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// draw source image to canvas
		canvas.drawBitmap(pOriginalBitmap, rect, rect, paint);

		// return final image
		return result;
	}

	/**
	 * This method is used to draw shadow around given bitmap.
	 * @param pOriginalBitmap  The bitmap that we want to convert into shadow bitmap.
	 * @param shadowSize The size of the shadow around the bitmap.
	 * @return Shadowed bitmap.
	 */
	public static Bitmap getShadowBitmap(Bitmap pOriginalBitmap, int shadowSize)
	{
		// create new bitmap, which will be painted and becomes result image
		Bitmap bmOut = Bitmap.createBitmap(pOriginalBitmap.getWidth() + shadowSize, pOriginalBitmap.getHeight() + shadowSize, Bitmap.Config.ARGB_8888);
		// setup canvas for painting
		Canvas canvas = new Canvas(bmOut);
		// setup default color
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		// create a blur paint for capturing alpha
		Paint ptBlur = new Paint();
		ptBlur.setMaskFilter(new BlurMaskFilter(15, Blur.NORMAL));
		int[] offsetXY = new int[2];
		// capture alpha into a bitmap
		Bitmap bmAlpha = pOriginalBitmap.extractAlpha(ptBlur, offsetXY);
		// create a color paint
		Paint ptAlphaColor = new Paint();
		ptAlphaColor.setColor(0xFFFFFFFF);
		// paint color for captured alpha region (bitmap)
		canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
		// free memory
		bmAlpha.recycle();

		// paint the image source
		canvas.drawBitmap(pOriginalBitmap, 0, 0, null);

		// return out final image
		return bmOut;
	}

	/**
	 * Scale and keep aspect ratio
	 * 
	 * @param pBitmap
	 *            The bitmap that needs to be scaled.
	 * @param pWidth
	 *            The width of the bitmap.
	 * @return Scaled bitmap.
	 */
	static public Bitmap scaleToFitWidth(Bitmap pBitmap, int pWidth)
	{
		float mFactor = pWidth / (float) pBitmap.getWidth();
		return Bitmap.createScaledBitmap(pBitmap, pWidth, (int) (pBitmap.getHeight() * mFactor), false);
	}

	/**
	 * Scale and keep aspect ratio
	 * 
	 * @param pBitmap
	 *            The bitmap that needs to be scaled.
	 * @param pHeight
	 *            The height of the bitmap.
	 * @return Scaled bitmap.
	 */
	static public Bitmap scaleToFitHeight(Bitmap pBitmap, int pHeight)
	{
		float mFactor = pHeight / (float) pBitmap.getHeight();
		return Bitmap.createScaledBitmap(pBitmap, (int) (pBitmap.getWidth() * mFactor), pHeight, false);
	}

	/**
	 * Scale and keep aspect ratio
	 * 
	 * @param pBitmap
	 *            The bitmap that needs to be scaled.
	 * @param pWidth
	 *            The width of the bitmap.
	 * @param pHeight
	 *            The height of the bitmap.
	 * @return Scaled bitmap.
	 */
	static public Bitmap scaleToFill(Bitmap pBitmap, int pWidth, int pHeight)
	{
		float mFactorH = pHeight / (float) pBitmap.getWidth();
		float mFactorW = pWidth / (float) pBitmap.getWidth();
		float mFactorToUse = (mFactorH > mFactorW) ? mFactorW : mFactorH;
		return Bitmap.createScaledBitmap(pBitmap, (int) (pBitmap.getWidth() * mFactorToUse), (int) (pBitmap.getHeight() * mFactorToUse), false);
	}

	/**
	 * Scale and don't keep aspect ratio
	 * 
	 * @param pBitmap
	 *            The bitmap that needs to be scaled.
	 * @param pWidth
	 *            The width of the bitmap.
	 * @param pHeight
	 *            The height of the bitmap.
	 * @return Scaled bitmap.
	 */
	static public Bitmap strechToFill(Bitmap pBitmap, int pWidth, int pHeight)
	{
		float mFactorH = pHeight / (float) pBitmap.getHeight();
		float mFactorW = pWidth / (float) pBitmap.getWidth();
		return Bitmap.createScaledBitmap(pBitmap, (int) (pBitmap.getWidth() * mFactorW), (int) (pBitmap.getHeight() * mFactorH), false);
	}
}
