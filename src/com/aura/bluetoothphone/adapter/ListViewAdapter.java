package com.aura.bluetoothphone.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aura.bluetoothphone.R;

/**
 * 录音文件 适配器
 * 
 * @author Robin
 * @ClassName: ListViewAdapter 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月25日 下午3:44:59 
 *
 */
public class ListViewAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>> listitems;
	private LayoutInflater listContainerInflater;
	public ListViewAdapter(Context context,List<Map<String, Object>> listitems)
    {
   	 this.context=context;
   	 //创建视图容器并设置上下文
   	 listContainerInflater=LayoutInflater.from(context);
   	 this.listitems=listitems;
    }

	@Override
	public int getCount() {
		return listitems.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListTextView ltv=null;
		if(convertView==null){
			ltv=new ListTextView();
			convertView = LayoutInflater.from(context).inflate(R.layout.list_recorder_adapter, null);
			ltv.Name=(TextView) convertView.findViewById(R.id.textV);
			ltv.tx=(ImageView)convertView.findViewById(R.id.imageV);
			convertView.setTag(ltv);
		}else{
			ltv = (ListTextView)convertView.getTag();
		}
		ltv.Name.setText((CharSequence) listitems.get(position).get("text"));
		ltv.tx.setImageBitmap((Bitmap) listitems.get(position).get("images"));
		return convertView;
	}
	
	public final class ListTextView
    {
   	 public TextView Name;
   	 public ImageView tx;
   	 
    }
}
