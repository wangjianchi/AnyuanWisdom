package com.ayfp.anyuanwisdom.view.notice.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeDetail;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/15  10:41.
 * @description:
 */

public class ReadUserAdpater extends BaseQuickAdapter<NoticeDetail.ReadUsersBean,BaseViewHolder>{
    public ReadUserAdpater( @Nullable List<NoticeDetail.ReadUsersBean> data) {
        super(R.layout.item_read_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeDetail.ReadUsersBean item) {
        if (item.getType() != 1){
            helper.setText(R.id.tv_name,item.getReal_name());
            GlideUtils.loadImageViewErr(item.getPortrait(),(ImageView)helper.getView(R.id.iv_head),R.mipmap.image_head);
        }
    }
}
