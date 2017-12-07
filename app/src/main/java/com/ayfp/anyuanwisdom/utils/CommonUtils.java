package com.ayfp.anyuanwisdom.utils;

import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
}
