package com.ayfp.anyuanwisdom.view.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.UserBean;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.home.HomeActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  14:32.
 * @description:
 */

public class LoginActivity extends BaseActivity{
    @BindView(R.id.et_account)
    EditText mEditAccount;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_login) void login(){
        String account = mEditAccount.getText().toString();
        String password = mEditPassword.getText().toString();
        if (TextUtils.isEmpty(account)){
            ToastUtils.showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtils.showToast("请输入密码");
            return;
        }
        showProgress();
        RetrofitService.getApi().login(RetrofitService.TOKEN,account,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<UserBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<UserBean>>() {

                    @Override
                    public void loadSuccess(AppResultData<UserBean> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                          if (data.getResult()!= null){
                              Preferences.saveUserId(data.getResult().getUser_id());
                              Preferences.saveUserName(data.getResult().getUser_name());
                              Preferences.saveUserAccount(data.getResult().getAccount());
                              Preferences.saveUserToken(data.getResult().getToken());
                              LoginInfo info = new LoginInfo(data.getResult().getAccount(),data.getResult().getToken());
                              NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallbackWrapper() {
                                  @Override
                                  public void onResult(int i, Object o, Throwable throwable) {
                                      dismissProgress();
                                      if (i == ResponseCode.RES_SUCCESS){
                                          Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }else {
                                          ToastUtils.showToast("聊天服务器登录失败，请稍后重试");
                                      }
                                  }
                              });
                          }else {
                              dismissProgress();
                              ToastUtils.showToast("账号或密码错误");
                          }
                        }else {
                            dismissProgress();
                            ToastUtils.showToast("账号或密码错误");
                        }
                    }
                });
    }
}
