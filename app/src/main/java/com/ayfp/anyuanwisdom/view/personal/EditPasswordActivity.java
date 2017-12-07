package com.ayfp.anyuanwisdom.view.personal;

import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

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

    }
}
