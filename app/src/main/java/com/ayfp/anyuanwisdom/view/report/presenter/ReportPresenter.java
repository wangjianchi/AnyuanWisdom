package com.ayfp.anyuanwisdom.view.report.presenter;

import android.text.TextUtils;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventConfig;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.report.iview.IReportView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:24.
 * @description:
 */

public class ReportPresenter implements IBasePresenter {
    private IReportView mView;
    private List<EventCategory> categoryList = new ArrayList<>();
    private List<EventDegree> degreeList = new ArrayList<>();
    private List<EventConfig.EventStatusBean> statusList = new ArrayList<>();
    private EventCategory mEventCategory;
    private String mCategoryId;
    private String mDegreeId;
    private String mStatusId;
    private int townId, villageId;
    public ReportPresenter(IReportView view){
        this.mView = view;
    }

    @Override
    public void getData() {
        try {
            categoryList = AppCache.getInstance().getCategoryList();
            degreeList = AppCache.getInstance().getDegreeList();
            statusList = AppCache.getInstance().getStatusList();
            mEventCategory = categoryList.get(0);
            mCategoryId = mEventCategory.getId();
        }catch (Exception e){

        }
    }

    public void commitEventReport(String title,String content,String images,String houseNubmer){
        if (TextUtils.isEmpty(mDegreeId)){
            ToastUtils.showToast("请选择事件程度");
            mView.loadComplete();
            return;
        }
        if (TextUtils.isEmpty(mStatusId)){
            ToastUtils.showToast("请选择事件状态");
            mView.loadComplete();
            return;
        }
        RetrofitService.getApi().eventReport(RetrofitService.TOKEN, Preferences.getUserName(), title, CommonUtils.StringToInt(mCategoryId)
                ,CommonUtils.StringToInt(mDegreeId),content,images,townId,villageId,houseNubmer,CommonUtils.StringToInt(mStatusId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {
                    @Override
                    public void loadSuccess(AppResultData<Object> objectAppResultData) {
                        mView.loadComplete();
                        if (objectAppResultData.getStatus() == RetrofitService.SUCCESS){
                            mView.reportSuccess();
                        }else {
                            ToastUtils.showToast(objectAppResultData.getStatusMsg());
                        }
                    }
                });

    }

    public void commitEventEdit(int eventId,String title,String content,String images,String houseNubmer){
        if (TextUtils.isEmpty(mDegreeId)){
            ToastUtils.showToast("请选择事件程度");
            mView.loadComplete();
            return;
        }
        if (TextUtils.isEmpty(mStatusId)){
            ToastUtils.showToast("请选择事件状态");
            mView.loadComplete();
            return;
        }
        RetrofitService.getApi().updateEventReport(RetrofitService.TOKEN,eventId, Preferences.getUserName(), title, CommonUtils.StringToInt(mCategoryId),
                CommonUtils.StringToInt(mDegreeId),content,images,townId,villageId,houseNubmer,CommonUtils.StringToInt(mStatusId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {
                    @Override
                    public void loadSuccess(AppResultData<Object> objectAppResultData) {
                        mView.loadComplete();
                        if (objectAppResultData.getStatus() == RetrofitService.SUCCESS){
                            mView.reportSuccess();
                        }else {
                            ToastUtils.showToast(objectAppResultData.getStatusMsg());
                        }
                    }
                });

    }


    public List<EventCategory> getCategoryList() {
        return categoryList;
    }

    public List<EventDegree> getDegreeList() {
        return degreeList;
    }

    public List<EventConfig.EventStatusBean> getStatusList() {
        return statusList;
    }

    public EventCategory getEventCategory() {
        return mEventCategory;
    }


    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    @Override
    public void networkConnected() {

    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public void setDegreeId(String degreeId) {
        mDegreeId = degreeId;
    }

    public void setStatusId(String statusId) {
        mStatusId = statusId;
    }
}
