package com.ayfp.anyuanwisdom.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/26  14:04.
 * @description:
 */

public class LocationService extends Service {
    private AMapLocationClient mLocationClient = new AMapLocationClient(MyApplication.getContext());
    private AMapLocationClientOption mLocationClientOption = new AMapLocationClientOption();
    @Override
    public void onCreate() {
        Log.i("LocationService", "onCreate: ");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocationService", "onStartCommand: ");
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置间隔为2分钟
        mLocationClientOption.setInterval(2*60*1000);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.i("LocationService", "onLocationChanged: "+ JSON.toJSONString(aMapLocation));
                if (aMapLocation != null){
                    updateLocation(aMapLocation.getLongitude()+","+aMapLocation.getLatitude());
                }
            }
        });
        mLocationClient.startLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateLocation(String location){
        RetrofitService.getApi().uploadLocation(RetrofitService.TOKEN, Preferences.getUserName(),location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AppResultData<Object>>() {
                    @Override
                    public void loadSuccess(AppResultData<Object> data) {

                    }
                });
    }
    @Override
    public void onDestroy() {
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
        super.onDestroy();
    }
}
