package com.aura.bluetoothphone.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;


/**
 * 对话框单选列表适配器
 * 
 * @author LiGang
 * @version 1.0
 * @date 2013-8-2
 * 
 */
public class DialogSingleItemAdapter extends BaseAdapter {

	/** 上下文 */
	private Context context;

	/** 选项列表 */
	private String[] array_Items;

	/** 选项列表 */
	private ArrayList<String> list_Items;

	/** 选项列表属性 0、list列表 ， 1、数组 */
	private int ITEMS_TYPE = 0;

	private int checkItem = -1;

	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文
	 * @param - list_Reviews
	 *            列表
	 */
	public DialogSingleItemAdapter(Context context, ArrayList<String> list_Items) {
		this.context = context;
		this.list_Items = list_Items;

		ITEMS_TYPE = 0;

	}

	/**
	 * 初始化
	 * 
	 * @param context
	 *            上下文
	 * @param list_Items
	 *            选项列表
	 * @param checkItem
	 *            选中item
	 */
	public DialogSingleItemAdapter(Context context, ArrayList<String> list_Items, int checkItem) {
		this.context = context;
		this.list_Items = list_Items;

		ITEMS_TYPE = 0;
		this.checkItem = checkItem;

	}

	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文
	 * @param array_Items
	 *            数组
	 */
	public DialogSingleItemAdapter(Context context, String[] array_Items) {
		this.context = context;
		this.array_Items = array_Items;
		ITEMS_TYPE = 1;

	}

	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文
	 * @param array_Items
	 *            数组
	 * @param checkItem
	 *            选中item
	 */
	public DialogSingleItemAdapter(Context context, String[] array_Items, int checkItem) {
		this.context = context;
		this.array_Items = array_Items;

		ITEMS_TYPE = 1;
		this.checkItem = checkItem;

	}

	@Override
	public int getCount() {
		if (ITEMS_TYPE == 0) {
			return list_Items.size();
		} else {
			return array_Items.length;
		}
	}

	@Override
	public Object getItem(int position) {
		if (ITEMS_TYPE == 0) {
			return list_Items.get(position);
		} else {
			return array_Items[position];
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final viewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_listview, null);
			holder = new viewHolder();
			holder.txt_name = (TextView) convertView.findViewById(R.id.item_dialog_listview_txt_name);
			holder.img_Check = (ImageView) convertView.findViewById(R.id.item_dialog_listview_img_check);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}

		// 根据传入参数是数组还是列表展示相关内容
		if (ITEMS_TYPE == 0) {
			holder.txt_name.setText(list_Items.get(position));
		} else {
			holder.txt_name.setText(array_Items[position]);
		}
		
		// 显示选中项
		if (checkItem > -1 && position == checkItem) {
			holder.img_Check.setVisibility(View.VISIBLE);
		}else{
			holder.img_Check.setVisibility(View.GONE);
		}

		return convertView;
	}

	/**
	 * 内部容器类
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-8-2
	 * 
	 */
	private class viewHolder {

		/** 选项名称 */
		TextView txt_name;
		/** 选中图标 */
		ImageView img_Check;

	}

}
