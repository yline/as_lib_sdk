package com.yline.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.yline.application.BaseApplication;
import com.yline.log.LogFileUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * 功能一、获得屏幕相关的辅助类
 * 功能二、单位转换
 */
public class UIScreenUtil
{

	public UIScreenUtil()
	{
		/** 实例化失败 */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * dp to px
	 *
	 * @param context 上下文
	 * @param dpValue dp
	 * @return px
	 */
	public static int dp2px(Context context, float dpValue)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * sp to px
	 *
	 * @param context 上下文
	 * @param spValue sp
	 * @return px
	 */
	public static int sp2px(Context context, float spValue)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * px to dp
	 *
	 * @param context 上下文
	 * @param pxValue px
	 * @return dp
	 */
	public static float px2dp(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxValue / scale);
	}

	/**
	 * px to sp
	 *
	 * @param context 上下文
	 * @param pxValue px
	 * @return sp
	 */
	public static float px2sp(Context context, float pxValue)
	{
		return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context 上下文
	 * @return such as 720 if success
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获取当前屏幕的绝对宽度,(排除状态栏、底部栏、横竖屏等因素)
	 *
	 * @param context 上下文
	 * @return such as 720 if success
	 */
	public static int getAbsoluteScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		int widthPixels = displayMetrics.widthPixels;
		int heightPixels = displayMetrics.heightPixels;

		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
		{
			try
			{
				widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
				heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
			} catch (IllegalAccessException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenWidth<17 IllegalAccessException", e);
			} catch (IllegalArgumentException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenWidth<17 IllegalArgumentException", e);
			} catch (InvocationTargetException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenWidth<17 InvocationTargetException", e);
			} catch (NoSuchMethodException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenWidth<17 NoSuchMethodException", e);
			}
		}

		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 17)
		{
			try
			{
				Point realSize = new Point();
				Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
				widthPixels = realSize.x;
				heightPixels = realSize.y;
			} catch (IllegalAccessException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight<17 IllegalAccessException", e);
			} catch (NoSuchMethodException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight<17 NoSuchMethodException", e);
			} catch (InvocationTargetException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight<17 InvocationTargetException", e);
			}
		}

		return Math.min(widthPixels, heightPixels);
	}

	/**
	 * 获取当前屏幕的绝对高度,(排除状态栏、底部栏、横竖屏等因素)
	 *
	 * @param context 上下文
	 * @return such as 1280 if success
	 */
	public static int getAbsoluteScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		int widthPixels = displayMetrics.widthPixels;
		int heightPixels = displayMetrics.heightPixels;

		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
		{
			try
			{
				widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
				heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
			} catch (IllegalAccessException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight<17 IllegalAccessException", e);
			} catch (NoSuchMethodException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight<17 NoSuchMethodException", e);
			} catch (InvocationTargetException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight<17 InvocationTargetException", e);
			}
		}

		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 17)
		{
			try
			{
				Point realSize = new Point();
				Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
				widthPixels = realSize.x;
				heightPixels = realSize.y;
			} catch (IllegalAccessException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight>=17 IllegalAccessException", e);
			} catch (IllegalArgumentException e)
			{
				LogFileUtil.e(BaseApplication.TAG,
						"ScreenUtil getAbsoluteScreenHeight>=17 IllegalArgumentException",
						e);
			} catch (InvocationTargetException e)
			{
				LogFileUtil.e(BaseApplication.TAG,
						"ScreenUtil getAbsoluteScreenHeight>=17 InvocationTargetException",
						e);
			} catch (NoSuchMethodException e)
			{
				LogFileUtil.e(BaseApplication.TAG, "ScreenUtil getAbsoluteScreenHeight>=17 NoSuchMethodException", e);
			}
		}

		return Math.max(widthPixels, heightPixels);
	}

	/**
	 * 获得屏幕高度
	 *
	 * @param context 上下文
	 * @return such as 1184 if success
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏高度
	 *
	 * @param context 上下文
	 * @return such as 50 if success
	 */
	public static int getStatusHeight(Context context)
	{
		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			LogFileUtil.e(BaseApplication.TAG, "ScreenUtil -> getStatusHeight Exception", e);
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 *
	 * @param activity 上下文
	 * @return bitmap of screen
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity 上下文
	 * @return bitmap of screen without status bar
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
}