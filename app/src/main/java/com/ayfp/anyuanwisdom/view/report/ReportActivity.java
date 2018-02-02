package com.ayfp.anyuanwisdom.view.report;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventConfig;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.FileUtils;
import com.ayfp.anyuanwisdom.utils.KeyboardUtils;
import com.ayfp.anyuanwisdom.utils.PermissionCheckUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.utils.UIUtils;
import com.ayfp.anyuanwisdom.view.ImageBrowserActivity;
import com.ayfp.anyuanwisdom.view.report.adapter.ReportImageAdapter;
import com.ayfp.anyuanwisdom.view.report.bean.EventBean;
import com.ayfp.anyuanwisdom.view.report.bean.ReportImageBean;
import com.ayfp.anyuanwisdom.view.report.iview.IReportView;
import com.ayfp.anyuanwisdom.view.report.presenter.ReportPresenter;
import com.ayfp.anyuanwisdom.view.report.view.EventCategoryPopupWindow;
import com.ayfp.anyuanwisdom.view.report.view.EventTownPopupWindow;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:23.
 * @description:
 */

public class ReportActivity extends BaseActivity<ReportPresenter> implements IReportView {
    public static final String EDIT_REPORT_DATA = "edit_report_bean";
    private static int REQUEST_CAMERA = 2;
    private static int REQUEST_IMAGES= 3;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.iv_right)
    ImageView mImageRight;
    @BindView(R.id.rv_report_pic)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_root)
    View mRootView;
    @BindView(R.id.et_title)
    EditText mEditTitle;
    @BindView(R.id.tv_category_name)
    TextView mTextCategoryName;
    @BindView(R.id.rg_event_degree)
    RadioGroup mRadioGroup;
    @BindView(R.id.rg_event_status)
    RadioGroup mRadioGroupStatus;
    @BindView(R.id.et_event_content)
    EditText mEditContent;
    @BindView(R.id.tv_address)
    TextView mTextAddress;
    @BindView(R.id.et_number)
    EditText mEditNumber;
    private ReportImageAdapter mReportImageAdapter;
    private List<ReportImageBean> mData = new ArrayList<>();
    private String picPath = "";
    private boolean mEdit = false;
    private EventBean mEventBean;

    @Override
    public void loadComplete() {
        dismissProgress();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("事件上报");
        mImageRight.setImageResource(R.mipmap.icon_report_list);
        mImageRight.setVisibility(View.VISIBLE);
        mPresenter.getData();
        mTextCategoryName.setText("事件分类："+mPresenter.getEventCategory().getCate_name());
        mData.add(new ReportImageBean());
        mReportImageAdapter = new ReportImageAdapter(mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mReportImageAdapter);
        mReportImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mData.get(position).getType() == 1) {
                    checkPermission();
                }else {
                    ImageBrowserActivity.start(ReportActivity.this,mData.get(position).getImageFile());
                }
            }
        });
        setRadioButton();
        setStatusRadioButton();
    }
    private void parseIntent(Intent intent){
        if (intent.hasExtra(EDIT_REPORT_DATA)){
            mEventBean = intent.getParcelableExtra(EDIT_REPORT_DATA);
            if (mEventBean != null){
                mEdit = true;
            }
        }
    }

    private void showEditData(){
        mEditTitle.setText(mEventBean.getTitle());
        mEditNumber.setText(mEventBean.getHouse_number());
        mEditContent.setText(mEventBean.getContent());
        mData.clear();
        for (ReportImageBean imageBean : mEventBean.getImageList()){
            imageBean.setDelete(true);
            mData.add(imageBean);
        }
        mData.add(new ReportImageBean());
        mReportImageAdapter.notifyDataSetChanged();
        mTextCategoryName.setText("事件分类："+mEventBean.getEvent_category());
        mTextAddress.setText("上报地点："+mEventBean.getVillage_name());
        for (int i = 0;i < mRadioGroup.getChildCount();i++){
            RadioButton rb = (RadioButton) mRadioGroup.getChildAt(i);
            if (mEdit && rb.getText().equals(mEventBean.getEvent_degree())){
                rb.setChecked(true);
            }
        }
        for (int i = 0;i < mRadioGroupStatus.getChildCount();i++){
            RadioButton rb = (RadioButton) mRadioGroupStatus.getChildAt(i);
            if (mEdit && rb.getText().equals(mEventBean.getEvent_status())){
                rb.setChecked(true);
            }
        }
        mPresenter.setVillageId(CommonUtils.StringToInt(mEventBean.getVillage_id()));
        mPresenter.setTownId(CommonUtils.StringToInt(mEventBean.getTown_id()));
        mPresenter.setCategoryId(mEventBean.getEvent_category_id());
        mPresenter.setDegreeId(mEventBean.getEvent_degree_id());
        mPresenter.setStatusId(mEventBean.getEvent_status_id());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        parseIntent(intent);
        showEditData();
    }

    private void setRadioButton() {
        for (int i = 0; i < mPresenter.getDegreeList().size(); i++) {
            final EventDegree eventDegree = mPresenter.getDegreeList().get(i);
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(UIUtils.dip2px(20), 0, 0, 0);
            radioButton.setLayoutParams(lp);
            radioButton.setButtonDrawable(getResources().getDrawable(R.drawable.select_radio_event));
            String text = eventDegree.getDegree_name();
            radioButton.setText(text);
            radioButton.setTextSize(13);
            radioButton.setPadding(UIUtils.dip2px(7),0,0,0);
            if (text.equals("严重")) {
                radioButton.setTextColor(getResources().getColor(R.color.color_red));
            } else if (text.equals("较重")) {
                radioButton.setTextColor(getResources().getColor(R.color.color_orange));
            } else {
                radioButton.setTextColor(getResources().getColor(R.color.color_green));
            }
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.setDegreeId(eventDegree.getId());
                }
            });
            mRadioGroup.addView(radioButton);

        }
    }
    private void setStatusRadioButton() {
        for (int i = 0; i < mPresenter.getStatusList().size(); i++) {
            final EventConfig.EventStatusBean eventStatusBean = mPresenter.getStatusList().get(i);
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(UIUtils.dip2px(20), 0, 0, 0);
            radioButton.setLayoutParams(lp);
            radioButton.setButtonDrawable(getResources().getDrawable(R.drawable.select_radio_event));
            String text = eventStatusBean.getStatus();
            radioButton.setText(text);
            radioButton.setTextSize(13);
            radioButton.setPadding(UIUtils.dip2px(7),0,0,0);
            radioButton.setTextColor(getResources().getColor(R.color.text_color_222222));
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.setStatusId(eventStatusBean.getId());
                }
            });
            mRadioGroupStatus.addView(radioButton);
        }
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
                       ReportImageBean imageBean = new ReportImageBean();
                       imageBean.setType(2);
                       imageBean.setImageFile(file.getAbsolutePath());
                       imageBean.setDelete(true);
                       mData.add(0,imageBean);
