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
import com.aura.bluetoothphone.utils.ToastUtil;

/**
 * 联系人详情
 * 
 * @author Robin
 * @ClassName: ContentsDeteles 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月28日 下午4:35:17 
 *
 */
public class ContentsDeteles extends BaseActivity implements OnClickListener{
	
	private TextView txt_name ;
	private TextView txt_phone ;
	private TextView txt_stop_phone ;
	private TextView txt_deletes_people ;
	private String id ;
	
	@Override
	protected int getContentViewId() {
		return R.layout.activity_contents_deteles;
	}

	@Override
	protected void findViews() {
		txt_name			= (TextView)findViewById(R.id.txt_name) ;
		txt_phone			= (TextView)findViewById(R.id.txt_phone) ;
		txt_stop_phone		= (TextView)findViewById(R.id.txt_stop_phone) ;
		txt_deletes_people  = (TextView)findViewById(R.id.txt_deletes_people) ;
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
		txt_stop_phone.setOnClickListener(this);
		txt_deletes_people.setOnClickListener(this);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void initGetData() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			String position = bundle.getString("position") ;
			List<ContactsPhoneBean> mDataList = (List<ContactsPhoneBean>) bundle.getSerializable("mDataList") ;
			ContactsPhoneBean bean = mDataList.get(Integer.valueOf(position)) ;
			id = ""+bean.getContactId() ;
			txt_name.setText(bean.getName());
			txt_phone.setText(bean.getPhone());
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.txt_deletes_people:		//删除联系人
			ToastUtil.showToast(ContentsDeteles.this, "此功能未做");
			break ;
		case R.id.txt_stop_phone:			//拒接电话
			ToastUtil.showToast(ContentsDeteles.this, "此功能未做");
			break ;
		}
		
	}
	
}
