package com.aura.bluetoothphone.configs;

import android.app.Application;
import android.content.Context;

/**
 * 全局公用Application类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月24日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 * 
 */
public class TApplication extends Application {

	/** 全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示 */
	public static Context context;

	/**
	 * 整个应用程序的初始入口函数
	 * 
	 * 本方法内一般用来初始化程序的全局数据，或者做应用的长数据保存取回操作
	 * 
	 * @version 1.0
	 * @createTime 2014年12月24日,下午2:12:17
	 * @updateTime 2014年12月24日,下午2:12:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	@Override
	public void onCreate() {
		// 实例化全局调用的上下文
		context = getApplicationContext();
		super.onCreate();
	}


}
