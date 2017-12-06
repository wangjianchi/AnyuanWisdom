package com.ayfp.anyuanwisdom.view.report.adapter;

import android.support.annotation.Nullable;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * @author:: wangjianchi
 * @time: 2017/12/6  10:57.
 * @description:
 */

public class EventCategoryAdapter extends BaseQuickAdapter<EventCategory,BaseViewHolder> {
    public EventCategoryAdapter( @Nullable List<EventCategory> data) {
        super(R.layout.item_event_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventCategory item) {
        helper.setText(R.id.tv_category_name,item.getCate_name());
    }
}
