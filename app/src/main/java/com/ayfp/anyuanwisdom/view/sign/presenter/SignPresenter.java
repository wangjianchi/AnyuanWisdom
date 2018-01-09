package com.ayfp.anyuanwisdom.view.sign.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
import com.ayfp.anyuanwisdom.utils.FileUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.sign.adapter.SignImageAdapter;
import com.ayfp.anyuanwisdom.view.sign.bean.SignAddress;
import com.ayfp.anyuanwisdom.view.sign.bean.SignStatusBean;
import com.ayfp.anyuanwisdom.view.sign.iview.ISignView;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> mImageSignIn = new ArrayList<>();
    private List<String> mImageSignOut = new ArrayList<>();
    private List<String> mSignAddress = new ArrayList<>();
    private String mSignInAddress,mSignOutAddress;
    public SignPresenter(ISignView view){
        this.mView = view;
    }
    @Override
    public void getData() {
        mImageSignIn.add(SignImageAdapter.TAKEPHOTO);
        mImageSignOut.add(SignImageAdapter.TAKEPHOTO);
    }

    @Override
    public void networkConnected() {

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
                                mView.showNotSignOutView();
                                try {
                                    String[] signInLocation = mSignStatusBean.getSign_in_locate().split(",");
                                    mView.loadLocation(true, CommonUtils.StringToDouble(signInLocation[1]),CommonUtils.StringToDouble(signInLocation[0]));
                                }catch (Exception e){

                                }
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
    public void getLocation(final boolean signIn){
        mView.openGps();
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
                .compose(mView.<AppResultData<SignAddress>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<SignAddress>>() {
                    @Override
                    public void loadSuccess(AppResultData<SignAddress> data) {
                            mView.loadComplete();
                            if (data.getStatus() == RetrofitService.SUCCESS){
                                mSignAddress = data.getResult().getAddress();
                            }
                    }
                });
    }


    public void signIn(String content){
        StringBuffer SignInImages = new StringBuffer();
        for (int i = 0 ; i < mImageSignIn.size(); i++){
            if (mImageSignIn.get(i)!= SignImageAdapter.TAKEPHOTO){
                Bitmap bitmap = BitmapFactory.decodeFile(mImageSignIn.get(i));
                String image = FileUtils.Bitmap2StrByBase64(bitmap);
                if (SignInImages.length() > 0){
                    SignInImages.append(";"+image);
                }else {
                    SignInImages.append(image);
                }
            }
        }
        RetrofitService.getApi().signIn(RetrofitService.TOKEN,mSignStatusBean.getSign_id(),
                content,mSignInAddress,mSignLongitude+","+mSignLatitude,SignInImages.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {
                    @Override
                    public void loadSuccess(AppResultData<Object> data) {
                        mView.loadComplete();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mView.signInSuccess();
                            if (data.getStatus() == RetrofitService.SUCCESS){
                                mView.startLocation();
                                ToastUtils.showToast("签到成功");
                            }
                        }
                    }
                });
    }

    public void signOut(String content){
        StringBuffer SignInImages = new StringBuffer();
        for (int i = 0 ; i < mImageSignOut.size(); i++){
            if (mImageSignOut.get(i)!= SignImageAdapter.TAKEPHOTO){
                Bitmap bitmap = BitmapFactory.decodeFile(mImageSignOut.get(i));
                String image = FileUtils.Bitmap2StrByBase64(bitmap);
                if (SignInImages.length() > 0){
                    SignInImages.append(";"+image);
                }else {
                    SignInImages.append(image);
                }
            }
        }
        RetrofitService.getApi().signOut(RetrofitService.TOKEN,mSignStatusBean.getSign_id(),
                content,mSignOutAddress,mSignOutLongitude+","+mSignOutLatitude,SignInImages.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {
                    @Override
                    public void loadSuccess(AppResultData<Object> data) {
                        mView.loadComplete();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mView.signOutSuccess();
                            if (data.getStatus() == RetrofitService.SUCCESS){
                                mView.stopLocation();
                                ToastUtils.showToast("退签成功");
                            }
                        }
                    }
                });
    }
    public SignStatusBean getSignStatusBean() {
        return mSignStatusBean;
    }

    public List<String> getImageSignIn() {
        return mImageSignIn;
    }

    public List<String> getImageSignOut() {
        return mImageSignOut;
    }

    public List<String> getSignAddress() {
        return mSignAddress;
    }

    public void setSignInAddress(String signInAddress) {
        mSignInAddress = signInAddress;
    }

    public void setSignOutAddress(String signOutAddress) {
        mSignOutAddress = signOutAddress;
    }
}
