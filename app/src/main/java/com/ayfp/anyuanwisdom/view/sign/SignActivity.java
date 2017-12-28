package com.ayfp.anyuanwisdom.view.sign;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.service.LocationService;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.utils.PermissionCheckUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.ImageBrowserActivity;
import com.ayfp.anyuanwisdom.view.sign.adapter.SignImageAdapter;
import com.ayfp.anyuanwisdom.view.sign.iview.ISignView;
import com.ayfp.anyuanwisdom.view.sign.presenter.SignPresenter;
import com.ayfp.anyuanwisdom.view.sign.view.SignAddressPopupWindow;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author:: wangjianchi
 * @time: 2017/12/25  9:29.
 * @description:
 */

public class SignActivity extends BaseActivity<SignPresenter> implements ISignView {
    private static final int GPS_REQUEST_CODE = 102;
    private static int PIG_NUMBER = 5;
    private static int REQUEST_CAMERA_SIGN_IN = 103;
    private static int REQUEST_CAMERA_SIGN_OUT = 104;
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
    @BindView(R.id.layout_sign_out)
    View mLayoutSignOut;
    @BindView(R.id.et_sign_content)
    EditText mEditContent;
    @BindView(R.id.tv_sign_description)
    TextView mTextSignDes;
    @BindView(R.id.iv_commit)
    ImageView mImagmeCommit;
    @BindView(R.id.layout_root)
    View mRootView;
    @BindView(R.id.iv_map_image)
    ImageView mImageMap;
    private AMap mAMap;
    private SignImageAdapter mSignInAdpater;
    private SignImageAdapter mSignOutAdpater;
    private String picPath;

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
        mPresenter.getData();

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
        mTextSignInTime.setText("签到时间：" + mPresenter.getSignStatusBean().getSign_in_time());
        mTextSignInAddress.setText("签到位置：" + mPresenter.getSignStatusBean().getSign_in_address());
        mTextSignSelect.setVisibility(View.GONE);
        mSignInAdpater = new SignImageAdapter(mPresenter.getSignStatusBean().getSign_in_imgs());
        mSignRecyclerView.setLayoutManager(new GridLayoutManager(this, PIG_NUMBER));
        mSignRecyclerView.setAdapter(mSignInAdpater);
        mEditContent.setText(mPresenter.getSignStatusBean().getContent());
        mSignInAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageBrowserActivity.start(SignActivity.this,mPresenter.getSignStatusBean().getSign_in_imgs().get(position));
            }
        });
    }

    @Override
    public void showSignOutView() {
        mTextSignOutTime.setText("退签时间：" + mPresenter.getSignStatusBean().getSign_in_time());
        mTextSignOutAddress.setText("退签位置：" + mPresenter.getSignStatusBean().getSign_in_address());
        mTextSignOutSelect.setVisibility(View.GONE);
        mSignOutAdpater = new SignImageAdapter(mPresenter.getSignStatusBean().getSign_in_imgs());
        mSignOutRecyclerView.setLayoutManager(new GridLayoutManager(this, PIG_NUMBER));
        mSignOutRecyclerView.setAdapter(mSignOutAdpater);
        GlideUtils.loadImageView(mPresenter.getSignStatusBean().getLocate_path_url(),mImageMap);
        mMapView.setVisibility(View.GONE);
        mImagmeCommit.setVisibility(View.GONE);
        mTextSignDes.setText("您今天已经完成签到");
        mSignOutAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageBrowserActivity.start(SignActivity.this,mPresenter.getSignStatusBean().getSign_out_imgs().get(position));
            }
        });
    }

    @Override
    public void showNotSignView() {
        mLayoutSignOut.setVisibility(View.GONE);
        mSignInAdpater = new SignImageAdapter(mPresenter.getImageSignIn());
        mSignRecyclerView.setLayoutManager(new GridLayoutManager(this, PIG_NUMBER));
        mSignRecyclerView.setAdapter(mSignInAdpater);
        mSignInAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mPresenter.getImageSignIn().get(position).equals(SignImageAdapter.TAKEPHOTO)) {
                    checkCameraPermission(REQUEST_CAMERA_SIGN_IN);
                }else {
                    ImageBrowserActivity.start(SignActivity.this,mPresenter.getImageSignIn().get(position));
                }
            }
        });
    }

    @Override
    public void showNotSignOutView() {
        mLayoutSignOut.setVisibility(View.VISIBLE);
        mSignOutAdpater = new SignImageAdapter(mPresenter.getImageSignOut());
        mSignOutRecyclerView.setLayoutManager(new GridLayoutManager(this, PIG_NUMBER));
        mSignOutRecyclerView.setAdapter(mSignOutAdpater);
        mSignOutAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mPresenter.getImageSignOut().get(position).equals(SignImageAdapter.TAKEPHOTO)) {
                    checkCameraPermission(REQUEST_CAMERA_SIGN_OUT);
                }else {
                    ImageBrowserActivity.start(SignActivity.this,mPresenter.getImageSignOut().get(position));
                }
            }
        });
        if (mPresenter.getSignStatusBean().getSign_status() == SignPresenter.SIGN_STATUS_IN){
            mImagmeCommit.setImageResource(R.mipmap.bg_sign_out);
            mTextSignDes.setText("退签后系统会自动结束行程");
        }
    }

    @Override
    public void startLocation() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    @Override
    public void stopLocation() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }

    @Override
    public void signInSuccess() {
        showProgress();
        mPresenter.getSignStatus();
    }

    @Override
    public void signOutSuccess() {
        showProgress();
        mPresenter.getSignStatus();
    }

    @Override
    public void openGps() {
        if (!CommonUtils.isOPen(this)) {
            if (CommonUtils.canToggleGPS(this)) {
                CommonUtils.openGPS(this);
            } else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, GPS_REQUEST_CODE);
                ToastUtils.showToast("打开GPS可以获取到精确定位");
            }
        }
    }

    private void checkCameraPermission(int requestCode) {
        if (PermissionCheckUtils.checkCameraPermissions(SignActivity.this, requestCode)) {
            openCamera(requestCode);
        }
    }

    private void openCamera(int requestCode) {
        picPath = CommonUtils.getImageFileName();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = FileProvider.getUriForFile(MyApplication.getContext(), "com.ayfp.fileprovider", new File(picPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //permission granted
            openCamera(requestCode);
        } else {
            //permission denied
            ToastUtils.showToast("请打开相机权限！");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_SIGN_IN || requestCode == REQUEST_CAMERA_SIGN_OUT) {
            compreeImage(picPath, requestCode);
        }
    }

    private void compreeImage(String url, final int requestCode) {
        final String outputPath = StorageUtil.getWritePath("", StorageType.TYPE_IMAGE);
        Luban.with(this)
                .load(url)
                .setTargetDir(outputPath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        if (requestCode == REQUEST_CAMERA_SIGN_IN) {
                            mPresenter.getImageSignIn().add(0, file.getAbsolutePath());
                            mSignInAdpater.notifyDataSetChanged();
                        } else if (requestCode == REQUEST_CAMERA_SIGN_OUT) {
                            mPresenter.getImageSignOut().add(0, file.getAbsolutePath());
                            mSignOutAdpater.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    @OnClick(R.id.tv_sign_in_select)
    void signAddressSelect() {
        SignAddressPopupWindow popupWindow = new SignAddressPopupWindow(this, mPresenter.getSignAddress(), new SignAddressPopupWindow.OnSignAddressSelect() {
            @Override
            public void signAddressSelect(String address) {
                mTextSignInAddress.setText("签到位置：" + address);
                mPresenter.setSignInAddress(address);
            }
        });
        popupWindow.showAtLocation(mRootView, Gravity.NO_GRAVITY,0,0);
    }

    @OnClick(R.id.tv_sign_out_select)
    void signOutAddressSelect() {
        SignAddressPopupWindow popupWindow = new SignAddressPopupWindow(this, mPresenter.getSignAddress(), new SignAddressPopupWindow.OnSignAddressSelect() {
            @Override
            public void signAddressSelect(String address) {
                mTextSignOutAddress.setText("退签位置：" + address);
                mPresenter.setSignOutAddress(address);
            }
        });
        popupWindow.showAtLocation(mRootView, Gravity.NO_GRAVITY,0,0);
    }

    @OnClick(R.id.iv_commit) void commit(){
        String content = mEditContent.getText().toString();
        if (TextUtils.isEmpty(content)){
            ToastUtils.showToast("请输入签到内容");
            return;
        }
        showProgress();
        if (mPresenter.getSignStatusBean().getSign_status() == SignPresenter.SIGN_STATUS_NONE){
            mPresenter.signIn(content);
        }else if (mPresenter.getSignStatusBean().getSign_status() == SignPresenter.SIGN_STATUS_IN){
            mPresenter.signOut(content);
        }
    }
}
