package com.aura.bluetoothphone.activity;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.utils.IntentUtil;
import com.aura.bluetoothphone.utils.LogUtil;
import com.aura.bluetoothphone.utils.StringUtil;
import com.aura.bluetoothphone.utils.ToastUtil;
import com.aura.bluetoothphone.view.CircleImageView;

/**
 * 通话页面
 * 
 * @author Robin
 * @ClassName: CallingActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016年10月24日 下午4:34:50
 *
 */
@SuppressWarnings("unused")
public class CallingActivity extends Activity implements OnClickListener {
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
	/** 来电头像 */
	private CircleImageView image_head;
	/** 来电 号码 */
	private TextView txt_phone_number;
	/** 来电计时 、 来电提示 */
	private TextView txt_number_and_countdown;
	/** 来电 短信提示 */
	private ImageButton imageBut_sms;
	/** 接听 */
	private ImageButton imageBut_answer;
	/** 拒接 */
	private ImageButton imageBut_reject;
	/** 联系人 */
	private ImageButton imageBut_contents;
	/** 键盘弹出 */
	private ImageButton imageBut_keys;
	/** 录音 */
	private ImageButton imageBut_recording;
	/** 扩音 */
	private ImageButton imageBut_volume;
	/** 挂断 */
	private ImageButton imageBut_hangup;

	/** 此view 是用于 来电 */
	private LinearLayout view_call_operation;
	/** 通话 view */
	private LinearLayout view_call_phone;
	/** 键盘 view */
	private LinearLayout view_keyboard;

	private boolean mSpeekModle = true; // 扩音开关
	private boolean mKeyBooard = true; // 键盘开关
	private boolean mRecording = true; // 录音开关
	private int mCountdown = 0; // 倒计时数据
	private int mFrequency = 1000; // 倒计时频率
	private Timer timer = new Timer();
	private static boolean mDTMFToneEnabled; // 系统参数“按键操作音”标志位
	private Object mToneGeneratorLock = new Object(); // 监视器对象锁
	private ToneGenerator mToneGenerator; // 声音产生器
	private static final int DTMF_DURATION_MS = 120; // 声音的播放时间
	public static final int NUM_ONE = 1;
	public static final int TELE_START_TIME = 2; // 开始录音了
	public static final int TELE_END_TIME = 3; // 录音结束
	public static final int TELE_FAILURE_TIME = 4; // 录音失败
	private String phoneNumber = ""; // 电话号码
	private MediaRecorder recorder = null; // 录音控件
	private File root_file, file; // 录音保存文件夹
	private String root_directory; // 文件保存路径
	/** 上次按下的时间 */
	private long lastExitTime = 0;
	
