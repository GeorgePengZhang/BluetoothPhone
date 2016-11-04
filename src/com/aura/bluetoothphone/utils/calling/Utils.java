
package com.aura.bluetoothphone.utils.calling;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

public class Utils {
    /**
     * 挂断电话action
     */
    public static final String ACTION_END_CALL = "com.likebamboo.phoneshow.ACTION_END_CALL";
    /**
     * 直接显示Toast
     * 
     * @param context 当前环境上下文对象
     * @param text 内容
     * @param isShort 是否短时间显示（false则为长时间显示）
     */
    public static void showToast(Context context, String text, boolean isShort) {
        if (isShort) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 直接显示Toast
     * 
     * @param context 当前环境上下文对象
     * @param text 内容
     * @param isShort 是否短时间显示（false则为长时间显示）
     */
    public static void showToast(Context context, int resId, boolean isShort) {
        if (isShort) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 直接显示Toast
     * 
     * @param context 当前环境上下文对象
     * @param resId 字符串资源id
     */
    public static void showToast(Context context, int resId) {
        showToast(context, resId, true);
    }

    /**
     * 直接显示Toast
     * 
     * @param context 当前环境上下文对象
     * @param text 内容
     */
    public static void showToast(Context context, String text) {
        showToast(context, text, true);
    }

    /**
     * 关闭输入法
     */
    public static void closeEditer(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager)context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断网络是否可用
     */
    public static boolean CheckNetworkConnection(Context context) {
        ConnectivityManager con = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = con.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            // 当前网络不可用
            return false;
        }
        return true;
    }

    /**
     * 判断wifi网络是否可用
     */
    public static boolean IsWifiEnable(Context context) {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    /**
     * Get the external app cache directory.
     * 
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalFileDir(Context context) {
        if (hasFroyo()) {
            final File file = context.getExternalFilesDir(null);
            return file;
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String fileDir = "/Android/data/" + context.getPackageName() + "/files";
        return new File(Environment.getExternalStorageDirectory().getPath() + fileDir);
    }

    public static File getCacheDir(Context context) {
        File cacheDir = null;
        try {
            cacheDir = getExternalFileDir(context);
        } catch (Exception e) {
            e.printStackTrace();
            cacheDir = context.getFilesDir();
        }
        return cacheDir;
    }

    /**
     * >= android 2.2 版本
     * 
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * >= android 2.3 版本
     * 
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * >= android 3.0 版本
     * 
     * @return
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * 收起输入法
     * 
     * @param ctx
     * @param view
     */
    public static void HideKeyboard(Context ctx, View view) {
        if (null == view)
            return;
        InputMethodManager imm = (InputMethodManager)ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 字符串转Html
     * 
     * @param text 字符串
     * @return 格式化的html。
     */
    public static Spanned formatHtml(String text) {
        return Html.fromHtml(text);
    }


    /**
     * 发送挂断电话的广播
     * 
     * @param ctx 上下文对象
     */
    public static void sendEndCallBroadCast(Context ctx) {
        Intent i = new Intent();
        i.setAction(ACTION_END_CALL);
        ctx.sendBroadcast(i);
    }

    /**
     * 挂断电话
     */
    public static synchronized void endCall(Context ctx) {
        TelephonyManager mTelMgr = (TelephonyManager)ctx
                .getSystemService(Service.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        try {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[])null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            System.out.println("End call.");
            iTelephony = (ITelephony)getITelephonyMethod.invoke(mTelMgr, (Object[])null);
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fail to answer ring call.");
        }
    }

    /**
     * 接听电话
     */
    public static synchronized void answerCall(Context ctx) {
    	try {
            Runtime.getRuntime().exec("input keyevent " +
                    Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
        } catch (IOException e) {
            // Runtime.exec(String) had an I/O problem, try to fall back
            String enforcedPerm = "android.permission.CALL_PRIVILEGED";
            Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                    Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_HEADSETHOOK));
            Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                    Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_HEADSETHOOK));
 
            ctx.sendOrderedBroadcast(btnDown, enforcedPerm);
            ctx.sendOrderedBroadcast(btnUp, enforcedPerm);
        }
    }
}
