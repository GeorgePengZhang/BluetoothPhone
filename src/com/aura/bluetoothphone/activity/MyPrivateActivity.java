package com.aura.bluetoothphone.activity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.adapter.MyListAdapter;
import com.aura.bluetoothphone.interf.RighClickListener;
import com.aura.bluetoothphone.utils.ToastUtil;

/**
 * 蓝牙设备 配置管理界面
 * 
 * @author Robin
 * @ClassName: MyPrivateActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016年9月28日 下午2:23:04
 *
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyPrivateActivity extends BaseActivity implements
		RighClickListener {

	/** 蓝牙开关 */
	private CheckBox cbox_Volumn;
	private static int REQUEST_ENABLE = 10;
	private ListView list_bonded_devices;
	private List<BluetoothDevice> bondedDevicesList;
	private MyListAdapter mBondedAdapter;
	private ListView list_search_devices;
	private List<BluetoothDevice> searchDevicesList;
	private MyListAdapter mSearchAdapter;
	private BluetoothAdapter adapter;;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_private;
	}

	@Override
	protected void findViews() {
		cbox_Volumn = (CheckBox) findViewById(R.id.setting_cbox_volume);
		// 已配对设备列表
		list_bonded_devices = (ListView) findViewById(R.id.list_bonded_devices);
		// 搜索到的设备列表
		list_search_devices = (ListView) findViewById(R.id.list_search_devices);
	}

	@Override
	protected void init() {
		titleView.setListener(this);
		list_bonded_devices.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MyPrivateActivity.this, "已经配对",
						Toast.LENGTH_SHORT).show();
				try {
					// 连接
					// connect(device);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// 为陪对的设备 点击事件
		list_search_devices.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BluetoothDevice device = searchDevicesList.get(position);
				if (null == device.getName() || "".equals(device.getName())) {
					ToastUtil.showToast(MyPrivateActivity.this, "配对取消！");
				} else {
					try {
						// 配对
						Method createBondMethod = BluetoothDevice.class
								.getMethod("createBond");
						createBondMethod.invoke(device);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		// checkbox改变事件，改变之后的ischeked值
		cbox_Volumn
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Intent intent = new Intent();
							// 打开蓝牙设备
							intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
							// 是设备能够被搜索
							intent.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
							// 设置蓝牙可见性，最多300秒
							intent.putExtra(
									BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
									30000);
							startActivityForResult(intent, REQUEST_ENABLE);
						} else {
							titleView.setProgressBar(false);
							adapter.disable();
							// 蓝牙关闭
							// 如果正在搜索，就先取消搜索
							if (adapter.isDiscovering()) {
								adapter.cancelDiscovery();
							}
							// 弹出对话框提示用户是后打开
							searchDevicesList.clear();
							mSearchAdapter.notifyDataSetChanged();
						}
					}
				});
	}

	@Override
	protected void initGetData() {
		bondedDevicesList = new ArrayList<BluetoothDevice>();
		// 设置适配器
		mBondedAdapter = new MyListAdapter(this, bondedDevicesList);
		list_bonded_devices.setAdapter(mBondedAdapter);
		searchDevicesList = new ArrayList<BluetoothDevice>();
		mSearchAdapter = new MyListAdapter(this, searchDevicesList);
		list_search_devices.setAdapter(mSearchAdapter);

		titleView.setTitle("BT Setting");
		titleView.setBackBtn();
	}

	@Override
	protected void widgetListener() {
		// 检查设备是否支持蓝牙,若支持则打开
		checkBluetooth();
		// 获取所有已经绑定的蓝牙设备
		getBondedDevices();
		// 注册用以接收到已搜索到的蓝牙设备的receiver
		IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mFilter.addAction(BluetoothDevice.ACTION_FOUND);
		mFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		mFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		mFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		// 注册广播接收器，接收并处理搜索结果
		registerReceiver(receiver, mFilter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE) {
			titleView.setProgressBar(true);
			titleView.setTitle("Are the search...");
			// 弹出对话框提示用户是后打开
			searchDevicesList.clear();
			mSearchAdapter.notifyDataSetChanged();
			// 如果正在搜索，就先取消搜索
			if (!adapter.isDiscovering()) {
				adapter.cancelDiscovery();
			}
			// 开始搜索蓝牙设备,搜索到的蓝牙设备通过广播返回
			adapter.startDiscovery();
		}

	}

	/**
	 * 检查设备是否支持蓝牙,若支持则打开
	 */
	private void checkBluetooth() {
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter == null) {
			// 设备不支持蓝牙
			Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
			cbox_Volumn.setChecked(false);
		} else {
			// 判断蓝牙是否打开，如果没有则打开蓝牙
			// adapter.enable() 直接打开蓝牙，但是不会弹出提示，以下方式会提示用户是否打开
			if (!adapter.isEnabled()) {
				cbox_Volumn.setChecked(false);
				Intent intent = new Intent();
				// 打开蓝牙设备
				intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				// 是设备能够被搜索
				intent.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				// 设置蓝牙可见性，最多300秒
				intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
						30000);
				startActivityForResult(intent, REQUEST_ENABLE);
			} else {
				cbox_Volumn.setChecked(true);
			}
		}
	}

	/**
	 * 获取所有已经绑定的蓝牙设备
	 */
	private void getBondedDevices() {
		bondedDevicesList.clear();
		Set<BluetoothDevice> devices = adapter.getBondedDevices();
		bondedDevicesList.addAll(devices);
		// 为listview动态设置高度（有多少条目就显示多少条目）
		setListViewHeight(bondedDevicesList.size());
		mBondedAdapter.notifyDataSetChanged();
		// 弹出对话框提示用户是后打开
		searchDevicesList.clear();
		mSearchAdapter.notifyDataSetChanged();
		// 如果正在搜索，就先取消搜索
		if (!adapter.isDiscovering()) {
			adapter.cancelDiscovery();
		}
		// 开始搜索蓝牙设备,搜索到的蓝牙设备通过广播返回
		adapter.startDiscovery();

	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// 获得已经搜索到的蓝牙设备
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				titleView.setProgressBar(true);
				titleView.setTitle("Are the search...");
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// 搜索到的不是已经绑定的蓝牙设备
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					// 防止重复添加
					if (searchDevicesList.indexOf(device) == -1)
						searchDevicesList.add(device);
					// devicesList.add("未配对 | "+device.getName() + "（" +
					// device.getAddress()+"）");
					mSearchAdapter.notifyDataSetChanged();
				}
				// 搜索完成
			} else if (action
					.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				titleView.setProgressBar(false);
				titleView.setTitle("BT Setting");
				mSearchAdapter.notifyDataSetChanged();
			} else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
				// 状态改变的广播
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String name = device.getName();
				if (device.getName().equalsIgnoreCase(name)) {
					int connectState = device.getBondState();
					switch (connectState) {
					case BluetoothDevice.BOND_NONE: // 10
						Toast.makeText(MyPrivateActivity.this,
								"取消配对：" + device.getName(), Toast.LENGTH_SHORT)
								.show();
						break;
					case BluetoothDevice.BOND_BONDING: // 11
						Toast.makeText(MyPrivateActivity.this,
								"正在配对：" + device.getName(), Toast.LENGTH_SHORT)
								.show();
						break;
					case BluetoothDevice.BOND_BONDED: // 12
						Toast.makeText(MyPrivateActivity.this,
								"完成配对：" + device.getName(), Toast.LENGTH_SHORT)
								.show();
						getBondedDevices();
						try {
							// 连接
							connect(device);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	};

	// 蓝牙设备的连接（客户端）
	private void connect(BluetoothDevice device) {
		// 固定的UUID
		final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
		UUID uuid = UUID.fromString(SPP_UUID);
		try {
			BluetoothSocket socket = device
					.createRfcommSocketToServiceRecord(uuid);
			socket.connect();
			// OutputStream outputStream = socket.getOutputStream();
			// InputStream inputStream = socket.getInputStream();
			// outputStream.write("StartOnNet\n".getBytes());
			// outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 为listview动态设置高度（有多少条目就显示多少条目）
	private void setListViewHeight(int count) {
		if (mBondedAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < count; i++) {
			View listItem = mBondedAdapter
					.getView(i, null, list_bonded_devices);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = list_bonded_devices.getLayoutParams();
		params.height = totalHeight + 60;
		list_bonded_devices.setLayoutParams(params);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 解除注册
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getBondedDevices();
	}

	@Override
	public void RighOnClick() {
		getBondedDevices();
	}

}
