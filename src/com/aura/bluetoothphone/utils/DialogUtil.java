package com.aura.bluetoothphone.utils;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.widget.CustomDialog;



/**
 * 对话框封装工具类
 * 
 * @author LiGang
 * @date 2014-03-20
 * @version 1.0
 * 
 */
public class DialogUtil {

	
	
	


	/**
	 * 显示提示信息对话框
	 * 
	 * @version 1.0
	 * @createTime 2014-10-2,上午10:36:57
	 * @updateTime 2014-10-2,上午10:36:57
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 */
	public static void showMessageDg(Context context, String title, String msg, CustomDialog.OnClickListener listener) {
		CustomDialog dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setMessage(msg);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		dialog.setButton1(context.getString(R.string.enter), listener);

		dialog.setButton2(context.getString(R.string.cancel), new CustomDialog.OnClickListener() {
			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	/**
	 * 显示提示信息对话框
	 * 
	 * @version 1.0
	 * @createTime 2014-10-2,上午10:36:57
	 * @updateTime 2014-10-2,上午10:36:57
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 */
	public static void showMessageCancel(Context context, String title, String msg) {
		CustomDialog dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setMessage(msg);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setButton2(context.getString(R.string.cancel), new CustomDialog.OnClickListener() {
			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * 显示提示信息对话框
	 * 
	 * @version 1.0
	 * @createTime 2014-10-2,上午10:36:57
	 * @updateTime 2014-10-2,上午10:36:57
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 */
	public static void showSingleMessageDg(Context context, String title, String msg, CustomDialog.OnClickListener listener) {
		CustomDialog dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setMessage(msg);
		dialog.setButton3(context.getString(R.string.enter), listener);
		dialog.show();
	}

	/**
	 * 描述：无标题 提示信息对话框
	 * 
	 * @version 1.0
	 * @createTime 2014-4-23 下午1:07:16
	 * @updateTime 2014-4-23 下午1:07:16
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (修改内容描述)
	 */
	public static void showSingleMessageDg(Context context, String msg, CustomDialog.OnClickListener listener) {
		CustomDialog dialog = new CustomDialog(context);
		dialog.setMessage(msg);
		dialog.setButton3(context.getString(R.string.enter), listener);
		dialog.show();
	}
	


	/**
	 * 显示退出对话框
	 * 
	 * @version 1.0
	 * @createTime 2014-10-2,上午10:36:57
	 * @updateTime 2014-10-2,上午10:36:57
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 */
	public static void showExitsDg(final Context context) {
		CustomDialog dialog = new CustomDialog(context);
		dialog.setTitle(context.getString(R.string.prompt));
		dialog.setMessage(context.getString(R.string.warning_exits_system));

		dialog.setEnterBtn(new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				((Activity) context).finish();
//				TApplication.chatObjectInfoBean = null;
//				TApplication.IsOnTop = true;
				dialog.dismiss();
			}
		});
		dialog.setCancelBtn(new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
			}

		});
		dialog.show();
	}


	
	

	
	
	/**
	 * 提示登录
	 * 
	 * @version 1.0
	 * @createTime 2014-10-2,上午10:36:57
	 * @updateTime 2014-10-2,上午10:36:57
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 */
	public static void showLogin(final Context context) {
		CustomDialog dialog = new CustomDialog(context);
		dialog.setTitle(context.getString(R.string.prompt));
		dialog.setMessage(context.getString(R.string.warning_exits_system));

		dialog.setEnterBtn(new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				((Activity) context).finish();
				dialog.dismiss();
				
			}
		});
		dialog.setCancelBtn(new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
			}

		});
		dialog.show();
	}
	
	/**
	 * 
	 * 提示信息框
	 * 
	 * @version 1.0
	 * @createTime 2015年12月17日,下午2:16:22
	 * @updateTime 2015年12月17日,下午2:16:22
	 * @createAuthor	LiGang
	 * @updateAuthor	LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @param data
	 */
	public static void showDelete(final Context context,final String data) {
		CustomDialog dialog = new CustomDialog(context);
		dialog.setTitle(context.getString(R.string.prompt));
		dialog.setMessage(data);
		dialog.setButton1("取消", "确定");
		dialog.setEnterBtn(new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
				
				

			}
		});
		dialog.setCancelBtn(new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
			}
			
			
		});
		dialog.show();
	}

}
