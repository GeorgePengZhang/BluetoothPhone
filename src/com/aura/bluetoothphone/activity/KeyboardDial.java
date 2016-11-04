package com.aura.bluetoothphone.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.adapter.ContactsPhoneAdapter;
import com.aura.bluetoothphone.bean.ContactsPhoneBean;
import com.aura.bluetoothphone.fragment.BaseFragment;
import com.aura.bluetoothphone.utils.IntentUtil;
import com.aura.bluetoothphone.utils.StringUtil;
import com.aura.bluetoothphone.utils.connects.CnToCharParser;
import com.aura.bluetoothphone.utils.connects.ContactsUtils;
import com.aura.bluetoothphone.view.HomeEditTextWithDel;

/**
 * 拨号
 * 
 * @author Robin
 * @ClassName: KeyboardDial
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016年9月23日 上午11:36:34
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class KeyboardDial extends BaseFragment implements OnClickListener {

	/** 数字键 1 */
	private Button btn_one;
	/** 数字键 2 */
	private Button btn_two;
	/** 数字键 3 */
	private Button btn_three;
	/** 数字键 4 */
	private Button btn_four;
	/** 数字键 5 */
	private Button btn_five;
	/** 数字键 6 */
	private Button btn_six;
	/** 数字键 7 */
	private Button btn_seven;
	/** 数字键 8 */
	private Button btn_eight;
	/** 数字键 9 */
	private Button btn_nine;
	/** 数字键 * */
	private Button btn_star;
	/** 数字键 0 */
	private Button btn_zero;
	/** 数字键 # */
	private Button btn_jing;

	/** 拨号键 */
	private Button btn_call;
	/** 电话显示框 */
	HomeEditTextWithDel edit_phone;

	private static final int DTMF_DURATION_MS = 120; // 声音的播放时间
    private Object mToneGeneratorLock = new Object(); // 监视器对象锁
    private ToneGenerator mToneGenerator;             // 声音产生器
    private static boolean mDTMFToneEnabled;         // 系统参数“按键操作音”标志位
    
    private View mView ;
    boolean flag = true ;
