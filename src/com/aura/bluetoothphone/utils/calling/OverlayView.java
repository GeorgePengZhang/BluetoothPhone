package com.aura.bluetoothphone.utils.calling;

import java.io.File;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.activity.CallingActivity;
import com.aura.bluetoothphone.activity.CallingContactActivity;
import com.aura.bluetoothphone.utils.IntentUtil;
import com.aura.bluetoothphone.utils.LogUtil;
import com.aura.bluetoothphone.utils.StringUtil;
import com.aura.bluetoothphone.utils.ToastUtil;
import com.aura.bluetoothphone.view.CircleImageView;

/**
 * 半屏显示
 * 
 * @author likebamboo
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class OverlayView extends Overlay {

	private static Context mContext = null;
	/** 数字键 1 */
	private static Button btn_one;
	/** 数字键 2 */
	private static Button btn_two;
	/** 数字键 3 */
	private static Button btn_three;
	/** 数字键 4 */
	private static Button btn_four;
	/** 数字键 5 */
	private static Button btn_five;
	/** 数字键 6 */
	private static Button btn_six;
	/** 数字键 7 */
	private static Button btn_seven;
	/** 数字键 8 */
	private static Button btn_eight;
	/** 数字键 9 */
	private static Button btn_nine;
	/** 数字键 * */
	private static Button btn_star;
	/** 数字键 0 */
	private static Button btn_zero;
	/** 数字键 # */
	private static Button btn_jing;
	/** 来电头像 */
	private static CircleImageView image_head;
	/** 来电 号码 */
	private static TextView txt_phone_number;
	/** 来电计时 、 来电提示 */
	private static TextView txt_number_and_countdown;
	/** 来电 短信提示 */
	private static ImageButton imageBut_sms;
	/** 接听 */
	private static ImageButton imageBut_answer;
	/** 拒接 */
	private static ImageButton imageBut_reject;
	/** 联系人 */
	private static ImageButton imageBut_contents;
	/** 键盘弹出 */
	private static ImageButton imageBut_keys;
	/** 录音 */
	private static ImageButton imageBut_recording;
	/** 扩音 */
	private static ImageButton imageBut_volume;
	/** 挂断 */
	private static ImageButton imageBut_hangup;

	/** 此view 是用于 来电 */
	private static LinearLayout view_call_operation;
	/** 通话 view */
	private static LinearLayout view_call_phone;
	/** 键盘 view */
	private static LinearLayout view_keyboard;

	private static boolean mSpeekModle = true; // 扩音开关
	private static boolean mKeyBooard = true; // 键盘开关
	private static boolean mRecording = true; // 录音开关
	private static int mCountdown = 0; // 倒计时数据
	private static int mFrequency = 1000; // 倒计时频率
	private static Timer timer = new Timer();
	private static boolean mDTMFToneEnabled; // 系统参数“按键操作音”标志位
	private static Object mToneGeneratorLock = new Object(); // 监视器对象锁
	private static ToneGenerator mToneGenerator; // 声音产生器
	private static final int DTMF_DURATION_MS = 120; // 声音的播放时间
	public static final int NUM_ONE = 1;
	public static final int TELE_START_TIME = 2; // 开始录音了
	public static final int TELE_END_TIME = 3; // 录音结束
	public static final int TELE_FAILURE_TIME = 4; // 录音失败
	private static String phoneNumber = ""; // 电话号码
	private static MediaRecorder recorder = null; // 录音控件
	private static File root_file; // 录音保存文件夹
	private static File file;
	private static String root_directory; // 文件保存路径
	/** 上次按下的时间 */
	private static long lastExitTime = 0;

	private static String[] mNotifacations = { "你好,我现在有事,一会再联系你！", "正在开会，稍后回复你！",
			"现在不方便接电话！", "在吃饭，一会打给你！", "在外面不方便接电话，稍后回复你！" };
	private static int mRingPosition = 0;

	/**
	 * 显示
	 * 
	 * @param context
	 *            上下文对象
	 * @param number
	 */
	public static void show(final Context context, final String number,
			final int percentScreen) {
		synchronized (monitor) {
			mContext = context;

			init(context, number, R.layout.activity_calling, percentScreen);
		}
	}

	/**
	 * 隐藏
	 * 
	 * @param context
	 */
	public static void hide(Context context) {
		mContext = context;
		synchronized (monitor) {
			if (mOverlay != null) {
				try {
					WindowManager wm = (WindowManager) context
							.getSystemService(Context.WINDOW_SERVICE);
					// Remove view from WindowManager
					wm.removeView(mOverlay);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mOverlay = null;
			}
		}
	}

	/**
	 * 初始化布局
	 * 
	 * @param context
	 *            上下文对象
	 * @param number
	 *            电话号码
	 * @param layout
	 *            布局文件
	 * @return 布局
	 */
	private static ViewGroup init(Context context, String number, int layout,
			int percentScreen) {
		WindowManager.LayoutParams params = getShowingParams();
		int height = getHeight(context, percentScreen);
		params.height = height;
		ViewGroup overlay = init(context, layout, params);

		initView(overlay, number, percentScreen);
		widgetListener();
		return overlay;
	}

	/**
	 * 初始化界面
	 */
	private static void initView(View v, String phoneNum, int percentScreen) {
		btn_one = (Button) v.findViewById(R.id.btn_one);
		btn_two = (Button) v.findViewById(R.id.btn_two);
		btn_three = (Button) v.findViewById(R.id.btn_three);
		btn_four = (Button) v.findViewById(R.id.btn_four);
		btn_five = (Button) v.findViewById(R.id.btn_five);
		btn_six = (Button) v.findViewById(R.id.btn_six);
		btn_seven = (Button) v.findViewById(R.id.btn_seven);
		btn_eight = (Button) v.findViewById(R.id.btn_eight);
		btn_nine = (Button) v.findViewById(R.id.btn_nine);
		btn_star = (Button) v.findViewById(R.id.btn_star);
		btn_zero = (Button) v.findViewById(R.id.btn_zero);
		btn_jing = (Button) v.findViewById(R.id.btn_jing);

		view_call_operation = (LinearLayout) v
				.findViewById(R.id.view_call_operation);
		view_call_phone = (LinearLayout) v.findViewById(R.id.view_call_phone);
		view_keyboard = (LinearLayout) v.findViewById(R.id.view_keyboard);
		image_head = (CircleImageView) v
				.findViewById(R.id.image_head_imageView);
		txt_phone_number = (TextView) v.findViewById(R.id.txt_phone_number);
		txt_number_and_countdown = (TextView) v
				.findViewById(R.id.txt_number_and_countdown);
		imageBut_sms = (ImageButton) v.findViewById(R.id.imageBut_sms);
		imageBut_answer = (ImageButton) v.findViewById(R.id.imageBut_answer);
		imageBut_reject = (ImageButton) v.findViewById(R.id.imageBut_reject);
		imageBut_contents = (ImageButton) v
				.findViewById(R.id.imageBut_contents);
		imageBut_keys = (ImageButton) v.findViewById(R.id.imageBut_keys);
		imageBut_recording = (ImageButton) v
				.findViewById(R.id.imageBut_recording);
		imageBut_volume = (ImageButton) v.findViewById(R.id.imageBut_volume);
		imageBut_hangup = (ImageButton) v.findViewById(R.id.imageBut_hangup);
	}

	/**
	 * 添加监听器
	 */
	private static void widgetListener() {
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
		initGetData() ;
		onClick();
	}

	/**
	 * 获取显示参数
	 * 
	 * @return
	 */
	private static WindowManager.LayoutParams getShowingParams() {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		// TYPE_TOAST TYPE_SYSTEM_OVERLAY 在其他应用上层 在通知栏下层 位置不能动鸟
		// TYPE_PHONE 在其他应用上层 在通知栏下层
		// TYPE_PRIORITY_PHONE TYPE_SYSTEM_ALERT 在其他应用上层 在通知栏上层 没试出来区别是啥
		// TYPE_SYSTEM_ERROR 最顶层(通过对比360和天天动听歌词得出)
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.x = 0;
		params.y = 0;
		params.format = PixelFormat.RGBA_8888;// value = 1
		params.gravity = Gravity.TOP;
		params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
		params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

		return params;
	}

	/**
	 * 获取界面显示的高度 ，默认为手机高度的2/3
	 * 
	 * @param context
	 *            上下文对象
	 * @return
	 */
	private static int getHeight(Context context, int percentScreen) {
		return getLarger(context) * percentScreen / 100;
	}

	@SuppressWarnings("deprecation")
	private static int getLarger(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int height = 0;
		if (Utils.hasHoneycombMR2()) {
			height = getLarger(display);
		} else {
			height = display.getHeight() > display.getWidth() ? display
					.getHeight() : display.getWidth();
		}
		System.out.println("getLarger: " + height);
		return height;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private static int getLarger(Display display) {
		Point size = new Point();
		display.getSize(size);
		return size.y > size.x ? size.y : size.x;
	}
	private static void initGetData() {
		txt_number_and_countdown.setText("来电");
		phoneNumber = txt_phone_number.getText().toString();
		// 按键声音播放设置及初始化
		try {
			// 获取系统参数“按键操作音”是否开启
			mDTMFToneEnabled = Settings.System.getInt(
					mContext.getContentResolver(),
					Settings.System.DTMF_TONE_WHEN_DIALING, 1) == 1;
			synchronized (mToneGeneratorLock) {
				if (mDTMFToneEnabled && mToneGenerator == null) {
					mToneGenerator = new ToneGenerator(
							AudioManager.STREAM_DTMF, 80); // 设置声音的大小
					((Activity)mContext).setVolumeControlStream(AudioManager.STREAM_DTMF);
				}
			}
		} catch (Exception e) {
			mDTMFToneEnabled = false;
			mToneGenerator = null;
		}
	}

	@SuppressLint("HandlerLeak")
	static
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CallingActivity.NUM_ONE:
				txt_number_and_countdown.setText(getStringTime(mCountdown++));
				break;
			case CallingActivity.TELE_START_TIME:
				ToastUtil.showToast(mContext, "开始录音啦！");
				break;
			case CallingActivity.TELE_END_TIME:
				ToastUtil.showToast(mContext, "录音结束！文件保存路径为：" + ""
						+ root_directory);
				break;
			case CallingActivity.TELE_FAILURE_TIME:
				ToastUtil.showToast(mContext, "录音失败！");
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

	static TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// 需要做的事:发送消息
			Message message = new Message();
			message.what = CallingActivity.NUM_ONE;
			myHandler.sendMessage(message);
		}
	};

	// 取消任务计时
	public static void stopClick() {
		timer.cancel();
	}

	// 格式化倒计时
	private static String getStringTime(int cnt) {
		int hour = cnt / 3600;
		int min = cnt % 3600 / 60;
		int second = cnt % 60;
		return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
	}
	private static void onClick() {
		imageBut_volume.setOnClickListener(new OnClickListener() { // 扩音
					@Override
					public void onClick(View v) {
						if (mSpeekModle) {
							StringUtil.setSpeekModle(mContext, mSpeekModle);
							imageBut_volume
									.setBackground(mContext
											.getResources()
											.getDrawable(
													R.drawable.tableid_btn_volume_true));
							mSpeekModle = false;
						} else {
							StringUtil.setSpeekModle(mContext, mSpeekModle);
							imageBut_volume.setBackground(mContext
									.getResources().getDrawable(
											R.drawable.tableid_btn_volume));
							mSpeekModle = true;
						}

					}
				});
		imageBut_contents.setOnClickListener(new OnClickListener() { // 联系人

					@Override
					public void onClick(View v) {
						IntentUtil.gotoActivity(mContext,
								CallingContactActivity.class);
					}
				});

		imageBut_hangup.setOnClickListener(new OnClickListener() { // 挂断

					@Override
					public void onClick(View v) {
						if (recorder != null) {
							recorder.stop();
							recorder.release();
							recorder = null;
							Message msg_start = new Message();
							msg_start.what = TELE_END_TIME;
							myHandler.sendMessage(msg_start);
						}
						stopClick();
					}
				});

		imageBut_keys.setOnClickListener(new OnClickListener() {// 键盘弹出显示

					@Override
					public void onClick(View v) {
						if (mKeyBooard) {
							imageBut_keys.setBackground(mContext.getResources()
									.getDrawable(
											R.drawable.tableid_btn_keys_true));
							mKeyBooard = false;
							view_keyboard.setVisibility(View.VISIBLE);
							view_keyboard.setAnimation(StringUtil
									.moveToViewLocation());
						} else {
							imageBut_keys.setBackground(mContext.getResources()
									.getDrawable(R.drawable.tableid_btn_keys));
							mKeyBooard = true;
							view_keyboard.setVisibility(View.GONE);
							view_keyboard.setAnimation(StringUtil
									.moveToViewBottom());
						}

					}
				});

		imageBut_recording.setOnClickListener(new OnClickListener() { // 录音

					@Override
					public void onClick(View v) {
						if (mRecording) {
							recorDing(phoneNumber, mRecording);
							imageBut_recording
									.setBackground(mContext.getResources()
											.getDrawable(
													R.drawable.tableid_btn_recording_true));
							mRecording = false;
							lastExitTime = System.currentTimeMillis();
							ToastUtil.showToast(mContext, "开始录音");
						} else {
							// 判断2次点击事件时间
							if ((System.currentTimeMillis() - lastExitTime) < 3000) {
								ToastUtil.showToast(mContext,
										"录音三秒以上才可以取消");
								return;
							}
							recorDing(phoneNumber, mRecording);
							imageBut_recording.setBackground(mContext.getResources()
									.getDrawable(
											R.drawable.tableid_btn_recording));
							mRecording = true;
							ToastUtil.showToast(mContext, "结束录音");
						}
					}
				});

		imageBut_sms.setOnClickListener(new OnClickListener() {// 短信 回复

					@Override
					public void onClick(View v) {
						setNotifacation();
					}
				});

		imageBut_answer.setOnClickListener(new OnClickListener() {// 接听 来电

					@Override
					public void onClick(View v) {
						goAnswer();
					}
				});
		imageBut_reject.setOnClickListener(new OnClickListener() { // 拒接 来电

					@Override
					public void onClick(View v) {

					}
				});

		btn_one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_1);
				setEditText("1");

			}
		});

		btn_two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_2);
				setEditText("2");

			}
		});
		btn_three.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_3);
				setEditText("3");

			}
		});
		btn_four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_4);
				setEditText("4");

			}
		});
		btn_five.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_5);
				setEditText("5");
			}
		});

		btn_six.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_6);
				setEditText("6");
			}
		});

		btn_seven.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_7);
				setEditText("7");
			}
		});

		btn_eight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_8);
				setEditText("8");
			}
		});

		btn_nine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_9);
				setEditText("9");
			}
		});
		btn_star.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_S);
				setEditText("*");
			}
		});
		btn_zero.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_0);
				setEditText("0");
			}
		});

		btn_jing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playTone(ToneGenerator.TONE_DTMF_P);
				setEditText("#");
			}
		});
	}
	
	/**
	 * 播放按键声音
	 */
	private static void playTone(int tone) {
		if (!mDTMFToneEnabled) {
			return;
		}
		AudioManager audioManager = (AudioManager) mContext
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
	private static void setEditText(String num) {
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
	public static void recorDing(String phoneNumber, boolean mRecording) {
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
	private static void setNotifacation() {
		mRingPosition = 0 ;
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
            	ToastUtil.showToast(mContext, mNotifacations[mRingPosition-1]);
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
	private static void goAnswer() {
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
