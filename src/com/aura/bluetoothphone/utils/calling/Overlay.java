
package com.aura.bluetoothphone.utils.calling;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 显示窗口抽象类
 * 
 * @author Robin
 * @ClassName: Overlay 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @date 2016年11月1日 下午2:16:42 
 *
 */
public abstract class Overlay {
    public static ViewGroup mOverlay;

    protected static final Object monitor = new Object();

    protected static ViewGroup init(Context context, int layout, WindowManager.LayoutParams params) {
        WindowManager wm = (WindowManager)context.getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        
        if (mOverlay != null) {
            try {
                wm.removeView(mOverlay);
                mOverlay = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup overlay = (ViewGroup)inflater.inflate(layout, null);
        mOverlay = overlay;
        wm.addView(overlay, params);
        return overlay;
    }
}
