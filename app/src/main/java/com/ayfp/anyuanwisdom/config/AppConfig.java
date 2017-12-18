package com.ayfp.anyuanwisdom.config;

import android.content.Context;
import android.content.Intent;

import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.view.home.HomeActivity;
import com.ayfp.anyuanwisdom.view.login.LoginActivity;

/**
 * @author:: wangjianchi
 * @time: 2017/12/15  16:45.
 * @description:
 */

public class AppConfig {
    public static HomeActivity mHomeActivity;
    public static void logOut(Context context){
        Preferences.saveUserToken(null);
        Preferences.saveUserAccount(null);
        Preferences.saveUserName(null);
        Preferences.saveUserId(null);
        mHomeActivity.observeOnlineStatus(false);
        mHomeActivity.observeCustomNotification(false);
        Intent intent = new Intent(context,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
