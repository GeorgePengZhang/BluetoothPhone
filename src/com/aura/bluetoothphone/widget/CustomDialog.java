package com.aura.bluetoothphone.widget;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.adapter.DialogSingleItemAdapter;

/**
 * 自定义对话框
 * 
 * @author 罗文忠
 * @version 1.0
 * @date 2013-7-30
 * 
 */
public class CustomDialog extends Dialog {

	/** 对话框按钮1的ID */
	public static final int ID_BUTTON_1 = R.id.custom_dialog_btn_1;
	/** 对话框按钮2的ID */
	public static final int ID_BUTTON_2 = R.id.custom_dialog_btn_2;
	/** 对话框按钮3的ID */
	public static final int ID_BUTTON_3 = R.id.custom_dialog_btn_3;

	/** 上下文 */
	private Context context;

	/** 内容视图 */
	private View view_Content;
	/** 标题栏视图 */
	private View view_Title;
	/** 等待框视图 */
	private View view_Progress;
	/** 按钮组视图 */
	private View view_Buttons;
	/** 列表控件 */
	private ListView view_ListView;

	/** 自定义视图视图 */
	private LinearLayout view_Custom;

	/** 标题 */
	private TextView txt_Title;
	/** 提示信息 */
	private TextView txt_Message;
	/** 等待信息 */
	private TextView txt_Progress;

	/** 按钮1 红色 */
	private Button btn_Button1;
	/** 按钮2 灰色 */
	private Button btn_Button2;
	/** 按钮3 灰色 */
	private Button btn_Button3;

	/** 输入框 */
	private EditText edit_Input;
	
	 private Window window = null;

	public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public CustomDialog(Context context) {
		super(context, R.style.dialog_style);
		init(context);
	}
	/**
	 * 初始话对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午9:39:59
	 * @updateTime 2013-7-30,上午9:39:59
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * 
	 */
	protected void init(Context context) {
		this.context = context;
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
		this.setContentView(R.layout.view_custom_dialog);
		
		window = getWindow(); // 得到对话框
		window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画

		view_Title = findViewById(R.id.custom_dialog_view_title);
		view_Buttons = findViewById(R.id.custom_dialog_view_buttons);
		view_Progress = findViewById(R.id.custom_dialog_view_progress);
		view_Custom = (LinearLayout) findViewById(R.id.custom_dialog_view_custom);
		view_Content = findViewById(R.id.custom_dialog_view_content);
		view_ListView = (ListView) findViewById(R.id.custom_dialog_view_listview);

		txt_Title = (TextView) findViewById(R.id.custom_dialog_txt_title);
		txt_Progress = (TextView) findViewById(R.id.custom_dialog_txt_progress);
		txt_Message = (TextView) findViewById(R.id.custom_dialog_txt_message);

		edit_Input = (EditText) findViewById(R.id.custom_dialog_edit_input);

		btn_Button1 = (Button) findViewById(R.id.custom_dialog_btn_1);
		btn_Button2 = (Button) findViewById(R.id.custom_dialog_btn_2);
		btn_Button3 = (Button) findViewById(R.id.custom_dialog_btn_3);

		widgetListener();

	}

	/**
	 * 设置自定义dialog背景为空
	 *
	 * @version 1.0
	 * @createTime 2014年9月4日,下午5:09:17
	 * @updateTime 2014年9月4日,下午5:09:17
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public void setView_CustomToNull() {
		view_Custom.setBackgroundResource(R.color.transparent);
	}

	/**
	 * 组件
	 * 
	 * @version 1.0
	 * @createTime 2013-9-28,下午11:17:46
	 * @updateTime 2013-9-28,下午11:17:46
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	private void widgetListener() {

	}

	/**
	 * 设置标题栏
	 * 
	 */
	@Override
	public void setTitle(CharSequence title) {
		view_Title.setVisibility(View.VISIBLE);
		txt_Title.setText(title);
	}

	/**
	 * 设置提示信息
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:03:22
	 * @updateTime 2013-7-30,上午10:03:22
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param msg
	 */
	public void setMessage(String msg) {
		txt_Message.setVisibility(View.VISIBLE);
		txt_Message.setText(msg);
	}
	
	/**
	 * 设置提示信息位置
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:03:22
	 * @updateTime 2013-7-30,上午10:03:22
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param - msg
	 */
	public void setGravity(int gravity) {
		txt_Message.setVisibility(View.VISIBLE);
		txt_Message.setGravity(gravity);
	}

