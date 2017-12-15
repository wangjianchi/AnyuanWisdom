package com.ayfp.anyuanwisdom.view.personal;

import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/7  17:50.
 * @description:
 */

public class EditPasswordActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.et_old_password)
    EditText mEditOld;
    @BindView(R.id.et_new_password)
    EditText mEditNew;
    @BindView(R.id.et_confirm_password)
    EditText mEditConfirm;
    @BindView(R.id.tv_show_password)
    TextView mTextShowPassword;

    private boolean showPassword = false;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_password;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("修改密码");
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
    @OnClick(R.id.tv_show_password) void showPassword(){
        if (!showPassword){
            mEditOld.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mEditNew.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mEditConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else {
            mEditOld.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditNew.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        showPassword = !showPassword;
        mTextShowPassword.setCompoundDrawablesWithIntrinsicBounds(showPassword?getResources().getDrawable(R.mipmap.icon_password_select):getResources().getDrawable(R.mipmap.icon_password_unselect),null,null,null);
    }
    @OnClick(R.id.tv_commit) void commit(){
        String old_password = mEditOld.getText().toString();
        String new_password = mEditNew.getText().toString();
        String confirm_password = mEditConfirm.getText().toString();
        if (TextUtils.isEmpty(old_password) || TextUtils.isEmpty(new_password) || TextUtils.isEmpty(confirm_password)){
            ToastUtils.showToast("密码不能为空");
            return;
        }
        if (!new_password.equals(confirm_password)){
            ToastUtils.showToast("两次密码不一致");
            return;
        }
        RetrofitService.getApi().editPassword(RetrofitService.TOKEN, Preferences.getUserName(),old_password,new_password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {

                    @Override
                    public void loadSuccess(AppResultData<Object> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            ToastUtils.showToast("修改密码成功");
                            finish();
                        }else {
                            ToastUtils.showToast(data.getStatusMsg());
                        }
                    }
                });
    }
}
