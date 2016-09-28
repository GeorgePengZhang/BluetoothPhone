package com.aura.bluetoothphone.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Toast操作工具类
 * 
 * @author 罗文忠
 * @date 2013-03-19
 * @version 1.0.0
 * 
 */
public class ToastUtil {

	private static Toast toast;

	/**
	 * 显示提示信息
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-03-19
	 * @param text
	 *            提示内容
	 */
	public static void showToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();

	}
	
	/**
	 * 显示提示信息(时间较长)
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-04-07
	 * @param text
	 *            提示内容
	 */
	public static void showLongToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		} else {
			toast.setText(text);
		}
//		toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, DisplayUtil.dip2px(Application.context, 150));
		toast.show();
		
	}

}
