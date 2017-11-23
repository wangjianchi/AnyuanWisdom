package com.ayfp.anyuanwisdom.base;

/**
 * description: presenter
 * autour: wangjianchi
 * date: 2017/6/27 15:32
 * update: 2017/6/27
 * version:
*/

public interface IBasePresenter {
    /**
     * 获取网络数据，更新界面
     *
     */
    void getData();


    /**
     * 网络重新连接
     */
    void networkConnected();

}
