package com.aura.bluetoothphone.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.aura.bluetoothphone.configs.TApplication;



/**
 * 应用工具类》
 * 
 * @Description
 * @author 刘艺谋
 * @date 2013-7-19
 */
public class SystemUtil {

	/**
	 * 获取当前系统版本号
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午3:45:06
	 * @updateTime 2013-10-21,下午3:45:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public static int getCurrentVersionCode() {
		int versionCode = 1;
		// 获取packagemanager的实例
		PackageManager packageManager = TApplication.context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(TApplication.context.getPackageName(), 0);
			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			versionCode = 1;
		}
		return versionCode;
	}

	/**
	 * 获取当前系统版本名称
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午3:45:17
	 * @updateTime 2013-10-21,下午3:45:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public static String getCurrentVersionName() {
		String versionName = "";
		// 获取packagemanager的实例
		PackageManager packageManager = TApplication.context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(TApplication.context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			versionName = "1.0.0";
		}
		return versionName;
	}

	/**
	 * 获取本机的Mac地址
	 * 
	 * @version 1.0
	 * @createTime 2013-11-13,上午11:58:43
	 * @updateTime 2013-11-13,上午11:58:43
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 本机mac
	 */
	public static String getPhoneMac() {
		WifiManager wifi = (WifiManager) TApplication.context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String mac = info.getMacAddress();
		return mac;
	}
	
	/**
	 * 判断系统语言是否中文
	 *
	 * @version 1.0
	 * @createTime 2014年9月29日,下午3:09:00
	 * @updateTime 2014年9月29日,下午3:09:00
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 *  @return 
	 */
	public static boolean IsSystemLanguage(){
		if (TApplication.context.getResources().getConfiguration().locale.getCountry().equals("CN") || TApplication.context.getResources().getConfiguration().locale.getCountry().equals("TW")) {
			return  true;
		} else {
			return  false;
		}
	}

	/**
	 * 获取系统语言
	 *
	 * @version 1.0
	 * @createTime 2014年9月29日,下午3:09:00
	 * @updateTime 2014年9月29日,下午3:09:00
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 *  @return 只返回英文和中文
	 */
	public static String getSystemLanguage(){
		if (TApplication.context.getResources().getConfiguration().locale.getCountry().equals("CN") || TApplication.context.getResources().getConfiguration().locale.getCountry().equals("TW")) {
			return  "zh";
		} else {
			return  "en";
		}
	}
}
