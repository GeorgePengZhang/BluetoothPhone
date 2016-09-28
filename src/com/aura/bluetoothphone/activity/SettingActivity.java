package com.aura.bluetoothphone.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.utils.ToastUtil;

/**
 * @Name：IOTControl
 * @author：Administrator Robin
 * @Description：
 * @date：2016-07-15 15:25
 * @Upauthor：Administrator #Update：2016-07-15 15:25
 * @tags：
 */
public class SettingActivity extends BaseActivity{

    /** 检测更新 */
    private View btn_CheckUpdate;
    /** 新消息提醒 */
//    private View relative_volume;
    /** 关于 */
    private TextView btn_about;
    /** 版本号 */
    private TextView txt_Version;

    /** 声音 */
    private CheckBox cbox_Volumn;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findViews() {
        txt_Version = (TextView) findViewById(R.id.setting_txt_version);
        btn_CheckUpdate = findViewById(R.id.setting_btn_checkupdate);
//        relative_volume = findViewById(R.id.setting_relative_volume);
//        relative_volume.setClickable(false);
        btn_about = (TextView) findViewById(R.id.setting_btn_about);

        cbox_Volumn = (CheckBox) findViewById(R.id.setting_cbox_volume);
    }

    @Override
    protected void widgetListener() {
        cbox_Volumn.setChecked(false);
    }

    @Override
    protected void init() {
        titleView.setTitle("设置");
        titleView.setBackBtn();
    }

    @Override
    protected void initGetData() {

        // checkbox改变事件，改变之后的ischeked值
        cbox_Volumn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToastUtil.showToast(context,"开");
                } else {
                    ToastUtil.showToast(context,"关");
                }

            }
        });

        //关于
        btn_about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.showToast(context,"跳转关于界面");
            }
        });


        btn_CheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(context,"版本升级中...");
            }
        });
    }


}
