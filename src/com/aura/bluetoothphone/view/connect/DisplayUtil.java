package com.aura.bluetoothphone.view.connect;

import android.content.Context;

/**
 * @ClassName: DisplayUtil
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:32:39 PM
 */
public class DisplayUtil {

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int)(dp*density + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (px/density + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;

        return (int) (sp*density + 0.5f);
    }

    public static int px2sp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;

        return (int) (px/density + 0.5f);
    }
}