package com.ayfp.anyuanwisdom.view.home;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;

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
    }

    private void getIndexNotice(){
        RetrofitService.getApi().getIndexAffiche(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<List<NoticeListBean>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<NoticeListBean>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<NoticeListBean>> data) {

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

    @Override
    public void networkConnected() {

    }
}
