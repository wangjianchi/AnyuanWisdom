package com.ayfp.anyuanwisdom.view.personal.adapter;


import android.support.annotation.Nullable;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.view.personal.bean.PersonSettingBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/7  11:49.
 * @description:
 */

public class PersonSettingAdapter extends BaseQuickAdapter<PersonSettingBean,BaseViewHolder> {
    public PersonSettingAdapter( @Nullable List<PersonSettingBean> data) {
        super(R.layout.item_personal_setting, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonSettingBean item) {
        helper.setText(R.id.tv_personal_name,item.getName());
        helper.setImageDrawable(R.id.iv_personal_icon,item.getDrawable());
        if (item.isDiliver()){
            helper.setVisible(R.id.view_line, false)
                    .setVisible(R.id.view_deliver,true);
        }else {
            helper.setVisible(R.id.view_line, true)
                    .setVisible(R.id.view_deliver,false);
        }
    }
}
