package com.ayfp.anyuanwisdom.utils;

/**
 * Created by 建池 on 2017/12/3.
 */

public class CommonUtils {

    public static int StringToInt(String str){
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            return 0;
        }
    }
}
