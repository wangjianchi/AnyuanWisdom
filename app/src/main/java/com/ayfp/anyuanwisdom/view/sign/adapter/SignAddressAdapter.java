package com.ayfp.anyuanwisdom.view.sign.adapter;

import android.support.annotation.Nullable;

import com.ayfp.anyuanwisdom.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/27  14:44.
 * @description:
 */

public class SignAddressAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SignAddressAdapter(@Nullable List<String> data) {
        super(R.layout.item_event_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_category_name,item);
    }
}
