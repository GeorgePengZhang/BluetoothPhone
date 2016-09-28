package com.aura.bluetoothphone.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.configs.BroadcastFilters;
import com.aura.bluetoothphone.view.TitleView;


/**
 * 基类Fragment
 * 
 * @Description 所有的Fragment必须继承自此类
 * @author LiGang
 * @version 1.0
 * @date 2014年3月29日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 * 
 */
public abstract class BaseFragment extends Fragment {
	/** 父视图 */
	protected View view_Parent;

	/** 首页单选按钮组 */
	protected RadioGroup radioGroup_main;

	/** 广播接收器 */
	protected BroadcastReceiver receiver;
	/** 广播过滤器 */
	public IntentFilter filter;
	protected Context context;
	/** 标题 */
	protected TitleView titleView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getActivity();
		view_Parent = getViews();
		titleView = findViewById(R.id.view_title);

		getViews();
		initGetData();
		findViews();
		widgetListener();
		init();
		registerReceiver();
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findViewById(int resId) {
		return (T) view_Parent.findViewById(resId);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if ((ViewGroup) view_Parent.getParent() != null) {
			((ViewGroup) view_Parent.getParent()).removeView(view_Parent);
		}
		// radioGroup_main = (RadioGroup)
		// getActivity().findViewById(R.id.radiogroup_actions);
		return view_Parent;
	}

	/**
	 * 获取view
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:09:05
	 * @updateTime 2014年5月22日,上午10:09:05
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	protected abstract View getViews();

	/**
	 * 控件查找
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:03:58
	 * @updateTime 2014年5月22日,上午10:03:58
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void findViews();

	/**
	 * 组件监听
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:05:39
	 * @updateTime 2014年5月22日,上午10:05:39
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void widgetListener();

	/**
	 * 初始化
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:05:06
	 * @updateTime 2014年5月22日,上午10:05:06
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void init();

	/**
	 * 是否已经创建
	 * 
	 * @version 1.0
	 * @createTime 2014年6月6日,上午11:16:54
	 * @updateTime 2014年6月6日,上午11:16:54
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return true已经创建，false未创建
	 */
	public boolean isCreated() {
		return view_Parent != null;
	}

	/**
	 * 获取初始数据
	 * 
	 * @version 1.0
	 * @createTime 2014年6月7日,上午11:13:55
	 * @updateTime 2014年6月7日,上午11:13:55
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public abstract void initGetData();

	/**
	 * 设置广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
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

		// TODO 添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
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
				BaseFragment.this.onReceive(context, intent);
			}
		};
		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
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
		// TODO 接受到广播之后做的处理操作
	}

}
