package com.aura.bluetoothphone.executor;

import android.content.Context;
import android.content.DialogInterface.OnDismissListener;

import com.aura.bluetoothphone.widget.CustomDialog;


/**
 * 等待提示框封装类
 * 
 * @Description TODO
 * @author LiGang
 * @version 1.0
 * @date 2014年4月19日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 * z
 */
public class ProcessDialogUtil {

	/** 等待提示框对象 */
	public static CustomDialog progressDialog=null;

	/**
	 * 显示等待提示框
	 * 
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:26:03
	 * @updateTime 2014年4月19日,下午2:26:03
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *            上下文
	 * @param -text
	 *            提示文本信息
	 * @param cancelable
	 *            提示框是否可取消 true 可取消， false 不可取消
	 */
	public static void showDialog(Context context, String processMsg, final boolean cancelable) {

		if (null != progressDialog) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			progressDialog = null;
		}
		
		progressDialog = new CustomDialog(context);
		progressDialog.setCancelable(cancelable);
		progressDialog.setProgress(processMsg);
		progressDialog.show();

	}
	
	
	/**
	 * 设置对话框取消监听
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:43:37
	 * @updateTime 2014年4月19日,下午2:43:37
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param listener
	 */
	public static void setOnDismissListener(OnDismissListener listener) {
		if (null != progressDialog) {
			progressDialog.setOnDismissListener(listener);
		}
	}

	/**
	 * 关闭加载提示框
	 * 
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:25:46
	 * @updateTime 2014年4月19日,下午2:25:46
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public static void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