	/**
	 * 设置自定义对话框视图
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:05:10
	 * @updateTime 2013-7-30,上午10:05:10
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param view
	 */
	public void setCustomView(View view) {
		view_Content.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
		view_Custom.setVisibility(View.VISIBLE);
		view_Custom.addView(view);
	}

	/**
	 * 设置自定义对话框视图
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:05:10
	 * @updateTime 2013-7-30,上午10:05:10
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param - view
	 */
	public void setCustomView(int resource) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		view_Custom.setVisibility(View.VISIBLE);
		view_Custom.addView(view);
	}

	/**
	 * 设置输入框
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:06:41
	 * @updateTime 2013-7-30,上午10:06:41
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param hint
	 */
	public void setEditInput(String hint) {
		edit_Input.setVisibility(View.VISIBLE);
		edit_Input.setHint(hint);
	}

	/**
	 * 设置输入框
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:06:41
	 * @updateTime 2013-7-30,上午10:06:41
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param hint
	 *                提示文本
	 * @param lines
	 *                行数
	 */
	public void setEditInput(String hint, int lines) {
		edit_Input.setVisibility(View.VISIBLE);
		edit_Input.setHint(hint);
		edit_Input.setMinLines(lines);
	}

	/**
	 * 获取输入框内容
	 * 
	 * @version 1.0
	 * @createTime 2013-8-9,下午9:15:33
	 * @updateTime 2013-8-9,下午9:15:33
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public String getEditInputText() {
		return edit_Input.getText().toString().trim();
	}

	/**
	 * 设置等待对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:07:48
	 * @updateTime 2013-7-30,上午10:07:48
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param progress
	 */
	public void setProgress(String progress) {
		view_Progress.setVisibility(View.VISIBLE);
		view_Content.setVisibility(View.GONE);
		txt_Progress.setText(progress);
	}

	/**
	 * 设置按钮1
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:23:09
	 * @updateTime 2013-7-30,上午10:23:09
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 *                按钮监听事件
	 */
	public void setEnterBtn(final OnClickListener listener) {
		view_Buttons.setVisibility(View.VISIBLE);
		btn_Button1.setVisibility(View.VISIBLE);
		btn_Button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onClick(CustomDialog.this, ID_BUTTON_1, null);
			}
		});
	}

	/**
	 * 设置取消按钮
	 * 
	 * @version 1.0
	 * @createTime 2013-10-16,下午5:03:24
	 * @updateTime 2013-10-16,下午5:03:24
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 */
	public void setCancelBtn(final OnClickListener listener) {
		view_Buttons.setVisibility(View.VISIBLE);
		btn_Button2.setVisibility(View.VISIBLE);
		btn_Button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onClick(CustomDialog.this, ID_BUTTON_2, null);
			}
		});
	}

	/**
	 * 设置单按钮
	 *
	 * @version 1.0
	 * @createTime 2014年1月2日,下午4:06:01
	 * @updateTime 2014年1月2日,下午4:06:01
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param listener
	 */
	public void setSingleBtn(final OnClickListener listener) {
		view_Buttons.setVisibility(View.VISIBLE);
		btn_Button3.setVisibility(View.VISIBLE);
		btn_Button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onClick(CustomDialog.this, ID_BUTTON_3, null);
			}
		});
	}

	/**
	 * 设置单选列表对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-8-2,下午8:01:08
	 * @updateTime 2013-8-2,下午8:01:08
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param items
	 *                列表选项
	 * @param listener
	 *                列表监听事件
	 */
	public void setSingleSelectItems(final ArrayList<String> items, final OnClickListener listener) {
		view_ListView.setVisibility(View.VISIBLE);
		DialogSingleItemAdapter adapter = new DialogSingleItemAdapter(context, items);
		view_ListView.setAdapter(adapter);

		view_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				listener.onClick(CustomDialog.this, position, items.get(position));
			}
		});
	}

	/**
	 * 设置单选按钮
	 * 
	 * @version 1.0
	 * @createTime 2013-10-10,下午10:04:14
	 * @updateTime 2013-10-10,下午10:04:14
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param items
	 *                列表选项
	 * @param listener
	 *                列表监听事件
	 * @param checkItem
	 *                当前选中项
	 */
	public void setSingleSelectItems(final ArrayList<String> items, int checkItem, final OnClickListener listener) {
		view_ListView.setVisibility(View.VISIBLE);
		DialogSingleItemAdapter adapter = new DialogSingleItemAdapter(context, items, checkItem);
		view_ListView.setAdapter(adapter);

		view_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				listener.onClick(CustomDialog.this, position, items.get(position));
			}
		});
	}

	/**
	 * 设置单选对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-10-10,下午10:00:14
	 * @updateTime 2013-10-10,下午10:00:14
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param items
	 *                列表选项
	 * @param listener
	 *                列表监听事件
	 */
	public void setSingleSelectItems(final String[] items, final OnClickListener listener) {
		view_ListView.setVisibility(View.VISIBLE);
		DialogSingleItemAdapter adapter = new DialogSingleItemAdapter(context, items);
		view_ListView.setAdapter(adapter);

		view_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				listener.onClick(CustomDialog.this, position, items[position]);
			}
		});
	}

	/**
	 * 设置单选对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-10-10,下午10:01:10
	 * @updateTime 2013-10-10,下午10:01:10
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param items
	 *                列表选项
	 * @param listener
	 *                返回监听
	 * @param checkItem
	 *                选中按钮
	 */
	public void setSingleSelectItems(final String[] items, int checkItem, final OnClickListener listener) {
		view_ListView.setVisibility(View.VISIBLE);
		DialogSingleItemAdapter adapter = new DialogSingleItemAdapter(context, items, checkItem);
		view_ListView.setAdapter(adapter);
		view_ListView.setSelection(checkItem);

		view_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				listener.onClick(CustomDialog.this, position, items[position]);
			}
		});
	}

	/**
	 * 设置单选列表对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-8-2,下午8:01:08
	 * @updateTime 2013-8-2,下午8:01:08
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param items
	 *                列表选项
	 * @param listener
	 *                列表监听事件
	 */
	public void setSingleSelectItems(ArrayList<Object> items, ListAdapter adapter, final OnClickListener listener) {
		view_ListView.setVisibility(View.VISIBLE);
		view_ListView.setAdapter(adapter);

		view_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				listener.onClick(CustomDialog.this, position, null);
			}
		});
	}

	/**
	 * 设置按钮1
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:23:09
	 * @updateTime 2013-7-30,上午10:23:09
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *                按钮名称
	 * @param listener
	 *                按钮监听事件
	 */
	public void setButton1(String text, final OnClickListener listener) {
		view_Buttons.setVisibility(View.VISIBLE);
		btn_Button1.setVisibility(View.VISIBLE);
		btn_Button1.setText(text);
		btn_Button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onClick(CustomDialog.this, ID_BUTTON_1, null);
			}
		});
	}

	/**
	 * 设置按钮2
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:23:09
	 * @updateTime 2013-7-30,上午10:23:09
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *                按钮名称
	 * @param listener
	 *                按钮监听事件
	 */
	public void setButton2(String text, final OnClickListener listener) {
		view_Buttons.setVisibility(View.VISIBLE);
		btn_Button2.setVisibility(View.VISIBLE);
		btn_Button2.setText(text);
		btn_Button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onClick(CustomDialog.this, ID_BUTTON_2, null);
			}
		});
	}

	/**
	 * 设置按钮3
	 * 
	 * @version 1.0
	 * @createTime 2013-7-30,上午10:23:09
	 * @updateTime 2013-7-30,上午10:23:09
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *                按钮名称
	 * @param listener
	 *                按钮监听事件
	 */
	public void setButton3(String text, final OnClickListener listener) {
		view_Buttons.setVisibility(View.VISIBLE);
		btn_Button3.setVisibility(View.VISIBLE);
		btn_Button3.setText(text);
		btn_Button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onClick(CustomDialog.this, ID_BUTTON_3, null);
			}
		});
	}

	/**
	 * 对话框按钮点击回调事件
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-7-30
	 * 
	 */
	public interface OnClickListener {
		/**
		 * 对话框按钮被点击时候调用
		 * 
		 * @version 1.0
		 * @createTime 2013-7-30,上午10:13:13
		 * @updateTime 2013-7-30,上午10:13:13
		 * @createAuthor 罗文忠
		 * @updateAuthor 罗文忠
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param dialog
		 *                当前对话框对象
		 * @param id
		 *                被点击按钮的id(例：CustomDialog.ID_BUTTON_1 ,
		 *                CustomDialog.ID_BUTTON_2 ...)
		 * 
		 * @param object
		 *                附带信息
		 */
		public void onClick(CustomDialog dialog, int id, Object object);

	}
	
	public void setButton1(String bt1,String bt2) {  
		btn_Button1.setText(bt2);
		btn_Button2.setText(bt1);
	}

}
