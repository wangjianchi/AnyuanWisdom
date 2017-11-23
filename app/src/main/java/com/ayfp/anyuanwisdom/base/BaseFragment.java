package com.ayfp.anyuanwisdom.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by cd14 on 2017/7/21.
 */

public abstract class BaseFragment<P extends IBasePresenter> extends RxFragment implements IBaseView {
    protected Context mContext;
    protected String TAG;
    public LinearLayout layout_no_network;
    public Button btn_nonetwork;
    protected P mPresenter;
    protected View mRootView;
    protected boolean isVisible = true;
    private boolean isPrepared; //懒加载是否就绪


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        TAG=getClass().getSimpleName();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(attachLayoutRes(),null);
            ButterKnife.bind(this,mRootView);
            if (isVisible){
                initViews();
            } else{
                isPrepared = true;
            }
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible() {
        if (mRootView != null && isPrepared){
            initViews();
            isPrepared = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }
    protected void onInvisible() {
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();
    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();
}