	private String[] mNotifacations = {"你好,我现在有事,一会再联系你！","正在开会，稍后回复你！","现在不方便接电话！","在吃饭，一会打给你！","在外面不方便接电话，稍后回复你！"};
	private int mRingPosition = 0;
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {    
		// TODO Auto-generated method stub    
		super.onWindowFocusChanged(hasFocus);    
		try {     
			Object service = getSystemService("statusbar");      
			Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");      
			Method test = statusbarManager.getMethod("collapse");      
			test.invoke(service);    
			} catch (Exception ex) {      
				ex.printStackTrace();    
				}
		} 
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		   getWindow().setFlags(0x400, 0x400);
	        getWindow().setFlags(0x80, 0x80);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		
		setContentView(R.layout.activity_calling);
		findViews();
		widgetListener();
		initGetData();
	}

	private void findViews() {
		btn_one = (Button) findViewById(R.id.btn_one);
		btn_two = (Button) findViewById(R.id.btn_two);
		btn_three = (Button) findViewById(R.id.btn_three);
		btn_four = (Button) findViewById(R.id.btn_four);
		btn_five = (Button) findViewById(R.id.btn_five);
		btn_six = (Button) findViewById(R.id.btn_six);
		btn_seven = (Button) findViewById(R.id.btn_seven);
		btn_eight = (Button) findViewById(R.id.btn_eight);
		btn_nine = (Button) findViewById(R.id.btn_nine);
		btn_star = (Button) findViewById(R.id.btn_star);
		btn_zero = (Button) findViewById(R.id.btn_zero);
		btn_jing = (Button) findViewById(R.id.btn_jing);

		view_call_operation = (LinearLayout) findViewById(R.id.view_call_operation);
		view_call_phone = (LinearLayout) findViewById(R.id.view_call_phone);
		view_keyboard = (LinearLayout) findViewById(R.id.view_keyboard);
		image_head = (CircleImageView) findViewById(R.id.image_head_imageView);
		txt_phone_number = (TextView) findViewById(R.id.txt_phone_number);
		txt_number_and_countdown = (TextView) findViewById(R.id.txt_number_and_countdown);
		imageBut_sms = (ImageButton) findViewById(R.id.imageBut_sms);
		imageBut_answer = (ImageButton) findViewById(R.id.imageBut_answer);
		imageBut_reject = (ImageButton) findViewById(R.id.imageBut_reject);
		imageBut_contents = (ImageButton) findViewById(R.id.imageBut_contents);
		imageBut_keys = (ImageButton) findViewById(R.id.imageBut_keys);
		imageBut_recording = (ImageButton) findViewById(R.id.imageBut_recording);
		imageBut_volume = (ImageButton) findViewById(R.id.imageBut_volume);
		imageBut_hangup = (ImageButton) findViewById(R.id.imageBut_hangup);

	}

	private void widgetListener() {
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
		
		imageBut_volume.setOnClickListener(this);
		imageBut_contents.setOnClickListener(this);
		imageBut_hangup.setOnClickListener(this);
		imageBut_keys.setOnClickListener(this);
		imageBut_recording.setOnClickListener(this);
		imageBut_sms.setOnClickListener(this);
		imageBut_answer.setOnClickListener(this);
		imageBut_reject.setOnClickListener(this);
	}

	private void initGetData() {
		txt_number_and_countdown.setText("来电");
		phoneNumber = txt_phone_number.getText().toString();
		// 按键声音播放设置及初始化
		try {
			// 获取系统参数“按键操作音”是否开启
			mDTMFToneEnabled = Settings.System.getInt(
					CallingActivity.this.getContentResolver(),
					Settings.System.DTMF_TONE_WHEN_DIALING, 1) == 1;
			synchronized (mToneGeneratorLock) {
				if (mDTMFToneEnabled && mToneGenerator == null) {
					mToneGenerator = new ToneGenerator(
							AudioManager.STREAM_DTMF, 80); // 设置声音的大小
					CallingActivity.this
							.setVolumeControlStream(AudioManager.STREAM_DTMF);
				}
			}
		} catch (Exception e) {
			mDTMFToneEnabled = false;
			mToneGenerator = null;
		}
	}

	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CallingActivity.NUM_ONE:
				txt_number_and_countdown.setText(getStringTime(mCountdown++));
				break;
			case CallingActivity.TELE_START_TIME:
				ToastUtil.showToast(CallingActivity.this, "开始录音啦！");
				break;
			case CallingActivity.TELE_END_TIME:
				ToastUtil.showToast(CallingActivity.this, "录音结束！文件保存路径为：" + ""
						+ root_directory);
				break;
			case CallingActivity.TELE_FAILURE_TIME:
				ToastUtil.showToast(CallingActivity.this, "录音失败！");
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// 需要做的事:发送消息
			Message message = new Message();
			message.what = CallingActivity.NUM_ONE;
			myHandler.sendMessage(message);
		}
	};

	// 取消任务计时
	public void stopClick() {
		timer.cancel();
	}

	// 格式化倒计时
	private String getStringTime(int cnt) {
		int hour = cnt / 3600;
		int min = cnt % 3600 / 60;
		int second = cnt % 60;
		return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imageBut_volume: // 扩音
			if (mSpeekModle) {
				StringUtil.setSpeekModle(CallingActivity.this, mSpeekModle);
				imageBut_volume.setBackground(getResources().getDrawable(
						R.drawable.tableid_btn_volume_true));
				mSpeekModle = false;
			} else {
				StringUtil.setSpeekModle(CallingActivity.this, mSpeekModle);
				imageBut_volume.setBackground(getResources().getDrawable(
						R.drawable.tableid_btn_volume));
				mSpeekModle = true;
			}
			break;
		case R.id.imageBut_contents: // 联系人
			IntentUtil.gotoActivity(CallingActivity.this,
					CallingContactActivity.class);
			break;
		case R.id.imageBut_hangup: // 挂断
			if (recorder != null) {
				recorder.stop();
				recorder.release();
				recorder = null;
				Message msg_start = new Message();
				msg_start.what = TELE_END_TIME;
				myHandler.sendMessage(msg_start);
			}
			stopClick();
			finish();
			break;
		case R.id.imageBut_keys: // 键盘弹出显示
			if (mKeyBooard) {
				imageBut_keys.setBackground(getResources().getDrawable(
						R.drawable.tableid_btn_keys_true));
				mKeyBooard = false;
				view_keyboard.setVisibility(View.VISIBLE);
				view_keyboard.setAnimation(StringUtil.moveToViewLocation());
			} else {
				imageBut_keys.setBackground(getResources().getDrawable(
						R.drawable.tableid_btn_keys));
				mKeyBooard = true;
				view_keyboard.setVisibility(View.GONE);
				view_keyboard.setAnimation(StringUtil.moveToViewBottom());
			}
			break;
		case R.id.imageBut_recording: // 录音
			if (mRecording) {
				recorDing(phoneNumber, mRecording);
				imageBut_recording.setBackground(getResources().getDrawable(
						R.drawable.tableid_btn_recording_true));
				mRecording = false;
				lastExitTime = System.currentTimeMillis();
				ToastUtil.showToast(CallingActivity.this, "开始录音");
			} else {
				// 判断2次点击事件时间
				if ((System.currentTimeMillis() - lastExitTime) < 3000) {
					ToastUtil.showToast(CallingActivity.this, "录音三秒以上才可以取消");
					return;
				}
				recorDing(phoneNumber, mRecording);
				imageBut_recording.setBackground(getResources().getDrawable(
						R.drawable.tableid_btn_recording));
				mRecording = true;
				ToastUtil.showToast(CallingActivity.this, "结束录音");
			}

			break;
		case R.id.imageBut_sms:				// 短信 回复
			setNotifacation() ;
			
			break ;
		case R.id.imageBut_answer:			// 接听 来电
			goAnswer() ;
			break ;
		case R.id.imageBut_reject:			// 拒接 来电
			finish();

			break ;
		case R.id.btn_one:
			playTone(ToneGenerator.TONE_DTMF_1);
			setEditText("1");
			break;
		case R.id.btn_two:
			playTone(ToneGenerator.TONE_DTMF_2);
			setEditText("2");
			break;
		case R.id.btn_three:
			playTone(ToneGenerator.TONE_DTMF_3);
			setEditText("3");
			break;
		case R.id.btn_four:
			playTone(ToneGenerator.TONE_DTMF_4);
			setEditText("4");
			break;
		case R.id.btn_five:
			playTone(ToneGenerator.TONE_DTMF_5);
			setEditText("5");
			break;
		case R.id.btn_six:
			playTone(ToneGenerator.TONE_DTMF_6);
			setEditText("6");
			break;
		case R.id.btn_seven:
			playTone(ToneGenerator.TONE_DTMF_7);
			setEditText("7");
			break;
		case R.id.btn_eight:
			playTone(ToneGenerator.TONE_DTMF_8);
			setEditText("8");
			break;
		case R.id.btn_nine:
			playTone(ToneGenerator.TONE_DTMF_9);
			setEditText("9");
			break;
		case R.id.btn_star:
			playTone(ToneGenerator.TONE_DTMF_S);
			setEditText("*");
			break;
		case R.id.btn_zero:
			playTone(ToneGenerator.TONE_DTMF_0);
			setEditText("0");
			break;
		case R.id.btn_jing:
			playTone(ToneGenerator.TONE_DTMF_P);
			setEditText("#");
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
		AudioManager audioManager = (AudioManager) CallingActivity.this
				.getSystemService(Context.AUDIO_SERVICE);
		int ringerMode = audioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_SILENT
				|| ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
			// 静音或者震动时不发出声音
			return;
		}
		synchronized (mToneGeneratorLock) {
			if (mToneGenerator == null) {
				LogUtil.out("TAG", "playTone: mToneGenerator == null, tone: "
						+ tone);
				return;
			}
			mToneGenerator.startTone(tone, DTMF_DURATION_MS); // 发出声音
		}
	}

	/**
	 * 设置电话
	 * 
	 * @author Robin
	 * @Title: setEditText
	 * @Description: TODO
	 * @param @param string 设定文件
	 * @return void 返回类型
	 * @throws
	 * @date 2016年9月23日 下午4:12:07
	 */
	private void setEditText(String num) {
		// txt_number_and_countdown.setText("");
		StringBuilder builder = new StringBuilder(txt_phone_number.getText()
				.toString().trim());
		builder.append(num);
		txt_phone_number.setText(builder.toString());
	}

	/**
	 * 电话录音
	 * 
	 * @author Robin
	 * @Title: recorDing
	 * @Description: TODO
	 * @param @param phoneNumber 电话号码
	 * @param @param mRecording 录音标志
	 * @return void 返回类型
	 * @throws
	 * @date 2016年10月25日 下午2:24:04
	 */
	public void recorDing(String phoneNumber, boolean mRecording) {
		if (TextUtils.isEmpty(phoneNumber)) {
			return;
		}
		Message msg_start = new Message();
		if (mRecording) {
			try {
				root_directory = Environment.getExternalStorageDirectory()
						+ "/Aura_recorded_call";
				root_file = new File(root_directory);
				if (!root_file.exists()) {
					root_file.mkdir();
				}
				if (phoneNumber == null) {	
					phoneNumber = "call";
				}
				String record_call = root_directory + "/" + phoneNumber + "_"
						+ System.currentTimeMillis() + ".amr";
				file = new File(record_call);
				if (!file.exists()) {
					file.createNewFile();
				}

				recorder = new MediaRecorder();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 获得声音数据源    VOICE_CALL
				recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT); // 格式输出
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); // 音频编码方式
				recorder.setOutputFile(file.getAbsolutePath()); // 输出文件
				recorder.prepare(); // 准备

				recorder.start();

				msg_start.what = TELE_START_TIME;

			} catch (Exception e) {
				root_directory = "null";
				recorder = null;
				msg_start.what = TELE_FAILURE_TIME;
				e.printStackTrace();
			}
		} else {
			if (recorder != null) {
				recorder.stop();
				recorder.release();
				recorder = null;
				msg_start.what = TELE_END_TIME;

			} else {
				msg_start.what = TELE_FAILURE_TIME;
			}
		}
		myHandler.sendMessage(msg_start);
	}
	
	/**
	 * 弹出选择 列表
	 * @author Robin
	 * @Title: setNotifacation 
	 * @Description: TODO
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @date 2016年10月26日 上午10:22:07
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setNotifacation() {
		mRingPosition = 0 ;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(R.drawable.logo) ;
		builder.setTitle("短息回复") ;
		builder.setSingleChoiceItems(mNotifacations, R.layout.msm_dialog_item, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mRingPosition = which+1;
			}
		});
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
            public void onClick(DialogInterface dialog, int which) {
            	if (mRingPosition==0) {
					return;
				}
            	ToastUtil.showToast(CallingActivity.this, mNotifacations[mRingPosition-1]);
            }
	    });
		
	    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    	@Override
            public void onClick(DialogInterface dialog, int which) {
            	
            }
	    });
		
		builder.show();
	}
	
	/**
	 * 接听 来电
	 * @author Robin
	 * @Title: goAnswer 
	 * @Description: TODO
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @date 2016年10月26日 上午11:21:26
	 */
	private void goAnswer() {
		view_call_phone.setAnimation(StringUtil.moveToViewLocation());
		view_call_phone.setVisibility(View.VISIBLE);
		view_call_operation.setAnimation(StringUtil.moveToViewBottom());
		view_call_operation.setVisibility(View.GONE);
		
		timer.schedule(task, 0, mFrequency); // 0s后执行task,经过1s再次执行
		
//		RingtoneManager rm = new RingtoneManager(this);
//		rm.setType(RingtoneManager.TYPE_RINGTONE);
//		rm.getCursor() ;
	}


}







