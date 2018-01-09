package com.ayfp.anyuanwisdom.config;

import android.content.Context;
import android.content.Intent;

import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.view.contacts.view.NotificationAcitvity;
import com.ayfp.anyuanwisdom.view.home.HomeActivity;
import com.ayfp.anyuanwisdom.view.login.LoginActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;

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
        AppCache.getInstance().setUserBean(null);
        mHomeActivity.observeOnlineStatus(false);
        mHomeActivity.observeCustomNotification(false);
        Intent intent = new Intent(context,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        initNotificationConfig(false);
    }

    public static void initNotificationConfig(boolean enable) {
        // 初始化消息提醒
        NIMClient.toggleNotification(enable);

        // 加载状态栏配置
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.ring = enable;
        config.vibrate = enable;
        config.titleOnlyShowAppName = true;
        config.notificationEntrance = NotificationAcitvity.class;
        config.notificationFolded = false;
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(config);
    }
}
