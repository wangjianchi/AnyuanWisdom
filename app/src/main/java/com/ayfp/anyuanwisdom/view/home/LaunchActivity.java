package com.ayfp.anyuanwisdom.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.view.login.LoginActivity;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:18.
 * @description:
 */

public class LaunchActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Preferences.checkUserLogin()){
                    startActivity(new Intent(LaunchActivity.this,HomeActivity.class));
                }else {
                    startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                }
                finish();
            }
        },500);
    }
}
