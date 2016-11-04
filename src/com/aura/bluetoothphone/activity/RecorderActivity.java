package com.aura.bluetoothphone.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;
import com.aura.bluetoothphone.adapter.ListViewAdapter;
import com.aura.bluetoothphone.utils.DialogUtil;
import com.aura.bluetoothphone.utils.ToastUtil;
import com.aura.bluetoothphone.widget.CustomDialog;

/**
 * 录音文件Activity
 * 
 * @author Robin
 * @ClassName: RecorderActivity 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月25日 下午3:04:00 
 *
 */
@SuppressWarnings("unused")
public class RecorderActivity extends BaseActivity {

	private TextView txt_type ;
	private ListView listView ;
	private ListViewAdapter adapter;
	private String t[];
	private File f;
	private List<Map<String, Object>> list;
	private String path ;
	
	@Override
	protected int getContentViewId() {
		return R.layout.activity_recorder;
	}

	@Override
	protected void findViews() {
		txt_type = (TextView)findViewById(R.id.txt_type) ;
		listView = (ListView)findViewById(R.id.list_recorder) ;
		
	}

	@Override
	protected void init() {
		titleView.setTitle("Recording file");
		titleView.setBackBtn();
		adapter = new ListViewAdapter(this, getData());
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	@Override
	protected void initGetData() {
		titleView.setRightBtn("清空", new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearTemp() ;
			}
		});
	}

	@Override
	protected void widgetListener() {
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				final int nn = arg2;
				if (f.list() != null) {
					String sss[] = f.list();
					if (sss.length != 0) {
						final String s = t[arg2];
						AlertDialog builder = new AlertDialog.Builder(RecorderActivity.this)
								.setTitle("文件操作")
								.setMessage(t[arg2])
								.setPositiveButton("删除",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int i) {
												// TODO Auto-generated method
												// stub
												// 删除文件
												File ff = new File(path + "/"
														+ s);
												ff.delete();
												// 删除listview里的一个元素
												list.remove(nn);
												adapter.notifyDataSetChanged();
												if (list.size()==0) {
													 list.clear();
													 txt_type.setVisibility(View.VISIBLE);
												}else{
													 txt_type.setVisibility(View.GONE);
												}
											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
											}
										}).create();
						builder.show();// 显示对话框
					}

				}
				adapter.notifyDataSetChanged();
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (f.list() != null) {
					String ss[] = f.list();
					if (ss.length != 0) {
						/**
						 * 将通用android.content.Intent.ACTION_VIEW意图的数据设置为一个音频文件的URI，
						 * 并制定其MIME类型，这样Android就能挑选适当的应用程序进行播放。
						 */
//						Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//						File sdcard = Environment.getExternalStorageDirectory();
						File audioFile = new File(Environment.getExternalStorageDirectory()
								+ "/Aura_recorded_call/" + t[arg2]);	
//						intent.setDataAndType(Uri.fromFile(audioFile), "audio/mp3");
//						startActivity(intent);
						MediaPlayer mPlayer = new MediaPlayer();  
						//设置要播放的文件  
						try {    
				             //设置要播放的文件  
				             mPlayer.setDataSource(audioFile.getPath());  
				             mPlayer.prepare();  
				             //播放  
				             mPlayer.start();         
				         }catch(Exception e){  
				             Log.e("TAG", "prepare() failed");    
				         }  
					}

				} else {
				}

			}
		});
	}
	/**
	 * 获取录音文件数据
	 * @author Robin
	 * @Title: getData 
	 * @Description: TODO
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws 
	 * @date 2016年10月25日 下午4:42:07
	 */
	private List<Map<String, Object>> getData() {
		list = new ArrayList<Map<String, Object>>();
		Bitmap bf = BitmapFactory.decodeResource(getResources(), R.drawable.bf);
		path = Environment.getExternalStorageDirectory()
				+ "/Aura_recorded_call";
		
		f = new File(path);
		if (f.list() == null) {
			txt_type.setVisibility(View.VISIBLE);
		} else {
			t = f.list();
			if (t.length == 0) {
				txt_type.setVisibility(View.VISIBLE);
			} else {
				txt_type.setVisibility(View.GONE);
				for (int i = 0; i < t.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("text", t[i]);
					map.put("images", bf);
					list.add(map);
				}
			}
		}
		return list;
	}
	
	//清空文件夹
    public void clearTemp(){
    	DialogUtil.showMessageDg(RecorderActivity.this, getString(R.string.prompt),
                "确定删除所有录音文件吗？", new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                    	File dir = new File(Environment.getExternalStorageDirectory()
                				+ "/Aura_recorded_call");//清空文件夹
                        String[] files = dir.list() ;
                        if(null != files){
                            for (int i = 0; i < files.length; i++) {
                                String s = files[i];
                             // 删除文件
                				File ff = new File(path + "/"
                						+ s);
                				ff.delete();
                            }
                            list.clear();
                            ToastUtil.showToast(context, "已清除");
                            txt_type.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
    }

}