//                       if (mData.size() > 5){
//                           mData.remove(5);
//                       }
                       mReportImageAdapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.i("ReportActivity", "onError: "+e.getMessage());
                   }
               }).launch();
    }

    @OnClick(R.id.btn_category)
    void selectCategory() {
        KeyboardUtils.hideSoftInput(this);
        EventCategoryPopupWindow popupWindow = new EventCategoryPopupWindow(this, new EventCategoryPopupWindow.OnEventCategorySelect() {
            @Override
            public void categorySelect(EventCategory eventCategory) {
                mTextCategoryName.setText("事件分类："+eventCategory.getCate_name());
                mPresenter.setCategoryId(eventCategory.getId());
            }
        });
        popupWindow.showAtLocation(mRootView, Gravity.NO_GRAVITY, 0, 0);
    }
    @OnClick(R.id.btn_location)
    void location(){
        KeyboardUtils.hideSoftInput(this);
        EventTownPopupWindow popupWindow = new EventTownPopupWindow(this, new EventTownPopupWindow.OnTownAndVillageSelectListener() {
            @Override
            public void selectTownAndVillage(int townId, int villageId,String address) {
                mPresenter.setTownId(townId);
                mPresenter.setVillageId(villageId);
                mTextAddress.setText("上报地点："+address);
            }
        });
        popupWindow.showAtLocation(mRootView, Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    protected ReportPresenter createPresenter() {
        return new ReportPresenter(this);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.iv_right) void right(){
        Intent intent = new Intent(this,ReportListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_commit) void commit(){
        String title = mEditTitle.getText().toString();
        if (TextUtils.isEmpty(title)){
            ToastUtils.showToast("请输入标题");
            return;
        }
        String content = mEditContent.getText().toString();
        if (mPresenter.getTownId() == 0 || mPresenter.getVillageId() == 0){
            ToastUtils.showToast("请选择上报地点");
            return;
        }
        if (TextUtils.isEmpty(content)){
            ToastUtils.showToast("请输入内容");
            return;
        }
        String houseNumber = mEditNumber.getText().toString();
        showProgress();
        StringBuffer eventImages = new StringBuffer();
        for (int i = 0 ; i < mData.size(); i++){
            if (mData.get(i).getType() == 2){
                Bitmap bitmap = BitmapFactory.decodeFile(mData.get(i).getImageFile());
                String image = FileUtils.Bitmap2StrByBase64(bitmap);
                if (eventImages.length() > 0){
                    eventImages.append(";"+image);
                }else {
                    eventImages.append(image);
                }
            }
        }
        if (mEdit){
            mPresenter.commitEventEdit(CommonUtils.StringToInt(mEventBean.getId()),title,content,eventImages.toString(),houseNumber);
        }else {
            mPresenter.commitEventReport(title,content,eventImages.toString(),houseNumber);
        }
    }

    @Override
    public void reportSuccess() {
        ToastUtils.showToast("事件上报成功");
        right();
    }
}
