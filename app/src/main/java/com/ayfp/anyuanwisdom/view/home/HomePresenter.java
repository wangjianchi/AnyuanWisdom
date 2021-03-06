package com.ayfp.anyuanwisdom.view.home;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventConfig;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.bean.Town;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.ayfp.anyuanwisdom.view.sign.bean.SignStatusBean;
import com.ayfp.anyuanwisdom.view.sign.presenter.SignPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 建池 on 2017/12/3.
 */

public class HomePresenter implements IBasePresenter {
    public IHomeView mView;
    public HomePresenter(IHomeView view){
        this.mView = view;
    }
    @Override
    public void getData() {
        getIndexNotice();
        getEventCategory();
        getEventDegree();
        getTown();
        getSignStatus();
        getConfig();
    }

    private void getIndexNotice(){
        RetrofitService.getApi().getIndexAffiche(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<List<NoticeListBean>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<NoticeListBean>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<NoticeListBean>> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mView.getNoticeList(data.getResult());
                        }
                    }
                });
    }

    private void getEventCategory(){
        RetrofitService.getApi().getEventCategory(RetrofitService.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<List<EventCategory>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<EventCategory>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<EventCategory>> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            if (data.getResult() != null && data.getResult().size() > 0){
                                AppCache.getInstance().setCategoryList(data.getResult());
                            }
                        }
                    }
                });
    }

    private void getEventDegree(){
        RetrofitService.getApi().getEventDegree(RetrofitService.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<List<EventDegree>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<EventDegree>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<EventDegree>> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            if (data.getResult() != null && data.getResult().size() > 0){
                                AppCache.getInstance().setDegreeList(data.getResult());
                            }
                        }
                    }
                });
    }

    private void getTown(){
        RetrofitService.getApi().getTownOptions(RetrofitService.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<List<Town>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<Town>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<Town>> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            if (data.getResult() != null && data.getResult().size() > 0){
                                AppCache.getInstance().setTowns(data.getResult());
                            }
                        }
                    }
                });
    }
    public void getSignStatus(){
        RetrofitService.getApi().getSignStatus(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<SignStatusBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<SignStatusBean>>() {
                    @Override
                    public void loadSuccess(AppResultData<SignStatusBean> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            if (data.getResult().getSign_status() == SignPresenter.SIGN_STATUS_IN){
                                mView.startLocation();
                            }
                        }
                    }
                });
    }

    public void getConfig(){
        RetrofitService.getApi().getConfig(RetrofitService.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<EventConfig>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<EventConfig>>() {
                    @Override
                    public void loadSuccess(AppResultData<EventConfig> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            if (data.getResult() != null && data.getResult().getEvent_status().size() > 0){
                                AppCache.getInstance().setStatusList(data.getResult().getEvent_status());
                            }
                        }
                    }
                });
    }

    @Override
    public void networkConnected() {

    }
}
