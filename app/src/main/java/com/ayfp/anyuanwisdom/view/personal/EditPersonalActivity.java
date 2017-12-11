package com.ayfp.anyuanwisdom.view.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.bean.UserBean;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.FileUtils;
import com.ayfp.anyuanwisdom.utils.PermissionCheckUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.personal.adapter.PersonEditAdapter;
import com.ayfp.anyuanwisdom.view.personal.bean.PersonEditBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  9:44.
 * @description:
 */

public class EditPersonalActivity extends BaseActivity {
    private static int REQUEST_CAMERA = 2;
    private static int REQUEST_IMAGES= 3;
    private static int REQUEST_PHONE= 4;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private PersonEditAdapter mAdapter;
    private List<PersonEditBean> mData = new ArrayList<>();
    private String[] titles = {"头像","姓名","性别","组织","政治面貌","电话号码","出生日期","学历"};
    private String picPath;

    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_personal;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("个人资料");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
        mAdapter = new PersonEditAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0){
                    checkPermission();
                } else if (position == 5){
                    startActivityForResult(new Intent(EditPersonalActivity.this,EditPhoneActivity.class),REQUEST_PHONE);
                }
            }
        });
    }
    private void checkPermission() {
        if (PermissionCheckUtils.checkCameraPermissions(this, 1006)) {
            showSelectImageDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1006) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
                showSelectImageDialog();
            } else {
                //permission denied
                ToastUtils.showToast("请打开权限！");
            }
        }
    }


    private void showSelectImageDialog() {
        final String[] aryShop = {"拍照", "从相册选择"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("图片上传")
                .setCancelable(true)
                .setItems(aryShop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            picPath = CommonUtils.getImageFileName();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri photoUri = FileProvider.getUriForFile(MyApplication.getContext(), "com.ayfp.fileprovider", new File(picPath));
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, REQUEST_IMAGES);
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA){
            compreeImage(picPath);
        }else if (requestCode == REQUEST_IMAGES){
            if (data != null){
                Uri uri = data.getData();
                if (uri != null){
                    String path = FileUtils.convertUri(this,uri);
                    compreeImage(path);
                }
            }
        }else if (requestCode == REQUEST_PHONE){
            if (resultCode == RetrofitService.SUCCESS){
                UserBean userBean = AppCache.getInstance().getUserBean();
                mData.get(5).setName(userBean.getPhone_tel());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void compreeImage(String url){
        final String outputPath = StorageUtil.getWritePath("", StorageType.TYPE_IMAGE);
        Luban.with(this)
                .load(url)
                .setTargetDir(outputPath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.i("ReportActivity", "onStart: ");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.i("ReportActivity", "onSuccess: ");
                        editUserPortrait(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ReportActivity", "onError: "+e.getMessage());
                    }
                }).launch();
    }

    private void editUserPortrait(String  filepath){
        showProgress();
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        String portrait = FileUtils.Bitmap2StrByBase64(bitmap);
        RetrofitService.getApi().editUserPortrait(RetrofitService.TOKEN, Preferences.getUserName(),portrait)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<UserBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<UserBean>>() {

                    @Override
                    public void loadSuccess(AppResultData<UserBean> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            UserBean userBean = AppCache.getInstance().getUserBean();
                            userBean.setPortrait(data.getResult().getPortrait());
                            AppCache.getInstance().setUserBean(userBean);
                            mData.get(0).setImage_url(data.getResult().getPortrait());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    private void initData(){
        UserBean userBean = AppCache.getInstance().getUserBean();
        String[] content = {userBean.getPortrait(), userBean.getReal_name(), userBean.getGender(), userBean.getOrganization(),
                userBean.getPolitics_status(), userBean.getPhone_tel(), userBean.getBirthday(), userBean.getEducation()};
        for (int i = 0 ; i < content.length ;i ++){
            PersonEditBean bean = new PersonEditBean();
            bean.setTitle(titles[i]);
            bean.setName(content[i]);
            if (i == 0){
                bean.setImage_url(content[0]);
                bean.setImage(true);
            }
            if (i == 0 || i == 5){
                bean.setEdit(true);
            }
            mData.add(bean);
        }
    }
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }


}
