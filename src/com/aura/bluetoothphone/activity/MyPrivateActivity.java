package com.aura.bluetoothphone.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
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
import com.aura.bluetoothphone.utils.DialogUtil;
import com.aura.bluetoothphone.utils.LogUtil;
import com.aura.bluetoothphone.utils.ToastUtil;
import com.aura.bluetoothphone.widget.CustomDialog;

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
				BluetoothDevice device = bondedDevicesList.get(position) ;
				showSelectImageDg(context,device);
			}
		});

		// 为配对的设备 点击事件
		list_search_devices.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int prot = position;
				DialogUtil.showMessageDg(MyPrivateActivity.this,
						getString(R.string.prompt),
						getString(R.string.auth_process_blue),
						new CustomDialog.OnClickListener() {
							@Override
							public void onClick(CustomDialog dialog, int id,
									Object object) {
								BluetoothDevice device = searchDevicesList
										.get(prot);
								if (null == device.getName()
										|| "".equals(device.getName())) {
									ToastUtil.showToast(MyPrivateActivity.this,
											"配对取消！");
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
								dialog.dismiss();
							}
						});

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
	

  	/**
  	 * 取消配对
  	 * @author Robin
  	 * @Title: unpairDevice 
  	 * @Description: TODO
  	 * @param @param device    设定文件 
  	 * @return void    返回类型 
  	 * @throws 
  	 * @date 2016年10月18日 上午9:30:25
  	 */
	private void unpairDevice(BluetoothDevice device) {
		try {
			Method m = device.getClass()
					.getMethod("removeBond", (Class[]) null);
			m.invoke(device, (Object[]) null);
		} catch (Exception e) {
			LogUtil.out("TAG:"+e.getMessage());

		}
		getBondedDevices();
		mBondedAdapter.notifyDataSetChanged();
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
		if (requestCode == REQUEST_ENABLE && resultCode == 120) {
			// cbox_Volumn.setChecked(true);
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
		} else {
			cbox_Volumn.setChecked(false);
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
				cbox_Volumn.setChecked(true);
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
					case BluetoothDevice.BOND_NONE: 					// 10
						ToastUtil.showToast(MyPrivateActivity.this, "取消配对：" + device.getName());
						break;
					case BluetoothDevice.BOND_BONDING: 					// 11
						ToastUtil.showToast(MyPrivateActivity.this, "取消配对：" + device.getName());
						break;
					case BluetoothDevice.BOND_BONDED: 					// 12
						ToastUtil.showToast(MyPrivateActivity.this, "完成配对：" + device.getName());
						getBondedDevices();
//						try {
//							// 连接
//							connect(device);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						break;
					}
				}
			}
		}
	};
	 BluetoothDevice device ;  
	// 蓝牙设备的连接（客户端）
	private void connect(BluetoothDevice devices) {
		// 如果正在搜索，就先取消搜索
		if (!adapter.isDiscovering()) {
			adapter.cancelDiscovery();
		}
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// 固定的UUID
//				final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
//				UUID uuid = UUID.fromString(SPP_UUID);
//				try {  
//					BluetoothSocket socket = device
//							.createRfcommSocketToServiceRecord(uuid);
//					socket.connect();
//					// OutputStream outputStream = socket.getOutputStream();
//					// InputStream inputStream = socket.getInputStream();
//					// outputStream.write("StartOnNet\n".getBytes());
//					// outputStream.flush();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
		device = devices ;
		adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.getProfileProxy(MyPrivateActivity.this, mProfileServiceListener, BluetoothProfile.A2DP);
		
		
	}
    BluetoothHeadset bh ;
    BluetoothA2dp mBTA2DP;
    
	private BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {

		@Override
		public void onServiceConnected(int profile, BluetoothProfile proxy) {
			 
			 
			 Log.i("log", "onServiceConnected");
	            try {
	                if (profile == BluetoothProfile.HEADSET) {
	                    bh = (BluetoothHeadset) proxy;
	                    if (bh.getConnectionState(device) != BluetoothProfile.STATE_CONNECTED){
	                        bh.getClass()
	                                .getMethod("connect", BluetoothDevice.class)
	                                .invoke(bh, device);
	                    }
	                } else if (profile == BluetoothProfile.A2DP) {
	                	mBTA2DP = (BluetoothA2dp)proxy;
//	                	BluetoothHeadsetClient mclient=(BluetoothHeadsetClient)proxy;
//	                    if (mBTA2DP.getConnectionState(device) != BluetoothProfile.STATE_CONNECTED){
//	                    	mBTA2DP.getClass()
//	                                .getMethod("connect", BluetoothDevice.class)
//	                                .invoke(mBTA2DP, device);
//	                    }
	                    Method m = mBTA2DP.getClass().getDeclaredMethod("connect",BluetoothDevice.class);    
	                    m.setAccessible(true);  
	                    //连接Headset  
	                    boolean successHeadset = (Boolean)m.invoke(mBTA2DP, device);  
	                    if (successHeadset) {
	                    	ToastUtil.showToast(MyPrivateActivity.this, "连接成功！！！！");
						} else {
							ToastUtil.showToast(MyPrivateActivity.this, "连接失败！！！！");
						}
	                }
	                if (bh != null && mBTA2DP != null) {
//	                    new A2dpConnectionThread(context, device, mBTA2DP, bh).start();
	                	
	                	
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
		}

		@Override
		public void onServiceDisconnected(int profile) {
			
		}};

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
		// 如果正在搜索，就先取消搜索
		if (adapter.isDiscovering()) {
			adapter.cancelDiscovery();
		}
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
	
	/**
     * 显示选择图片的对话框
     * 
     * @author Robin
     * @Title: showSelectImageDg 
     * @Description: TODO
     * @param @param context    设定文件 
     * @return void    返回类型 
     * @throws 
     * @date 2016年10月9日 上午11:01:49 
     */
	public void showSelectImageDg(final Context context,final BluetoothDevice device) {

		String[] items = context.getResources().getStringArray(R.array.select_blue);

		CustomDialog dialog = new CustomDialog(context);
		dialog.setTitle(context.getString(R.string.dialog_select_blue));
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		dialog.setSingleSelectItems(items, new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				switch (id) {
				case 0:
					// 取消配对
					unpairDevice(device) ;
					break;
				case 1:
					// 连接蓝牙
					try {
						// 连接
						 connect(device);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				default:
					break;
				}
				dialog.dismiss();
			}
		});

		dialog.show();

	}

}
