package com.ayfp.anyuanwisdom.view.home;

import com.ayfp.anyuanwisdom.base.IBaseView;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;

import java.util.List;

/**
 * Created by 建池 on 2017/12/3.
 */

public interface IHomeView extends IBaseView {
    void getNoticeList(List<NoticeListBean> listBean);
    void startLocation();
}
