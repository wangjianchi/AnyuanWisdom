package com.ayfp.anyuanwisdom.view.contacts.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/18  16:52.
 * @description:
 */

public class SearchContactAdpater extends BaseQuickAdapter<Person,BaseViewHolder>{
    public SearchContactAdpater(@Nullable List<Person> data) {
        super(R.layout.item_expandable_lv2, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Person person) {
        helper.setText(R.id.tv_name, person.getReal_name())
                .setText(R.id.tv_position,person.getJob_position());
        GlideUtils.loadImageView(person.getPortrait(),(ImageView) helper.getView(R.id.iv_head));
    }
}
