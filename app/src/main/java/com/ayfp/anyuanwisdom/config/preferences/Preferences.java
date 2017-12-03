package com.ayfp.anyuanwisdom.config.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ayfp.anyuanwisdom.base.MyApplication;

/**
 * Created by hzxuwen on 2015/4/13.
 */
public class Preferences {
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_USER_ID= "user_id";
    private static final String KEY_USER_NAME = "user_name";

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    public static void saveUserId(String userId){
        saveString(KEY_USER_ID,userId);
    }

    public static String getUserId(){
        return getString(KEY_USER_ID);
    }

    public static void saveUserName(String userName){
        saveString(KEY_USER_NAME,userName);
    }

    public static String getUserName(){
        return getString(KEY_USER_NAME);
    }

    public static boolean checkUserLogin(){
        if (TextUtils.isEmpty(getUserId())){
            return false;
        }else {
            return true;
        }
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences("ayfp", Context.MODE_PRIVATE);
    }
}
