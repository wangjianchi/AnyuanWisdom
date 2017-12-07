package com.ayfp.anyuanwisdom.view.contacts.adapter;

import android.view.View;
import android.widget.ImageView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.contacts.bean.Level0Item;
import com.ayfp.anyuanwisdom.view.contacts.bean.Level1Item;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.impl.customization.DefaultP2PSessionCustomization;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  18:03.
 * @description:
 */

public class ContactsAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ContactsAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv2);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                helper.setText(R.id.tv_title_name, lv0.title)
                        .setText(R.id.tv_number, lv0.subTitle)
                        .setImageResource(R.id.iv_head, lv0.isExpanded() ? R.mipmap.icon_contacts_down : R.mipmap.icon_contacts_right);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                helper.setText(R.id.tv_title_name, lv1.title)
                        .setText(R.id.tv_number, lv1.subTitle)
                        .setImageResource(R.id.iv_head, lv1.isExpanded() ? R.mipmap.icon_contacts_down : R.mipmap.icon_contacts_right);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final Person person = (Person) item;
                helper.setText(R.id.tv_name, person.getReal_name())
                .setText(R.id.tv_position,person.getJob_position());
                GlideUtils.loadImageView(person.getPortrait(),(ImageView) helper.getView(R.id.iv_head));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NimUIKit.startChatting(MyApplication.getContext(),person.getAccount(), SessionTypeEnum.P2P,new DefaultP2PSessionCustomization(),null);
                    }
                });
                break;
            default:
                break;
        }
    }
}
