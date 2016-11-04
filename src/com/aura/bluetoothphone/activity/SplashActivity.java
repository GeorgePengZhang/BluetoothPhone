package com.aura.bluetoothphone.activity;

import android.app.AlertDialog;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.utils.IntentUtil;


/**
 * 程序启动页面
 *
 * @author Robin
 * @Title:
 * @Description:
 * @param
 * @throws
 * @date 2016-07-12 16:20
 */
public class SplashActivity extends BaseActivity {

	ImageView splash_image;

	FrameLayout sple_id;
	TextView tv_note;
	TextView tv_note_error;
	private AlertDialog myDialog = null; 
	
	@Override
	protected int getContentViewId() {
		return R.layout.splash_screen_view;
	}
	
	@Override
	protected void findViews() {
		splash_image = (ImageView) findViewById(R.id.splash_image);
		sple_id = (FrameLayout) findViewById(R.id.sple_id);
		tv_note = (TextView) findViewById(R.id.tv_note);
		tv_note_error = (TextView) findViewById(R.id.tv_note_error);
		
	}

	@Override
	protected void initGetData() {

		AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
		anima.setDuration(2000);// 设置动画显示时间 
		sple_id.startAnimation(anima);
		anima.setAnimationListener(new AnimationImpl());
		
	}

	@Override
	protected void widgetListener() {
	}
	
	@Override
	protected void init() {

	}

	private class AnimationImpl implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
//			sple_id.setBackgroundResource(R.drawable.splash_screenh_log);
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			doLogin() ;

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

	}
	
	void doLogin() {
		IntentUtil.gotoActivity(SplashActivity.this, MainActivity.class);
		finish();
	}
	
}
