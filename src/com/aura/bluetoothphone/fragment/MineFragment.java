package com.aura.bluetoothphone.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.activity.MyPrivateActivity;
import com.aura.bluetoothphone.utils.DialogUtil;
import com.aura.bluetoothphone.utils.IntentUtil;
import com.aura.bluetoothphone.utils.PhotoUtil;
import com.aura.bluetoothphone.utils.SpUtil;
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
    
    
    private Bitmap imageBitmap;
    private File mCurrentPhotoFile;
    /** Standard activity result: operation succeeded. */
    public static final int RESULT_OK           = -1;
    private static final int PHOTO_PICKED_WITH_DATA = 1881;
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    private static final int PHOTO_CROP_RESOULT = 1884;
    private static final int ICON_SIZE = 96;
    
    
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

//        titleView.setRightImgBtn(R.drawable.icon_setting_normal,new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                IntentUtil.gotoActivity(context,SettingActivity.class);
//            }
//        });
        imageBitmap = SpUtil.loadDrawable(getActivity()) ;
        if (null != imageBitmap) {
        	head_imageView.setImageBitmap(imageBitmap); 
		}
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
            case R.id.xqb_head_imageView: //	头像 切换 
            	showSelectImageDg(getActivity());
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
	public void showSelectImageDg(final Context context) {

		String[] items = context.getResources().getStringArray(R.array.select_image);

		CustomDialog dialog = new CustomDialog(context);
		dialog.setTitle(context.getString(R.string.dialog_select_image_title));
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		dialog.setSingleSelectItems(items, new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				switch (id) {
				case 0:
					// 拍照获取
	                doTakePhoto();
					break;
				case 1:
					// 相册获取
	                doPickPhotoFromGallery();
					break;

				default:
					break;
				}
				dialog.dismiss();
			}
		});

		dialog.show();

	}
	
	 /**
     * 调用系统相机拍照
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Photo");
            if (!file.exists()) {
                file.mkdirs();
            }
            mCurrentPhotoFile = new File(file, PhotoUtil.getRandomFileName());
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
        	ToastUtil.showToast(getActivity(), getActivity().getString(R.string.photoPickerNotFoundText));
        }
    }

    /**
     * Constructs an intent for capturing a photo and storing it in a temporary
     * file.
     */
    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 相机剪切图片
     */
    protected void doCropPhoto(File f) {
        try {
            // Add the image to the media store
            MediaScannerConnection.scanFile(getActivity(), new String[]{
                    f.getAbsolutePath()
            }, new String[]{
                    null
            }, null);

            // Launch gallery to crop the photo
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, CAMERA_CROP_RESULT);
        } catch (Exception e) {
        	ToastUtil.showToast(getActivity(), getActivity().getString(R.string.photoPickerNotFoundText));
        }
    }

    /**
     * 获取系统剪裁图片的Intent.
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * 从相册选择图片
     */
    protected void doPickPhotoFromGallery() {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToast(getActivity(), getActivity().getString(R.string.photoPickerNotFoundText));
        }
    }

    /**
     * 获取调用相册的Intent
     */
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * 相册裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CROP_RESOULT);
    }

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            switch (requestCode) {
                case PHOTO_PICKED_WITH_DATA:
                    // 相册选择图片后裁剪图片
                    startPhotoZoom(data.getData());
                    break;
                case PHOTO_CROP_RESOULT:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        imageBitmap = extras.getParcelable("data");
                        //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        head_imageView.setImageBitmap(imageBitmap);
                        
                        SpUtil.saveDrawable(getActivity(), imageBitmap);
                        
                    }
                    break;
                case CAMERA_WITH_DATA:
                    // 相机拍照后裁剪图片
                    doCropPhoto(mCurrentPhotoFile);
                    break;
                case CAMERA_CROP_RESULT:
                    imageBitmap = data.getParcelableExtra("data");
                    // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    head_imageView.setImageBitmap(imageBitmap);
                    SpUtil.saveDrawable(getActivity(), imageBitmap);
                    break;
            }
        }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
