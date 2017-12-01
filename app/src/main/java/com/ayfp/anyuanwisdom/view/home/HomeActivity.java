package com.ayfp.anyuanwisdom.view.home;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.view.contacts.ContactsActivity;
import com.ayfp.anyuanwisdom.view.live.LiveActivity;
import com.ayfp.anyuanwisdom.view.notice.NoticeListActivity;
import com.ayfp.anyuanwisdom.view.personal.MineActivity;
import com.ayfp.anyuanwisdom.view.report.ReportActivity;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:17.
 * @description:
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.layout_notice_left)
    View mNoticeLeft;
    @BindView(R.id.layout_notice_right)
    View mNoticeRight;
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
        requestBasicPermission();
    }
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
    protected IBasePresenter createPresenter() {
        return null;
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
}
