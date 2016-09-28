package com.aura.bluetoothphone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自动计算列表高度的gridview控件
 * 
 * @Description 将gridview用于Scrollview、Listview嵌套使用时候可以使用该控件，避免显示不全和事件冲突
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月31日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 * 
 */
public class AutoSizeGridView extends GridView {

	/** view大小更改监听 */
	private OnSizeChangeListener listener;

	public AutoSizeGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public AutoSizeGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public AutoSizeGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		if (listener != null) {
			listener.onChange(w, h, oldw, oldh);
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	/**
	 * 设置size大小更改监听
	 * 
	 * @version 1.0
	 * @createTime 2013-11-28,下午4:31:49
	 * @updateTime 2013-11-28,下午4:31:49
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void setOnSizeChangListener(OnSizeChangeListener onSizeChangeListener) {
		this.listener = onSizeChangeListener;
	}

	/**
	 * view 大小更改监听事件
	 * 
	 * @Description TODO
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-11-28
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd.
	 *             Inc. All rights reserved.
	 * 
	 */
	public interface OnSizeChangeListener {

		/**
		 * View 大小更改监听事件
		 * 
		 * @version 1.0
		 * @createTime 2013-11-28,下午4:18:33
		 * @updateTime 2013-11-28,下午4:18:33
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param w
		 *            新的宽度
		 * @param h
		 *            新的高度
		 * @param oldw
		 *            旧的宽度
		 * @param oldh
		 *            新的高度
		 */
		public void onChange(int w, int h, int oldw, int oldh);
	}

}
