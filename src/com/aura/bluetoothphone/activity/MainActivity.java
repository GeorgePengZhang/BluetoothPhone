package com.aura.bluetoothphone.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.fragment.CallsLogListFragment;
import com.aura.bluetoothphone.fragment.ContactsPhoneListFragment;
import com.aura.bluetoothphone.fragment.MineFragment;
import com.aura.bluetoothphone.utils.ToastUtil;
import com.aura.bluetoothphone.view.MyRadioView;

/**
 * 主页面
 * 
 * @author Robin
 * @ClassName: MainActivity 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月23日 上午11:35:33 
 *
 */
public class MainActivity extends BaseFragmentActivity {

	/** 上次按退出的时间 */
	private long lastExitTime = 0;
	/** fragment模块集合 */
	private List<Fragment> list_Fragments = new ArrayList<Fragment>();
	/**  */
	private MyRadioView radio_contects;
	/**  */
	private MyRadioView radio_key;
	/**  */
	private MyRadioView radio_me;
	/**  */
	private MyRadioView radio_conversation;
	/** 当前显示的Fragment */
	private int current_Fragment = -1;
	/** 最近联系人 */
	public final static int FRAGMENT_CONNECTS = 0;
	/** 拨号 */
	public final static int FRAGMENT_KEY = 1;
	/** 通讯录 */
	public final static int FRAGMENT_CONVERSATION = 2;
	/** 我的 */
	public final static int FRAGMENT_MINE = 3;
//	/** 最近联系人 */
//	private MainFragment fragment_main;
	private CallsLogListFragment fragment_main;
	/** 我的 */
	private MineFragment fragment_mine;
	/** 拨号 */
	private KeyboardDial keyboard_dial ;
	/** 通讯录 */
	private ContactsPhoneListFragment conversation ;
	
	private String keyView;
	private MyRadioView myRadioView ;
	@Override
	protected int getContentViewId() {
		return R.layout.activity_main;
	}

	@Override
	protected void findViews() {
		radio_contects = (MyRadioView) findViewById(R.id.main_radio_contacts);
		radio_key = (MyRadioView) findViewById(R.id.main_radio_keyboard);
		radio_me = (MyRadioView) findViewById(R.id.main_radio_me);
		radio_conversation = (MyRadioView) findViewById(R.id.main_radio_conversation);
	}

	@Override
	protected void init() {
		fragment_main = new CallsLogListFragment() ;
		fragment_mine = new MineFragment() ;
		keyboard_dial = new KeyboardDial() ;
		conversation = new ContactsPhoneListFragment() ;
		list_Fragments.add(fragment_main) ;
		list_Fragments.add(keyboard_dial) ;
		list_Fragments.add(conversation) ; 
		list_Fragments.add(fragment_mine) ; 
		switchView(FRAGMENT_CONNECTS);
		radio_contects.setCheck(true);
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//	}


	@Override
	protected void widgetListener() {
		radio_contects.setOnClickListener(radioClick);
		radio_key.setOnClickListener(radioClick);
		radio_me.setOnClickListener(radioClick);
		radio_conversation.setOnClickListener(radioClick);
	}

	/** 底部按钮点击事件处理 */
	private View.OnClickListener radioClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			myRadioView = (MyRadioView) v;
			if (myRadioView.isCheck) {
				return;
			}
			radio_contects.setCheck(false);
			radio_contects = myRadioView;
			radio_contects.setCheck(true);
			switch (v.getId()) {
				case R.id.main_radio_contacts:
					switchView(FRAGMENT_CONNECTS);
					break;
				case R.id.main_radio_keyboard:
					switchView(FRAGMENT_KEY);
					break;
				case R.id.main_radio_me:
					switchView(FRAGMENT_MINE);
					break;
				case R.id.main_radio_conversation:
					switchView(FRAGMENT_CONVERSATION);
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 选择界面
	 *
	 * @version 1.0
	 * @createTime 2014年11月3日,上午10:04:27
	 * @updateTime 2014年11月3日,上午10:04:27
	 * @createAuthor 李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param position
	 */
	public void switchView(int position) {
		try {
			if (current_Fragment == position) {
				return;
			}
			// 获取Fragment的操作对象
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.content_frame, list_Fragments.get(position));
			if (current_Fragment != -1) {
				getSupportFragmentManager().popBackStack(position + "", 0);
				transaction.addToBackStack(position + "");// 将上一个Fragment存回栈，生命周期为stop，不销毁
			}
			transaction.commitAllowingStateLoss();// 提交更改
			current_Fragment = position;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				// 判断2次点击事件时间
				if ((System.currentTimeMillis() - lastExitTime) > 2000) {
					ToastUtil.showToast(MainActivity.this, getString(R.string.hint_exit_application));
					lastExitTime = System.currentTimeMillis();
				} else {
					finish();
				}

			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

}
