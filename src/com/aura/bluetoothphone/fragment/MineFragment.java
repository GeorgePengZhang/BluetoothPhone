package com.aura.bluetoothphone.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.activity.MyPrivateActivity;
import com.aura.bluetoothphone.activity.SettingActivity;
import com.aura.bluetoothphone.utils.DialogUtil;
import com.aura.bluetoothphone.utils.IntentUtil;
import com.aura.bluetoothphone.utils.ToastUtil;
import com.aura.bluetoothphone.view.CircleImageView;
import com.aura.bluetoothphone.widget.CustomDialog;


/**
 * @Name：我的
 * @author：Administrator Robin
 * @Description：
 * @date：2016-07-14 15:36
 * @Upauthor：Administrator #Update：2016-07-14 15:36
 * @tags：
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{

    /** 头像 */
    private CircleImageView head_imageView;

    /** 设备管理 */
    private RelativeLayout blue_device;
    /** 帮助 */
    private RelativeLayout blue_about;
    /** 关于 */
    private RelativeLayout blue_help;
    /** 退出 */
    private RelativeLayout rat_freeze_exit;
    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_mine, null);
    }

    @Override
    protected void findViews() {
        head_imageView  = (CircleImageView) findViewById(R.id.xqb_head_imageView);

        blue_device 	= (RelativeLayout) findViewById(R.id.blue_device);
        blue_help 		= (RelativeLayout) findViewById(R.id.bule_help);
        blue_about 		= (RelativeLayout) findViewById(R.id.blue_about);
        rat_freeze_exit = (RelativeLayout) findViewById(R.id.exit_layout);
    }

    @Override
    protected void widgetListener() {
        titleView.setTitle("Personal homepage");

        titleView.setRightImgBtn(R.drawable.icon_setting_normal,new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                IntentUtil.gotoActivity(context,SettingActivity.class);
            }
        });
        head_imageView.setOnClickListener(this);
        blue_device.setOnClickListener(this);
        blue_help.setOnClickListener(this);
        blue_about.setOnClickListener(this);
        rat_freeze_exit.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void initGetData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.xqb_head_imageView: // 个人详情
//                IntentUtil.gotoActivity(context, PersonalActivity.class);
                break;
            case R.id.blue_device: // 我的设备
                IntentUtil.gotoActivity(context, MyPrivateActivity.class);
//            	IntentUtil.gotoActivity(context, BuleAcyiivty.class);
                break;
            case R.id.bule_help: // 帮助
            	ToastUtil.showToast(getActivity(), "dsadsa");
                break;
            case R.id.blue_about: // g关于
            	ToastUtil.showToast(getActivity(), "dsadsa");
              break;
            case R.id.exit_layout: // 退出
                DialogUtil.showMessageDg(getActivity(), getString(R.string.prompt),
                        getString(R.string.warning_exits_system), new CustomDialog.OnClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                getActivity().finish();
                            }
                        });

                break;
        }
    }
}
