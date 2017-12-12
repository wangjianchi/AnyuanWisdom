package com.ayfp.anyuanwisdom.view.report.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.bean.Town;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2017/12/12  15:08.
 * @description:
 */

public class EventTownAdapter extends BaseQuickAdapter<Town,BaseViewHolder> {
    private boolean village;
    public EventTownAdapter(@Nullable List<Town> data) {
        super(R.layout.item_town, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Town item) {
        TextView tv = helper.getView(R.id.tv_town);
        if (village){
            helper.setText(R.id.tv_town,item.getVillage_name());
        }else {
            helper.setText(R.id.tv_town, item.getTown_name());
        }
        if (item.isSelect()){
            helper.setBackgroundRes(R.id.tv_town,R.drawable.bg_town_select);
        }else {
            helper.setBackgroundColor(R.id.tv_town, Color.WHITE);
        }
    }

    public boolean isVillage() {
        return village;
    }

    public void setVillage(boolean village) {
        this.village = village;
    }
}
