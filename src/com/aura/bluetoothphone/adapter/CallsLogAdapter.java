package com.aura.bluetoothphone.adapter;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.bean.CallsLogBean;
import com.aura.bluetoothphone.utils.connects.ContactsPhotoLoader;


/**
 * @ClassName: CallsLogAdapter
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:31:16 PM
 */
public class CallsLogAdapter extends BaseAdapter {
	
	private List<CallsLogBean> mList;
	private Context mContext;
	
	public CallsLogAdapter(Context context, List<CallsLogBean> list) {
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
			convertView = View.inflate(mContext, R.layout.callslog_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.id_image);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.number = (TextView) convertView.findViewById(R.id.number);
			viewHolder.label = (TextView) convertView.findViewById(R.id.label);
			viewHolder.typeicon = convertView.findViewById(R.id.call_type_icons);
			viewHolder.typedate = (TextView) convertView.findViewById(R.id.call_count_and_date);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		CallsLogBean bean = (CallsLogBean) getItem(position);
		
		
		// The date of this call, relative to the current time.
		//设置通话距离现在的时间
        CharSequence dateText = DateUtils.getRelativeTimeSpanString(bean.getDate(),
        			System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE);
        viewHolder.typedate.setText(dateText);
        
        
        //设置通话类型，来电、去电、未接
        int type = bean.getType();
        switch (type) {
		case CallLog.Calls.INCOMING_TYPE:
			viewHolder.typeicon.setBackgroundResource(R.drawable.ic_call_incoming_holo_dark);
			break;
		case CallLog.Calls.OUTGOING_TYPE:
			viewHolder.typeicon.setBackgroundResource(R.drawable.ic_call_outgoing_holo_dark);
			break;
		case CallLog.Calls.MISSED_TYPE:
			viewHolder.typeicon.setBackgroundResource(R.drawable.ic_call_missed_holo_dark);
			break;

		default:
			break;
		}
        
        //------------------------------------
        CharSequence numberFormattedLabel = Phone.getTypeLabel(mContext.getResources(), bean.getType(), bean.getNumbertype());
        
        final CharSequence nameText;
        final CharSequence numberText;
        final CharSequence labelText;
        final CharSequence displayNumber = getDisplayNumber(bean.getNumber(), bean.getFormattednumber());
        if (TextUtils.isEmpty(bean.getName())) {
            nameText = displayNumber;
            if (TextUtils.isEmpty(bean.getGeocodedlocation())) {
                numberText = mContext.getResources().getString(R.string.call_log_empty_gecode);
            } else {
                numberText = bean.getGeocodedlocation();
            }
            labelText = null;
        } else {
            nameText = bean.getName();
            numberText = displayNumber;
            labelText = numberFormattedLabel;
        }
        //设置用户名字
        viewHolder.name.setText(nameText);
        //设置用户电话号码
        viewHolder.number.setText(numberText);
        //设置用户电话类型
        viewHolder.label.setText(labelText);
        //------------------------------------
		
        //设置用户头像
		String lookupuri = bean.getLookupuri();
		if (lookupuri != null) {
			ContactsPhotoLoader.getInstance().loadImage(mContext, viewHolder.image, Uri.parse(bean.getLookupuri()), bean.getPhotoid(), R.drawable.ic_contact_picture_holo_dark);
		} else {
			viewHolder.image.setImageResource(R.drawable.ic_contact_picture_holo_dark);
		}
	
		return convertView;
	}
	
	private static class ViewHolder {
		private ImageView image;
		private TextView name;
		private TextView number;
		private TextView label;
		private TextView typedate;
		private View typeicon;
	}

	
	public static final String UNKNOWN_NUMBER = "-1";
    public static final String PRIVATE_NUMBER = "-2";
    public static final String PAYPHONE_NUMBER = "-3";
    
    public CharSequence getDisplayNumber(CharSequence number, CharSequence formattedNumber) {
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        if (number.equals(UNKNOWN_NUMBER)) {
            return mContext.getResources().getString(R.string.unknown);
        }
        if (number.equals(PRIVATE_NUMBER)) {
            return mContext.getResources().getString(R.string.private_num);
        }
        if (number.equals(PAYPHONE_NUMBER)) {
            return mContext.getResources().getString(R.string.payphone);
        }
        if (TextUtils.isEmpty(formattedNumber)) {
            return number;
        } else {
            return formattedNumber;
        }
    }
}
