package com.aura.bluetoothphone.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.adapter.CallsLogAdapter;
import com.aura.bluetoothphone.bean.CallsLogBean;
import com.aura.bluetoothphone.utils.DialogUtil;
import com.aura.bluetoothphone.utils.connects.ContactsUtils;


/**
 * 通话记录
 * @author Robin
 * @ClassName: CallsLogListFragment 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月28日 下午3:59:49 
 *
 */
public class CallsLogListFragment extends BaseFragment {

	private MyQueryHandler myQueryHandler;
	private ListView mListView;
	private List<CallsLogBean> mDataList;
	private TextView mEmptyView;
	private CallsLogAdapter callsLogAdapter;
	
	
	

	@Override
	protected View getViews() {
		return View.inflate(context, R.layout.callslog_layout, null);
	}

	@Override
	protected void findViews() {
		mListView = (ListView) findViewById(R.id.id_listview);
		mDataList = new ArrayList<CallsLogBean>();
		mEmptyView = (TextView) findViewById(R.id.id_empty);
	    mListView.setEmptyView(mEmptyView);
		
	}

	
	@Override
	protected void init() {
		myQueryHandler = new MyQueryHandler(getActivity().getContentResolver());
		myQueryHandler.startQuery(0, null, ContactsUtils.Calls.CONTENT_URI, ContactsUtils.Calls.PROJECTION_PRIMARY, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
	}

	@Override
	public void initGetData() {
		titleView.setTitle("最近联系人");
        titleView.setRightBtn("清空", new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtil.showWf(context , "Delete all call records");
			}
		});
	}
	
	@Override
	protected void widgetListener() {
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
						CallsLogBean bean = CallsLogBean.getBeanFromCursor(cursor);
						Log.d("TAG", "bean:"+bean);
						mDataList.add(bean);
					}
					
					cursor.close();
					callsLogAdapter = new CallsLogAdapter(getActivity(), mDataList);
					mListView.setAdapter(callsLogAdapter);
				}
			}
		}

}
