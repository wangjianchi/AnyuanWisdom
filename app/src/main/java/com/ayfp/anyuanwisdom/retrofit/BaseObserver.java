package com.ayfp.anyuanwisdom.retrofit;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ayfp.anyuanwisdom.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  16:49.
 * @description:
 */

public abstract class BaseObserver<E> implements Observer<E> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(E e) {
        Log.i("BaseObserver", "onNext: "+ JSON.toJSONString(e));
        loadSuccess(e);
        if (e instanceof AppResultData){
            AppResultData data = (AppResultData) e;
            if (data.getStatus() != RetrofitService.SUCCESS){
                if (RetrofitService.mErrorCode.containsKey(data.getStatus())){
                    ToastUtils.showToast(RetrofitService.mErrorCode.get(data.getStatus()));
                }
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
    public abstract void loadSuccess(E e);

}
