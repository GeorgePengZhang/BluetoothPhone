package com.aura.bluetoothphone.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.configs.BroadcastFilters;
import com.aura.bluetoothphone.utils.SystemBarTintManager;
import com.aura.bluetoothphone.view.TitleView;



/**
 * 基类 FragmentActivity
 * 
 * @Description 所有的FragmentActivity 都继承自此类
 * @author LiGang
 * @version 1.0
 * @date 2014年5月24日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

	/** 广播接收器 */
	public BroadcastReceiver receiver;
	/** 广播过滤器 */
	public IntentFilter filter;
	/** 4.4版本以上的沉浸式 */
	protected SystemBarTintManager mTintManager;
	private Context context;
	/** 标题 */
	protected TitleView titleView;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		View view = View.inflate(context, getContentViewId(), null);
		// 布局内容会从actionbar以下开始
//		if (TApplication.sdk > 13) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			view.setFitsSystemWindows(true);
		}
		titleView = (TitleView) findViewById(R.id.view_title);
		//SDKInitializer.initialize(getApplicationContext());
		setContentView(view);
		findViews();
		widgetListener();
		initGetData();
		init();

		mTintManager = new SystemBarTintManager(this);
		setStatusBarState();
		registerReceiver();

	}

	/**
	 *
	 *	设置状态栏颜色与程序头部颜色一致
	 *
	 * @version 1.0
	 * @createTime 2015年10月29日,下午2:43:20
	 * @updateTime 2015年10月29日,下午2:43:20
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	private void setStatusBarState() {
		if (Build.VERSION.SDK_INT >= 19) {
			setTranslucentStatus(true);
			mTintManager = new SystemBarTintManager(this);
			mTintManager.setStatusBarTintEnabled(true);
			// 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
			mTintManager.setStatusBarTintResource(R.color.android_main);
			// 设置状态栏的文字颜色
			mTintManager.setStatusBarDarkMode(false, this);
		}
	}

	@TargetApi(19)
	protected void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 获取显示view的xml文件ID
	 * 
	 * 在Activity的 {@link #onCreate(Bundle)}里边被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:31:19
	 * @updateTime 2014年4月21日,下午2:31:19
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return xml文件ID
	 */
	protected abstract int getContentViewId();

	/**
	 * 控件查找
	 * 
	 * 在 {@link #getContentViewId()} 之后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:58:20
	 * @updateTime 2014年4月21日,下午1:58:20
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void findViews();

	/**
	 * 初始化应用程序，设置一些初始化数据都获取数据等操作
	 * 
	 * 在{@link #widgetListener()}之后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:55:15
	 * @updateTime 2014年4月21日,下午1:55:15
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void init();

	/**
	 * 获取上一个界面传送过来的数据
	 * 
	 * 在{link com.tentinet.baseproject.activity.BaseActivity#init()}之前被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:42:56
	 * @updateTime 2014年4月21日,下午2:42:56
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void initGetData() {
	};

	/**
	 * 组件监听模块
	 * 
	 * 在{@link #findViews()}后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:56:06
	 * @updateTime 2014年4月21日,下午1:56:06
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void widgetListener();

	/**
	 * 设置广播过滤器
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:43:15
	 * @updateTime 2014年5月22日,下午1:43:15
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void setFilterActions() {
		filter = new IntentFilter();
		filter.addAction(BroadcastFilters.ACTION_TEST);

	}

	/**
	 * 注册广播
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:41:25
	 * @updateTime 2014年5月22日,下午1:41:25
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void registerReceiver() {
		setFilterActions();
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				BaseFragmentActivity.this.onReceive(context, intent);
			}
		};
		registerReceiver(receiver, filter);

	}

	/**
	 * 广播监听回调
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:40:30
	 * @updateTime 2014年5月22日,下午1:40:30
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *            上下文
	 * @param intent
	 *            广播附带内容
	 */
	protected void onReceive(Context context, Intent intent) {

		// TODO 接收到广播之后的处理操作

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

}
