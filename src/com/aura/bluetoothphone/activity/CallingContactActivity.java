package com.aura.bluetoothphone.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.adapter.ContactsPhoneAdapter;
import com.aura.bluetoothphone.bean.ContactsPhoneBean;
import com.aura.bluetoothphone.service.ContactsSyncService;
import com.aura.bluetoothphone.utils.IntentUtil;
import com.aura.bluetoothphone.utils.connects.CnToCharParser;
import com.aura.bluetoothphone.utils.connects.ContactsUtils;
import com.aura.bluetoothphone.view.connect.ClearEditText;
import com.aura.bluetoothphone.view.connect.SideBar;
import com.aura.bluetoothphone.view.connect.SideBar.OnTouchingLetterChangedListener;

/**
 * 通话 可联系人界面
 * 
 * @author Robin
 * @ClassName: CallingContactActivity 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月25日 上午10:26:44 
 *
 */
public class CallingContactActivity extends BaseActivity implements OnTouchingLetterChangedListener{

	//是否显示搜索栏
	private static final boolean HAVE_EDIT = true;
	
	private ListView mListView;
	private SideBar mSideBar;
	private MyQueryHandler myQueryHandler;
	private List<ContactsPhoneBean> mDataList;
	private List<ContactsPhoneBean> mCurShowList;
	private ContactsPhoneAdapter mPhoneAdapter;
	private TextView mContactsCounts;
	private TextView mEmptyView;
	private ClearEditText mClearEdit;
		
	@Override
	protected int getContentViewId() {
		return R.layout.contacts_layout;
	}

	@Override
	protected void findViews() {
		mListView = (ListView) findViewById(R.id.id_listview);
		mSideBar = (SideBar) findViewById(R.id.id_sidebar);
		mSideBar.setOnTouchingLetterChangedListener(this);
		mEmptyView = (TextView) findViewById(R.id.id_empty);
	    mEmptyView.setText(getString(R.string.noContactsHelpText));
	    mListView.setEmptyView(mEmptyView);
	    
	}
	
	@Override
	protected void widgetListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ContactsPhoneBean bean = mDataList.get(position) ;
				Bundle build = new Bundle() ;
				build.putString("position", ""+position);
				build.putSerializable("mDataList", (Serializable) mDataList);
				IntentUtil.gotoActivity(context, ContentsDeteles.class,build);
			}
		});
	}
	
	@Override
	protected void init() {
		Intent intent = new Intent(CallingContactActivity.this, ContactsSyncService.class);
	    startService(intent);
	    
	    IntentFilter filter = new IntentFilter(ContactsSyncService.UPDATE_CONTACTS);
	    LocalBroadcastManager.getInstance(CallingContactActivity.this).registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	protected void initGetData() {
		if (HAVE_EDIT) {
			mClearEdit = (ClearEditText) findViewById(R.id.id_edit);
			mClearEdit.setVisibility(View.VISIBLE);
			mClearEdit.addTextChangedListener(mTextWatcher);
		}
		
		myQueryHandler = new MyQueryHandler(CallingContactActivity.this.getContentResolver());
		myQueryHandler.startQuery(0, null, ContactsUtils.Phone.CONTENT_CALLABLES_URI, ContactsUtils.Phone.PROJECTION_PRIMARY, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
		mDataList = new ArrayList<ContactsPhoneBean>();
		
	    mContactsCounts = new TextView(CallingContactActivity.this);
	    mContactsCounts.setGravity(Gravity.CENTER);
	    mListView.addFooterView(mContactsCounts);
	    mContactsCounts.setText(mDataList.size()+"个联系人");
	    
	    titleView.setTitle("联系人");
	    titleView.setBackBtn();
		titleView.setRightBtn("添加", new OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开新增联系人界面
				Intent intent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public void onResume() {
		super.onResume();
		
		//
		if (mPhoneAdapter != null) {
			mPhoneAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LocalBroadcastManager.getInstance(CallingContactActivity.this).unregisterReceiver(mBroadcastReceiver);
	}
	
	BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ContactsSyncService.UPDATE_CONTACTS.equals(action)) {
				if (myQueryHandler != null) {
					myQueryHandler.cancelOperation(0);
					myQueryHandler.startQuery(0, null, ContactsUtils.Phone.CONTENT_CALLABLES_URI, ContactsUtils.Phone.PROJECTION_PRIMARY, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
				}
			}
		}
		
	};
	

	@Override
	public void onTouchingLetterChanged(String s) {
		if (mPhoneAdapter != null && mCurShowList != null) {
			for (int i = 0; i < mCurShowList.size(); i++) {
				ContactsPhoneBean bean = mCurShowList.get(i);
				if (bean.getFirstLetter().equals(s)) {
					mListView.setSelection(i);
					break;
				}
			}
		}
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
	
	private TextWatcher mTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			udpateFilterDataList(s.toString());
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
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
	 * 更新联系人列表
	 * @param list
	 */
	private void updateListAdapter(List<ContactsPhoneBean> list) {
		if (mCurShowList != null) {
			mCurShowList = null;
		}
		mCurShowList = list;
		mPhoneAdapter = new ContactsPhoneAdapter(CallingContactActivity.this, list);
		mListView.setAdapter(mPhoneAdapter);
		mContactsCounts.setText(list.size()+"个联系人");
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

}