//	List<Contact> list = new ArrayList<Contact>();
	List<ContactsPhoneBean> mDataList = new ArrayList<ContactsPhoneBean>(); ;
	ListView listView ;
	private MyQueryHandler myQueryHandler;
	
	View view3 ;
	Button btn_invision ;
	Button btn_show ;
	
	@Override
	protected View getViews() {
		return View.inflate(context, R.layout.fragment_keyboard, null);
	}

	@Override
	protected void findViews() {
		edit_phone	 = (HomeEditTextWithDel) findViewById(R.id.ballpark_search_edit);
		btn_one		 = (Button) findViewById(R.id.btn_one);
		btn_two		 = (Button) findViewById(R.id.btn_two);
		btn_three	 = (Button) findViewById(R.id.btn_three);
		btn_four	 = (Button) findViewById(R.id.btn_four);
		btn_five	 = (Button) findViewById(R.id.btn_five);
		btn_six		 = (Button) findViewById(R.id.btn_six);
		btn_seven	 = (Button) findViewById(R.id.btn_seven);
		btn_eight	 = (Button) findViewById(R.id.btn_eight);
		btn_nine 	 = (Button) findViewById(R.id.btn_nine);
		btn_star 	 = (Button) findViewById(R.id.btn_star);
		btn_zero 	 = (Button) findViewById(R.id.btn_zero);
		btn_jing	 = (Button) findViewById(R.id.btn_jing);
		btn_call	 = (Button) findViewById(R.id.btn_call);
		mView		 = (View)findViewById(R.id.view) ;
		view3		 = (View)findViewById(R.id.view3) ;
		listView	 = (ListView)findViewById(R.id.lv) ;
		btn_invision = (Button)findViewById(R.id.btn_invision) ;
		btn_show     = (Button)findViewById(R.id.btn_show) ;
	}

	@Override
	protected void widgetListener() {
		titleView.setTitle("Call");
		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
		btn_three.setOnClickListener(this);
		btn_four.setOnClickListener(this);
		btn_five.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		btn_eight.setOnClickListener(this);
		btn_nine.setOnClickListener(this);
		btn_star.setOnClickListener(this);
		btn_zero.setOnClickListener(this);
		btn_jing.setOnClickListener(this);
		btn_call.setOnClickListener(this);
		btn_invision.setOnClickListener(this);
		btn_show.setOnClickListener(this);
		
		btn_one.setSoundEffectsEnabled(false);
		btn_two.setSoundEffectsEnabled(false);
		btn_three.setSoundEffectsEnabled(false);
		btn_four.setSoundEffectsEnabled(false);
		btn_five.setSoundEffectsEnabled(false);
		btn_six.setSoundEffectsEnabled(false);
		btn_seven.setSoundEffectsEnabled(false);
		btn_eight.setSoundEffectsEnabled(false);
		btn_nine.setSoundEffectsEnabled(false);
		btn_zero.setSoundEffectsEnabled(false);
		btn_jing.setSoundEffectsEnabled(false);
		btn_star.setSoundEffectsEnabled(false);
		
		listView.setVisibility(View.GONE);
		edit_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				 //文本变化中
				udpateFilterDataList(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				//文本变化前
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// 文本变化后
				if (s.toString().length() == 0) {
					edit_phone.onFocusChange(null, false);
					listView.setVisibility(View.GONE);
				}else{
					edit_phone.onFocusChange(null, true);
					listView.setVisibility(View.VISIBLE);
				}
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		
		myQueryHandler = new MyQueryHandler(getActivity().getContentResolver());
		myQueryHandler.startQuery(0, null, ContactsUtils.Phone.CONTENT_CALLABLES_URI, ContactsUtils.Phone.PROJECTION_PRIMARY, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
	}
	
	/**
	 * 搜索字符查找特定联系人
	 * @param s
	 */
	private void udpateFilterDataList(String s) {
		if (TextUtils.isEmpty(s)) {
			updateListAdapter(mDataList);
		} else {
			//汉字转拼音
			String filterName = CnToCharParser.getInstance().getSpell(s, false).toUpperCase(Locale.getDefault());
			List<ContactsPhoneBean> list = new ArrayList<ContactsPhoneBean>();
			int size = mDataList.size();
			for (int i = 0; i < size; i++) {
				ContactsPhoneBean bean = mDataList.get(i);
				
				String spellname = bean.getSpellname().toUpperCase(Locale.getDefault());
				String phone = bean.getPhone();
				String name = bean.getName();
				String number = bean.getNumber();
				
				if (spellname.indexOf(filterName) != -1 ) {
					ContactsPhoneBean clone = bean.clone();
					clone.setName(matcherSearchTitle(name, s));
					list.add(clone);
				} else if (phone.indexOf(filterName) != -1) {
					ContactsPhoneBean clone = bean.clone();
					clone.setNumber(matcherSearchTitle(number, s));
					list.add(clone);
				}
				
			}
			updateListAdapter(list);
		}
	}
	
	 /** 
		 * 搜索关键字标红 
		 * @param title 
		 * @param keyword 
		 * @return 
		 */  
		public static String matcherSearchTitle(String title,String keyword){  
		    String content = title;    
		    String wordReg = "(?i)"+keyword;//用(?i)来忽略大小写    
		    StringBuffer sb = new StringBuffer();    
		    Matcher matcher = Pattern.compile(wordReg).matcher(content);    
		    while(matcher.find()){    
		        //这样保证了原文的大小写没有发生变化    
		        matcher.appendReplacement(sb, "<font color=#33B5E5>"+matcher.group()+"</font>");  
		    }    
		    matcher.appendTail(sb);    
		    content = sb.toString();   
		    return content;  
		}
	
	//异步查询联系人数据库
		private class MyQueryHandler extends AsyncQueryHandler {

			public MyQueryHandler(ContentResolver cr) {
				super(cr);
			}
			
			@Override
			protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
				super.onQueryComplete(token, cookie, cursor);
				if (cursor != null && cursor.getCount() > 0) {
					mDataList.clear();
					
					while (cursor.moveToNext()) {
						ContactsPhoneBean bean = ContactsPhoneBean.getBeanFromCursor(cursor);
						//去掉电话号码一样的
						if (!mDataList.contains(bean)) {
							mDataList.add(bean);
						}
						Collections.sort(mDataList);
					}
					
					cursor.close();
					updateListAdapter(mDataList);
					
				}
			}
		}
		private ContactsPhoneAdapter mPhoneAdapter;
		/**
		 * 更新联系人列表
		 * @param list
		 */
		private void updateListAdapter(List<ContactsPhoneBean> list) {
			
			mPhoneAdapter = new ContactsPhoneAdapter(getActivity(), list);
			listView.setAdapter(mPhoneAdapter);
		}
	

	@Override
	protected void init() {
		//按键声音播放设置及初始化
        try {
            // 获取系统参数“按键操作音”是否开启
            mDTMFToneEnabled = Settings.System.getInt(getActivity().getContentResolver(),
                    Settings.System.DTMF_TONE_WHEN_DIALING, 1) == 1;
            synchronized (mToneGeneratorLock) {
                if (mDTMFToneEnabled && mToneGenerator == null) {
                    mToneGenerator = new ToneGenerator(
                            AudioManager.STREAM_DTMF, 80); // 设置声音的大小
                    getActivity().setVolumeControlStream(AudioManager.STREAM_DTMF);
                }
            }
        } catch (Exception e) {
            mDTMFToneEnabled = false;
            mToneGenerator = null;
        }
	}

	@Override
	public void initGetData() {

	}

	@Override
	public void onResume() {
		super.onResume();
		
		if (mPhoneAdapter != null) {
			mPhoneAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_one:
			playTone(ToneGenerator.TONE_DTMF_1);
			setEditText("1") ;
			break;
		case R.id.btn_two:
			playTone(ToneGenerator.TONE_DTMF_2);
			setEditText("2") ;
			break;
		case R.id.btn_three:
			playTone(ToneGenerator.TONE_DTMF_3);
			setEditText("3") ;
			break;
		case R.id.btn_four:
			playTone(ToneGenerator.TONE_DTMF_4);
			setEditText("4") ;
			break;
		case R.id.btn_five:
			playTone(ToneGenerator.TONE_DTMF_5);
			setEditText("5") ;
			break;
		case R.id.btn_six:
			playTone(ToneGenerator.TONE_DTMF_6);
			setEditText("6") ;
			break;
		case R.id.btn_seven:
			playTone(ToneGenerator.TONE_DTMF_7);
			setEditText("7") ;
			break;
		case R.id.btn_eight:
			playTone(ToneGenerator.TONE_DTMF_8);
			setEditText("8") ;
			break;
		case R.id.btn_nine:
			playTone(ToneGenerator.TONE_DTMF_9);
			setEditText("9") ;
			break;
		case R.id.btn_star:
			playTone(ToneGenerator.TONE_DTMF_S);
			setEditText("*") ;
			break;
		case R.id.btn_zero:
			playTone(ToneGenerator.TONE_DTMF_0);
			setEditText("0") ;
			break;
		case R.id.btn_jing:
			playTone(ToneGenerator.TONE_DTMF_P);
			setEditText("#") ;
			break;
		case R.id.btn_call:				// 跳转 通话界面
			
			IntentUtil.gotoActivity(getActivity(), CallingActivity.class);
			
			
			break;
		case R.id.btn_invision:			//隐藏键盘
			mView.setAnimation(StringUtil.moveToViewBottom());
			mView.setVisibility(View.GONE);
			
			view3.setAnimation(StringUtil.moveToViewLocation());
			view3.setVisibility(View.VISIBLE);
			
			break ;
		case R.id.btn_show:				//显示键盘
			mView.setAnimation(StringUtil.moveToViewLocation());
		    mView.setVisibility(View.VISIBLE);
		    
		    view3.setAnimation(StringUtil.moveToViewBottom());
			view3.setVisibility(View.GONE);
			StringUtil.moveToViewLocation() ;
			break;
		default:
			break;
		}
	}
	
	 /**
     * 播放按键声音
     */
    private void playTone(int tone) {
        if (!mDTMFToneEnabled) {
            return;
        }
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();
        if (ringerMode == AudioManager.RINGER_MODE_SILENT
                || ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
            // 静音或者震动时不发出声音
            return;
        }
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                Log.w("TAG", "playTone: mToneGenerator == null, tone: " + tone);
                return;
            }
            mToneGenerator.startTone(tone, DTMF_DURATION_MS);   //发出声音
        }
    }

	/**
	 * 设置电话
	 * 
	 * @author Robin
	 * @Title: setEditText 
	 * @Description: TODO
	 * @param @param string    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @date 2016年9月23日 下午4:12:07
	 */
	private void setEditText(String num) {
		StringBuilder builder = new StringBuilder(edit_phone.getText().toString().trim()) ;
		builder.append(num) ;
		edit_phone.setText(builder.toString());
	}
	
    

}
