package com.ayfp.anyuanwisdom.view.report.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.UIUtils;
import com.ayfp.anyuanwisdom.view.report.bean.EventListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2018/1/31  14:42.
 * @description:
 */

public class EventListAdapter extends BaseQuickAdapter<EventListBean,BaseViewHolder> {
    public EventListAdapter( @Nullable List<EventListBean> data) {
        super(R.layout.item_list_event, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventListBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_date,item.getReport_time())
        .addOnClickListener(R.id.iv_edit)
        .addOnClickListener(R.id.iv_delete)
        .addOnClickListener(R.id.layout_item);
        TextView textStatus = helper.getView(R.id.tv_status);
        if (item.getEvent_status_id().equals("1")){
            textStatus.setText("上报中");
            textStatus.setCompoundDrawablesWithIntrinsicBounds(UIUtils.getDrawable(R.mipmap.icon_status_up),null,null,null);
            textStatus.setTextColor(UIUtils.getColor(R.color.color_f93b3b));
        }else if (item.getEvent_status_id().equals("2")){
            textStatus.setText("处理中");
            textStatus.setCompoundDrawablesWithIntrinsicBounds(UIUtils.getDrawable(R.mipmap.icon_status_two),null,null,null);
            textStatus.setTextColor(UIUtils.getColor(R.color.color_fc9604));
        }else {
            textStatus.setText("处理完成");
            textStatus.setCompoundDrawablesWithIntrinsicBounds(UIUtils.getDrawable(R.mipmap.icon_status_three),null,null,null);
            textStatus.setTextColor(UIUtils.getColor(R.color.color_22ac38));
        }
    }

}
