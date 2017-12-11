package com.ayfp.anyuanwisdom.view.personal;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.UserBean;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/11  16:50.
 * @description:
 */

public class EditPhoneActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.et_phone)
    EditText mEditPhone;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_phone;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("修改手机号");
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
    @OnClick(R.id.tv_commit) void commit(){
        final String phone = mEditPhone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            return;
        }
        showProgress();
        RetrofitService.getApi().editUserTel(RetrofitService.TOKEN, Preferences.getUserName(),phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {

                    @Override
                    public void loadSuccess(AppResultData<Object> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            dismissProgress();
                            UserBean userBean = AppCache.getInstance().getUserBean();
                            userBean.setPhone_tel(phone);
                            AppCache.getInstance().setUserBean(userBean);
                            setResult(RetrofitService.SUCCESS);
                            finish();
                        }
                    }
                });
    }
}
