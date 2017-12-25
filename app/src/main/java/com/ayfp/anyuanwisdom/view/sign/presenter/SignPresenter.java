package com.ayfp.anyuanwisdom.view.sign.presenter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.view.sign.bean.SignStatusBean;
import com.ayfp.anyuanwisdom.view.sign.iview.ISignView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/25  14:28.
 * @description:
 */

public class SignPresenter implements IBasePresenter {
    public static final int SIGN_STATUS_NONE = 0;
    public static final int SIGN_STATUS_IN = 1;
    public static final int SIGN_STATUS_OUT = 2;
    private int mStatus;
    public double mSignLongitude,mSignLatitude,mSignOutLongitude,mSignOutLatitude;
    private ISignView mView;
    private AMapLocationClient mLocationClient = new AMapLocationClient(MyApplication.getContext());
    private AMapLocationClientOption mLocationClientOption = new AMapLocationClientOption();
    private SignStatusBean mSignStatusBean;
    public SignPresenter(ISignView view){
        this.mView = view;
    }
    @Override
    public void getData() {

    }

    @Override
    public void networkConnected() {

    }

    public void getLocation(final boolean signIn){
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClientOption.setOnceLocation(true);
        mLocationClientOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (signIn){
                    mSignLatitude = aMapLocation.getLatitude();
                    mSignLongitude = aMapLocation.getLongitude();
                }else {
                    mSignOutLatitude = aMapLocation.getLatitude();
                    mSignOutLongitude = aMapLocation.getLongitude();
                }
                mView.loadLocation(signIn,aMapLocation.getLatitude(),aMapLocation.getLongitude());
                getSignAddressByLocation(aMapLocation.getLongitude()+","+aMapLocation.getLatitude());
            }
        });
        mLocationClient.startLocation();
    }
    private void getSignAddressByLocation(String location){
        RetrofitService.getApi().getAddressByLocation(RetrofitService.TOKEN,location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {
                    @Override
                    public void loadSuccess(AppResultData<Object> data) {
                            mView.loadComplete();
                    }
                });
    }

    public void getSignStatus(){
        RetrofitService.getApi().getSignStatus(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<SignStatusBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<SignStatusBean>>() {
                    @Override
                    public void loadSuccess(AppResultData<SignStatusBean> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mSignStatusBean = data.getResult();
                            if (mSignStatusBean.getSign_status() == SIGN_STATUS_NONE){
                                getLocation(true);
                                mView.showNotSignView();
                            }else if (mSignStatusBean.getSign_status() == SIGN_STATUS_IN){
                                getLocation(false);
                                mView.showSignInView();
                            }else {
                                mView.loadComplete();
                                mView.showSignInView();
                                mView.showSignOutView();
                                try {
                                    String[] signInLocation = mSignStatusBean.getSign_in_locate().split(",");
                                    mView.loadLocation(true, CommonUtils.StringToDouble(signInLocation[1]),CommonUtils.StringToDouble(signInLocation[0]));
                                    String[] signOutLocation = mSignStatusBean.getSign_out_locate().split(",");
                                    mView.loadLocation(false, CommonUtils.StringToDouble(signOutLocation[1]),CommonUtils.StringToDouble(signOutLocation[0]));
                                }catch (Exception e){

                                }
                            }
                        }else {
                            mView.loadComplete();
                        }
                    }
                });
    }

    public SignStatusBean getSignStatusBean() {
        return mSignStatusBean;
    }
}
