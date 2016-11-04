package com.aura.bluetoothphone.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.utils.ToastUtil;

/**
 * 铃声管理类
 * 
 * @author Robin
 * @ClassName: SoundActivity 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月26日 下午2:37:25 
 *
 */
@SuppressWarnings("unused")
public class SoundActivity extends BaseActivity {
	
    /** 铃声设置 */
    private RelativeLayout btn_sound ;
    /** 铃声名字 */
    private TextView txt_sound_name ;

    /** 震动 开关 */
	private CheckBox cbox_volume ;
    
	@Override
	protected int getContentViewId() {
		return R.layout.activity_sound;
	}

	@Override
	protected void findViews() {
		btn_sound 			= (RelativeLayout)findViewById(R.id.setting_btn_sound) ;
		txt_sound_name		= (TextView) findViewById(R.id.setting_txt_sound_name) ;
		cbox_volume			= (CheckBox) findViewById(R.id.setting_cbox_volume) ;
		
	}

	@Override
	protected void init() {
		titleView.setTitle("Setting sound");
        titleView.setBackBtn();
	}

	@Override
	protected void initGetData() {

	}
	private Vibrator vibrator;  
	@Override
	protected void widgetListener() {
		// checkbox改变事件，改变之后的ischeked值
		cbox_volume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToastUtil.showToast(context,"开");
                    /* 
                     * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到 
                     * */  
                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);  
                    long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启   
                    vibrator.vibrate(pattern,2);           //重复两次上面的pattern 如果只想震动一次，index设为-1   
                } else {
                    ToastUtil.showToast(context,"关");
                    vibrator.cancel();
                }

            }
        });

        //铃声选择
		btn_sound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	String arr[] = getRing()  ;
            	setNotifacation(arr);
            }
        });
	}
	
	
	private int mRingPosition = 0;
	private void setNotifacation(final String []mNotifacations) {
		mRingPosition = 0 ;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(R.drawable.logo) ;
		builder.setTitle("铃声选择") ;
		builder.setSingleChoiceItems(mNotifacations, R.layout.msm_dialog_item, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mRingPosition = which;
				try {
					rm.stopPreviousRingtone();
//					rm = new RingtoneManager(SoundActivity.this);
//					rm.setType(RingtoneManager.TYPE_RINGTONE);
//					rm.getCursor();
					rm.getRingtone(mRingPosition).play();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
            public void onClick(DialogInterface dialog, int which) {
            	if (mRingPosition==0) {
					return;
				}
            	ToastUtil.showToast(SoundActivity.this, mNotifacations[mRingPosition]);
            }
	    });
		
	    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    	@Override
            public void onClick(DialogInterface dialog, int which) {
	    		rm.stopPreviousRingtone();
            }
	    });
		
		builder.show();
	}
	public List<String> ringList;
	public Cursor cursor;
	public RingtoneManager rm;
//	public String ringArrayp[];
	public String[] getRing() {
		/* 新建一个arraylist来接收从系统中获取的短信铃声数据 */
		ringList = new ArrayList<String>();
		/* 添加“跟随系统”选项 */
//		ringList.add("跟随系统");
		/* 获取RingtoneManager */
		rm = new RingtoneManager(SoundActivity.this);
		/* 指定获取类型为短信铃声 */
		rm.setType(RingtoneManager.TYPE_RINGTONE);
//		rm.setType(RingtoneManager.TYPE_RINGTONE);
		rm.getCursor();
		/* 创建游标 */
		cursor = rm.getCursor();
		/* 游标移动到第一位，如果有下一项，则添加到ringlist中 */
		if (cursor.moveToFirst()) {
			do { // 游标获取RingtoneManager的列inde x
				ringList.add(cursor
						.getString(RingtoneManager.TITLE_COLUMN_INDEX));
			} while (cursor.moveToNext());
		}
		final int size =ringList.size();
		String[] arr = (String[])ringList.toArray(new String[size]);
//		ringArrayp=(String[]) ringList.toArray() ;
//		for (int i = 0; i < ringList.size(); i++) {
//			ringArrayp[i] = ringList.get(i).toString() ;
//		}
//		System.out.println("-------"+arr.length);
		return arr;
	}


}
