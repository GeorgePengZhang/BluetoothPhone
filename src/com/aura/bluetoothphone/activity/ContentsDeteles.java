package com.aura.bluetoothphone.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.bean.ContactsPhoneBean;

/**
 * 联系人详情
 * 
 * @author Robin
 * @ClassName: ContentsDeteles 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月28日 下午4:35:17 
 *
 */
public class ContentsDeteles extends BaseActivity {
	
	
	private TextView txt_name ;
	private TextView txt_phone ;
	private TextView txt_stop_phone ;
	private String id ;
	
	@Override
	protected int getContentViewId() {
		return R.layout.activity_contents_deteles;
	}

	@Override
	protected void findViews() {
		txt_name		= (TextView)findViewById(R.id.txt_name) ;
		txt_phone		= (TextView)findViewById(R.id.txt_phone) ;
		txt_stop_phone	= (TextView)findViewById(R.id.txt_stop_phone) ;
	}

	@Override
	protected void init() {
		titleView.setTitle("Contact details");
		titleView.setBackBtn();
		titleView.setRightBtn("editor", new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new 
				Intent(Intent.ACTION_EDIT,Uri.parse("content://com.android.contacts/contacts/"+id));
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void widgetListener() {
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void initGetData() {
		Bundle bundle = getIntent().getExtras();
		String position = bundle.getString("position") ;
		id = position ;
		List<ContactsPhoneBean> mDataList = (List<ContactsPhoneBean>) bundle.getSerializable("mDataList") ;
		ContactsPhoneBean bean = mDataList.get(Integer.valueOf(position)) ;
		
		txt_name.setText(bean.getName());
		txt_phone.setText(bean.getPhone());
	}

	
	
	
	
	
	
	
	
	
	
}
