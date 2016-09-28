package com.aura.bluetoothphone.utils;

import android.content.Context;

import java.lang.reflect.Field;
/**
 * 像素转换工具类
 * @Description TODO
 * @author LiGang
 * @date 2015年11月2日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class DisplayUtil {

	/**
	 * 
	 * 获取通知栏高度
	 * 
	 * @version 1.0
	 * @createTime 2015年11月2日,下午3:16:57
	 * @updateTime 2015年11月2日,下午3:16:57
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 
	 * dp转px
	 * 
	 * @version 1.0
	 * @createTime 2015年11月2日,下午3:17:09
	 * @updateTime 2015年11月2日,下午3:17:09
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 
	 * px转dp
	 * 
	 * @version 1.0
	 * @createTime 2015年11月2日,下午3:17:18
	 * @updateTime 2015年11月2日,下午3:17:18
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * 获取屏幕宽度
	 * 
	 * @version 1.0
	 * @createTime 2015年11月2日,下午3:17:34
	 * @updateTime 2015年11月2日,下午3:17:34
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 
	 * 获取屏幕高度
	 * 
	 * @version 1.0
	 * @createTime 2015年11月2日,下午3:17:43
	 * @updateTime 2015年11月2日,下午3:17:43
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

}
