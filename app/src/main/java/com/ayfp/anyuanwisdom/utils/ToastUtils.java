package com.ayfp.anyuanwisdom.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.ayfp.anyuanwisdom.base.MyApplication;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  16:31.
 * @description:
 */

public class ToastUtils {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };


    public static void showToast(Context mContext,String text){
        showToast(mContext,text,2000);
    }
    public static void showToast(String text){
        showToast(MyApplication.getContext(),text,2000);
    }

    public static void showToast(Context mContext, String text, int duration) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration);

        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }
}
