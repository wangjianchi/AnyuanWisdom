package com.ayfp.anyuanwisdom.view.sign;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.sign.adapter.SignImageAdapter;
import com.ayfp.anyuanwisdom.view.sign.iview.ISignView;
import com.ayfp.anyuanwisdom.view.sign.presenter.SignPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/12/25  9:29.
 * @description:
 */

public class SignActivity extends BaseActivity<SignPresenter> implements ISignView {
    private static final int GPS_REQUEST_CODE = 102;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.tv_time)
    TextView mTextTime;
    @BindView(R.id.tv_sign_in_time)
    TextView mTextSignInTime;
    @BindView(R.id.tv_sign_in_address)
    TextView mTextSignInAddress;
    @BindView(R.id.tv_sign_in_select)
    TextView mTextSignSelect;
    @BindView(R.id.rv_sign_in_photos)
    RecyclerView mSignRecyclerView;
    @BindView(R.id.tv_sign_out_time)
    TextView mTextSignOutTime;
    @BindView(R.id.tv_sign_out_address)
    TextView mTextSignOutAddress;
    @BindView(R.id.tv_sign_out_select)
    TextView mTextSignOutSelect;
    @BindView(R.id.rv_sign_out_photos)
    RecyclerView mSignOutRecyclerView;
    AMap mAMap;
    private SignImageAdapter mSignInAdpater;
    private SignImageAdapter mSignOutAdpater;
    @Override
    public void loadComplete() {
        dismissProgress();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initViews() {
        if (!CommonUtils.isOPen(this)) {
            if (CommonUtils.canToggleGPS(this)) {
                CommonUtils.openGPS(this);
            } else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, GPS_REQUEST_CODE);
                ToastUtils.showToast("打开GPS可以获取到精确定位");
            }
        }
        mTextTitle.setText("签到");
        showProgress();
        mPresenter.getSignStatus();
        getCurrentTime();
    }

    private void getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        mTextTime.setText(formatter.format(curDate));
    }

    @Override
    protected SignPresenter createPresenter() {
        return new SignPresenter(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void loadLocation(boolean sign, double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        if (sign) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_map_sign_in)));
        } else {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_map_sign_out)));
        }
        mAMap.addMarker(markerOptions);
        float zoomLevel = 15;
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, zoomLevel, 0, 0));
        mAMap.moveCamera(camera);
    }

    @Override
    public void showSignInView() {
        mTextSignInTime.setText("签到时间："+mPresenter.getSignStatusBean().getSign_in_time());
        mTextSignInAddress.setText("签到位置："+mPresenter.getSignStatusBean().getSign_in_address());
        mTextSignSelect.setVisibility(View.GONE);
        mSignInAdpater = new SignImageAdapter(mPresenter.getSignStatusBean().getSign_in_imgs());
        mSignRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mSignRecyclerView.setAdapter(mSignInAdpater);
    }

    @Override
    public void showSignOutView() {
        mTextSignOutTime.setText("退签时间："+mPresenter.getSignStatusBean().getSign_in_time());
        mTextSignOutAddress.setText("退签位置："+mPresenter.getSignStatusBean().getSign_in_address());
        mTextSignOutSelect.setVisibility(View.GONE);
        mSignOutAdpater = new SignImageAdapter(mPresenter.getSignStatusBean().getSign_in_imgs());
        mSignOutRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mSignOutRecyclerView.setAdapter(mSignOutAdpater);
    }

    @Override
    public void showNotSignView() {

    }
}
