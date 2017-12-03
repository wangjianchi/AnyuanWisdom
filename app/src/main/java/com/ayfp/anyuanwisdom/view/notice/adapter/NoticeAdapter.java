package com.ayfp.anyuanwisdom.view.notice.adapter;

import android.support.annotation.Nullable;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/11/29  14:39.
 * @description:
 */

public class NoticeAdapter extends BaseQuickAdapter<NoticeListBean,BaseViewHolder> {
    public NoticeAdapter( @Nullable List<NoticeListBean> data) {
        super(R.layout.item_notice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeListBean item) {
        helper.setText(R.id.tv_notice_title,item.getTitle())
                .setText(R.id.tv_notice_content,item.getContent())
                .setText(R.id.tv_notice_author,item.getAuthor()+" "+item.getAdd_time());

    }
}
