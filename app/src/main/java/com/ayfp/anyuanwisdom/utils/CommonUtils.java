package com.ayfp.anyuanwisdom.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by 建池 on 2017/12/3.
 */

public class CommonUtils {
    private static final String FILE_FORMAT = "yyyyMMdd";
    private static final DateFormat fileNameFormat = new SimpleDateFormat(FILE_FORMAT, Locale.getDefault());
    public static int StringToInt(String str){
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            return 0;
        }
    }

    public static double StringToDouble(String str){
        try {
            return Double.parseDouble(str);
        }catch (Exception e){
            return 0;
        }
    }

    public static String getImageFileName(){

        Calendar now = new GregorianCalendar();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String fileName = StorageUtil.getWritePath(simpleDate.format(now.getTime())+".jpg", StorageType.TYPE_IMAGE) ;

        return fileName;
    }

    public static String getShowTime(long time){
        String timeStr = "";
        if (CommonUtils.isToday(time)) {
            //判断如果是今天
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            timeStr = dateFormat.format(new Date(time));
        } else {
            if (CommonUtils.isYeterday(time)) {
                //判断如果是昨天
                timeStr = "昨天";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                timeStr = dateFormat.format(new Date(time));
            }
        }
        return timeStr;
    }
    /**
     * 判断是不是今天的方法
     *
     * @param l
     * @return
     */
    public static boolean isToday(long l) {
        return l >= getTodayStart();
    }

    /**
     * 获取今天的开始时间
     *
     * @return
     */
    public static long getTodayStart() {
        GregorianCalendar gc = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        gc.setTime(date);
        Date date2 = new Date(date.getTime() - gc.get(Calendar.HOUR_OF_DAY) * 60 * 60
                * 1000 - gc.get(Calendar.MINUTE) * 60 * 1000 - gc.get(Calendar.SECOND)
                * 1000);
        long time = date2.getTime();
        return time;
    }

    /**
     * 判断是不是前天的方法
     *
     * @param l
     * @return
     */
    public static boolean isYeterday(long l) {
        GregorianCalendar gc = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        gc.setTime(date);
        Date date2 = new Date(date.getTime() - gc.get(Calendar.HOUR_OF_DAY) * 60 * 60
                * 1000 - gc.get(Calendar.MINUTE) * 60 * 1000 - gc.get(Calendar.SECOND)
                * 1000 - 24 * 60 * 60 * 1000);
        return l > date2.getTime();
    }
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
    }

    /**
     * 判断是否可以打开GPS
     * @param context
     * @return
     */
    public static boolean canToggleGPS(Context context) {
        PackageManager pacman = context.getPackageManager();
        PackageInfo pacInfo = null;

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            return false; //package not found
        }

        if(pacInfo != null){
            for(ActivityInfo actInfo : pacInfo.receivers){
                //test if recevier is exported. if so, we can toggle GPS.
                if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported){
                    return true;
                }
            }
        }

        return false; //default
    }
}
