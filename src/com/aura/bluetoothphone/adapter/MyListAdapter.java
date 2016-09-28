package com.aura.bluetoothphone.adapter;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.bean.BlueBean;
/**
 * wifi列表 适配器 
 *
 * @author Robin
 * @ClassName: MyListAdapter 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月28日 上午11:33:57 
 *
 */
public class MyListAdapter extends BaseAdapter {
	private Context mContext;
	private List<BluetoothDevice> mDevices;
	private BluetoothDevice device;
	
	public MyListAdapter(Context context, List<BluetoothDevice> devices) {
		mContext = context;
		mDevices = devices;
	}

	@Override
	public int getCount() {
		if (mDevices == null) {
			return 0;
		}
		return mDevices.size();
	}

	@Override
	public Object getItem(int position) {
		return mDevices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.blue_list, null);
		BluetoothDevice bean = mDevices.get(position) ;
		
		// tv.findViewById(R.id.id))获得填充后的布局文件中的具体哪个ID的对象，并赋值
		if (null == bean.getName() || "".equals( bean.getName()) || "null".equals( bean.getName()) ) {
			((TextView) view.findViewById(R.id.deivce_name)).setText(
					"Unknown device");
		} else {
			((TextView) view.findViewById(R.id.deivce_name)).setText(
					bean.getName());
		}
		
		((TextView) view.findViewById(R.id.address)).setText(
				bean.getAddress());
		return view;
	}

}
