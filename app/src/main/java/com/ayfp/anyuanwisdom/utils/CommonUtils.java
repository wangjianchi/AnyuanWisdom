package com.ayfp.anyuanwisdom.utils;

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

}
