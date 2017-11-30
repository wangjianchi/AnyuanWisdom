package com.ayfp.anyuanwisdom.view.report.presenter;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.view.report.iview.IReportView;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:24.
 * @description:
 */

public class ReportPresenter implements IBasePresenter {
    private IReportView mView;
    public ReportPresenter(IReportView view){
        this.mView = view;
    }

    @Override
    public void getData() {

    }

    @Override
    public void networkConnected() {

    }
}
