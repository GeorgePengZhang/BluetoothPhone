package com.aura.bluetoothphone.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aura.bluetoothphone.R;

/**
 * @Name：IOTControl
 * @author：Administrator Robin
 * @Description：
 * @date：2016-08-10 16:05
 * @Upauthor：Administrator #Update：2016-08-10 16:05
 * @tags：
 */
public class MessageAdapter extends BaseAdapter {

    ArrayList<String> arrayList ;
    Context context ;
    public MessageAdapter( ArrayList<String> arrayList , Context context){
         this.arrayList = arrayList ;
         this.context = context ;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_more, null);
            holder.txt_Name = (TextView) convertView.findViewById(R.id.txt_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_Name.setText(arrayList.get(i));


        return convertView;
    }

    private static class ViewHolder{
        /** 名称*/
        private TextView txt_Name;
    }
}
