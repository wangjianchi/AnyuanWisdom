package com.ayfp.anyuanwisdom.view.contacts.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/5  14:30.
 * @description:
 */

public class RecentContactsAdapter extends BaseQuickAdapter<RecentContact, BaseViewHolder> {
    public RecentContactsAdapter(@Nullable List<RecentContact> data) {
        super(R.layout.item_recent_contact, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RecentContact item) {
        helper.setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_date, CommonUtils.getShowTime(item.getTime()));
        helper.setVisible(R.id.view_unred,item.getUnreadCount() > 0);
        UserInfoHelper.getUserInfo(item.getContactId(), new UserInfoHelper.UserInfoCallback() {
            @Override
            public void getUserInfo(NimUserInfo userInfo) {
                Log.i("RecentContactsAdapter", "getUserInfo: " + JSON.toJSONString(userInfo));
                helper.setText(R.id.tv_username,userInfo.getName());
                GlideUtils.loadImageViewErr(userInfo.getAvatar(),(ImageView) helper.getView(R.id.img_head),R.mipmap.image_head);
            }
        });

    }
}
