package com.ayfp.anyuanwisdom.weidgts;

import com.ayfp.anyuanwisdom.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:08.
 * @description:
 */

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return  R.id.load_more_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return  R.id.load_more_load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end;
    }
}
