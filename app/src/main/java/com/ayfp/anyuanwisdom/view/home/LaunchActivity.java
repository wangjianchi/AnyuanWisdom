package com.ayfp.anyuanwisdom.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ayfp.anyuanwisdom.R;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:18.
 * @description:
 */

public class LaunchActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this,HomeActivity.class));
                finish();
            }
        },2000);
    }
}