package com.ayfp.anyuanwisdom.view.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.config.AppConfig;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.nim.avchat.AVChatProfile;
import com.ayfp.anyuanwisdom.nim.avchat.activity.AVChatActivity;
import com.ayfp.anyuanwisdom.nim.avchat.receiver.PhoneCallStateObserver;
import com.ayfp.anyuanwisdom.service.LocationService;
import com.ayfp.anyuanwisdom.utils.PermissionCheckUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.contacts.view.ContactsActivity;
import com.ayfp.anyuanwisdom.view.live.CallLiveActivity;
import com.ayfp.anyuanwisdom.view.live.LiveActivity;
import com.ayfp.anyuanwisdom.view.notice.NoticeListActivity;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.ayfp.anyuanwisdom.view.personal.MineActivity;
import com.ayfp.anyuanwisdom.view.report.ReportActivity;
import com.ayfp.anyuanwisdom.view.sign.SignActivity;
import com.netease.nim.uikit.common.util.log.LogUtil;
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
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.model.CustomNotification;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:17.
 * @description:
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements IHomeView {
    @BindView(R.id.iv_back)
    ImageView mImageLeft;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.iv_right)
    ImageView mImageRight;
//    @BindView(R.id.vp_notice)
//    InfiniteViewPager mViewPager;
//    @BindView(R.id.layout_container)
//    View mContainer;
    @BindView(R.id.iv_unread)
    ImageView mImageUnread;
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private final int LOCATION_PERMISSION_SERVICE_REQUEST_CODE = 101;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        mImageLeft.setVisibility(View.GONE);
        mTextTitle.setText("安远精准扶贫信息管理系统");
        mImageRight.setVisibility(View.VISIBLE);
        mImageRight.setImageResource(R.mipmap.icon_mine);
        AppConfig.mHomeActivity = this;
        requestBasicPermission();
        mPresenter.getData();
        observeOnlineStatus(true);
        observeCustomNotification(true);
        observerUnreadCount(true);
        registerAVChatIncomingCallObserver(true);
        AppConfig.initNotificationConfig(true);

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
    private void observerUnreadCount(boolean register){
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            // 更新未读数变化
            int totalImUnread = NIMClient.getService(MsgService.class).getTotalUnreadCount();
            updateUnread(totalImUnread);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        int totalImUnread = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        updateUnread(totalImUnread);
    }

    private void updateUnread(int totalImUnread){
        if (totalImUnread > 0){
            mImageUnread.setVisibility(View.VISIBLE);
        }else {
            mImageUnread.setVisibility(View.GONE);
        }
    }

    /**
     * 注册音视频来电观察者
     *
     * @param register
     */
    private void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall(new Observer<AVChatData>() {
            @Override
            public void onEvent(AVChatData data) {
                String extra = data.getExtra();
                Log.e("Extra", "Extra Message->" + extra);
                if (PhoneCallStateObserver.getInstance().getPhoneCallState() != PhoneCallStateObserver.PhoneCallStateEnum.IDLE
                        || AVChatProfile.getInstance().isAVChatting()
                        || AVChatManager.getInstance().getCurrentChatId() != 0) {
                    LogUtil.i(TAG, "reject incoming call data =" + data.toString() + " as local phone is not idle");
                    AVChatManager.getInstance().sendControlCommand(data.getChatId(), AVChatControlCommand.BUSY, null);
                    return;
                }
                // 有网络来电打开AVChatActivity
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatProfile.getInstance().launchActivity(data, AVChatActivity.FROM_BROADCASTRECEIVER);
            }
        }, register);
    }

    /**
     * 推送
     *
     * @param register
     */

    private boolean register = false;

    public void observeCustomNotification(boolean register) {
        if (this.register != register) {
            NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(pushObserver, register);
            this.register = register;
        }
    }

    Observer pushObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification customNotification) {
            Log.i("Notification", "customNotification: " + JSON.toJSONString(customNotification));
            if (customNotification.getContent().contains("live call")){
                startActivity(new Intent(HomeActivity.this,CallLiveActivity.class));
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
        if (requestCode == BASIC_PERMISSION_REQUEST_CODE){
            MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        }else if (requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
                sign();
            } else {
                //permission denied
                ToastUtils.showToast("请打开权限,否则无法使用签到！");
            }
        }else if (requestCode == LOCATION_PERMISSION_SERVICE_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
                startLocation();
            } else {
                //permission denied
                ToastUtils.showToast("请打开权限,否则无法正常完成签到！");
            }
        }
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

    @OnClick(R.id.iv_right) void mine(){
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_contacts) void contacts(){
        StatusCode code = NIMClient.getStatus();
        Log.i("HomeActivity", "contacts: "+code);
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
    @OnClick(R.id.iv_sign) void sign(){
        if (PermissionCheckUtils.checkLocationPermissions(this,LOCATION_PERMISSION_REQUEST_CODE)){
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void getNoticeList(final List<NoticeListBean> listBean) {
//        mContainer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return mViewPager.dispatchTouchEvent(motionEvent);
//            }
//        });
//        GalleryAdapter galleryAdapter = new GalleryAdapter(listBean,this);
//        galleryAdapter.setListener(new GalleryAdapter.OnClickListener() {
//            @Override
//            public void onClick(int position) {
//                Intent intent = new Intent(HomeActivity.this,NoticeDetailActivity.class);
//                intent.putExtra("id",listBean.get(position).getId());
//                startActivity(intent);
//            }
//        });
//        PagerAdapter adapter = new InfinitePagerAdapter(galleryAdapter);
//        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setPageMargin(UIUtils.dip2px(-15));
//        mViewPager.setPageTransformer(true,new ScalePageTransformer());
//        mViewPager.setAdapter(adapter);
    }

    @Override
    public void startLocation() {
        if (PermissionCheckUtils.checkLocationPermissions(this,LOCATION_PERMISSION_SERVICE_REQUEST_CODE)){
            ToastUtils.showToast("今天还有签到未完成，请完成后到签到页面退签");
            Intent intent = new Intent(this, LocationService.class);
            startService(intent);
        }
    }
}
