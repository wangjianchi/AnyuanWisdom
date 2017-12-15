package com.ayfp.anyuanwisdom.view.home;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.config.AppConfig;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.utils.UIUtils;
import com.ayfp.anyuanwisdom.view.contacts.view.ContactsActivity;
import com.ayfp.anyuanwisdom.view.home.adapter.GalleryAdapter;
import com.ayfp.anyuanwisdom.view.home.adapter.InfinitePagerAdapter;
import com.ayfp.anyuanwisdom.view.home.adapter.InfiniteViewPager;
import com.ayfp.anyuanwisdom.view.home.adapter.ScalePageTransformer;
import com.ayfp.anyuanwisdom.view.live.LiveActivity;
import com.ayfp.anyuanwisdom.view.notice.NoticeDetailActivity;
import com.ayfp.anyuanwisdom.view.notice.NoticeListActivity;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.ayfp.anyuanwisdom.view.personal.MineActivity;
import com.ayfp.anyuanwisdom.view.report.ReportActivity;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:17.
 * @description:
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements IHomeView {
    @BindView(R.id.vp_notice)
    InfiniteViewPager mViewPager;
    @BindView(R.id.layout_container)
    View mContainer;
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        AppConfig.mHomeActivity = this;
        requestBasicPermission();
        mPresenter.getData();
        observeOnlineStatus(true);
    }
    /**
     * 监听在线状态
     * @param register
     */
    public void observeOnlineStatus(boolean register){
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver,register);
    }
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code == StatusCode.UNLOGIN ){
                LoginInfo info = new LoginInfo(Preferences.getUserAccount(),Preferences.getUserToken());
                NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallbackWrapper() {
                    @Override
                    public void onResult(int i, Object o, Throwable throwable) {

                    }
                });
            }else  if (code == StatusCode.KICKOUT || code == StatusCode.KICK_BY_OTHER_CLIENT){
                ToastUtils.showToast("您的帐号正在其他设备登录，请重新登录");
                AppConfig.logOut(HomeActivity.this);
            }
        }
    };



    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(HomeActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @OnClick(R.id.iv_personal) void mine(){
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_contacts) void contacts(){
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_live) void live(){
        Intent intent = new Intent(this, LiveActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_notice) void notice(){
        Intent intent = new Intent(this, NoticeListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_report) void report(){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    @Override
    public void getNoticeList(final List<NoticeListBean> listBean) {
        mContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });
        GalleryAdapter galleryAdapter = new GalleryAdapter(listBean,this);
        galleryAdapter.setListener(new GalleryAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(HomeActivity.this,NoticeDetailActivity.class);
                intent.putExtra("id",listBean.get(position).getId());
                startActivity(intent);
            }
        });
        PagerAdapter adapter = new InfinitePagerAdapter(galleryAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(UIUtils.dip2px(-15));
        mViewPager.setPageTransformer(true,new ScalePageTransformer());
        mViewPager.setAdapter(adapter);
    }
}
