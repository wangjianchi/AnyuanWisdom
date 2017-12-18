package com.ayfp.anyuanwisdom.view.report.presenter;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.EventCategory;
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
    private EventCategory mEventCategory;
    private EventDegree mEventDegree;
    private int townId, villageId;
    public ReportPresenter(IReportView view){
        this.mView = view;
    }

    @Override
    public void getData() {
        try {
            categoryList = AppCache.getInstance().getCategoryList();
            degreeList = AppCache.getInstance().getDegreeList();
            mEventCategory = categoryList.get(0);
        }catch (Exception e){

        }
    }

    public void commitEventReport(String title,String content,String images,String houseNubmer){
        RetrofitService.getApi().eventReport(RetrofitService.TOKEN, Preferences.getUserName(), title, CommonUtils.StringToInt(mEventCategory.getId())
                ,CommonUtils.StringToInt(mEventDegree.getId()),content,images,townId,villageId,houseNubmer)
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

    public EventCategory getEventCategory() {
        return mEventCategory;
    }

    public EventDegree getEventDegree() {
        return mEventDegree;
    }

    public void setEventCategory(EventCategory eventCategory) {
        mEventCategory = eventCategory;
    }

    public void setEventDegree(EventDegree eventDegree) {
        mEventDegree = eventDegree;
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
}
