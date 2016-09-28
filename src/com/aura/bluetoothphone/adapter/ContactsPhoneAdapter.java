package com.aura.bluetoothphone.adapter;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.bean.ContactsPhoneBean;
import com.aura.bluetoothphone.utils.connects.ContactsPhotoLoader;
import com.aura.bluetoothphone.utils.connects.ContactsUtils;


/**
 * @ClassName: ContactsPhoneAdapter
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:31:00 PM
 */
public class ContactsPhoneAdapter extends BaseAdapter {
	
	private List<ContactsPhoneBean> mList;
	private Context mContext;
	
	public ContactsPhoneAdapter(Context context, List<ContactsPhoneBean> list) {
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.contacts_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.header = convertView.findViewById(R.id.id_header);
			viewHolder.headerIndex = (TextView) convertView.findViewById(R.id.id_headerindex);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.id_photo);
			viewHolder.name = (TextView) convertView.findViewById(R.id.id_name);
			viewHolder.number = (TextView) convertView.findViewById(R.id.id_phone);
			viewHolder.type = (TextView) convertView.findViewById(R.id.id_type);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ContactsPhoneBean bean = (ContactsPhoneBean) getItem(position);
		
		// 上一条数据的首字母
		String previewLetter = (position - 1) >= 0 ? mList.get(position-1).getFirstLetter() : " ";
		String firstLetter = bean.getFirstLetter();
		if (!firstLetter.equals(previewLetter)) {
			viewHolder.header.setVisibility(View.VISIBLE);
			viewHolder.headerIndex.setText(firstLetter);
		} else {
			viewHolder.header.setVisibility(View.GONE);
		}
		
		
		viewHolder.name.setText(Html.fromHtml(bean.getName()));
		viewHolder.number.setText(Html.fromHtml(bean.getNumber()));
		viewHolder.type.setText(ContactsContract.CommonDataKinds.Phone.getTypeLabel(mContext.getResources(), bean.getType(), null));
		
		Uri uri = ContactsUtils.getContactPhotoUri(bean.getContactId());
		ContactsPhotoLoader.getInstance().loadImage(mContext, viewHolder.image, uri, bean.getPhotoId(), R.drawable.ic_contact_picture_holo_dark);
		
		return convertView;
	}
	
	private static class ViewHolder {
		private View header;
		private TextView headerIndex;
		private ImageView image;
		private TextView name;
		private TextView number;
		private TextView type;
	}
}
