package com.ayfp.anyuanwisdom.view.report;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.utils.PermissionCheckUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.report.adapter.ReportImageAdapter;
import com.ayfp.anyuanwisdom.view.report.bean.ReportImageBean;
import com.ayfp.anyuanwisdom.view.report.iview.IReportView;
import com.ayfp.anyuanwisdom.view.report.presenter.ReportPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:23.
 * @description:
 */

public class ReportActivity extends BaseActivity<ReportPresenter> implements IReportView{
    private static int REQUEST_CAMERA = 2;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.tv_right)
    TextView mTextRight;
    @BindView(R.id.rv_report_pic)
    RecyclerView mRecyclerView;
    private ReportImageAdapter mReportImageAdapter;
    private List<ReportImageBean> mData = new ArrayList<>();
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("事件上报");
        mTextRight.setText("提交");
        mTextRight.setVisibility(View.VISIBLE);

        mData.add(new ReportImageBean());
        mReportImageAdapter = new ReportImageAdapter(mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setAdapter(mReportImageAdapter);
        mReportImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mData.get(position).getType() == 1){
                    checkPermission();
                }
            }
        });
    }

    private void checkPermission(){
        if (PermissionCheckUtils.checkCameraPermissions(this,1006)){
            showSelectImageDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1006){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
                showSelectImageDialog();
            } else {
                //permission denied
                ToastUtils.showToast("请打开权限！");
            }
        }
    }

    private void showSelectImageDialog(){
        final String[] aryShop = {"拍照", "从相册选择"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("图片上传")
                .setCancelable(true)
                .setItems(aryShop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       if (i == 0){
                           Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                           Uri photoUri = Uri.fromFile(new File(mFilePath));
//                           intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                           startActivityForResult(intent, REQUEST_CAMERA);
                       }else {
                           Intent intent = new Intent();
                           intent.setType("image/*");
                           intent.setAction(Intent.ACTION_GET_CONTENT);
                           startActivityForResult(intent, 2);
                       }
                    }
                }).show();

    }

    @Override
    protected ReportPresenter createPresenter() {
        return new ReportPresenter(this);
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
