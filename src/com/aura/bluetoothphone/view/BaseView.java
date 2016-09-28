package com.aura.bluetoothphone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * 基础自定义view
 *
 * @Description 用于规范自定义view的编写流程
 * @author CodeApe
 * @version 1.0
 * @date 2014年9月2日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 *
 */
public abstract class BaseView extends RelativeLayout{
	
	/** 上下文*/
	protected Context context;
	/** 父视图*/
	protected View view_Parent;

	public BaseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inflaterView(context);
	}

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflaterView(context);
	}

	public BaseView(Context context) {
		super(context);
		inflaterView(context);
	}
	
	
	/**
	 * 初始化
	 *
	 * @version 1.0
	 * @createTime 2014年9月2日,上午11:31:29
	 * @updateTime 2014年9月2日,上午11:31:29
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 */
	private void inflaterView(Context context){
		this.context = context;
		view_Parent = LayoutInflater.from(this.context).inflate(getContentViewId(), null);
		this.addView(view_Parent);
		findViews();
		widgetListener();
		init();
	}
	
	/**
	 * 获取显示view的layout id
	 *
	 * @version 1.0
	 * @createTime 2014年9月2日,上午11:42:37
	 * @updateTime 2014年9月2日,上午11:42:37
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return
	 */
	protected  abstract int getContentViewId();
	
	/**
	 * 控件查找
	 *
	 * @version 1.0
	 * @createTime 2014年9月2日,上午11:43:51
	 * @updateTime 2014年9月2日,上午11:43:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected  abstract void findViews();
	
	/**
	 * 组件监听
	 *
	 * @version 1.0
	 * @createTime 2014年9月2日,上午11:44:45
	 * @updateTime 2014年9月2日,上午11:44:45
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected  abstract void widgetListener();
	
	/**
	 * 初始化
	 *
	 * @version 1.0
	 * @createTime 2014年9月2日,上午11:45:08
	 * @updateTime 2014年9月2日,上午11:45:08
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected  abstract void init();
	

}
