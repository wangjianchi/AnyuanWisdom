package com.ayfp.anyuanwisdom.view.contacts.adapter;

import android.support.annotation.Nullable;

import com.ayfp.anyuanwisdom.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/5  14:30.
 * @description:
 */

public class RecentContactsAdapter extends BaseQuickAdapter<RecentContact,BaseViewHolder> {
    public RecentContactsAdapter(@Nullable List<RecentContact> data) {
        super(R.layout.item_recent_contact, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecentContact item) {

    }
}
