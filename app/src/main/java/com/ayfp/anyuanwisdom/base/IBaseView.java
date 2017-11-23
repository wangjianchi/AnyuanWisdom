package com.ayfp.anyuanwisdom.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by cd14 on 2017/6/27.
 * view 基础接口
 */

public interface IBaseView {
    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
    /**
     * 加载完成
     */
    void loadComplete();

}
